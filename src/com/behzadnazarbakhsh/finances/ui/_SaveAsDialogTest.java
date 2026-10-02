package com.behzadnazarbakhsh.finances.ui;

import static org.junit.Assert.*;
import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class _SaveAsDialogTest{

    private  _ApplicationModelSpy mockModel = new _ApplicationModelSpy();
    private  SaveAsDialog dialog = new SaveAsDialog(null, mockModel);

    @After
    public void teardown(){
        dialog.dispose();
    }

    @Test
    public void layout(){
        assertEquals("save as dialog mode", FileDialog.SAVE,dialog.getMode());
        assertEquals("save as dialog title", "Save As", dialog.getTitle());
    }

    @Test
    public void saveAsDialogShouldTellApplicationModelToSaveWhenSavedButtonPushed(){
        doSave(dialog, "com/behzadnazarbakhsh/finances/resources/", "filename");
        assertEquals("application should be told to save", new File("com/behzadnazarbakhsh/finances/resources/filename"), mockModel.saveAsCalledWith);
    }

    @Test
    public void saveAsDialogShouldDoNothingWhenCancelButtonPushed(){
        doSave(dialog, null, null);
        assertNull("application model should not have been told to save", mockModel.saveAsCalledWith);
    }

    @Test
    public void saveAsDialogShouldHandleSaveExceptionGracefully(){
        final Frame frame = new Frame();
        final  SaveAsDialog exceptionThrowingDialog = createExceptionThrowingSaveAsDialog(frame);

        __Invocation.invokeAndWaitFor("Warning dialog", 1000, new __Invocation(){
            @Override
            public void invoke() {
                doSave(exceptionThrowingDialog, "/example", "filename");
            }

            @Override
            public boolean stopWaitingWhen() {
                Dialog dialog = warningDialogOrNullIfNotFound(frame);
                return dialog != null && dialog.isVisible();

            }
        });

        JDialog dialogWindow = (JDialog) warningDialogOrNullIfNotFound(frame);
        JOptionPane dialogPane = (JOptionPane)dialogWindow.getContentPane().getComponent(0);

        assertEquals("warning dialogWindow parent", frame, dialogWindow.getParent());
        assertEquals("warning dialogWindow title is 'save as'", "Save File", dialogWindow.getTitle());
        assertEquals("warning dialogWindow message", "Could not save file: generic exception" , dialogPane.getMessage());
        assertEquals(" Warning dialogWindow type should be 'warning'", JOptionPane.WARNING_MESSAGE,dialogPane.getMessageType());
    }

    private static void doSave(SaveAsDialog exceptionThrowingDialog, String directory, String filename) {
        exceptionThrowingDialog.setDirectory(directory);
        exceptionThrowingDialog.setFile(filename);
        exceptionThrowingDialog.doSave();
    }

    private SaveAsDialog createExceptionThrowingSaveAsDialog(Frame frame){
        final ApplicationModel exceptionThrower = new _ApplicationModelSpy(){
            @Override
            public void save(File saveFile) throws IOException{
                throw  new IOException("generic exception");
            }
        };
        return new SaveAsDialog(frame,exceptionThrower);
    }

    private Dialog warningDialogOrNullIfNotFound(Frame frame){
        Component[] childWindows = frame.getOwnedWindows();
        if (childWindows.length < 2) return  null;
        else return (Dialog)childWindows[1];
    }


}
