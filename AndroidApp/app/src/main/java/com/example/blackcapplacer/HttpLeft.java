package com.example.blackcapplacer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpLeft extends AsyncTask<Void, Void, Void> {
    private final Activity parentActivity;
    private final ProgressDialog dialog;
    private final String baseUrl;

    public HttpLeft(Activity parentActivity, String ipAddress) {
        this.parentActivity = parentActivity;
        this.baseUrl = "http://" + ipAddress + "/~pi/left.php";
        this.dialog = new ProgressDialog(parentActivity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("通信中…");
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("HttpDrop", "アクセスURL: " + baseUrl);
        execGet();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) dialog.dismiss();
    }

    private void execGet() {
        HttpURLConnection connection = null;
        InputStream in = null;
        try {
            URL url = new URL(baseUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            in = connection.getInputStream();
            byte[] buffer = new byte[1024];
            while (in.read(buffer) > 0) {}
        } catch (Exception e) {
            Log.e("HttpDrop", "通信エラー", e);
        } finally {
            try {
                if (in != null) in.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                Log.e("HttpDrop", "後処理エラー", e);
            }
        }
    }
}
