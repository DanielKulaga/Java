/*
Daniel Kułaga 09.12.2019r.
Abu uruchomic program nalezy w arguemncie uruchomienia programu podac "localhost".
Pozniej uruchomic server.java pozniej  client.java ( dwa razy) aby bylo dwoch graczy
i cieszyc sie grą :) :)
Korzystalem z : - dokumentacja java i strona z przykladem
https://cs.lmu.edu/~ray/notes/javanetexamples/?fbclid=IwAR0-dAA-uDOSo4djInRhY8bbgSxvCqkklH99od7pwMFLmwj0tOgfwdyeTpA#tictactoe
 */
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = 56999; // liczba ktora miesci sie w wolnym zakresie
        try (ServerSocket serversocket = new ServerSocket(port)) { //
            System.out.println("Serwer dziala - klienci moga dolaczyc do gry");
            ExecutorService pool = Executors.newFixedThreadPool(20);
            TicTacToe ticTacToe = new TicTacToe();
            pool.execute(ticTacToe.new Player(serversocket.accept(), 'O')); //polaczenie zaakceptowane nadanie symbolu O
            pool.execute(ticTacToe.new Player(serversocket.accept(), 'X')); //polaczenie zaakceptowane nadanie symbolu X
        }
    }
}

class TicTacToe {
    private int counter = 0;
    private Player actualPlayer;
    private Player[] tab = new Player[9];

    private boolean hasWinner() { //mechanizm dzialania kolka i krzyzyk'a
        return (tab[0] != null && tab[0] == tab[1] && tab[0] == tab[2])
                || (tab[3] != null && tab[3] == tab[4] && tab[3] == tab[5])
                || (tab[6] != null && tab[6] == tab[7] && tab[6] == tab[8])
                || (tab[0] != null && tab[0] == tab[3] && tab[0] == tab[6])
                || (tab[1] != null && tab[1] == tab[4] && tab[1] == tab[7])
                || (tab[2] != null && tab[2] == tab[5] && tab[2] == tab[8])
                || (tab[0] != null && tab[0] == tab[4] && tab[0] == tab[8])
                || (tab[2] != null && tab[2] == tab[4] && tab[2] == tab[6]
        );
    }

    private synchronized void move(int position, Player player) {    //position - konkretna kratka
        if (player != actualPlayer || player.opponent == null || tab[position] != null) {
            throw new IllegalStateException("BLAD");   // wyrzucam blad
        } else {
            tab[position] = actualPlayer; //ktora wybral kratke gracz
            counter++;
            actualPlayer = actualPlayer.opponent;// zamiana zawodnikow
        }
    }


    class Player implements Runnable {
        Player opponent;
        char symbol;
        Socket socket; // wysylanie odbieranie informacji
        Scanner input;
        PrintWriter output;

        Player(Socket socket, char symbol) {
            this.socket = socket;
            this.symbol = symbol;
        }

        @Override
        public void run() {
            try {
                input = new Scanner(socket.getInputStream());
                output = new PrintWriter(socket.getOutputStream(), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (symbol == 'O') {
                actualPlayer = this; // ruch przeciwnika
            } else {
                opponent = actualPlayer; // dwa razy nie moze byc np. krzyzyk
                opponent.opponent = this;
            }
            output.println(symbol);//wysyła klinetowi symbol za pomoca inputstream'a, client odczytuje

            while (input.hasNextLine()) { // kiedy clien'ci wysylaja jakas wiadomosc
                int moveIndex = input.nextInt();//indexowane pola
                try {
                    move(moveIndex, this); //wykonuje ruch
                    output.println("Move approved");//wysylam informacje ze ruch dotarl
                    opponent.output.println("Opponent move" + moveIndex);
                    if (hasWinner()) {
                        output.println("Win");
                        opponent.output.println("Lose");
                    }while(counter == 9) {
                        output.println("REMIS");
                        opponent.output.println("REMIS");
                    }
                } catch (IllegalStateException e) {
                    output.println(e.getMessage());
                }
            }
        }
    }
}
