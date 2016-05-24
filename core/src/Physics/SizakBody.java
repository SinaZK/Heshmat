package Physics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Joint;

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

}