"use strict";
const http = require("http")
const fs = require("fs")
const ImagePaths = ["computer", "default", "favicon", "foerderband-down", "foerderband-left", "foerderband-right", "foerderband-up",
    "gewichtssensor", "lampe", "schalter", "tonne"]

var WorldCreator = ""
var Images = {}

function loadImage(name) {
    fs.readFile(`./Images/${name}.png`, (error, data) => { if (error) throw error; Images[name] = data })
}

async function load() {
    fs.readFile("./WorldCreator.html", "utf8", (error, data) => { if (error) throw error; WorldCreator = data })
    for (var i = 0; i < ImagePaths.length; i++)
        loadImage(ImagePaths[i])
}
load()

http.createServer(async function (request, response) {
    if (request.url == "/favicon.ico") {
        response.writeHead(200, { 'Content-Type': 'image/png', 'Access-Control-Allow-Origin': "http://192.168.1.5:7070/" })
        response.end(Images["favicon"])
    } else if (ImagePaths.includes(request.url.slice(1).replace("/", "-"))) {
        response.writeHead(200, { 'Content-Type': 'image/png', 'Access-Control-Allow-Origin': "http://192.168.1.5:7070" })
        response.end(Images[request.url.slice(1).replace("/", "-")])
    } else if (request.url == "/") {
        response.writeHead(200, { 'Content-Type': 'text/html' })
        response.end(WorldCreator)
    }
}).listen(7070)