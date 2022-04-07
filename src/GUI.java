
import Memory.RealMemory;
import Memory.VirtualMemory;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


import GUIMenu.userMemoryWindow;
import GUIMenu.virtualMachineWindow;

import java.awt.*;
import java.awt.event.*;
public class GUI{
    private static int programID = 1;



    private static JFrame frame;
    public static RealMachine rm = new RealMachine(programID);
    private JPanel panel;
    private JLabel virtualMemoryLabel;
    private JLabel userMemoryLabel;
    private JButton submitButton;
    private JButton stepButton;
    virtualMachineWindow v = new virtualMachineWindow();
    static virtualMachineWindow virtualMemory = new virtualMachineWindow();
    static userMemoryWindow userMemory = new userMemoryWindow();

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
    private static JFormattedTextField ptrField;
    private static JFormattedTextField r0Field;
    private static JFormattedTextField r1Field;

    private static JFormattedTextField pcField;
    private static JFormattedTextField siField;
    private static JFormattedTextField piField;
    
    private static JFormattedTextField tiField;
    private static JFormattedTextField modeField;
    private static JFormattedTextField sfField;
    
    private static JFormattedTextField cfField;
    private static JFormattedTextField zfField;
    private static JFormattedTextField ofField;
    // TextArea for our output
    private static JTextArea tArea;

    

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
        virtualMemoryLabel.setBounds(15, 610, size.width, size.height);
        userMemoryLabel.setBounds(15, 310, size1.width, size1.height);
        // Pridedam juos
        panel.add(virtualMemoryLabel);
        panel.add(userMemoryLabel);
        
        // Registru textFieldai
        ptrField = new JFormattedTextField("");
        ptrField.setBounds(45,630,50,20);
        ptrField.setValue(RealMachine.PTR);
        ptrField.setEditable(false);
        panel.add(ptrField);

        r0Field = new JFormattedTextField("");
        r0Field.setBounds(45,650,50,20);
        r0Field.setValue(RealMachine.R0);
        r0Field.setEditable(false);
        panel.add(r0Field);

        r1Field = new JFormattedTextField("");
        r1Field.setBounds(45,670,50,20);
        r1Field.setValue(RealMachine.R1);
        r1Field.setEditable(false);
        panel.add(r1Field);

        tiField = new JFormattedTextField("");
        tiField.setBounds(135,630,50,20);
        tiField.setValue(RealMachine.TI);
        tiField.setEditable(false);
        panel.add(tiField);

        modeField = new JFormattedTextField("");
        modeField.setBounds(135,650,50,20);
        modeField.setValue(RealMachine.mode);
        modeField.setEditable(false);
        panel.add(modeField);

        sfField = new JFormattedTextField("");
        sfField.setBounds(290,630,30,20);
        sfField.setValue(RealMachine.sf);
        sfField.setEditable(false);
        panel.add(sfField);

        cfField = new JFormattedTextField("");
        cfField.setBounds(290,650,30,20);
        cfField.setValue(RealMachine.sf);
        cfField.setEditable(false);
        panel.add(cfField);

        zfField = new JFormattedTextField("");
        zfField.setBounds(290,670,30,20);
        zfField.setValue(RealMachine.sf);
        zfField.setEditable(false);
        panel.add(zfField);

        ofField = new JFormattedTextField("");
        ofField.setBounds(290,690,30,20);
        ofField.setValue(RealMachine.sf);
        ofField.setEditable(false);
        panel.add(ofField);

        pcField = new JFormattedTextField("");
        pcField.setBounds(220,630,40,20);
        pcField.setValue(RealMachine.PC);
        pcField.setEditable(false);
        panel.add(pcField);

        siField = new JFormattedTextField("");
        siField.setBounds(220,650,40,20);
        siField.setValue(RealMachine.SI);
        siField.setEditable(false);
        panel.add(siField);

        piField = new JFormattedTextField("");
        piField.setBounds(135,670,50,20);
        piField.setValue(RealMachine.PI);
        piField.setEditable(false);
        panel.add(piField);
        // TextArea musu outputui
        tArea = new JTextArea(100, 10);
        panel.add(tArea);
        tArea.append("");
        tArea.setBounds(330,615,800,130);
        tArea.setLineWrap(true);
        tArea.setWrapStyleWord(true);
        // Mygtukas kazkam
        stepButton = new JButton( new AbstractAction("Step") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                startWithSteps();
                //JOptionPane.showMessageDialog(frame, "Step mygtukas veikia");
            }
        });
        stepButton.setBounds(150, 720, 90, 20);
        panel.add(stepButton);

        submitButton = new JButton( new AbstractAction("Start") { 
            @Override
            public void actionPerformed( ActionEvent e ) {
                try {
                    start();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                //JOptionPane.showMessageDialog(frame, "Kolkas nesugalvojom ka mygtukas daro, bet veikia uzkrovimo mygtukas!");
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

        testLabel = new JLabel("");
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

    public void start() throws InterruptedException{
        
        rm.start();
    }
    public void startWithSteps(){
        rm.runWithSteps();
    }

    public static void updateRegisters(int ptr,int r0, int r1, byte pc, byte si, byte pi,byte ti,byte mode){
        ptrField.setValue(ptr);
        r0Field.setValue(r0);
        r1Field.setValue(r1);
        pcField.setValue(pc);
        siField.setValue(si);
        piField.setValue(pi);
        tiField.setValue(ti);
        modeField.setValue(mode);
    }
    public static void updateStatusFlags(byte[] status){
        cfField.setValue(status[0]);
        zfField.setValue(status[1]);
        sfField.setValue(status[2]);
        ofField.setValue(status[3]);
    }

    public static void updateVirtualMemory(VirtualMemory vm){
        virtualMemory.update(vm);
    }
    public static void updateRealMemory(RealMemory rm){
        userMemory.update(rm);
    }

    public static void updateOutputStream(String text){
        tArea.append(text);
    }

    public static int inputDialog(){
        String s = (String)JOptionPane.showInputDialog(frame, "Input");
        return Integer.parseInt(s);
    }

    public static void main(String[] args) {
        new GUI();
        System.out.println("Sukuriam Realia masina");
    }

}
