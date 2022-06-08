package wordlemainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class WordleGui extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener, ComponentListener

{
	public
	wordleDictionary Dictionary;
	String correctAnswer[];
	String playerNames[];
	CharBox wordArea[][][];
	JPanel gameMenu;
	JPanel wordleGameArea;
	JPanel wordleQuestionArea[];
	JPanel keyboard;
	KeyboardBox keyboardKeys[];
	JPanel bottomLayer;
	JLabel bottomLayerNamesLabel;
	JLabel bottomLayerTimeLabel;
	JTextField playerOneNameField;
	JTextField playerTwoNameField;
	JRadioButton onePlayerButton;
	JRadioButton twoPlayerButton;
	JRadioButton addKeyboardYESButton;
	JRadioButton addKeyboardNOButton;
	JButton startGameButton;
	JButton openStatsButton;
	Timer timer;
	int positionInWord[];
	int enteredCharactersInWord[];
	int numOfTries[];
	int playerAmount;
	int focusedOn;
	boolean addKeyboard;
	boolean gameStarted;
	boolean gameFinished[];
	boolean pressedOnKeyboardBox;
	boolean draggingKeyboardBox;
	int draggingX;
	int draggingY;
	int draggingXOffset;
	int draggingYOffset;
	KeyboardBox draggedKeyboardBox;
	
	public WordleGui(wordleDictionary Dictionary)
	{
		this.Dictionary = Dictionary;

		gameStarted = false;
		pressedOnKeyboardBox= false;
		draggingKeyboardBox = false;
		draggingX = 0;
		draggingY = 0;
		
		this.setVisible(true);
		
		this.setSize(new Dimension(400,250));
		this.setMinimumSize(new Dimension(425,300));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.getWidth() - this.getSize().getWidth())/2, (int)(screenSize.getHeight() - this.getSize().getHeight())/2);
				
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Wordle");
		
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);
        this.setFocusable(true);
        this.requestFocus();
		
		this.setLayout(new GridLayout(1,1));
		this.setBackground(new Color(60,60,60));
		
		this.getContentPane().setBackground(new Color(60,60,60));

		startMenu();
		
	}

	public void startMenu()
	{
		
		addKeyboard = false;
		playerAmount = 1;
		
		gameMenu = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		gameMenu.setLayout(gbl);
		
		GridBagConstraints gcon = new GridBagConstraints();
		
		this.add(gameMenu);
		
		startGameButton = new JButton();
		openStatsButton = new JButton();
		
		onePlayerButton = new JRadioButton();
		twoPlayerButton = new JRadioButton();
		
		ButtonGroup playerAmountGroup = new ButtonGroup();
		playerAmountGroup.add(onePlayerButton);
		playerAmountGroup.add(twoPlayerButton);
		
		onePlayerButton.setSelected(true);
		
		playerOneNameField = new JTextField();
		playerTwoNameField = new JTextField();
		
		addKeyboardYESButton = new JRadioButton();
		addKeyboardNOButton = new JRadioButton();
		
		ButtonGroup shouldAddKeyboardButton = new ButtonGroup();
		shouldAddKeyboardButton.add(addKeyboardYESButton);
		shouldAddKeyboardButton.add(addKeyboardNOButton);
		
		addKeyboardNOButton.setSelected(true);
		
		onePlayerButton.setText("OnePlayer");
		twoPlayerButton.setText("TwoPlayer");
		
		addKeyboardYESButton.setText("Yes");
		addKeyboardNOButton.setText("No");
		
		startGameButton.setText("Start");
		openStatsButton.setText("Stats");
		
		JLabel enterPlayerNamesLabel = new JLabel("Enter Players");
		JLabel enterIfKeyboardLabel = new JLabel("Do you want to add keyboard?");

		enterPlayerNamesLabel.setText("Enter Players");
		enterIfKeyboardLabel.setText("Do you want to add keyboard?");

		onePlayerButton.addActionListener(this);
		twoPlayerButton.addActionListener(this);
		startGameButton.addActionListener(this);
		addKeyboardYESButton.addActionListener(this);
		addKeyboardNOButton.addActionListener(this);
		
		gcon.weightx=1;
		gcon.weighty=3;
		gcon.gridx=0;
		gcon.gridy=0;
		gcon.gridwidth=4;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,0,0,0);
		
		gameMenu.add(enterPlayerNamesLabel, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=0;
		gcon.gridy=1;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,0,0,0);
		
		gameMenu.add(onePlayerButton, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=0;
		gcon.gridy=2;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(twoPlayerButton, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=1;
		gcon.gridy=1;
		gcon.gridwidth=3;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.LINE_START;
		gcon.fill=GridBagConstraints.HORIZONTAL;
		gcon.insets = new Insets(0,0,0,20);

		gameMenu.add(playerOneNameField, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=1;
		gcon.gridy=2;
		gcon.gridwidth=3;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.LINE_START;
		gcon.fill=GridBagConstraints.HORIZONTAL;
		gcon.insets = new Insets(0,0,0,20);

		gameMenu.add(playerTwoNameField, gcon);
		
		gcon.weightx=1;
		gcon.weighty=3;
		gcon.gridx=0;
		gcon.gridy=3;
		gcon.gridwidth=4;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,10,0,0);
		
		gameMenu.add(enterIfKeyboardLabel, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=0;
		gcon.gridy=4;
		gcon.gridwidth=2;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(addKeyboardNOButton, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=2;
		gcon.gridy=4;
		gcon.gridwidth=2;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(addKeyboardYESButton, gcon);
		
		gcon.weightx=1;
		gcon.weighty=4;
		gcon.gridx=0;
		gcon.gridy=5;
		gcon.gridwidth=2;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(10,10,5,10);

		gameMenu.add(startGameButton, gcon);

		gcon.weightx=1;
		gcon.weighty=4;
		gcon.gridx=2;
		gcon.gridy=5;
		gcon.gridwidth=2;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(10,10,5,10);

		gameMenu.add(openStatsButton, gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=0;
		gcon.gridy=6;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(new JLabel(), gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=1;
		gcon.gridy=6;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(new JLabel(), gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=2;
		gcon.gridy=6;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(new JLabel(), gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=3;
		gcon.gridy=6;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(new JLabel(), gcon);
		
		playerTwoNameField.setEnabled(false);

		gameMenu.revalidate();
		gameMenu.repaint();
	}
	
	
	public void startWordle()
	{
		
		{
			
			focusedOn = 1;
			
			this.remove(gameMenu);
			
			wordleGameArea = new JPanel();
			wordleGameArea.setBackground(new Color(60,60,60));
			
			this.add(wordleGameArea);
			
			wordleGameArea.setLayout(new GridBagLayout());
			GridBagConstraints gcon = new GridBagConstraints();
			
			correctAnswer = new String[playerAmount];
			
			for(int i=0; i<playerAmount; i++) {
				correctAnswer[i] = this.Dictionary.random();
				System.out.println(correctAnswer[i]);
			}
			
			wordleQuestionArea = new JPanel[playerAmount];
			
			GridLayout layout = new GridLayout(5,5);
			layout.setHgap(5);
			layout.setVgap(5);
			
			
			for(int i=0; i<playerAmount; i++) {
				wordleQuestionArea[i] = new JPanel();
				
				wordleQuestionArea[i].setPreferredSize(new Dimension (55, 55));
				wordleQuestionArea[i].setMinimumSize(new Dimension (55, 55));
				wordleQuestionArea[i].setBackground(new Color(20,20,20));
				wordleQuestionArea[i].setLayout(layout);
				wordleQuestionArea[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				
				gcon.fill = GridBagConstraints.BOTH;
				gcon.weightx=1;
				gcon.weighty=11;
				gcon.gridx=i;
				gcon.gridy=0;
				gcon.gridwidth=1;
				gcon.gridheight=1;
				if(playerAmount>1) {
					if(i==0)
					{
						gcon.insets = new Insets(10,10,10,5);
					}
					else
					{
						if(i==1)
						{
							gcon.insets = new Insets(10,5,10,10);
						}
					}
				}
				else
				{
					gcon.insets = new Insets(10,10,10,10);
				}
				wordleGameArea.add(wordleQuestionArea[i], gcon);
			}
			
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
		
		if(addKeyboard) {
			startKeyboard();
		}
		
		startBottomLayer();
		
		this.revalidate();
		this.repaint();
	}
	
	private void startKeyboard() {
		
		GridBagConstraints gcon = new GridBagConstraints();
	
		keyboard = new JPanel();
		
			keyboard = new JPanel();
			
			keyboard.setPreferredSize(new Dimension (55*playerAmount, 20));
			keyboard.setMinimumSize(new Dimension (55*playerAmount, 20));
			keyboard.setBackground(new Color(20,20,20));
			
			gcon.fill = GridBagConstraints.BOTH;
			gcon.weightx=1;
			gcon.weighty=6;
			gcon.gridx=0;
			gcon.gridy=1;
			gcon.gridwidth=playerAmount;
			gcon.gridheight=1;
			wordleGameArea.add(keyboard, gcon);
			
			keyboard.setLayout(new GridBagLayout());
			keyboard.setBorder(BorderFactory.createEmptyBorder(5,10,0,5));
			
			keyboardKeys = new KeyboardBox[28];
			
			for(int i=0; i<26 ;i++)
			{
				keyboardKeys[i] = new KeyboardBox((char)(i+65)+"",playerAmount);
				gcon.fill = GridBagConstraints.BOTH;
				gcon.weightx=1;
				gcon.weighty=1;
				gcon.gridx=i%10;
				gcon.gridy=i/10;
				gcon.gridwidth=1;
				gcon.gridheight=1;
				gcon.insets = new Insets(0,0,5,5);
				keyboard.add(keyboardKeys[i].KeyboardBoxPanel, gcon);
			}
			
			keyboardKeys[26] = new KeyboardBox("DELETE", playerAmount);
			gcon.fill = GridBagConstraints.BOTH;
			gcon.weightx=1;
			gcon.weighty=1;
			gcon.gridx=6;
			gcon.gridy=2;
			gcon.gridwidth=2;
			gcon.gridheight=1;
			gcon.insets = new Insets(0,0,5,5);
			keyboard.add(keyboardKeys[26].KeyboardBoxPanel, gcon);
			
			keyboardKeys[27] = new KeyboardBox("ENTER", playerAmount);
			gcon.fill = GridBagConstraints.BOTH;
			gcon.weightx=1;
			gcon.weighty=1;
			gcon.gridx=8;
			gcon.gridy=2;
			gcon.gridwidth=2;
			gcon.gridheight=1;
			gcon.insets = new Insets(0,0,5,5);
			keyboard.add(keyboardKeys[27].KeyboardBoxPanel, gcon);
		
	}

	private void startBottomLayer() {
		
		bottomLayer = new JPanel();
		
		bottomLayer.setPreferredSize(new Dimension ((530*playerAmount), 15));
		bottomLayer.setMinimumSize(new Dimension ((330*playerAmount), 15));
		
		bottomLayer.setBackground(new Color(0,0,0));

		GridBagConstraints gcon = new GridBagConstraints();
		
		gcon.fill = GridBagConstraints.BOTH;
		gcon.weightx=0;
		gcon.weighty=0;
		gcon.gridx=0;
		if(addKeyboard) {
			gcon.gridy=2;
		}
		else
		{
			gcon.gridy=1;
		}
		gcon.gridwidth=2;
		gcon.gridheight=1;
		
		bottomLayer.setLayout(new GridLayout(1,3));
		bottomLayer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		
		bottomLayerTimeLabel = new JLabel("Time: 0.00");
		bottomLayerTimeLabel.setHorizontalAlignment(JLabel.RIGHT);
		bottomLayerTimeLabel.setVerticalAlignment(JLabel.CENTER);
		bottomLayerTimeLabel.setForeground(Color.white);
		
		bottomLayerNamesLabel = new JLabel();
		bottomLayerNamesLabel.setHorizontalAlignment(JLabel.CENTER);
		bottomLayerNamesLabel.setVerticalAlignment(JLabel.CENTER);
		bottomLayerNamesLabel.setForeground(Color.white);
		
		if(playerAmount==1) {
			bottomLayerNamesLabel.setText(playerNames[0]);
		}
		else
		{
			bottomLayerNamesLabel.setText(playerNames[0]+" vs "+playerNames[1]);

		}
		
		bottomLayer.add(new JLabel(""));
		bottomLayer.add(bottomLayerNamesLabel);
		bottomLayer.add(bottomLayerTimeLabel);

		
		wordleGameArea.add(bottomLayer, gcon);
		
		timer = new Timer(1000, this);
		timer.start(); 

		
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
			if(addKeyboard) {
				for(int i=0;i<28;i++) {
					keyboardKeys[i].changeKeyboard(newFocus);
				}
			}
		}
	}

	public void pressBackspace() {
		
		if(enteredCharactersInWord[focusedOn-1]>0) {
			
			wordArea[numOfTries[focusedOn-1]][enteredCharactersInWord[focusedOn-1]-1][focusedOn-1].changeBoxText("");
			enteredCharactersInWord[focusedOn-1]--;
			
			if(enteredCharactersInWord[focusedOn-1]<4) {
				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
				positionInWord[focusedOn-1]--;
				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
			}
		}
		
	}
	
	public void pressEnter() {
		if(enteredCharactersInWord[focusedOn-1] == 5) {
			
			String enteredWord = "";
			
			for(int i=0; i<5; i++) {
				enteredWord += wordArea[numOfTries[focusedOn-1]][i][focusedOn-1].getText();
			}
			if(Dictionary.contains(enteredWord)) {
				
    			for(int i=0; i<5; i++) {
    				wordArea[numOfTries[focusedOn-1]][i][focusedOn-1].checkCorrectness(i, correctAnswer[focusedOn-1]);
        			if(addKeyboard) {
            				keyboardKeys[(wordArea[numOfTries[focusedOn-1]][i][focusedOn-1].getText().charAt(0))-65].changeColor(wordArea[numOfTries[focusedOn-1]][i][focusedOn-1].charBoxPanel.getBackground(),focusedOn);
        			}
    				
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
            				if(focusedOn>1) {
	            				changeFocus((focusedOn - 1));
            				}
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
        				if(focusedOn>1) {
        				changeFocus((focusedOn - 1));
        				}
        			}
        			
        		}
				
			}

		}
	}
	
	public void pressLeft() {
		
	}
	
	public void pressRight() {
		
	}
	
	public void pressChar(String pressedKey) {
		
        wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].changeBoxText(pressedKey.toUpperCase());
        
        if(enteredCharactersInWord[focusedOn-1]<5) {
			enteredCharactersInWord[focusedOn-1]++;
        }
        
		if(positionInWord[focusedOn-1]<4) {
			positionInWord[focusedOn-1]++;
			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]-1][focusedOn-1].unHighlight();
			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
		}
		
	}
	
	public void paintKeyboardBoxManually(KeyboardBox buttonToDraw, int x, int y, Graphics g){
		
		Graphics2D g2 = (Graphics2D)g;
		
		int buttonWidth = buttonToDraw.KeyboardBoxPanel.getWidth();
		int buttonHeight = buttonToDraw.KeyboardBoxPanel.getHeight();
		Color buttonColor = buttonToDraw.KeyboardBoxPanel.getBackground();
		
		Color labelColor = buttonToDraw.charLabel.getForeground();
		String labelText = buttonToDraw.charLabel.getText();
		Font labelFont = buttonToDraw.charLabel.getFont();
		FontMetrics metrics = buttonToDraw.charLabel.getFontMetrics(labelFont);
		int labelWidth = metrics.stringWidth(labelText);
		int labelHeight = metrics.getHeight();
		int labelAscent = metrics.getAscent();

				
		g2.setColor(buttonColor);
		g2.fillRect(x, y, buttonWidth, buttonHeight);
		
		g2.setColor(labelColor);
		g2.setFont(labelFont);
		
		g2.drawString(buttonToDraw.charLabel.getText(), x + (buttonWidth - labelWidth)/2, y + (buttonHeight - labelHeight)/2 + labelAscent);

		

	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paintComponents(g);
		if(draggingKeyboardBox)
		{
			paintKeyboardBoxManually(draggedKeyboardBox, draggingX-draggingXOffset, draggingY-draggingYOffset, g);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		
		if(event.getSource() == startGameButton) {
			if((!(playerOneNameField.getText().equals("")||playerOneNameField.getText().equals("Please Enter A Name")
					||playerOneNameField.getText().equals("Name Too Long")||playerOneNameField.getText().length()>20))
						&&((playerAmount!=2||(!(playerTwoNameField.getText().equals("")||playerTwoNameField.getText().equals("Please Enter A Name")
								||playerTwoNameField.getText().equals("Name Too Long")||playerTwoNameField.getText().length()>20)))))
			{
				if(!gameStarted) {
					
					this.setSize(new Dimension(550,550));
					this.setMinimumSize(new Dimension(350,350));
					
					gameStarted = true;
					
					if(playerAmount==1) {
						
						playerNames = new String[] {playerOneNameField.getText()};
						enteredCharactersInWord = new int[]{0};
						positionInWord = new int[]{0};
						numOfTries = new int[]{0};
						gameFinished = new boolean[]{false};
						
						if(addKeyboard)
						{
							this.setSize(new Dimension(this.getWidth(), (16*(this.getHeight()/11))+15));
							this.setMinimumSize(new Dimension(this.getMinimumSize().width, (16*(this.getMinimumSize().height/11))+15));
						}
						else
						{
							this.setSize(new Dimension(this.getWidth(), this.getHeight()));
							this.setMinimumSize(new Dimension(this.getMinimumSize().width, this.getMinimumSize().height));
						}
					}
					else
					{
						playerNames = new String[] {playerOneNameField.getText(), playerTwoNameField.getText()};
						enteredCharactersInWord = new int[]{0,0};
						positionInWord = new int[]{0,0};
						numOfTries = new int[]{0,0};
						gameFinished = new boolean[]{false,false};
						
						if(addKeyboard)
						{
							this.setSize(new Dimension((this.getWidth()-15)*2, (16*(this.getHeight()/11)+15)));
							this.setMinimumSize(new Dimension((this.getMinimumSize().width-15)*2, (16*(this.getMinimumSize().height/11))+15));
						}
						else
						{
							this.setSize(new Dimension((this.getWidth()-15)*2, this.getHeight()));
							this.setMinimumSize(new Dimension(((this.getMinimumSize().width-15)*2),this.getMinimumSize().height));
						}
					}
					
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					this.setLocation((int)(screenSize.getWidth() - this.getSize().getWidth())/2, (int)(screenSize.getHeight() - this.getSize().getHeight())/2);
					
					startWordle();
					
				}
			}
			else
			{
				if((playerOneNameField.getText().equals("")||playerOneNameField.getText().equals("Please Enter A Name")||playerOneNameField.getText().equals("Name Too Long"))){
					playerOneNameField.setText("Please Enter A Name");
				}
				else
				{
					if((playerOneNameField.getText().length()>20)){
						playerOneNameField.setText("Name Too Long");
					}
				}
				
				if(playerTwoNameField.isEnabled()) {
					if((playerTwoNameField.getText().equals("")||playerTwoNameField.getText().equals("Please Enter A Name")||playerTwoNameField.getText().equals("Name Too Long"))){
						playerTwoNameField.setText("Please Enter A Name");
					}
					else
					{
						if((playerTwoNameField.getText().length()>20)){
							playerTwoNameField.setText("Name Too Long");
						}
					}
				}
			}
		}
		
		if(event.getSource() == onePlayerButton) {
			if(!gameStarted) {
				playerAmount=1;
				playerTwoNameField.setText("");
				playerTwoNameField.setEnabled(false);
			}
		}
		
		if(event.getSource() == twoPlayerButton) {
			if(!gameStarted) {
				playerAmount=2;
				playerTwoNameField.setEnabled(true);
			}
		}
		
		if(event.getSource() == addKeyboardYESButton) {
			if(!gameStarted) {
				addKeyboard=true;
			}
		}
		
		if(event.getSource() == addKeyboardNOButton) {
			if(!gameStarted) {
				addKeyboard=false;				
			}
		}
		
		if(event.getSource() == timer) {
			if(gameStarted) {
				
				String TimeString = bottomLayerTimeLabel.getText();
				
				if(!TimeString.equals("Time: Too Long")) {
				
					int minute = Integer.parseInt(TimeString.substring(6,7));
					int tenSecond = Integer.parseInt(TimeString.substring(8,9));
					int oneSecond = Integer.parseInt(TimeString.substring(9,10));
					
					if(oneSecond==9)
					{
						oneSecond=0;
						if(tenSecond==5)
						{
							tenSecond=0;
							if(minute==9)
							{
								bottomLayerTimeLabel.setText("Time: Too Long");
								return;
							}
							else
							{
								minute++;
							}
						}
						else
						{
							tenSecond++;
						}
					}
					else
					{
						oneSecond++;
					}

					bottomLayerTimeLabel.setText("Time: "+minute+"."+tenSecond+oneSecond);
					
				}				
			}
		}		
	}
	
	public void keyPressed(KeyEvent event) {
		if(gameStarted && !gameFinished[focusedOn-1]) {
			
	    	if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

	    		pressBackspace();
	    		
	    		return;
	    	}
	    	
	    	if(event.getKeyCode() == KeyEvent.VK_ENTER) {
	    		
	    		pressEnter();
	    		
	    		return;
	    	}
	    	
	    	if (event.getKeyCode() == KeyEvent.VK_LEFT) {
	    		if((focusedOn)>1){
	    			if(!gameFinished[focusedOn-2]) {
		    			changeFocus(focusedOn-1);
	    			}
	    		}
	    	}
	    	
	    	if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
	    		if((focusedOn)<playerAmount){
	    			if(!gameFinished[focusedOn]) {
	    			changeFocus(focusedOn+1);
	    			}
	    		}
	    	}
	    	
	    	if ((event.getKeyChar() > 64 && event.getKeyChar() < 91) || (event.getKeyChar() > 96 && event.getKeyChar() < 123))
	    	{
	    		pressChar(event.getKeyChar()+"");
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

		int clickedX = e.getX()-7;
		int clickedY = e.getY()-32;
		
		//this is windows specific and maybe my computer specific
		
		if(gameStarted) {
			if(playerAmount>1) {
				if(clickedY>wordleQuestionArea[0].getY()&&clickedY<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getHeight()) {
					if (clickedX>wordleQuestionArea[0].getX() && clickedX<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getWidth()){
						if(!gameFinished[0]) {
							changeFocus(1);
						}
					}
					if (clickedX>wordleQuestionArea[1].getX() && clickedX<wordleQuestionArea[1].getX()+wordleQuestionArea[1].getWidth()){
						if(!gameFinished[1]) {
							changeFocus(2);
						}
					}
				}
			}


			
			if(addKeyboard) {
				int keyboardX = keyboard.getX();
				int keyboardY = keyboard.getY();
				int keyboardWidth = keyboard.getWidth();
				int keyboardHeight = keyboard.getHeight();
				
				if(clickedX>keyboardX&&clickedX<(keyboardX+keyboardWidth)&&clickedY>keyboardY&&clickedY<(keyboardY+keyboardHeight)) {
					for(int i=0; i<28; i++) {
						int currentKeyX = keyboardKeys[i].KeyboardBoxPanel.getX();
						int currentKeyY = keyboardKeys[i].KeyboardBoxPanel.getY();
						int currentKeyWidth = keyboardKeys[i].KeyboardBoxPanel.getWidth();
						int currentKeyHeight = keyboardKeys[i].KeyboardBoxPanel.getHeight();
						
						if(clickedX>(keyboardX+currentKeyX)&&clickedX<(keyboardX+currentKeyX+currentKeyWidth)&&clickedY>(keyboardY+currentKeyY)&&clickedY<(keyboardY+currentKeyY+currentKeyHeight)) {
							pressedOnKeyboardBox = true;
							draggedKeyboardBox = keyboardKeys[i];
							draggingXOffset = clickedX-(keyboardX+currentKeyX);
							draggingYOffset = clickedY-(keyboardY+currentKeyY);
							break;
						}
					}
				}
			}
		}		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
		@SuppressWarnings("unused")
		int frameWidth = this.getWidth();

		int clickedX = e.getX()-7;
		int clickedY = e.getY()-32;
		
		//this is windows specific and maybe my computer specific
		
		if(draggingKeyboardBox) {
			if(gameStarted) {
				if(playerAmount>1) {
					if(clickedY>wordleQuestionArea[0].getY()&&clickedY<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getHeight()) {
						if (clickedX>wordleQuestionArea[0].getX() && clickedX<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getWidth()){
							if(!gameFinished[0]) {
								changeFocus(1);
								String draggedKeyText = draggedKeyboardBox.getText();
								
								if(draggedKeyText.equals("ENTER")) {
									pressEnter();
								}
								else
								{
									if(draggedKeyText.equals("DELETE")) {
										pressBackspace();
									}
									else
									{
										pressChar(draggedKeyText);
									}
								}
							}
						}
						if (clickedX>wordleQuestionArea[1].getX() && clickedX<wordleQuestionArea[1].getX()+wordleQuestionArea[1].getWidth()){
							if(!gameFinished[1]) {
								changeFocus(2);
								String draggedKeyText = draggedKeyboardBox.getText();
								
								if(draggedKeyText.equals("ENTER")) {
									pressEnter();
								}
								else
								{
									if(draggedKeyText.equals("DELETE")) {
										pressBackspace();
									}
									else
									{
										pressChar(draggedKeyText);
									}
								}
							}
						}
					}
				}
				else
				{
					if(!gameFinished[0]) {
						String draggedKeyText = draggedKeyboardBox.getText();
						
						if(draggedKeyText.equals("ENTER")) {
							pressEnter();
						}
						else
						{
							if(draggedKeyText.equals("DELETE")) {
								pressBackspace();
							}
							else
							{
								pressChar(draggedKeyText);
							}
						}
					}
				}
			}			
		}

		
		draggingKeyboardBox = false;
		pressedOnKeyboardBox = false;
		repaint();
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
	public void mouseDragged(MouseEvent e) {
		if(pressedOnKeyboardBox) {
			
			draggingKeyboardBox = true;
			draggingX = e.getX();
			draggingY = e.getY();
			repaint();
			
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
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
			if(addKeyboard) {
				for(int key=0;key<28;key++) {
					keyboardKeys[key].resizeText();
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
