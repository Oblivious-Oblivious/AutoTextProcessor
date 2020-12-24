package datamodel;

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
     * words -> The number of words in the specific line_block
     * style -> The current style of the line_block
     * format -> The current format of the line_block
     */
    private final List<String> lines;
    private final int words;
    private StyleEnum style;
    private FormatEnum format;

    private int calculate_num_words() {
        return lines()
                .stream()
                .mapToInt(line -> line
                        .strip()
                        .split("\\s+")
                        .length)
                .sum();
    }

    /**
     * @message lines_to_string
     * @brief Turns each paragraph into a single string with spaces
     * @return The newly created string
     */
    private String lines_to_string() {
        return String
                .join(" ", lines());
    }

    /**
     * @Constructor
     * @param lines -> The lines list to set for the line_block paragraph
     */
    public LineBlock(List<String> lines) {
        this.lines = lines;
        this.words = calculate_num_words();
    }

    /**
     * @message style_eq
     * @brief Setter for style
     * @param style -> New value to set
     */
    public void style_eq(StyleEnum style) {
        this.style = style;
    }
    /**
     * @message style
     * @brief Getter for style
     * @return style
     */
    public StyleEnum style() {
        return this.style;
    }

    /**
     * @message format_eq
     * @brief Setter for format
     * @param format -> New value to set
     */
    public void format_eq(FormatEnum format) {
        this.format = format;
    }
    /**
     * @message format
     * @brief Getter for format
     * @return format
     */
    public FormatEnum format() {
        return this.format;
    }

    /**
     * @message lines
     * @brief Getter for lines
     * @return lines
     */
    public List<String> lines() {
        return this.lines;
    }
    /**
     * @message words
     * @brief Getter for words
     * @return words
     */
    public int words() {
        return this.words;
    }

    /**
     * @message get_stats_as_string
     * @brief Produce a string line detailing the number of lines and words
     * @return -> The formatted string
     */
    public String get_stats_as_string() {
        return "Lines: " + lines().size() + ", Words: " + words();
    }

    /**
     * @message is_all_caps
     * @brief Checks whether a paragraph is fully capitalized or not
     * @return -> A boolean that checks the afro-mentioned
     */
    public boolean is_all_caps() {
        /* We capitalize each line and check for inequality */
        /* In case the find different strings we return false */
        for(String line : lines())
            if(!line.equals(line.toUpperCase()))
                return false;
        return true;
    }

    /**
     * @message is_in_position
     * @brief Checks whether the index of `this` is contained in the list of positions
     * @param line_blocks -> The list that encapsulates `this`
     * @param positions -> The positions to check existence
     * @return -> A boolean that checks the afro-mentioned
     */
    public boolean is_in_position(List<LineBlock> line_blocks, List<Integer> positions) {
        return positions.contains(line_blocks.indexOf(this));
    }

    /**
     * @message starts_with
     * @brief Checks whether the paragraph has an initial of a passed prefix
     * @param prefix -> The substring to validate as an initial
     * @return -> A boolean that checks the afro-mentioned
     */
    public boolean starts_with(String prefix) {
        /* In case we try validate and empty paragraph */
        if(lines().size() == 0)
            return false;

        /* In case the prefix is larger than the paragraph */
        if(prefix.length() > lines().get(0).length())
            return false;

        /* Check the first line by character */
        for(int i = 0; i < prefix.length(); i++)
            if(prefix.charAt(i) != lines().get(0).charAt(i))
                return false;

        return true;
    }

    /**
	 * @message undefined
	 * @brief Destination for the delegation when
	 * 		  trying to validate an undefined rule
	 * @return false
	 */
	public boolean undefined() {
		return false; /* ALWAYS RETURNS FALSE */
	}

    /* TODO -> CHECK WHERE IS THIS USED AT */
//    public String replace_first(String prefix, String new_prefix) {
//        lines().set(0, lines().get(0).replaceFirst(prefix, new_prefix));
//        return lines().get(0);
//    }

    /* TODO -> MAKE THEM LOOK MORE ALIKE USING DEPENDENCY INJECTION */
    /**
     * @message export_markdown
     * @brief Factory method that produces Writer objects according to style and format
     * @param write_handler -> The handler to pass to the Writer actor
     */
    public int export_markdown(WriteHandler write_handler) {
        if(style() == StyleEnum.H1)
            return new StartWriter().write_to_file("#", this, write_handler);
        else if(style() == StyleEnum.H2)
            return new StartWriter().write_to_file("##", this, write_handler);
        else if(style() == StyleEnum.OMITTED)
            return 0;
        else if(format() == FormatEnum.BOLD)
            return new BothWriter().write_to_file("**", this, write_handler);
        else if(format() == FormatEnum.ITALICS)
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
        if(style() == StyleEnum.H1) {
            /* We choose a specific font family combined with size and style */
            Font font = new Font(Font.FontFamily.COURIER, 32, Font.NORMAL);
            return new ParagraphWriter().write_to_file(pdf_document, this.lines_to_string(), font);
        }
        else if(style() == StyleEnum.H2) {
            Font font = new Font(Font.FontFamily.COURIER, 20, Font.NORMAL);
            return new ParagraphWriter().write_to_file(pdf_document, this.lines_to_string(), font);
        }
        else if(style() == StyleEnum.OMITTED)
            return 0;
        else if(format() == FormatEnum.BOLD) {
            Font font = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
            return new ParagraphWriter().write_to_file(pdf_document, this.lines_to_string(), font);
        }
        else if(format() == FormatEnum.ITALICS) {
            Font font = new Font(Font.FontFamily.COURIER, 12, Font.ITALIC);
            return new ParagraphWriter().write_to_file(pdf_document, this.lines_to_string(), font);
        }
        else {
            Font font = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
            return new ParagraphWriter().write_to_file(pdf_document, this.lines_to_string(), font);
        }
    }
}
