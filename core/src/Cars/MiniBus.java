package Cars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import BaseCar.AttachPart;
import BaseCar.NormalCar;
import DataStore.CarStatData;
import Entity.AnimatedSpriteSheet;
import GameScene.GameManager;
import Misc.TextureHelper;
import Physics.CzakBody;

/**
 * Created by sinazk on 7/7/16.
 * 12:45
 */

public class MiniBus extends NormalCar
{
	public MiniBus(GameManager gm, CarStatData carStatData)
	{
		super(gm, carStatData);
	}

	Sprite layerSprite;
	@Override
	public void create()
	{

		layerSprite = new Sprite(TextureHelper.loadTexture("gfx/car/7/layer.png", gameScene.disposeTextureArray));

		Sprite tmp = body.bodies.get(0).getmSprite().get(0);
		body.bodies.get(0).getmSprite().clear();
		body.bodies.get(0).addSprite(layerSprite);
		body.bodies.get(0).addSprite(tmp);


		createPassengers();
		createAttachedParts();

		super.create();
	}

	@Override
	public void drawBody(Batch batch)
	{
		body.flushSprites();
		body.bodies.get(0).draw(batch, 0);//layer

		for(int i = 0;i < body.bodies.size();i++)
			body.bodies.get(i).draw(batch);

		body.bodies.get(0).draw(batch, 1);

		for(int i = 0;i < attachParts.size();i++)
			attachParts.get(i).draw(batch);
	}

	AnimatedSpriteSheet partSheet;
	AttachPart doorPart, lightPart;
	public void createAttachedParts()
	{
		partSheet = new AnimatedSpriteSheet("gfx/car/7/misc.png", gameScene.disposeTextureArray);

		partSheet.addAnimation("door", 156, 5, 266, 220, 1, 1, -1);
		partSheet.addAnimation("light", 295, 7, 312, 94, 2, 1, -1);

		lightPart = new AttachPart(this);
		lightPart.create(340, -83, 17, 43, 0, true);
		lightPart.addSprite(partSheet.getAnimation("light").sprites[0], true);
		lightPart.addSprite(partSheet.getAnimation("light").sprites[1], true);
		lightPart.setPercent(80, 50);
		attachParts.add(lightPart);


		doorPart = new AttachPart(this);
		doorPart.create(70, -23, 113, 218, 0, true);
		doorPart.addSprite(partSheet.getAnimation("door").sprites[0], true);
		doorPart.setPercent(5, 30);
		attachParts.add(doorPart);
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

	public void createPassengers()
	{
		for(int i = 1;i <= 3;i++)
		{
			CzakBody mainBody = body.getBodyByName("pmb" + i);
			body.addBodyWithWeld(mainBody, "mainBody", gameScene.world);
			setLimitForJoint("pr" + i, -10, 20, false);

			
		}

	}

	public void setLimitForJoint(String name, float lower, float upper, boolean enableMotor)
	{
		RevoluteJoint revoluteJoint1 = (RevoluteJoint) body.getJointByName(name);
		revoluteJoint1.enableLimit(true);
		revoluteJoint1.setLimits((float) Math.toRadians(lower), (float) Math.toRadians(upper));
		revoluteJoint1.enableMotor(enableMotor);
		revoluteJoint1.setMotorSpeed(0);
	}
}
