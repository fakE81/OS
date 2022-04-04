import java.util.ArrayList;
import java.util.List;

import Memory.HDD;
import Memory.VirtualMemory;
import Memory.Word;

public class VirtualMachine {
    


    //https://github.com/IgnasJ/OS
    //https://github.com/dovius/OS
    

    // 1) Registrai??
    // 2) Memory paduodamas
    // 3) Komandu procesinimas.
    // 3.1) Komandu atpazinimas
    // 3.2) Komandu metodai.
    // Registrai statiniai.


    private String writeStatus = "START";
    private VirtualMemory virtualMemory;

    // Registrai esantys VM yra susynchronized su RM registrais
    VirtualMachine(){
        this.virtualMemory = new VirtualMemory();
    }



    public void run(){
        //processCommand();
        UploadToMemory();
        processProgram();
    }

    // Komandu tvarkymo metodas.
    private void processProgram(){

        // Code segmento pradzia. 2 Blokas.
        for(int i = 2; i < VirtualMemory.BLOCK_SIZE ; i++){
            for(int j = 0; j < VirtualMemory.BLOCK_COUNT; j++){
                // Gaunam komanda.
                Word command = virtualMemory.getWord(i, j);
                System.out.println("Processing command:" + command.getValue());

                //Tikrinimas:
                if(command.getValue().equals("HALT")){
                    System.out.println("Programa baigia darba!");
                    return;
                }
                // TODO: Komandas surasyti visas taip is word'o.
                else if(command.getValue().substring(0,2).equals("JM")){
                    System.out.println("Command:JM");
                    // ADD();
                }
                // Handle interupt
                if(test()){
                    // handle
                }

            }
        }
    }




    private boolean test(){
        // TODO: Pakeist kai reiks.
        return true;
    }

    // Is HDD ikelimas i VM atminti, bet LAIKINAS.
    private void UploadToMemory(){
        // TODO: Padaryt ne fixed size nuskaityma!!
        String code = HDD.read(300, 0, 2);
        System.out.println(code);
        // Split commands:

        // Kodo gabaliukai isskaidomi po 4 baitus.
        List<String> snipets= new ArrayList<String>();
        int index = 0;
        while (index<code.length()) {
            snipets.add(code.substring(index, Math.min(index+4,code.length())));
            index=index+4;
        }

        // Iteruojam  per komandas.
        for(String snipet : snipets){
            // Tikrinam ir surasom viska i memory.
            if(snipet.charAt(0) == '$'){
                // Do Nothing
            }
            else if(snipet.equals("HALT")){
                virtualMemory.WriteCodeSegement(new Word(snipet));
                virtualMemory.display();
                return;
            }
            else if(snipet.equals("DATA") && writeStatus.equals("START")){
                writeStatus = "DATA";
                continue;
            }
            else if(snipet.equals("CODE") && writeStatus.equals("DATA")){
                writeStatus = "CODE";
                continue;
            }

            // Kvieciam rasyma.
            if(writeStatus.equals("DATA")){
                // TODO: Kitaip sugalvot. Dabar tik bendram atvejui.
                virtualMemory.WriteDataSegment(new Word(snipet));
            }
            else if(writeStatus.equals("CODE")){
                virtualMemory.WriteCodeSegement(new Word(snipet));
            }
        }
    }

}
