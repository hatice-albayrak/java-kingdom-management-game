

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hatic
 */
public class Sanayi_sehri extends Şehir {
    
  //SanayiSehri: Hem altın hem yiyecek üretir, dengeli bir yapıya sahiptir.  
    public Sanayi_sehri(String ad, int uretimKapasitesi, int x, int y) {
       super(ad, uretimKapasitesi, x, y);
    } 
    @Override 
    public String getTip() {
        return "Sanayi sehri"; 
    }
    @Override 
    public UretimSonucu turSonuUretimHesapla(Krallık krallik) { 
// Temel üretim mantığını çağırıyoruz 
   UretimSonucu temel = super.turSonuUretimHesapla(krallik);
   
   // Sanayi şehri: dengeli üretim 
   int altin = (int)(temel.altin * 1.2); // Altın orta seviyede 
   int yiyecek = (int)(temel.yiyecek * 1.1); // Yiyecek orta seviyede
   
   return new UretimSonucu(altin, yiyecek);
  }

}