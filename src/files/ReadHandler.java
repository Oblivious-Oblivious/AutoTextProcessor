package files;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @class ReadHandler
 * @brief File handler for making file read operations more intuitive
 */
public class ReadHandler {
    /**
     * file_reader -> The FileReader object used in the method
     * buffered_reader -> The BufferedReader object used in the method
     * filepath -> The name of the file to read from
     */
    private FileReader file_reader;
    private BufferedReader buffered_reader;
    private String filepath;

    public ReadHandler() {}

    /**
     * @message open
     * @brief Open a file in read mode
     * @return -> a boolean signaling if the opening was successful
     */
    public boolean open(String filepath) {
        this.filepath = filepath;

        /* Check beforehand if the file exists */
        File file = new File(this.filepath);
        if(!file.exists())
            return false;

        try {
            this.file_reader = new FileReader(this.filepath);
            this.buffered_reader = new BufferedReader(file_reader);
            return true;
        }
        catch(FileNotFoundException e) {
            System.out.println("There was an error with creating the input file. | `" + this.filepath + '`');
            return false;
        }
    }

    /**
     * @message read_line
     * @brief Read a new line from the file descriptor
     * @return The line read
     */
    public String read_line() {
        try {
            return buffered_reader.readLine();
        }
        catch(IOException e) {
            System.out.println("Error on readLine");
            return null;
        }
    }

    /**
     * @message close
     * @brief Attempts to close the buffers to avoid memory overflows
     */
    public void close() {
        try {
            file_reader.close();
            buffered_reader.close();
        }
        catch(IOException e) {
            System.out.println("There was an error with closing the file descriptors. | `" + this.filepath + '`');
        }
    }
}
