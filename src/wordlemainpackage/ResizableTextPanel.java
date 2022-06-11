package wordlemainpackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResizableTextPanel {
	
	public
	
	JPanel panel;
	JLabel label;
	String labelText;
	int verticalInset;
	int horizontalInset;
	int size;
	
	public ResizableTextPanel(String text, Color bgcolor, Color fgcolor, Font font, int prefferedTextSizeToFit, int verticalIns, int HorizontalIns)
	{
		size = prefferedTextSizeToFit;
		verticalInset = verticalIns;
		horizontalInset = HorizontalIns;
		
		panel = new JPanel();
		
		panel.setLayout(new GridLayout(1,1));
		panel.setBackground(bgcolor);
		
		labelText = text;
		label = new JLabel(labelText);
		
		panel.setFont(font);
		panel.setForeground(fgcolor);
		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
				
		panel.add(label);
		

	}
	
	
	public void resizeText()
	{
		
		Font labelFont = label.getFont();
		
		String tempString;
		
		if(size!=0)
		{
			tempString="";
			for(int i=0;i<size;i++)
			{
				tempString += "W";
			}
		}
		else
		{
			tempString=labelText;
		}
		
		int prefferedStringWidth = label.getFontMetrics(labelFont).stringWidth(tempString);
		int actualStringWidth = label.getFontMetrics(labelFont).stringWidth(labelText)+5;
		int stringWidth = Math.max(actualStringWidth, prefferedStringWidth);
		int componentWidth = label.getWidth()-horizontalInset;

		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = panel.getHeight()-verticalInset;

		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		label.setFont(new Font(labelFont.getName(), Font.BOLD, fontSizeToUse));

	}
}
