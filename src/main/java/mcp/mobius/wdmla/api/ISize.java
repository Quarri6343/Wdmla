package mcp.mobius.wdmla.api;

public interface ISize {

    int getW();

    int getH();

    ISize w(int width);

    ISize h(int height);
}
