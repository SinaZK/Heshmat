package Physics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

import java.util.ArrayList;

import Misc.BodyStrings;
import Misc.Log;

/**
 * Created by sinazk on 5/16/16.
 * 05:21
 *
 * a sizak body consist of bodies and joints!!! ComplexBody
 */
public class SizakBody
{
	public ArrayList <CzakBody> bodies = new ArrayList<CzakBody>();
	public ArrayList <Joint> joints = new ArrayList<Joint>();

	public SizakBody(){}

	public void addJoint(Joint j)
	{
		joints.add(j);
	}

	public void addBody(CzakBody body)
	{
		bodies.add(body);
	}

	public void loadFromBodyFile()
	{

	}

	public void draw(Batch batch)
	{
		for(int i = 0;i < bodies.size();i++)
			bodies.get(i).draw(batch);
	}

	public CzakBody getBodyByName(String name)
	{
		for(int i = 0;i < bodies.size();i++)
			if(bodies.get(i).bodyName.equals(name))
				return bodies.get(i);

		Log.e("Tag", "Body not founded = " + name);
		Log.e("Tag", "available are : ");
		for(int i = 0;i < bodies.size();i++)
			Log.e("Tag", "i = " + i + ": " + bodies.get(i).bodyName);

		return null;
	}

	public int getBodyIndexByName(String name)
	{
		for(int i = 0;i < bodies.size();i++)
			if(bodies.get(i).bodyName.equals(name))
				return i;

		return -1;
	}

	public Joint getJointByName(String name)
	{
		for(int i = 0;i < joints.size();i++)
			if(joints.get(i).getUserData().equals(name))
				return joints.get(i);

		return null;
	}

	public int getJointIndexByName(String name)
	{
		for(int i = 0;i < joints.size();i++)
			if(joints.get(i).getUserData().equals(name))
				return i;

		return -1;
	}

	public void setCar()
	{
		for(int i = 0;i < bodies.size();i++)
			bodies.get(i).setUserData(BodyStrings.CAR_STRING + " " + bodies.get(i).getmBody().getUserData());
	}

	public void setPrefixToUserData(String type)
	{
		for(int i = 0;i < bodies.size();i++)
			bodies.get(i).setUserData(type + " " + bodies.get(i).getmBody().getUserData());
	}

	public void setCenterPosition(float worldX, float worldY)
	{
		Vector2 position = bodies.get(0).getmBody().getWorldCenter();
		Vector2 diffVector = new Vector2(worldX - position.x, worldY - position.y);

		for(int i = 0;i < bodies.size();i++)
		{
			position = bodies.get(i).getmBody().getWorldCenter();
			bodies.get(i).setPosition(position.x + diffVector.x, position.y + diffVector.y);
		}
	}

	public void setCenterPosition(float worldX, float worldY, float r)
	{
		Vector2 position = bodies.get(0).getmBody().getWorldCenter();
		Vector2 diffVector = new Vector2(worldX - position.x, worldY - position.y);

		for(int i = 0;i < bodies.size();i++)
		{
			position = bodies.get(i).getmBody().getWorldCenter();
			bodies.get(i).setPosition(position.x + diffVector.x, position.y + diffVector.y, r);
		}
	}

	public WeldJoint addBodyWithWeld(CzakBody body, String attachedBodyName, World world)
	{
		bodies.add(body);

		CzakBody attachedBody = getBodyByName(attachedBodyName);

		WeldJointDef weldJointDef = new WeldJointDef();
		weldJointDef.initialize(body.getmBody(), attachedBody.getmBody(), body.getmBody().getWorldCenter());

		return (WeldJoint)world.createJoint(weldJointDef);
	}

	public WeldJoint addBodyWithWeldWithoutAdding(CzakBody body, String attachedBodyName, World world)
	{
		CzakBody attachedBody = getBodyByName(attachedBodyName);

		WeldJointDef weldJointDef = new WeldJointDef();
		weldJointDef.initialize(body.getmBody(), attachedBody.getmBody(), body.getmBody().getWorldCenter());

		return (WeldJoint)world.createJoint(weldJointDef);
	}

	public void setAllBodiesV(float vX, float vY)
	{
		for (int i = 0;i < bodies.size();i++)
			bodies.get(i).getmBody().setLinearVelocity(vX, vY);
	}

	public void setAllBodiesR(float r)
	{
		for (int i = 0;i < bodies.size();i++)
			bodies.get(i).setR(r);
	}

	public void setType(BodyDef.BodyType type)
	{
		for (int i = 0;i < bodies.size();i++)
			bodies.get(i).setType(type);
	}

	public void flushSprites()
	{
		for(int i = 0;i < bodies.size();i++)
			bodies.get(i).flushSprites();
	}

	public void disposeBodies(World world)
	{
		for(int i = 0;i < bodies.size();i++)
			world.destroyBody(bodies.get(i).getmBody());
	}

}
