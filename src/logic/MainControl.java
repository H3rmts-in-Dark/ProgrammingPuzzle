package logic;


import Enums.Cabletype;
import Enums.Signalcolor;
import tiles.Default;
import tiles.Schalter;
import world.World;


public class MainControl {

	private static GameTicker gameTicker;
	private static World world;

	private MainControl() {
	}

	public static void initialize() {
		gameTicker = new GameTicker();
	}

	public static void start() {
		getGameTicker().start();
	}

	public static GameTicker getGameTicker() {
		return gameTicker;
	}

	public static void createWorld() {
		world = new World(4,3);
		
		world.setTile(0,0,new Default(Signalcolor.blue,Cabletype.so));
		world.setTile(0,2,new Default(Signalcolor.blue,Cabletype.no));
		world.setTile(1,0,new Default(Signalcolor.blue,Cabletype.osw));
		world.setTile(3,2,new Default(Signalcolor.blue,Cabletype.nw));
		world.setTile(2,0,new Default(Signalcolor.blue,Cabletype.m));
		world.setTile(3,0,new Default(Signalcolor.blue,Cabletype.w));
		world.setTile(2,1,new Default(Signalcolor.blue,Cabletype.sn));
		world.setTile(2,2,new Default(Signalcolor.blue,Cabletype.wno));
		world.setTile(3,1,new Default(Signalcolor.blue,Cabletype.s));
		world.setTile(1,1,new Default(Signalcolor.blue,Cabletype.nws));
		world.setTile(1,2,new Default(Signalcolor.blue,Cabletype.wno));
		world.setTile(0,1,new Schalter(Signalcolor.blue,Cabletype.so));
	}

	public static World getWorld() {
		return world;
	}

}


/**
world.setTile(0,0,new Förderband(Rotation.right,2,Signalcolor.red,Cabletype.notype));
		world.setTile(1,0,new Förderband(Rotation.right,2,Signalcolor.red,Cabletype.notype));
		world.setTile(2,0,new Förderband(Rotation.down,2,Signalcolor.blue,Cabletype.notype));
		world.setTile(2,1,new Förderband(Rotation.down,2,Signalcolor.blue,Cabletype.notype));
		world.setTile(2,2,new Förderband(Rotation.right,2,Signalcolor.blue,Cabletype.notype));
		world.setTile(1,2,new Förderband(Rotation.left,2,Signalcolor.blue,Cabletype.notype));
		world.setTile(0,2,new Förderband(Rotation.up,2,Signalcolor.red,Cabletype.notype));
		world.setTile(0,1,new Förderband(Rotation.up,2,Signalcolor.red,Cabletype.notype));

		world.setTile(4,1,new Computer(Signalcolor.blue,Cabletype.w));

		world.setTile(0,3,new Schalter(Signalcolor.red,Cabletype.notype));
		world.setTile(3,1,new Schalter(Signalcolor.blue,Cabletype.no));

		world.setTile(3,0,new Lampe(Signalcolor.blue,Cabletype.notype));

		world.setTile(3,2,new Entitysensor(Signalcolor.green,Cabletype.notype));
		world.setTile(3,3,new Lampe(Signalcolor.green,Cabletype.notype));
		world.setTile(4,2,new Computer(Signalcolor.green,Cabletype.notype));

		world.addEntity(new Box(0,0));
		world.addEntity(new Box(2,0));
		world.addEntity(new Box(2,2));
		world.addEntity(new Box(0,2));
*/