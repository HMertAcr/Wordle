package wordlemainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class KeyboardBox
{
	
	public
	JPanel KeyboardBoxPanel;
	JLabel charLabel;
	
	public KeyboardBox(String newLabelText)
	{
		
		KeyboardBoxPanel = new JPanel();
				
		KeyboardBoxPanel.setLayout(new GridLayout(1,1));
		KeyboardBoxPanel.setBackground(new Color(0,0,0));

		charLabel = new JLabel();
		charLabel.setText(newLabelText);

		charLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		charLabel.setForeground(Color.white);
		
		KeyboardBoxPanel.setPreferredSize(new Dimension(10,10));
		KeyboardBoxPanel.setMinimumSize(new Dimension(10,10));
		
		charLabel.setHorizontalAlignment(JLabel.CENTER);
		charLabel.setVerticalAlignment(JLabel.CENTER);
				
		KeyboardBoxPanel.add(charLabel);
		

	}
	
	public String getText() {
		
		return charLabel.getText();
		
	}
	
	public void resizeText()
	{
				
		Font labelFont = charLabel.getFont();
		
		String tempString = "";
		int tempLength = charLabel.getText().length();
		
		if(tempLength==1) {
			tempString = "W";
		}
		
		if(tempLength==5||tempLength==6) {
			tempString = "DELETE";
		}

		
		int stringWidth = charLabel.getFontMetrics(labelFont).stringWidth(tempString);
		int componentWidth = KeyboardBoxPanel.getWidth()-5;

		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = KeyboardBoxPanel.getHeight();

		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		charLabel.setFont(new Font(labelFont.getName(), Font.BOLD, fontSizeToUse));

	}
	/*

	public void checkCorrectness(int position, String correctWord)
	{
				
		String character = charLabel.getText();
		
		if(correctWord.contains(character))
		{
			if(correctWord.charAt(position) == character.charAt(0))
			{
				charBoxPanel.setBackground(new Color(85,140,80));
				return;
			}
			charBoxPanel.setBackground(new Color(180,160,60));
			return;
		}
		else
		{
			charBoxPanel.setBackground(new Color(60,60,60));
			return;
		}
		
		
	}
	*/
}
