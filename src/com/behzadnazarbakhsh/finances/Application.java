package com.behzadnazarbakhsh.finances;

import com.behzadnazarbakhsh.finances.ui.ApplicationFrame;
import com.behzadnazarbakhsh.finances.ui.ApplicationModel;

import javax.swing.*;

public class Application{

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationFrame.newWindow();
            }
        });

    }
}
