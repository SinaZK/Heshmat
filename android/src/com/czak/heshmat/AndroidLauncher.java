package com.czak.heshmat;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

import GoogleServices.IGoogleServices;
import Misc.Log;
import heshmat.MainActivity;
import io.cava.android.sdk.Cavaio;
import ir.adad.client.Adad;
import ir.tapsell.tapsellvideosdk.developer.CheckCtaAvailabilityResponseHandler;
import ir.tapsell.tapsellvideosdk.developer.DeveloperInterface;
import src.com.Czak.Android.MarketStrings.MarketStrings;
import src.com.Czak.AndroidPermissions.PermissionManager;
import src.com.Czak.NativeMultimedia.NativeMultimedia;
import src.com.Czak.Security.Security;
import src.com.NivadBilling.NivadPurchase;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices
{

	public static MarketStrings.Market market = MarketStrings.Market.CafeBazaar;
	private final static int REQUEST_CODE_UNUSED = 9002;
	public Security security;

	NivadPurchase nivadPurchase;

	private final static int requestCode = 1;

	RelativeLayout layout;
	NativeMultimedia nativeMultimedia;
	PermissionManager permissionManager;

	GameHelper gameHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Cavaio.sharedInstance().init(this, "http://my.cavaio.com", "fe4c1a01e03e3cbb5ff22b79b936ddbe8162b6ac");

		nativeMultimedia = new NativeMultimedia(this);

		nivadPurchase = new NivadPurchase();
		nivadPurchase.onCreate(this);


		initAdad();
		initTapsellVideo();
		initGameHelper();

		permissionManager = new PermissionManager(this);
		permissionManager.checkPermission();


//			purchase.init();
// 			purchase = new PurchaseHelperAndroid(this);
	}

	public void initAdad()
	{
		Adad.initialize(getApplicationContext());

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		layout = new RelativeLayout(this);


		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		View gameView = initializeForView(new MainActivity(this, nivadPurchase, nativeMultimedia), config);
		layout.addView(gameView);


		Adad.enableBannerAds();

		getLayoutInflater().inflate(R.layout.sizak, layout);

		setContentView(layout);
	}

	@Override
	public void signIn()
	{
		Gdx.app.log("MainActivity Android", "Try to login");
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (Exception e)
		{
			Gdx.app.log("MainActivity Android", "Log in failed: " + e.getMessage() + ".");
		}

	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		} catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void submitScore(int wave)
	{
		if(isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_wave), wave);
		}
	}

	@Override
	public void showWaveScores()
	{
		if(isSignedIn() == true)
		{
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
					getString(R.string.leaderboard_wave)), requestCode);
		} else
		{
			signIn();
		}

	}
	@Override
	public void rateGame()
	{
		String str = "";
		String mode = Intent.ACTION_EDIT;

		switch (market)
		{


			case CafeBazaar:
				str = "http://cafebazaar.ir/app/com.czak.heshmat/?l=fa";
				break;

			default:
				break;
		}

		if(str.equals(""))
			return;


		Intent nazar = new Intent(mode,
				Uri.parse(str));

		try
		{
			startActivity(nazar);
		} catch (Exception e)
		{
			Log.e("Tag", e.toString());
		}
	}

	@Override
	public void showTerrainScore(int terrainID)
	{
	}

	@Override
	public boolean isSignedIn()
	{
		return gameHelper.isSignedIn();
	}

	@Override
	public boolean isHaveLuckyPatcher()
	{
		return false;
	}

	@Override
	public void makeToastShorts(String s)
	{
		final String fs = s;
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(getApplicationContext(), fs, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void makeToastLong(String s)
	{
		final String fs = s;
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(getApplicationContext(), fs, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void Countly(String name)
	{

		Cavaio.sharedInstance().recordEvent(name);

	}

	@Override
	public void disableAds()
	{
		Adad.disableBannerAds();

	}

	@Override
	public void enableAds()
	{
		Adad.enableBannerAds();
	}

	@Override
	public void changeLayoutToGDX()
	{
//		 sTODO Auto-generated method stub
	}

	@Override
	public void changeLayoutToRawAndroid()
	{
		// sTODO Auto-generated method stub

	}

	@Override
	public void showTapsell()
	{
		// sTODO Auto-generated method stub

	}

	@Override
	public void tapSellGivenPurchaseFlowDone()
	{
		// sTODO Auto-generated method stub

	}

	@Override
	public boolean tapSellIsSthPurchased()
	{
		// sTODO Auto-generated method stub
		return false;
	}

	@Override
	public int tapSellGetPurchasedID()
	{
		return 0;
	}

	boolean isTapsellHaveVDO = false;

	@Override
	public boolean haveVDO()
	{
		return isTapsellHaveVDO;
	}

	@Override
	public void playVDO()
	{

		try
		{
			DeveloperInterface.getInstance(getContext())
					.showNewVideo(this,
							DeveloperInterface.TAPSELL_DIRECT_ADD_REQUEST_CODE,
						DeveloperInterface.DEFAULT_MIN_AWARD,
							DeveloperInterface.VideoPlay_TYPE_NON_SKIPPABLE);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		isTapsellHaveVDO = false;
	}

	@Override
	public void loadVDO()
	{
	}

	public void initTapsellVideo()
	{
		DeveloperInterface.getInstance(getContext()).init("iomgabikbnktsrplsranehmbihtdtemkqqrgflkkbfdqmggcfqrleqkdmorjmhphoiddsf", this);
	}

	@Override
	public void tapsellCheckVideo()
	{
		try
		{

			DeveloperInterface.getInstance(this).setBackDisability(true);
			Log.e("Tag", "checking for video");
			DeveloperInterface.getInstance(getContext())
					.checkCtaAvailability(
							this, DeveloperInterface.DEFAULT_MIN_AWARD,
							DeveloperInterface.VideoPlay_TYPE_NON_SKIPPABLE, new CheckCtaAvailabilityResponseHandler()
							{
								@Override
								public void onResponse(Boolean isConnected, Boolean isAvailable)
								{
									System.err.println(isConnected + " " + isAvailable);
									isTapsellHaveVDO = true;
								}
							});
		} catch (Exception e)
		{
//			makeToastLong(e.toString());
		}
	}


	@Override
	protected void onDestroy()
	{
		nivadPurchase.destroy();
//		purchase.dispose();
		super.onDestroy();
	}

	int videoAward = -1;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(nivadPurchase.nivadBilling.handleActivityResult(requestCode, resultCode, data))
			return;

		super.onActivityResult(requestCode, resultCode, data);

		gameHelper.onActivityResult(requestCode, resultCode, data);

//		if(purchase.mHelper != null)
//			purchase.mHelper.handleActivityResult(requestCode, resultCode, data);

		try
		{
			if(requestCode == DeveloperInterface.TAPSELL_DIRECT_ADD_REQUEST_CODE)
			{
				if(data.hasExtra(DeveloperInterface.TAPSELL_DIRECT_AVAILABLE_RESPONSE))
					isTapsellHaveVDO = true;

				videoAward = data.getIntExtra(DeveloperInterface.TAPSELL_DIRECT_AWARD_RESPONSE, -1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public int getAward()
	{
		return videoAward;
	}

	@Override
	public void consumeAward()
	{
		videoAward = -1;
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		Cavaio.sharedInstance().onStart();

		gameHelper.setMaxAutoSignInAttempts(1);
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		Cavaio.sharedInstance().onStop();
		gameHelper.onStop();
	}

	public void initGameHelper()
	{
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed()
			{
				Log.e("Tag", "Login Failed");
			}

			@Override
			public void onSignInSucceeded()
			{
				Log.e("Tag", "Login Succeed");
			}
		};

		gameHelper.setup(gameHelperListener);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if(permissionManager != null)
			permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}
