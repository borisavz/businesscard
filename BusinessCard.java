package businesscard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BusinessCard {
    public static enum Position {
        UP_RIGHT_CORNER, UP_LEFT_CORNER, DOWN_RIGHT_CORNER,
        DOWN_LEFT_CORNER, CENTER
    }
    private String name, phoneNumber, address, contact;
    private String[] description = new String[20];
    private Color background = Color.WHITE, foreground = Color.BLACK, 
        infoColor = foreground, descriptionColor = foreground;
    private BufferedImage bufferedImage, logo;
    private Graphics graphics;
    private int positionY = 50, positionX = 30, width = 1000, height = 550,
        logoX1, logoY1, infoFontSize = 30, infoFontStyle = Font.PLAIN, 
        descriptionFontSize = 30, descriptionFontStyle = Font.PLAIN;
    private String infoFontName = Font.SANS_SERIF, descriptionFontName = Font.SANS_SERIF;
    private boolean infoColorChanged = false, descriptionColorChanged = false;
    public BusinessCard(int width, int height) {
        setWidth(width);
        setHeight(height);
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
        graphics.setFont(new Font(infoFontName, infoFontStyle, infoFontSize));
        graphics.setColor(background);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(foreground);
        graphics.drawRect(10, 10, width - 20, height - 20);
        graphics.setColor(infoColor);
        drawString("", name, infoFontSize);
        drawString("Address: ", address, infoFontSize);
        drawString("Phone number: ", phoneNumber, infoFontSize);
        drawString("Contact: ", contact, infoFontSize);
        graphics.setColor(foreground);
        graphics.drawLine(30, positionY - 5, width - 30, positionY - 5);
        positionY += infoFontSize - 10;
        graphics.setFont(new Font(descriptionFontName, descriptionFontStyle, descriptionFontSize));
        graphics.setColor(descriptionColor);
        for(String description1 : description)
            drawString("", description1, descriptionFontSize);
        if(logo != null)
            graphics.drawImage(logo, getLogoX(), getLogoY(), null);
    }
    public void save(String path) throws IOException {
        applyChanges();
        if(!path.endsWith(".png"))
            path += ".png";
        ImageIO.write(bufferedImage, "png", new File(path));
    }
    private void drawString(String prefix, String stringToDraw, int fontSize) {
        if(stringToDraw != null && !stringToDraw.isEmpty()) {
            graphics.drawString(prefix + stringToDraw, positionX, positionY);
            positionY += fontSize + 5;
        }
    }
    public void addLogo(String path) throws IOException {
        addLogo(path, Position.DOWN_LEFT_CORNER);
    }
    public void addLogo(String path, Position position) throws IOException {
        logo = ImageIO.read(new File(path));
        setLogoPosition(position);
    }
    public void addLogo(String path, int x, int y) throws IOException {
        logo = ImageIO.read(new File(path));
        setLogoX(x);
        setLogoY(y);
    }
    public void removeLogo() {
        logo = null;
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
        if(!infoColorChanged)
            infoColor = foreground;
        if(!descriptionColorChanged)
            descriptionColor = foreground;
    }
    public Color getInfoColor() {
        return infoColor;
    }
    public void setInfoColor(Color infoColor) {
        this.infoColor = infoColor;
        infoColorChanged = true;
    }
    public Color getDescriptionColor() {
        return descriptionColor;
    }
    public void setDescriptionColor(Color descriptionColor) {
        this.descriptionColor = descriptionColor;
        descriptionColorChanged = true;
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
    public Point getLogoPosition() {
        return new Point(getLogoX(), getLogoY());
    }
    public void setLogoPosition(int x, int y) {
        setLogoX(x);
        setLogoY(y);
    }
    public void setLogoPosition(Position position) {
        switch(position) {
            case UP_RIGHT_CORNER:
                setLogoPosition(getWidth() - logo.getWidth(), 0);
                break;
            case UP_LEFT_CORNER:
                setLogoPosition(0, 0);
                break;
            case DOWN_RIGHT_CORNER:
                setLogoPosition(getWidth() - logo.getWidth(), getHeight() - logo.getHeight());
                break;
            case DOWN_LEFT_CORNER:
                setLogoPosition(0, getHeight() - logo.getHeight());
                break;
            case CENTER:
                setLogoPosition(getWidth() / 2, getHeight() / 2);
                break;
        }
    }
    public int getLogoX() {
        return logoX1;
    }
    public void setLogoX(int logoX1) {
        this.logoX1 = logoX1;
    }
    public int getLogoY() {
        return logoY1;
    }
    public void setLogoY(int logoY1) {
        this.logoY1 = logoY1;
    }
    public Font getInfoFont() {
        return new Font(infoFontName, infoFontStyle, infoFontSize);
    }
    public void setInfoFont(Font infoFont) {
        infoFontName = infoFont.getName();
        infoFontStyle = infoFont.getStyle();
        infoFontSize = infoFont.getSize();
    }
    public String getInfoFontName() {
        return infoFontName;
    }
    public void setInfoFontName(String infoFontName) {
        this.infoFontName = infoFontName;
    }
    public int getInfoFontSize() {
        return infoFontSize;
    }
    public void setInfoFontSize(int infoFontSize) {
        this.infoFontSize = infoFontSize;
    }
    public int getInfoFontStyle() {
        return infoFontStyle;
    }
    public void setInfoFontStyle(int infoFontStyle) {
        this.infoFontStyle = infoFontStyle;
    }
    public Font getDescriptionFont() {
        return new Font(descriptionFontName, descriptionFontStyle, descriptionFontSize);
    }
    public void setDescriptionFont(Font descriptionFont) {
        descriptionFontName = descriptionFont.getName();
        descriptionFontStyle = descriptionFont.getStyle();
        descriptionFontSize = descriptionFont.getSize();
    }
    public String getDescriptionFontName() {
        return descriptionFontName;
    }
    public void setDescriptionFontName(String descriptionFontName) {
        this.descriptionFontName = descriptionFontName;
    }
    public int getDescriptionFontSize() {
        return descriptionFontSize;
    }
    public void setDescriptionFontSize(int descriptionFontSize) {
        this.descriptionFontSize = descriptionFontSize;
    }
    public int getDescriptionFontStyle() {
        return descriptionFontStyle;
    }
    public void setDescriptionFontStyle(int descriptionFontStyle) {
        this.descriptionFontStyle = descriptionFontStyle;
    }
}