package GameScene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.compression.lzma.Base;

import EnemyBase.BaseEnemy;
import EnemyBase.EnemyFactory;
import Misc.BodyStrings;
import Misc.Log;
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

//				Log.e("GameSceneContactManager.java", s1 + " and " + s2 + " contacted ");

				if(BodyStrings.isBullet(s1) && BodyStrings.isBullet(s2))
					handleBulletToBullet(contact, s1, s2);

				if(BodyStrings.isBullet(s1) && s2.equals(BodyStrings.GroundString))
					handleBulletToGround(contact, BaseBullet.getBulletID(s1));
				if(BodyStrings.isBullet(s2) && s1.equals(BodyStrings.GroundString))
					handleBulletToGround(contact, BaseBullet.getBulletID(s2));

				if(BodyStrings.isBullet(s1) && BodyStrings.isEnemy(s2))
					handleBulletToEnemy(contact, s1, s2);
				if(BodyStrings.isBullet(s2) && BodyStrings.isEnemy(s1))
					handleBulletToEnemy(contact, s2, s1);

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

				if(BodyStrings.isEnemy(s1) && BodyStrings.isEnemy(s2))
					contact.setEnabled(false);
			}

			@Override
			public void endContact(Contact contact)
			{

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

				String s1 = (String)contact.getFixtureA().getBody().getUserData();
				String s2 = (String)contact.getFixtureB().getBody().getUserData();

				if(BodyStrings.isEnemy(s1) || BodyStrings.isEnemy(s2))
					contact.setEnabled(false);

				if(BodyStrings.isCar(s1) && BodyStrings.isBullet(s2))
					handleBulletToCarPreSolve(contact, s1, s2);
				if(BodyStrings.isCar(s2) && BodyStrings.isBullet(s1))
					handleBulletToCarPreSolve(contact, s2, s1);

				if(BodyStrings.isBullet(s1) && BodyStrings.isBullet(s2))
					handleBulletToBulletPreSolve(contact, s1, s2);
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse)
			{
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
		if(BaseBullet.getBulletShooter(data1).equals(BaseBullet.getBulletShooter(data2)))
		{
			contact.setEnabled(false);
			return;
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

		bulletFactory.bullets.get(i1).hitByBullet(data2);
		bulletFactory.bullets.get(i2).hitByBullet(data1);
	}

	public void handleBulletToGround(Contact contact, int bulletID)
	{
		bulletFactory.bullets.get(bulletID).hitByGround();
	}

	public void handleBulletToEnemy(Contact contact, String bulletData, String enemyData)
	{
		if(BaseBullet.getBulletShooter(bulletData).equals(BodyStrings.Shooter_ENEMY))
			return;

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
}
