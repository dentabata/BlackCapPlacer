package com.example.blackcapplacer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String DEVICE_IP = "192.168.0.1";
    private static final String PREFS_NAME = "AppSettings";
    private static final String PREF_KEY_IP = "device_ip";

    private Button Button_Settings;
    private Button Button_Drop;
    private Button Button_Forward;
    private Button Button_Back;
    private Button Button_Left;
    private Button Button_Right;
    private Button Button_Stop;
    private EditText ipEdit;

    private WebView webView; // ← クラス変数にする

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ボタンの初期化
        Button_Settings = findViewById(R.id.button_settings);
        Button_Drop = findViewById(R.id.button_drop);
        Button_Forward = findViewById(R.id.button_forward);
        Button_Back = findViewById(R.id.button_back);
        Button_Left = findViewById(R.id.button_left);
        Button_Right = findViewById(R.id.button_right);
        Button_Stop = findViewById(R.id.button_stop);

        Button_Settings.setOnClickListener(this);
        Button_Drop.setOnClickListener(this);
        Button_Forward.setOnClickListener(this);
        Button_Back.setOnClickListener(this);
        Button_Left.setOnClickListener(this);
        Button_Right.setOnClickListener(this);
        Button_Stop.setOnClickListener(this);

        // IPアドレス欄の初期化と復元
        ipEdit = findViewById(R.id.edit_ip_address);
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        DEVICE_IP = prefs.getString(PREF_KEY_IP, DEVICE_IP);
        ipEdit.setText(DEVICE_IP);

        // WebView の初期化
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        // === ↓↓↓ 追加したズーム設定 ↓↓↓ ===
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        // === ↑↑↑ ここまで ↑↑↑ ===

        // 初期のストリーム URL を読み込む
        loadStreamUrl();

        // IP変更時に保存＆WebView再読み込み
        ipEdit.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DEVICE_IP = s.toString();
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString(PREF_KEY_IP, DEVICE_IP);
                editor.apply();

                // 新しい URL を再読み込み
                loadStreamUrl();
            }
        });
    }

    private void loadStreamUrl() {
        String streamUrl = "http://" + DEVICE_IP + ":8080/?action=stream"; // 例: MJPEG-streamer
        webView.loadUrl(streamUrl);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.button_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);

        } else if (id == R.id.button_drop) {
            Log.d("HttpDrop", "Drop onClick");
            new HttpDrop(this, DEVICE_IP).execute();

        } else if (id == R.id.button_forward) {
            Log.d("HttpForward", "Forward onClick");
            new HttpForward(this, DEVICE_IP).execute();

        } else if (id == R.id.button_back) {
            Log.d("HttpBack", "Back onClick");
            new HttpBack(this, DEVICE_IP).execute();

        } else if (id == R.id.button_left) {
            Log.d("HttpLeft", "Left onClick");
            new HttpLeft(this, DEVICE_IP).execute();

        } else if (id == R.id.button_right) {
            Log.d("HttpRight", "Right onClick");
            new HttpRight(this, DEVICE_IP).execute();

        } else if (id == R.id.button_stop) {
            Log.d("HttpStop", "Stop onClick");
            new HttpStop(this, DEVICE_IP).execute();
        }
    }
}
