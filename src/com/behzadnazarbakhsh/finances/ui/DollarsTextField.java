package com.behzadnazarbakhsh.finances.ui;

import com.behzadnazarbakhsh.finances.values.Dollars;
import com.behzadnazarbakhsh.finances.values.UserEnteredDollars;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

// If you ever want to subclass this class, br careful of tace condition with the event handler in
// the constructor. If you could execute before the subclass constructor.
public final class DollarsTextField extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField textField = new JTextField();
    private JLabel iconLabel = new JLabel();

    public DollarsTextField(Dollars initialValue){
        textField.setText(initialValue.toString());

        addComponents();
        addListener();
    }

    private void addListener() {
        addTextChangeListener(new ChangeListener() {
            @Override
            public void textChanged() {
                getDollars().render(new Resources(),new DollarsTextFieldRenderTargetAdapter(DollarsTextField.this));
            }
        });
    }

    private void addComponents() {
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        iconPanel.add(iconLabel);
        iconPanel.setOpaque(false);

        this.setLayout(new OverlayLayout(this)); //ToDO: check this
        this.add(iconPanel);
        this.add(textField);
        iconLabel.setVisible(false);
        textField.setOpaque(false);
    }

    public void setIcon(Icon icon) {
        if (icon == null){
         iconLabel.setVisible(false);
        }
        else{
            iconLabel.setIcon(icon);
            iconLabel.setVisible(true);
        }
    }

    public void setIconToolTip(String text){
        iconLabel.setToolTipText(text);
    }

    public String getText(){
        return textField.getText();
    }

    @Override
    public Color getForeground(){
        if(textField != null) return textField.getForeground();
        else return super.getForeground();
    }

    @Override
    public void setForeground(Color color){
        super.setForeground(color);
        if(textField != null) textField.setForeground(color);
    }

    public void setText(String value){
        textField.setText(value);
    }

    public UserEnteredDollars getDollars() {
        return new UserEnteredDollars(textField.getText());
    }

    public void addTextChangeListener(final ChangeListener listener) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fireEvent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fireEvent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fireEvent();
            }

            public void fireEvent(){
                listener.textChanged();
            }
        });
    }

    public interface ChangeListener {
        public void textChanged();
    }

    private static class DollarsTextFieldRenderTargetAdapter implements RenderTarget{
        private DollarsTextField field;

        public DollarsTextFieldRenderTargetAdapter(DollarsTextField field){
            this.field =field;
        }

        @Override
        public void setText(String text) {
            //This space intentionally left blank. We never overwrite user's text.
        }

        @Override
        public void setIcon(Icon icon, String toolTipText) {
            field.setIcon(icon);
            field.setIconToolTip(toolTipText);
        }

        @Override
        public void setForeGroundColor(Color color) {
            field.setForeground(color);
        }
    }

}
