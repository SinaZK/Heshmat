package Human;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;

import BaseCar.BaseCar;
import GameScene.GameManager;
import GameScene.GameScene;
import Misc.BodyStrings;
import Misc.Log;
import Misc.TextureHelper;
import Physics.CzakBody;
import Physics.SizakBody;
import Physics.SizakBodyLoader;
import PhysicsFactory.PhysicsConstant;

/**
 * Created by sinazk on 7/9/16.
 * 12:28
 */
public class DriverHuman
{
	GameManager gameManager;
	BaseCar car;
	public SizakBody body;
	public WeldJoint carWeldJoint;
	public RevoluteJoint neckRevolute;

	public static float lowLimit = (float)Math.toRadians(-25);
	public static float highLimit = (float)Math.toRadians(25);

	Sprite headSprite, bodySprite;
	float rat = PhysicsConstant.PIXEL_TO_METER;

	public DriverHuman(GameManager gameManager)
	{
		this.gameManager = gameManager;
		car = gameManager.selectedCar;

		headSprite = new Sprite(TextureHelper.loadTexture("gfx/human/head.png", gameManager.gameScene.disposeTextureArray));
		bodySprite = new Sprite(TextureHelper.loadTexture("gfx/human/body.png", gameManager.gameScene.disposeTextureArray));

		if(car.driverScale == 2)
			headSprite.setSize(50, 50);
		if(car.driverScale == 1)
		{
			bodySprite.setScale(3f/5f);
			headSprite.setSize(35, 30);
		}

		reset();
		attachToCar();
		addNeck();
		resetCT = 0;
	}

	public void loadBody()
	{
		body = SizakBodyLoader.loadBodyFile("gfx/human/driver" + (int)car.driverScale + "x.body", gameManager.gameScene.world, gameManager.gameScene.disposeTextureArray);

		body.setPrefixToUserData(BodyStrings.HUMAN_STRING);
		alignToCar();
	}

	public void addNeck()
	{
		Vector2 position = new Vector2(-7 / rat, 25 / rat);
		if(car.driverScale == 2)
			position.set(-7 / rat, 40 / rat);

		position.add(body.bodies.get(0).getmBody().getWorldCenter().x, body.bodies.get(0).getmBody().getWorldCenter().y);

		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.initialize(body.getBodyByName("humanBody").getmBody(), body.getBodyByName("headBody").getmBody(), position);
		neckRevolute = (RevoluteJoint)gameManager.gameScene.world.createJoint(revoluteJointDef);

		neckRevolute.enableLimit(true);
		neckRevolute.setLimits(lowLimit, highLimit);
	}

	public void destroyNeck()
	{
		if(neckRevolute == null)
			return;

		gameManager.gameScene.world.destroyJoint(neckRevolute);
	}

	public void attachToCar()
	{
		carWeldJoint = car.body.addBodyWithWeldWithoutAdding(body.getBodyByName("humanBody"), "mainBody", gameManager.gameScene.world);
	}

	public void alignToCar()
	{
		setPositionInPixel(car.body.bodies.get(0).getmBody().getWorldCenter().x * rat + car.driverX,
				car.body.bodies.get(0).getmBody().getWorldCenter().y * rat + car.driverY);
	}

	int resetCT = 0;
	public void run()
	{
		if(resetCT > 0)
		{
			resetCT--;
			alignToCar();

			if(resetCT == 0)
			{
				alignToCar();
				addNeck();
				attachToCar();
			}
		}

	}

	public void draw(Batch batch)
	{
		if(gameManager.levelManager.currentLevel.getCurrentPart().mode == GameScene.LevelModeEnum.Shooting)
			return;

		if(resetCT > 0)
			return;

		body.draw(batch);
	}

	public void setPositionInPixel(float x, float y)
	{
		float rat = PhysicsConstant.PIXEL_TO_METER;

		x /= rat;
		y /= rat;

		body.setCenterPosition(x, y);

		Vector2 position = new Vector2(-35 / rat, 25 / rat);
		if(car.driverScale == 2)
			position.set(-50 / rat, 40 / rat);
		body.bodies.get(1).setPosition(x + position.x, y + position.y);

		body.setAllBodiesR(0);
	}

	public void reset()
	{
		if(body != null)
			body.disposeBodies(gameManager.gameScene.world);

		loadBody();
		body.bodies.get(0).addSprite(bodySprite);
		body.bodies.get(1).addSprite(headSprite);

		resetCT = car.staticCount + 2;
	}
}
