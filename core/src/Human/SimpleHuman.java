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

	Texture standBodyTexture;
	Sprite bodySprite;
	CzakBody standBody;
	Human driverHuman;

	public SimpleHuman(GameManager gameManager)
	{
		this.gameManager = gameManager;
		world = gameManager.gameScene.world;
		gameScene = gameManager.gameScene;
		gunManager = gameManager.gunManager;
	}

	public void loadResources()
	{
		standBodyTexture   = TextureHelper.loadTexture("gfx/human/stand.png", gameScene.disposeTextureArray);
	}

	float imageScaleX, imageScaleY;
	float groundHeight;
	public void create(float width, float height)
	{
		loadResources();
		standBody = new CzakBody();
		bodySprite = new Sprite(standBodyTexture);
		standBody.addSprite(bodySprite);

		imageScaleX = bodySprite.getWidth() / width;
		imageScaleY = bodySprite.getHeight() / height;
		bodySprite.setSize(width, height);
		standBody.setBody(PhysicsFactory.createBoxBody(world, 0, 0, width, height, BodyDef.BodyType.StaticBody));
		standBody.setUserData(BodyStrings.HUMAN_STRING + " " + "Simple");

		groundHeight = gameManager.levelManager.currentLevel.terrain.Points.getLast().y;
	}

	public void run()
	{

		if(gameManager.levelManager.levelModeEnum == GameScene.LevelModeEnum.Shooting)
		{
			setWorldPosition(gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().x, gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().y);
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
				standBody.getmSprite().get(0).setFlip(true, false);

			gunManager.getSelectedGun().setPosition(gunPos.x, gunPos.y);

			standBody.draw(batch);
			gunManager.getSelectedGun().draw(gameScene.getBatch());
			standBody.getmSprite().get(0).setFlip(false, false);
		}
		else
		{

		}
	}

	public void setPosition(float cX, float cY)
	{
		standBody.setPosition(cX / PhysicsConstant.PIXEL_TO_METER, cY / PhysicsConstant.PIXEL_TO_METER);
	}

	public void setWorldPosition(float cX, float cY)
	{
		standBody.setPosition(cX, cY);
	}

	public void setActive(boolean active)
	{
		standBody.getmBody().setActive(active);
	}
}
