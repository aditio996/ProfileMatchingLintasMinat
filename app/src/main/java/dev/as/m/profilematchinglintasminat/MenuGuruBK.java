package dev.as.m.profilematchinglintasminat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MenuGuruBK extends AppCompatActivity {

    private Command command;
    private TextView text0, text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        Command.setINSERT();
        AppObject.idKelasTerpilih = "1";
        setContentView(R.layout.activity_menu_guru_bk);
        text0 = findViewById(R.id.homeGuruBK0);
        text1 = findViewById(R.id.homeGuruBK1);
        text2 = findViewById(R.id.homeGuruBK2);
        text0.setTextSize(Command.medText);
        text1.setTextSize(Command.medText);
        text2.setTextSize(Command.medText);
        text0.setText("Selamat Datang Guru BK");
        text1.setText("Aplikasi "+getResources().getString(R.string.app_name)+" merupakan aplikasi Sistem Pendukung Keputusan dengan metode Profile Matching untuk menentukan lintas minat siswa kelas X di SMA N 4 Padang.");
        text2.setText("Silahkan klik bantuan untuk info lebih lanjut");
        //command.refresh("lintasMinat", null, false, "");
        command.refresh("lintasminat", null, false, "");
        command.refresh("kelas", null, false, "");
        command.refresh("siswa", null, false, "");
        command.refresh("aspek", null, false, "");
        command.refresh("kriteria", null, false, "");
        command.refresh("guru", null, false, "");
    }

    public void toDataLintasMinat(View view) {
        if(AppObject.lintasMinat != null){
            command.move(LihatDataLintasMinat.class, false);
        }else{
            setDataNotFound("Lintas Minat");
            command.move(TambahDataLintasMinat.class, false);
        }
    }

    public void toDataSiswa(View view) {
        if(AppObject.kelas != null){
            if(AppObject.siswa != null){
                command.move(LihatDataSiswa.class, false);
            }else {
                setDataNotFound("Siswa");
                command.move(TambahDataSiswa.class, false);
            }
        }else{
            setDataNotFound("Kelas");
        }
    }

    public void toDataKelas(View view) {
        if(AppObject.lintasMinat != null){
            if(AppObject.kelas != null){
                command.move(LihatDataKelas.class, false);
            }else{
                setDataNotFound("Kelas");
                command.move(TambahDataKelas.class, false);
            }
        }else{
            setDataNotFound("Lintas Minat");
        }
    }

    public void toDataAspek(View view) {
        if (AppObject.aspek != null) {
            command.move(LihatDataAspek.class, false);
        }else {
            setDataNotFound("Aspek");
            command.move(TambahDataAspek.class, false);
        }
    }

    public void toDataKriteria(View view) {
        if (AppObject.aspek != null) {
            if(AppObject.kriteria != null){
                command.move(LihatDataKriteria.class, false);
            }else{
                setDataNotFound("Kriteria");
                command.move(TambahDataKriteria.class, false);
            }
        }else {
            setDataNotFound("Aspek");
        }
    }

    public void toDataNilai(View view) {
        if(AppObject.siswa != null){
            if(AppObject.kriteria != null){
                //if (AppObject.nilai != null) {

                //}else {
                    //setDataNotFound("Nilai");

                //    command.move(InputNilaiSiswa.class, false);
                //}
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
                setDataNotFound("Kriteria");
            }
        }else{
            setDataNotFound("Siswa");
        }
    }

    public void logoutGuruBK(View view) {
        AppObject.guruBK = null;
        command.move(LoginSiswa.class, true);
    }

    private void setDataNotFound(String table){
        command.setMessage("Data "+table+" Belum ada.\ninput data "+table+" terlebih dahulu");
    }

    public void bantuanGuruBK(View view) {
        command.move(Bantuan.class, false);
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
                .setNegativeButton("Tidak", null);
        dialog.show();
        //command.move(LoginSiswa.class, true);
    }
}
