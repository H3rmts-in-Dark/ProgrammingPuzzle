package tasks;


import abstractclasses.Entity;
import abstractclasses.Task;


public class MoveEntityTask extends Task {

	private Entity entity;

	public MoveEntityTask(Integer tickDifference,Entity entity) {
		super(tickDifference,TILEHEIGHTWIDHT);
		this.entity = entity;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void runCode() { /*
		System.out.println("switch");
		switch (entity.getRotation()) {
			case down:
				entity.changePixelpositionY(1);
			break;
			case left:
				entity.changePixelpositionX(-1);
			break;
			case right:
				entity.changePixelpositionX(1);
			break;
			case up:
				entity.changePixelpositionY(-1);
			break;
				// $CASES-OMITTED$
		}
		WindowRepaintTask.RepaintWindow(entity.getWorld().getWindow());*/
	}

	@Override
	public void onEnd() { 
		//entity.getWorld().getTile(entity.getPosition().y,entity.getPosition().y).onSteppedUpon(entity);
	}

}
