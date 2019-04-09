package com.example.dell.fatiguedetection;

import android.app.ActivityOptions;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Detection extends AppCompatActivity {

    enum status {awake,tired}
    private Boolean vibrationFlag;
    private Boolean soundFlag;
    private Boolean uploadFlag;
    private ConstraintLayout layout;
    private ImageView back;
    private TextView text;
    private Handler handler;
    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Explode());
       // btn_back=(Button)findViewById(R.id.btn_back);

        back=(ImageView)findViewById(R.id.back);
        layout=(ConstraintLayout)findViewById(R.id.activity_detection);
        text=(TextView)findViewById(R.id.textView);
        vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        layout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        text.setText(R.string.wake);
                        break;
                    case 2:
                        layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        text.setText(R.string.fatigue);
                        vibrator.vibrate(50000);
                        break;
                }
            }
;       };

        ChangeStatus status=new ChangeStatus();
        status.start();

        Intent intent=getIntent();
        vibrationFlag=intent.getBooleanExtra("vibration",false);
        soundFlag=intent.getBooleanExtra("sound",false);
        uploadFlag=intent.getBooleanExtra("upload",false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Detection.this,Home.class),
                        ActivityOptions.makeSceneTransitionAnimation(Detection.this).toBundle());
            }
        });
    }

    public class ChangeStatus extends Thread {

        @Override
        public void run(){//加入收到的判断，msg.what==1显示清醒状态，meg.what==2显示疲劳状态并震动
            super.run();
            Message msg=Message.obtain();
            msg.what=1;
            handler.sendMessage(msg);


        }
    }


}


