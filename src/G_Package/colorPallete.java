package G_Package;

import java.awt.Color;

public class colorPallete {
//  protected Color PC1;
//  protected Color PC2;
//  protected Color PC3;
//  protected Color PC4;
//  protected Color PC5;
//  protected Color PC6;
//  protected Color forHover;

  //new Pallete
  private Color search; //249 249 250   - secondary panel color     - SP
  private Color rightSide; //236 238 239   - primary panel color       - PP
  private Color leftSide; //222 225 227   - tertiary panel color      - TP
  private Color orderPane; //207 211 215   - hover color               - HC
  private Color inactiveButton; //191 197 201   - inactive buttons          - IB
  private Color centerPiece; //173 180 185   - side panel                - SPC
  private Color choice; //152 161 168   - secondary font color      - SFC
  protected Color gray_7; //127 139 147   - tertiary font color       - TFC 
  private Color centerPane; //96 110 121    - primary font color        - PFC
  private Color header;
  private Color titleColor;
  private Color sideTitle; 
  
  public colorPallete() {
//      PC1 = new Color(245,245,245);
//      PC2 = new Color(192,192,192);
//      PC3 = new Color(128,128,128);
//      PC4 = new Color(220,220,220);
//      PC5 = new Color(211,211,211);
//      PC6 = new Color(255,228,225);
//      forHover = new Color(205,205,205);

//  	header = new Color(79, 94, 232);
//  	header = Color.BLACK;
  	  setTitleColor(Color.WHITE);
  	  setSideTitle(Color.LIGHT_GRAY);
      setSearch(new Color(243, 244, 248)); 	// 100
      setRightSide(new Color(210, 212, 218)); 	// 200
      setChoice(new Color(179, 181, 189));	// 300 
      setOrderPane(new Color(148, 150, 161)); 	// 400
      setInactiveButton(new Color(119, 121, 134)); 	// 500
      setCenterPiece(new Color(91, 93, 107)); 	// 600
      setLeftSide(new Color(64, 66, 82)); 	// 700
      setHeader(new Color(40, 42, 58)); 	// 800
      setCenterPane(new Color(16, 18, 35)); 	// 900
      
  }

public Color getHeader() {
	return header;
}

public void setHeader(Color header) {
	this.header = header;
}

public Color getChoice() {
	return choice;
}

public void setChoice(Color choice) {
	this.choice = choice;
}

public Color getLeftSide() {
	return leftSide;
}

public void setLeftSide(Color leftSide) {
	this.leftSide = leftSide;
}

public Color getRightSide() {
	return rightSide;
}

public void setRightSide(Color rightSide) {
	this.rightSide = rightSide;
}

public Color getTitleColor() {
	return titleColor;
}

public void setTitleColor(Color titleColor) {
	this.titleColor = titleColor;
}

public Color getSearch() {
	return search;
}

public void setSearch(Color search) {
	this.search = search;
}

public Color getSideTitle() {
	return sideTitle;
}

public void setSideTitle(Color sideTitle) {
	this.sideTitle = sideTitle;
}

public Color getCenterPiece() {
	return centerPiece;
}

public void setCenterPiece(Color centerPiece) {
	this.centerPiece = centerPiece;
}

public Color getOrderPane() {
	return orderPane;
}

public void setOrderPane(Color orderPane) {
	this.orderPane = orderPane;
}

public Color getInactiveButton() {
	return inactiveButton;
}

public void setInactiveButton(Color inactiveButton) {
	this.inactiveButton = inactiveButton;
}

public Color getCenterPane() {
	return centerPane;
}

public void setCenterPane(Color centerPane) {
	this.centerPane = centerPane;
}
}
