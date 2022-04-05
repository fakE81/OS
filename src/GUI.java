
import Memory.HDD;
import Memory.RealMemory;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import GUIMenu.virtualMachineWindow;

import java.awt.*;
import java.awt.event.*;  
public class GUI implements ActionListener {
    
    private JFrame frame;
    private JButton button;  
    private JPanel panel;
    private JLabel label;
    virtualMachineWindow v = new virtualMachineWindow();
    
    
    public GUI()
    {
        frame = new JFrame();
        frame.setSize(1400, 800);
        button = new JButton("Virtual Machine");
        button.setBounds(5, 5, 5, 5);
        button.addActionListener(this);
        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); // Pixels top/bottom/left/right
        
        panel.add(v.scPaneTable); // lentele VM
        //panel.add(button);
        //panel.add(scPaneTable);

        frame.add(panel, BorderLayout.CENTER);
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
        panel.add(v.scPaneTable);
        
    }

}
