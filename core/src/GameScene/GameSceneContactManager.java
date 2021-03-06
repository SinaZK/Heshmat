package GameScene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.compression.lzma.Base;

import EnemyBase.BaseEnemy;
import EnemyBase.EnemyFactory;
import Misc.BodyStrings;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;
import WeaponBase.BaseBullet;
import WeaponBase.BulletFactory;
import heshmat.MainActivity;

public class GameSceneContactManager
{
	GameScene mScene;
	MainActivity act;
	GameManager gameManager;
	BulletFactory bulletFactory;
	EnemyFactory enemyFactory;

	GameSceneContactManager(MainActivity act, GameScene gs)
	{
		this.act = act;
		mScene = gs;
		gameManager = mScene.gameManager;
		bulletFactory = gameManager.bulletFactory;
		enemyFactory = gameManager.enemyFactory;
	}

	public ContactListener makeContact()
	{
		ContactListener cl = new ContactListener() {
			@Override
			public void beginContact(Contact contact)
			{
				String s1 = (String)contact.getFixtureA().getBody().getUserData();
				String s2 = (String)contact.getFixtureB().getBody().getUserData();

				if(BodyStrings.isCar(s1) && BodyStrings.isGround(s2))
					gameManager.selectedCar.hitByGround(contact, s1, true, false);
				if(BodyStrings.isCar(s2) && BodyStrings.isGround(s1))
					gameManager.selectedCar.hitByGround(contact, s2, true, false);

				if(BodyStrings.isBullet(s1) && BodyStrings.isBullet(s2))
					handleBulletToBullet(contact, s1, s2);

				if(BodyStrings.isBullet(s1) && s2.equals(BodyStrings.GroundString))
					handleBulletToGround(contact, BaseBullet.getBulletID(s1));
				if(BodyStrings.isBullet(s2) && s1.equals(BodyStrings.GroundString))
					handleBulletToGround(contact, BaseBullet.getBulletID(s2));

				if(BodyStrings.isEnemy(s1))
				{
					int id = BaseEnemy.getEnemyID(s1);
					enemyFactory.enemies.get(id).hitBy(contact);
				}

				if(BodyStrings.isEnemy(s2))
				{
					int id = BaseEnemy.getEnemyID(s2);
					enemyFactory.enemies.get(id).hitBy(contact);
				}

//				if(BodyStrings.isBullet(s1) && BodyStrings.isEnemy(s2))
//					handleBulletToEnemy(contact, s1, s2);
//				if(BodyStrings.isBullet(s2) && BodyStrings.isEnemy(s1))
//					handleBulletToEnemy(contact, s2, s1);

				if(BodyStrings.isCar(s1) && BodyStrings.isEnemy(s2))
					handleCarToEnemy(contact, s1, s2);
				if(BodyStrings.isCar(s2) && BodyStrings.isEnemy(s1))
					handleCarToEnemy(contact, s2, s1);


//				if(s1.equals(BodyStrings.FINISH_MODE_STRING) || s2.equals(BodyStrings.FINISH_MODE_STRING))
//					Log.e("GameSceneContactManager.java", s1 + " and " + s2 + " collided");

				if(BodyStrings.isCar(s1) && BodyStrings.isBullet(s2))
					handleBulletToCarBeginContact(contact, s1, s2);
				if(BodyStrings.isCar(s2) && BodyStrings.isBullet(s1))
					handleBulletToCarBeginContact(contact, s2, s1);

				if(BodyStrings.isCar(s1) && s2.equals(BodyStrings.FINISH_MODE_STRING))
					handleFinishBody(contact, s1);
				if(BodyStrings.isCar(s2) && s1.equals(BodyStrings.FINISH_MODE_STRING))
					handleFinishBody(contact, s2);

//				Log.e("GameSceneContactManager.java", s1 + " and " + s2 + "  Begin contacted ISATTACH : " + BodyStrings.isCarAttach(s1) + " and " +
//				BodyStrings.isCarAttach(s2));

//				if(BodyStrings.isEnemy(s1) && BodyStrings.isEnemy(s2))
//					contact.setEnabled(false);

//				if(BodyStrings.isEnemy(s1) || BodyStrings.isEnemy(s2))
//					Log.e("GameSceneContactManager.java", s1 + " and " + s2 + " contacted " + " isEnable = " + contact.isEnabled());
			}

			@Override
			public void endContact(Contact contact)
			{
				String s1 = (String)contact.getFixtureA().getBody().getUserData();
				String s2 = (String)contact.getFixtureB().getBody().getUserData();

				if(BodyStrings.isCar(s1) && BodyStrings.isGround(s2))
					gameManager.selectedCar.hitByGround(contact, s1, false, true);
				if(BodyStrings.isCar(s2) && BodyStrings.isGround(s1))
					gameManager.selectedCar.hitByGround(contact, s2, false, true);

//				if(BodyStrings.isEnemy(s1) || BodyStrings.isEnemy(s2))
//					Log.e("GameSceneContactManager.java", s1 + " and " + s2 + " contacted " + " isEnable = " + contact.isEnabled());
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

				String s1 = (String)contact.getFixtureA().getBody().getUserData();
				String s2 = (String)contact.getFixtureB().getBody().getUserData();

				if(BodyStrings.isCar(s1) && BodyStrings.isCar(s2))
					contact.setEnabled(false);

				if(BodyStrings.isCar(s1) && BodyStrings.isGround(s2))
					gameManager.selectedCar.hitByGroundPreSolve(contact, contact.getFixtureA(), contact.getFixtureB(), s1);
				if(BodyStrings.isCar(s2) && BodyStrings.isGround(s1))
					gameManager.selectedCar.hitByGroundPreSolve(contact, contact.getFixtureB(), contact.getFixtureA(), s2);

				if(BodyStrings.isEnemy(s1) && BodyStrings.isEnemy(s2))
				{
					if(BodyStrings.isShootingEnemy(s1) && BodyStrings.isShootingEnemy(s2))
						contact.setEnabled(false);
					else
					{
						if(BodyStrings.isDrivingEnemy(s1) && BodyStrings.isDrivingEnemy(s2))
						{
							int id = BaseEnemy.getEnemyID(s1);
							enemyFactory.enemies.get(id).hitBy(contact);

							id = BaseEnemy.getEnemyID(s2);
							enemyFactory.enemies.get(id).hitBy(contact);
						}
						else contact.setEnabled(false);
					}
				}

				if(BodyStrings.isCar(s1) && BodyStrings.isEnemy(s2))
					if(BodyStrings.isShootingEnemy(s2))
						contact.setEnabled(false);

				if(BodyStrings.isCar(s2) && BodyStrings.isEnemy(s1))
					if(BodyStrings.isShootingEnemy(s1))
						contact.setEnabled(false);

				if(BodyStrings.isCar(s1) && BodyStrings.isBullet(s2))
					handleBulletToCarPreSolve(contact, s1, s2);
				if(BodyStrings.isCar(s2) && BodyStrings.isBullet(s1))
					handleBulletToCarPreSolve(contact, s2, s1);

				if(BodyStrings.isCar(s1) && BodyStrings.isCarAttach(s2) ||
						BodyStrings.isCar(s2) && BodyStrings.isCarAttach(s1))
				{
					contact.setEnabled(false);
				}

				if(BodyStrings.isBullet(s1) && BodyStrings.isBullet(s2))
					handleBulletToBulletPreSolve(contact, s1, s2);

				if(BodyStrings.isBullet(s1) && BodyStrings.isEnemy(s2))
					handleBulletToEnemy(contact, s1, s2);
				if(BodyStrings.isBullet(s2) && BodyStrings.isEnemy(s1))
					handleBulletToEnemy(contact, s2, s1);

				if(BodyStrings.isCarAttach(s1) || BodyStrings.isCarAttach(s2))
				{
					if(BodyStrings.isGround(s1) || BodyStrings.isGround(s2))
					{
//						Log.e("Tag", "contact : " + s1 + " " + s2);

						gameManager.selectedCar.handleWithGround(contact);
					}
					else contact.setEnabled(false);
				}

				if(BodyStrings.isHuman(s1) || BodyStrings.isHuman(s2))
				{
					contact.setEnabled(false);
				}

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse)
			{
				String s1 = (String)contact.getFixtureA().getBody().getUserData();
				String s2 = (String)contact.getFixtureB().getBody().getUserData();

			}
		};

		return  cl;
	}

	private void handleBulletToCarBeginContact(Contact contact, String carString, String bulletString)
	{
		if(BaseBullet.getBulletShooter(bulletString).equals(BodyStrings.Shooter_HUMAN))
			return;

		int bulletID = BaseBullet.getBulletID(bulletString);
		bulletFactory.bullets.get(bulletID).hitByCar(carString);

		gameManager.selectedCar.hitByBullet(bulletString);
	}

	private void handleBulletToCarPreSolve(Contact contact, String carString, String bulletString)
	{
//		if(BaseBullet.getBulletShooter(bulletString).equals(BodyStrings.Shooter_HUMAN))
		{
			contact.setEnabled(false);
			return;
		}
	}

	public void handleBulletToBulletPreSolve(Contact contact, String data1, String data2)
	{
		contact.setEnabled(false);
		if(!(BaseBullet.getBulletShooter(data1).equals(BaseBullet.getBulletShooter(data2))))
		{
			int i1 = BaseBullet.getBulletID(data1);
			int i2 = BaseBullet.getBulletID(data2);

//			bulletFactory.bullets.get(i1).hitByBullet(data1);
//			bulletFactory.bullets.get(i2).hitByBullet(data2);
		}
	}

	public void handleBulletToBullet(Contact contact, String data1, String data2)
	{
		if(BaseBullet.getBulletShooter(data1).equals(BaseBullet.getBulletShooter(data2)))
		{
			return;
		}

		int i1 = BaseBullet.getBulletID(data1);
		int i2 = BaseBullet.getBulletID(data2);

//		bulletFactory.bullets.get(i1).hitByBullet(data2);
//		bulletFactory.bullets.get(i2).hitByBullet(data1);
	}

	public void handleBulletToGround(Contact contact, int bulletID)
	{
		bulletFactory.bullets.get(bulletID).hitByGround();
	}

	public void handleBulletToEnemy(Contact contact, String bulletData, String enemyData)
	{
		contact.setEnabled(false);
		if(BaseBullet.getBulletShooter(bulletData).equals(BodyStrings.Shooter_ENEMY))
		{
			return;
		}

		int bulletID = BaseBullet.getBulletID(bulletData);
		int enemyID = BaseEnemy.getEnemyID(enemyData);

		bulletFactory.bullets.get(bulletID).hitByEnemy(enemyData);
		enemyFactory.enemies.get(enemyID).hitByBullet(bulletData);

		contact.setEnabled(false);
	}

	public void handleFinishBody(Contact contact, String carData)
	{
//		Log.e("GameSceneContactManager.java", "Handling finish Body");

		gameManager.levelManager.currentLevel.getCurrentPart().isFinished = true;

		contact.setEnabled(false);
	}

	public void handleCarToEnemy(Contact contact, String carData, String enemyData)
	{
		int enemyID = BaseEnemy.getEnemyID(enemyData);

		if(BodyStrings.isDrivingEnemy(enemyData))
		{
			enemyFactory.enemies.get(enemyID).hitByCar(contact, carData);
            if(enemyFactory.enemies.get(enemyID).DAMAGE != -1)
			    gameManager.selectedCar.hitByDrivingEnemy(contact);
		}

		contact.setEnabled(false);
	}

	public static float getContactIntense(Contact contact)
	{
		Fixture x1 = contact.getFixtureA();
		Fixture x2 = contact.getFixtureB();
		float rat = PhysicsConstant.PIXEL_TO_METER;
		WorldManifold world;
		world = contact.getWorldManifold();
		Vector2 worldPoint = world.getPoints()[0];
		Vector2 v1 = x1.getBody().getLinearVelocityFromWorldPoint(worldPoint);
		Vector2 v2 = x2.getBody().getLinearVelocityFromWorldPoint(worldPoint);
		Vector2 minus = new Vector2(v1.x - v2.x, v1.y - v2.y);
		float relativeSpeed = minus.len();
		float fric = x1.getFriction() * x2.getFriction();
		float intence = relativeSpeed * fric;

		return intence;
	}
}
