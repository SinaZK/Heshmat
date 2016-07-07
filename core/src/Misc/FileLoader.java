package Misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import heshmat.MainActivity;

/**
 * Created by sinazk on 6/19/16.
 * 14:19
 */
public class FileLoader
{
	FileHandle fileHandle;
	ArrayList<String> lineStrings = new ArrayList<String>();

	public void loadFile(String path)
	{
		lineStrings.clear();

		FileHandle f = Gdx.files.internal(path);
		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		try
		{
			String input;
			while ((input = dis.readLine()) != null)
			{
				lineStrings.add(input);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String getLine(int lineNumber)
	{
		return lineStrings.get(lineNumber);
	}

	public float getFloat(int lineNumber, int pos)
	{
		return Float.valueOf(BodyStrings.getPartOf(lineStrings.get(lineNumber), pos));
	}

	public long getLong(int lineNumber, int pos)
	{
		return Long.valueOf(BodyStrings.getPartOf(lineStrings.get(lineNumber), pos));
	}

	public int getInt(int lineNumber, int pos)
	{
		return Integer.valueOf(BodyStrings.getPartOf(lineStrings.get(lineNumber), pos));
	}

	public String getString(int lineNumber, int pos)
	{
		return BodyStrings.getPartOf(lineStrings.get(lineNumber), pos);
	}

	public float getFloat(int lineNumber)
	{
		return Float.valueOf(lineStrings.get(lineNumber));
	}

	public float getInt(int lineNumber)
	{
		return Integer.valueOf(lineStrings.get(lineNumber));
	}
}
