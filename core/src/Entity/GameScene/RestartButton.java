package Entity.GameScene;

import com.badlogic.gdx.graphics.Texture;

import Dialog.PauseMenuDialog;
import Entity.Button;
import GameScene.GameScene;

/**
 * Created by sinazk on 6/27/16.
 * 01:55
 */
public class RestartButton extends Button
{
	PauseMenuDialog pauseMenuDialog;
	public RestartButton(PauseMenuDialog pauseMenuDialog)
	{
		super(pauseMenuDialog.RestartButtonTexture1, pauseMenuDialog.RestartButtonTexture2);

		this.pauseMenuDialog = pauseMenuDialog;


		setRunnable(pauseMenuDialog.gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				RestartButton.this.pauseMenuDialog.setIsActive(false);
				RestartButton.this.pauseMenuDialog.dialogManager.popQ();
				RestartButton.this.pauseMenuDialog.gameScene.restart();
			}
		});
	}
}
