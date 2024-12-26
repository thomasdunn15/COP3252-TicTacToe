import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.ImageIcon;


public class TicTacToe implements ActionListener{

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JPanel title = new JPanel();
    JPanel name = new JPanel();
    JPanel buttonGrid = new JPanel();
    JLabel titleText = new JLabel();
    JLabel nameText = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;
    int spacesLeft = 0;
    ImageIcon X = new ImageIcon(Objects.requireNonNull(getClass().getResource("/fsuLogo.jpg")));
    ImageIcon O = new ImageIcon(Objects.requireNonNull(getClass().getResource("/ufLogo.jpg")));
    ImageIcon placeholder = new ImageIcon(Objects.requireNonNull(getClass().getResource("/placeholder.jpg")));
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("Settings");
    ButtonGroup player1Group = new ButtonGroup();
    ButtonGroup player2Group = new ButtonGroup();
    JMenuItem player1Human = new JRadioButtonMenuItem("Player 1: Human Player", true);
    JMenuItem player1Computer = new JRadioButtonMenuItem("Player 1: Computer Player");
    JMenuItem player2Human = new JRadioButtonMenuItem("Player 2: Human Player", true);
    JMenuItem player2Computer = new JRadioButtonMenuItem("Player 2: Computer Player");
    JMenuItem newGame = new JMenuItem("New Game");
    boolean isHumanPlayer1 = true, isHumanPlayer2 = true;
    Random randomTurn = new Random();

    TicTacToe(){

        //creates the frame of the program
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(123, 47, 60));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setIconImage(X.getImage());

        //creates the menubar to file menu
        menuBar.add(fileMenu);

        //groups radio buttons together
        player1Group.add(player1Human);
        player1Group.add(player1Computer);

        //adds radio buttons to file menu
        fileMenu.add(player1Human);
        fileMenu.add(player1Computer);

        //separates each radio button
        fileMenu.addSeparator();

        //groups radio buttons together
        player2Group.add(player2Human);
        player2Group.add(player2Computer);

        //adds radio buttons to file menu
        fileMenu.add(player2Human);
        fileMenu.add(player2Computer);

        //separates radio buttons from new game button
        fileMenu.addSeparator();

        //adds new game button to file menu
        fileMenu.add(newGame);

        //adds the menu bar to the frame
        frame.setJMenuBar(menuBar);

        fileMenu.setMnemonic(KeyEvent.VK_S); // Alt + S for settings
        newGame.setMnemonic(KeyEvent.VK_N); // N for New Game

        // code below to set the title text to say FSU and also format and color the text
        titleText.setBackground(new Color(206, 184, 136, 255));
        frame.setForeground(new Color(6, 14, 39, 8));
        titleText.setFont(new Font("Calibri", Font.BOLD, 75));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        titleText.setText("FSU");
        titleText.setOpaque(true);

        //code below to put my name on the bottom and format and color the text
        nameText.setBackground(new Color(206, 184, 136, 255));
        frame.setForeground(new Color(0, 0, 0));
        nameText.setFont(new Font("SansSerif", Font.BOLD, 75));
        nameText.setHorizontalAlignment(JLabel.CENTER);
        nameText.setText("Thomas Dunn");
        nameText.setOpaque(true);

        //sets the layout and bounds of the title panel
        title.setLayout(new BorderLayout());
        title.setBounds(0,0,800,100);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 5);

        //sets layout and bounds of name panel
        name.setLayout(new BorderLayout());
        name.setBounds(1, 100, 100, 100);

        //adds the title text and border to title panel
        title.add(titleText);
        title.setBorder(border);

        //sets the button grid panel. as a 3x3 panel; also sets background color.
        buttonGrid.setLayout(new GridLayout(3,3,10,10));
        buttonGrid.setBackground(new Color(0,0,0));

        //sets the buttons and adds each one to the action listener
        for (int i=0; i<9;i++){
            buttons[i] = new JButton(placeholder);
            buttonGrid.add(buttons[i]);
            buttons[i].setVisible(true);
            buttons[i].addActionListener(this);

        }

        //add the name text and border to the name panel
        name.add(nameText);
        name.setBorder(border);

        //adds the title panel, name panel, and button grid to the frame.
        frame.add(title, BorderLayout.NORTH);
        frame.add(name, BorderLayout.SOUTH);
        frame.add(buttonGrid);

        //adds menu buttons to the action listener.
        player1Human.addActionListener(this);
        player1Computer.addActionListener(this);
        player2Human.addActionListener(this);
        player2Computer.addActionListener(this);
        newGame.addActionListener(this);

        //starts the game with first function.
        firstTurn();

    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource().equals(player1Human)){
            if (player1Human.isSelected()) {
                isHumanPlayer1 = true;
                newGame();
            }
        }
        //if player1 radio button is selected it starts
        // a new game and sets isHumanPLayer1 to false.
        if (e.getSource().equals(player1Computer)){
            if (player1Computer.isSelected()) {
                isHumanPlayer1 = false;
                newGame();
            }
        }
        //if player1 radio button is selected it starts
        // a new game and sets isHumanPLayer2 to true.
        if (e.getSource().equals(player2Human)){
            if (player2Human.isSelected()) {
                isHumanPlayer2 = true;
                newGame();
            }
        }
        //if player1 radio button is selected it starts
        // a new game and sets isHumanPLayer2 to false.
        if (e.getSource().equals(player2Computer)){
            if (player2Computer.isSelected()) {
                isHumanPlayer2 = false;
                newGame();
            }
        }
        //if new game button is selected it starts a new game.
        if (e.getSource().equals(newGame)){
            newGame();
        }

        //player vs. player
        if (isHumanPlayer1 && isHumanPlayer2){
            //for loop to set the icon of the button that is clicked and check if someone won.
            for (int i = 0; i < 9; i++) {
            if (e.getSource().equals(buttons[i])) {
                if (player1_turn) {
                    if (buttons[i].getIcon().equals(placeholder)) {
                        buttons[i].setIcon(X);
                        player1_turn = false;
                        titleText.setText("O's Turn");
                        check();
                    }
                } else {
                    if (buttons[i].getIcon().equals(placeholder)) {
                        buttons[i].setIcon(O);
                        player1_turn = true;
                        titleText.setText("X's Turn");
                        check();
                    }
                }
            }
        }
            //player vs. computer
        }else if (isHumanPlayer1 && !isHumanPlayer2){

            //created vector so the moves could be added
            // to then be removed from num of moves that could be made for computer.

            Vector<Integer> randNum = new Vector<>();
            randNum.add(1);
            randNum.add(2);
            randNum.add(3);
            randNum.add(4);
            randNum.add(5);
            randNum.add(6);
            randNum.add(7);
            randNum.add(8);
            randNum.add(9);

            //for loop to set the icon of the button that is clicked and check if someone won.
            for (int i = 0; i < 9; i++) {
                if (player1_turn){
                    if (e.getSource().equals(buttons[i])) {
                       if (buttons[i].getIcon().equals(placeholder)) {
                          buttons[i].setIcon(X);
                           player1_turn = false;
                           titleText.setText("O's Turn");
                           check();
                           randNum.remove(i);
                       }
                    }
                } else {
                    //calls chooseBest function to make the computer move.
                    chooseBest(X, O, i, randNum);
                    player1_turn = true;
                    titleText.setText("X's Turn");
                    check();
                }
            }
            //computer vs. player
        } else if (!isHumanPlayer1 && isHumanPlayer2){

            //created vector so the moves could be added
            // to then be removed from num of moves that could be made for computer.

            Vector<Integer> randNum = new Vector<>();
            randNum.add(1);
            randNum.add(2);
            randNum.add(3);
            randNum.add(4);
            randNum.add(5);
            randNum.add(6);
            randNum.add(7);
            randNum.add(8);
            randNum.add(9);

            //for loop to set the icon of the button that is clicked and check if someone won.
            for (int i = 0; i < 9; i++) {
                if(player1_turn){
                    //calls chooseBest function to make the computer move.
                    chooseBest(X, O, i, randNum);
                    player1_turn = false;
                    titleText.setText("O's Turn");
                    check();
                } else if(e.getSource().equals(buttons[i])){
                    if (buttons[i].getIcon().equals(placeholder)) {
                        buttons[i].setIcon(O);
                        player1_turn = true;
                        titleText.setText("X's Turn");
                        check();
                    }
                }
            }
            //computer vs. computer
        }else if (!isHumanPlayer1 && !isHumanPlayer2){

            //created vector so the moves could be added
            // to then be removed from num of moves that could be made for computer.

            Vector<Integer> randNum = new Vector<>();
            randNum.add(1);
            randNum.add(2);
            randNum.add(3);
            randNum.add(4);
            randNum.add(5);
            randNum.add(6);
            randNum.add(7);
            randNum.add(8);
            randNum.add(9);

            for (int i = 0; i < 9; i++) {
                if (player1_turn){
                    //calls chooseBest function to make the computer move.
                    chooseBest(X, O, i, randNum);
                    player1_turn = false;
                    titleText.setText("O's Turn");
                    check();
                }else {
                    //calls chooseBest function to make the computer move.
                    chooseBest(X, O, i, randNum);
                    player1_turn = true;
                    titleText.setText("X's Turn");
                    check();
                }
            }
        }
    }

    //sets player1_turn to true
    public void firstTurn(){
        player1_turn = true;
    }

    //function looks through whole board to see if
    // there are two pieces that could add up to be three in a row.
    //if that is true it moves in the place that will make that be true.
    //else, it just chooses a random space to go to.
    public void chooseBest(ImageIcon X, ImageIcon O, int i, Vector<Integer> randNum){
        int[][] winConditions = {
                {0, 1, 2}, // horizontal top row
                {3, 4, 5}, // horizontal middle row
                {6, 7, 8}, // horizontal bottom row
                {0, 3, 6}, // vertical left column
                {1, 4, 7}, // vertical middle column
                {2, 5, 8}, // vertical right column
                {0, 4, 8}, // diagonal top left to bottom right
                {2, 4, 6}  // diagonal top right to bottom left
        };
        for (int[] winCondition : winConditions) {
            if (buttons[winCondition[0]].getIcon().equals(O) &&
                    buttons[winCondition[2]].getIcon().equals(O) &&
                    !buttons[winCondition[1]].getIcon().equals(X) &&
                    buttons[winCondition[1]].getIcon().equals(placeholder)) {
                i = winCondition[1];
                break;
            } else if (buttons[winCondition[0]].getIcon().equals(O) &&
                    buttons[winCondition[1]].getIcon().equals(O) &&
                    !buttons[winCondition[2]].getIcon().equals(X) &&
                    buttons[winCondition[2]].getIcon().equals(placeholder)){
                i = winCondition[2];
                break;
            } else if (buttons[winCondition[1]].getIcon().equals(O) &&
                    buttons[winCondition[2]].getIcon().equals(O) &&
                    !buttons[winCondition[0]].getIcon().equals(X) &&
                    buttons[winCondition[0]].getIcon().equals(placeholder)){
                i = winCondition[0];
                break;
            } else {
                    i = randomTurn.nextInt(randNum.size());
            }
        }
        buttons[i].setIcon(O);
    }

    //function to check and see if there is a win made. Checks all
    //possible win conditions and sees if any buttons have made them.
    //if that is true either x or o wins.
    //if there are no moves left to be made, and neither person has won
    //the tie function is called.
    public void check(){
        int[][] winConditions = {
                {0, 1, 2}, // horizontal top row
                {3, 4, 5}, // horizontal middle row
                {6, 7, 8}, // horizontal bottom row
                {0, 3, 6}, // vertical left column
                {1, 4, 7}, // vertical middle column
                {2, 5, 8}, // vertical right column
                {0, 4, 8}, // diagonal top left to bottom right
                {2, 4, 6}  // diagonal top right to bottom left
        };
        //check X wins conditions
        for (int[] winCondition : winConditions) {
            if (buttons[winCondition[0]].getIcon().equals(X) &&
                    buttons[winCondition[1]].getIcon().equals(X) &&
                    buttons[winCondition[2]].getIcon().equals(X)) {
                xWins();
            } //check O wins conditions
            else if (buttons[winCondition[0]].getIcon().equals(O) &&
                    buttons[winCondition[1]].getIcon().equals(O) &&
                    buttons[winCondition[2]].getIcon().equals(O)) {
                oWins();
            }
        }
        for (int i=0; i<9; i++){
             if (buttons[i].getIcon().equals(placeholder)){
                 spacesLeft++;
             }
        }
        if (spacesLeft == 0)
        {
            tie();
        }
        else{
            spacesLeft = 0;
        }
    }

    public void xWins(){

        titleText.setText("X wins!");

        reset();

    }

    public void oWins(){

        titleText.setText("O wins!");

        reset();

    }
    public void tie(){
        for(int i=0;i<9;i++) {
            buttons[i].setEnabled(false);
        }
        titleText.setText("Tie");

        reset();
    }
    public void reset(){

        //from lines 230 to 245 I used the example from
        // "http://www.java2s.com/example/java-api/java/awt/image/bufferedimage/type_int_argb-4.html"
        //to figure out how to resize an icon.

        Image image = X.getImage();

        int newWidth = image.getWidth(null) /5; // double the width
        int newHeight = image.getHeight(null) /5; // double the height

        Image newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) newImage.getGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, newWidth, newHeight, null);

        g2d.dispose();

        ImageIcon newIcon = new ImageIcon(newImage);

        Object[] options = {"Yes", "No"};
        int result = JOptionPane.showOptionDialog(null, "Would You Like To Play Again?",
                "", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, newIcon, options, options[0]);

        if (result == 0) {
            newGame();
        } else if (result == 1) {
            for(int i=0;i<9;i++){
                buttons[i].setEnabled(false);
            }
        }else{
            for(int i=0;i<9;i++){
                buttons[i].setEnabled(false);
            }
        }
    }

    public void newGame(){
        titleText.setText("FSU");
        for(int i=0;i<9;i++) {
            buttons[i].setIcon(placeholder);
            buttons[i].setEnabled(true);
            buttons[i].setBackground(Color.WHITE);
        }
        firstTurn();
    }
}