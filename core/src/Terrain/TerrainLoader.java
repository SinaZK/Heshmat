package Terrain;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import Misc.Log;
import heshmat.MainActivity;

public class TerrainLoader
{
	MainActivity act;

	FileHandle f;

	public float y, ay, by;
	public float y2, ay2, by2;
	public float maxA;
	public float maxB;
	public float maxA2;
	public float maxB2;
	public float floatRandSeed;
	public float zoom;
	public int randSeed;
	public float minSlope;
	public float maxSlope;
	public float slopeStep;
	
	public int bgSize;
	public float[] layersSpeedX = new float[10];
	public float[] layersSpeedY = new float[10];
	public int[] layersIsAuto = new int[10];
	
	public int coinStep;
	public int coinStepAdderAfter;
	public int coinStepAdder;
	public int coinNum;
	public int coinValue;
	public int coinValueAdder;
	public int fuelStep;
	public int firstFuel;
	public int timeStep;
	public int firstTime;
	
	public int sandDist;
	public int stoneDist;
	public int bumperDist;
	public int bridgeDist;
	
	public TerrainLoader(String path, boolean isInternal) 
	{
		readFromAddress(path, isInternal);
	}

	public void readFromAddress(String path, boolean isInternal)
	{
		if(isInternal)
			f = Gdx.files.internal(path);
		else
			f = Gdx.files.external(path);

		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		zoom = readAndStore(dis);
		floatRandSeed = readAndStore(dis);
		randSeed = (int) (floatRandSeed);
		y = readAndStore(dis);
		ay = readAndStore(dis);
		by = readAndStore(dis);
		y2 = readAndStore(dis);
		ay2 = readAndStore(dis);
		by2 = readAndStore(dis);
		maxA = readAndStore(dis);
		maxB = readAndStore(dis);
		maxA2 = readAndStore(dis);
		maxB2 = readAndStore(dis);
		minSlope = readAndStore(dis);
		maxSlope = readAndStore(dis);
		slopeStep = readAndStore(dis);
		
		float floatBgSize = readAndStore(dis);
		bgSize = (int) floatBgSize;
		
		for(int i = 0;i < bgSize;i++)
		{
			layersSpeedX[i] = readAndStore(dis);
			layersSpeedY[i] = readAndStore(dis);
			float tmp = readAndStore(dis);
			layersIsAuto[i] = (int) tmp;
		}
		
		coinStep = (int) readAndStore(dis);
		coinStepAdderAfter = (int) readAndStore(dis);
		coinStepAdder = (int) readAndStore(dis);
		coinNum = (int) readAndStore(dis);
		coinValue = (int) readAndStore(dis);
		coinValueAdder = (int) readAndStore(dis);
		fuelStep = (int) readAndStore(dis);
		firstFuel = (int) readAndStore(dis);
		timeStep = (int) readAndStore(dis);
		firstTime = (int) readAndStore(dis);
		
		sandDist = (int) readAndStore(dis);
		stoneDist = (int) readAndStore(dis);
		bumperDist = (int) readAndStore(dis);
		bridgeDist = (int) readAndStore(dis);

	}

	public float separator(String s)
	{
		String [] val = s.split(" ", 2);

		float ret = Float.valueOf(val[1]); 
		
		return ret;
	}

	public float readAndStore(BufferedReader dis)
	{
		float storage = 0;
		try
		{
			String in = dis.readLine();
			storage = separator(in);
			
		} catch (IOException e)
		{
			Log.e("TerrainLoader Exception", e.toString());
		}
		
		return storage;
	}
}
