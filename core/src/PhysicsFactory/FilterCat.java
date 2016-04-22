package PhysicsFactory;

public class FilterCat 
{
	public static short CATEGORYBIT_WALL = 1;
	public static short CATEGORYBIT_CAR = 2;
	public static short CATEGORYBIT_WHEEL = 4;
	public static short CATEGORYBIT_COIN = 8;
	public static short CATEGORYBIT_KAPOOT = 16;
	public static short CATEGORYBIT_HAND = 32;
	public static short CATEGORYBIT_HUMAN = 64;
	public static short CATEGORYBIT_HEAD = 128;
	public static short CATEGORYBIT_CABIN = 256;
	public static short CATEGORYBIT_BACKWALL = 512;
	

	public static short MASKBITS_WALL = (short) (CATEGORYBIT_WALL + CATEGORYBIT_CAR + CATEGORYBIT_WHEEL);
	public static short MASKBITS_CAR = (short) +(CATEGORYBIT_WALL + CATEGORYBIT_HAND
			+ CATEGORYBIT_HUMAN + CATEGORYBIT_CAR + CATEGORYBIT_HEAD + CATEGORYBIT_BACKWALL);
			
			
			
	public static short MASKBITS_WHEEL = (short) (CATEGORYBIT_WALL
			+ CATEGORYBIT_KAPOOT + +CATEGORYBIT_HAND + CATEGORYBIT_HUMAN + CATEGORYBIT_HEAD);
	public static short MASKBITS_COIN = 0;
	public static short MASKBITS_KAPOOT = (short) (CATEGORYBIT_WHEEL);
	public static short MASKBITS_HAND = (short) (CATEGORYBIT_WALL + CATEGORYBIT_CABIN + CATEGORYBIT_WHEEL);
	public static short MASKBITS_HUMAN = (short) (CATEGORYBIT_WALL + CATEGORYBIT_CABIN
			+ CATEGORYBIT_CAR + CATEGORYBIT_WHEEL + CATEGORYBIT_HEAD + CATEGORYBIT_HUMAN);

	public static short MASKBITS_HEAD = (short) (CATEGORYBIT_WALL + CATEGORYBIT_CAR
			+ CATEGORYBIT_WHEEL + CATEGORYBIT_HEAD + CATEGORYBIT_HUMAN);

	public static short MASKBITS_CABIN = (short) (CATEGORYBIT_HUMAN);
	public static short MASKBITS_BACKWAAL = (short) (CATEGORYBIT_CAR);
}
