import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
//import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.Properties;
import java.io.FileNotFoundException;

public class Ksiazka_tel {
//getResourceAsStream("file.txt")

    public static void main(String[] args) {
        int a;

        Properties p1 = new Properties();
        try{
            p1.load(new FileInputStream(args[0]));
        }catch (FileNotFoundException ex){
            System.out.println("FileNotFoundException");
        }catch (IOException ex){
            System.out.println("IOException");
        }finally {
            //System.out.println("Koniec obslugi wyjatku-finally");
        }


        Scanner scanner = new Scanner(System.in);


        System.out.println("Oto twoja książka telefoniczna :) Wpisz numer operacji, ktora chcesz wykonać:");
        System.out.println("1-Dodaj numer do ksiazki telefoniczej");
        System.out.println("2-Usun numer z ksiazki telefoniczej");
        System.out.println("3-Pokaz wybrany numer telefonu");

        a = scanner.nextInt();
        scanner.nextLine();//przy zmianie typu podaje sie scanner.nextLine();
        switch (a)
        {
            case 1:
                System.out.println("Podaj nazwę kontaktu, ktory chcesz utworzyc:");
                String nazwa = scanner.nextLine();
                System.out.println("Podaj numer kontaktu, ktory chcesz utworzyc:");
                String numer = scanner.nextLine();
                p1.put(nazwa,numer);
                break;
            case 2:
                System.out.println("Podaj nazwę kontaktu, ktory chcesz usunac:");
                nazwa = scanner.nextLine();
                if(p1.containsKey(nazwa))
                {
                    p1.remove(nazwa);
                    System.out.println("Plik zostal usuniety");
                }else
                {
                    System.out.println("Nie ma w ksiazce takiego kontaktu");
                }
                break;
            case 3:
                System.out.println("Podaj nazwa kontaktu, ktorego numer mam wyswietlic:");
                nazwa = scanner.nextLine();
                System.out.println(nazwa +" numer telefonu: " + p1.get(nazwa));
                break;
            default:
                System.out.println("Wybrana opcja jest niedostepna");

        }
        try {
            p1.store(new FileOutputStream(args[0]),null);
        }catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
        }catch(IOException ex){
            System.out.println("IOException");
        } finally{
            //System.out.println("\"Koniec obslugi wyjatku-finally");
        }


    }



}
