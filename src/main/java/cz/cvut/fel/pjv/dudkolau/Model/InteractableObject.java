package cz.cvut.fel.pjv.dudkolau.Model;

public class InteractableObject implements GameObject {
    private int xCoord;
    private int yCoord;

    @Override
    public int getXCoord() {
        return xCoord;
    }

    @Override
    public int getYCoord() {
        return yCoord;
    }

    public void interactAction(){}
}
