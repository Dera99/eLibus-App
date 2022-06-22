/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpustakaan.smk.pgri.pkg1.jakarta;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Atthoriq
 */
public class LandingPage extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    ResultSet rs = null;
    Connection CC = new koneksi().connect();
    PreparedStatement pst = null;
    public Statement stt;
    public LandingPage() {
        initComponents();
        initial();
        getProfile();
    }
    public String formula = "SELECT Judul,image,mst_author.author_name,new_bliblio.call_number FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id ORDER BY IdBliblio DESC ";
    public int from = 0;
    String a,b,c,d,e;
    public String sql,img, cnn,cvr;
    int rows,col,limit;
    public void img(){
        try {
            
             stt = CC.createStatement();
            rs = stt.executeQuery("SELECT image FROM new_bliblio WHERE call_number = '"+cnn+"'");
            if(rs.next()){
                cvr = rs.getString("image");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getProfile(){
        try {
            
             stt = CC.createStatement();
            rs = stt.executeQuery("SELECT * From profile");
            if(rs.next()){
                toLandingPage.setText(rs.getString("Profil"));
                Profil.setText(rs.getString("Profil"));
                Tagline.setText(rs.getString("Tagline"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    String[]cn = {a,b,c,d,e};
    private void initial(){
        try{
         JToggleButton[]img = {img1,img2,img3,img4,img5};
         String[]rsim = null;
         PreparedStatement stmt = CC.prepareStatement(formula+"LIMIT "+ from +", 5",
        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE
            );
         
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        rs.first();
       int rowcount = 0;
            do {
                   
                    rowcount++;
                } while (rs.next());
            rs.first();
           
            int rowindex = 0; // initial rowindex
            // iterate panel default false
                 int panel;
                   for (panel=0;panel<5;panel++){
                       img[rowindex].setVisible(false);
                   }
             //end of iterate panel     
            Object array2D[][] = new Object[rowcount][];
            do {
                Object[] data = {
                    rs.getString("image"),

                };
                 array2D[rowindex] = new Object[numberOfColumns];
                  for (int i = 0; i < numberOfColumns; i++) {
                    array2D[rowindex][i] = rs.getObject(i + 1);
                    }
                  cnn =rs.getString("new_bliblio.call_number");
                  img();
                  img[rowindex].setVisible(true);
//                  InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+cvr+"");
//                  ImageIcon icon = new ImageIcon(ImageIO.read(stream));
//                  Image image = icon.getImage().getScaledInstance(img[rowindex].getWidth(),img[rowindex].getHeight(),Image.SCALE_SMOOTH);
//                  img[rowindex].setIcon(icon);
               File im = new File("src/Uploads/Books/"+cvr+"");
               InputStream stream = new FileInputStream(im);
               ImageIcon icon = new ImageIcon(ImageIO.read(stream));
               Image image = icon.getImage().getScaledInstance(img[rowindex].getWidth(),img[rowindex].getHeight(),Image.SCALE_SMOOTH);
               img[rowindex].setIcon(icon);
                  cn[rowindex] = rs.getString("call_number");
                  
                //System.out.println("array2D[" + rowindex + "] = " + Arrays.toString(array2D[rowindex])); 
             rowindex++;
                } while (rs.next());              
        
        }catch(Exception e){
             e.printStackTrace();
        }
    }
    public String judul;
    public String auth;
    public String Jdl;
    public String Cnn;
    public String Eks;
    public String pnls;
    public String pnrbt;
    public String thtbt;
    public String gmd;
    public String bhs;
    public String g,id;
    public void getData(String cnu){
        try{

               Statement stat = CC.createStatement();
               ResultSet rs = stat.executeQuery("SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id WHERE call_number = '"+ cnu +"'");
               if(rs.next()){
                    ResultSet rsa = stat.executeQuery("SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id INNER JOIN mst_publisher ON new_bliblio.IdPublisher = mst_publisher.publisher_id INNER JOIN gmd ON new_bliblio.IdGMD = gmd.gmd_id INNER JOIN mst_language ON new_bliblio.IdLanguage = mst_language.language_id WHERE new_bliblio.call_number = '"+ rs.getString("new_bliblio.call_number") +"' LIMIT 1");
                    if(rsa.next()){
                        Jdl = rsa.getString("Judul");
                        Cnn = rsa.getString("new_bliblio.call_number");
                        pnrbt = rsa.getString("mst_publisher.publisher_name");
                        pnls = rsa.getString("mst_author.author_name");
                        gmd = rsa.getString("gmd.gmd_name");
                        bhs = rsa.getString("mst_language.language_name");
                        thtbt =  rsa.getString("new_bliblio.PublisherYear");
                        g = rsa.getString("new_bliblio.image");
                        id = rsa.getString("IdBliblio");
                        ResultSet rsb = stat.executeQuery("SELECT COUNT(*) FROM item WHERE call_number = '"+ Cnn +"'AND NOT location_id = '3' AND NOT location_id = '4'");
                        if(rsb.next()){
                            Eks = rsb.getString("COUNT(*)");
                        }
                        System.out.println(bhs);
                    }
               }else{

                   }
           }catch (Exception e){
           e.printStackTrace();
       }
    }
    public void Throw(){
        Detail obj = new Detail();
        obj.dJudul.setText(Jdl);
        obj.dCN.setText(Cnn);
        obj.dSisa.setText(Eks);
        obj.dPenerbit.setText(pnrbt);
        obj.dTahun.setText(thtbt);
        obj.dPenulis.setText(pnls);
        obj.Bhs.setText(bhs);
        obj.dGMD.setText(gmd);
        obj.id = id;
        InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
                  ImageIcon icon;
        try {
            icon = new ImageIcon(ImageIO.read(stream));
             Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        Profil = new javax.swing.JLabel();
        Tagline = new javax.swing.JLabel();
        img2 = new javax.swing.JToggleButton();
        img3 = new javax.swing.JToggleButton();
        img4 = new javax.swing.JToggleButton();
        img5 = new javax.swing.JToggleButton();
        lihatKatalog = new javax.swing.JButton();
        img1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        toLogin = new javax.swing.JLabel();
        toKunjungan = new javax.swing.JLabel();
        toTentang = new javax.swing.JLabel();
        toLandingPage = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        kGradientPanel1.setBackground(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setkGradientFocus(300);
        kGradientPanel1.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setkTransparentControls(false);
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Profil.setFont(new java.awt.Font("Times New Roman", 1, 40)); // NOI18N
        Profil.setText("SMK PGRI 1 Jakarta");
        kGradientPanel1.add(Profil, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, -1, -1));

        Tagline.setFont(new java.awt.Font("Georgia", 0, 22)); // NOI18N
        Tagline.setText("Literasi Mencerdaskan");
        kGradientPanel1.add(Tagline, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 233, 324, -1));

        img2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N
        img2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img2ActionPerformed(evt);
            }
        });
        kGradientPanel1.add(img2, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 442, 140, 220));

        img3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N
        img3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img3ActionPerformed(evt);
            }
        });
        kGradientPanel1.add(img3, new org.netbeans.lib.awtextra.AbsoluteConstraints(351, 442, 140, 220));

        img4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N
        img4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img4ActionPerformed(evt);
            }
        });
        kGradientPanel1.add(img4, new org.netbeans.lib.awtextra.AbsoluteConstraints(509, 442, 140, 220));

        img5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N
        img5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img5ActionPerformed(evt);
            }
        });
        kGradientPanel1.add(img5, new org.netbeans.lib.awtextra.AbsoluteConstraints(667, 442, 140, 220));

        lihatKatalog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Lihat Katalog 1.png"))); // NOI18N
        lihatKatalog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lihatKatalogMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lihatKatalogMouseExited(evt);
            }
        });
        lihatKatalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lihatKatalogActionPerformed(evt);
            }
        });
        kGradientPanel1.add(lihatKatalog, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 297, 242, 53));
        lihatKatalog.setBorderPainted(false);
        lihatKatalog.setContentAreaFilled(false);
        lihatKatalog.setFocusPainted(false);
        lihatKatalog.setOpaque(false);

        img1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N
        img1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img1ActionPerformed(evt);
            }
        });
        kGradientPanel1.add(img1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 442, 140, 220));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        toLogin.setBackground(new java.awt.Color(255, 255, 255));
        toLogin.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toLogin.setText("Login");
        toLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toLoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toLoginMouseExited(evt);
            }
        });

        toKunjungan.setBackground(new java.awt.Color(255, 255, 255));
        toKunjungan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toKunjungan.setText("Kunjungan");
        toKunjungan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toKunjunganMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toKunjunganMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toKunjunganMouseExited(evt);
            }
        });

        toTentang.setBackground(new java.awt.Color(255, 255, 255));
        toTentang.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toTentang.setText("Tentang Perpustakaan");
        toTentang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toTentangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toTentangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toTentangMouseExited(evt);
            }
        });

        toLandingPage.setBackground(new java.awt.Color(255, 255, 255));
        toLandingPage.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toLandingPage.setText("SMK PGRI 1 Jakarta");
        toLandingPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toLandingPageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toLandingPageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toLandingPageMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(toLandingPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(toTentang)
                .addGap(59, 59, 59)
                .addComponent(toKunjungan)
                .addGap(71, 71, 71)
                .addComponent(toLogin)
                .addGap(52, 52, 52))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(toLogin)
                .addComponent(toKunjungan)
                .addComponent(toTentang)
                .addComponent(toLandingPage))
        );

        kGradientPanel1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, 1280, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 40)); // NOI18N
        jLabel4.setText("Perpustakaan");
        kGradientPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 168, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void img5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img5ActionPerformed
        getData(cn[4]);
        Throw();
    }//GEN-LAST:event_img5ActionPerformed

    private void lihatKatalogMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lihatKatalogMouseEntered
        lihatKatalog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Lihat Katalog 2.png")));
    }//GEN-LAST:event_lihatKatalogMouseEntered

    private void lihatKatalogMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lihatKatalogMouseExited
       lihatKatalog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Lihat Katalog 1.png")));
    }//GEN-LAST:event_lihatKatalogMouseExited

    private void lihatKatalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihatKatalogActionPerformed
        Katalog obj = new Katalog();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lihatKatalogActionPerformed

    private void img1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img1ActionPerformed
      getData(cn[0]);
      Throw();
    }//GEN-LAST:event_img1ActionPerformed

    private void toLandingPageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLandingPageMouseEntered
        toLandingPage.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toLandingPageMouseEntered

    private void toLandingPageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLandingPageMouseExited
        toLandingPage.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_toLandingPageMouseExited

    private void toTentangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toTentangMouseEntered
        toTentang.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toTentangMouseEntered

    private void toTentangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toTentangMouseExited
        toTentang.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_toTentangMouseExited

    private void toKunjunganMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKunjunganMouseEntered
        toKunjungan.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toKunjunganMouseEntered

    private void toKunjunganMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKunjunganMouseExited
        toKunjungan.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_toKunjunganMouseExited

    private void toLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLoginMouseEntered
        toLogin.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toLoginMouseEntered

    private void toLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLoginMouseExited
        toLogin.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_toLoginMouseExited

    private void toLandingPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLandingPageMouseClicked
        LandingPage obj = new LandingPage();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toLandingPageMouseClicked

    private void toTentangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toTentangMouseClicked
        TentangPerpus obj = null;
        try {
            obj = new TentangPerpus();
        } catch (IOException ex) {
            Logger.getLogger(LandingPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toTentangMouseClicked

    private void toKunjunganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKunjunganMouseClicked
       Kunjungan obj = new Kunjungan();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toKunjunganMouseClicked

    private void toLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLoginMouseClicked
       Login obj = null;
        try {
            obj = new Login();
        } catch (IOException ex) {
            Logger.getLogger(LandingPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toLoginMouseClicked

    private void img2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img2ActionPerformed
       getData(cn[1]);
        Throw();
    }//GEN-LAST:event_img2ActionPerformed

    private void img3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img3ActionPerformed
       getData(cn[2]);
        Throw();
    }//GEN-LAST:event_img3ActionPerformed

    private void img4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img4ActionPerformed
        getData(cn[3]);
        Throw();
    }//GEN-LAST:event_img4ActionPerformed

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
            java.util.logging.Logger.getLogger(LandingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LandingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LandingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LandingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LandingPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Profil;
    private javax.swing.JLabel Tagline;
    private javax.swing.JToggleButton img1;
    private javax.swing.JToggleButton img2;
    private javax.swing.JToggleButton img3;
    private javax.swing.JToggleButton img4;
    private javax.swing.JToggleButton img5;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JButton lihatKatalog;
    private javax.swing.JLabel toKunjungan;
    private javax.swing.JLabel toLandingPage;
    private javax.swing.JLabel toLogin;
    private javax.swing.JLabel toTentang;
    // End of variables declaration//GEN-END:variables

    private void setBorder(Border createEmptyBorder) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
