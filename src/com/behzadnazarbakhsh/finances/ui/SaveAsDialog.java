package com.behzadnazarbakhsh.finances.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveAsDialog extends FileDialog {
    private static final long serialVersionUID =1L;
    private ApplicationModel model;

    public SaveAsDialog(Frame parentWindow, ApplicationModel model){
        super(parentWindow, "Save As", FileDialog.SAVE);
        this.model = model;
    }

    //non-private for testing purpose
    void doSave(){
        try{
            String directory = this.getDirectory();
            String file = this.getFile();
            if(file != null) {
                model.save(new File(directory, file));
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(this.getParent(), "Could not save file: " + e.getLocalizedMessage(), "Save File", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void displayModally() {
        this.setVisible(true);
        this.doSave(); // This line of code is not tested.
    }
}
