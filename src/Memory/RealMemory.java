package Memory;

import java.util.ArrayList;

public class RealMemory {
    
    // Block count = 16
    // Word klase, kuri yra cia isreiksta. [Block_Count][Block_Size]
    // Rasyti i bloka Char[], gauti is bloko.

    public static int BLOCK_COUNT = 32;
    private static int BLOCK_SIZE = 16;
    
    public ArrayList<Integer> usedBlocks = new ArrayList<Integer>();

    // Nezinau kas geriau ar turet dvimati masyva su Word, ar turet atskira klase Block. Kuri tures 16 zodziu?
    private Word[][] memory = new Word[BLOCK_COUNT][BLOCK_SIZE];

    public RealMemory(){
        for (int x = 0; x < BLOCK_COUNT; ++x) {
            for (int y = 0; y < BLOCK_SIZE; ++y) {
                memory[x][y] = new Word();
            }
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
