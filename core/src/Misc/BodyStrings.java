package Misc;

/**
 * Created by Sinazk on 5/4/16.
 * Hi
 */

public class BodyStrings
{
	public static String BULLET_STRING = "BULLET";
	public static String ENEMY_STRING = "ENEMY";

	public static String BulletPistolString = "Pistol";
	public static String BulletRocketString = "Rocket";

	public static String EnemyPigeon = "Pigeon";

	public static String GroundString = "Ground";

	public static boolean isBullet(String s)
	{
		String tmp = getPartOf(s, 0);
		return tmp.equals(BULLET_STRING);
	}

	public static boolean isGround(String s)
	{
		return getPartOf(s, 0).equals(GroundString);
	}

	public static boolean isEnemy(String s)
	{
		return getPartOf(s, 0).equals(ENEMY_STRING);
	}

	public static String getPartOf(String s, int index)
	{
		String [] tmp = s.split(" ");
		return tmp[index];
	}
}
