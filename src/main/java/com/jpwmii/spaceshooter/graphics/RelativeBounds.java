package com.jpwmii.spaceshooter.graphics;

public class RelativeBounds {
    //Expected values are between 0 and 1; specifies position from the point (0,0) as a percentage of window width and height
    private double horizontalPosition;
    private double verticalPosition;
    private final double width;
    private final double height;

    public RelativeBounds(double horizontalPosition, double verticalPosition, double width, double height) {
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
        this.width = width;
        this.height = height;
    }

    private void adjustPositionToFitTheScreen() {
        //TODO: correct object position when object is near the window border (sprite may extend beyond the limits of the window)
    }

    public double getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(double horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
        adjustPositionToFitTheScreen();
    }

    public double getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(double verticalPosition) {
        this.verticalPosition = verticalPosition;
        adjustPositionToFitTheScreen();
    }

    public double getWidth() {
        return width;
    }

    /*public void setWidth(double width) {
        this.width = width;
    }*/

    public double getHeight() {
        return height;
    }

    /*public void setHeight(double height) {
        this.height = height;
    }*/
}