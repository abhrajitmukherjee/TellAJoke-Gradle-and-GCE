package com.example.android.jokelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeReveal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_reveal);
        Intent intent=getIntent();
        String joke=intent.getStringExtra(Intent.EXTRA_TEXT);
        TextView tv=(TextView)findViewById(R.id.txt_joke);
        tv.setText(joke);
    }
}
