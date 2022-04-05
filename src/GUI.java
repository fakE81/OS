
import Memory.HDD;
import Memory.RealMemory;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import GUIMenu.userMemoryWindow;
import GUIMenu.virtualMachineWindow;

import java.awt.*;
import java.awt.event.*;  
public class GUI implements ActionListener {
    
    private JFrame frame;
    
    private JPanel panel;
    private JLabel virtualMemoryLabel;
    private JLabel userMemoryLabel;
    virtualMachineWindow v = new virtualMachineWindow();
    virtualMachineWindow virtualMemory = new virtualMachineWindow();

    userMemoryWindow userMemory = new userMemoryWindow();
    
    public GUI()
    {
        frame = new JFrame();
        frame.setSize(1400, 800);
        frame.setSize(600, 800);
        frame.setVisible(true);
        panel = new JPanel();
        virtualMemoryLabel = new JLabel("Virtual Memory");
        userMemoryLabel = new JLabel("User Memory");
        panel.add(virtualMemoryLabel);
        panel.add(userMemoryLabel);
        Dimension size = virtualMemoryLabel.getPreferredSize();
        Dimension size1 = userMemoryLabel.getPreferredSize();
        virtualMemoryLabel.setBounds(15, 290, size.width, size.height);
        userMemoryLabel.setBounds(15, 610, size1.width, size1.height);
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); // Pixels top/bottom/left/right
        panel.setLayout( new BorderLayout() );
        panel.add(virtualMemory.scPaneTable, BorderLayout.PAGE_END); // Border layoutas vietai nusakyti
        panel.add(userMemory.scPaneTable, BorderLayout.PAGE_START); // lentele VM
        frame.add(panel, BorderLayout.BEFORE_LINE_BEGINS);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("OS");
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        // Starting class.
        
        new GUI();
        System.out.println("Sukuriam Realia masina");
        RealMachine rm = new RealMachine();
        rm.run();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
    }

}
