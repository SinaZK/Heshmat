package Scene.EndGameScenes;

import Entity.Button;
import Entity.Entity;
import Entity.VideoButton;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.TextureHelper;
import Scene.EndGameScene;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 8/10/2016.
 * 07:19
 */
public class LoopEndGameScene extends EndGameScene
{
    public LoopEndGameScene(GameScene gameScene)
    {
        super(gameScene);
    }


    @Override
    public void loadResources()
    {
        DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
        DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;

        createBack();

        restartButton = new Button(TextureHelper.loadTexture(add + "restart1.png", disposeTextureArray),
                TextureHelper.loadTexture(add + "restart2.png", disposeTextureArray));
        restartButton.setRunnable(act, new Runnable()
        {

            @Override
            public void run()
            {
                restartGameScene();
            }
        });


        backToMenuButton = new Button(TextureHelper.loadTexture(add + "back1.png", disposeTextureArray),
                TextureHelper.loadTexture(add + "back2.png", disposeTextureArray));
        backToMenuButton.setRunnable(act, new Runnable()
        {

            @Override
            public void run()
            {
                goToGarageScene();
            }
        });


        Button showScoreButton = new Button(TextureHelper.loadTexture(add + "leader1.png", disposeTextureArray),
                TextureHelper.loadTexture(add + "leader2.png", disposeTextureArray));

        showScoreButton.setRunnable(act, new Runnable()
        {
            @Override
            public void run()
            {
                act.showLoopLeaderBoard();
            }
        });

        final Entity waveEnt = new Entity(TextureHelper.loadTexture(add + "wave.png", disposeTextureArray));
        final Entity coinEnt = new Entity(TextureHelper.loadTexture(add + "coin.png", disposeTextureArray));
        final Entity killEnt = new Entity(TextureHelper.loadTexture(add + "kill.png", disposeTextureArray));

        waveEnt.setPosition(DX + 200, DY + 280);
        coinEnt.setPosition(DX + 200, DY + 220);
        killEnt.setPosition(DX + 200, DY + 160);

        showScoreButton.setPosition(DX + 241, DY + 20);
        backToMenuButton.setPosition(DX + 370, DY + 20);
        restartButton.setPosition(DX + 498, DY + 20);


        VideoButton videoButton = new VideoButton(act);
        videoButton.setPosition(DX + 50, DY + 20);

        attachChild(waveEnt);
        attachChild(videoButton);
        attachChild(showScoreButton);

        attachChild(backToMenuButton);
        attachChild(restartButton);
        attachChild(killEnt);
        attachChild(coinEnt);

    }

    @Override
    public void run()
    {
        super.run();
    }

    @Override
    public void draw()
    {
        super.draw();
    }

    @Override
    public void start(boolean isLevelFinished)
    {
        super.start(isLevelFinished);

        if(gameScene.gameManager.levelManager.levelType == LevelManager.LevelType.LOOP)
        {
            act.submitLoopScore((int) act.levelPackageStatDatas[act.selectorStatData.selectedLevelPack].getEndlessStartingWave());
        }
    }

    @Override
    public int countStars() {
        return -1;
    }


    @Override
    public void drawTexts()
    {
        gameScene.font22.draw(getBatch(), "" + (int) act.levelPackageStatDatas[act.selectorStatData.selectedLevelPack].getEndlessStartingWave(), 460, 310);
        gameScene.font22.draw(getBatch(), "" + gameScene.gameManager.goldCollect, 460, 250);//gold
        gameScene.font22.draw(getBatch(), "" + gameScene.gameManager.enemyKilledCount, 460, 190);
    }
}
