package wordlemainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class WordleGui {
	public
	
	String correctAnswer;
	CharBox wordArea[][];
	


	public WordleGui() {
		
		//get random answer from file
		correctAnswer = "abcde";
		
		wordArea = new CharBox[5][5];
		
	}
	
	public void startGui() {		

		JFrame wordleBox = new JFrame("Wordle");
		wordleBox.setLayout(null);
		wordleBox.setBackground(Color.lightGray);
		wordleBox.setSize(500, 500);
		wordleBox.pack();
		wordleBox.setVisible(true);
		
	}
	
	public void changeGui() {
		
	}
	
}
