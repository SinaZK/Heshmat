package Entity.GameScene;

import Dialog.PauseMenuDialog;
import Entity.Button;

/**
 * Created by sinazk on 7/2/16.
 * 06:51
 */
public class EndGameButton extends Button
{
	PauseMenuDialog pauseMenuDialog;
	public EndGameButton(PauseMenuDialog pauseMenuDialog)
	{
		super(pauseMenuDialog.EndButtonTexture1, pauseMenuDialog.EndButtonTexture2);

		this.pauseMenuDialog = pauseMenuDialog;


		setRunnable(pauseMenuDialog.gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{

				EndGameButton.this.pauseMenuDialog.setIsActive(false);
				EndGameButton.this.pauseMenuDialog.dialogManager.popQ();
				EndGameButton.this.pauseMenuDialog.gameScene.EndTheGame(false);
			}
		});
	}
}
