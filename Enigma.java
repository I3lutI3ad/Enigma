/**
 *
 * @author ripusudan
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Enigma extends JFrame{
    
    private static int count =0;
    private static JPanel jpanel;
    private static JComboBox<Character> rotar1;
    private static JComboBox<Character> rotar2;
    private static JComboBox<Character> rotar3;
    private static JComboBox<Character> rotar4;
    private static JTextArea ciphered;
    private static JTextArea actual;
    private static JScrollPane scrollc;
    private static JScrollPane scrolla;
    private static JButton reset;
    private Character[] rotars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final String plugboard = "BADCFEHGJILKNMPORQTSUVWXYZ";
    private static final String rotar1s = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final String rotar2s = "YAZPLMEDCIJBWSXOKNRFVUHGTQ";
    private static final String rotar3s = "MQPZYRGTOXWNEIFSJBKVCLUAHD";
    private static final String rotar4s = "WLPOKNBJIUHVCGYTFXZDRESAMQ";
    private static final String reflector = "ZYXWVUTSRQPONMLKJIHGFEDCBA";    
    
    public Enigma(){
        initComponents();
    }
    
    private void initComponents(){
        jpanel = new JPanel();
        jpanel.setLayout(null);
        rotar1 = new JComboBox<Character>(rotars);
        rotar2 = new JComboBox<Character>(rotars);
        rotar3 = new JComboBox<Character>(rotars);
        rotar4 = new JComboBox<Character>(rotars);
        reset = new JButton("RESET");
        ciphered = new JTextArea(255,130);
        actual = new JTextArea(255,130);
        scrollc= new JScrollPane(ciphered);
        scrolla= new JScrollPane(actual);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Enigma");
        this.setSize(265, 370);
        jpanel.setSize(265, 360);
        jpanel.setLocation(0, 0);
        jpanel.setBackground(Color.DARK_GRAY);
        this.add(jpanel);
        rotar4.setBounds(5, 8, 62, 20);
        rotar3.setBounds(70, 8, 62, 20);
        rotar2.setBounds(135, 8, 62, 20);
        rotar1.setBounds(200, 8, 62, 20);
        ciphered.setEditable(false);
        ciphered.setLineWrap(true);
        actual.setLineWrap(true);
        scrollc.setBounds(5, 40, 255, 130);
        scrolla.setBounds(5,180, 255, 130);
        reset.setBounds(100, 320, 70, 25);
        jpanel.add(scrollc);
        jpanel.add(scrolla);
        jpanel.add(rotar1);
        jpanel.add(rotar2);
        jpanel.add(rotar3);
        jpanel.add(rotar4);
        jpanel.add(reset);
        this.addWindowListener( new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            actual.requestFocus();
    }
}); 
    }
    
    public static void main(String [] args){
        new Enigma().setVisible(true);
        
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ciphered.setText("");
                actual.setText("");
                rotar1.setSelectedIndex(0);
                rotar2.setSelectedIndex(0);
                rotar3.setSelectedIndex(0);
                rotar4.setSelectedIndex(0);
                count=0;
            }
        });
        
        actual.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()>=65 && e.getKeyCode()<=91){
                char c = (char)e.getKeyCode();
                if(count==5){
                    //System.out.println(ciphered.getText().length());
                    ciphered.setText(ciphered.getText().concat(" "));
                    count=0;
                }
                ciphered.setText(ciphered.getText().concat(encrypt(c)+""));
                    //encrypt(c);
                count++;
                
                if(rotar1.getSelectedIndex()!=25)
                    rotar1.setSelectedIndex(rotar1.getSelectedIndex()+1);
                else{
                    rotar1.setSelectedIndex(0);
                    if(rotar2.getSelectedIndex()!=25)
                        rotar2.setSelectedIndex(rotar2.getSelectedIndex()+1);
                    else{
                        rotar2.setSelectedIndex(0);
                        if(rotar3.getSelectedIndex()!=25)
                            rotar3.setSelectedIndex(rotar3.getSelectedIndex()+1);
                        else{
                            rotar3.setSelectedIndex(0);
                            if(rotar4.getSelectedIndex()!=25)
                               rotar4.setSelectedIndex(rotar4.getSelectedIndex()+1);
                            else{
                                rotar4.setSelectedIndex(0);
                                }
                            }
                }
                }
                
            }}

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
    }
    
    public static int MoL(int N){
        //int n;
        if(N<0){
            return 26+N;
        }
        else if(N>25){
            return N-26;
        }
        else
            return N;
    }
    
    public static char encrypt(char ch){
        
        int r1 = rotar1.getSelectedIndex();
        int r2 = rotar2.getSelectedIndex();
        int r3 = rotar3.getSelectedIndex();
        int r4 = rotar4.getSelectedIndex();
        
        
        
        int fp = plugboard.indexOf(ch);
        //System.out.println(r1+" "+r2+" "+r3+" "+r4);
        int fr1 = MoL((rotar1s.indexOf((char)(65+fp)))-r1);
        int fr2 = MoL((rotar2s.indexOf((char)(65+fr1)))-r2);
        int fr3 = MoL((rotar3s.indexOf((char)(65+fr2))-r3));
        int fr4 = MoL((rotar4s.indexOf((char)(65+fr3))-r4));
        int r = reflector.indexOf((char)(65+fr4));
        int br4 = ((int)rotar4s.charAt(MoL(r+r4)))-65;
        int br3 = ((int)rotar3s.charAt(MoL(br4+r3)))-65;
        int br2 = ((int)rotar2s.charAt(MoL(br3+r2)))-65;
        int br1 = ((int)rotar1s.charAt(MoL(br2+r1)))-65;
        int bp = ((int)plugboard.charAt(br1))-65;
        //System.out.println(fp+" "+fr1+" "+fr2+" "+fr3+" "+fr4+" "+r+" "+br4+" "+br3+" "+br2+" "+br1+" "+bp);
        return (char)(65+bp);
    }
    
}