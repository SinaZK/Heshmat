package BaseLevel.Modes;


import java.util.ArrayList;
import java.util.Random;

import Enemy.EnemyState.StopSign;
import EnemyBase.BaseEnemy;
import Entity.LevelEntities.ModeSplashImage;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.CameraHelper;
import PhysicsFactory.PhysicsConstant;

/**
 * Created by sinazk on 5/22/16.
 * 4:57
 */
public class DrivingMode extends LevelMode {
    public static final float INF_DIST = 1000 * 1000;


    public float distance;
    public float time, fullTime, objDist;
    float distCounter;

    boolean isEnding;

    public static float distBeforeEnd = 2000;
    public boolean isEndSignInit = false;

    public ArrayList<DrivingEnemyQuery> queries = new ArrayList<DrivingEnemyQuery>();

    public DrivingMode(LevelManager levelManager) {
        super(levelManager);
        mode = GameScene.LevelModeEnum.Driving;
        modeSplashImage = new ModeSplashImage(levelManager, levelManager.DrivingModeSplashTexture);
    }

    @Override
    public void run() {
        super.run();

//		Log.e("Tag", "isEnding = " + isEnding + "isFinished = " + isFinished + " isCameraDOne = " + isCameraDone);

        if (isFinished) {
            car.stop();
            return;
        }

        time -= levelManager.gameScene.getDeltaTime();

        if (time <= 0)
            levelManager.isLost = true;

        if (getCurrentPos() >= distance) {
            isEnding = true;
        }

        if (isEnding) {
            if (!car.isStopped())
                car.stop();
            else {
//				Log.e("DrivingMode.java", "isFinished = true");
                gameManager.distanceTraveled += distance;
                isFinished = true;
            }
        }

        levelManager.gameScene.drivingModeHUD.getBatch().begin();
        levelManager.gameScene.drawDist(levelManager.gameScene.drivingModeHUD.getBatch(), getCurrentPos(), distance);
        levelManager.gameScene.drivingModeHUD.getBatch().end();

        if (getCurrentPos() > distCounter && getCurrentPos() + distBeforeEnd < distance) {
//			distCounter += objDist;
            distCounter += Math.abs(random.nextInt()) % (objDist / 2) + objDist / 2;

            if (queries.size() == 0)
                return;

            int id = Math.abs(random.nextInt()) % queries.size();
            queries.get(id).getEnemy();
        }

        if (!isEndSignInit) {
//			if(distance - getCurrentPos() < 1000)
            float firstXinPixel = firstCarX * PhysicsConstant.PIXEL_TO_METER;
            if (CameraHelper.getXMax(camera) + 100 > firstXinPixel + distance) {
                isEndSignInit = true;

                int level = (int) (levelManager.act.selectorStatData.selectedLevel * 1.5f);
                if (levelManager.levelType == LevelManager.LevelType.LOOP)
                    level = (int) (levelManager.getLoopCurrentWave() * (1.5f));

                StopSign stopSign = (StopSign) levelManager.gameManager.enemyFactory.getDrivingEnemy(BaseEnemy.EnemyType.StopSign,
                        level, null);

//				Log.e("drivingMode", "Stop Sign HP : " + stopSign.hitPoint);

                float x = firstXinPixel + distance;
                stopSign.attachToGround(x);
            }
        }

        handlePopRestartButton();

    }

    Random random;

    @Override
    public void start() {
        random = new Random(100 + levelManager.currentLevel.levelParts.size());

        time = fullTime;
        cameraSpeedX = 100;
        cameraSpeedY = 100;
        cameraZoomSpeed = 0.005f;

        distCounter = objDist / 2;

        super.start();

        modeSplashImage.set(0.8f, 1.2f, 0.02f, 2.0f);

        for (int i = 0; i < queries.size(); i++)
            queries.get(i).reset();

        gameManager.activity.audioManager.playDrivingMusic();
    }

    public float getCurrentPos() {
        float diff = car.body.bodies.get(0).getmBody().getWorldCenter().x - firstCarX;
        diff *= PhysicsConstant.PIXEL_TO_METER;
        return diff;
    }

    public float getCurrentPosFull() {
        float diff = car.body.bodies.get(0).getmBody().getWorldCenter().x;
        diff *= PhysicsConstant.PIXEL_TO_METER;

        return diff;
    }

    public float getEndDistance() {
        return firstCarX * PhysicsConstant.PIXEL_TO_METER + distance;
    }

    @Override
    public void reset() {
        super.reset();
        isEnding = false;
        isEndSignInit = false;

        for (int i = 0; i < queries.size(); i++)
            queries.get(i).reset();

        gameManager.activity.audioManager.playDrivingMusic();
    }

    @Override
    public void setCamera(boolean isSuperCallNeeded) {
        if (!isFinished) {
            cameraPosZoom = levelManager.currentLevel.terrain.cameraZoom + Math.max(Math.min(car.getSpeedInMeter() / 30, 1.5f), 0);

            float cameraWidth = camera.viewportWidth * camera.zoom;
            cameraWidth /= 2;

            float cameraPosTmp = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER;
            cameraPosTmp += cameraWidth - gameManager.selectedCar.body.bodies.get(0).getWidth() / 2;
            cameraPosTmp -= 20;

            cameraPos.x = cameraPosTmp;
            cameraPos.y = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER + 80;
        }

        if (isSuperCallNeeded)
            super.setCamera(false);
    }

    @Override
    public void setCameraOnReset() {
        camera.position.x = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER + 400;
        camera.position.y = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER + 80;
        camera.zoom = levelManager.currentLevel.terrain.cameraZoom;

        super.setCameraOnReset();
    }

    @Override
    public void resume() {
        super.resume();

        gameManager.activity.audioManager.playDrivingMusic();
    }


    public void handlePopRestartButton() {
        if (isFinished || gameScene.gameStat == GameScene.GAME_STAT.PAUSE) {
            gameScene.shouldPopRestartButtonDraw = false;
            return;
        }

        float angle = Math.abs(car.convertAngleToDegrees());

//		Log.e("DrivingMode.java", "angle = " + (int)Math.abs(angle - 180f) + " OnAir = " + car.isInAir());

        if (Math.abs(angle - 180f) < 30 && !car.isInAir())
            gameScene.shouldPopRestartButtonDraw = true;
        else
            gameScene.shouldPopRestartButtonDraw = false;
    }
}
