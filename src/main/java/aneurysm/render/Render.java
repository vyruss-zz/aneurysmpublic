package aneurysm.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import aneurysm.ui.DataLists;
import aneurysm.ui.Window;

public class Render {

	private Window host;
	
	public Render(Window w) {
		host = w;
	}
	
	public void drawHighlighted(Graphics g, ImageObserver io) {
		g.setColor(Color.yellow);
		if(RenderControls.isItemSelected()) g.setColor(Color.CYAN);
		int hx = RenderControls.getHighlightedX();
		int hy = RenderControls.getHighlightedY();
		int hw = RenderControls.getHighlightedWidth();
		int hh = RenderControls.getHighlightedHeight();
		if(RenderControls.isRot90()) {
			int t = hy;
			hy = -hx;
			hx = t;
			t = hh;
			hh = -hw;
			hw = t;
		}
		if (RenderControls.isVertsMode()) {
			g.drawRect(
					((hx - (48)) - RenderControls.getCameraXOffset())
							/ RenderControls.getZoomLevel(),
					((hy - (48)) - RenderControls.getCameraYOffset())
							/ RenderControls.getZoomLevel(),
					96 / (RenderControls.getZoomLevel()), 96 / (RenderControls.getZoomLevel()));
		}
		if (RenderControls.isLinesMode()) {
			g.drawLine(
					(hx - RenderControls.getCameraXOffset()
							- (RenderControls.getZoomLevel())) / RenderControls.getZoomLevel(),
					(hy - RenderControls.getCameraYOffset()
							- (RenderControls.getZoomLevel())) / RenderControls.getZoomLevel(),
					(hw - RenderControls.getCameraXOffset()
							- (RenderControls.getZoomLevel())) / RenderControls.getZoomLevel(),
					(hh - RenderControls.getCameraYOffset()
							- (RenderControls.getZoomLevel())) / RenderControls.getZoomLevel());
			g.drawLine(
					(hx - RenderControls.getCameraXOffset()
							+ (RenderControls.getZoomLevel())) / RenderControls.getZoomLevel(),
					(hy - RenderControls.getCameraYOffset()
							+ (RenderControls.getZoomLevel())) / RenderControls.getZoomLevel(),
					(hw - RenderControls.getCameraXOffset()
							+ (RenderControls.getZoomLevel())) / RenderControls.getZoomLevel(),
					(hh - RenderControls.getCameraYOffset()
							+ (RenderControls.getZoomLevel())) / RenderControls.getZoomLevel());
		}
		if (RenderControls.isThingsMode()) {
			int w = DataLists.getObjectImage(Integer.parseInt(RenderControls.getHighlightedInfo())).getWidth(io);
			int h = DataLists.getObjectImage(Integer.parseInt(RenderControls.getHighlightedInfo())).getHeight(io);
			int scaleW = w * 2 / ((RenderControls.getZoomLevel() / 2) + 1);
			int scaleH = h * 2 / ((RenderControls.getZoomLevel() / 2) + 1);
			g.drawRect(
					((((hx - (scaleW / 2) * RenderControls.getZoomLevel()))
							- RenderControls.getCameraXOffset()) / RenderControls.getZoomLevel()),
					((((hy - (scaleH / 2) * RenderControls.getZoomLevel())
							- RenderControls.getCameraYOffset())) / RenderControls.getZoomLevel()),
					((scaleW * RenderControls.getZoomLevel()) + scaleW / 2 / RenderControls.getZoomLevel())
							/ RenderControls.getZoomLevel(),
					((scaleH * RenderControls.getZoomLevel()) + scaleH / 2 / RenderControls.getZoomLevel())
							/ RenderControls.getZoomLevel());
		}
	}

	public void drawLines(Graphics g) {
		if (RenderControls.isLinesMode())
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.gray);
		for (int i = 0; i < DataLists.getLines().size(); i++) {
			int sx = DataLists.getLines().get(i).getStartV().getX();
			int sy = DataLists.getLines().get(i).getStartV().getY();
			int ex = DataLists.getLines().get(i).getEndV().getX();
			int ey = DataLists.getLines().get(i).getEndV().getY();
			if (RenderControls.isRot90()) {
				int t = sy;
				sy = -sx;
				sx = t;
				t = ey;
				ey = -ex;
				ex = t;
			}
			g.drawLine((((sx) - RenderControls.getCameraXOffset()) / RenderControls.getZoomLevel()),
					(((sy) - RenderControls.getCameraYOffset()) / RenderControls.getZoomLevel()),
					((ex) - RenderControls.getCameraXOffset()) / RenderControls.getZoomLevel(),
					(((ey) - RenderControls.getCameraYOffset()) / RenderControls.getZoomLevel()));
		}
	}

	public void drawVertices(Graphics g) {
		if (RenderControls.isVertsMode())
			g.setColor(Color.GREEN);
		if (RenderControls.isLinesMode())
			g.setColor(Color.GRAY);
		for (int i = 0; i < DataLists.getVertices().size(); i++) {
			int x = DataLists.getVertices().get(i).getX();
			int y = DataLists.getVertices().get(i).getY();
			if (RenderControls.isRot90()) {
				int t = y;
				y = -x;
				x = t;
			}
			g.fillRect((((x) - RenderControls.getCameraXOffset()) / RenderControls.getZoomLevel()) - 1,
					(((y) - RenderControls.getCameraYOffset()) / RenderControls.getZoomLevel()) - 1, 3, 3);
		}
	}

	public void drawGrid(Graphics g, int width, int height) {
		if (RenderControls.getGridIntensity() > 0) {

			g.setColor(new Color(41, 50, 156));

			for (int i = RenderControls.getGridStartX(); i < RenderControls.getGridStartX() + 20000; i += 2
					* RenderControls.getGridIntensity()) {
				g.drawLine(((i - RenderControls.getCameraXOffset()) / RenderControls.getZoomLevel()), 0,
						((i - RenderControls.getCameraXOffset()) / RenderControls.getZoomLevel()), height);
			}
			for (int i = RenderControls.getGridStartX(); i > RenderControls.getGridStartX() - 20000; i -= 2
					* RenderControls.getGridIntensity()) {
				g.drawLine(((i - RenderControls.getCameraXOffset()) / RenderControls.getZoomLevel()), 0,
						((i - RenderControls.getCameraXOffset()) / RenderControls.getZoomLevel()), height);
			}
			for (int i = RenderControls.getGridStartY(); i < RenderControls.getGridStartY() + 20000; i += 2
					* RenderControls.getGridIntensity()) {
				g.drawLine(0, ((i - RenderControls.getCameraYOffset()) / RenderControls.getZoomLevel()), width,
						((i - RenderControls.getCameraYOffset()) / RenderControls.getZoomLevel()));
			}
			for (int i = RenderControls.getGridStartY(); i > RenderControls.getGridStartY() - 20000; i -= 2
					* RenderControls.getGridIntensity()) {
				g.drawLine(0, ((i - RenderControls.getCameraYOffset()) / RenderControls.getZoomLevel()), width,
						((i - RenderControls.getCameraYOffset()) / RenderControls.getZoomLevel()));
			}
		}
	}

	public void drawThings(Graphics g, ImageObserver io) {
		int sprX = 0;
		int sprY = 0;
		Image currentSprite = null;
		for (int i = 0; i < DataLists.getObjects().size(); i++) {
			currentSprite = DataLists.getObjectImage(DataLists.getObjects().get(i).getThingID());
			sprX = DataLists.getObjects().get(i).getX();
			sprY = DataLists.getObjects().get(i).getY();
			if (RenderControls.isRot90()) {
				int t = sprX;
				sprX = sprY;
				sprY = -t;
			}
			if (RenderControls.isThingsMode()) {

				int sprW = currentSprite.getWidth(io);
				int sprH = currentSprite.getHeight(io);
				int scaleW = sprW * 2 / ((RenderControls.getZoomLevel() / 2) + 1);
				int scaleH = sprH * 2 / ((RenderControls.getZoomLevel() / 2) + 1);
				currentSprite = currentSprite.getScaledInstance(scaleW, scaleH, Image.SCALE_SMOOTH);
				g.drawImage(currentSprite,
						((((sprX - (scaleW / 2) * RenderControls.getZoomLevel())) - RenderControls.getCameraXOffset())
								/ RenderControls.getZoomLevel()),
						((((sprY - (scaleH / 2) * RenderControls.getZoomLevel()) - RenderControls.getCameraYOffset()))
								/ RenderControls.getZoomLevel()),
						io);
				g.setColor(Color.CYAN);
				g.fillRect((((sprX - 3) - RenderControls.getCameraXOffset()) / RenderControls.getZoomLevel()),
						((sprY - 3) - RenderControls.getCameraYOffset()) / RenderControls.getZoomLevel(), 6, 6);
			} else {
				g.setColor(Color.RED);
				g.fillRect((((sprX - 1) - RenderControls.getCameraXOffset()) / RenderControls.getZoomLevel()),
						((sprY - 1) - RenderControls.getCameraYOffset()) / RenderControls.getZoomLevel(), 2, 2);
			}
		}
	}
}