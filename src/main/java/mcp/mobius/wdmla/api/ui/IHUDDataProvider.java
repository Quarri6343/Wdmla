package mcp.mobius.wdmla.api.ui;

import mcp.mobius.wdmla.api.IIdentifiedBlock;

public interface IHUDDataProvider {

    void appendHUDInfo (IWidget rootWidget, IIdentifiedBlock target);
}
