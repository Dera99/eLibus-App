/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpustakaan.smk.pgri.pkg1.jakarta;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
public class Petugas_EditAnggota extends javax.swing.JFrame {
    
    /**
     * Creates new form Petugas_EditAnggota
     */
    ResultSet rs = null;
    Connection CC = null;
    PreparedStatement pst = null;
    public Petugas_EditAnggota() {
        initComponents();
        CC = new koneksi().connect();
        subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
        readCB();
        readCBJurusan();
        cbkelas.setEnabled(false);
        nis.setEnabled(false);
        userLogin();
        toogle(false);
        getAdd();
        telepon.setEditable(false);
        email.setEditable(false);
         Locale local = new Locale("id", "ID");
        Locale.setDefault(local);
    }
   public String sql;
   
    private void userLogin(){
    toUser.setText(UserSession.getUserLogin());
    }
    
    public void toogle(boolean f){
        nis.setEditable(f);
        nama.setEditable(f);
        cbTingkat.setVisible(f);
        cbJurusan.setVisible(f);
        cbkelas.setVisible(f);
        alamat.setEditable(f);
        ttl.setEditable(f);
        jkaname.setVisible(f);
        add1.setEditable(f);
        add2.setEditable(f);
        add3.setEditable(f);
        add4.setEditable(f);
        add5.setEditable(f);
        jButton2.setEnabled(f);
        jButton3.setEnabled(f);
    }
   
   public void readCB(){
       try{
           cbTingkat.addItem("X");
           cbTingkat.addItem("XI");
           cbTingkat.addItem("XII");
       }catch (Exception e){
           e.printStackTrace();
       }
   }
    public int value;
    public void readCBJurusan(){
       try{
           Statement stat = CC.createStatement();
           sql = "SELECT * FROM Jurusan ";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String jurusan = rs.getString("Jurusan");
               cbJurusan.addItem(jurusan);
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       }
   }
    public void Refresh(){
        cbkelas.removeAllItems();
        cbkelas.addItem("Kelas");
    }
    public void readCBKelas(){
       try{
           Statement stat = CC.createStatement();
           sql = "SELECT * FROM Kelas JOIN Jurusan ON Kelas.IdJurusan = Jurusan.IdJurusan WHERE Jurusan.Jurusan ='"+String.valueOf(cbJurusan.getSelectedItem())+"' AND Kelas.TingkatKelas = '"+ String.valueOf(cbTingkat.getSelectedItem())  +"'";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String kelas = rs.getString("Kelas.Kelas");
               cbkelas.addItem(kelas);
           }
           rs.last();
           int jumlah = rs.getRow();
           rs.first();
       }catch (Exception e){
       }
   }
    public void getId(){
        try{
           Statement stat = CC.createStatement();
           sql = "SELECT IdKelas FROM Kelas JOIN Jurusan ON Kelas.IdJurusan = Jurusan.IdJurusan WHERE Jurusan.Jurusan ='"+String.valueOf(cbJurusan.getSelectedItem())+"' AND Kelas.TingkatKelas = '"+ String.valueOf(cbTingkat.getSelectedItem())  +"' AND Kelas.Kelas = '"+ String.valueOf(cbkelas.getSelectedItem()) +"'";
           ResultSet rs = stat.executeQuery(sql);
           if(rs.next()){
               value = rs.getInt("IdKelas");
           }
       }catch (Exception e){
       }
    
    }
    private void getAdd(){
        try{
         JTextField[]form = {add1,add2,add3,add4,add5};
         JLabel[]label = {label1,label2,label3,label4,label5};
         PreparedStatement stmt = CC.prepareStatement("SELECT * FROM adjust ",
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
                 int panelq;
                   for (panelq =0;panelq <5;panelq++){
                       label[panelq].setVisible(false);
                       form[panelq].setVisible(false);
                   }
             //end of iterate panel     
            Object array2D[][] = new Object[rowcount][];
            do {
                 array2D[rowindex] = new Object[numberOfColumns];
                  for (int i = 0; i < numberOfColumns; i++) {
                    array2D[rowindex][i] = rs.getObject(i + 1);
                    }
                  if(rs.getInt("Status")>0){
                    label[rowindex].setVisible(true);
                    form[rowindex].setVisible(true);
                  }
                    label[rowindex].setText(rs.getString("tName"));
                  
                  
                //System.out.println("array2D[" + rowindex + "] = " + Arrays.toString(array2D[rowindex])); 
             rowindex++;
                } while (rs.next());              
        
        }catch(Exception e){
             e.printStackTrace();
        }
    }
   public void refreshData(){
       try{
           Statement stat = CC.createStatement();
           String ns = nis.getText();
           sql = "SELECT * FROM anggota INNER JOIN kelas ON anggota.IdKelas = kelas.IdKelas WHERE anggota.Nis="+ns+"";
           ResultSet rs = stat.executeQuery(sql);
           if(rs.next()){
               String k = rs.getString("kelas.TingkatKelas");
               String l = rs.getString("kelas.IdJurusan");
               String s = rs.getString("kelas.kelas");
               nis.setText(ns);
               nama.setText(rs.getString("anggota.Nama"));
               kls.setText(k+l+s);
               alamat.setText(rs.getString("anggota.Alamat"));
           }
       }catch(Exception e){
       }
   }
   public void SOP(String a){
       System.out.println(a);
   }
   public void updateData(){
       try{
            String ns = nis.getText();
            String nm = nama.getText();
            String ks = kls.getText();
            String al = alamat.getText();
            String tl = ttl.getText();
            String ad1 = label1.getText().replaceAll("\\s+","");
            String ad2 = label2.getText().replaceAll("\\s+","");
            String ad3 = label3.getText().replaceAll("\\s+","");
            String ad4 = label4.getText().replaceAll("\\s+","");
            String ad5 = label5.getText().replaceAll("\\s+","");
            char jk = jkaname.getSelectedItem().toString().charAt(0);
           if(value<1){
               sql = "UPDATE anggota SET anggota.Nama ='"+nm+"', anggota.ttl='"+ tl +"', anggota.Alamat='"+al+"', anggota.jk ='"+ jk +"', anggota."+ad1+" = '"+ add1.getText() +"', anggota."+ad2+" = '"+ add2.getText() +"', anggota."+ad3+" = '"+ add3.getText() +"', anggota."+ad4+" = '"+ add4.getText() +"', anggota."+ad5+" = '"+ add5.getText() +"' WHERE Nis ="+ns+"";
           
           }else{
            sql = "UPDATE anggota SET anggota.Nama ='"+nm+"', anggota.IdKelas="+value+", anggota.ttl='"+ tl +"', anggota.Alamat='"+al+"', anggota.jk ='"+ jk +"', anggota."+ad1+" = '"+ add1.getText() +"', anggota."+ad2+" = '"+ add2.getText() +"', anggota."+ad3+" = '"+ add3.getText() +"', anggota."+ad4+" = '"+ add4.getText() +"', anggota."+ad5+" = '"+ add5.getText() +"' WHERE Nis ="+ns+"";
            }
           pst = CC.prepareStatement(sql);
           pst.execute();
           JOptionPane.showMessageDialog(null, "Data Anggota Berhasil Terupdate");
           pst.close();
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
        jLabel14 = new javax.swing.JLabel();
        toDataJurusan = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
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
        jLabel17 = new javax.swing.JLabel();
        toInputBuku = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        toDataPenulis = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        toDataUsulan = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        alamat = new javax.swing.JTextArea();
        nis = new javax.swing.JTextField();
        nama = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        kls = new javax.swing.JLabel();
        cbTingkat = new javax.swing.JComboBox<>();
        cbJurusan = new javax.swing.JComboBox<>();
        cbkelas = new javax.swing.JComboBox<>();
        submit2 = new javax.swing.JButton();
        add1 = new javax.swing.JTextField();
        label1 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        add2 = new javax.swing.JTextField();
        label3 = new javax.swing.JLabel();
        add3 = new javax.swing.JTextField();
        label4 = new javax.swing.JLabel();
        add4 = new javax.swing.JTextField();
        add5 = new javax.swing.JTextField();
        label5 = new javax.swing.JLabel();
        jkaname = new javax.swing.JComboBox<>();
        JK = new javax.swing.JLabel();
        lbl_ttl = new javax.swing.JLabel();
        ttl = new javax.swing.JTextField();
        tt = new javax.swing.JRadioButton();
        label6 = new javax.swing.JLabel();
        telepon = new javax.swing.JTextField();
        label7 = new javax.swing.JLabel();
        label8 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });
        jPanel1.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(229, 231, 238));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
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
        toDataPetugas.setBounds(0, 40, 154, 40);

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
        toLogin.setBounds(0, 80, 154, 40);

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
        toInputAnggota.setBounds(0, 40, 152, 40);

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

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel14.setText("Data Kelas");

        javax.swing.GroupLayout toDataKelasLayout = new javax.swing.GroupLayout(toDataKelas);
        toDataKelas.setLayout(toDataKelasLayout);
        toDataKelasLayout.setHorizontalGroup(
            toDataKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataKelasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        toDataKelasLayout.setVerticalGroup(
            toDataKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuAnggota.add(toDataKelas);
        toDataKelas.setBounds(0, 80, 152, 40);

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

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel15.setText("Data Jurusan");

        javax.swing.GroupLayout toDataJurusanLayout = new javax.swing.GroupLayout(toDataJurusan);
        toDataJurusan.setLayout(toDataJurusanLayout);
        toDataJurusanLayout.setHorizontalGroup(
            toDataJurusanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataJurusanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        toDataJurusanLayout.setVerticalGroup(
            toDataJurusanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        subMenuAnggota.add(toDataJurusan);
        toDataJurusan.setBounds(0, 120, 152, 40);

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
        toBebasPustaka.setBounds(0, 160, 152, 40);

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
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
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
                .addContainerGap(189, Short.MAX_VALUE))
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
            public void mouseExited(java.awt.event.MouseEvent evt) {
                subMenuBlibliografisubMenuBlibliografiMouseExited(evt);
            }
        });
        subMenuBlibliografi.setLayout(null);

        toDataBuku.setBackground(new java.awt.Color(229, 231, 238));
        toDataBuku.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        toDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toDataBukutoDataBukuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toDataBukutoDataBukuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toDataBukutoDataBukuMouseExited(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel17.setText("Data Buku");

        javax.swing.GroupLayout toDataBukuLayout = new javax.swing.GroupLayout(toDataBuku);
        toDataBuku.setLayout(toDataBukuLayout);
        toDataBukuLayout.setHorizontalGroup(
            toDataBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        toDataBukuLayout.setVerticalGroup(
            toDataBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
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

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel30.setText("Input Buku");

        javax.swing.GroupLayout toInputBukuLayout = new javax.swing.GroupLayout(toInputBuku);
        toInputBuku.setLayout(toInputBukuLayout);
        toInputBukuLayout.setHorizontalGroup(
            toInputBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toInputBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        toInputBukuLayout.setVerticalGroup(
            toInputBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toInputBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        subMenuBlibliografi.add(toInputBuku);
        toInputBuku.setBounds(0, 40, 150, 47);

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

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel32.setText("Data Penulis");

        javax.swing.GroupLayout toDataPenulisLayout = new javax.swing.GroupLayout(toDataPenulis);
        toDataPenulis.setLayout(toDataPenulisLayout);
        toDataPenulisLayout.setHorizontalGroup(
            toDataPenulisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataPenulisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        toDataPenulisLayout.setVerticalGroup(
            toDataPenulisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        subMenuBlibliografi.add(toDataPenulis);
        toDataPenulis.setBounds(0, 80, 152, 43);

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

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel33.setText("Usulan Buku");

        javax.swing.GroupLayout toDataUsulanLayout = new javax.swing.GroupLayout(toDataUsulan);
        toDataUsulan.setLayout(toDataUsulanLayout);
        toDataUsulanLayout.setHorizontalGroup(
            toDataUsulanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toDataUsulanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        toDataUsulanLayout.setVerticalGroup(
            toDataUsulanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        subMenuBlibliografi.add(toDataUsulan);
        toDataUsulan.setBounds(0, 120, 150, 43);

        jPanel1.add(subMenuBlibliografi);
        subMenuBlibliografi.setBounds(80, 140, 150, 170);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Profil Anggota");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(110, 30, 350, 30);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Alamat");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(110, 340, 70, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("NIS");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(110, 150, 80, 20);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Nama");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(110, 210, 70, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Kelas");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(110, 270, 70, 20);

        alamat.setColumns(20);
        alamat.setRows(5);
        jScrollPane1.setViewportView(alamat);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(230, 340, 500, 90);

        nis.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(nis);
        nis.setBounds(230, 150, 500, 23);

        nama.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaActionPerformed(evt);
            }
        });
        jPanel1.add(nama);
        nama.setBounds(230, 210, 500, 23);

        jButton2.setText("Hapus Anggota");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(1040, 650, 120, 25);

        jButton3.setText("Submit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(1190, 650, 70, 25);

        kls.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        kls.setText("jLabel2");
        jPanel1.add(kls);
        kls.setBounds(230, 270, 70, 20);

        cbTingkat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbTingkat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tingkat" }));
        cbTingkat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTingkatActionPerformed(evt);
            }
        });
        jPanel1.add(cbTingkat);
        cbTingkat.setBounds(300, 270, 110, 22);

        cbJurusan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbJurusan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jurusan" }));
        cbJurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbJurusanActionPerformed(evt);
            }
        });
        jPanel1.add(cbJurusan);
        cbJurusan.setBounds(420, 270, 240, 22);

        cbkelas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kelas" }));
        cbkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelasActionPerformed(evt);
            }
        });
        jPanel1.add(cbkelas);
        cbkelas.setBounds(670, 270, 60, 22);

        submit2.setText("Kembali");
        submit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit2ActionPerformed(evt);
            }
        });
        jPanel1.add(submit2);
        submit2.setBounds(100, 650, 90, 25);

        add1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(add1);
        add1.setBounds(900, 270, 350, 23);

        label1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label1.setText("NIS");
        jPanel1.add(label1);
        label1.setBounds(770, 270, 130, 20);

        label2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label2.setText("Nama");
        jPanel1.add(label2);
        label2.setBounds(770, 330, 130, 20);

        add2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        add2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add2ActionPerformed(evt);
            }
        });
        jPanel1.add(add2);
        add2.setBounds(900, 330, 350, 23);
        add2.getAccessibleContext().setAccessibleDescription("");

        label3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label3.setText("NIS");
        jPanel1.add(label3);
        label3.setBounds(770, 390, 130, 20);

        add3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(add3);
        add3.setBounds(900, 390, 350, 23);

        label4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label4.setText("Nama");
        jPanel1.add(label4);
        label4.setBounds(770, 450, 130, 20);

        add4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        add4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add4ActionPerformed(evt);
            }
        });
        jPanel1.add(add4);
        add4.setBounds(900, 450, 350, 23);

        add5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        add5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add5ActionPerformed(evt);
            }
        });
        jPanel1.add(add5);
        add5.setBounds(900, 510, 350, 23);

        label5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label5.setText("Nama");
        jPanel1.add(label5);
        label5.setBounds(770, 510, 130, 20);

        jkaname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jkaname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laki-Laki", "Perempuan", " " }));
        jkaname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkanameActionPerformed(evt);
            }
        });
        jPanel1.add(jkaname);
        jkaname.setBounds(310, 450, 90, 22);

        JK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JK.setText("Jenis Kelamin");
        jPanel1.add(JK);
        JK.setBounds(100, 450, 90, 20);

        lbl_ttl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ttl.setText("TTL");
        jPanel1.add(lbl_ttl);
        lbl_ttl.setBounds(100, 510, 90, 20);

        ttl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ttl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttlActionPerformed(evt);
            }
        });
        jPanel1.add(ttl);
        ttl.setBounds(230, 510, 500, 23);

        tt.setBackground(new java.awt.Color(255, 255, 255));
        tt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tt.setText("Edit Data");
        tt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttActionPerformed(evt);
            }
        });
        jPanel1.add(tt);
        tt.setBounds(110, 100, 93, 23);

        label6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label6.setText("Laki-Laki");
        jPanel1.add(label6);
        label6.setBounds(230, 450, 80, 20);

        telepon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(telepon);
        telepon.setBounds(900, 210, 350, 23);

        label7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label7.setText("No Telepon");
        jPanel1.add(label7);
        label7.setBounds(770, 210, 130, 20);

        label8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label8.setText("Alamat Email");
        jPanel1.add(label8);
        label8.setBounds(770, 150, 130, 20);

        email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(email);
        email.setBounds(900, 150, 350, 23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
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

    private void namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaActionPerformed

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
    }//GEN-LAST:event_jPanel1MouseEntered

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         try {
             String value = nis.getText();
             String delete = "DELETE FROM anggota WHERE `Nis`='"+value+"'";
             pst = CC.prepareStatement(delete);
             pst.execute();
             JOptionPane.showMessageDialog(null, "Data Anggota Berhasil Terhapus");
             pst.close();
             this.dispose();
             Petugas_DataAnggota obj = new Petugas_DataAnggota();
             obj.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);    
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            Logger.getLogger(Petugas_EditAnggota.class.getName()).log(Level.SEVERE, null, ex);
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       getId();
        updateData();
        //refreshData();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cbTingkatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTingkatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTingkatActionPerformed

    private void cbJurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJurusanActionPerformed
        Refresh();
        readCBKelas();
        cbkelas.setEnabled(true);
    }//GEN-LAST:event_cbJurusanActionPerformed

    private void cbkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelasActionPerformed
        // TODO add your handling code here:
        Object select = cbkelas.getSelectedItem();
        System.out.print(cbkelas.getSelectedItem());
    }//GEN-LAST:event_cbkelasActionPerformed

    private void toDataBukutoDataBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataBukutoDataBukuMouseClicked
        Petugas_DataBuku obj = new Petugas_DataBuku();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_toDataBukutoDataBukuMouseClicked

    private void toDataBukutoDataBukuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataBukutoDataBukuMouseEntered
        toDataBuku.setBackground(new java.awt.Color(188,190,208));
    }//GEN-LAST:event_toDataBukutoDataBukuMouseEntered

    private void toDataBukutoDataBukuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDataBukutoDataBukuMouseExited
        toDataBuku.setBackground(new java.awt.Color(229, 231, 238));
    }//GEN-LAST:event_toDataBukutoDataBukuMouseExited

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

    private void subMenuBlibliografisubMenuBlibliografiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subMenuBlibliografisubMenuBlibliografiMouseExited

        subMenuBlibliografi.setVisible(false);
    }//GEN-LAST:event_subMenuBlibliografisubMenuBlibliografiMouseExited

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
       subMenuBlibliografi.setVisible(true);
    }//GEN-LAST:event_jPanel3MouseEntered

    private void submit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit2ActionPerformed
        Petugas_DataAnggota obj = new Petugas_DataAnggota();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_submit2ActionPerformed

    private void add2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add2ActionPerformed

    private void add4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add4ActionPerformed

    private void add5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add5ActionPerformed

    private void jkanameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkanameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jkanameActionPerformed

    private void ttlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttlActionPerformed

    private void ttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttActionPerformed
        if(tt.isSelected()){
        toogle(true);}
        else{
        toogle(false);
        }
    }//GEN-LAST:event_ttActionPerformed

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
            java.util.logging.Logger.getLogger(Petugas_EditAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Petugas_EditAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Petugas_EditAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Petugas_EditAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Petugas_EditAnggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JK;
    public javax.swing.JTextField add1;
    public javax.swing.JTextField add2;
    public javax.swing.JTextField add3;
    public javax.swing.JTextField add4;
    public javax.swing.JTextField add5;
    public javax.swing.JTextArea alamat;
    private javax.swing.JComboBox<String> cbJurusan;
    private javax.swing.JComboBox<String> cbTingkat;
    private javax.swing.JComboBox<String> cbkelas;
    public javax.swing.JTextField email;
    private javax.swing.JPanel empty1;
    private javax.swing.JPanel empty2;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JComboBox<String> jkaname;
    public javax.swing.JLabel kls;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel label5;
    public javax.swing.JLabel label6;
    protected javax.swing.JLabel label7;
    public javax.swing.JLabel label8;
    private javax.swing.JLabel lbl_ttl;
    public javax.swing.JTextField nama;
    public javax.swing.JTextField nis;
    private javax.swing.JPanel subMenuAdmin;
    private javax.swing.JPanel subMenuAnggota;
    private javax.swing.JPanel subMenuBlibliografi;
    private javax.swing.JPanel subMenuLaporan;
    private javax.swing.JPanel subMenuSirkulasi;
    private javax.swing.JButton submit2;
    public javax.swing.JTextField telepon;
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
    private javax.swing.JRadioButton tt;
    public javax.swing.JTextField ttl;
    // End of variables declaration//GEN-END:variables
}
