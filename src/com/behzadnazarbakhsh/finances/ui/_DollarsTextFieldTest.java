package com.behzadnazarbakhsh.finances.ui;

import static org.junit.Assert.*;

import com.behzadnazarbakhsh.finances.values.ValidDollars;
import org.junit.*;

import javax.swing.*;
import java.awt.*;

public class _DollarsTextFieldTest {
    public DollarsTextField field;
    private JTextField textComponent;
    private JLabel iconComponent;
    private JPanel iconPanel;

   @Before
   public void setup(){
       field = new DollarsTextField(new ValidDollars(42));

       Component[] components = field.getComponents();

       //Note: for overlay layout to work properly, icon must be first. If you change
       //the way components are added to the container, be sure to do a visual check.
       iconPanel = (JPanel) components[0];
       iconComponent = (JLabel) iconPanel.getComponents()[0];
       textComponent = (JTextField) components[1];
    }

   @Test
   public void layout(){
       Component[] components = field.getComponents();
       assertEquals("layout", OverlayLayout.class, field.getLayout().getClass());
       assertEquals(":# of components", 2, components.length);

       FlowLayout iconLayout = (FlowLayout) iconPanel.getLayout();
       assertEquals("icon should be contain within a panel", JPanel.class, iconPanel.getClass());
       assertFalse("icon plan should be transparent", iconPanel.isOpaque());
       assertEquals("icon panel layout", FlowLayout.class, iconLayout.getClass());
       assertEquals("icon panel alignment", FlowLayout.RIGHT, iconLayout.getAlignment());

       assertEquals("layout should include the warning icon", JLabel.class, iconComponent.getClass());
       assertEquals("layout should include the text field", JTextField.class, textComponent.getClass());

       assertFalse("icon should be invisible by default", iconComponent.isVisible());
   }

   @Test
   public void canSetAndClearIcon(){
       ImageIcon icon = new ImageIcon();
       field.setIcon(icon);

       assertEquals("icon",icon, iconComponent.getIcon());
       assertTrue("icon label should be visible", iconComponent.isVisible());

       field.setIcon(null);
       assertFalse("icon label should not be visible", iconComponent.isVisible());
   }

   @Test
   public void settingForegroundColorChangesTextColor(){
       field.setForeground(Color.CYAN);
       assertEquals("can retrieve color",Color.CYAN, field.getForeground());
       assertEquals("actual text color changed",Color.CYAN, textComponent.getForeground());
   }

   @Test
   public void getForegroundColorIsBaseOnTextColorNotPanelColor(){
       textComponent.setForeground(Color.BLUE);
       assertEquals("color is based on text color", Color.BLUE, field.getForeground());
   }

   @Test
   public void canGetAndSetArbitraryText(){
       field.setText("foo");
       assertEquals("foo", field.getText());
   }

   @Test
   public void textReflectsDollarAmountUponConstruction(){
        assertEquals("$42", field.getText());
   }

   @Test
   public void canRetrieveAmount() {
       assertEquals(new ValidDollars(42), field.getDollars());
   }

   @Test
    public void changingTextsChangesDollarAmount(){
        field.setText("124");
        assertEquals(new ValidDollars(124), field.getDollars());
   }

   @Test
   public void canCallFunctionWhenTextChanges(){
        final boolean[] change = {false};
        DollarsTextField.ChangeListener listener =new DollarsTextField.ChangeListener(){
          public void textChanged(){
              change[0] = true;
          }
        };

        field.addTextChangeListener(listener);
        assertFalse("textChanged() shouldn't have been called yet", change[0]);
        field.setText("1000");
        assertTrue("textChanged() should have been called", change[0]);
   }

   @Test
    public void fieldIsRenderedByDomainClassWhenTextChanges() throws Exception {
       field.setText("10");
       assertEquals("start black", Color.BLACK, textComponent.getForeground());
       assertFalse("starts with no icon", iconComponent.isVisible());
       assertNull("start with no tooltips", iconComponent.getToolTipText());

       field.setText("  -10");
       assertEquals("should not change text", "  -10", textComponent.getText());
       assertFalse("should change color", Color.BLACK.equals(textComponent.getForeground()));

       field.setText("xxx");
       assertTrue("should set icon", iconComponent.isVisible());
       assertNotNull("should set tooltip text", iconComponent.getToolTipText());
   }
}
