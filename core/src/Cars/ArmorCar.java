package Cars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import BaseCar.NormalCar;
import DataStore.CarStatData;
import GameScene.GameManager;
import Misc.TextureHelper;

/**
 * Created by sinazk on 7/7/16.
 * 12:45
 */

public class ArmorCar extends NormalCar
{
	public ArmorCar(GameManager gm, CarStatData carStatData)
	{
		super(gm, carStatData);
	}

	Sprite layerSprite = new Sprite(TextureHelper.loadTexture("gfx/car/5/layer.png", gameScene.disposeTextureArray));
	Sprite bodySprite  = new Sprite(TextureHelper.loadTexture("gfx/car/5/body1.png", gameScene.disposeTextureArray));
	@Override
	public void create()
	{
		body.bodies.get(0).getmSprite().clear();
		body.bodies.get(0).getmSprite().add(bodySprite);

		super.create();
	}

	@Override
	public void drawBody(Batch batch)
	{
		body.flushSprites();

		layerSprite.setPosition(body.bodies.get(0).getmSprite().get(0).getX(), body.bodies.get(0).getmSprite().get(0).getY());
		layerSprite.setRotation(body.bodies.get(0).getmSprite().get(0).getRotation());
		layerSprite.draw(batch);
		for(int i = 1;i < body.bodies.size() - 1;i++)
			body.bodies.get(i).draw(batch);
		body.bodies.get(0).draw(batch);//car Skeleton

		body.bodies.get(3).draw(batch);
	}
}
