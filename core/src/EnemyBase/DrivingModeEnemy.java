package EnemyBase;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;

import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import Enemy.EnemyState.EnemyState;
import Entity.AnimatedSpriteSheet;
import Entity.SizakAnimation;
import GameScene.GameManager;
import GameScene.GameSceneContactManager;
import Misc.BodyStrings;
import Misc.FileLoader;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;

/**
 * Created by sinazk on 7/4/16.
 * 05:41
 *
 * It can be in any mode(Driving, Shooting ...) So don't use ShootingMode!
 */
public class DrivingModeEnemy extends BaseEnemy
{
	public DrivingModeEnemy(GameManager gameManager, int id)
	{
		super(gameManager, id);
	}

	public int stateCount, currentState;
	public ArrayList<EnemyState> states = new ArrayList<EnemyState>();
	public boolean isAttachedToGround;

	@Override
	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr)//shootingMode should be null!!!
	{
		super.create(shootingMode, level, attr);
		isKillCount = false;
		currentState = 0;
		isAttachedToGround = false;

		for(int i = 0;i < stateCount;i++)
		{
			states.get(i).body.getmBody().setActive(false);
			states.get(i).reset();
		}

		states.get(currentState).body.getmBody().setActive(true);

		mainBody = states.get(currentState).body;
	}

	@Override
	public void draw(Batch batch)
	{
		if(currentState >= stateCount)
			currentState = stateCount - 1;
		states.get(currentState).draw(batch);
	}

	@Override
	public FileLoader load(String path)
	{
		FileLoader loader = super.load(path);

		int lineCT = 10;
		stateCount = loader.getInt(lineCT++, 1);

		for(int i = 0;i < stateCount;i++)
		{
			float width  = loader.getFloat(lineCT, 1);
			float height = loader.getFloat(lineCT, 2);

			lineCT++;

			float percent = loader.getFloat(lineCT++, 1);
			float minHP = percent * getMAX_HP() / 100;

			EnemyState enemyState = new EnemyState(this, minHP);
			enemyState.width = width;
			enemyState.height = height;

			states.add(enemyState);

			lineCT++;//end
		}

		return loader;
	}

	@Override
	public void run()
	{
		if(mainBody != null)
		{
			x = mainBody.getmBody().getWorldCenter().x * PhysicsConstant.PIXEL_TO_METER;
			y = mainBody.getmBody().getWorldCenter().y * PhysicsConstant.PIXEL_TO_METER;
		}
		if(hitPoint < 0 || currentState >= stateCount)
		{
			release();
		}

		for(int i = 0;i < stateCount;i++)
			states.get(i).setPosition(x, y, states.get(currentState).body.getmBody().getAngle());

		if(isFree)
			return;

		if(isAttachedToGround)
		{
			if(!states.get(currentState).isAttachedToGround)
				states.get(currentState).attachToGround();
		}
		else states.get(currentState).detachFromGround();

		states.get(currentState).run();
	}

	@Override
	public void init(String userData, int id, Texture texture)
	{
		Log.e("DrivingModeEnemy.java", "Nothing to do here");
	}

	@Override
	public void init(String userData, int id, AnimatedSpriteSheet texture)
	{
		Log.e("DrivingModeEnemy.java", "Nothing to do here");
//		super.init(userData, id, texture);
	}

	public void initFromAnimation(String userData, int id, SizakAnimation animation)
	{
		for(int i = 0;i < stateCount;i++)
		{
			states.get(i).init(userData, id, animation.sprites[i]);

//			if(i == stateCount - 1)
//				states.get(i).body.setUserData(BodyStrings.CAR_ATTACH_STRING + " " + userData + " " + id);
		}
	}

	@Override
	public void setPosition(float X, float Y)
	{
		super.setPosition(X, Y);

		for(int i = 0;i < stateCount;i++)
			states.get(i).setPosition(X, Y);
	}

	public void setPosition(float x)
	{
		int pieceID = gameManager.levelManager.currentLevel.terrain.getIndexOfX(x);
		float y1 = gameManager.levelManager.currentLevel.terrain.Points.get(pieceID).y;
		float y2 = gameManager.levelManager.currentLevel.terrain.Points.get(pieceID - 1).y;
		float Y = Math.max(y1, y2) + states.get(currentState).height / 2;
		setPosition(x, Y);
	}

	public void attachToGround(float x)
	{
		if(isAttachedToGround)
			return;

		int pieceID = gameManager.levelManager.currentLevel.terrain.getIndexOfX(x);
		if(pieceID == -1)
			pieceID = gameManager.levelManager.currentLevel.terrain.Points.size() - 1;
		float y1 = gameManager.levelManager.currentLevel.terrain.Points.get(pieceID).y;
		float y2 = gameManager.levelManager.currentLevel.terrain.Points.get(pieceID - 1).y;
		float Y = Math.max(y1, y2) + states.get(currentState).height / 2;
		setPosition(x, Y);

		isAttachedToGround = true;

//		Log.e("enemy", "attach : " + enemyType + " id = " + pieceID + " y = " + y1);
	}

	public void goToNextState()
	{
		currentState++;

//		Log.e("DrivingModeEnemy.java", "CurrentState++ = " + currentState);

		if(currentState >= stateCount)
		{
			currentState = stateCount - 1;
			return;
		}

		mainBody = states.get(currentState).body;
		mainBody.getmBody().setActive(true);
	}

	@Override
	public void hitByCar(Contact contact, String carData)
	{
		super.hitByCar(contact, carData);

		float intence = GameSceneContactManager.getContactIntense(contact) * gameManager.selectedCar.getCollisionDamageRate();

		damage(intence);
//		Log.e("DrivingModeEnemy.java", "Intense = " + intence);
	}

	@Override
	public void hitBy(Contact contact)
	{
		if(hitPoint < 0.1f * getMAX_HP())
			return;

		float intence = GameSceneContactManager.getContactIntense(contact) * gameManager.selectedCar.getCollisionDamageRate();
		damage(intence);
	}

	@Override
	public void release()
	{
		super.release();

		for(int i = 0;i < stateCount;i++)
			states.get(i).release();
	}

}
