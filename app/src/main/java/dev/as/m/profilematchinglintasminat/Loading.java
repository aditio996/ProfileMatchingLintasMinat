package dev.as.m.profilematchinglintasminat;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Loading extends Activity {

    private Command command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        command = new Command(this);
        setContentView(R.layout.activity_loading);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        command.waitAndMove(LoginSiswa.class, true, 2);
    }

    @Override
    public void onBackPressed() {
        command.setMessage("On loading...\nPlease wait!");
    }
}
