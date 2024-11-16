package G_Package;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class customFontGallery {
	private Font FG1;
    private Font FG2;
    private Font FG3;
    private Font FG4;
    private Font FG5;
    private Font tCF;
    private Font dCF;
    private Font vFG;
    private Font pFG;

    public customFontGallery() {
    	//Title
        try {
            File font1 = new File("SFPRODISPLAYBOLD.OTF");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font SFProBold = Font.createFont(Font.TRUETYPE_FONT, font1);
            ge.registerFont(SFProBold);
            setFG1(SFProBold.deriveFont(Font.BOLD, 19));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        
        //Headers
        try {
            File font2 = new File("SFPRODISPLAYREGULAR.OTF");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font SFProRegular = Font.createFont(Font.TRUETYPE_FONT, font2);
            ge.registerFont(SFProRegular);
            setFG2(SFProRegular.deriveFont(Font.BOLD, 15));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        
        //Normal Text
        try {
            File font2 = new File("SFPRODISPLAYREGULAR.OTF");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font SFProRegular = Font.createFont(Font.TRUETYPE_FONT, font2);
            ge.registerFont(SFProRegular);
            setFG3(SFProRegular.deriveFont(Font.BOLD, 15));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        
        try {
            File font2 = new File("SFPRODISPLAYREGULAR.OTF");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font SFProRegular = Font.createFont(Font.TRUETYPE_FONT, font2);
            ge.registerFont(SFProRegular);
            setFG4(SFProRegular.deriveFont(Font.PLAIN, 13));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        
        try {
            File font2 = new File("SFPRODISPLAYREGULAR.OTF");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font SFProRegular = Font.createFont(Font.TRUETYPE_FONT, font2);
            ge.registerFont(SFProRegular);
            setFG5(SFProRegular.deriveFont(Font.BOLD, 11));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        //Special for D&T
        try {
        	File font1 = new File("SFPRODISPLAYBOLD.OTF");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font SFProBold = Font.createFont(Font.TRUETYPE_FONT, font1);
            ge.registerFont(SFProBold);
            settCF(SFProBold.deriveFont(Font.TRUETYPE_FONT, 21));
        } catch (IOException | FontFormatException e) {
        	e.printStackTrace();
        }
        
        try {
        	File font2 = new File("SFPRODISPLAYREGULAR.OTF");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font SFProRegular = Font.createFont(Font.TRUETYPE_FONT, font2);
            ge.registerFont(SFProRegular);
            setdCF(SFProRegular.deriveFont(Font.PLAIN, 15));
        } catch (IOException | FontFormatException e) {
        	e.printStackTrace();
        }
        
        try {
        	File font1 = new File("SFPRODISPLAYBOLD.OTF");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font SFProBold = Font.createFont(Font.TRUETYPE_FONT, font1);
            ge.registerFont(SFProBold);
            setvFG(SFProBold.deriveFont(Font.TRUETYPE_FONT, 51));
        } catch (IOException | FontFormatException e) {
        	e.printStackTrace();
        }
        
        try {
        	File font1 = new File("SFPRODISPLAYBOLD.OTF");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font SFProBold = Font.createFont(Font.TRUETYPE_FONT, font1);
            ge.registerFont(SFProBold);
            setpFG(SFProBold.deriveFont(Font.TRUETYPE_FONT, 12));
        } catch (IOException | FontFormatException e) {
        	e.printStackTrace();
        }
    }

	public Font getFG2() {
		return FG2;
	}

	public void setFG2(Font fG2) {
		FG2 = fG2;
	}

	public Font getvFG() {
		return vFG;
	}

	public void setvFG(Font vFG) {
		this.vFG = vFG;
	}

	public Font getFG1() {
		return FG1;
	}

	public void setFG1(Font fG1) {
		FG1 = fG1;
	}

	public Font getpFG() {
		return pFG;
	}

	public void setpFG(Font pFG) {
		this.pFG = pFG;
	}

	public Font getFG4() {
		return FG4;
	}

	public void setFG4(Font fG4) {
		FG4 = fG4;
	}

	public Font gettCF() {
		return tCF;
	}

	public void settCF(Font tCF) {
		this.tCF = tCF;
	}

	public Font getdCF() {
		return dCF;
	}

	public void setdCF(Font dCF) {
		this.dCF = dCF;
	}

	public Font getFG3() {
		return FG3;
	}

	public void setFG3(Font fG3) {
		FG3 = fG3;
	}

	public Font getFG5() {
		return FG5;
	}

	public void setFG5(Font fG5) {
		FG5 = fG5;
	}
}
