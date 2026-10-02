package com.behzadnazarbakhsh.finances.ui;

import javax.swing.*;
import java.net.URL;

public class Resources {

    public ImageIcon invalidDollarIcon() {
        URL iconURL = getClass().getClassLoader().getResource("com/behzadnazarbakhsh/finances/resources/invalid_dollars.png");
        return new ImageIcon(iconURL, "Invalid dollar amount");
    }
}
