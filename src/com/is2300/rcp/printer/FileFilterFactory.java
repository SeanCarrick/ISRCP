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

import com.is2300.rcp.StartPrinting;
import com.is2300.rcp.filters.FileFilterEx;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 * 
 * @version 0.1.0
 * @since 0.1.0
 */
public final class FileFilterFactory {
    //<editor-fold defaultstate="collapsed" desc="Public Static Constants">
    public static final Map<String, String[]> LANG_CODES;
    public static final Map<String, String> LANGS;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Member Fields">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Static Initializer">
    static {
        LANG_CODES = new HashMap<>();
        LANGS = new HashMap<>();
        loadLanguagesMap();
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
    public static FileFilter createFileFilter(String language) {
        return new FileFilterEx(LANG_CODES.get(language));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Instance Methods">
    
    //</editor-fold>

    static void loadLanguagesMap() {

        LANGS.put("adab", "Ada Body");
        LANGS.put("adas", "Ada Spec");
        LANGS.put("arduino", "Arduino / Nano Sketch");
        LANGS.put("asp", "ASP");
        LANGS.put("aspnet", "ASP.Net");
        LANGS.put("bash", "Bash Scripting");
        LANGS.put("basic", "BASIC");
        LANGS.put("batch", "Batch Files");
        LANGS.put("c", "C");
        LANGS.put("objc", "Objective C");
        LANGS.put("cpp", "C++");
        LANGS.put("C#", "cs");
        LANGS.put("CGI", "cgi");
        LANGS.put("Cold Fusion", "cold");
        LANGS.put("Digital Mars", "dm");
        LANGS.put("Erlang", "erl");
        LANGS.put("Flash", "flash");
        LANGS.put("Flash/Flex Action", "flex");
        LANGS.put("HTML", "html");
        LANGS.put("J#", "jsharp");
        LANGS.put("Java", "java");
        LANGS.put("JavaScript", "js");
        LANGS.put("Lua", "lua");
        LANGS.put("Mathematica", "math");
        LANGS.put("MetaTrader", "meta");
        LANGS.put("Perl", "perl");
        LANGS.put("PHP", "php");
        LANGS.put("Python", "python");
        LANGS.put("Python Notebook", "jupyter");
        LANGS.put("R", "r");
        LANGS.put("Ruby", "ruby");
        LANGS.put("Ruby on Rails", "rails");
        LANGS.put("SSL", "ssl");
        LANGS.put("TCL", "tcl");
        LANGS.put("Unreal Script", "us");
        LANGS.put("VB.net", "vbnet");
        LANGS.put("Visual Basic", "vbs");
        LANGS.put("XML", "xml");

        LANG_CODES.put("adab", new String[]{".adb"});
        LANG_CODES.put("adas", new String[]{".ads"});
        LANG_CODES.put("arduino", new String[]{".ino"});
        LANG_CODES.put("asp", new String[]{".asp"});
        LANG_CODES.put("aspnet", new String[]{".aspx", ".axd", ".asx", ".asmx", 
                    ".ashx", ".svc"});
        LANG_CODES.put("bash", new String[]{".sh"});
        LANG_CODES.put("basic", new String[]{".bas", ".mod"});
        LANG_CODES.put("batch", new String[]{".bat"});
        LANG_CODES.put("c", new String[]{".h", ".c", ".cc"});
        LANG_CODES.put("objc", new String[]{".m", ".mm"});
        LANG_CODES.put("cpp", new String[]{".h", ".cpp", ".cxx"});
        LANG_CODES.put("cs", new String[]{".cs"});
        LANG_CODES.put("cgi", new String[]{".cgi"});
        LANG_CODES.put("cold", new String[]{".cfm"});
        LANG_CODES.put("dm", new String[]{".d"});
        LANG_CODES.put("erl", new String[]{".yaws"});
        LANG_CODES.put("flash", new String[]{".swf"});
        LANG_CODES.put("flex", new String[]{".as", ".mxml"});
        LANG_CODES.put("html", new String[]{".htm", ".html", ".xhtml", 
            ".jhtml"});
        LANG_CODES.put("jsharp", new String[]{".jsl"});
        LANG_CODES.put("java", new String[]{".java", ".jsp", ".jspx", ".wss", 
                    ".do", ".action"});
        LANG_CODES.put("js", new String[]{".js", ".jse", ".htm", ".html", 
                    ".xhtml", ".asp", ".hta", ".aspx"});
        LANG_CODES.put("lua", new String[]{".lua"});
        LANG_CODES.put("math", new String[]{".m"});
        LANG_CODES.put("meta", new String[]{".mq4", ".mq5", ".mqt"});
        LANG_CODES.put("perl", new String[]{".pl", ".pm"});
        LANG_CODES.put("php", new String[]{".php", ".php3", ".php4", ".phtml"});
        LANG_CODES.put("python", new String[]{".py"});
        LANG_CODES.put("jupyter", new String[]{".ipynb"});
        LANG_CODES.put("r", new String[]{".r"});
        LANG_CODES.put("ruby", new String[]{".rb", ".rhtml"});
        LANG_CODES.put("rails", new String[]{".erb", ".rjs"});
        LANG_CODES.put("ssl", new String[]{".shtml"});
        LANG_CODES.put("tcl", new String[]{".tcl"});
        LANG_CODES.put("us", new String[]{".uc"});
        LANG_CODES.put("vbnet", new String[]{".vb"});
        LANG_CODES.put("vbs", new String[]{".vbs"});
        LANG_CODES.put("xml", new String[]{".xml", ".rss", ".svg"});
    }
}
