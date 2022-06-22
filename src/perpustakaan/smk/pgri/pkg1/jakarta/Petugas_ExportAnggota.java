package perpustakaan.smk.pgri.pkg1.jakarta;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.opencsv.CSVParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author AMIRULLAH
 */
public class Petugas_ExportAnggota extends javax.swing.JFrame {

    /**
     * Creates new form Petugas_ImportEksemplar
     */
    ResultSet rs = null;
    Connection CC = null;
    PreparedStatement pst = null;
    public Statement stt;
    public Statement stt1;
    public String sql;
    public Petugas_ExportAnggota() {
        initComponents();
        CC = new koneksi().connect();
        cbReadKls();
        getAdd();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public int rsKls,jumlah;
    private void cbReadKls(){
        try{
        Statement stat = CC.createStatement();
           sql = "SELECT * FROM Kelas INNER JOIN Jurusan ON Jurusan.IdJurusan=kelas.IdJurusan";
           ResultSet rs = stat.executeQuery(sql);
           while(rs.next()){
           String kelas = rs.getString("Kelas.Kelas");
           String jurus = rs.getString("IdJurusan");
           String tk = rs.getString("TingkatKelas");
               cbkls.addItem(tk+" "+jurus+" "+kelas);
           }
           rs.last();
           jumlah = rs.getRow();
           rs.first();
        }catch(Exception e){
        
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
                getIdKls();
                       System.out.println(rsKls);
                exportCSV();                
                cbkls.setSelectedIndex(0);
            //JOptionPane.showMessageDialog(null, "Import Data Berhasil Ditambahkan, Silahkan Tekan Submit Untuk Menyimpan !!");
              System.out.println(csv);
        }
         
    }
    

   public String label1,label2,label3,label4,label5;
   public String[]label = {label1,label2,label3,label4,label5};
     public void getAdd(){
          int rowindex = 0;
        try{
        
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
           
            // initial rowindex
            // iterate panel default false
             
                  
             //end of iterate panel     
            Object array2D[][] = new Object[rowcount][];
            do {
                 array2D[rowindex] = new Object[numberOfColumns];
                  for (int i = 0; i < numberOfColumns; i++) {
                    array2D[rowindex][i] = rs.getObject(i + 1);
                    }
                  if(rs.getInt("Status")>0){
                   label[rowindex] = rs.getString("tName");            //label[rowindex].setVisible(true);
                    //form[rowindex].setVisible(true);
                    //label[rowindex] = rs.getString("tName");
                  }
                    label[rowindex] = rs.getString("tName");;
                   
                  
                //System.out.println("array2D[" + rowindex + "] = " + Arrays.toString(array2D[rowindex])); 
             rowindex++;
                } while (rs.next());              
        }catch(Exception e){
             e.printStackTrace();
        }
       
    }
    public void exportCSV() throws SQLException, IOException{
                
        try{
         String a = "Tempat,Tanggal Lahir \t";
         String[] entries = {"NO URUT","NIS","NAMA","JK","KELAS","ALAMAT",a,label[0],label[1],label[2],label[3],label[4]};
         CSVPrinter printer = new CSVPrinter(new FileWriter(csv),CSVFormat.EXCEL.withHeader(entries).withIgnoreEmptyLines());
        PreparedStatement stmt = CC.prepareStatement("SELECT * FROM anggota WHERE IdKelas="+rsKls+"", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
          ResultSet rs = stmt.executeQuery();
          ResultSetMetaData Mdata = (ResultSetMetaData) rs.getMetaData();
          int count = 0;
          String data[] = new String[12];
          while(rs.next()){
           count++;
           String co = Integer.toString(count);
           data[0] = co;
           data[1] = rs.getString("Nis");
           data[2] = rs.getString("Nama");;
           data[3] = rs.getString("JK");
           data[4] = cbkls.getSelectedItem().toString();
           data[5] = rs.getString("alamat");
           data[6] = rs.getString("TTL");
           data[7] = rs.getString(label[0].replaceAll(" ",""));
           data[8] = rs.getString(label[1].replaceAll(" ",""));
           data[9] = rs.getString(label[2].replaceAll(" ",""));
           data[10] = rs.getString(label[3].replaceAll(" ",""));
           data[11] = rs.getString(label[4].replaceAll(" ",""));
           printer.printRecord(data);
          }
        printer.flush();
         printer.close();
         JOptionPane.showMessageDialog(null, "Data Berhasil Di Export");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
   public String tk,decision,grade;
   public void getIdKls(){
        
         try{
              String rskl = (String) cbkls.getSelectedItem();
         String[] arrOfStr = rskl.split(" "); 
            for (String a : arrOfStr){
                   tk=arrOfStr[0];
                   decision=arrOfStr[1];
                   grade = arrOfStr[2];
            }

             Statement stat = CC.createStatement();
             sql="SELECT * FROM kelas WHERE kelas.TingkatKelas='"+tk+"' AND kelas.IdJurusan='"+decision+"' AND kelas.Kelas='"+grade+"'";
             ResultSet rs = stat.executeQuery(sql);
             if (rs.next()){
                 rsKls = rs.getInt("kelas.IdKelas");
                 
             }
         }catch(Exception e){
            e.printStackTrace();
         }
   }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cancel = new javax.swing.JButton();
        browse = new javax.swing.JButton();
        filename = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbkls = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Export Data Anggota");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(90, 10, 230, 30);

        cancel.setText("Batal");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel1.add(cancel);
        cancel.setBounds(60, 200, 80, 22);

        browse.setText("Export");
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseActionPerformed(evt);
            }
        });
        jPanel1.add(browse);
        browse.setBounds(270, 200, 72, 22);
        jPanel1.add(filename);
        filename.setBounds(100, 80, 230, 20);

        jLabel2.setText("Pilih Kelas");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 100, 70, 16);

        cbkls.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kelas" }));
        cbkls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbklsActionPerformed(evt);
            }
        });
        jPanel1.add(cbkls);
        cbkls.setBounds(90, 100, 240, 22);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseActionPerformed
        try {
            // TODO add your handling code here:
//        readCSV();
        chooseDir();
        } catch (IOException ex) {
            Logger.getLogger(Petugas_ExportAnggota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Petugas_ExportAnggota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_browseActionPerformed

    private void cbklsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbklsActionPerformed
        // TODO add your handling code here:
       //getIdKls();
  
    }//GEN-LAST:event_cbklsActionPerformed

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
            java.util.logging.Logger.getLogger(Petugas_ExportAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Petugas_ExportAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Petugas_ExportAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Petugas_ExportAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Petugas_ExportAnggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browse;
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox<String> cbkls;
    private javax.swing.JLabel filename;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}