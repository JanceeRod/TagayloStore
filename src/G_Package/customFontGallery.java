package G_Package;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

//import F_Package;

public class customFontGallery {
	private Font titleFont;
    private Font productPriceREGULAR;
    private Font productPriceBOLD;
    private Font productNameREGULAR;
    private Font productNameBOLD;
    private Font FG4;
    private Font FG5;
    private Font tCF;
    private Font dCF;
    private Font vFG;
    private Font pFG;

    public customFontGallery() {
    	//Title
        try {
            InputStream InterFontBOLDFile = getClass().getResourceAsStream("/F_Package/Inter_28pt-Bold.ttf");
            if (InterFontBOLDFile != null) {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                Font InterFontBOLD = Font.createFont(Font.TRUETYPE_FONT, InterFontBOLDFile);
                ge.registerFont(InterFontBOLD);
                setTitleFont(InterFontBOLD.deriveFont(Font.BOLD, 24));
            } else {
                System.err.println("Font file not found in the package.");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        //Product Price
        try {
            InputStream InterFontREGULARFile = getClass().getResourceAsStream("/F_Package/Inter_28pt-Regular.ttf");
            if (InterFontREGULARFile != null) {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                Font InterFontREGULAR = Font.createFont(Font.TRUETYPE_FONT, InterFontREGULARFile);
                ge.registerFont(InterFontREGULAR);
                setProductPriceREGULAR(InterFontREGULAR.deriveFont(Font.PLAIN, 20));
            } else {
                System.err.println("Font file not found in the package.");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        try {
            InputStream InterFontBOLDFile = getClass().getResourceAsStream("/F_Package/Inter_28pt-Bold.ttf");
            if (InterFontBOLDFile != null) {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                Font InterFontBOLD = Font.createFont(Font.TRUETYPE_FONT, InterFontBOLDFile);
                ge.registerFont(InterFontBOLD);
                setProductPriceBOLD(InterFontBOLD.deriveFont(Font.PLAIN, 20));
            } else {
                System.err.println("Font file not found in the package.");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        //Product Name
        try {
            InputStream InterFontREGULARFile = getClass().getResourceAsStream("/F_Package/Inter_28pt-Regular.ttf");
            if (InterFontREGULARFile != null) {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                Font InterFontREGULAR = Font.createFont(Font.TRUETYPE_FONT, InterFontREGULARFile);
                ge.registerFont(InterFontREGULAR);
                setProductNameREGULAR(InterFontREGULAR.deriveFont(Font.PLAIN, 12));
            } else {
                System.err.println("Font file not found in the package.");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        try {
            InputStream InterFontBOLDFile = getClass().getResourceAsStream("/F_Package/Inter_28pt-Bold.ttf");
            if (InterFontBOLDFile != null) {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                Font InterFontBOLD = Font.createFont(Font.TRUETYPE_FONT, InterFontBOLDFile);
                ge.registerFont(InterFontBOLD);
                setProductNameBOLD(InterFontBOLD.deriveFont(Font.PLAIN, 12));
            } else {
                System.err.println("Font file not found in the package.");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }






        //-------------------------------------
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

    public void setTitleFont(Font font) {
        titleFont = font;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public void setProductPriceBOLD(Font font) {
        productPriceBOLD = font;
    }

    public Font getProductPriceBOLD() {
		return productPriceBOLD;
	}

    public void setProductPriceREGULAR(Font font) {
        productPriceREGULAR = font;
    }

    public Font getProductPriceREGULAR() {
        return productPriceREGULAR;
    }

    private void setProductNameBOLD(Font font) {
        productNameBOLD = font;
    }

    public Font getProductNameBOLD() {
        return productNameBOLD;
    }

	public Font getvFG() {
		return vFG;
	}

	public void setvFG(Font vFG) {
		this.vFG = vFG;
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

	public Font getProductNameREGULAR() {
		return productNameREGULAR;
	}

	public void setProductNameREGULAR(Font fG3) {
		productNameREGULAR = fG3;
	}

	public Font getFG5() {
		return FG5;
	}

	public void setFG5(Font fG5) {
		FG5 = fG5;
	}
}
