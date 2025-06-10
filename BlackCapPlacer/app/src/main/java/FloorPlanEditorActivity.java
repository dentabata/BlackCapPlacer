import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blackcapplacer.FloorPlanEditorView;
import com.example.blackcapplacer.R;

public class FloorPlanEditorActivity extends AppCompatActivity {

    private FloorPlanEditorView editorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_plan_editor);

        editorView = findViewById(R.id.floor_plan_editor_view);
        Button saveButton = findViewById(R.id.button_save_plan);

        saveButton.setOnClickListener(v -> {
            // 仮：Bitmapを保存してUriを返す
            Uri imageUri = editorView.saveAsImage(); // 独自メソッド
            Intent resultIntent = new Intent();
            resultIntent.putExtra("floorPlanUri", imageUri);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
