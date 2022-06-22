/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perpustakaan.smk.pgri.pkg1.jakarta;

/**
 *
 * @author Atthoriq
 */
public class UserSession {
    
    private static String userLogin;
    private static int UserId;
    public static int Publisher,Place;
    private static String nis,nama,kelas,alamat;
    private static String call_numb,Judul,fisik;

    public static void setUserLogin(String userLogin) {
        UserSession.userLogin = userLogin;
    }
    public static void setUserId(int UserId) {
        UserSession.UserId = UserId;
    }
    public static void setDefaultKelas(String kelas){
        UserSession.kelas = kelas;
    } 
    public static void setCallNumb(String call_numb){
        UserSession.call_numb = call_numb;
    }   
     public static void setJudul(String Judul){
        UserSession.Judul = Judul;
    }   
      public static void setFisik(String fisik){
        UserSession.fisik = fisik;
    }   
   public static void setPublisher(int Publisher){
       UserSession.Publisher = Publisher;
   }
   public static void setPlace(int Place){
       UserSession.Place = Place;
   }
    
    
    public static String getUserLogin() {
        return userLogin;
    }
    public static int GetUserId(){
        return UserId;
    }
    public static String getDefaultKelas(){
        return kelas;
    }
    public static int getPublisher(){
        return Publisher;
    }
    public static int getPlace(){
        return Place;
    }
    public static String getCallNumb(){
        return call_numb;
    }
     public static String getJudul(){
        return Judul;
    }
      public static String getFisik(){
        return fisik;
    }

}
    
