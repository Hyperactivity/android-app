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

    private ScrollPickerItem selectedItem;
    private LinkedList<ScrollPickerItem> items;

    private float movingSpeed;
    private ScrollPickerItem nextSelectedItem;
    private int moveDirection;

    private float sideX1;
    private float sideX2;

    public ScrollPickerItemManager(float canvasWidth, float canvasHeight, float itemPercentSize) {
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.itemPercentSize = itemPercentSize;
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
                }

                float totalMove = computeRadiusByPos(0);
                float currMove = Math.abs((nextSelectedItem.getCenterX() + move + diff - canvasWidth / 2f));
                float progress = (totalMove - currMove) / totalMove;

                Log.d(Constants.Log.TAG, "x: " + nextSelectedItem.getCenterX() + " tx: " + canvasWidth / 2f + " move: " + move + " diff: " + diff + " progress: " + progress);

                int pos = -getItemsLeft().size();
                for (ScrollPickerItem item : items) {
                    item.setCenterX(item.getCenterX() + move + diff);

                    float nr = item.getRadius();

                    if (moveDirection < 0) {
                        float r = computeRadiusByPos(Math.abs(pos));
                        float rd = signum(pos, -1) * (computeRadiusByPos(pos) - computeRadiusByPos(Math.abs(pos) + 1));
                        nr = r + progress * rd;

                    } else {
                        float r = computeRadiusByPos(Math.abs(pos));
                        float rd = -signum(pos, 1) * (computeRadiusByPos(pos) - computeRadiusByPos(Math.abs(pos) + 1));
                        nr = r + progress * rd;
                    }

                    item.setRadius(nr);
                    pos++;
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
        Iterator<ScrollPickerItem> it = getItemsLeft().iterator();
        while (it.hasNext()) {
            it.next().doDraw(canvas);
        }

        it = ((LinkedList<ScrollPickerItem>) getItemsRight()).descendingIterator();

        while (it.hasNext()) {
            it.next().doDraw(canvas);
        }

        selectedItem.doDraw(canvas);
    }

    public void addItem(int itemColor) {
        addItem(itemColor, false, false);
    }

    public void addItem(int itemColor, boolean selected) {
        addItem(itemColor, selected, false);
    }

    public void addItem(int itemColor, boolean selected, boolean calculate) {
        ScrollPickerItem item = new ScrollPickerItem(itemColor);

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
        float radius = itemPercentSize * canvasHeight / 2f;
        sideX1 = canvasWidth / 2f + radius;
        sideX2 = sideX1 + radius;

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
        float margin = (canvasHeight - diameter) / 2f;

        if (margin < 0f) {
            Log.w(Constants.Log.TAG, "Margin of scroll picker is negative.");
        }

        item.setRadius(computeRadiusByPos(pos));
        item.setCenterX(computeXByPos(pos));
        item.setCenterY(diameter / 2f + margin);

        item.setVisible(true);
    }

    private float computeRadiusByPos(int pos) {
        float diameter = itemPercentSize * canvasHeight;
        float radius = (diameter / 2f) / (1f + Math.abs(pos) * 0.5f);

        return radius;
    }

    private float computeXByPos(int pos) {
        float centerX = canvasWidth / 2f;

        float result = centerX;

        for (int i = 0; i < Math.abs(pos); i++) {
            result += Math.signum(pos) * computeRadiusByPos(i);
        }

        return result;
    }

    /**
     * @return -1 if value < 0. 1 if value > 0. resultOnZero if value = 0
     */
    private int signum(int value, int resultOnZero) {
        if (value < 0) {
            return -1;
        } else if (value > 0) {
            return 1;
        }

        return resultOnZero;
    }
}