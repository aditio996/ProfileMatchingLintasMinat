package dev.as.m.profilematchinglintasminat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TambahDataLintasMinat extends AppCompatActivity {

    private Command command;
    private EditText etMapelLintasMinat, etKeterangan;
    private TextView judulManage;
    private Button btnManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_tambah_data_lintas_minat);
        etMapelLintasMinat = findViewById(R.id.etmapelLintasMinat);
        etKeterangan = findViewById(R.id.etKeterangan);
        judulManage = findViewById(R.id.judulManageLintasMinat);
        btnManage = findViewById(R.id.btnManageLM);
        if(Command.MANAGE == Command.UPDATE) {
            setUpdate();
        }else {
            setInsert();
        }

    }

    private void setInsert() {
        judulManage.setText("Tambah Data Lintas Minat");
        btnManage.setText("Tambah Data");
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapelLintasMinat = command.getText(etMapelLintasMinat);
                String keterangan = command.getText(etKeterangan);
                new Connect("insert", "insert into lintasminat " +
                        "(lintasMinat, keterangan) values " +
                        "('" + mapelLintasMinat + "', '" + keterangan + "')", TambahDataLintasMinat.this).addIntent(LihatDataLintasMinat.class, true, "Data Lintas Minat " + mapelLintasMinat + " Berhasil Ditambahkan")
                        .add("lintasMinat");
            }
        });
    }

    private void setUpdate(){
        judulManage.setText("Update Data Lintas Minat");
        btnManage.setText("Update Data");
        final String kd = getIntent().getExtras().getString("kode");
        etMapelLintasMinat.setText(command.getById(kd, "lintasMinat", "lintasMinat"));
        etKeterangan.setText(command.getById(kd, "lintasMinat", "keterangan"));
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Connect("update", "update lintasminat set " +
                        "lintasMinat='"+command.getText(etMapelLintasMinat)+"', " +
                        "keterangan='"+command.getText(etKeterangan)+"' where lintasMinat='"+kd+"'", TambahDataLintasMinat.this)
                        .addIntent(LihatDataLintasMinat.class, true, "Data Lintas Minat Telah diupdate")
                        .add("lintasMinat", "lintasMinat", kd);
            }
        });
    }
}
