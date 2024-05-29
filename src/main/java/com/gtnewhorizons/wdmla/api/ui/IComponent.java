package com.gtnewhorizons.wdmla.api.ui;

/**
 * Base UI Component interface
 */
public interface IComponent {

    void tick(int x, int y);

    int getWidth();

    int getHeight();
}
