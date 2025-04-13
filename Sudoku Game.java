import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Sudoku {
    private static final int SIZE = 9;
    private static final int SUBGRIDSIZE = 3;

    private JFrame frame = new JFrame();
    private final JTextField[][] cells = new JTextField[SIZE][SIZE];

    // 0 means empty
    private final int[][] puzzle = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    public Sudoku() {
        frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        Font font = new Font("SansSerif", Font.BOLD, 20);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(font);
                cell.setBorder(BorderFactory.createLineBorder(Color.gray));

                if (puzzle[row][col] != 0) {
                    cell.setText(String.valueOf(puzzle[row][col]));
                    cell.setEditable(false);
                    cell.setBackground(new Color(230, 230, 230));
                }

                cells[row][col] = cell;
                boardPanel.add(cell);
            }
        }

        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(e -> {
            if (isSolved()) {
                JOptionPane.showMessageDialog(frame, "Congratulations! Puzzle Solved!");
            } else {
                JOptionPane.showMessageDialog(frame, "Solution is not correct. Try again!");
            }
        });

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(checkButton, BorderLayout.SOUTH);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    private boolean isSolved() {
        int[][] board = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String text = cells[i][j].getText().trim();
                if (text.isEmpty() || !text.matches("[1-9]")) return false;
                board[i][j] = Integer.parseInt(text);
            }
        }

        return isValidBoard(board);
    }

    private boolean isValidBoard(int[][] board) {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            boolean[] rowCheck = new boolean[SIZE + 1];
            boolean[] colCheck = new boolean[SIZE + 1];
            for (int j = 0; j < SIZE; j++) {
                int r = board[i][j];
                int c = board[j][i];
                if (rowCheck[r] || colCheck[c]) return false;
                rowCheck[r] = true;
                colCheck[c] = true;
            }
        }

        // Check subgrids
        for (int row = 0; row < SIZE; row += SUBGRIDSIZE) {
            for (int col = 0; col < SIZE; col += SUBGRIDSIZE) {
                boolean[] blockCheck = new boolean[SIZE + 1];
                for (int i = 0; i < SUBGRIDSIZE; i++) {
                    for (int j = 0; j < SUBGRIDSIZE; j++) {
                        int val = board[row + i][col + j];
                        if (blockCheck[val]) return false;
                        blockCheck[val] = true;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Sudoku::new);
    }
}
