
import Memory.HDD;
import Memory.RealMemory;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
    private JButton submitButton;
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

    private JLabel testLabel; // Jei sita istrinam paskutinio labelio niekad nerodo nzn kas per bugas:DDDD
    // TextFields for Registers
    private JTextField ptrField;
    private JTextField r0Field;
    private JTextField r1Field;

    private JTextField pcField;
    private JTextField siField;
    private JTextField piField;
    
    private JTextField tiField;
    private JTextField modeField;
    private JTextField sfField;
    // TextArea for our output
    private JTextArea tArea;

    
    public GUI()
    {
        frame = new JFrame();
        frame.setSize(600, 800);
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
        
        // Registru textFieldai
        ptrField = new JFormattedTextField("");
        ptrField.setBounds(45,630,30,20);
        panel.add(ptrField);

        r0Field = new JTextField("");
        r0Field.setBounds(45,650,30,20);
        panel.add(r0Field);

        r1Field = new JTextField("");
        r1Field.setBounds(45,670,30,20);
        panel.add(r1Field);

        tiField = new JTextField("");
        tiField.setBounds(115,630,30,20);
        panel.add(tiField);

        modeField = new JTextField("");
        modeField.setBounds(115,650,30,20);
        panel.add(modeField);

        sfField = new JTextField("");
        sfField.setBounds(115,670,30,20);
        panel.add(sfField);

        pcField = new JTextField("");
        pcField.setBounds(170,630,30,20);
        panel.add(pcField);

        siField = new JTextField("");
        siField.setBounds(170,650,30,20);
        panel.add(siField);

        piField = new JTextField("");
        piField.setBounds(170,670,30,20);
        panel.add(piField);
        // TextArea musu outputui
        tArea = new JTextArea(10, 10);
        panel.add(tArea);
        tArea.append("Musu outputas keliaus \n cia su tArea.append \n funkcija");
        tArea.setBounds(315,615,130,130);
        // Mygtukas kazkam
        submitButton = new JButton("Uzkrauti");
        submitButton.addActionListener(this);
        submitButton.setBounds(30, 720, 90, 20);
        panel.add(submitButton);
        // Darom registru labeli ir ji pridedam
        ptr = new JLabel("PTR");
        panel.add(ptr);
        ptr.setBounds(15,630,size.width, size.height);

        r0 = new JLabel("R0");
        panel.add(r0);
        r0.setBounds(15,650,size.width, size.height);

        r1 = new JLabel("R1");
        panel.add(r1);
        r1.setBounds(15,670,size.width, size.height);

        ti = new JLabel("TI");
        panel.add(ti);
        ti.setBounds(80,630,size.width, size.height);

        mode = new JLabel("MODE");
        panel.add(mode);
        mode.setBounds(80,650,size.width, size.height);

        sf = new JLabel("SF");
        panel.add(sf);
        sf.setBounds(80,670,size.width, size.height);

        pc = new JLabel("PC");
        panel.add(pc);
        pc.setBounds(150,630,size.width, size.height);

        si = new JLabel("SI");
        panel.add(si);
        si.setBounds(150,650,size.width, size.height);

        pi = new JLabel("PI");
        panel.add(pi);
        pi.setBounds(150,670,size.width, size.height);

        testLabel = new JLabel("T");
        panel.add(testLabel);
        testLabel.setBounds(155,670,size.width, size.height);
        
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
        
        JOptionPane.showMessageDialog(frame, "Kolkas nesugalvojom ka mygtukas daro, bet veikia!");
    }

}
