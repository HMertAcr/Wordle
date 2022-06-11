package wordlemainpackage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class WordleGui extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener, ComponentListener
{
	
	public
	String dictionaryFileLocation;
	String wordleScoreFileLocation;
	WordleDictionary dictionary;
	WordleScore score;
	String correctAnswer[];
	String playerNames[];
	CharBox wordArea[][][];
	JPanel gameMenu;
	JPanel statsMenu;
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
	ResizableButton backButton;
	ResizableTextPanel[] highScoreGrids;
	ResizableTextPanel[] MostGuessedCharGrids;
	ResizableTextPanel[] MostAskedCharGrids;
	ResizableTextPanel highscoreTitle;
	ResizableTextPanel mostGuessedTitle;
	ResizableTextPanel mostAskedTitle;
	ResizableTextPanel createdBy;
	Timer timer;
	int secondsPassed;
	int positionInWord[];
	int enteredCharactersInWord[];
	int numOfTries[];
	int playerAmount;
	int focusedOn;
	int finalScore[];
	boolean addKeyboard;
	boolean gameStarted;
	boolean showingStatsMenu;
	boolean gameFinished[];
	boolean gameWon[];
	boolean pressedOnQuestionArea[];
	boolean pressedOnKeyboardBox;
	boolean draggingKeyboardBox;
	int draggingX;
	int draggingY;
	int draggingXOffset;
	int draggingYOffset;
	KeyboardBox draggedKeyboardBox;
	Font messageFont;
	int messageCordinates[][];
	Font scoreFont;
	int scoreCordinates[][];
	ScoreEntry highscores[];
	CharAmount mostGuessedCharacters[];
	CharAmount mostAskedCharacters[];
	
	public WordleGui(String dictionaryFileLocation, String wordleScoreFileLocation)
	{
		this.dictionaryFileLocation = dictionaryFileLocation;
		this.wordleScoreFileLocation = wordleScoreFileLocation;
		
		dictionary = new WordleDictionary(this.dictionaryFileLocation);
		score = new WordleScore(wordleScoreFileLocation);
		
		gameStarted = false;
		showingStatsMenu = false;
		pressedOnKeyboardBox= false;
		draggingKeyboardBox = false;
		draggingX = 0;
		draggingY = 0;
		
		this.setVisible(true);
		
		this.setMinimumSize(new Dimension(450,325));
		this.setSize(new Dimension(500,350));
		
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
		
		gameMenu.setBackground(new Color(230,230,230));
		
		gameMenu.setLayout(new GridBagLayout());
		
		GridBagConstraints gcon = new GridBagConstraints();
		
		this.add(gameMenu);
		
		startGameButton = new JButton();
		openStatsButton = new JButton();
		
		startGameButton.setText("Start");
		openStatsButton.setText("Stats");
		
		startGameButton.setBackground(new Color(60,60,60));
		openStatsButton.setBackground(new Color(60,60,60));
		
		startGameButton.setForeground(Color.white);
		openStatsButton.setForeground(Color.white);
		
		onePlayerButton = new JRadioButton();
		twoPlayerButton = new JRadioButton();
		
		ButtonGroup playerAmountGroup = new ButtonGroup();
		playerAmountGroup.add(onePlayerButton);
		playerAmountGroup.add(twoPlayerButton);
		
		onePlayerButton.setSelected(true);
		
		onePlayerButton.setText("OnePlayer");
		twoPlayerButton.setText("TwoPlayer");
		
		onePlayerButton.setBackground(new Color(230,230,230));
		twoPlayerButton.setBackground(new Color(230,230,230));
		
		onePlayerButton.setForeground(Color.black);
		twoPlayerButton.setForeground(Color.black);
				
		playerOneNameField = new JTextField();
		playerTwoNameField = new JTextField();
		
		playerOneNameField.setBackground(Color.white);
		playerTwoNameField.setBackground(Color.white);
		
		playerTwoNameField.setForeground(Color.black);
		playerOneNameField.setForeground(Color.black);
		
		addKeyboardYESButton = new JRadioButton();
		addKeyboardNOButton = new JRadioButton();
		
		ButtonGroup shouldAddKeyboardButton = new ButtonGroup();
		shouldAddKeyboardButton.add(addKeyboardYESButton);
		shouldAddKeyboardButton.add(addKeyboardNOButton);
		
		addKeyboardNOButton.setSelected(true);
		
		addKeyboardYESButton.setText("Yes");
		addKeyboardNOButton.setText("No");
		
		addKeyboardYESButton.setBackground(new Color(230,230,230));
		addKeyboardNOButton.setBackground(new Color(230,230,230));
		
		addKeyboardYESButton.setForeground(Color.black);
		addKeyboardNOButton.setForeground(Color.black);
		
		JLabel enterPlayerNamesLabel = new JLabel("Enter Players");
		JLabel enterIfKeyboardLabel = new JLabel("Do you want to add keyboard?");
		
		enterPlayerNamesLabel.setBackground(new Color(230,230,230));
		enterIfKeyboardLabel.setBackground(new Color(230,230,230));
		
		enterPlayerNamesLabel.setForeground(Color.black);
		enterIfKeyboardLabel.setForeground(Color.black);
		
		onePlayerButton.addActionListener(this);
		twoPlayerButton.addActionListener(this);
		startGameButton.addActionListener(this);
		openStatsButton.addActionListener(this);
		addKeyboardYESButton.addActionListener(this);
		addKeyboardNOButton.addActionListener(this);
		
		
		gcon.weightx=1;
		gcon.weighty=3;
		gcon.gridx=0;
		gcon.gridy=0;
		gcon.gridwidth=9;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,0,0,0);
		
		gameMenu.add(enterPlayerNamesLabel, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=0;
		gcon.gridy=1;
		gcon.gridwidth=3;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,0,0,0);
		
		gameMenu.add(onePlayerButton, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=0;
		gcon.gridy=2;
		gcon.gridwidth=3;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(twoPlayerButton, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=3;
		gcon.gridy=1;
		gcon.gridwidth=5;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.LINE_START;
		gcon.fill=GridBagConstraints.HORIZONTAL;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(playerOneNameField, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=3;
		gcon.gridy=2;
		gcon.gridwidth=5;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.LINE_START;
		gcon.fill=GridBagConstraints.HORIZONTAL;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(playerTwoNameField, gcon);
		
		gcon.weightx=1;
		gcon.weighty=3;
		gcon.gridx=0;
		gcon.gridy=3;
		gcon.gridwidth=9;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,10,0,0);
		
		gameMenu.add(enterIfKeyboardLabel, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=1;
		gcon.gridy=4;
		gcon.gridwidth=3;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(addKeyboardNOButton, gcon);
		
		gcon.weightx=1;
		gcon.weighty=2;
		gcon.gridx=5;
		gcon.gridy=4;
		gcon.gridwidth=3;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.NONE;
		gcon.insets = new Insets(0,0,0,0);

		gameMenu.add(addKeyboardYESButton, gcon);
		
		gcon.weightx=1;
		gcon.weighty=4;
		gcon.gridx=0;
		gcon.gridy=5;
		gcon.gridwidth=3;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,15,0,10);

		gameMenu.add(openStatsButton, gcon);

		gcon.weightx=1;
		gcon.weighty=4;
		gcon.gridx=3;
		gcon.gridy=5;
		gcon.gridwidth=6;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,5,0,15);

		gameMenu.add(startGameButton, gcon);
		
		for(int i=0;i<9;i++)
		{
			gcon.weightx=1;
			gcon.weighty=0;
			gcon.gridx=i;
			gcon.gridy=6;
			gcon.gridwidth=1;
			gcon.gridheight=1;
			gcon.anchor=GridBagConstraints.CENTER;
			gcon.fill=GridBagConstraints.BOTH;
			gcon.insets = new Insets(0,0,0,0);
			
			JLabel dummyJlabel = new JLabel();
			
			dummyJlabel.setMinimumSize(new Dimension(100, 10));
			dummyJlabel.setPreferredSize(new Dimension(100, 10));
			gameMenu.add(dummyJlabel, gcon);
		}
		
		playerTwoNameField.setEnabled(false);

		gameMenu.revalidate();
		gameMenu.repaint();
		
	}
	
	private void startStats()
	{
		
		statsMenu = new JPanel();
		
		statsMenu.setBackground(new Color(60,60,60));

		statsMenu.setLayout(new GridBagLayout());
		
		GridBagConstraints gcon = new GridBagConstraints();
		
		this.remove(gameMenu);
		this.add(statsMenu);
		
		backButton = new ResizableButton();
		backButton.setText("Back");
		backButton.addActionListener(this);
		backButton.setPrefferedTextSize(8);
		
		backButton.setBackground(new Color(20,20,20));
		backButton.setForeground(new Color(255,255,255));
		backButton.setPressedBackgroundColor(new Color(220,30,30));
		backButton.setHoverBackgroundColor(new Color(40,40,40));
		backButton.setFocusable(false);
		backButton.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(255,255,255)));
		
		JPanel highscorePanel = new JPanel(new GridLayout(11,3,2,2));
		
		highscorePanel.setBackground(new Color(255,255,255));
		highscorePanel.setBorder(BorderFactory.createMatteBorder(1,2,1,2,new Color(255,255,255)));
		
		highScoreGrids = new ResizableTextPanel[33];
		
		highScoreGrids[0] = new ResizableTextPanel("Name:",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),4,JLabel.CENTER,JLabel.CENTER,0,0);
		highScoreGrids[1] = new ResizableTextPanel("Score:",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),4,JLabel.CENTER,JLabel.CENTER,0,0);
		highScoreGrids[2] = new ResizableTextPanel("Word:",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),4,JLabel.CENTER,JLabel.CENTER,0,0);
		
		highscorePanel.add(highScoreGrids[0].panel);
		highscorePanel.add(highScoreGrids[1].panel);
		highscorePanel.add(highScoreGrids[2].panel);
		
		for(int i=0;i<10;i++)
		{
			
			highScoreGrids[3+(i*3)+0] = new ResizableTextPanel(highscores[i].scoreListNames,new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),5,JLabel.CENTER,JLabel.CENTER,0,0);
			highScoreGrids[3+(i*3)+1] = new ResizableTextPanel(highscores[i].scoreListScores,new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),5,JLabel.CENTER,JLabel.CENTER,0,0);
			highScoreGrids[3+(i*3)+2] = new ResizableTextPanel(highscores[i].scoreListWords,new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),5,JLabel.CENTER,JLabel.CENTER,0,0);
			
			highscorePanel.add(highScoreGrids[3+(i*3)+0].panel);
			highscorePanel.add(highScoreGrids[3+(i*3)+1].panel);
			highscorePanel.add(highScoreGrids[3+(i*3)+2].panel);
			
		}
		
		MostGuessedCharGrids = new ResizableTextPanel[12];
		MostAskedCharGrids = new ResizableTextPanel[12];
		
		JPanel MostGuessedCharPanel = new JPanel(new GridLayout(6,2,2,2));
		JPanel MostAskedCharPanel = new JPanel(new GridLayout(6,2,2,2));
		
		MostGuessedCharPanel.setBackground(new Color(255,255,255));
		MostGuessedCharPanel.setBorder(BorderFactory.createMatteBorder(1,2,1,2,new Color(255,255,255)));
		
		MostAskedCharPanel.setBackground(new Color(255,255,255));
		MostAskedCharPanel.setBorder(BorderFactory.createMatteBorder(1,2,1,2,new Color(255,255,255)));
		
		MostGuessedCharGrids[0] = new ResizableTextPanel("Character:",new Color(20,20,20),new  Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),6,JLabel.CENTER,JLabel.CENTER,0,0);
		MostGuessedCharGrids[1] = new ResizableTextPanel("Times:",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),6,JLabel.CENTER,JLabel.CENTER,0,0);

		MostGuessedCharPanel.add(MostGuessedCharGrids[0].panel);
		MostGuessedCharPanel.add(MostGuessedCharGrids[1].panel);
		
		for(int i=0;i<5;i++)
		{
			
			MostGuessedCharGrids[2+(i*2)+0] = new ResizableTextPanel(mostGuessedCharacters[i].character,new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),4,JLabel.CENTER,JLabel.CENTER,0,0);
			MostGuessedCharGrids[2+(i*2)+1] = new ResizableTextPanel(mostGuessedCharacters[i].amount+"",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),4,JLabel.CENTER,JLabel.CENTER,0,0);
			
			MostGuessedCharPanel.add(MostGuessedCharGrids[2+(i*2)+0].panel);
			MostGuessedCharPanel.add(MostGuessedCharGrids[2+(i*2)+1].panel);
			
		}
		
		MostAskedCharGrids[0] = new ResizableTextPanel("Character:",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),6,JLabel.CENTER,JLabel.CENTER,0,0);
		MostAskedCharGrids[1] = new ResizableTextPanel("Times:",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),6,JLabel.CENTER,JLabel.CENTER,0,0);

		MostAskedCharPanel.add(MostAskedCharGrids[0].panel);
		MostAskedCharPanel.add(MostAskedCharGrids[1].panel);
		
		for(int i=0;i<5;i++)
		{
			
			MostAskedCharGrids[2+(i*2)+0] = new ResizableTextPanel(mostAskedCharacters[i].character,new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),4,JLabel.CENTER,JLabel.CENTER,0,0);
			MostAskedCharGrids[2+(i*2)+1] = new ResizableTextPanel(mostAskedCharacters[i].amount+"",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),4,JLabel.CENTER,JLabel.CENTER,0,0);
			
			MostAskedCharPanel.add(MostAskedCharGrids[2+(i*2)+0].panel);
			MostAskedCharPanel.add(MostAskedCharGrids[2+(i*2)+1].panel);
			
		}
		
		highscoreTitle = new ResizableTextPanel("HIGHSCORES:",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),9,JLabel.CENTER,JLabel.CENTER,0,0);
		mostGuessedTitle = new ResizableTextPanel("Guessed Words Included:",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.PLAIN, 25),12,JLabel.CENTER,JLabel.CENTER,0,0);
		mostAskedTitle = new ResizableTextPanel("Asked Words Included:",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.PLAIN, 25),12,JLabel.CENTER,JLabel.CENTER,0,0);
		createdBy = new ResizableTextPanel("Created by MAGIC",new Color(20,20,20),new Color(255,255,255),new Font("SansSerif", Font.BOLD, 25),12,JLabel.CENTER,JLabel.CENTER,0,0);
		
		highscoreTitle.panel.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(255,255,255)));
		mostGuessedTitle.panel.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(255,255,255)));
		mostAskedTitle.panel.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(255,255,255)));
		createdBy.panel.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(255,255,255)));
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=0;
		gcon.gridy=0;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(10,10,10,10);
		
		backButton.setPreferredSize(new Dimension(100,25));
		backButton.setMinimumSize(new Dimension(100,25));
		
		statsMenu.add(backButton, gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=0;
		gcon.gridy=1;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,10,0,10);
		
		mostGuessedTitle.panel.setPreferredSize(new Dimension(100,25));
		mostGuessedTitle.panel.setMinimumSize(new Dimension(100,25));
		
		statsMenu.add(mostGuessedTitle.panel, gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=0;
		gcon.gridy=2;
		gcon.gridwidth=1;
		gcon.gridheight=6;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(10,10,10,10);
		
		MostGuessedCharPanel.setPreferredSize(new Dimension(100,150));
		MostGuessedCharPanel.setMinimumSize(new Dimension(50,150));
		
		statsMenu.add(MostGuessedCharPanel, gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=0;
		gcon.gridy=3;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,0,0,0);
		
		statsMenu.add(new JLabel(), gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=0;
		gcon.gridy=4;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,0,0,0);
		
		statsMenu.add(new JLabel(), gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=0;
		gcon.gridy=5;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,0,0,0);
		
		statsMenu.add(new JLabel(), gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=0;
		gcon.gridy=6;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,0,0,0);
		
		statsMenu.add(new JLabel(), gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=0;
		gcon.gridy=7;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,0,0,0);
		
		statsMenu.add(new JLabel(), gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=1;
		gcon.gridy=0;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(10,10,10,10);
		
		highscoreTitle.panel.setPreferredSize(new Dimension(100,25));
		highscoreTitle.panel.setMinimumSize(new Dimension(100,25));
		
		statsMenu.add(highscoreTitle.panel, gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=1;
		gcon.gridy=1;
		gcon.gridwidth=1;
		gcon.gridheight=7;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,10,10,10);
		
		highscorePanel.setPreferredSize(new Dimension(100,175));
		highscorePanel.setMinimumSize(new Dimension(100,175));
		
		statsMenu.add(highscorePanel, gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=2;
		gcon.gridy=0;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(10,10,10,10);
		
		mostAskedTitle.panel.setPreferredSize(new Dimension(100,25));
		mostAskedTitle.panel.setMinimumSize(new Dimension(100,25));
		
		statsMenu.add(mostAskedTitle.panel, gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=2;
		gcon.gridy=1;
		gcon.gridwidth=1;
		gcon.gridheight=6;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(0,10,0,10);
		
		MostAskedCharPanel.setPreferredSize(new Dimension(100,150));
		MostAskedCharPanel.setMinimumSize(new Dimension(100,150));
		
		statsMenu.add(MostAskedCharPanel, gcon);
		
		gcon.weightx=1;
		gcon.weighty=1;
		gcon.gridx=2;
		gcon.gridy=7;
		gcon.gridwidth=1;
		gcon.gridheight=1;
		gcon.anchor=GridBagConstraints.CENTER;
		gcon.fill=GridBagConstraints.BOTH;
		gcon.insets = new Insets(10,10,10,10);
		
		createdBy.panel.setPreferredSize(new Dimension(100,25));
		createdBy.panel.setMinimumSize(new Dimension(100,25));
		
		statsMenu.add(createdBy.panel, gcon);
		
		statsMenu.revalidate();
		statsMenu.repaint();
	}
	
	public void startWordle()
	{
		
		{
						
			this.remove(gameMenu);
			
			wordleGameArea = new JPanel();
			wordleGameArea.setBackground(new Color(60,60,60));
			
			this.add(wordleGameArea);
			
			wordleGameArea.setLayout(new GridBagLayout());
			GridBagConstraints gcon = new GridBagConstraints();
			
			wordleQuestionArea = new JPanel[playerAmount];
			
			GridLayout layout = new GridLayout(5,5);
			layout.setHgap(5);
			layout.setVgap(5);
			
			
			for(int i=0; i<playerAmount; i++)
			{
				
				System.out.println(correctAnswer[i]);
				
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
				if(playerAmount>1)
				{
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
			
			for(int row=0;row<5;row++)
			{
				for(int column=0;column<5;column++)
				{
					for(int i=0;i<playerAmount;i++)
					{
						
						wordArea[row][column][i] = new CharBox();
						wordleQuestionArea[i].add(wordArea[row][column][i].charBoxPanel);
						
					}
				}			
			}
			wordArea[0][0][0].highlight();			
		}
		
		if(addKeyboard)
		{
			startKeyboard();
		}
		
		startBottomLayer();
		
		this.revalidate();
		this.repaint();
	}
	
	private void startKeyboard()
	{
		
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

	private void startBottomLayer()
	{
		
		bottomLayer = new JPanel();
		
		bottomLayer.setPreferredSize(new Dimension ((530*playerAmount), 15));
		bottomLayer.setMinimumSize(new Dimension ((330*playerAmount), 15));
		
		bottomLayer.setBackground(new Color(0,0,0));

		GridBagConstraints gcon = new GridBagConstraints();
		
		gcon.fill = GridBagConstraints.BOTH;
		gcon.weightx=0;
		gcon.weighty=0;
		gcon.gridx=0;
		if(addKeyboard)
		{
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
		
		bottomLayerTimeLabel = new JLabel("Time: 0:00");
		bottomLayerTimeLabel.setHorizontalAlignment(JLabel.RIGHT);
		bottomLayerTimeLabel.setVerticalAlignment(JLabel.CENTER);
		bottomLayerTimeLabel.setForeground(Color.white);
		
		bottomLayerNamesLabel = new JLabel();
		bottomLayerNamesLabel.setHorizontalAlignment(JLabel.CENTER);
		bottomLayerNamesLabel.setVerticalAlignment(JLabel.CENTER);
		bottomLayerNamesLabel.setForeground(Color.white);
		
		if(playerAmount==1)
		{
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
	
	public void changeFocus(int newFocus)
	{
		wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
		focusedOn = newFocus;

		if(!gameFinished[focusedOn-1])
		{
			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
			if(addKeyboard) {
				for(int i=0;i<28;i++)
				{
					keyboardKeys[i].changeKeyboard(newFocus);
				}
			}
		}
	}

	public void pressBackspace()
	{
		
		if(enteredCharactersInWord[focusedOn-1]>0)
		{
			
			wordArea[numOfTries[focusedOn-1]][enteredCharactersInWord[focusedOn-1]-1][focusedOn-1].changeBoxText("");
			enteredCharactersInWord[focusedOn-1]--;
			
			if(enteredCharactersInWord[focusedOn-1]<4)
			{
				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
				positionInWord[focusedOn-1]--;
				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
			}
		}
		
	}
	
	public void pressEnter()
	{
		if(enteredCharactersInWord[focusedOn-1] == 5)
		{
			
			String enteredWord = "";
			
			for(int i=0; i<5; i++)
			{
				enteredWord += wordArea[numOfTries[focusedOn-1]][i][focusedOn-1].getText();
			}
			if(dictionary.contains(enteredWord))
			{
				score.newGuess(focusedOn-1,enteredWord,numOfTries[focusedOn-1]+1,secondsPassed);
    			for(int i=0; i<5; i++)
    			{
    				wordArea[numOfTries[focusedOn-1]][i][focusedOn-1].checkCorrectness(i, correctAnswer[focusedOn-1]);
        			if(addKeyboard)
        			{
            				keyboardKeys[(wordArea[numOfTries[focusedOn-1]][i][focusedOn-1].getText().charAt(0))-65].changeColor(wordArea[numOfTries[focusedOn-1]][i][focusedOn-1].charBoxPanel.getBackground(),focusedOn);
        			}
    				
    			}
    			

    			
        		if(numOfTries[focusedOn-1]<4)
        		{

        			if(enteredWord.equals(correctAnswer[focusedOn-1]))
        			{
            			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
            			
            			gameFinished[focusedOn-1]=true;
            			calculateFontSize(focusedOn-1,"You Won!",0);
            			gameWon[focusedOn-1]=true;
            			score.playerFinishedGame(focusedOn-1);
            			finalScore[focusedOn-1]=score.getPlayerScore(focusedOn-1);
            			this.repaint();
            			
            			if(playerAmount==2)
            			{
                			if(focusedOn<playerAmount)
                			{
                				changeFocus((focusedOn + 1));
                			}
                			else
                			{
                				if(focusedOn>1)
                				{
    	            				changeFocus((focusedOn - 1));
                				}
                			}
            			}
            			else
            			{
            				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
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
        			
        			gameFinished[focusedOn-1]=true;
        			calculateFontSize(focusedOn-1,"You Lost!",0);
        			gameWon[focusedOn-1]=false;
        			score.playerFinishedGame(focusedOn-1);
        			finalScore[focusedOn-1]=score.getPlayerScore(focusedOn-1);
        			this.repaint();
        			
        			if(playerAmount==2)
        			{
            			if(focusedOn<playerAmount)
            			{
            				changeFocus((focusedOn + 1));
            			}
            			else
            			{
            				if(focusedOn>1)
            				{
            					changeFocus((focusedOn - 1));
            				}
            			}
        			}
        			else
        			{
        				wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].unHighlight();
        			}
        		}
			}
		}
	}
	
	public void pressLeft()
	{
		if((focusedOn)>1)
		{
			if(!gameFinished[focusedOn-2])
			{
    			changeFocus(focusedOn-1);
			}
		}
	}
	
	public void pressRight()
	{
		if((focusedOn)<playerAmount)
		{
			if(!gameFinished[focusedOn])
			{
			changeFocus(focusedOn+1);
			}
		}
	}
	
	public void pressChar(String pressedKey)
	{
		
        wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].changeBoxText(pressedKey.toUpperCase());
        
        if(enteredCharactersInWord[focusedOn-1]<5)
        {
			enteredCharactersInWord[focusedOn-1]++;
        }
        
		if(positionInWord[focusedOn-1]<4)
		{
			positionInWord[focusedOn-1]++;
			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]-1][focusedOn-1].unHighlight();
			wordArea[numOfTries[focusedOn-1]][positionInWord[focusedOn-1]][focusedOn-1].highlight();
		}
		
	}
	
	public void drawDraggedKeyboard(KeyboardBox buttonToDraw, int x, int y, Graphics g)
	{
		
		Graphics2D g2 = (Graphics2D)g;
		
	    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		
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
		g2.drawString(labelText, x + (buttonWidth - labelWidth)/2, y + (buttonHeight - labelHeight)/2 + labelAscent);
		
	}
	
	
	public void calculateFontSize(int player, String message, int scoreValue)
	{		
		
		String score = String.format("Score: %04d", scoreValue);
		
		int areaX = wordleQuestionArea[player].getX()+7;
		int areaY = wordleQuestionArea[player].getY()+32;
		
		int areaWidth = wordleQuestionArea[player].getWidth();
		int areaHeight = wordleQuestionArea[player].getHeight();
				
		int componentHeight = areaHeight/5;
		int componentWidth = 9*areaWidth/10;
		
		int messageX = areaX + areaWidth/2 - componentWidth/2;
		int messageY = areaY + 1*((areaHeight-componentHeight)/3);

		int scoreX = areaX + areaWidth/2 - componentWidth/2;
		int scoreY = areaY + 2*((areaHeight-componentHeight)/3);
		
		messageFont = new Font("SansSerif", Font.BOLD, 25);
		scoreFont = new Font("SansSerif", Font.BOLD, 25);
		
		int messageWidth = getFontMetrics(messageFont).stringWidth(message);
		int scoreWidth = getFontMetrics(scoreFont).stringWidth(score);
		
		double messageWidthRatio = (double)componentWidth / (double)messageWidth;
		int messageNewFontSize = (int)(messageFont.getSize() * messageWidthRatio);
		int messageFontSizeToUse = Math.min(messageNewFontSize, componentHeight);
		
		messageFont = new Font("SansSerif", Font.BOLD, messageFontSizeToUse);
		messageWidth = getFontMetrics(messageFont).stringWidth(message);
		
		
		double scoreWidthRatio = (double)componentWidth / (double)scoreWidth;
		int scoreNewFontSize = (int)(scoreFont.getSize() * scoreWidthRatio);
		int scoreFontSizeToUse = Math.min(scoreNewFontSize, componentHeight);
		
		scoreFont = new Font("SansSerif", Font.BOLD, scoreFontSizeToUse);
		scoreWidth = getFontMetrics(scoreFont).stringWidth(score);
		
		int messageHeight = getFontMetrics(messageFont).getHeight();
		int scoreHeight = getFontMetrics(scoreFont).getHeight();
		
		int messageAscent = getFontMetrics(messageFont).getAscent();
		int scoreAscent = getFontMetrics(scoreFont).getAscent();
		
		
		messageCordinates[player] = new int[]{messageX + (componentWidth - messageWidth)/2, messageY + (componentHeight - messageHeight)/2 + messageAscent};
		scoreCordinates[player] = new int[]{scoreX + (componentWidth - scoreWidth)/2, scoreY + (componentHeight - scoreHeight)/2 + scoreAscent};
		
	}
	
	
	public void displayStatus(int player, String message, int scoreValue, boolean antialiass,Graphics g)
	{
		
		
		String score = String.format("Score: %04d", scoreValue);
		Graphics2D g2 = (Graphics2D)g;
		
		if(antialiass)
		{
		    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		else
		{
		    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}

		g2.setColor(new Color(225,50,50));
		
		g2.setFont(messageFont);
		g2.drawString(message, messageCordinates[player][0], messageCordinates[player][1]);
		
		g2.setFont(scoreFont);
		g2.drawString(score, scoreCordinates[player][0], scoreCordinates[player][1]);
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paintComponents(g);
		
		if(gameStarted)
		{
			
			if(draggingKeyboardBox)
			{
				drawDraggedKeyboard(draggedKeyboardBox, draggingX-draggingXOffset, draggingY-draggingYOffset, g);
				for(int i=0;i<playerAmount;i++)
				{
					if(gameFinished[i])
					{
						if(gameWon[i])
						{
							displayStatus(i,"You Won!",finalScore[i],false,g);
						}
						else
						{
							displayStatus(i,"You Lost!",finalScore[i],false,g);
						}
					}
				}
			}
			else
			{
				for(int i=0;i<playerAmount;i++)
				{
					if(gameFinished[i])
					{
						if(gameWon[i])
						{
							displayStatus(i,"You Won!",finalScore[i],true,g);
						}
						else
						{
							displayStatus(i,"You Lost!",finalScore[i],true,g);
						}
					}
				}
			}
			

		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		
		if(event.getSource() == startGameButton)
		{
			if((!(playerOneNameField.getText().equals("")||playerOneNameField.getText().equals("Please Enter A Name")
					||playerOneNameField.getText().equals("Name Too Long")||playerOneNameField.getText().length()>10))
						&&((playerAmount!=2||(!(playerTwoNameField.getText().equals("")||playerTwoNameField.getText().equals("Please Enter A Name")
								||playerTwoNameField.getText().equals("Name Too Long")||playerTwoNameField.getText().length()>10)))))
			{
				if(!gameStarted)
				{
					
					this.setSize(new Dimension(550,550));
					this.setMinimumSize(new Dimension(350,350));
					
					gameStarted = true;
					
					secondsPassed = 0;
					focusedOn = 1;
										
					if(playerAmount==1)
					{
						
						correctAnswer = new String[] {this.dictionary.random(),this.dictionary.random()};
						playerNames = new String[] {playerOneNameField.getText()};
						enteredCharactersInWord = new int[]{0};
						positionInWord = new int[]{0};
						numOfTries = new int[]{0};
						finalScore = new int[]{0};
						gameFinished = new boolean[]{false};
						gameWon = new boolean[]{false};;
						pressedOnQuestionArea = new boolean[]{false};
						messageCordinates = new int[][]{{0,0}};
						scoreCordinates = new int[][]{{0,0}};
						
						score.setNewPlayers(1,playerNames,correctAnswer);
						
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
						correctAnswer = new String[] {this.dictionary.random(),this.dictionary.random()};
						playerNames = new String[] {playerOneNameField.getText(), playerTwoNameField.getText()};
						enteredCharactersInWord = new int[]{0,0};
						positionInWord = new int[]{0,0};
						numOfTries = new int[]{0,0};
						finalScore = new int[]{0,0};
						gameFinished = new boolean[]{false,false};
						gameWon = new boolean[]{false,false};;
						pressedOnQuestionArea = new boolean[]{false,false};
						messageCordinates = new int[][]{{0,0},{0,0}};
						scoreCordinates = new int[][]{{0,0},{0,0}};
						
						score.setNewPlayers(2,playerNames,correctAnswer);
						
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
				if((playerOneNameField.getText().equals("")||playerOneNameField.getText().equals("Please Enter A Name")||playerOneNameField.getText().equals("Name Too Long")))
				{
					playerOneNameField.setText("Please Enter A Name");
				}
				else
				{
					if((playerOneNameField.getText().length()>10))
					{
						playerOneNameField.setText("Name Too Long");
					}
				}
				
				if(playerTwoNameField.isEnabled())
				{
					if((playerTwoNameField.getText().equals("")||playerTwoNameField.getText().equals("Please Enter A Name")||playerTwoNameField.getText().equals("Name Too Long")))
					{
						playerTwoNameField.setText("Please Enter A Name");
					}
					else
					{
						if((playerTwoNameField.getText().length()>10))
						{
							playerTwoNameField.setText("Name Too Long");
						}
					}
				}
			}
		}
		
		if(event.getSource() == openStatsButton)
		{
			showingStatsMenu = true;
			this.setMinimumSize(new Dimension(1000,625));
			this.setSize(new Dimension(1000,625));
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation((int)(screenSize.getWidth() - this.getSize().getWidth())/2, (int)(screenSize.getHeight() - this.getSize().getHeight())/2);
			
			highscores = score.getHighscore();
			mostAskedCharacters = score.getMostGuessed();
			mostGuessedCharacters = score.getMostAsked();
			startStats();
		}
		
		if(event.getSource() == backButton)
		{

			showingStatsMenu = false;
			this.setMinimumSize(new Dimension(450,325));
			this.setSize(new Dimension(500,350));
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation((int)(screenSize.getWidth() - this.getSize().getWidth())/2, (int)(screenSize.getHeight() - this.getSize().getHeight())/2);
			
			this.remove(statsMenu);
			startMenu();
		}
		
		if(event.getSource() == onePlayerButton)
		{
			if(!gameStarted)
			{
				playerAmount=1;
				playerTwoNameField.setText("");
				playerTwoNameField.setEnabled(false);
			}
		}
		
		if(event.getSource() == twoPlayerButton)
		{
			if(!gameStarted)
			{
				playerAmount=2;
				playerTwoNameField.setEnabled(true);
			}
		}
		
		if(event.getSource() == addKeyboardYESButton)
		{
			if(!gameStarted)
			{
				addKeyboard=true;
			}
		}
		
		if(event.getSource() == addKeyboardNOButton)
		{
			if(!gameStarted)
			{
				addKeyboard=false;				
			}
		}
		
		if(event.getSource() == timer)
		{
			if(gameStarted)
			{
				
				secondsPassed++;
				
				String TimeString = bottomLayerTimeLabel.getText();
				
				if(!TimeString.equals("Time: Too Long"))
				{
				
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
								timer.stop();
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

					bottomLayerTimeLabel.setText("Time: "+minute+":"+tenSecond+oneSecond);
					
				}				
			}
		}		
	}

	public void keyPressed(KeyEvent event)
	{
		if(gameStarted && !gameFinished[focusedOn-1])
		{
			
	    	if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE)
	    	{
	    		pressBackspace();
	    		
	    		return;
	    	}
	    	
	    	if(event.getKeyCode() == KeyEvent.VK_ENTER)
	    	{
	    		pressEnter();
	    		
	    		return;
	    	}
	    	
	    	if (event.getKeyCode() == KeyEvent.VK_LEFT)
	    	{
	    		pressLeft();
	    		
	    		return;
	    	}
	    	
	    	if (event.getKeyCode() == KeyEvent.VK_RIGHT)
	    	{
	    		pressRight();
	    		
	    		return;
	    	}
	    	
	    	if ((event.getKeyChar() > 64 && event.getKeyChar() < 91) || (event.getKeyChar() > 96 && event.getKeyChar() < 123))
	    	{
	    		pressChar(event.getKeyChar()+"");
	    		
				return;
	    	}
			
		}

    }
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		/**/
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		/**/	
	}

	@Override
	public void mousePressed(MouseEvent e)
	{		

		int clickedX = e.getX()-7;
		int clickedY = e.getY()-32;
		
		//this is windows specific and maybe my computer specific
		
		if(gameStarted)
		{
			if(playerAmount>1)
			{
				if(clickedY>wordleQuestionArea[0].getY()&&clickedY<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getHeight())
				{
					if (clickedX>wordleQuestionArea[0].getX() && clickedX<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getWidth())
					{
						if(!gameFinished[0])
						{
							pressedOnQuestionArea[0]=true;
						}
					}
					if (clickedX>wordleQuestionArea[1].getX() && clickedX<wordleQuestionArea[1].getX()+wordleQuestionArea[1].getWidth())
					{
						if(!gameFinished[1])
						{
							
							pressedOnQuestionArea[1]=true;
						}
					}
				}
			}


			
			if(addKeyboard)
			{
				int keyboardX = keyboard.getX();
				int keyboardY = keyboard.getY();
				int keyboardWidth = keyboard.getWidth();
				int keyboardHeight = keyboard.getHeight();
				
				if(clickedX>keyboardX&&clickedX<(keyboardX+keyboardWidth)&&clickedY>keyboardY&&clickedY<(keyboardY+keyboardHeight))
				{
					for(int i=0; i<28; i++)
					{
						int currentKeyX = keyboardKeys[i].KeyboardBoxPanel.getX();
						int currentKeyY = keyboardKeys[i].KeyboardBoxPanel.getY();
						int currentKeyWidth = keyboardKeys[i].KeyboardBoxPanel.getWidth();
						int currentKeyHeight = keyboardKeys[i].KeyboardBoxPanel.getHeight();
						
						boolean bothFinished = true;
						
						for(int j=0;j<gameFinished.length;j++)
						{
							if(!gameFinished[j])
							{
								bothFinished = false;
							}
						}
						
						if(!bothFinished)
						{
							if(clickedX>(keyboardX+currentKeyX)&&clickedX<(keyboardX+currentKeyX+currentKeyWidth)&&clickedY>(keyboardY+currentKeyY)&&clickedY<(keyboardY+currentKeyY+currentKeyHeight))
							{
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
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{

		int clickedX = e.getX()-7;
		int clickedY = e.getY()-32;
		
		//this is windows specific and maybe my computer specific
		
		if(gameStarted)
		{
			if(draggingKeyboardBox)
			{
				if(playerAmount>1)
				{
					if(clickedY>wordleQuestionArea[0].getY()&&clickedY<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getHeight())
					{
						if (clickedX>wordleQuestionArea[0].getX() && clickedX<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getWidth())
						{
							if(!gameFinished[0])
							{
								changeFocus(1);
								String draggedKeyText = draggedKeyboardBox.getText();
								
								if(draggedKeyText.equals("ENTER"))
								{
									pressEnter();
								}
								else
								{
									if(draggedKeyText.equals("DELETE"))
									{
										pressBackspace();
									}
									else
									{
										pressChar(draggedKeyText);
									}
								}
							}
						}
						if (clickedX>wordleQuestionArea[1].getX() && clickedX<wordleQuestionArea[1].getX()+wordleQuestionArea[1].getWidth())
						{
							if(!gameFinished[1])
							{
								changeFocus(2);
								String draggedKeyText = draggedKeyboardBox.getText();
								
								if(draggedKeyText.equals("ENTER"))
								{
									pressEnter();
								}
								else
								{
									if(draggedKeyText.equals("DELETE"))
									{
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
					if(clickedY>wordleQuestionArea[0].getY()&&clickedY<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getHeight())
					{
						if (clickedX>wordleQuestionArea[0].getX() && clickedX<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getWidth())
						{
							if(!gameFinished[0])
							{
								String draggedKeyText = draggedKeyboardBox.getText();
								
								if(draggedKeyText.equals("ENTER"))
								{
									pressEnter();
								}
								else
								{
									if(draggedKeyText.equals("DELETE"))
									{
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
			}
			else
			{
				if(playerAmount>1)
				{
					if(clickedY>wordleQuestionArea[0].getY()&&clickedY<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getHeight())
					{
						if (clickedX>wordleQuestionArea[0].getX() && clickedX<wordleQuestionArea[0].getY()+wordleQuestionArea[0].getWidth())
						{
							if(!gameFinished[0])
							{
								if(pressedOnQuestionArea[0]==true)
									changeFocus(1);
							}
						}
						if (clickedX>wordleQuestionArea[1].getX() && clickedX<wordleQuestionArea[1].getX()+wordleQuestionArea[1].getWidth())
						{
							if(!gameFinished[1])
							{
								if(pressedOnQuestionArea[1]==true)
									changeFocus(2);
								}
							}
						}
					}
				
				for(int i=0; i<pressedOnQuestionArea.length; i++)
				{
					pressedOnQuestionArea[i]=false;
				}
			}
		}	
		
		draggingKeyboardBox = false;
		pressedOnKeyboardBox = false;
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		/**/
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		/**/
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		/**/
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(pressedOnKeyboardBox)
		{
			
			draggingKeyboardBox = true;
			draggingX = e.getX();
			draggingY = e.getY();
			repaint();
			
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		/**/
	}
	
	@Override
	public void componentResized(ComponentEvent e)
	{
		if(gameStarted)
		{
			for(int row=0;row<5;row++)
			{
				for(int column=0;column<5;column++)
				{
					for(int i=0; i<playerAmount; i++)
					{
						wordArea[row][column][i].resizeText();
					}
				}
			}
			if(addKeyboard)
			{
				for(int key=0;key<28;key++)
				{
					keyboardKeys[key].resizeText();
				}
			}
			for(int i=0; i<playerAmount; i++)
			{
				if(gameFinished[i])
				{
					if(gameWon[i]) {
						calculateFontSize(i,"You Won!",0);
					}
					else
					{
						calculateFontSize(i,"You Lost!",0);
					}
				}
			}
		}
		else
		{
			if(showingStatsMenu)
			{
				for(int i=0;i<33;i++)
				{
					highScoreGrids[i].resizeText();
				}
				for(int i=0;i<12;i++)
				{
					MostGuessedCharGrids[i].resizeText();
				}
				for(int i=0;i<12;i++)
				{
					MostAskedCharGrids[i].resizeText();
				}
				highscoreTitle.resizeText();
				mostGuessedTitle.resizeText();
				mostAskedTitle.resizeText();
				createdBy.resizeText();
				backButton.resizeText();
			}
		}
		this.repaint();
	}
	
	@Override
	public void componentMoved(ComponentEvent e)
	{
		/**/
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		/**/
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		/**/		
	}
	
}
