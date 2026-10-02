package com.behzadnazarbakhsh.finances.ui;

import javax.swing.*;
import java.awt.*;

public interface RenderTarget {

    void setText(String text);
    void setIcon(Icon icon, String toolTipText);
    void setForeGroundColor(Color color);
}
