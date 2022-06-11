package wordlemainpackage;

import java.awt.Font;

import javax.swing.*;

@SuppressWarnings("serial")
public class ResizableButton extends JButton
{

	int prefferedSize;
	int verticalInset;
	int horizontalInSet;
	
	public ResizableButton()
	{
		
	}
	
	
	public void setPrefferedTextSize(int prefferedSize)
	{
		this.prefferedSize = prefferedSize;
		verticalInset = 0;
		horizontalInSet = 0;
	}
	
	public void setVerticalOffset(int verticalInset)
	{
		this.verticalInset = verticalInset;
	}
	
	public void setHorizontalOffset(int horizontalInSet)
	{
		this.horizontalInSet = horizontalInSet;
	}
	
	public void resizeText()
	{
		
		Font labelFont = this.getFont();
		
		String tempString;
		
		if(prefferedSize!=0)
		{
			tempString="";
			for(int i=0;i<prefferedSize;i++)
			{
				tempString += "W";
			}
		}
		else
		{
			tempString=this.getText();
		}
		
		int prefferedStringWidth = this.getFontMetrics(labelFont).stringWidth(tempString);
		int actualStringWidth = this.getFontMetrics(labelFont).stringWidth(this.getText())+5;
		int stringWidth = Math.max(actualStringWidth, prefferedStringWidth);
		int componentWidth = this.getWidth()-horizontalInSet;

		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = this.getHeight()-verticalInset;

		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		this.setFont(new Font(labelFont.getName(), labelFont.getStyle(), fontSizeToUse));
		
	}
}
