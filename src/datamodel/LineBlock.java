package datamodel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import files.BothWriter;
import files.ParagraphWriter;
import files.StartWriter;
import files.WriteHandler;

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

    /**
     * @message lines_to_string
     * @brief Turns each paragraph into a single string with spaces
     * @return The newly created string
     */
    private String lines_to_string() {
        StringBuilder total = new StringBuilder();

        for(String line : this.get_lines())
            total.append(line).append(" ");

        return total.toString();
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

    /**
     * @message export_markdown
     * @brief Factory method that produces Writer objects according to style and format
     * @param write_handler -> The handler to pass to the Writer actor
     */
    public int export_markdown(WriteHandler write_handler) {
        if(this.style == StyleEnum.H1)
            return new StartWriter().write_to_file("#", this, write_handler);
        else if(this.style == StyleEnum.H2)
            return new StartWriter().write_to_file("##", this, write_handler);
        else if(this.style == StyleEnum.OMITTED)
            return 0;
        else if(this.format == FormatEnum.BOLD)
            return new BothWriter().write_to_file("**", this, write_handler);
        else if(this.format == FormatEnum.ITALICS)
            return new BothWriter().write_to_file("_", this, write_handler);
        else
            return new StartWriter().write_to_file("", this, write_handler);
    }

    /**
     * @message export_pdf
     * @brief Factory method for creating Pdf Writer objects according to style and format
     * @param pdf_document -> A document for writing to pdf files (tested in itext library)
     */
    public int export_pdf(com.itextpdf.text.Document pdf_document) {
        if(this.style == StyleEnum.H1) {
            /* We choose a specific font family combined with size and style */
            Font font = new Font(Font.FontFamily.COURIER, 32, Font.NORMAL);
            return new ParagraphWriter().write_to_file(pdf_document, this.lines_to_string(), font);
        }
        else if(this.style == StyleEnum.H2) {
            Font font = new Font(Font.FontFamily.COURIER, 20, Font.NORMAL);
            return new ParagraphWriter().write_to_file(pdf_document, this.lines_to_string(), font);
        }
        else if(this.style == StyleEnum.OMITTED) {
            return 0;
        }
        else if(this.format == FormatEnum.BOLD) {
            Font font = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
            return new ParagraphWriter().write_to_file(pdf_document, this.lines_to_string(), font);
        }
        else if(this.format == FormatEnum.ITALICS) {
            Font font = new Font(Font.FontFamily.COURIER, 12, Font.ITALIC);
            return new ParagraphWriter().write_to_file(pdf_document, this.lines_to_string(), font);
        }
        else {
            Font font = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
            return new ParagraphWriter().write_to_file(pdf_document, this.lines_to_string(), font);
        }
    }
}
