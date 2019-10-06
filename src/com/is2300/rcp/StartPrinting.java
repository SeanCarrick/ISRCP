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
package com.is2300.rcp;

import com.is2300.cmdlineparser.CmdLineParser;
import com.is2300.rcp.desktop.RcpFrame;
import com.is2300.rcp.enums.SysExits;
import com.is2300.rcp.printer.FileFilterFactory;
import com.is2300.rcp.printer.PrintSetup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * ***Integrity Solutions*** *Recursive Code Printer Utility* is designed from 
 * the ground up to be a useful tool for developers who desire to generate a
 * hard-copy of their project's source code for copyright purposes. Integrity
 * Solutions deemed it necessary to create this utility to provide a less painful
 * way to generate that hard-copy.
 * 
 * In our experience, there are not many, if any, Integrated Development
 * Environments (IDEs) that provide a method in which the source files for an
 * entire project tree can be printed as a single print job. This has caused 
 * many developers to forego the important task of copyrighting their source
 * code and, thereby, protecting their intellectual property rights. This seemed
 * to be a problem that needed a quality solution.
 * 
 * This is where our *Recursive Code Printer Utility* comes into play. This is
 * not, by any stretch of the imagination, a pretty utility to use, but it is
 * very effective and efficient. One must drop to the command line to use this
 * utility, but it works well and it works quickly. Since it is an Open Source
 * project, we don't mind showing you how the heart of this utility works:
 * 
 * ```java
 *     public static boolean print(String pathToFiles, FileFilter filter) {
 *         if ( pathToFiles == null ) {
 *             throw new NullPointerException("The path cannot be null.");
 *         }
 *       
 *         boolean printed = false;
 *         File file = new File(pathToFiles);
 *       
 *         if ( file.isDirectory() ) {
 *             for ( File f : file.listFiles(filter) ) {
 *                 if ( f.isDirectory() ) {
 *                     print(f.getAbsolutePath(), filter);
 *                 } else {
 *                     printed = printFile(f);
 *                     printCount++;
 *                 }
 *             }
 *         } else { 
 *             printed = printFile(file);
 *         }
 *       
 *         return printed;
 *     }
 * ```
 * 
 * The above code is the heart of the ***ISRCP***. It provides a recursive way
 * of generating all source code files in the given path to hard copy. The main
 * method in this class will tell you exactly how many files were processed and
 * exactly how many seconds it took to process them. Run this utility against
 * any of your source trees and you will be impressed by the functionality and
 * the performance. When we ran this utility during testing against a small 
 * project of 25 folder and 500 source files, the program exited in less than
 * 25 seconds, having processed every file in the tree and sent it to the 
 * printer. Unfortunately, printers being what they are, it took approximately
 * 3 hours for all of the printouts to complete, but we have no control over 
 * that aspect of the process. At least we didn't have to sit there and print
 * each and every one of those 500 files individually! That would have probably
 * taken one person about 2 or 3 days to get all of the files printed. And, 
 * worse, they may have missed one or two because of their eyes going crossed
 * due to clicking four or five times just to generate 1 printout. When you 
 * multiply that by the number of files, that's a minimum of 2,000 clicks just
 * to create printouts of each file. The hand cramps would be awful!
 * 
 * All of this is just a way to say that we hope and pray that you find this
 * utility useful with printing out your development projects!
 * 
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 *
 * @version 0.23.384
 * @since 0.1.0
 */
public class StartPrinting {
    public static final int MAJOR;
    public static final int MINOR;
    public static final int BUILD;
    public static final String WARRANTY;
    public static final Properties PROPS;
    
    static {
        BUILD  = -1 * ((int)System.currentTimeMillis() / 5000000);
        
        LocalDate ld = LocalDate.now();
        int minor = ld.getMonthValue() + ld.getYear() + ld.getDayOfMonth()
                + ld.getDayOfYear();
        minor /= 100;
        
        if ( minor > 99 ) {
            minor = 1;
        }
        
        MINOR = minor;
        
        MAJOR = BUILD / (MINOR * 100);
        
        StringBuilder sb = new StringBuilder();
        sb.append("Copyright (C) 2019 Integrity Solutions\n\n");
        sb.append("This program is free software: you can redistribute it and/or modify ");
        sb.append("it under the terms of the GNU General Public License as published by ");
        sb.append("the Free Software Foundation, either version 3 of the License, or ");
        sb.append("(at your option) any later version.");
        sb.append("\n");
        sb.append("This program is distributed in the hope that it will be useful, ");
        sb.append("but WITHOUT ANY WARRANTY; without even the implied warranty of ");
        sb.append("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the ");
        sb.append("GNU General Public License for more details.");
        sb.append("\n");
        sb.append("You should have received a copy of the GNU General Public License ");
        sb.append("along with this program.  If not, see <http://www.gnu.org/licenses/>.");
        
        WARRANTY = sb.toString();
                
        PROPS = new Properties();
        
        File propsFile = new File(System.getProperty("user.home") 
                + System.getProperty("file.separator") + ".isrcp.conf");
        try (InputStream is = new FileInputStream(propsFile)) {
            PROPS.load(is);
        } catch (FileNotFoundException ex) {
            // First time that the utility has been run, so we will just leave
            //+ alone. The properties file will be saved when we exit.
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //<editor-fold defaultstate="collapsed" desc="Old Command Line Code">
        // Use CmdLineParser library to parse the arguments.
//        CmdLineParser parser = new CmdLineParser(args);
//        
//        Map<String, List<String>> testMap = parser.getSwitches();
//        
//        if ( parser.isSwitchPresent("-c") 
//                || parser.isSwitchPresent("--conditions") ) {
//            
//            showConditions();
//            
//        } else if ( (parser.isSwitchPresent("-d") 
//                || parser.isSwitchPresent("--dir")) 
//                && (parser.isSwitchPresent("-l") 
//                || parser.isSwitchPresent("--filter")) ) {
//            long start = System.currentTimeMillis();
//            
//            String[] langCodes = new String[]{"-l", "--lang"};
//            String[] dirKeys = new String[]{"-d", "--dir"};
//            
//            String langCode = parser.getKeyForValue(langCodes);
//            List<String> langs = parser.getValueList(langCode);
//            String lang = langs.get(0);
//            if ( lang.isBlank() ) {
//                throw new IllegalArgumentException("No switch provided for the "
//                        + "language to print.");
//            }
//            String dirKey = parser.getKeyForValue(dirKeys);
//            List<String> dirs = parser.getValueList(dirKey);
//            String dir = dirs.get(0);
//            PrintSetup.print(dir, FileFilterFactory.createFileFilter(lang));
//            
//            long finish = System.currentTimeMillis();
//            long totalTime = (finish - start) / 1000;
//            System.out.println("Number of files printed: " + 
//                    PrintSetup.getPrintCount() + " in " + totalTime + " seconds");
//            
//        } else if ( parser.isSwitchPresent("-h") 
//                || parser.isSwitchPresent("--help") ) {
//            
//            showHelp(SysExits.EX_OK);
//            
//        } else if ( parser.isSwitchPresent("-v") 
//                || parser.isSwitchPresent("--version") ) {
//            
//            showVersion();
//            
//        } else if ( parser.isSwitchPresent("-w") 
//                || parser.isSwitchPresent("--warranty") ) {
//            
//            showWarranty();
//            
//        } else if ( parser.isSwitchPresent("-g") 
//                || parser.isSwitchPresent("--gui") ) {
//            
//            RcpFrame.main(args);
//            
//        } else {
//            showHelp(SysExits.EX_USAGE);
//        }
//        </editor-fold>
//        
//        /*
//         * We are just going to start the GUI for now. We'll figure out the
//         * command-line stuff later.
//         */
        RcpFrame.main(args);
    }
    
    
    static void showHelp(SysExits exitStatus) {
        System.out.println("Recursive Code Printer (ISRCP)");
        System.out.println("-".repeat(30));
        System.out.println("Copyright (C) 2019 Integrity Solutions");
        System.out.println("\tVersion " + MAJOR + "." + MINOR + "." + BUILD);
        System.out.println("\nLICENSE:\nThis program comes with ABOSLUTELY NO "
                + "WARRANTY; for details type `-w'");
        System.out.println(" or `-warranty'.");
        System.out.println("This is free software, and you are welcome to "
                + "reditribute it");
        System.out.println("under certain conditions; type `-c` or `-conditions'"
                + " for details.");
        System.out.println("\n");
        System.out.println("-".repeat("USAGE".length()));
        System.out.println("USAGE");
        System.out.println("-".repeat("USAGE".length()));
        System.out.println();
//        System.out.println("\t-c | --conditions\tShow conditions details");
        System.out.println("   -d path | --dir path\t\tThe folder to process");
        System.out.println("   -l lang | --lang lang\tThe language code for the"
                + " files to process");
        System.out.println("\t-h | --help\t\tShow this help and exit");
        System.out.println("\t-v | --version\t\tShow program version and exit");
        System.out.println("\t-w | --waranty\t\tShow warranty details");
        System.out.println("\t-x | --extension\tExtension of the files to print");
        System.out.println("\n");
        System.out.println("-".repeat("EXAMPLE".length()));
        System.out.println("EXAMPLE");
        System.out.println("-".repeat("EXAMPLE".length()));
        System.out.println("    On Linux/Mac:");
        System.out.println("\t/path/to/java -jar /path/to/ISRCP.jar -d /path/to/"
                + "project/src -f csharp");
        System.out.println("\t\t~ OR ~");
        System.out.println("\t/path/to/java -jar /path/to/ISRCP.jar --dir /path"
                + "/to/project/src --filter java");
        System.out.println("\n    On Windows:");
        System.out.println("\t\"C:\\Program Files\\path\\to\\java\" -jar C:\\"
                + "path\\to\\ISRCP.jar -d C:\\path\\to\\project\\src -f "
                + "vbnet");
        System.out.println("\t\t~ OR ~");
        System.out.println("\t\"C:\\Program Files\\path\\to\\java\" -jar C:\\"
                + "path\\to\\ISRCP.jar --dir C:\\path\\to\\project\\src "
                + "--filter bat");
        System.out.println("\nIf any folder in the path contains space(s), you "
                + "must enclose the path in quotation marks, i.e.:\n\n");
        System.out.println("/path/to/java -jar /path/to/ISRCP.jar -d \"/path/to"
                + "/Your Folder/src\" --filter jupiter");
        System.out.println("\n**NOTE**\n");
        System.out.println("The examples provided all end with a folder called "
                + "\"src\", but this is not a requirement.");
        System.out.println("\n");
        System.out.println("-".repeat("SUPPORTED LANGUAGES".length()));
        System.out.println("SUPPORTED LANGUAGES");
        System.out.println("-".repeat("SUPPORTED LANGUAGES".length()));
        System.out.println("The following languages are supported by ISRCP. "
                + "These are the values to pass\nto ISRCP with the -f or --"
                + "filter switch in parenthesis.\n\nFor example:\n\n\tjava -jar "
                + "ISRCP.jar --dir /path/to/src --filter csharp\n\nThis will "
                + "print out all C# source files in the provided path\n");
        System.out.println(getLangs());
        System.out.println("\n\n");
        
        exit(exitStatus);
    }
    
    private static String getLangs() {
        StringBuilder sb = new StringBuilder();
        
        /*
                {"Ada Body", "Ada Spec", "Arduino / Nano Sketch",
                "ASP", "ASP.Net", "Bash Scripting", "BASIC", "Batch Files",
                "C", "Objective C", "C++", "C#", "CGI", "Cold Fusion", 
                "Digital Mars", "Erlang", "Flash", "Flash/Flex Action",
                "HTML", "J#", "Java", "JavaScript", "Lua", "Mathematica",
                "MetaTrader", "Perl", "PHP", "Python", "Python Notebook",
                "R", "Ruby", "Ruby on Rails", "SSL", "TCL", "Unreal Script",
                "VB.net", "Visual Basic", "XML"}
        */
        sb.append("Ada Body (adab)\t\t\tAda Specification (adas)\tArduino / Nano Sketch (ardns)");
        sb.append("\nASP (asp)\t\t\tASP.Net (aspnet)\t\tBash Scripting (bash)\t\tBASIC (basic)\n");
        sb.append("Batch Files (bat)\t\tC (c)\t\t\t\tC++ (cpp)\t\t\tC# (csharp)\n");
        sb.append("Objective C (objc)\t\tCGI (cgi)\t\t\tCold Fusion (cold)\n");
        sb.append("Digital Mars (dm)\t\tErlang (erl)\t\t\tFlash (flash)\n");
        sb.append("Flash/Flex Action (flex)\tHTML (html)\t\t\tJ# (jsharp)\n");
        sb.append("Java (java)\t\t\tJavaScript (js)\t\t\tLua (lua)\n");
        sb.append("Mathematica (math)\t\tMetaTrader (meta)\t\tPerl (perl)\n");
        sb.append("PHP (php)\t\t\tPython (python)\t\t\tPython Notebook (jupiter)\tR (r)\n");
        sb.append("Ruby (ruby)\t\t\tRuby on Rails (rails)\t\tSSL (ssl)\t\t\tTCL (tcl)\n");
        sb.append("Unreal Script (us)\t\tVB.net (vbnet)\t\t\tVisual Basic (vb)\t\tXML (xml)\n");
        
        return sb.toString();
    }
    
    public static void showVersion() {
        System.out.println("Recursive Code Printer (ISRCP)");
        System.out.println("-".repeat(30));
        System.out.println("Copyright (C) 2019 Integrity Solutions");
        System.out.println("\tVersion " + MAJOR + "." + MINOR + "." + BUILD);
        System.out.println("\n");
        
        exit(SysExits.EX_OK);
    }
    
    public static void showWarranty() {
        System.out.println("15. Disclaimer of Warranty.");
        System.out.println();
        System.out.println("THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY");
        System.out.println("APPLICABLE LAW.  EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT");
        System.out.println("HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY");
        System.out.println("OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO,");
        System.out.println("THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR");
        System.out.println("PURPOSE.  THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM");
        System.out.println("IS WITH YOU.  SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF");
        System.out.println("ALL NECESSARY SERVICING, REPAIR OR CORRECTION.");
        System.out.println();
        System.out.println("16. Limitation of Liability.");
        System.out.println();
        System.out.println("IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING");
        System.out.println("WILL ANY COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MODIFIES AND/OR CONVEYS");
        System.out.println("THE PROGRAM AS PERMITTED ABOVE, BE LIABLE TO YOU FOR DAMAGES, INCLUDING ANY");
        System.out.println("GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE");
        System.out.println("USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED TO LOSS OF");
        System.out.println("DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD");
        System.out.println("PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER PROGRAMS),");
        System.out.println("EVEN IF SUCH HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF");
        System.out.println("SUCH DAMAGES.");
        System.out.println();
        System.out.println("Interpretation of Sections 15 and 16.");
        System.out.println();
        System.out.println("If the disclaimer of warranty and limitation of liability provided");
        System.out.println("above cannot be given local legal effect according to their terms,");
        System.out.println("reviewing courts shall apply local law that most closely approximates");
        System.out.println("an absolute waiver of all civil liability in connection with the");
        System.out.println("Program, unless a warranty or assumption of liability accompanies a");
        System.out.println("copy of the Program in return for a fee.\n");
        
        exit(SysExits.EX_OK);
    }
    
    public static void showConditions() {
        
    }
    
    public static void exit(SysExits status) {
        System.out.println("Thank you for using Integrity Solutions' Recursive "
                + "Code Printer utility...\n");
        System.exit(status.toInt());
    }

}
