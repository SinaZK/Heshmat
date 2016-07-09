package Cars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;

import BaseCar.AttachPart;
import BaseCar.NormalCar;
import DataStore.CarStatData;
import Entity.AnimatedSpriteSheet;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.Log;
import Misc.TextureHelper;
import Physics.CzakBody;
import PhysicsFactory.*;

/**
 * Created by sinazk on 7/7/16.
 * 12:45
 */

public class PeykanOffroad extends NormalCar
{
	public PeykanOffroad(GameManager gm, CarStatData carStatData)
	{
		super(gm, carStatData);
	}

	@Override
	public void create()
	{
		Sprite tmpSprite = new Sprite(body.bodies.get(0).getmSprite().get(0));//
		body.bodies.get(0).getmSprite().clear();
		body.bodies.get(0).getmSprite().add(tmpSprite);

		createAttachedParts();

		super.create();
	}

	@Override
	public void drawBody(Batch batch)
	{
		body.flushSprites();
		body.bodies.get(0).draw(batch);//car Skeleton

		for(int i = 0;i < attachParts.size();i++)
			attachParts.get(i).draw(batch);

		for(int i = 1;i < body.bodies.size();i++)//wheels
			body.bodies.get(i).draw(batch);
	}

	AnimatedSpriteSheet partSheet;
	AttachPart enginePart, boxPart, tirePart;
	public void createAttachedParts()
	{

		partSheet = new AnimatedSpriteSheet("gfx/car/3/misc.png", gameScene.disposeTextureArray);

		partSheet.addAnimation("engine", 15, 16, 268, 96, 1, 2, -1);
		partSheet.addAnimation("box"   , 10, 116, 316, 172, 1, 2, -1);
		partSheet.addAnimation("tire"  , 11, 178, 360, 245, 1, 2, -1);

		enginePart = new AttachPart(this);
		enginePart.create(170, 20, 70, 50, 0, true);
		enginePart.addSprite(partSheet.getAnimation("engine").sprites[0], true);
		enginePart.addSprite(partSheet.getAnimation("engine").sprites[1], true);
		enginePart.setPercent(80, 50);
		attachParts.add(enginePart);

		boxPart = new AttachPart(this);
		boxPart.create(25, -75, 100, 37, 0, true);
		boxPart.addSprite(partSheet.getAnimation("box").sprites[0], true);
		boxPart.addSprite(partSheet.getAnimation("box").sprites[1], true);
		boxPart.setPercent(50, 40);
		attachParts.add(boxPart);

		tirePart = new AttachPart(this);
		tirePart.create(-170, 15, 100, 40, 25.5f, true);
		tirePart.addSprite(partSheet.getAnimation("tire").sprites[0], true);
		tirePart.addSprite(partSheet.getAnimation("tire").sprites[1], true);
		tirePart.setPercent(30, 10);
		attachParts.add(tirePart);
	}

	@Override
	public void run(boolean isGas, boolean isBrake, float rate)
	{
		super.run(isGas, isBrake, rate);
	}

	@Override
	public void reset()
	{
		super.reset();

		for(int i = 0;i < attachParts.size();i++)
			attachParts.get(i).reset();
	}

	@Override
	public void hitByDrivingEnemy(Contact contact)
	{
		super.hitByDrivingEnemy(contact);

	}

}
