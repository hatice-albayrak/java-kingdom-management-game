import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author hatic
 */
public class Krallık {

    private int altin;
    private int yiyecek;
    private int halkMemnuniyeti;
    private int vergiOrani;
    private int guvenlikSeviyesi ;
    private int orduGucu ;

     // Krallığın sahip olduğu şehirler
    private Şehir[] sehirler;

    // Oyun akışı kontrolü -
    private int turSayisi;
    private final int max_turSayisi = 10;

    private final Scanner sc ;
    private final Random rnd ;
   

//    Constructor: başlangıç değerlerini ayarla
    public Krallık() {
        this.sc = new Scanner(System.in);
        this.rnd  =new Random();

        // Başlangıç değerleri
        this.altin = 500;
        this.yiyecek = 300;
        this.halkMemnuniyeti = 70;
        this.guvenlikSeviyesi = 60; 
        this.orduGucu = 50;
         this.vergiOrani = 10;

        
        this.turSayisi = 1;
       
        // diziyi declare ettim
        this.sehirler = new Şehir[3];
      
        sehirler[0] = new Ticaret_sehri("Deniz Ticaret kenti", 5, 3, 5);
        sehirler[1] = new Tarim_sehri("Tarim Ovasi", 7, 7, 2);
        sehirler[2] = new Sanayi_sehri("Sanayi sehri", 9, 0, 9);

    }
    
      // Oyunu başlat
    public void oyunuBaslat() {
        System.out.println(" -----Krallik Oyunu Basladi !--------");
        // Oyun bitene kadar tur oyna neden while kaç tur bilinmiyor

         while (turSayisi <= max_turSayisi && !oyunBittiMi()) {
  
        System.out.println();
        System.out.println("-----" + turSayisi + ".Tur -----");
        
          // Her tur: turuOyna() çağrılır (seçim burada lokal tutulur)   
        turuOyna();    // oyuncuya menü sunulur    
        
        // Tur sonu üretimi hesapla ve kaynakları güncelle
        turSonuUretim();
        // Tur sayısını artır
        turSayisi++; }
        

         System.out.println(" OYUN BITTI");
        System.out.println("Son Durum → Altin: " + altin + " | Yiyecek: " + yiyecek     
                + " | Memnuniyet: " + halkMemnuniyeti 
                + " | Guvenlik: " + guvenlikSeviyesi 
                + " | Ordu: " + orduGucu);
    }
       

    private void turuOyna() {

        System.out.println("-----" + turSayisi + ".Tur ---------------");
        
       durumYazdir(); // krallığın mevcut durumu yazdırılır
       menuYazdir(); // oyuncuya menü gösterilir 
       sehirleriGoruntule();
        
        System.out.println(); 

        System.out.println("1- Sehirleri Goruntule");
        System.out.println("2 - sehre yatirim yap");
        System.out.println("3 - Vergi ayarla");
        System.out.println("4 - Turu bitir ");

        //yapılacak yatırımı seçeceğiz  
       
int secim = sayiOkuAralik("Seciminiz: ", 1, 4);

        switch (secim) {
            case 1: 
                sehirleriGoruntule(); 
            break;
            case 2:
                sehireYatirimYap();
                break;
            case 3:
                vergiyiAyarla();
                break;
            case 4:
                System.out.println("Tur sonlandiriliyor...");
                break;
          default: // sayiOkuAralik zaten sınır kontrolü yapıyor 
              break;
        }
        // Metot sonlandığında 'secim' değişkeni scope dışına çıkar ve unutulur
    }  
      
    
    
    
// Krallığın durumunu yazdır
    public void durumYazdir() {
        System.out.println("=== Orta Cag Kralligi Yonetim Oyunu ===");
        System.out.println("\n--- TUR: " + turSayisi + " ---");
        System.out.println("Altin: " + altin);
        System.out.println("Yiyecek: " + yiyecek);
        System.out.println("Halk Memnuniyeti: " + halkMemnuniyeti);
        System.out.println("Guvenlik Seviyesi: " + guvenlikSeviyesi);
        System.out.println("Ordu Gucu: " + orduGucu);
        System.out.println("Kralliktaki bolgeler:"); 
       
        for (int i = 0; i < sehirler.length; i++) { 
          
          if (sehirler[i] == null) {
              System.out.println((i + 1) + ") [Bos bolge]"); 
              continue; 
          }
          
           System.out.println((i+1) + ") " + sehirler[i].getTip() + " - \"" 
                   + sehirler[i].getAd() + "\" (x=" + sehirler[i].getX() 
                   + ", y=" + sehirler[i].getY() + ")"); 
           
           
           System.out.println(" UretimKap: " + sehirler[i].getUretimKapasitesi() 
                   + " | Gelisim: " + sehirler[i].getGelisimSeviyesi() 
                   + " | Ordu: " + sehirler[i].getOrduSeviyesi() 
                   + " | Guvenlik: " + sehirler[i].getGuvenlikSeviyesi() 
                   + " | Memnuniyet: " + sehirler[i].getHalkMemnuniyeti() 
                   + " | Vergi: %" + sehirler[i].getVergiOrani());
           
        }
    }

    public void menuYazdir() {
        System.out.println("\nNe yapmak istersiniz?");
        System.out.println("1) Sehirleri goruntule");
        System.out.println("2) Sehire yatirim yap");
        System.out.println("3) Vergi oranini ayarla");
        System.out.println("4) Turu bitir");
    }

    // Şehirleri listele
    public void sehirleriGoruntule() {
        System.out.println("\n--- SEHIRLER ---");
        for (int i = 0; i < sehirler.length; i++) { 
            if (sehirler[i] == null) { 
                System.out.println((i + 1) + ") [Bos bolge]"); 
                continue; 
            }
            
            System.out.println((i + 1) + ") ");
            sehirler[i].bilgiGoster();
        }
    }

    public void sehireYatirimYap() {
        sehirleriGoruntule();
        int idx = sayiOkuAralik("Yatirim yapilacak sehir (1-" + sehirler.length + "): "
                + "", 1, sehirler.length) - 1;

        int miktar = sayiOkuMin("Kac altin yatiracaksiniz? (min 1): ", 1);

        if (miktar > altin) {
            System.out.println("Yeterli altin yok!");
            return;
        }
        // Krallik altini azalir, sehir gelisir
        altin -= miktar;
        sehirler[idx].yatirimYap(miktar);

        
        toplamlariGuncelleSehirlerden();

        System.out.println("Yatirim basarili!");
    }

    public void vergiyiAyarla() {
        sehirleriGoruntule();
        int idx = sayiOkuAralik("Vergisi ayarlanacak sehir (1-" + sehirler.length + "): ", 1, sehirler.length) - 1;
             if (sehirler[idx] == null) { 
                 System.out.println("Secilen sehir bos."); 
                 return; 
             }
        
        
        int yeniVergi = sayiOkuAralik("Vergi orani (0-50 arasi onerilir): ", 0, 100);
        sehirler[idx].setVergiOrani(yeniVergi);

        System.out.println("Vergi orani guncellendi: " + sehirler[idx].getAd() + " -> %" + yeniVergi);
    }

    public void turSonuUretim() {
        System.out.println("\n--- TUR SONU ---");

        int turAltin = 0;
        int turYiyecek = 0;

        for (Şehir sehirler1 : sehirler) {// for (int i = 0; i < sehirler.length; i++)
            Şehir.UretimSonucu sonuc = sehirler1.turSonuUretimHesapla(this);
            turAltin += sonuc.altin;
            turYiyecek += sonuc.yiyecek;
        }

        // Krallik kaynakları artar
        altin += turAltin;
        yiyecek += turYiyecek;

        // Basit tüketim: her tur yiyecek azalsın 
        int tuketim = 15;
        yiyecek -= tuketim;
        if (yiyecek < 0) {
            yiyecek = 0;
        }

        // Sehirlerin memnuniyet/güvenlik 
        toplamlariGuncelleSehirlerden();

        System.out.println("Bu tur kazanc -> Altin: +" + turAltin + ", Yiyecek: +" + turYiyecek);
        System.out.println("Tuketim -> Yiyecek: -" + tuketim);
    }

    public void toplamlariGuncelleSehirlerden() {
        // Krallik değerlerini şehirlerin ortalaması/toplamı 
        int memTop = 0;
        int guvTop = 0;
        int orduTop = 0;

        for (int i = 0; i < sehirler.length; i++) {
            memTop += sehirler[i].getHalkMemnuniyeti();
            guvTop += sehirler[i].getGuvenlikSeviyesi();
            orduTop += sehirler[i].getOrduSeviyesi();
        }

        halkMemnuniyeti = memTop / sehirler.length;
        guvenlikSeviyesi = guvTop / sehirler.length;
        orduGucu = orduTop;
    }

    public boolean oyunBittiMi() {
        if (turSayisi > max_turSayisi) {
            return true;
        }
        if (altin <= 0 && yiyecek <= 0) {
            return true;
        }

        // memnuniyet veya güvenlik çok düşerse bitir
        if (halkMemnuniyeti <= 10) {
            return true;
        }
        if (guvenlikSeviyesi <= 10) {
            return true;
        }

        return false;
    }

    
    public int sayiOkuAralik(String mesaj, int min, int max) {
        int x;
        while (true) {
            System.out.print(mesaj);
            if (sc.hasNextInt()) {
                x = sc.nextInt();
                sc.nextLine();
                if (x >= min && x <= max) {
                    return x;
                }
            } else {
                sc.nextLine();   // hatalı giriş temizlenir
            }
            System.out.println("Hatali giris! [" + min + "-" + max + "] araliginda giriniz.");
        }
    }

    public int sayiOkuMin(String mesaj, int min) {
        int x;
        while (true) {
            System.out.print(mesaj);
            if (sc.hasNextInt()) {
                x = sc.nextInt();
                sc.nextLine();
                if (x >= min) {
                    return x;
                }
            } else {
                sc.nextLine();  
            }
            System.out.println("Hatali giris! Minimum: " + min);
        }
    }

    
    public int getAltin() {
        return altin;
    }

    public int getYiyecek() {
        return yiyecek;
    }

    public int getHalkMemnuniyeti() {
        return halkMemnuniyeti;
    }

    public void setHalkMemnuniyeti(int halkMemnuniyeti) {
        this.halkMemnuniyeti = halkMemnuniyeti;
    }

    public int getVergiOrani() {
        return vergiOrani;
    }

    public void setVergiOrani(int vergiOrani) {
        this.vergiOrani = vergiOrani;
    }

    public int getGuvenlikSeviyesi() {
        return guvenlikSeviyesi;
    }

    public void setGuvenlikSeviyesi(int guvenlikSeviyesi) {
        this.guvenlikSeviyesi = guvenlikSeviyesi;
    }

    public int getOrduGucu() {
        return orduGucu;
    }

    public void setOrduGucu(int orduGucu) {
        this.orduGucu = orduGucu;
    }

    public Şehir[] getSehirler() {
        return sehirler;
    }

    public void setSehirler(Şehir[] sehirler) {
        this.sehirler = sehirler;
    }

    public int getTurSayisi() {
        return turSayisi;
    }

    public void setTurSayisi(int turSayisi) {
        this.turSayisi = turSayisi;
    }
    
    
}



    
    
    
    
    
    
    
    
    
   
