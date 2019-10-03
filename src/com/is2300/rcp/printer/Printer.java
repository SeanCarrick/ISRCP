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

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 * 
 * @version 0.1.0
 * @since 0.23.384
 */
public class Printer {
    //<editor-fold defaultstate="collapsed" desc="Public Static Constants">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Member Fields">
    private static int printCount;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Static Initializer">
    static {
        printCount = 0;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Intstance Initializer">
    {
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor(s)">
    private Printer () {
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Static Methods">

    /**
     * This method handles the necessary work for printing the files.
     * 
     * @param toPrint   The folder which contains the files to be printed.
     * @param filter The extension of the files to be printed, so that only
     *                  the desired files are printed, and not everything in the
     *                  folder.
     * @return          {@code true} on success; {@code false} otherwise.
     * @throws NullPointerException if the pathname argument is null.
     */
    public static boolean print(String pathToFiles, FileFilter filter) {
        if ( pathToFiles == null ) {
            throw new NullPointerException("The path cannot be null.");
        }
        
        boolean printed = false;
        File file = new File(pathToFiles);
        
        if ( file.isDirectory() ) {
            for ( File f : file.listFiles(filter) ) {
                if ( f.isDirectory() ) {
                    print(f.getAbsolutePath(), filter);
                } else {
                    printed = printFile(f);
                    printCount++;
                }
            }
        } else { 
            printed = printFile(file);
        }
        
        return printed;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Private Static Methods">
    private static boolean printFiles(File[] files) {
        for ( File file : files ) {
            System.out.println(file.getAbsolutePath());
        }
        
        return true;
    }
    
    private static boolean printFile(File file) {
        System.out.println(file.getAbsolutePath());
        
        return true;
    }
    
    public static int getPrintCount() {
        return printCount;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Instance Methods">
    
    //</editor-fold>

}
