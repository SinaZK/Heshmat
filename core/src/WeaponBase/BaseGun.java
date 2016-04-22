package WeaponBase;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;

import Entity.Entity;
import heshmat.MainActivity;

public abstract class BaseGun implements InputProcessor
{
	public MainActivity act;
	public Entity image;
	public int mRateOfFire;
	
	public float x, y;
	
	public BaseGun(MainActivity a) 
	{
		act = a;
	}
	
	public void draw(Batch batch)
	{
		image.draw(batch, 1);
	};
	
	public abstract void run();
	
	public abstract void shoot();

	public abstract void loadResources();

	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
		image.setPosition(x, y);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
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
