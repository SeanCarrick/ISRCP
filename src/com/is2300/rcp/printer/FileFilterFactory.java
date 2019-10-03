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

import com.is2300.rcp.filters.AdaBodyFileFilter;
import com.is2300.rcp.filters.AdaSpecFileFilter;
import com.is2300.rcp.filters.ArduinoNanoSketchFileFilter;
import com.is2300.rcp.filters.AspClassicFileFilter;
import com.is2300.rcp.filters.AspNetFileFilter;
import com.is2300.rcp.filters.BashFileFilter;
import com.is2300.rcp.filters.BasicFileFilter;
import com.is2300.rcp.filters.BatchFileFilter;
import com.is2300.rcp.filters.CFileFilter;
import com.is2300.rcp.filters.CSharpFileFilter;
import com.is2300.rcp.filters.CgiFileFilter;
import com.is2300.rcp.filters.ColdFusionFileFilter;
import com.is2300.rcp.filters.CppFileFilter;
import com.is2300.rcp.filters.DigitalMarsDFileFilter;
import com.is2300.rcp.filters.ErlangFileFilter;
import com.is2300.rcp.filters.FlashFileFilter;
import com.is2300.rcp.filters.FlashFlexActionScriptFileFilter;
import com.is2300.rcp.filters.HtmlFileFilter;
import com.is2300.rcp.filters.JSharpFileFilter;
import com.is2300.rcp.filters.JavaFileFilter;
import com.is2300.rcp.filters.JavaScriptFileFilter;
import com.is2300.rcp.filters.LuaFileFilter;
import com.is2300.rcp.filters.MathematicaFileFilter;
import com.is2300.rcp.filters.MetaTraderFileFilter;
import com.is2300.rcp.filters.ObjCFileFilter;
import com.is2300.rcp.filters.PerlFileFilter;
import com.is2300.rcp.filters.PhpFileFilter;
import com.is2300.rcp.filters.PythonFileFilter;
import com.is2300.rcp.filters.PythonNotebookFileFilter;
import com.is2300.rcp.filters.RFileFilter;
import com.is2300.rcp.filters.RubyFileFilter;
import com.is2300.rcp.filters.RubyRailsFileFilter;
import com.is2300.rcp.filters.SslFileFilter;
import com.is2300.rcp.filters.TclFileFilter;
import com.is2300.rcp.filters.UnrealScriptFileFilter;
import com.is2300.rcp.filters.VBDotNetFileFilter;
import com.is2300.rcp.filters.VisualBasicScriptFileFilter;
import com.is2300.rcp.filters.XmlFileFilter;
import java.io.FileFilter;

/**
 *
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 * 
 * @version 0.1.0
 * @since 0.1.0
 */
public final class FileFilterFactory {
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
    private FileFilterFactory () {
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Static Methods">
    public static FileFilter createFileFilter(String lang) {
        FileFilter filter = null;
        
        switch ( lang ) {
            case "adab":
                filter = new AdaBodyFileFilter();
                break;
            case "adas":
                filter = new AdaSpecFileFilter();
                break;
            case "ardns":
                filter = new ArduinoNanoSketchFileFilter();
                break;
            case "asp":
                filter = new AspClassicFileFilter();
                break;
            case "aspnet":
                filter = new AspNetFileFilter();
                break;
            case "bash":
                filter = new BashFileFilter();
                break;
            case "basic":
                filter = new BasicFileFilter();
                break;
            case "bat":
                filter = new BatchFileFilter();
                break;
            case "c":
                filter = new CFileFilter();
                break;
            case "cpp":
                filter = new CppFileFilter();
                break;
            case "csharp":
                filter = new CSharpFileFilter();
                break;
            case "objc":
                filter = new ObjCFileFilter();
                break;
            case "cgi":
                filter = new CgiFileFilter();
                break;
            case "cold":
                filter = new ColdFusionFileFilter();
                break;
            case "dm":
                filter = new DigitalMarsDFileFilter();
                break;
            case "erl":
                filter = new ErlangFileFilter();
                break;
            case "flash":
                filter = new FlashFileFilter();
                break;
            case "flex":
                filter = new FlashFlexActionScriptFileFilter();
                break;
            case "html":
                filter = new HtmlFileFilter();
                break;
            case "jsharp":
                filter = new JSharpFileFilter();
                break;
            case "java":
                filter = new JavaFileFilter();
                break;
            case "js":
                filter = new JavaScriptFileFilter();
                break;
            case "lua":
                filter = new LuaFileFilter();
                break;
            case "math":
                filter = new MathematicaFileFilter();
                break;
            case "meta":
                filter = new MetaTraderFileFilter();
                break;
            case "perl":
                filter = new PerlFileFilter();
                break;
            case "php":
                filter = new PhpFileFilter();
                break;
            case "python":
                filter = new PythonFileFilter();
                break;
            case "jupiter":
                filter = new PythonNotebookFileFilter();
                break;
            case "r":
                filter = new RFileFilter();
                break;
            case "ruby":
                filter = new RubyFileFilter();
                break;
            case "rails":
                filter = new RubyRailsFileFilter();
                break;
            case "ssl":
                filter = new SslFileFilter();
                break;
            case "tcl":
                filter = new TclFileFilter();
                break;
            case "us":
                filter = new UnrealScriptFileFilter();
                break;
            case "vbnet":
                filter = new VBDotNetFileFilter();
                break;
            case "vb":
                filter = new VisualBasicScriptFileFilter();
                break;
            case "xml":
                filter = new XmlFileFilter();
                break;
        }
        
        return filter;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Instance Methods">
    
    //</editor-fold>

}
