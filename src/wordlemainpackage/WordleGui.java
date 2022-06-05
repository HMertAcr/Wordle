package wordlemainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class WordleGui extends JFrame implements ActionListener, KeyListener, MouseListener, ComponentListener

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
		playerAmount = 0;
		
		this.setVisible(true);
		this.setSize(new Dimension(550,550));
		
		this.setMinimumSize(new Dimension(350,350));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.getWidth() - this.getSize().getWidth())/2, (int)(screenSize.getHeight() - this.getSize().getHeight())/2);
				
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addComponentListener(this);
        this.setFocusable(true);
        this.requestFocus();
		
		this.setLayout(new GridLayout(1,1));
		this.setBackground(new Color(60,60,60));
		this.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.getContentPane().setBackground(new Color(60,60,60));

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
		
		onePlayerButton.setText("OnePlayer");
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
				wordleQuestionArea[i].setBackground(new Color(18,18,18));
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
	
	public void gameWon(int player){
		System.out.print("Player "+(player+1)+" Won!");
	}
   
	public void gameLost(int player){
		System.out.print("Player "+(player+1)+" Lost!");
	}
	
	public void changeFocus(int newFocus) {
		wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
		focusedOn = newFocus;
		if(!gameFinished[focusedOn-1]) {
			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
		}
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
				
				GridLayout layout = new GridLayout(1,1);
				layout.setHgap(10);
				layout.setVgap(10);
				
				this.setLayout(layout);				
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
				
				this.setSize(new Dimension(this.getWidth()*2-10, this.getHeight()));
				
				this.setMinimumSize(new Dimension((this.getMinimumSize().width*2)-10,this.getMinimumSize().height));
				
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				this.setLocation((int)(screenSize.getWidth() - this.getSize().getWidth())/2, (int)(screenSize.getHeight() - this.getSize().getHeight())/2);
				
				GridLayout layout = new GridLayout(1,2);
				
				layout.setHgap(10);
				layout.setVgap(10);
				
				this.setLayout(layout);
				
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
		            			
		            			gameWon(focusedOn-1);
		            			gameFinished[focusedOn-1]=true;
		            			
		            			if(focusedOn<playerAmount) {
		            				changeFocus((focusedOn + 1));
		            			}
		            			else
		            			{
		            				changeFocus((focusedOn - 1));
		            			}
		            			
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
	            			
	            			gameLost(focusedOn-1);
	            			gameFinished[focusedOn-1]=true;

	            			if(focusedOn<playerAmount) {
	            				changeFocus((focusedOn + 1));
	            			}
	            			else
	            			{
	            				changeFocus((focusedOn - 1));
	            			}
	            			
	            		}
	    				
	    			}

	    		}
	    		return;
	    	}
	    	
	    	if (event.getKeyCode() == KeyEvent.VK_LEFT) {
	    		if((focusedOn)>1){
	    			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
	    			focusedOn--;
	    			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
	    		}
	    	}
	    	
	    	if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
	    		if((focusedOn)<playerAmount){
	    			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
	    			focusedOn++;
	    			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
	    		}
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
	public void mousePressed(MouseEvent e) {
		
		int frameWidth = this.getWidth();
		int frameHeight = this.getHeight();

		int clickedX = e.getX();
		int clickedY = e.getY();
		
		if(gameStarted) {
			if (clickedX<=frameWidth/playerAmount){
				changeFocus(1);
			}
			else
			{
				changeFocus(2);
			}
		}
        //this.repaint();
        Graphics g=getGraphics();  
        g.setColor(Color.yellow);
        g.fillOval(e.getX()-5,e.getY()-5,10,10);
        //System.out.print(e.getX()+" "+e.getY());
        
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		/**/
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
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
	
	@Override
	public void componentResized(ComponentEvent e) {
		if(gameStarted) {
			for(int row=0;row<5;row++) {
				for(int column=0;column<5;column++) {
					for(int i=0; i<playerAmount; i++) {
						wordArea[row][column][i].resizeText();
					}
				}
			}
		}
	}
	
	@Override
	public void componentMoved(ComponentEvent e) {
		/**/
	}

	@Override
	public void componentShown(ComponentEvent e) {
		/**/
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		/**/		
	}

	
}
