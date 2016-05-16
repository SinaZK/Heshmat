package Physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Misc.BodyStrings;
import Misc.TextureHelper;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;

/**
 * Created by sinazk on 5/16/16.
 * 05:23
 * <p/>
 * a loader who loads .body file(sizakBody)
 */
public class SizakBodyLoader
{
	static String EOF = "end";
	static String BodyTagString = "body";
	static String BodyTypeDynamicString = "dynamic";
	static String BodyTypeStaticString = "static";
	static String CircleBody = "circle";
	static String PolygonBody = "polygon";
	static String JointTagString = "joint";
	static String WeldJointString = "weld";
	static String WheelJointString = "wheel";
	static String RevoluteJointString = "revolute";
	static String TRUE = "true";
	static String FALSE = "false";


	public static SizakBody loadBodyFile(String path, World world, ArrayList<Texture> disposableArray)
	{
		FileHandle f = Gdx.files.internal(path);
		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		SizakBody retBody = new SizakBody();
		float ratio = PhysicsConstant.PIXEL_TO_METER;

		String read;
		while (true)
		{
			try
			{
				read = dis.readLine();

				if(read.equals(EOF))
					break;

				if(BodyStrings.getPartOf(read, 0).equals(BodyTagString))
				{
					CzakBody newBody = new CzakBody();
					newBody.bodyName = BodyStrings.getPartOf(read, 1);
					BodyDef.BodyType bodyType = BodyDef.BodyType.DynamicBody;//faghat null nabashe
					FixtureDef fixtureDef = PhysicsFactory.
							createFixtureDef(Float.valueOf(BodyStrings.getPartOf(read, 4)), Float.valueOf(BodyStrings.getPartOf(read, 5)),
									Float.valueOf(BodyStrings.getPartOf(read, 6)));
					String imgAdd = BodyStrings.getPartOf(read, 7);

					if(BodyStrings.getPartOf(read, 2).equals(BodyTypeDynamicString))
						bodyType = BodyDef.BodyType.DynamicBody;

					if(BodyStrings.getPartOf(read, 2).equals(BodyTypeStaticString))
						bodyType = BodyDef.BodyType.StaticBody;

					if(BodyStrings.getPartOf(read, 3).equals(CircleBody))
					{
						newBody.setBody(createCircleBody(world, bodyType, fixtureDef, dis));
						newBody.addSprite(new Sprite(TextureHelper.loadTexture(imgAdd, disposableArray)));
						newBody.setUserData(newBody.bodyName);
						retBody.addBody(newBody);
					}

					if(BodyStrings.getPartOf(read, 3).equals(PolygonBody))
					{
						newBody.setBody(createPolygonBody(world, bodyType, fixtureDef, dis));
						newBody.addSprite(new Sprite(TextureHelper.loadTexture(imgAdd, disposableArray)));
						newBody.setUserData(newBody.bodyName);
						retBody.addBody(newBody);
					}
				}//Body Tag

				if(BodyStrings.getPartOf(read, 0).equals(JointTagString))
				{
					String jointName = BodyStrings.getPartOf(read, 1);
					String jointType = BodyStrings.getPartOf(read, 2);
					String body1Name = BodyStrings.getPartOf(read, 3);
					String body2Name = BodyStrings.getPartOf(read, 4);
					String collide = BodyStrings.getPartOf(read, 5);
					boolean collideConnected = false;
					if(collide.equals(TRUE))
						collideConnected = true;

					if(jointType.equals(WheelJointString))
					{
						WheelJoint wheelJoint = createWheelJoint(world, retBody.getBodyByName(body1Name).getmBody(), retBody.getBodyByName(body2Name).getmBody(),
								collideConnected, dis);
						wheelJoint.setUserData(jointName);
						retBody.addJoint(wheelJoint);
					}//Wheel Joint

					if(jointType.equals(WeldJointString))
					{
						WeldJoint weldJoint = createWeldJoint(world, retBody.getBodyByName(body1Name).getmBody(), retBody.getBodyByName(body2Name).getmBody(), dis);
						weldJoint.setUserData(jointName);
						retBody.addJoint(weldJoint);
					}//Weld Joint

					if(jointType.equals(RevoluteJointString))
					{
						RevoluteJoint wheelJoint = createRevoluteJoint(world, retBody.getBodyByName(body1Name).getmBody(), retBody.getBodyByName(body2Name).getmBody(),
								collideConnected, dis);
						wheelJoint.setUserData(jointName);
						retBody.addJoint(wheelJoint);
					}//Revolute Joint
				}//Joint Tag
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return retBody;
	}

	public static WheelJoint createWheelJoint(World world, Body body, Body wheel, boolean collide, BufferedReader dis) throws IOException
	{
		String input = dis.readLine();
		float cX = Float.valueOf(BodyStrings.getPartOf(input, 0));
		float cY = Float.valueOf(BodyStrings.getPartOf(input, 1));
		Vector2 position = new Vector2(cX / ratio, cY / ratio);
		input = dis.readLine();
		float damping = Float.valueOf(BodyStrings.getPartOf(input, 0));
		input = dis.readLine();
		float freq = Float.valueOf(BodyStrings.getPartOf(input, 0));

		final WheelJointDef mWheelJoint = new WheelJointDef();
		mWheelJoint.initialize(body, wheel, position, new Vector2(0.0f, 1.0f));
		mWheelJoint.collideConnected = collide;

		mWheelJoint.dampingRatio = damping;
		mWheelJoint.frequencyHz = freq;

		return (WheelJoint) world.createJoint(mWheelJoint);
	}

	public static RevoluteJoint createRevoluteJoint(World world, Body body, Body wheel, boolean collide, BufferedReader dis) throws IOException
	{
		String input = dis.readLine();
		float cX = Float.valueOf(BodyStrings.getPartOf(input, 0));
		float cY = Float.valueOf(BodyStrings.getPartOf(input, 1));
		Vector2 position = new Vector2(cX / ratio, cY / ratio);

		final RevoluteJointDef mWheelJoint = new RevoluteJointDef();
		mWheelJoint.initialize(body, wheel, position);
		mWheelJoint.collideConnected = collide;

		return (RevoluteJoint) world.createJoint(mWheelJoint);
	}

	public static WeldJoint createWeldJoint(World world, Body a, Body b, BufferedReader dis) throws IOException
	{
		final WeldJointDef mParch = new WeldJointDef();
		mParch.bodyA = a;
		mParch.bodyB = b;
		mParch.collideConnected = false;

		return (WeldJoint) world.createJoint(mParch);
	}

	public static Body createCircleBody(World world, BodyDef.BodyType bodyType, FixtureDef fixtureDef, BufferedReader dis) throws IOException
	{
		String input = dis.readLine();
		float cX = Float.valueOf(BodyStrings.getPartOf(input, 0));
		float cY = Float.valueOf(BodyStrings.getPartOf(input, 1));
		input = dis.readLine();
		float radius = Float.valueOf(input);

		return PhysicsFactory.createCircleBody(world, cX, cY, radius, bodyType, fixtureDef);
	}

	static float ratio = PhysicsConstant.PIXEL_TO_METER;

	public static Body createPolygonBody(World world, BodyDef.BodyType bodyType, FixtureDef fixtureDef, BufferedReader dis) throws IOException
	{
		String input = dis.readLine();
		float cX = Float.valueOf(BodyStrings.getPartOf(input, 0)) / ratio;
		float cY = Float.valueOf(BodyStrings.getPartOf(input, 1)) / ratio;

		input = dis.readLine();
		float width = Float.valueOf(BodyStrings.getPartOf(input, 0)) / ratio;
		float height = Float.valueOf(BodyStrings.getPartOf(input, 1)) / ratio;

		final BodyDef mBodyDef = new BodyDef();
		mBodyDef.type = bodyType;
		mBodyDef.position.x = cX;
		mBodyDef.position.y = cY;
		Body body = world.createBody(mBodyDef);

		input = dis.readLine();
		int polygonCounter = Integer.valueOf(input);

		for (int i = 0; i < polygonCounter; i++)
		{
			input = dis.readLine();
			int vertexCounter = Integer.valueOf(BodyStrings.getPartOf(input, 1));

			Vector2[] vertices = new Vector2[vertexCounter];

			for (int j = 0; j < vertexCounter; j++)
			{
				input = dis.readLine();

				vertices[j] = new Vector2((Float.valueOf(BodyStrings.getPartOf(input, 0)) * width),
						(-Float.valueOf(BodyStrings.getPartOf(input, 1)) * height));
			}

			final PolygonShape mPoly = new PolygonShape();
			mPoly.set(vertices);
			fixtureDef.shape = mPoly;

			body.createFixture(fixtureDef);

			mPoly.dispose();
		}

		return body;
	}
}
