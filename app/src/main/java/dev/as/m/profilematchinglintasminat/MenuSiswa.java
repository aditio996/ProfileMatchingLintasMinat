package dev.as.m.profilematchinglintasminat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MenuSiswa extends AppCompatActivity {

    private TextView text0, text1, text2;
    private Command command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_menu_siswa);
        AppObject.MASKED = 0;
        text0 = findViewById(R.id.homeSiswaText0);
        text1 = findViewById(R.id.homeSiswaText1);
        text2 = findViewById(R.id.homeSiswaText2);
        command.refresh("lintasminat", null, false, "");
        command.refresh("kelas", null, false, "");
        command.refresh("siswa", null, false, "");
        command.refresh("aspek", null, false, "");
        command.refresh("kriteria", null, false, "");
        text0.setTextSize(Command.medText);
        text1.setTextSize(Command.medText);
        text2.setTextSize(Command.medText);
        text0.setText("Selamat Datang "+command.get("siswaOnLogin", 0, "namaSiswa"));
        text1.setText("Aplikasi "+getResources().getString(R.string.app_name)+" merupakan aplikasi Sistem Pendukung Keputusan dengan metode Profile Matching untuk menentukan lintas minat siswa kelas X di SMA N 4 Padang.");
        text2.setText("Silahkan klik tombol lihat rekomendasi untuk melihat rekomendasi lintas minat anda.");
    }

    public void logoutSiswa(View view) {
        AppObject.siswaOnLogin = null;
        command.move(LoginSiswa.class, true);
    }

    public void toDataNilaiSiswa(View view) {
        if(command.get("siswaOnLogin", 0, "nilaiProfilSiswa").length() > 4){
            if(command.get("siswaOnLogin", 0, "rekomendasiLintasMinat").length() > 4){
                Intent intent = new Intent(this, InputNilaiSiswa.class);
                intent.putExtra("idSiswa", command.get("siswaOnlogin", 0, "idSiswa"));
                startActivity(intent);
            }else{
                command.setMessage("Data Nilai Anda Belum diproses\nMohon Tunggu Konfirmasi Dari Guru BK", Toast.LENGTH_LONG);
            }
        }else {
            command.setMessage("Data Nilai Anda Belum Diinput Oleh Guru", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
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

    public void bantuanSiswa(View view) {
        command.move(Bantuan.class, false);
    }
}
