package dev.as.m.profilematchinglintasminat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginGuruBK extends AppCompatActivity {

    EditText etUsername, etPassword;
    ImageView imageShowPass;
    private Command command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_guru_bk);
        command = new Command(this);
        etUsername = findViewById(R.id.etUsernameLoginGuruBK);
        etPassword = findViewById(R.id.etPasswordLoginGuruBK);
        imageShowPass = findViewById(R.id.loginGuruBKShowPass);
        etPassword.setText("asmawati");
        etUsername.setText("asmawati");
    }

    int counter = 0;
    public void showOrHidePass(View view) {
        counter++;
        if(counter % 2 == 0){
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            etPassword.setSelection(etPassword.getText().length());
            imageShowPass.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_view));
        }else{
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            etPassword.setSelection(etPassword.getText().length());
            imageShowPass.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_view_on));
        }
    }

    public void loginGuruBK(View view) {
        String username = command.getText(etUsername);
        String password = command.getText(etPassword);
        if(!username.equals("") && !password.equals("")){
            new Connect("login", "select * from gurubk where username='"+username+"' and password='"+password+"'", LoginGuruBK.this)
                    .add("guruBK")
                    .addIntent(MenuGuruBK.class, true, "Login Berhasil");
        }else{
            command.setMessage("Data belum lengkap");
        }
    }
}
