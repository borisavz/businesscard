package businesscard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BusinessCard {
    private String name, phoneNumber, address, contact;
    private String[] description = new String[20];
    private Color background = Color.WHITE, foreground = Color.BLACK;
    private BufferedImage bufferedImage;
    private Graphics graphics;
    private int positionY = 50, positionX = 30, width = 1000, height = 550, 
        fontSize = 30, fontStyle = Font.PLAIN;
    private String fontName = Font.SANS_SERIF;
    private Font font = new Font(fontName, fontStyle, fontSize);
    public BusinessCard(int width, int height) {
        this.width = width;
        this.height = height;
        refresh();
    }
    public BusinessCard() {
        this(1000, 550);
    }
    public BufferedImage preview() {
        applyChanges();
        return bufferedImage;
    }
    private void refresh() {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = bufferedImage.getGraphics();
    }
    private void applyChanges() {
        refresh();
        graphics.setFont(new Font(Font.SANS_SERIF, fontStyle, fontSize));
        graphics.setColor(background);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(foreground);
        graphics.drawRect(10, 10, width - 20, height - 20);
        drawString("", name);
        drawString("Address: ", address);
        drawString("Phone number: ", phoneNumber);
        drawString("Contact: ", contact);
        graphics.drawLine(30, positionY - 5, width - 30, positionY - 5);
        positionY += fontSize - 10;
        graphics.setFont(new Font(Font.SANS_SERIF, fontStyle, fontSize));
        for(String description1 : description)
            drawString("", description1);
    }
    public void save(String path) throws IOException {
        applyChanges();
        if(!path.endsWith(".png"))
            path += ".png";
        ImageIO.write(bufferedImage, "png", new File(path));
    }
    private void drawString(String prefix, String stringToDraw) {
        if(stringToDraw != null && !stringToDraw.isEmpty()) {
            graphics.drawString(prefix + stringToDraw, positionX, positionY);
            positionY += fontSize + 5;
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String[] getDescription() {
        return description;
    }
    public void setDescription(String[] description) {
        for(int i = 0; i < this.description.length && i < description.length; i++)
            this.description[i] = description[i];
    }
    public Color getBackground() {
        return background;
    }
    public void setBackground(Color background) {
        this.background = background;
    }
    public Color getForeground() {
        return foreground;
    }
    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public Font getFont() {
        return font;
    }
    public String getFontName() {
        return fontName;
    }
    public void setFont(Font font) {
        this.font = font;
        fontName = font.getName();
        fontStyle = font.getStyle();
        fontSize = font.getSize();
    }
    public int getFontSize() {
        return fontSize;
    }
    public void setFontSize(int fontSize) {
        font = new Font(fontName, fontStyle, fontSize);
        this.fontSize = fontSize;
    }
    public int getFontStyle() { 
        return fontStyle;
    }
    public void setFontStyle(int fontStyle) {
        font = new Font(fontName, fontStyle, fontSize);
        this.fontStyle = fontStyle;
    }
}
