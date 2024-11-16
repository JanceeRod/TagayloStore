package G_Package;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class customScrollBarUI extends BasicScrollBarUI {
	customColorPallete color = new customColorPallete();
	
	private Color trackColor = Color.DARK_GRAY;
	private Color thumbColor = Color.GRAY;
	private Color backgroundColor;
	
	public void CustomScrollBarUI(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public void setCustomUI(Color thumbColor, Color trackColor, Color backgroundColor) {
		this.thumbColor = thumbColor;
		this.trackColor = trackColor;
		this.backgroundColor = backgroundColor;
		repaintScrollBar();
	}
	
	public Color getThumbColor() {
		return thumbColor;
	}
	
	public void setThumbColor(Color thumbColor) {
		this.thumbColor = thumbColor;
		repaintScrollBar();
	}
	
	public Color getTrackColor() {
		return trackColor;
	}
	
	public void setTrackColor(Color trackColor) {
		this.trackColor = trackColor;
		repaintScrollBar();
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public Color setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		repaintScrollBar();
		return backgroundColor;
	}
	
	@Override
	protected JButton createDecreaseButton(int orientation) {
		return createZeroButton();
	}
	
	@Override
	protected JButton createIncreaseButton(int orientation) {
		return createZeroButton();
	}
	
	private JButton createZeroButton() {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(0, 0));
		button.setMinimumSize(new Dimension(0, 0));
		button.setMaximumSize(new Dimension(0, 0));
		return button;
	}
	
	private void repaintScrollBar() {
		if (scrollbar != null) {
            scrollbar.repaint();
        }
	}
	
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(thumbColor);
		
		g2.setBackground(backgroundColor);
		g2.clearRect(0, 0, c.getWidth(), c.getHeight());
		
		if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
			int thumbWidth = 5;
			int thumbHeight = thumbBounds.height;
			int x = thumbBounds.x + (thumbBounds.width - thumbWidth) / 2;
			int y = thumbBounds.y;
			
			g2.fillRoundRect(x, y, thumbWidth, thumbHeight, 5, 5);
		} else {
			int thumbWidth = 5;
			int thumbHeight = thumbBounds.height - 1;
			int x = thumbBounds.x;
			int y = thumbBounds.y + (thumbBounds.height - thumbHeight) / 2;
			
			g2.fillRoundRect(x, y, thumbWidth, thumbHeight, 5, 5);
		}
		g2.dispose();
	}
	
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		g.setColor(trackColor);
		if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
			int x = (trackBounds.width - 1) / 2;
			g.fillRect(x, trackBounds.y, 1, trackBounds.height);
		} else {
			int y = (trackBounds.height - 1) / 2;
			g.fillRect(trackBounds.x, y, trackBounds.width, 1);
		}
		
		g.setColor(backgroundColor);
		if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
			g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
		} else {
			g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
		}
	}
}
