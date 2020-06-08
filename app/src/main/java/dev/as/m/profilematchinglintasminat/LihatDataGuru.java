package dev.as.m.profilematchinglintasminat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class LihatDataGuru extends AppCompatActivity {

    private Command command;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_lihat_data_guru);
        layout = findViewById(R.id.layoutGuru);

        LinearLayout row = command.getRow();
        command.addCol("No", Command.smallText, 0.1f, row, 0,null);
        command.addCol("Nama\nGuru", Command.smallText, 0.5f, row, 0,null);
        //command.addCol("Username", Command.smallText, 0.2f, row, 0,null);
        //command.addCol("Password", Command.smallText, 0.2f, row, 0,null);
        command.addCol("Jabatan", Command.smallText, 0.25f, row, 0,null);
        command.addCol("Aksi", Command.smallText, 0.15f, row, 0, null);
        layout.addView(row);
        int number = 1;
        if(AppObject.guruBK != null) {
            for (int i = 0; i < AppObject.guruBK.length(); i++) {
                LinearLayout row2 = command.getRow();
                row2.setBackgroundResource(R.drawable.table_data);
                command.addCol("" + (number), Command.smallText, 0.1f, row2, 1, null);
                command.addCol(command.get("gurubk", i, "namaGuruBK"), Command.smallText, 0.5f, row2, 1, null);
                command.addCol("BK", Command.smallText, 0.25f, row2, 1, null);
                command.addCol("Detail", Command.smallText, 0.15f, row2, 2, new AlertMessage(
                                command.get("gurubk", i, "namaGuruBK"),
                                "Username : " + command.get("guruBK", i, "username") + "\n" +
                                        "Password : " + command.get("guruBK", i, "password") + "\n" +
                                        "Jabatan : Guru BK"
                        ).setId(command.get("gurubk", i, "idGuruBK"))
                                .setTable("guruBK")
                                .setField("idGuruBK")
                );
                command.setTarget(TambahDataGuru.class, false);
                layout.addView(row2);
                number++;
            }
        }
        if(AppObject.guru != null) {
            for (int i = 0; i < AppObject.guru.length(); i++) {
                LinearLayout row2 = command.getRow();
                row2.setBackgroundResource(R.drawable.table_data);
                command.addCol("" + (number), Command.smallText, 0.1f, row2, 1, null);
                command.addCol(command.get("guru", i, "namaGuru"), Command.smallText, 0.5f, row2, 1, null);
                command.addCol("Wali Kelas", Command.smallText, 0.25f, row2, 1, null);
                command.addCol("Detail", Command.smallText, 0.15f, row2, 2, new AlertMessage(
                                command.get("guru", i, "namaGuru"),
                                "Username : " + command.get("guru", i, "username") + "\n" +
                                        "Password : " + command.get("guru", i, "password") + "\n" +
                                        "Jabatan : Guru Wali Kelas\n" +
                                        "Kelas : "+command.get("guru", i, "kelas")
                        ).setId(command.get("guru", i, "idGuru"))
                                .setTable("guru")
                                .setField("idGuru")
                );
                command.setTarget(TambahDataGuru.class, false);
                layout.addView(row2);
                number++;
            }
        }
    }

    public void tambahGuru(View view) {
        Command.setINSERT();
        command.move(TambahDataGuru.class, false);
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
