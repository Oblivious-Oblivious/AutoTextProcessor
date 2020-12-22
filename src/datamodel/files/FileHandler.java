package datamodel.files;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;

/* TODO -> FAIL ON FILE EXISTENCE */

/**
 * @class FileHandler
 * @brief Implements the functions of reading data from and writing to a file
 */
public class FileHandler {
    /**
     * reader -> A reader file object
     * writer -> A writer file object
     * fd -> The buffered reader file descriptor
     * filename -> the filename we want to either read or write to
     */
    private FileReader reader;
    private FileWriter writer;
    private BufferedReader fd;
    private final String filename;

    /**
     * @message write_line_to_file
     * @brief Manages the errors of writing data to a file
     * @param data the data to write
     */
    private void write_line_to_file(String data) {
        try {
            /* Reset the file stream */
            this.writer.write(data);
            this.writer.write("\n");
        }
        catch(Exception e) {
            System.out.println("There was an error with writing data to the output file. | `" + this.filename + '`');
        }
    }

    /**
     * @message read_line_from_file
     * @brief Manages the errors of reading data from a file
     * @return the line read
     */
    private String read_line_from_file() {
        try {
            /* Reset the file stream */
            return this.fd.readLine();
        }
        catch(Exception e) {
            System.out.println("There was an error with reading a line from the input file. | `" + this.filename + '`');
            return null;
        }
    }

    /**
     * @message create_writer_fd
     * @brief Manages the errors of creating a writer file object
     */
    private FileWriter create_writer_fd() {
        try {
            File f = new File(this.filename);
            if(f.exists())
                f.delete();

            return new FileWriter(this.filename, true); /* APPEND */
        }
        catch(Exception e) {
            System.out.println("There was an error with creating the output file. | `" + this.filename + '`');
            return null;
        }
    }

    /**
     * @message create_reader_fd
     * @brief Manages the errors of creating a reader file object as well as its buffered reader
     */
    private FileReader create_reader_fd() {
        try {
            File f = new File(this.filename);
            if(!f.exists())
                throw new Exception("File does not exist");
                
            this.reader = new FileReader(this.filename);
            this.fd = new BufferedReader(this.reader);
            return this.reader;
        }
        catch(Exception e) {
            System.out.println("There was an error with creating the input file. | `" + this.filename + '`');
            return null;
        }
    }

    /**
     * @Constructor
     */
    public FileHandler(String filename, boolean w) {
        this.filename = filename;
        this.fd = null;

        if(w)
            this.writer = create_writer_fd();
        else
            this.reader = create_reader_fd();
    }

    /**
     * @message read_line
     * @brief Read data from a file
     * @return The data captured from the file descriptor 
     */
    public String read_line() {
        return read_line_from_file();
    }

    /**
     * @message append_line
     * @brief Appends a new line to the file without overwriting
     */
    public void append_line(String data) {
        write_line_to_file(data);
    }

    /**
     * @message close_fd
     * @brief Close all file descriptors after flushing any data remaining
     */
    public void close_fd() {
        try {
            if(this.writer != null) writer.flush();
            if(this.reader != null) reader.close();
            if(this.writer != null) writer.close();
        }
        catch(Exception e) {
            System.out.println("There was an error with closing the files. | `" + this.filename + '`');
        }
    }
}