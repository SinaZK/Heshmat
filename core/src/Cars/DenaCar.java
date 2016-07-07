package Cars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import BaseCar.NormalCar;
import DataStore.CarStatData;
import GameScene.GameManager;
import Misc.Log;
import Misc.TextureHelper;
import PhysicsFactory.PhysicsFactory;

/**
 * Created by sinazk on 7/7/16.
 * 12:00
 */

public class DenaCar extends NormalCar
{
	public DenaCar(GameManager gm, CarStatData carStatData)
	{
		super(gm, carStatData);
	}

	Sprite layerSprite = new Sprite(TextureHelper.loadTexture("gfx/car/1/layer.png", gameScene.disposeTextureArray));
	@Override
	public void create()
	{
		Sprite tmpSprite = new Sprite(body.bodies.get(0).getmSprite().get(0));//
		body.bodies.get(0).getmSprite().clear();
		body.bodies.get(0).getmSprite().add(tmpSprite);

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
	}
}
