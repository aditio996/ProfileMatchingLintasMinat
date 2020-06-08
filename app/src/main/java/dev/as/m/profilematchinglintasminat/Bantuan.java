package dev.as.m.profilematchinglintasminat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Bantuan extends AppCompatActivity {

    private TextView judulBantuan;
    private Command command;
    private int layer = 0;
    public static final int bantuanHome = 0;
    public static final int bantuanLM = 1;
    public static final int bantuanSPK = 2;
    public static final int bantuanPM = 3;
    public static final int bantuanAspek = 4;
    public static final int bantuanKriteria = 5;
    public static final int bantuanAbout = 6;
    private LinearLayout layout;
    ScrollView.LayoutParams defaultLP, nLP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = new Command(this);
        setContentView(R.layout.activity_bantuan);
        init();
    }

    private void addToLayout(View... views){
        for(int i=0;i<views.length;i++){
            layout.addView(views[i]);
        }
    }

    private void showBantuanLM(){
        clearLayout();
        layer = bantuanLM;
        setJudulBantuan("Lintas Minat");
        LinearLayout.LayoutParams layoutParams = command.getLP();
        layoutParams.bottomMargin = 10;
        layoutParams.leftMargin = 10;
        layoutParams.rightMargin = 10;
        TextView t0 = command.getTextView(Color.BLACK, Command.medText);
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.kurikulum_2013));
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams1);
        addToLayout(imageView);
        command.addSpace(layout, 10);
        t0.setText("    Lintas Minat adalah program kurikuler yang disediakan untuk mengakomodasi perluasan pilihan minat, bakat dan/atau kemampuan akademik peserta didik dengan orientasi penguasaan kelompok mata pelajaran keilmuan di luar pilihan minat.");
        t0.setLayoutParams(layoutParams);
        TextView t1 = command.getTextView(Color.BLACK, Command.medText);
        t1.setText("    Dalam Kurikulum 2013, selain memilih mata pelajaran dalam suatu peminatan tertentu, siswa juga diberi kesempatan untuk mengambil mata pelajaran dari peminatan lain. Hal ini memberi peluang kepada siswa untuk mempelajari mata pelajaran yang diminati namun tidak terdapat pada kelompok mata pelajaran peminatan.");
        t1.setLayoutParams(layoutParams);
        addToLayout(t0, t1);
        if(AppObject.lintasMinat != null){
            if(AppObject.lintasMinat.length() > 0){
                TextView t2 = command.getTextView(Color.BLACK, Command.medText);
                t2.setText("    SMA N 4 Padang pada tahun pembelajaran 2018/2019 memiliki "+AppObject.lintasMinat.length()+" Mata Pelajaran Lintas Minat, yaitu :");
                t2.setLayoutParams(layoutParams);
                addToLayout(t2);
                LinearLayout rowHead = command.getRow();
                command.addCol("No", Command.medText, 0.2f, rowHead, 0, null);
                command.addCol("Lintas Minat", Command.medText, 0.8f, rowHead, 0, null);
                addToLayout(rowHead);
                for(int i=0;i<AppObject.lintasMinat.length();i++){
                    LinearLayout rowData = command.getRow();
                    TextView tv0 = command.getCol(""+(i+1), Command.medText, 0.2f, rowData, 1, null);
                    TextView tv1 = command.getCol(command.get("lintasMinat", i, "lintasMinat"), Command.medText, 0.8f, rowData, 1, null);
                    tv0.setPadding(7, 0, 0, 0);
                    tv1.setPadding(7, 0, 0, 0);
                    addToLayout(rowData);
                }
            }
        }
        command.addSpace(layout, 100);
    }

    private void showBantuanSPK(){
        clearLayout();
        layer = bantuanSPK;
        setJudulBantuan("Sistem Pendukung Keputusan");
        LinearLayout.LayoutParams layoutParams = command.getLP();
        layoutParams.bottomMargin = 10;
        layoutParams.leftMargin = 10;
        layoutParams.rightMargin = 10;
        TextView t0 = command.getTextView(Color.BLACK, Command.medText);
        t0.setText("    Sistem Pendukung Keputusan (Decision Support System) merupakan informasi interaktif yang menyediakan informasi, pemodelan, dan pemanipulasian data. Sistem ini digunakan untuk membantu pengambilan keputusan dalam situasi yang semi terstruktur dan situasi yang tidak terstruktur, dimana tak seorang pun tahu secara pasti bagaimana keputusan seharusnya dibuat.");
        t0.setLayoutParams(layoutParams);
        addToLayout(t0);
    }

    private void showBantuanPM(){
        clearLayout();
        layer = bantuanPM;
        setJudulBantuan("Profile Matching");
        LinearLayout.LayoutParams layoutParams = command.getLP();
        layoutParams.bottomMargin = 10;
        layoutParams.leftMargin = 10;
        layoutParams.rightMargin = 2;
        TextView t0 = command.getTextView(Color.BLACK, Command.medText);
        t0.setText("    Profile matching merupakan suatu mekanisme pengambilan keputusan dengan mengasumsikan bahwa terdapat nilai target yang ideal yang harus dipenuhi oleh subyek yang diteliti, bukannya tingkat minimal yang harus dipenuhi atau dilewati.");
        t0.setLayoutParams(layoutParams);
        TextView t1 = command.getTextView(Color.BLACK, Command.medText);
        t1.setText("    Dalam proses profile matching secara garis besar merupakan proses membandingkan antara kompetensi individu kedalam kompetensi jabatan sehingga dapat diketahui perbedaan kompetentasinya (Disebut juga gap). Semakin kecil gap yang dihasilkan maka bobot nilainya semakin besar yang berarti memiliki peluang lebih besar untuk individu menempati proses tersebut.");
        t1.setLayoutParams(layoutParams);
        TextView t2 = command.getTextView(Color.BLACK, Command.medText);
        t2.setText("    Terdapat beberapa tahapan dan perumusan perhitungan dengan metode profile matching, yaitu:");
        t2.setLayoutParams(layoutParams);

        TextView t3 = command.getTextView(Color.BLACK, Command.medText);
        t3.setText("    1.\tPemetaan GAP Kompetensi");
        t3.setLayoutParams(layoutParams);
        TextView t4 = command.getTextView(Color.BLACK, Command.medText);
        LinearLayout.LayoutParams layoutParams2 = command.getLP();
        layoutParams2.leftMargin = 70;
        layoutParams2.rightMargin = 10;
        layoutParams2.bottomMargin = 10;
        t4.setText("Gap yang dimaksud disini adalah perbedaaan antara profil ideal lintas minat dengan profil siswa.");
        t4.setLayoutParams(layoutParams2);
        TextView t5 = command.getTextView(Color.BLACK, Command.medText);
        t5.setText("GAP = (profile siswa) - (profile ideal lintas minat)");
        t5.setBackgroundResource(R.drawable.border3);
        t5.setTextSize(Command.smallText);
        t5.setTypeface(t5.getTypeface(), Typeface.BOLD_ITALIC);
        t5.setLayoutParams(layoutParams2);
        t5.setGravity(Gravity.CENTER);

        TextView t6 = command.getTextView(Color.BLACK, Command.medText);
        t6.setText("    2.\tPembobotan");
        t6.setLayoutParams(layoutParams);
        TextView t7 = command.getTextView(Color.BLACK, Command.medText);
        t7.setText("Pada tahap ini, akan ditentukan bobot nilai masing-masing aspek dengan menggunakan bobot nilai yang telah ditentukan bagi masing-masing aspek itu sendiri.");
        t7.setLayoutParams(layoutParams2);
        addToLayout(t0, t1, t2, t3, t4, t5, t6, t7);
        addRowX(layout, 0,"GAP", "Bobot\nGAP", "Keterangan");
        addRowX(layout, 1,"0", "5.0", "Kompetensi sesuai dengan yang dibutuhkan");
        addRowX(layout, 1,"1", "4.5", "Kompetensi individu lebih 1 tingkat");
        addRowX(layout, 1,"-1", "4.0", "Kompetensi individu kurang 1 tingkat");
        addRowX(layout, 1,"2", "3.5", "Kompetensi individu lebih 2 tingkat");
        addRowX(layout, 1,"-2", "3.0", "Kompetensi individu kurang 2 tingkat");
        addRowX(layout, 1,"3", "2.5", "Kompetensi individu lebih 3 tingkat");
        addRowX(layout, 1,"-3", "2.0", "Kompetensi individu kurang 3 tingkat");
        addRowX(layout, 1,"4", "1.5", "Kompetensi individu lebih 4 tingkat");
        addRowX(layout, 1,"-4", "1.0", "Kompetensi individu kurang 4 tingkat");
        command.addSpace(layout, 10);
        TextView t8 = command.getTextView(Color.BLACK, Command.medText);
        t8.setText(" 3.\tPerhitungan dan Pengelompokan Core dan Secondary Factor");
        t8.setLayoutParams(layoutParams);
        TextView t9 = command.getTextView(Color.BLACK, Command.medText);
        t9.setText("Setelah menentukan bobot nilai GAP untuk kriteria yang dibutuhkan, kemudian tiap kriteria dikelompokkan lagi menjadi 2 kelompok yaitu core factor dan secondary factor.");
        t9.setLayoutParams(layoutParams2);
        TextView t10 = command.getTextView(Color.BLACK, Command.medText);
        t10.setText("a.\t\tCore Factor");
        t10.setTypeface(t10.getTypeface(), Typeface.ITALIC);
        t10.setLayoutParams(layoutParams2);
        TextView t11 = command.getTextView(Color.BLACK, Command.medText);
        t11.setText("   \t\tCore factor merupakan kriteria yang paling menonjol / paling dibutuhkan oleh suatu aspek. Untuk menghitung core factor digunakan persamaan sebagai berikut :");
        t11.setLayoutParams(layoutParams2);
        TextView t12 = command.getTextView(Color.BLACK, Command.medText);
        t12.setText("NCF = (∑NC)/(∑IC)");
        t12.setBackgroundResource(R.drawable.border3);
        t12.setTextSize(Command.smallText);
        t12.setTypeface(t12.getTypeface(), Typeface.BOLD_ITALIC);
        t12.setLayoutParams(layoutParams2);
        t12.setGravity(Gravity.CENTER);
        TextView t13 = command.getTextView(Color.BLACK, Command.medText);
        t13.setText("   \tKeterangan\n   \tNCF\t: Nilai Rata-rata Core Factor\n   \tNC\t: Jumlah Total Nilai Core Factor\n   \tIC\t   : Jumlah Item Core Factor");
        t13.setLayoutParams(layoutParams2);
        TextView t14 = command.getTextView(Color.BLACK, Command.medText);
        t14.setText("b.\t\tSecondary Factor");
        t14.setTypeface(t14.getTypeface(), Typeface.ITALIC);
        t14.setLayoutParams(layoutParams2);
        TextView t15 = command.getTextView(Color.BLACK, Command.medText);
        t15.setText("\t\t\tSecondary factor adalah item-item selain kriteria yang ada pada core factor. Untuk menghitung secondary factor digunakan persamaan sebagai berikut :");
        t15.setLayoutParams(layoutParams2);
        TextView t16 = command.getTextView(Color.BLACK, Command.medText);
        t16.setText("NSF = (∑NS)/(∑IS)");
        t16.setBackgroundResource(R.drawable.border3);
        t16.setTextSize(Command.smallText);
        t16.setTypeface(t16.getTypeface(), Typeface.BOLD_ITALIC);
        t16.setLayoutParams(layoutParams2);
        t16.setGravity(Gravity.CENTER);
        TextView t17 = command.getTextView(Color.BLACK, Command.medText);
        t17.setText("   \tKeterangan\n   \tNSF\t: Nilai Rata-rata Secondary Factor\n   \tNS\t\t: Jumlah Total Nilai Secondary Factor\n   \tIS\t   : Jumlah Item Secondary Factor");
        t17.setLayoutParams(layoutParams2);
        TextView t18 = command.getTextView(Color.BLACK, Command.medText);
        t18.setText("    4.\tPerhitungan Nilai Total Tiap Aspek");
        t18.setLayoutParams(layoutParams);
        TextView t19 = command.getTextView(Color.BLACK, Command.medText);
        t19.setText("Dari perhitungan core factor dan secondary factor dari tiap-tiap aspek. Kemudian dihitung nilai total dari tiap-tiap aspek yang diperkirakan berpengaruh pada kompetensi individu dari tiap-tiap siswa. Untuk menghitung nilai total dari masing-masing aspek digunakan persamaan sebagai berikut :");
        t19.setLayoutParams(layoutParams2);
        TextView t20 = command.getTextView(Color.BLACK, Command.medText);
        t20.setText("N = (x)% × NCF + (x)% × NSF");
        t20.setBackgroundResource(R.drawable.border3);
        t20.setTextSize(Command.smallText);
        t20.setTypeface(t20.getTypeface(), Typeface.BOLD_ITALIC);
        t20.setLayoutParams(layoutParams2);
        t20.setGravity(Gravity.CENTER);
        TextView t21 = command.getTextView(Color.BLACK, Command.medText);
        t21.setText("   \tKeterangan\n   \tN\t     : Nilai Total Tiap Aspek\n   \tNCF\t  : Rata-rata Core Factor\n   \tNSF\t  : Rata-rata Secondary Factor");
        t21.setLayoutParams(layoutParams2);
        TextView t22 = command.getTextView(Color.BLACK, Command.medText);
        t22.setText("    5.\tPerhitungan Ranking");
        t22.setLayoutParams(layoutParams);
        TextView t23 = command.getTextView(Color.BLACK, Command.medText);
        t23.setText("Hasil akhir dari proses profile matching adalah ranking mata pelajaran lintas minat sebagai rekomendasi lintas minat yang bisa dipilih oleh siswa. Penentuan mengacu pada ranking hasil perhitungan yang ditunjukkan pada persamaan sebagai berikut :");
        t23.setLayoutParams(layoutParams2);
        TextView t24 = command.getTextView(Color.BLACK, Command.medText);
        t24.setText("Ranking = ∑(x)%N");
        t24.setBackgroundResource(R.drawable.border3);
        t24.setTextSize(Command.smallText);
        t24.setTypeface(t24.getTypeface(), Typeface.BOLD_ITALIC);
        t24.setLayoutParams(layoutParams2);
        t24.setGravity(Gravity.CENTER);
        TextView t25 = command.getTextView(Color.BLACK, Command.medText);
        t25.setText("   \tKeterangan\n   \t(x)\t  : Nilai persen yang diinputkan\n   \tN\t   : Nilai Total Tiap Aspek");
        t25.setLayoutParams(layoutParams2);


        addToLayout(t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24, t25);
        command.addSpace(layout, 100);

    }

    private void addRow(LinearLayout parent, int type, String s1, String s2, String s3){
        LinearLayout row = command.getRow();
        command.addCol(s1, Command.smallText, 0.2f, row, type, null);
        command.addCol(s2, Command.smallText, 0.2f, row, type, null);
        command.addCol(s3, Command.smallText, 0.6f, row, type, null);
        parent.addView(row);
    }

    private void addRowX(LinearLayout parent, int type, String s1, String s2, String s3){
        LinearLayout row = command.getRow();
        command.addCol(s1, Command.smallText, 0.15f, row, type, null);
        command.addCol(s2, Command.smallText, 0.15f, row, type, null);
        command.addCol(s3, Command.smallText, 0.7f, row, type, null);
        parent.addView(row);
    }

    private void addRow2(LinearLayout parent, int type, String s1, String s2, String s3, String s4, String s5){
        LinearLayout row = command.getRow();
        command.addCol(s1, Command.smallText, 0.2f, row, type, null);
        command.addCol(s2, Command.smallText, 0.35f, row, type, null);
        command.addCol(s3, Command.smallText, 0.15f, row, type, null);
        command.addCol(s4, Command.smallText, 0.15f, row, type, null);
        command.addCol(s5, Command.smallText, 0.15f, row, type, null);
        parent.addView(row);
    }

    private void addRow2(LinearLayout parent, int type, String s1, String s2, String s3){
        LinearLayout row = command.getRow();
        if(type!= 300){
            command.addCol(s1, Command.smallText, 0.2f, row, type, null);
            command.addCol(s2, Command.smallText, 0.3f, row, type, null);
            command.addCol(s3, Command.smallText, 0.5f, row, type, null);
        }else {
            command.addCol(s1, Command.smallText, 0.2f, row, type, null);
            command.addCol(s2, Command.smallText, 0.1f, row, type, null);
            command.addCol(s3, Command.smallText, 0.7f, row, type, null);
        }
        parent.addView(row);
    }

    private void addRow0(LinearLayout parent, int type, String s1){
        LinearLayout row = command.getRow();
        command.addCol(s1, Command.medText, 1, row, type, null);
        parent.addView(row);
    }

    private void showBantuanAspek(){
        clearLayout();
        layer = bantuanAspek;
        setJudulBantuan("Aspek");
        LinearLayout.LayoutParams layoutParams = command.getLP();
        layoutParams.bottomMargin = 10;
        layoutParams.leftMargin = 10;
        layoutParams.rightMargin = 2;
        TextView t0 = command.getTextView(Color.BLACK, Command.medText);
        t0.setText("    Hal yang sangat penting dalam penggunaan metode Profile Matching adalah penentuan aspek-aspek apa saja yang akan digunakan sebagai perbandingan.");
        t0.setLayoutParams(layoutParams);
        addToLayout(t0);
        if(AppObject.aspek != null){
            if(AppObject.aspek.length() > 0){
                TextView t1 = command.getTextView(Color.BLACK, Command.medText);
                t1.setText("    Dalam kasus penentuan lintas minat kelas X di SMA N 4 Padang, ada "+AppObject.aspek.length()+" aspek penilaian yang digunakan, yaitu:");
                t1.setLayoutParams(layoutParams);
                addToLayout(t1);
                addRow2(layout, 0, "Kode\nAspek", "Nama\nAspek", "Bobot CF", "Bobot SF", "Bobot\nAspek");
                for(int i=0;i<AppObject.aspek.length();i++){
                    addRow2(layout, 1, "A"+command.get("aspek", i, "idAspek"), command.get("aspek", i, "namaAspek"), command.get("aspek", i, "bobotCF")+"%", command.get("aspek", i, "bobotSF")+"%", command.get("aspek", i, "bobotAspek")+"%");
                }
            }else{
                TextView t1 = command.getTextView(Color.BLACK, Command.medText);
                t1.setText("    Data aspek penilaian lintas minat belum diinput");
                t1.setTextColor(Color.RED);
                t1.setGravity(Gravity.CENTER);
                t1.setLayoutParams(layoutParams);
                addToLayout(t1);
            }
        }else {
            TextView t1 = command.getTextView(Color.BLACK, Command.medText);
            t1.setText("    Data aspek penilaian lintas minat belum diinput");
            t1.setTextColor(Color.RED);
            t1.setGravity(Gravity.CENTER);
            t1.setLayoutParams(layoutParams);
            addToLayout(t1);
        }
    }

    private void showBantuanKriteria(){
        clearLayout();
        layer = bantuanKriteria;
        setJudulBantuan("Kriteria");
        if(AppObject.kriteria != null){
            if(AppObject.kriteria.length() > 0){
                LinearLayout.LayoutParams layoutParams = command.getLP();
                layoutParams.bottomMargin = 10;
                layoutParams.leftMargin = 10;
                layoutParams.rightMargin = 2;
                TextView t0 = command.getTextView(Color.BLACK, Command.medText);
                t0.setText("    Setiap aspek mempunyai kriteria tersendiri dalam menentukan lintas minat. Berikut adalah kriteria dan Sub Kriteria dalam penentuan lintas minat siswa kelas X di SMA N 4 Padang :");
                t0.setLayoutParams(layoutParams);
                addToLayout(t0);
                for(int i=0;i<AppObject.aspek.length();i++){
                    String idAspek = command.get("aspek", i, "idAspek");
                    addRow0(layout, 100, "Aspek "+command.get("aspek", i, "namaAspek")+" ["+"A"+command.get("aspek", i, "idAspek")+"]");
                    addRow2(layout, 0, "Kode\nKriteria", "Nama\nKriteria", "SubKriteria");
                    for(int j=0;j<AppObject.kriteria.length();j++){
                        if(command.get("kriteria", j, "idAspek").equals(idAspek)){
                            String subKriteria = command.get("kriteria", j, "subKriteria");
                            String sub5 = command.unserialize(subKriteria, SubKriteria.sub5, 5);
                            String sub4 = command.unserialize(subKriteria, SubKriteria.sub4, 5);
                            String sub3 = command.unserialize(subKriteria, SubKriteria.sub3, 5);
                            String sub2 = command.unserialize(subKriteria, SubKriteria.sub2, 5);
                            String sub1 = command.unserialize(subKriteria, SubKriteria.sub1, 5);
                            addRow2(layout, 1, "K"+command.get("kriteria", j, "idKriteria"), command.get("kriteria", j, "namaKriteria"),
                                    "" +
                                            "\t"+sub5+" [5]\n"+
                                            "\t"+sub4+" [4]\n"+
                                            "\t"+sub3+" [3]\n"+
                                            "\t"+sub2+" [2]\n"+
                                            "\t"+sub1+" [1]"
                            );
                        }
                    }
                    addRow0(layout, 200, "");
                    command.addSpace(layout, 10);
                }
                command.addSpace(layout, 100);
            }else {
                LinearLayout.LayoutParams layoutParams = command.getLP();
                layoutParams.bottomMargin = 10;
                layoutParams.leftMargin = 10;
                layoutParams.rightMargin = 2;
                TextView t0 = command.getTextView(Color.BLACK, Command.medText);
                t0.setText("    Setiap aspek mempunyai kriteria tersendiri dalam menentukan lintas minat.");
                t0.setLayoutParams(layoutParams);
                addToLayout(t0);
                command.addSpace(layout, 100);
            }
        }else{
            LinearLayout.LayoutParams layoutParams = command.getLP();
            layoutParams.bottomMargin = 10;
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 2;
            TextView t0 = command.getTextView(Color.BLACK, Command.medText);
            t0.setText("    Setiap aspek mempunyai kriteria tersendiri dalam menentukan lintas minat.");
            t0.setLayoutParams(layoutParams);
            addToLayout(t0);
            command.addSpace(layout, 100);
        }

    }

    private void showBantuanAbout(){
        clearLayout();
        layer = bantuanAbout;
        setJudulBantuan("Tentang Kami");
        LinearLayout.LayoutParams layoutParams = command.getLP();
        layoutParams.bottomMargin = 10;
        layoutParams.leftMargin = 100;
        layoutParams.rightMargin = 100;
        LinearLayout linearLayout = command.getRow();
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        
        TextView t0 = command.getTextView(Color.BLACK, Command.medText);
        t0.setText(getResources().getString(R.string.app_name));
        t0.setGravity(Gravity.CENTER);
        linearLayout.addView(t0);
        command.addSpace(linearLayout, 50);
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.logo_upi_yptk));
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams1);
        linearLayout.addView(imageView);
        command.addSpace(linearLayout, 50);
        TextView t1 = command.getTextView(Color.BLACK, Command.smallText);
        t1.setText("    Aplikasi ini dirancang dalam rangka memenuhi Tugas Akhir (Skripsi) dengan Judul : "+getResources().getString(R.string.judul_skripsi));
        t1.setGravity(Gravity.START);
        linearLayout.addView(t1);
        command.addSpace(linearLayout, 20);
        addRow2(linearLayout, 300, "Nama", ":", "Aditio Sudirman");
        addRow2(linearLayout, 300, "Nobp", ":", "15101152630003");
        addRow2(linearLayout, 300, "Fakultas", ":", "Ilmu Komputer");
        addRow2(linearLayout, 300, "Jurusan", ":", "Teknik Informatika");
        command.addSpace(linearLayout, 50);

        TextView t2 = command.getTextView(Color.BLACK, Command.medText);
        t2.setText("UPI YPTK Padang\n2019");
        t2.setGravity(Gravity.CENTER);
        linearLayout.addView(t2);

        addToLayout(linearLayout);

    }

    private void showBantuanHome(){
        clearLayout();
        layer = bantuanHome;
        setJudulBantuan("Bantuan");
        nLP.topMargin = 50;
        layout.setLayoutParams(nLP);
        Button btnLM = command.getButton("Lintas Minat", Gravity.CENTER, Command.MATCH);
        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) btnLM.getLayoutParams());
        lp.width = getWindow().getWindowManager().getDefaultDisplay().getWidth() /2 + getWindow().getWindowManager().getDefaultDisplay().getWidth() /3;
        lp.bottomMargin = 20;
        Button btnSPK = command.getButton("Sistem Pendukung Keputusan", Gravity.CENTER, Command.MATCH);
        Button btnPM = command.getButton("Profile Matching", Gravity.CENTER, Command.MATCH);
        Button btnAspek = command.getButton("Aspek", Gravity.CENTER, Command.MATCH);
        Button btnKriteria = command.getButton("Kriteria", Gravity.CENTER, Command.MATCH);
        Button btnAbout = command.getButton("Tentang Kami", Gravity.CENTER, Command.MATCH);
        btnLM.setLayoutParams(lp);
        btnSPK.setLayoutParams(lp);
        btnPM.setLayoutParams(lp);
        btnAspek.setLayoutParams(lp);
        btnKriteria.setLayoutParams(lp);
        btnAbout.setLayoutParams(lp);
        btnLM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBantuanLM();
            }
        });
        btnSPK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBantuanSPK();
            }
        });
        btnPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBantuanPM();
            }
        });
        btnAspek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBantuanAspek();
            }
        });
        btnKriteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showBantuanKriteria();
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBantuanAbout();
            }
        });
        layout.addView(btnLM);
        layout.addView(btnSPK);
        layout.addView(btnPM);
        layout.addView(btnAspek);
        layout.addView(btnKriteria);
        layout.addView(btnAbout);
    }

    private void init(){
        layout = findViewById(R.id.layoutBantuan);
        defaultLP = ((ScrollView.LayoutParams) layout.getLayoutParams());
        nLP = ((ScrollView.LayoutParams) layout.getLayoutParams());
        judulBantuan = findViewById(R.id.judulBantuan);
        if(layer == bantuanHome) showBantuanHome();
        else if(layer == bantuanLM) showBantuanLM();
        else if(layer == bantuanSPK) showBantuanSPK();
        else if(layer == bantuanPM) showBantuanPM();
        else if(layer == bantuanAspek) showBantuanAspek();
        else if(layer == bantuanKriteria) showBantuanKriteria();
        else if(layer == bantuanAbout) showBantuanAbout();
        else showBantuanHome();
    }

    @Override
    public void onBackPressed() {
        if(layer == bantuanHome){
            if(AppObject.siswaOnLogin != null) command.move(MenuSiswa.class, true);
            else if(AppObject.admin != null) command.move(MenuAdmin.class, true);
            else if(AppObject.guruOnLogin != null) command.move(MenuGuru.class, true);
            else command.move(MenuGuruBK.class, true);
        } else {
            showBantuanHome();
        }
    }

    private void setJudulBantuan(String judul){
        judulBantuan.setText(judul);
    }

    private void clearLayout(){
        layout.removeAllViews();
        layout.setLayoutParams(defaultLP);
    }
}
