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


    private Paging paging;
    private HDD hdd;
    private RealMemory memory;

    RealMachine(){
        PTR = 0;
        R0 = 0;
        R1 = 0;
        PC = 0;
        SI = 0;
        PI = 0;
        TI = 0;
        mode = 0;

        this.hdd = new HDD();
        this.memory = new RealMemory();
        this.paging = new Paging();
    }


    public void run(){
        try {
            // Viskas vyksta cia.
            LoadProgram("Test1.txt"); // Uzsikraunam programa i HDD.
            PTR = paging.getFreeBlock(memory);
            paging.createPageTable(PTR, memory);
            VirtualMachine vm = new VirtualMachine(PTR); // Fill memory padarom.
            vm.syncMemory(memory);
            //TODO: Puslapiavimas.

            // Cia While(SI !=9):
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
                        programCode += (char) r;
                }
                HDD.write(programCode.toCharArray(), 0);
            }
    }

    public static void Interrupt(){
        if(SI == 1){
            System.out.println(PC+")Output stream: " + String.valueOf(R0));
        }
        else if(SI == 2){

        }
        else if(SI == 9){
            // Read input.
        }

        if(PI == 0){
            // Neteisingas adresas.
        }
        else if(PI == 2){
            // Neteisingas operacijos kodas.
        }

        SI = 0;
    }


    // Getters/Setters

    public static void HALT(){
        System.out.println(PC+")HALT!");
    }
}
