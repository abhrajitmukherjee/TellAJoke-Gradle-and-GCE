/**
 * Created by abhrajit on 7/19/16.
 */
package com.example.android.tellajoke;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class CallJoke {
    InterstitialAd mInterstitialAd;
    Activity activity;
    EndpointsAsyncTask mEpas;

    public CallJoke(Context context, EndpointsAsyncTask epas) {

        activity = (Activity) context;
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(activity.getString(R.string.banner_ad_unit_id));
        mEpas=epas;

    }


    public void getJoke() {
        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                loadJoke();
            }

            @Override
            public void onAdClosed() {
                mEpas.execute(activity);
            }
        });

    }

    private void loadJoke() {


        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {

            mEpas.execute(activity);
        }

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(activity.getString(R.string.test_device_id))
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}
