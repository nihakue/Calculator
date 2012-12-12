/* Gabriel West
 * CS 2410
 * Project 1 Calculator
 * 11/19/12
 * IDE: Eclipse
 */

package west.gabriel;
import java.awt.BorderLayout;

//These are the external libraries that I use. They are located in jeval-0.9.4 folder
import net.sourceforge.*;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

//The rest of these are normal Java libraries
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Calculator class (this is the main class)

public class Calculator extends JPanel{
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 300;
	static final int HEIGHT = 300;
	int count = 0;
	int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	int[] numCodes = {KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0};
	JButton[] numbersB = new JButton[10];
	JButton[] functionsB = new JButton[10];
	String[] functionsS = {".", "+", "-", "/", "*", "C", "(", ")", "<-", "="};
	String currentOperator;
	boolean justCalculated = false, goingAgain = false;
	boolean firstNum = true;
	char operator;
	JTextField display;
	JTextArea history;
	JPanel numbersP;
	JPanel buttonGrid;
	NumberHandler numberH;
	FunctionHandler functionH;
	Keyculator keyHandler;
	Dimension buttonD = new Dimension(50, 50);
	Evaluator eval = new Evaluator();
	String expression = "";
	Font errorF, calcF, historyF;
	public static JFrame historyFrame;
	Dimension screenSize;
	
	//Constructor initializes the UI and registers action listeners.
	Calculator(){
		screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		buttonGrid = new JPanel(new GridLayout(5, 4));
		
		numberH = new NumberHandler();
		functionH = new FunctionHandler();
		keyHandler = new Keyculator();
		addKeyListener(keyHandler);
		
		initFonts();
		initHistory();
		initDisplay();
		initButtons();
		setFocusable(true);
		setFocusCycleRoot(true);
		setVisible(true);
		
	}
	
	//Creates the fonts
	
	private void initFonts() {
		historyF = new Font("Courier", Font.BOLD, 14);
		errorF = new Font("Dialog", Font.ITALIC, 12);
		calcF = new Font("Courier", Font.BOLD, 28);
		
	}

	//Creates the History window and text area, positions it to the right of the calculator
	
	private void initHistory() {
		int widwleft = (screenSize.width/2)+WIDTH/2;
		int widwtop = (screenSize.height/2)-HEIGHT/2;
		history = new JTextArea(1, 1);
		history.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		history.setFont(historyF);
		history.setEditable(false);
		JScrollPane pane = new JScrollPane(history);
		historyFrame = new JFrame("History");
		historyFrame.getContentPane().add(pane);
		historyFrame.setResizable(false);
		historyFrame.setFocusable(false);
		historyFrame.setSize(WIDTH, HEIGHT);
		historyFrame.setLocation(widwleft, widwtop);
		historyFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		historyFrame.setVisible(false);
		
	}

	//Initializes the calculator text area, adds itself to the calculator
	private void initDisplay() {
		display = new JTextField();
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setFont(calcF);
		display.setEditable(false);
		this.add(display, BorderLayout.NORTH);
		display.setFocusable(false);
	}

	//Adds all the buttons and registers their action listeners.
	private void initButtons() {
		for (int i = 0; i<numbersB.length; i++){
			numbersB[i] = new JButton(Integer.toString(i));
			numbersB[i].setSize(buttonD);
			numbersB[i].addActionListener(numberH);
			numbersB[i].setFocusable(false);
		}
		for (int i = 0; i<functionsB.length; i++){
			functionsB[i] = new JButton(functionsS[i]);
			functionsB[i].setSize(buttonD);
			functionsB[i].addActionListener(functionH);
			functionsB[i].setFocusable(false);
		}
		buttonGrid.add(functionsB[6]);
		buttonGrid.add(functionsB[7]);
		buttonGrid.add(functionsB[8]);
		buttonGrid.add(functionsB[5]);
		buttonGrid.add(numbersB[7]);
		buttonGrid.add(numbersB[8]);
		buttonGrid.add(numbersB[9]);
		buttonGrid.add(functionsB[3]);
		buttonGrid.add(numbersB[4]);
		buttonGrid.add(numbersB[5]);
		buttonGrid.add(numbersB[6]);
		buttonGrid.add(functionsB[4]);
		buttonGrid.add(numbersB[1]);
		buttonGrid.add(numbersB[2]);
		buttonGrid.add(numbersB[3]);
		buttonGrid.add(functionsB[2]);
		buttonGrid.add(numbersB[0]);
		buttonGrid.add(functionsB[0]);
		buttonGrid.add(functionsB[9]);
		buttonGrid.add(functionsB[1]);
		this.add(buttonGrid, BorderLayout.CENTER);
		buttonGrid.setLocation(0, 0);
		buttonGrid.setVisible(true);
		buttonGrid.setFocusable(false);
		buttonGrid.addKeyListener(keyHandler);
	
	}

	//Handles number buttons only
	public class NumberHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			resetFonts();
			input(e.getActionCommand());
		}
	}
	//Handles function buttons only
	public class FunctionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String operator = e.getActionCommand();
			resetFonts();
			
			if(operator.equals("=")){
				calculate();
			}
			
			else if (operator == "C"){
				display.setText("");
				expression = "";
			}
			else if(operator == "<-"){
				backspace();
			}
			else{
				input(operator);
			}
		}
	}
	//Handles both functions and numbers for keys pressed
	public class Keyculator extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			String token = String.valueOf(e.getKeyChar());
			System.out.println(token);
			resetFonts();
			if(e.getKeyCode() == 8)
				backspace();
		
			if(token.equals("=") || e.getKeyCode()==10)
				calculate();
			else{
				input(token);
			}
		}
	}
	//Checks to see if the expression in the display ends with a valid operator
	private boolean alreadyMathing(){
		String temp = expression.substring(expression.length()-1);
		System.out.println(temp);
		for (String op:functionsS){
			if(temp.equals(op)){
				return true;
			}
		}
		return false;
	}
	
	//Method for inputting numbers and functions.
	public void input(String token) {
		//If the token is a number, the program checks to see if it just completed an operation, if so
		//it clears the display first.
		if (isNumber(token)){
			if (justCalculated){
				goingAgain = false;
				clearAll();
				justCalculated = false;
			}
			expression += token;
			display.setText(expression);
			return;
		}
		//If the token is a function or operator, first checks to see if the display conatins an calculated expression,
		//if so, it assumes the user wants to re use the answer, so it leaves the expression intact.
		else if (isFunction(token)){
			if (justCalculated){
				goingAgain = true;
			}
			//This section of code checks the last character in the expression, and prevents multiple operators
			//from stacking up on eachother. If a different operator is entered, it replaces the existing operator.
			if (expression.length()>0 
					&& alreadyMathing() 
					&& !(token.equals("(") || token.equals(")")) 
					&& !expression.endsWith(")")){
				
				if (isLast(token)){
					return;
				}
				else{
					backspace();
					expression += token;
					display.setText(expression);						
				}
			}
			else{
				expression+= token;
				display.setText(expression);
			}
			justCalculated = false;
		}
		goingAgain = false;
	}
	//Checks the token against an array of acceptable operators
	private boolean isFunction(String token) {
		for (int i = 0; i < functionsS.length; i++) {
			if (token.equals(functionsS[i])){
				return true;
			}
		}
		return false;
	}
	//Checks the token against an array of acceptable numbers
	private boolean isNumber(String token) {
		for (int i = 0; i<numbers.length; i++){
			if (token.equals(String.valueOf(numbers[i]))){
				return true;
			}
		}
		return false;
	}
	//Evaluates the expression, sends a papertrail to history, and handles any formatting exceptions.
	private void calculate() {
		try {
			String tempExpression = eval.evaluate(expression);
			history.append(expression + "\n" + tempExpression + "=\n\n");
			expression = tempExpression;
			fixFormat();
			display.setText("= " + expression);
			
		} catch (EvaluationException e1) {
			display.setFont(errorF);
			display.setText(e1.getMessage());
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(new Calculator(), e2.getMessage());
		}
		justCalculated = true;			
	}
	
	private void resetFonts() {
		display.setFont(calcF);
	}
	private void clearAll() {
		display.setText("");
		expression = "";
	}
	
	private void fixFormat(){
		double temp = Double.parseDouble(expression);
		DecimalFormat df = new DecimalFormat("#.#######");
		expression = df.format(temp);
	}
	//Main method creates a JFrame and adds a new calculator object.
	public static void main(String[] args){
		JFrame test = new JFrame("Calculator");
		
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setAlwaysOnTop(true);
		test.setResizable(false);
		test.getContentPane().add(new Calculator());
		menuSetup(test);
		test.pack();
		test.setLocationRelativeTo(null);
		test.setVisible(true);
		test.setFocusable(false);
	}

	private void backspace() {
		if (expression.length()>0){
			expression = expression.substring(0, expression.length()-1);
			display.setText(expression);
		}
	}
	//Checks a token against the last character in the expression
	private boolean isLast(String op){
		int begin = expression.length()-1;
		if(expression.substring(begin).equals(op))
			return true;
		return false;	
	}
	//Initializes the menu bar
	private static void menuSetup(JFrame j) {
		JMenuBar menuMB = new JMenuBar();
		JMenu fileM = new JMenu("File");
		JMenu viewM = new JMenu("View");
		JMenuItem exitMI = new JMenuItem("Exit");
		JMenuItem historyMI = new JMenuItem("Show History");
		MenuHandler menuH = new MenuHandler();
		j.setJMenuBar(menuMB);
		menuMB.add(fileM);
		menuMB.add(viewM);
		fileM.add(exitMI);
		viewM.add(historyMI);
		exitMI.addActionListener(menuH);
		historyMI.addActionListener(menuH);
	}
}
