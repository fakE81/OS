import java.util.Scanner;

public class Main {

    // 1) Hdd nuskaitymas. 1 Faila nuskaityti. Perduoti ji RM
    // 2) Realios masinos registrus Static. Turi Memory
    // 3) VirtualMachine. Gauna Memory ir bando atpazinti komandas.


    
    public static void main(String[] args) {
        // Starting class.
        System.out.println("Sukuriam HDD objekta skaitymui");
        HDD fileReader = new HDD();
        
        System.out.println(fileReader.read(2, 2));
        
        
        
    }
   }