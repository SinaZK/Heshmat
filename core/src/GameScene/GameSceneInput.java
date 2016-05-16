package GameScene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import Enemy.Pigeon;
import Misc.GUI;

public class GameSceneInput implements InputProcessor 
{
	GameScene mScene;

	public GameSceneInput(GameScene gs)
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
		if(keycode == Input.Keys.NUM_1)
		{
			mScene.gameManager.swapGun();
		}

		if(keycode == Input.Keys.NUM_2)
		{
			Pigeon p = mScene.gameManager.enemyFactory.getPigeon();
			p.setPosition(400, 200);
		}

		if(keycode == Input.Keys.CONTROL_LEFT)
		{
			mScene.gameManager.selectedGun.shoot();
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

}
