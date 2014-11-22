package model;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: model
 */
public class CSVParser {
    private static final Logger LOG = Logger.getGlobal();

    private CSVReader reader = null;
    private List<String[]> list = null;
    private CSVWriter writer = null;

    public CSVParser() {

    }
    public CSVParser(String filepath) {
        try {
            this.reader = new CSVReader(new FileReader(filepath), ';', ',', 1);
        } catch (FileNotFoundException e) {
            LOG.log(Level.WARNING, "Datei nicht gefunden.", e);
        }
    }

    public List<String[]> getLines() {
        try {
            this.list = reader.readAll();
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Datei kann nicht geöffnet werden.", e);
        }
        return list;
    }

    public boolean saveAs(File file, String[] entries) {
        try {
            writer = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
            writer.writeNext(entries);
            writer.close();
            return true;

        } catch (IOException e) {
            LOG.log(Level.WARNING, "Datei kann nicht geöffnet werden.", e);
        }
        return false;
    }
}
