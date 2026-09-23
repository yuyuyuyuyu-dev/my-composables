import json
import re
import sys
import urllib.request

MAVEN = "https://repo1.maven.org/maven2"
CATALOG = "gradle/libs.versions.toml"
VERSION = re.compile(r"(\d+)\.(\d+)\.(\d+)(?:-(alpha|beta|rc)(\d+))?")
STAGES = ["alpha", "beta", "rc", None]


def fetch(path):
    with urllib.request.urlopen(f"{MAVEN}/{path}") as response:
        return response.read().decode()


def versions(group, artifact):
    metadata = fetch(f"{group.replace('.', '/')}/{artifact}/maven-metadata.xml")
    return re.findall(r"<version>([^<]+)</version>", metadata)


def key(version):
    match = VERSION.fullmatch(version)
    if not match:
        return None
    major, minor, patch, stage, build = match.groups()
    return int(major), int(minor), int(patch), STAGES.index(stage), int(build or 0)


def line(version):
    return version.split(".")[:2]


def catalog_version(name):
    with open(CATALOG) as catalog:
        return re.search(rf'^{name} = "([^"]+)"$', catalog.read(), re.MULTILINE).group(1)


def compose_requirements(material3):
    path = f"org/jetbrains/compose/material3/material3/{material3}/material3-{material3}.module"
    module = json.loads(fetch(path))
    for variant in module.get("variants", []):
        for dependency in variant.get("dependencies", []):
            group = dependency["group"]
            if group.startswith("org.jetbrains.compose.") and not group.startswith("org.jetbrains.compose.material3"):
                version = dependency.get("version", {})
                yield version.get("strictly") or version.get("requires")


def works_with(material3, compose):
    return all(
        required is not None and key(required) is not None and key(required) <= key(compose)
        for required in compose_requirements(material3)
    )


def main():
    current_compose = catalog_version("composeMultiplatform")
    current_material3 = catalog_version("material3")
    stable = [v for v in versions("org.jetbrains.compose", "org.jetbrains.compose.gradle.plugin") if key(v) and key(v)[3] == 3]
    compose = max(stable, key=key)
    if key(current_compose) and key(compose) < key(current_compose):
        print(f"compose={current_compose}")
        print(f"material3={current_material3}")
        return
    candidates = sorted(
        (v for v in versions("org.jetbrains.compose.material3", "material3") if key(v) and line(v) == line(compose)),
        key=key,
        reverse=True,
    )
    material3 = next((v for v in candidates if works_with(v, compose)), None)
    if material3 is None:
        sys.exit(f"No material3 on the {'.'.join(line(compose))} line works with Compose Multiplatform {compose}.")
    print(f"compose={compose}")
    print(f"material3={material3}")


main()
