package dev.as.m.profilematchinglintasminat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TambahDataSiswa extends AppCompatActivity {

    private EditText etNis, etPassword, etPassword2, etNamaSiswa;
    private Spinner spKelas;
    private Command command;
    private TextView judulManage;
    private Button btnManage;
    private RadioButton rbLakiLaki, rbPerempuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_tambah_data_siswa);
        etNamaSiswa = findViewById(R.id.etNamaSiswa);
        etNis = findViewById(R.id.etNis);
        etPassword = findViewById(R.id.etPassword);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        spKelas = findViewById(R.id.spKelasSiswa);
        judulManage = findViewById(R.id.judulManageSiswa);
        btnManage = findViewById(R.id.btnManageSiswa);
        rbLakiLaki = findViewById(R.id.rbLakiLaki);
        rbPerempuan = findViewById(R.id.rbPerempuan);
        rbLakiLaki.setChecked(true);
        List<String> kelasArray = new ArrayList<>();
        kelasArray.add("- Pilih Kelas -");
        for(int i = 0; i <AppObject.kelas.length();i++){
            kelasArray.add(command.get("kelas", i, "kelas"));
        }
        spKelas.setAdapter(new ArrayAdapter<>(this, R.layout.simple_list, kelasArray));
        if(Command.MANAGE == Command.INSERT){
            setInsert();
        }else {
            setUpdate();
        }
    }

    private void setInsert(){
        judulManage.setText("Tambah Data Kelas");
        btnManage.setText("Tambah Data");
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!command.getText(etNamaSiswa).equals("") && !command.getText(etNis).equals("") && !command.getText(etPassword).equals("") && !command.getText(etPassword2).equals("") && spKelas.getSelectedItemPosition() != 0){
                    if(command.getText(etPassword).equals(command.getText(etPassword2))){
                        String namaSiswa = command.getText(etNamaSiswa);
                        String nis = command.getText(etNis);
                        String password = command.getText(etPassword);
                        String kelas = spKelas.getSelectedItem().toString();
                        String jenisKelamin = "Laki-laki";
                        if(rbPerempuan.isChecked())jenisKelamin = "Perempuan";
                        new Connect("insert", "insert into siswa " +
                                "(namaSiswa, jenisKelamin, nis, password, kelas) values " +
                                "('"+namaSiswa+"', '"+jenisKelamin+"', '"+nis+"', '"+password+"', '"+kelas+"')", TambahDataSiswa.this).addIntent(LihatDataSiswa.class, true, "Data Siswa " + namaSiswa + " Berhasil Ditambahkan")
                                .add("siswa");
                    }else{
                        command.setMessage("Password tidak cocok");
                    }
                }else{
                    command.setMessage("Data belum lengkap!");
                }
            }
        });
    }

    private void setUpdate(){
        final String kd = getIntent().getExtras().getString("kode");
        judulManage.setText("Update Data Siswa");
        btnManage.setText("Update Data");
        etNamaSiswa.setText(command.getById(kd, "siswa", "namaSiswa"));
        etNis.setText(command.getById(kd, "siswa", "nis"));
        etPassword.setText(command.getById(kd, "siswa", "password"));
        etPassword2.setText(command.getById(kd, "siswa", "password"));
        if(command.getById(kd, "siswa", "jenisKelamin").equals("Perempuan"))rbPerempuan.setChecked(true);
        int indexKelas = command.getIndex("kelas", "kelas", command.getById(kd, "siswa", "kelas")) + 1;
        spKelas.setSelection(indexKelas);
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!command.getText(etNamaSiswa).equals("") && !command.getText(etNis).equals("") && !command.getText(etPassword).equals("") && !command.getText(etPassword2).equals("") && spKelas.getSelectedItemPosition() != 0){
                    if(command.getText(etPassword).equals(command.getText(etPassword2))){
                        String namaSiswa = command.getText(etNamaSiswa);
                        String nis = command.getText(etNis);
                        String password = command.getText(etPassword);
                        String kelas = spKelas.getSelectedItem().toString();
                        String jenisKelamin = "Laki-laki";
                        if(rbPerempuan.isChecked()) jenisKelamin = "Perempuan";
                        new Connect("update", "update siswa set " +
                                "namaSiswa='"+namaSiswa+"', " +
                                "jenisKelamin='"+jenisKelamin+"', " +
                                "nis='"+nis+"', " +
                                "password='"+password+"', " +
                                "kelas='"+kelas+"' " +
                                "where idSiswa='"+kd+"'", TambahDataSiswa.this)
                                .addIntent(LihatDataSiswa.class, true, "Data Siswa Telah diupdate")
                                .add("siswa", "idSiswa", kd);
                    }else{
                        command.setMessage("Password tidak cocok");
                    }
                }else{
                    command.setMessage("Data belum lengkap!");
                }
            }
        });
    }

}
