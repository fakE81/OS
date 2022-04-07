package Memory;
public class VirtualMemory {
    
    // Virtuali atmintis VM
    // Data segmentai, Kodo segmentai.

    public static int BLOCK_COUNT = 16;
    public static int BLOCK_SIZE = 16;
    private Word[][] memory = new Word[BLOCK_COUNT][BLOCK_SIZE];

    private int currentDataBlock = 0;
    private int currentDataSegement = 0;

    private int currentCodeBlock = 6;
    private int currentCodeSegement = 0;

    // Nusinulinam
    public VirtualMemory(){
        for (int x = 0; x < BLOCK_COUNT; ++x) {
            for (int y = 0; y < BLOCK_SIZE; ++y) {
                memory[x][y] = new Word();
            }
        }
    }
    // Rasom Data:
    public void WriteDataSegment(Word word){
        if(currentDataSegement < 16){
            memory[currentDataBlock][currentDataSegement] = word; 
            currentDataSegement++;
        }else{
            currentDataBlock++;
            currentDataSegement = 0;
            memory[currentDataBlock][currentDataSegement] = word;
        }

    }
    //Rasom Code:
    public void WriteCodeSegement(Word word){
        if(currentCodeSegement < 16){
            memory[currentCodeBlock][currentCodeSegement] = word; 
            currentCodeSegement++;
        }else{
            currentCodeBlock++;
            currentCodeSegement = 0;
            memory[currentCodeBlock][currentCodeSegement] = word;
        }
    }
    public Word getWord(int x1, int x2){
        return memory[x1][x2];
    }
    public Word getWord(byte pc){
        return memory[pc/16+6][pc%16];
    }
    public void setWord(String word,int x1, int x2){
        memory[x1][x2] = new Word(word);
    }

    public void display() {
        for (int x = 0; x < BLOCK_COUNT; ++x) {
            for (int y = 0; y < BLOCK_SIZE; ++y) {
                if(memory[x][y]!=null)
                    System.out.print(memory[x][y].getValue() + "|");
                else
                System.out.print(memory[x][y] + "|");
            }
            System.out.println();
        }
    }
}
