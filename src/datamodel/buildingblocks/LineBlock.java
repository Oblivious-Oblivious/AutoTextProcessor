package datamodel.buildingblocks;

import java.util.List;

public class LineBlock {
    public LineBlock() {}

    public String getStatsAsString() {
        return "";
    }

    public int getNumWords() {
        return 1;
    }

    public void setStyle(StyleEnum style) {}
    public void setFormat(FormatEnum format) {}

    public List<String> getLines() {
        return null;
    }

    public boolean startsWith(String prefix) {
        return false;
    }

    public String replaceFirst(String prefix, String new_prefix) {
        return "";
    }
}
