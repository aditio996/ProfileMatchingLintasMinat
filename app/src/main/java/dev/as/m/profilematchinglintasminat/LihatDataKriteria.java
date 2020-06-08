package dev.as.m.profilematchinglintasminat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LihatDataKriteria extends AppCompatActivity {
    private Command command;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_lihat_data_kriteria);
        layout = findViewById(R.id.layoutKriteria);

        for(int h=0;h<AppObject.aspek.length();h++){
            LinearLayout aspekRow = command.getRow();
            command.addCol("Aspek : "+command.get("aspek", h, "namaAspek"), Command.medText, 1, aspekRow, 0, null);
            layout.addView(aspekRow);
            LinearLayout row = command.getRow();
            command.addCol("No", Command.smallText, 1, row, 0,null);
            command.addCol("Kode\nKriteria", Command.smallText, 1, row, 0,null);
            command.addCol("Nama\nKriteria", Command.smallText, 2, row, 0,null);
            command.addCol("Aksi", Command.smallText, 2, row, 0, null);
            layout.addView(row);
            int count = 0;
            for(int i=0;i<AppObject.kriteria.length();i++){
                if(command.get("aspek", h, "idAspek").equals(command.get("kriteria", i, "idAspek"))) {
                    LinearLayout row2 = command.getRow();
                    row2.setBackgroundResource(R.drawable.table_data);
                    command.addCol("" + (i + 1), Command.smallText, 1, row2, 1, null);
                    command.addCol("K"+command.get("kriteria", i, "idKriteria"), Command.smallText, 1, row2, 1, null);
                    command.addCol(command.get("kriteria", i, "namaKriteria"), Command.smallText, 2, row2, 1, null);
                    command.setTarget(TambahDataKriteria.class, false);
                    String namaAspek = command.getById(command.get("kriteria", i, "idAspek"), "aspek", "namaAspek");
                    command.addCol("Detail", Command.medText, 2, row2, 2, new AlertMessage(
                                    "Kriteria " + command.get("kriteria", i, "namaKriteria"),
                                    "Aspek : " + namaAspek + "\n" +
                                            "Jenis Kriteria : " + command.get("kriteria", i, "jenisKriteria") +"\n"+
                                            "Sub Kriteria :\n" +
                                            "[5] "+command.unserialize(command.get("kriteria", i, "subKriteria"), SubKriteria.sub5, 5)+"\n" +
                                            "[4] "+command.unserialize(command.get("kriteria", i, "subKriteria"), SubKriteria.sub4, 5)+"\n" +
                                            "[3] "+command.unserialize(command.get("kriteria", i, "subKriteria"), SubKriteria.sub3, 5)+"\n" +
                                            "[2] "+command.unserialize(command.get("kriteria", i, "subKriteria"), SubKriteria.sub2, 5)+"\n" +
                                            "[1] "+command.unserialize(command.get("kriteria", i, "subKriteria"), SubKriteria.sub1, 5)+"" +
                                            ""
                            ).setId(command.get("kriteria", i, "idKriteria"))
                                    .setTable("kriteria")
                                    .setField("idKriteria")
                    );
                    layout.addView(row2);
                    count++;
                }
            }
            if(count == 0){
                LinearLayout rowNotFound = command.getRow();
                command.addCol("Belum ada kriteria untuk aspek "+command.get("aspek", h, "namaAspek")+"", Command.smallText, 1, rowNotFound, -1, null);
                layout.addView(rowNotFound);
            }
            command.addSpace(layout, 10);
        }

    }

    public void tambahKriteria(View view) {
        Command.setINSERT();
        command.move(TambahDataKriteria.class, false);
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
