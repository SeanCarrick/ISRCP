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

package com.is2300.rcp.printer;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 * 
 * @version 0.1.0
 * @since 0.1.0
 */
public class FormattedPrinter implements Printable, ActionListener {
    int[] pageBreaks;
    long lineCount;
    int numPages;
    String[] lines;
    File file;
    
    /**
     * Instantiates the `FormattedPrinter` object.
     * 
     * @param pathToPrintFile The path to the file to be printed.
     * @throws IllegalArgumentException in the event that the path parameter is
     *          null or blank.
     */
    public FormattedPrinter(String pathToPrintFile) {
        if ( pathToPrintFile == null || pathToPrintFile.isBlank() ) {
            throw new IllegalArgumentException("No path to file to print "
                    + "provided...");
        }
        
        file = new File(pathToPrintFile);
        
        try {
            lineCount = Files.lines(file.toPath()).count();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
        }
        
        lines = new String[(int)lineCount];
        
        BufferedReader in = null;
        try ( FileReader fr = new FileReader(file) ) {
            in = new BufferedReader(fr);
            
            int counter = 0;
            while ( counter < lineCount ) {
                lines[counter] = in.readLine();
                counter++;
            }
            
            in.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) 
            throws PrinterException {
        Font font = new Font("Courier", Font.PLAIN, 10);
        FontMetrics metrics = graphics.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
        
        if ( pageBreaks == null ) {
            // initTextLines();
            int linesPerPage = (int)(pageFormat.getImageableHeight()/lineHeight);
            int numBreaks = (lines.length - 1) / linesPerPage;
            pageBreaks = new int[numBreaks];
            
            for ( int b = 0; b < numBreaks; b++ ) {
                pageBreaks[b] = (b+1) * linesPerPage;
            }
        }
        
        if ( pageIndex > pageBreaks.length ) {
            return NO_SUCH_PAGE;
        }
        
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
        int y   = 0;
        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];
        int end = (pageIndex == pageBreaks.length) ? lines.length : pageBreaks[pageIndex];
        
        for ( int line = start; line < end; line++ ) {
            y += lineHeight;
            graphics.drawString(lines[line], 0, y);
        }
        
        return PAGE_EXISTS;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if ( ok ) {
            try {
                job.print();
            } catch ( PrinterException ex ) {
                System.err.println(ex);
                ex.printStackTrace(System.err);
            }
        }
    }

}
