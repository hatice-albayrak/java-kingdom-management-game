
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hatic
 */
public class Ticaret_sehri extends Şehir{
    //TicaretSehri: Altın üretiminde uzman, yiyecek çok az üretir.
    public Ticaret_sehri(String ad, int uretimKapasitesi, int x, int y) {
        super(ad, uretimKapasitesi, x, y ); 
    }
    @Override 
    public String getTip() { 
        return "Deniz Ticaret kenti";
    } 
@Override 
public UretimSonucu turSonuUretimHesapla(Krallık krallik) { 
    UretimSonucu temel = super.turSonuUretimHesapla(krallik);
    
   int altin = (int)(temel.altin * 1.8); // Altın üretimi yüksek 
   int yiyecek = (int)(temel.yiyecek * 0.3);// Yiyecek çok az
   
   return new UretimSonucu(altin, yiyecek); }
}


