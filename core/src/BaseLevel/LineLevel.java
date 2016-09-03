package BaseLevel;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

import BaseLevel.Modes.DrivingMode;
import BaseLevel.Modes.LevelMode;
import BaseLevel.Modes.ShootingMode;
import Entity.LevelEntities.ModeSplashImage;
import GameScene.GameManager;
import Misc.FileLoader;
import Misc.Log;
import Misc.TextureHelper;
import Scene.EndGameScene;
import Scene.EndGameScenes.LineEndGameScene;
import Scene.EndGameScenes.LoopEndGameScene;
import Terrain.TerrainControler;
import Terrain.TerrainMisc.Bridge;
import Terrain.TerrainMisc.ObjectManager;

/**
 * Created by sinazk on 10/8/16.
 * 09:21
 */
public class LineLevel extends BaseLevel {
    TerrainControler terrainControler;
    ObjectManager objectManager;
    OrthographicCamera camera;

    public LineLevel(GameManager gameManager) {
        super(gameManager);

        camera = gameScene.camera;
    }

    Random random;

    @Override
    public void start() {
        super.start();

        carIntenseMult = 3;
        random = new Random();

        objectManager = new ObjectManager(gameManager.gameScene, gameManager.gameScene.world, terrain);
        terrainControler = terrain.terrainControler;
    }

    public static float InitDistance = 5000;
    public float lastInit = 0;

    @Override
    public void run() {
        super.run();

        bridgeRun();
        boxShelfRun();
        coinInitRun();
//        debugRun();
    }

    public float BridgeCount;
    public int BridgeMinSize, BridgeMaxSize;
    public float bridgeCounter;

    public float BoxCount;
    public int BoxMinSize, BoxMaxSize;
    public float boxCounter;

    public int CoinMinRowSize, CoinMaxRowSize;
    public float CoinBaseValue, CoinCount;
    public float CoinStepCounter;
    public float coinCounter;
    public int coinInitNum = 0;

    @Override
    public void load(String add) {
        super.load(add);

        FileLoader fileLoader = new FileLoader();
        fileLoader.loadFile(add + "level.lvl");

        int line = 8;
//        Log.e("LineLevel.java", "read = " + fileLoader.getLine(line));
        BridgeMinSize = fileLoader.getInt(line, 1);
        BridgeMaxSize = fileLoader.getInt(line, 2);
        line++;
        BridgeCount = fileLoader.getFloat(line, 1);
        bridgeCounter = BridgeCount;
        line++;

        BoxMinSize = fileLoader.getInt(line, 1);
        BoxMaxSize = fileLoader.getInt(line, 2);
        line++;
        BoxCount = fileLoader.getFloat(line, 1);
        boxCounter = BoxCount;
        line++;

        CoinMinRowSize = fileLoader.getInt(line, 1);
        coinInitNum = CoinMinRowSize;
        CoinMaxRowSize = fileLoader.getInt(line, 2);
        line++;
        CoinCount = fileLoader.getFloat(line, 1);
        coinCounter = CoinCount;
        line++;
        CoinBaseValue = fileLoader.getFloat(line, 1);
        line++;
        CoinStepCounter = fileLoader.getFloat(line, 1);
        line++;
    }

    public void bridgeRun() {
        if (lastInit + InitDistance > terrain.points.getLast().x)
            return;

        if (terrain.points.getLast().x >= bridgeCounter) {
            bridgeCounter += BridgeCount;
            Vector2 last = terrain.points.getLast();//points are in pixel

            int size = Math.abs(random.nextInt()) % (BridgeMaxSize - BridgeMinSize) + BridgeMinSize;
            objectManager.addBridge(last.x, last.y, size);

            lastInit = last.x + size * Bridge.pieceW;
        }
    }

    public void boxShelfRun() {
        if (lastInit + InitDistance > terrain.points.getLast().x)
            return;

        if (terrain.points.getLast().x >= boxCounter) {
            boxCounter += BoxCount;
            Vector2 last = terrain.points.getLast();//points are in pixel

            int ct = (int) (boxCounter / BoxCount);
            int size = Math.abs(random.nextInt()) % (BoxMaxSize - BoxMinSize) + BoxMinSize;

//            Log.e("LineLevel", "adding Shelf size = " + size);
            objectManager.addBoxShelf(last.x + 200, last.y + 110 / 2 + 1, 1 + ct, size);

            terrainControler.makeGround((int) ((90 * size + 500) / terrain.xSize));

            lastInit = last.x + 90 * size;
        }
    }

    public void coinInitRun() {
        if (lastInit + InitDistance > terrain.points.getLast().x)
            return;

        if (terrain.points.getLast().x >= coinCounter) {
            coinCounter += CoinCount;
            Vector2 last = terrain.points.getLast();//points are in pixel

            int ct = (int) (coinCounter / CoinCount);
            if (ct % CoinStepCounter == 0) {
                coinInitNum++;
                if (coinInitNum > 3 * CoinMaxRowSize)
                    coinInitNum = 3 * CoinMaxRowSize;
            }

            int rowSize = Math.abs(random.nextInt()) % (CoinMaxRowSize - CoinMinRowSize) + CoinMinRowSize;

            objectManager.addCoin(last.x, rowSize, coinInitNum, (int) CoinBaseValue);

            lastInit = last.x + rowSize * 80;
        }
    }

    @Override
    public void drawOnBatch(Batch batch) {
        super.drawOnBatch(batch);

        objectManager.run(batch);
    }

    @Override
    public EndGameScene createEndGameScene() {
        return new LineEndGameScene(gameScene);
    }

    float counter = 0;
    float FLAT_TIME = 5;
    boolean oneTime = true;

    private void debugRun() {
        if (oneTime) {
            counter += gameScene.getDeltaTime();
            if (counter >= FLAT_TIME) {
                terrainControler.makeGround(100);

                Log.e("LineLevel", "adding Box shelf");

                objectManager.addBoxShelf(terrain.points.getLast().x, terrain.points.getLast().y + 50, 5, 5);
//                objectManager.addBridge(camera.position.x, camera.position.y, 10);

                counter = 0;
//                FLAT_TIME = 1000 * 1000;
//                oneTime = false;
            }
        }
    }

    @Override
    public void restart() {
        super.restart();

        lastInit = 0;

        boxCounter = BoxCount;
        bridgeCounter = BridgeCount;
        coinCounter = CoinCount;
        coinInitNum = CoinMinRowSize;

        objectManager.restart();
        random = new Random();
    }
}
