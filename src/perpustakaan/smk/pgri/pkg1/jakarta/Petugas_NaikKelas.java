/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpustakaan.smk.pgri.pkg1.jakarta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Atthoriq
 */
public class Petugas_NaikKelas extends javax.swing.JFrame {

    /**
     * Creates new form Petugas_NaikKelas
     */
    public ResultSet rs;
    Connection CC = new koneksi().connect();
    public Statement stt;
     public DefaultTableModel tmdl;
    public PreparedStatement prst;
    public Petugas_NaikKelas() {
        initComponents();
        readCB();
        AsalJurusan.setEnabled(false);
        AsalKelas.setEnabled(false);
        TTingkat.setEnabled(false);
        Tkelas.setEnabled(false);
        TJurusan.setEnabled(false);
        jButton1.setEnabled(false);
    }
    
    public String sql;
    public String asaltingkat;
    public String asalkelas;
    public String asalId;
    
    public void readCB(){
       AsalTingkat.addItem("X");
       AsalTingkat.addItem("XI");
       AsalTingkat.addItem("XII");
       
        try{
           Statement stat = CC.createStatement();
           sql = "SELECT * FROM jurusan";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String jurus = rs.getString("Jurusan");
               AsalJurusan.addItem(jurus);
               TJurusan.addItem(jurus);
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       }
   }
    public int IdKelasAsal;
    public int IdKelasTujuan;
    public void searchKelasAsal(){
        try{
           Statement stat = CC.createStatement();
           sql = "SELECT IdKelas FROM Kelas JOIN Jurusan ON Kelas.IdJurusan = Jurusan.IdJurusan Where TingkatKelas='"+ String.valueOf(AsalTingkat.getSelectedItem()) +"' AND Jurusan.Jurusan = '"+String.valueOf(AsalJurusan.getSelectedItem())+"' AND Kelas.kelas='"+ String.valueOf(AsalKelas.getSelectedItem()) +"'";
           //System.out.println(sql);
           ResultSet rs = stat.executeQuery(sql);
           if(rs.next()){
               IdKelasAsal = rs.getInt("IdKelas");
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       }
    }
    public void searchKelasTujuan(){
        try{
           Statement stat = CC.createStatement();
           sql = "SELECT IdKelas FROM Kelas JOIN Jurusan ON Kelas.IdJurusan = Jurusan.IdJurusan Where TingkatKelas='"+ String.valueOf(TTingkat.getSelectedItem()) +"' AND Jurusan.Jurusan = '"+String.valueOf(TJurusan.getSelectedItem())+"' AND Kelas.kelas='"+ String.valueOf(Tkelas.getSelectedItem()) +"'";
           //System.out.println(sql);
           ResultSet rs = stat.executeQuery(sql);
           if(rs.next()){
               IdKelasTujuan = rs.getInt("IdKelas");
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       }
    }
     public void judul() {
            Object[] judul = {
         "Nis", "Nama", "Kelas"
        };
        tmdl = new DefaultTableModel(null, judul);
        tklas.setModel(tmdl);}
    
    public void Datas() {
        String Kelas;
        try {
            stt = CC.createStatement();
            tmdl.getDataVector().removeAllElements();
            tmdl.fireTableDataChanged();
            rs = stt.executeQuery("SELECT * FROM anggota JOIN Kelas ON anggota.IdKelas = Kelas.IdKelas WHERE anggota.IdKelas = "+IdKelasAsal);
            while(rs.next()){
            Object[] data = {
                rs.getString("Nis"),
                rs.getString("Nama"),
                rs.getString("TingkatKelas")+rs.getString("idJurusan")+rs.getString("Kelas")
                
            };
            tmdl.addRow(data);
            }
            }catch(Exception e){
          e.printStackTrace();
        }
    }
    public void NaikKelas(){
        try{
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now(); 
            LocalDateTime next = now.plusYears(1);
           Statement stat = CC.createStatement();
           stat.executeUpdate("UPDATE Anggota SET IdKelas = '"
                   + IdKelasTujuan+"', Expired = '"+ next +"' WHERE IdKelas = '"+ IdKelasAsal +"'");
           JOptionPane.showMessageDialog(null, "Data Kelas Berhasil diUpdate");
       }catch (Exception e){
       e.printStackTrace();
       }
    }
    public void Lulus(){
         try{
           Statement stat = CC.createStatement();
           stat.executeUpdate("Delete Anggota WHERE IdKelas = '"+ IdKelasAsal +"'");
           JOptionPane.showMessageDialog(null, "Data Kelas Berhasil diUpdate");
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tklas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        TJurusan = new javax.swing.JComboBox<>();
        Tkelas = new javax.swing.JComboBox<>();
        AsalTingkat = new javax.swing.JComboBox<>();
        AsalJurusan = new javax.swing.JComboBox<>();
        AsalKelas = new javax.swing.JComboBox<>();
        TTingkat = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Masukan Kelas yang Akan diubah");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Masukan Kelas tujuan");

        tklas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "#", "NIS", "Nama"
            }
        ));
        jScrollPane1.setViewportView(tklas);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Anggota Kelas Terkait");

        jButton1.setText("Ubah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        TJurusan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TJurusan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "jurusan" }));
        TJurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TJurusanActionPerformed(evt);
            }
        });

        Tkelas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kelas" }));
        Tkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TkelasActionPerformed(evt);
            }
        });

        AsalTingkat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AsalTingkat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tingkat" }));
        AsalTingkat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsalTingkatActionPerformed(evt);
            }
        });

        AsalJurusan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AsalJurusan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "jurusan" }));
        AsalJurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsalJurusanActionPerformed(evt);
            }
        });

        AsalKelas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AsalKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kelas" }));
        AsalKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsalKelasActionPerformed(evt);
            }
        });

        TTingkat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TTingkat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        TTingkat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TTingkatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(88, 88, 88)
                        .addComponent(TTingkat, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Tkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(22, 22, 22)
                        .addComponent(AsalTingkat, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AsalJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AsalKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(AsalTingkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AsalJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AsalKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tkelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TTingkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jButton1)
                .addGap(81, 81, 81))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int opt = JOptionPane.showConfirmDialog(null, "Apakah Data sudah Benar?" , "Update", JOptionPane.YES_NO_OPTION);
        if(opt==0){
            System.out.println(IdKelasAsal);
            System.out.println(IdKelasTujuan);
            if(IdKelasTujuan <= 0){
                Lulus();
            } else {
                NaikKelas();
            }
            this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void AsalTingkatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsalTingkatActionPerformed
        AsalJurusan.setEnabled(true);
    }//GEN-LAST:event_AsalTingkatActionPerformed

    private void AsalJurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsalJurusanActionPerformed
        AsalKelas.removeAllItems();
        AsalKelas.setEnabled(true);
        try{
           Statement stat = CC.createStatement();
           sql = "SELECT * FROM kelas INNER JOIN Jurusan ON kelas.IdJurusan = Jurusan.IdJurusan WHERE TingkatKelas = '"+ String.valueOf(AsalTingkat.getSelectedItem()) +"'AND Jurusan.Jurusan = '"+ String.valueOf(AsalJurusan.getSelectedItem()) +"'";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String kls = rs.getString("kelas.Kelas");
               AsalKelas.addItem(kls);
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       }
    }//GEN-LAST:event_AsalJurusanActionPerformed

    private void AsalKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsalKelasActionPerformed
       searchKelasAsal();
        judul();
       Datas();
        TTingkat.setEnabled(true);
        TTingkat.removeAllItems();
        TTingkat.addItem("X");
        TTingkat.addItem("XI");
        TTingkat.addItem("XII");
        TTingkat.removeItem(String.valueOf(AsalTingkat.getSelectedItem()));
        if(String.valueOf(AsalTingkat.getSelectedItem()) == "XII"){   
            TTingkat.removeAllItems();
            TTingkat.addItem("Luluskan");
        }
        
    }//GEN-LAST:event_AsalKelasActionPerformed

    private void TkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TkelasActionPerformed
        searchKelasTujuan();
        jButton1.setEnabled(true);
    }//GEN-LAST:event_TkelasActionPerformed

    private void TJurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TJurusanActionPerformed
        Tkelas.setEnabled(true);
        Tkelas.removeAllItems();
        try{
           Statement stat = CC.createStatement();
           sql = "SELECT * FROM kelas INNER JOIN Jurusan ON kelas.IdJurusan = Jurusan.IdJurusan WHERE TingkatKelas = '"+ String.valueOf(TTingkat.getSelectedItem()) +"'AND Jurusan.Jurusan = '"+ String.valueOf(TJurusan.getSelectedItem()) +"'";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String kls = rs.getString("kelas.Kelas");
               Tkelas.addItem(kls);
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       }
    }//GEN-LAST:event_TJurusanActionPerformed

    private void TTingkatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TTingkatActionPerformed
        if(String.valueOf(TTingkat.getSelectedItem()) == "Luluskan"){    
            TJurusan.setEnabled(false);
            jButton1.setEnabled(true);
        }else{
            TJurusan.setEnabled(true);
        }
    }//GEN-LAST:event_TTingkatActionPerformed

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
            java.util.logging.Logger.getLogger(Petugas_NaikKelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Petugas_NaikKelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Petugas_NaikKelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Petugas_NaikKelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Petugas_NaikKelas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AsalJurusan;
    private javax.swing.JComboBox<String> AsalKelas;
    private javax.swing.JComboBox<String> AsalTingkat;
    private javax.swing.JComboBox<String> TJurusan;
    private javax.swing.JComboBox<String> TTingkat;
    private javax.swing.JComboBox<String> Tkelas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tklas;
    // End of variables declaration//GEN-END:variables
}
