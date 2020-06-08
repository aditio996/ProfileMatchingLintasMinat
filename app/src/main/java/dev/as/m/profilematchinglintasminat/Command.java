package dev.as.m.profilematchinglintasminat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Command {

    private Context context;
    public static int MANAGE = 0;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static String SELECTED_GURU = "";

    public Command(){}

    public Command(Context context){
        this.context = context;
    }

    public static void setINSERT(){
        MANAGE = INSERT;
    }

    public static void setUPDATE(){
        MANAGE = UPDATE;
    }

    public int toInt(String s){
        return Integer.parseInt(s);
    }

    public float toFloat(String s){
        float f = Float.parseFloat(s);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        s = decimalFormat.format(f);
        f = Float.parseFloat(s.replaceAll(",", "."));
        return f;
    }

    public Command move(Class<?> target, boolean isFinish){
        Intent intent = new Intent(context, target);
        if(isFinish)intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return this;
    }

    public Command move(Class<?> target, boolean isFinish, String bundle){
        Intent intent = new Intent(context, target);
        intent.putExtra("kode", bundle);
        if(isFinish)intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return this;
    }

    public LinearLayout getRow(){
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    private Class<?> target;
    private boolean finish;
    public Command setTarget(Class<?> target, boolean finish){
        this.target = target;
        this.finish = finish;
        return this;
    }

    public int getIndex(String table, String field, String value){
        if(table.equals("lintasMinat")){
            for(int i = 0; i<AppObject.lintasMinat.length();i++){
                if(value.equals(get("lintasMinat", i, field))){
                    return i;
                }
            }
        }else if(table.equals("kelas")){
            for(int i = 0; i<AppObject.kelas.length();i++){
                if(value.equals(get("kelas", i, field))){
                    return i;
                }
            }
        }else if(table.equals("siswa")){
            for(int i = 0; i<AppObject.siswa.length();i++){
                if(value.equals(get("siswa", i, field))){
                    return i;
                }
            }
        }else if(table.equals("aspek")){
            for(int i = 0; i<AppObject.aspek.length();i++){
                if(value.equals(get("aspek", i, field))){
                    return i;
                }
            }
        }else if(table.equals("kriteria")){
            for(int i = 0; i<AppObject.kriteria.length();i++){
                if(value.equals(get("kriteria", i, field))){
                    return i;
                }
            }
        }
        return 0;
    }

    public static float microText = 10;
    public static float smallText = 14;
    public static float smallText2 = 14;
    public static float medText = 18;
    public static float bigText = 22;
    public void addCol(String text, float textSize, float weight, LinearLayout parent, int type, final AlertMessage alertMessage){
        TextView col = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = weight;
        col.setLayoutParams(layoutParams);
        col.setText(text);
        col.setTextSize(textSize);
        if(type == 0){
            col.setBackground(context.getResources().getDrawable(R.drawable.table_header));
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.WHITE);
        }else if(type== 1){
            col.setBackground(context.getResources().getDrawable(R.drawable.table_data));
            col.setPadding(2, 0, 0, 0);
            col.setTextColor(Color.BLACK);
        }else if(type== -1){
            col.setBackground(context.getResources().getDrawable(R.drawable.table_data));
            col.setPadding(5, 5, 5, 5);
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.RED);
        }else if(type== -2){
            col.setBackground(context.getResources().getDrawable(R.drawable.drawable_button));
            col.setPadding(5, 5, 5, 5);
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.WHITE);
        }else if(type == 100){
            col.setBackground(context.getResources().getDrawable(R.drawable.table_header0));
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.WHITE);
        }else if(type == 200){
            col.setBackground(context.getResources().getDrawable(R.drawable.table_header_01));
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.WHITE);
        }else if(type == 250){
            col.setBackground(context.getResources().getDrawable(R.drawable.table_header_for_siswa));
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.WHITE);
        }else if(type == 300){
            col.setBackgroundColor(Color.parseColor("#00000000"));
            col.setGravity(Gravity.LEFT);
            col.setTextColor(Color.BLACK);
        }else if(type == 400){
            col = new EditText(context);
            col.setBackgroundColor(Color.parseColor("#00000000"));
            col.setText("");
            col.setHint(text);
            col.setGravity(Gravity.LEFT);
            col.setTextColor(Color.BLACK);
        }else{
            col.setBackgroundResource(R.drawable.drawable_button);
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.WHITE);
            col.setClickable(true);
            col.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
                            .setTitle(alertMessage.getTitle())
                            .setMessage(alertMessage.getMessage());
                    dialog.setPositiveButton("Update", onClickDialog(target, finish, alertMessage));

                    dialog.setNegativeButton("Hapus", onClickDialog(
                            "Hapus Data",
                            "Apakah anda yakin menghapus data "+alertMessage.getTable()+" ini?",
                            "delete",
                            "delete from "+alertMessage.getTable().toLowerCase()+" where "+alertMessage.getField()+"='"+alertMessage.getId()+"'",
                            alertMessage));
                    dialog.setNeutralButton("Batal", null);
                    dialog.show();
                }
            });
        }
        parent.addView(col);
    }

    public TextView getCol(String text, float textSize, float weight, LinearLayout parent, int type, final AlertMessage alertMessage){
        TextView col = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = weight;
        col.setLayoutParams(layoutParams);
        col.setText(text);
        col.setTextSize(textSize);
        if(type == 0){
            col.setBackground(context.getResources().getDrawable(R.drawable.table_header));
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.WHITE);
        }else if(type== 1){
            col.setBackground(context.getResources().getDrawable(R.drawable.table_data));
            col.setPadding(2, 0, 0, 0);
            col.setTextColor(Color.BLACK);
        }else if(type== -1){
            col.setBackground(context.getResources().getDrawable(R.drawable.table_data));
            col.setPadding(5, 5, 5, 5);
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.RED);
        }else if(type== -2){
            col.setBackground(context.getResources().getDrawable(R.drawable.drawable_button));
            col.setPadding(5, 5, 5, 5);
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.WHITE);
            col.setClickable(true);
        }else{
            col.setBackgroundResource(R.drawable.drawable_button);
            col.setGravity(Gravity.CENTER);
            col.setTextColor(Color.WHITE);
            col.setClickable(true);
            col.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
                            .setTitle(alertMessage.getTitle())
                            .setMessage(alertMessage.getMessage());

                    dialog.setPositiveButton("Update", onClickDialog(target, finish, alertMessage));
                    dialog.setNegativeButton("Hapus", onClickDialog(
                            "Hapus Data",
                            "Apakah anda yakin menghapus data "+alertMessage.getTable()+" ini?",
                            "delete",
                            "delete from "+alertMessage.getTable().toLowerCase()+" where "+alertMessage.getField()+"='"+alertMessage.getId()+"'",
                            alertMessage));
                    dialog.setNeutralButton("Batal", null);
                    dialog.show();
                }
            });
        }
        parent.addView(col);
        return col;
    }

    public Spinner addColSubKriteria(float weight, LinearLayout parent, int i, float textSize){
        Spinner col = new Spinner(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = weight;
        col.setLayoutParams(layoutParams);
        col.setPadding(5, 5, 5, 5);
        LinearLayout colBg = getRow();
        colBg.setLayoutParams(layoutParams);
        ArrayList<String> arr = new ArrayList<>();
        arr.add("- Pilih Profil Ideal -");
        arr.add("[5] "+unserialize(get("kriteria", i, "subKriteria"), 0, 5));
        arr.add("[4] "+unserialize(get("kriteria", i, "subKriteria"), 1, 5));
        arr.add("[3] "+unserialize(get("kriteria", i, "subKriteria"), 2, 5));
        arr.add("[2] "+unserialize(get("kriteria", i, "subKriteria"), 3, 5));
        arr.add("[1] "+unserialize(get("kriteria", i, "subKriteria"), 4, 5));
        int layoutId = R.layout.simple_list;
        if(textSize == smallText)layoutId = R.layout.simple_list_small;
        col.setAdapter(new ArrayAdapter<>(context, layoutId, arr));
        colBg.setBackground(context.getResources().getDrawable(R.drawable.table_for_list));
        //colBg.setBackground(context.getResources().getDrawable(R.drawable.ic_launcher_background));
        colBg.addView(col);
        parent.addView(colBg);
        return col;
    }

    public Spinner addColLintasMinat(float weight, LinearLayout parent, float textSize, String titleText){
        Spinner col = new Spinner(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = weight;
        col.setLayoutParams(layoutParams);
        LinearLayout colBg = getRow();
        colBg.setLayoutParams(layoutParams);
        ArrayList<String> arr = new ArrayList<>();
        arr.add(titleText);
        for(int j=0;j<AppObject.lintasMinat.length();j++){
            arr.add(get("lintasMinat", j, "lintasMinat"));
        }
        int layoutId = R.layout.simple_list;
        if(textSize == smallText)layoutId = R.layout.simple_list_small;
        col.setAdapter(new ArrayAdapter<>(context, layoutId, arr));
        colBg.setBackground(context.getResources().getDrawable(R.drawable.table_data));
        colBg.addView(col);
        parent.addView(colBg);
        return col;
    }

    public Button getButton(String text){
        Button button = new Button(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.gravity = Gravity.END;
        button.setLayoutParams(lp);
        button.setBackgroundResource(R.drawable.button1);
        button.setTextColor(Color.WHITE);
        button.setText(text);
        return button;
    }

    public LinearLayout.LayoutParams getLP(){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return lp;
    }

    public TextView getTextView(int textColor, float fontSize){
        TextView textView = new TextView(context);
        textView.setTextColor(textColor);
        textView.setTextSize(fontSize);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }else {
            textView.setGravity(Gravity.START | Gravity.CENTER | Gravity.END);
        }
        return textView;
    }

    public static final int MATCH = 0;
    public static final int WRAP = 1;
    public Button getButton(String text, int gravity, int type){
        Button button = new Button(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);;
        if(type == MATCH) lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        else if(type == WRAP) lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        else lp.width = type;
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.gravity = gravity;
        button.setLayoutParams(lp);
        button.setBackgroundResource(R.drawable.button1);
        button.setTextColor(Color.WHITE);
        button.setText(text);
        return button;
    }



    public void setDataNotFound(String table){
        setMessage("Data "+table+" Belum ada.\ninput data "+table+" terlebih dahulu");
    }
    public static final int MOVE = 0;
    public static final int SET_QUERY = 1;
    public DialogInterface.OnClickListener onClickDialog(final String title, final String message, final String method, final String sqlQuery, final AlertMessage alertMessage){
        DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AlertDialog.Builder(context, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("Ya", onClickDialog(method, sqlQuery, alertMessage))
                        .setNegativeButton("Tidak", null)
                        .show()
                ;
            }
        };
        return onClick;
    }
    public DialogInterface.OnClickListener onClickDialog(final Class<?> target, final boolean finish, final AlertMessage alertMessage){
        DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setUPDATE();
                SELECTED_GURU = alertMessage.getTable();
                move(target, finish, alertMessage.getId());
            }
        };
        return onClick;
    }
    public DialogInterface.OnClickListener onClickDialog(final String method, final String sqlQuery, final AlertMessage alertMessage){
        DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Connect(method, sqlQuery, context).addIntent(context.getClass(), true, "Data Telah dihapus").add(alertMessage.getTable(), alertMessage.getField(), alertMessage.getId());
            }
        };
        return onClick;
    }

    public void updateJSON(String table, String field, String oldValue, String newValue){
        try {
            if(table.equals("lintasMinat")) {
                for (int i = 0; i < AppObject.lintasMinat.length(); i++) {
                    if (AppObject.lintasMinat.getJSONObject(i).getString(field).equals(oldValue)) {
                        AppObject.lintasMinat.getJSONObject(i).put(field, newValue);
                        break;
                    }
                }
            }else if(table.equals("kelas")){
                for (int i = 0; i < AppObject.lintasMinat.length(); i++) {
                    if (AppObject.lintasMinat.getJSONObject(i).getString(field).equals(oldValue)) {
                        AppObject.lintasMinat.getJSONObject(i).put(field, newValue);
                        break;
                    }
                }
            }else if(table.equals("siswa")){
                for (int i = 0; i < AppObject.lintasMinat.length(); i++) {
                    if (AppObject.lintasMinat.getJSONObject(i).getString(field).equals(oldValue)) {
                        AppObject.lintasMinat.getJSONObject(i).put(field, newValue);
                        break;
                    }
                }
            }else if(table.equals("aspek")){
                for (int i = 0; i < AppObject.lintasMinat.length(); i++) {
                    if (AppObject.lintasMinat.getJSONObject(i).getString(field).equals(oldValue)) {
                        AppObject.lintasMinat.getJSONObject(i).put(field, newValue);
                        break;
                    }
                }
            }else if(table.equals("kriteria")){
                for (int i = 0; i < AppObject.lintasMinat.length(); i++) {
                    if (AppObject.lintasMinat.getJSONObject(i).getString(field).equals(oldValue)) {
                        AppObject.lintasMinat.getJSONObject(i).put(field, newValue);
                        break;
                    }
                }
            }else if(table.equals("subKriteria")){
                for (int i = 0; i < AppObject.lintasMinat.length(); i++) {
                    if (AppObject.lintasMinat.getJSONObject(i).getString(field).equals(oldValue)) {
                        AppObject.lintasMinat.getJSONObject(i).put(field, newValue);
                        break;
                    }
                }
            }else if(table.equals("nilai")){
                for (int i = 0; i < AppObject.lintasMinat.length(); i++) {
                    if (AppObject.lintasMinat.getJSONObject(i).getString(field).equals(oldValue)) {
                        AppObject.lintasMinat.getJSONObject(i).put(field, newValue);
                        break;
                    }
                }
            }else if(table.equals("rekomendasi")){
                for (int i = 0; i < AppObject.lintasMinat.length(); i++) {
                    if (AppObject.lintasMinat.getJSONObject(i).getString(field).equals(oldValue)) {
                        AppObject.lintasMinat.getJSONObject(i).put(field, newValue);
                        break;
                    }
                }
            }else if(table.equals("guruBK")){
                for (int i = 0; i < AppObject.lintasMinat.length(); i++) {
                    if (AppObject.lintasMinat.getJSONObject(i).getString(field).equals(oldValue)) {
                        AppObject.lintasMinat.getJSONObject(i).put(field, newValue);
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void waitAndMove(final Class<?> target, final boolean isFinish, int sec){
        sec = sec * 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                move(target, isFinish);
            }
        }, sec);
    }

    public void setMessage(Object o, int length){
        int duration = Toast.LENGTH_SHORT;
        if(length != 0) duration = Toast.LENGTH_LONG;
        Toast.makeText(context, ""+o, duration).show();
    }
    public void setMessage(Object o){
        Toast.makeText(context, ""+o, Toast.LENGTH_SHORT).show();
    }

    public String getText(TextView editText){
        return editText.getText().toString();
    }


    public Command refresh(String table, Class<?> newTarget, boolean finish, String message){
        new Connect("refresh", "select * from "+table.toLowerCase()+"", context)
                .add(table)
                .addIntent(newTarget, finish, message);
        return this;
    }

    public void setJSONArray(String table, String result){
        try {
            switch (table) {
                case "lintasMinat":
                    AppObject.lintasMinat = new JSONArray(result);
                    break;
                case "lintasminat":
                    AppObject.lintasMinat = new JSONArray(result);
                    break;
                case "guruBK":
                    AppObject.guruBK = new JSONArray(result);
                    break;
                case "gurubk":
                    AppObject.guruBK = new JSONArray(result);
                    break;
                case "admin":
                    AppObject.admin = new JSONArray(result);
                    break;
                case "guru":
                    AppObject.guru = new JSONArray(result);
                    break;
                case "guruOnLogin":
                    AppObject.guruOnLogin = new JSONArray(result);
                    break;
                case "siswa":
                    AppObject.siswa = new JSONArray(result);
                    break;
                case "siswaOnLogin":
                    AppObject.siswaOnLogin = new JSONArray(result);
                    break;
                case "kelas":
                    AppObject.kelas = new JSONArray(result);
                    break;
                case "aspek":
                    AppObject.aspek = new JSONArray(result);
                    break;
                case "kriteria":
                    AppObject.kriteria = new JSONArray(result);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String get(String table, int i, String field){
        switch (table) {
            case "lintasMinat":
                if (AppObject.lintasMinat != null) {
                    try {
                        return AppObject.lintasMinat.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "kelas":
                if (AppObject.kelas != null) {
                    try {
                        return AppObject.kelas.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "siswa":
                if (AppObject.siswa != null) {
                    try {
                        return AppObject.siswa.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "siswaOnLogin":
                if (AppObject.siswaOnLogin != null) {
                    try {
                        return AppObject.siswaOnLogin.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "guruBK":
                if (AppObject.guruBK != null) {
                    try {
                        return AppObject.guruBK.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "gurubk":
                if (AppObject.guruBK != null) {
                    try {
                        return AppObject.guruBK.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "aspek":
                if (AppObject.aspek != null) {
                    try {
                        return AppObject.aspek.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "kriteria":
                if (AppObject.kriteria != null) {
                    try {
                        return AppObject.kriteria.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "admin":
                if (AppObject.admin != null) {
                    try {
                        return AppObject.admin.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "guru":
                if (AppObject.guru != null) {
                    try {
                        return AppObject.guru.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "guruOnLogin":
                if (AppObject.guruOnLogin != null) {
                    try {
                        return AppObject.guruOnLogin.getJSONObject(i).getString(field);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        return "null";
    }
    public String getByKelas(String kd, String table, String field){
        switch (table){
            case "kelas":
                if (AppObject.kelas != null) {
                    for(int i=0;i<AppObject.kelas.length();i++) {
                        try {
                            if(kd.equals(get("kelas", i, "kelas"))) return AppObject.kelas.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
        return "";
    }

    public String getById(String kd, String table, String field){
        switch (table) {
            case "lintasMinat":
                if (AppObject.lintasMinat != null) {
                    for(int i=0;i<AppObject.lintasMinat.length();i++) {
                        try {
                            if(kd.equals(get("lintasMinat", i, "lintasMinat"))) return AppObject.lintasMinat.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "kelas":
                if (AppObject.kelas != null) {
                    for(int i=0;i<AppObject.kelas.length();i++) {
                        try {
                            if(kd.equals(get("kelas", i, "idKelas"))) return AppObject.kelas.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "siswa":
                if (AppObject.siswa != null) {
                    for(int i=0;i<AppObject.siswa.length();i++) {
                        try {
                            if(kd.equals(get("siswa", i, "idSiswa"))) return AppObject.siswa.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "siswaOnLogin":
                if (AppObject.siswaOnLogin != null) {
                    for(int i=0;i<AppObject.siswaOnLogin.length();i++) {
                        try {
                            if(kd.equals(get("siswaOnLogin", i, "idSiswa"))) return AppObject.siswaOnLogin.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "guruBK":
                if (AppObject.guruBK != null) {
                    for(int i=0;i<AppObject.guruBK.length();i++) {
                        try {
                            if(kd.equals(get("guruBK", i, "idGuruBK"))) return AppObject.guruBK.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "gurubk":
                if (AppObject.guruBK != null) {
                    for(int i=0;i<AppObject.guruBK.length();i++) {
                        try {
                            if(kd.equals(get("gurubk", i, "idGuruBK"))) return AppObject.guruBK.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "aspek":
                if (AppObject.aspek != null) {
                    for(int i=0;i<AppObject.aspek.length();i++) {
                        try {
                            if(kd.equals(get("aspek", i, "idAspek"))) return AppObject.aspek.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "kriteria":
                if (AppObject.kriteria != null) {
                    for(int i=0;i<AppObject.kriteria.length();i++) {
                        try {
                            if(kd.equals(get("kriteria", i, "idKriteria"))) return AppObject.kriteria.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "admin":
                if (AppObject.admin != null) {
                    for(int i=0;i<AppObject.admin.length();i++) {
                        try {
                            if(kd.equals(get("admin", i, "idAdmin"))) return AppObject.admin.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "guru":
                if (AppObject.guru != null) {
                    for(int i=0;i<AppObject.guru.length();i++) {
                        try {
                            if(kd.equals(get("guru", i, "idGuru"))) return AppObject.guru.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
        return "null";
    }

    public String getBy(String kd, String kdField, String table, String field){
        switch (table) {
            case "lintasMinat":
                if (AppObject.lintasMinat != null) {
                    for(int i=0;i<AppObject.lintasMinat.length();i++) {
                        try {
                            if(kd.equals(get("lintasMinat", i, kdField))) return AppObject.lintasMinat.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "kelas":
                if (AppObject.kelas != null) {
                    for(int i=0;i<AppObject.kelas.length();i++) {
                        try {
                            if(kd.equals(get("kelas", i, kdField))) return AppObject.kelas.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "siswa":
                if (AppObject.siswa != null) {
                    for(int i=0;i<AppObject.siswa.length();i++) {
                        try {
                            if(kd.equals(get("siswa", i, kdField))) return AppObject.siswa.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "siswaOnLogin":
                if (AppObject.siswaOnLogin != null) {
                    for(int i=0;i<AppObject.siswaOnLogin.length();i++) {
                        try {
                            if(kd.equals(get("siswaOnLogin", i, kdField))) return AppObject.siswaOnLogin.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "guruBK":
                if (AppObject.guruBK != null) {
                    for(int i=0;i<AppObject.guruBK.length();i++) {
                        try {
                            if(kd.equals(get("guruBK", i, kdField))) return AppObject.guruBK.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "aspek":
                if (AppObject.aspek != null) {
                    for(int i=0;i<AppObject.aspek.length();i++) {
                        try {
                            if(kd.equals(get("aspek", i, kdField))) return AppObject.aspek.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "kriteria":
                if (AppObject.kriteria != null) {
                    for(int i=0;i<AppObject.kriteria.length();i++) {
                        try {
                            if(kd.equals(get("kriteria", i, kdField))) return AppObject.kriteria.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "admin":
                if (AppObject.admin != null) {
                    for(int i=0;i<AppObject.admin.length();i++) {
                        try {
                            if(kd.equals(get("admin", i, kdField))) return AppObject.admin.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "guru":
                if (AppObject.guru != null) {
                    for(int i=0;i<AppObject.guru.length();i++) {
                        try {
                            if(kd.equals(get("guru", i, kdField))) return AppObject.guru.getJSONObject(i).getString(field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
        return "null";
    }

    public void addSpace(LinearLayout parent, int space) {
        View view = new View(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, space));
        parent.addView(view);
    }

    public String serialize(String[] arr){
        //a:8:{i:0;s:1:"1";i:1;s:1:"2";i:2;s:1:"3";i:3;s:1:"4";i:4;s:1:"5";i:5;s:1:"6";i:6;s:1:"7";i:7;s:1:"8";};
        //"a:8:{i:0;s:1:\"1\";i:1;s:1:\"2\";i:2;s:1:\"3\";i:3;s:1:\"4\";i:4;s:1:\"5\";i:5;s:1:\"6\";i:6;s:1:\"7\";i:7;s:1:\"8\";}"
        int a = arr.length;
        String jsonString = "a:"+a+":{";
        for(int i=0;i<arr.length;i++){
            int stringLength = arr[i].length();
            jsonString+="i:"+i+";s:"+stringLength+":\""+arr[i]+"\";";
        }
        jsonString+="}";
        return jsonString;
    }
    public String serialize(int[] arr){
        //a:8:{i:0;s:1:"1";i:1;s:1:"2";i:2;s:1:"3";i:3;s:1:"4";i:4;s:1:"5";i:5;s:1:"6";i:6;s:1:"7";i:7;s:1:"8";};
        //"a:8:{i:0;s:1:\"1\";i:1;s:1:\"2\";i:2;s:1:\"3\";i:3;s:1:\"4\";i:4;s:1:\"5\";i:5;s:1:\"6\";i:6;s:1:\"7\";i:7;s:1:\"8\";}"
        int a = arr.length;
        String jsonString = "a:"+a+":{";
        for(int i=0;i<arr.length;i++){
            int stringLength = String.valueOf(arr[i]).length();
            jsonString+="i:"+i+";s:"+stringLength+":\""+arr[i]+"\";";
        }
        jsonString+="}";
        return jsonString;
    }
    public String serialize(ArrayList<String> arr){
        int a = arr.size();
        String jsonString = "a:"+a+":{";
        for(int i=0;i<arr.size();i++){
            int stringLength = arr.get(i).length();
            jsonString+="i:"+i+";s:"+stringLength+":\""+arr.get(i)+"\";";
        }
        jsonString+="}";
        return jsonString;
    }
    public String serializeFloat(ArrayList<Float> arr){
        int a = arr.size();
        String jsonString = "a:"+a+":{";
        for(int i=0;i<arr.size();i++){
            int stringLength = String.valueOf(arr.get(i)).length();
            jsonString+="i:"+i+";s:"+stringLength+":\""+arr.get(i)+"\";";
        }
        jsonString+="}";
        return jsonString;
    }

    public String unserialize(String jsonString, int i, int jsonLength){
        String ii = String.valueOf(i);
        int iLength = ii.length();
        //String a = jsonString.substring(jsonString.indexOf("a:")+2, jsonString.indexOf(":{"));
        //String s0 = jsonString.substring( jsonString.indexOf("i:"+i+";")+(3+iLength), jsonString.indexOf("\";")+2 );
        jsonString+="last";
        String s0 = "";
        if(i+1 == jsonLength){
            s0 = jsonString.substring( jsonString.indexOf("i:"+i+";")+(3+iLength), jsonString.indexOf("\";}last")+2 );
        }else{
            s0 = jsonString.substring( jsonString.indexOf("i:"+i+";")+(3+iLength), jsonString.indexOf("\";i:"+(i+1))+2 );
        }
        String s = s0.substring(s0.indexOf("\"")+1, s0.indexOf("\";"));
        //String s = jsonString.substring( jsonString.indexOf("i:"+i, (1+iLength) ), jsonString.indexOf("\";") );
        return s;
    }

}
