package com.example.android.tellajoke;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.jokelibrary.JokeReveal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv=(TextView)findViewById(R.id.app_header);
        tv.setText(getString(R.string.header_text));



    }

    public void jokeButtonClick(View v){
        CallJoke calljoke;


        final ProgressBar spinner=(ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        EndpointsAsyncTask epas=new EndpointsAsyncTask() {
            @Override
            public void receiveData(String result,Context context) {
                Intent sendIntent = new Intent(context, JokeReveal.class);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result);
                sendIntent.setType("text/plain");
                spinner.setVisibility(View.GONE);
                startActivity(sendIntent);
            }
        };
        calljoke=new CallJoke(this,epas);


        calljoke.getJoke();
    }


}

