// Compose Resources fetches `./composeResources/...` at run time, but the
// generated Karma config serves only the test bundle. Without this every
// string resource resolves to nothing in the browser tests, so a test that
// asserts on resource backed text can never pass there.
config.files.push({
    pattern: __dirname + "/kotlin/composeResources/**",
    included: false,
    served: true,
    watched: false,
});
config.proxies["/composeResources/"] = "/base/kotlin/composeResources/";
