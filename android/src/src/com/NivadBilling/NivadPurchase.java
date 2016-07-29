package src.com.NivadBilling;

import android.content.Intent;

import com.czak.heshmat.AndroidLauncher;

import java.util.ArrayList;

import PurchaseIAB.purchaseIAB;
import io.nivad.iab.BillingProcessor;
import io.nivad.iab.TransactionDetails;

/**
 * Created by sinazk on 7/22/16.
 * 06:30
 */
public class NivadPurchase implements BillingProcessor.IBillingHandler, purchaseIAB.IABInterface
{
	String base64EncodedPublicKeyBazaar = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDDE4EbspPoDvv0deuSZdr6hTx8MVGS6UjktOtXD4mvBpOmHpIroV67o5UG2iCbT0SGhq3b/5owMu5GgGs3W2tTXEXRQq+pkiO2VajHKhWHRnDN1t+SDQ4SvZ0Q7ET72iHm1HzvDzUz8JAYodlYbe+ZN5CIlHUJROjbMWnXyQjap0ePZlfYghOPOk/PgYLyFI7sPjaEnLRPOoOja1KTP2t062WLf9LnOymy/2+jc80CAwEAAQ==";

	public static final String [] SKU_STRINGS_BAZAAR = {"coin1", "coin2", "coin3", "coin4", "coin5", "coin6", "coin7", "coin8", "coin9", "coin10"};

	AndroidLauncher act;
	public BillingProcessor nivadBilling;

	public boolean isPurchasing = false;
	public boolean isSTHPurchased = false;
	int purchaseID = -1;

	public void onCreate(AndroidLauncher androidLauncher)
	{
		nivadBilling = new BillingProcessor(androidLauncher,
				base64EncodedPublicKeyBazaar,
				"386d04a6-5cce-4e5e-b566-f326c3bfc922",
				"Xd5cOHJg2z5L4VtIpsCv3HDT9TIisekVhzWc0lmBOMQVyzgeE9BEm6tM9nDFdhDR", this);

		act = androidLauncher;
	}

	@Override
	public void onProductPurchased(String s, TransactionDetails transactionDetails)
	{
		act.Countly("Nivad DONE : " + SKU_STRINGS_BAZAAR[purchaseID]);
		isSTHPurchased = true;
		nivadBilling.consumePurchase(SKU_STRINGS_BAZAAR[purchaseID]);
	}

	@Override
	public void makePurchase(int id)
	{
		isPurchasing = true;
		purchaseID = id;

		act.Countly("Nivad Try : " + SKU_STRINGS_BAZAAR[purchaseID]);
		nivadBilling.purchase(act, SKU_STRINGS_BAZAAR[id]);
	}

	@Override
	public void givenPurchaseFlowDone()
	{
		isPurchasing = false;
		isSTHPurchased = false;
	}

	@Override
	public boolean isSthPurchased()
	{
		return isSTHPurchased;
	}

	@Override
	public int getPurchasedID()
	{
		return purchaseID;
	}

	@Override
	public ArrayList<Integer> getPrices()
	{
		return null;
	}

	@Override
	public boolean isReadyForPurchase()
	{
		return true;
	}

	public void destroy()
	{
		if (nivadBilling != null)
			nivadBilling.release();
	}

	@Override
	public void onPurchaseHistoryRestored(){}

	@Override
	public void onBillingError(int i, Throwable throwable){}

	@Override
	public void onBillingInitialized(){}

	@Override
	public void init()
	{
		//not for nivad
	}
}
