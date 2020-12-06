package datamodel.buildingblocks;

import java.util.List;

/**
 * @class LineBlock
 * @desc: The list of lines read from the file
 * @param lines -> The List of lines read on Document
 * @param prefix -> A string with which out text starts with
 */
public class LineBlock {
    private List<String> lines;
    private StyleEnum style;
    private FormatEnum format;
    private int words;

    private int getNumWords() {
        int res = 0;
        
        for(String line : this.lines)
            res += line.strip().split(" ").length;
        
        return res;
    }

    public LineBlock(List<String> lines) {
        this.lines = lines;
        this.words = getNumWords();
    }

    public String getStatsAsString() {
        return "Lines: " + this.lines.size() + ", Words: " + getWords();
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }
    public void setFormat(FormatEnum format) {
        this.format = format;
    }

    public StyleEnum getStyle() {
        return this.style;
    }
    public FormatEnum getFormat() {
        return this.format;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public int getWords() {
        return this.words;
    }

    public boolean startsWith(String prefix) {
        return this.lines.get(0).contains(prefix);
    }

    public boolean isCapital() {
        for(String line : this.lines)
            if(!line.equals(line.toUpperCase()))
                return false;
        return true;
    }

    public String replaceFirst(String prefix, String new_prefix) {
        /* TODO -> CHECK WHERE IS THIS USED AT */
        this.lines.set(0, this.lines.get(0).replaceFirst(prefix, new_prefix));
        return this.lines.get(0);
    }
}
