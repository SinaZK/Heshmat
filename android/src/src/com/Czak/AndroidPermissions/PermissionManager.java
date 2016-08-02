package src.com.Czak.AndroidPermissions;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.czak.heshmat.AndroidLauncher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinazk on 8/2/16.
 * 23:58
 * <p/>
 * ask user to give the permissions on android 6.0 and higher
 */
public class PermissionManager
{
	AndroidLauncher act;
	public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
	String TAG = "PermissionManager";

	final private int PERMISSION_PHONE_STATE = 123;

	public PermissionManager(AndroidLauncher androidLauncher)
	{
		act = androidLauncher;
	}

	public void checkPermission()
	{
		if(ContextCompat.checkSelfPermission(act, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
		{
			ActivityCompat.requestPermissions(act,
					new String[]{
							Manifest.permission.READ_PHONE_STATE
					}, PERMISSION_PHONE_STATE);
		}
	}

	public boolean checkAndRequestPermissions()
	{
//		int permissionSendMessage = ContextCompat.checkSelfPermission(act,
//				Manifest.permission.SEND_SMS);
//		int locationPermission = ContextCompat.checkSelfPermission(act, Manifest.permission.READ_PHONE_STATE);

		int phoneStatePermission = ContextCompat.checkSelfPermission(act, Manifest.permission.READ_PHONE_STATE);


		List<String> listPermissionsNeeded = new ArrayList<>();
		if(phoneStatePermission != PackageManager.PERMISSION_GRANTED)
		{
			listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
		}

		if(!listPermissionsNeeded.isEmpty())
		{
			ActivityCompat.requestPermissions(act, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
			return false;
		}
		return true;
	}

	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		switch (requestCode)
		{
			case PERMISSION_PHONE_STATE:
			{
				// If request is cancelled, the result arrays are empty.
				if(grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{

//					act.makeToastShorts("GRANTED");
					// permission was granted, yay! Do the
				}
				else
				{
//					act.makeToastShorts("DENIED");
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}
		}
	}
}
