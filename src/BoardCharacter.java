import javax.swing.*;
import java.awt.*;

public class BoardCharacter
{
    // Attributes

    private ImageIcon mImage;
    private String mStatus;
    private String mColor;
    private int mNbHourglass;
    private boolean mRecto;

    // Constructor

    public BoardCharacter(String color, int nbHourglass, ImageIcon image)
    {
        this.mColor = color;
        this.mStatus = "Rien";
        this.mNbHourglass = nbHourglass;
        this.mRecto = true;
        this.mImage = image;
    }

    // Getter et Setter


    public ImageIcon getmImage() {
        return this.mImage;
    }

    public String getmStatus()
    {
        return this.mStatus;
    }
    public void setmStatus(String status)
    {
        this.mStatus = status;
    }

    public String getmColor()
    {
        return this.mColor;
    }
    public void setmColor(String color)
    {
        this.mColor = color;
    }

    public int getmNbHourglass()
    {
        return this.mNbHourglass;
    }

    public boolean getmRecto()
    {
        return this.mRecto;
    }
    public void setmRecto(boolean recto)
    {
        this.mRecto = recto;
    }

}
