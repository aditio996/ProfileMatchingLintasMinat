package dev.as.m.profilematchinglintasminat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TambahDataKelas extends AppCompatActivity {

    private Command command;
    private RadioButton rbIps, rbMipa;
    private EditText etKelas;
    private Spinner spLm1, spLm2;
    private Button btnManage;
    private TextView judulManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_tambah_data_kelas);
        judulManage = findViewById(R.id.judulManageDataKelas);
        rbIps = findViewById(R.id.rbIps);
        rbMipa = findViewById(R.id.rbMipa);
        etKelas = findViewById(R.id.etKelas);
        spLm1 = findViewById(R.id.spLm1);
        spLm2 = findViewById(R.id.spLm2);
        btnManage = findViewById(R.id.btnManageKelas);
        List<String> lintasMinatArray1 = new ArrayList<>();
        List<String> lintasMinatArray2 = new ArrayList<>();
        lintasMinatArray1.add("- Pilih Lintas Minat 1 -");
        lintasMinatArray2.add("- Pilih Lintas Minat 2 -");
        for(int i=0;i<AppObject.lintasMinat.length();i++){
            lintasMinatArray1.add(command.get("lintasMinat", i, "lintasMinat"));
            lintasMinatArray2.add(command.get("lintasMinat", i, "lintasMinat"));
        }
        spLm1.setAdapter(new ArrayAdapter<>(this, R.layout.simple_list, lintasMinatArray1));
        spLm2.setAdapter(new ArrayAdapter<>(this, R.layout.simple_list, lintasMinatArray2));
        if(Command.MANAGE != Command.UPDATE){
            setInsert();
        }else{
            setUpdate();
        }
    }

    private void setInsert(){
        rbMipa.setChecked(true);
        judulManage.setText("Tambah Data Kelas");
        btnManage.setText("Tambah Data");
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!command.getText(etKelas).equals("") && spLm1.getSelectedItemPosition() != 0 && spLm2.getSelectedItemPosition() != 0){
                    String kelas = command.getText(etKelas).toUpperCase();
                    String peminatan = "MIPA";
                    if(rbIps.isChecked())peminatan = "IPS";
                    String lintasMinat1 = spLm1.getSelectedItem().toString();
                    String lintasMinat2 = spLm2.getSelectedItem().toString();
                    if(kelas.contains(peminatan)) {
                        new Connect("insert", "insert into kelas " +
                                "(kelas, peminatan, lintasMinat1, lintasMinat2) values " +
                                "('" + kelas + "', '" + peminatan + "', '" + lintasMinat1 + "', '" + lintasMinat2 + "')", TambahDataKelas.this).addIntent(LihatDataKelas.class, true, "Data Kelas " + kelas + " Berhasil Ditambahkan")
                                .add("kelas");
                    }else {
                        command.setMessage("Peminatan dan kelas tidak cocok");
                    }
                }else{
                    command.setMessage("Data belum lengkap!");
                }
            }
        });
    }

    private void setUpdate(){
        final String kd = getIntent().getExtras().getString("kode");
        judulManage.setText("Update Data Kelas");
        btnManage.setText("Update Data");
        etKelas.setText(command.getById(kd, "kelas", "kelas"));
        rbMipa.setChecked(true);
        if(command.getById(kd, "kelas", "peminatan").equals("IPS")){
            rbIps.setChecked(true);
        }
        int indexLM1 = command.getIndex("lintasMinat", "lintasMinat", command.getById(kd, "kelas", "lintasMinat1"))+1;
        int indexLM2 = command.getIndex("lintasMinat", "lintasMinat", command.getById(kd, "kelas", "lintasMinat2"))+1;
        spLm1.setSelection(indexLM1);
        spLm2.setSelection(indexLM2);
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!command.getText(etKelas).equals("") && spLm1.getSelectedItemPosition() != 0 && spLm2.getSelectedItemPosition() != 0){
                    String kelas = command.getText(etKelas).toUpperCase();
                    String peminatan = "MIPA";
                    if(rbIps.isChecked())peminatan = "IPS";
                    String lintasMinat1 = spLm1.getSelectedItem().toString();
                    String lintasMinat2 = spLm2.getSelectedItem().toString();
                    if(kelas.contains(peminatan)) {
                        new Connect("update", "update kelas set " +
                                "kelas='"+kelas+"', " +
                                "peminatan='"+peminatan+"', " +
                                "lintasMinat1='"+lintasMinat1+"', " +
                                "lintasMinat2='"+lintasMinat2+"' " +
                                "where idKelas='"+kd+"'", TambahDataKelas.this)
                                .addIntent(LihatDataKelas.class, true, "Data Kelas Telah diupdate")
                                .add("kelas", "idKelas", kd);
                    }else {
                        command.setMessage("Peminatan dan kelas tidak cocok");
                    }
                }else{
                    command.setMessage("Data belum lengkap!");
                }
            }
        });
    }



}
