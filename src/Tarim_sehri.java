

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hatic
 */
public class Tarim_sehri extends Şehir{
   // TarimSehri: Yiyecek üretiminde uzman, altın çok az üretir.
    public Tarim_sehri(String ad, int uretimKapasitesi, int x, int y) { 
        super(ad, uretimKapasitesi, x, y);
    }
@Override 
public String getTip() { 
    return "Tarim Ovasi"; 
}
   @Override public UretimSonucu turSonuUretimHesapla(Krallık krallik) { // Temel üretim mantığını çağırıyoruz
       UretimSonucu temel = super.turSonuUretimHesapla(krallik); 
    
   // Tarım şehri: yiyecek üretimi yüksek, altın üretimi düşük
       int altin = (int)(temel.altin * 0.3); // Altın çok az
       int yiyecek = (int)(temel.yiyecek * 1.8); // Yiyecek çok fazla
    
       return new UretimSonucu(altin, yiyecek);
   }
}
