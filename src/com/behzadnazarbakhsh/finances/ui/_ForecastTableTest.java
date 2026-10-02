package com.behzadnazarbakhsh.finances.ui;

import com.behzadnazarbakhsh.finances.values.SelfRenderable;
import org.junit.*;
import static org.junit.Assert.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class _ForecastTableTest {

    @Test
    public void tableRowsShouldUseStandardColor_WhenJustOneRow(){
        DefaultTableModel tableModel = new DefaultTableModel(0,1);
        tableModel.addRow(new String[]{""});
        JTable table = new ForecastTable(tableModel);

        assertEquals("row 0 should have standard background", ForecastTable.STANDARD_BACKGROUND_COLOR, getCellBackground(table,0, 0));
    }

    @Test
    public void tableRowsShouldAlternateColors_WhenThereAreNoColumnHeader(){
        DefaultTableModel tableModel = new DefaultTableModel(0,1);
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        JTable table = new ForecastTable(tableModel);

        assertEquals("row 0 should have standard background", ForecastTable.STANDARD_BACKGROUND_COLOR, getCellBackground(table,0, 0));
        assertEquals("row 1 should have alternate background", ForecastTable.ALTERNATE_BACKGROUND_COLOR, getCellBackground(table,1, 0));
        assertEquals("row 2 should have standard background", ForecastTable.STANDARD_BACKGROUND_COLOR, getCellBackground(table,2, 0));
        assertEquals( "row 1 should have alternate background", ForecastTable.ALTERNATE_BACKGROUND_COLOR, getCellBackground(table,3, 0));
    }

    @Test
    public void tableRowsShouldAlternateColors_WhenThereAreColumnHeader(){
        DefaultTableModel tableModel = new DefaultTableModel(0,1);
        tableModel.setColumnIdentifiers(new Object[] {"Header"});
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        JTable table = new ForecastTable(tableModel);

        assertEquals("row 0 should have standard background", ForecastTable.STANDARD_BACKGROUND_COLOR, getCellBackground(table,0, 0));
        assertEquals("row 1 should have alternate background", ForecastTable.ALTERNATE_BACKGROUND_COLOR, getCellBackground(table,1, 0));
        assertEquals("row 2 should have standard background", ForecastTable.STANDARD_BACKGROUND_COLOR, getCellBackground(table,2, 0));
        assertEquals( "row 1 should have alternate background", ForecastTable.ALTERNATE_BACKGROUND_COLOR, getCellBackground(table,3, 0));
    }

    @Test
    public void tableRowsShouldUseSelectionBackgroundColor_WhenSelected(){
        DefaultTableModel tableModel = new DefaultTableModel(0,1);
        tableModel.addRow(new String[]{""});
        JTable table = new ForecastTable(tableModel);

        table.setRowSelectionInterval(0,0);
        assertEquals("row 0 should have selection background", ForecastTable.SELECTION_BACKGROUND_COLOR, getCellBackground(table,0, 0));
    }

    @Test
    @SuppressWarnings("serial")
    public void tableShouldUseSelfRenderableObjectsRenderThemselves(){
        SelfRenderable renderable = new SelfRenderable() {

            @Override
            public void render(Resources resources, RenderTarget target) {
                target.setText("I render myself!");
            }
        };

        DefaultTableModel tableModel = new DefaultTableModel(0,1){
          @Override
          public Class<?> getColumnClass(int column){
              return SelfRenderable.class;
          }
        };
        tableModel.addRow(new SelfRenderable[] {renderable});
        JTable table = new ForecastTable(tableModel);

        String cellText = getCellText(table, 0, 0);
        assertEquals("I render myself!", cellText);
    }
    private String getCellText(JTable table, int row, int column){
        TableCellRenderer renderer = table.getCellRenderer(row,column);
        JLabel label = (JLabel) table.prepareRenderer(renderer, row, column);
        return label.getText();
    }

    private static Color getCellBackground(JTable table, int row, int column) {
        TableCellRenderer renderer = table.getCellRenderer(row, column);
        Component component = table.prepareRenderer(renderer, row, column);
        Color actualColor = component.getBackground();
        return actualColor;
    }
}
