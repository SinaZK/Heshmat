package WeaponBase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import Entity.Entity;
import GameScene.GameManager;
import Misc.CameraHelper;
import Misc.Log;
import Misc.TextureHelper;
import heshmat.MainActivity;

public abstract class BaseGun implements InputProcessor
{
	public MainActivity act;
	public Entity image;
	public float rateOfFire;//numberOfBulletsPerSecond
	public boolean isShootingEnabled = true;
	public float shootingTimeCounter = 0;
	//public Entity debugTest;

	public GameManager gameManager;
	
	public float x, y;
	public Vector2 shootingPoint = new Vector2(0, 0);
	
	public BaseGun(MainActivity a, GameManager gm)
	{
		act = a;
		gameManager = gm;

		//debugTest = new Entity(TextureHelper.loadTexture("gfx/pistolbullet.png", gameManager.gameScene.disposeTextureArray));
	}
	
	public void draw(Batch batch)
	{
		image.draw(batch, 1);
		//batch.draw(debugTest.getImg().getTexture(), getShootingX(), getShootingY());
	};
	
	public abstract void loadResources();

	public float getShootingX()
	{
		if(image == null)
			return 0;

		Vector2 tmp = new Vector2(shootingPoint.x + x, shootingPoint.y + y);
		Vector2 shootingPointAfterRotate = CameraHelper.rotatePoint(x + image.getWidth() / 2, y + image.getHeight() / 2, image.getRotation(), tmp);
		//shootingPointAfterRotate.add(image.getWidth() / 2, image.getHeight() / 2);

		return shootingPointAfterRotate.x;
	}

	public float getShootingY()
	{
		if(image == null)
			return 0;

		Vector2 tmp = new Vector2(shootingPoint.x + x, shootingPoint.y + y);
		Vector2 shootingPointAfterRotate = CameraHelper.rotatePoint(x + image.getWidth() / 2, y + image.getHeight() / 2, image.getRotation(), tmp);
		//shootingPointAfterRotate.add(image.getWidth() / 2, image.getHeight() / 2);

		return shootingPointAfterRotate.y;
	}

	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
		image.setPosition(x, y);
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
