package Enemy.EnemyState;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import EnemyBase.DrivingModeEnemy;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import Terrain.Terrain;

;

/**
 * Created by sinazk on 7/4/16.
 * 06:01
 */
public class EnemyState
{
	GameManager gameManager;
	DrivingModeEnemy parent;

	public float minHP;
	public EnemyState(DrivingModeEnemy par, float minHp)
	{
		parent = par;
		gameManager = parent.gameManager;
		minHP = minHp;
	}

	public float width, height;
	public CzakBody body;
	public boolean isDestroyed, isAttachedToGround;

	public void draw(Batch batch)
	{
		if(isDestroyed)
			return;

		if(body != null)
		{
			body.getmSprite().get(0).setSize(width, height);
			body.draw(batch);
		}

		float paddingX = 5;

		float hpBarX = parent.x - width / 2 + paddingX;
		float hpBarY = parent.y + height / 2 + 6;
		gameManager.hpBarSprite.draw(batch, hpBarX, hpBarY, parent.hitPoint, parent.getMAX_HP(), width - paddingX * 2, 10);
		gameManager.gameScene.font24Gold.draw(batch, "" + parent.level, hpBarX, hpBarY);
	}

	public void run()
	{
		if(isDestroyed)
			return;

		if(parent.hitPoint < minHP)
		{
			destroy();
		}
	}

	public void destroy()
	{
		detachFromGround();
		body.getmBody().setActive(false);
		isDestroyed = true;

		parent.goToNextState();
	}

	public void reset()
	{
		isDestroyed = false;
		setPosition(parent.x, parent.y, 0);
		detachFromGround();
	}

	public void init(String userData, int id, Sprite sprite)
	{
		body = new CzakBody(PhysicsFactory.createBoxBody(gameManager.gameScene.world, 0, 0, width, height, BodyDef.BodyType.DynamicBody),
			sprite);
		body.setUserData(BodyStrings.ENEMY_STRING + " " + userData + " " + id);
	}


	public void setPosition(float x, float y)
	{
		if(body != null)
			body.setPosition(x / PhysicsConstant.PIXEL_TO_METER, y / PhysicsConstant.PIXEL_TO_METER);
	}

	public void setPosition(float x, float y, float r)
	{
		if(body != null)
			body.getmBody().setTransform(x / PhysicsConstant.PIXEL_TO_METER, y / PhysicsConstant.PIXEL_TO_METER, r);
	}

	public void release()
	{
		if(body != null)
		{
			body.getmBody().setActive(false);
			body.getmBody().setLinearVelocity(0, 0);
			body.getmBody().setAngularVelocity(0);
		}
	}

	public void attachToGround()
	{
		if(isAttachedToGround)
		{
			return;
		}

		body.getmBody().setType(BodyDef.BodyType.StaticBody);

		isAttachedToGround = true;
	}

	public void detachFromGround()
	{
		if(!isAttachedToGround)
			return;

//		gameManager.activity.audioManager.playCrash();

		isAttachedToGround = false;
		body.getmBody().setType(BodyDef.BodyType.DynamicBody);
	}
}
