package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Create A Icon
 * @author NobIsh
 * create 16x16 Icon from java
 */
public class IconMaker {
	static void draw(Graphics g, String s){
		g.setColor(Color.black);
		if(s.equalsIgnoreCase("exit")){
			g.drawArc(1, 1, 13, 13, 125, 290);
			g.drawLine(7, 0, 7, 8);
		}else if(s.equalsIgnoreCase("paste")){
			g.drawRect(0, 0, 10, 12);
			g.fillRect(5, 3, 10, 12);
		}else if(s.equalsIgnoreCase("expand")){
			g.drawLine(7, 2, 7, 11);
			g.drawLine(2, 7, 11, 7);
		}else if(s.equalsIgnoreCase("clear")){
			g.drawLine(2, 2, 11, 11);
			g.drawLine(2, 11, 11, 2);
		}else if(s.equalsIgnoreCase("prepare")){
			g.drawRect(0, 5, 8, 8);
			Polygon p = new Polygon();
			p.addPoint(4, 8);
			p.addPoint(1, 8);
			p.addPoint(5, 15);
			p.addPoint(15, 0);
			p.addPoint(5, 12);
			g.fillPolygon(p);
		}else if(s.equalsIgnoreCase("start")){
			Polygon p = new Polygon();
			p.addPoint(0, 15);
			p.addPoint(0, 0);
			p.addPoint(15, 7);
			g.fillPolygon(p);
		}else if(s.equalsIgnoreCase("next")){
			Polygon p = new Polygon();
			p.addPoint(0, 15);
			p.addPoint(0, 0);
			p.addPoint(15, 7);
			g.drawPolygon(p);
		}else if(s.equalsIgnoreCase("prev")){
			Polygon p = new Polygon();
			p.addPoint(15, 15);
			p.addPoint(15, 0);
			p.addPoint(0, 7);
			g.drawPolygon(p);
		}else if(s.equalsIgnoreCase("pause")){
			g.fillRect(1, 2, 4, 12);
			g.fillRect(7, 2, 4, 12);
		}else if(s.equalsIgnoreCase("stop")){
			g.fillRect(1, 3, 10, 10);
		}else if(s.equalsIgnoreCase("save")){
			Polygon p = new Polygon();
			p.addPoint(0, 0);
			p.addPoint(12, 0);
			p.addPoint(15, 3);
			p.addPoint(15, 15);
			p.addPoint(0, 15);
			g.drawPolygon(p);
			g.drawRect(4, 0, 6, 4);
			g.drawRect(3, 7, 9, 6);
		}else{
			g.drawRect(0, 0, 15, 15);
			g.drawRect(5, 5, 5, 5);
		}
	}
	BufferedImage IconsImage;
	IconMaker(){
		try {
			IconsImage = ImageIO.read(new URL("https://www.smashingmagazine.com/images/icons-4/97.jpg"));
		} catch (IOException e) {
		}
	}
	/**
	 * create Icon(size be defined) From KeyWord
	 * @param str key-word like "Open", "Save", "Start", "Stop" , etc
	 * @param size Icon size (Icon is square)
	 * @return iconImage
	 */
	static public Image createIcon(String str, int size){
		BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		draw(g, str);
		return bi;
	}
	/**
	 * create DefaultIcon(16x16, for menu) From KeyWord
	 * @param str key-word like "Open", "Save", "Start", "Stop" , etc
	 * @return iconImage
	 */
	static public Image createIcon(String str){
		return createIcon(str, 16);
	}
	public static BufferedImage makeQR(String source, int size) throws WriterException{
		//http://qiita.com/tool-taro/items/73d8e63b7a1ccc10ed3c
        String encoding = "UTF-8";
        ConcurrentHashMap hints = new ConcurrentHashMap();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, encoding);
        hints.put(EncodeHintType.MARGIN, 0);
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(source, BarcodeFormat.QR_CODE, size, size, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return image;
	}
}
