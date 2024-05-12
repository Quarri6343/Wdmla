package mcp.mobius.wdmla.api;

public interface IArea extends ISize {

    int getX();

    int getY();

    IArea x(int x);

    IArea y(int y);

    @Override
    IArea w(int width);

    @Override
    IArea h(int height);

    default int getEX() {
        return getX() + getW();
    }

    default int getEY() {
        return getY() + getH();
    }
}
