const fs = require("node:fs");
const path = require("node:path");

const measurementDir = path.join(__dirname, "measurement");
fs.mkdirSync(measurementDir, { recursive: true });
const marksFile = path.join(measurementDir, "marks.log");
fs.writeFileSync(marksFile, "");

const hooksFile = path.join(measurementDir, "hooks.js");
fs.writeFileSync(
	hooksFile,
	`
function mark(kind, test) {
	const request = new XMLHttpRequest();
	request.open("GET", "/__mark?" + kind + "&" + encodeURIComponent(test.fullTitle()) + "&" + (test.state || ""), false);
	request.send();
}
beforeEach(function () { mark("start", this.currentTest); });
afterEach(function () { mark("end", this.currentTest); });
`,
);

config.files.unshift({
	pattern: hooksFile,
	included: true,
	served: true,
	watched: false,
});

const measurementMarksFactory = () => (request, response, next) => {
	if (!request.url.startsWith("/__mark?")) {
		next();
		return;
	}
	const [kind, test, state] = request.url.slice("/__mark?".length).split("&");
	fs.appendFileSync(
		marksFile,
		`${Date.now()}\t${kind}\t${decodeURIComponent(test)}\t${state}\n`,
	);
	response.end("ok");
};
measurementMarksFactory.$inject = [];

config.plugins.push({
	"middleware:measurementMarks": ["factory", measurementMarksFactory],
});
config.beforeMiddleware = (config.beforeMiddleware || []).concat([
	"measurementMarks",
]);

config.client.mocha = Object.assign(config.client.mocha || {}, {
	timeout: 60000,
});
