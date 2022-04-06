import Memory.RealMemory;
import Memory.VirtualMemory;


public class Paging {

    int blocksUsed = 0;
    public void createPageTable(int blockId, RealMemory rm){
        for(int i = 0; i < RealMemory.BLOCK_COUNT && blocksUsed <  VirtualMemory.BLOCK_COUNT;i++){
            if(!rm.usedBlocks.contains(i)){
                rm.setWord(String.valueOf(i), blockId, i-1);
                blocksUsed++;
            }
        }
    }




    public int getFreeBlock(RealMemory rm){
        for(int i = 0; i < RealMemory.BLOCK_COUNT;i++){
            if(!rm.usedBlocks.contains(i)){
                rm.usedBlocks.add(i);
                return i;
            }
        }
        return -1;
    }
}
