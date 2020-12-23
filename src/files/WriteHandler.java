package files;

import datamodel.LineBlock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @class WriteHandler
 * @brief File handler for making file write_line operations more intuitive
 */
public class WriteHandler {
    /**
     * file_writer -> The FileWriter object used in the methods
     * filepath -> The new file to write_line to
     */
    private FileWriter file_writer;
    private String filepath;

    /**
     * @message open
     * @brief Open the file in write_line node
     * @param filepath -> The file to open
     * @return -> a boolean signaling if the opening was successful
     */
    public boolean open(String filepath) {
        this.filepath = filepath;

        /* Delete any previous instance of the file */
        File f = new File(filepath);
        f.delete();

        try {
            this.file_writer = new FileWriter(filepath, true);
            return true;
        }
        catch(IOException e) {
            System.out.println("There was an error with creating the output file. | `" + this.filepath + '`');
            return false;
        }
    }

    /**
     * @message write
     * @brief Writes a string in the file
     * @param str -> The string to write
     */
    public void write(String str) {
        try {
            this.file_writer.write(str);
        }
        catch(IOException e) {
            System.out.println("There was an error with writing data to the output file. | `" + this.filepath + '`');
        }
    }

    /**
     * @message write_line
     * @brief Writes a line in the file
     * @param line -> The line to write
     */
    public void write_line(String line) {
        try {
            this.file_writer.write(line);
            this.file_writer.write("\n");
        }
        catch(IOException e) {
            System.out.println("There was an error with writing data to the output file. | `" + this.filepath + '`');
        }
    }

    /**
     * @message write_all_lines
     * @brief Grab each line from the line_block and write_line it with a space after it
     * @param block -> The line_block containing the lines
     */
    public void write_all_lines(LineBlock block) {
        for(String line : block.get_lines())
            this.write(line + " ");
    }

    /**
     * @message close
     * @brief Attempts to close the buffers to avoid memory overflows
     */
    public void close() {
        try {
            file_writer.close();
        }
        catch(IOException e) {
            System.out.println("There was an error with closing the file descriptor. | `" + this.filepath + '`');
        }
    }
}
