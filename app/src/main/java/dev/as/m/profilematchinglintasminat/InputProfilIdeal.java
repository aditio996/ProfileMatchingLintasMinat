package dev.as.m.profilematchinglintasminat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class InputProfilIdeal extends AppCompatActivity {

    private Command command;
    private LinearLayout layout;
    private Spinner spLM;
    private ArrayList<Spinner> spKrit;
    private Button btnSetProfilIdeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_input_profil_ideal);
        layout = findViewById(R.id.layoutInputProfilIdeal);
        spLM = findViewById(R.id.spInputProfilIdealLM);
        btnSetProfilIdeal = new Button(this);
        spKrit = new ArrayList<>();
        ArrayList<String> lintasMinatArray = new ArrayList<>();
        lintasMinatArray.add("- Pilih Lintas Minat -");
        for(int i=0;i<AppObject.lintasMinat.length();i++){
            lintasMinatArray.add(command.get("lintasMinat", i, "lintasMinat"));
        }
        spLM.setAdapter(new ArrayAdapter<>(this, R.layout.simple_list, lintasMinatArray));
        for(int i=0;i<AppObject.aspek.length();i++){
            LinearLayout rowAspek = command.getRow();
            command.addCol("Aspek "+command.get("aspek", i, "namaAspek"), Command.medText, 1, rowAspek, 0, null);
            //rowAspek.setBackground(getResources().getDrawable(R.drawable.text_field));
            layout.addView(rowAspek);
            LinearLayout rowHeader = command.getRow();
            command.addCol("Kode\nKriteria", Command.smallText, 0.5f, rowHeader, 0, null);
            command.addCol("Nama\nKriteria", Command.medText, 2, rowHeader, 0, null);
            command.addCol("Profil\nIdeal", Command.medText, 2, rowHeader, 0, null);
            layout.addView(rowHeader);
            for(int j=0;j<AppObject.kriteria.length();j++){
                if(command.get("aspek", i, "idAspek").equals(command.get("kriteria", j, "idAspek"))) {
                    LinearLayout rowKrit = command.getRow();
                    command.addCol("K" + command.get("kriteria", j, "idKriteria"), Command.smallText, 0.5f, rowKrit, 1, null);
                    command.addCol(command.get("kriteria", j, "namaKriteria"), Command.smallText, 2, rowKrit, 1, null);
                    spKrit.add(command.addColSubKriteria(2, rowKrit, j, Command.smallText));
                    layout.addView(rowKrit);
                }
            }
            command.addSpace(layout, 10);
        }
        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.gravity = Gravity.END;
        btnSetProfilIdeal.setLayoutParams(lp);
        btnSetProfilIdeal.setText("Set Profil Ideal");*/
        btnSetProfilIdeal = command.getButton("Set Profil Ideal");
        btnSetProfilIdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfilIdeal();
            }
        });
        layout.addView(btnSetProfilIdeal);
    }

    public void setProfilIdeal() {
        boolean lengkap = true;
        String[] profilIdealArray = new String[spKrit.size()];
        for(int i=0;i<spKrit.size();i++){
            String s = spKrit.get(i).getSelectedItem().toString();
            if(spKrit.get(i).getSelectedItemPosition() != 0) profilIdealArray[i] = s.substring(s.indexOf("[")+1, s.indexOf("]"));
            if(spKrit.get(i).getSelectedItemPosition() == 0){
                lengkap = false;
                break;
            }
        }
        if(spLM.getSelectedItemPosition() == 0)lengkap = false;
        if(lengkap){
            String lintasMinat = spLM.getSelectedItem().toString();
            String profilIdeal = command.serialize(profilIdealArray);
            new Connect("update", "update lintasminat set " +
                    "profilIdeal='" + profilIdeal + "' "+
                    "where lintasMinat='" + lintasMinat + "'", InputProfilIdeal.this)
                    .addIntent(MenuGuruBK.class, true, "Data Profil Ideal Telah diinput")
                    .add("lintasMinat", "lintasMinat", spLM.getSelectedItem().toString());
            ;
        }else{
            command.setMessage("Data belum lengkap");
        }
    }
}
