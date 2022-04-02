import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RealMachine {
    


    private HDD hdd;
    // 1) Registrai.
    // 2)



    RealMachine(){
        this.hdd = new HDD();
    }


    public void run(){
        try {
            LoadProgram("Test1.txt");
            System.out.println(hdd.read(20, 0));
            System.out.println("Programa sekmingai nuskaityta");
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
