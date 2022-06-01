package wordlemainpackage;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Source {

	public static void main(String[] args) {
		
		
		JFrame appWindow = new JFrame();
		appWindow.setLayout(new GridLayout(1,1));
		
		WordleGui newGame1 = new WordleGui();
		
		appWindow.add(newGame1.mainWordleBox);
		
		appWindow.setSize(550,550);
				
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		appWindow.setLocation((int)(screenSize.getWidth() - appWindow.getSize().getWidth())/2, (int)(screenSize.getHeight() - appWindow.getSize().getHeight())/2);
		
		appWindow.setMinimumSize(new Dimension(350, 350));
		
		appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		appWindow.setTitle("Wordle");
		
		appWindow.setVisible(true);
		
	}

}
