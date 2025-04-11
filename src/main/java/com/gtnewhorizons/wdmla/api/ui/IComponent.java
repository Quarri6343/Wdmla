package com.gtnewhorizons.wdmla.api.ui;

import net.minecraft.util.ResourceLocation;

// spotless:off
/**
 * Base UI Component.<br>
 * How to implement a simple component without considering child element:<br>
 * <ul>
 * <li>Prepare the custom style class like {@link com.gtnewhorizons.wdmla.api.ui.style.ITextStyle} to decide what parameter is required to construct ui element</li>
 * <li>Prepare the {@link IDrawable} class.
 * This class will call rendering class to render the ui, based on provided style and {@link com.gtnewhorizons.wdmla.api.ui.sizer.IArea} information</li>
 * <li>Implement {@link com.gtnewhorizons.wdmla.impl.ui.component.Component} class. Call the foreground(drawable) with provided condition in tick method.</li>
 * </ul>
 * Full Example code:<br>
 * <pre>
 * {@code
 * public class SimpleTextComponent extends Component {
 *
 *     protected ITextStyle style;
 *
 *     public SimpleTextComponent(String text) {
 *         super(new Padding(), new TextSize(text), new TextDrawable(text));
 *         this.style = new TextStyle();
 *     }
 *
 *     public SimpleTextComponent style(ITextStyle style) {
 *         ((TextDrawable) this.foreground).style(style);
 *         this.style = style;
 *         return this;
 *     }
 *
 *     @Override
 *     public SimpleTextComponent size(@NotNull ISize size) {
 *         throw new IllegalArgumentException("You can't set the size of TextComponent!");
 *     }
 *
 *     @Override
 *     public void tick(int x, int y) {
 *         foreground.draw(new Area(x + padding.getLeft(), y + padding.getTop(), width, height));
 *     }
 * }
 * }
 * </pre>
 */
// spotless:on
public interface IComponent {

    /**
     * Client render tick event. Call everything that consists UI component.
     * @param x x position in the minecraft screen coordinate
     * @param y y position in the minecraft screen coordinate
     */
    void tick(int x, int y);

    /**
     * Must return positive value for rendering. If you want a negative sized element for layout, use {@link com.gtnewhorizons.wdmla.api.ui.sizer.IPadding}.
     * @return the ui component width.
     */
    int getWidth();

    /**
     * Must return positive value for rendering. If you want a negative sized element for layout, use {@link com.gtnewhorizons.wdmla.api.ui.sizer.IPadding}.
     * @return the ui component height.
     */
    int getHeight();

    /**
     * Applies custom identifier of resource location to component for later lookup purpose.<br>
     * If any component has potential to be used or replaced by other providers, appending a new tag is the best option.<br>
     * Only one tag can be applied to component for now.
     * @param tag unique identifier of new or existing tag
     * @return this component object
     */
    IComponent tag(ResourceLocation tag);

    /**
     * get the tag of this component.
     * @return the unique tag.
     */
    ResourceLocation getTag();
}
