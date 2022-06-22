 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perpustakaan.smk.pgri.pkg1.jakarta;
import static java.lang.Class.forName;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Atthoriq
 */


public class koneksi {
    private Connection CC;
        config con = new config();
        Configuration co = new Configuration();
        
        
    public Connection connect(){
      String ip = con.GetProp(co.lbl_ip.getText());
      String db = con.GetProp(co.lbl_db.getText());
      String user = con.GetProp(co.lbl_user.getText());
      String pass = con.GetProp(co.lbl_pass.getText());
    try{
    CC = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db+"", ""+user+"", ""+pass+"");
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        co.setVisible(true);
        
    }
        return CC;
    }
}
