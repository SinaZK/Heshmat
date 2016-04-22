package Misc;

import com.badlogic.gdx.Gdx;

public class Log 
{
	
	public static void e(String Tag, String Msg)
	{
		Gdx.app.log(Tag, Msg);
	}
	
}
