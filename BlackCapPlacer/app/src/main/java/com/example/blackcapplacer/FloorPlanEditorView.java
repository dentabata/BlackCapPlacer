package com.example.blackcapplacer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FloorPlanEditorView extends View {
    private List<RectF> rectangles = new ArrayList<>();
    // タッチ処理や四角形描画ロジックを追加

    public FloorPlanEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        for (RectF rect : rectangles) {
            canvas.drawRect(rect, paint);
        }
    }

    public Uri saveAsImage() {
        // Bitmap に描画内容を保存し、MediaStoreに登録→Uriを返す処理をここに書く
        return null;
    }
}
