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
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Atthoriq
 */
public class Dialog_Konfirmasi extends javax.swing.JFrame {
    public ResultSet rs;
    Connection CC = new koneksi().connect();
    public Statement stt;
     public DefaultTableModel tmdl;
    public PreparedStatement prst;
    /**
     * Creates new form Dialog_Konfirmasi
     */
    public Dialog_Konfirmasi() {
        initComponents();
    }
    public int waktu = 0;
    public String judul,cnn;
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
    String id;
    public void pinjam(){
        lamaPinjam();
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now(); 
            LocalDateTime next = now.plusDays(waktu);
           Statement stat = CC.createStatement();
           ResultSet rs = stat.executeQuery("SELECT * FROM Anggota WHERE Nis = '"+ Nis.getText() +"'");
           if(rs.next()){
               ResultSet ra = stat.executeQuery("SELECT * FROM item INNER JOIN new_bliblio ON item.call_number = new_bliblio.call_number WHERE item_Code = '"+ noEx.getText() +"' AND NOT (location_id = '3' OR location_id = '4') AND item.call_number = '"+ cnn +"' AND new_bliblio.Judul = '"+ judul +"' ");
               if(ra.next()){
                   id = ra.getString("IdBliblio");
                   ResultSet rb = stat.executeQuery("SELECT COUNT(nis) FROM transaksi WHERE nis = "+ Nis.getText() +" AND NOT Status = 4");
                   if(rb.next()){
                       if(rb.getInt("COUNT(nis)") < jumlah){
                                stat.executeUpdate("INSERT INTO transaksi(Barcode,id_bliblio,Nis,TanggalPinjam,Tenggat,Status,Keterangan) VALUES('"
                                + noEx.getText()+"','"
                                + id+"','"
                                +Nis.getText()+ "','"
                                +dtf.format(now)+ "','"
                                +dtf.format(next)+ "','1','Dipinjam')");
                                stat.executeUpdate("UPDATE item SET location_Id = '3' WHERE item_Code = '"+ noEx.getText() +"' ");
                                JOptionPane.showMessageDialog(null, "Data Peminjaman Dibuat");
                       }else{
                         int opt = JOptionPane.showConfirmDialog(null, "Siswa Terkait Sudah melebihi batas peminjaman Aktif, Tetap Pinjamkan Buku?" , "Update", JOptionPane.YES_NO_OPTION);
                        if(opt == 0){
                                stat.executeUpdate("INSERT INTO transaksi(Barcode,id_bliblio,Nis,TanggalPinjam,Tenggat,Status,Keterangan) VALUES('"
                                + noEx.getText()+"','"
                                + id+"','"
                                +Nis.getText()+ "','"
                                +dtf.format(now)+ "','"
                                +dtf.format(next)+ "','1','Dipinjam')");
                                stat.executeUpdate("UPDATE item SET location_Id = '3' WHERE item_Code = '"+ noEx.getText() +"' ");
                                JOptionPane.showMessageDialog(null, "Data Peminjaman Dibuat");
                
                        }
                   }
                   }
                   this.dispose();
               }else{
                   JOptionPane.showMessageDialog(null, "Data Eksemplar Tidak Ditemukan Atau tidak sesuai dengan Buku yang dipilih");
               }
           }else{
                   JOptionPane.showMessageDialog(null, "Data Anggota Tidak Ditemukan");
               }
       }catch (Exception e){
       e.printStackTrace();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Nis = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        noEx = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Nis Anggota");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(20, 50, 96, 31);

        Nis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(Nis);
        Nis.setBounds(150, 50, 370, 28);

        jButton2.setText("Batal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(310, 170, 100, 30);

        jButton3.setText("Pinjam");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3);
        jButton3.setBounds(420, 170, 100, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("No Eksemplar");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 100, 108, 31);

        noEx.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        noEx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                noExKeyPressed(evt);
            }
        });
        jPanel2.add(noEx);
        noEx.setBounds(150, 100, 370, 28);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        rule();
        pinjam();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void noExKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noExKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
           rule();
           pinjam();
       }
    }//GEN-LAST:event_noExKeyPressed

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
            java.util.logging.Logger.getLogger(Dialog_Konfirmasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dialog_Konfirmasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dialog_Konfirmasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dialog_Konfirmasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dialog_Konfirmasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Nis;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField noEx;
    // End of variables declaration//GEN-END:variables
}
