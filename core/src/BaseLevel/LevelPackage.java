package BaseLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import GameScene.GameManager;
import GameScene.LevelManager;
import Misc.BodyStrings;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/4/16.
 * 15:16
 */
public class LevelPackage
{
	MainActivity activity;
	public int numberOfLevels;

	public LevelPackage(MainActivity act)
	{
		this.activity = act;
	}

	public void load(String add)
	{
		FileHandle f = Gdx.files.internal(add);
		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		try
		{
			String in = dis.readLine();

			numberOfLevels = Integer.valueOf(BodyStrings.getPartOf(in, 1));


		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
