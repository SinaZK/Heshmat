package src.com.Czak.Security;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;

import src.com.Czak.Android.MarketStrings.MarketStrings;
import com.czak.heshmat.AndroidLauncher;


public class Security 
{
	public boolean checkLuckyPatcher(AndroidLauncher act)
	{
		int a = 1;
		int b = a + 1;
		if(b == 2)
			return false;

		if(AndroidLauncher.market != MarketStrings.Market.CafeBazaar)
			return false;

		if (packageExists(act, "com.dimonvideo.luckypatcher"))
		{
			return true;
		}

		if (packageExists(act, "com.chelpus.lackypatch"))
		{
			return true;
		}

		if (packageExists(act, "com.forpda.lp"))
		{
			return true;
		}

		return false;
	}

	private boolean packageExists(AndroidLauncher act, final String packageName)
	{
		try
		{
			ApplicationInfo info = act.getPackageManager().getApplicationInfo(packageName, 0);

			if (info == null)
			{
				// No need really to test for null, if the package does not
				// exist it will really rise an exception. but in case Google
				// changes the API in the future lets be safe and test it
				return false;
			}

			return true;
		}
		catch (Exception ex)
		{
			// If we get here only means the Package does not exist

			//	    	Log.e("Tag", "ERROR = " + ex.toString());
		}

		return false;
	}

	public static final int VALID = 0;

	public static final int INVALID = 1;
	private static final String SIGNATUREDebug = "nxE0z5d1Pyu1PSKS4g/dR7+ASDo=";
	private static final String SIGNATURESigned = "VWO8pRzLsZOPWgRL1OPxvl6xV2M=";

	public static int checkAppSignature(AndroidLauncher act, Context context) {

		try {

			PackageInfo packageInfo = context.getPackageManager()

					.getPackageInfo(context.getPackageName(),

							PackageManager.GET_SIGNATURES);

			for (Signature signature : packageInfo.signatures) 
			{
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				final String currentSignature = Base64.encodeToString(md.digest(), Base64.DEFAULT);

				if (SIGNATURESigned.trim().equals(currentSignature.trim()))
					return VALID;
				
				if (SIGNATUREDebug.trim().equals(currentSignature.trim()))
					return VALID;
			}

		} catch (Exception e) {

			//assumes an issue in checking signature., but we let the caller decide on what to do.

		}

		return INVALID;

	}
}
