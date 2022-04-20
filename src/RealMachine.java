import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Memory.HDD;
import Memory.RealMemory;

public class RealMachine extends Thread{
    
    // Registrai:
    public static int PTR;
    public static int R0;
    public static int R1;

    public static byte PC;
    public static byte SI;
    public static byte PI;
    public static byte TI;
    public static byte mode;

    // byte sf
    public static byte[] sf = {0,0,0,0}; // 0-CF,1-ZF,2-SF,3-OF 


    private Paging paging;
    private HDD hdd;
    private RealMemory memory;

    private static boolean run = true;
    private boolean firstLoad = true;
    private int programID;
    private VirtualMachine vm;
    RealMachine(int id){
        programID = id;
        PTR = 0;
        R0 = 0;
        R1 = 0;
        PC = 0;
        SI = 0;
        PI = 0;
        TI = 100;
        mode = 0;

        this.hdd = new HDD();
        this.memory = new RealMemory();
        this.paging = new Paging();
    }

    @Override
    public void run(){
        try {
            // TODO: Kanalu iren.
            // TODO: supervizorine.
            // TODO: Interruptai susideti, su failais dirba kanalu irenginys
            // TODO: PTR lentele eina i 17bloko VM atminti
            // Viskas vyksta cia.


            /*
            Kanalo irengini pasakom kad skaitysim is hardo ir visa informacija visi 16bloku yra kisami i supervizorine atminti
            Iskvieciam Exchange, viskas atsiranda supervizorineje. Galima patikrinti ar nera blogu komandu.
            Jei viskas gerai Isskiriam 16bloku VM masinai/Puslapiavimas. Uzpildom supervizorine atmintimi VM.
            Kanalu irengini isvedimas/ivedimas. Source ir Destinaton nurodomas.
            */
            LoadProgram("Test1.txt"); // Uzsikraunam programa i HDD.
            PTR = paging.getFreeBlock(memory);
            paging.createPageTable(PTR, memory);
            vm = new VirtualMachine(PTR,programID); // Fill memory padarom.
            vm.syncMemory(memory);
            // Pagrindinis ciklas:
            // SI = 2 Halt.
            while(run){
                vm.run();
                vm.syncMemory(memory);
                //Thread.sleep(1000);

                // Patikrinti ar interupto nebuvo. Test paleist reik. ir tada Interrupt()
                GUI.updateRegisters(PTR, R0, R1, PC, SI, PI, TI, mode);
                GUI.updateStatusFlags(sf);
                GUI.updateRealMemory(memory);
            }
            //memory.display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runWithSteps(){
        if(firstLoad){
            try {
                LoadProgram("Test1.txt");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // Uzsikraunam programa i HDD.
            PTR = paging.getFreeBlock(memory);
            paging.createPageTable(PTR, memory);
            vm = new VirtualMachine(PTR,programID); // Fill memory padarom.
            vm.syncMemory(memory);
            firstLoad = false;
        }
        vm.run();
        vm.syncMemory(memory);
        GUI.updateRegisters(PTR, R0, R1, PC, SI, PI, TI, mode);
        GUI.updateStatusFlags(sf);
        GUI.updateRealMemory(memory);
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
        // TODO: Rasymo ir failu interuptai?
        if(SI == 1){
            GUI.updateOutputStream(String.valueOf(R0)+"\n");
        }
        else if(SI == 2){
            run = false;
            System.out.println("HALT!");
        }

        if(PI == 1){
            System.out.println("Neteisingas adresas!");
        }
        else if(PI == 2){
            System.out.println("Neteisingas operacijos kodas!");
        }
        else if(PI == 3){
            System.out.println("Perpildymas!");
        }
        else if(PI == 4){
            System.out.println("Dalyba iš 0");
        }

        if(TI == 0){
            TI = 100;
        }
        SI = 0;
        PI = 0;
    }


    // Getters/Setters

    public static void HALT(){
        System.out.println(PC+")HALT!");
    }
}
