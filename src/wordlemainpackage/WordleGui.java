package wordlemainpackage;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class WordleGui

{
	public
	wordleDictionary Dictionary;
	String correctAnswer;
	CharBox wordArea[][];
	JPanel mainWordleBox;
	JPanel wordleQuestionArea;
	JPanel draggableKeyboardBox;
	KeyListener keyListener;
	int positionInWord;
	int enteredCharactersInWord;
	int numOfTries;

	public WordleGui(wordleDictionary Dictionary)
	{
		this.Dictionary = Dictionary;
		correctAnswer = this.Dictionary.random();
		
		System.out.println(correctAnswer);
		
		enteredCharactersInWord = 0;
		positionInWord = 0;
		numOfTries = 0;
		
		startGui(false);
		
	}
	
	public void startWordArea()
	
	{
		
		wordArea = new CharBox[5][5];
		
		for(int row=0;row<5;row++) {
			for(int column=0;column<5;column++) {
				wordArea[row][column] = new CharBox();
				wordleQuestionArea.add(wordArea[row][column].charBoxPanel);
								
				
			}
		}
		wordArea[0][0].highlight();
	}
	
	public void addKeyListener()
	{
		
		keyListener = new KeyListener() {
            public void keyPressed(KeyEvent event) {
            	if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            		if(enteredCharactersInWord>0) {
            			
            			wordArea[numOfTries][enteredCharactersInWord-1].changeBoxText("");
            			enteredCharactersInWord--;
            			
            			if(enteredCharactersInWord<4) {
            				wordArea[numOfTries][positionInWord].unHighlight();
            				positionInWord--;
            				wordArea[numOfTries][positionInWord].highlight();
            			}
            		}
            		return;
            	}
            	if(event.getKeyCode() == KeyEvent.VK_ENTER) {
            		if(enteredCharactersInWord == 5) {
            			
            			String enteredWord = "";
            			
            			for(int i=0; i<5; i++) {
            				enteredWord += wordArea[numOfTries][i].getText();
            			}
            			if(Dictionary.contains(enteredWord)) {
            				
	            			for(int i=0; i<5; i++) {
	            				wordArea[numOfTries][i].checkCorrectness(i, correctAnswer);
	            			}
	            			
		            		if(numOfTries<4) {

		            			if(enteredWord.equals(correctAnswer)) {
			            			wordArea[numOfTries][positionInWord].unHighlight();
			            			System.out.print("you won");
			            			removeKeyListener();
			            			
		            			}else {
		            				
		            				wordArea[numOfTries][positionInWord].unHighlight();
			            			numOfTries++;
			            			positionInWord=0;
			            			enteredCharactersInWord=0;
		                			wordArea[numOfTries][positionInWord].highlight();
		                			
		            			}

		            		}else{
		            			wordArea[numOfTries][positionInWord].unHighlight();
		            			System.out.println("you lost");
		            			removeKeyListener();
		            		}
            				
            			}

            		}
            		return;
            	}
            	
            	if(KeyEvent.getKeyText(event.getKeyCode()).length() == 1)
            	{
		            wordArea[numOfTries][positionInWord].changeBoxText("" + Character.toUpperCase(event.getKeyChar()));
		            
		            if(enteredCharactersInWord<5) {
            			enteredCharactersInWord++;
		            }
		            
        			if(positionInWord<4) {
        				positionInWord++;
        				wordArea[numOfTries][positionInWord-1].unHighlight();
            			wordArea[numOfTries][positionInWord].highlight();
        			}
        			
            	}
            	
            }
            
            public void keyReleased(KeyEvent event) {
            	//**//
            }
            public void keyTyped(KeyEvent event) {
            	//**//
            }
        };
        
        mainWordleBox.setFocusable(true);
        mainWordleBox.requestFocus();
		mainWordleBox.addKeyListener(keyListener);
		
	}
	
	public void removeKeyListener()
	{
		mainWordleBox.setFocusable(false);
		mainWordleBox.removeKeyListener(keyListener);
	}
	
	public void startGui(boolean adddraggableKeyboardBox)
	{
		
		if(adddraggableKeyboardBox)
		{
			
			mainWordleBox = new JPanel();
			wordleQuestionArea = new JPanel();
			draggableKeyboardBox = new JPanel();
			
			mainWordleBox.setLayout(new GridLayout(2,1));
			mainWordleBox.setBackground(Color.yellow);
			
			
		}
		else
		{
			
			mainWordleBox = new JPanel();
			wordleQuestionArea = new JPanel();
			
			mainWordleBox.setLayout(new GridLayout(1,1));
			mainWordleBox.setBackground(Color.blue);
			mainWordleBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			wordleQuestionArea.setBackground(Color.green);
			
			GridLayout layout = new GridLayout(5,5);
			layout.setHgap(5);
			layout.setVgap(5);
			
			wordleQuestionArea.setLayout(layout);
			wordleQuestionArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			startWordArea();

			mainWordleBox.add(wordleQuestionArea);
			mainWordleBox.setVisible(true);
			wordleQuestionArea.setVisible(true);
						
			addKeyListener();
			
		}
		
		
	}
	
	public void changeGui()
	{
		
	}
	
}
