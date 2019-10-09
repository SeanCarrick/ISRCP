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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.DocumentName;
import javax.print.attribute.standard.MediaName;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.MediaTray;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.PrinterResolution;
import javax.print.attribute.standard.Sides;

/**
 *
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 * 
 * @version 0.1.0
 * @since 0.1.0
 */
public class Printer {
    //<editor-fold defaultstate="collapsed" desc="Public Static Constants">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Member Fields">
    final String defaultPrinter;
    final PrintService service;
    final PrintRequestAttributeSet attributes;
    final DocPrintJob job;
    FileInputStream in;
    Doc doc;
    DocFlavor flavor;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Intstance Initializer">
    {
        service = PrintServiceLookup.lookupDefaultPrintService();
        defaultPrinter = service.getName();
        attributes = new HashPrintRequestAttributeSet();
        flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        job = service.createPrintJob();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor(s)">
    public Printer () {
        System.out.println("\nPrinting to: " + defaultPrinter + "\n\n");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    public boolean print(File fileToPrint) throws PrintException, IOException {
        if ( fileToPrint == null ) {
            throw new IllegalArgumentException("No file to print was provided.");
        }
        boolean success = false;
        attributes.add(new Copies(1));
        attributes.add(Sides.DUPLEX);
        attributes.add(OrientationRequested.PORTRAIT);
        attributes.add(MediaTray.MAIN);
//        attributes.add(MediaSize.NA.LETTER);
        flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        
        try ( FileInputStream in = new FileInputStream(fileToPrint) ) {
            DocAttributeSet set = new HashDocAttributeSet();
            set.add(Chromaticity.MONOCHROME);
            set.add(MediaName.NA_LETTER_WHITE);
            set.add(MediaSizeName.NA_LETTER);
            set.add(OrientationRequested.PORTRAIT);
            set.add(PrintQuality.NORMAL);
            set.add(Sides.DUPLEX);
            doc = new SimpleDoc(in, flavor, set);
            PrintJobWatcher watcher = new PrintJobWatcher(job);
            job.print(doc, attributes);
            watcher.waitForDone();
            in.close();
            
            InputStream ff = new ByteArrayInputStream("\f".getBytes());
            Doc docFF = new SimpleDoc(ff, flavor, null);
            job.print(docFF, null);
            watcher.waitForDone();
            
            success = true;
        } catch ( FileNotFoundException ex ) {
            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
            return success;
        } catch(IOException ex ) {
            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
            return success;
        } catch (PrintException ex) {
            if ( ex.getMessage().contains("already printing") ) {
                success = true;
            } else {
                System.err.println(ex.getMessage());
                ex.printStackTrace(System.err);
                return success;
            }
        }
        
        return success;
    }
    //</editor-fold>

}
