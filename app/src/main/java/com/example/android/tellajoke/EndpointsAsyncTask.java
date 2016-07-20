package com.example.android.tellajoke;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.jokelibrary.*;
import com.example.jokegce.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


abstract class EndpointsAsyncTask extends AsyncTask<Context, Void, String> implements CallbackReceiver {

    private MyApi myApiService = null;
    private Context context;
    private ProgressBar spinner;

    @Override
    public abstract void receiveData(String result, Context context);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }

    @Override
    protected String doInBackground(Context... params) {
        context = params[0];

        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(BuildConfig.LocalServer)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
//Use the code below in case you deploy to GCE web server the setRootUrl should be of the form https://yourprojectid.appspot.com/_ah/api/
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
//                    .setRootUrl(BuildConfig.BaseEndPoint);

            myApiService = builder.build();
        }


        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent sendIntent = new Intent(context, JokeReveal.class);
        sendIntent.putExtra(Intent.EXTRA_TEXT, result);
        sendIntent.setType("text/plain");
        spinner= (ProgressBar) ((Activity) context).findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        context.startActivity(sendIntent);


    }
}