package hr.java.vjezbe.glavna;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.entitet.Predmet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Glavna {

    private static final int BROJ_PROFESORA = 2;
    private static final int BROJ_STUDENATA = 3;
    private static final int BROJ_PREDMETA = 3;
    private static final int BROJ_ISPITNIH_ROKOVA = 1;
    public static void main(String[] args) {
        
        Scanner unos = new Scanner(System.in);

        Profesor[] profesori = new Profesor[BROJ_PROFESORA];
        Predmet[] predmeti = new Predmet[BROJ_PREDMETA];
        Student[] studenti = new Student[BROJ_STUDENATA];
        Ispit[] ispiti = new Ispit[BROJ_ISPITNIH_ROKOVA];

        for(int i = 0;i<BROJ_PROFESORA;i++){
            System.out.println("Unesite " + (i+1) +". profesora: ");
            profesori[i] = unesiProfesora(unos,i+1);
        }
        for(int i = 0; i<BROJ_PREDMETA;i++){
            System.out.println("Unesite " + (i+1)+". predmet: ");
            predmeti[i] = unesiPredmet(unos,profesori);
        }
        for(int i = 0; i<BROJ_STUDENATA;i++){
            System.out.println("Unesite " + (i+1)+". studenta: ");
            studenti[i] = unesiStudenta(unos, i+1);
        }
        for(int i = 0; i<BROJ_ISPITNIH_ROKOVA;i++){
            System.out.println("Unesite " + (i+1)+". ispitni rok: ");
            ispiti[i] = unesiIspitniRok(unos,  predmeti,studenti);
        }

        for (int i = 0; i<BROJ_ISPITNIH_ROKOVA;i++){
            ispisiIspitniRok(ispiti, i);
        }

    }
    private static void ispisiIspitniRok(Ispit[] ispiti, Integer redniBroj){
        String ocjena = switch (ispiti[redniBroj].getOcjena()){
            case 5: yield "izvrstan";
            case 4: yield "vrlo dobar";
            case 3: yield "dobar";
            case 2: yield  "dovoljan";
            case 1: yield "nedovoljan";
            default:  yield "nepoznata ocjena";
        };
        if(ispiti[redniBroj].getOcjena().equals(5) ){
            System.out.print("Student: " + ispiti[redniBroj].getStudent().getIme() + " " +
                    ispiti[redniBroj].getStudent().getPrezime() +
                    " je ostvario ocjenu '" +ocjena+"' na predmetu " +
                    ispiti[redniBroj].getPredmet().getNaziv());
        }else{
            System.out.println("ocijena nije 5");
        }
    }

    private static Profesor unesiProfesora(Scanner unos, Integer redniBroj){
        System.out.print("Unesite sifru " + redniBroj +". profesora: ");
        String sifra = unos.nextLine();
        System.out.print("Unesite ime " + redniBroj + ". profesora: ");
        String ime = unos.nextLine();
        System.out.print("Unesite prezime " + redniBroj + ". profesora: ");
        String prezime = unos.nextLine();
        System.out.print("Unesite titulu " + redniBroj + ". profesora: ");
        String titulu = unos.nextLine();
        return new Profesor(sifra,ime,prezime,titulu);
    }
    private static Student unesiStudenta(Scanner unos, Integer redniBroj){
        System.out.print("Unesite ime " + redniBroj +". studenta: ");
        String ime = unos.nextLine();
        System.out.print("Unesite prezime " + redniBroj +". studenta: ");
        String prezime = unos.nextLine();
        System.out.print("Unesite datum rodenja studenta " + prezime + " " + ime +" u formatu (dd.MM.yyyy.:)");
        String datumRodenjaString = unos.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate datumRodenja = LocalDate.parse(datumRodenjaString, formatter);
        System.out.print("Unesite JMBAG studenta: " + ime + " "+prezime+": ");
        String jmbag = unos.nextLine();
        return new Student(ime,prezime,jmbag,datumRodenja);
    }
    private static Predmet unesiPredmet(Scanner unos, Profesor[] profesor){
        System.out.print("Unesite sifru predmeta: ");
        String sifra = unos.nextLine();
        System.out.print("Unesite naziv predmeta: ");
        String naziv = unos.nextLine();
        System.out.print("Unesite broj ECTS bodova za predmeta '" + naziv +"':");
        Integer brojECTSBodova = unos.nextInt();
        unos.nextLine();
        System.out.println("Odaberite profesora:");
        for(int i = 0; i<BROJ_PROFESORA; i++){
            System.out.println((i+1)+"."+profesor[i].getIme()+" "+profesor[i].getPrezime());
        }
        System.out.print("Odabir >> ");
        Integer redniBrojProfesora = unos.nextInt();
        unos.nextLine();
        System.out.print("Unesite broj studenata na predmetu '" + naziv +"':");
        Integer brojStudenata = unos.nextInt();
        unos.nextLine();
        return new Predmet(sifra, naziv,brojECTSBodova, profesor[redniBrojProfesora-1]);
    }

    private static Ispit unesiIspitniRok(Scanner unos, Predmet[] predmeti, Student[] studenti ){
        System.out.println("Odaberite predmet:");
        for(int i = 0; i<BROJ_PREDMETA;i++){
            System.out.println(">>"+(i+1)+". "+predmeti[i].getNaziv());
        }
        Integer redniBrojPredmeta = unos.nextInt();
        unos.nextLine();

        System.out.println("Odaberite studenta: ");
        for(int i = 0; i<BROJ_STUDENATA;i++){
            System.out.println(">>"+(i+1)+". "+studenti[i].getIme() + " "+studenti[i].getPrezime());
        }
        Integer redniBrojStudenta = unos.nextInt();
        unos.nextLine();

        System.out.print("Unesite ocijenu na ispitu (1-5): ");
        Integer ocijena = unos.nextInt();
        unos.nextLine();

        System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm):");
        String datumString = unos.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
        LocalDateTime datumIspita = LocalDateTime.parse(datumString, formatter);

        return new Ispit(predmeti[redniBrojPredmeta-1], studenti[redniBrojStudenta-1], ocijena, datumIspita);

    }
}
