package com.gtnewhorizons.wdmla.impl.ui.component;

import com.gtnewhorizons.wdmla.api.ui.style.IRectStyle;
import com.gtnewhorizons.wdmla.impl.ui.drawable.RectDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;

public class RectComponent extends Component {

    public static final int DEFAULT_W = 25;
    public static final int DEFAULT_H = 25;

    public RectComponent() {
        super(new Padding(), new Size(DEFAULT_W, DEFAULT_H), new RectDrawable());
    }

    public RectComponent style(IRectStyle style) {
        ((RectDrawable) foreground).style(style);
        return this;
    }
}
