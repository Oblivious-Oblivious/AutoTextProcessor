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

    public LineBlock(List<String> lines) {
        this.lines = lines;
    }

    public String getStatsAsString() {
        return "Lines: " + this.lines.size() + ", Words: " + getNumWords();
    }

    public int getNumWords() {
        int res = 0;
        
        for(String line : this.lines)
            res += line.split(" ").length;
        
        return res;
    }

    /* TODO -> Implement */
    public void setStyle(StyleEnum style) {}
    public void setFormat(FormatEnum format) {}

    public List<String> getLines() {
        return this.lines;
    }

    public boolean startsWith(String prefix) {
        return this.lines.get(0).contains(prefix);
    }

    public String replaceFirst(String prefix, String new_prefix) {
        /* TODO -> CHECK IF CORRECT */
        this.lines.set(0, this.lines.get(0).replaceFirst(prefix, new_prefix));
        return this.lines.get(0);
    }
}
