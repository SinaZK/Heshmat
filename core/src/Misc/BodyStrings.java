package Misc;

/**
 * Created by Sinazk on 5/4/16.
 * Hi
 */

public class BodyStrings
{
	public static String BulletPistolString = "PistolBullet";
	public static String BulletRocketString = "RocketString";//remember after adding new bullet, complete the FullStringIsBullet() !!!

	public static String GroundString = "Ground";

	public static boolean FullStringIsBullet(String s)
	{
		String tmp = getFirstPart(s);
		if(tmp.equals(BulletPistolString) || tmp.equals(BulletRocketString))
			return true;

		return false;
	}

	public static int getIndexOf(String s)
	{
		String [] tmp = s.split(" ");
		return Integer.valueOf(tmp[1]);
	}

	public static String getFirstPart(String s)
	{
		String [] tmp = s.split(" ");
		return tmp[0];
	}
}
