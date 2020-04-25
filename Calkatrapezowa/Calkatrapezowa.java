/*URUCHOMIENIE: Aby uruchomic program nalezy go skompilowac i przy uruchamianiu podac
dwa argumenty:
(pierwszy) liczba - poczatek przedziału funkcji z ktorego chcemy policzyc calke (np.1)
(SPACJA)
(drugi) koniec przedziału (np.100).
Wykonał : Daniel Kułaga
*/
public abstract class Calkatrapezowa {
    public double obliczCalke(double a, double b) {
        double n = 10000; //ilosc czesci (podzialow)
        double wynikCalka = 0;
        double h = (b - a) / n;

        for (int i = 2; i <= n; i++) //suma trapezów od 2 do n
        {
            wynikCalka = wynikCalka + f(a + (i - 1) * h);
        }
        wynikCalka = wynikCalka + (f(a) / 2) + (f(b) / 2);  //(f(a + (n+1-1) * h))/2
        wynikCalka *= h;
        return wynikCalka;
    }

    public abstract double f(double x);


    public static void main(String[] args) {
        Calkatrapezowa c1 = new Funkcja();
        System.out.println(c1.obliczCalke(Double.parseDouble(args[0]), Double.parseDouble(args[1])));


    }
}

