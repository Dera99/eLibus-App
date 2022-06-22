/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpustakaan.smk.pgri.pkg1.jakarta;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Atthoriq
 */
public class Siswa_Home extends javax.swing.JFrame {
     ResultSet rs = null;
    Connection CC = null;
    PreparedStatement pst = null;
    public Statement stt;
    public Siswa_Home() {
        initComponents();
        SubUser.setVisible(false);
        SubSirk.setVisible(false);
         CC = new koneksi().connect();
        userLogin();
        hakakses();
        no.setVisible(false);
        jumlahnotif();
         initial();
         getProfile();
        
    }
    private void userLogin(){
    toUser.setText(UserSession.getUserLogin());
    }
    int UserId = UserSession.GetUserId();
    public void getProfile(){
        try {
            
             stt = CC.createStatement();
            rs = stt.executeQuery("SELECT * From profile");
            if(rs.next()){
                PGRI.setText(rs.getString("Profil"));
                Profil.setText(rs.getString("Profil"));
                Tagline.setText(rs.getString("Tagline"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     private void hakakses() {
    String user=toUser.getText();
        if(user.equals(3) || user.equals(3)){
       //Master
        //DataBuku.setEnabled(true);
   
     
      
        }
    
        }
     public String formula = "SELECT Judul,image,mst_author.author_name,new_bliblio.call_number FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id ORDER BY IdBliblio DESC ";
    public int from = 0;
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
    String a,b,c,d,e;
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
                  InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+cvr+"");
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
     public void jumlahnotif(){
         try {
            ;
            Statement stt = CC.createStatement();
            rs = stt.executeQuery("SELECT COUNT(*) FROM notifikasi WHERE Nis = '"+ UserId +"' AND Status = 1");
            if(rs.next()){
                if(rs.getInt("COUNT(*)") > 0){
                    no.setVisible(true);
                    no.setText("<html><B>(" +  rs.getInt("COUNT(*)") + ")</B></html>");
                }else{
                    no.setVisible(false);
                    
                }
            }
            
            }catch(Exception e){
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

        kGradientPanel2 = new keeptoo.KGradientPanel();
        Profil = new javax.swing.JLabel();
        Tagline = new javax.swing.JLabel();
        img2 = new javax.swing.JToggleButton();
        img3 = new javax.swing.JToggleButton();
        img4 = new javax.swing.JToggleButton();
        img5 = new javax.swing.JToggleButton();
        img1 = new javax.swing.JToggleButton();
        lihatKatalog2 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        PGRI = new javax.swing.JLabel();
        toSirkulasi = new javax.swing.JLabel();
        toUsulan = new javax.swing.JLabel();
        toBebpus = new javax.swing.JLabel();
        toUser = new javax.swing.JLabel();
        no = new javax.swing.JLabel();
        SubSirk = new javax.swing.JPanel();
        toKatalog = new javax.swing.JLabel();
        toPengembalian = new javax.swing.JLabel();
        toDenda = new javax.swing.JLabel();
        toHistori = new javax.swing.JLabel();
        SubUser = new javax.swing.JPanel();
        toProf = new javax.swing.JLabel();
        toNotif = new javax.swing.JLabel();
        toOut = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        kGradientPanel2.setBackground(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setkGradientFocus(300);
        kGradientPanel2.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setkTransparentControls(false);
        kGradientPanel2.setLayout(null);

        Profil.setFont(new java.awt.Font("Times New Roman", 1, 40)); // NOI18N
        Profil.setText("Perpustakaan SMK PGRI 1 Jakarta ");
        kGradientPanel2.add(Profil);
        Profil.setBounds(35, 163, 633, 47);

        Tagline.setFont(new java.awt.Font("Georgia", 0, 22)); // NOI18N
        Tagline.setText("Literasi Mencerdaskan");
        kGradientPanel2.add(Tagline);
        Tagline.setBounds(35, 228, 280, 26);

        img2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N
        img2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img2ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(img2);
        img2.setBounds(193, 442, 140, 220);

        img3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N
        img3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img3ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(img3);
        img3.setBounds(351, 442, 140, 220);

        img4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N
        img4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img4ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(img4);
        img4.setBounds(509, 442, 140, 220);

        img5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N
        img5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img5ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(img5);
        img5.setBounds(667, 442, 140, 220);

        img1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N
        img1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img1ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(img1);
        img1.setBounds(35, 442, 140, 220);

        lihatKatalog2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Lihat Katalog 1.png"))); // NOI18N
        lihatKatalog2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lihatKatalog2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lihatKatalog2MouseExited(evt);
            }
        });
        lihatKatalog2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lihatKatalog2ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(lihatKatalog2);
        lihatKatalog2.setBounds(30, 290, 240, 60);
        lihatKatalog2.setBorderPainted(false);
        lihatKatalog2.setContentAreaFilled(false);
        lihatKatalog2.setFocusPainted(false);
        lihatKatalog2.setOpaque(false);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        PGRI.setBackground(new java.awt.Color(255, 255, 255));
        PGRI.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        PGRI.setText("SMK PGRI 1 Jakarta");
        PGRI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PGRIMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PGRIMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PGRIMouseExited(evt);
            }
        });

        toSirkulasi.setBackground(new java.awt.Color(255, 255, 255));
        toSirkulasi.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toSirkulasi.setText("Sirkulasi");
        toSirkulasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toSirkulasiMouseEntered(evt);
            }
        });

        toUsulan.setBackground(new java.awt.Color(255, 255, 255));
        toUsulan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toUsulan.setText("Usulan");
        toUsulan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toUsulanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toUsulanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toUsulanMouseExited(evt);
            }
        });

        toBebpus.setBackground(new java.awt.Color(255, 255, 255));
        toBebpus.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toBebpus.setText("Bebas Pustaka");
        toBebpus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toBebpusMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toBebpusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toBebpusMouseExited(evt);
            }
        });

        toUser.setBackground(new java.awt.Color(255, 255, 255));
        toUser.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toUser.setText("User");
        toUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toUserMouseEntered(evt);
            }
        });

        no.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        no.setText("1");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(PGRI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 653, Short.MAX_VALUE)
                .addComponent(toSirkulasi)
                .addGap(75, 75, 75)
                .addComponent(toUsulan)
                .addGap(50, 50, 50)
                .addComponent(toBebpus)
                .addGap(45, 45, 45)
                .addComponent(toUser)
                .addGap(17, 17, 17)
                .addComponent(no, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PGRI)
                    .addComponent(toSirkulasi)
                    .addComponent(toUsulan)
                    .addComponent(toBebpus)
                    .addComponent(toUser)
                    .addComponent(no, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        kGradientPanel2.add(jPanel14);
        jPanel14.setBounds(0, 11, 1278, 20);

        SubSirk.setBackground(new java.awt.Color(255, 255, 255));
        SubSirk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SubSirkMouseExited(evt);
            }
        });

        toKatalog.setBackground(new java.awt.Color(255, 255, 255));
        toKatalog.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toKatalog.setText("Katalog");
        toKatalog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toKatalogMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toKatalogMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toKatalogMouseExited(evt);
            }
        });

        toPengembalian.setBackground(new java.awt.Color(255, 255, 255));
        toPengembalian.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toPengembalian.setText("Pengembalian");
        toPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toPengembalianMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toPengembalianMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toPengembalianMouseExited(evt);
            }
        });

        toDenda.setBackground(new java.awt.Color(255, 255, 255));
        toDenda.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toDenda.setText("Denda");
        toDenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDendaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDendaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toDendaMouseExited(evt);
            }
        });

        toHistori.setBackground(new java.awt.Color(255, 255, 255));
        toHistori.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toHistori.setText("Histori");
        toHistori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toHistoriMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toHistoriMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toHistoriMouseExited(evt);
            }
        });

        javax.swing.GroupLayout SubSirkLayout = new javax.swing.GroupLayout(SubSirk);
        SubSirk.setLayout(SubSirkLayout);
        SubSirkLayout.setHorizontalGroup(
            SubSirkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubSirkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SubSirkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toKatalog)
                    .addComponent(toPengembalian)
                    .addComponent(toDenda)
                    .addComponent(toHistori))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        SubSirkLayout.setVerticalGroup(
            SubSirkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubSirkLayout.createSequentialGroup()
                .addComponent(toKatalog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toPengembalian)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toDenda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toHistori)
                .addGap(0, 32, Short.MAX_VALUE))
        );

        kGradientPanel2.add(SubSirk);
        SubSirk.setBounds(830, 40, 140, 130);

        SubUser.setBackground(new java.awt.Color(255, 255, 255));
        SubUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SubUserMouseExited(evt);
            }
        });

        toProf.setBackground(new java.awt.Color(255, 255, 255));
        toProf.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toProf.setText("Profil");
        toProf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toProfMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toProfMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toProfMouseExited(evt);
            }
        });

        toNotif.setBackground(new java.awt.Color(255, 255, 255));
        toNotif.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toNotif.setText("Notifkasi");
        toNotif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toNotifMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toNotifMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toNotifMouseExited(evt);
            }
        });

        toOut.setBackground(new java.awt.Color(255, 255, 255));
        toOut.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        toOut.setText("Log Out");
        toOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toOutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toOutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toOutMouseExited(evt);
            }
        });

        javax.swing.GroupLayout SubUserLayout = new javax.swing.GroupLayout(SubUser);
        SubUser.setLayout(SubUserLayout);
        SubUserLayout.setHorizontalGroup(
            SubUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SubUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toProf)
                    .addComponent(toNotif)
                    .addComponent(toOut))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SubUserLayout.setVerticalGroup(
            SubUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubUserLayout.createSequentialGroup()
                .addComponent(toProf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toNotif)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toOut)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        kGradientPanel2.add(SubUser);
        SubUser.setBounds(1200, 40, 80, 80);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1282, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void img5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img5ActionPerformed
        getData(cn[4]);
       Throw();
    }//GEN-LAST:event_img5ActionPerformed

    private void lihatKatalog2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lihatKatalog2MouseEntered
        lihatKatalog2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Lihat Katalog 2.png")));
    }//GEN-LAST:event_lihatKatalog2MouseEntered

    private void lihatKatalog2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lihatKatalog2MouseExited
        lihatKatalog2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Lihat Katalog 1.png")));
    }//GEN-LAST:event_lihatKatalog2MouseExited

    private void PGRIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PGRIMouseClicked
        Siswa_Home obj = new Siswa_Home();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_PGRIMouseClicked

    private void PGRIMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PGRIMouseEntered
        PGRI.setForeground(new java.awt.Color(0,72,181));
        SubUser.setVisible(false);
        SubSirk.setVisible(false);
    }//GEN-LAST:event_PGRIMouseEntered

    private void PGRIMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PGRIMouseExited
        toBebpus.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_PGRIMouseExited

    private void toSirkulasiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toSirkulasiMouseEntered
        SubSirk.setVisible(true);
        SubUser.setVisible(false);
    }//GEN-LAST:event_toSirkulasiMouseEntered

    private void toUsulanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toUsulanMouseClicked
        Siswa_Usulan_Buku obj = null;
         try {
             obj = new Siswa_Usulan_Buku();
         } catch (IOException ex) {
             Logger.getLogger(Siswa_Home.class.getName()).log(Level.SEVERE, null, ex);
         }
        obj.setVisible(true);
    }//GEN-LAST:event_toUsulanMouseClicked

    private void toUsulanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toUsulanMouseEntered
        toUsulan.setForeground(new java.awt.Color(0,72,181));
        SubUser.setVisible(false);
        SubSirk.setVisible(false);
    }//GEN-LAST:event_toUsulanMouseEntered

    private void toUsulanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toUsulanMouseExited
        toUsulan.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_toUsulanMouseExited

    private void toBebpusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toBebpusMouseEntered
        toBebpus.setForeground(new java.awt.Color(0,72,181));
        SubUser.setVisible(false);
        SubSirk.setVisible(false);
    }//GEN-LAST:event_toBebpusMouseEntered

    private void toBebpusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toBebpusMouseExited
        toBebpus.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_toBebpusMouseExited

    private void toUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toUserMouseEntered
        SubUser.setVisible(true);
        SubSirk.setVisible(false);
    }//GEN-LAST:event_toUserMouseEntered

    private void toKatalogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKatalogMouseClicked
        Katalog obj = new Katalog();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toKatalogMouseClicked

    private void toKatalogMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKatalogMouseEntered
        toKatalog.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toKatalogMouseEntered

    private void toKatalogMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKatalogMouseExited
        toKatalog.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_toKatalogMouseExited

    private void toPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toPengembalianMouseClicked
        Siswa_PeminjamanBerjalan obj = new Siswa_PeminjamanBerjalan();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toPengembalianMouseClicked

    private void toPengembalianMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toPengembalianMouseEntered
        toPengembalian.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toPengembalianMouseEntered

    private void toPengembalianMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toPengembalianMouseExited
        toPengembalian.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_toPengembalianMouseExited

    private void toDendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDendaMouseClicked
        Siswa_Denda obj = new Siswa_Denda();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDendaMouseClicked

    private void toDendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDendaMouseEntered
        toDenda.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toDendaMouseEntered

    private void toDendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDendaMouseExited
        toDenda.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_toDendaMouseExited

    private void toHistoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toHistoriMouseClicked
        Siswa_HistoriPeminjaman obj = new Siswa_HistoriPeminjaman();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toHistoriMouseClicked

    private void toHistoriMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toHistoriMouseEntered
        toHistori.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toHistoriMouseEntered

    private void toHistoriMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toHistoriMouseExited
        toHistori.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_toHistoriMouseExited

    private void SubSirkMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SubSirkMouseExited

    }//GEN-LAST:event_SubSirkMouseExited

    private void toProfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toProfMouseClicked
        Siswa_Profil obj = new Siswa_Profil();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toProfMouseClicked

    private void toProfMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toProfMouseEntered
        toProf.setForeground(new java.awt.Color(0,72,181));       // TODO add your handling code here:
    }//GEN-LAST:event_toProfMouseEntered

    private void toProfMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toProfMouseExited
        toProf.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_toProfMouseExited

    private void toNotifMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toNotifMouseEntered
        toNotif.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toNotifMouseEntered

    private void toNotifMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toNotifMouseExited
        toNotif.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_toNotifMouseExited

    private void toOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toOutMouseClicked
        Login obj = null;
         try {
             obj = new Login();
         } catch (IOException ex) {
             Logger.getLogger(Siswa_Home.class.getName()).log(Level.SEVERE, null, ex);
         }
        UserSession.setUserLogin(null);
        UserSession.setUserId(0);
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toOutMouseClicked

    private void toOutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toOutMouseEntered
        toOut.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toOutMouseEntered

    private void toOutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toOutMouseExited
        toOut.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_toOutMouseExited

    private void SubUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SubUserMouseExited

    }//GEN-LAST:event_SubUserMouseExited

    private void lihatKatalog2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihatKatalog2ActionPerformed
         Katalog obj = new Katalog();
       obj.setVisible(true);
       this.dispose();

    }//GEN-LAST:event_lihatKatalog2ActionPerformed

    private void toBebpusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toBebpusMouseClicked
         try {
            Statement stat = CC.createStatement();
            ResultSet rs = stat.executeQuery("SELECT TingkatKelas from Kelas INNER JOIN Anggota ON Anggota.IdKelas = Kelas.IdKelas WHERE Anggota.Nis = '"+ UserId +"'");
            if(rs.next()){
                if(rs.getString("TingkatKelas").equalsIgnoreCase("XII")){
                    ResultSet rst = stat.executeQuery("SELECT COUNT(*) FROM transaksi WHERE Nis = '"+ UserId +"'  AND status = '1' OR status = '2' OR status = '3'");
                    if(rst.next()){
                        int num = rst.getInt("COUNT(*)");
                        if(num <= 0){
                            ResultSet rsj = stat.executeQuery("SELECT COUNT(*) FROM Denda INNER JOIN transaksi ON Denda.IdTransaksi = transaksi.IdTransaksi WHERE Nis = '"+ UserId +"'  AND Denda.status = '1' OR Denda.status = '2'");
                            if(rsj.next()){
                                int numlg = rsj.getInt("COUNT(*)");
                                if(numlg <= 0){
                                    Siswa_BebasPustaka obj = new Siswa_BebasPustaka();
                                    obj.setVisible(true);
                                }else{
                                    JOptionPane.showMessageDialog(null, "Anda Masih memiliki Transaksi Atau Denda yang belum dipenuhi, Silahkan Penuhi Tanggungan Anda");
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Anda Masih memiliki Transaksi Atau Denda yang belum dipenuhi, Silahkan Penuhi Tanggungan Anda");
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Menu Ini Diperuntukan Untuk kelas XII");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Siswa_Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toBebpusMouseClicked

    private void toNotifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toNotifMouseClicked
        Siswa_Notifikasi obj = new Siswa_Notifikasi();
        obj.setVisible(true);
    }//GEN-LAST:event_toNotifMouseClicked

    private void img1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img1ActionPerformed
       getData(cn[0]);
       Throw();
    }//GEN-LAST:event_img1ActionPerformed

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
            java.util.logging.Logger.getLogger(Siswa_Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Siswa_Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Siswa_Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Siswa_Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Siswa_Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PGRI;
    private javax.swing.JLabel Profil;
    private javax.swing.JPanel SubSirk;
    private javax.swing.JPanel SubUser;
    private javax.swing.JLabel Tagline;
    private javax.swing.JToggleButton img1;
    private javax.swing.JToggleButton img2;
    private javax.swing.JToggleButton img3;
    private javax.swing.JToggleButton img4;
    private javax.swing.JToggleButton img5;
    private javax.swing.JPanel jPanel14;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JButton lihatKatalog2;
    private javax.swing.JLabel no;
    private javax.swing.JLabel toBebpus;
    private javax.swing.JLabel toDenda;
    private javax.swing.JLabel toHistori;
    private javax.swing.JLabel toKatalog;
    private javax.swing.JLabel toNotif;
    private javax.swing.JLabel toOut;
    private javax.swing.JLabel toPengembalian;
    private javax.swing.JLabel toProf;
    private javax.swing.JLabel toSirkulasi;
    private javax.swing.JLabel toUser;
    private javax.swing.JLabel toUsulan;
    // End of variables declaration//GEN-END:variables
}
