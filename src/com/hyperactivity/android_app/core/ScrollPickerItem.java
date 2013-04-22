package com.hyperactivity.android_app.core;

import android.graphics.*;

public class ScrollPickerItem {
    private boolean visible;
    private float centerX;
    private float centerY;
    private float radius;
    private Paint circlePaint;
    private String text;
    private boolean showText;
    private float textMargin;
    private Paint textPaint;

    public ScrollPickerItem(int circleColor, String text, int textColor) {
        circlePaint = new Paint();
        circlePaint.setColor(circleColor);

        visible = false;
        centerX = 0f;
        centerY = 0f;
        radius = 0f;

        this.text = text;
        this.showText = false;
        this.textMargin = 0f;
        textPaint = new Paint();
        textPaint.setTextSize(24f);
        textPaint.setColor(textColor);
    }

    public void doUpdate(float delta) {

    }

    public void doDraw(Canvas canvas) {
        if (visible) {
            canvas.drawCircle(centerX, centerY, radius, circlePaint);
            Paint paint = new Paint();
            paint.setColor(android.graphics.Color.BLACK);
            canvas.drawRect(centerX - 1, centerY - radius, centerX + 1, centerY + radius, paint);

            if (showText) {
                Rect bounds = new Rect();
                paint.getTextBounds(text, 0, text.length(), bounds);
                canvas.drawText(text, centerX - bounds.width(), centerY + radius + textMargin + bounds.height(), textPaint);
            }
        }
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

    public void setShowText(boolean show) {
        this.showText = show;
    }

    public void setTextMargin(float margin) {
        this.textMargin = margin;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ScrollPickerItem) {
            ScrollPickerItem obj = (ScrollPickerItem) o;

            if (isVisible() == obj.isVisible() && getRadius() == obj.getRadius() && getCenterX() == obj.getCenterX() && getCenterY() == obj.getCenterY() && getCirclePaint().equals(obj.getCirclePaint())) {
                return true;
            }
        }

        return false;
    }
}
