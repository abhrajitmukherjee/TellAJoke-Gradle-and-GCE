package com.example.android.tellajoke;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.concurrent.CountDownLatch;


public class GceUnitTest extends ActivityInstrumentationTestCase2<MainActivity> {
    MainActivity mActivity;
    final String LOG_TAG;

    public GceUnitTest(){
        super(MainActivity.class);
        LOG_TAG=this.getClass().getSimpleName();

    }


    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
      }

    @android.support.test.annotation.UiThreadTest
    public void testCallBack() throws Throwable {


        final CountDownLatch signal = new CountDownLatch(1);
        // Execute the async task on the UI thread! THIS IS KEY!
       runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EndpointsAsyncTask esa=new EndpointsAsyncTask() {
                        @Override
                        public void receiveData(String result, Context context) {
                            Log.d(LOG_TAG,result);
                            System.out.println(result);
                            assertNotNull("Joke is Null",result);
                            assertNotSame("Joke is empty",result,"");
                            assertFalse("Unable to Connect to GCE",result.contains("failed to connect to"));

                            signal.countDown();
                        }

                    };

                  esa.execute(mActivity);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "ERROR", e);
                    fail();
                }
            }
        });
        try {

            signal.await();// wait for callback
        } catch (InterruptedException e1) {
            fail();
            e1.printStackTrace();
        }
    }



}