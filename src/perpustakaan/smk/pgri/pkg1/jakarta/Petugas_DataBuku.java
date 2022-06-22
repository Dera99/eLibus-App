package perpustakaan.smk.pgri.pkg1.jakarta;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author Atthoriq
 */
public class Petugas_DataBuku extends javax.swing.JFrame {

    /**
     * Creates new form Petugas_DataBuku
     */
    ResultSet rs = null;
    Connection CC = null;
    PreparedStatement pst = null;
    public String sql;
    public Statement stb;
    ResultSet rsa=null;
    public Petugas_DataBuku() {
        initComponents();
        sort.setVisible(false);
        CC = new koneksi().connect();
         subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
        userLogin();
        if(a == null){
        a = "SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id INNER JOIN gmd ON gmd.gmd_id = new_bliblio.IdGMD INNER JOIN mst_publisher ON mst_publisher.publisher_id = new_bliblio.IdPublisher INNER JOIN mst_language ON mst_language.language_id = new_bliblio.IdLanguage"; 
        }
        showtable(a);
         Locale local = new Locale("id", "ID");
        Locale.setDefault(local);
    }
    private void userLogin(){
        toUser.setText(UserSession.getUserLogin());
    }
    public String a;
    public void showtable(String SSS){
        DefaultTableModel model = new DefaultTableModel() ;
          model.addColumn("No");
          model.addColumn("No Panggil");
          model.addColumn("Judul Buku");
          model.addColumn("GMD");
          model.addColumn("Edisi");
          model.addColumn("ISBN");
          model.addColumn("Penulis");
          model.addColumn("Penerbit");
          model.addColumn("Tahun Terbit");
          model.addColumn("Judul Seri");
          model.addColumn("Bahasa");
          
          try{
              Statement stat = CC.createStatement();
              int no = 1;
              String SQL = SSS;
              ResultSet rs = stat.executeQuery(SQL);
              while(rs.next()){
                model.addRow(new Object[]{no++,rs.getString("new_bliblio.call_number"),
                rs.getString("new_bliblio.Judul"),
                rs.getString("gmd.gmd_name"),
                rs.getString("new_bliblio.Edisi"),
                rs.getString("new_bliblio.isbn_issn"),
                rs.getString("mst_author.author_name"),
                rs.getString("mst_publisher.publisher_name"),
                rs.getString("new_bliblio.PublisherYear"),
                rs.getString("new_bliblio.SeriesTitle"),
                rs.getString("mst_language.language_name")});
                tblBuku.setModel(model);
              }
          }catch(Exception e){
              JOptionPane.showMessageDialog(null, e);
          }
    }

    public String ext;
    public File csv;
    public void chooseDir() throws IOException, SQLException{
        JFileChooser f = new JFileChooser();
         f.setFileSelectionMode(JFileChooser.FILES_ONLY); 
         FileNameExtensionFilter fnef = new FileNameExtensionFilter("CSV (Comma delimited)(*.csv)","csv");
         f.setFileFilter(fnef);
         f.setAcceptAllFileFilterUsed(false);
         int excelChooser = f.showSaveDialog(null);
            if (excelChooser == JFileChooser.APPROVE_OPTION) {
                csv=f.getSelectedFile();
                ext =".csv";
                if(!f.getSelectedFile().getName().endsWith(ext)){
                    csv=new File(csv+ext);
    
                }
                exportCSV();
            //JOptionPane.showMessageDialog(null, "Import Data Berhasil Ditambahkan, Silahkan Tekan Submit Untuk Menyimpan !!");
              System.out.println(csv);
        }
         
    }

    private void exportCSV() throws SQLException, IOException{
        try{
         String[] entries = {"Judul","GMD","Edisi","ISBN","Penerbit","Tahun Terbit","Deskripsi Fisik","Judul Seri","No. Panggil","Bahasa","Tempat terbit","Klasifikasi","Abstrak/Catatan","Foto","Pernyataan tanggung jawab","Penulis","Subjek","Exemplar(barcode) \t"};
         CSVPrinter printer = new CSVPrinter(new FileWriter(csv),CSVFormat.EXCEL.withHeader(entries));
        PreparedStatement stmt = CC.prepareStatement("SELECT * FROM new_bliblio INNER JOIN gmd ON new_bliblio.IdGMD=gmd.gmd_id "
                    + "INNER JOIN mst_publisher ON mst_publisher.publisher_id=new_bliblio.IdPublisher "
                    + "INNER JOIN mst_language ON mst_language.language_id=new_bliblio.IdLanguage "
                    + "INNER JOIN mst_place ON mst_place.place_id=new_bliblio.TempatTerbit "
                    + "INNER JOIN Item ON Item.biblio_id=new_bliblio.IdBliblio "
                    + "INNER JOIN mst_author ON mst_author.author_id=new_bliblio.author_id", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
          ResultSet rs = stmt.executeQuery();
          ResultSetMetaData Mdata = (ResultSetMetaData) rs.getMetaData();
          String data[] = new String[18];
          while(rs.next()){
           data[0] = rs.getString("Judul");
           data[1] = rs.getString("gmd.gmd_name");
           data[2] = rs.getString("Edisi");;
           data[3] = rs.getString("isbn_issn");
           data[4] = rs.getString("mst_publisher.publisher_name");
           data[5] = rs.getString("PublisherYear");
           data[6] = rs.getString("Notes");
           data[7] = rs.getString("SeriesTitle");
           data[8] = rs.getString("call_number");
           data[9] = rs.getString("mst_language.language_name");
           data[10] = rs.getString("mst_place.place_name");
           data[11] = rs.getString("Klasifikasi");
           data[12] = rs.getString("abstrak");
           data[13] = rs.getString("image");
           data[14] = rs.getString("penanggung");
           data[15] = rs.getString("mst_author.author_name");
           data[16] = rs.getString("subjek");
           data[17] = rs.getString("Item.item_code").replaceAll("[<>]", "");
           printer.printRecord(data);
          }
        printer.flush();
         printer.close();
         JOptionPane.showMessageDialog(null, "Data Berhasil Di Export");
        }catch(IOException ex){
            ex.printStackTrace();
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

        jPanel2 = new javax.swing.JPanel();
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
        sort = new javax.swing.JPanel();
        nPanggil = new javax.swing.JLabel();
        judul = new javax.swing.JLabel();
        Tanggal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Active = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBuku = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
        });
        jPanel2.setLayout(null);

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

        jPanel2.add(jPanel3);
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

        jPanel2.add(subMenuAdmin);
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

        jPanel2.add(subMenuLaporan);
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

        jPanel2.add(subMenuAnggota);
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
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
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

        jPanel2.add(subMenuSirkulasi);
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

        jPanel2.add(subMenuBlibliografi);
        subMenuBlibliografi.setBounds(80, 140, 150, 170);

        sort.setBackground(new java.awt.Color(255, 255, 255));
        sort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sortMouseExited(evt);
            }
        });

        nPanggil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nPanggil.setText("No Panggil");
        nPanggil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nPanggilMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nPanggilMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nPanggilMouseExited(evt);
            }
        });

        judul.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        judul.setText("Judul");
        judul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                judulMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                judulMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                judulMouseExited(evt);
            }
        });

        Tanggal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tanggal.setText("Tanggal");
        Tanggal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TanggalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TanggalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TanggalMouseExited(evt);
            }
        });

        javax.swing.GroupLayout sortLayout = new javax.swing.GroupLayout(sort);
        sort.setLayout(sortLayout);
        sortLayout.setHorizontalGroup(
            sortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sortLayout.createSequentialGroup()
                .addGroup(sortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(judul, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 40, Short.MAX_VALUE))
            .addComponent(nPanggil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sortLayout.setVerticalGroup(
            sortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sortLayout.createSequentialGroup()
                .addComponent(nPanggil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(judul)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Tanggal)
                .addContainerGap())
        );

        jPanel2.add(sort);
        sort.setBounds(170, 40, 90, 60);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Data Buku");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(110, 30, 170, 30);

        Active.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Active.setText("Tanggal");
        jPanel2.add(Active);
        Active.setBounds(170, 100, 60, 15);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 112, 207));
        jLabel7.setText("Lihat Klasifikasi");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });
        jPanel2.add(jLabel7);
        jLabel7.setBounds(830, 100, 90, 15);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 112, 207));
        jLabel3.setText("Input Buku");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });
        jPanel2.add(jLabel3);
        jLabel3.setBounds(1180, 100, 70, 15);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Sort By");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
        });
        jPanel2.add(jLabel4);
        jLabel4.setBounds(110, 100, 140, 15);

        tblBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Panggil", "Judul Buku", "GMD", "Edisi", "ISBN", "Penerbit", "Email", "Tahun Terbit", "Judul Seri", "Bahasa"
            }
        ));
        tblBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBukuMouseClicked(evt);
            }
        });
        tblBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblBukuKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblBuku);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(110, 140, 1140, 580);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 112, 207));
        jLabel5.setText("Export Data Buku");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
        });
        jPanel2.add(jLabel5);
        jLabel5.setBounds(1070, 100, 100, 15);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 112, 207));
        jLabel6.setText("Import Data Eksemplar");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });
        jPanel2.add(jLabel6);
        jLabel6.setBounds(930, 100, 124, 15);

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
        jPanel2.add(jTextField1);
        jTextField1.setBounds(910, 40, 340, 32);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1296, 759));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        jLabel3.setForeground(new java.awt.Color(0, 112, 207));
    }//GEN-LAST:event_jLabel3MouseExited

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered
         subMenuBlibliografi.setVisible(false);
        subMenuSirkulasi.setVisible(false);
        subMenuAnggota.setVisible(false);
        subMenuLaporan.setVisible(false);
        subMenuAdmin.setVisible(false);
        sort.setVisible(false);
    }//GEN-LAST:event_jPanel2MouseEntered

    private void tblBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblBukuKeyPressed

    }//GEN-LAST:event_tblBukuKeyPressed

   
    private void tblBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBukuMouseClicked
            int i = tblBuku.getSelectedRow();
            TableModel model = tblBuku.getModel() ;
            String cn = model.getValueAt(i, 1).toString();
            String jdl = model.getValueAt(i, 2).toString();
        String[] options = {"Eksemplar", "Pinjamkan Buku"};
        int x = JOptionPane.showOptionDialog(null, "Pilih Aksi",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if(x == 0 ){
            Petugas_DataEksemplar obj = new Petugas_DataEksemplar();
            Petugas_InputEksemplar obj1 = new Petugas_InputEksemplar();
            
            UserSession.setCallNumb(cn);
            UserSession.setJudul(jdl);
            obj.call_number.setText(cn);
            obj.Judul.setText(jdl);
            try{
                Statement stat = CC.createStatement();
                sql="SELECT * FROM new_bliblio WHERE call_number = '"+cn+"'";
                ResultSet rs = stat.executeQuery(sql);
                if (rs.next()){
                    obj.desk.setText(rs.getString("Notes"));
                    String n = rs.getString("Notes");
                    obj.showtable("SELECT * FROM Item INNER JOIN new_bliblio ON Item.biblio_id = new_bliblio.IdBliblio INNER JOIN mst_coll_type ON mst_coll_type.coll_type_id = Item.coll_type_id INNER JOIN mst_location ON mst_location.location_id = Item.location_id WHERE item.call_number='"+cn+"'");

                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            obj.setVisible(true);
            obj.pack();
            obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.dispose();
        }else{
            // 
            try{
              Statement stat = CC.createStatement();
              String SQL = "SELECT COUNT(*) FROM `item` JOIN new_bliblio ON new_bliblio.call_number = item.call_number WHERE new_bliblio.call_number = '" + cn + "' AND NOT location_Id = '3' ";
              ResultSet rs = stat.executeQuery(SQL);
              if(rs.next()){
                int a = rs.getInt("COUNT(*)");
                System.out.print(a);
                if( a <= 0 ){
                    JOptionPane.showMessageDialog(null,"Tidak Ada Eksemplar Tersedia" );
                }else{
                    Dialog_Konfirmasi obj = new Dialog_Konfirmasi();
                    obj.judul = jdl;
                    obj.cnn = cn;
                    obj.setVisible(true);
                }
                
              }
          }catch(Exception e){
              JOptionPane.showMessageDialog(null, e);
          }
            
        }
        
    }//GEN-LAST:event_tblBukuMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        try {
            chooseDir();
        } catch (IOException ex) {
            Logger.getLogger(Petugas_DataBuku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Petugas_DataBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        jLabel5.setForeground(new java.awt.Color(0, 112, 207));
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        Petugas_InputBuku obj = new Petugas_InputBuku();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

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

    private void nPanggilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nPanggilMouseClicked
        sort.setVisible(false);
        Active.setText(nPanggil.getText());
        if(a == null){
        a = "SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id INNER JOIN gmd ON gmd.gmd_id = new_bliblio.IdGMD INNER JOIN mst_publisher ON mst_publisher.publisher_id = new_bliblio.IdPublisher INNER JOIN mst_language ON mst_language.language_id = new_bliblio.IdLanguage"; 
        }
        showtable(a + " ORDER BY call_Number ASC");
    }//GEN-LAST:event_nPanggilMouseClicked

    private void nPanggilMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nPanggilMouseEntered
        nPanggil.setForeground(new java.awt.Color(0,112,207));
    }//GEN-LAST:event_nPanggilMouseEntered

    private void nPanggilMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nPanggilMouseExited
        nPanggil.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_nPanggilMouseExited

    private void judulMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_judulMouseClicked
        sort.setVisible(false);
        Active.setText(judul.getText());
        if(a == null){
        a = "SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id INNER JOIN gmd ON gmd.gmd_id = new_bliblio.IdGMD INNER JOIN mst_publisher ON mst_publisher.publisher_id = new_bliblio.IdPublisher INNER JOIN mst_language ON mst_language.language_id = new_bliblio.IdLanguage"; 
        }
        showtable(a + " ORDER BY Judul ASC");
    }//GEN-LAST:event_judulMouseClicked

    private void judulMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_judulMouseEntered
        judul.setForeground(new java.awt.Color(0,112,207));
    }//GEN-LAST:event_judulMouseEntered

    private void judulMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_judulMouseExited
        judul.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_judulMouseExited

    private void TanggalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TanggalMouseClicked
        sort.setVisible(false);
        Active.setText(Tanggal.getText());
        if(a == null){
        a = "SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id INNER JOIN gmd ON gmd.gmd_id = new_bliblio.IdGMD INNER JOIN mst_publisher ON mst_publisher.publisher_id = new_bliblio.IdPublisher INNER JOIN mst_language ON mst_language.language_id = new_bliblio.IdLanguage"; 
        }
        showtable(a + " ORDER BY IdBliblio ASC");
    }//GEN-LAST:event_TanggalMouseClicked

    private void TanggalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TanggalMouseEntered
        // [0,112,207]
        Tanggal.setForeground(new java.awt.Color(0,112,207));
    }//GEN-LAST:event_TanggalMouseEntered

    private void TanggalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TanggalMouseExited
        Tanggal.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_TanggalMouseExited

    private void sortMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sortMouseExited

    }//GEN-LAST:event_sortMouseExited

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        sort.setVisible(true);
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        Petugas_ImportEksemplar obj = new Petugas_ImportEksemplar();
        obj.setVisible(true);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        jLabel6.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        
        jLabel6.setForeground(new java.awt.Color(0,112,207));
    }//GEN-LAST:event_jLabel6MouseExited

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
            Logger.getLogger(Petugas_DataBuku.class.getName()).log(Level.SEVERE, null, ex);
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

    private void jTextField1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseEntered

    }//GEN-LAST:event_jTextField1MouseEntered

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        showtable("SELECT * FROM new_bliblio INNER JOIN mst_author ON mst_author.author_id = new_bliblio.author_id INNER JOIN gmd ON gmd.gmd_id = new_bliblio.IdGMD INNER JOIN mst_publisher ON mst_publisher.publisher_id = new_bliblio.IdPublisher INNER JOIN mst_language ON mst_language.language_id = new_bliblio.IdLanguage WHERE Judul LIKE '%"+ jTextField1.getText() +"%' OR call_number LIKE '%" + jTextField1.getText() + "%' OR author_name LIKE '%" + jTextField1.getText() + "%'");       
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
       Petugas_DataKategori obj = new Petugas_DataKategori();
       obj.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
         jLabel7.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
         jLabel7.setForeground(new java.awt.Color(0,112,207));
    }//GEN-LAST:event_jLabel7MouseExited

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
            java.util.logging.Logger.getLogger(Petugas_DataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Petugas_DataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Petugas_DataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Petugas_DataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Petugas_DataBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Active;
    private javax.swing.JLabel Tanggal;
    private javax.swing.JPanel empty1;
    private javax.swing.JPanel empty2;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    protected javax.swing.JTextField jTextField1;
    private javax.swing.JLabel judul;
    private javax.swing.JLabel nPanggil;
    private javax.swing.JPanel sort;
    private javax.swing.JPanel subMenuAdmin;
    private javax.swing.JPanel subMenuAnggota;
    private javax.swing.JPanel subMenuBlibliografi;
    private javax.swing.JPanel subMenuLaporan;
    private javax.swing.JPanel subMenuSirkulasi;
    public javax.swing.JTable tblBuku;
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