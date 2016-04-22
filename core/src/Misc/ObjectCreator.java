package Misc;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;

public class ObjectCreator 
{
	public static void createGround(World world)
	{
		Body groundBody;
		BodyDef groundDef;
		groundDef = new BodyDef();
//		groundDef.position.set(new Vector2((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX,
//				16f * WORLD_TO_BOX));
		
		groundDef.position.set(400 / PhysicsConstant.PIXEL_TO_METER,
				5 / PhysicsConstant.PIXEL_TO_METER);
		
		groundBody = world.createBody(groundDef);
		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox(400 * 1000 / PhysicsConstant.PIXEL_TO_METER,
				10f / PhysicsConstant.PIXEL_TO_METER);
		groundBody.createFixture(groundShape, 0f);
		groundShape.dispose();
	}
	
	public static Body createBox(World world, float x, float y)
	{
		Body groundBody;
		BodyDef groundDef;
		groundDef = new BodyDef();
//		groundDef.position.set(new Vector2((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX,
//				16f * WORLD_TO_BOX));
		
		groundDef.position.set(x / PhysicsConstant.PIXEL_TO_METER,
				y / PhysicsConstant.PIXEL_TO_METER);
		
		FixtureDef boxFixtureDef = PhysicsFactory.createFixtureDef(1, 0.4f, 1);

		groundBody = world.createBody(groundDef);
		groundBody.setType(BodyType.DynamicBody);
		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox(15 / PhysicsConstant.PIXEL_TO_METER,
				15f / PhysicsConstant.PIXEL_TO_METER);
		
		boxFixtureDef.shape = groundShape;
		
		
		groundBody.createFixture(boxFixtureDef);
		groundShape.dispose();
		
		return groundBody;
	}
	
}
