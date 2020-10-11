package abstractclasses;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import logic.Rotation;
import tasks.ChangeImageTask;
import world.Animation;
import world.World;
import world.World.Layers;

/**
 * Die Grundklasse aller Entities. Um als ein Entity klassifiziert zu werden,
 * muss das Objekt / Lebewesen sich bewegen können.
 */
public abstract class Entity {

	private Rotation rotation;
	private Boolean interactable;
	private Boolean passable;
	private String description;
	private Point position;
	
	private Animation actualanimation;
	private ArrayList<Animation> animations;
	private ChangeImageTask animationtask;

	private World world;

	protected Entity(Boolean passable, Boolean interactable, Point position) {
		this.interactable = interactable;
		this.passable = passable;
		this.rotation = Rotation.right;
		this.position = position;
		this.description = "default description";
		this.world = null;
		
		animations = new ArrayList<>();
		animationtask = new ChangeImageTask(10,this,Layers.Objects,-1);
	}

	/**
	 * is calles when added to a world
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	public Boolean getInteractable() {
		return interactable;
	}

	public Boolean getPassable() {
		return passable;
	}

	public BufferedImage getImage() {
		return actualanimation.getActualImg();
	}

	public void addAnimation(Animation animation) {
		animations.add(animation);
	}

	public void nextImage() {
		actualanimation.nextImage();
	}
	
	public void triggerObjektAnimation(Animation animation) {
		actualanimation = animation;
	}
	
	public ArrayList<Animation> getObjektanimations() {
		return animations;
	}

	public void setPassable(Boolean passable) {
		this.passable = passable;
	}

	public Point getPosition() {
		return position;
	}

	public Integer getX() {
		return (int) getPosition().getX();
	}

	public Integer getY() {
		return (int) getPosition().getY();
	}

	public String getDescription() {
		return description;
	}

	public World getWorld() {
		return world;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPosition(Point newPosition, World world) {
		position = newPosition;
		world.getTile(getX(), getY()).onSteppedUpon(this);
	}

	public void interact() {
		Point pos = new Point(-1, -1);
		switch (rotation) {
		case down:
			if (position.y < world.getHeight() - 1)
				pos = new Point(position.x, position.y - 1);
			break;
		case left:
			if (position.x > 0)
				pos = new Point(position.x - 1, position.y);
			break;
		case right:
			if (position.x < world.getWidth() - 1)
				pos = new Point(position.x + 1, position.y);
			break;
		case up:
			if (position.y > 0)
				pos = new Point(position.x, position.y - 1);
			break;
		}
		Tile tile = world.getTile(pos.x, pos.y);
		if (tile != null)
			tile.onInteract(this);
		for (Entity e : world.getEntitys())
			if (e.getPosition().equals(pos))
				e.onInteract(this);
	}
	
	public abstract void onInteract(Entity entity);
	
	public String getName() {
		return this.getClass().getName().replace("tiles.", "");
	}
	
	public void remove() {
		animationtask.end();
	}

}
