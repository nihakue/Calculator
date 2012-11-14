package west.gabriel;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Calculator extends JPanel{
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 300;
	static final int HEIGHT = 400;
	int count = 0;
	int[] numbers = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	int[] numCodes = {KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0};
	JButton[] numbersB = new JButton[10];
	JButton[] functionsB = new JButton[10];
	String[] functionsS = {".", "+", "-", "/", "*", "C", "(", ")", "%", "="};
	JTextArea display;
	JPanel numbersP;
	JPanel buttonGrid;
	NumberHandler numberH;
	FunctionHandler functionH;
	Keyculator keyHandler;
	Dimension buttonD = new Dimension(50, 50);
	private JMenuBar menuMB;
	private JMenu fileM;
	private JMenuItem exitMI;
	private int total = 0;
	private int scratch;
	
	
	Calculator(){
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		buttonGrid = new JPanel(new GridLayout(5, 4));
		
		numberH = new NumberHandler();
		functionH = new FunctionHandler();
		keyHandler = new Keyculator();
		addKeyListener(keyHandler);
		
		initDisplay();
		initButtons();
		setFocusable(true);
		setVisible(true);
		
		
		
	}
	
	private void initDisplay() {
		display = new JTextArea();
		display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		display.setSize(WIDTH, HEIGHT/5);	
		display.setFont(new Font("Courier", Font.BOLD, 30));
		display.setEditable(false);
		this.add(display, BorderLayout.NORTH);
	}

	private void initButtons() {
		for (int i = 0; i<numbersB.length; i++){
			numbersB[i] = new JButton(Integer.toString(i));
			numbersB[i].setSize(buttonD);
			numbersB[i].addActionListener(numberH);
		}
		for (int i = 0; i<functionsB.length; i++){
			functionsB[i] = new JButton(functionsS[i]);
			functionsB[i].setSize(buttonD);
			functionsB[i].addActionListener(functionH);
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
		this.add(buttonGrid);
		buttonGrid.setLocation(0, 0);
		buttonGrid.setVisible(true);
	
	}

	public class NumberHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			display.append(e.getActionCommand().toString());
			scratch = Integer.parseInt(display.getText());
			System.out.println(scratch);
			System.out.println(total);
		}
	}
	
	public class FunctionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand()=="+"){
				total += scratch;
				scratch = 0;
				display.setText(null);
			}
			if(e.getActionCommand() == "="){
				display.setText(String.valueOf(total));
			}
			if (e.getActionCommand() == "C"){
				total = 0;
				scratch = 0;
				display.setText(null);
			}
		}
	}

	
	public static void main(String[] args){
		JFrame test = new JFrame("Calculator");
		
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setAlwaysOnTop(true);
		test.setResizable(false);
		test.getContentPane().add(new Calculator());
		menuSetup(test);
		test.pack();
		test.setVisible(true);
		
	}
	//Comment
	public class Keyculator extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			for (int i = 0; i<numCodes.length; i++){
				if (e.getKeyCode() == numCodes[i]){
					display.append(e.getKeyText(e.getKeyCode()));
					scratch = Integer.parseInt(display.getText());
					return;
				}
			}
			for (int i = 0; i < functionsS.length; i++) {
				if (String.valueOf(e.getKeyChar()) == functionsS[i]){
					display.append(functionsS[i]);
					System.out.println(functionsS[i]);
				}
			}
		}
	}

	private static void menuSetup(JFrame j) {
		JMenuBar menuMB = new JMenuBar();
		JMenu fileM = new JMenu("File");
		JMenuItem exitMI = new JMenuItem("Exit");
		MenuHandler menuH = new MenuHandler();
		j.setJMenuBar(menuMB);
		menuMB.add(fileM);
		fileM.add(exitMI);
		exitMI.addActionListener(menuH);
	}
}
