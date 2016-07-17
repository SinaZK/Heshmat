package GoogleServices;

public interface IGoogleServices 
{
	public void signIn();
	public void signOut();
	public void rateGame();
	public void submitScore(int terrainID, long score);
	public void showScores();
	public void showTerrainScore(int terrainID);
	public boolean isSignedIn();
	public boolean isHaveLuckyPatcher();
	public void makeToastShorts(String s);
	public void makeToastLong(String s);
	public void Countly(String name);
	public void disableAds();
	public void enableAds();
	
	public void changeLayoutToGDX();
	public void changeLayoutToRawAndroid();
	
	public void showTapsell();
	public void tapSellGivenPurchaseFlowDone();
	public boolean tapSellIsSthPurchased();
	public int tapSellGetPurchasedID();

	public void loadVDO();//not used
	public boolean haveVDO();
	public void playVDO();
	public void tapsellCheckVideo();
	public int getAward();
	public void consumeAward();
}
