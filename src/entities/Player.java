package entities;

import world.Tile;

public class Player extends world.Entity {

	public enum DirectionOfView {
		right, left, up, down
	}

	public DirectionOfView direction;

	public void walk() {
		if (getTileInFront().getPassable()) {
			switch (direction) {
			case right:
				break;
			case left:
				break;
			case up:
				break;
			case down:
				break;
			}
		}
	}

	public void interact() {
		switch (direction) {
		case down:
			if (logic.Main.world.getTile((int) getPosition().getX(), (int) getPosition().getY() + 1)
					.getInteractable()) {
				logic.Main.world.getTile((int) getPosition().getX(), (int) getPosition().getY() + 1).onInteract();
			}
			break;
		case left:
			if (logic.Main.world.getTile((int) getPosition().getX() - 1, (int) getPosition().getY())
					.getInteractable()) {
				logic.Main.world.getTile((int) getPosition().getX() - 1, (int) getPosition().getY()).onInteract();
			}
			break;
		case right:
			if (logic.Main.world.getTile((int) getPosition().getX() + 1, (int) getPosition().getY())
					.getInteractable()) {
				logic.Main.world.getTile((int) getPosition().getX() + 1, (int) getPosition().getY()).onInteract();
			}
			break;
		case up:
			if (logic.Main.world.getTile((int) getPosition().getX(), (int) getPosition().getY() - 1)
					.getInteractable()) {
				logic.Main.world.getTile((int) getPosition().getX(), (int) getPosition().getY() - 1).onInteract();
			}
			break;
		}
	}

	@Override
	public void onTick() {
	}

	private Tile getTileInFront() {
		switch (direction) {
		case down:
			return logic.Main.world.getTile((int) getPosition().getX(), (int) getPosition().getY() + 1);
		case left:
			return logic.Main.world.getTile((int) getPosition().getX() - 1, (int) getPosition().getY());
		case right:
			return logic.Main.world.getTile((int) getPosition().getX() + 1, (int) getPosition().getY());
		case up:
			return logic.Main.world.getTile((int) getPosition().getX(), (int) getPosition().getY() - 1);
		}
		return null;
	}

}
