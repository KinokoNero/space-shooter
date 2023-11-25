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
        adjustPositionToFitTheWindow();
    }

    private void adjustPositionToFitTheWindow() {
        final double minRelativeHorizontalPosition = 0;
        final double maxRelativeHorizontalPosition = 1 - this.width;
        final double minRelativeVerticalPosition = 0;
        final double maxRelativeVerticalPosition = 1 - this.height;

        if(this.horizontalPosition < minRelativeHorizontalPosition)
            this.horizontalPosition = minRelativeHorizontalPosition;
        else if(this.horizontalPosition > maxRelativeHorizontalPosition)
            this.horizontalPosition = maxRelativeHorizontalPosition;

        if(this.verticalPosition < minRelativeVerticalPosition)
            this.verticalPosition = minRelativeVerticalPosition;
        else if(this.verticalPosition > maxRelativeVerticalPosition)
            this.verticalPosition = maxRelativeVerticalPosition;
    }

    public double getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(double horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
        adjustPositionToFitTheWindow();
    }

    public double getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(double verticalPosition) {
        this.verticalPosition = verticalPosition;
        adjustPositionToFitTheWindow();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}