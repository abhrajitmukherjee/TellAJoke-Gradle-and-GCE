package com.example.android.tellajoke;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GceUnitTest extends ActivityInstrumentationTestCase2<MainActivity> {
    MainActivity mActivity;

    public GceUnitTest(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
      }

    public void testCallBack() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);
        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EndpointsAsyncTask task = (EndpointsAsyncTask)new EndpointsAsyncTask(new EndpointsAsyncTask.Receiver<BusinessRules>() {
                        @Override
                        public void onReceiveResult(
                                BusinessRules rules, Exception e) {
                            assertNotNull(rules);
                            assertNull(e);
                            signal.countDown();// notify the count downlatch
                        }
                    });
                    task.start(mActivity.getApplicationContext());
                } catch (Exception e) {
                    Log.e(TAG, "ERROR", e);
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

    @Test
    public void verifyAsyncTask() throws Throwable {
        assertTrue("Faiked",true);

        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
                    new EndpointsAsyncTask().execute();

                } catch (Throwable e) {

                }

            }


        });



    }


}