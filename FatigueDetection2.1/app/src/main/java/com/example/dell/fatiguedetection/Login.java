package com.example.dell.fatiguedetection;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

    private EditText edit_username=null;
    private EditText edit_password=null;
    private Button btn_signin=null;
    private String username=null;
    private String password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Explode());

        edit_username=(EditText)findViewById(R.id.edit_username);
        edit_password=(EditText)findViewById(R.id.edit_password);
        btn_signin=(Button)findViewById(R.id.btn_signin);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=edit_username.getText().toString();
                password=edit_password.getText().toString();
                //此处应有验证账户及密码操作
                //check();
                if(true){//验证成功
                    startActivity(new Intent(Login.this,Home.class)
                            , ActivityOptions.makeSceneTransitionAnimation(Login.this).toBundle());
                }else{
                    Toast.makeText(Login.this,"Incorrect username or password.",Toast.LENGTH_LONG).show();
                }

            }
        });



    }

}
