package com.behzadnazarbakhsh.finances.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class ApplicationFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public static final String TITLE = "Financial Projector";
    public static final Point INITIAL_POSITION = new Point(400,300);
    public static final Dimension INITIAL_SIZE = new Dimension(900, 400);

    private ApplicationModel model;
    private SaveAsDialog saveAsDialog;

    public static void newWindow() {
        new ApplicationFrame(new ApplicationModel()).setVisible(true);
    }

    public ApplicationFrame(ApplicationModel applicationmodel){
        super(TITLE);
        this.model = applicationmodel;
        configureWindow();

        addComponents();
    }

    private void configureWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(INITIAL_POSITION);
        setSize(INITIAL_SIZE);
    }

    private void addComponents() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(BorderLayout.CENTER,forecastTable());
        contentPane.add(BorderLayout.NORTH, getConfigurationPanel());
        setJMenuBar(menuBar());
        saveAsDialog = new SaveAsDialog(ApplicationFrame.this, model);
    }

    private Component forecastTable() {
        return new JScrollPane(new ForecastTable(model.stockMarketTableModel()));
    }

    private ConfigurationPanel getConfigurationPanel() {
        return new ConfigurationPanel(model);
    }

    private JMenuBar menuBar(){
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(newMenuItem());
        fileMenu.add(closeMenuItem());
        fileMenu.add(saveAsMenuItem());
        menubar.add(fileMenu);
        return menubar;
    }

    private static JMenuItem newMenuItem() {

        return menuItem("New", KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newWindow();
            }
        });
    }


    private JMenuItem closeMenuItem() {
        return menuItem("Close", KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private JMenuItem saveAsMenuItem() {
        return menuItem("Save As ...", KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAsDialog.displayModally();
            }
        });
    }

    private static JMenuItem menuItem(String name, KeyStroke accelerator, ActionListener action) {
        JMenuItem newMenuItem = new JMenuItem(name);
        newMenuItem.setAccelerator(accelerator);
        newMenuItem.addActionListener(action);
        return newMenuItem;
    }
}
