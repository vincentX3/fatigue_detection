package com.example.dell.fatiguedetection;

import android.app.ActivityOptions;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    private Toolbar toolbar_home;
    private ImageView tb_setting;
    private Button btn_start;
    private Button btn_history;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private Intent intentToSettings;
    //private Intent intentToHistory;
    private Intent intentToDetection;
    private Boolean vibrationFlag;
    private Boolean soundFlag;
    private Boolean uploadFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        toolbar_home=(Toolbar)findViewById(R.id.toolbar_home);
        tb_setting=(ImageView)findViewById(R.id.tb_settings);
        btn_start=(Button)findViewById(R.id.btn_start);
        btn_history=(Button)findViewById(R.id.btn_history);
        bluetoothManager=(BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter=bluetoothManager.getAdapter();
        intentToSettings=new Intent(Home.this,Settings.class);
        intentToDetection=new Intent(Home.this,Detection.class);

        final Intent intent=getIntent();

        vibrationFlag=intent.getBooleanExtra("vibration",false);
        soundFlag=intent.getBooleanExtra("sound",false);
        uploadFlag=intent.getBooleanExtra("upload",false);

        initToolbar();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothAdapter==null){
                    Toast.makeText(Home.this,"Bluetooth unsupported.",Toast.LENGTH_SHORT).show();
                }
                if(bluetoothAdapter.isEnabled()){//Bluetooth is open.后续判断条件应改为是否绑定到设备
                    intentToDetection.putExtra("vibration",vibrationFlag);
                    intentToDetection.putExtra("sound",soundFlag);
                    intentToDetection.putExtra("upload",uploadFlag);
                    startActivity(intentToDetection,
                            ActivityOptions.makeSceneTransitionAnimation(Home.this).toBundle());

                }else{//Bluetooth is closed
                    Intent enabler=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enabler,1);
                    startActivity(new Intent(Home.this,BluetoothConnection.class),
                            ActivityOptions.makeSceneTransitionAnimation(Home.this).toBundle());
                }
            }
        });

        btn_history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(Home.this,History.class),
                        ActivityOptions.makeSceneTransitionAnimation(Home.this).toBundle());
            }
        });


    }

    private void initToolbar(){
        toolbar_home.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this,"left button",Toast.LENGTH_LONG).show();
            }
        });

        tb_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentToSettings,
                        ActivityOptions.makeSceneTransitionAnimation(Home.this).toBundle());
            }
        });
    }


}
