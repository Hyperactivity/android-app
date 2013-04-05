package com.hyperactivity.android_app.core;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ScrollPickerItem {
    private String text;
    private boolean visible;
    private float centerX;
    private float centerY;
    private float radius;
    private Paint circlePaint;
    private Paint textPaint;

    public ScrollPickerItem(String text, int circleColor, int textColor) {
        this.text = text;
        circlePaint = new Paint(circleColor);
        textPaint = new Paint(textPaint);

        visible = false;
        centerX = 0f;
        centerY = 0f;
        radius = 0f;
    }

    public void doUpdate(float delta) {

    }

    public void doDraw(Canvas canvas) {
        if(visible) {
            canvas.drawCircle(centerX, centerY, radius, circlePaint);

            if(text.length() > 0) {
                //draw text.
                canvas.drawText(text, centerX, centerY, textPaint);
            }
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setCirclePaint(Paint circlePaint) {
        this.circlePaint = circlePaint;
    }

    public String getText() {
        return text;
    }

    public boolean isVisible() {
        return visible;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getRadius() {
        return radius;
    }

    public Paint getCirclePaint() {
        return circlePaint;
    }
}
