package WeaponBase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Entity.Entity;
import GameScene.GameManager;
import GameScene.GameScene;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import Misc.TextureHelper;
import heshmat.MainActivity;

public class BaseGun implements InputProcessor
{
	public MainActivity act;
	public Sprite image, showSprite;
	public float rateOfFire;//numberOfBulletsPerSecond
	public boolean isShootingEnabled = true;
	public float shootingTimeCounter = 0;
	//public Entity debugTest;

	public GameManager gameManager;

	public float x, y;
	public Vector2 shootingPoint = new Vector2(0, 0);
	public Vector2 humanPos = new Vector2(0, 0);

	public float bodyDensity, bodyElasticity, bodyFriction;
	
	public BaseGun(MainActivity a, GameManager gm)
	{
		act = a;
		gameManager = gm;

		//debugTest = new Entity(TextureHelper.loadTexture("gfx/pistolbullet.png", gameManager.gameScene.disposeTextureArray));
	}

	public void draw(Batch batch)
	{
		if(image.getRotation() >= -90 && image.getRotation() <= 90)
		{
			image.draw(batch, 1);
		}
		else
		{
			image.setFlip(false, true);
			image.draw(batch);
			image.setFlip(false, false);
		}
	};

	public void drawShow(Batch batch)
	{
		showSprite.draw(batch);
	}
	
	public void loadResources(String path, ArrayList<Texture> disposalArray)
	{
		ArrayList<Texture> use;
		if(act.sceneManager.gameScene == null)
			use = disposalArray;
		else
			use = act.sceneManager.gameScene.disposeTextureArray;

		image = new Sprite(TextureHelper.loadTexture(path + "image.png", use));
		showSprite = new Sprite(TextureHelper.loadTexture(path + "show.png", use));

		FileHandle f = Gdx.files.internal(path + "gun.gun");
		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		try {

			String [] val;
			String in;

			in = dis.readLine();//size
			float width = Float.valueOf(BodyStrings.getPartOf(in, 1));
			float height = Float.valueOf(BodyStrings.getPartOf(in, 2));

			in = dis.readLine();//HumanPos
			float hX = Float.valueOf(BodyStrings.getPartOf(in, 1));
			float hY = Float.valueOf(BodyStrings.getPartOf(in, 2));
			humanPos.set(hX, hY);

			in = dis.readLine();//origin
			float oX = Float.valueOf(BodyStrings.getPartOf(in, 1));
			float oY = Float.valueOf(BodyStrings.getPartOf(in, 2));

			in = dis.readLine();//shootingPos
			float shootX = Float.valueOf(BodyStrings.getPartOf(in, 1));
			float shootY = Float.valueOf(BodyStrings.getPartOf(in, 2));

			in = dis.readLine();
			rateOfFire = Float.valueOf(BodyStrings.getPartOf(in, 1));

			float scaleX = image.getWidth() / width;
			float scaleY = image.getHeight() / height;

			image.setSize(width, height);
			image.setOrigin(oX / scaleX, oY / scaleY);

			shootingPoint.set(shootX / scaleX, shootY / scaleY);

			in = dis.readLine();
			bodyDensity = Float.valueOf(BodyStrings.getPartOf(in, 1));
			bodyElasticity = Float.valueOf(BodyStrings.getPartOf(in, 2));
			bodyFriction = Float.valueOf(BodyStrings.getPartOf(in, 3));
		}
		catch (IOException e)
		{
			Log.e("Tag", e.toString());
		}//catch

	}

	public float getShootingX()
	{
		if(image == null)
			return 0;

		Vector2 tmp = new Vector2(shootingPoint.x + x, shootingPoint.y + y);
		Vector2 shootingPointAfterRotate = CameraHelper.rotatePoint(x + image.getOriginX(), y + image.getOriginY(), image.getRotation(), tmp);

		return shootingPointAfterRotate.x;

	}

	public float getShootingY()
	{
		if(image == null)
			return 0;

		Vector2 tmp = new Vector2(0, 0);
		tmp.set(shootingPoint.x + x, shootingPoint.y + y);

		float rotation = image.getRotation();
		float originY = image.getOriginY();

		if(rotation >= 90 && rotation <= 270)
		{
			tmp.set(shootingPoint.x + x, -image.getHeight() + shootingPoint.y + y);
			originY = image.getHeight() - originY;
		}


		Vector2 shootingPointAfterRotate = CameraHelper.rotatePoint(x + image.getOriginX(), y + originY, rotation, tmp);

		return shootingPointAfterRotate.y;
	}

	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
		image.setPosition(x, y);
		showSprite.setPosition(x, y);
	}

	public void setX(float x)
	{
		this.x = x;
		image.setPosition(x, image.getY());
		showSprite.setPosition(x, image.getY());
	}

	public void setSize(float w, float h)
	{
		image.setSize(w, h);
		showSprite.setSize(w, h);
	}

	public void rePosition(float carX, float carY)
	{
		setPosition(carX, carY);
	}

	public float getRotationByPoint(float tX, float tY)
	{
		float midX = x + image.getWidth() / 2;
		float midY = y + image.getHeight() / 2;
		float ret = (float)Math.toDegrees(Math.atan((midY - tY) / (midX - tX)));
		if(midX > tX)
			ret += 180;

		return ret;
	}

	public void shoot()
	{
		if(isShootingEnabled == false)
			return;

	};

	public void run()
	{
		shootingTimeCounter += Gdx.graphics.getDeltaTime();

		if(shootingTimeCounter > 1 / rateOfFire)
		{
			shootingTimeCounter = 0;
			isShootingEnabled = true;
		}

		if(isTouched)
			shoot();
	}

	public void slotGunInitOnAttachCar()
	{
		gameManager = act.sceneManager.gameScene.gameManager;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		// TODO Auto-generated method stub
		return false;
	}

	Vector3 touchPoint;
	public boolean isTouched;

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		touchPoint = new Vector3(screenX, screenY, 0);
		gameManager.gameScene.camera.unproject(touchPoint);

		image.setRotation(getRotationByPoint(touchPoint.x, touchPoint.y));
		shoot();

		isTouched = true;
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isTouched = false;
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		Vector3 touchPoint = new Vector3(screenX, screenY, 0);
		gameManager.gameScene.camera.unproject(touchPoint);

		image.setRotation(getRotationByPoint(touchPoint.x, touchPoint.y));
		shoot();

		isTouched = true;

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
