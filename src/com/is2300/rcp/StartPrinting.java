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
import com.is2300.rcp.enums.SysExits;
import com.is2300.rcp.printer.FileFilterFactory;
import com.is2300.rcp.printer.Printer;
import java.io.FileFilter;
import java.time.LocalDate;

/**
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
    private static final String[] langs;
    
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

        langs = new String[]{"Ada Body", "Ada Spec", "Arduino / Nano Sketch",
                "ASP", "ASP.Net", "Bash Scripting", "BASIC", "Batch Files",
                "C", "Objective C", "C++", "C#", "CGI", "Cold Fusion", 
                "Digital Mars", "Erlang", "Flash", "Flash/Flex Action",
                "HTML", "J#", "Java", "JavaScript", "Lua", "Mathematica",
                "MetaTrader", "Perl", "PHP", "Python", "Python Notebook",
                "R", "Ruby", "Ruby on Rails", "SSL", "TCL", "Unreal Script",
                "VB.net", "Visual Basic", "XML"};
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Use CmdLineParser library to parse the arguments.
        CmdLineParser parser = new CmdLineParser(args);
        
        if ( parser.isSwitchPresent("-c") 
                || parser.isSwitchPresent("--conditions") ) {
            
            showConditions();
            
        } else if ( (parser.isSwitchPresent("-d") 
                || parser.isSwitchPresent("--dir")) 
                && (parser.isSwitchPresent("-f") 
                || parser.isSwitchPresent("--filter")) ) {
            
            FileFilter filter = FileFilterFactory.createFileFilter(
                    parser.getSwitchValue(parser.getArgument(2)));
            Printer.print(parser.getSwitchValue(parser.getArgument(0)), filter);
            
            System.out.println("Number of files printed: " + Printer.getPrintCount());
            
        } else if ( parser.isSwitchPresent("-h") 
                || parser.isSwitchPresent("--help") ) {
            
            showHelp(SysExits.EX_OK);
            
        } else if ( parser.isSwitchPresent("-v") 
                || parser.isSwitchPresent("--version") ) {
            
            showVersion();
            
        } else if ( parser.isSwitchPresent("-w") 
                || parser.isSwitchPresent("--warranty") ) {
            
            showWarranty();
            
        } else {
            showHelp(SysExits.EX_USAGE);
        }
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
        System.out.println("   -f lang | --filter lang\tThe language code for the"
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
