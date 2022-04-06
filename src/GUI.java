
import Memory.HDD;
import Memory.RealMemory;

import javax.swing.AbstractAction;
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


import GUIMenu.userMemoryWindow;
import GUIMenu.virtualMachineWindow;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;  
public class GUI{
    
    private JFrame frame;
    public static RealMachine rm = new RealMachine();
    private JPanel panel;
    private JLabel virtualMemoryLabel;
    private JLabel userMemoryLabel;
    private JButton submitButton;
    private JButton stepButton;
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

    private JLabel cf;
    private JLabel zf;
    private JLabel of;

    private JLabel testLabel; // Jei sita istrinam paskutinio labelio niekad nerodo nzn kas per bugas:DDDD
    // TextFields for Registers
    private JFormattedTextField ptrField;
    private JFormattedTextField r0Field;
    private JFormattedTextField r1Field;

    private JFormattedTextField pcField;
    private JFormattedTextField siField;
    private JFormattedTextField piField;
    
    private JFormattedTextField tiField;
    private JFormattedTextField modeField;
    private JFormattedTextField sfField;
    
    private JFormattedTextField cfField;
    private JFormattedTextField zfField;
    private JFormattedTextField ofField;
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
        virtualMemoryLabel.setBounds(15, 610, size.width, size.height);
        userMemoryLabel.setBounds(15, 290, size1.width, size1.height);
        // Pridedam juos
        panel.add(virtualMemoryLabel);
        panel.add(userMemoryLabel);
        
        // Registru textFieldai
        ptrField = new JFormattedTextField("");
        ptrField.setBounds(45,630,50,20);
        ptrField.setValue(new RealMachine().PTR);
        ptrField.setEditable(false);
        panel.add(ptrField);

        r0Field = new JFormattedTextField("");
        r0Field.setBounds(45,650,50,20);
        r0Field.setValue(new RealMachine().R0);
        r0Field.setEditable(false);
        panel.add(r0Field);

        r1Field = new JFormattedTextField("");
        r1Field.setBounds(45,670,50,20);
        r1Field.setValue(new RealMachine().R1);
        r1Field.setEditable(false);
        panel.add(r1Field);

        tiField = new JFormattedTextField("");
        tiField.setBounds(135,630,50,20);
        tiField.setValue(new RealMachine().TI);
        tiField.setEditable(false);
        panel.add(tiField);

        modeField = new JFormattedTextField("");
        modeField.setBounds(135,650,50,20);
        modeField.setValue(new RealMachine().mode);
        modeField.setEditable(false);
        panel.add(modeField);

        sfField = new JFormattedTextField("");
        sfField.setBounds(290,630,30,20);
        sfField.setValue(new RealMachine().sf);
        sfField.setEditable(false);
        panel.add(sfField);

        cfField = new JFormattedTextField("");
        cfField.setBounds(290,650,30,20);
        cfField.setValue(new RealMachine().sf);
        cfField.setEditable(false);
        panel.add(cfField);

        zfField = new JFormattedTextField("");
        zfField.setBounds(290,670,30,20);
        zfField.setValue(new RealMachine().sf);
        zfField.setEditable(false);
        panel.add(zfField);

        ofField = new JFormattedTextField("");
        ofField.setBounds(290,690,30,20);
        ofField.setValue(new RealMachine().sf);
        ofField.setEditable(false);
        panel.add(ofField);

        pcField = new JFormattedTextField("");
        pcField.setBounds(220,630,40,20);
        pcField.setValue(new RealMachine().PC);
        pcField.setEditable(false);
        panel.add(pcField);

        siField = new JFormattedTextField("");
        siField.setBounds(220,650,40,20);
        siField.setValue(new RealMachine().SI);
        siField.setEditable(false);
        panel.add(siField);

        piField = new JFormattedTextField("");
        piField.setBounds(135,670,50,20);
        piField.setValue(new RealMachine().PI);
        piField.setEditable(false);
        panel.add(piField);
        // TextArea musu outputui
        tArea = new JTextArea(10, 10);
        panel.add(tArea);
        tArea.append("Musu outputas keliaus \n cia su tArea.append \n funkcija");
        tArea.setBounds(330,615,130,130);
        // Mygtukas kazkam
        stepButton = new JButton( new AbstractAction("Step") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                JOptionPane.showMessageDialog(frame, "Step mygtukas veikia");
            }
        });
        stepButton.setBounds(150, 720, 90, 20);
        panel.add(stepButton);

        submitButton = new JButton( new AbstractAction("Uzkrauti") { 
            @Override
            public void actionPerformed( ActionEvent e ) {
                JOptionPane.showMessageDialog(frame, "Kolkas nesugalvojom ka mygtukas daro, bet veikia uzkrovimo mygtukas!");
            }
        });
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
        ti.setBounds(100,630,size.width, size.height);

        mode = new JLabel("MODE");
        panel.add(mode);
        mode.setBounds(100,650,size.width, size.height);

        sf = new JLabel("SF");
        panel.add(sf);
        sf.setBounds(270,630,size.width, size.height);

        cf = new JLabel("CF");
        panel.add(cf);
        cf.setBounds(270,650,size.width, size.height);

        zf = new JLabel("ZF");
        panel.add(zf);
        zf.setBounds(270,670,size.width, size.height);

        of = new JLabel("OF");
        panel.add(of);
        of.setBounds(270,690,size.width, size.height);

        pc = new JLabel("PC");
        panel.add(pc);
        pc.setBounds(200,630,size.width, size.height);

        si = new JLabel("SI");
        panel.add(si);
        si.setBounds(200,650,size.width, size.height);

        pi = new JLabel("PI");
        panel.add(pi);
        pi.setBounds(100,670,size.width, size.height);

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
        //RealMachine rm = new RealMachine();
        rm.run();
    }

}
