package dev.as.m.profilematchinglintasminat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LihatDataKelas extends AppCompatActivity {
    private Command command;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_lihat_data_kelas);
        layout = findViewById(R.id.layoutKelas);
        LinearLayout row = command.getRow();
        command.addCol("No", Command.smallText, 1, row, 0,null);
        command.addCol("Kelas", Command.smallText, 2, row, 0,null);
        command.addCol("Aksi", Command.smallText, 2, row, 0, null);
        layout.addView(row);

        for(int i=0;i<AppObject.kelas.length();i++){
            LinearLayout row2 = command.getRow();
            row2.setBackgroundResource(R.drawable.table_data);
            command.addCol(""+(i+1), Command.smallText, 1, row2, 1, null);
            command.addCol(command.get("kelas", i, "kelas"), Command.smallText, 2, row2, 1, null);
            command.setTarget(TambahDataKelas.class, false);
            command.addCol("Detail", Command.medText, 2, row2, 2, new AlertMessage(
                            "Kelas "+command.get("kelas", i, "kelas"),
                            "Lintas Minat 1 : "+command.get("kelas", i, "lintasMinat1")+"\n"
                                    +"Lintas Minat 2 : "+command.get("kelas", i, "lintasMinat2")
                    ).setId(command.get("kelas", i, "idKelas"))
                            .setTable("kelas")
                            .setField("idKelas")
            );
            layout.addView(row2);
        }

    }

    public void tambahKelas(View view) {
        Command.setINSERT();
        command.move(TambahDataKelas.class, false);
    }

    @Override
    public void onBackPressed() {
        if(AppObject.admin != null){
            command.move(MenuAdmin.class, true);
        }else {
            command.move(MenuGuruBK.class, true);
        }
    }


}
