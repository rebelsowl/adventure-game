package com.mae.utility;

import java.awt.*;

/**
 * Helper class for event areas
 */
public class EventRectangle extends Rectangle {

    int defaultX, getDefaultY;
    boolean eventDone = false;


    /**
     * Constructs a new <code>Rectangle</code> whose upper-left corner is
     * specified as
     * {@code (x,y)} and whose width and height
     * are specified by the arguments of the same name.
     *
     * @param x      the specified X coordinate
     * @param y      the specified Y coordinate
     * @param width  the width of the <code>Rectangle</code>
     * @param height the height of the <code>Rectangle</code>
     * @since 1.0
     */
    public EventRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public int getDefaultX() {
        return defaultX;
    }

    public void setDefaultX(int defaultX) {
        this.defaultX = defaultX;
    }

    public int getGetDefaultY() {
        return getDefaultY;
    }

    public void setGetDefaultY(int getDefaultY) {
        this.getDefaultY = getDefaultY;
    }

    public boolean isEventDone() {
        return eventDone;
    }

    public void setEventDone(boolean eventDone) {
        this.eventDone = eventDone;
    }
}
