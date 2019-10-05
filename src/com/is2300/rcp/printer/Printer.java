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
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

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

    //<editor-fold defaultstate="collapsed" desc="Static Initializer">
    static {
        
    }
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

    //<editor-fold defaultstate="collapsed" desc="Public Static Methods">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    public boolean print(File fileToPrint) {
        if ( fileToPrint == null ) {
            throw new IllegalArgumentException("No file to print was provided.");
        }
        boolean success = false;
        
        try ( FileInputStream in = new FileInputStream(fileToPrint) ) {
            doc = new SimpleDoc(in, flavor, null);
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

    //<editor-fold defaultstate="collapsed" desc="Private Instance Methods">
    
    //</editor-fold>

}
