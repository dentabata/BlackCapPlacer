package com.example.blackcapplacer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDrop extends AsyncTask<Integer, Void, Void> {
    private Activity parentActivity;
    private ProgressDialog dialog = null;
    //実行するphpのURL
    private final String DEFAULTURL = "http://192.168.11.145/~pi/ledtest.php?";
    private String uri = null;

    public HttpDrop(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    //タスク開始時
    @Override
    protected void onPreExecute() {
        // ダイアログを表示
        dialog = new ProgressDialog(parentActivity);
        dialog.setMessage("通信中…");
        dialog.show();
    }

    //メイン処理
    @Override
    protected Void doInBackground(Integer... arg0) {
        uri = DEFAULTURL + "num=" + arg0[0].toString() + "&stat=" + arg0[1].toString();
        Log.d("RasPiDrop", uri);
        exec_get();
        return null;
    }

    //タスク終了時
    @Override
    protected void onPostExecute(Void result) {
        dialog.dismiss();
    }
    private String exec_get() {
        HttpURLConnection http = null;
        InputStream in = null;
        String src = new String();
        try {
            // URLにHTTP接続
            URL url = new URL(uri);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            // データを取得
            in = http.getInputStream();
            // HTMLソースを読み出す
            byte[] line = new byte[1024];
            int size;
            while (true) {
                size = in.read(line);
                if (size <= 0) break;
                src += new String(line);
            }
            // HTMLソースを表示
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (http != null) http.disconnect();
                if (in != null) in.close();
            } catch (Exception e) {
            }
        }
        return src;
    }
}
