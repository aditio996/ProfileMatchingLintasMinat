package dev.as.m.profilematchinglintasminat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Connect extends AsyncTask<String, Void, String> {
    private String method, sqlQuery;
    private String result = "-2";
    //public final String ADDRESS_PHP = "http://192.168.43.221/ProfileMatchingLintasMinatSMAN4Padang/phpCommand.php";

    private final String ERROR_QUERY = "0";
    private final String ERROR_METHOD = "-2";
    private final String NOT_FOUND = "-1";
    private final String SUCCESS = "1";
    private Context context;
    private Command command;

    public Connect(String method, String sqlQuery, Context context){
        this.method = method;
        this.sqlQuery = sqlQuery;
        this.context = context;
        command = new Command(context);
        execute(method, sqlQuery);
    }

    private String table;
    public Connect add(String table){
        this.table = table;
        return this;
    }
    private Class<?> newTarget;
    private boolean finish;
    private String message;
    public Connect addIntent(Class<?> newTarget, boolean finish, String message){
        this.newTarget = newTarget;
        this.finish = finish;
        this.message = message;
        return this;
    }
    String id, field;
    public Connect add(String table, String field, String id){
        this.table = table;
        this.field = field;
        this.id = id;
        return this;
    }

    private boolean loop = false;
    public Connect setLoop(boolean loop){
        this.loop = loop;
        return this;
    }

    @Override
    protected void onPostExecute(String s) {
        if(!result.equals(ERROR_METHOD)) {
            if (method.equals("login")) {
                if (!result.equals(NOT_FOUND)) {
                    command.setJSONArray(table, result);
                    if(newTarget != null)command.move(newTarget, finish);
                    if(table.equals("guruBK") || method.equals("gurubk"))
                        command.setMessage(message);
                    else if(table.equals("siswa"))
                        command.setMessage(message+"\n" +
                                "Selamat Datang "+command.get(table, 0, "namaSiswa"));
                    else if(table.equals("admin"))
                        command.setMessage(message);
                    else if(table.equals("guru"))
                        command.setMessage(message);
                } else {
                    command.setMessage("Username dan Password tidak cocok");
                }
            }
            if (method.equals("refresh")){
                if (!result.equals(NOT_FOUND)) {
                    command.setJSONArray(table, result);
                    if(newTarget == null) {
                        /*new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                command.refresh(table, newTarget, finish, message);
                            }
                        }, 10000);*/
                    }else {
                        command.move(newTarget, finish, message);
                    }
                }else{
                    //if(newTarget == null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                command.refresh(table, newTarget, finish, message);
                            }
                        }, 1000);
                    //}
                }
            }
            if (method.equals("insert") || method.equals("delete") || method.equals("update")){
                if(result.equals(SUCCESS)){
                    if(message!=null){if(!message.equals("")) command.setMessage(message);}
                    //refresh();
                    if(newTarget != null){
                        //command.move(newTarget, finish);
                        command.refresh(table, newTarget, finish, message);
                    }
                }else{
                    command.setMessage(""+method+" Gagal");
                }
            }
        }else{
            command.setMessage("Koneksi Error");
        }
    }
    private void refresh(){
        if(!method.equals("insert") && !method.equals("update")) {
            try {
                if (table.equals("lintasMinat")) {
                        for (int i = 0; i < AppObject.lintasMinat.length(); i++) {
                            if (AppObject.lintasMinat.getJSONObject(i).getString(field).equals(id)) {
                                if (method.equals("delete"))
                                    AppObject.lintasMinat.remove(i);
                                break;
                            }
                        }
                } else if (table.equals("kelas")) {
                        for (int i = 0; i < AppObject.kelas.length(); i++) {
                            if (AppObject.kelas.getJSONObject(i).getString(field).equals(id)) {
                                AppObject.kelas.remove(i);
                                break;
                            }
                        }
                } else if (table.equals("siswa")) {
                        for (int i = 0; i < AppObject.siswa.length(); i++) {
                            if (AppObject.siswa.getJSONObject(i).getString(field).equals(id)) {
                                AppObject.siswa.remove(i);
                                break;
                            }
                        }
                } else if (table.equals("aspek")) {
                        for (int i = 0; i < AppObject.aspek.length(); i++) {
                            if (AppObject.aspek.getJSONObject(i).getString(field).equals(id)) {
                                AppObject.aspek.remove(i);
                                break;
                            }
                        }
                } else if (table.equals("kriteria")) {
                        for (int i = 0; i < AppObject.kriteria.length(); i++) {
                            if (AppObject.kriteria.getJSONObject(i).getString(field).equals(id)) {
                                AppObject.kriteria.remove(i);
                                break;
                            }
                        }
                } else if (table.equals("guruBK")) {
                        for (int i = 0; i < AppObject.guruBK.length(); i++) {
                            if (AppObject.guruBK.getJSONObject(i).getString(field).equals(id)) {
                                AppObject.guruBK.remove(i);
                                break;
                            }
                        }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        String Address = AppObject.ADDRESS_PHP+"/ProfileMatchingLintasMinatSMAN4Padang/phpCommand.php";
        try {
            URL url = new URL(Address);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = "";
            data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                    URLEncoder.encode("sqlQuery", "UTF-8") + "=" + URLEncoder.encode(sqlQuery, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String responce = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                responce += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            result = responce;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
