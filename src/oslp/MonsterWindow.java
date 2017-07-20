/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oslp;

/**
 *
 * @author Artpicks
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import java.text.NumberFormat;

public class MonsterWindow extends javax.swing.JFrame {

    /** Creates new form MonsterWindow */
    
    private static final int MAX_X = 8; //Max buttons in a row
    private JTextArea log_display;
    private JRadioButton remove_mode;
    private MonsterLog monster_log;
    public MonsterWindow() {
        initComponents();
    }
    
    public void makeDynamicWindow(MonsterLog monster){
        this.setTitle("OSLP - "+monster.getName());
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon(getClass().getResource("/oslp/images/Beer tankard.png"));
        this.setIconImage(img.getImage());
        monster_log = monster;
        GridBagConstraints gbc = new GridBagConstraints();
        int x = 0;
        int y = 0;
        log_display = new JTextArea();
        log_display.setPreferredSize(new Dimension(400,400));
        log_display.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        gbc.gridx = 50;
        gbc.gridy = 0;
        gbc.gridheight = 999;
        gbc.fill = GridBagConstraints.VERTICAL;
        getContentPane().add(log_display, gbc);
        gbc = new GridBagConstraints();
        JButton back_button = new JButton();
        back_button.setText("Back");
        back_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                StartMenu menu = new StartMenu();
                MonsterWindow.this.setVisible(false);
                monster_log.saveMonsterXML();
                menu.setVisible(rootPaneCheckingEnabled);
                MonsterWindow.this.dispose();
            }
        });
        gbc.gridx = MAX_X-1;
        gbc.gridy = 99;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(back_button, gbc);
        JPanel remove_panel = new JPanel();
        remove_panel.setLayout(new java.awt.GridBagLayout());
        remove_panel.setPreferredSize(new Dimension(95, 50));
        remove_mode = new JRadioButton();
        remove_mode.setText("Remove");
        remove_panel.add(remove_mode, new GridBagConstraints());
        gbc.gridy = 98;
        getContentPane().add(remove_panel, gbc);
        JButton inc_killcount = new JButton();
        inc_killcount.setText("Add Kill Count");
        inc_killcount.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!remove_mode.isSelected()){
                    monster_log.addKillcount(1);
                }
                else{
                    monster_log.subKillcount(1);
                }
                displayLog();
            }
        });
        gbc.gridy = 97;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(inc_killcount, gbc);
        ArrayList<ItemDrop> local_drops = monster.getDrops();
        for (ItemDrop local_drop : local_drops) {
            addButton(local_drop, x, y);
            x++;
            if(x >= MAX_X){
                x = 0;
                y++;
            }
        }
        displayLog();
        pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int xi = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int yi = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(xi, yi);
    }
    //Adds button with the item Icon, name and quantity with rairty color code
    //at GridbagLayout(x,y). 
    private void addButton(ItemDrop itemdrop, int x, int y){
        Font myFont = new Font("Helvetica", Font.BOLD, 8);
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridBagLayout());
        panel.setPreferredSize(new Dimension(95,75));
        JLabel itemname = new JLabel(itemdrop.getItem().getName());
        itemname.setFont(myFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(itemname, gbc);
        JLabel quantity;
        JButton button = new JButton();
        System.out.println(itemdrop.getItem().getName());
        button.setIcon(new ImageIcon(getClass().getResource(itemdrop.getItem().getIconPath())));
        button.setPreferredSize(new Dimension(50,50));
        if(itemdrop.getMinDrop() == itemdrop.getMaxDrop()){
            quantity = new JLabel("Quantity: "+Integer.toString(itemdrop.getMinDrop()));
            button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!remove_mode.isSelected()){
                    monster_log.getDrops().get(monster_log.getDrops().indexOf(itemdrop)).addQuantity(itemdrop.getMinDrop());
                }
                else{
                    monster_log.getDrops().get(monster_log.getDrops().indexOf(itemdrop)).subQuantity(itemdrop.getMinDrop());
                }
                displayLog();
            }
            });
        }
        else{
            quantity = new JLabel("Quantity: Varies");
            button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    int quan = Integer.parseInt((String)JOptionPane.showInputDialog("Enter a Quantity: "));
                    if(!remove_mode.isSelected()){
                        monster_log.getDrops().get(monster_log.getDrops().indexOf(itemdrop)).addQuantity(quan);
                    }
                    else{
                        monster_log.getDrops().get(monster_log.getDrops().indexOf(itemdrop)).subQuantity(quan);
                    }
                    displayLog();
                }
                catch(NumberFormatException w){
                }
            }
            });       
        }
        quantity.setOpaque(true);
        quantity.setBackground(getRarityColour(itemdrop.getRarity()));
        quantity.setFont(myFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(quantity, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(button, gbc);
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.NONE;
        getContentPane().add(panel, gbc);
    }
    
    private Color getRarityColour(String rarity){
        String clean_rarity = rarity.replace(" ", "");
        switch (clean_rarity) {
            case "Always":
                return new Color(175, 238, 238);
            case "Common":
                return new Color(86, 225, 86);
            case "Uncommon":
                return new Color(255, 237, 76);
            case "Rare":
                return new Color(255, 134, 60);
            case "Veryrare":
                return new Color(255, 98, 98);
            default:
                return new Color(200, 200, 200);
        }
    }
    
    private void displayLog(){
        log_display.setText("");
        String newline = System.getProperty("line.separator");
        log_display.append(monster_log.getName()+" Loot Log"+newline);
        log_display.append(newline);
        if(monster_log.getKillcount() > 0){
            log_display.append("Kill Count: "+monster_log.getKillcount());
            log_display.append(newline);
        }
        Map<String, ItemDrop> monsterLog = monster_log.getMap();
        long running_total = 0;
        for(String key : monsterLog.keySet()){
            ItemDrop monsterItem = monsterLog.get(key);
            if (monsterItem.getQuantity() > 0) {
                long quantity = monsterItem.getQuantity();
                long price = monsterItem.getItem().getPrice();
                long total = quantity * price;
                running_total = total+running_total;
                String squantity = NumberFormat.getIntegerInstance().format(quantity);
                String stotal =  NumberFormat.getIntegerInstance().format(total);
                log_display.append(squantity+" "+key+" = "+stotal+" gp"+newline);
            }
        }
        String srt = NumberFormat.getIntegerInstance().format(running_total);
        log_display.append(newline);
        log_display.append("Total gp value of drops: "+srt);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        monster_log.saveMonsterXML();
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MonsterWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MonsterWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MonsterWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonsterWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MonsterWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
