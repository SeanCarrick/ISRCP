/*
 * Copyright (C) 2019 Integrity Solutions
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pekinsoft.rcp.printer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 * 
 * @version 0.1.0
 * @since 0.1.0
 */
public class PrettyPrinter {
    //<editor-fold defaultstate="collapsed" desc="Public Static Constants">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Member Fields">
    private final char NEW_LINE = '\n';
    private final char TAB = '\t';
    
    private final String LINE_CONTINUATION = " \u21B5";
    private final String BLOCK_COMMENT_START = "/*";
    private final String BLOCK_COMMENT_END = "*/";
    private final String BLOCK_COMMENT_DOC = "/**";
    private final String BLOCK_COMMENT_LINE = " * ";
    private final String LINE_COMMENT = "//";
    private final String LINE_COMMENT_CONTINUED = "//+";
    
    private StringBuilder out;
    private String[] words;
    
    private int index;
    private int wordCount;
    private int pageWidth;
    private int currentWidth;
    private int charWidth;
    private int lineHeight;
    private int pageCount;
    private int linesPerPage;
    private int currentLine;
    private int charsPerLine;
    
    private File in;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Static Initializer">
    static {
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Intstance Initializer">
    {
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor(s)">
    /**
     * Creates a new `PrettyPrinter` object that allows plain text to be neatly
     * printed out. This class uses various techniques to keep the pages 
     * formatted nicely, regardless of the input file that is supplied.
     * 
     * For example, if the provided text file runs a single line that makes up
     * all of the text it contains, the text will be split into numerous lines
     * that are (default) 80 characters wide, but broken at the closest space
     * that is less than the 75th character. Whenever a line is longer than 80
     * characters, and is broken by the `PrettyPrinter` object, a line return
     * character (&crarr;) is inserted at the 75th character position to 
     * provide a visual clue the the line has been wrapped.
     * 
     * ***NOTE:*** Future versions of this class may include lexers to colorize
     * source code files appropriately.
     * 
     * @param inputFile             The file from which the text is to be 
     *                              printed.
     * @param desiredWidth          The desired width of the text on each line.
     *                              Defaults to 80 characters if not provided.
     * @param desiredLinesPerPage   The desired number of lines down the page.
     *                              Defaults to 50 lines, if not provided.
     */
    public PrettyPrinter (String inputFilePath, int desiredWidth, 
            int desiredLinesPerPage) {
        this.in = new File(inputFilePath);
        this.out = new StringBuilder();
        this.index = 0;
        this.wordCount = 0;
        this.pageWidth = 0;
        this.currentWidth = 0;
        this.charWidth = 0;
        this.lineHeight = 0;
        this.pageCount = 0;
        this.linesPerPage = desiredLinesPerPage > 0 ? linesPerPage : 50;
        this.currentLine = 0;
        this.charsPerLine = desiredWidth > 0 ? desiredWidth : 75;;
        
        fixText(index);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Static Methods">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Instance Methods">
    private void fixText(int index) {
        if ( index > wordCount ) {
            throw new IllegalArgumentException("Not enough words");
        }
        
        readFile();
        
        if ( index < wordCount ) {
            for ( String word : words ) {
                if ( currentWidth < charsPerLine ) {
                    out.append(word).append(" ");
                    currentWidth += word.length() + 1;
                    index++;
                } else {
                    if ( currentWidth == charsPerLine ) {
                        out.append("\n").append(word).append(" ");
                        currentWidth = word.length() + 1;
                        currentLine++;
                    } else if ( currentWidth > charsPerLine ) {
                        String tmp = out.toString().substring(0, 
                                out.toString().lastIndexOf(" "));
                        String droppedWord = out.toString().substring(
                                out.toString().lastIndexOf(" ") + 1);
                        out = new StringBuilder();
                        out.append(tmp).append(" ").append(LINE_CONTINUATION).append("\n");
                        out.append(droppedWord).append(" ").append(word).append(" ");
                        currentWidth = droppedWord.length() + word.length() + 2;
                        currentLine++;
                    }
                }
            }
        }
        
        writeTempFile();
    }
    
    private void readFile() {
        try ( BufferedReader in = new BufferedReader(new FileReader(this.in)); ) {
            StringBuilder sb = new StringBuilder();
            
            String line = in.readLine();
            while ( line != null ) {
                sb.append(line);
                line = in.readLine();
            } 
            
            in.close();
            
            String fileText = sb.toString(); //.replaceAll("\\s+", " ");
            words = fileText.split(" ");
            wordCount = words.length;
        } catch (IOException ex) {
            Logger.getLogger(PrettyPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void writeTempFile() {
        try {
            File tmpFile = File.createTempFile("prn", "txt");
            FileWriter fw = new FileWriter(tmpFile);
            BufferedWriter out = new BufferedWriter(fw);
            
            out.write(this.out.toString());
            
            out.close();
            fw.close();
            
            print(tmpFile.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(PrettyPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void print(String pathOfFileToPrint) {
        FormattedPrinter printer = new FormattedPrinter(pathOfFileToPrint);
        printer.actionPerformed(null);
    }
    //</editor-fold>

}
