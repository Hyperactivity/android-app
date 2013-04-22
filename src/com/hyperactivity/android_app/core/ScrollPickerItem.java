package com.hyperactivity.android_app.core;

import android.graphics.*;

public class ScrollPickerItem {
    private boolean visible;
    private float centerX;
    private float centerY;
    private float radius;
    private String text;
    private boolean showText;
    private float textMargin;
    private Paint textPaint;
    private Bitmap image;
    private Bitmap renderImage;

    public ScrollPickerItem(Bitmap image, String text, int textColor) {
        this.image = image;

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

        renderImage = image;
    }

    public void doUpdate(float delta) {

    }

    public void doDraw(Canvas canvas) {
        if (visible) {
            canvas.drawBitmap(renderImage, centerX - radius, centerY - radius, null);
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
        resizeImage();
    }

    /*public void setCirclePaint(Paint circlePaint) {
        this.circlePaint = circlePaint;
    }*/

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

//    public Paint getCirclePaint() {
//        return circlePaint;
//    }

    public void setShowText(boolean show) {
        this.showText = show;
    }

    public void setTextMargin(float margin) {
        this.textMargin = margin;
    }

    public void resizeImage() {
        renderImage = Bitmap.createScaledBitmap(image, (int)radius*2, (int)radius*2, true);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ScrollPickerItem) {
            ScrollPickerItem obj = (ScrollPickerItem) o;

            if (isVisible() == obj.isVisible() && getRadius() == obj.getRadius() && getCenterX() == obj.getCenterX() && getCenterY() == obj.getCenterY()) {
                return true;
            }
        }

        return false;
    }
}
