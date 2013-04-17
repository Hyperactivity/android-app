package com.hyperactivity.android_app.core;

import android.graphics.Canvas;
import android.util.Log;
import com.hyperactivity.android_app.Constants;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: lucas
 * Date: 2013-04-15
 * Time: 4:12 PM
 */
public class ScrollPickerItemManager {
    private float canvasHeight;
    private float canvasWidth;
    private float itemPercentSize;
    private float textSize;

    private ScrollPickerItem selectedItem;
    private LinkedList<ScrollPickerItem> items;

    private float movingSpeed;
    private ScrollPickerItem nextSelectedItem;
    private int moveDirection;

    public ScrollPickerItemManager(float canvasWidth, float canvasHeight, float itemPercentSize, float textSize) {
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.itemPercentSize = itemPercentSize;
        this.textSize = textSize;
        this.movingSpeed = 0f;
        this.nextSelectedItem = null;
        this.moveDirection = 0;

        items = new LinkedList<ScrollPickerItem>();
    }

    public void move(float x) {
        float limit = 150f;
        float speed = 5f;

        if (moveDirection == 0) {
            try {
                if (x > limit) {
                    nextSelectedItem = ((LinkedList<ScrollPickerItem>) getItemsLeft()).getLast();
                    moveDirection = 1;
                } else if (x < -limit) {
                    nextSelectedItem = ((LinkedList<ScrollPickerItem>) getItemsRight()).getFirst();
                    moveDirection = -1;
                }
                movingSpeed = moveDirection * x * speed;

            } catch (NoSuchElementException e) {
                moveDirection = 0;
                nextSelectedItem = null;
            }
        }
    }

    public void doUpdate(float delta) {
        if (moveDirection != 0) {
            if (moveDirection * nextSelectedItem.getCenterX() < moveDirection * canvasWidth / 2f) {
                float move = moveDirection * movingSpeed * delta;
                float diff = moveDirection * (moveDirection * canvasWidth / 2f - moveDirection * (nextSelectedItem.getCenterX() + move));

                if (moveDirection * diff > 0f) {
                    diff = 0f;
                } else if (diff < 0f) {
                    Log.d("hej", "hej");
                }

                Log.d(Constants.Log.TAG, "x: " + nextSelectedItem.getCenterX() + " tx: " + canvasWidth / 2f + " move: " + move + " diff: " + diff);

                for (ScrollPickerItem item : items) {
                    item.setCenterX(item.getCenterX() + move + diff);
                }
            } else {
                Log.d(Constants.Log.TAG, "Done: x: " + nextSelectedItem.getCenterX() + " tx: " + canvasWidth / 2f);
                moveDirection = 0;
                movingSpeed = 0f;
                selectedItem = nextSelectedItem;
                nextSelectedItem = null;
            }
        }

        Iterator<ScrollPickerItem> it = items.iterator();
        while (it.hasNext()) {
            it.next().doUpdate(delta);
        }
    }

    public void doDraw(Canvas canvas) {
        Iterator<ScrollPickerItem> it = items.iterator();
        while (it.hasNext()) {
            it.next().doDraw(canvas);
        }
    }

    public void addItem(String text, int itemColor, int textColor) {
        addItem(text, itemColor, textColor, false, false);
    }

    public void addItem(String text, int itemColor, int textColor, boolean selected) {
        addItem(text, itemColor, textColor, selected, false);
    }

    public void addItem(String text, int itemColor, int textColor, boolean selected, boolean calculate) {
        ScrollPickerItem item = new ScrollPickerItem(text, itemColor, textColor);

        if (selected) {
            selectedItem = item;
        }

        items.add(item);

        if (calculate) {
            recalculateItems();
        }
    }

    public void onCanvasChanged(float width, float height) {
        this.canvasWidth = width;
        this.canvasHeight = height;

        recalculateItems();
    }

    public void recalculateItems() {
        List<ScrollPickerItem> listLeft = getItemsLeft();
        List<ScrollPickerItem> listRight = getItemsRight();

        int position = -listLeft.size();
        for (ScrollPickerItem item : listLeft) {
            updateItemFrame(item, position);
            position++;
        }

        updateItemFrame(selectedItem, position);

        for (ScrollPickerItem item : listRight) {
            position++;
            updateItemFrame(item, position);
        }
    }

    public List<ScrollPickerItem> getItemsLeft() {
        List<ScrollPickerItem> list = new LinkedList<ScrollPickerItem>();

        Iterator<ScrollPickerItem> it = items.iterator();
        while (it.hasNext()) {
            ScrollPickerItem item = it.next();

            if (item.equals(selectedItem)) {
                return list;
            }

            list.add(item);
        }

        return list;
    }

    public List<ScrollPickerItem> getItemsRight() {
        List<ScrollPickerItem> list = new LinkedList<ScrollPickerItem>();

        Iterator<ScrollPickerItem> it = items.descendingIterator();
        while (it.hasNext()) {
            ScrollPickerItem item = it.next();

            if (item.equals(selectedItem)) {
                return list;
            }

            list.add(0, item);
        }

        return list;
    }

    private void updateItemFrame(ScrollPickerItem item, int pos) {
        float diameter = itemPercentSize * canvasHeight;
        float margin = (canvasHeight - diameter - textSize) / 3f;
        float centerX = canvasWidth / 2f;

        if (margin < 0f) {
            Log.w(Constants.Log.TAG, "Margin of scroll picker is negative.");
        }

        item.setRadius(diameter / 2f);
        item.setCenterX(centerX + pos * diameter);
        item.setCenterY(item.getRadius() + margin);
        item.setTextSize(textSize);
        item.setTextMargin(margin);

        item.setVisible(true);
    }
}
