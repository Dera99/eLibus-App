/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpustakaan.smk.pgri.pkg1.jakarta;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Atthoriq
 */
public class Siswa_KonfirmasiPeminjaman extends javax.swing.JFrame {
    public ResultSet rs;
    Connection CC = new koneksi().connect();
    public Statement stt;
    public PreparedStatement prst;
    /**
     * Creates new form Siswa_KonfirmasiPeminjaman
     */
    public Siswa_KonfirmasiPeminjaman() {
        initComponents();
    }
    String cnn,id;
    int nis = UserSession.GetUserId();
    String bcd;
     public int waktu = 0;
    public void lamaPinjam(){
        try{
            Statement stat = CC.createStatement();
            ResultSet rb = stat.executeQuery("SELECT * FROM pengaturan LIMIT 1");
            if(rb.next()){
                waktu = rb.getInt("LamaPinjam");
            }
        }catch(Exception e){
        }
    }
     public void pinjam(){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now(); 
            LocalDateTime next = now.plusDays(waktu);
        try {
            Statement stat = CC.createStatement();
            String sql = "SELECT * FROM item WHERE item_Code = '"+ bcd +"' AND 	call_number = '"+ cnn +"' AND NOT (location_id = '3' AND NOT location_id = '4')";
            ResultSet rs = stat.executeQuery(sql);
            if (rs.next())
            {
                stat.executeUpdate("INSERT INTO transaksi(Barcode,id_bliblio,Nis,TanggalPinjam,Tenggat,Status,Keterangan) VALUES('"
                   + bcd+"','"
                   + id+"','"
                   +nis+ "','"
                   +dtf.format(now)+ "','"
                   +dtf.format(next)+ "','1','Dipinjam')");
                   stat.executeUpdate("UPDATE item SET location_Id = '3' WHERE item_Code = '"+ bcd +"' ");
                    JOptionPane.showMessageDialog(null, "Data Peminjaman Dibuat");
            }else{
                JOptionPane.showMessageDialog(null, "No Eksemplar yang Dimasukan Salah");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Siswa_KonfirmasiPeminjamanLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bar = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("No Eksemplar");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 50, 108, 31);

        bar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                barKeyPressed(evt);
            }
        });
        jPanel1.add(bar);
        bar.setBounds(150, 50, 370, 30);

        jButton2.setText("Batal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(320, 130, 100, 30);

        jButton3.setText("Pinjam");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(430, 130, 100, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        bcd = bar.getText();
        pinjam();
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void barKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            bcd = bar.getText();
            pinjam();
            this.dispose();
        }
    }//GEN-LAST:event_barKeyPressed

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
            java.util.logging.Logger.getLogger(Siswa_KonfirmasiPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Siswa_KonfirmasiPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Siswa_KonfirmasiPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Siswa_KonfirmasiPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Siswa_KonfirmasiPeminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bar;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}