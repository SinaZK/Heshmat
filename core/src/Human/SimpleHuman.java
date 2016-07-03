package Human;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.GunManager;
import Misc.BodyStrings;
import Misc.TextureHelper;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;

/**
 * Created by sinazk on 6/2/16.
 * 18:09
 */
public class SimpleHuman
{
	GameScene gameScene;
	GameManager gameManager;
	GunManager gunManager;
	World world;

	Texture fullBodyTexture;
	Sprite bodySprite;
	CzakBody body;

	public SimpleHuman(GameManager gameManager)
	{
		this.gameManager = gameManager;
		world = gameManager.gameScene.world;
		gameScene = gameManager.gameScene;
		gunManager = gameManager.gunManager;
	}

	public void loadResources(String path)
	{
		fullBodyTexture = TextureHelper.loadTexture(path, gameScene.disposeTextureArray);
	}

	float imageScaleX, imageScaleY;
	float groundHeight;
	public void create(float width, float height)
	{
		loadResources("gfx/human/1/stand.png");
		body = new CzakBody();
		bodySprite = new Sprite(fullBodyTexture);
		body.addSprite(bodySprite);
		imageScaleX = bodySprite.getWidth() / width;
		imageScaleY = bodySprite.getHeight() / height;
		bodySprite.setSize(width, height);
		body.setBody(PhysicsFactory.createBoxBody(world, 0, 0, width, height, BodyDef.BodyType.StaticBody));
		body.setUserData(BodyStrings.HUMAN_STRING + " " + "Simple");

		groundHeight = gameManager.levelManager.currentLevel.terrain.Points.getLast().y;
	}

	public void run()
	{

		if(gameManager.levelManager.levelModeEnum == GameScene.LevelModeEnum.Shooting)
		{
			float rat = PhysicsConstant.PIXEL_TO_METER;
			setWorldPosition(gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().x,
					 groundHeight / rat + bodySprite.getHeight() / 2 / rat);
		}

		if(gameManager.levelManager.levelModeEnum == GameScene.LevelModeEnum.Driving)
		{
			setWorldPosition(gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().x, gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().y);
			setActive(false);
		}
	}

	public void draw(Batch batch)
	{
		if(gameManager.levelManager.levelModeEnum == GameScene.LevelModeEnum.Shooting)
		{
			float r = gunManager.getSelectedGun().image.getRotation();

			Vector2 gunPos = new Vector2(gunManager.getSelectedGun().humanPos);
			gunPos.set(gunPos.x / imageScaleX + bodySprite.getX(), gunPos.y / imageScaleY + bodySprite.getY());

			if(r >= 90 && r <= 270)
				body.getmSprite().get(0).setFlip(true, false);

			gunManager.getSelectedGun().setPosition(gunPos.x, gunPos.y);

			body.draw(batch);
			gunManager.getSelectedGun().draw(gameScene.getBatch());
			body.getmSprite().get(0).setFlip(false, false);


//			Log.e("SimpleHuman.java", "r = " + gunManager.getSelectedGun().image.getRotation());
//			Log.e("SimpleHuman.java", "bodyPos = " + body.getmBody().getWorldCenter().x + ", " + body.getmBody().getWorldCenter().y);
//			Log.e("SimpleHuman.java", "gunPos = " + gunPos.x + ", " + gunPos.y);
//			Log.e("SimpleHuman.java", "humanPos = " + gunManager.getSelectedGun().humanPos);
//			Log.e("SimpleHuman.java", "sprite pos = " + bodySprite.getX() + ", " + bodySprite.getY());
		}

	}

	public void setPosition(float cX, float cY)
	{
		body.setPosition(cX / PhysicsConstant.PIXEL_TO_METER, cY / PhysicsConstant.PIXEL_TO_METER);
	}

	public void setWorldPosition(float cX, float cY)
	{
		body.setPosition(cX, cY);
	}

	public void setActive(boolean active)
	{
		body.getmBody().setActive(active);
	}
}
