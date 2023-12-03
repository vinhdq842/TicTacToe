package com.soe.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author SOE
 */

public class GameClient implements Runnable, ActionListener {
    private static final int PORT = 8420;
    private static final String host = "localhost"; // replace with your server hostname or ip
    Thread gameThread;
    Game game;
    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    JButton[][] buttons = new JButton[3][3];
    JFrame frame;
    int turn = 0;
    int curTurn = 1;

    public static void main(String[] args) {
        new GameClient(650, 200);
    }

    public GameClient(int x, int y) {
        gameThread = new Thread(this);
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();
        frame.setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(3, 3));
        frame.setBounds(x, y, 400, 300);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                contentPane.add(buttons[i][j] = new JButton("", i, j));
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(this);
            }
        frame.setVisible(true);
        gameThread.start();
    }


    @Override
    public void run() {
        while (game == null) {
            try {
                socket = new Socket(host, PORT);
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                turn = ois.read();
                frame.setTitle("Tic Tac Toe" + (turn == 1 ? " - Player 1" : " - Player 2"));
                game = (Game) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        while (true) {
            try {
                curTurn = ois.read();
                game = (Game) ois.readObject();
                updateButtons(game);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int winner(int[][] map) {
        for (int i = 0; i < 3; i++) {
            if (map[i][0] == map[i][1] && map[i][0] == map[i][2] && map[i][0] != -1) return map[i][0] == 1 ? 1 : 2;
        }
        for (int j = 0; j < 3; j++) {
            if (map[0][j] == map[1][j] && map[0][j] == map[2][j] && map[0][j] != -1) return map[0][j] == 1 ? 1 : 2;
        }
        if (map[0][0] == map[1][1] && map[0][0] == map[2][2] && map[0][0] != -1) return map[0][0] == 1 ? 1 : 2;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (map[i][j] == -1) return -1;
        return 0;
    }

    private void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) System.out.print(arr[i][j] + " ");
            System.out.println();
        }
    }

    private void updateButtons(Game game) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (game.map[i][j] != -1)
                    buttons[i][j].setIcon(new ImageIcon(getClass().getResource("/images/" + (game.map[i][j] == 1 ? "x.png" : "o.png"))));
            }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (turn != curTurn) return;
        JButton button = (JButton) e.getSource();
        if (game != null && game.map[button.row][button.col] == -1) {
            game.map[button.row][button.col] = turn == 1 ? 1 : 2;
            try {
                oos.writeObject(game);
                oos.flush();
                int winner;
                if ((winner = winner(game.map)) != -1) {
                    JOptionPane.showMessageDialog(frame, (winner == 1 ? "Player 1 won!" : winner == 2 ? "Player 2 won!" : "Draw!"), "Notification", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
                updateButtons(game);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class JButton extends javax.swing.JButton {
        int row;
        int col;

        JButton(String s, int row, int col) {
            super(s);
            this.row = row;
            this.col = col;
        }

    }

}
