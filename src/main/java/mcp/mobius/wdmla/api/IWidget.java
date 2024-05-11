package mcp.mobius.wdmla.api;

public interface IWidget {

    IWidget padding(IPadding padding);

    IWidget size(ISize size);

    void tick(int x, int y);

    int getWidth();

    int getHeight();
}
