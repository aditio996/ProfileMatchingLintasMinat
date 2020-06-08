package dev.as.m.profilematchinglintasminat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LihatDataSiswa extends AppCompatActivity {
    private Command command;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_lihat_data_siswa);
        layout = findViewById(R.id.layoutSiswa);
        for(int h=0;h<AppObject.kelas.length();h++){
            LinearLayout kelasRow = command.getRow();
            command.addCol("Kelas : "+command.get("kelas", h, "kelas"), Command.medText, 1, kelasRow, 0, null);
            layout.addView(kelasRow);
            LinearLayout row = command.getRow();
            command.addCol("No", Command.smallText, 1, row, 0,null);
            command.addCol("Nama Siswa", Command.smallText, 2, row, 0,null);
            command.addCol("Aksi", Command.smallText, 2, row, 0, null);
            layout.addView(row);
            int count = 0;
            for(int i=0;i<AppObject.siswa.length();i++){
                if(command.get("kelas", h, "kelas").equals(command.get("siswa", i, "kelas"))) {
                    LinearLayout row2 = command.getRow();
                    row2.setBackgroundResource(R.drawable.table_data);
                    command.addCol("" + (count + 1), Command.smallText, 1, row2, 1, null);
                    command.addCol(command.get("siswa", i, "namaSiswa"), Command.smallText, 2, row2, 1, null);
                    command.setTarget(TambahDataSiswa.class, false);
                    command.addCol("Detail", Command.medText, 2, row2, 2, new AlertMessage(
                                    "Nama :" + command.get("siswa", i, "namaSiswa"),
                                    "Jenis Kelamin : " + command.get("siswa", i, "jenisKelamin") + "\n" +
                                    "NIS : " + command.get("siswa", i, "nis") + "\n" +
                                            "Password : " + command.get("siswa", i, "password") + "\n" +
                                            "Kelas : " + command.get("siswa", i, "kelas")
                            ).setId(command.get("siswa", i, "idSiswa"))
                                    .setTable("siswa")
                                    .setField("idSiswa")
                    );
                    layout.addView(row2);
                    count++;
                }
            }
            if(count == 0){
                LinearLayout rowNotFound = command.getRow();
                command.addCol("Belum ada data siswa di kelas "+command.get("kelas", h, "kelas")+"", Command.smallText, 1, rowNotFound, -1, null);
                layout.addView(rowNotFound);
            }
            command.addSpace(layout, 10);
        }


    }

    public void tambahSiswa(View view) {
        Command.setINSERT();
        command.move(TambahDataSiswa.class, false);
    }

    @Override
    public void onBackPressed() {
        if(AppObject.admin != null){
            command.move(MenuAdmin.class, true);
        }else{
            command.move(MenuGuruBK.class, true);
        }
    }
}
