package Entity.GameScene;

import Dialog.PauseMenuDialog;
import Entity.Button;

/**
 * Created by sinazk on 6/27/16.
 * 02:39
 */
public class ResumeButton extends Button
{
	PauseMenuDialog pauseMenuDialog;
	public ResumeButton(PauseMenuDialog pauseMenuDialog)
	{
		super(pauseMenuDialog.ResumeButtonTexture1, pauseMenuDialog.ResumeButtonTexture2);

		this.pauseMenuDialog = pauseMenuDialog;


		setRunnable(pauseMenuDialog.gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				ResumeButton.this.pauseMenuDialog.gameScene.resume();
				ResumeButton.this.pauseMenuDialog.setIsActive(false);
				ResumeButton.this.pauseMenuDialog.dialogManager.popQ();
			}
		});
	}
}
