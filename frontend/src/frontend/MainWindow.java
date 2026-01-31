package frontend;

import javax.swing.*;

import api.DataService;
import api.NumberLanguage;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField number1;
	private JTextField number2;
	private JTextField output;
	
	private JComboBox<String> languageSelector;
	
	private JLabel langLabel;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	
	private JButton addButton;
	private JButton subtractButton;
	private JButton multiplyButton;
	private JButton divideButton;
	
	
	private DataService dataService;
	
	private ResourceBundle messages;
	private Locale currentLocale;
	
	public MainWindow(DataService dataService) {
		this.dataService = dataService;
		
		// detect system language
		currentLocale = Locale.getDefault();
		if (!currentLocale.getLanguage().equals("en")) {
			currentLocale = new Locale("tr", "TR");
		}
		
		// load resource bundle
		messages = ResourceBundle.getBundle("frontend.messages", currentLocale);
		
		setTitle(messages.getString("window.title"));
		setSize(400,250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// main panel
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// Top panel with title and language
		JPanel topPanel = new JPanel(new BorderLayout());
		JLabel topLabel = new JLabel(messages.getString("service.message"), SwingConstants.CENTER);
		topPanel.add(topLabel, BorderLayout.NORTH);
		
		// Language selection
		JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		langLabel = new JLabel(messages.getString("language.label"));
		
		String[] languages = {"Türkçe", "English"};
		languageSelector = new JComboBox<>(languages);
		
		if (currentLocale.getLanguage().equals("en")) {
			languageSelector.setSelectedItem("English");
		} else {
			languageSelector.setSelectedItem("Türkçe");
		}
		
		languageSelector.addActionListener(e -> changeLanguage());
		languagePanel.add(langLabel);
		languagePanel.add(languageSelector);
		topPanel.add(languagePanel, BorderLayout.SOUTH);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		
		// input/output fields
		JPanel ioPanel = new JPanel(new GridLayout(3,2,10,10));
		
		label1 = new JLabel(messages.getString("first.number"));
		number1 = new JTextField(20);
		
		label2 = new JLabel(messages.getString("second.number"));
		number2 = new JTextField(20);
		
		label3 = new JLabel(messages.getString("result"));
		output = new JTextField(20);
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
		
		addButton = new JButton(messages.getString("button.add"));
		subtractButton = new JButton(messages.getString("button.subtract"));
		multiplyButton = new JButton(messages.getString("button.multiply"));
		divideButton = new JButton(messages.getString("button.divide"));
		
		
		// button listeners
		addButton.addActionListener(e -> calculation('+', languageSelector));
		subtractButton.addActionListener(e -> calculation('-', languageSelector));
		multiplyButton.addActionListener(e -> calculation('*', languageSelector));
		divideButton.addActionListener(e -> calculation('/', languageSelector));
		
		buttonPanel.add(addButton);
		buttonPanel.add(subtractButton);
		buttonPanel.add(multiplyButton);
		buttonPanel.add(divideButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
	
	}
	
	private void changeLanguage() {
		String selectedLang = (String) languageSelector.getSelectedItem();
		
		if (selectedLang.equals("English")) {
			currentLocale = new Locale("en", "US");
		} else {
			currentLocale = new Locale("tr", "TR");
		}
		
		messages = ResourceBundle.getBundle("frontend.messages", currentLocale);
		
		setTitle(messages.getString("window.title"));
		label1.setText(messages.getString("first.number"));
		label2.setText(messages.getString("second.number"));
		label3.setText(messages.getString("result"));
		langLabel.setText(messages.getString("language.label"));
		addButton.setText(messages.getString("button.add"));
		subtractButton.setText(messages.getString("button.subtract"));
		multiplyButton.setText(messages.getString("button.multiply"));
		divideButton.setText(messages.getString("button.divide"));
		
		output.setText("");
		
		revalidate();
		repaint();
	}
	
	private void calculation(char operation, JComboBox<String> languageSelector) {
		String text1 = getInput1();
		String text2 = getInput2();
		
		String selectedLanguage = (String) languageSelector.getSelectedItem();
	
		int num1 = dataService.convertStringToInteger(text1, selectedLanguage);
		int num2 = dataService.convertStringToInteger(text2, selectedLanguage);
		
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
