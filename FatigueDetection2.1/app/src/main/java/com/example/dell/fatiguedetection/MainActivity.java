package com.example.dell.fatiguedetection;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView welcomeImg=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ActionBar actionBar=getActionBar();
        //actionBar.hide();

        getWindow().setExitTransition(new Explode());
        welcomeImg=(ImageView)this.findViewById(R.id.welcome_img);
        AlphaAnimation anima=new AlphaAnimation(1.0f,0.0f);
        anima.setDuration(3000);
        anima.setFillAfter(true);
        welcomeImg.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());
    }

    private class AnimationImpl implements AnimationListener{
        @Override
        public void onAnimationStart(Animation animation){
            welcomeImg.setBackgroundResource(R.drawable.openscreen_title);
        }

        @Override
        public void onAnimationEnd(Animation animation){
            skip();
        }

        @Override
        public void onAnimationRepeat(Animation animation){

        }

    }

    private void skip(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        },100);



    }
}
