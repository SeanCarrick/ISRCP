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
 * `PrintSetup` is the primary class for printing out project code files, or any
 * other types of files that are needed. The difference here is that this class
 * will recursively call its `print(String pathToFiles, FileFilter filter)`
 * method whenever it encounters a folder in the path. In this way, a person is
 * able to print all files of the type specified in the parameter `FileFilter
 * filter`, without the need of printing each folder's worth of files, one 
 * folder at a time.
 * 
 * The primary purpose behind this utility is, after all, for developers to use
 * this to print out the source code of their projects when they reach a release
 * build. Most IDEs do not provide a method for recursively printing **all** of
 * the source code files in the project as a single print job. Instead, 
 * developers are forced to, at best, print all of the source files in a single
 * folder of their project tree, and, at worst, print each file individually.
 * This really makes it hard to generate the hard-copy of the source code that 
 * is required for copyrighting purposes.
 * 
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 * 
 * @version 0.1.0
 * @since 0.23.384
 */
public class PrintSetup {
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
    private PrintSetup () {
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Static Methods">

    /**
     * This method handles the necessary work for printing the files.
     * 
     * Typically, this method will be invoked by a program in this manner:
     * 
     * ```java
     * FileFilter filter = new MyCustomFileFilter();
     * boolean success = PrintSetup.print(args[0], filter);
     * 
     * if ( success ) {
     *     // Do something here.
     * } else {
     *     // Report failure.
     * }
     * ```
     * 
     * @param pathToFiles   The folder which contains the files to be printed.
     * @param filter The extension of the files to be printed, so that only
     *                  the desired files are printed, and not everything in the
     *                  folder.
     * @return          `true` on success; `false` otherwise.
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
