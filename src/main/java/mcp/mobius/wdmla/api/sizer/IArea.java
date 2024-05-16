package mcp.mobius.wdmla.api.sizer;

public interface IArea extends ISize {

    int getX();

    int getY();

    default int getEX() {
        return getX() + getW();
    }

    default int getEY() {
        return getY() + getH();
    }
}
