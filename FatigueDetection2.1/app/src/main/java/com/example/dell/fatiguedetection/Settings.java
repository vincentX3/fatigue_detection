package com.example.dell.fatiguedetection;

import android.app.ActivityOptions;
import android.app.Service;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class Settings extends AppCompatActivity {

    private Toolbar toolbar_settings;
    private Switch btn_vibration;
    private Switch btn_sound;
    private Switch btn_upload;
    private Vibrator vibrator;
    private Intent intent;
    private Boolean vibrationFlag;
    private Boolean soundFlag;
    private Boolean uploadFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Slide());
        toolbar_settings=(Toolbar)findViewById(R.id.toolbar_settings);
        btn_vibration=(Switch)findViewById(R.id.btn_vibration);
        btn_sound=(Switch)findViewById(R.id.btn_sound);
        btn_upload=(Switch)findViewById(R.id.btn_upload);
        vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        intent=new Intent(Settings.this,Home.class);
        vibrationFlag=false;
        soundFlag=false;
        uploadFlag=false;

       toolbar_settings.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(Settings.this).toBundle());
            }
        });

        btn_vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    vibrator.vibrate(1000);
                    vibrationFlag=true;
                    intent.putExtra("vibration",vibrationFlag);
                }else{
                    vibrationFlag=false;
                    intent.putExtra("vibration",vibrationFlag);
                }
            }
        });

        btn_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    vibrator.vibrate(500);
                    soundFlag=true;
                    intent.putExtra("sound",soundFlag);
                }else{
                    soundFlag=false;
                    intent.putExtra("sound",soundFlag);
                }
            }
        });

        btn_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    vibrator.vibrate(500);
                    uploadFlag=true;
                    intent.putExtra("upload",uploadFlag);
                }else{
                    uploadFlag=false;
                    intent.putExtra("upload",uploadFlag);
                }
            }
        });
    }
}
