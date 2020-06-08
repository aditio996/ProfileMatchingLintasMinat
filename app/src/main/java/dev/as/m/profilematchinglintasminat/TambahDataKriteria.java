package dev.as.m.profilematchinglintasminat;

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
import java.util.List;

public class TambahDataKriteria extends AppCompatActivity {

    private Command command;
    private RadioButton rbCF, rbSF;
    private EditText etNamaKriteria;
    private Spinner spAspek;
    private Button btnManage;
    private TextView judulManage;
    private EditText etSubKriteria5, etSubKriteria4, etSubKriteria3, etSubKriteria2, etSubKriteria1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_tambah_data_kriteria);
        judulManage = findViewById(R.id.judulManageDataKriteria);
        rbCF = findViewById(R.id.rbCF);
        rbSF = findViewById(R.id.rbSF);
        etNamaKriteria = findViewById(R.id.etNamaKriteria);
        spAspek = findViewById(R.id.spAspek);
        btnManage = findViewById(R.id.btnManageKriteria);
        etSubKriteria5 = findViewById(R.id.etSubKriteria5);
        etSubKriteria4 = findViewById(R.id.etSubKriteria4);
        etSubKriteria3 = findViewById(R.id.etSubKriteria3);
        etSubKriteria2 = findViewById(R.id.etSubKriteria2);
        etSubKriteria1 = findViewById(R.id.etSubKriteria1);
        List<String> aspekArray1 = new ArrayList<>();
        aspekArray1.add("- Pilih Aspek -");
        for(int i=0;i<AppObject.aspek.length();i++){
            aspekArray1.add(command.get("aspek", i, "namaAspek"));
        }
        spAspek.setAdapter(new ArrayAdapter<>(this, R.layout.simple_list, aspekArray1));
        if(Command.MANAGE != Command.UPDATE){
            setInsert();
        }else{
            setUpdate();
        }
    }

    private void setInsert(){
        rbCF.setChecked(true);
        judulManage.setText("Tambah Data Kriteria");
        btnManage.setText("Tambah Data");
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!command.getText(etNamaKriteria).equals("") && spAspek.getSelectedItemPosition() != 0 && !command.getText(etSubKriteria5).equals("") && !command.getText(etSubKriteria4).equals("") && !command.getText(etSubKriteria3).equals("") && !command.getText(etSubKriteria2).equals("") && !command.getText(etSubKriteria1).equals("")){
                    String namaKriteria = command.getText(etNamaKriteria);
                    String jenisKriteria = "CF";
                    if(rbSF.isChecked())jenisKriteria= "SF";
                    int indexAspek = command.getIndex("aspek", "namaAspek", spAspek.getSelectedItem().toString());
                    String idAspek = command.get("aspek", indexAspek, "idAspek");
                    String subKriteria5 = command.getText(etSubKriteria5);
                    String subKriteria4 = command.getText(etSubKriteria4);
                    String subKriteria3 = command.getText(etSubKriteria3);
                    String subKriteria2 = command.getText(etSubKriteria2);
                    String subKriteria1 = command.getText(etSubKriteria1);
                    String[] subKriteria = new String[]{subKriteria5, subKriteria4, subKriteria3, subKriteria2, subKriteria1};
                    new Connect("insert", "insert into kriteria " +
                            "(idAspek, namaKriteria, jenisKriteria, subKriteria) values " +
                            "('" + idAspek + "', '" + namaKriteria + "', '" + jenisKriteria + "', '"+command.serialize(subKriteria)+"')", TambahDataKriteria.this).addIntent(LihatDataKriteria.class, true, "Data Kriteria " + namaKriteria + " Berhasil Ditambahkan")
                            .add("kriteria");
                }else{
                    command.setMessage("Data belum lengkap!");
                }
            }
        });
    }

    private void setUpdate() {
        final String kd = getIntent().getExtras().getString("kode");
        rbCF.setChecked(true);
        judulManage.setText("Update Data Kriteria");
        btnManage.setText("Update Data");
        etNamaKriteria.setText(command.getById(kd, "kriteria", "namaKriteria"));
        rbCF.setChecked(true);
        if(command.getById(kd, "kriteria", "jenisKriteria").equals("SF")){
            rbSF.setChecked(true);
        }
        int indexAspek = command.getIndex("aspek", "idAspek", command.getById(kd, "kriteria", "idAspek")) + 1;
        spAspek.setSelection(indexAspek);

        etSubKriteria5.setText(command.unserialize(command.getById(kd, "kriteria", "subKriteria"), SubKriteria.sub5, SubKriteria.length));
        etSubKriteria4.setText(command.unserialize(command.getById(kd, "kriteria", "subKriteria"), SubKriteria.sub4, SubKriteria.length));
        etSubKriteria3.setText(command.unserialize(command.getById(kd, "kriteria", "subKriteria"), SubKriteria.sub3, SubKriteria.length));
        etSubKriteria2.setText(command.unserialize(command.getById(kd, "kriteria", "subKriteria"), SubKriteria.sub2, SubKriteria.length));
        etSubKriteria1.setText(command.unserialize(command.getById(kd, "kriteria", "subKriteria"), SubKriteria.sub1, SubKriteria.length));
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!command.getText(etNamaKriteria).equals("") && spAspek.getSelectedItemPosition() != 0 && !command.getText(etSubKriteria5).equals("") && !command.getText(etSubKriteria4).equals("") && !command.getText(etSubKriteria3).equals("") && !command.getText(etSubKriteria2).equals("") && !command.getText(etSubKriteria1).equals("")){
                    String namaKriteria = command.getText(etNamaKriteria);
                    String jenisKriteria = "CF";
                    String subKriteria5 = command.getText(etSubKriteria5);
                    String subKriteria4 = command.getText(etSubKriteria4);
                    String subKriteria3 = command.getText(etSubKriteria3);
                    String subKriteria2 = command.getText(etSubKriteria2);
                    String subKriteria1 = command.getText(etSubKriteria1);
                    String[] subKriteria = new String[]{subKriteria5, subKriteria4, subKriteria3, subKriteria2, subKriteria1};
                    if(rbSF.isChecked())jenisKriteria= "SF";
                    int indexAspek = command.getIndex("aspek", "namaAspek", spAspek.getSelectedItem().toString());
                    String idAspek = command.get("aspek", indexAspek, "idAspek");
                    new Connect("update", "update kriteria set " +
                            "idAspek='"+idAspek+"', " +
                            "namaKriteria='"+namaKriteria+"', " +
                            "jenisKriteria='"+jenisKriteria+"', " +
                            "subKriteria='"+command.serialize(subKriteria)+"' " +
                            "where idKriteria='"+kd+"'", TambahDataKriteria.this).addIntent(LihatDataKriteria.class, true, "Data Kriteria " + namaKriteria + " Berhasil Diupdate")
                            .add("kriteria");
                }else{
                    command.setMessage("Data belum lengkap!");
                }
            }
        });
    }



}
