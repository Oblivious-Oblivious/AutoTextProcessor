package datamodel;

import java.util.List;

/**
 * @class LineBlock
 * @brief The list of lines read from the file
 */
public class LineBlock {
    /**
     * lines -> The List of lines read on Document
     * words -> The number of words in the specific lineBlock
     */
    private final List<String> lines;
    private final int words;
    private StyleEnum style;
    private FormatEnum format;

    private int get_num_words() {
        int res = 0;
        
        for(String line : this.lines)
            res += line.strip().split(" ").length;
        
        return res;
    }

    public LineBlock(List<String> lines) {
        this.lines = lines;
        this.words = get_num_words();
    }

    public String get_stats_as_string() {
        return "Lines: " + this.lines.size() + ", Words: " + get_words();
    }

    public void set_style(StyleEnum style) {
        this.style = style;
    }
    public void set_format(FormatEnum format) {
        this.format = format;
    }

    public StyleEnum get_style() {
        return this.style;
    }
    public FormatEnum get_format() {
        return this.format;
    }

    public List<String> get_lines() {
        return this.lines;
    }

    public int get_words() {
        return this.words;
    }

    public boolean starts_with(String prefix) {
        return this.lines.get(0).contains(prefix);
    }

    public boolean is_capital() {
        for(String line : this.lines)
            if(!line.equals(line.toUpperCase()))
                return false;
        return true;
    }

    public String replace_first(String prefix, String new_prefix) {
        /* TODO -> CHECK WHERE IS THIS USED AT */
        this.lines.set(0, this.lines.get(0).replaceFirst(prefix, new_prefix));
        return this.lines.get(0);
    }
}
