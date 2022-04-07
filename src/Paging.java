import Memory.RealMemory;
import Memory.VirtualMemory;


public class Paging {

    int blocksUsed = 0;
    public void createPageTable(int blockId, RealMemory rm){
        for(int i = 0; i < RealMemory.BLOCK_COUNT && blocksUsed <  VirtualMemory.BLOCK_COUNT;i++){
            int number = (int) (Math.random() * (rm.freeBlocks.size() - 0)) + 0;
            int value = rm.freeBlocks.get(number);
            rm.setWord(String.valueOf(value), blockId, blocksUsed);
            rm.freeBlocks.remove(number);
            blocksUsed++;
            //if(!rm.usedBlocks.contains(i)){
            //    rm.setWord(String.valueOf(i), blockId, i-1);
            //    blocksUsed++;
            //}
        }
    }

    public int getFreeBlock(RealMemory rm){
        
        for(int i = 0; i < RealMemory.BLOCK_COUNT;){
            int number = (int) (Math.random() * (rm.freeBlocks.size() - 0)) + 0;
            int value = rm.freeBlocks.get(number);
            rm.freeBlocks.remove(number);
            return value;
        }
        return -1;
    }
}
