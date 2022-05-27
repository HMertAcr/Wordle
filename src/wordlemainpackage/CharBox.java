package wordlemainpackage;

public class CharBox {
	public
	char character;
	int colorState;
	//0=black empty
	//1=gray char not in word
	//2=yellow char in word wrong place
	//3=green char in correct place
	
	public CharBox() {
		character = ' ';
		colorState = 0;
	}
	
	public void enterChar(char newchar) {
		character = newchar;
	}
	
	public void checkColor(int position, String correctWord) {
		if(correctWord.charAt(position) == character) {
			colorState = 3;
			return;
		}
		
		for(int i=0;i<5;i++) {
			if(correctWord.charAt(i) == character) {
				colorState = 2;
				return;
			}
		}
		
		colorState = 1;
		return;
		
	}
	
}
