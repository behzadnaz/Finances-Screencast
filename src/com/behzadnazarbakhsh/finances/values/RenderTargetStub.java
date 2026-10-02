package com.behzadnazarbakhsh.finances.values;

import com.behzadnazarbakhsh.finances.ui.RenderTarget;

import javax.swing.*;
import java.awt.*;

// Based on Martin Fowler article
// Dummy: Objects are passed around but never actually used
// Fake: Objects have actually working implementation, but usually take some shortcut
// which makes them not suitable for production
// Stubs: provide canned answers to calls made during the test, usually not responding
// at all to anything outside what is programmed in for the test.
// Mocks: objects pre-programmed with expectations which form a specification of the calls
// they are expected to receive.

class RenderTargetStub implements RenderTarget {
    public String text;
    public Icon icon;
    public String tooltipText;
    public Color foregroundcolor;

    public void setText(String text){
        this.text = text;
    }

    public  void setIcon(Icon icon, String tooltipText){
        this.icon = icon;
        this.tooltipText= tooltipText;
    }

    public void setForeGroundColor(Color color){
        this.foregroundcolor = color;
    }
}
