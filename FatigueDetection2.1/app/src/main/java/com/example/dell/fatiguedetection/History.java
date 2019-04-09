package com.example.dell.fatiguedetection;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class History extends AppCompatActivity {

    Toolbar toolbar_history;
    CardView card1;
    CardView card2;
    CardView card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());

        toolbar_history=(Toolbar)findViewById(R.id.toolbar_history);
        card1=(CardView)findViewById(R.id.card1);
        card2=(CardView)findViewById(R.id.card2);
        card3=(CardView)findViewById(R.id.card3);

        card1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(History.this,Details.class),
                        ActivityOptions.makeSceneTransitionAnimation(History.this).toBundle());
            }
        });

        card2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(History.this,Details.class),
                        ActivityOptions.makeSceneTransitionAnimation(History.this).toBundle());
            }
        });

        card3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(History.this,Details.class),
                        ActivityOptions.makeSceneTransitionAnimation(History.this).toBundle());
            }
        });


        toolbar_history.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(History.this,Home.class),
                        ActivityOptions.makeSceneTransitionAnimation(History.this).toBundle());
            }
        });
    }
}
