package dev.as.m.profilematchinglintasminat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MenuGuru extends AppCompatActivity {

    private Command command;
    private TextView text0, text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        AppObject.guruBK = null;
        setContentView(R.layout.activity_menu_guru);
        text0 = findViewById(R.id.homeGuruText0);
        text1 = findViewById(R.id.homeGuruText1);
        text2 = findViewById(R.id.homeGuruText2);
        command.refresh("lintasminat", null, false, "");
        command.refresh("kelas", null, false, "");
        command.refresh("siswa", null, false, "");
        command.refresh("aspek", null, false, "");
        command.refresh("kriteria", null, false, "");
        text0.setTextSize(Command.medText);
        text1.setTextSize(Command.medText);
        text2.setTextSize(Command.medText);
        text0.setText("Selamat Datang Guru");
        text1.setText("Aplikasi "+getResources().getString(R.string.app_name)+" merupakan aplikasi Sistem Pendukung Keputusan dengan metode Profile Matching untuk menentukan lintas minat siswa kelas X di SMA N 4 Padang.");
        text2.setText("Silahkan klik tombol lihat rekomendasi untuk melihat rekomendasi lintas minat anda.");
    }

    public void toInputNilaiGuru(View view) {
        if(AppObject.siswa != null){
            if(AppObject.kriteria != null){
                int cekLintasMinat = 0;
                for(int i=0;i<AppObject.lintasMinat.length();i++) {
                    if (!command.get("lintasMinat", i, "profilIdeal").equals("")){
                        cekLintasMinat++;
                    }
                }
                if(cekLintasMinat == AppObject.lintasMinat.length()) {
                    command.move(InputNilaiSiswa.class, false);
                }else {
                    command.setMessage("Data Lintas Minat Belum Lengkap / Error, Mohon Konfirmasi Kepada Administrator", Toast.LENGTH_LONG);
                }
            }else{
                command.setDataNotFound("Kriteria");
            }
        }else{
            command.setDataNotFound("Siswa");
        }
    }

    public void bantuanGuru(View view) {
        command.move(Bantuan.class, false);
    }

    public void logoutGuru(View view) {
        AppObject.guruOnLogin = null;
        command.move(LoginSiswa.class, true);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("Keluar Aplikasi")
                .setMessage("Anda Yakin Ingin Keluar Aplikasi?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppObject.guru = null;
                        System.exit(0);
                    }
                })
                .setNegativeButton("Tidak", null)
                ;
        dialog.show();
    }
}
