package G_Package;

import java.awt.Color;

public class customColorPallete {
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

  public static Color light_orange = new Color(255,152, 73);
  public static Color medyo_black = new Color(65, 66, 58);
  
  public customColorPallete() {
      setTitleColor(Color.WHITE);
      setSideTitle(Color.LIGHT_GRAY);
      setSearch(new Color(243, 244, 248)); 	// 100
      setRightSide(new Color(236, 226, 210)); 	// 200
      setChoice(new Color(255, 152, 73));	// 300
      setOrderPane(new Color(255, 152, 73)); 	// 400
      setInactiveButton(new Color(189, 148, 116)); 	// 500
      setCenterPiece(new Color(255, 152, 73)); 	// 600
      setLeftSide(new Color(236, 226, 210)); 	// 700
      setHeader(new Color(217, 100, 30)); 	// 800
      setCenterPane(new Color(235, 235, 235)); 	// 900
      
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
