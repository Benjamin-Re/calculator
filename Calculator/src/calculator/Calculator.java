package calculator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame implements ActionListener, KeyListener{
	private JButton one, two, three, four, five, six, seven, eight, nine, zero, div, mult, min, plus, equals, c, sign, com, sqr;
	private float n, x, y, result;
	private char operator;
	private String text = "";
	private String text2 = "";
	private boolean xFilled = false;
	private boolean warning = false;
	private boolean opClicked = false;
	private boolean resultDisplayed = false;
	public JLabel out, out2;
	private char keysource;
	
	public Calculator () {
		super("Calculator");
		
		
		// 2 containers for buttons and display
		JPanel container = new JPanel();
		JPanel container2 = new JPanel();
		
		
		// JTextField field = new JTextField();
		// Display label
		out = new JLabel();
		out2 = new JLabel();
		out2.setFont(new Font("Arial", 2, 14));
		// set old text above new
		out2.setVerticalAlignment(JLabel.TOP);
		out2.setHorizontalTextPosition(JLabel.LEFT);
		// Container2.add(field);
		container2.add(out2);
		container2.add(out);
		
		
		// Create and add the  buttons
		sqr = new JButton("^2");
		sqr.addActionListener(this);
		
		nine = new JButton("9");
		nine.addActionListener(this);
		eight = new JButton("8");
		eight.addActionListener(this);
		seven = new JButton("7");
		seven.addActionListener(this);
		
		six = new JButton("6");
		six.addActionListener(this);
		five = new JButton("5");
		five.addActionListener(this);
		four = new JButton("4");
		four.addActionListener(this);
		
		three = new JButton("3");
		three.addActionListener(this);
		two = new JButton("2");
		two.addActionListener(this);
		one = new JButton("1");
		one.addActionListener(this);
		
		div = new JButton("/");
		div.addActionListener(this);
		mult = new JButton("*");
		mult.addActionListener(this);
		min = new JButton("-");
		min.addActionListener(this);
		
		plus = new JButton("+");
		plus.addActionListener(this);
		equals = new JButton("=");
		equals.addActionListener(this);
		c = new JButton("C");
		c.addActionListener(this);
		
		zero = new JButton("0");
		zero.addActionListener(this);
		sign = new JButton("+/-");
		sign.addActionListener(this);
		com = new JButton(".");
		com.addActionListener(this);
		
		// Add  buttons to container
		
		container.add(seven);
		container.add(eight);
		container.add(nine);
		container.add(four);
		container.add(five);
		container.add(six);
		container.add(one);
		container.add(two);
		container.add(three);
		container.add(div);
		container.add(mult);
		container.add(min);
		container.add(plus);
		container.add(equals);	
		container.add(c);
		container.add(zero);
		container.add(sign);
		container.add(com);
		container.add(sqr);
		
		// GridLayout(rows, colums, spacing, spacing)
		container.setLayout(new GridLayout(7,3,3,3));
		container2.setLayout(new GridLayout(2,1));
		

		
		setFocusable(true);
		addKeyListener(this);
		
		add(container2);
		add(container);
		setLayout(new GridLayout (2,1));
		setSize(300,500);
		//pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// After button click make JFrame reclaim focus so keys work.
		this.requestFocusInWindow();
		// Check pre-conditions
		// is a warning displayed?
		if (warning) {
			text = "";
			warning = false;
		}
		// No leading double 0?
		if (text.startsWith("0")&&text.length()==1) {
			text = "";
		}
		// change sign check first
		JButton source = (JButton)arg0.getSource();
		if (source == sign) {
			setSign();
		}

		// Is a result currently displayed? Reset text so a new number can be displayed.
		if (resultDisplayed && source != sign) {
			text = "";
			out.setText(text);
			//resultDisplayed = false;
		}
		// Handlers for the different buttons
		if (source == one) {
			numHandler(1);
		} else if (source == two) {
			numHandler(2);
		} else if (source == three) {
			numHandler(3);
		} else if (source == four) {
			numHandler(4);
		} else if (source == five) {
			numHandler(5);
		} else if (source == six) {
			numHandler(6);
		} else if (source == seven) {
			numHandler(7);
		} else if (source == eight) {
			numHandler(8);
		} else if (source == nine) {
			numHandler(9);
		} else if (source == div) {
			opHandler('/');
		} else if (source == mult) {
			opHandler('*');
		} else if (source == min) {
			opHandler('-');
		} else if (source == plus) {
			opHandler('+');
		} else if (source == equals) {
			equal();
		} else if (source == zero) {
			setZero();
		} else if (source == c) {
			clear();
		} else if (source == com) {
			comHandler();
		} else if (source == sqr) {
			sqrHandler();
		}
	} 
	


	private void calc() {
		text = "";
		System.out.println(operator);
		switch(operator) {
			case '+':
				result = x + y;
				break;
			case '-':
				result = x - y;
				break;
			case '/':
				try {
					result = x / y;
				} catch (ArithmeticException e) {
					text = "Division by Zero undefined";
					out.setText(text);
					warning = true;
				}
				break;
			case '*':
				result = x * y;
				break;
			case '^':
				result = x * x;
				System.out.println(result);
				break;
			default : 
				result = x;
				xFilled = false;
				break;
		}
		System.out.println("result:"+result);
		text += result;
		//text2 = ""+result;
		out.setText(text);
		setX();
		resultDisplayed = true;
		System.out.println("resultDisplayed "+resultDisplayed);
		
	}

	public void setY() {
		try {
			System.out.println("text: "+text);
			String t = out.getText();
			this.y = Float.parseFloat(t);
			System.out.println("y:"+y);
		} catch (NumberFormatException e) {
			System.out.println("y missing or tapped = twice");
		}
		text = "";
		xFilled = false;
	}
	

	public void setX() {
		System.out.println("text:"+text);
		try {
				this.x = Float.parseFloat(text);
				xFilled = true;
				System.out.println("x:"+ x);
				text = "";
		} catch (NumberFormatException e) {
			xFilled = false;
			operator = 'x';
			System.out.println("You only entered an operator or tapped an operator twice");
		}
	}
	
	
	
	public static void main(String[] args) {
		Calculator c = new Calculator();
	}
	
	public void opHandler (char c) {
		if (text.startsWith("+") | text.startsWith("*") | text.startsWith("/")) {
			text2 = text2.substring(0,text2.length()-1)+c;
			out2.setText(text2);
		}
		if (text.startsWith("+") | text.startsWith("*") | text.startsWith("/") | resultDisplayed){
			
			text= ""+c;
			
			text2 += c;
			operator = c;
			out.setText(text);
			
			resultDisplayed = false;
			System.out.println("resultDisplayed "+resultDisplayed);
		} else if (!opClicked) {
				setX();
				operator = c;
				text2 += c;
				text += c;
				out.setText(text);
				out2.setText(text2);
				opClicked = true;
		// one operator clicked? Then x is already loaded so load y
		} else {
			setY();
			//opClicked = false;
			text2 += c;
			out2.setText(text2);
			calc();
			operator = c;
		}
	}
	
	public void numHandler(int n) {
		resultDisplayed = false;
		// dont display double operators
		if (text.startsWith("+") || text.startsWith("*") || text.startsWith("/")) {
			text="";
			out.setText(text);
		}
		if (text.startsWith("-") && xFilled) {
			text="";
			out.setText(text);
		}
		text += n;
		out.setText(text);
		text2 += n;
		out2.setText(text2);
	}
	
	public void equal () {
		//opClicked = false;
		if (!xFilled) {
			// case only one number and = is clicked fill x
			setX();
		} else {
			// case operator has been clicked fill y
			setY();
		}
		// Calculate and set text = result
		calc(); 
		//out.setText(text);
	}
	
	private void setZero() {
		text += 0;
		text2 += 0;
		if (text.startsWith("00")) {
			text = "0";
		}
		out2.setText(text2);
		out.setText(text);
	}
	
	public void clear() {
		xFilled = false;
		operator = 'x';
		text = "0";
		text2 = "";
		out.setText(text);
		out2.setText(text2);
		result = 0;
		xFilled = false;
		opClicked = false;
		resultDisplayed = false;
		System.out.println("result: "+result);
	}
	
	public void setSign() {
		if (text.startsWith("-")) {
			text = "+" + text.substring(1);
		} else if (text.startsWith("+")){
			text = "-" + text.substring(1);
		} else {
			text = "-" + text;
		}
		out.setText(text);
	}
	
	public void comHandler() {
		char [] array = text.toCharArray();
		boolean alreadyHasAComma = false;
		for (int i = 0; i < text.length(); i++) {
			if (array[i] == '.') {
				alreadyHasAComma = true;
			}
		}
		if (!alreadyHasAComma) {
			text += ".";
			text2 += ".";
			out.setText(text);
		}
	}
	
	public void sqrHandler() {
		setX();
		operator='^';
		calc();
		out.setText(text);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// No leading double 0?
		if (text.startsWith("0")&&text.length()==1) {
			text = "";
		}
		// key Handler
		char c = e.getKeyChar();
		keysource = c;
	
		if (keysource == '1') {
			numHandler(1);
		} else if (keysource == '2') {
			numHandler(2);
		} else if (keysource == '3') {
			numHandler(3);
		} else if (keysource == '4') {
			numHandler(4);
		} else if (keysource == '5') {
			numHandler(5);
		} else if (keysource == '6') {
			numHandler(6);
		} else if (keysource == '7') {
			numHandler(7);
		} else if (keysource == '8') {
			numHandler(8);
		} else if (keysource == '9') {
			numHandler(9);
		
		
		} else if (keysource == '/') {
			opHandler('/');
		} else if (keysource == '*') {
			opHandler('*');
		} else if (keysource == '-') {
			opHandler('-');
		} else if (keysource == '+') {
			opHandler('+');
		} else if (keysource == '0') {
			setZero();
		} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			clear();
		} else if (keysource == ',') {
			comHandler();
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			equal();
		} 
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		char c = e.getKeyChar();
		//System.out.println("Key released: "+c);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		//System.out.println("Key typed: "+c);
	}
}