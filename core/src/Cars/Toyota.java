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

public class Toyota extends NormalCar
{
	public Toyota(GameManager gm, CarStatData carStatData)
	{
		super(gm, carStatData);
	}

	Sprite layerSprite = new Sprite(TextureHelper.loadTexture("gfx/car/2/layer.png", gameScene.disposeTextureArray));
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
		body.bodies.get(0).draw(batch);//car Skeleton
		for(int i = 1;i < body.bodies.size();i++)
			body.bodies.get(i).draw(batch);
	}
}
