package com.gtnewhorizons.wdmla.impl.ui.component;

import static com.gtnewhorizons.wdmla.impl.ui.drawable.TexturedProgressDrawable.TEXTURE_H;
import static com.gtnewhorizons.wdmla.impl.ui.drawable.TexturedProgressDrawable.TEXTURE_W;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;
import com.gtnewhorizons.wdmla.impl.ui.drawable.TexturedProgressDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import com.gtnewhorizons.wdmla.impl.ui.value.Progress;

// TODO: full implementation to use any texture
public class TexturedProgressComponent extends TooltipComponent {

    public TexturedProgressComponent(long current, long max) {
        super(
                new ArrayList<>(),
                new Padding(),
                new Size(TEXTURE_W, TEXTURE_H),
                new TexturedProgressDrawable(new Progress(current, max)));
    }

    @Override
    public TextComponent size(@NotNull ISize size) {
        throw new IllegalArgumentException("You can't set the size of this component!");
    }
}
