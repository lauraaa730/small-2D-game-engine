package cz.cvut.fel.pjv.dudkolau.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static cz.cvut.fel.pjv.dudkolau.Constants.tileDimension;

public class HitBox {

    private Rectangle  rectangle;
    private static Rectangle testR1 = new Rectangle();
    private static Rectangle testR2 = new Rectangle();

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public void setRectangle(int x, int y, int width, int height) {
        this.rectangle = new Rectangle();
        this.rectangle.setY(y);
        this.rectangle.setX(x);
        this.rectangle.setWidth(width);
        this.rectangle.setHeight(height);
        this.rectangle.setFill(Color.TRANSPARENT);
        this.rectangle.setStroke(Color.RED); //maybe add to constants
        this.rectangle.setStrokeWidth(1);
    }


    public void changexCoord(int x) {
        this.rectangle.setX(x*tileDimension);

    }

    public void changeYCoord(int y) {
        this.rectangle.setY(y*tileDimension);
    }

    private boolean willCollide(HitBox a, HitBox b) {
        /*if (a.getCoords()[1]>= b.getCoords()[0] && a.getCoords()[3]>=b.getCoords()[2] &&
        a.getCoords()[0]<= b.getCoords()[1] && a.getCoords()[2]<=b.getCoords()[3]) {
            return true;
        }*/
        return false;
    }

    public static boolean willCollideWithObject(Entity entity, GameObject object, Directions d) {
        testR1.setX(entity.getHitBox().getRectangle().getX());
        testR1.setY(entity.getHitBox().getRectangle().getY());
        testR1.setWidth(entity.getHitBox().getRectangle().getWidth());
        testR1.setHeight(entity.getHitBox().getRectangle().getHeight());

        if (d == Directions.RIGHT) {
            testR1.setX(testR1.getX()+tileDimension);
        } else if (d == Directions.LEFT) {
            testR1.setX(testR1.getX()-tileDimension);
        } else if (d == Directions.UP) {
            testR1.setY(testR1.getY()-tileDimension);
        } else if (d == Directions.DOWN) {
            testR1.setY(testR1.getY()+tileDimension);
        }
        if (testR1.getBoundsInParent().intersects(object.getHitBox().getRectangle().getBoundsInParent())) {
            return true;
        }
        return false;
    }
    public static boolean willCollideWithEntity(Entity entity1, Entity entity2, Directions d1, Directions d2) {
        if (entity1.getHitBox().getRectangle().getBoundsInParent().intersects(entity2.getHitBox().getRectangle().getBoundsInParent())) {
            return true;
        }
        return false;
    }

}
