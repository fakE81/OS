package Memory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class HDD {
    // Hard drive.
    private static final int DISK_SIZE = 4096; // Size of disk.
    private static final String FILL_CHARACTER = " ";
    private static RandomAccessFile file;

    // Sukuriamas tuscias HDD. Size = 4096
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
            for (int i = 0; i < DISK_SIZE/2; ++i) {
              file.seek(i*2 ); // moves the pointer to the position specified with the bytePosition parameter.
              file.writeChars(FILL_CHARACTER); // Uzpildom tusciai HDD.
            }
          } catch (IOException e) {
              System.out.println("Error initializing HDD");
          }
        
    }
    // Rasom nuo kazkokio tai offset'o.
    public static void write(char[] data, int off) { // Rasom i pasirinkta sektoriu tam tikra data?
        if (off < 0 || off > DISK_SIZE) {
          throw new IllegalArgumentException("Incorrect offset");
        }
        try {
          file.seek(off);
          file.writeChars(new String(data)); // rasom
        } catch (IOException e) {
          System.out.println(" write error");
        }
      }
      // ProgramID - pasirenki kuria programa jei id = 1, bus uzkraunama prima programa, 2 - antra ir t.t., size svarbu kad didelis butu.
      public static String read(int size, int offset,int programId) {
        String dataRead = new String();
        if (offset < 0 || offset > DISK_SIZE) {
          throw new IllegalArgumentException("Incorrect sector");
        }
        try {
          file.seek(offset);
          for (int i = 0; i < size; i++) {
            char c = file.readChar();
            // Tikrinam ar programa prasideda, jei taip, minusuojam ID;
            if(c == '$'){
              programId--;
            }
            // Jei id = 0, matom kad ta programa reikia uzsikrauti. ja ir uzsikraunam
            if(programId == 0){
              dataRead += c;
            }else if(programId < 0){
              return dataRead;
            }
          }
          file.read();
        } catch (IOException e) {
          System.out.println(" read error");
        }
        return dataRead;
      }

}
