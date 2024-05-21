package mcp.mobius.wdmla.impl.ui.component;

import java.util.ArrayList;

import mcp.mobius.wdmla.api.ui.style.IProgressStyle;
import mcp.mobius.wdmla.impl.ui.drawable.ProgressDrawable;
import mcp.mobius.wdmla.impl.ui.value.Progress;
import mcp.mobius.wdmla.impl.ui.value.sizer.Padding;
import mcp.mobius.wdmla.impl.ui.value.sizer.Size;

public class ProgressComponent extends Component {

    public static final int DEFAULT_W = 100;
    public static final int DEFAULT_H = 12;

    public ProgressComponent(int current, int max) {
        super(
                new ArrayList<>(),
                new Padding(),
                new Size(DEFAULT_W, DEFAULT_H),
                new ProgressDrawable(new Progress(current, max)));
    }

    public ProgressComponent style(IProgressStyle style) {
        ((ProgressDrawable) foreground).style(style);
        return this;
    }
}
