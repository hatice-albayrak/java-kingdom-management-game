

import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hatic
 */
public class Şehir {
    private  String ad;
    private int uretimKapasitesi;

    private int gelisimSeviyesi;     
    private int orduSeviyesi;        
    private int guvenlikSeviyesi;    
    private int halkMemnuniyeti;     

    private int vergiOrani;          // 0-100 (şehir bazlı)
    private int baskinIhtimali;      // 0-100 (yüksekse baskın çok )

    private int depoAltin;           // şehirde biriken kaynak (baskında azalir)
    private int depoYiyecek;
    
    int x; 
    private int y; // koordinatlar

    private final Random rnd;
    

    
      public Şehir(String ad, int uretimKapasitesi, int x, int y) {
        this.ad = ad;
        this.uretimKapasitesi = uretimKapasitesi;

        this.gelisimSeviyesi = 1;
        this.orduSeviyesi = 10;
        this.guvenlikSeviyesi = 60;
        this.halkMemnuniyeti = 70;

        this.vergiOrani = 10;
        this.baskinIhtimali = 20;

        this.depoAltin = 50;
        this.depoYiyecek = 50;
        
        this.x = x; 
        this.y = y;

        this.rnd = new Random();
    }


    // turSonuUretimHesapla dönüşü için 
    public static class UretimSonucu {
        public int altin;
        public int yiyecek;

        public UretimSonucu(int altin, int yiyecek) {
            this.altin = altin;
            this.yiyecek = yiyecek;
        }
    }

  
    // Yatırım: gelişim ve ordu artar
    public void yatirimYap(int altinMiktari) {
        // Basit kural: her 100 altın = +1 gelişim, her 150 altın = +1 ordu
       
      
        int gelisimArtis = altinMiktari / 100;
        int orduArtis = altinMiktari / 150;

        if (gelisimArtis < 1)
            gelisimArtis = 1; 

        gelisimSeviyesi += gelisimArtis;
        orduSeviyesi += orduArtis;

       
        guvenlikSeviyesi = guvenlikSeviyesi + orduArtis * 2;
        if (guvenlikSeviyesi > 100) guvenlikSeviyesi = 100;

        // yatırım halkı mutlu eder
        halkMemnuniyeti += 2;
        if (halkMemnuniyeti > 100) 
            halkMemnuniyeti = 100;
        
     uretimKapasitesi+= gelisimArtis * 10;
     
    }

    public void baskinIhtimaliGuncelle() {
        // Ordu ve güvenlik yükseldikçe baskın ihtimali düş
       
       int savunma = (orduSeviyesi * 2 + guvenlikSeviyesi);
        baskinIhtimali = 100 - (savunma / 2);


        if (baskinIhtimali < 3)
            baskinIhtimali = 3;   // tamamen 0 olmasın
        
        if (baskinIhtimali > 60)
            baskinIhtimali = 60;
    }

    public boolean baskinGerceklesiyorMu() {
        // 0-99 arası sayı
        int sayi = rnd.nextInt(100);
            if (sayi < baskinIhtimali) {
            return true;
    }

    return false;
}

    // Temel şehir: dengeli üretim (alt sınıflar override edecek) 
    public UretimSonucu turSonuUretimHesapla(Krallık krallik) {
        baskinIhtimaliGuncelle(); 

        int altinUretim = uretimKapasitesi + gelisimSeviyesi * 2;
        int yiyecekUretim = uretimKapasitesi + gelisimSeviyesi * 2;

      
        int vergiEtki = (altinUretim * vergiOrani) / 100;
        altinUretim += vergiEtki;

        // Memnuniyet güncelle
        halkMemnuniyeti -= vergiOrani / 10;
        if (halkMemnuniyeti < 0) 
            halkMemnuniyeti = 0;

        // Depoya ekle
        depoAltin += altinUretim;
        depoYiyecek += yiyecekUretim;

        
       boolean baskin = baskinGerceklesiyorMu();
        if (baskin) {
            // Baskın olursa kaynak kaybı , güvenlik düşüşü
            int kayipAltin = depoAltin / 3;
            int kayipYiyecek = depoYiyecek / 3;

            depoAltin -= kayipAltin;
            depoYiyecek -= kayipYiyecek;

            guvenlikSeviyesi -= 10;
            if (guvenlikSeviyesi < 0) 
                guvenlikSeviyesi = 0;

            halkMemnuniyeti -= 8;
            if (halkMemnuniyeti < 0) 
                halkMemnuniyeti = 0;

            System.out.println(ad + " sehrine BASKIN oldu! Kayip: -" + kayipAltin + " altin, -" + kayipYiyecek + " yiyecek");
        }

      
        int kralligaAltin = depoAltin / 4;
        int kralligaYiyecek = depoYiyecek / 4;

        depoAltin -= kralligaAltin;
        depoYiyecek -= kralligaYiyecek;

        // Üretim iyi ise memnuniyet artsın
        if (!baskin && (altinUretim + yiyecekUretim) > 2 * uretimKapasitesi) {
            halkMemnuniyeti += 2;
            
            if (halkMemnuniyeti > 100)
                halkMemnuniyeti = 100;
        }

        return new UretimSonucu(kralligaAltin, kralligaYiyecek);
    }

    // Şehir bilgilerini yazdır
 public void bilgiGoster() { 
     System.out.println("Sehir: " + ad + " | Tip: " + getTip()
             + " | UretimKap: " + uretimKapasitesi 
             + " | Gelisim: " + gelisimSeviyesi 
             + " | Ordu: " + orduSeviyesi 
             + " | Guvenlik: " + guvenlikSeviyesi 
             + " | Memnuniyet: " + halkMemnuniyeti 
             + " | Vergi: %" + vergiOrani 
             + " | Koordinat: (" + x + "," + y + ")"); }

        // Alt sınıflar bunu override etsin
    public String getTip() {
        return "TemelSehir";
    }
    
  
    

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public int getGelisimSeviyesi() {
        return gelisimSeviyesi;
    }

    public void setGelisimSeviyesi(int gelisimSeviyesi) {
        this.gelisimSeviyesi = gelisimSeviyesi;
    }

    public int getUretimKapasitesi() { 
        return uretimKapasitesi; } 
    
  public void setUretimKapasitesi(int uretimKapasitesi) { 
      this.uretimKapasitesi = uretimKapasitesi;
    }
    
    public int getOrduSeviyesi() {
        return orduSeviyesi;
    }

    public void setOrduSeviyesi(int orduSeviyesi) {
        this.orduSeviyesi = orduSeviyesi;
    }

    public int getGuvenlikSeviyesi() {
        return guvenlikSeviyesi;
    }

    public void setGuvenlikSeviyesi(int guvenlikSeviyesi) {
        this.guvenlikSeviyesi = guvenlikSeviyesi;
    }

    public int getHalkMemnuniyeti() {
        return halkMemnuniyeti;
    }

    public void setHalkMemnuniyeti(int halkMemnuniyeti) {
        this.halkMemnuniyeti = halkMemnuniyeti;
    }

    public int getBaskinIhtimali() {
        return baskinIhtimali;
    }

    public void setBaskinIhtimali(int baskinIhtimali) {
        this.baskinIhtimali = baskinIhtimali;
    }

    public int getDepoAltin() {
        return depoAltin;
    }

    public void setDepoAltin(int depoAltin) {
        this.depoAltin = depoAltin;
    }

    public int getDepoYiyecek() {
        return depoYiyecek;
    }

    public void setDepoYiyecek(int depoYiyecek) {
        this.depoYiyecek = depoYiyecek;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Random getRnd() {
        return rnd;
    }

    
   
    public int getVergiOrani() {
        return vergiOrani;
    }
    public void setVergiOrani(int vergiOrani) {
        if (vergiOrani < 0) vergiOrani = 0;
        if (vergiOrani > 100) vergiOrani = 100;
        this.vergiOrani = vergiOrani;
    }
    

    
    
    
    
    
}
    
    

