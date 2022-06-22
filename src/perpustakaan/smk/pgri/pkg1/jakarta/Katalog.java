/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpustakaan.smk.pgri.pkg1.jakarta;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
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

/**
 *
 * @author Atthoriq
 */
public class Katalog extends javax.swing.JFrame {

    /**
     * Creates new form Katalog
     */
    ResultSet rs = null;
    Connection CC = new koneksi().connect();
    PreparedStatement pst = null;
    
    public Statement stt;
    public Katalog() {
        initComponents();
         PanelLog.setVisible(false);
         PanelUmum.setVisible(false);
         tampilNavbar();
        SubUser.setVisible(false);
        SubSirk.setVisible(false);
        initial();
        check(from);
        getProfile();
        kategori.setVisible(false);
        
    }
    
    int from = 0;
    public String formula = "SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id ORDER BY new_bliblio.IdBliblio DESC ";
    public String id;
    int idbl;
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
    
    public String g;
    int UserId = UserSession.GetUserId();
    String UserLogin = UserSession.getUserLogin();
    public void getProfile(){
        try {
            
             stt = CC.createStatement();
            rs = stt.executeQuery("SELECT * From profile");
            if(rs.next()){
                PGRI.setText(rs.getString("Profil"));
                toLandingPage.setText(rs.getString("Profil"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void check(int fc){
       try {
            int Next = fc + 12;
            Statement stt = CC.createStatement();
            rs = stt.executeQuery(formula+"LIMIT "+ Next +", 12");
            if(rs.next()){
                next.setVisible(true);
            }else{
                next.setVisible(false);
            } 
            int Back = fc - 12;
            if(Back < 0){
                back.setVisible(false);
            }else{
                back.setVisible(true);
            }
            
            }catch(Exception e){
          e.printStackTrace();
        }
    }
    public void tampilNavbar(){
            
        if( UserId == 0){
            System.out.println(UserId);
             PanelUmum.setVisible(true);
        }else{
            PanelUmum.setVisible(false);
            PanelLog.setVisible(true);
            toUser.setText(UserLogin);
        }
    }
    public String sql,img, cnn,cvr;
    int rows,col,limit;
    public String rsImg[];
    public void img(){
        try {
            
             stt = CC.createStatement();
            rs = stt.executeQuery("SELECT image FROM new_bliblio WHERE IdBliblio = '"+cnn+"'");
            if(rs.next()){
                cvr = rs.getString("image");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    int  bl1,bl2,bl3,bl4,bl5,bl6,bl7,bl8,bl9,bl10,bl11,bl12;
    int[]bl = {bl1,bl2,bl3,bl4,bl5,bl6,bl7,bl8,bl9,bl10,bl11,bl12};
    private void initial(){
        try{
         JPanel[]buku = {buku1,buku2,buku3,buku4,buku5,buku6,buku7,buku8,buku9,buku10,buku11,buku12};
         JLabel[]Judul = {judul1,judul2,judul3,judul4,judul5,judul6,judul7,judul8,judul9,judul10,judul11,judul12};
         JLabel[]Author = {penulis1,penulis2,penulis3,penulis4,penulis5,penulis6,penulis7,penulis8,penulis9,penulis10,penulis11,penulis12};
         JToggleButton[]img = {toggle1,toggle2,toggle3,toggle4,toggle5,toggle6,toggle7,toggle8,toggle9,toggle10,toggle11,toggle12};
         String[]rsim = null;
         
         PreparedStatement stmt = CC.prepareStatement(formula+"LIMIT "+ from +", 12",
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
                   for (panel=0;panel<12;panel++){
                       buku[panel].setVisible(false);
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
                  buku[rowindex].setVisible(true);
                  Judul[rowindex].setText(rs.getString("Judul"));
                  Author[rowindex].setText(rs.getString("mst_author.author_name"));
                  cnn =rs.getString("new_bliblio.IdBliblio");
                  bl[rowindex] = rs.getInt("new_bliblio.IdBliblio");
                  img();
                  System.out.println(cvr);
//                  InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+cvr+"");
//                  ImageIcon icon = new ImageIcon(ImageIO.read(stream));
//                  Image image = icon.getImage().getScaledInstance(img[rowindex].getWidth(),img[rowindex].getHeight(),Image.SCALE_SMOOTH);
//                  img[rowindex].setIcon(icon);
                  File im = new File("src/Uploads/Books/"+cvr+"");
                    InputStream stream = new FileInputStream(im);
                    ImageIcon icon = new ImageIcon(ImageIO.read(stream));
                    Image image = icon.getImage().getScaledInstance(img[rowindex].getWidth(),img[rowindex].getHeight(),Image.SCALE_SMOOTH);
                    img[rowindex].setIcon(icon);
                //System.out.println("array2D[" + rowindex + "] = " + Arrays.toString(array2D[rowindex])); 
             rowindex++;
                } while (rs.next());              
        
        }catch(Exception e){
             e.printStackTrace();
        }
    }
   
    public void getData(){
        try{

               Statement stat = CC.createStatement();
               ResultSet rs = stat.executeQuery("SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id WHERE new_bliblio.IdBliblio = '"+ idbl +"'");
               if(rs.next()){
                    ResultSet rsa = stat.executeQuery("SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id INNER JOIN mst_publisher ON new_bliblio.IdPublisher = mst_publisher.publisher_id INNER JOIN gmd ON new_bliblio.IdGMD = gmd.gmd_id INNER JOIN mst_language ON new_bliblio.IdLanguage = mst_language.language_id WHERE new_bliblio.IdBliblio = '"+ rs.getString("new_bliblio.IdBliblio") +"' LIMIT 1");
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelUmum = new javax.swing.JPanel();
        toLogin = new javax.swing.JLabel();
        toKunjungan = new javax.swing.JLabel();
        toTentang = new javax.swing.JLabel();
        toLandingPage = new javax.swing.JLabel();
        PanelLog = new javax.swing.JPanel();
        PGRI = new javax.swing.JLabel();
        toSirkulasi = new javax.swing.JLabel();
        toUsulan = new javax.swing.JLabel();
        toBebpus = new javax.swing.JLabel();
        toUser = new javax.swing.JLabel();
        SubSirk = new javax.swing.JPanel();
        toKatalog = new javax.swing.JLabel();
        toPengembalian = new javax.swing.JLabel();
        toDenda = new javax.swing.JLabel();
        toHistori = new javax.swing.JLabel();
        SubUser = new javax.swing.JPanel();
        toProf = new javax.swing.JLabel();
        toNotif = new javax.swing.JLabel();
        toOut = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        buku1 = new javax.swing.JPanel();
        toggle1 = new javax.swing.JToggleButton();
        judul1 = new javax.swing.JLabel();
        penulis1 = new javax.swing.JLabel();
        buku2 = new javax.swing.JPanel();
        toggle2 = new javax.swing.JToggleButton();
        judul2 = new javax.swing.JLabel();
        penulis2 = new javax.swing.JLabel();
        buku4 = new javax.swing.JPanel();
        toggle4 = new javax.swing.JToggleButton();
        judul4 = new javax.swing.JLabel();
        penulis4 = new javax.swing.JLabel();
        buku3 = new javax.swing.JPanel();
        toggle3 = new javax.swing.JToggleButton();
        judul3 = new javax.swing.JLabel();
        penulis3 = new javax.swing.JLabel();
        buku5 = new javax.swing.JPanel();
        toggle5 = new javax.swing.JToggleButton();
        judul5 = new javax.swing.JLabel();
        penulis5 = new javax.swing.JLabel();
        buku6 = new javax.swing.JPanel();
        toggle6 = new javax.swing.JToggleButton();
        judul6 = new javax.swing.JLabel();
        penulis6 = new javax.swing.JLabel();
        buku7 = new javax.swing.JPanel();
        toggle7 = new javax.swing.JToggleButton();
        judul7 = new javax.swing.JLabel();
        penulis7 = new javax.swing.JLabel();
        buku8 = new javax.swing.JPanel();
        toggle8 = new javax.swing.JToggleButton();
        judul8 = new javax.swing.JLabel();
        penulis8 = new javax.swing.JLabel();
        buku9 = new javax.swing.JPanel();
        toggle9 = new javax.swing.JToggleButton();
        judul9 = new javax.swing.JLabel();
        penulis9 = new javax.swing.JLabel();
        buku10 = new javax.swing.JPanel();
        toggle10 = new javax.swing.JToggleButton();
        judul10 = new javax.swing.JLabel();
        penulis10 = new javax.swing.JLabel();
        buku11 = new javax.swing.JPanel();
        toggle11 = new javax.swing.JToggleButton();
        judul11 = new javax.swing.JLabel();
        penulis11 = new javax.swing.JLabel();
        buku12 = new javax.swing.JPanel();
        toggle12 = new javax.swing.JToggleButton();
        judul12 = new javax.swing.JLabel();
        penulis12 = new javax.swing.JLabel();
        next = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        kategori = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });
        jPanel1.setLayout(null);

        PanelUmum.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout PanelUmumLayout = new javax.swing.GroupLayout(PanelUmum);
        PanelUmum.setLayout(PanelUmumLayout);
        PanelUmumLayout.setHorizontalGroup(
            PanelUmumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelUmumLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(toLandingPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 659, Short.MAX_VALUE)
                .addComponent(toTentang)
                .addGap(59, 59, 59)
                .addComponent(toKunjungan)
                .addGap(71, 71, 71)
                .addComponent(toLogin)
                .addGap(52, 52, 52))
        );
        PanelUmumLayout.setVerticalGroup(
            PanelUmumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUmumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(toLogin)
                .addComponent(toKunjungan)
                .addComponent(toTentang)
                .addComponent(toLandingPage))
        );

        jPanel1.add(PanelUmum);
        PanelUmum.setBounds(0, 10, 1280, 20);

        PanelLog.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout PanelLogLayout = new javax.swing.GroupLayout(PanelLog);
        PanelLog.setLayout(PanelLogLayout);
        PanelLogLayout.setHorizontalGroup(
            PanelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLogLayout.createSequentialGroup()
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
                .addGap(48, 48, 48))
        );
        PanelLogLayout.setVerticalGroup(
            PanelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLogLayout.createSequentialGroup()
                .addGroup(PanelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PGRI)
                    .addComponent(toSirkulasi)
                    .addComponent(toUsulan)
                    .addComponent(toBebpus)
                    .addComponent(toUser))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(PanelLog);
        PanelLog.setBounds(0, 11, 1278, 20);

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
                .addContainerGap(31, Short.MAX_VALUE))
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

        jPanel1.add(SubSirk);
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

        jPanel1.add(SubUser);
        SubUser.setBounds(1200, 40, 80, 80);

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextField1MouseEntered(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(900, 70, 340, 32);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Buku Terakhir");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(25, 77, 240, 29);

        buku1.setBackground(new java.awt.Color(255, 255, 255));
        buku1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku1MouseClicked(evt);
            }
        });

        toggle1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul1.setText("Judul Buku");

        penulis1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis1.setText("Penulis");

        javax.swing.GroupLayout buku1Layout = new javax.swing.GroupLayout(buku1);
        buku1.setLayout(buku1Layout);
        buku1Layout.setHorizontalGroup(
            buku1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis1)
                    .addComponent(judul1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku1Layout.setVerticalGroup(
            buku1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku1);
        buku1.setBounds(25, 138, 161, 238);

        buku2.setBackground(new java.awt.Color(255, 255, 255));
        buku2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku2MouseClicked(evt);
            }
        });

        toggle2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul2.setText("Judul Buku");

        penulis2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis2.setText("Penulis");

        javax.swing.GroupLayout buku2Layout = new javax.swing.GroupLayout(buku2);
        buku2.setLayout(buku2Layout);
        buku2Layout.setHorizontalGroup(
            buku2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis2)
                    .addComponent(judul2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku2Layout.setVerticalGroup(
            buku2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku2);
        buku2.setBounds(233, 138, 161, 238);

        buku4.setBackground(new java.awt.Color(255, 255, 255));
        buku4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku4MouseClicked(evt);
            }
        });

        toggle4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul4.setText("Judul Buku");

        penulis4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis4.setText("Penulis");

        javax.swing.GroupLayout buku4Layout = new javax.swing.GroupLayout(buku4);
        buku4.setLayout(buku4Layout);
        buku4Layout.setHorizontalGroup(
            buku4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis4)
                    .addComponent(judul4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku4Layout.setVerticalGroup(
            buku4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle4, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku4);
        buku4.setBounds(668, 138, 161, 238);

        buku3.setBackground(new java.awt.Color(255, 255, 255));
        buku3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku3MouseClicked(evt);
            }
        });

        toggle3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul3.setText("Judul Buku");

        penulis3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis3.setText("Penulis");

        javax.swing.GroupLayout buku3Layout = new javax.swing.GroupLayout(buku3);
        buku3.setLayout(buku3Layout);
        buku3Layout.setHorizontalGroup(
            buku3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis3)
                    .addComponent(judul3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku3Layout.setVerticalGroup(
            buku3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku3);
        buku3.setBounds(449, 138, 161, 238);

        buku5.setBackground(new java.awt.Color(255, 255, 255));
        buku5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku5MouseClicked(evt);
            }
        });

        toggle5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul5.setText("Judul Buku");

        penulis5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis5.setText("Penulis");

        javax.swing.GroupLayout buku5Layout = new javax.swing.GroupLayout(buku5);
        buku5.setLayout(buku5Layout);
        buku5Layout.setHorizontalGroup(
            buku5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(judul5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toggle5, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku5Layout.setVerticalGroup(
            buku5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle5, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku5);
        buku5.setBounds(877, 138, 161, 238);

        buku6.setBackground(new java.awt.Color(255, 255, 255));
        buku6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku6MouseClicked(evt);
            }
        });

        toggle6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul6.setText("Judul Buku");

        penulis6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis6.setText("Penulis");

        javax.swing.GroupLayout buku6Layout = new javax.swing.GroupLayout(buku6);
        buku6.setLayout(buku6Layout);
        buku6Layout.setHorizontalGroup(
            buku6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle6, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis6)
                    .addComponent(judul6, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku6Layout.setVerticalGroup(
            buku6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle6, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku6);
        buku6.setBounds(1087, 138, 161, 238);

        buku7.setBackground(new java.awt.Color(255, 255, 255));
        buku7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku7MouseClicked(evt);
            }
        });

        toggle7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul7.setText("Judul Buku");

        penulis7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis7.setText("Penulis");

        javax.swing.GroupLayout buku7Layout = new javax.swing.GroupLayout(buku7);
        buku7.setLayout(buku7Layout);
        buku7Layout.setHorizontalGroup(
            buku7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle7, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis7)
                    .addComponent(judul7, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku7Layout.setVerticalGroup(
            buku7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle7, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku7);
        buku7.setBounds(25, 408, 161, 238);

        buku8.setBackground(new java.awt.Color(255, 255, 255));
        buku8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku8MouseClicked(evt);
            }
        });

        toggle8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul8.setText("Judul Buku");

        penulis8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis8.setText("Penulis");

        javax.swing.GroupLayout buku8Layout = new javax.swing.GroupLayout(buku8);
        buku8.setLayout(buku8Layout);
        buku8Layout.setHorizontalGroup(
            buku8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis8)
                    .addComponent(judul8, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku8Layout.setVerticalGroup(
            buku8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle8, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku8);
        buku8.setBounds(233, 408, 161, 238);

        buku9.setBackground(new java.awt.Color(255, 255, 255));
        buku9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku9MouseClicked(evt);
            }
        });

        toggle9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul9.setText("Judul Buku");

        penulis9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis9.setText("Penulis");

        javax.swing.GroupLayout buku9Layout = new javax.swing.GroupLayout(buku9);
        buku9.setLayout(buku9Layout);
        buku9Layout.setHorizontalGroup(
            buku9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle9, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis9)
                    .addComponent(judul9, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku9Layout.setVerticalGroup(
            buku9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle9, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku9);
        buku9.setBounds(449, 408, 161, 238);

        buku10.setBackground(new java.awt.Color(255, 255, 255));
        buku10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku10MouseClicked(evt);
            }
        });

        toggle10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul10.setText("Judul Buku");

        penulis10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis10.setText("Penulis");

        javax.swing.GroupLayout buku10Layout = new javax.swing.GroupLayout(buku10);
        buku10.setLayout(buku10Layout);
        buku10Layout.setHorizontalGroup(
            buku10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle10, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis10)
                    .addComponent(judul10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku10Layout.setVerticalGroup(
            buku10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle10, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku10);
        buku10.setBounds(668, 408, 161, 238);

        buku11.setBackground(new java.awt.Color(255, 255, 255));
        buku11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku11MouseClicked(evt);
            }
        });

        toggle11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul11.setText("Judul Buku");

        penulis11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis11.setText("Penulis");

        javax.swing.GroupLayout buku11Layout = new javax.swing.GroupLayout(buku11);
        buku11.setLayout(buku11Layout);
        buku11Layout.setHorizontalGroup(
            buku11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle11, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis11)
                    .addComponent(judul11, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku11Layout.setVerticalGroup(
            buku11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle11, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku11);
        buku11.setBounds(877, 408, 161, 238);

        buku12.setBackground(new java.awt.Color(255, 255, 255));
        buku12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 231, 238), 1, true));
        buku12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buku12MouseClicked(evt);
            }
        });

        toggle12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Cover.png"))); // NOI18N

        judul12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul12.setText("Judul Buku");

        penulis12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        penulis12.setText("Penulis");

        javax.swing.GroupLayout buku12Layout = new javax.swing.GroupLayout(buku12);
        buku12.setLayout(buku12Layout);
        buku12Layout.setHorizontalGroup(
            buku12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buku12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggle12, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penulis12)
                    .addComponent(judul12, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buku12Layout.setVerticalGroup(
            buku12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buku12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggle12, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(penulis12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(buku12);
        buku12.setBounds(1087, 408, 161, 238);

        next.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        next.setText(">");
        next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextMouseExited(evt);
            }
        });
        jPanel1.add(next);
        next.setBounds(640, 680, 12, 17);

        back.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        back.setText("<");
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backMouseExited(evt);
            }
        });
        jPanel1.add(back);
        back.setBounds(620, 680, 12, 17);

        kategori.setBackground(new java.awt.Color(255, 255, 255));
        kategori.setText("Pencarian Kategori");
        kategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                kategoriMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kategoriMouseExited(evt);
            }
        });
        jPanel1.add(kategori);
        kategori.setBounds(1100, 100, 130, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void buku1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku1MouseClicked
        judul = judul1.getText();
        auth = penulis1.getText();
        idbl = bl[0];
        getData();
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
//        InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
//                  ImageIcon icon;
        try {
             File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream));
//            icon = new ImageIcon(ImageIO.read(stream));
//             Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             
            obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku1MouseClicked

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
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
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

    private void toNotifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toNotifMouseClicked
        Siswa_Notifikasi obj = new Siswa_Notifikasi();
        obj.setVisible(true);
    }//GEN-LAST:event_toNotifMouseClicked

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
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
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

    private void toLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLoginMouseClicked
        Login obj = null;
        try {
            obj = new Login();
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toLoginMouseClicked

    private void toLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLoginMouseEntered
        toLogin.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toLoginMouseEntered

    private void toLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLoginMouseExited
        toLogin.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_toLoginMouseExited

    private void toKunjunganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKunjunganMouseClicked
        Kunjungan obj = new Kunjungan();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toKunjunganMouseClicked

    private void toKunjunganMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKunjunganMouseEntered
        toKunjungan.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toKunjunganMouseEntered

    private void toKunjunganMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKunjunganMouseExited
        toKunjungan.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_toKunjunganMouseExited

    private void toTentangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toTentangMouseClicked
        TentangPerpus obj = null;
        try {
            obj = new TentangPerpus();
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toTentangMouseClicked

    private void toTentangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toTentangMouseEntered
        toTentang.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toTentangMouseEntered

    private void toTentangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toTentangMouseExited
        toTentang.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_toTentangMouseExited

    private void toLandingPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLandingPageMouseClicked
        LandingPage obj = new LandingPage();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toLandingPageMouseClicked

    private void toLandingPageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLandingPageMouseEntered
        toLandingPage.setForeground(new java.awt.Color(0,72,181));
    }//GEN-LAST:event_toLandingPageMouseEntered

    private void toLandingPageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLandingPageMouseExited
        toLandingPage.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_toLandingPageMouseExited

    private void buku2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku2MouseClicked
        judul = judul2.getText();
        auth = penulis2.getText();
        idbl = bl[1];
        getData();
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
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
                 // ImageIcon icon;
        try {
//            icon = new ImageIcon(ImageIO.read(stream));
//             Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream));
              obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku2MouseClicked

    private void buku3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku3MouseClicked
        judul = judul3.getText();
        auth = penulis3.getText();
        idbl = bl[2];
        getData();
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
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
                 // ImageIcon icon;
        try {
//            icon = new ImageIcon(ImageIO.read(stream));
//             Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
            File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream)); 
            obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku3MouseClicked

    private void buku4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku4MouseClicked
        judul = judul4.getText();
        auth = penulis4.getText();
        idbl = bl[3];
        getData();
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
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
          //        ImageIcon icon;
        try {
//            icon = new ImageIcon(ImageIO.read(stream));
//             Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
            File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream)); 
            obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku4MouseClicked

    private void buku5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku5MouseClicked
        judul = judul5.getText();
        auth = penulis5.getText();
        idbl = bl[4];
        getData();
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
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
          //        ImageIcon icon;
        try {
            //icon = new ImageIcon(ImageIO.read(stream));
             //Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream));
             obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku5MouseClicked

    private void buku6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku6MouseClicked
        judul = judul6.getText();
        auth = penulis6.getText();
        idbl = bl[5];
        getData();
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
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
          //        ImageIcon icon;
        try {
            //icon = new ImageIcon(ImageIO.read(stream));
             //Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream));
             obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku6MouseClicked

    private void buku7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku7MouseClicked
        judul = judul7.getText();
        auth = penulis7.getText();
        idbl = bl[6];
        getData();
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
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
          //        ImageIcon icon;
        try {
            //icon = new ImageIcon(ImageIO.read(stream));
             //Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream));
             obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku7MouseClicked

    private void buku8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku8MouseClicked
        judul = judul8.getText();
        auth = penulis8.getText();
        idbl = bl[7];
        getData();
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
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
          //        ImageIcon icon;
        try {
            //icon = new ImageIcon(ImageIO.read(stream));
             //Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream));
             obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku8MouseClicked

    private void buku9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku9MouseClicked
        judul = judul9.getText();
        auth = penulis9.getText();
        idbl = bl[8];
        getData();
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
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
          //        ImageIcon icon;
        try {
            //icon = new ImageIcon(ImageIO.read(stream));
             //Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream));
             obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku9MouseClicked

    private void buku10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku10MouseClicked
        judul = judul10.getText();
        auth = penulis10.getText();
        idbl = bl[9];
        getData();
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
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
          //        ImageIcon icon;
        try {
            //icon = new ImageIcon(ImageIO.read(stream));
             //Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream));
             obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku10MouseClicked

    private void buku11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku11MouseClicked
        judul = judul11.getText();
        auth = penulis11.getText();
        idbl = bl[10];
        getData();
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
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
          //        ImageIcon icon;
        try {
            //icon = new ImageIcon(ImageIO.read(stream));
             //Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream));
             obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.setVisible(true);
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku11MouseClicked

    private void buku12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buku12MouseClicked
        judul = judul12.getText();
        auth = penulis12.getText();
        idbl = bl[11];
        getData();
        Detail obj = new Detail();
        obj.dJudul.setText(Jdl);
        obj.dCN.setText(Cnn);
        obj.dSisa.setText(Eks);
        obj.dPenerbit.setText(pnrbt);
        obj.dTahun.setText(thtbt);
        obj.dPenulis.setText(pnls);
        obj.Bhs.setText(bhs);
        obj.dGMD.setText(gmd);
        obj.setVisible(true);
        obj.id = id;
        //InputStream stream = getClass().getResourceAsStream("/Uploads/Books/"+g+"");
          //        ImageIcon icon;
        try {
            //icon = new ImageIcon(ImageIO.read(stream));
             //Image image = icon.getImage().getScaledInstance(obj.img.getWidth(),obj.img.getHeight(),Image.SCALE_SMOOTH);
             File im = new File("src/Uploads/Books/"+g+"");
             InputStream stream = new FileInputStream(im);
             ImageIcon icon = new ImageIcon(ImageIO.read(stream));
             obj.img.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        obj.pack();
        this.dispose();
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buku12MouseClicked

    private void nextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextMouseClicked
        from = from + 12;
        initial();
        check(from);
    }//GEN-LAST:event_nextMouseClicked

    private void nextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextMouseEntered
        next.setForeground(new java.awt.Color(0, 112, 207));
    }//GEN-LAST:event_nextMouseEntered

    private void nextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextMouseExited
        next.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_nextMouseExited

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
        from = from - 12;
        initial();
        check(from);
    }//GEN-LAST:event_backMouseClicked

    private void backMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseEntered
        back.setForeground(new java.awt.Color(0, 112, 207));
    }//GEN-LAST:event_backMouseEntered

    private void backMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseExited
        back.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_backMouseExited

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if(kategori.isSelected()){     
            formula = "SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id INNER JOIN klasifikasi_between ON new_bliblio.klasifikasi BETWEEN klasifikasi_between.from AND klasifikasi_between.to WHERE klasifikasi_between.klasifikasi LIKE '%" + jTextField1.getText() + "%' ORDER BY new_bliblio.IdBliblio DESC "; 
        }else{
            formula = "SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id WHERE Judul LIKE '%"+ jTextField1.getText() +"%' OR call_number LIKE '%" + jTextField1.getText() + "%' OR author_name LIKE '%" + jTextField1.getText() + "%' ORDER BY new_bliblio.IdBliblio DESC "; 
       }initial();
       check(from);
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseEntered
       kategori.setVisible(true);
    }//GEN-LAST:event_jTextField1MouseEntered

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
       kategori.setVisible(false);
    }//GEN-LAST:event_jPanel1MouseEntered

    private void kategoriMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kategoriMouseEntered
        kategori.setVisible(true);
    }//GEN-LAST:event_kategoriMouseEntered

    private void kategoriMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kategoriMouseExited
       kategori.setVisible(false);
    }//GEN-LAST:event_kategoriMouseExited

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
            java.util.logging.Logger.getLogger(Katalog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Katalog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Katalog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Katalog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Katalog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PGRI;
    private javax.swing.JPanel PanelLog;
    private javax.swing.JPanel PanelUmum;
    private javax.swing.JPanel SubSirk;
    private javax.swing.JPanel SubUser;
    private javax.swing.JLabel back;
    private javax.swing.JPanel buku1;
    private javax.swing.JPanel buku10;
    private javax.swing.JPanel buku11;
    private javax.swing.JPanel buku12;
    private javax.swing.JPanel buku2;
    private javax.swing.JPanel buku3;
    private javax.swing.JPanel buku4;
    private javax.swing.JPanel buku5;
    private javax.swing.JPanel buku6;
    private javax.swing.JPanel buku7;
    private javax.swing.JPanel buku8;
    private javax.swing.JPanel buku9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel judul1;
    private javax.swing.JLabel judul10;
    private javax.swing.JLabel judul11;
    private javax.swing.JLabel judul12;
    private javax.swing.JLabel judul2;
    private javax.swing.JLabel judul3;
    private javax.swing.JLabel judul4;
    private javax.swing.JLabel judul5;
    private javax.swing.JLabel judul6;
    private javax.swing.JLabel judul7;
    private javax.swing.JLabel judul8;
    private javax.swing.JLabel judul9;
    private javax.swing.JRadioButton kategori;
    private javax.swing.JLabel next;
    private javax.swing.JLabel penulis1;
    private javax.swing.JLabel penulis10;
    private javax.swing.JLabel penulis11;
    private javax.swing.JLabel penulis12;
    private javax.swing.JLabel penulis2;
    private javax.swing.JLabel penulis3;
    private javax.swing.JLabel penulis4;
    private javax.swing.JLabel penulis5;
    private javax.swing.JLabel penulis6;
    private javax.swing.JLabel penulis7;
    private javax.swing.JLabel penulis8;
    private javax.swing.JLabel penulis9;
    private javax.swing.JLabel toBebpus;
    private javax.swing.JLabel toDenda;
    private javax.swing.JLabel toHistori;
    private javax.swing.JLabel toKatalog;
    private javax.swing.JLabel toKunjungan;
    private javax.swing.JLabel toLandingPage;
    private javax.swing.JLabel toLogin;
    private javax.swing.JLabel toNotif;
    private javax.swing.JLabel toOut;
    private javax.swing.JLabel toPengembalian;
    private javax.swing.JLabel toProf;
    private javax.swing.JLabel toSirkulasi;
    private javax.swing.JLabel toTentang;
    private javax.swing.JLabel toUser;
    private javax.swing.JLabel toUsulan;
    private javax.swing.JToggleButton toggle1;
    private javax.swing.JToggleButton toggle10;
    private javax.swing.JToggleButton toggle11;
    private javax.swing.JToggleButton toggle12;
    private javax.swing.JToggleButton toggle2;
    private javax.swing.JToggleButton toggle3;
    private javax.swing.JToggleButton toggle4;
    private javax.swing.JToggleButton toggle5;
    private javax.swing.JToggleButton toggle6;
    private javax.swing.JToggleButton toggle7;
    private javax.swing.JToggleButton toggle8;
    private javax.swing.JToggleButton toggle9;
    // End of variables declaration//GEN-END:variables
}
