package intersource;

/**
 * This class implements the method-parseLine. parseLine gets @line and returns string which is splitted by standrads
 * methods of string. Replaces superfluous symbols in string.
 */
public class LineParser {

    public String[] parseLine(String  line){
        return line
                .replace("[«»:.,?-]", "")
                .replace("[^0-9,]", "")
                .toLowerCase()
                .split(" ");
    }
}
