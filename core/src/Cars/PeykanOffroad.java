package Cars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;

import BaseCar.AttachPart;
import BaseCar.NormalCar;
import DataStore.CarStatData;
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

	Sprite layerSprite = new Sprite(TextureHelper.loadTexture("gfx/car/3/layer.png", gameScene.disposeTextureArray));
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

		layerSprite.setPosition(body.bodies.get(0).getmSprite().get(0).getX(), body.bodies.get(0).getmSprite().get(0).getY());
		layerSprite.setRotation(body.bodies.get(0).getmSprite().get(0).getRotation());
		layerSprite.draw(batch);
		for(int i = 1;i < body.bodies.size();i++)
			body.bodies.get(i).draw(batch);
		body.bodies.get(0).draw(batch);//car Skeleton

		enginePart.draw(batch);
	}

	CzakBody tirePart, boxPart;
	WeldJoint engineJoint;

	AttachPart enginePart;
	public void createAttachedParts()
	{


//		x = 400;
//		y = 300;

		enginePart = new AttachPart(this);
		enginePart.create(50, 50, 100, 100, true);

		enginePart.addSprite(new Sprite(TextureHelper.loadTexture("gfx/car/3/engine1.png", gameScene.disposeTextureArray)));
		enginePart.addSprite(new Sprite(TextureHelper.loadTexture("gfx/car/3/engine2.png", gameScene.disposeTextureArray)));
//		enginePart.setType(BodyDef.BodyType.StaticBody);

		attachParts.add(enginePart);

	}

	@Override
	public void run(boolean isGas, boolean isBrake, float rate)
	{
		super.run(isGas, isBrake, rate);

//		Log.e("Peykan", "hp = " + hitpoint);

		if(hitpoint < 200)
		{
			enginePart.detach();
		}

		if(staticCount > -10 && staticCount <= 0)
		{
			staticCount--;

			if(staticCount == -10)
			{
				enginePart.reset();
			}
		}
	}

	@Override
	public void reset()
	{
		super.reset();
	}
}
