package com.example.blackcapplacer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;

public class FloorPlanEditorActivity extends AppCompatActivity {

    private DrawableImageView drawableImageView;
    private File imageFile;
    private Bitmap originalBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_plan_editor);

        drawableImageView = findViewById(R.id.drawable_image_view);
        Button saveButton = findViewById(R.id.button_save);
        Button deleteButton = findViewById(R.id.button_delete);
        Button resetButton = findViewById(R.id.button_reset);

        String path = getIntent().getStringExtra("imagePath");
        if (path == null) {
            Toast.makeText(this, "画像パスがありません", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        imageFile = new File(path);
        originalBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        drawableImageView.setImageBitmap(originalBitmap);

        saveButton.setOnClickListener(v -> saveModifiedImage());

        deleteButton.setOnClickListener(v -> {
            if (imageFile.exists()) {
                boolean deleted = imageFile.delete();
                if (deleted) {
                    Toast.makeText(this, "画像を削除しました", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);  // 削除通知用に結果返す
                    finish();
                } else {
                    Toast.makeText(this, "画像の削除に失敗しました", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetButton.setOnClickListener(v -> {
            drawableImageView.resetMarks();
            drawableImageView.setImageBitmap(originalBitmap);
        });

    }

    private void saveModifiedImage() {
        Bitmap modified = drawableImageView.getMarkedBitmap();

        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            modified.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();

            Toast.makeText(this, "保存しました", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("imagePath", imageFile.getAbsolutePath());
            setResult(RESULT_OK, resultIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "保存に失敗しました", Toast.LENGTH_SHORT).show();
        }
    }

}
