package WeaponBase;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

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
	public int mRateOfFire;
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
	
	public abstract void run();
	
	public abstract void shoot();

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
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.E)
		{
			image.rotateBy(10);
		}
		if(keycode == Input.Keys.Q)
		{
			image.rotateBy(-10);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
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
