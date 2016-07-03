package Terrain;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import BaseLevel.BaseLevel;
import BaseLevel.Modes.DrivingMode;
import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.CameraHelper;
import PhysicsFactory.PhysicsConstant;
import SceneManager.SceneManager;
import heshmat.MainActivity;

public class Terrain
{
	MainActivity act;

	GameScene gameScene;
	GameManager gameManager;
	LevelManager levelManager;
	BaseLevel level;
	public PolygonSpriteBatch polygonSpriteBatch;
	public Batch spriteBatch;
	OrthographicCamera mCamera;
	World mPhysicsWorld;

	String address;

	public static float rat = PhysicsConstant.PIXEL_TO_METER;
	public float xSize = 15;
	float firstNum = 200;

	public int removeSize = 150;

	public LinkedList<Vector2> Points;
	LinkedList<TerrainPiece> Pieces;

	public Texture repeatingTextureRegion;
	public Texture upTextureRegion;
	public Texture coinTexture;
	public Texture gasTexture;
	public Texture timeTexture;

	public Stage bgSpriteBatch;
	public int bgSize;
	public ArrayList<Sprite> bgLayers = new ArrayList<Sprite>();
	public float[] bgLayersSpeedX = new float[10];
	public float[] bgLayersSpeedY = new float[10];
	public boolean[] bgLayerIsAutoParallax = new boolean[10];

	TerrainLoader terrainLoader;

	int coinStepCounter, coinPackageCounter;
	public int coinStep;
	public int coinStepAdderAfter;
	public int coinStepAdder;
	public int coinNum;
	public int coinValue;
	public int coinValueAdder;
	public int fuelStep;
	public int firstFuel;
	public int timeStep;
	public int firstTime;

	public int sandDist;
	public int stoneDist;
	public int bumperDist;
	public int bridgeDist;

	public float mudRate = 0.91f;

	public int getXof(float x)
	{
		for (int i = 0; i < Points.size(); i++)
		{
			if(Points.get(i).x >= x)
				return i;
		}

		return -1;
	}

	public Terrain(MainActivity a, String add)
	{
		act = a;
		gameScene = a.sceneManager.gameScene;
		gameManager = gameScene.gameManager;
		levelManager = gameManager.levelManager;
		level = levelManager.currentLevel;
		mCamera = gameScene.camera;
		mPhysicsWorld = gameScene.world;
		address = add;

		Points = new LinkedList<Vector2>();
		Pieces = new LinkedList<TerrainPiece>();

		terrainLoader = new TerrainLoader(add + "map.terrain", true);

		setAtt();

		random = new Random(terrainLoader.randSeed);
		mCamera.zoom = terrainLoader.zoom;
		cameraZoom = terrainLoader.zoom;
	}
	public float cameraZoom;

	public void loadResources(Texture tUp, Texture tDown)
	{
		repeatingTextureRegion = tDown;
		upTextureRegion = tUp;
	}

	public void loadResources(Texture tUp, Texture tDown, Texture coinTexture, Texture gasT, Texture timeT)
	{
		repeatingTextureRegion = tDown;
		upTextureRegion = tUp;

		this.timeTexture = timeT;
		this.gasTexture = gasT;
		this.coinTexture = coinTexture;
	}

	public void create(Batch batch, PolygonSpriteBatch pScene)
	{
		bgSpriteBatch = new Stage(new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));

		polygonSpriteBatch = pScene;
		spriteBatch = batch;

		float firstX = -xSize * firstNum;
		for (int i = 0; i < 2; i++)
		{
			firstX += xSize;
			Points.addLast(new Vector2(firstX, getHeight(0)));

		}

		for (int i = 0; i < firstNum; i++)
		{
			firstX += xSize;
			Points.addLast(new Vector2(firstX, getHeight(0)));
			addLastPiece();
		}


	}//Create

	public void drawFirstLayer()//asli
	{
		for (int i = 0; i < Pieces.size(); i++)
		{
			Pieces.get(i).drawFirst();
		}
	}

	public void drawSecondLayer()//grass
	{
		for (int i = 0; i < Pieces.size(); i++)
		{
			Pieces.get(i).drawSecond();
		}
	}

	int minBridgeLength = 8;
	int maxBridgeLength = 20;

	int minBumperRadius = 20;
	int maxBumperRadius = 100;
	int maxBumperHeight = 22;

	int stoneHeight = 1000;
	int maxStoneRadius = 35;

	int maxSandAmount = 150;

	int maxObstacleSlope = 25;
	int safeDistance = 1097;

	int fuelStepCounter;

	float lastBridgeX = -1;
	int curBridgeDist = bridgeDist;
	float lastStoneX = -1;
	int curStoneDist = stoneDist;
	float lastSandX = -1;
	int curSandDist = sandDist;
	float lastBumperX = -1;
	int curBumperDist = bumperDist;
	float lastObstacle = -1;

	Random utilRand = new Random(727);

	public void run()
	{
		float lastVisible = CameraHelper.getXMax(mCamera, mCamera.zoom * 1.5f);

		while (lastVisible >= Points.getLast().x)
		{
			if(gameManager.levelManager.levelModeEnum == GameScene.LevelModeEnum.Driving)
			{
				if(!handleTheLastPartOfDrivingMode())
				{
					Vector2 prevPoint = Points.get(Points.size() - 2);
					Vector2 lastPoint = Points.getLast();
					float slope = prevPoint.angle(lastPoint);

					float height = getHeight((int) ((int) lastPoint.x / xSize + 1));
					Points.add(new Vector2(lastPoint.x + xSize, height));

					addLastPiece();
				}
			}
			else
			{
				Vector2 lastPoint = Points.getLast();
				Points.add(new Vector2(lastPoint.x + xSize, lastPoint.y));
				addLastPiece();
			}
		}

		while (Points.size() > 3 * removeSize)
			removeFirstPiece();
	}

	public static float sightArea = 500;
	public boolean handleTheLastPartOfDrivingMode()
	{
		DrivingMode drivingMode = (DrivingMode) level.getCurrentPart();

		Vector2 lastPoint = Points.getLast();
		if(lastPoint.x + sightArea > drivingMode.getEndDistance())
		{
			Points.add(new Vector2(lastPoint.x + xSize, lastPoint.y));
			addLastPiece();

			return true;
		}

		return false;
	}

	public void addFirstPiece()
	{
		float x1 = Points.get(0).x;
		float y1 = Points.get(0).y;

		float x2 = Points.get(1).x;
		float y2 = Points.get(1).y;

		Pieces.addFirst(new TerrainPiece(act, this, mPhysicsWorld));
		Pieces.getFirst().create(x1, y1, x2, y2);
	}

	public void removeFirstPiece()
	{
		Points.removeFirst();
		Pieces.getFirst().dispose(true);
		Pieces.removeFirst();
	}

	public int ct = 0;

	public void addLastPiece()
	{
		int sz = Points.size();

		float x1 = Points.get(sz - 2).x;
		float y1 = Points.get(sz - 2).y;

		float x2 = Points.get(sz - 1).x;
		float y2 = Points.get(sz - 1).y;

		if(x2 - x1 != xSize)
			return;

		Pieces.addLast(new TerrainPiece(act, this, mPhysicsWorld));
		Pieces.getLast().create(x1, y1, x2, y2);
	}

	public void removeLastPiece()
	{
		Points.removeLast();
		Pieces.getLast().dispose(true);
		Pieces.removeLast();
	}

	void createFirstTerrain()
	{
		Pieces.addLast(new TerrainPiece(act, this, mPhysicsWorld));
		Pieces.getLast().create(Points.getFirst().x, Points.getFirst().y, Points.getLast().x, Points.getLast().y);
	}

	float y, ay, by;
	float y2, ay2, by2;
	int prevPosition;
	Random random;
	float maxA;
	float maxB;
	float maxA2;
	float maxB2;
	float minSlope;
	float maxSlope;
	float slopeStep;

	private void setAtt()
	{
		y = terrainLoader.y;
		ay = terrainLoader.ay;
		by = terrainLoader.by;
		y2 = terrainLoader.y2;
		ay2 = terrainLoader.ay2;
		by2 = terrainLoader.by2;
		maxA = terrainLoader.maxA;
		maxB = terrainLoader.maxB;
		maxA2 = terrainLoader.maxA2;
		maxB2 = terrainLoader.maxB2;
		slopeStep = terrainLoader.slopeStep;
		minSlope = terrainLoader.minSlope;
		maxSlope = terrainLoader.maxSlope;

		bgSize = terrainLoader.bgSize;

		for (int i = 0; i < bgSize; i++)
		{
			bgLayersSpeedX[i] = terrainLoader.layersSpeedX[i];
			bgLayersSpeedY[i] = terrainLoader.layersSpeedY[i];

			if(terrainLoader.layersIsAuto[i] == 1)
				bgLayerIsAutoParallax[i] = true;
			else
				bgLayerIsAutoParallax[i] = false;
		}


		coinStep = terrainLoader.coinStep;
		coinStepAdderAfter = terrainLoader.coinStepAdderAfter;
		coinStepAdder = terrainLoader.coinStepAdder;
		coinNum = terrainLoader.coinNum;
		coinValue = terrainLoader.coinValue;
		coinValueAdder = terrainLoader.coinValueAdder;
		fuelStep = terrainLoader.fuelStep;
		firstFuel = terrainLoader.firstFuel;
		timeStep = terrainLoader.timeStep;
		firstTime = terrainLoader.firstTime;

		sandDist = terrainLoader.sandDist;
		stoneDist = terrainLoader.stoneDist;
		bumperDist = terrainLoader.bumperDist;
		bridgeDist = terrainLoader.bridgeDist;
	}

	private float getHeight(int pPosition)
	{
		maxA = Math.min(maxSlope, minSlope + (pPosition / slopeStep));


		if(pPosition == 0)
		{
			y = 0;
			ay = -1;
			by = 0;
			y2 = 0;
			ay2 = 0;
			by2 = 0;
		}

		if(pPosition > prevPosition)
		{
			if(by < -maxB) by = -maxB;
			if(by > maxB) by = maxB;
			y += ay;
			ay += by;
			if(Math.abs(ay) < maxA / 4)
				by -= by * random.nextFloat() * 0.1;
			if(Math.abs(ay) > maxA || random.nextFloat() > 0.5)
				by += random.nextFloat() - 0.5f - ay * random.nextFloat() / maxA;

			for (int i = 0; i < 4; i++)
			{
				if(ay2 < -maxA2)
				{
					ay2 = -maxA2;
					by2 = 0;
				}
				if(ay2 > maxA2)
				{
					ay2 = maxA2;
					by2 = 0;
				}
				if(by2 < -maxB2) by2 = -maxB2;
				if(by2 > maxB2) by2 = maxB2;
				y2 += ay2;
				ay2 += by2;
				by2 += random.nextFloat() - 0.5f - ay2 * random.nextFloat() / maxA2;
			}
		}
		prevPosition = Math.max(pPosition, prevPosition);
		return y + y2;
	}


	public void loadBG(String path)
	{
		for (int i = 1; i <= bgSize; i++)
			bgLayers.add(new Sprite(new Texture(Gdx.files.internal(path + "layer" + i + ".jpg"))));

		for (int i = 0; i < bgLayers.size(); i++)
		{
			float width = Math.max(mCamera.viewportWidth, bgLayers.get(i).getWidth());
			float height = Math.max(mCamera.viewportHeight, bgLayers.get(i).getHeight());

			bgLayers.get(i).setSize(width, height);
		}
	}

	public void drawBG()
	{
		bgSpriteBatch.act();
		bgSpriteBatch.draw();

		bgSpriteBatch.getBatch().begin();

		float deltaY = mCamera.position.y - Gdx.graphics.getHeight() / 2;
		deltaY *= -1;

		for (int i = 0; i < bgSize; i++)
		{
			float IH = bgLayers.get(i).getHeight();
			float yStarter = IH - mCamera.viewportHeight;
			yStarter /= 2;
			yStarter *= -1;

			if(!bgLayerIsAutoParallax[i])
			{
				float CX = CameraHelper.getXMin(mCamera, 2.5f) / bgLayersSpeedX[i];

				float IW = bgLayers.get(i).getWidth();
				int c = (int) (CX / IW);
				float r = CX - c * IW;

				float dY = deltaY / bgLayersSpeedY[i];


				if(dY < yStarter)
					dY = yStarter;
				if(dY > 0)
					dY = 0;

				if(CX < 0)
					bgSpriteBatch.getBatch().draw(bgLayers.get(i), -r - IW, dY);
				bgSpriteBatch.getBatch().draw(bgLayers.get(i), -r, dY);
				bgSpriteBatch.getBatch().draw(bgLayers.get(i), -r + IW, dY);
			} else
			{
				if(act.sceneManager.gameScene.gameStat != GameScene.GAME_STAT.PAUSE)
					bgLayers.get(i).setPosition(bgLayers.get(i).getX() - bgLayersSpeedX[i], 0);

				if(bgLayers.get(i).getX() <= -bgLayers.get(i).getWidth())
					bgLayers.get(i).setPosition(bgLayers.get(i).getX() + Gdx.graphics.getWidth() * 2, 0);

				float dY = deltaY / bgLayersSpeedY[i];

				if(dY < yStarter)
					dY = yStarter;
				if(dY > 0)
					dY = 0;

				bgSpriteBatch.getBatch().draw(bgLayers.get(i), bgLayers.get(i).getX(), dY);
			}
		}

		bgSpriteBatch.getBatch().end();
	}

	public void destroyAllPieces()
	{
		for (int i = 0; i < Pieces.size(); i++)
			Pieces.get(i).dispose(false);

		Pieces.removeAll(Pieces);
		Points.removeAll(Points);
	}

	public void dispose()
	{
		polygonSpriteBatch = null;
		spriteBatch = null;
		bgSpriteBatch.dispose();
		polygonSpriteBatch = null;
		destroyAllPieces();
		mPhysicsWorld = null;
	}


}//Class
