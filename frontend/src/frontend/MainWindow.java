package frontend;

import javax.swing.*;

import api.DataService;

import java.awt.*;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField number1;
	private JTextField number2;
	private JTextField output;
	
	private DataService dataService;
	
	public MainWindow(DataService dataService) {
		this.dataService = dataService;
		
		setTitle("Calculator"); // TODO:tr ceviri
		setSize(400,250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// main panel
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel mainLabel = new JLabel(dataService.getData(), SwingConstants.CENTER);
		mainPanel.add(mainLabel, BorderLayout.NORTH);
		
		// input/output fields
		JPanel ioPanel = new JPanel(new GridLayout(3,2,10,10));
		
		JLabel label1 = new JLabel("Birinci sayi: ");
		number1 = new JTextField(50);
		
		JLabel label2 = new JLabel("İkinci sayi: ");
		number2 = new JTextField(50);
		
		JLabel label3 = new JLabel("Sonuc sayi: ");
		output = new JTextField(50);
		output.setEditable(false);
		output.setBackground(Color.LIGHT_GRAY);
		
		ioPanel.add(label1);
		ioPanel.add(number1);
		ioPanel.add(label2);
		ioPanel.add(number2);
		ioPanel.add(label3);
		ioPanel.add(output);
		
		mainPanel.add(ioPanel, BorderLayout.CENTER);
		
		// button panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		JButton addButton = new JButton("ADD");
		JButton subtractButton = new JButton("SUBTRACT");
		JButton multiplyButton = new JButton("MULTIPLY");
		JButton divideButton = new JButton("DIVIDE");
		
		// button listeners
		addButton.addActionListener(e -> calculation('+'));
		subtractButton.addActionListener(e -> calculation('-'));
		multiplyButton.addActionListener(e -> calculation('*'));
		divideButton.addActionListener(e -> calculation('/'));
		
		buttonPanel.add(addButton);
		buttonPanel.add(subtractButton);
		buttonPanel.add(multiplyButton);
		buttonPanel.add(divideButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
	
	}
	
	private void calculation(char operation) {
		String text1 = getInput1();
		String text2 = getInput2();
		
		int num1 = dataService.convertStringToInteger(text1);
		int num2 = dataService.convertStringToInteger(text2);
		
		double result = 0;
		
		switch (operation) {
		case '+':
			result = num1 + num2;
			break;
		case '-':
			result = num1 - num2;
			break;
		case '*':
			result = num1 * num2;
			break;
		case '/':
			if (num2 == 0) {
				output.setText("Cannot divide by zero");
				return;
			}
			result = num1 / num2;
			break;
		}
		
		output.setText(String.valueOf(result));
	}
	
	public String getInput1() {
		return number1.getText();
	}
	
	public String getInput2() {
		return number2.getText();
	}
	
	public String getInput3() {
		return output.getText();
	}

}
