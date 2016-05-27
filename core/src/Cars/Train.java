package Cars;

import BaseCar.NormalCar;
import Entity.Button;
import GameScene.GameManager;
import GameScene.GameScene;
import HUD.DrivingHUD;
import Misc.TextureHelper;

/**
 * Created by sinazk on 5/21/16.
 * kare shode :X
 */
public class Train extends NormalCar
{
	Button collapseButton;
	public Train(GameManager gm)
	{
		super(gm);

		collapseButton = new Button(TextureHelper.loadTexture("gfx/scene/game/exit1.png", gameScene.disposeTextureArray),
				TextureHelper.loadTexture("gfx/scene/game/exit2.png", gameScene.disposeTextureArray));
		collapseButton.setPosition(400, 10);
		collapseButton.setSize(50, 50);

		HUD.addActor(collapseButton);
	}

	public boolean isCollapsed = false;
	public boolean shouldCollapsed = false;

	public void collapse()
	{
		if(isCollapsed)
			return;

		gameManager.gameScene.world.destroyJoint(body.getJointByName("vaslJoint"));
		isCollapsed = true;
	}

	@Override
	public void run(boolean isGas, boolean isBrake, float rate)
	{
		super.run(isGas, isBrake, rate);

		if(shouldCollapsed)
			collapse();

		if(collapseButton.isClicked)
			shouldCollapsed = true;
	}

	@Override
	public boolean isStopped()
	{
		return super.isStopped();
	}
}
