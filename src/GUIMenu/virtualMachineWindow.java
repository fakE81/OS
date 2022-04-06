package GUIMenu;

import java.awt.Component;
import java.awt.*;
import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Memory.VirtualMemory;

import java.awt.event.WindowAdapter;

import java.awt.event.*;

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
        for(int i =0;i < vm.BLOCK_COUNT; i++){
            for(int j =0; j < vm.BLOCK_SIZE; j++){
                virtualData[i][j]= vm.getWord(i, j).getValue();
            }
        }
    }
}
