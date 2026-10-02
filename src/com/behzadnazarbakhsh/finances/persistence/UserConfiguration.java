package com.behzadnazarbakhsh.finances.persistence;


import com.behzadnazarbakhsh.finances.values.UserEnteredDollars;

import java.io.*;

public class UserConfiguration {

    public static  boolean STUB_OUT_FILE_SYSTEM_FOR_TESTING =false;

    private File path = null;
    public UserEnteredDollars startingBalance;
    public UserEnteredDollars costBasis;
    public UserEnteredDollars yearlySpending;

    public File lastSavedToOrNullIfNeverSaved() {
        return path;
    }
    public void save(File path) throws IOException {
        writeFile(path, startingBalance, costBasis, yearlySpending);
        this.path =path;
    }

    private void writeFile(File path, UserEnteredDollars startingBalance, UserEnteredDollars costBasis, UserEnteredDollars yearlySpending) throws IOException {
        if(STUB_OUT_FILE_SYSTEM_FOR_TESTING) return;
        Writer writer = new BufferedWriter(new FileWriter(path));
        try {
            writeLine( writer,"com.behzad.finances,1");
            writeLine(writer, startingBalance.getUserText());
            writeLine(writer, costBasis.getUserText());
            writeLine(writer, yearlySpending.getUserText());
        } finally {
            writer.close(); //ToDO: Exception handling
        }
    }

    public void writeLine(Writer writer, String line) throws IOException {
        line = line.replace("\\", "\\\\");
        line = line.replace("\n", "\\n");
        writer.write(line + "\n");
    }
}
