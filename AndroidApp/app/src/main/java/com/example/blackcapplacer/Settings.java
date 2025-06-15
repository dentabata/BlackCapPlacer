package com.example.blackcapplacer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class Settings extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1001;
    private static final int REQUEST_CODE_EDIT_IMAGE = 1002;

    private RecyclerView roomFileList;
    private ImageView floorPlanImage;
    private Button addRoomButton;
    private Button editRoomButton;

    private File selectedImageFile = null;
    private RoomFileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        roomFileList = findViewById(R.id.room_file_list);
        floorPlanImage = findViewById(R.id.floor_plan_image);
        addRoomButton = findViewById(R.id.button_add_room);
        editRoomButton = findViewById(R.id.button_edit_room);

        // RecyclerView の初期化
        adapter = new RoomFileAdapter(getFilesDir(), file -> {
            selectedImageFile = file;
            floorPlanImage.setImageURI(Uri.fromFile(file));
        });
        roomFileList.setLayoutManager(new LinearLayoutManager(this));
        roomFileList.setAdapter(adapter);

        // 「間取りを追加」ボタン → ギャラリーを開く
        addRoomButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        });

        // 「編集」ボタン → 選択したファイルがある場合のみ遷移
        editRoomButton.setOnClickListener(v -> {
            if (selectedImageFile != null) {
                Intent intent = new Intent(this, FloorPlanEditorActivity.class);
                intent.putExtra("imagePath", selectedImageFile.getAbsolutePath());
                startActivityForResult(intent, REQUEST_CODE_EDIT_IMAGE);
            } else {
                Toast.makeText(this, "編集する画像を選んでください", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                ImageSaveDialog.show(this, imageUri, savedFile -> {
                    adapter.refresh();
                    selectedImageFile = savedFile;
                    floorPlanImage.setImageURI(Uri.fromFile(savedFile));  // ← 追加
                    Toast.makeText(this, "保存しました", Toast.LENGTH_SHORT).show();
                });
            }
        } else if (requestCode == REQUEST_CODE_EDIT_IMAGE && resultCode == RESULT_OK) {
            adapter.refresh();
            floorPlanImage.setImageDrawable(null);
        }
    }
}
