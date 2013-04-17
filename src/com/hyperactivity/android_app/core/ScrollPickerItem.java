package com.hyperactivity.android_app.core;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ScrollPickerItem {
    private boolean visible;
    private float centerX;
    private float centerY;
    private float radius;
    private Paint circlePaint;

    public ScrollPickerItem(int circleColor) {
        circlePaint = new Paint();
        circlePaint.setColor(circleColor);

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

    @Override
    public boolean equals(Object o) {
        if(o instanceof ScrollPickerItem) {
            ScrollPickerItem obj = (ScrollPickerItem)o;

            if(isVisible() == obj.isVisible() && getRadius() == obj.getRadius() && getCenterX() == obj.getCenterX() && getCenterY() == obj.getCenterY() && getCirclePaint().equals(obj.getCirclePaint())) {
                return true;
            }
        }

        return false;
    }
}
