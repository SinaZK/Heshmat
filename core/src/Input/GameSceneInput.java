package Input;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import Misc.GUI;
import Misc.ObjectCreator;
import GameScene.GameSceneNormal;

public class GameSceneInput implements InputProcessor 
{
	GameSceneNormal mScene;
	
	public GameSceneInput(GameSceneNormal gs) 
	{
		mScene = gs;
	}
	
	@Override
	public boolean keyDown(int keycode) 
	{
		//if(keycode == Keys.DOWN)
		return false;
	}

	@Override
	public boolean keyUp(int keycode) 
	{
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
		if(button == Buttons.LEFT)
		{
			ObjectCreator.createBox(mScene.world, touchPos.x, touchPos.y);
		}

		if(button == Buttons.RIGHT)
		{
		}

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

}
