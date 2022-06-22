/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpustakaan.smk.pgri.pkg1.jakarta;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author Atthoriq
 */
public class Petugas_PengaturanSistemFoto extends javax.swing.JFrame {

    /**
     * Creates new form TentangPerpus
     */
    
         ResultSet rs = null;
    Connection CC = null;
    PreparedStatement pst = null;
    Statement stt;
    public Petugas_PengaturanSistemFoto() throws IOException {
        initComponents();
        CC = new koneksi().connect();
        getProfile();
        
        }
    public String pro1,pro2;
    public void getProfile() throws IOException{
        try {
            
             stt = CC.createStatement();
            rs = stt.executeQuery("SELECT * From profile");
            if(rs.next()){
                pro1 = rs.getString("foto1");
                pro2 = rs.getString("foto2");
            }
//             InputStream stream1 = getClass().getResourceAsStream("/Uploads/Foto/Perpus1/"+pro1+"");
//             ImageIcon icon1 = new ImageIcon(ImageIO.read(stream1));
//             Image image1 = icon1.getImage().getScaledInstance(imgFoto1.getWidth(),imgFoto1.getHeight(),Image.SCALE_SMOOTH);
//             imgFoto1.setIcon(icon1);
                File im = new File("src/Uploads/foto/Perpus1/"+pro1+"");
               InputStream stream = new FileInputStream(im);
               ImageIcon icon1 = new ImageIcon(ImageIO.read(stream));
               Image image1 = icon1.getImage().getScaledInstance(imgFoto1.getWidth(),imgFoto1.getHeight(),Image.SCALE_SMOOTH);
             imgFoto1.setIcon(icon1);
//             InputStream stream2 = getClass().getResourceAsStream("/Uploads/Foto/Perpus2/"+pro2+"");
//             ImageIcon icon2 = new ImageIcon(ImageIO.read(stream2));
//             Image image2 = icon1.getImage().getScaledInstance(imgFoto2.getWidth(),imgFoto2.getHeight(),Image.SCALE_SMOOTH);
//             imgFoto2.setIcon(icon2);
               File ima = new File("src/Uploads/foto/Perpus2/"+pro2+"");
               InputStream stream2 = new FileInputStream(ima);
               ImageIcon icon2 = new ImageIcon(ImageIO.read(stream2));
               Image image2 = icon2.getImage().getScaledInstance(imgFoto2.getWidth(),imgFoto2.getHeight(),Image.SCALE_SMOOTH);
             imgFoto2.setIcon(icon2);
        } catch (SQLException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         public File f1,destinationFile1,file1;
         public File f2,destinationFile2,file2;
 public void attach1(){
     try{
        JFileChooser imgFileChooser = new JFileChooser();
        imgFileChooser.setDialogTitle("Select Images File");
         FileNameExtensionFilter fnef = new FileNameExtensionFilter("Images File","jpeg","jpg","png");
         imgFileChooser.setFileFilter(fnef);
         imgFileChooser.setAcceptAllFileFilterUsed(false);
        int excelChooser = imgFileChooser.showOpenDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            f1 = imgFileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(f1.toString());
            Image image = icon.getImage().getScaledInstance(imgFoto1.getWidth(),imgFoto1.getHeight(),Image.SCALE_SMOOTH);
            imgFoto1.setIcon(icon);
            
        }
        }catch(Exception e){
             e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Upload");
        }
 }
 public void attach2(){
     try{
        JFileChooser imgFileChooser = new JFileChooser();
        imgFileChooser.setDialogTitle("Select Images File");
         FileNameExtensionFilter fnef = new FileNameExtensionFilter("Images File","jpeg","jpg","png");
         imgFileChooser.setFileFilter(fnef);
         imgFileChooser.setAcceptAllFileFilterUsed(false);
        int excelChooser = imgFileChooser.showOpenDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            f2 = imgFileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(f2.toString());
            Image image = icon.getImage().getScaledInstance(imgFoto2.getWidth(),imgFoto2.getHeight(),Image.SCALE_SMOOTH);
            imgFoto2.setIcon(icon);
            
        }
        }catch(Exception e){
             e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Upload");
        }
 }
 public String foto1 = null;
 public String foto2 = null;
 public void check1() throws IOException{
         //tring foto=null;
            if(f1!=null){
            String filename = f1.getAbsolutePath();
            String newpath = "src/Uploads/Foto/Perpus1/";
            File directory = new File(newpath);
            if(!directory.exists()){
                directory.mkdirs();
            }
            File sourceFile = null;
            File destinationFile = null;
            String extension = filename.substring(filename.lastIndexOf('.')+1);
            sourceFile = new File(filename);
            Date tanggal_update = new Date();
            String tampilan = "yyyyMMddhhmmss";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String tanggal = String.valueOf(fm.format(tanggal_update));
            String resultNew = newpath+"/newImage" + tanggal.toString()+ "." +extension;
            destinationFile = new File(resultNew);
            Files.copy(sourceFile.toPath(), destinationFile.toPath());
           //System.out.println(destinationFile.getName());
            file1 = destinationFile;
            foto1 = destinationFile.getAbsoluteFile().getName();
            }else{
                foto1 = pro1;
            }
            //System.out.println(foto1);
 }
 public void check2() throws IOException{
         //tring foto=null;
            if(f2!=null){
            String filename = f2.getAbsolutePath();
            String newpath = "src/Uploads/Foto/Perpus2/";
            File directory = new File(newpath);
            if(!directory.exists()){
                directory.mkdirs();
            }
            File sourceFile = null;
            File destinationFile = null;
            String extension = filename.substring(filename.lastIndexOf('.')+1);
            sourceFile = new File(filename);
            Date tanggal_update = new Date();
            String tampilan = "yyyyMMddhhmmss";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String tanggal = String.valueOf(fm.format(tanggal_update));
            String resultNew = newpath+"/newImage" + tanggal.toString()+ "." +extension;
            destinationFile = new File(resultNew);
            Files.copy(sourceFile.toPath(), destinationFile.toPath());
           //System.out.println(destinationFile.getName());
            file2 = destinationFile;
            foto2 = destinationFile.getAbsoluteFile().getName();
            }else{
                foto2 = pro2;
            }
            //System.out.println(foto2);
 }
 public void Update() throws IOException{
     check1();
     check2();
     try{
         System.out.println(foto1);
         System.out.println(foto2);
         stt = CC.createStatement();
         String sql ="UPDATE profile SET foto1='"+foto1+"',foto2='"+foto2+"'";
         stt.executeUpdate(sql);
         JOptionPane.showMessageDialog(null, "Foto Berhasil Di Upload!!");
     }catch(Exception e){
         e.printStackTrace();
         JOptionPane.showMessageDialog(null, e);
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

        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        imgFoto2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        imgFoto1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(229, 231, 238));
        jPanel11.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imgFoto2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imgFoto2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setLabelFor(this);
        jLabel1.setText("Ganti Foto Perpustakaan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(229, 231, 238));
        jPanel14.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imgFoto1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imgFoto1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButton1.setText("Pilih Foto");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Pilih Foto");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Upload");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Batal");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(29, 29, 29)
                        .addComponent(jButton3))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        attach1();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        attach2();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
             try {
                 // TODO add your handling code here:
                 Update();
             } catch (IOException ex) {
                 Logger.getLogger(Petugas_PengaturanSistemFoto.class.getName()).log(Level.SEVERE, null, ex);
             }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Petugas_PengaturanSistemFoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Petugas_PengaturanSistemFoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Petugas_PengaturanSistemFoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Petugas_PengaturanSistemFoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Petugas_PengaturanSistemFoto().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Petugas_PengaturanSistemFoto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imgFoto1;
    private javax.swing.JLabel imgFoto2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
}
