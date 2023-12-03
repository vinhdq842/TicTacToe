package com.soe.tictactoe;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author SOE
 */

public class GameServer {
    private static final int PORT = 8420;
    private ServerSocket server;
    private Socket clientX, clientO;
    private Game game;
    private int curTurn = 1;

    public static void main(String[] args) {
        new GameServer().start();
    }

    public GameServer(){
        game = new Game();
    }

    public void start() {
        try {
            server = new ServerSocket(GameServer.PORT);
            clientX = server.accept();
            clientO = server.accept();
            ClientProcess cp1 = new ClientProcess(clientX, 1);
            ClientProcess cp2 = new ClientProcess(clientO, 2);
            cp1.setPair(cp2);
            cp2.setPair(cp1);
            cp1.process();
            cp2.process();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class ClientProcess implements Runnable {
        Socket socket;
        Thread thread;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        ClientProcess pair;

        private ClientProcess(Socket socket, int turn) {
            this.socket = socket;
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                oos.write(turn);
                oos.writeObject(game);
                oos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setPair(ClientProcess pair) {
            this.pair = pair;
        }

        public void process() {
            if (thread == null) thread = new Thread(this);
            thread.start();
        }


        @Override
        public void run() {
            while (true) {
                try {
                    Game game = (Game) ois.readObject();
                    curTurn = curTurn == 1 ? 2 : 1;
                    oos.write(curTurn);
                    oos.writeObject(game);
                    oos.flush();
                    pair.oos.write(curTurn);
                    pair.oos.writeObject(game);
                    pair.oos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }

        }

    }


}