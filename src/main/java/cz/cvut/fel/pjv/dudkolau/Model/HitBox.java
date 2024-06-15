package cz.cvut.fel.pjv.dudkolau.Model;

//import javafx.scene.shape.Rectangle;
import java.awt.*;


import static cz.cvut.fel.pjv.dudkolau.Constants.tileDimension;

public class HitBox {

    private Rectangle  rectangle;
    private int xCoord;
    private int yCoord;
    private int width;
    private int height;


    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public void setRectangle(int x, int y, int width, int height, int xOffset, int yOffset) {
        this.rectangle = new Rectangle();
        this.xCoord = x+(xOffset/tileDimension);
        this.yCoord = y+(yOffset/tileDimension);
        this.width = width-2*xOffset;
        this.height = height-2*yOffset;
        this.rectangle.setLocation(x*tileDimension,y*tileDimension);
        this.rectangle.setSize(this.width,this.height);
        this.rectangle.setBounds(xCoord*tileDimension,yCoord*tileDimension, this.width, this.height);
    }


    public void changexCoord(int x) {
        this.xCoord+= x;
        this.rectangle.setLocation(xCoord*tileDimension, yCoord*tileDimension);
        this.rectangle.setBounds(this.rectangle);
    }

    public void changeYCoord(int y) {
        this.yCoord+=y;
        this.rectangle.setLocation( xCoord*tileDimension, yCoord*tileDimension);
        this.rectangle.setBounds(this.rectangle);
    }


    public static boolean checkCollisionWithObject(Entity entity, GameObject object) {
        if (entity.getHitBox().getRectangle().intersects(object.getHitBox().getRectangle())) {
            return true;
        }
        return false;
    }
    public static boolean checkCollisionWithEntity(Entity entity1, Entity entity2) {
        if (entity1.getHitBox().getRectangle().intersects(entity2.getHitBox().getRectangle())) {
            return true;
        }
        return false;
    }

}
