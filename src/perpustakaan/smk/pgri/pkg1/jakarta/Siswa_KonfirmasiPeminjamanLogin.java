/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpustakaan.smk.pgri.pkg1.jakarta;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
/**
 *
 * @author Atthoriq
 */
public class Siswa_KonfirmasiPeminjamanLogin extends javax.swing.JFrame {
    public ResultSet rs;
    Connection CC = new koneksi().connect();
    public Statement stt;
    public PreparedStatement prst;
    /**
     * Creates new form Siswa_KonfirmasiPeminjamanLogin
     */
    public Siswa_KonfirmasiPeminjamanLogin() {
        initComponents();
        lamaPinjam();
    }
    String cnn,id;
    int nis;
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
    public void cek(){
        try {
            Statement stat = CC.createStatement();
            String sql = "SELECT * FROM user INNER JOIN anggota ON user.Nis = Anggota.Nis WHERE Username = '"+NIS.getText()+
            "' and Password = '"+PASS.getText()+"' AND Role='3'";
            ResultSet rs = stat.executeQuery(sql);
            if (rs.next())
            {
                nis = rs.getInt("Anggota.Nis");
            }else{
                JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Siswa_KonfirmasiPeminjamanLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    int jumlah;
    public void rule(){
        try {
            
             stt = CC.createStatement();
            rs = stt.executeQuery("SELECT * From pengaturan");
            if(rs.next()){
                jumlah = rs.getInt("MaxPinjam");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
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
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        PASS = new javax.swing.JPasswordField();
        NIS = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        IdEx = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jButton2.setText("Batal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(310, 260, 100, 30);

        jButton3.setText("Pinjam");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(420, 260, 100, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Password");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 70, 74, 31);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("NIS");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 10, 29, 31);

        PASS.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(PASS);
        PASS.setBounds(150, 70, 370, 30);

        NIS.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jPanel1.add(NIS);
        NIS.setBounds(150, 10, 370, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("No Eksemplar");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 130, 108, 31);

        IdEx.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        IdEx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IdExKeyPressed(evt);
            }
        });
        jPanel1.add(IdEx);
        IdEx.setBounds(150, 130, 370, 30);

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
            rule();
             cek();
        if(nis>0){
             try {
            stt = CC.createStatement();
            rs = stt.executeQuery("SELECT COUNT(nis) FROM transaksi WHERE nis = "+ nis +" AND NOT Status = 4");
            if(rs.next()){
            if(rs.getInt("COUNT(nis)") < jumlah){
                bcd = IdEx.getText();
                pinjam();
            }else{
                JOptionPane.showMessageDialog(null, "Anda Sudah Melebihi Batas Meminjam, Silahkan Datang kembali setelah mengembalikan Buku");
                System.out.print("Julah");
            }}
             } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
       
        
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void IdExKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IdExKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            rule();
             cek();
        if(nis>0){
             try {
            stt = CC.createStatement();
            rs = stt.executeQuery("SELECT COUNT(nis) FROM transaksi WHERE nis = "+ nis +" AND NOT Status = 4");
            if(rs.next()){
            if(rs.getInt("COUNT(nis)") < jumlah){
                bcd = IdEx.getText();
                pinjam();
            }else{
                JOptionPane.showMessageDialog(null, "Anda Sudah Melebihi Batas Meminjam, Silahkan Datang kembali setelah mengembalikan Buku");
                System.out.print("Julah");
            }}
             } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
        }
    }//GEN-LAST:event_IdExKeyPressed

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
            java.util.logging.Logger.getLogger(Siswa_KonfirmasiPeminjamanLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Siswa_KonfirmasiPeminjamanLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Siswa_KonfirmasiPeminjamanLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Siswa_KonfirmasiPeminjamanLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Siswa_KonfirmasiPeminjamanLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IdEx;
    private javax.swing.JTextField NIS;
    private javax.swing.JPasswordField PASS;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}