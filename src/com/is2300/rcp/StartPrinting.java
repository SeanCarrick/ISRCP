/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.is2300.rcp;

import com.is2300.cmdlineparser.CmdLineParser;
import com.is2300.rcp.printer.Printer;
import java.io.File;

/**
 *
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 */
public class StartPrinting {
    public static final int MAJOR = 0;
    public static final int MINOR = 1;
    public static final int BUILD = 1;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Use CmdLineParser library to parse the arguments.
        CmdLineParser parser = new CmdLineParser(args);
        
        if ( parser.isSwitchPresent("-h") 
                || parser.isSwitchPresent("--help") ) {
            showHelp();
        } else if ( parser.isSwitchPresent("-v") 
                || parser.isSwitchPresent("--version") ) {
            showVersion();
        } else if ( parser.isSwitchPresent("-f") 
                || parser.isSwitchPresent("--folder") ) {
//            Printer.print(new File(parser.getSwitchValue(parser.getArgument(0))));
            File file = new File(parser.getSwitchValue(parser.getArgument(0)));
            System.out.println(file.getAbsolutePath());
        }
    }
    
    static void showHelp() {
        System.out.println("Recursive Code Printer (ISRCP)");
        System.out.println("-".repeat(30));
        System.out.println("Copyright (C) 2019 Integrity Solutions");
        System.out.println("\tVersion " + MAJOR + "." + MINOR + "." + BUILD);
        System.out.println("\n");
        System.out.println("-".repeat("USAGE".length()));
        System.out.println("USAGE");
        System.out.println("-".repeat("USAGE".length()));
        System.out.println();
        System.out.println("\t-h | --help\t\tShow this help and exit");
        System.out.println("\t-v | --version\t\tShow program version and exit");
        System.out.println("   -f path | --folder path\tThe folder to process");
        System.out.println("\n");
        System.out.println("-".repeat("EXAMPLE".length()));
        System.out.println("EXAMPLE");
        System.out.println("-".repeat("EXAMPLE".length()));
        System.out.println("    On Linux/Mac:");
        System.out.println("\t/path/to/java -jar /path/to/ISRCP.jar -f /path/to/"
                + "project/src");
        System.out.println("\t\t~ OR ~");
        System.out.println("\t/path/to/java -jar /path/to/ISRCP.jar --folder /path"
                + "/to/project/src");
        System.out.println("\n    On Windows:");
        System.out.println("\t\"C:\\Program Files\\path\\to\\java\" -jar C:\\"
                + "path\\to\\ISRCP.jar -f C:\\path\\to\\project\\src");
        System.out.println("\t\t~ OR ~");
        System.out.println("\t\"C:\\Program Files\\path\\to\\java\" -jar C:\\"
                + "path\\to\\ISRCP.jar --folder C:\\path\\to\\project\\src");
        System.out.println("\nIf any folder in the path contains space(s), you "
                + "must enclose the path in quotation marks, i.e.:\n\n");
        System.out.println("/path/to/java -jar \"/path/to/Your Folder/src\"");
        System.out.println("\n**NOTE**\n");
        System.out.println("The examples provided all end with a folder called "
                + "\"src\", but this is not a requirement.");
        System.out.println("\n\n");
        
        exit(0);
    }
    
    public static void exit(int status) {
        System.out.println("Thank you for using Integrity Solutions' Recursive "
                + "Code Printer utility...\n");
        System.exit(status);
    }
    
    public static void showVersion() {
        System.out.println("Recursive Code Printer (ISRCP)");
        System.out.println("-".repeat(30));
        System.out.println("Copyright (C) 2019 Integrity Solutions");
        System.out.println("\tVersion " + MAJOR + "." + MINOR + "." + BUILD);
        System.out.println("\n");
        
        exit(0);
    }
    
}
