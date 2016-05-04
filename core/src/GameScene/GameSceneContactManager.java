package GameScene;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import Misc.Log;
import heshmat.MainActivity;

public class GameSceneContactManager
{
	GameSceneNormal mScene;
	MainActivity act;

	GameSceneContactManager(MainActivity act, GameSceneNormal gs)
	{
		this.act = act;
		mScene = gs;
	}

	public ContactListener makeContact()
	{
		ContactListener cl = new ContactListener() {
			@Override
			public void beginContact(Contact contact)
			{
			}

			@Override
			public void endContact(Contact contact) {

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}
		};

		return  cl;
	}

}
