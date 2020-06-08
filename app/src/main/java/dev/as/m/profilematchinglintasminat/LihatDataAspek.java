package dev.as.m.profilematchinglintasminat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LihatDataAspek extends AppCompatActivity {
    private Command command;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_lihat_data_aspek);
        layout = findViewById(R.id.layoutAspek);
        LinearLayout row = command.getRow();
        command.addCol("No", Command.smallText, 1, row, 0,null);
        command.addCol("Kode\nAspek", Command.smallText, 1, row, 0,null);
        command.addCol("Nama\nAspek", Command.smallText, 2, row, 0,null);
        command.addCol("Aksi", Command.smallText, 2, row, 0, null);
        layout.addView(row);

        for(int i=0;i<AppObject.aspek.length();i++){
            LinearLayout row2 = command.getRow();
            row2.setBackgroundResource(R.drawable.table_data);
            command.addCol(""+(i+1), Command.smallText, 1, row2, 1, null);
            command.addCol("A"+command.get("aspek", i, "idAspek"), Command.smallText, 1, row2, 1, null);
            command.addCol(command.get("aspek", i, "namaAspek"), Command.smallText, 2, row2, 1, null);
            //command.addCol(command.get("lintasMinat", i, "kapasitas"), Command.smallText, 2, row2, 1, null);
            command.setTarget(TambahDataAspek.class, false);
            command.addCol("Detail", Command.medText, 2, row2, 2, new AlertMessage(
                            command.get("aspek", i, "namaAspek"),
                            "Bobot Core Factor : "+command.get("aspek", i, "bobotCF")+"%\n" +
                                    "Bobot Secondary Factor : "+command.get("aspek", i, "bobotSF")+"%\n" +
                                    "Bobot Aspek : "+command.get("aspek", i, "bobotAspek")+"%"
                    ).setId(command.get("aspek", i, "idAspek"))
                            .setTable("aspek")
                            .setField("idAspek")
            );
            layout.addView(row2);
        }

    }

    public void tambahAspek(View view) {
        Command.setINSERT();
        command.move(TambahDataAspek.class, false);
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
