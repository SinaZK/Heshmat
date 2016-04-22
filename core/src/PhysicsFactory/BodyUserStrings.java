package PhysicsFactory;

public class BodyUserStrings 
{
	//hichkudum gheire wheel nabayad ba w shooroo she!
	public static String GroundString = "Ground";
	public static String CarBodyString = "Car";
	public static String CarCeilBodyString = "Ceil";
	public static String CarCabinBodyString = "Cabin";
	public static String WheelBodyString = "w";
	public static String HumanHeadString = "Head";
	public static String HumanHandString = "Hand";
	public static String HumanLegString = "Hand";
	public static String HumanNeckString = "Head";
	public static String HumanMainBodyxString = "Head";
	public static String MAGMABodyString = "Magma";
	
	public static boolean isCar(String s)
	{
		return (s == CarBodyString) || (s == CarCeilBodyString) || (s.charAt(0) == 'w');
	}
	
	public static boolean isHuman(String s)
	{
		return (s == HumanHandString) || (s == HumanHeadString) || (s == HumanLegString) || (s == HumanMainBodyxString) 
				|| (s == HumanNeckString);
	}
}
