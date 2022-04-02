import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Memory.HDD;
import Memory.RealMemory;

public class RealMachine {
    


    private HDD hdd;
    private RealMemory memory;
    // 1) Registrai.
    // 2)



    RealMachine(){
        this.hdd = new HDD();
        this.memory = new RealMemory();
    }


    public void run(){
        try {
            // Viskas vyksta cia.
            LoadProgram("Test1.txt");
            System.out.println(hdd.read(20, 0));
            System.out.println("Programa sekmingai nuskaityta");
            memory.setWord("lala", 0, 0);
            memory.setWord("FFFF", 1, 0);
            memory.setWord("baba", 2, 0);
            memory.display();
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
                    // Panaikinam newLine ir t.t.
                    if(r > 30 && r < 150)
                        programCode += (char) r;
                }
                hdd.write(programCode.toCharArray(), 0);
            }
    }




    
}
