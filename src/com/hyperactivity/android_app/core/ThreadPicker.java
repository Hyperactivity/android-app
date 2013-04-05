package com.hyperactivity.android_app.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.R;

public class ThreadPicker extends SurfaceView implements SurfaceHolder.Callback {
    private ThreadPickerThread thread;          //The thread that actually draws the animation
    private float xTouch;
    private float yTouch;
    private float xOffset;
    private float yOffset;
    private float scrollSpeed;
    private String friendID;

    public ThreadPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new ThreadPickerThread(holder, context);

        scrollSpeed = (float) getResources().getInteger(R.integer.scroll_speed);
    }

    /**
     * Standard window-focus override. Notice focus lost so we can pause on
     * focus lost. e.g. user switches to take a call.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            thread.pause();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xTouch = event.getX();
            yTouch = event.getY();
            thread.onTouchDown(xTouch, yTouch);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float deltaX = -(xTouch - event.getX());
            float deltaY = -(yTouch - event.getY());

            //offset should be cleared on scroll direction changes.
            if ((xOffset < 0 && deltaX > 0) || (xOffset > 0 && deltaX < 0)) {
                xOffset = 0;
            }
            if ((yOffset < 0 && deltaY > 0) || (yOffset > 0 && deltaY < 0)) {
                yOffset = 0;
            }

            xOffset += deltaX;
            yOffset += deltaY;

            thread.onTouchMove(deltaX * scrollSpeed, deltaY * scrollSpeed);

            xTouch = event.getX();
            yTouch = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            thread.onTouchUp(event.getX(), event.getY());

            xOffset = 0;
            yOffset = 0;
            xTouch = 0;
            yTouch = 0;
        }
        return true;
    }

    /*
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
    public void surfaceCreated(SurfaceHolder holder) {
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
        thread.setRunning(true);
        thread.start();
    }

    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setCanvasSize(width, height);
    }

    /*
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.e(Constants.Log.TAG, e.getMessage());
            }
        }
    }

    public ThreadPickerThread getThread() {
        return thread;
    }

    public class ThreadPickerThread extends Thread {
        //State-tracking constants
        public static final int STATE_READY = 1;
        public static final int STATE_RUNNING = 2;
        public static final int STATE_PAUSE = 3;
        int state;                      //The state of the renderer. READY, RUNNING or PAUSE
        boolean run = false;            //Indicate whether the surface has been created & is ready to draw
        SurfaceHolder surfaceHolder;    //Handle to the surface manager object we interact with
        long lastTime;                  //Used to figure out elapsed time between frames
        Context context;                //Handle to the application context, used to e.g. fetch Drawables.
        float fpsTimer;
        int fps;
        int frames;
        Paint fpsPaint;
        float canvasWidth = 1;          //width of the drawable area
        float canvasHeight = 1;         //height of the drawable area
        float canvasRatio;              //ratio of the drawable height and width. height / width
        Rect clipBounds;

        public ThreadPickerThread(SurfaceHolder surfaceHolder, Context context) {
            this.surfaceHolder = surfaceHolder;
            this.context = context;
        }

        /**
         * Dump state to the provided Bundle. Typically called when the
         * Activity is being suspended.
         *
         * @return Bundle with this view's state
         */
        public Bundle saveState(Bundle map) {
            synchronized (surfaceHolder) {
                if (map != null) {
                    //save values to map
                }
            }
            return map;
        }

        /**
         * Restores state from the indicated Bundle. Typically called when
         * the Activity is being restored after having been previously
         * destroyed.
         *
         * @param savedState Bundle containing the state
         */
        public synchronized void restoreState(Bundle savedState) {
            synchronized (surfaceHolder) {
                setState(STATE_PAUSE);

                //restore values from savedState.
            }
        }

        /**
         * Resumes from a pause.
         */
        public void unpause() {
            // Move the real time clock up to now
            synchronized (surfaceHolder) {
                lastTime = System.currentTimeMillis();
            }

            if (state == STATE_PAUSE) {
                setState(STATE_RUNNING);
            }
        }

        public void onTouchDown(float x, float y) {
        }

        public void onTouchUp(float x, float y) {
        }

        public void onTouchMove(float dx, float dy) {
        }

        @Override
        public void run() {
            fpsTimer = 0;
            fps = 0;
            frames = 0;
            long now;
            float delta;

            while (run) {
                Canvas c = null;
                try {
                    c = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        now = System.currentTimeMillis();
                        delta = (now - lastTime) / 1000.0f;
                        lastTime = now;

                        clipBounds = c.getClipBounds();

                        if (state == STATE_RUNNING || state == STATE_READY) {
                            doUpdate(delta);
                        }

                        doDraw(c);
                    }
                } catch (Exception e) {
                    Log.e(Constants.Log.TAG, "exception", e);
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        /**
         * logic goes here.
         */
        private void doUpdate(float delta) {
            //Compute fps
            frames++;
            fpsTimer += delta;
            if (fpsTimer >= 1) {
                fps = frames;
                frames = 0;
                fpsTimer = 0;
            }

            if (state == STATE_READY) {
                doInit();
                setState(STATE_RUNNING);
            } else if (state == STATE_RUNNING) {
                //TODO: do me
            }
        }

        /**
         * Pauses the physics doUpdate & animation.
         */
        public void pause() {
            synchronized (surfaceHolder) {
                if (state == STATE_RUNNING) {
                    setState(STATE_PAUSE);
                }
            }
        }

        /**
         * Initializes the thread
         */
        private void doInit() {
            fpsPaint = new Paint();
            fpsPaint.setColor(getResources().getColor(R.color.fps_color));
            fpsPaint.setTextSize(16f);

            //TODO: do me
        }

        /**
         * draw all the graphics
         */
        private void doDraw(Canvas canvas) {
            if (state == STATE_RUNNING) {
                //TODO: do me

                canvas.drawColor(01);
                canvas.drawText("FPS: " + fps, 10, 20, fpsPaint);
            }
        }

        public void setState(int mode) {
            this.state = mode;
        }

        /**
         * Used to signal the thread whether it should be running or not.
         * Passing true allows the thread to run; passing false will shut it
         * down if it's already running. Calling start() after this was most
         * recently called with false will result in an immediate shutdown.
         *
         * @param run true to run, false to shut down
         */
        public void setRunning(boolean run) {
            this.run = run;
        }

        /**
         * Callback invoked when the surface dimensions change.
         */
        public void setCanvasSize(float width, float height) {
            // synchronized to make sure these all change atomically
            synchronized (surfaceHolder) {

                canvasWidth = width;
                canvasHeight = height;

                canvasRatio = (float) canvasHeight / canvasWidth;

                Log.i(this.getClass().getName(), "Canvas width: " + canvasWidth + " height: " + canvasHeight + " ratio: " + canvasRatio);
            }
        }
    }
}

