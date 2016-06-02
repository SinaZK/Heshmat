package Misc;

/**
 * Created by Sinazk on 5/4/16.
 * Hi
 */

public class BodyStrings
{
	public static String CAR_STRING = "CAR";
	public static String BULLET_STRING = "BULLET";
	public static String ENEMY_STRING = "ENEMY";

	public static String HUMAN_STRING = "HUMAN";
	public static String Human_HeadString = "Head";
	public static String Human_HandString = "Hand";
	public static String Human_LegString = "Hand";
	public static String Human_NeckString = "Head";
	public static String Human_MainBodyString = "Head";

	public static String BulletPistolString = "Pistol";
	public static String BulletRocketString = "Rocket";

	public static String EnemyPigeon = "Pigeon";

	public static String FINISH_MODE_STRING = "FINISH";
	public static String GroundString = "Ground";

	public static boolean isHuman(String s)
	{
		return getPartOf(s, 0).equals(HUMAN_STRING);
	}

	public static boolean isBullet(String s)
	{
		return getPartOf(s, 0).equals(BULLET_STRING);
	}

	public static boolean isCar(String s)
	{
		return getPartOf(s, 0).equals(CAR_STRING);
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
