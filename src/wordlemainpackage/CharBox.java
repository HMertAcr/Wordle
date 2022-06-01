package wordlemainpackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class CharBox
{
	
	public
	JPanel charBoxPanel;
	JLabel charLabel;
	int colorState;
	//0=black empty
	//1=gray char not in word
	//2=yellow char in word wrong place
	//3=green char in correct place
	
	public CharBox()
	{
		
		colorState = 0;
		
		charBoxPanel = new JPanel();
				
		charBoxPanel.setLayout(new GridLayout(1,1));
		charBoxPanel.setBackground(Color.black);

		charLabel = new JLabel();
		
		charLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		charLabel.setForeground(Color.white);
		
		charLabel.setHorizontalAlignment(JLabel.CENTER);
		charLabel.setVerticalAlignment(JLabel.CENTER);
				
		charBoxPanel.add(charLabel);
		

	}
	
	public void changeBoxText(String newLabelText) {
		
		charLabel.setText(newLabelText);
		
	}
	
	public void highlight() {
		charLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
	}
	
	public void unHighlight() {
		charLabel.setBorder(BorderFactory.createEmptyBorder());
	}
	
	public void checkCorrectness(int position, String correctWord)
	{
		
		String character = charLabel.getText();
		
		if(correctWord.contains(character))
		{
			if(correctWord.charAt(position) == character.charAt(0))
			{
				colorState = 3;
				charBoxPanel.setBackground(Color.green);
				return;
			}
			colorState = 2;
			charBoxPanel.setBackground(Color.yellow);
			return;
		}
		else
		{
			colorState = 1;
			charBoxPanel.setBackground(Color.gray);
			return;
		}
		
	}
	
}
