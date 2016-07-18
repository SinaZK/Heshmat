package ChangeAblePackage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.ArrayList;

import Misc.CameraHelper;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;

public class HorizentalChangablePackage extends Actor
{
	//0Base
	Stage mScene;

	public int current;
	public ArrayList<Group> entity =  new ArrayList<Group>();
	public OrthographicCamera mCamera;

	float groupWidth;
	float groupHeight;

	float paddingX;

	float SWIPE_TIME = 10000;

	public Actor cameraBitch = new Actor();
	
	float [] positions = new float[100];

	public float shouldGoToX = -1;
	public boolean isPanStop = false;
	public boolean isPanning = false;//by user
	public boolean isFling = false;

	public HorizentalChangablePackage(float x, float y, float w, float h, OrthographicCamera pCamera)
	{
		setPosition(x, y);
		
		cameraBitch.setPosition(x, y);
		cameraBitch.setBounds(x, y, 1, 1);

		setBounds(x, y, w, h);
		setTouchable(Touchable.enabled);
		mCamera = pCamera;
		setPosition(x, y);
	}

	public void setATT(float gw, float gh, float padding, Stage pScene)
	{
		groupWidth = gw;
		groupHeight = gh;

		paddingX = padding;

		mScene = pScene;

		mScene.addActor(cameraBitch);
		cameraBitch.setY(gh / 2);
	}

	public void run()
	{
		if(isFling)
		{
		}
		else if(isPanStop)
		{
			setCurrent(calcCurrent(), SWIPE_TIME);
			isPanStop = false;
		}
		
		CameraHelper.chaseCamera(mCamera, cameraBitch);
		
		int curr = calcCurrent();
		for(int i = 0;i < entity.size();i++)
			entity.get(i).setScale(1);
		
		float maxScaleADD = 0.3f;
		float diff = Math.abs(getXOfI(curr) - cameraBitch.getX());
		diff = groupWidth / 2 + paddingX / 2 - diff;
		float scale = 1 + maxScaleADD * diff / (groupWidth / 2 + paddingX / 2);
		entity.get(curr).setScale(scale);
		
//		float cx = getXOfI(curr);
//		float cy = getY() + groupHeight / 2;
	}

	public float getXOfI(int i)
	{
		return getX() + paddingX + (i) * (groupWidth + paddingX) + groupWidth / 2; 
	}

	public void addItem(Group g)
	{
		g.setOrigin(g.getWidth() / 2, g.getHeight() / 2);
		entity.add(g);
		g.setPosition(getXOfI(entity.size() - 1) - groupWidth / 2, getY());
		g.setSize(groupWidth, groupHeight);
	}

	public void setCurrent(int i)
	{
		current = i;
		cameraBitch.setPosition(getXOfI(i), getY() + groupHeight / 2);
	}
	
	public void setCurrent(int i, float time)
	{
		current = i;
		cameraBitch.addAction(parallel(moveTo(getXOfI(i), getY() + groupHeight / 2, time)));
	}

	public int calcCurrent()
	{
		float x = cameraBitch.getX();

		int pos = -1;
		for(int i = 0;i < entity.size();i++)
			if(positions[i] > x)
			{
				pos = i;
				break;
			}
		
		if(pos == -1)
			pos = entity.size() - 1;
		
		return pos;
	}

}
