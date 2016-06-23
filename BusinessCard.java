package businesscard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BusinessCard {
    public static enum Position {
        UP_RIGHT_CORNER, UP_LEFT_CORNER, DOWN_RIGHT_CORNER,
        DOWN_LEFT_CORNER, CENTER, CUSTOM, DEFAULT
    }
    private String name, phoneNumber, address, contact, infoFontName = Font.SANS_SERIF, 
        descriptionFontName = Font.SANS_SERIF, description[];
    private Color backgroundColor = Color.WHITE, foregroundColor = Color.BLACK, 
        infoColor = foregroundColor, descriptionColor = foregroundColor;
    private BufferedImage businessCard, logo;
    private Graphics graphics;
    private Position defaultLogoPosition = Position.DOWN_LEFT_CORNER, logoPosition = Position.DOWN_LEFT_CORNER;
    private int positionY = 50, positionX = 30, width = 1000, height = 550,
        logoX, logoY, logoWidth, logoHeight, infoFontSize = 30, infoFontStyle = Font.PLAIN, 
        descriptionFontSize = 30, descriptionFontStyle = Font.PLAIN;
    private boolean infoColorChanged = false, descriptionColorChanged = false;
    public BusinessCard(int width, int height) {
        setDimensions(width, height);
    }
    public BusinessCard() {
        this(1000, 550);
    }
    public BufferedImage preview() {
        applyChanges();
        return businessCard;
    }
    private void applyChanges() {
        businessCard = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = businessCard.getGraphics();
        graphics.setFont(new Font(getInfoFontName(), getInfoFontStyle(), getInfoFontSize()));
        graphics.setColor(getBackgroundColor());
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(getForegroundColor());
        graphics.drawRect(10, 10, getWidth() - 20, getHeight() - 20);
        graphics.setColor(getInfoColor());
        drawString("", getName(), getInfoFontSize());
        drawString("Address: ", getAddress(), getInfoFontSize());
        drawString("Phone number: ", getPhoneNumber(), getInfoFontSize());
        drawString("Contact: ", getContact(), getInfoFontSize());
        graphics.setColor(getForegroundColor());
        graphics.drawLine(30, positionY - 5, getWidth() - 30, positionY - 5);
        positionY += infoFontSize - 10;
        graphics.setFont(new Font(getDescriptionFontName(), getDescriptionFontStyle(), getDescriptionFontSize()));
        graphics.setColor(getDescriptionColor());
        for(String description1 : description)
            drawString("", description1, getDescriptionFontSize());
        if(logo != null) {
            positionLogo(getLogoPosition());
            graphics.drawImage(logo, getLogoX(), getLogoY(), getLogoWidth(), getLogoHeight(), null); 
        }
    }
    public void save(String path) throws IOException {
        checkIfMakesSense("Path can't be null!", path == null);
        applyChanges();
        if(!path.endsWith(".png"))
            path += ".png";
        ImageIO.write(businessCard, "png", new File(path));
    }
    private void drawString(String prefix, String stringToDraw, int fontSize) {
        checkIfMakesSense("Text can't be outside of the business card!", positionY > getHeight());
        if(stringToDraw != null) {
            graphics.drawString(prefix + stringToDraw, getIndentation(), positionY);
            positionY += fontSize + 5;
        }
    }
    private void checkIfMakesSense(String message, boolean condition) {
        if(condition)
            throw new IllegalArgumentException(message);
    }
    private void checkIfNull(String message, Object object) {
        if(object == null)
            throw new NullPointerException(message);
    }
    private void loadLogo(String path) throws IOException {
        checkIfMakesSense("Path can't be null!", path == null);
        logo = ImageIO.read(new File(path));
    }
    public void moveAndResizeLogo(int x, int y, int width, int height) {
        setLogoPosition(x, y);
        setLogoDimensions(width, height);
    }
    private void positionLogo(Position logoPosition) {
        switch(logoPosition) {
            case UP_RIGHT_CORNER:
                setLogoPosition(getWidth() - getLogoWidth(), 0);
                break;
            case UP_LEFT_CORNER:
                setLogoPosition(0, 0);
                break;
            case DOWN_RIGHT_CORNER:
                setLogoPosition(getWidth() - getLogoWidth(), getHeight() - getLogoHeight());
                break;
            case DOWN_LEFT_CORNER:
                setLogoPosition(0, getHeight() - getLogoHeight());
                break;
            case CENTER:
                setLogoPosition(getWidth() / 2 - getLogoWidth() / 2, getHeight() / 2 - getLogoHeight() / 2);
                break;
            case DEFAULT:
                positionLogo(getDefaultLogoPosition());
                break;
        }
    }
    public void addLogo(String path) throws IOException {
        addLogo(path, Position.DOWN_LEFT_CORNER);
    }
    public void addLogo(String path, Position position) throws IOException {
        loadLogo(path);
        setLogoDimensions(logo.getWidth(), logo.getHeight());
        setLogoPosition(position);
    }
    public void addLogo(String path, int x, int y) throws IOException {
        loadLogo(path);
        moveAndResizeLogo(x, y, logo.getWidth(), logo.getHeight());
    }
    public void addLogo(String path, int x, int y, int width, int height) throws IOException {
        loadLogo(path);
        moveAndResizeLogo(x, y, width, height);
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
        this.description = description.clone();
    }
    public Color getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public Color getForegroundColor() {
        return foregroundColor;
    }
    public void setForegroundColor(Color foregroundColor) {
        checkIfMakesSense("Foreground color can't be null!", foregroundColor == null);
        this.foregroundColor = foregroundColor;
        if(!infoColorChanged)
            infoColor = foregroundColor;
        if(!descriptionColorChanged)
            descriptionColor = foregroundColor;
    }
    public Color getInfoColor() {
        return infoColor;
    }
    public void setInfoColor(Color infoColor) {
        checkIfMakesSense("Info color can't be null!", infoColor == null);
        this.infoColor = infoColor;
        infoColorChanged = true;
    }
    public Color getDescriptionColor() {
        return descriptionColor;
    }
    public void setDescriptionColor(Color descriptionColor) {
        checkIfMakesSense("Description color can't be null!", descriptionColor == null);
        this.descriptionColor = descriptionColor;
        descriptionColorChanged = true;
    }
    public int getIndentation() {
        return positionX;
    }
    public void setIndentation(int indentation) {
        checkIfMakesSense("Text can't be outside of the business card!", indentation > getWidth() || indentation < 0);
        positionX = indentation;
    }
    public int getWidth() {
        return width;
    }
    public void setDimensions(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
    public Dimension getDimensions() {
        return new Dimension(getWidth(), getHeight());
    }
    public void setWidth(int width) {
        checkIfMakesSense("Width can't be zero or negative!", width <= 0);
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        checkIfMakesSense("Height can't be zero or negative!", height <= 0);
        this.height = height;
    }
    public Position getDefaultLogoPosition() {
        return defaultLogoPosition;
    }
    public void setDefaultLogoPosition(Position defaultLogoPosition) {
        this.defaultLogoPosition = defaultLogoPosition == null || defaultLogoPosition == Position.DEFAULT ? 
            Position.DOWN_LEFT_CORNER : defaultLogoPosition;
    }
    public Position getLogoPosition() {
        return logoPosition;
    }
    public void setLogoPosition(int x, int y) {
        checkIfNull("Logo is null!", logo);
        setLogoX(x);
        setLogoY(y);
    }
    public void setLogoPosition(Position logoPosition) {
        this.logoPosition = logoPosition == null || logoPosition == Position.DEFAULT ?
            getDefaultLogoPosition() : logoPosition;
    }
    public int getLogoX() {
        checkIfNull("Logo is null!", logo);
        return logoX;
    }
    public void setLogoX(int logoX) {
        setLogoPosition(Position.CUSTOM);
        checkIfMakesSense("Logo can't be outside of the business card!", 
            logoX > getWidth() || logoX + getLogoWidth() < 0);
        this.logoX = logoX;
    }
    public int getLogoY() {
        checkIfNull("Logo is null!", logo);
        return logoY;
    }
    public void setLogoY(int logoY) {
        checkIfMakesSense("Logo can't be outside of the business card!", 
            logoY > getWidth() || logoY + getLogoHeight() < 0);
        this.logoY = logoY;
    }
    public int getLogoWidth() {
        checkIfNull("Logo is null!", logo);
        return logoWidth;
    }
    public void setLogoDimensions(int width, int height) {
        setLogoWidth(width);
        setLogoHeight(height);
    }
    public void setLogoWidth(int logoWidth) {
        checkIfNull("Logo is null!", logo);
        checkIfMakesSense("Logo width can't be zero or negative!", logoWidth <= 0);
        this.logoWidth = logoWidth;
    }
    public int getLogoHeight() {
        checkIfNull("Logo is null!", logo);
        return logoHeight;
    }
    public void setLogoHeight(int logoHeight) {
        checkIfNull("Logo is null!", logo);
        checkIfMakesSense("Logo width can't be zero or negative!", logoHeight <= 0);
        this.logoHeight = logoHeight;
    }
    public Font getInfoFont() {
        return new Font(infoFontName, infoFontStyle, infoFontSize);
    }
    public void setInfoFont(Font infoFont) {
        checkIfMakesSense("Font can't be null!", infoFont == null);
        setInfoFontName(infoFont.getName());
        setInfoFontStyle(infoFont.getStyle());
        setInfoFontSize(infoFont.getSize());
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
        checkIfMakesSense("Font size can't be zero or negative!", infoFontSize == 0);
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
        checkIfMakesSense("Font can't be null!", descriptionFont == null);
        setDescriptionFontName(descriptionFont.getName());
        setDescriptionFontStyle(descriptionFont.getStyle());
        setDescriptionFontSize(descriptionFont.getSize());
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
        checkIfMakesSense("Font size can't be zero or negative!", descriptionFontSize <= 0);
        this.descriptionFontSize = descriptionFontSize;
    }
    public int getDescriptionFontStyle() {
        return descriptionFontStyle;
    }
    public void setDescriptionFontStyle(int descriptionFontStyle) {
        this.descriptionFontStyle = descriptionFontStyle;
    }
}