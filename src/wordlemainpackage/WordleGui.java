package wordlemainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class WordleGui extends JFrame implements ActionListener, KeyListener, MouseListener

{
	public
	wordleDictionary Dictionary;
	String correctAnswer[];
	CharBox wordArea[][][];
	JPanel wordleQuestionArea[];
	JPanel gameMenu;
	JButton onePlayerButton;
	JButton twoPlayerButton;
	int positionInWord[];
	int enteredCharactersInWord[];
	int numOfTries[];
	int playerAmount;
	boolean gameStarted;
	boolean gameFinished[];
	int focusedOn;

	public WordleGui(wordleDictionary Dictionary)
	{
		this.Dictionary = Dictionary;

		gameStarted = false;
		focusedOn = 1;
		
		this.setVisible(true);
		this.setSize(550,550);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.getWidth() - this.getSize().getWidth())/2, (int)(screenSize.getHeight() - this.getSize().getHeight())/2);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.setFocusable(true);
        this.requestFocus();
		
		this.setLayout(new GridLayout(1,1));
		this.setBackground(Color.blue);
		getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
		startMenu();
		
	}
	
	public void startMenu() 
	{
		gameMenu = new JPanel();
		
		onePlayerButton = new JButton();
		twoPlayerButton = new JButton();
		
		gameMenu.setLayout(new GridLayout(1,2));

		//gameMenu.setLayout(null);
		
		//onePlayerButton.setBounds(50,50,150,100);
		//twoPlayerButton.setBounds(250,50,150,100);
		
		onePlayerButton.setText("SinglePlayer");
		twoPlayerButton.setText("TwoPlayer");
		
		onePlayerButton.addActionListener(this);
		twoPlayerButton.addActionListener(this);
				
		gameMenu.add(onePlayerButton);
		gameMenu.add(twoPlayerButton);

		this.add(gameMenu);

	}
	
	
	public void startWordle()
	{
		
		{
			
			correctAnswer = new String[playerAmount];
			
			for(int i=0; i<playerAmount; i++) {
				correctAnswer[i] = this.Dictionary.random();
				System.out.println(correctAnswer[i]);
			}
			
			wordleQuestionArea = new JPanel[playerAmount];
			
			for(int i=0; i<playerAmount; i++) {
				wordleQuestionArea[i] = new JPanel();
			}
			
			
			GridLayout layout = new GridLayout(5,5);
			layout.setHgap(5);
			layout.setVgap(5);
			
			this.remove(gameMenu);
			
			for(int i=0; i<playerAmount; i++) {
				this.add(wordleQuestionArea[i]);
				wordleQuestionArea[i].setBackground(Color.green);
				wordleQuestionArea[i].setLayout(layout);
				wordleQuestionArea[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			}
			
			startWordArea();
			
		}
		
	}
	
	public void startWordArea()
	
	{
		
		wordArea = new CharBox[5][5][playerAmount];
		
		for(int row=0;row<5;row++) {
			for(int column=0;column<5;column++) {
				for(int i=0;i<playerAmount;i++) {
					
					wordArea[row][column][i] = new CharBox();
					wordleQuestionArea[i].add(wordArea[row][column][i].charBoxPanel);
					
				}
			}
		}
		wordArea[0][0][0].highlight();
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == onePlayerButton) {
			if(!gameStarted) {
				gameStarted = true;
				
				enteredCharactersInWord = new int[]{0};
				positionInWord = new int[]{0};
				numOfTries = new int[]{0};
				gameFinished = new boolean[]{false};
				
				playerAmount=1;
				focusedOn = 1;
				
				this.setSize(550, 550);
				
				GridLayout layout = new GridLayout(1,1);
				layout.setHgap(10);
				layout.setVgap(10);
				
				this.setLayout(layout);
				this.setBackground(Color.blue);
				
				startWordle();
			}
		}
		if(event.getSource() == twoPlayerButton) {
			if(!gameStarted) {
				gameStarted = true;
				
				enteredCharactersInWord = new int[]{0,0};
				positionInWord = new int[]{0,0};
				numOfTries = new int[]{0,0};
				gameFinished = new boolean[]{false,false};
				
				playerAmount=2;
				focusedOn = 1;
				
				this.setSize(1090, 550);
				
				GridLayout layout = new GridLayout(1,2);
				
				layout.setHgap(10);
				layout.setVgap(10);
				
				this.setLayout(layout);
				
				getContentPane().setBackground(Color.blue);
				
				startWordle();
			}
		}
	}
	
	public void keyPressed(KeyEvent event) {
		if(gameStarted && !gameFinished[focusedOn-1]) {
			
	    	if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
	    		
	    		//System.out.println(wordArea[0][1].charBoxPanel.getHeight());
	    		//System.out.println(wordArea[0][1].charBoxPanel.getWidth());
	    		//System.out.println(wordleQuestionArea.getHeight());
	    		//System.out.println(wordleQuestionArea.getWidth());

	    		if(enteredCharactersInWord[focusedOn-1]>0) {
	    			
	    			wordArea[numOfTries[focusedOn-1]][enteredCharactersInWord[focusedOn-1]-1][focusedOn-1].changeBoxText("");
	    			enteredCharactersInWord[focusedOn-1]--;
	    			
	    			if(enteredCharactersInWord[focusedOn-1]<4) {
	    				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
	    				positionInWord[focusedOn-1]--;
	    				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
	    			}
	    		}
	    		return;
	    	}
	    	if(event.getKeyCode() == KeyEvent.VK_ENTER) {
	    		if(enteredCharactersInWord[focusedOn-1] == 5) {
	    			
	    			String enteredWord = "";
	    			
	    			for(int i=0; i<5; i++) {
	    				enteredWord += wordArea[numOfTries[focusedOn-1]][i][focusedOn-1].getText();
	    			}
	    			if(Dictionary.contains(enteredWord)) {
	    				
	        			for(int i=0; i<5; i++) {
	        				wordArea[numOfTries[focusedOn-1]][i][focusedOn-1].checkCorrectness(i, correctAnswer[focusedOn-1]);
	        			}
	        			
	            		if(numOfTries[focusedOn-1]<4) {

	            			if(enteredWord.equals(correctAnswer[focusedOn-1])) {
		            			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
		            			System.out.print("you won");
		            			gameFinished[focusedOn-1]=true;
		            			
	            			}
	            			else
	            			{
	            				
	            				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
		            			numOfTries[focusedOn-1]++;
		            			positionInWord[focusedOn-1]=0;
		            			enteredCharactersInWord[focusedOn-1]=0;
	                			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
	                			
	            			}

	            		}
	            		else
	            		{
	            			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
	            			System.out.println("you lost");
	            			gameFinished[focusedOn-1]=true;
	            		}
	    				
	    			}

	    		}
	    		return;
	    	}
	    	
	    	if ((event.getKeyChar() > 64 && event.getKeyChar() < 91) || (event.getKeyChar() > 96 && event.getKeyChar() < 123))
	    	{
	            wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].changeBoxText("" + Character.toUpperCase(event.getKeyChar()));
	            
	            if(enteredCharactersInWord[focusedOn-1]<5) {
	    			enteredCharactersInWord[focusedOn-1]++;
	            }
	            
				if(positionInWord[focusedOn-1]<4) {
					positionInWord[focusedOn-1]++;
					wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]-1][focusedOn-1].unHighlight();
	    			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
				}
				return;
	    	}
			
		}

    }
   
	@Override
	public void keyTyped(KeyEvent e) {
		/**/
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		/**/		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int frameWidth = this.getWidth();
		@SuppressWarnings("unused")
		int frameHeight = this.getHeight();

		int clickedX = e.getX();
		@SuppressWarnings("unused")
		int clickedY = e.getY();
		if(gameStarted) {
			if (clickedX<=frameWidth/playerAmount){
				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
				focusedOn = 1;
				if(!gameFinished[focusedOn-1]) {
					wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
				}
			}
			else
			{
				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
				focusedOn = 2;
				if(!gameFinished[focusedOn-1]) {
					wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
				}
			}
		}
		
        Graphics g=getGraphics();  
        g.setColor(Color.yellow);  
        g.fillOval(e.getX()-5,e.getY()-5,10,10);
        
        //System.out.print(e.getX()+" "+e.getY());
        
	}

	@Override
	public void mousePressed(MouseEvent e) {
		/**/
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		/**/
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		/**/
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		/**/
		
	}

	
}
