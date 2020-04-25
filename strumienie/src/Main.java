/*import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = null;
        int letters[] = new int[24]; //dlatego taka wartosc bo najdluzsze slowo ma cos kolo tego
        int letters_counter;
        String word;
        int word_length;
        //int i = 0;
        try {
            in = new BufferedReader(new FileReader("wolnelektury.txt"));
            String l;
            while ((l = in.readLine()) != null) {
                StringTokenizer st1 =
                        new StringTokenizer(l, " -.,?!:;");

                while (st1.hasMoreTokens()) {
                    word = st1.nextToken();
                    word_length = word.length();
                    for (int i = 0; i < 24; i++) {
                        if (i == word_length) {
                            letters[i] = letters[i] + 1;
                        }
                    }
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        for (int i = 0; i < 24; i++) {
            if(i != 0) {
                System.out.println(i + " " + letters[i]);
            }
        }


    }

}
*/
public class BookRental {
    String id;
    String customerName;
 ...
}

public class BookRentals {
    private Vector rentals;
    public String GetRental(rentalId){
        for (int i=0; i< rentals.size(); i++)
        {
            BookRental rental = (BookRental) rentals.elementAt(i);

        }
    }

    public String getCustomerName(String rentalId) {
        for (int i = 0; i < rentals.size(); i++) {
            BookRental rental = (BookRental) rentals.elementAt(i);
            if (rental.getId().equals(rentalId)) {
                return rental.getCustomerName();
            }
        }
        throw new RentalNotFoundException();
    }

    public void deleteRental(String rentalId) {
        for (int i = 0; i < rentals.size(); i++) {
            BookRental rental = (BookRental) rentals.elementAt(i);
            if (rental.getId().equals(rentalId)) {
                rentals.remove(i);
                return;
            }
        }
        throw new RentalNotFoundException();
    }
}

public class RentalNotFoundException extends Exception {
 ...
}////////////////
//Po refaktoryzacji :
public class BookRentals {
    private Vector rentals;

    public String getCustomerName(String rentalId) {
        return rentals.elementAt(getIndex(rentalId)).getCustomerName();
    }

    public void deleteRental(String rentalId) {
        rentals.remove(getIndex(rentalId));
    }

    private int getIndex(String id){
        for (int i = 0; i < rentals.size(); i++) {
            BookRental rental = (BookRental) rentals.elementAt(i);
            if (rental.getId().equals(rentalId)) {
                return i;
            }
        }
        throw new RentalNotFoundException();
    }
}
//ZADANIE@
////////////////////////////////////////////////////////
class ReportCatalogueIndexCommandParser {

    final String NO_GROUPING = "orgNoGrouping";
    static final int ORG_CATALOG = 0;
    static final int PART_CATALOG = 1;

    int getGroupingType(String grouping) {
        if (grouping.equals(NO_GROUPING)) {
            return ORG_CATALOG;
        } else if (grouping.equals("orgGroupByCountry")) {
            return ORG_CATALOG;
        } else if (grouping.equals("orgGroupByTypeOfOrgName")) {
            return ORG_CATALOG;
        } else if (grouping.equals("part")) {
            return PART_CATALOG;
        } else
            throw new IllegalArgumentException("Invalid grouping!");
    }
 ...
}

//Po refaktoryzacji- zamist wielu ifach jeden z "or"ami
class ReportCatalogueIndexCommandParser {

    final String NO_GROUPING = "orgNoGrouping";
    static final int ORG_CATALOG = 0;
    static final int PART_CATALOG = 1;

    int getGroupingType(String grouping) {
        if (grouping.equals("orgGroupByCountry") || grouping.equals("orgGroupByTypeOfOrgName") || grouping.equals(NO_GROUPING)) {
            return ORG_CATALOG;
        }else if (grouping.equals("part")) {
            return PART_CATALOG;
        } else
            throw new IllegalArgumentException("Invalid grouping!");
    }
 ...
}
//Zadania przed i po refaktoryzacji
class Order {
 ...
    boolean IsSameString(String s1, String s2) {
        if (s1 == s2) return true;
        if (s1 == null) return false;
        return (s1.equals(s2));
    }
}

class Mail {
 ...
    static boolean IsSameString(String s1, String s2) {
        if (s1 == s2)
            return true;
        if (s1 == null)
            return false;
        return (s1.equals(s2));
    }
}


//---------------------------------------------------
class Order {
    // ...
    boolean IsSameString(String s1, String s2) {
        porownaj temp = new  porownaj();
        return porownaj.IsSameString(s1, s2);
    }
}

class Mail {
    // ...
    static boolean IsSameString(String s1, String s2) {
        porownaj temp = new  porownaj();
        return porownaj.IsSameString(s1, s2);
    }
}

class porownaj{
    boolean IsSameString(String s1, String s2) {
        if (s1 == s2)
            return true;
        if (s1 == null)
            return false;
        return (s1.equals(s2));
    }
}
/////////////ZADANIE przed i po (jeszcze tą ostatnia metode mozna zrefaktoryzowac(getTotalfee () )
public class BookRental {
    private String bookTitle;
    private String author;
    private Date rentDate;
    private Date dueDate;
    private double rentalFee;

    public boolean isOverdue() {
        Date now = new Date();
        return dueDate.before(now);
    }
    public double getTotalFee() {
        return isOverdue() ? 1.2 * rentalFee : rentalFee;
    }
}

public class MovieRental {
    private String movieTitle;
    private int classification;
    private Date rentDate;
    private Date dueDate;
    private double rentalFee;

    public boolean isOverdue() {
        Date now = new Date();
        return dueDate.before(now);
    }
    public double getTotalFee() {
        return isOverdue() ? 1.3 * rentalFee : rentalFee;
    }
}





//--------------------------------------------
public class Rental{

    protected String author;
    protected Date rentDate;
    protected Date dueDate;
    protected double rentalFee;

    public boolean isOverdue() {
        Date now = new Date();
        return dueDate.before(now);
    }

    public double getTotalFee() {
        return isOverdue() ? x * rentalFee : rentalFee;
    }
}

public class BookRental extends Rental{
    private String bookTitle;
    private String author;

    public double getTotalFee() {
        return isOverdue() ? 1.2 * rentalFee : rentalFee;
    }
}
public class MovieRental extends Rental {
    private String movieTitle;
    private int classification;

    public double getTotalFee() {
        return isOverdue() ? 1.3 * rentalFee : rentalFee;
    }
}






