package GUIMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Memory.RealMemory;

public class userMemoryWindow {
    
    
    public JTable table;
    public JScrollPane scPaneTable;
    public String[][] virtualBoxes = new String[17][16];
    public String [] virtualColumns = new String[16];

    public userMemoryWindow()
    {

        
        int maxC = 16;
        int maxL = 16;
        for(int i = 0; i < maxC; i++)
        {
            virtualColumns[i] = Integer.toHexString(i);
        }
        for(int i =0;i < maxC+1; i++){
            for(int j =0; j < maxL; j++){
                
                virtualBoxes[i][j] = " ";
            }
        }

        table = new JTable(virtualBoxes, virtualColumns);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        scPaneTable = new JScrollPane(table);
    }

    public void update(RealMemory vm){
        for(int i =0;i < 17; i++){
            for(int j =0; j < 16; j++){
                virtualBoxes[i][j]= vm.getWord(i, j).getValue();
            }
        }
        table.repaint();
    }
}