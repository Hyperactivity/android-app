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
    private float textMargin;

    public ScrollPickerItem(String text, int circleColor, int textColor) {
        this.text = text;
        circlePaint = new Paint();
        circlePaint.setColor(circleColor);
        textPaint = new Paint();
        textPaint.setColor(textColor);

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
                canvas.drawText(text, centerX - textPaint.measureText(text)/2f, centerY + radius + textMargin + textPaint.getTextSize()/1.25f, textPaint);
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

    public void setTextMargin(float textMargin) {
        this.textMargin = textMargin;
    }

    public void setTextSize(float textSize) {
        textPaint.setTextSize(textSize);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ScrollPickerItem) {
            ScrollPickerItem obj = (ScrollPickerItem)o;

            if(getText().equals(obj.getText()) && isVisible() == obj.isVisible() && getRadius() == obj.getRadius() && getCenterX() == obj.getCenterX() && getCenterY() == obj.getCenterY() && getCirclePaint().equals(obj.getCirclePaint())) {
                return true;
            }
        }

        return false;
    }
}
