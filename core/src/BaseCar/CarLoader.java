package BaseCar;

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

import Cars.Train;
import DataStore.CarStatData;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.Log;
import Misc.TextureHelper;
import Physics.CzakBody;
import Physics.SizakBody;
import Physics.SizakBodyLoader;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;

/**
 * Created by sinazk on 5/16/16.
 * 05:23
 * <p/>
 * a loader who loads .body file(sizakBody)
 */
public class CarLoader
{
	public static String EOF = "END";
	static String WheelTagString = "wheel";
	public static String GUN_SLOT = "GUN";
	public static String UPGRADE_ENGINE   = "ENGINE";
	public static String UPGRADE_HIT_POINT = "HITPOINT";


	float ratio = PhysicsConstant.PIXEL_TO_METER;
	public static NormalCar loadCarFile(GameManager gameManager, String path, World world, ArrayList<Texture> disposableArray, CarStatData carStatData)
	{

		FileHandle f = Gdx.files.internal(path);
		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		NormalCar retCar = new NormalCar(gameManager, carStatData);

		try
		{
			String read;
			dis.readLine();//price
			read = dis.readLine();
			retCar.hitpoint = Float.valueOf(BodyStrings.getPartOf(read, 1));

			read = dis.readLine();
			retCar.collisionDamageRate = Float.valueOf(BodyStrings.getPartOf(read, 1));

			read = dis.readLine();
			retCar.body = SizakBodyLoader.loadBodyFile(BodyStrings.getPartOf(read, 1), world, disposableArray);

			read = dis.readLine();
			retCar.wheelNum = Integer.valueOf(BodyStrings.getPartOf(read, 1));

			retCar.isWheelDrive = new boolean[retCar.wheelNum];
			retCar.wheelJoints = new WheelJoint[retCar.wheelNum];
			retCar.wheelSpeed = new float[retCar.wheelNum];
			retCar.wheelTorque = new float[retCar.wheelNum];

			for(int i = 0;i < retCar.wheelNum;i++)
			{
				read = dis.readLine();
				String jointName = BodyStrings.getPartOf(read, 1);
				retCar.wheelJoints[i] = (WheelJoint)retCar.body.getJointByName(jointName);
//				Log.e("CarLoader.java", "jointName = " + jointName + " id = " + retCar.body.getJointIndexByName(jointName));
				retCar.isWheelDrive[i] = Boolean.valueOf(BodyStrings.getPartOf(read, 2));

				read = dis.readLine();
				retCar.wheelTorque[i] = Float.valueOf(BodyStrings.getPartOf(read, 1));
				read = dis.readLine();
				retCar.wheelSpeed[i] = Float.valueOf(BodyStrings.getPartOf(read, 1));
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return retCar;
	}

}
