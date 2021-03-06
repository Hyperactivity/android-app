package com.hyperactivity.android_app.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.forum.models.Category;

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
    private final static float RADIUS_SCALE = 0.5f;

    private float canvasHeight;
    private float canvasWidth;
    private float itemPercentSize;

    private ScrollPickerItem selectedItem;
    private LinkedList<ScrollPickerItem> items;

    private float movingSpeed;
    private int moveDirection;

    private ScrollPickerEventCallback callback;

    public ScrollPickerItemManager(float canvasWidth, float canvasHeight, float itemPercentSize) {
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.itemPercentSize = itemPercentSize;
        this.movingSpeed = 0f;
        this.moveDirection = 0;

        items = new LinkedList<ScrollPickerItem>();
    }

    public void move(float x) {
        float limit = 150f;
        float speed = 5f;

        if (moveDirection == 0) {
            try {
                if (x > limit) {
                    selectedItem.setShowText(false);
                    setSelectedItem(((LinkedList<ScrollPickerItem>) getItemsLeft()).getLast());
                    moveDirection = 1;
                } else if (x < -limit) {
                    selectedItem.setShowText(false);
                    setSelectedItem(((LinkedList<ScrollPickerItem>) getItemsRight()).getFirst());
                    moveDirection = -1;
                }
//              movingSpeed = moveDirection * x * speed;
                movingSpeed = 2000f;

            } catch (NoSuchElementException e) {
                moveDirection = 0;
                selectedItem.setShowText(true);
            }
        }
    }

    public void doUpdate(float delta) {
        if (moveDirection != 0) {
            if (moveDirection * selectedItem.getCenterX() < moveDirection * canvasWidth / 2f) {
                float move = moveDirection * movingSpeed * delta;
                float diff = moveDirection * (moveDirection * canvasWidth / 2f - moveDirection * (selectedItem.getCenterX() + move));

                if (moveDirection * diff > 0f) {
                    diff = 0f;
                }

                float totalMove = computeRadiusByPos(0);
                float currMove = Math.abs((selectedItem.getCenterX() + move + diff - canvasWidth / 2f));
                float progress = (totalMove - currMove) / totalMove;

                int pos = -getItemsLeft().size() - moveDirection;
                for (ScrollPickerItem item : items) {
                    item.setCenterX(item.getCenterX() + move + diff);

                    float r = computeRadiusByPos(Math.abs(pos));
                    float rd = -moveDirection * signum(pos, moveDirection * 1) * Math.abs(computeRadiusByPos(pos + moveDirection) - computeRadiusByPos(pos));
                    float nr = r + progress * rd;

                    item.setRadius(nr);
                    pos++;
                }

                //realign items since their radiuses have changed.
                realignItems(((LinkedList<ScrollPickerItem>) getItemsLeft()).descendingIterator(), -1);
                realignItems(getItemsRight().iterator(), 1);

            } else {
                moveDirection = 0;
                movingSpeed = 0f;
                selectedItem.setShowText(true);
            }
        }

        Iterator<ScrollPickerItem> it = items.iterator();
        while (it.hasNext()) {
            it.next().doUpdate(delta);
        }
    }

    private void realignItems(Iterator<ScrollPickerItem> it, int dir) {
        float prevX = selectedItem.getCenterX();
        float prevR = selectedItem.getRadius();
        while (it.hasNext()) {
            ScrollPickerItem item = it.next();
            item.setCenterX(prevX + dir * prevR);
            prevX = item.getCenterX();
            prevR = item.getRadius();
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

        if (selectedItem != null) {
            selectedItem.doDraw(canvas);
        }
    }

    public void addCategories(Context context, List<Category> categories, int textColor) {
        for(Category category : categories) {
            addItem(category.getImage(context), category.getHeadLine(), textColor, category, false, false);
        }

        if(categories.size() > 0) {
            recalculateItems();
        }
    }

    public void addItem(Bitmap image, String text, int textColor, Category category) {
        addItem(image, text, textColor, category, false, false);
    }

    public void addItem(Bitmap image, String text, int textColor, Category category, boolean selected) {
        addItem(image, text, textColor, category, selected, false);
    }

    public void addItem(Bitmap image, String text, int textColor, Category category, boolean selected, boolean calculate) {
        ScrollPickerItem item = new ScrollPickerItem(image, text, textColor, category);

        if (selected) {
            setSelectedItem(item);
            item.setShowText(true);
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
        if (selectedItem == null && items.size() > 0) {
            setSelectedItem(items.get(items.size() / 2));
            selectedItem.setShowText(true);
            selectedItem.setVisible(true);
        }

        int position = -getItemsLeft().size();

        for (ScrollPickerItem item : items) {
            updateItemFrame(item, position);
            position++;
        }
    }

    public List<ScrollPickerItem> getItemsLeft() {
        List<ScrollPickerItem> list = new LinkedList<ScrollPickerItem>();

        Iterator<ScrollPickerItem> it = ((LinkedList<ScrollPickerItem>)items.clone()).iterator();
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

        Iterator<ScrollPickerItem> it = ((LinkedList<ScrollPickerItem>)items.clone()).descendingIterator();
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
        float margin = (canvasHeight - diameter) / 3f;

        if (margin < 0f) {
            Log.w(Constants.Log.TAG, "Margin of scroll picker is negative.");
        }

        item.setRadius(computeRadiusByPos(pos));
        item.setCenterX(computeXByPos(pos));
        item.setCenterY(diameter / 2f + margin);

        item.setTextMargin(margin);
        item.setVisible(true);
    }

    private float computeRadiusByPos(int pos) {
        float diameter = itemPercentSize * canvasHeight;
        float radius = (diameter / 2f) / (1f + Math.abs(pos) * RADIUS_SCALE);

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

    public void reset() {
        items.clear();
        setSelectedItem(null);
        moveDirection = 0;
        movingSpeed = 0f;
    }

    private void setSelectedItem(ScrollPickerItem item) {
        selectedItem = item;

        if (callback != null) {
            callback.selectedItemChanged(selectedItem);
        }
    }

    public ScrollPickerItem getSelectedItem() {
        return selectedItem;
    }

    public void setCallback(ScrollPickerEventCallback callback) {
        this.callback = callback;
    }
}