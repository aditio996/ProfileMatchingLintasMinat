package dev.as.m.profilematchinglintasminat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TambahDataAspek extends AppCompatActivity {

    private Command command;
    private EditText etNamaAspek, etBobotCF, etBobotSF, etBobotAspek;
    private TextView judulManage;
    private Button btnManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_tambah_data_aspek);
        etNamaAspek = findViewById(R.id.etNamaAspek);
        etBobotCF = findViewById(R.id.etBobotCF);
        etBobotSF = findViewById(R.id.etBobotSF);
        etBobotAspek = findViewById(R.id.etBobotAspek);
        judulManage = findViewById(R.id.judulManageDataAspek);
        btnManage = findViewById(R.id.btnManageAspek);
        if(Command.MANAGE == Command.UPDATE) {
            setUpdate();
        }else {
            setInsert();
        }
    }

    private void setInsert() {
        judulManage.setText("Tambah Data Aspek");
        btnManage.setText("Tambah Data");
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaAspek = command.getText(etNamaAspek);
                String bobotCF = command.getText(etBobotCF);
                String bobotSF = command.getText(etBobotSF);
                String bobotAspek = command.getText(etBobotAspek);
                if(command.toInt(bobotCF) + command.toInt(bobotSF) == 100) {
                    new Connect("insert", "insert into aspek " +
                            "(namaAspek, bobotCF, bobotSF, bobotAspek) values " +
                            "('" + namaAspek + "', '" + bobotCF + "', '"+bobotSF+"', '"+bobotAspek+"')", TambahDataAspek.this)
                            .addIntent(LihatDataAspek.class, true, "Data Aspek " + namaAspek + " Berhasil Ditambahkan")
                            .add("aspek");
                }else {
                    command.setMessage("Jumlah Bobot CF dan Bobot SF harus 100%!");
                }
            }
        });
    }

    private void setUpdate(){
        judulManage.setText("Update Data Aspek");
        btnManage.setText("Update Data");
        final String kd = getIntent().getExtras().getString("kode");
        etNamaAspek.setText(command.getById(kd, "aspek", "namaAspek"));
        etBobotCF.setText(command.getById(kd, "aspek", "bobotCF"));
        etBobotSF.setText(command.getById(kd, "aspek", "bobotSF"));
        etBobotAspek.setText(command.getById(kd, "aspek", "bobotAspek"));
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String namaAspek = command.getText(etNamaAspek);
                final String bobotCF = command.getText(etBobotCF);
                final String bobotSF = command.getText(etBobotSF);
                final String bobotAspek = command.getText(etBobotAspek);
                if(command.toInt(bobotCF) + command.toInt(bobotSF) == 100) {
                    new Connect("update", "update aspek set " +
                            "namaAspek='" + namaAspek + "', " +
                            "bobotCF='" + bobotCF + "', " +
                            "bobotSF='" + bobotSF + "', " +
                            "bobotAspek='" + bobotAspek + "' " +
                            "where idAspek='" + kd + "'", TambahDataAspek.this)
                            .addIntent(LihatDataAspek.class, true, "Data Aspek Sukses Diupdate")
                            .add("aspek", "idAspek", kd);
                }else {
                    command.setMessage("Jumlah Bobot CF dan Bobot SF harus 100%!");
                }
            }
        });
    }

}
