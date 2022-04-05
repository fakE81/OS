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
        while(true){
                Word command = virtualMemory.getWord(RealMachine.PC);
                RealMachine.PC++;
                // Tikrinimas:
                if(command.getValue().equals("HALT")){
                    RealMachine.HALT();
                    return;
                }
                processCommand(command.getValue());
        }
                // Gaunam komanda.

    }
        
    

    private void processCommand(String command){

        //----------------Aritmetines-----------------//
        if(command.substring(0,3).equals("ADD")){
            ADD();
        }
        else if(command.substring(0,3).equals("SUB")){
            SUB();
        }
        else if(command.substring(0,3).equals("MUL")){
            MUL();
        }
        else if(command.substring(0,3).equals("DIV")){
            DIV();
        }
        //----------------Darbas su vm atmintim-----------------//
        else if(command.substring(0,2).equals("WD")){
            // TODO: Add zeros to make 4 chars.
            int x1 = Integer.parseInt(command.substring(2, 3));
            int x2 = Integer.parseInt(command.substring(3, 4));
            System.out.println(RealMachine.PC+")WD: Saving value to address  " +x1*16 + ":" +x2);
            virtualMemory.setWord(Integer.toString(RealMachine.R0), x1, x2);
            virtualMemory.display();
        }

        // TODO: L0xx, R0 registrui, L1xx - R1 registrui. Load register1
        else if(command.substring(0,2).equals("LD")){
            int x1 = Integer.parseInt(command.substring(2, 3),16);
            int x2 = Integer.parseInt(command.substring(3, 4),16);
            System.out.println(RealMachine.PC+")LD: Loading value from address  " +x1 + ":" +x2);

            char[] word = new char[4];

            word = virtualMemory.getWord(x1, x2).getCharValue();

            RealMachine.R0 = Integer.parseInt(new String(word));
        }
        // Sukeicia vietomis R0 ir R1
        // TODO: Padaryt gal kad MOVE 0004 leistu?
        // TODO: Bet tada reik keist programos uzsikrovima.
        else if(command.substring(0,4).equals("MOVE")){
            System.out.println(RealMachine.PC+")MOVING R0<->R1");
            int temp = RealMachine.R1;
            RealMachine.R1 = RealMachine.R0;
            RealMachine.R0 = temp;
        }

        //--------------Palyginimas/Valdymo-------------------/
        else if(command.substring(0,3).equals("CMP")){
            CMP();
        }
        else if(command.substring(0,2).equals("JM")){
            int x1 = Integer.parseInt(command.substring(2, 3));
            int x2 = Integer.parseInt(command.substring(3, 4));
            System.out.println(RealMachine.PC+")JM: Jumping to address:  " +x1*16 + ":" +x2);
            RealMachine.PC = (byte) (16*x1+x2);
        }
        else if(command.substring(0,2).equals("JB")){
            int x1 = Integer.parseInt(command.substring(2, 3));
            int x2 = Integer.parseInt(command.substring(3, 4));
            if(RealMachine.sf[0]==1){
                RealMachine.PC = (byte) (16*x1+x2);
                System.out.println(RealMachine.PC+")JB: Jumping to address:  " +x1*16 + ":" +x2);
            }
        }
        else if(command.substring(0,2).equals("JA")){
            int x1 = Integer.parseInt(command.substring(2, 3));
            int x2 = Integer.parseInt(command.substring(3, 4));
            if(RealMachine.sf[0]==1 && RealMachine.sf[1]==0){
                RealMachine.PC = (byte) (16*x1+x2);
                System.out.println(RealMachine.PC+")JA: Jumping to address:  " +x1*16 + ":" +x2);
            }
        }
        else if(command.substring(0,2).equals("JE")){
            int x1 = Integer.parseInt(command.substring(2, 3));
            int x2 = Integer.parseInt(command.substring(3, 4));
            if( RealMachine.sf[1]==1){
                RealMachine.PC = (byte) (16*x1+x2);
                System.out.println(RealMachine.PC+")JE: Jumping to address:  " +x1*16 + ":" +x2);
            }
        }
        else if(command.substring(0,2).equals("JN")){
            int x1 = Integer.parseInt(command.substring(2, 3));
            int x2 = Integer.parseInt(command.substring(3, 4));
            if( RealMachine.sf[1]==0){
                RealMachine.PC = (byte) (16*x1+x2);
                System.out.println(RealMachine.PC+")JN: Jumping to address:  " +x1*16 + ":" +x2);
            }
        }

        //-------------------Ivedimo/Isvedimo------------------//
        else if(command.substring(0,4).equals("PDR0")){
            RealMachine.SI = (byte)1;
        }
        else if(command.substring(0,4).equals("PDRS")){
            // ADD();
        }
        // TODO: Reiktu daryt per interupta, bet dabar paprastai is atminties bus:)
        else if(command.substring(0,2).equals("PR")){
            //RealMachine.SI = (byte)2;
            int blockNumber = Integer.parseInt(command.substring(2, 3),16);
            int wordNumber = Integer.parseInt(command.substring(3, 4),16);
            String text = virtualMemory.getWord(blockNumber, wordNumber).getValue();
            while(text.charAt(text.length()-1)!='#'){
                System.out.print(text);
                wordNumber++;
                text = virtualMemory.getWord(blockNumber, wordNumber).getValue();
                if(wordNumber==15){
                    wordNumber = 0;
                    blockNumber++;
                }
            }
            System.out.println();
        }
        else if(command.substring(0,2).equals("RE")){
            RealMachine.SI = (byte)9;
        }
        //-------------------Darbas su failais------------------//
        // TODO: Darbas su failais.
        else if(command.substring(0,4).equals("OPEN")){
            // ADD();
        }
        else if(command.substring(0,2).equals("WR")){
            // ADD();
        }
        else if(command.substring(0,4).equals("CLOS")){
            // ADD();
        }
        else if(command.substring(0,3).equals("DEL")){
            // ADD();
        }else{
            return;
        }
        // Handle interupt
        if(test()){
            RealMachine.Interrupt();
        }
    }


    // R0+R1, Atsakymas idedamas i R0
    private void ADD(){
        System.out.println(RealMachine.PC+")R0+R1");
        if(RealMachine.R0 + RealMachine.R1 > Integer.MAX_VALUE){
            // Overflow flag.
            RealMachine.sf[3] = 1;
            return;
        }else{
            RealMachine.R0 += RealMachine.R1;
        }
    }
    // R0-R1, Atsakymas i R0.
    private void SUB(){
        System.out.println(RealMachine.PC+")R0-R1");
        if(RealMachine.R0 - RealMachine.R1 < Integer.MIN_VALUE){
            // Overflow flag.
            RealMachine.sf[3] = 1;
            return;
        }else{
            RealMachine.R0 -= RealMachine.R1;
        }
    }
    // R0*R1, Atsakymas i R0
    private void MUL(){
        System.out.println(RealMachine.PC+")R0*R1");
        if(RealMachine.R0 * RealMachine.R1 > Integer.MIN_VALUE){
            // Overflow flag.
            RealMachine.sf[3] = 1;
            return;
        }else{
            RealMachine.R0 *= RealMachine.R1;
        }
    }
    // R0/R1, Atsakymas i R0, Liekana i R1.
    private void DIV(){
        System.out.println(RealMachine.PC+")R0/R1");
        int liekana = RealMachine.R0 % RealMachine.R1;
        RealMachine.R0 /= RealMachine.R1;
        RealMachine.R1 = liekana;

    }

    //----------------------//
    private void CMP(){
        if(RealMachine.R0 > RealMachine.R1){
            RealMachine.sf[0] = 0;
            RealMachine.sf[1] = 0;
        }
        else if(RealMachine.R0 < RealMachine.R1){
            RealMachine.sf[0] = 1;
        }
        else if(RealMachine.R0 == RealMachine.R1){
            RealMachine.sf[1] = 1;
        }
        else{
            RealMachine.sf[1] = 0;
        }
    }
    //--------Jumpai------------//

    private boolean test(){
        // TODO: TI pridet veliau
        return (RealMachine.PI + RealMachine.SI > 0);
    }

    // Is HDD ikelimas i VM atminti, bet LAIKINAS.
    private void UploadToMemory(){
        // TODO: Padaryt ne fixed size nuskaityma!!
        String code = HDD.read(300, 0, 1);
        System.out.println(code);
        // Split commands:
        String[] snipets = code.split("\\r?\\n"); 
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
                List<String> tempSnipets= new ArrayList<String>();
                int index = 0;
                while (index<snipet.length()) {
                    tempSnipets.add(snipet.substring(index, Math.min(index+4,snipet.length())));
                    index=index+4;
                 }
                for(String temp : tempSnipets){
                    if(temp.length() != 1 && temp.charAt(temp.length()-1)=='#'){
                        virtualMemory.WriteDataSegment(new Word(temp.substring(0, temp.length()-1)));
                        virtualMemory.WriteDataSegment(new Word("#"));
                    }
                    else{
                        virtualMemory.WriteDataSegment(new Word(temp));
                    }
                    
                }
            }
            else if(writeStatus.equals("CODE")){
                virtualMemory.WriteCodeSegement(new Word(snipet));
            }
        }
    }

}
