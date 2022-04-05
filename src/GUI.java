
import Memory.HDD;
import Memory.RealMemory;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import GUIMenu.userMemoryWindow;
import GUIMenu.virtualMachineWindow;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;  
public class GUI implements ActionListener {
    
    private JFrame frame;
    private JPanel panel;
    private JLabel virtualMemoryLabel;
    private JLabel userMemoryLabel;
    virtualMachineWindow v = new virtualMachineWindow();
    virtualMachineWindow virtualMemory = new virtualMachineWindow();
    userMemoryWindow userMemory = new userMemoryWindow();

    // VARIABLES FOR REGISTERS
    private JLabel ptr;
    private JLabel r0;
    private JLabel r1;
    private JLabel pc;
    private JLabel si;
    private JLabel pi;
    private JLabel ti;
    private JLabel mode;
    private JLabel sf;
    private JTextField plrField;
    public GUI()
    {
        frame = new JFrame();
        frame.setSize(1000, 800);
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); // Pixels top/bottom/left/right
        panel.setLayout( new BorderLayout() );
        // Inicijuojam savo atminties labelius
        virtualMemoryLabel = new JLabel("Virtual Memory");
        userMemoryLabel = new JLabel("User Memory");
        // Nustatom ju vietas
        Dimension size = virtualMemoryLabel.getPreferredSize();
        Dimension size1 = userMemoryLabel.getPreferredSize();
        virtualMemoryLabel.setBounds(15, 290, size.width, size.height);
        userMemoryLabel.setBounds(15, 610, size1.width, size1.height);
        // Pridedam juos
        panel.add(virtualMemoryLabel);
        panel.add(userMemoryLabel);
        // Darom registru labeli ir ji pridedam
        ptr = new JLabel("PLR");
        panel.add(ptr);
        ptr.setBounds(15,630,size.width, size.height);

        r0 = new JLabel("R0");
        panel.add(r0);
        r0.setBounds(15,650,size.width, size.height);

        r1 = new JLabel("R1");
        panel.add(r1);
        r1.setBounds(15,670,size.width, size.height);

        pc = new JLabel("PC");
        panel.add(pc);
        pc.setBounds(300,630,size.width, size.height);

        si = new JLabel("SI");
        panel.add(si);
        si.setBounds(300,650,size.width, size.height);

        pi = new JLabel("PI");
        panel.add(pi);
        pi.setBounds(300,670,size.width, size.height);

        ti = new JLabel("TI");
        panel.add(ti);
        ti.setBounds(150,630,size.width, size.height);

        mode = new JLabel("MODE");
        panel.add(mode);
        mode.setBounds(150,650,size.width, size.height);

        sf = new JLabel("SF");
        panel.add(sf);
        sf.setBounds(150,670,size.width, size.height);
        // Darom registro teksto lauka
        plrField = new JTextField("sw");
        plrField.setBounds(400,650,40,40);
        panel.add(plrField);
        // Pridedam savo atmintis
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
