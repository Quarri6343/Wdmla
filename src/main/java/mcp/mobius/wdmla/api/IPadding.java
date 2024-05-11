package mcp.mobius.wdmla.api;

public interface IPadding {

    default IPadding vPadding(int padding) {
        return topPadding(padding).bottomPadding(padding);
    }

    default IPadding hPadding(int padding) {
        return leftPadding(padding).rightPadding(padding);
    }

    IPadding topPadding(int padding);

    IPadding bottomPadding(int padding);

    IPadding leftPadding(int padding);

    IPadding rightPadding(int padding);

    int getLeftPadding();

    int getRightPadding();

    int getTopPadding();

    int getBottomPadding();
}
