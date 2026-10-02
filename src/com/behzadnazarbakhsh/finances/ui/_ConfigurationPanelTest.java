package com.behzadnazarbakhsh.finances.ui;

import javax.swing.*;

import com.behzadnazarbakhsh.finances.values.ValidDollars;
import net.miginfocom.swing.MigLayout;

import org.junit.*;

import java.awt.*;

import static org.junit.Assert.*;

public class _ConfigurationPanelTest {
    private ConfigurationPanel panel;
    private ApplicationModel model;

    private DollarsTextField startingBalanceField(){
        return (DollarsTextField) panel.getComponent(1);
    }

    private DollarsTextField costBasisField(){
        return (DollarsTextField) panel.getComponent(3);
    }

    private DollarsTextField yearlySpendingField(){
        return (DollarsTextField) panel.getComponent(5);
    }

    @Before
    public void setup() {
        model = new ApplicationModel();
        panel = new ConfigurationPanel(model);
    }

    @Test
    public void layout(){
        MigLayout manager = (MigLayout) panel.getLayout();
        assertEquals("layout", MigLayout.class, manager.getClass());
        assertEquals("layout constraints", "fillx, wrap 2", manager.getLayoutConstraints());
        assertEquals("column constraints", "[right]rel[grow]", manager.getColumnConstraints());

        Component[] components = panel.getComponents();

        assertEquals("# of components", 6, components.length);
        assertFormField("starting balance", components[0], components[1]);
        assertFormField("cost basis", components[2], components[3]);
    }

    @Test
    public void fieldInitializeToModelsValue(){
        assertEquals("starting balance field text", model.startingBalance(), startingBalanceField().getDollars());
        assertEquals("cost basis field text", model.startingCostBasis(), costBasisField().getDollars());
        assertEquals("yearly spending field text", model.yearlySpending(), yearlySpendingField().getDollars());
    }

    @Test
    public void startingBalanceFieldUpdateApplicationModel(){
        _ApplicationModelSpy mockModel = new _ApplicationModelSpy();
        panel = new ConfigurationPanel(mockModel);
        startingBalanceField().setText("664");
        assertEquals("configurationPanel should be updated", new ValidDollars(664), mockModel.setStartingBalanceCalledWith);
    }

    @Test
    public void costBasisFieldUpdateApplicationModel(){
        _ApplicationModelSpy mockModel = new _ApplicationModelSpy();
        panel = new ConfigurationPanel(mockModel);
        costBasisField().setText("670");
        assertEquals("configurationPanel should be updated", new ValidDollars(670), mockModel.setCostBasisCalledWith);
    }

    @Test
    public void yearlySpendingYearUpdateApplicationModel(){
        _ApplicationModelSpy mockModel = new _ApplicationModelSpy();
        panel = new ConfigurationPanel(mockModel);
        yearlySpendingField().setText("680");
        assertEquals("configurationPanel should be updated", new ValidDollars(680), mockModel.setYearlySpendingCalledWith);
    }

    private void assertFormField(String message, Component label, Component field) {
        MigLayout manager = (MigLayout)panel.getLayout();
        assertEquals(message + " label ", JLabel.class, label.getClass());
        assertEquals(message + " field ", DollarsTextField.class, field.getClass());
        assertEquals(message + " field constraints ", "growx", manager.getComponentConstraints(field));
    }




}
