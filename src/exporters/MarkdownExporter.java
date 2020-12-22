package exporters;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;
import datamodel.files.FileHandler;
import datamodel.files.WriteHandler;

public class MarkdownExporter implements IExporter {
    private final Document document;
    private final String outputFileName;
    private final FileHandler handler;

    private FileHandler create_output_file_handler() {
        return new WriteHandler(this.outputFileName);
    }

    private String get_styleNotation(LineBlock l) {
        if(l.get_style() == StyleEnum.H1)
            return "#";
        if(l.get_style() == StyleEnum.H2)
            return "##";
        if(l.get_style() == StyleEnum.OMITTED)
            return null;
        
        /* NORMAL */
        return "";
    }

    private String get_formatNotation(LineBlock l) {
        if(l.get_format() == FormatEnum.BOLD)
            return "**";
        if(l.get_format() == FormatEnum.ITALICS)
            return "_";
        
        /* REGULAR */
        return "";
    }

    private int addParagraph(LineBlock l) {
        StringBuilder parBlock = new StringBuilder();

        for(String block : l.get_lines())
            parBlock.append(block)
                    .append(" ");

        String styleNotation = get_styleNotation(l);
        String formatNotation = get_formatNotation(l);

        if(styleNotation != null) { /* OMITTED */
            if(styleNotation.equals("")) /* NORMAL */
                handler.append_line(formatNotation + parBlock + formatNotation);
            else
                handler.append_line(styleNotation + parBlock);
            handler.append_line("");
            return 1;
        }
        return 0;
    }

    public MarkdownExporter(Document document, String outputFileName) {
        this.document = document;
        this.outputFileName = outputFileName;
        this.handler = create_output_file_handler();
    }

    public int export() {
        int par = 0;

        for(LineBlock l : this.document.get_line_blocks()) {
            par += addParagraph(l);
        }

        this.handler.close_fd();

        return par;
    }
}
