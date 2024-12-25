// LetterTileView.java
package com.example.lab11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.core.content.ContextCompat;

public class LetterTileView extends View {
    private Paint borderPaint;
    private Paint textPaint;
    private Paint backgroundPaint;
    private String letter = "";
    private int state = 0; // 0 - empty, 1 - filled, 2 - correct, 3 - wrong position, 4 - incorrect

    public LetterTileView(Context context) {
        super(context);
        init();
    }

    private void init() {
        borderPaint = new Paint();
        borderPaint.setColor(Color.GRAY);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40);
        textPaint.setFakeBoldText(true);

        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = Math.min(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        switch (state) {
            case 0: // Empty
                backgroundPaint.setColor(Color.WHITE);
                break;
            case 1: // Filled
                backgroundPaint.setColor(Color.WHITE);
                break;
            case 2: // Correct
                backgroundPaint.setColor(Color.parseColor("#6aaa64"));
                textPaint.setColor(Color.WHITE);
                break;
            case 3: // Wrong position
                backgroundPaint.setColor(Color.parseColor("#c9b458"));
                textPaint.setColor(Color.WHITE);
                break;
            case 4: // Incorrect
                backgroundPaint.setColor(Color.parseColor("#787c7e"));
                textPaint.setColor(Color.WHITE);
                break;
        }

        canvas.drawRect(0, 0, width, height, backgroundPaint);
        canvas.drawRect(0, 0, width, height, borderPaint);

        if (!letter.isEmpty()) {
            Rect bounds = new Rect();
            textPaint.getTextBounds(letter, 0, 1, bounds);
            float textHeight = bounds.height();
            canvas.drawText(letter, width / 2f,
                    (height + textHeight) / 2f, textPaint);
        }
    }

    public void setLetter(String letter) {
        this.letter = letter.toUpperCase();
        state = letter.isEmpty() ? 0 : 1;
        invalidate();
    }

    public void setState(int state) {
        this.state = state;
        invalidate();
    }
}
