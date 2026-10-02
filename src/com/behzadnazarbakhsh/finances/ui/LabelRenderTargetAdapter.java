package com.behzadnazarbakhsh.finances.ui;

import javax.swing.*;
import java.awt.*;

class LabelRenderTarget implements RenderTarget {
    private JLabel label;

    public LabelRenderTarget(JLabel label){
        this.label = label;
    }

    @Override
    public void setText(String text) {
        label.setText(text);
    }

    @Override
    public void setIcon(Icon icon, String toolTipText) {
        label.setIcon(icon);
        label.setToolTipText(toolTipText);
    }

    @Override
    public void setForeGroundColor(Color color) {
        label.setForeground(color);
    }
}
