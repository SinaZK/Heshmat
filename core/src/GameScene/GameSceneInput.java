package GameScene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import Misc.GUI;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;

public class GameSceneInput implements InputProcessor 
{
	GameScene mScene;

	public ArrayList<Integer> doAbleKeys = new ArrayList<Integer>();
	public ArrayList<Boolean> isKeypressed = new ArrayList<Boolean>();

	public GameSceneInput(GameScene gs)
	{
		mScene = gs;
		for(int i = 0;i < 100;i++)
			isKeypressed.add(false);

		doAbleKeys.add(Input.Keys.UP);
		doAbleKeys.add(Input.Keys.DOWN);
	}
	
	@Override
	public boolean keyDown(int keycode) 
	{
		for(int i = 0;i < doAbleKeys.size();i++)
			if(doAbleKeys.get(i) == keycode)
				isKeypressed.set(i, true);

		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		for(int i = 0;i < doAbleKeys.size();i++)
			if(doAbleKeys.get(i) == keycode)
				isKeypressed.set(i, false);

		if(keycode == Input.Keys.NUM_3)
		{
			float ratio = PhysicsConstant.PIXEL_TO_METER;
			mScene.gameManager.selectedCar.body.setCenterPosition(mScene.camera.position.x / ratio, mScene.camera.position.y / ratio + 5);
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		Vector2 touchPos = GUI.convertTouchPos(mScene.getCamera(), screenX, screenY);
		/*if(button == Buttons.LEFT)
		{
			//ObjectCreator.createBox(polygonSpriteBatch.world, touchPos.x, touchPos.y);
		}

		if(button == Buttons.RIGHT)
		{
		}
		*/

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
	public boolean scrolled(int amount) 
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	public boolean isKeyPressed(int keycode)
	{
		for(int i = 0;i < doAbleKeys.size();i++)
			if(doAbleKeys.get(i) == keycode)
				return isKeypressed.get(i);

		return false;
	}

}
