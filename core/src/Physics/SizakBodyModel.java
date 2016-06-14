package Physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import BaseCar.CarSlot;
import Misc.BodyStrings;
import Misc.Log;
import Misc.TextureHelper;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/5/16.
 * 23:39
 *
 * It's just a model! just showing sprites at the positions without creating any body. no need to any world.
 */
public class SizakBodyModel
{
	public MainActivity activity;
	public ArrayList <Sprite> sprites = new ArrayList<Sprite>();

	public SizakBodyModel(MainActivity activity)
	{
		this.activity = activity;
	}

	public void loadSpritesFromBodyFile(String path, ArrayList<Texture> disposableArray)
	{
		FileHandle f = Gdx.files.internal(path);
		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		float ratio = PhysicsConstant.PIXEL_TO_METER;

		String read;
		while (true)
		{
			try
			{
				read = dis.readLine();

				if(read.equals(SizakBodyLoader.EOF))
					break;

				if(BodyStrings.getPartOf(read, 0).equals(SizakBodyLoader.BodyTagString))
				{
					String imgAdd = BodyStrings.getPartOf(read, 7);

					if(BodyStrings.getPartOf(read, 3).equals(SizakBodyLoader.CircleBody))
					{
						sprites.add(new Sprite(TextureHelper.loadTexture(imgAdd, disposableArray)));
						createCircleBodySprite(dis);
					}

					if(BodyStrings.getPartOf(read, 3).equals(SizakBodyLoader.PolygonBody))
					{
						sprites.add(new Sprite(TextureHelper.loadTexture(imgAdd, disposableArray)));
						createPolygonBodySprite(dis);
					}
				}//Body Tag

				if(BodyStrings.getPartOf(read, 0).equals(SizakBodyLoader.JointTagString))
				{
					String jointType = BodyStrings.getPartOf(read, 2);

					if(jointType.equals(SizakBodyLoader.WheelJointString))
					{
						dis.readLine();
						dis.readLine();
						dis.readLine();
					}//Wheel Joint

					if(jointType.equals(SizakBodyLoader.RevoluteJointString))
					{
						dis.readLine();
					}//Revolute Joint
				}//Joint Tag
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	float ratio = PhysicsConstant.PIXEL_TO_METER;
	public void createPolygonBodySprite(BufferedReader dis) throws IOException
	{
		String input = dis.readLine();
		float cX = Float.valueOf(BodyStrings.getPartOf(input, 0)) / ratio;
		float cY = Float.valueOf(BodyStrings.getPartOf(input, 1)) / ratio;

		input = dis.readLine();
		float width = Float.valueOf(BodyStrings.getPartOf(input, 0)) / ratio;
		float height = Float.valueOf(BodyStrings.getPartOf(input, 1)) / ratio;

		input = dis.readLine();
		int polygonCounter = Integer.valueOf(input);

		for (int i = 0; i < polygonCounter; i++)
		{
			input = dis.readLine();
			int vertexCounter = Integer.valueOf(BodyStrings.getPartOf(input, 1));

			for (int j = 0; j < vertexCounter; j++)
				dis.readLine();
		}

		sprites.get(sprites.size() - 1).setSize(width * ratio, height * ratio);
		sprites.get(sprites.size() - 1).setPosition(cX * ratio - width / 2 * ratio, cY * ratio - height / 2 * ratio);
	}

	public void createCircleBodySprite(BufferedReader dis) throws IOException
	{
		String input = dis.readLine();
		float cX = Float.valueOf(BodyStrings.getPartOf(input, 0));
		float cY = Float.valueOf(BodyStrings.getPartOf(input, 1));
		input = dis.readLine();
		float radius = Float.valueOf(input);

		sprites.get(sprites.size() - 1).setSize(2 * radius, 2 * radius);
		sprites.get(sprites.size() - 1).setPosition(cX - radius, cY - radius);
	}

	public void draw(Batch batch)
	{
		for(int i = 0;i < sprites.size();i++)
			sprites.get(i).draw(batch);
	}

	public void setPosition(float x, float y)
	{
		if(sprites.size() == 0)
			return;

		float diffX = x - sprites.get(0).getX();
		float diffY = y - sprites.get(0).getY();

		for(int i = 0;i < sprites.size();i++)
			sprites.get(i).setPosition(sprites.get(i).getX() + diffX, sprites.get(i).getY() + diffY);
	}
}
