package cz.cvut.fel.pjv.dudkolau;

/*
* This file was created following
* the tutorial on:
*  https://www.youtube.com/watch?v=YqHixKvqwrE&list=PLugjnBl3zi6bnLHsgYZvg24BIehuKKVWm&index=12https://www.youtube.com/watch?v=YqHixKvqwrE&list=PLugjnBl3zi6bnLHsgYZvg24BIehuKKVWm&index=12
*
*
* */
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int columns;
    private int offsetX;
    private int offsetY;
    private final int width;
    private final int height;
    public SpriteAnimation( ImageView imageView,
                            Duration duration,
                            int count,
                            int columns,
                            int offsetX,
                            int offsetY,
                            int width,
                            int height) {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

        //method
        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator((Interpolator.LINEAR));
        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    public void setOffsetX(int x ){
        this.offsetX = x;
    }

    public void setOffsetY(int y) {
        this.offsetY = y;
    }

    protected void interpolate(double frac) {
        final int index = Math.min((int)Math.floor(count*frac), count-1);
        final int x = (index%columns)*width + offsetX;
        final int y = (index/columns)*height + offsetY;
        imageView.setViewport(new Rectangle2D(x,y,width,height));
    }
}
