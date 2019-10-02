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
    private Printer () {
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Static Methods">

    /**
     * This method handles the necessary work for printing the files.
     * 
     * @param toPrint   The folder which contains the files to be printed.
     * @param extension The extension of the files to be printed, so that only
     *                  the desired files are printed, and not everything in the
     *                  folder.
     * @return          {@code true} on success; {@code false} otherwise.
     * @throws NullPointerException if the pathname argument is null.
     */
    public static boolean print(String pathToFiles, String extension) {
        if ( pathToFiles == null ) {
            throw new NullPointerException("The path cannot be null.");
        }
        
        File file = new File(pathToFiles);
        
        if ( file.isDirectory() ) {
            for ( File f : file.listFiles() ) {
                if ( f.isDirectory() ) {
                    print(f.getAbsolutePath(), extension);
                } else {
                    boolean printed = printFiles(f.getParentFile().listFiles());
                }
            }
        }
        
        return true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Private Static Methods">
    private static boolean printFiles(File[] files) {
        for ( File file : files ) {
            System.out.println(file.getAbsolutePath());
        }
        
        return true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Instance Methods">
    
    //</editor-fold>

}
