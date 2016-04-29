package game.Networking;

import game.GUI.*;
import javafx.util.Pair;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Handler extends Thread {

    Board board;
    String msgArray[];
    String msg;
    PrintStream out;
int count;
    public Handler(String message, PrintStream out)
    {
        msg=message;
        msgArray = message.split(" ");
        board = Board.board;

        this.out = out;
    }

    @Override
    public void run()
    {
        if (msgArray[0].equals("ADDME")) {//Sending list of all the previously connected members
            String message = "";
            for (int i = 0; i < Main.list.size(); i++) {
                message += Main.list.get(i).toString();
            }
            message += "\n";
            out.print(message);
            String addmember = "ADDMEMBER " + msgArray[1] + " " + msgArray[2] + " ";
            if (msgArray.length == 4) {
                addmember += msgArray[3];
            } else {
                addmember += "Player";
            }
            System.out.println(addmember);

            for (int i = 0; i < Main.list.size(); i++) {
                try {
                    Socket s;
                    if (!Main.list.get(i).isbot() && Main.list.get(i).getId() != Main.playerId) {
                        s = new Socket(Main.list.get(i).getHost(), Main.list.get(i).getPort());

                        PrintStream out = new PrintStream(s.getOutputStream());
                        out.print(addmember + "\n");
                        s.close();
                    }

                } catch (UnknownHostException e) {
                    //TODO error handling
                    Main.list.remove(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Main.list.add(
                    new Player(Main.list.size(),
                            msgArray[1],
                            Integer.parseInt(msgArray[2]),
                            1,
                            false,
                            msgArray[3]
                    )
            );

            Menu.gui.updateUsers();


            //ADDMEMBER 127.0.0.1 3128
        } else if (msgArray[0].equals("ADDMEMBER")) {
            Main.list.add(
                    new Player(Main.list.size(),
                            msgArray[1],
                            Integer.parseInt(msgArray[2]),
                            1,
                            false,
                            msgArray[3]
                    )
            );
            Menu.gui.updateUsers();


            //PLAYER i pos
        } else if (msgArray[0].equals("PLAYER")) {
            count = Integer.parseInt(msgArray[msgArray.length - 1]);
            if (Math.abs(count - Main.previousCount) < 3) {

                board.setBarPos(Integer.parseInt(msgArray[1]), Integer.parseInt(msgArray[2]));

            }
            Main.previousCount = count;


            //BALL  x y velx vely
        } else if (msgArray[0].equals("BALL")) {
            count = Integer.parseInt(msgArray[msgArray.length - 1]);
            if (Math.abs(count - Main.previousCount) < 3) {
                board.setBallPos(Integer.parseInt(msgArray[1]), Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]), Integer.parseInt(msgArray[4]), Integer.parseInt(msgArray[5]));
            }
            Main.previousCount = count;

        } else if (msgArray[0].equals("STARTED")) {
            System.out.println("change " + msg + " " + msgArray[1]);
            Main.list.get(Integer.parseInt(msgArray[1])).updateStatus(2);


            //SCORE 0 9
        } else if (msgArray[0].equals("SCORE")) {
            int id = Integer.parseInt(msgArray[1]);
            int score = Integer.parseInt(msgArray[2]);

            Main.list.get(id).Lives = score;
            swing._instance.updateScore(id);
            //

            //DEAD i

            int i = Integer.parseInt(msgArray[1]);
            Main.list.get(i).isAlive = false;
            Board.board.repaint();


            //DISCONNECTED i
        } else if (msgArray[0].equals("DEAD")) {
            int i = Integer.parseInt(msgArray[1]);
            Main.list.get(i).isAlive = false;
            Board.board.repaint();


            //DISCONNECTED i
        } else if (msgArray[0].equals("DISCONNECTED i")) {

            //RECONNECTED i
        } else if (msgArray[0].equals("RECONNECTED i")) {
        }
    }
}
