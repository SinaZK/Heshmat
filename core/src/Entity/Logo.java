package Entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import GameScene.GameScene;
import Misc.TextureHelper;
import SceneManager.SceneManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy;

/**
 * Created by sinazk on 7/19/16.
 * 05:36
 */
public class Logo
{
	public GameScene gameScene;
	public float x, y;
	float DX;
	float DY;

	Entity logo1, logo2, back, sign;

	ArrayList<Texture> disposeTextureArray;
	OrthographicCamera camera;

	public float distAlpha = 1;
	public float currentAlpha = 0;
	public float alphaSpeed = 0.01f;

	public Logo(GameScene gs)
	{
		this.gameScene = gs;

		disposeTextureArray = gameScene.disposeTextureArray;
		camera = (OrthographicCamera)gameScene.getCamera();
	}

	public void setPos(float x1, float y1)
	{
		x = x1;
		y = y1;
	}

	public void load()
	{
		DX = (camera.viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (camera.viewportHeight - SceneManager.WORLD_Y) / 2;
		String add = "gfx/scene/splash/";

		logo1  = new Entity(TextureHelper.loadTexture(add + "logo1.png", disposeTextureArray));
		logo2  = new Entity(TextureHelper.loadTexture(add+"logo2.png", disposeTextureArray));
		logo1.setSize(127, 119);
		logo2.setSize(171, 171);
//		back   = new Entity(TextureHelper.loadTexture(add+"back.jpg", disposeTextureArray));
//		float bX = back.getWidth() - SceneManager.WORLD_X;
//		float bY = back.getHeight() - SceneManager.WORLD_Y;
//		back.setPosition(DX - bX / 2, DY - bY / 2);
		sign = new Entity(TextureHelper.loadTexture(add+"sign.png", disposeTextureArray));
		sign.setSize(172, 67);

		logo1.setOrigin(logo1.getWidth() / 2, logo1.getHeight() / 2);
		logo2.setOrigin(logo2.getWidth() / 2, logo2.getHeight() / 2);
		sign.setPosition(DX + 310, DY + 115);
		logo2.setPosition(DX + 310, DY + 192);
		logo1.setPosition(DX + 335, DY + 218);


		gameScene.attachChild(logo1);
		gameScene.attachChild(logo2);
		gameScene.attachChild(sign);

		logo1.setRotation(180);
		logo2.setRotation(180);
		logo1.addAction(parallel(rotateBy(-180, 1f)));
		logo2.addAction(parallel(rotateBy(+180, 1f)));

		sign.setColor(sign.getColor().r, sign.getColor().g, sign.getColor().b, 0);
	}

	float STOP_TIME = 1f;
	float FADE_TIME = 1.5f;
	public void create()
	{
		Timer.schedule(new Timer.Task()
		{

			@Override
			public void run()
			{
				sign.addAction(parallel(alpha(1, 1)));
			}
		}, 1, 1, 0);

//		Timer.schedule(new Timer.Task() {
//
//			@Override
//			public void run()
//			{
//				Color black = new Color();
//				black.r = 0;
//				black.g = 0;
//				black.b = 0;
//
//				logo1.addAction(parallel(color(black, FADE_TIME)));
//				logo2.addAction(parallel(color(black, FADE_TIME)));
//				sign.addAction(parallel(color(black, FADE_TIME)));
//			}
//		}, 1.8f + STOP_TIME, 1, 0);

		Timer.schedule(new Timer.Task() {

			@Override
			public void run()
			{
				//null
			}
		}, 1.8f + STOP_TIME + FADE_TIME, 1, 0);
	}

	public void start()
	{

	}

	public void run()
	{
		logo1.act(gameScene.getDeltaTime());
		logo2.act(gameScene.getDeltaTime());
		sign.act(gameScene.getDeltaTime());
	}
	public void draw(Batch batch)
	{
		logo1.setPosition(logo1.getX() + x, logo1.getY() + y);
		logo2.setPosition(logo2.getX() + x, logo2.getY() + y);
		sign.setPosition (sign.getX() + x , sign.getY() + y);

		logo1.draw(batch, currentAlpha);
		logo2.draw(batch, currentAlpha);
		sign.draw(batch, currentAlpha);

		if(currentAlpha < distAlpha)
			currentAlpha += alphaSpeed;
		if(currentAlpha > distAlpha)
			currentAlpha -= alphaSpeed;
	}
}
