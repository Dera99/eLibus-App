/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpustakaan.smk.pgri.pkg1.jakarta;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.ObjectInputFilter.Config;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Atthoriq
 */
public class Siswa_Profil extends javax.swing.JFrame {

    /**
     * Creates new form Siswa_Profil
     */
    ResultSet rs = null;
    Connection CC = null;
    PreparedStatement pst = null;
    Statement stt;
    public Siswa_Profil() {
        initComponents();
        CC = new koneksi().connect();
        SubSirk.setVisible(false);
        SubUser.setVisible(false);
        userLogin();
        UserId();
        no.setVisible(false);
        jumlahnotif();
        getProfile();
    }
    public void getProfile(){
        try {
            
             stt = CC.createStatement();
            rs = stt.executeQuery("SELECT * From profile");
            if(rs.next()){
                PGRI.setText(rs.getString("Profil"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Katalog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     private void userLogin(){
        toUser.setText(UserSession.getUserLogin());
    }
      int UserId = UserSession.GetUserId();
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
       public String pro;
     private void UserId(){
        //toUser.setText(UserSession.getUserLogin());
        try {
        Statement stat = CC.createStatement();
          
           String sql = "SELECT anggota.Nama,anggota.Nis,anggota.Alamat,kelas.TingkatKelas,kelas.IdJurusan,kelas.kelas,anggota.TTL,user.Username,anggota.email,anggota.NoHp,anggota.Expired,anggota.Profiles \n" +
"FROM user INNER JOIN anggota ON user.Nis=anggota.Nis \n" +
"INNER JOIN kelas ON anggota.IdKelas=Kelas.IdKelas \n" +
"INNER JOIN jurusan ON kelas.IdJurusan=jurusan.IdJurusan WHERE anggota.Nis='"+UserId+"'";
           ResultSet rs = stat.executeQuery(sql);
           if (rs.next())
            {
                
                String Nama = rs.getString("anggota.Nama");
                String Nis = rs.getString("anggota.Nis");
                String Alamat = rs.getString("anggota.Alamat");
                String tk = rs.getString("kelas.TingkatKelas");
                String IdJurus = rs.getString("kelas.IdJurusan");
                String Kelas = rs.getString("kelas.kelas");
                String TTL = rs.getString("anggota.TTL");
                String user = rs.getString("user.Username");
                String Email = rs.getString("anggota.email");
                String NoHp = rs.getString("anggota.NoHp");
                String expire = rs.getString("anggota.Expired");
                pro = rs.getString("anggota.Profiles");
                //System.out.println(pro);
//                InputStream stream = getClass().getResourceAsStream("/Uploads/Profiles/"+pro+"");
//                ImageIcon icon = new ImageIcon(ImageIO.read(stream));
//                Image image = icon.getImage().getScaledInstance(img.getWidth(),img.getHeight(),Image.SCALE_SMOOTH);
                nama.setText(Nama);
                nis.setText(Nis);
                alamat.setText(Alamat);
                kelas.setText(tk+IdJurus+Kelas);
                ttl.setText(TTL);
                username.setText(user);
                email.setText(Email);
                nohp.setText(NoHp);
                tgl.setText(expire);    
            }
                File im = new File("src/Uploads/Profiles/"+pro+"");
               InputStream stream = new FileInputStream(im);
               ImageIcon icon = new ImageIcon(ImageIO.read(stream));
               Image image = icon.getImage().getScaledInstance(img.getWidth(),img.getHeight(),Image.SCALE_SMOOTH);
               img.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }
//     public String img,file;
//     private void attach(){
//        JFileChooser chooser = new JFileChooser();
//        chooser.showOpenDialog(null);
//        File f = chooser.getSelectedFile();
//        file = f.getAbsolutePath();
//        System.out.println(f.getAbsolutePath());
//        Image getAbsolutePath = null;
//        ImageIcon icon = new ImageIcon(file);
//        Image image = icon.getImage().getScaledInstance(img.getWidth(),img.getHeight(),Image.SCALE_SMOOTH);
//        img.setIcon(icon);
//    }
     public File f,destinationFile,file;
 public void attach(){
     try{
        JFileChooser imgFileChooser = new JFileChooser();
        imgFileChooser.setDialogTitle("Select Images File");
         FileNameExtensionFilter fnef = new FileNameExtensionFilter("Images File","jpeg","jpg","png");
         imgFileChooser.setFileFilter(fnef);
         imgFileChooser.setAcceptAllFileFilterUsed(false);
        int excelChooser = imgFileChooser.showOpenDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            f = imgFileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(f.toString());
            Image image = icon.getImage().getScaledInstance(img.getWidth(),img.getHeight(),Image.SCALE_SMOOTH);
            img.setIcon(icon);
            
        }
        }catch(Exception e){
             e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Upload");
        }
 }
 public void uploadToDir() throws IOException{
            String foto=null;
            if(f!=null){
            String filename = f.getAbsolutePath();
            String newpath = "src/Uploads/Profiles/";
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
            file = destinationFile;
            foto = destinationFile.getAbsoluteFile().getName();
            }else{
                foto = pro;
            }
            System.out.println(foto);
            try{
            String User = username.getText();
            String Email = email.getText();
            String NoHP = nohp.getText();
            String Sql = "UPDATE user JOIN anggota on anggota.Nis = user.Nis\n" +
"            SET user.Username = '"+User+"',\n" +
"            anggota.Email='"+Email+"',\n" +
"            anggota.NoHp='"+NoHP+"',anggota.Profiles ='"+foto+"' Where anggota.Nis = '"+nis.getText()+"'" ;
                pst = CC.prepareStatement(Sql);
                pst.execute();
                pst.close();
              JOptionPane.showMessageDialog(null, "Update Berhasil");
              username.setText(User);
              email.setText(Email);
              nohp.setText(NoHP);
              //System.out.println(foto);
//            update.setEnabled(false);
//            hapus.setEnabled(false);
         
        }catch(Exception e){
            e.printStackTrace();
        }
 }
 public void updateId(){
     try{
            
            String User = username.getText();
            String Email = email.getText();
            String NoHP = nohp.getText();
            String foto = file.getName();
            System.out.println(foto);
            String Sql = "UPDATE user JOIN anggota on anggota.Nis = user.Nis\n" +
            "SET user.Username = '"+User+"',\n" +
            "anggota.Email='"+Email+"',\n" +
            "anggota.NoHp='"+NoHP+"', anggota.Profiles ='"+foto+"' Where anggota.Nis = '"+nis.getText()+"'" ;
                pst = CC.prepareStatement(Sql);
                pst.execute();
                pst.close();
              JOptionPane.showMessageDialog(null, "Update Berhasil");
              username.setText(User);
              email.setText(Email);
              nohp.setText(NoHP);
              System.out.println(foto);
//            update.setEnabled(false);
//            hapus.setEnabled(false);
         
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

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        img = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        nama = new javax.swing.JLabel();
        nis = new javax.swing.JLabel();
        alamat = new javax.swing.JLabel();
        kelas = new javax.swing.JLabel();
        ttl = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        nohp = new javax.swing.JTextField();
        username = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        SubSirk = new javax.swing.JPanel();
        toKatalog = new javax.swing.JLabel();
        toPengembalian = new javax.swing.JLabel();
        toDenda = new javax.swing.JLabel();
        toHistori = new javax.swing.JLabel();
        SubUser = new javax.swing.JPanel();
        toProf = new javax.swing.JLabel();
        toNotif = new javax.swing.JLabel();
        toOut = new javax.swing.JLabel();
        tgl = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        PGRI = new javax.swing.JLabel();
        toSirkulasi = new javax.swing.JLabel();
        toUsulan = new javax.swing.JLabel();
        toBebpus = new javax.swing.JLabel();
        toUser = new javax.swing.JLabel();
        no = new javax.swing.JLabel();
        expired1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        jLabel2.setText("Profil Anggota");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(33, 52, 161, 32);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(img, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(img, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(33, 116, 240, 280);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jLabel3.setText("Nama");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(407, 116, 49, 23);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jLabel8.setText("NIS");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(407, 168, 31, 23);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jLabel9.setText("Alamat");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(407, 221, 100, 23);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jLabel10.setText("Kelas");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(407, 280, 120, 23);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jLabel11.setText("Tempat, Tanggal Lahir");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(407, 341, 220, 23);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jLabel12.setText("Username");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(410, 390, 110, 23);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jButton1.setText("Unggah Foto");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(33, 424, 240, 34);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jLabel13.setText("Email");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(410, 440, 100, 23);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jLabel14.setText("No HP");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(410, 490, 90, 23);

        nama.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        nama.setText("Nama");
        jPanel1.add(nama);
        nama.setBounds(665, 116, 230, 23);

        nis.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        nis.setText("NIS");
        jPanel1.add(nis);
        nis.setBounds(665, 168, 220, 23);

        alamat.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        alamat.setText("Alamat");
        jPanel1.add(alamat);
        alamat.setBounds(665, 221, 330, 23);

        kelas.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        kelas.setText("Kelas");
        jPanel1.add(kelas);
        kelas.setBounds(665, 280, 80, 23);

        ttl.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        ttl.setText("Tempat, Tanggal Lahir");
        jPanel1.add(ttl);
        ttl.setBounds(660, 340, 340, 23);

        jButton2.setText("Simpan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(1187, 660, 80, 23);

        jButton3.setText("Ubah Password");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(1040, 660, 140, 23);

        nohp.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jPanel1.add(nohp);
        nohp.setBounds(660, 480, 350, 30);

        username.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });
        jPanel1.add(username);
        username.setBounds(660, 380, 350, 30);

        email.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jPanel1.add(email);
        email.setBounds(660, 430, 350, 30);

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

        tgl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tgl.setText("Expired");
        jPanel1.add(tgl);
        tgl.setBounds(40, 490, 230, 17);

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

        jPanel1.add(jPanel14);
        jPanel14.setBounds(0, 11, 1278, 20);

        expired1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        expired1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        expired1.setText("Aktif Hingga");
        jPanel1.add(expired1);
        expired1.setBounds(40, 470, 230, 17);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // TODO add your handling code here:
            uploadToDir();
        } catch (IOException ex) {
            Logger.getLogger(Siswa_Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       Siswa_UbahPassword obj = new Siswa_UbahPassword();
       obj.setVisible(true);
       
    }//GEN-LAST:event_jButton3ActionPerformed

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
            Logger.getLogger(Siswa_Profil.class.getName()).log(Level.SEVERE, null, ex);
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

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

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
            Logger.getLogger(Siswa_Profil.class.getName()).log(Level.SEVERE, null, ex);
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        attach();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Siswa_Profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Siswa_Profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Siswa_Profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Siswa_Profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Siswa_Profil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PGRI;
    private javax.swing.JPanel SubSirk;
    private javax.swing.JPanel SubUser;
    private javax.swing.JLabel alamat;
    private javax.swing.JTextField email;
    private javax.swing.JLabel expired1;
    private javax.swing.JLabel img;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel kelas;
    private javax.swing.JLabel nama;
    private javax.swing.JLabel nis;
    private javax.swing.JLabel no;
    private javax.swing.JTextField nohp;
    private javax.swing.JLabel tgl;
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
    private javax.swing.JLabel ttl;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

}
