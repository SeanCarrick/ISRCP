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

package com.is2300.rcp.filters;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 * 
 * @version 0.1.0
 * @since 0.1.0
 */
public class FileFilterEx implements FileFilter {
    //<editor-fold defaultstate="collapsed" desc="Public Static Constants">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Member Fields">
    private final String[] extensions;    
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
    public FileFilterEx (String[] extensions) {
        this.extensions = extensions;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Static Methods">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    /**
     * Tests whether or not the specified abstract path name should be included
     * in a pathname list.
     * 
     * @param file The abstract pathname to be tested
     * @return boolean `true` if and only if the pathname should be included
     */
    @Override
    public boolean accept(File file) {
        for ( String extension : extensions ) {
            if ( file.getName().toLowerCase().endsWith(extension) 
                    || file.isDirectory() ) {
                return true;
            }
        }
        
        return false;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Instance Methods">
    
    //</editor-fold>

}
