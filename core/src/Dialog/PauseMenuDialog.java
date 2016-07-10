package Dialog;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import Entity.GameScene.EndGameButton;
import Entity.GameScene.RestartButton;
import Entity.GameScene.ResumeButton;
import GameScene.GameScene;
import Misc.TextureHelper;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 6/27/16.
 * 01:57
 */
public class PauseMenuDialog extends Dialog
{
	public GameScene gameScene;
	float DX, DY;
	public PauseMenuDialog(DialogManager dialogManager, GameScene gameScene)
	{
		super(dialogManager);

		this.gameScene = gameScene;
		this.DX = gameScene.DX;
		this.DY = gameScene.DY;
	}

	public Texture RestartButtonTexture1, RestartButtonTexture2;
	public Texture ResumeButtonTexture1, ResumeButtonTexture2;
	public Texture EndButtonTexture1, EndButtonTexture2;
	@Override
	public void create()
	{
		String add = "gfx/scene/dialog/pausemenu/";
		backSprite = new Sprite(TextureHelper.loadTexture(add + "back.png", dialogManager.disposalTexture));
//		backSprite.setSize(300, 300);
		backSprite.setPosition(DX + (SceneManager.WORLD_X - backSprite.getWidth()) / 2 - 30, DY + (SceneManager.WORLD_Y - backSprite.getHeight()) / 2 + 50);

		RestartButtonTexture1 = TextureHelper.loadTexture(add + "restart1.png", gameScene.disposeTextureArray);
		RestartButtonTexture2 = TextureHelper.loadTexture(add + "restart2.png", gameScene.disposeTextureArray);
		RestartButton restartButton = new RestartButton(this);
		restartButton.setSize(50, 50);
		restartButton.setPosition(DX + 340, DY + 250);
		scene.addActor(restartButton);

		ResumeButtonTexture1 = TextureHelper.loadTexture(add + "resume1.png", gameScene.disposeTextureArray);
		ResumeButtonTexture2 = TextureHelper.loadTexture(add + "resume2.png", gameScene.disposeTextureArray);
		ResumeButton resumeButton = new ResumeButton(this);
		resumeButton.setSize(50, 50);
		resumeButton.setPosition(DX + 400, DY + 250);
		scene.addActor(resumeButton);

		EndButtonTexture1 = TextureHelper.loadTexture(add + "exit1.png", gameScene.disposeTextureArray);
		EndButtonTexture2 = TextureHelper.loadTexture(add + "exit2.png", gameScene.disposeTextureArray);
		EndGameButton endGameButton = new EndGameButton(this);
		endGameButton.setSize(50, 50);
		endGameButton.setPosition(DX + 280, DY + 250);
		scene.addActor(endGameButton);
	}
}
