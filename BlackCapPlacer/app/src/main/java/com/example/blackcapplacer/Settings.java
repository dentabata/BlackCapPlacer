package com.example.blackcapplacer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blackcapplacer.R;

public class Settings extends AppCompatActivity {

    private static final int REQUEST_CODE_CREATE_PLAN = 1001;

    private RecyclerView roomFileList;
    private ImageView floorPlanImage;
    private Button addRoomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        roomFileList = findViewById(R.id.room_file_list);
        floorPlanImage = findViewById(R.id.floor_plan_image);
        addRoomButton = findViewById(R.id.button_add_room);

        addRoomButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, FloorPlanEditorActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CREATE_PLAN);
        });

        // TODO: ファイルリスト読み込み・RecyclerView設定
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CREATE_PLAN && resultCode == RESULT_OK) {
            // 戻ってきたデータを受け取る（例：画像 or JSON）
            Uri floorPlanUri = data.getParcelableExtra("floorPlanUri");
            if (floorPlanUri != null) {
                floorPlanImage.setImageURI(floorPlanUri);
                // TODO: ファイル保存やリスト追加処理
            }
        }
    }
}

