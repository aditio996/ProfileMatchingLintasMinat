package dev.as.m.profilematchinglintasminat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LihatDataLintasMinat extends AppCompatActivity {
    private Command command;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_lihat_data_lintas_minat);
        layout = findViewById(R.id.layoutLintasMinat);
        LinearLayout row = command.getRow();
        command.addCol("No", Command.smallText, 1, row, 0,null);
        command.addCol("Lintas\nMinat", Command.smallText, 2, row, 0,null);
        command.addCol("Aksi", Command.smallText, 2, row, 0, null);
        layout.addView(row);

        for(int i=0;i<AppObject.lintasMinat.length();i++){
            LinearLayout row2 = command.getRow();
            row2.setBackgroundResource(R.drawable.table_data);
            command.addCol(""+(i+1), Command.smallText, 1, row2, 1, null);
            command.addCol(command.get("lintasMinat", i, "lintasMinat"), Command.smallText, 2, row2, 1, null);
            //command.addCol(command.get("lintasMinat", i, "kapasitas"), Command.smallText, 2, row2, 1, null);
            command.setTarget(TambahDataLintasMinat.class, false);
            if(!command.get("lintasMinat", i, "profilIdeal").equals("")) {
                String profilIdeal = "";
                if(AppObject.kriteria != null) {
                    for (int j = 0; j < AppObject.kriteria.length(); j++) {
                        int np = command.toInt(command.unserialize(command.get("lintasMinat", i, "profilIdeal"), j, AppObject.kriteria.length()));
                        int x = 0;
                        if (np == 5) x = 0;
                        else if (np == 4) x = 1;
                        else if (np == 3) x = 2;
                        else if (np == 2) x = 3;
                        else if (np == 1) x = 4;
                        String subKriteria = command.unserialize(command.get("kriteria", j, "subKriteria"), x, SubKriteria.length);

                        profilIdeal += "K" + command.get("kriteria", j, "idKriteria") + " = [" + np + "]" + subKriteria + "\n";
                    }
                }
                command.addCol("Detail", Command.medText, 2, row2, 2, new AlertMessage(
                                command.get("lintasMinat", i, "lintasMinat"),
                                command.get("lintasMinat", i, "keterangan")+"\n\n"+
                                "Profil Ideal :\n"+
                                profilIdeal
                        ).setId(command.get("lintasMinat", i, "lintasMinat"))
                                .setTable("lintasMinat")
                                .setField("lintasMinat")
                );
            }else {
                command.addCol("Detail", Command.medText, 2, row2, 2, new AlertMessage(
                                command.get("lintasMinat", i, "lintasMinat"),
                                command.get("lintasMinat", i, "keterangan")
                        ).setId(command.get("lintasMinat", i, "lintasMinat"))
                                .setTable("lintasMinat")
                                .setField("lintasMinat")
                );
            }
            layout.addView(row2);
        }

    }

    public void tambahLintasMinat(View view) {
        Command.setINSERT();
        command.move(TambahDataLintasMinat.class, false);
    }

    @Override
    public void onBackPressed() {
        if(AppObject.admin != null){
            command.move(MenuAdmin.class, true);
        }else{
            command.move(MenuGuruBK.class, true);
        }
    }

    public void inputProfilIdeal(View view) {
        if(AppObject.kriteria != null){
            command.move(InputProfilIdeal.class, false);
        }else{
            command.setDataNotFound("Kriteria");
        }
    }

}
