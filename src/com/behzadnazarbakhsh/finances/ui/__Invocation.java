package com.behzadnazarbakhsh.finances.ui;

import javax.swing.*;
import java.util.Date;

import static org.junit.Assert.fail;

// This is used as the utility test classes
public abstract class __Invocation {
        abstract public void invoke();

        abstract boolean stopWaitingWhen();

        public static void invokeAndWaitFor(String message, int timeout, final __Invocation check) {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    check.invoke();
                }
            });

            long startTime = new Date().getTime();
            while (!check.stopWaitingWhen()) {
                Thread.yield();
                long elapsedMilliseconds = new Date().getTime() - startTime;
                if (elapsedMilliseconds > 1000) fail("Expected " + message + "within " + timeout + " milliseconds");
            }
            // we pass the test when we reach this point
        }
    }

