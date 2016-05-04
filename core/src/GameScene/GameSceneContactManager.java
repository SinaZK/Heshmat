package GameScene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import Misc.BodyStrings;
import Misc.Log;
import WeaponBase.BulletFactory;
import heshmat.MainActivity;

public class GameSceneContactManager
{
	GameScene mScene;
	MainActivity act;
	GameManager gameManager;
	BulletFactory bulletFactory;

	GameSceneContactManager(MainActivity act, GameScene gs)
	{
		this.act = act;
		mScene = gs;
		gameManager = mScene.gameManager;
		bulletFactory = gameManager.bulletFactory;
	}

	public ContactListener makeContact()
	{
		ContactListener cl = new ContactListener() {
			@Override
			public void beginContact(Contact contact)
			{
			}

			@Override
			public void endContact(Contact contact)
			{

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse)
			{
				String s1 = (String)contact.getFixtureA().getBody().getUserData();
				String s2 = (String)contact.getFixtureB().getBody().getUserData();

				if(BodyStrings.FullStringIsBullet(s1) && BodyStrings.FullStringIsBullet(s2))
					handleBulletToBullet(contact, s1, s2);

				if(BodyStrings.FullStringIsBullet(s1) && s2.equals(BodyStrings.GroundString))
					handleBulletToGround(contact, BodyStrings.getIndexOf(s1));

				if(BodyStrings.FullStringIsBullet(s2) && s1.equals(BodyStrings.GroundString))
					handleBulletToGround(contact, BodyStrings.getIndexOf(s2));
			}
		};

		return  cl;
	}

	public void handleBulletToBullet(Contact contact, String data1, String data2)
	{
//		Log.e("GameSceneContactManager", "collision : " + data1 + " and " + data2);
		int i1 = BodyStrings.getIndexOf(data1);
		int i2 = BodyStrings.getIndexOf(data2);

		bulletFactory.bullets.get(i1).shouldRelease = true;
		bulletFactory.bullets.get(i2).shouldRelease = true;
	}

	public void handleBulletToGround(Contact contact, int bulletID)
	{
		bulletFactory.bullets.get(bulletID).hitByGround();
	}
}
