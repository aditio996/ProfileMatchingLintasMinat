package dev.as.m.profilematchinglintasminat;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InputNilaiSiswa extends AppCompatActivity {

    int layer = 0;
    public static final int PILIH_SISWA = 0;
    public static final int INPUT_NILAI = 1;
    public static final int HASIL = 2;
    public static final int DETAIL_PERHITUNGAN = 3;
    TextView judul;
    LinearLayout layout;
    Command command;
    private ArrayList<Spinner> spProfilSiswa;
    //private Spinner spMinatSiswa, spMinatOrtu;
    //private EditText citaCita;
    String idSiswa;
    private TotalAkhir totalAkhir;
    private boolean ada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_input_nilai_siswa);
        judul = findViewById(R.id.judulInputNilaiSiswa);
        layout = findViewById(R.id.layoutInputNilaiSiswa);

        if(AppObject.siswaOnLogin == null) {
            if (layer == PILIH_SISWA) {
                if(AppObject.guruBK != null) {
                    if (AppObject.idKelasTerpilih == null || AppObject.idKelasTerpilih.equals("null") || AppObject.idKelasTerpilih.equals(""))
                        AppObject.idKelasTerpilih = command.get("kelas", 0, "idKelas");
                }
                if(AppObject.guruOnLogin != null) {
                    AppObject.idKelasTerpilih = command.getByKelas(command.get("guruOnLogin", 0, "kelas"), "kelas", "idKelas");
                }
                showPilihSiswa(AppObject.idKelasTerpilih);
            } else if (layer == INPUT_NILAI) {
                showInputNilai();
            } else if (layer == HASIL) {
                showHasil();
            } else if (layer == DETAIL_PERHITUNGAN) {
                showHitung();
            }
        }else{
            //idSiswa = String.valueOf(getIntent().getExtras().getString("idSiswa"));
            idSiswa = command.get("siswaOnLogin", 0, "idSiswa");
            int[] profilSiswaArray = new int[AppObject.kriteria.length()];
            String jsonStringSiswa = command.get("siswaOnLogin", 0, "nilaiProfilSiswa");
            for(int j=0;j<AppObject.kriteria.length();j++){
                profilSiswaArray[j] = command.toInt(command.unserialize(jsonStringSiswa, j, AppObject.kriteria.length()));
            }
            layer = HASIL;
            theProfilTemp = command.serialize(profilSiswaArray);
            new Waiting(1).setProfilSiswa(profilSiswaArray);
        }
    }

    private String theProfilTemp = "";

    private void showPilihSiswa(String idKelasTerpilih){
        layout.removeAllViews();
        String kelas = command.getById(idKelasTerpilih, "kelas", "kelas");
        if(AppObject.guruOnLogin != null) {
            idKelasTerpilih = command.getByKelas(command.get("guruOnLogin", 0, "kelas"), "kelas", "idKelas");
        }
        judul.setText("Pilih Siswa Kelas "+command.getById(idKelasTerpilih, "kelas", "kelas"));
        LinearLayout rowPilihKelas = command.getRow();
        //xx
        final Spinner spinner = new Spinner(this);
        ArrayList<String> pilihlahKelasnya = new ArrayList<>();
        pilihlahKelasnya.add("- Pilih Kelas -");
        for(int i = 0;i<AppObject.kelas.length();i++){
            //xx
            pilihlahKelasnya.add(command.get("kelas", i, "kelas"));
/*
            TextView colPilihKelas = command.getCol(command.get("kelas", i, "kelas"), Command.smallText, 0.12f, rowPilihKelas, -2, null);
            colPilihKelas.setTextSize(10);
            colPilihKelas.setPadding(0, 20, 0, 20);
            if(colPilihKelas.getText().toString().equals(kelas))colPilihKelas.setBackground(getResources().getDrawable(R.drawable.border2_pressed));
            final int finalI = i;
            colPilihKelas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppObject.idKelasTerpilih = command.get("kelas", finalI, "idKelas");
                    showPilihSiswa(command.get("kelas", finalI, "idKelas"));
                }
            });*/
        }
        //xx
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, pilihlahKelasnya));
        rowPilihKelas.addView(spinner);
        rowPilihKelas.setGravity(Gravity.RIGHT);
        //TextView refreshKelas = command.getCol("Refresh", 12, 0.5f, rowPilihKelas, 1, null);
        Button refreshKelas = command.getButton("Refresh", Gravity.END, Command.WRAP);
        rowPilihKelas.addView(refreshKelas);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AppObject.idKelasTerpilih = command.get("kelas", i-2, "idKelas");
                //if(AppObject.idKelasTerpilih.equals("null")){
                //    AppObject.idKelasTerpilih = "0";
                //}
                //AppObject.idKelasTerpilih = String.valueOf(command.toInt(AppObject.idKelasTerpilih) - 1);
                //command.setMessage(AppObject.idKelasTerpilih);
                //showPilihSiswa(command.get("kelas", adapterView.getSelectedItemPosition(), "idKelas"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        refreshKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AppObject.idKelasTerpilih = command.get("kelas", adapterView.getSelectedItemPosition(), "idKelas");
                if(spinner.getSelectedItemPosition() != 0) {
                    if(AppObject.idKelasTerpilih.equals("null")){
                        AppObject.idKelasTerpilih = command.get("kelas", 0, "idKelas");
                        showPilihSiswa(AppObject.idKelasTerpilih);
                    }else {
                        AppObject.idKelasTerpilih = command.get("kelas", Integer.parseInt(AppObject.idKelasTerpilih), "idKelas");
                        showPilihSiswa(AppObject.idKelasTerpilih);
                    }
                }else{
                    command.setMessage("Kelas Belum dipilih");
                }
            }
        });


        if(AppObject.guruOnLogin != null) {
            rowPilihKelas.setVisibility(View.GONE);
        }
        layout.addView(rowPilihKelas);
        command.addSpace(layout, 10);
        //command.setMessage(AppObject.guruBK+"|"+AppObject.guruOnLogin+"|"+AppObject.siswaOnLogin);
        //CONDITION HERE
        if(AppObject.guruBK != null) {
            for (int h = 0; h < AppObject.kelas.length(); h++) {
                if (idKelasTerpilih.equals(command.get("kelas", h, "idKelas"))) {
                    LinearLayout kelasRow = command.getRow();
                    command.addCol("Kelas : " + command.get("kelas", h, "kelas"), Command.medText, 1, kelasRow, 0, null);
                    layout.addView(kelasRow);
                    LinearLayout row = command.getRow();
                    float w0 = 0.05f;
                    float w1 = 0.3f;
                    //float w2 = 0.25f;
                    float w3 = 0.3f;
                    float w4 = 0.35f;
                    command.addCol("No", Command.microText, w0, row, 0, null);
                    command.addCol("Nama Siswa", Command.smallText, w1, row, 0, null);
                    //command.addCol("NLM", Command.smallText, w2, row, 0, null);
                    command.addCol("Rekomendasi", Command.smallText, w3, row, 0, null);
                    command.addCol("Aksi", Command.smallText, w4, row, 0, null);
                    layout.addView(row);
                    int count = 0;
                    for (int i = 0; i < AppObject.siswa.length(); i++) {
                        if (command.get("kelas", h, "kelas").equals(command.get("siswa", i, "kelas"))) {
                            LinearLayout row2 = command.getRow();
                            row2.setBackgroundResource(R.drawable.table_data);
                            String nlm = "";
                            /*if (!command.get("siswa", i, "nlm").equals("")) {
                                String serializedNLM = command.get("siswa", i, "nlm");
                                int counterLM = 0;
                                for (int x = 0; x < AppObject.lintasMinat.length(); x++) {
                                    String peminatan = command.getByKelas(command.get("siswa", i, "kelas"), "kelas", "peminatan");
                                    if (peminatan.equals("IPS")) {
                                        if (command.get("lintasMinat", x, "lintasMinat").equals("B.Inggris") || command.get("lintasMinat", x, "lintasMinat").equals("B.Jepang")) {
                                            //System.err.println(counterLM);
                                            //nlm+="NLM"+(x+1)+" = "+command.unserialize(serializedNLM, x, AppObject.lintasMinat.length());
                                            nlm += command.get("lintasMinat", x, "lintasMinat") + " = " + command.unserialize(serializedNLM, counterLM, 2);
                                            if (x < AppObject.lintasMinat.length() - 1) nlm += "\n";
                                            counterLM++;
                                        }
                                    } else {
                                        //nlm+="NLM"+(x+1)+" = "+command.unserialize(serializedNLM, x, AppObject.lintasMinat.length());
                                        nlm += command.get("lintasMinat", x, "lintasMinat") + " = " + command.unserialize(serializedNLM, x, AppObject.lintasMinat.length());
                                        if (x < AppObject.lintasMinat.length() - 1) nlm += "\n";
                                    }
                                }
                            } else {
                                nlm = "    -    ";
                            }*/
                            command.addCol("" + (count + 1), Command.microText, w0, row2, 1, null);
                            command.addCol(command.get("siswa", i, "namaSiswa"), Command.microText, w1, row2, 1, null);
                            //command.addCol(nlm, Command.microText, w2, row2, 1, null);
                            String rekomendasiLintasMinat = command.get("siswa", i, "rekomendasiLintasMinat");
                            if(rekomendasiLintasMinat.equals("null")){
                                rekomendasiLintasMinat = "";
                            }
                            if (!rekomendasiLintasMinat.equals("")) {
                                TextView tvRekomendasi = command.getCol(rekomendasiLintasMinat, Command.smallText, w3, row2, 1, null);
                                tvRekomendasi.setGravity(Gravity.CENTER);
                            } else {
                                rekomendasiLintasMinat = "Belum Diproses";
                                TextView tvRekomendasi = command.getCol(rekomendasiLintasMinat, Command.smallText, w3, row2, 1, null);
                                tvRekomendasi.setTextColor(Color.RED);
                                tvRekomendasi.setGravity(Gravity.CENTER);
                            }

                            //command.setTarget(TambahDataSiswa.class, false);
                            TextView colAction = command.getCol("Proses", Command.smallText2, w4/2, row2, -2, null);
                            TextView colAction2 = command.getCol("Detail", Command.smallText2, w4/2, row2, -2, null);

                            rekomendasiLintasMinat = command.get("siswa", i, "rekomendasiLintasMinat");
                            //if (!rekomendasiLintasMinat.equals("")) colAction.setText("Detail");
                            //colAction.setTextSize(14);
                            final int finalI = i;
                            final String finalRekomendasiLintasMinat = rekomendasiLintasMinat;
                            colAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AppObject.MASKED = AppObject.PROSES;
                                    //idSiswa = command.get("siswa", finalI, "idSiswa");
                                    idSiswa = command.get("siswa", finalI, "idSiswa");
                                    if (/*finalRekomendasiLintasMinat.equals("") &&*/ command.get("siswa", finalI, "nilaiProfilSiswa").equals("") || command.get("siswa", finalI, "nilaiProfilSiswa").equals("null")) {
                                        //layer = INPUT_NILAI;
                                        //new Waiting(1);
                                        command.setMessage("Nilai untuk "+command.getById(idSiswa, "siswa", "namaSiswa")+" belum diinput oleh guru");
                                    } else {
                                        ada = true;
                                        int[] profilSiswaArray = new int[AppObject.kriteria.length()];
                                        String jsonStringSiswa = command.getById(idSiswa, "siswa", "nilaiProfilSiswa");
                                        for (int j = 0; j < AppObject.kriteria.length(); j++) {
                                            profilSiswaArray[j] = command.toInt(command.unserialize(jsonStringSiswa, j, AppObject.kriteria.length()));
                                        }
                                        layer = HASIL;
                                        new Waiting(1).setProfilSiswa(profilSiswaArray);
                                        //new Waiting(1);
                                    }
                                    /*
                                    idSiswa = command.get("siswa", finalI, "idSiswa");
                                    if (finalRekomendasiLintasMinat.equals("") && command.get("siswa", finalI, "nilaiProfilSiswa").equals("")) {
                                        //layer = INPUT_NILAI;
                                        //new Waiting(1);
                                        command.setMessage("Nilai untuk "+command.getById(idSiswa, "siswa", "namaSiswa")+" belum diinput oleh guru");
                                    } else {
                                        ada = true;
                                        int[] profilSiswaArray = new int[AppObject.kriteria.length()];
                                        String jsonStringSiswa = command.getById(idSiswa, "siswa", "nilaiProfilSiswa");
                                        for (int j = 0; j < AppObject.kriteria.length(); j++) {
                                            profilSiswaArray[j] = command.toInt(command.unserialize(jsonStringSiswa, j, AppObject.kriteria.length()));
                                        }
                                        layer = HASIL;
                                        new Waiting(1).setProfilSiswa(profilSiswaArray);
                                        //new Waiting(1);
                                    }
                                    */
                                }
                            });
                            colAction2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AppObject.MASKED = AppObject.DETAIL;
                                    idSiswa = command.get("siswa", finalI, "idSiswa");
                                    if (/*finalRekomendasiLintasMinat.equals("") &&*/ command.get("siswa", finalI, "nilaiProfilSiswa").equals("") || command.get("siswa", finalI, "nilaiProfilSiswa").equals("null")) {
                                        //layer = INPUT_NILAI;
                                        //new Waiting(1);
                                        command.setMessage("Nilai untuk "+command.getById(idSiswa, "siswa", "namaSiswa")+" belum diinput oleh guru");
                                    } else {
                                        if(finalRekomendasiLintasMinat.equals("") || finalRekomendasiLintasMinat.equals("null")){
                                            command.setMessage("Nilai untuk "+command.getById(idSiswa, "siswa", "namaSiswa")+" belum Diproses");
                                        }else {
                                            ada = true;
                                            int[] profilSiswaArray = new int[AppObject.kriteria.length()];
                                            String jsonStringSiswa = command.getById(idSiswa, "siswa", "nilaiProfilSiswa");
                                            for (int j = 0; j < AppObject.kriteria.length(); j++) {
                                                profilSiswaArray[j] = command.toInt(command.unserialize(jsonStringSiswa, j, AppObject.kriteria.length()));
                                            }
                                            layer = HASIL;
                                            new Waiting(1).setProfilSiswa(profilSiswaArray);
                                            //new Waiting(1);
                                        }
                                    }

                                }
                            });
                            layout.addView(row2);
                            count++;
                        }
                    }
                    if (count == 0) {
                        layout.removeView(kelasRow);
                        layout.removeView(row);
                    }
                    command.addSpace(layout, 10);
                }

            }
        }else if(AppObject.guruOnLogin != null){
            for (int h = 0; h < AppObject.kelas.length(); h++) {
                if (idKelasTerpilih.equals(command.get("kelas", h, "idKelas"))) {
                    LinearLayout kelasRow = command.getRow();
                    command.addCol("Kelas : " + command.get("kelas", h, "kelas"), Command.medText, 1, kelasRow, 0, null);
                    layout.addView(kelasRow);
                    LinearLayout row = command.getRow();
                    float w0 = 0.1f;
                    float w1 = 0.45f;
                    float w2 = 0.3f;
                    //float w3 = 0.25f;
                    float w4 = 0.15f;
                    command.addCol("No", Command.smallText, w0, row, 0, null);
                    command.addCol("Nama Siswa", Command.smallText, w1, row, 0, null);
                    command.addCol("Nilai Profil\nSiswa", Command.smallText, w2, row, 0, null);
                    //command.addCol("Rekomendasi", Command.smallText, w3, row, 0, null);
                    command.addCol("Aksi", Command.smallText, w4, row, 0, null);
                    layout.addView(row);
                    int count = 0;
                    for (int i = 0; i < AppObject.siswa.length(); i++) {
                        if (command.get("kelas", h, "kelas").equals(command.get("siswa", i, "kelas"))) {
                            LinearLayout row2 = command.getRow();
                            row2.setBackgroundResource(R.drawable.table_data);
                            /*String nlm = "";
                            if (!command.get("siswa", i, "nlm").equals("") || !command.get("siswa", i, "nlm").equals("null")) {//sasa
                                String serializedNLM = command.get("siswa", i, "nlm");
                                int counterLM = 0;
                                for (int x = 0; x < AppObject.lintasMinat.length(); x++) {
                                    String peminatan = command.getByKelas(command.get("siswa", i, "kelas"), "kelas", "peminatan");
                                    if (peminatan.equals("IPS")) {
                                        if (command.get("lintasMinat", x, "lintasMinat").equals("B.Inggris") || command.get("lintasMinat", x, "lintasMinat").equals("B.Jepang")) {
                                            //System.err.println(counterLM);
                                            //nlm+="NLM"+(x+1)+" = "+command.unserialize(serializedNLM, x, AppObject.lintasMinat.length());
                                            nlm += command.get("lintasMinat", x, "lintasMinat") + " = " + command.unserialize(serializedNLM, counterLM, 2);
                                            if (x < AppObject.lintasMinat.length() - 1) nlm += "\n";
                                            counterLM++;
                                        }
                                    } else {
                                        //nlm+="NLM"+(x+1)+" = "+command.unserialize(serializedNLM, x, AppObject.lintasMinat.length());
                                        nlm += command.get("lintasMinat", x, "lintasMinat") + " = " + command.unserialize(serializedNLM, x, AppObject.lintasMinat.length());
                                        if (x < AppObject.lintasMinat.length() - 1) nlm += "\n";
                                    }
                                }
                            } else {
                                nlm = "    -    ";
                            }*/
                            command.addCol("" + (count + 1), Command.smallText, w0, row2, 1, null);
                            command.addCol(command.get("siswa", i, "namaSiswa"), Command.smallText, w1, row2, 1, null);
                            //command.addCol(nlm, Command.microText, w2, row2, 1, null);
                            if(command.get("siswa", i, "nilaiProfilSiswa").equals("") || command.get("siswa", i, "nilaiProfilSiswa").equals("null")) {
                                //command.addCol("Belum diinput", Command.smallText, w2, row2, 1, null);
                                TextView tv1 = command.getCol("Belum diinput", Command.smallText, w2, row2, 1, null);
                                tv1.setTextColor(Color.RED);
                            }else{
                                TextView tv1 = command.getCol("Sudah diinput", Command.smallText, w2, row2, 1, null);
                                tv1.setTextColor(Color.parseColor("#339933"));
                            }
                            /*//String rekomendasiLintasMinat = command.get("siswa", i, "rekomendasiLintasMinat");

                            if (!rekomendasiLintasMinat.equals("")) {
                                //TextView tvRekomendasi = command.getCol(rekomendasiLintasMinat, Command.smallText, w3, row2, 1, null);
                                //tvRekomendasi.setGravity(Gravity.CENTER);
                            } else {
                                rekomendasiLintasMinat = "Nilai Belum Diinput";
                                //TextView tvRekomendasi = command.getCol(rekomendasiLintasMinat, Command.smallText, w3, row2, 1, null);
                                //tvRekomendasi.setTextColor(Color.RED);
                                //tvRekomendasi.setGravity(Gravity.CENTER);
                            }

                            //command.setTarget(TambahDataSiswa.class, false);*/
                            TextView colAction = command.getCol("Input Nilai", Command.medText, w4, row2, -2, null);
                            //rekomendasiLintasMinat = command.get("siswa", i, "rekomendasiLintasMinat");
                            if (!command.get("siswa", i, "nilaiProfilSiswa").equals("") || !command.get("siswa", i, "nilaiProfilSiswa").equals("null")) colAction.setText("Detail");
                            colAction.setTextSize(14);
                            final int finalI = i;
                            String finalRekomendasiLintasMinat ="";
                            if (command.get("siswa", i, "nilaiProfilSiswa").equals("null")){
                                finalRekomendasiLintasMinat = "";
                            }else {
                                finalRekomendasiLintasMinat = command.get("siswa", i, "nilaiProfilSiswa");
                            }

                            final String finalRekomendasiLintasMinat1 = finalRekomendasiLintasMinat;
                            colAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    idSiswa = command.get("siswa", finalI, "idSiswa");
                                    if (finalRekomendasiLintasMinat1.equals("")) {
                                        layer = INPUT_NILAI;
                                        new Waiting(1);
                                    } else {
                                        ada = true;
                                        int[] profilSiswaArray = new int[AppObject.kriteria.length()];
                                        String jsonStringSiswa = command.getById(idSiswa, "siswa", "nilaiProfilSiswa");
                                        for (int j = 0; j < AppObject.kriteria.length(); j++) {
                                            profilSiswaArray[j] = command.toInt(command.unserialize(jsonStringSiswa, j, AppObject.kriteria.length()));
                                        }
                                        layer = HASIL;
                                        new Waiting(1).setProfilSiswa(profilSiswaArray);
                                        //new Waiting(1);
                                    }
                                }
                            });
                            layout.addView(row2);
                            count++;
                        }
                    }
                    if (count == 0) {
                        layout.removeView(kelasRow);
                        layout.removeView(row);
                    }
                    command.addSpace(layout, 10);
                }

            }
        }
        //END CONDITION HERE
    }

    void showInputNilai(){
        judul.setText("Input Nilai Siswa");
        spProfilSiswa = new ArrayList<>();
        layout.removeAllViews();
        LinearLayout rowSiswa = command.getRow();
        TextView tvSiswa = command.getCol("" +
                "Kode Siswa : S"+command.getById(idSiswa, "siswa", "idSiswa")+"\n" +
                "NIS : "+command.getById(idSiswa, "siswa", "nis")+"\n" +
                "Nama Siswa : "+command.getById(idSiswa, "siswa", "namaSiswa")+"\n" +
                "Kelas : "+command.getById(idSiswa, "siswa", "kelas")+"\n" +
                "Jenis Kelamin : "+command.getById(idSiswa, "siswa", "jenisKelamin")+"" +
                ""
                , Command.medText, 1, rowSiswa, 1, null);
        tvSiswa.setBackground(getResources().getDrawable(R.drawable.text_field));
        layout.addView(rowSiswa);
        command.addSpace(layout, 10);


        /*LinearLayout rowHeadPilihMinat0 = command.getRow();
        command.addCol("Hasil Angket", Command.smallText, 1, rowHeadPilihMinat0, 100, null);
        layout.addView(rowHeadPilihMinat0);
        LinearLayout rowHeadPilihMinat = command.getRow();
        command.addCol("Minat Siswa", Command.medText, 1, rowHeadPilihMinat, 0, null);
        command.addCol("Minat Ortu", Command.medText, 1, rowHeadPilihMinat, 0, null);
        layout.addView(rowHeadPilihMinat);
        LinearLayout rowPilihMinat = command.getRow();
        spMinatSiswa = command.addColLintasMinat(1, rowPilihMinat, Command.medText, "- Pilih Minat Siswa -");
        spMinatOrtu = command.addColLintasMinat(1, rowPilihMinat, Command.medText, "- Pilih Minat Ortu -");
        layout.addView(rowPilihMinat);
        LinearLayout rowTutup0 = command.getRow();
        command.addCol("", Command.smallText, 1, rowTutup0, 200, null);
        layout.addView(rowTutup0);
        command.addSpace(layout, 10);*/



        for(int i=0;i<AppObject.aspek.length();i++){
            LinearLayout rowAspek = command.getRow();
            command.addCol("[A"+command.get("aspek", i, "idAspek")+"]"+" Aspek "+command.get("aspek", i, "namaAspek")+"", Command.medText, 1, rowAspek, 100, null);
            layout.addView(rowAspek);
            LinearLayout rowHead = command.getRow();
            command.addCol("Kriteria", Command.medText, 1, rowHead, 0, null);
            command.addCol("Nilai Profil\nSiswa", Command.medText, 1, rowHead, 0, null);
            layout.addView(rowHead);
            for(int j=0;j<AppObject.kriteria.length();j++){
                if(command.get("kriteria", j, "idAspek").equals(command.get("aspek", i, "idAspek"))){
                    LinearLayout rowData = command.getRow();
                    command.addCol("[K"+command.get("kriteria", j, "idKriteria")+"] "+command.get("kriteria", j, "namaKriteria"), Command.smallText2, 1, rowData, 1, null);
                    //command.addCol(command.get("kriteria", j, "namaKriteria"), Command.medText, 1, rowData, 1, null);
                    spProfilSiswa.add(command.addColSubKriteria(1, rowData, j, Command.smallText2));
                    if(totalAkhir != null){
                        if(totalAkhir.getProfilSiswa(j) == 5) spProfilSiswa.get(j).setSelection(SubKriteria.sub5+1);
                        else if(totalAkhir.getProfilSiswa(j) == 4) spProfilSiswa.get(j).setSelection(SubKriteria.sub4+1);
                        else if(totalAkhir.getProfilSiswa(j) == 3) spProfilSiswa.get(j).setSelection(SubKriteria.sub3+1);
                        else if(totalAkhir.getProfilSiswa(j) == 2) spProfilSiswa.get(j).setSelection(SubKriteria.sub2+1);
                        else if(totalAkhir.getProfilSiswa(j) == 1) spProfilSiswa.get(j).setSelection(SubKriteria.sub1+1);
                        else spProfilSiswa.get(j).setSelection(0);
                    }
                    layout.addView(rowData);
                }
            }
            LinearLayout rowTutup = command.getRow();
            command.addCol("", Command.smallText, 1, rowTutup, 200, null);
            layout.addView(rowTutup);
            command.addSpace(layout, 5);
        }
        Button btnSetNilai = command.getButton("Set Nilai");

        btnSetNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean lengkap = true;
                int[] profilSiswaArray = new int[spProfilSiswa.size()];
                for(int i=0;i<spProfilSiswa.size();i++){
                    if(spProfilSiswa.get(i).getSelectedItemPosition() == 0){
                        lengkap = false;
                        break;
                    }
                    String s = spProfilSiswa.get(i).getSelectedItem().toString();
                    profilSiswaArray[i] = command.toInt(s.substring(s.indexOf("[")+1, s.indexOf("]")));
                }
                //if (spMinatSiswa.getSelectedItemPosition() == 0 && spMinatOrtu.getSelectedItemPosition() == 0) lengkap = false;
                if (lengkap) {
                    layer = HASIL;
                    theProfilTemp = command.serialize(profilSiswaArray);
                    new Waiting(1).setProfilSiswa(profilSiswaArray);
                }else {
                    command.setMessage("Data nilai belum lengkap");
                }

            }
        });
        layout.addView(btnSetNilai);
    }

    private void showHasil(){
        ada = true;
        if(AppObject.guruOnLogin!= null) {
            judul.setText("Detail Nilai");
        }else{
            judul.setText("Hasil Perhitungan");
        }
        spProfilSiswa = new ArrayList<>();
        layout.removeAllViews();
        LinearLayout rowSiswa = command.getRow();
        TextView tvSiswa = command.getCol("" +
                        "Kode Siswa : S"+idSiswa+"\n" +
                        "NIS : "+command.getById(idSiswa, "siswa", "nis")+"\n" +
                        "Nama Siswa : "+command.getById(idSiswa, "siswa", "namaSiswa")+"\n" +
                        "Kelas : "+command.getById(idSiswa, "siswa", "kelas")+"\n" +
                        "Jenis Kelamin : "+command.getById(idSiswa, "siswa", "jenisKelamin")+"" +
                        ""
                , Command.medText, 1, rowSiswa, 1, null);
        String peminatan = command.getByKelas(command.getById(idSiswa, "siswa", "kelas"), "kelas", "peminatan");
        tvSiswa.setBackground(getResources().getDrawable(R.drawable.text_field));
        layout.addView(rowSiswa);
        command.addSpace(layout, 5);

        if(AppObject.guruOnLogin != null) {
            Button btnUpdateNilai = command.getButton("Update Nilai Profil");
            btnUpdateNilai.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnUpdateNilai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layer = INPUT_NILAI;
                    new Waiting(1);
                }
            });
            layout.addView(btnUpdateNilai);
        }

        if(AppObject.guruBK != null) {
            Button btnDetail = command.getButton("Lihat Detail Perhitungan");
            btnDetail.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layer = DETAIL_PERHITUNGAN;
                    new Waiting(1);
                }
            });
            if(AppObject.MASKED == AppObject.PROSES){
                btnDetail.setVisibility(View.GONE);
            }
            layout.addView(btnDetail);
            command.addSpace(layout, 5);

            if(AppObject.MASKED != AppObject.PROSES) {
                LinearLayout rowHeaderRekomendasi0 = command.getRow();
                command.addCol("Ranking Rekomendasi Lintas Minat", Command.medText, 1, rowHeaderRekomendasi0, 100, null);
                layout.addView(rowHeaderRekomendasi0);
                LinearLayout rowHeaderRekomendasi1 = command.getRow();
                command.addCol("Ranking", Command.smallText, 1, rowHeaderRekomendasi1, 0, null);
                command.addCol("Lintas Minat", Command.smallText, 2, rowHeaderRekomendasi1, 0, null);
                command.addCol("Nilai\nProfile Matching", Command.smallText, 1, rowHeaderRekomendasi1, 0, null);
                layout.addView(rowHeaderRekomendasi1);
                /* HIDE IT*/

                for (int i = 0; i < totalAkhir.getRankingArray().size(); i++) {
                    LinearLayout rowDataRekomendasi = command.getRow();
                    String namaLM = totalAkhir.getLintasMinatByRanking(i);
                    if (peminatan.equals("IPS")) {

                        if (command.get("lintasMinat", 0, "lintasMinat").equals(namaLM))
                            namaLM = "B.Inggris";
                        else namaLM = "B.Jepang";
                    }
                    command.addCol("" + (i + 1), Command.smallText, 1, rowDataRekomendasi, 1, null);
                    command.addCol("" + namaLM, Command.smallText, 2, rowDataRekomendasi, 1, null);
                    command.addCol("" + totalAkhir.getRanking(i), Command.smallText, 1, rowDataRekomendasi, 1, null);
                    layout.addView(rowDataRekomendasi);
                }
            }
        }


        if(AppObject.guruOnLogin == null) {
            if (AppObject.MASKED != AppObject.PROSES) {
                command.addSpace(layout, 5);
                if (AppObject.siswaOnLogin != null) {
                    command.addSpace(layout, 20);
                }
                LinearLayout rowRekomendasi = command.getRow();
                if (AppObject.siswaOnLogin != null) {
                    String lintasMinatTertinggi = totalAkhir.getLintasMinatTertinggi().toUpperCase();
                    if(peminatan.equals("IPS")){
                        for (int i = 0; i < totalAkhir.getRankingArray().size(); i++) {
                            String namaLM = totalAkhir.getLintasMinatByRanking(i);
                            //if (peminatan.equals("IPS")) {
                            if (command.get("lintasminat", 0, "lintasMinat").equals(namaLM))
                                lintasMinatTertinggi = "B.Jepang";
                            else lintasMinatTertinggi = "B.Inggris";
                            //}
                        }
                    }
                    command.addCol("Berdasarkan Perhitungan Profile Matching Saudara \"" + command.getById(idSiswa, "siswa", "namaSiswa") + "\" Direkomendasikan Memilih Lintas Minat\n\"" + lintasMinatTertinggi + "\"", Command.medText, 1, rowRekomendasi, 250, null);
                } else {
                    String lintasMinatTertinggi = totalAkhir.getLintasMinatTertinggi().toUpperCase();
                    if(peminatan.equals("IPS")){
                        for (int i = 0; i < totalAkhir.getRankingArray().size(); i++) {
                            String namaLM = totalAkhir.getLintasMinatByRanking(i);
                            //if (peminatan.equals("IPS")) {
                            if (command.get("lintasminat", 0, "lintasMinat").equals(namaLM))
                                lintasMinatTertinggi = "B.Jepang";
                            else lintasMinatTertinggi = "B.Inggris";
                            //}
                        }
                    }
                    command.addCol("Berdasarkan Perhitungan Profile Matching Saudara \"" + command.getById(idSiswa, "siswa", "namaSiswa") + "\" Direkomendasikan Memilih Lintas Minat\n\"" + lintasMinatTertinggi + "\"", Command.smallText, 1, rowRekomendasi, 200, null);
                }
                layout.addView(rowRekomendasi);
                command.addSpace(layout, 10);
            }
        }
        command.addSpace(layout, 5);
        if(AppObject.siswaOnLogin == null) {
            if(AppObject.guruOnLogin != null || (AppObject.guruBK != null && AppObject.MASKED == AppObject.PROSES)) {
                LinearLayout rowForSiswa = command.getRow();
                command.addCol("Nilai Yang Telah Diinput", Command.medText, 1, rowForSiswa, 100, null);
                layout.addView(rowForSiswa);
                LinearLayout rowForSiswa1 = command.getRow();
                command.addCol("Kode\nKriteria", Command.smallText, 0.15f, rowForSiswa1, 0, null);
                command.addCol("Kriteria", Command.smallText, 0.35f, rowForSiswa1, 0, null);
                command.addCol("Nilai Profil", Command.smallText, 0.4f, rowForSiswa1, 0, null);
                command.addCol("Bobot", Command.smallText, 0.1f, rowForSiswa1, 0, null);
                layout.addView(rowForSiswa1);
                for (int i = 0; i < AppObject.kriteria.length(); i++) {
                    LinearLayout rowNilai = command.getRow();
                    String profilSiswa = command.getById(idSiswa, "siswa", "nilaiProfilSiswa");
                    if (!theProfilTemp.equals("")) profilSiswa = theProfilTemp;

                    String subKriteria = command.get("kriteria", i, "subKriteria");
                    String bobot = command.unserialize(profilSiswa, i, AppObject.kriteria.length());
                    int j = 0;
                    switch (command.toInt(bobot)) {
                        case 5:
                            j = 0;
                            break;
                        case 4:
                            j = 1;
                            break;
                        case 3:
                            j = 2;
                            break;
                        case 2:
                            j = 3;
                            break;
                        case 1:
                            j = 4;
                            break;
                    }
                    String nilaiProfil = command.unserialize(subKriteria, j, SubKriteria.length);
                    command.addCol("K" + command.get("kriteria", i, "idKriteria"), Command.smallText, 0.15f, rowNilai, 1, null);
                    command.addCol(command.get("kriteria", i, "namaKriteria"), Command.smallText, 0.35f, rowNilai, 1, null);
                    command.addCol(nilaiProfil, Command.smallText, 0.4f, rowNilai, 1, null);
                    command.addCol(bobot, Command.smallText, 0.1f, rowNilai, 1, null);
                    layout.addView(rowNilai);
                }
                LinearLayout rowTutup = command.getRow();
                command.addCol("", Command.smallText, 1, rowTutup, 200, null);
                layout.addView(rowTutup);
                if (AppObject.guruBK != null && AppObject.MASKED == AppObject.PROSES) {
                    Button btnProsesLM = command.getButton("Proses Lintas Minat");
                    btnProsesLM.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    btnProsesLM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AppObject.MASKED = AppObject.DETAIL;
                            ada = true;
                            int[] profilSiswaArray = new int[AppObject.kriteria.length()];
                            String jsonStringSiswa = command.getById(idSiswa, "siswa", "nilaiProfilSiswa");
                            for (int j = 0; j < AppObject.kriteria.length(); j++) {
                                profilSiswaArray[j] = command.toInt(command.unserialize(jsonStringSiswa, j, AppObject.kriteria.length()));
                            }
                            layer = HASIL;
                            new Waiting(1).setProfilSiswa(profilSiswaArray);
                        }
                    });
                    layout.addView(btnProsesLM);
                }
                command.addSpace(layout, 5);
            }


            command.addSpace(layout, 100);
            /* HIDE IT*/
            /*if(AppObject.guruOnLogin != null) {
                //btnDetail.setVisibility(View.GONE);
                //rowHeaderRekomendasi1.setVisibility(View.GONE);
                //rowHeaderRekomendasi0.setVisibility(View.GONE);
                rowRekomendasi.setVisibility(View.GONE);
            }*/
        }else {
            /*
            LinearLayout rowForSiswa = command.getRow();
            command.addCol("Nilai Yang Telah Diinput", Command.medText, 1, rowForSiswa, 100, null);
            layout.addView(rowForSiswa);
            LinearLayout rowForSiswa1 = command.getRow();
            command.addCol("Kode\nKriteria", Command.smallText, 0.15f, rowForSiswa1, 0, null);
            command.addCol("Kriteria", Command.smallText, 0.35f, rowForSiswa1, 0, null);
            command.addCol("Nilai Profil", Command.smallText, 0.4f, rowForSiswa1, 0, null);
            command.addCol("Bobot", Command.smallText, 0.1f, rowForSiswa1, 0, null);
            layout.addView(rowForSiswa1);
            for(int i=0;i<AppObject.kriteria.length();i++){
                LinearLayout rowNilai = command.getRow();
                String profilSiswa = command.get("siswaOnLogin", 0, "nilaiProfilSiswa");
                String subKriteria = command.get("kriteria", i, "subKriteria");
                String bobot = command.unserialize(profilSiswa, i, AppObject.kriteria.length());
                int j=0;
                switch (command.toInt(bobot)){
                    case 5:
                        j = 0;
                        break;
                    case 4:
                        j = 1;
                        break;
                    case 3:
                        j = 2;
                        break;
                    case 2:
                        j = 3;
                        break;
                    case 1:
                        j = 4;
                        break;
                }
                String nilaiProfil = command.unserialize(subKriteria, j, SubKriteria.length);
                command.addCol("K"+command.get("kriteria", i, "idKriteria"), Command.smallText, 0.15f, rowNilai, 1, null);
                command.addCol(command.get("kriteria", i, "namaKriteria"), Command.smallText, 0.35f, rowNilai, 1, null);
                command.addCol(nilaiProfil, Command.smallText, 0.4f, rowNilai, 1, null);
                command.addCol(bobot, Command.smallText, 0.1f, rowNilai, 1, null);
                layout.addView(rowNilai);
            }
            LinearLayout rowTutup = command.getRow();
            command.addCol("", Command.smallText, 1, rowTutup, 200, null);
            layout.addView(rowTutup);
            */
            command.addSpace(layout, 100);
        }
    }

    private void showHitung(){
        judul.setText("Detail Perhitungan Profile Matching");
        spProfilSiswa = new ArrayList<>();
        layout.removeAllViews();
        LinearLayout rowSiswa = command.getRow();
        TextView tvSiswa = command.getCol("" +
                        "Kode Siswa : S"+idSiswa+"\n" +
                        "NIS : "+idSiswa+"\n" +
                        "Nama Siswa : "+command.getById(idSiswa, "siswa", "namaSiswa")+"\n" +
                        "Kelas : "+command.getById(idSiswa, "siswa", "kelas")+"\n" +
                        "Jenis Kelamin : "+command.getById(idSiswa, "siswa", "jenisKelamin")+"" +
                        ""
                , Command.medText, 1, rowSiswa, 1, null);
        tvSiswa.setBackground(getResources().getDrawable(R.drawable.text_field));
        layout.addView(rowSiswa);
        command.addSpace(layout, 5);
        float w0 = 0.2f;
        float w1 = 1;
        int counter = 0;
        String persenAspekString = "";
        String peminatan = command.getByKelas(command.getById(idSiswa, "siswa", "kelas"), "kelas", "peminatan");
        int counterLM = 0;
        for(int i=0;i<AppObject.lintasMinat.length();i++){
            String namaLM = command.get("lintasMinat", counterLM, "lintasMinat");
            if(peminatan.equals("IPS")){
                if(!command.get("lintasMinat", i, "lintasMinat").equals("B.Inggris") && !command.get("lintasMinat", i, "lintasMinat").equals("B.Jepang"))
                    continue;
                else {
                    if (counterLM == 0) namaLM = "B.Inggris";
                    else namaLM = "B.Jepang";
                }
            }
            //start
            w0 = 0.2f;
            w1 = 1;
            LinearLayout rowLM = command.getRow();
            command.addCol(namaLM+"", Command.medText, 1, rowLM, 100, null);
            LinearLayout rowNilaiTotalLM = command.getRow();
            TextView tvNilaiTotalLM = command.getCol("Nilai Total Lintas Minat = "+totalAkhir.getTotalNilai(counterLM), Command.medText, 1, rowNilaiTotalLM, 1, null);
            tvNilaiTotalLM.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
            if(namaLM.equals(totalAkhir.getLintasMinatTertinggi())){
                tvNilaiTotalLM.setTextColor(Color.parseColor("#339933"));
            }
            layout.addView(rowLM);
            layout.addView(rowNilaiTotalLM);
            for(int j=0;j<AppObject.aspek.length();j++){
                String idAspek = command.get("aspek", j, "idAspek");
                LinearLayout rowAspek = command.getRow();
                command.addCol(namaLM+" | " +
                        "Aspek "+command.get("aspek", j, "namaAspek")+"" +
                        " [A"+command.get("aspek", j, "idAspek")+"] ", Command.microText, 1, rowAspek, 200, null);
                layout.addView(rowAspek);
                LinearLayout rowKriteriaHeader = command.getRow();
                command.addCol("Kriteria", Command.microText, w0, rowKriteriaHeader, 0, null);
                float jumlahKriteria = 0;
                for(int k=0;k<AppObject.kriteria.length();k++){
                    if(command.get("kriteria", k, "idAspek").equals(idAspek)) {
                        jumlahKriteria++;
                    }
                }
                w1 = 1 / jumlahKriteria;
                for(int k=0;k<AppObject.kriteria.length();k++){
                    if(command.get("kriteria", k, "idAspek").equals(idAspek)) {
                        command.addCol(
                                "K"+command.get("kriteria", k, "idKriteria")+"\n" +
                                        " ["+command.get("kriteria", k, "jenisKriteria")+"]"
                                , Command.microText, w1, rowKriteriaHeader, 0, null);
                    }
                }
                layout.addView(rowKriteriaHeader);

                int indexLintasMinat = command.getIndex("lintasMinat", "lintasMinat", namaLM);
                if(peminatan.equals("IPS")){
                    if(namaLM.equals("B.Inggris"))indexLintasMinat = 0;
                    else indexLintasMinat = 1;
                }
                int indexAspek = command.getIndex("aspek", "idAspek", idAspek);
                int mulaiKriteria = indexLintasMinat * AppObject.kriteria.length() + indexAspek;
                int akhirKriteria = mulaiKriteria + AppObject.kriteria.length();
                //if (counterLM==0&&j==1)command.setMessage(mulaiKriteria+" - "+akhirKriteria);

                LinearLayout rowProfilSiswa = command.getRow();
                command.addCol("NPS", Command.microText, w0, rowProfilSiswa, 0, null);

                LinearLayout rowProfilIdeal = command.getRow();
                command.addCol("NPI", Command.microText, w0, rowProfilIdeal, 0, null);

                LinearLayout rowGap = command.getRow();
                command.addCol("Gap", Command.microText, w0, rowGap, 0, null);

                LinearLayout rowBobotGap = command.getRow();
                command.addCol("bGap", Command.microText, w0, rowBobotGap, 0, null);
                float wCF = 1;
                for(int k=mulaiKriteria;k<akhirKriteria;k++){
                    if(command.get("kriteria", k%AppObject.kriteria.length(), "idAspek").equals(idAspek)) {
                        TextView tv = command.getCol(""+totalAkhir.getProfilSiswa(k), Command.microText, w1, rowProfilSiswa, 1, null);
                        tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                        TextView tvProfilIdeal = command.getCol(""+totalAkhir.getProfilIdeal(k), Command.microText, w1, rowProfilIdeal, 0, null);
                        tvProfilIdeal.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                        TextView tvGap = command.getCol(""+totalAkhir.getGap(k), Command.microText, w1, rowGap, 1, null);
                        tvGap.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                        TextView tvBobotGap = command.getCol(""+totalAkhir.getBobotGap(k), Command.microText, w1, rowBobotGap, 1, null);
                        tvBobotGap.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                        tvBobotGap.setTextColor(Color.parseColor("#993333"));
                        //wCF++;
                    }
                }
                layout.addView(rowProfilSiswa);
                layout.addView(rowProfilIdeal);
                layout.addView(rowGap);
                layout.addView(rowBobotGap);

                int indexPerhitungan = indexLintasMinat * AppObject.aspek.length() + indexAspek;

                LinearLayout rowCF = command.getRow();
                command.addCol("NCF", Command.microText, w0, rowCF, 0, null);
                TextView tvCF = command.getCol("" +
                        ""+totalAkhir.getJumlahBobotGapCF(indexPerhitungan)+" / " +getJumlahCF(idAspek)+ " = "+
                        ""+totalAkhir.getTotalCF(indexPerhitungan)+"", Command.microText, wCF, rowCF, 1, null);
                tvCF.setPadding(5, 0, 0, 0);

                LinearLayout rowSF = command.getRow();
                command.addCol("NSF", Command.microText, w0, rowSF, 0, null);
                TextView tvSF = command.getCol("" +
                        ""+totalAkhir.getJumlahBobotGapSF(indexPerhitungan)+" / " +getJumlahSF(idAspek)+ " = "+
                        ""+totalAkhir.getTotalSF(indexPerhitungan)+"", Command.microText, wCF, rowSF, 1, null);
                tvSF.setPadding(5, 0,0,0);

                LinearLayout rowNTA = command.getRow();
                command.addCol("NA"+idAspek, Command.microText, w0, rowNTA, 0, null);
                TextView tvNTA = command.getCol("" +
                        "("+totalAkhir.getTotalCF(indexPerhitungan)+" * "+command.getById(idAspek, "aspek", "bobotCF")+"%) + (" +totalAkhir.getTotalSF(indexPerhitungan)+ " * "+command.getById(idAspek, "aspek", "bobotSF")+"%) = "+
                        ""+command.toFloat(String.valueOf(totalAkhir.getTotalAspek(indexPerhitungan)))+"", Command.microText, wCF, rowNTA, 1, null);
                tvNTA.setPadding(5, 0,0,0);
                tvNTA.setTextColor(Color.RED);

                layout.addView(rowCF);
                layout.addView(rowSF);
                layout.addView(rowNTA);
                persenAspekString+="("+command.toFloat(String.valueOf(totalAkhir.getTotalAspek(indexPerhitungan)))+" * "+command.getById(idAspek, "aspek", "bobotAspek")+"%)";
                if(j < AppObject.aspek.length()-1)persenAspekString+=" + ";
                //w1 = wCF;
                //command.addSpace(layout, 1);
            }
            LinearLayout rowTotalLintasMinat = command.getRow();
            command.addCol("NLM"+command.get("lintasMinat", counterLM, "idLintasMinat"), Command.microText, w0, rowTotalLintasMinat, 0, null);
            TextView tvNTA = command.getCol("" +
                    ""+persenAspekString+" = " +
                    ""+command.toFloat(String.valueOf(totalAkhir.getTotalNilai(counterLM)))+"", Command.microText, 1, rowTotalLintasMinat, 1, null);
            tvNTA.setPadding(5, 0,0,0);
            tvNTA.setTextColor(Color.RED);
            persenAspekString = "";

            layout.addView(rowTotalLintasMinat);
            //System.err.println(totalAkhir.getTotalAspek());
            /*LinearLayout rowNilaiTotalLM = command.getRow();
            TextView tvNilaiTotalLM = command.getCol("Nilai Total Lintas Minat = "+totalAkhir.getTotalNilai(counterLM), Command.bigText, 1, rowNilaiTotalLM, 1, null);
            tvNilaiTotalLM.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
            if(totalAkhir.getLintasMinatByRanking(counterLM).equals(totalAkhir.getLintasMinatTertinggi())){
                tvNilaiTotalLM.setTextColor(Color.parseColor("#339933"));
            }
            layout.addView(rowNilaiTotalLM);*/
            //TextView tvNTA = command.getCol(totalAkhir.getTotalNilai(counterLM)+"", Command.microText, 1, rowNTA, 1, null);
            //tvNTA.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
            LinearLayout rowTutup = command.getRow();
            command.addCol("", Command.medText, 1, rowTutup, 200, null);
            layout.addView(rowTutup);
            command.addSpace(layout, 20);
            counterLM++;
            //end
        }
    }

    private void hitungProfilMatching(int[] profilSiswa){
        String peminatan = command.getByKelas(command.getById(idSiswa, "siswa", "kelas"), "kelas", "peminatan");
        String kelas =  command.getById(idSiswa, "siswa", "kelas");
        //String lintasMinat1 = command.getByKelas(kelas, "kelas", "lintasMinat1");
        //String lintasMinat2 = command.getByKelas(kelas, "kelas", "lintasMinat2");
        ArrayList<Float> totalNilaiArray = new ArrayList<>();
        ArrayList<Float> gapArray = new ArrayList<>();
        ArrayList<Float> bobotGapArray = new ArrayList<>();
        ArrayList<Float> profilIdealArray = new ArrayList<>();
        ArrayList<Float> profilSiswaArray = new ArrayList<>();
        ArrayList<Float> totalCFArray = new ArrayList<>();
        ArrayList<Float> totalSFArray = new ArrayList<>();
        ArrayList<Float> jumlahBobotGapCFArray = new ArrayList<>();
        ArrayList<Float> jumlahBobotGapSFArray = new ArrayList<>();
        ArrayList<Float> totalAspekArray = new ArrayList<>();
        ArrayList<Ranking> rankingArray = new ArrayList<>();
        for(int h=0;h<AppObject.lintasMinat.length();h++) {
            /*if(command.getByKelas(command.getById(idSiswa, "siswa", "kelas"), "kelas", "peminatan").equals("IPS")){
                //if(!command.get("lintasMinat", h, "lintasMinat").equals("B.Inggris") || !command.get("lintasMinat", h, "lintasMinat").equals("B.Jepang")) continue;
                System.err.println("This is ips bro");
            }*/
            if(peminatan.equals("IPS")){
                if(!command.get("lintasMinat", h, "lintasMinat").equals("B.Inggris") && !command.get("lintasMinat", h, "lintasMinat").equals("B.Jepang"))
                    continue;
            }
            String profilIdealLM1 = command.get("lintasMinat", h, "profilIdeal");
            float totalNilai = 0;
            for (int i = 0; i < AppObject.aspek.length(); i++) {
                String idAspek = command.get("aspek", i, "idAspek");
                float bobotCF = command.toFloat(command.get("aspek", i, "bobotCF"));
                float bobotSF = command.toFloat(command.get("aspek", i, "bobotSF"));
                float bobotAspek = command.toFloat(command.get("aspek", i, "bobotAspek"));
                float jumlahCF = getJumlahCF(idAspek);
                float jumlahSF = getJumlahSF(idAspek);
                float jumlahBobotGapCF = 0;
                float jumlahBobotGapSF = 0;
                for (int j = 0; j < AppObject.kriteria.length(); j++) {
                    if(command.get("kriteria", j, "idAspek").equals(idAspek)) {
                        String jenisKriteria = command.get("kriteria", j, "jenisKriteria");
                        float profilIdeal1 = command.toFloat(command.unserialize(profilIdealLM1, j, AppObject.kriteria.length()));
                        float gap = profilSiswa[j] - profilIdeal1;
                        float bobotGap = getBobotGap(gap);
                        if (jenisKriteria.equals("CF")) {
                            jumlahBobotGapCF += bobotGap;
                        } else {
                            jumlahBobotGapSF += bobotGap;
                        }
                        gapArray.add(gap);
                        bobotGapArray.add(bobotGap);
                        profilIdealArray.add(profilIdeal1);
                        profilSiswaArray.add(command.toFloat(String.valueOf(profilSiswa[j])));
                    }
                }
                float totalCf = jumlahBobotGapCF / jumlahCF;
                float totalSf = jumlahBobotGapSF / jumlahSF;
                float totalAspek = ((totalCf * bobotCF/100) + (totalSf * bobotSF/100)) * bobotAspek/100;
                totalNilai += totalAspek;
                totalCFArray.add(totalCf);
                totalSFArray.add(totalSf);
                float totalAspekTemp = (totalCf * bobotCF/100) + (totalSf * bobotSF/100);
                totalAspekArray.add(totalAspekTemp);
                jumlahBobotGapCFArray.add(jumlahBobotGapCF);
                jumlahBobotGapSFArray.add(jumlahBobotGapSF);
            }
            totalNilaiArray.add(command.toFloat(String.valueOf(totalNilai)));
        }
        for(int i=0;i<totalNilaiArray.size();i++){
            rankingArray.add(new Ranking(command.get("lintasMinat", i, "lintasMinat"), totalNilaiArray.get(i)));
        }
        Collections.sort(rankingArray, new Comparator<Ranking>() {
            @Override
            public int compare(Ranking o1, Ranking o2) {
                if(o1.getNilai() > o2.getNilai())return -1;
                return 0;
            }
        });
        //Collections.reverse(rankingArray);
        totalAkhir = new TotalAkhir(idSiswa, gapArray, bobotGapArray, profilIdealArray, profilSiswaArray, totalCFArray, totalSFArray, totalAspekArray, totalNilaiArray, rankingArray, jumlahBobotGapCFArray, jumlahBobotGapSFArray);
        if(peminatan.equals("IPS")){
            String theTertinggi = "B.Jepang";
            if(totalAkhir.getLintasMinatTertinggi().equals(command.get("lintasMinat", 0, "lintasMinat"))) theTertinggi = "B.Inggris";
            if(AppObject.guruOnLogin == null && AppObject.MASKED != AppObject.PROSES) new Connect("update", "update siswa set rekomendasiLintasMinat='" + theTertinggi + "' where idSiswa='" + idSiswa + "'", this);
        }else {
            if(AppObject.guruOnLogin == null && AppObject.MASKED != AppObject.PROSES) new Connect("update", "update siswa set rekomendasiLintasMinat='" + totalAkhir.getLintasMinatTertinggi() + "' where idSiswa='" + idSiswa + "'", this);
        }
    }

    private int getJumlahCF(String idAspek){
        int jumlah = 0;
        for(int i=0;i<AppObject.kriteria.length();i++){
            if(command.get("kriteria", i, "idAspek").equals(idAspek)){
                if(command.get("kriteria", i, "jenisKriteria").equals("CF")){
                    jumlah++;
                }
            }
        }
        return jumlah;
    }

    private int getJumlahSF(String idAspek){
        int jumlah = 0;
        for(int i=0;i<AppObject.kriteria.length();i++){
            if(command.get("kriteria", i, "idAspek").equals(idAspek)){
                if(command.get("kriteria", i, "jenisKriteria").equals("SF")){
                    jumlah++;
                }
            }
        }
        return jumlah;
    }

    private float getBobotGap(float gap){
        if(gap == 0) return 5.0f;//tidak ada selisih
        else if(gap > 0 && gap <= 1) return 4.5f;//kompetensi kelebihan 1 tingkat
        else if(gap < 0 && gap >= -1) return 4.0f;//kompetensi kekurangan 1 tingkat
        else if(gap > 1 && gap <= 2) return 3.5f;//kompetensi kelebihan 2 tingkat
        else if(gap < 1 && gap >= -2) return 3.0f;//kompetensi kekurangan 2 tingkat
        else if(gap > 2 && gap <= 3) return 2.5f;//kompetensi kelebihan 3 tingkat
        else if(gap < 2 && gap >= -3) return 2.0f;//kompetensi kekurangan 3 tingkat
        else if(gap > 3 && gap <= 4) return 1.5f;//kompetensi kelebihan 4 tingkat
        else if(gap < 3 && gap >= -4) return 1.0f;//kompetensi kekurangan 4 tingkat
        return 0;
    }

    class Waiting extends AsyncTask<String, Void, String>{

        int time;
        private TextView textView;
        private int[] profilSiswa = null;
        private boolean refresh = false;
        Waiting(int time){
            this.time = time;
            execute();
        }

        public Waiting setProfilSiswa(int[] profilSiswa){
            Waiting.this.profilSiswa = profilSiswa;
            return Waiting.this;
        }
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            layout.removeAllViews();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.topMargin = 400;
            ProgressBar progressBar = new ProgressBar(InputNilaiSiswa.this);
            progressBar.setLayoutParams(layoutParams);
            layout.addView(progressBar);
            textView = new TextView(InputNilaiSiswa.this);
            textView.setText("Loading...");
            textView.setGravity(Gravity.CENTER);
            layout.addView(textView);
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            if(!refresh) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (layer == INPUT_NILAI) {
                            showInputNilai();
                        } else if (layer == PILIH_SISWA) {
                            showPilihSiswa(AppObject.idKelasTerpilih);
                            //showPilihSiswa("1");
                        } else if (layer == HASIL) {
                            showHasil();
                        } else if (layer == DETAIL_PERHITUNGAN) {
                            showHitung();
                        }
                    }
                }, time);
            }else{
                refresh = false;
                command.refresh("siswa", InputNilaiSiswa.class, true, "");
            }
        }
        @Override
        protected String doInBackground(String... strings) {
            if(!refresh) {
                if (Waiting.this.profilSiswa != null) {
                    hitungProfilMatching(Waiting.this.profilSiswa);
                    String serializedNilaiProfilSiswa = command.serialize(Waiting.this.profilSiswa);
                    String serializedNlm = command.serializeFloat(totalAkhir.getTotalNilaiArray());
                    //String minatSiswa = spMinatSiswa.getSelectedItem().toString();
                    //String minatOrtu = spMinatOrtu.getSelectedItem().toString();
                    if(AppObject.guruOnLogin != null){
                        new Connect("update", "update siswa set nilaiProfilSiswa='" + serializedNilaiProfilSiswa + "' where idSiswa='" + InputNilaiSiswa.this.idSiswa + "'", InputNilaiSiswa.this);
                    }else {
                        if(AppObject.MASKED != AppObject.PROSES) new Connect("update", "update siswa set nilaiProfilSiswa='" + serializedNilaiProfilSiswa + "', nlm='" + serializedNlm + "' where idSiswa='" + InputNilaiSiswa.this.idSiswa + "'", InputNilaiSiswa.this);
                    }
                }
            }
            return null;
        }
        public Waiting setRefresh(boolean refresh){
            this.refresh = refresh;
            return this;
        }
    }

    @Override
    public void onBackPressed() {
        if(AppObject.siswaOnLogin == null) {
            if(AppObject.guruOnLogin != null) {
                if (layer == PILIH_SISWA) {
                    layer = INPUT_NILAI;
                    command.move(MenuGuru.class, true);
                } else if (layer == INPUT_NILAI) {
                    layer = PILIH_SISWA;
                    totalAkhir = null;
                    new Waiting(1);
                } else if (layer == HASIL) {
                    if (ada) {
                        layer = PILIH_SISWA;
                        totalAkhir = null;
                        new Waiting(1).setRefresh(true);

                        //new Waiting(1);
                    } else {
                        layer = INPUT_NILAI;
                        new Waiting(1);
                    }
                } else if (layer == DETAIL_PERHITUNGAN) {
                    layer = HASIL;
                    new Waiting(1);
                }
            }else if(AppObject.guruBK != null){
                if (layer == PILIH_SISWA) {
                    layer = INPUT_NILAI;
                    command.move(MenuGuruBK.class, true);
                } else if (layer == INPUT_NILAI) {
                    layer = PILIH_SISWA;
                    totalAkhir = null;
                    new Waiting(1);
                } else if (layer == HASIL) {
                    if (ada) {
                        layer = PILIH_SISWA;
                        totalAkhir = null;
                        new Waiting(1).setRefresh(true);

                        //new Waiting(1);
                    } else {
                        layer = INPUT_NILAI;
                        new Waiting(1);
                    }
                } else if (layer == DETAIL_PERHITUNGAN) {
                    layer = HASIL;
                    new Waiting(1);
                }
            }
        }else {
            if (layer == HASIL) {
                command.move(MenuSiswa.class, true);
            } else if (layer == DETAIL_PERHITUNGAN) {
                layer = HASIL;
                new Waiting(1);
            }
        }
    }


}
