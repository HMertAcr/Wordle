package wordlemainpackage;

import java.awt.*;
import javax.swing.*;

public class CharBox
{
	
	public
	JPanel charBoxPanel;
	JLabel charLabel;
	
	public CharBox()
	{
		
		charBoxPanel = new JPanel();
				
		charBoxPanel.setLayout(new GridLayout(1,1));
		charBoxPanel.setBackground(new Color(0,0,0));

		charLabel = new JLabel();
		
		charLabel.setFont(new Font("SansSerif", Font.BOLD, 92));
		charLabel.setForeground(Color.white);
		
		charLabel.setHorizontalAlignment(JLabel.CENTER);
		charLabel.setVerticalAlignment(JLabel.CENTER);
				
		charBoxPanel.add(charLabel);
		

	}
	
	public void changeBoxText(String newLabelText)
	{
		
		charLabel.setText(newLabelText);
		
	}
	
	public String getText()
	{
		
		return charLabel.getText();
		
	}
	
	public void resizeText()
	{
				
		Font labelFont = charLabel.getFont();
		
		int stringWidth = charLabel.getFontMetrics(labelFont).stringWidth("W");
		int componentWidth = charBoxPanel.getWidth()-5;

		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = charBoxPanel.getHeight()-5;

		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		charLabel.setFont(new Font(labelFont.getName(), Font.BOLD, fontSizeToUse));

	}
	
	public void highlight()
	{
		charLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(180,160,60)));
	}
	
	public void unHighlight()
	{
		charLabel.setBorder(BorderFactory.createEmptyBorder());
	}
	
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
	
}
