import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import Memory.HDD;
import Memory.RealMemory;
import Memory.VirtualMemory;
import Memory.Word;

public class VirtualMachine {
    private String writeStatus = "START";
    private VirtualMemory virtualMemory;

    private int PTR;
    private int id;

    File file;

    VirtualMachine(){

    }
    // Registrai esantys VM yra susynchronized su RM registrais
    VirtualMachine(int PTR,int id){
        this.id = id;
        this.virtualMemory = new VirtualMemory();
        this.PTR = PTR;
        UploadToMemory();
    }
    public void run(){
        try {
            processProgram();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        GUI.updateVirtualMemory(virtualMemory);
    }

    // Komandu tvarkymo metodas.
    private void processProgram() throws IOException{

        Word command = virtualMemory.getWord(RealMachine.PC);
        System.out.println(RealMachine.PC +") "+command.getValue());
        RealMachine.PC++;
        try {
            processCommand(command.getValue());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private void processCommand(String command) throws IOException{

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
            int x1 = Integer.parseInt(command.substring(2, 3),16);
            int x2 = Integer.parseInt(command.substring(3, 4),16);
            //System.out.println(RealMachine.PC+")WD: Saving value to address  " +x1*16 + ":" +x2);
            if(x1<2&&x2<16){
                virtualMemory.setWord(Integer.toString(RealMachine.R0), x1, x2);
            }else{
                RealMachine.PI = 1;
            }
        }

        // TODO: L0xx, R0 registrui, L1xx - R1 registrui. Load register1
        else if(command.substring(0,2).equals("L0")){
            int x1 = Integer.parseInt(command.substring(2, 3),16);
            int x2 = Integer.parseInt(command.substring(3, 4),16);
            //System.out.println(RealMachine.PC+")LD: Loading value from address  " +x1 + ":" +x2);
            if(x1<2&&x2<16){
                char[] word = new char[4];
                word = virtualMemory.getWord(x1, x2).getCharValue();
                RealMachine.R0 = Integer.parseInt(new String(word));
            }else{
                RealMachine.PI = 1;
            }
        }
        else if(command.substring(0,2).equals("L1")){
            int x1 = Integer.parseInt(command.substring(2, 3),16);
            int x2 = Integer.parseInt(command.substring(3, 4),16);
            //System.out.println(RealMachine.PC+")LD: Loading value from address  " +x1 + ":" +x2);

            if(x1<2&&x2<16){
                char[] word = new char[4];
                word = virtualMemory.getWord(x1, x2).getCharValue();

                RealMachine.R1 = Integer.parseInt(new String(word));
            }else{
                RealMachine.PI = 1;
            }
        }
        // Sukeicia vietomis R0 ir R1
        else if(command.substring(0,4).equals("MOVE")){
            //System.out.println(RealMachine.PC+")MOVING R0<->R1");
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
            //System.out.println(RealMachine.PC+")JM: Jumping to address:  " +x1*16 + ":" +x2);
            RealMachine.PC = (byte) (16*x1+x2);
        }
        else if(command.substring(0,2).equals("JB")){
            int x1 = Integer.parseInt(command.substring(2, 3));
            int x2 = Integer.parseInt(command.substring(3, 4));
            if(RealMachine.sf[0]==1){
                RealMachine.PC = (byte) (16*x1+x2);
                //System.out.println(RealMachine.PC+")JB: Jumping to address:  " +x1*16 + ":" +x2);
            }
        }
        else if(command.substring(0,2).equals("JA")){
            int x1 = Integer.parseInt(command.substring(2, 3));
            int x2 = Integer.parseInt(command.substring(3, 4));
            if(RealMachine.sf[0]==1 && RealMachine.sf[1]==0){
                RealMachine.PC = (byte) (16*x1+x2);
                //System.out.println(RealMachine.PC+")JA: Jumping to address:  " +x1*16 + ":" +x2);
            }
        }
        else if(command.substring(0,2).equals("JE")){
            int x1 = Integer.parseInt(command.substring(2, 3));
            int x2 = Integer.parseInt(command.substring(3, 4));
            if( RealMachine.sf[1]==1){
                RealMachine.PC = (byte) (16*x1+x2);
                //System.out.println(RealMachine.PC+")JE: Jumping to address:  " +x1*16 + ":" +x2);
            }
        }
        else if(command.substring(0,2).equals("JN")){
            int x1 = Integer.parseInt(command.substring(2, 3));
            int x2 = Integer.parseInt(command.substring(3, 4));
            if( RealMachine.sf[1]==0){
                RealMachine.PC = (byte) (16*x1+x2);
                //System.out.println(RealMachine.PC+")JN: Jumping to address:  " +x1*16 + ":" +x2);
            }
        }

        //-------------------Ivedimo/Isvedimo------------------//
        else if(command.substring(0,4).equals("PDR0")){
            RealMachine.SI = (byte)1;
        }
        else if(command.substring(0,2).equals("PR")){
            //RealMachine.SI = (byte)2;
            int blockNumber = Integer.parseInt(command.substring(2, 3),16);
            int wordNumber = Integer.parseInt(command.substring(3, 4),16);
            String text = virtualMemory.getWord(blockNumber, wordNumber).getValue();
            while(text.charAt(text.length()-1)!='#'){
                GUI.updateOutputStream(text);
                wordNumber++;
                text = virtualMemory.getWord(blockNumber, wordNumber).getValue();
                if(wordNumber==15){
                    wordNumber = 0;
                    blockNumber++;
                }
            }
            GUI.updateOutputStream(" ");
        }
        else if(command.substring(0,2).equals("RE")){
            //RealMachine.SI = (byte)9;
            int value = GUI.inputDialog();
            int x1 = Integer.parseInt(command.substring(2, 3),16);
            int x2 = Integer.parseInt(command.substring(3, 4),16);

            virtualMemory.setWord(Integer.toString(value), x1, x2);
        }
        //-------------------Darbas su failais------------------//
        // TODO: Darbas su failais.
        else if(command.substring(0,2).equals("OP")){
            int x1 = Integer.parseInt(command.substring(2, 3),16);
            int x2 = Integer.parseInt(command.substring(3, 4),16);

            String filename = virtualMemory.getWord(x1, x2).getValue();
            file = new File(filename);
        }
        // R0 - Kiek skaityti baitu. RDxy, xy - adresas kur patalpinti duomenis.
        else if(command.substring(0,2).equals("RD")){
            int countChar = RealMachine.R0;
            FileInputStream fis = new FileInputStream(file);
            int scanned = 0;
            char c;
            String text="";
            try {
                while((c=(char) fis.read())!=-1){
                    text += c;
                    scanned++;
                    if(scanned == countChar)
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            text+="#";
            List<String> tempSnipets= new ArrayList<String>();
            int index = 0;
            while (index<text.length()) {
                tempSnipets.add(text.substring(index, Math.min(index+4,text.length())));
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
        // Seek pointer - R0
        else if(command.substring(0,2).equals("WR")){
            int x1 = Integer.parseInt(command.substring(2, 3),16);
            int x2 = Integer.parseInt(command.substring(3, 4),16);

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            int pointer = RealMachine.R0;
            raf.seek(pointer);

            String text = virtualMemory.getWord(x1, x2).getValue();
            while(text.charAt(text.length()-1)!='#'){
                x2++;
                text += virtualMemory.getWord(x1, x2).getValue();
                if(x2==15){
                    x2 = 0;
                    x1++;
                }
            }
            text = text.substring(0, text.length()-1);

            byte[] s = text.getBytes("UTF-8");

            System.out.println(text);
            raf.write(s);
            raf.close();
        }
        else if(command.substring(0,4).equals("CLOS")){
            file = null;
        }
        //-----------HALT-------------//
        else if(command.substring(0,4).equals("HALT")){
            RealMachine.SI = 2;
        }
        else{
            RealMachine.PI = 2;
        }

        if(RealMachine.sf[3]==1){
            RealMachine.PI = 3;
        }

        // Handle interupt
        if(test()){
            RealMachine.Interrupt();
        }
        RealMachine.TI--;
    }


    // R0+R1, Atsakymas idedamas i R0
    private void ADD(){
        //System.out.println(RealMachine.PC+")R0+R1");
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
        //System.out.println(RealMachine.PC+")R0-R1");
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
        //System.out.println(RealMachine.PC+")R0*R1");
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
        //System.out.println(RealMachine.PC+")R0/R1");
        if(RealMachine.R1 != 0){
            int liekana = RealMachine.R0 % RealMachine.R1;
            RealMachine.R0 /= RealMachine.R1;
            RealMachine.R1 = liekana;
        }
        else{
            RealMachine.PI = 4;
        }

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
        return (RealMachine.PI + RealMachine.SI > 0) || RealMachine.TI == 0;
    }


    public void syncMemory(RealMemory rm){
        int syncBlocks = 0;
        for(int i =0; syncBlocks < VirtualMemory.BLOCK_COUNT;i++){  
            int blockId = Integer.valueOf(rm.getWord(PTR, i).getValue());
            for(int j = 0; j < VirtualMemory.BLOCK_COUNT; j++){
                rm.setWord(virtualMemory.getWord(i, j).getValue(), blockId, j);
            }
            syncBlocks++;
        }
    }

    // Is HDD ikelimas i VM atminti, bet LAIKINAS.
    private void UploadToMemory(){
        // TODO: Padaryt ne fixed size nuskaityma!!
        String code = HDD.read(1000, 0, id);
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
