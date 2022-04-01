import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class HDD {
    // Hard drive.
    // Skaitom is failo, kuriame yra komandos.
    
    // Uzsikrovima pasidaryt.
    private static final int SECTORS = 1024; // Kiek baitu skaitysim trubut?
    private static final String EMPTY_CELL_CHARACTER = " "; // Klausimas ar reikia mum
    private static final int WORDS_PER_SECTOR = 16; // 16 x 16
    private static RandomAccessFile file;

    public HDD()
    {
        try
        {
            // If the file does not already exist then an attempt will be made to create it.
            file = new RandomAccessFile("HDD", "rw"); // rw - Open for reading and writing.

        }
        catch(FileNotFoundException e)
        {
            System.out.println("Creating HDD error");
        }

        try {
            for (int i = 0; i < SECTORS; ++i) {
              file.seek(i * WORDS_PER_SECTOR * 2); // moves the pointer to the position specified with the bytePosition parameter.
              file.writeChars(EMPTY_CELL_CHARACTER); // cia reikia tustiem langeliams kaip pavyzdziuose
            }
          } catch (IOException e) {
            System.out.println("Error initializing HDD");
          }
        
    }
    public static void write(char[] data, int sector) { // Rasom i pasirinkta sektoriu tam tikra data?
        if (sector < 0 || sector > SECTORS) {
          throw new IllegalArgumentException("Incorrect sector");
        }
        try {
          file.seek(sector * WORDS_PER_SECTOR * 2);
          file.writeChars(new String(data)); // rasom
        } catch (IOException e) {
          System.out.println(" write error");
        }
      }
    
    
      public static String read(int size, int offset) {
        String dataRead = new String();
        if (offset < 0 || offset > SECTORS) {
          throw new IllegalArgumentException("Incorrect sector");
        }
        try {
          file.seek(offset);
          for (int i = 0; i < size; i++) {
            dataRead += file.readChar();
          }
          file.read();
        } catch (IOException e) {
          System.out.println(" read error");
        }
        return dataRead;
      }

}
