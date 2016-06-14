package PhysicsFactory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class PhysicsFactory 
{
	/**
	 * deprecated, left for backward compatibility
	 */
	public static Body createLineBody(final World pPhysicsWorld, final float pX1, final float pY1, final float pX2, final float pY2, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createLineBody(pPhysicsWorld, pX1, pY1, pX2, pY2, pFixtureDef, PhysicsConstant.PIXEL_TO_METER);
	}

	/**
	 * deprecated, left for backward compatibility
	 */
	public static Body createLineBody(final World pPhysicsWorld, final float pX1, final float pY1, final float pX2, final float pY2, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final BodyDef lineBodyDef = new BodyDef();
		lineBodyDef.type = BodyType.StaticBody;
		
		final Body boxBody = pPhysicsWorld.createBody(lineBodyDef);
		
		final EdgeShape linePoly = new EdgeShape();
		
		linePoly.set(new Vector2(pX1 / pPixelToMeterRatio, pY1 / pPixelToMeterRatio), new Vector2(pX2 / pPixelToMeterRatio, pY2 / pPixelToMeterRatio));
		pFixtureDef.shape = linePoly;
		
		boxBody.createFixture(pFixtureDef);
		
		linePoly.dispose();
		
		return boxBody;
	}
	
	
	public static Body createCircleBody(final World pPhysicsWorld, final float pCenterX, final float pCenterY, final float pRadius, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return createCircleBody(pPhysicsWorld, pCenterX, pCenterY, pRadius, 0, pBodyType, pFixtureDef, PhysicsConstant.PIXEL_TO_METER);
	}

	/**
	 * create Body of circle shape
	 * @param pPhysicsWorld
	 * @param pCenterX
	 * @param pCenterY
	 * @param pRadius
	 * @param pRotation
	 * @param pBodyType
	 * @param pFixtureDef
	 * @return
	 */
	public static Body createCircleBody(final World pPhysicsWorld, final float pCenterX, final float pCenterY, final float pRadius, final float pRotation, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return createCircleBody(pPhysicsWorld, pCenterX, pCenterY, pRadius, pRotation, pBodyType, pFixtureDef, PhysicsConstant.PIXEL_TO_METER);
	}

	/**
	 * create Body of circle shape
	 * @param pPhysicsWorld
	 * @param pCenterX
	 * @param pCenterY
	 * @param pRadius
	 * @param pBodyType
	 * @param pFixtureDef
	 * @param pPixelToMeterRatio
	 * @return
	 */
	public static Body createCircleBody(final World pPhysicsWorld, final float pCenterX, final float pCenterY, final float pRadius, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		return createCircleBody(pPhysicsWorld, pCenterX, pCenterY, pRadius, 0, pBodyType, pFixtureDef, pPixelToMeterRatio);
	}

	/**
	 * create Body of circle shape
	 * @param pPhysicsWorld
	 * @param pCenterX
	 * @param pCenterY
	 * @param pRadius
	 * @param pRotation
	 * @param pBodyType
	 * @param pFixtureDef
	 * @param pPixelToMeterRatio
	 * @return
	 */
	public static Body createCircleBody(final World pPhysicsWorld, final float pCenterX, final float pCenterY, final float pRadius, final float pRotation, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = pBodyType;

		circleBodyDef.position.x = pCenterX / pPixelToMeterRatio;
		circleBodyDef.position.y = pCenterY / pPixelToMeterRatio;

		circleBodyDef.angle = (float) (pRotation * Math.PI / 180f);

		final Body circleBody = pPhysicsWorld.createBody(circleBodyDef);

		final CircleShape circlePoly = new CircleShape();
		pFixtureDef.shape = circlePoly;

		final float radius = pRadius / pPixelToMeterRatio;
		circlePoly.setRadius(radius);

		circleBody.createFixture(pFixtureDef);

		circlePoly.dispose();

		return circleBody;
	}
	
	
	public static FixtureDef createFixtureDef(final float pDensity, final float pElasticity, final float pFriction) {
		return PhysicsFactory.createFixtureDef(pDensity, pElasticity, pFriction, false);
	}


	public static FixtureDef createFixtureDef(final float pDensity, final float pElasticity, final float pFriction, final boolean pSensor) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = pDensity;
		fixtureDef.restitution = pElasticity;
		fixtureDef.friction = pFriction;
		fixtureDef.isSensor = pSensor;
		return fixtureDef;
	}

	public static Body createBoxBody(World world, Sprite s, float density, float elasticity, float friction)
	{
		float rat = PhysicsConstant.PIXEL_TO_METER;
		return createBoxBody(world, s.getX(), s.getY(), s.getWidth(), s.getHeight(), BodyType.DynamicBody, 1, 1, 1);

	}

	public static Body createBoxBody(final World pPhysicsWorld, final float pCenterX, final float pCenterY,
			final float pWidth, final float pHeight, final float pRotation, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) 
	{
		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;

		boxBodyDef.position.x = pCenterX / pPixelToMeterRatio;
		boxBodyDef.position.y = pCenterY / pPixelToMeterRatio;

		final Body boxBody = pPhysicsWorld.createBody(boxBodyDef);

		final PolygonShape boxPoly = new PolygonShape();

		final float halfWidth = pWidth * 0.5f / pPixelToMeterRatio;
		final float halfHeight = pHeight * 0.5f / pPixelToMeterRatio;

		boxPoly.setAsBox(halfWidth, halfHeight);
		pFixtureDef.shape = boxPoly;

		boxBody.createFixture(pFixtureDef);

		boxPoly.dispose();

		boxBody.setTransform(boxBody.getWorldCenter(), MathUtils.degRad * pRotation);

		return boxBody;
	}
	
	public static Body createBoxBody(final World pPhysicsWorld, final float X, final float Y,
			final float pWidth, final float pHeight, final BodyType pBodyType, final FixtureDef pFixtureDef) 
	{
		float pCenterX = X + pWidth / 2;
		float pCenterY = Y + pHeight / 2;
		
		return createBoxBody(pPhysicsWorld, pCenterX, pCenterY, pWidth, pHeight, 0, pBodyType, pFixtureDef, PhysicsConstant.PIXEL_TO_METER);
	}
	
	public static Body createBoxBody(final World world, float x, float y, float w, float h, BodyType bodyType)
	{
		return createBoxBody(world, x, y, w, h, bodyType, createFixtureDef(1, 0.3f, 1));
	}
	
	public static Body createBoxBody(final World world, float x, float y, float w, float h, BodyType bodyType, float density, float elasticy,
			float friction)
	{
		return createBoxBody(world, x, y, w, h, bodyType, createFixtureDef(density, elasticy, friction));
	}
	
	public static FixtureDef createFixtureDef(final float pDensity, final float pElasticity, final float pFriction, final boolean pSensor, final short pCategoryBits, final short pMaskBits, final short pGroupIndex) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = pDensity;
		fixtureDef.restitution = pElasticity;
		fixtureDef.friction = pFriction;
		fixtureDef.isSensor = pSensor;
		final Filter filter = fixtureDef.filter;
		filter.categoryBits = pCategoryBits;
		filter.maskBits = pMaskBits;
		filter.groupIndex = pGroupIndex;
		return fixtureDef;
	}
	
	public static void removeBodySafely(World world, Body body) {
	    //to prevent some obscure c assertion that happened randomly once in a blue moon
	    final Array<JointEdge> list = body.getJointList();
	    while (list.size > 0) {
	        world.destroyJoint(list.get(0).joint);
	    }
	    // actual remove
	    world.destroyBody(body);
	}
}
