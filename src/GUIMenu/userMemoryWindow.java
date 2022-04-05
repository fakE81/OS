package GUIMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class userMemoryWindow {
    
    String virtualColumns[] =
    {
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
    };

    public JTable table;
    public JScrollPane scPaneTable;
    public String[][] virtualBoxes = new String[16][16];

    public userMemoryWindow()
    {

        
        int maxC = 16;
        int maxL = 16;
        for(int i =0;i < maxC; i++){
            for(int j =0; j < maxL; j++){
                
                virtualBoxes[i][j] = " ";
            }
        }

        table = new JTable(virtualBoxes, virtualColumns);
        
        scPaneTable = new JScrollPane(table);
    }
}