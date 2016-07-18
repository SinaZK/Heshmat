package ChangeAblePackage;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

import Scene.PurchaseScene;
import heshmat.MainActivity;


public class PurchaseChangeAbleGestureListenre implements GestureListener{

	PurchaseScene mScene;
	PurchaseSelectorHorizentalChange change;
	public PurchaseChangeAbleGestureListenre(PurchaseSelectorHorizentalChange change, PurchaseScene mScene)
	{
		this.mScene = mScene;
		this.change = change;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) 
	{
		
//		Log.e("Tag", "Tap");
		
		mScene.act.audioManager.playClick();
		
		int selected = change.current;
		
		mScene.purchaseHelper.makePurchase(selected);

		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		//		Log.e("Tag", "FLING");
		//		change.isFling = true;
		//		if(velocityX > 0)
		//			change.goLeft();

		//		if(velocityX < 0)
		//			change.goRight();
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if(change.isFling)
			return false;

		//		Log.e("Tag", "PAN");
		change.isPanning = true;

		change.cameraBitch.setPosition(change.cameraBitch.getX() - deltaX, change.cameraBitch.getY());
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		change.isPanning = false;
		change.isPanStop = true;

		if(change.cameraBitch.getActions().size > 0 || change.isFling)
			return false;

		//		Log.e("Tag", "PANSTOP: sz = " + change.cameraBitch.getActions().size + " should go to : " + 
		//		change.calcCurrent());


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
