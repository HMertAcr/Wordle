package wordlemainpackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

public class ResizableButton extends JButton
{

	int prefferedSize;
	int verticalInset;
	int horizontalInSet;
	Color pressedBackgroundColor;
	Color hoverBackgroundColor;
	
	
	public ResizableButton()
	{
		super.setContentAreaFilled(false);
		pressedBackgroundColor = this.getBackground();
		hoverBackgroundColor = this.getBackground();
		prefferedSize = 0;
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
	
    @Override
    protected void paintComponent(Graphics g)
    {
        if (getModel().isPressed())
        {
            g.setColor(pressedBackgroundColor);
        }
        else
        {
            if (getModel().isRollover())
            {
                g.setColor(hoverBackgroundColor);
            }
            else
            {
                g.setColor(getBackground());
            }
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b)
    {
    	/**/
    }

    public Color getHoverBackgroundColor()
    {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor)
    {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor()
    {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor)
    {
        this.pressedBackgroundColor = pressedBackgroundColor;
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
			tempString = this.getText();
		}
		
		int prefferedStringWidth = this.getFontMetrics(labelFont).stringWidth(tempString);
		int actualStringWidth = this.getFontMetrics(labelFont).stringWidth(this.getText());
		int stringWidth = Math.max(actualStringWidth, prefferedStringWidth);
		int componentWidth = this.getWidth()-horizontalInSet;

		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = this.getHeight()-verticalInset;

		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		this.setFont(new Font(labelFont.getName(), labelFont.getStyle(), fontSizeToUse));
		
	}
}
