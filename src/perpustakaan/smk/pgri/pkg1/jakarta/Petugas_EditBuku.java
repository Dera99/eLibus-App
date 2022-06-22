/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpustakaan.smk.pgri.pkg1.jakarta;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Atthoriq
 */
public class Petugas_EditBuku extends javax.swing.JFrame {

    /**
     * Creates new form Petugas_InputBuku
     */
    ResultSet rs = null;
    Connection CC = null;
    PreparedStatement pst = null;
    public Statement stt;
    public Petugas_EditBuku() {
        initComponents();
        CC = new koneksi().connect();
        subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
        initial();
        userLogin();
        readLang();
        readGMD();
        readPublisher();
        readPlace();
        readAuthor();
         Locale local = new Locale("id", "ID");
        Locale.setDefault(local);
    }
    public String sql;
    private void userLogin(){
        toUser.setText(UserSession.getUserLogin());
    }
    public void readGMD(){
       try{
           Statement stat = CC.createStatement();
           sql = "SELECT gmd_id, gmd_name FROM gmd";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String result = rs.getString("gmd.gmd_name");
               cbGMD.addItem(result);
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       
       }
   }
    public void readLang(){
       try{
           Statement stat = CC.createStatement();
           sql = "SELECT language_id, language_name FROM mst_language";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String result = rs.getString("mst_language.language_name");
               Bahasa.addItem(result);
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       
       }
   }
  
   public void readPublisher(){
       try{
           Statement stat = CC.createStatement();
           sql = "SELECT publisher_id, publisher_name FROM mst_publisher";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String result = rs.getString("mst_publisher.publisher_name");
               cbPenerbit.addItem(result);
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       
       }
   }
   public void readPlace(){
       try{
           Statement stat = CC.createStatement();
           sql = "SELECT place_id, place_name FROM mst_place";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String result = rs.getString("mst_place.place_name");
               cbTempat.addItem(result);
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       
       }
   }
   public void readAuthor(){
       try{
           Statement stat = CC.createStatement();
           sql = "SELECT author_id, author_name FROM mst_author";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String result = rs.getString("mst_author.author_name");
               cbPengarang.addItem(result);
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       
       }
   }
   

   public String file;
   public File f1,destinationFile1,file1;
   public String foto = null;
   public String pro;
    private void attach(){
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
            Image image = icon.getImage().getScaledInstance(img.getWidth(),img.getHeight(),Image.SCALE_SMOOTH);
            img.setIcon(icon);
            
        }
        }catch(Exception e){
             e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Upload");
        }
    }

   public int pub,place,language,auth;
   public void updateData(){
       int response = JOptionPane.showConfirmDialog(this, "Simpan Perubahan?", "Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
       if (response == JOptionPane.YES_OPTION){
       String value1 = Judul.getText();
           int value2 = cbGMD.getSelectedIndex()+1;
           String value3 = Edisi.getText();
           String value4 = ISBN.getText();
           int value5 = pub;
           String value6 = TahunTerbit.getText();
           String value7 = Deskripsi.getText();
           String value8 = JudulSeri.getText();
           String value9 = NoPanggil.getText();
           int value10 = language;
           int value11 = place;
           String value12 = DDC.getText();
           String gmbar = file;
           int Penulis = auth;
//           if (gmbar == null){
//               gmbar = "D:\\Collage\\Smester 8\\Project\\Perpustakaan-SMK-PGRI-1-Jakarta\\src\\perpustakaan\\smk\\pgri\\pkg1\\jakarta\\Button\\Cover.png";
//           }
          // gmbar = gmbar.replace("\\","\\\\");
            String Date;
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
             LocalDateTime now = LocalDateTime.now();  
             Date = dtf.format(now);
       try{
           check1();
           stt = CC.createStatement();
           sql = "UPDATE new_bliblio SET IdGMD ="+value2+",Judul ='"+value1+"',author_id="+Penulis+", Edisi='"+value3+"', isbn_issn='"+value4+"', IdPublisher="+value5+","
                   + "PublisherYear='"+value6+"',Notes='"+value7+"',SeriesTitle='"+value8+"', call_number='"+value9+"',IdLanguage="+value10+", TempatTerbit="+value11+","
                   + "Klasifikasi = '"+value12+"', image='"+foto+"' WHERE IdBliblio ="+id+"";
                  
           stt.executeUpdate(sql);
           JOptionPane.showMessageDialog(null, "Data Buku Berhasil Di Update !!");
           stt.close();
       
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
       }
       }
   }
   private void reset(){
        Judul.setText("");
        cbGMD.setSelectedIndex(0);
        Edisi.setText("");
        lbl_edisi.setEnabled(true);
        ISBN.setText("");
        lbl_isbn.setEnabled(true);
        Penerbit.setText("");
        cbPenerbit.setSelectedIndex(0);
        lbl_publisher.setEnabled(true);
        TahunTerbit.setText("");
        lbl_tterbit.setEnabled(true);
        Deskripsi.setText("");
        lbl_fisik.setEnabled(true);
        JudulSeri.setText("");
        lbl_judulseri.setEnabled(true);
        NoPanggil.setText("");
        lbl_nopanggil.setEnabled(true);
        Bahasa.setSelectedIndex(1);
        lbl_bahasa.setEnabled(true);
        TempatTerbit.setText("");
        cbTempat.setSelectedIndex(0);
        lbl_tempatTerbit.setEnabled(true);
        DDC.setText("");
        lbl_ddc.setEnabled(true);
   }
   public void setValue1(){
        try{
        int index = cbPenerbit.getSelectedIndex();
        Statement stat = CC.createStatement();
        sql = "SELECT * FROM mst_publisher WHERE publisher_id ="+index+"";
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
         String value = rs.getString("publisher_name");
         Penerbit.setText(value);
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
   }
   public void setValue2(){
        try{
        int index = cbTempat.getSelectedIndex();
        Statement stat = CC.createStatement();
        sql = "SELECT * FROM mst_place WHERE place_id="+index+"";
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
         String value = rs.getString("place_name");
         TempatTerbit.setText(value);
        }
        }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);  
        }
   }
   public void setValue3(){
        try{
        int index = Bahasa.getSelectedIndex();
        Statement stat = CC.createStatement();
        sql = "SELECT * FROM mst_language WHERE language_id="+index+"";
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
         String value = rs.getString("language_name");
         lang.setText(value);
        }
        }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);  
        }
   }
   public void setValue4(){
        try{
        int index = cbPengarang.getSelectedIndex();
        Statement stat = CC.createStatement();
        sql = "SELECT * FROM mst_author WHERE author_id="+index+"";
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
         String value = rs.getString("author_name");
         Pengarang.setText(value);
        }
        }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);  
        }
   }
   
    public void setIndex1(){
        try{
        int index = cbPenerbit.getSelectedIndex();
        Statement stat = CC.createStatement();
        sql = "SELECT * FROM mst_publisher WHERE publisher_id ="+index+"";
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
         String value = rs.getString("publisher_name");
         pub = index;
        }else{
         String Date;
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
             LocalDateTime now = LocalDateTime.now();  
             Date = dtf.format(now);
             stt = CC.createStatement();
             String name = Penerbit.getText();
             String SQL = "INSERT INTO mst_publisher (publisher_name,input_date,last_update) VALUES"
                     + "('"+name+"','"+Date+"','"+Date+"')";
             stt.executeUpdate(SQL);
            
            String Check = "SELECT mst_publisher.publisher_id, mst_publisher.publisher_name FROM mst_publisher WHERE publisher_name = '"+name+"'";
            ResultSet rsa = stat.executeQuery(Check);
            if(rsa.next()){
                pub= rsa.getInt("mst_publisher.publisher_id");
            }
             stt.close();
        }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
       
    }
    public void setIndex2(){
        try{
        int index = cbTempat.getSelectedIndex();
        Statement stat = CC.createStatement();
        sql = "SELECT * FROM mst_place WHERE place_id="+index+"";
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
         String value = rs.getString("place_name");
         place = index;
         TempatTerbit.setText(value);
        }else{
         String Date;
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
             LocalDateTime now = LocalDateTime.now();  
             Date = dtf.format(now);  
             stt = CC.createStatement();
             String tempat=TempatTerbit.getText();
             String SQL = "INSERT INTO mst_place (place_name,input_date,last_update) VALUES"
                     + "('"+tempat+"','"+Date+"','"+Date+"')";
             stt.executeUpdate(SQL);
             stt.close();
             String Check = "SELECT mst_place.place_id, place_name FROM mst_place WHERE place_name = '"+tempat+"'";
            ResultSet rsa = stat.executeQuery(Check);
            if(rsa.next()){
                place= rsa.getInt("place_id");
            }
        }
        }catch (Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
    }
    public void setIndex3(){
        try{
        int index = Bahasa.getSelectedIndex();
        Statement stat = CC.createStatement();
        sql = "SELECT * FROM mst_language WHERE language_id="+index+"";
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
         String value = rs.getString("language_name");
         language = index;
         lang.setText(value);
        }else{
         String Date;
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
             LocalDateTime now = LocalDateTime.now();  
             Date = dtf.format(now);  
             stt = CC.createStatement();
             String bhs = lang.getText();
             String SQL = "INSERT INTO mst_language (language_name,input_date,last_update) VALUES('"+bhs+"','"+Date+"','"+Date+"')";
             stt.executeUpdate(SQL);
             stt.close();
             String Check = "SELECT mst_language.language_id, language_name FROM mst_language WHERE language_name = '"+bhs+"'";
            ResultSet rsa = stat.executeQuery(Check);
            if(rsa.next()){
                language = rsa.getInt("language_id");
            }
        }
        }catch (Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
    }
    public void setIndex4(){
        try{
        int index = cbPengarang.getSelectedIndex();
        Statement stat = CC.createStatement();
        sql = "SELECT * FROM mst_author WHERE author_id="+index+"";
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
         String value = rs.getString("author_name");
         auth = index;
         Pengarang.setText(value);
        }else{
         String Date;
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
             LocalDateTime now = LocalDateTime.now();  
             Date = dtf.format(now);  
             stt = CC.createStatement();
             String author = Pengarang.getText();
             String SQL = "INSERT INTO mst_author (author_name,input_date,last_update) VALUES('"+author+"','"+Date+"','"+Date+"')";
             stt.executeUpdate(SQL);
             stt.close();
             String Check = "SELECT mst_author.author_id, author_name FROM mst_author WHERE author_name = '"+author+"'";
            ResultSet rsa = stat.executeQuery(Check);
            if(rsa.next()){
                auth = rsa.getInt("author_id");
            }
        }
        }catch (Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
    }
    public int id;
    public void initial(){
         try{
             Statement stat = CC.createStatement();
             String val = UserSession.getCallNumb();
             sql = "SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id INNER JOIN gmd ON gmd.gmd_id = new_bliblio.IdGMD INNER JOIN mst_publisher ON mst_publisher.publisher_id = new_bliblio.IdPublisher INNER JOIN mst_language ON mst_language.language_id = new_bliblio.IdLanguage INNER JOIN mst_place ON mst_place.place_id = new_bliblio.TempatTerbit WHERE new_bliblio.call_number='"+val+"'";
             ResultSet rs = stat.executeQuery(sql);
             if(rs.next()){
                 id = rs.getInt("new_bliblio.IdBliblio");
                 Judul.setText(rs.getString("Judul"));
                 Pengarang.setText(rs.getString("mst_author.author_name"));
                 cbPengarang.getModel().setSelectedItem(rs.getString("mst_author.author_name"));
                 String pn = rs.getString("gmd.gmd_name");
                 cbGMD.getModel().setSelectedItem(pn);
                 Edisi.setText(rs.getString("Edisi"));
                 ISBN.setText(rs.getString("isbn_issn"));
                 Penerbit.setText(rs.getString("mst_publisher.publisher_name"));
                 cbPenerbit.getModel().setSelectedItem(rs.getString("mst_publisher.publisher_name"));
                 TahunTerbit.setText(rs.getString("PublisherYear"));
                 Deskripsi.setText(rs.getString("Notes"));
                 JudulSeri.setText(rs.getString("SeriesTitle"));
                 NoPanggil.setText(rs.getString("call_number"));
                 lang.setText(rs.getString("mst_language.language_name"));
                 Bahasa.getModel().setSelectedItem(rs.getString("mst_language.language_name"));
                 TempatTerbit.setText(rs.getString("mst_place.place_name"));
                 cbTempat.getModel().setSelectedItem(rs.getString("mst_place.place_name"));
                 DDC.setText(rs.getString("klasifikasi"));
                 //JFileChooser chooser = new JFileChooser();
                 //chooser.showOpenDialog(null);
                 pro = rs.getString("new_bliblio.image");
//                 a.replace("\\","\\\\");
                 //file = .getAbsolutePath();
//                 InputStream stream1 = getClass().getResourceAsStream("/Uploads/Books/"+pro+"");
//                 ImageIcon icon1 = new ImageIcon(ImageIO.read(stream1));
//                 Image image1 = icon1.getImage().getScaledInstance(img.getWidth(),img.getHeight(),Image.SCALE_SMOOTH);
//                 img.setIcon(icon1);
               File im = new File("src/Uploads/Books/"+pro+"");
               InputStream stream = new FileInputStream(im);
               ImageIcon icon1 = new ImageIcon(ImageIO.read(stream));
               Image image1 = icon1.getImage().getScaledInstance(img.getWidth(),img.getHeight(),Image.SCALE_SMOOTH);
               img.setIcon(icon1);
             }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
    }
    
    private void Delete(){
       int response = JOptionPane.showConfirmDialog(this, "Apakah Anda Yakin Ingin Menghapus Data?", "Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
       if (response == JOptionPane.YES_OPTION){
          sql="DELETE FROM new_bliblio WHERE IdBliblio="+id+"";
         try{
            pst=CC.prepareStatement(sql);
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Data Eksemplar Berhasil Terhapus !!");
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
       }
    }
        public void check1() throws IOException{
         //tring foto=null;
            if(f1!=null){
            String filename = f1.getAbsolutePath();
            String newpath = "src/Uploads/Books/";
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
            foto = destinationFile.getAbsoluteFile().getName();
            }else{
                foto = pro;
            }
            //System.out.println(foto1);
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
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        toAdmin = new javax.swing.JLabel();
        toDash = new javax.swing.JLabel();
        toBlibli = new javax.swing.JLabel();
        toSriku = new javax.swing.JLabel();
        toAnggo = new javax.swing.JLabel();
        toLaporan = new javax.swing.JLabel();
        empty1 = new javax.swing.JPanel();
        toUser = new javax.swing.JLabel();
        empty2 = new javax.swing.JPanel();
        subMenuAdmin = new javax.swing.JPanel();
        toProfilPetugas = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        toDataPetugas = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        toLogin = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        subMenuLaporan = new javax.swing.JPanel();
        toLapPeminjaman = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        toLapBuku = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        toLapAnggota = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        toLapPengembalian = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        toLapDenda = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        subMenuAnggota = new javax.swing.JPanel();
        toDataAnggota = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        toInputAnggota = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        toDataKelas = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        toDataJurusan = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        toBebasPustaka = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        subMenuSirkulasi = new javax.swing.JPanel();
        toDataTransaksi = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        toPengembalianBuku = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        toDenda = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        toKonfDenda = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        toKonfDenda1 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        subMenuBlibliografi = new javax.swing.JPanel();
        toDataBuku = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        toInputBuku = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        toDataPenulis = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        toDataUsulan = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_judul = new javax.swing.JLabel();
        lbl_gmd = new javax.swing.JLabel();
        lbl_edisi = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Deskripsi = new javax.swing.JTextArea();
        Judul = new javax.swing.JTextField();
        Edisi = new javax.swing.JTextField();
        submit = new javax.swing.JButton();
        lbl_isbn = new javax.swing.JLabel();
        lbl_publisher = new javax.swing.JLabel();
        lbl_tterbit = new javax.swing.JLabel();
        lbl_tempatTerbit = new javax.swing.JLabel();
        lbl_bahasa = new javax.swing.JLabel();
        lbl_nopanggil = new javax.swing.JLabel();
        lbl_judulseri = new javax.swing.JLabel();
        lbl_fisik = new javax.swing.JLabel();
        TahunTerbit = new javax.swing.JTextField();
        Penerbit = new javax.swing.JTextField();
        ISBN = new javax.swing.JTextField();
        JudulSeri = new javax.swing.JTextField();
        TempatTerbit = new javax.swing.JTextField();
        NoPanggil = new javax.swing.JTextField();
        attach = new javax.swing.JButton();
        Bahasa = new javax.swing.JComboBox<>();
        cbGMD = new javax.swing.JComboBox<>();
        lbl_ddc = new javax.swing.JLabel();
        DDC = new javax.swing.JTextField();
        cbPenerbit = new javax.swing.JComboBox<>();
        cbTempat = new javax.swing.JComboBox<>();
        lang = new javax.swing.JTextField();
        Pengarang = new javax.swing.JTextField();
        lbl_pengarang = new javax.swing.JLabel();
        cbPengarang = new javax.swing.JComboBox<>();
        delete = new javax.swing.JButton();
        attach1 = new javax.swing.JButton();
        img1 = new javax.swing.JLabel();
        panelImg = new javax.swing.JPanel();
        img = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });
        jPanel1.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(229, 231, 238));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });
        jPanel3.setLayout(null);
        jPanel3.add(jSeparator1);
        jSeparator1.setBounds(0, 92, 80, 10);
        jPanel3.add(jSeparator2);
        jSeparator2.setBounds(0, 310, 80, 10);
        jPanel3.add(jSeparator3);
        jSeparator3.setBounds(0, 140, 80, 10);
        jPanel3.add(jSeparator4);
        jSeparator4.setBounds(0, 580, 80, 10);
        jPanel3.add(jSeparator5);
        jSeparator5.setBounds(0, 220, 80, 10);
        jPanel3.add(jSeparator6);
        jSeparator6.setBounds(0, 400, 80, 10);
        jPanel3.add(jSeparator7);
        jSeparator7.setBounds(0, 490, 80, 10);

        toAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Icon/Admin.png"))); // NOI18N
        toAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toAdminMouseEntered(evt);
            }
        });
        jPanel3.add(toAdmin);
        toAdmin.setBounds(20, 510, 32, 40);

        toDash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Icon/home.png"))); // NOI18N
        toDash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDashMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDashMouseEntered(evt);
            }
        });
        jPanel3.add(toDash);
        toDash.setBounds(20, 20, 32, 40);

        toBlibli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Icon/blibliograf.png"))); // NOI18N
        toBlibli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toBlibliMouseEntered(evt);
            }
        });
        jPanel3.add(toBlibli);
        toBlibli.setBounds(20, 150, 32, 40);

        toSriku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Icon/Sirkulasi.png"))); // NOI18N
        toSriku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toSrikuMouseEntered(evt);
            }
        });
        jPanel3.add(toSriku);
        toSriku.setBounds(20, 240, 32, 40);

        toAnggo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Icon/Anggota.png"))); // NOI18N
        toAnggo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toAnggoMouseEntered(evt);
            }
        });
        jPanel3.add(toAnggo);
        toAnggo.setBounds(20, 330, 32, 40);

        toLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/smk/pgri/pkg1/jakarta/Button/Icon/report.png"))); // NOI18N
        toLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toLaporanMouseEntered(evt);
            }
        });
        jPanel3.add(toLaporan);
        toLaporan.setBounds(20, 420, 32, 40);

        empty1.setBackground(new java.awt.Color(229, 231, 238));
        empty1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                empty1MouseEntered(evt);
            }
        });

        toUser.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        toUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toUser.setText("jLabel2");

        javax.swing.GroupLayout empty1Layout = new javax.swing.GroupLayout(empty1);
        empty1.setLayout(empty1Layout);
        empty1Layout.setHorizontalGroup(
            empty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empty1Layout.createSequentialGroup()
                .addComponent(toUser, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );
        empty1Layout.setVerticalGroup(
            empty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empty1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(toUser, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(empty1);
        empty1.setBounds(0, 90, 80, 50);

        empty2.setBackground(new java.awt.Color(229, 231, 238));
        empty2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                empty2MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout empty2Layout = new javax.swing.GroupLayout(empty2);
        empty2.setLayout(empty2Layout);
        empty2Layout.setHorizontalGroup(
            empty2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        empty2Layout.setVerticalGroup(
            empty2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jPanel3.add(empty2);
        empty2.setBounds(0, 580, 80, 140);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(0, 0, 80, 720);

        subMenuAdmin.setBackground(new java.awt.Color(229, 231, 238));
        subMenuAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                subMenuAdminMouseExited(evt);
            }
        });
        subMenuAdmin.setLayout(null);

        toProfilPetugas.setBackground(new java.awt.Color(229, 231, 238));
        toProfilPetugas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toProfilPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toProfilPetugasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toProfilPetugasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toProfilPetugasMouseExited(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel26.setText("Pengaturan");

        javax.swing.GroupLayout toProfilPetugasLayout = new javax.swing.GroupLayout(toProfilPetugas);
        toProfilPetugas.setLayout(toProfilPetugasLayout);
        toProfilPetugasLayout.setHorizontalGroup(
            toProfilPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toProfilPetugasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addContainerGap())
        );
        toProfilPetugasLayout.setVerticalGroup(
            toProfilPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuAdmin.add(toProfilPetugas);
        toProfilPetugas.setBounds(0, 0, 150, 40);

        toDataPetugas.setBackground(new java.awt.Color(229, 231, 238));
        toDataPetugas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toDataPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDataPetugasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDataPetugasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toDataPetugasMouseExited(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel29.setText("Data Petugas");

        javax.swing.GroupLayout toDataPetugasLayout = new javax.swing.GroupLayout(toDataPetugas);
        toDataPetugas.setLayout(toDataPetugasLayout);
        toDataPetugasLayout.setHorizontalGroup(
            toDataPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataPetugasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addContainerGap())
        );
        toDataPetugasLayout.setVerticalGroup(
            toDataPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuAdmin.add(toDataPetugas);
        toDataPetugas.setBounds(0, 40, 150, 40);

        toLogin.setBackground(new java.awt.Color(229, 231, 238));
        toLogin.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
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

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel31.setText("Log Out");

        javax.swing.GroupLayout toLoginLayout = new javax.swing.GroupLayout(toLogin);
        toLogin.setLayout(toLoginLayout);
        toLoginLayout.setHorizontalGroup(
            toLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addContainerGap())
        );
        toLoginLayout.setVerticalGroup(
            toLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuAdmin.add(toLogin);
        toLogin.setBounds(0, 80, 150, 40);

        jPanel1.add(subMenuAdmin);
        subMenuAdmin.setBounds(80, 490, 150, 120);

        subMenuLaporan.setBackground(new java.awt.Color(229, 231, 238));
        subMenuLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                subMenuLaporanMouseExited(evt);
            }
        });
        subMenuLaporan.setLayout(null);

        toLapPeminjaman.setBackground(new java.awt.Color(229, 231, 238));
        toLapPeminjaman.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toLapPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toLapPeminjamanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toLapPeminjamanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toLapPeminjamanMouseExited(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel21.setText("Laporan Peminjaman");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout toLapPeminjamanLayout = new javax.swing.GroupLayout(toLapPeminjaman);
        toLapPeminjaman.setLayout(toLapPeminjamanLayout);
        toLapPeminjamanLayout.setHorizontalGroup(
            toLapPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toLapPeminjamanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
        );
        toLapPeminjamanLayout.setVerticalGroup(
            toLapPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuLaporan.add(toLapPeminjaman);
        toLapPeminjaman.setBounds(0, 0, 210, 40);

        toLapBuku.setBackground(new java.awt.Color(229, 231, 238));
        toLapBuku.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toLapBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toLapBukuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toLapBukuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toLapBukuMouseExited(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel22.setText("Laporan Buku Hilang");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout toLapBukuLayout = new javax.swing.GroupLayout(toLapBuku);
        toLapBuku.setLayout(toLapBukuLayout);
        toLapBukuLayout.setHorizontalGroup(
            toLapBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toLapBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
        );
        toLapBukuLayout.setVerticalGroup(
            toLapBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuLaporan.add(toLapBuku);
        toLapBuku.setBounds(0, 40, 210, 40);

        toLapAnggota.setBackground(new java.awt.Color(229, 231, 238));
        toLapAnggota.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toLapAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toLapAnggotaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toLapAnggotaMouseExited(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel23.setText("Laporan Anggota");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout toLapAnggotaLayout = new javax.swing.GroupLayout(toLapAnggota);
        toLapAnggota.setLayout(toLapAnggotaLayout);
        toLapAnggotaLayout.setHorizontalGroup(
            toLapAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toLapAnggotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
        );
        toLapAnggotaLayout.setVerticalGroup(
            toLapAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuLaporan.add(toLapAnggota);
        toLapAnggota.setBounds(0, 80, 210, 40);

        toLapPengembalian.setBackground(new java.awt.Color(229, 231, 238));
        toLapPengembalian.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toLapPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toLapPengembalianMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toLapPengembalianMouseExited(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel24.setText("Laporan Pengembalian");
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout toLapPengembalianLayout = new javax.swing.GroupLayout(toLapPengembalian);
        toLapPengembalian.setLayout(toLapPengembalianLayout);
        toLapPengembalianLayout.setHorizontalGroup(
            toLapPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toLapPengembalianLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
        );
        toLapPengembalianLayout.setVerticalGroup(
            toLapPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuLaporan.add(toLapPengembalian);
        toLapPengembalian.setBounds(0, 120, 210, 40);

        toLapDenda.setBackground(new java.awt.Color(229, 231, 238));
        toLapDenda.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toLapDenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toLapDendaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toLapDendaMouseExited(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel25.setText("Laporan Denda");
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout toLapDendaLayout = new javax.swing.GroupLayout(toLapDenda);
        toLapDenda.setLayout(toLapDendaLayout);
        toLapDendaLayout.setHorizontalGroup(
            toLapDendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toLapDendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
        );
        toLapDendaLayout.setVerticalGroup(
            toLapDendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuLaporan.add(toLapDenda);
        toLapDenda.setBounds(0, 160, 210, 40);

        jPanel1.add(subMenuLaporan);
        subMenuLaporan.setBounds(80, 400, 200, 210);

        subMenuAnggota.setBackground(new java.awt.Color(229, 231, 238));
        subMenuAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                subMenuAnggotaMouseExited(evt);
            }
        });
        subMenuAnggota.setLayout(null);

        toDataAnggota.setBackground(new java.awt.Color(229, 231, 238));
        toDataAnggota.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toDataAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDataAnggotaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDataAnggotaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toDataAnggotaMouseExited(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setText("Data Anggota");

        javax.swing.GroupLayout toDataAnggotaLayout = new javax.swing.GroupLayout(toDataAnggota);
        toDataAnggota.setLayout(toDataAnggotaLayout);
        toDataAnggotaLayout.setHorizontalGroup(
            toDataAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataAnggotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        toDataAnggotaLayout.setVerticalGroup(
            toDataAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuAnggota.add(toDataAnggota);
        toDataAnggota.setBounds(0, 0, 150, 40);

        toInputAnggota.setBackground(new java.awt.Color(229, 231, 238));
        toInputAnggota.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toInputAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toInputAnggotaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toInputAnggotaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toInputAnggotaMouseExited(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel13.setText("Input Anggota");

        javax.swing.GroupLayout toInputAnggotaLayout = new javax.swing.GroupLayout(toInputAnggota);
        toInputAnggota.setLayout(toInputAnggotaLayout);
        toInputAnggotaLayout.setHorizontalGroup(
            toInputAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toInputAnggotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        toInputAnggotaLayout.setVerticalGroup(
            toInputAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuAnggota.add(toInputAnggota);
        toInputAnggota.setBounds(0, 40, 150, 40);

        toDataKelas.setBackground(new java.awt.Color(229, 231, 238));
        toDataKelas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toDataKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDataKelasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDataKelasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toDataKelasMouseExited(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel32.setText("Data Kelas");

        javax.swing.GroupLayout toDataKelasLayout = new javax.swing.GroupLayout(toDataKelas);
        toDataKelas.setLayout(toDataKelasLayout);
        toDataKelasLayout.setHorizontalGroup(
            toDataKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataKelasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        toDataKelasLayout.setVerticalGroup(
            toDataKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuAnggota.add(toDataKelas);
        toDataKelas.setBounds(0, 80, 150, 40);

        toDataJurusan.setBackground(new java.awt.Color(229, 231, 238));
        toDataJurusan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toDataJurusan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDataJurusanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDataJurusanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toDataJurusanMouseExited(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel33.setText("Data Jurusan");

        javax.swing.GroupLayout toDataJurusanLayout = new javax.swing.GroupLayout(toDataJurusan);
        toDataJurusan.setLayout(toDataJurusanLayout);
        toDataJurusanLayout.setHorizontalGroup(
            toDataJurusanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataJurusanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        toDataJurusanLayout.setVerticalGroup(
            toDataJurusanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuAnggota.add(toDataJurusan);
        toDataJurusan.setBounds(0, 120, 150, 40);

        toBebasPustaka.setBackground(new java.awt.Color(229, 231, 238));
        toBebasPustaka.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toBebasPustaka.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toBebasPustakaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toBebasPustakaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toBebasPustakaMouseExited(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel16.setText("Bebas Pustaka");

        javax.swing.GroupLayout toBebasPustakaLayout = new javax.swing.GroupLayout(toBebasPustaka);
        toBebasPustaka.setLayout(toBebasPustakaLayout);
        toBebasPustakaLayout.setHorizontalGroup(
            toBebasPustakaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toBebasPustakaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        toBebasPustakaLayout.setVerticalGroup(
            toBebasPustakaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuAnggota.add(toBebasPustaka);
        toBebasPustaka.setBounds(0, 160, 150, 40);

        jPanel1.add(subMenuAnggota);
        subMenuAnggota.setBounds(80, 310, 150, 210);

        subMenuSirkulasi.setBackground(new java.awt.Color(229, 231, 238));
        subMenuSirkulasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                subMenuSirkulasiMouseExited(evt);
            }
        });
        subMenuSirkulasi.setLayout(null);

        toDataTransaksi.setBackground(new java.awt.Color(229, 231, 238));
        toDataTransaksi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toDataTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDataTransaksiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDataTransaksiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toDataTransaksiMouseExited(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel27.setText("Data Transaksi");

        javax.swing.GroupLayout toDataTransaksiLayout = new javax.swing.GroupLayout(toDataTransaksi);
        toDataTransaksi.setLayout(toDataTransaksiLayout);
        toDataTransaksiLayout.setHorizontalGroup(
            toDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        toDataTransaksiLayout.setVerticalGroup(
            toDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuSirkulasi.add(toDataTransaksi);
        toDataTransaksi.setBounds(0, 0, 300, 40);

        toPengembalianBuku.setBackground(new java.awt.Color(229, 231, 238));
        toPengembalianBuku.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toPengembalianBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toPengembalianBukuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toPengembalianBukuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toPengembalianBukuMouseExited(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel18.setText("Konfirmasi Pengembalian Buku");

        javax.swing.GroupLayout toPengembalianBukuLayout = new javax.swing.GroupLayout(toPengembalianBuku);
        toPengembalianBuku.setLayout(toPengembalianBukuLayout);
        toPengembalianBukuLayout.setHorizontalGroup(
            toPengembalianBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toPengembalianBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        toPengembalianBukuLayout.setVerticalGroup(
            toPengembalianBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuSirkulasi.add(toPengembalianBuku);
        toPengembalianBuku.setBounds(0, 40, 300, 40);

        toDenda.setBackground(new java.awt.Color(229, 231, 238));
        toDenda.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
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

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel19.setText("Data Denda");

        javax.swing.GroupLayout toDendaLayout = new javax.swing.GroupLayout(toDenda);
        toDenda.setLayout(toDendaLayout);
        toDendaLayout.setHorizontalGroup(
            toDendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );
        toDendaLayout.setVerticalGroup(
            toDendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuSirkulasi.add(toDenda);
        toDenda.setBounds(0, 80, 300, 40);

        toKonfDenda.setBackground(new java.awt.Color(229, 231, 238));
        toKonfDenda.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toKonfDenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toKonfDendaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toKonfDendaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toKonfDendaMouseExited(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel20.setText("Konfirmasi Pembayaran Denda");

        javax.swing.GroupLayout toKonfDendaLayout = new javax.swing.GroupLayout(toKonfDenda);
        toKonfDenda.setLayout(toKonfDendaLayout);
        toKonfDendaLayout.setHorizontalGroup(
            toKonfDendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toKonfDendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        toKonfDendaLayout.setVerticalGroup(
            toKonfDendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuSirkulasi.add(toKonfDenda);
        toKonfDenda.setBounds(0, 120, 300, 40);

        toKonfDenda1.setBackground(new java.awt.Color(229, 231, 238));
        toKonfDenda1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toKonfDenda1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toKonfDenda1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toKonfDenda1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toKonfDenda1MouseExited(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel28.setText("Data Pengunjung");

        javax.swing.GroupLayout toKonfDenda1Layout = new javax.swing.GroupLayout(toKonfDenda1);
        toKonfDenda1.setLayout(toKonfDenda1Layout);
        toKonfDenda1Layout.setHorizontalGroup(
            toKonfDenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toKonfDenda1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        toKonfDenda1Layout.setVerticalGroup(
            toKonfDenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuSirkulasi.add(toKonfDenda1);
        toKonfDenda1.setBounds(0, 160, 300, 40);

        jPanel1.add(subMenuSirkulasi);
        subMenuSirkulasi.setBounds(80, 220, 250, 200);

        subMenuBlibliografi.setBackground(new java.awt.Color(229, 231, 238));
        subMenuBlibliografi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                subMenuBlibliografiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                subMenuBlibliografiMouseExited(evt);
            }
        });
        subMenuBlibliografi.setLayout(null);

        toDataBuku.setBackground(new java.awt.Color(229, 231, 238));
        toDataBuku.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDataBukuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDataBukuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toDataBukuMouseExited(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel14.setText("Data Buku");

        javax.swing.GroupLayout toDataBukuLayout = new javax.swing.GroupLayout(toDataBuku);
        toDataBuku.setLayout(toDataBukuLayout);
        toDataBukuLayout.setHorizontalGroup(
            toDataBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        toDataBukuLayout.setVerticalGroup(
            toDataBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toDataBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        subMenuBlibliografi.add(toDataBuku);
        toDataBuku.setBounds(0, 0, 150, 40);

        toInputBuku.setBackground(new java.awt.Color(229, 231, 238));
        toInputBuku.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toInputBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toInputBukuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toInputBukuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toInputBukuMouseExited(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setText("Input Buku");

        javax.swing.GroupLayout toInputBukuLayout = new javax.swing.GroupLayout(toInputBuku);
        toInputBuku.setLayout(toInputBukuLayout);
        toInputBukuLayout.setHorizontalGroup(
            toInputBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toInputBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        toInputBukuLayout.setVerticalGroup(
            toInputBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toInputBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        subMenuBlibliografi.add(toInputBuku);
        toInputBuku.setBounds(0, 40, 150, 43);

        toDataPenulis.setBackground(new java.awt.Color(229, 231, 238));
        toDataPenulis.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toDataPenulis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDataPenulisMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDataPenulisMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toDataPenulisMouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("Data Penulis");

        javax.swing.GroupLayout toDataPenulisLayout = new javax.swing.GroupLayout(toDataPenulis);
        toDataPenulis.setLayout(toDataPenulisLayout);
        toDataPenulisLayout.setHorizontalGroup(
            toDataPenulisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataPenulisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        toDataPenulisLayout.setVerticalGroup(
            toDataPenulisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        subMenuBlibliografi.add(toDataPenulis);
        toDataPenulis.setBounds(0, 80, 150, 43);

        toDataUsulan.setBackground(new java.awt.Color(229, 231, 238));
        toDataUsulan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toDataUsulan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDataUsulanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDataUsulanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toDataUsulanMouseExited(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel11.setText("Usulan Buku");

        javax.swing.GroupLayout toDataUsulanLayout = new javax.swing.GroupLayout(toDataUsulan);
        toDataUsulan.setLayout(toDataUsulanLayout);
        toDataUsulanLayout.setHorizontalGroup(
            toDataUsulanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataUsulanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        toDataUsulanLayout.setVerticalGroup(
            toDataUsulanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        subMenuBlibliografi.add(toDataUsulan);
        toDataUsulan.setBounds(0, 120, 150, 43);

        jPanel1.add(subMenuBlibliografi);
        subMenuBlibliografi.setBounds(80, 140, 150, 170);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Edit Buku");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(110, 70, 350, 30);

        lbl_judul.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_judul.setText("Judul");
        jPanel1.add(lbl_judul);
        lbl_judul.setBounds(120, 160, 80, 20);

        lbl_gmd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_gmd.setText("GMD");
        jPanel1.add(lbl_gmd);
        lbl_gmd.setBounds(120, 220, 70, 20);

        lbl_edisi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_edisi.setText("Edisi");
        jPanel1.add(lbl_edisi);
        lbl_edisi.setBounds(120, 250, 70, 20);

        Deskripsi.setColumns(20);
        Deskripsi.setRows(5);
        jScrollPane1.setViewportView(Deskripsi);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(320, 370, 630, 70);

        Judul.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(Judul);
        Judul.setBounds(320, 160, 630, 23);

        Edisi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Edisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdisiActionPerformed(evt);
            }
        });
        jPanel1.add(Edisi);
        Edisi.setBounds(320, 250, 630, 23);

        submit.setText("Perbarui");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        jPanel1.add(submit);
        submit.setBounds(1140, 660, 80, 23);

        lbl_isbn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_isbn.setText("ISBN");
        jPanel1.add(lbl_isbn);
        lbl_isbn.setBounds(120, 280, 80, 20);

        lbl_publisher.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_publisher.setText("Penerbit");
        jPanel1.add(lbl_publisher);
        lbl_publisher.setBounds(120, 310, 70, 20);

        lbl_tterbit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_tterbit.setText("Tahun Terbit");
        jPanel1.add(lbl_tterbit);
        lbl_tterbit.setBounds(120, 340, 90, 20);

        lbl_tempatTerbit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_tempatTerbit.setText("Tempat Terbit");
        jPanel1.add(lbl_tempatTerbit);
        lbl_tempatTerbit.setBounds(120, 540, 90, 20);

        lbl_bahasa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_bahasa.setText("Bahasa");
        jPanel1.add(lbl_bahasa);
        lbl_bahasa.setBounds(120, 500, 90, 40);

        lbl_nopanggil.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_nopanggil.setText("No Panggil");
        jPanel1.add(lbl_nopanggil);
        lbl_nopanggil.setBounds(120, 480, 150, 20);

        lbl_judulseri.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_judulseri.setText("Judul Seri");
        jPanel1.add(lbl_judulseri);
        lbl_judulseri.setBounds(120, 450, 70, 20);

        lbl_fisik.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_fisik.setText("Deskripsi Fisik");
        jPanel1.add(lbl_fisik);
        lbl_fisik.setBounds(120, 370, 100, 20);

        TahunTerbit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TahunTerbit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TahunTerbitActionPerformed(evt);
            }
        });
        jPanel1.add(TahunTerbit);
        TahunTerbit.setBounds(320, 340, 630, 23);

        Penerbit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Penerbit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PenerbitActionPerformed(evt);
            }
        });
        jPanel1.add(Penerbit);
        Penerbit.setBounds(320, 310, 240, 23);

        ISBN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(ISBN);
        ISBN.setBounds(320, 280, 630, 23);

        JudulSeri.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(JudulSeri);
        JudulSeri.setBounds(320, 450, 630, 23);

        TempatTerbit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TempatTerbit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TempatTerbitActionPerformed(evt);
            }
        });
        jPanel1.add(TempatTerbit);
        TempatTerbit.setBounds(320, 540, 200, 23);

        NoPanggil.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        NoPanggil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoPanggilActionPerformed(evt);
            }
        });
        jPanel1.add(NoPanggil);
        NoPanggil.setBounds(320, 480, 630, 23);

        attach.setText("Upload");
        attach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachActionPerformed(evt);
            }
        });
        jPanel1.add(attach);
        attach.setBounds(1060, 360, 120, 23);

        Bahasa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Bahasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Bahasa" }));
        Bahasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BahasaActionPerformed(evt);
            }
        });
        jPanel1.add(Bahasa);
        Bahasa.setBounds(530, 510, 150, 21);

        cbGMD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGMDActionPerformed(evt);
            }
        });
        jPanel1.add(cbGMD);
        cbGMD.setBounds(320, 220, 150, 22);

        lbl_ddc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ddc.setText("Klasifikasi");
        jPanel1.add(lbl_ddc);
        lbl_ddc.setBounds(120, 570, 90, 20);

        DDC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DDCActionPerformed(evt);
            }
        });
        DDC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DDCKeyPressed(evt);
            }
        });
        jPanel1.add(DDC);
        DDC.setBounds(320, 570, 630, 23);

        cbPenerbit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbPenerbit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Penerbit" }));
        cbPenerbit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPenerbitActionPerformed(evt);
            }
        });
        jPanel1.add(cbPenerbit);
        cbPenerbit.setBounds(570, 310, 150, 21);

        cbTempat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbTempat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Tempat" }));
        cbTempat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTempatActionPerformed(evt);
            }
        });
        jPanel1.add(cbTempat);
        cbTempat.setBounds(530, 540, 150, 21);

        lang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(lang);
        lang.setBounds(320, 510, 200, 23);

        Pengarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(Pengarang);
        Pengarang.setBounds(320, 190, 240, 23);

        lbl_pengarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_pengarang.setText("Pengarang");
        jPanel1.add(lbl_pengarang);
        lbl_pengarang.setBounds(120, 190, 80, 20);

        cbPengarang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbPengarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Pengarang" }));
        cbPengarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPengarangActionPerformed(evt);
            }
        });
        jPanel1.add(cbPengarang);
        cbPengarang.setBounds(570, 190, 150, 21);

        delete.setText("Hapus");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel1.add(delete);
        delete.setBounds(1040, 660, 80, 23);

        attach1.setText("Kembali");
        attach1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attach1ActionPerformed(evt);
            }
        });
        jPanel1.add(attach1);
        attach1.setBounds(120, 660, 80, 23);
        jPanel1.add(img1);
        img1.setBounds(1060, 140, 0, 0);

        panelImg.setMinimumSize(new java.awt.Dimension(160, 220));

        javax.swing.GroupLayout panelImgLayout = new javax.swing.GroupLayout(panelImg);
        panelImg.setLayout(panelImgLayout);
        panelImgLayout.setHorizontalGroup(
            panelImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImgLayout.createSequentialGroup()
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 41, Short.MAX_VALUE))
        );
        panelImgLayout.setVerticalGroup(
            panelImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImgLayout.createSequentialGroup()
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 33, Short.MAX_VALUE))
        );

        jPanel1.add(panelImg);
        panelImg.setBounds(1060, 160, 120, 190);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void EdisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdisiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdisiActionPerformed

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
    }//GEN-LAST:event_jPanel1MouseEntered

    private void TahunTerbitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TahunTerbitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TahunTerbitActionPerformed

    private void PenerbitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PenerbitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenerbitActionPerformed

    private void TempatTerbitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TempatTerbitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TempatTerbitActionPerformed

    private void NoPanggilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoPanggilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoPanggilActionPerformed

    private void toAdminMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toAdminMouseEntered
        subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(true);
    }//GEN-LAST:event_toAdminMouseEntered

    private void toDashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDashMouseClicked
        Petugas_Dashboard obj = new Petugas_Dashboard();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDashMouseClicked

    private void toDashMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDashMouseEntered

    }//GEN-LAST:event_toDashMouseEntered

    private void toBlibliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toBlibliMouseEntered
        subMenuBlibliografi.setVisible(true);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
    }//GEN-LAST:event_toBlibliMouseEntered

    private void toSrikuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toSrikuMouseEntered
        subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(true);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
    }//GEN-LAST:event_toSrikuMouseEntered

    private void toAnggoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toAnggoMouseEntered
        subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(true);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
    }//GEN-LAST:event_toAnggoMouseEntered

    private void toLaporanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLaporanMouseEntered
        subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(true);
        subMenuAdmin.setVisible(false);
    }//GEN-LAST:event_toLaporanMouseEntered

    private void empty1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empty1MouseEntered
        subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
    }//GEN-LAST:event_empty1MouseEntered

    private void empty2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empty2MouseEntered
        subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
    }//GEN-LAST:event_empty2MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited

    }//GEN-LAST:event_jPanel3MouseExited

    private void DDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DDCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DDCActionPerformed

    private void cbGMDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGMDActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbGMDActionPerformed

    private void BahasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BahasaActionPerformed
        // TODO add your handling code here:
        setValue3();
    }//GEN-LAST:event_BahasaActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        // TODO add your handling code here:
        setIndex1();
        setIndex2();
        setIndex3();
        setIndex4();
        updateData();
        Petugas_DataBuku obj = new Petugas_DataBuku();
        obj.setVisible(true);
        this.dispose();
        //reset();
    }//GEN-LAST:event_submitActionPerformed

    private void cbPenerbitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPenerbitActionPerformed
        // TODO add your handling code here:
        setValue1();
    }//GEN-LAST:event_cbPenerbitActionPerformed

    private void cbTempatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTempatActionPerformed
        // TODO add your handling code here:
        setValue2();
    }//GEN-LAST:event_cbTempatActionPerformed

    private void attachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachActionPerformed
        // TODO add your handling code here:
        attach();
    }//GEN-LAST:event_attachActionPerformed

    private void cbPengarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPengarangActionPerformed
        // TODO add your handling code here:
        setValue4();
    }//GEN-LAST:event_cbPengarangActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
        Delete();
        Petugas_DataBuku obj = new Petugas_DataBuku();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_deleteActionPerformed

    private void attach1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attach1ActionPerformed
        Petugas_DataBuku obj = new Petugas_DataBuku();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_attach1ActionPerformed

    private void toProfilPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toProfilPetugasMouseClicked
        Petugas_ProfilPetugas obj = new Petugas_ProfilPetugas();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toProfilPetugasMouseClicked

    private void toProfilPetugasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toProfilPetugasMouseEntered
        toProfilPetugas.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toProfilPetugasMouseEntered

    private void toProfilPetugasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toProfilPetugasMouseExited
        toProfilPetugas.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toProfilPetugasMouseExited

    private void toDataPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataPetugasMouseClicked
        Petugas_Data_Petugas obj = new Petugas_Data_Petugas();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDataPetugasMouseClicked

    private void toDataPetugasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataPetugasMouseEntered
        toDataPetugas.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toDataPetugasMouseEntered

    private void toDataPetugasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataPetugasMouseExited
        toDataPetugas.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toDataPetugasMouseExited

    private void toLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLoginMouseClicked
        Login obj = null;
        try {
            obj = new Login();
        } catch (IOException ex) {
            Logger.getLogger(Petugas_EditBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toLoginMouseClicked

    private void toLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLoginMouseEntered
        toLogin.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toLoginMouseEntered

    private void toLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLoginMouseExited
        toLogin.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toLoginMouseExited

    private void subMenuAdminMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subMenuAdminMouseExited
        subMenuAdmin.setVisible(false);
    }//GEN-LAST:event_subMenuAdminMouseExited

    private void subMenuLaporanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subMenuLaporanMouseExited
        subMenuLaporan.setVisible(false);
    }//GEN-LAST:event_subMenuLaporanMouseExited

    private void toDataAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataAnggotaMouseClicked
        Petugas_DataAnggota obj = new Petugas_DataAnggota();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDataAnggotaMouseClicked

    private void toDataAnggotaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataAnggotaMouseEntered
        toDataAnggota.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toDataAnggotaMouseEntered

    private void toDataAnggotaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataAnggotaMouseExited
        toDataAnggota.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toDataAnggotaMouseExited

    private void toInputAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toInputAnggotaMouseClicked
        Petugas_InputAnggota obj = new Petugas_InputAnggota();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toInputAnggotaMouseClicked

    private void toInputAnggotaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toInputAnggotaMouseEntered
        toInputAnggota.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toInputAnggotaMouseEntered

    private void toInputAnggotaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toInputAnggotaMouseExited
        toInputAnggota.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toInputAnggotaMouseExited

    private void toDataKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataKelasMouseClicked
        Petugas_DataKelas obj = new Petugas_DataKelas();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDataKelasMouseClicked

    private void toDataKelasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataKelasMouseEntered
        toDataKelas.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toDataKelasMouseEntered

    private void toDataKelasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataKelasMouseExited
        toDataKelas.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toDataKelasMouseExited

    private void toDataJurusanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataJurusanMouseClicked
        Petugas_DataJurusan obj = new Petugas_DataJurusan();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDataJurusanMouseClicked

    private void toDataJurusanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataJurusanMouseEntered
        toDataJurusan.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toDataJurusanMouseEntered

    private void toDataJurusanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataJurusanMouseExited
        toDataJurusan.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toDataJurusanMouseExited

    private void toBebasPustakaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toBebasPustakaMouseClicked
        Petugas_KonfirmasiBebasPustaka obj = new Petugas_KonfirmasiBebasPustaka();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toBebasPustakaMouseClicked

    private void toBebasPustakaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toBebasPustakaMouseEntered
        toBebasPustaka.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toBebasPustakaMouseEntered

    private void toBebasPustakaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toBebasPustakaMouseExited
        toBebasPustaka.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toBebasPustakaMouseExited

    private void subMenuAnggotaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subMenuAnggotaMouseExited
        subMenuAnggota.setVisible(false);
    }//GEN-LAST:event_subMenuAnggotaMouseExited

    private void toDataTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataTransaksiMouseClicked
        Petugas_DataTransaksi obj = new Petugas_DataTransaksi();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDataTransaksiMouseClicked

    private void toDataTransaksiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataTransaksiMouseEntered
        toDataTransaksi.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toDataTransaksiMouseEntered

    private void toDataTransaksiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataTransaksiMouseExited
        toDataTransaksi.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toDataTransaksiMouseExited

    private void toPengembalianBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toPengembalianBukuMouseClicked
        Petugas_KonfrimasiPengembalian obj = new Petugas_KonfrimasiPengembalian();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toPengembalianBukuMouseClicked

    private void toPengembalianBukuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toPengembalianBukuMouseEntered
        toPengembalianBuku.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toPengembalianBukuMouseEntered

    private void toPengembalianBukuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toPengembalianBukuMouseExited
        toPengembalianBuku.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toPengembalianBukuMouseExited

    private void toDendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDendaMouseClicked
        Petugas_dataDenda obj = new Petugas_dataDenda();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDendaMouseClicked

    private void toDendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDendaMouseEntered
        toDenda.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toDendaMouseEntered

    private void toDendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDendaMouseExited
        toDenda.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toDendaMouseExited

    private void toKonfDendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKonfDendaMouseClicked
        Petugas_KonfirmasiDenda obj = new Petugas_KonfirmasiDenda();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toKonfDendaMouseClicked

    private void toKonfDendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKonfDendaMouseEntered
        toKonfDenda.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toKonfDendaMouseEntered

    private void toKonfDendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKonfDendaMouseExited
        toKonfDenda.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toKonfDendaMouseExited

    private void toKonfDenda1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKonfDenda1MouseClicked
        Petugas_DataPengunjung obj = new Petugas_DataPengunjung();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toKonfDenda1MouseClicked

    private void toKonfDenda1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKonfDenda1MouseEntered
        toKonfDenda1.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toKonfDenda1MouseEntered

    private void toKonfDenda1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toKonfDenda1MouseExited
        toKonfDenda1.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toKonfDenda1MouseExited

    private void subMenuSirkulasiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subMenuSirkulasiMouseExited
        subMenuSirkulasi.setVisible(false);
    }//GEN-LAST:event_subMenuSirkulasiMouseExited

    private void toDataBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataBukuMouseClicked
        Petugas_DataBuku obj = new Petugas_DataBuku();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDataBukuMouseClicked

    private void toDataBukuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataBukuMouseEntered
        toDataBuku.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toDataBukuMouseEntered

    private void toDataBukuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataBukuMouseExited
        toDataBuku.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toDataBukuMouseExited

    private void toInputBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toInputBukuMouseClicked
        Petugas_InputBuku obj = new Petugas_InputBuku();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toInputBukuMouseClicked

    private void toInputBukuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toInputBukuMouseEntered
        toInputBuku.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toInputBukuMouseEntered

    private void toInputBukuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toInputBukuMouseExited
        toInputBuku.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toInputBukuMouseExited

    private void toDataPenulisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataPenulisMouseClicked
        Petugas_DataPenulis obj = new Petugas_DataPenulis();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDataPenulisMouseClicked

    private void toDataPenulisMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataPenulisMouseEntered
        toDataPenulis.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toDataPenulisMouseEntered

    private void toDataPenulisMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataPenulisMouseExited
        toDataPenulis.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toDataPenulisMouseExited

    private void toDataUsulanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataUsulanMouseClicked
        Petugas_DataUsulan obj = new Petugas_DataUsulan();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDataUsulanMouseClicked

    private void toDataUsulanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataUsulanMouseEntered
        toDataUsulan.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toDataUsulanMouseEntered

    private void toDataUsulanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataUsulanMouseExited
        toDataUsulan.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toDataUsulanMouseExited

    private void subMenuBlibliografiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subMenuBlibliografiMouseEntered
        subMenuBlibliografi.setVisible(true);
    }//GEN-LAST:event_subMenuBlibliografiMouseEntered

    private void subMenuBlibliografiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subMenuBlibliografiMouseExited

        subMenuBlibliografi.setVisible(false);
    }//GEN-LAST:event_subMenuBlibliografiMouseExited

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
        String a = null;
        String b = null;
        String img = null;
        Statement stb;
        ResultSet rsa;
        int count = 0;
        try{
            stb = CC.createStatement();
            String sql = "SELECT * FROM profile";
            rsa = stb.executeQuery(sql);
            if (rsa.next()){
                a = rsa.getString("profil");
                b = rsa.getString("alamat");
                img = rsa.getString("logo");
            }
            String icon = "src/Uploads/Foto/Logo/"+img+"";
            HashMap param = new HashMap();
            param.put("instansi", a);
            param.put("alamat", b);
            param.put("img", icon);

            File namaFile = new File("src/Laporan/LaporanPeminjam.jasper");
            InputStream file = new FileInputStream(new File("src/Laporan/LaporanPeminjam.jrxml"));
            JasperDesign jd = JRXmlLoader.load(file);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,param,CC);
            JasperViewer.viewReport(jp, false);

        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jLabel21MouseClicked

    private void toLapPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapPeminjamanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_toLapPeminjamanMouseClicked

    private void toLapPeminjamanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapPeminjamanMouseEntered
        toLapPeminjaman.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toLapPeminjamanMouseEntered

    private void toLapPeminjamanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapPeminjamanMouseExited
        toLapPeminjaman.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toLapPeminjamanMouseExited

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
        String a = null;
        String b = null;
        String img = null;
        Statement stb;
        ResultSet rsa;
        int count = 0;
        try{
            stb = CC.createStatement();
            String sql = "SELECT * FROM profile";
            rsa = stb.executeQuery(sql);
            if (rsa.next()){
                a = rsa.getString("profil");
                b = rsa.getString("alamat");
                img = rsa.getString("logo");
            }
            String icon = "src/Uploads/Foto/Logo/"+img+"";
            HashMap param = new HashMap();
            param.put("instansi", a);
            param.put("alamat", b);
            param.put("img", icon);

            File namaFile = new File("src/Laporan/LaporanBukuHilang.jasper");
            InputStream file = new FileInputStream(new File("src/Laporan/LaporanBukuHilang.jrxml"));
            JasperDesign jd = JRXmlLoader.load(file);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,param,CC);
            JasperViewer.viewReport(jp, false);

        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jLabel22MouseClicked

    private void toLapBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapBukuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_toLapBukuMouseClicked

    private void toLapBukuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapBukuMouseEntered
        toLapBuku.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toLapBukuMouseEntered

    private void toLapBukuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapBukuMouseExited
        toLapBuku.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toLapBukuMouseExited

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
        String a = null;
        String b = null;
        String img = null;
        int count = 0;
        Statement stb;
        ResultSet rsa;
        try{
            stb = CC.createStatement();
            String sql = "SELECT * FROM profile";
            rsa = stb.executeQuery(sql);
            if (rsa.next()){
                a = rsa.getString("profil");
                b = rsa.getString("alamat");
                img = rsa.getString("logo");
            }
            String icon = "src/Uploads/Foto/Logo/"+img+"";
            HashMap param = new HashMap();
            param.put("instansi", a);
            param.put("alamat", b);
            param.put("img", icon);

            //File namaFile = new File("src/Laporan/LaporanAnggota.jasper");
            InputStream file = new FileInputStream(new File("src/Laporan/LaporanAnggota.jrxml"));
            JasperDesign jd = JRXmlLoader.load(file);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            //String jr = "src/Laporan/LaporanAnggota.jasper";
            JasperPrint jp = JasperFillManager.fillReport(jr,param,CC);
            //JasperViewer.viewReport(jp, false);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jLabel23MouseClicked

    private void toLapAnggotaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapAnggotaMouseEntered
        toLapAnggota.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toLapAnggotaMouseEntered

    private void toLapAnggotaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapAnggotaMouseExited
        toLapAnggota.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toLapAnggotaMouseExited

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        // TODO add your handling code here:
        String a = null;
        String b = null;
        String img = null;
        int count = 0;
        Statement stb;
        ResultSet rsa;
        try{
            stb = CC.createStatement();
            String sql = "SELECT * FROM profile";
            rsa = stb.executeQuery(sql);
            if (rsa.next()){
                a = rsa.getString("profil");
                b = rsa.getString("alamat");
                img = rsa.getString("logo");
            }
            String icon = "src/Uploads/Foto/Logo/"+img+"";
            HashMap param = new HashMap();
            param.put("instansi", a);
            param.put("alamat", b);
            param.put("img", icon);

            File namaFile = new File("src/Laporan/LaporanPengembalian.jasper");
            InputStream file = new FileInputStream(new File("src/Laporan/LaporanPengembalian.jrxml"));
            JasperDesign jd = JRXmlLoader.load(file);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,param,CC);
            JasperViewer.viewReport(jp, false);

        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jLabel24MouseClicked

    private void toLapPengembalianMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapPengembalianMouseEntered
        toLapPengembalian.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toLapPengembalianMouseEntered

    private void toLapPengembalianMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapPengembalianMouseExited
        toLapPengembalian.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toLapPengembalianMouseExited

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        // TODO add your handling code here:
        String a = null;
        String b = null;
        String img = null;
        int count = 0;
        Statement stb;
        ResultSet rsa;
        try{
            stb = CC.createStatement();
            String sql = "SELECT * FROM profile";
            rsa = stb.executeQuery(sql);
            if (rsa.next()){
                a = rsa.getString("profil");
                b = rsa.getString("alamat");
                img = rsa.getString("logo");
            }
            String icon = "src/Uploads/Foto/Logo/"+img+"";
            HashMap param = new HashMap();
            param.put("instansi", a);
            param.put("alamat", b);
            param.put("img", icon);

            File namaFile = new File("src/Laporan/LaporanDenda.jasper");
            InputStream file = new FileInputStream(new File("src/Laporan/LaporanDenda.jrxml"));
            JasperDesign jd = JRXmlLoader.load(file);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,param,CC);
            JasperViewer.viewReport(jp, false);

        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jLabel25MouseClicked

    private void toLapDendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapDendaMouseEntered
        toLapDenda.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toLapDendaMouseEntered

    private void toLapDendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toLapDendaMouseExited
        toLapDenda.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toLapDendaMouseExited

    private void DDCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DDCKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
             setIndex1();
            setIndex2();
            setIndex3();
            setIndex4();
            updateData();
            Petugas_DataBuku obj = new Petugas_DataBuku();
            obj.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_DDCKeyPressed

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
            java.util.logging.Logger.getLogger(Petugas_EditBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Petugas_EditBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Petugas_EditBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Petugas_EditBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Petugas_EditBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Bahasa;
    private javax.swing.JTextField DDC;
    private javax.swing.JTextArea Deskripsi;
    private javax.swing.JTextField Edisi;
    private javax.swing.JTextField ISBN;
    private javax.swing.JTextField Judul;
    private javax.swing.JTextField JudulSeri;
    private javax.swing.JTextField NoPanggil;
    private javax.swing.JTextField Penerbit;
    private javax.swing.JTextField Pengarang;
    private javax.swing.JTextField TahunTerbit;
    private javax.swing.JTextField TempatTerbit;
    private javax.swing.JButton attach;
    private javax.swing.JButton attach1;
    private javax.swing.JComboBox<String> cbGMD;
    private javax.swing.JComboBox<String> cbPenerbit;
    private javax.swing.JComboBox<String> cbPengarang;
    private javax.swing.JComboBox<String> cbTempat;
    private javax.swing.JButton delete;
    private javax.swing.JPanel empty1;
    private javax.swing.JPanel empty2;
    private javax.swing.JLabel img;
    private javax.swing.JLabel img1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField lang;
    private javax.swing.JLabel lbl_bahasa;
    private javax.swing.JLabel lbl_ddc;
    private javax.swing.JLabel lbl_edisi;
    private javax.swing.JLabel lbl_fisik;
    private javax.swing.JLabel lbl_gmd;
    private javax.swing.JLabel lbl_isbn;
    private javax.swing.JLabel lbl_judul;
    private javax.swing.JLabel lbl_judulseri;
    private javax.swing.JLabel lbl_nopanggil;
    private javax.swing.JLabel lbl_pengarang;
    private javax.swing.JLabel lbl_publisher;
    private javax.swing.JLabel lbl_tempatTerbit;
    private javax.swing.JLabel lbl_tterbit;
    private javax.swing.JPanel panelImg;
    private javax.swing.JPanel subMenuAdmin;
    private javax.swing.JPanel subMenuAnggota;
    private javax.swing.JPanel subMenuBlibliografi;
    private javax.swing.JPanel subMenuLaporan;
    private javax.swing.JPanel subMenuSirkulasi;
    private javax.swing.JButton submit;
    private javax.swing.JLabel toAdmin;
    private javax.swing.JLabel toAnggo;
    private javax.swing.JPanel toBebasPustaka;
    private javax.swing.JLabel toBlibli;
    private javax.swing.JLabel toDash;
    private javax.swing.JPanel toDataAnggota;
    private javax.swing.JPanel toDataBuku;
    private javax.swing.JPanel toDataJurusan;
    private javax.swing.JPanel toDataKelas;
    private javax.swing.JPanel toDataPenulis;
    private javax.swing.JPanel toDataPetugas;
    private javax.swing.JPanel toDataTransaksi;
    private javax.swing.JPanel toDataUsulan;
    private javax.swing.JPanel toDenda;
    private javax.swing.JPanel toInputAnggota;
    private javax.swing.JPanel toInputBuku;
    private javax.swing.JPanel toKonfDenda;
    private javax.swing.JPanel toKonfDenda1;
    private javax.swing.JPanel toLapAnggota;
    private javax.swing.JPanel toLapBuku;
    private javax.swing.JPanel toLapDenda;
    private javax.swing.JPanel toLapPeminjaman;
    private javax.swing.JPanel toLapPengembalian;
    private javax.swing.JLabel toLaporan;
    private javax.swing.JPanel toLogin;
    private javax.swing.JPanel toPengembalianBuku;
    private javax.swing.JPanel toProfilPetugas;
    private javax.swing.JLabel toSriku;
    private javax.swing.JLabel toUser;
    // End of variables declaration//GEN-END:variables
}
