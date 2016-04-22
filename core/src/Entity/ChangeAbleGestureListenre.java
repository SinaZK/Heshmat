package Entity;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

import ChangeAblePackage.HorizentalChangablePackage;

public class ChangeAbleGestureListenre implements GestureListener 
{

	HorizentalChangablePackage change;
	public ChangeAbleGestureListenre(HorizentalChangablePackage pChange) 
	{
		change = pChange;
	}
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) 
	{
//		if(velocityX > 0)
//			change.goLeft();
//		
//		if(velocityX < 0)
//			change.goRight();
		
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) 
	{
		change.isPanning = true;
		
		change.cameraBitch.moveBy(-deltaX, 0);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) 
	{
		change.isPanning = false;
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}


}
