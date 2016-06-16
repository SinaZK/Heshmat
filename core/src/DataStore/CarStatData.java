package DataStore;

import Enums.Enums;

public class CarStatData
{
	public int engineLVL;
	public int hitPointLVL;
	public int [] selectedGunSLots = new int[10];
	public Enums.LOCKSTAT [][] gunSlotLockStats = new Enums.LOCKSTAT[10][4];

	public Enums.LOCKSTAT lockStat;
 }
