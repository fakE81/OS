package GUIMenu;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Memory.VirtualMemory;

public class virtualMachineWindow extends JFrame {
    
    public JTable table;
    public JScrollPane scPaneTable;
    String[][] virtualData = new String[16][16];
    String [] virtualColumns = new String[16];
    
    public virtualMachineWindow()
    {

        
        int maxC = 16;
        int maxL = 16;
        for(int i = 0; i < maxC; i++)
        {
            virtualColumns[i] = Integer.toHexString(i);
        }
        for(int i =0;i < maxC; i++){
            for(int j =0; j < maxL; j++){
                virtualData[i][j]= " ";
            }
        }

        table = new JTable(virtualData, virtualColumns);
        
        scPaneTable = new JScrollPane(table);
    }


    public void update(VirtualMemory vm){
        for(int i =0;i < VirtualMemory.BLOCK_COUNT; i++){
            for(int j =0; j < VirtualMemory.BLOCK_SIZE; j++){
                virtualData[i][j]= vm.getWord(i, j).getValue();
            }
        }
        table.repaint();
    }
}
