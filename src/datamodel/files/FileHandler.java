package datamodel.files;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;

/* CHECK THAT FILES ONLY APPEND */
/* TODO -> MAKE FILEHANDLER MORE PORTABLE */
/* FAIL ON FILE EXISTANCE */

/**
 * @class: FileHandler
 * @desc: Implements the funcitons of reading data from and writing to a file
 * @param reader A reader file object
 * @param writer A writer file object
 * @param fd The buffered reader file descriptor
 * @param filename the filename we want to either read or write to
 */
public class FileHandler {
    private FileReader reader;
    private FileWriter writer;
    private BufferedReader fd;
    private String filename;

    /**
     * @func: writeLineToFile
     * @desc: Manages the errors of writing data to a file
     * @param data the data to write
     */
    private int writeLineToFile(String data) {
        try {
            /* Reset the file stream */
            // getWriterFD().write(data);
            // getWriterFD().write("\n");
            this.writer.write(data);
            this.writer.write("\n");
            return 0;
        }
        catch(Exception e) {
            System.out.println("There was an error with writing data to the output file. | `" + this.filename + '`');
            return -1;
        }
    }

    /**
     * @func: readLineFromFile
     * @desc: Manages the errors of reading data from a file
     * @return the line read
     */
    private String readLineFromFile() {
        try {
            /* Reset the file stream */
            // return getReaderFD().readLine();
            return this.fd.readLine();
        }
        catch(Exception e) {
            System.out.println("There was an error with reading a line from the input file. | `" + this.filename + '`');
            return null;
        }
    }

    /**
     * @func: createWriterFD
     * @desc: Manages the errors of creating a writer file object
     */
    private FileWriter createWriterFD() {
        try {
            return new FileWriter(this.filename, true); /* APPEND */
        }
        catch(Exception e) {
            System.out.println("There was an error with creating the output file. | `" + this.filename + '`');
            return null;
        }
    }

    /**
     * @func: createReaderFD
     * @desc: Manages the errors of creating a reader file object as well as its buffered reader
     */
    private FileReader createReaderFD() {
        try {
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
            this.writer = createWriterFD();
        else
            this.reader = createReaderFD();
    }

    /**
     * @func: readLine
     * @desc: Read data from a file
     * @return The data captured from the file descriptor 
     */
    public String readLine() {
        // return readLineFromFile(getReaderFD());
        return readLineFromFile();
    }

    // /**
    //  * @func: readCharacterFromFile
    //  * @desc: Reads a single character
    //  * @return -> The result of `read`
    //  */
    // public int readCharacterFromFile() {
    //     try {
    //         // return getReaderFD().read();
    //         return this.fd.read();
    //     }
    //     catch(Exception e) {
    //         System.out.println("There was an error in reading character from file | `" + this.filename + '`');
    //         return -1;
    //     }
    // }

    /**
     * @func: appendLine
     * @desc: Appends a new line to the file without ovewritting
     * @return -> The result of write
     */
    public int appendLine(String data) {
        return writeLineToFile(data);
    }

    /**
     * @func: closeFD()
     * @desc: Close all file descriptors after flushing any data remaining
     */
    public int closeFD() {
        try {
            if(this.writer != null) writer.flush();
            if(this.reader != null) reader.close();
            if(this.writer != null) writer.close();
            return 0;
        }
        catch(Exception e) {
            System.out.println("There was an error with closing the files. | `" + this.filename + '`');
            return -1;
        }
    }
}