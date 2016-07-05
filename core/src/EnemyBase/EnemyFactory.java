package EnemyBase;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import BaseLevel.BaseLevel;
import BaseLevel.Modes.DrivingMode;
import BaseLevel.Modes.ShootingMode;
import Enemy.Bat;
import Enemy.Bomb;
import Enemy.BossBird;
import Enemy.EnemyState.GreenTree;
import Enemy.EnemyState.SmallStreetLight;
import Enemy.EnemyState.StreetLight;
import Enemy.EnemyState.TrafficLight;
import Enemy.EnemyState.WaterBox;
import Enemy.EnemyState.YellowTree;
import Enemy.FireBird;
import Enemy.Fly;
import Enemy.FurryBird;
import Enemy.HattyBird;
import Enemy.MaskBird;
import Enemy.Mosquito;
import Enemy.RedBird;
import Enemy.Wasp;
import Enemy.Worm;
import Entity.AnimatedSpriteSheet;
import GameScene.GameManager;
import GameScene.GameScene;
import Misc.CameraHelper;
import Misc.Log;
import Misc.TextureHelper;
import SceneManager.SceneManager;

public class EnemyFactory
{
	public GameScene gameScene;
	public GameManager gameManager;
	public ShootingMode shootingMode;
	public BaseLevel level;

	public ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();

	public EnemyFactory(GameManager gameManager)
	{
		gameScene = gameManager.gameScene;
		this.gameManager = gameManager;
	}

	public void create()
	{
		level = gameManager.levelManager.currentLevel;

//		if(level == null)
//			Log.e("EnemyFactory.java", "NULL");
//		else Log.e("EnemyFactory.java", "Not NULL");

		loadAnimations();
		loadInfoCars();
		loadDrivingEnemyAssets();
	}


	public void run()
	{
		for (int i = 0; i < enemies.size(); i++)
			if(!enemies.get(i).isFree)
				enemies.get(i).run();
			else if(enemies.get(i).isRunOnDeath)
				enemies.get(i).runOnDeath();
	}

	public void draw(Batch batch)
	{
		for (int i = 0; i < enemies.size(); i++)
			if(!enemies.get(i).isFree)
				enemies.get(i).draw(batch);
			else if(enemies.get(i).isRunOnDeath)
				enemies.get(i).drawOnDeath(batch);
	}

	public BaseEnemy getEnemyByType(BaseEnemy.EnemyType enemyType, int _level, ArrayList<String> attr)
	{
		if(level != null)
			if(level.getCurrentPart().mode == GameScene.LevelModeEnum.Shooting)
				shootingMode = (ShootingMode) level.getCurrentPart(); //necessary

		for (int i = 0; i < enemies.size(); i++)
			if(enemies.get(i).enemyType == enemyType && enemies.get(i).isFree)
			{
				enemies.get(i).create(shootingMode, _level, attr);
				return enemies.get(i);
			}

		return null;
	}

	public boolean haveEnemy(BaseEnemy.EnemyType enemyType)
	{
		if(level != null)
			if(level.getCurrentPart().mode == GameScene.LevelModeEnum.Shooting)
				shootingMode = (ShootingMode) level.getCurrentPart(); //necessary

		for (int i = 0; i < enemies.size(); i++)
			if(enemies.get(i).enemyType == enemyType && enemies.get(i).isFree)
				return true;

		return false;
	}

	public void damageArea(Vector2 centreOfExplosion, float length, float damage)
	{
		for (int i = 0; i < enemies.size(); i++)
			if(!enemies.get(i).isFree)
			{
				if(CameraHelper.distance(centreOfExplosion.x, centreOfExplosion.y, enemies.get(i).x, enemies.get(i).y) <= length)
					enemies.get(i).damage(damage);
			}
	}

//	public Pigeon getPigeon(ArrayList<String> attr)
//	{
//		if(haveEnemy(BaseEnemy.EnemyType.Pigeon))
//			return (Pigeon) getEnemyByType(BaseEnemy.EnemyType.Pigeon, attr);
//
//		Pigeon pigeon = new Pigeon(gameManager, enemies.size());
//		pigeon.create(shootingMode, attr);
//		enemies.add(pigeon);
//
//		return  pigeon;
//	}

	public Bat getBat(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.BAT))
			return (Bat) getEnemyByType(BaseEnemy.EnemyType.BAT, level, attr);
		Bat enemy = new Bat(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public Bomb getBomb(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.BOMB))
			return (Bomb) getEnemyByType(BaseEnemy.EnemyType.BOMB, level, attr);
		Bomb enemy = new Bomb(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public BossBird getBossBird(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.BOSS_BIRD))
			return (BossBird) getEnemyByType(BaseEnemy.EnemyType.BOSS_BIRD, level, attr);
		BossBird enemy = new BossBird(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public FireBird getFireBird(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.FIRE_BIRD))
			return (FireBird) getEnemyByType(BaseEnemy.EnemyType.FIRE_BIRD, level, attr);
		FireBird enemy = new FireBird(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public Fly getFly(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.FLY))
			return (Fly) getEnemyByType(BaseEnemy.EnemyType.FLY, level, attr);
		Fly enemy = new Fly(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public FurryBird getFuryBird(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.FURRY_BIRD))
			return (FurryBird) getEnemyByType(BaseEnemy.EnemyType.FURRY_BIRD, level, attr);
		FurryBird enemy = new FurryBird(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public HattyBird getHattyBird(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.HATTY_BIRD))
			return (HattyBird) getEnemyByType(BaseEnemy.EnemyType.HATTY_BIRD, level, attr);
		HattyBird enemy = new HattyBird(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public MaskBird getMaskBird(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.MASK_BIRD))
			return (MaskBird) getEnemyByType(BaseEnemy.EnemyType.MASK_BIRD, level, attr);
		MaskBird enemy = new MaskBird(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public RedBird getRedBird(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.RED_BIRD))
			return (RedBird) getEnemyByType(BaseEnemy.EnemyType.RED_BIRD, level, attr);
		RedBird enemy = new RedBird(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public Mosquito getMosquito(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.MOSQUITO))
			return (Mosquito) getEnemyByType(BaseEnemy.EnemyType.MOSQUITO, level, attr);
		Mosquito enemy = new Mosquito(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public Wasp getWasp(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.WASP))
			return (Wasp) getEnemyByType(BaseEnemy.EnemyType.WASP, level, attr);
		Wasp enemy = new Wasp(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public Worm getWorm(int level, ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.WORM))
			return (Worm) getEnemyByType(BaseEnemy.EnemyType.WORM, level, attr);
		Worm enemy = new Worm(gameManager, enemies.size());
		enemy.create(shootingMode, level, attr);
		enemies.add(enemy);
		return enemy;
	}

	public static String MOSQUITO = "MOSQUITO";
	public static String FLY = "FLY";
	public static String WASP = "WASP";
	public static String RED_BIRD = "RED_BIRD";
	public static String HATTY_BIRD = "HATTY_BIRD";
	public static String MASK_BIRD = "MASK_BIRD";
	public static String FURRY_BIRD = "FURRY_BIRD";
	public static String FIRE_BIRD = "FIRE_BIRD";
	public static String BAT = "BAT";
	public static String BOSS_BIRD = "BOSS_BIRD";
	public static String WORM = "WORM";
	public static String BOMB = "BOMB";

	public static String STREET_LIGHT   = "STREET_LIGHT";
	public static String SMALL_LIGHT    = "SMALL_LIGHT";
	public static String GREEN_TREE     = "GREEN_TREE";
	public static String YELLOW_TREE    = "YELLOW_TREE";
	public static String TRAFFIC_LIGHT  = "TRAFFIC_LIGHT";
	public static String WATER_BOX  = "WATER_BOX";



	public BaseEnemy getEnemy(String type, int level, ArrayList<String> attr)
	{
		if(type.equals(BAT))
			return getBat(level, attr);

		if(type.equals(BOMB))
			return getBomb(level, attr);

		if(type.equals(BOSS_BIRD))
			return getBossBird(level, attr);

		if(type.equals(FIRE_BIRD))
			return getFireBird(level, attr);

		if(type.equals(FLY))
			return getFly(level, attr);

		if(type.equals(FURRY_BIRD))
			return getFuryBird(level, attr);

		if(type.equals(HATTY_BIRD))
			return getHattyBird(level, attr);

		if(type.equals(MASK_BIRD))
			return getMaskBird(level, attr);

		if(type.equals(MOSQUITO))
			return getMosquito(level, attr);

		if(type.equals(RED_BIRD))
			return getRedBird(level, attr);

		if(type.equals(WASP))
			return getWasp(level, attr);

		if(type.equals(WORM))
			return getWorm(level, attr);

		return null;

	}

	public AnimatedSpriteSheet MosquitoEnemyAnimation, FlyEnemyAnimation, WaspEnemyAnimation, RedBirdEnemyAnimation, HattyBirdEnemyAnimation, MaskBirdEnemyAnimation;
	public AnimatedSpriteSheet FurryBirdEnemyAnimation, FireBirdEnemyAnimation, BatEnemyAnimation, BossBirdEnemyAnimation, WormEnemyAnimation, BombEnemyAnimation;

	public Texture FlyBulletTexture;

	public void loadAnimations()
	{
		MosquitoEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/1/mosquito.png", gameScene.disposeTextureArray);
		MosquitoEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 0, 0, 2490, 375, 1, 4, 8);
		MosquitoEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 714, 459, 2412, 993, 1, 3, 8);

		FlyEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/2/fly.png", gameScene.disposeTextureArray);
		FlyEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 254, 10, 1362, 214, 1, 4, 8);
		FlyEnemyAnimation.addAnimation(ENEMY_ANIMATION_ATTACK_STRING, 274, 382, 1974, 576, 1, 6, 6);
		FlyEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 258, 652, 1658, 962, 1, 4, 8);
		FlyBulletTexture = TextureHelper.loadTexture("gfx/enemy/2/bullet.png", gameScene.disposeTextureArray);


		WaspEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/3/wasp.png", gameScene.disposeTextureArray);
		WaspEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 354, 117, 2826, 408, 1, 5, 8);
		WaspEnemyAnimation.addAnimation(ENEMY_ANIMATION_ATTACK_STRING, 339, 597, 2889, 1017, 1, 5, 8);
		WaspEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 360, 1173, 2994, 1695, 1, 5, 8);

		RedBirdEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/4/bird.png", gameScene.disposeTextureArray);
		RedBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 0, 0, 2190, 396, 1, 4, 8);
		RedBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 0, 456, 2492, 950, 1, 4, 8);

		HattyBirdEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/5/bird.png", gameScene.disposeTextureArray);
		HattyBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 0, 0, 2866, 470, 1, 4, 8);
		HattyBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 0, 590, 2608, 1078, 1, 4, 8);

		MaskBirdEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/6/bird.png", gameScene.disposeTextureArray);
		MaskBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 446, 0, 2036, 288, 1, 4, 8);
		MaskBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 0, 370, 2456, 880, 1, 4, 8);

		FurryBirdEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/7/bird.png", gameScene.disposeTextureArray);
		FurryBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 0, 0, 2196, 382, 1, 4, 8);
		FurryBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 0, 436, 2486, 940, 1, 4, 8);

		FireBirdEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/8/bird.png", gameScene.disposeTextureArray);
		FireBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 250, 0, 2158, 372, 1, 4, 8);
		FireBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 0, 428, 2454, 920, 1, 4, 8);

		BatEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/9/bat.png", gameScene.disposeTextureArray);
		BatEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 164, 0, 2296, 364, 1, 4, 8);
		BatEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 0, 438, 2454, 932, 1, 4, 8);

		BossBirdEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/10/boss.png", gameScene.disposeTextureArray);
		BossBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 732, 0, 2493, 366, 1, 3, 8);
		BossBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_ATTACKBLOW_STRING, 807, 432, 2535, 807, 1, 3, 8);
		BossBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_ATTACKFIRE_STRING, 801, 879, 2550, 1242, 1, 3, 8);
		BossBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_LOWHEALTH_STRING, 735, 1300, 2481, 1782, 1, 3, 8);
		BossBirdEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 264, 1911, 2700, 2367, 1, 3, 8);

		WormEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/11/worm.png", gameScene.disposeTextureArray);
		WormEnemyAnimation.addAnimation(ENEMY_ANIMATION_FALL_STRING, 0, 0, 327, 232, 1, 1, 8);
		WormEnemyAnimation.addAnimation(ENEMY_ANIMATION_MOVE_STRING, 27, 807, 1449, 1095, 1, 4, 8);
		WormEnemyAnimation.addAnimation(ENEMY_ANIMATION_WAKEUP_STRING, 27, 807, 1449, 1095, 1, 4, 8);
		WormEnemyAnimation.addAnimation(ENEMY_ANIMATION_DIE_STRING, 0, 1308, 2496, 1671, 1, 6, 8);

		BombEnemyAnimation = new AnimatedSpriteSheet("gfx/enemy/12/bomb.png", gameScene.disposeTextureArray);
		BombEnemyAnimation.addAnimation(ENEMY_ANIMATION_FALL_STRING, 34, 4, 1266, 274, 1, 7, 8);
		BombEnemyAnimation.addAnimation(ENEMY_ANIMATION_EXPLODE_STRING, 34, 4, 1266, 274, 1, 5, 8);
	}

	public AnimatedSpriteSheet DrivingEnemiesSpriteSheet;

	public void loadDrivingEnemyAssets()
	{
		DrivingEnemiesSpriteSheet = new AnimatedSpriteSheet("gfx/enemy/driving/sheet.png", gameScene.disposeTextureArray);

		DrivingEnemiesSpriteSheet.addAnimation(ENEMY_DRIVING_STREET_LIGHT, 0, 0, 240, 348, 1, 2, NOT_ANIMATION);

		DrivingEnemiesSpriteSheet.addAnimation(ENEMY_DRIVING_SMALL_LIGHT, 30, 352, 232, 639, 1, 2, NOT_ANIMATION);

		DrivingEnemiesSpriteSheet.addAnimation(ENEMY_DRIVING_TRAFFIC_LIGHT, 251, 4, 479, 318, 1, 2, NOT_ANIMATION);

		DrivingEnemiesSpriteSheet.addAnimation(ENEMY_DRIVING_WATER_BOX, 560, 351, 796, 490, 1, 2, NOT_ANIMATION);

		DrivingEnemiesSpriteSheet.addAnimation(ENEMY_DRIVING_GREEN_TREE, 666, 39, 887, 300, 1, 2, NOT_ANIMATION);

		DrivingEnemiesSpriteSheet.addAnimation(ENEMY_DRIVING_YELLOW_TREE, 255, 333, 532, 535, 1, 2, NOT_ANIMATION);
	}

	public Sprite[] infoCards = new Sprite[SceneManager.ENEMY_NUM + 1];

	public void loadInfoCars()
	{
		String path = "gfx/enemy/";
		ArrayList<Texture> dp = gameScene.disposeTextureArray;

		for (int i = 1; i <= SceneManager.ENEMY_NUM; i++)
			infoCards[i] = new Sprite(TextureHelper.loadTexture(path + i + "/infocard.png", dp));
	}

	//for Spirtes and animations
	public static String ENEMY_ANIMATION_DIE_STRING = "die";
	public static String ENEMY_ANIMATION_FALL_STRING = "fall";
	public static String ENEMY_ANIMATION_EXPLODE_STRING = "explode";
	public static String ENEMY_ANIMATION_WAKEUP_STRING = "wakeUp";
	public static String ENEMY_ANIMATION_MOVE_STRING = "move";
	public static String ENEMY_ANIMATION_LOWHEALTH_STRING = "lowHealth";
	public static String ENEMY_ANIMATION_ATTACK_STRING = "attack";
	public static String ENEMY_ANIMATION_ATTACKFIRE_STRING = "attackFire";
	public static String ENEMY_ANIMATION_ATTACKBLOW_STRING = "attackBlow";

	public static String ENEMY_DRIVING_STREET_LIGHT  = "street light";
	public static String ENEMY_DRIVING_SMALL_LIGHT   = "small light";
	public static String ENEMY_DRIVING_GREEN_TREE    = "green tree";
	public static String ENEMY_DRIVING_YELLOW_TREE   = "yellow tree";
	public static String ENEMY_DRIVING_TRAFFIC_LIGHT = "traffic light";
	public static String ENEMY_DRIVING_WATER_BOX     = "water box";

	public static int NOT_ANIMATION = -1;
	//for Spirtes and animations

	public void pause()
	{

	}

	public void resume()
	{

	}

	public void restart()
	{
		for (int i = 0; i < enemies.size(); i++)
		{
			enemies.get(i).isFree = true;
			enemies.get(i).isRunOnDeath = false;
			enemies.get(i).release();
		}
	}

	public static BaseEnemy.EnemyType[] enemyTypes =
			{
					null,//0
					BaseEnemy.EnemyType.MOSQUITO,//1
					BaseEnemy.EnemyType.FLY,//2
					BaseEnemy.EnemyType.WASP,//3
					BaseEnemy.EnemyType.RED_BIRD,//4
					BaseEnemy.EnemyType.HATTY_BIRD,//5
					BaseEnemy.EnemyType.MASK_BIRD,//6
					BaseEnemy.EnemyType.FURRY_BIRD,//7
					BaseEnemy.EnemyType.FIRE_BIRD,//8
					BaseEnemy.EnemyType.BAT,//9
					BaseEnemy.EnemyType.BOSS_BIRD,//10
					BaseEnemy.EnemyType.WORM,//11
					BaseEnemy.EnemyType.BOMB,//12
			};

	public int getEnemyID(BaseEnemy.EnemyType enemyType)
	{
		for (int i = 1; i <= SceneManager.ENEMY_NUM; i++)
			if(enemyType == enemyTypes[i])
				return i;

		return -1;
	}

	public Sprite getInfoSprite(BaseEnemy.EnemyType enemyType)
	{
		if(getEnemyID(enemyType) == -1)
			return null;

		return infoCards[getEnemyID(enemyType)];
	}

	public static BaseEnemy.EnemyType StringToEnemy(String type)
	{
		if(type.equals(BAT))
			return BaseEnemy.EnemyType.BAT;
		if(type.equals(MOSQUITO))
			return BaseEnemy.EnemyType.MOSQUITO;
		if(type.equals(WASP))
			return BaseEnemy.EnemyType.WASP;
		if(type.equals(BOMB))
			return BaseEnemy.EnemyType.BOMB;
		if(type.equals(BOSS_BIRD))
			return BaseEnemy.EnemyType.BOSS_BIRD;
		if(type.equals(FIRE_BIRD))
			return BaseEnemy.EnemyType.FIRE_BIRD;
		if(type.equals(FLY))
			return BaseEnemy.EnemyType.FLY;
		if(type.equals(FURRY_BIRD))
			return BaseEnemy.EnemyType.FURRY_BIRD;
		if(type.equals(HATTY_BIRD))
			return BaseEnemy.EnemyType.HATTY_BIRD;
		if(type.equals(MASK_BIRD))
			return BaseEnemy.EnemyType.MASK_BIRD;
		if(type.equals(RED_BIRD))
			return BaseEnemy.EnemyType.RED_BIRD;
		if(type.equals(WORM))
			return BaseEnemy.EnemyType.WORM;

		if(type.equals(TRAFFIC_LIGHT))
			return BaseEnemy.EnemyType.TrafficLight;
		if(type.equals(SMALL_LIGHT))
			return BaseEnemy.EnemyType.SmallLight;
		if(type.equals(STREET_LIGHT))
			return BaseEnemy.EnemyType.StreetLight;
		if(type.equals(GREEN_TREE))
			return BaseEnemy.EnemyType.GreenTree;
		if(type.equals(YELLOW_TREE))
			return BaseEnemy.EnemyType.YellowTree;
		if(type.equals(WATER_BOX))
			return BaseEnemy.EnemyType.WaterBox;

		return null;
	}


	public BaseEnemy getDrivingEnemy(BaseEnemy.EnemyType type, int level, ArrayList<String> attr)
	{
//		Log.e("EnemyFactory.java", "start Create WorldBody Count = " + gameScene.world.getBodyCount());
		if(haveEnemy(type))
		{
//			Log.e("EnemyFactory.java", "have!!!! After Create WorldBody Count = " + gameScene.world.getBodyCount());
			return getEnemyByType(type, level, attr);
		}

		BaseEnemy retEnemy = null;
		switch (type)
		{
			case StreetLight:
				retEnemy = new StreetLight(gameManager, enemies.size());
				break;
			case SmallLight:
				retEnemy = new SmallStreetLight(gameManager, enemies.size());
				break;
			case TrafficLight:
				retEnemy = new TrafficLight(gameManager, enemies.size());
				break;
			case WaterBox:
				retEnemy = new WaterBox(gameManager, enemies.size());
				break;
			case GreenTree:
				retEnemy = new GreenTree(gameManager, enemies.size());
				break;
			case YellowTree:
				retEnemy = new YellowTree(gameManager, enemies.size());
				break;
		}

		assert retEnemy != null;
		retEnemy.create(shootingMode, level, attr);

		enemies.add(retEnemy);

//		Log.e("EnemyFactory.java", "not Have!!! After Create WorldBody Count = " + gameScene.world.getBodyCount());

		return retEnemy;
	}

}
