package logic;


import Enums.Rotations;
import Enums.Signalcolors;
import entitys.Box;
import tiles.Schalter;
import tiles.Computer;
import tiles.Förderband;
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
		world = new World(8,8);

		world.setTile(0,0,new Förderband(Rotations.right,Signalcolors.red,2));
		world.setTile(1,0,new Förderband(Rotations.right,Signalcolors.red,2));
		world.setTile(2,0,new Förderband(Rotations.down,Signalcolors.green,3));
		world.setTile(2,1,new Förderband(Rotations.down,Signalcolors.green,3));
		world.setTile(2,2,new Förderband(Rotations.right,Signalcolors.green,1));
		world.setTile(1,2,new Förderband(Rotations.left,Signalcolors.green,1));
		world.setTile(0,2,new Förderband(Rotations.up,Signalcolors.red,3));
		world.setTile(0,1,new Förderband(Rotations.up,Signalcolors.red,3));

		world.setTile(4,1,new Computer(Signalcolors.green));

		world.setTile(0,3,new Schalter(Signalcolors.red));
		world.setTile(3,1,new Schalter(Signalcolors.green));

		world.addEntity(new Box(0,0));
		world.addEntity(new Box(2,0));
		world.addEntity(new Box(2,2));
		world.addEntity(new Box(0,2));
	}

	public static World getWorld() {
		return world;
	}

}
