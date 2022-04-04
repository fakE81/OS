import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Memory.HDD;
import Memory.RealMemory;

public class RealMachine {
    
    // Registrai:
    public static int PTR;
    public static int R0;
    public static int R1;

    public static byte PC;
    public static byte SI;
    public static byte PI;
    public static byte TI;
    public static byte mode;

    public static byte[] sf = {0,0,0,0}; // 0-CF,1-ZF,2-SF,3-OF 

    private HDD hdd;
    private RealMemory memory;

    RealMachine(){
        this.hdd = new HDD();
        this.memory = new RealMemory();
    }


    public void run(){
        try {
            // Viskas vyksta cia.
            LoadProgram("Test1.txt"); // Uzsikraunam programa i HDD.
            VirtualMachine vm = new VirtualMachine(); // Virtuali masina uzkraunama
            //TODO: Puslapiavimas.
            vm.run();
            
            System.out.println("Realios masinos atmintis:");
            memory.display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Uzsikraunam programa i HDD
    private void LoadProgram(String filename) throws IOException{
        try (FileReader fr = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fr)){
                int r;
                String programCode= ""; // Tuscia.
                while((r = reader.read()) != -1){
                    // Panaikinam newLine, tarpus.
                    if(r > 33 && r < 150)
                        programCode += (char) r;
                }
                HDD.write(programCode.toCharArray(), 0);
            }
    }

    
}
