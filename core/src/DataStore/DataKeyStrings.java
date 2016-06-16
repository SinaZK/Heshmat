package DataStore;

public class DataKeyStrings 
{
	public static String GameStatData = "GameStatData";

	public static String [] CarStatData = {"Car0","Car1","Car2","Car3","Car4","Car5","Car6","Car7","Car8","Car9","Car10", "Car11",
			"Car12","Car13","Car14","Car15","Car16","Car17","Car18","Car19","Car20","Car21","Car22","Car23","Car24","Car25","Car26",
			"Car27","Car28","Car29","Car30","Car31","Car32","Car33","Car34","Car35","Car36","Car37","Car38","Car39","Car40","Car41",
			"Car42","Car43","Car44","Car45","Car46","Car47","Car48","Car49","Car50"};

	public static String [] GunStatData = {"Gun0","Gun1","Gun2","Gun3","Gun4","Gun5","Gun6","Gun7","Gun8","Gun9",
			"Gun10","Gun11", "Gun12", "Gun13", "Gun14", "Gun15", "Gun16", "Gun17", "Gun18", "Gun19", "Gun20",
			"Gun20","Gun21", "Gun22", "Gun23", "Gun24", "Gun25", "Gun26", "Gun27", "Gun28", "Gun29", "Gun30"};

	public static String [] LVLPackStatData = {"LVLPack0","LVLPack1", "LVLPack2", "LVLPack3", "LVLPack4", "LVLPack5", "LVLPack6", "LVLPack7", "LVLPack8", "LVLPack9",
			"LVLPack10", "LVLPack11", "LVLPack12", "LVLPack13", "LVLPack14", "LVLPack15", "LVLPack16", "LVLPack17", "LVLPack18", "LVLPack19", "LVLPack20"};
	
	public static String HumanStatData = "Human";
	public static String PlayerStatData = "PlayerProfile";
	
	public static String SelectorStatData = "Selector";
	public static String SettingStatData  = "Setting";

	public static String getLevelFromPack(int levelPack, int levelID)
	{
		return "Pack=" + levelPack + "LVLID=" + levelID;
	}
}
