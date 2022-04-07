package Memory;

import java.util.ArrayList;

public class RealMemory {
    
    // Block count = 16
    // Word klase, kuri yra cia isreiksta. [Block_Count][Block_Size]
    // Rasyti i bloka Char[], gauti is bloko.

    public static int BLOCK_COUNT = 17;
    private static int BLOCK_SIZE = 16;
    
    public ArrayList<Integer> usedBlocks = new ArrayList<Integer>();
    public ArrayList<Integer> freeBlocks = new ArrayList<Integer>();
    private Word[][] memory = new Word[BLOCK_COUNT][BLOCK_SIZE];

    public RealMemory(){
        for (int x = 0; x < BLOCK_COUNT; ++x) {
            for (int y = 0; y < BLOCK_SIZE; ++y) {
                memory[x][y] = new Word();
            }
            freeBlocks.add(x);
        }
    }

    public Word getWord(int x1, int x2){
        return memory[x1][x2];
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
