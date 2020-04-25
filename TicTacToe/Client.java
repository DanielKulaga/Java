import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Color;



public class Client {
    private Socket socket;
    private JFrame frame = new JFrame();
    private Button[] siatka = new Button[9];
    private Button actualButton;
    private PrintWriter out;
    private Scanner in;


    public static final Color PURPLE = new Color(19, 19, 153);

    private Client() throws Exception {
        String IP = "localhost";
        int port = 56999;
        socket = new Socket(IP, port);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        JPanel siatkaPanel = new JPanel();
        siatkaPanel.setBackground(PURPLE);
        siatkaPanel.setLayout(new GridLayout(3, 3, 6, 6));
        for (int i = 0; i < siatka.length; i++) {
            final int j = i;
            siatka[i] = new Button();
            siatka[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    actualButton = siatka[j];
                    out.println(j);//index
                }
            });
            siatkaPanel.add(siatka[i]);
        }
        frame.getContentPane().add(siatkaPanel);
    }

    private void start() {
        String information = in.nextLine();
        char symbol = information.charAt(0); //send player symbol
        char oponnentSymbol = symbol;
        if(oponnentSymbol == 'X')
        {
            symbol = 'O';

        }else{
            symbol = 'X';
        }

        frame.setTitle("TicTacToe  " + symbol); // nazwa i rodzaj gracza
        while (in.hasNextLine()) {

            information = in.nextLine();//poczatkowa informacja od serwera
            if (information.equals("Move approved")) { //wykonalem poprawny ruch
                actualButton.label.setText(symbol + "");
                actualButton.repaint();
            } else if (information.startsWith("Opponent move")) {
                int i = Integer.parseInt(information.substring(13));//poberam po zakonczonej informacji z miejsca 14 rruch przeciwnika
                siatka[i].label.setText(oponnentSymbol + "");//nanosze ruch na siatke
                siatka[i].repaint();//jeszcze raz dla przeciwnika
            } else if (information.equals("Win")) {
                JOptionPane.showMessageDialog(frame, "Gratuluje wygranej");
                break;
            } else if (information.equals("Lose")) {
                JOptionPane.showMessageDialog(frame, "Nastepnym razem bedzie lepiej ;)");
                break;
            } else if (information.equals("REMIS")) {
                JOptionPane.showMessageDialog(frame, "Remis");
                break;
            } else if (information.equals("ERROR")) {
                JOptionPane.showMessageDialog(frame, "Bledny ruch \n THE END");
                break;
            }
        }
        frame.dispose();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Button extends JPanel {
        JLabel label = new JLabel();

        Button() {
            setLayout(new GridBagLayout());
            label.setForeground(Color.red);
            label.setFont(new Font("Italic", Font.PLAIN, 70));
            add(label);
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setSize(510, 510);
        client.frame.setLocationRelativeTo(null);
        client.frame.setVisible(true);
        client.start();
    }
}