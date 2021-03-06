package com.hyperactivity.android_app.forum.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.hyperactivity.android_app.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-22
 * Time: 11:05
 */
public class Category {
    private int id;
    private String headLine;
    private int colorCode;
    private Category parentCategory;
    private List<Thread> threads;
    private Bitmap image;

    public int getId() {
        return id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public List<Thread> getThreads() {
        if (threads == null) {
            threads = new LinkedList<Thread>();
        }

        return threads;
    }

    public String getImageName() {
        String path = "";
        String extension = ".png";

        if (getHeadLine().equals("Kontakt")) {
            return path + "c_contact" + extension;
        } else if (getHeadLine().equals("Kreativitet")) {
            return path + "c_creativity" + extension;
        } else if (getHeadLine().equals("Allmänt")) {
            return path + "c_general" + extension;
        } else if (getHeadLine().equals("Hobby")) {
            return path + "c_hobby" + extension;
        } else if (getHeadLine().equals("Medicin")) {
            return path + "c_medicine" + extension;
        } else if (getHeadLine().equals("Skola")) {
            return path + "c_school" + extension;
        } else if (getHeadLine().equals("Tips")) {
            return path + "c_tips" + extension;
        } else {
            return null;
        }
    }

    public Bitmap getImage(Context context) {
        if (image == null) {
            String filename = getImageName();

//            TODO: not needed any more?
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inScaled = false;
//            options.inDither = false;
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            if (filename != null) {
                image = Utils.getBitmapFromAsset(context, filename);
            }

            if (image == null) {
                image = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(image);
                Paint paint = new Paint();
                paint.setColor(-getColorCode());
                c.drawCircle(300 / 2, 300 / 2, 300 / 2, paint);
            }
        }

        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category)) {
            return false;
        }

        Category c = (Category) o;

        if (this.getId() != c.getId()) return false;

        if (!Utils.objectsEqual(this.getHeadLine(), c.getHeadLine())) return false;

        if (this.getColorCode() != c.getColorCode())  return false;

        if (!Utils.objectsEqual(this.getImageName(), c.getImageName())) return false;

        return true;
    }

    @Override
    public String toString() {
        return "{" + getId() + " " + getHeadLine() + " " + getColorCode() + " " + getImageName() + " }";
    }
}
