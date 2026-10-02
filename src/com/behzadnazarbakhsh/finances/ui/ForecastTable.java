package com.behzadnazarbakhsh.finances.ui;

import com.behzadnazarbakhsh.finances.values.SelfRenderable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class ForecastTable extends JTable {

    private static final long serialVersionUID = 1L;
    public static final Color STANDARD_BACKGROUND_COLOR = Color.WHITE;
    public static final Color ALTERNATE_BACKGROUND_COLOR = new Color(223,230,236);
    public static final Color SELECTION_BACKGROUND_COLOR = Color.lightGray;

    public ForecastTable(TableModel model) {
        super(model);
        this.setDefaultRenderer(SelfRenderable.class, SelfRenderable());
    }

    public TableCellRenderer SelfRenderable(){
        return new DefaultTableCellRenderer(){
            private static final long serialVersionUID = 1L;

            public void setValue(Object value){
                SelfRenderable renderable = (SelfRenderable) value;
                renderable.render(new Resources(), new LabelRenderTarget(this));
            }
        };
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
        Component cell = super.prepareRenderer(renderer, row,column);

        if (isCellSelected(row,column)) cell.setBackground(SELECTION_BACKGROUND_COLOR);
        else if (alternatingRow(row)) cell.setBackground(ALTERNATE_BACKGROUND_COLOR);
        else cell.setBackground(STANDARD_BACKGROUND_COLOR);
        return cell;
    }

    private static boolean alternatingRow(int row) {
        return row % 2 == 1;
    }
}
