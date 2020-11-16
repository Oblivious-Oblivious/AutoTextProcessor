package exporters;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;
import datamodel.files.FileHandler;
import datamodel.files.WriteHandler;

/* TODO TURN STYLE AND FORMAT NOTATION INTO A SINGLE CHECK */
public class MarkdownExporter {
    private Document document;
    private String outputFileName;
    private FileHandler handler;

    private FileHandler createOutputFileHandler() {
        return new WriteHandler(this.outputFileName);
    }

    private String getStyleNotation(LineBlock l) {
        if(l.getStyle() == StyleEnum.H1)
            return "#";
        if(l.getStyle() == StyleEnum.H2)
            return "##";
        if(l.getStyle() == StyleEnum.OMITTED)
            return null;
        
        /* NORMAL */
        return "";
    }

    private String getFormatNotation(LineBlock l) {
        if(l.getFormat() == FormatEnum.BOLD)
            return "**";
        if(l.getFormat() == FormatEnum.ITALICS)
            return "_";
        
        /* REGULAR */
        return "";
    }

    private void addParagraph(LineBlock l) {
        StringBuilder parBlock = new StringBuilder();

        for(String block : l.getLines()) {
            // System.out.println("APP: `" + block + "`");
            parBlock.append(block + " ");
            // parBlock.append(" ");
        }

        String styleNotation = getStyleNotation(l);
        String formatNotation = getFormatNotation(l);

        /* TODO -> UGLY CODE */
        if(styleNotation != null) { /* OMMITED */
            if(styleNotation.equals("")) /* NORMAL */ {
                handler.appendLine(formatNotation + parBlock + formatNotation);
                System.out.println("A: `" + formatNotation + parBlock + formatNotation + "`");
            }
            else {
                handler.appendLine(styleNotation + parBlock);
                System.out.println("A: `" + styleNotation + parBlock + "`");
            }
            handler.appendLine("");
        }
    }

    private int exportParagraphs(FileHandler handler) {
        int par = 0;

        for(LineBlock l : document.getLineblocks()) {
            addParagraph(l);
            par++;
        }

        /* TODO -> IF BELOW IS REMOVED FILE HANDLING CRASHES */
        /* TODO -> JAVA GARBAGE COLLECTOR DOESNT WORK WTF ?!? */
        this.handler.closeFD();

        return par;
    }

    public MarkdownExporter(Document document, String outputFileName) {
        this.document = document;
        this.outputFileName = outputFileName;
        this.handler = createOutputFileHandler();
    }

    public int export() {
        return exportParagraphs(handler);
    }
}
