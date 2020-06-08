package dev.as.m.profilematchinglintasminat;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class TambahDataGuru extends AppCompatActivity {

    private Command command;
    private EditText etNamaGuru, etPassword, etPassword2, etUsername;
    private TextView judulManage;
    private Button btnManage;
    private RadioButton rbBK, rbWK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_tambah_data_guru);
        etNamaGuru= findViewById(R.id.etNamaGuru);
        etUsername = findViewById(R.id.etUsernameGuru);
        etPassword = findViewById(R.id.etPasswordGuru);
        etPassword2 = findViewById(R.id.etPasswordGuru2);
        judulManage = findViewById(R.id.judulManageDataGuru);
        btnManage = findViewById(R.id.btnManageGuru);
        rbBK = findViewById(R.id.rbBK);
        rbWK = findViewById(R.id.rbWK);
        if(Command.MANAGE == Command.UPDATE) {
            setUpdate();
        }else {
            setInsert();
        }
    }

    private void setInsert() {
        judulManage.setText("Tambah Data Guru");
        btnManage.setText("Tambah Data");
        rbBK.setChecked(true);
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String namaGuru = command.getText(etNamaGuru);
                final String username = command.getText(etUsername);
                final String password = command.getText(etPassword);
                String password2 = command.getText(etPassword2);
                if(!username.equals("") && !password.equals("") && !namaGuru.equals("")) {
                    if(password.equals(password2)){
                        if(rbBK.isChecked()){
                            new Connect("insert", "insert into gurubk " +
                                    "(namaGuruBK, username, password) values " +
                                    "('" + namaGuru + "', '" + username+ "', '"+password+"')", TambahDataGuru.this)
                                    .addIntent(LihatDataGuru.class, true, "Data Guru BK " + namaGuru + " Berhasil Ditambahkan")
                                    .add("guruBK");
                        }else if(rbWK.isChecked()){
                            AlertDialog.Builder b = new AlertDialog.Builder(TambahDataGuru.this);
                            b.setTitle("Pilih Kelas");
                            final String[] kelas = new String[AppObject.kelas.length()];
                            for(int i=0;i<AppObject.kelas.length();i++){
                                kelas[i] = command.get("kelas", i, "kelas");
                            }
                            b.setItems(kelas, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    add(kelas[which]);
                                }
                                void add(String kelas){
                                    new Connect("insert", "insert into guru " +
                                            "(namaGuru, username, password, kelas) values " +
                                            "('" + namaGuru + "', '" + username+ "', '"+password+"', '"+kelas+"')", TambahDataGuru.this)
                                            .addIntent(LihatDataGuru.class, true, "Data Guru " + namaGuru + " Berhasil Ditambahkan")
                                            .add("guru");
                                }

                            });
                            b.show();
                        }
                    }else {
                        command.setMessage("Password tidak cocok");
                    }
                }else {
                    command.setMessage("Data Belum Lengkap");
                }
            }
        });
    }

    private void setUpdate(){
        final String kd = getIntent().getExtras().getString("kode");
        judulManage.setText("Update Data Guru");
        btnManage.setText("Update Data");
        if(!Command.SELECTED_GURU.equals("guruBK")){
            rbWK.setChecked(true);
            etNamaGuru.setText(command.getById(kd, "guru", "namaGuru"));
            etUsername.setText(command.getById(kd, "guru", "username"));
            etPassword.setText(command.getById(kd, "guru", "password"));
        }else {
            rbBK.setChecked(true);
            etNamaGuru.setText(command.getById(kd, "guruBK", "namaGuruBK"));
            etUsername.setText(command.getById(kd, "guruBK", "username"));
            etPassword.setText(command.getById(kd, "guruBK", "password"));
        }
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String namaGuru = command.getText(etNamaGuru);
                final String username = command.getText(etUsername);
                final String password = command.getText(etPassword);
                String password2 = command.getText(etPassword2);
                if(!username.equals("") && !password.equals("") && !namaGuru.equals("")) {
                    if(password.equals(password2)){
                        if(rbBK.isChecked()){
                            new Connect("update", "update gurubk set " +
                                    "namaGuruBK='"+namaGuru+"'," +
                                    "username='"+username+"'," +
                                    "password='"+password+"' " +
                                    "where idGuruBK='"+kd+"'", TambahDataGuru.this)
                                    .addIntent(LihatDataGuru.class, true, "Data Guru BK " + namaGuru + " Berhasil Diupdate")
                                    .add("guruBK");
                        }else if(rbWK.isChecked()){
                            AlertDialog.Builder b = new AlertDialog.Builder(TambahDataGuru.this);
                            b.setTitle("Pilih Kelas");
                            final String[] kelas = new String[AppObject.kelas.length()];
                            for(int i=0;i<AppObject.kelas.length();i++){
                                kelas[i] = command.get("kelas", i, "kelas");
                            }
                            b.setItems(kelas, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    add(kelas[which]);
                                }
                                void add(String kelas){
                                    new Connect("update", "update guru set " +
                                            "namaGuru='"+namaGuru+"'," +
                                            "username='"+username+"'," +
                                            "password='"+password+"'," +
                                            "kelas='"+kelas+"' " +
                                            "where idGuru='"+kd+"'", TambahDataGuru.this)
                                            .addIntent(LihatDataGuru.class, true, "Data Guru " + namaGuru + " Berhasil Diupdate")
                                            .add("guru");
                                }

                            });
                            b.show();
                        }
                    }else {
                        command.setMessage("Password tidak cocok");
                    }
                }else {
                    command.setMessage("Data Belum Lengkap");
                }
            }
        });
    }

}
