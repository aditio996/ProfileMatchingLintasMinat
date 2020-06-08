package dev.as.m.profilematchinglintasminat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginSiswa extends AppCompatActivity {

    EditText etUsername, etPassword;
    ImageView imageShowPass;
    private Command command;
    TextView tvChange;
    private Spinner spPilihLevel;
    ArrayList<String> levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppObject.siswaOnLogin = null;
        AppObject.guruBK = null;
        setContentView(R.layout.activity_login_siswa);
        command = new Command(this);
        spPilihLevel = findViewById(R.id.pilihLevelLogin);
        layoutHiding = findViewById(R.id.layoutHiding);
        etUsername = findViewById(R.id.etUsernameLoginSiswa);
        etPassword = findViewById(R.id.etPasswordLoginSiswa);
        imageShowPass = findViewById(R.id.loginSiswaShowPass);
        tvChange = findViewById(R.id.tvChangeFormLogin);
        levels = new ArrayList<>();
        levels.add("- Pilih Level -");
        levels.add("Siswa");
        levels.add("BK");
        levels.add("Guru");
        levels.add("Admin");
        spPilihLevel.setAdapter(new ArrayAdapter<>(this, R.layout.simple_list, levels));
        //iLoginAs("BK");
    }

    private void iLoginAs(String actor){
        switch (actor){
            case "admin":{
                //forAdmin
                etUsername.setText("admin");
                etPassword.setText("admin");
                spPilihLevel.setSelection(4);
                break;
            }
            case "guru":{
                //forGuru
                etUsername.setText("elfawati");
                etPassword.setText("sma4ips1");
                spPilihLevel.setSelection(3);
                break;
            }
            case "BK":{
                //forBK
                etUsername.setText("asmawati");
                etPassword.setText("asmawati");
                spPilihLevel.setSelection(2);
                break;
            }
            case "siswa":{
                //forSiswa
                etUsername.setText("108732");
                etPassword.setText("690sma4");
                spPilihLevel.setSelection(1);
                break;
            }
        }
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

    public void toLoginGuruBK(View view) {
        command.move(LoginGuruBK.class, false);
    }

    public void loginSiswa(View view) {
        String username = command.getText(etUsername);
        String password = command.getText(etPassword);
        if(!username.equals("") && !password.equals("")){
            if(spPilihLevel.getSelectedItemPosition() != 0) {
                if(spPilihLevel.getSelectedItem().toString().equalsIgnoreCase("Siswa")) {
                    new Connect("login", "select * from siswa where nis='" + username + "' and password='" + password + "'", LoginSiswa.this)
                            .add("siswaOnLogin")
                            .addIntent(MenuSiswa.class, true, "Login Berhasil");
                }else if(spPilihLevel.getSelectedItem().toString().equalsIgnoreCase("BK")) {
                    new Connect("login", "select * from gurubk where username='"+username+"' and password='"+password+"'", LoginSiswa.this)
                            .add("guruBK")
                            .addIntent(MenuGuruBK.class, true, "Login Berhasil");
                }else if(spPilihLevel.getSelectedItem().toString().equalsIgnoreCase("Guru")) {
                    new Connect("login", "select * from guru where username='"+username+"' and password='"+password+"'", LoginSiswa.this)
                            .add("guruOnLogin")
                            .addIntent(MenuGuru.class, true, "Login Berhasil");
                }else if(spPilihLevel.getSelectedItem().toString().equalsIgnoreCase("Admin")) {
                    new Connect("login", "select * from admin where username='"+username+"' and password='"+password+"'", LoginSiswa.this)
                            .add("admin")
                            .addIntent(MenuAdmin.class, true, "Login Berhasil");
                }
            }else{
                command.setMessage("Silahkan pilih level login terlebih dahulu");
            }
        }else{
            command.setMessage("Data belum lengkap");
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("Keluar Aplikasi")
                .setMessage("Anda Yakin Ingin Keluar Aplikasi?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppObject.siswaOnLogin = null;
                        System.exit(0);
                    }
                })
                .setNegativeButton("Tidak", null)
                ;
        dialog.show();
    }

    private EditText etIPAddress;
    private LinearLayout layoutHiding;
    private int countOfChange = 0;
    public void changeIPLahBro(View view) {
        countOfChange++;
        if(countOfChange > 10){
            command.setMessage("IP saat ini = "+AppObject.ADDRESS_PHP, Toast.LENGTH_LONG);
            etIPAddress = findViewById(R.id.etIpAddress);
            layoutHiding.setVisibility(View.VISIBLE);
        }
    }

    public void changeIPLahBro2(View view) {
        if(etIPAddress.getText().toString().equals("")){
            command.setMessage("IP tidak terganti, IP saat ini = "+AppObject.ADDRESS_PHP, Toast.LENGTH_LONG);
        }else {
            AppObject.ADDRESS_PHP = "http://" + etIPAddress.getText().toString();
            command.setMessage("IP telah diganti menjadi = "+AppObject.ADDRESS_PHP, Toast.LENGTH_LONG);
        }
        countOfChange = 0;
        layoutHiding.setVisibility(View.GONE);
        etIPAddress.setText("");
    }

    public void resetIPLahBro(View view) {
        AppObject.ADDRESS_PHP = AppObject.DEFAULT_ADDRESS_PHP;
        countOfChange = 0;
        command.setMessage("IP telah direset menjadi = "+AppObject.ADDRESS_PHP, Toast.LENGTH_LONG);
        layoutHiding.setVisibility(View.GONE);
        etIPAddress.setText("");
    }
}
