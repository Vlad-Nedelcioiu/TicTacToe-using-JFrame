import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe implements ActionListener {

    Random firstMove = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel text_field = new JLabel();
    JButton[] buttons = new JButton[9];

    boolean player1Turn;

    TicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        text_field.setBackground(Color.lightGray);
        text_field.setForeground(Color.blue);
        text_field.setFont(new Font("Times New Roman", Font.BOLD, 35));
        text_field.setHorizontalAlignment(JLabel.CENTER);
        text_field.setText("Tic Tac Toe");
        text_field.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 500, 300);

        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(Color.GRAY);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("Times New Roman", Font.BOLD, 100));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttons[i].setBackground(Color.GRAY);
            buttons[i].setForeground(Color.BLUE);
        }

        title_panel.add(text_field);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);

        firstMove();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1Turn) {
                    if (buttons[i].getText() == "") {
                        buttons[i].setForeground(Color.RED);
                        buttons[i].setText("X");
                        player1Turn = false;
                        text_field.setText("O turn");
                        check();
                    }
                } else {
                    if (buttons[i].getText() == "") {
                        buttons[i].setForeground(Color.BLUE);
                        buttons[i].setText("O");
                        player1Turn = true;
                        text_field.setText("X turn");
                        check();
                    }
                }
            }
        }
    }

    public void firstMove() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (firstMove.nextInt(2) == 0) {
            player1Turn = true;
            text_field.setText("X turn");
        } else {
            player1Turn = false;
            text_field.setText("O turn");
        }
    }

    public void check() {
        // CHECK X

        int[] winningPatterns = {
                0b111000000, 0b000111000, 0b000000111, // Rows
                0b100100100, 0b010010010, 0b001001001, // Columns
                0b100010001, 0b001010100              // Diagonals
        };

        // Convert the board to two bit masks for X and O
        int xPattern = 0, oPattern = 0;
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getText().equals("X")) {
                xPattern |= (1 << i);
            } else if (buttons[i].getText().equals("O")) {
                oPattern |= (1 << i);
            }
        }

        // Check for win
        for (int winningPattern : winningPatterns) {
            if ((xPattern & winningPattern) == winningPattern) {
                xWin(); // Adjusted to not need parameters
                return;
            } else if ((oPattern & winningPattern) == winningPattern) {
                oWin(); // Adjusted to not need parameters
                return;
            }
        }
        if (Integer.bitCount(xPattern | oPattern) == 9) {
            // The board is full without any winner
            draw();
        }
    }

    private void draw() {
        text_field.setText("DRAW");
        text_field.setForeground(Color.GREEN);
    }


    public void xWin() {
        text_field.setText("X WINS");
        text_field.setForeground(Color.GREEN);
    }

    public void oWin() {
        text_field.setText("O WINS");
        text_field.setForeground(Color.GREEN);
    }
}
