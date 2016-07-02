package Dialog;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import Entity.Button;
import Entity.GameScene.RestartButton;
import Entity.GameScene.ResumeButton;
import GameScene.GameScene;
import Misc.TextureHelper;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 7/1/16.
 * 05:15
 */
public class InfoCarDialog extends Dialog
{
	public GameScene gameScene;
	float DX, DY;
	Sprite infoCardSprite;
	public InfoCarDialog(DialogManager dialogManager, GameScene gameScene)
	{
		super(dialogManager);

		this.gameScene = gameScene;
		this.DX = gameScene.DX;
		this.DY = gameScene.DY;
	}

	public Texture ResumeButtonTexture1, ResumeButtonTexture2;
	@Override
	public void create()
	{
		String add = "gfx/scene/dialog/infocard/";
		backSprite = new Sprite(TextureHelper.loadTexture(add + "back.png", dialogManager.disposalTexture));
		backSprite.setSize(300, 300);
		backSprite.setPosition((SceneManager.WORLD_X - backSprite.getWidth()) / 2, (SceneManager.WORLD_Y - backSprite.getHeight()) / 2);

		ResumeButtonTexture1 = TextureHelper.loadTexture(add + "resume1.png", gameScene.disposeTextureArray);
		ResumeButtonTexture2 = TextureHelper.loadTexture(add + "resume2.png", gameScene.disposeTextureArray);
		Button resumeButton = new Button(ResumeButtonTexture1, ResumeButtonTexture2);
		resumeButton.setSize(50, 50);
		resumeButton.setPosition(DX + 400, DY + 250);

		resumeButton.setRunnable(gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				InfoCarDialog.this.gameScene.resume();
				InfoCarDialog.this.setIsActive(false);
				InfoCarDialog.this.dialogManager.popQ();
			}
		});

		scene.addActor(resumeButton);
	}

	public void setSprite(Sprite infoSprite)
	{
		infoCardSprite = infoSprite;
	}

	@Override
	public void draw(Batch batch)
	{
		scene.getBatch().begin();
		infoCardSprite.setPosition(DX + 300, DY + 200);
		infoCardSprite.setSize(200, 400);
		infoCardSprite.draw(scene.getBatch());
		scene.getBatch().end();
		super.draw(batch);
	}
}
