package Terrain;

import BaseLevel.Modes.LevelMode;
import GameScene.*;
import Misc.Log;
import heshmat.MainActivity;

/**
 * Created by SinaZK on 8/10/2016.
 * 22:53
 */
public class TerrainControler
{
    MainActivity activity;
    GameScene gameScene;
    GameManager gameManager;

    Terrain terrain;
    public GameScene.LevelModeEnum levelMode;

    boolean isFlat;

    public TerrainControler(Terrain terrain)
    {
        this.terrain = terrain;

        activity = terrain.act;
        gameScene = terrain.gameScene;
        gameManager = terrain.gameManager;
    }

    public void run()
    {
//        Log.e("TerrainControler.java", "Make Ground Called");

        levelMode = gameManager.levelManager.levelModeEnum;


        if(isFlat)
        {
            terrain.makeLastPointWithSameHeight();
            terrain.addLastPiece();

            flatCounter++;
            if(flatCounter >= flatMax)
                isFlat = false;

//            Log.e("TerrainControler.java", "adding a piece counter = " + flatCounter);
        }
    }

    public boolean wantHandle()
    {
        return (isFlat);
    }

    int flatCounter;
    int flatMax;
    public void makeGround(int num)
    {
        isFlat = true;
        flatCounter = 0;
        flatMax = num;

//        Log.e("TerrainControler.java", "Make Ground Called");
    }

    public void makeGroundRealTime(int num)
    {
        for(int i = 0;i < num;i++)
        {
            terrain.makeLastPointWithSameHeight();
            terrain.addLastPiece();
        }
    }

    public void addBridge()
    {

    }
}
