/**
 * Created by abhrajit on 7/19/16.
 */
package com.example.android.tellajoke;

import android.app.Activity;
import android.content.Context;

public class CallJoke {
    Activity activity;
    EndpointsAsyncTask mEpas;

    public CallJoke(Context context,EndpointsAsyncTask epas) {

        activity = (Activity) context;
        mEpas=epas;

    }


    public void getJoke() {
        mEpas.execute(activity);
    }


}
