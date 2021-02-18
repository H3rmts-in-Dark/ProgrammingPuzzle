class World {
    constructor(name, height, width, tiles, entities) {
        this.name = name
        this.created = new Date().toLocaleString()
        this.height = height
        this.width = width
        this.tiles = tiles
        this.entities = entities
    }
}

class Tile {
    constructor(type, signalcolor, rotation, speed, x, y) {
        this.type = type
        this.signalcolor = signalcolor
        this.rotation = rotation
        this.speed = speed
        this.x = x
        this.y = y
    }
}

class Entity {
    constructor(rotation, x, y) {
        this.rotation = rotation
        this.x = x
        this.y = y
    }
}

class Computer extends Tile { constructor(signalcolor, x, y) { super("computer", signalcolor, null, null, x, y) } }
class Default extends Tile { constructor(signalcolor, x, y) { super("default", signalcolor, null, null, x, y) } }
class Gewichtssensor extends Tile { constructor(signalcolor, x, y) { super("gewichtssensor", signalcolor, null, null, x, y) } }
class Förderband extends Tile { constructor(signalcolor, rotation, speed, x, y) { super("förderband", signalcolor, rotation, speed, x, y) } }
class Lampe extends Tile { constructor(signalcolor, x, y) { super("lampe", signalcolor, null, null, x, y) } }
class Tonne extends Tile { constructor(x, y) { super("tonne", null, null, null, x, y) } }

console.log(JSON.stringify(new World("WorldBlueprint", 2, 2, [new Druckplatte("blau", 1, 1)], [])))
