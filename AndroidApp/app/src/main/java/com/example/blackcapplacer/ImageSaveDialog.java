package com.example.blackcapplacer;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.function.Consumer;

public class ImageSaveDialog {

    public static void show(Context context, Uri imageUri, Consumer<File> onSaved) {
        EditText input = new EditText(context);
        input.setHint("ファイル名（拡張子不要）");

        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(48, 24, 48, 0);
        layout.addView(input);

        new AlertDialog.Builder(context)
                .setTitle("ファイル名を入力")
                .setView(layout)
                .setPositiveButton("保存", (dialog, which) -> {
                    String fileName = input.getText().toString().trim();
                    if (fileName.isEmpty()) {
                        Toast.makeText(context, "ファイル名が空です", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 拡張子を jpg に決め打ち（必要に応じて判定可）
                    File destFile = new File(context.getFilesDir(), fileName + ".jpg");
                    try {
                        InputStream in = context.getContentResolver().openInputStream(imageUri);
                        FileOutputStream out = new FileOutputStream(destFile);
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = in.read(buffer)) != -1) {
                            out.write(buffer, 0, len);
                        }
                        in.close();
                        out.close();

                        onSaved.accept(destFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "保存に失敗しました", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("キャンセル", null)
                .show();
    }
}

