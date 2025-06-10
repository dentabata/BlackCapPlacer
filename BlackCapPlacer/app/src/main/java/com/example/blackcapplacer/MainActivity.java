package com.example.blackcapplacer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private Button Button_Drop;
    private Button Button_Forward;
    private Button Button_Back;
    private Button Button_Left;
    private Button Button_Right;

    private int ledNum;

    final int LED1 = 0;
    final int LED2 = 1;
    final int LED3 = 2;
    final int LED4 = 3;
    final int LED5 = 4;
    private int stat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button_Drop = (Button) findViewById(R.id.button_drop);
        Button_Forward = (Button) findViewById(R.id.button_forward);
        Button_Back = (Button) findViewById(R.id.button_back);
        Button_Left = (Button) findViewById(R.id.button_left);
        Button_Right = (Button) findViewById(R.id.button_right);

        Button_Drop.setOnClickListener(this);
        Button_Forward.setOnClickListener(this);
        Button_Back.setOnClickListener(this);
        Button_Left.setOnClickListener(this);
        Button_Right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Log.d("HttpDrop","Drop onClick");
        if(view.getId()==R.id.button_drop){
            HttpDrop task = new HttpDrop(this);
            task.execute(ledNum, stat);
        }
    }
}
