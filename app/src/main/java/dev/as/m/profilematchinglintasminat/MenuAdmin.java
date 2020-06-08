package dev.as.m.profilematchinglintasminat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MenuAdmin extends AppCompatActivity {

    private Command command;
    private TextView text0, text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        Command.setINSERT();
        setContentView(R.layout.activity_menu_admin);
        text0 = findViewById(R.id.homeAdmin0);
        text1 = findViewById(R.id.homeAdmin1);
        text2 = findViewById(R.id.homeAdmin2);
        text0.setTextSize(Command.medText);
        text1.setTextSize(Command.medText);
        text2.setTextSize(Command.medText);
        text0.setText("Selamat Datang Administrator");
        text1.setText("Aplikasi "+getResources().getString(R.string.app_name)+" merupakan aplikasi Sistem Pendukung Keputusan dengan metode Profile Matching untuk menentukan lintas minat siswa kelas X di SMA N 4 Padang.");
        text2.setText("Silahkan klik bantuan untuk info lebih lanjut");
        command.refresh("lintasminat", null, false, "");
        command.refresh("kelas", null, false, "");
        command.refresh("siswa", null, false, "");
        command.refresh("aspek", null, false, "");
        command.refresh("kriteria", null, false, "");
        command.refresh("guru", null, false, "");
        command.refresh("gurubk", null, false, "");
    }

    public void bantuanAdmin(View view) {
        command.move(Bantuan.class, false);
    }

    public void toDataKriteria(View view) {
        if (AppObject.aspek != null) {
            if(AppObject.kriteria != null){
                command.move(LihatDataKriteria.class, false);
            }else{
                command.setDataNotFound("Kriteria");
                command.move(TambahDataKriteria.class, false);
            }
        }else {
            command.setDataNotFound("Aspek");
        }
    }

    public void toDataAspek(View view) {
        if (AppObject.aspek != null) {
            command.move(LihatDataAspek.class, false);
        }else {
            command.setDataNotFound("Aspek");
            command.move(TambahDataAspek.class, false);
        }
    }

    public void toDataSiswa(View view) {
        if(AppObject.kelas != null){
            if(AppObject.siswa != null){
                command.move(LihatDataSiswa.class, false);
            }else {
                command.setDataNotFound("Siswa");
                command.move(TambahDataSiswa.class, false);
            }
        }else{
            command.setDataNotFound("Kelas");
        }
    }

    public void toDataGuru(View view) {
        if (AppObject.guru != null || AppObject.guruBK != null) {
            command.move(LihatDataGuru.class, false);
        }else {
            command.setDataNotFound("Guru");
            command.move(TambahDataGuru.class, false);
        }
    }

    public void toDataKelas(View view) {
        if(AppObject.lintasMinat != null){
            if(AppObject.kelas != null){
                command.move(LihatDataKelas.class, false);
            }else{
                command.setDataNotFound("Kelas");
                command.move(TambahDataKelas.class, false);
            }
        }else{
            command.setDataNotFound("Lintas Minat");
        }
    }

    public void toDataLintasMinat(View view) {
        if(AppObject.lintasMinat != null){
            command.move(LihatDataLintasMinat.class, false);
        }else{
            command.setDataNotFound("Lintas Minat");
            command.move(TambahDataLintasMinat.class, false);
        }
    }

    public void logoutAdmin(View view) {
        AppObject.admin = null;
        AppObject.guruBK = null;
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
                        System.exit(0);
                    }
                })
                .setNegativeButton("Tidak", null)
                ;
        dialog.show();
    }

}
