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

package com.is2300.cmdlineparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.apache.commons.lang3.ArrayUtils;

/**
 * `CmdLineParser` is a library class for parsing command line arguments in a
 * simple and elegant manner. This parser breaks down the difference between
 * switches, options and targets. These terms are defined, for this library, as
 * follows:
 * 
 * <dl>
 *      <dt>switch</dt>
 *      <dd>A `switch` is any option to the program that is set on the command
 *          line and is typically preceded with a single dash (-) for 
 *          single-letter options, or a double dash (--) for whole word options. 
 *          If the whole word option is used, there can be multiple words in the 
 *          switch name, typically hyphen separated, such as `--multi-word-option`
 *      </dd>
 *      <dt>option</dt>
 *      <dd>An `option` is typically a value that a switch denotes, such as
 *          `--port 8080`. In this example, the switch is `--port` and the 
 *          option is `8080`. For `CmdLineParser` library, you may have multiple
 *          options to a switch. Using the previous example of setting a port,
 *          you could specify alternate ports in the event that the first port
 *          is already in use. You would accomplish this by doing:
 * 
 *          `--port 8080 8585 9090`
 *          
 *          In this example, if port 8080 is already in use, your application
 *          could move on to the first alternate port, 8585, and the final 
 *          alternate port, 9090, each time your program discovers the provided
 *          port is already in use.
 * 
 *          We are able to have this feature available because, internally, we
 *          use the switch as the key and all options up until the next switch
 *          as the values for the key. This allows you to allow multiple options
 *          for any switches your program requires.</dd>
 *      <dt>target</dt>
 *      <dd>A `target` is any option to your program that has no associated
 *          switch. If your program requires any information from the command
 *          line that you don't want to assign switches to, this information
 *          needs to be provided before any switches. 
 * 
 *          However, another alternative would be to just have a generic switch, 
 *          such as `-t`, with no whole word option as the switch for your 
 *          targets. Obviously, if your program already uses the switch 
 *          exemplified above for a specific feature, you could use any switch 
 *          that doesn't already have an association for your program. 
 * 
 *          Furthermore, you could even just assign `-` as your target switch, 
 *          with no letter associated with it. If you take this option, make sure 
 *          your help documentation specifies that the user make sure they place 
 *          a space between the dash (-) and the target(s) they are passing into 
 *          your program. If the user uses the dash and does not put a space 
 *          after it, and before their first target, then `-target_name` will be 
 *          interpreted as a switch and all following targets will be assigned 
 *          to this key.</dd>
 * </dl>
 * 
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 * 
 * @version 0.3.152
 * @since 0.1.0
 */
public class CmdLineParser {
    //<editor-fold defaultstate="collapsed" desc="Public Static Constants">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Member Fields">
    private String[] args;                          // Default to null
    private Map<String, List<String>> switches;
    private List<String> targets;                   // Default to null
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Intstance Initializer">
    {
        args = null;
        switches = new HashMap<>();
        targets = new ArrayList<>();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor(s)">
    /**
     * Creates an instance of the `CmdLineParser` class to parse command line
     * arguments into a form that is easier to use throughout your program.
     * 
     * @param args The command line argument passed into the implementing 
     *             program.
     */
    public CmdLineParser(String[] args) {
        this.switches = parse(args);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    
    public Map<String, List<String>> getSwitches() {
        return this.switches;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Instance Methods">
    /**
     * Parse the arguments into a `java.util.HashMap` using the switch as the 
     * key and all arguments up to the next hyphen (-) as the options to the
     * switch. 
     * 
     * If the user needs to supply options that are required, but have no switch
     * associated with them, those options will need to be passed first on the
     * command line. This is information that needs to be included in the 
     * program documentation.
     * 
     * @param args An array of `java.lang.String` values which include, both, 
     *             switches and options, as well as options with no associated
     *             switch.
     * @return Map<String, List<String>> of the arguments in a more useful format.
     *             Will return an empty map if no arguments provided.
     */
    private Map<String, List<String>> parse(String[] args) {
        if ( args != null && args.length > 0 ) {
            this.args = args;
            
            // Just in case our map has data in it, which it shouldn't.
            switches.clear();
            
            /* We are going to perform a couple of loops. The first one is to
             * get the switches and their options. The second one may or may not
             * actually be run. If the developer used a dummy switch to mark the
             * unswitched targets for his/her program, then there will be no
             * elements left in the list we are going to create from our args
             * variable, so the second loop will not run.
             */
            List<String> arguments = new ArrayList<>(Arrays.asList(args));
            List<String> argList = new ArrayList<>();
            String key = null;
            boolean keyFound = false;
            
            /*
                for each arg in arguments
                    if key equals null
                        if arg starts with "-"
                            assign arg to key
                        otherwise if arg does not start with "-"
                            add arg to list
                        otherwise
                            set key to null
                            continue loop
                
            */
            for ( String arg : arguments ) {
                if ( arg.startsWith("-") && key == null ) {
                    key = arg;
                } else if ( !arg.startsWith("-") ) {
                    argList.add(arg);
                } else {
                    switches.put(key, argList);
                    key = arg;
                    argList.clear();
                }
            }
        }
        
        return this.switches;
    }
    //</editor-fold>

}
