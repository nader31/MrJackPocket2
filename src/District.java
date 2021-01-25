import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class District
{

    // Attributes

    public int mNumber;
    private String mOrientation;
    public final BoardCharacter mCharacter;
    private String mWall;


    // Constructor

    public District(BoardCharacter character,int number)
    {
        this.mOrientation = randomOrientationDistrict();
        this.mCharacter = character;
        this.mWall = orientationWall();
        this.mNumber = number;
    }


    // Getter et Setter

    public String getmOrientation()
    {
        return this.mOrientation;
    }
    public void setmOrientation(String orientation)
    {
        this.mOrientation = orientation;
    }

    public BoardCharacter getmCharacter()
    {
        return this.mCharacter;
    }

    public int getmNumber()
    {
        return this.mNumber;
    }
    public void setmNumber(int number)
    {
        this.mNumber = number;
    }

    public String getmWall()
    {
        return this.mWall;
    }
    public void setmWall(String wall)
    {
        this.mWall = wall;
    }


    // Methods

    // Défini l'orientation du district aléatoirement
    public String randomOrientationDistrict()
    {
        Random rand = new Random();
        List<String> listOrientation = new ArrayList<String>();
        listOrientation .add("SUD");
        listOrientation .add("EST");
        listOrientation .add("OUEST");
        listOrientation .add("NORD");

        int randomIndex = rand.nextInt(listOrientation.size()-1);
        return listOrientation.get(randomIndex);
    }

    // Défini l'orientation des murs en fonction de l'orientation du district
    public String orientationWall()
    {
        switch (mOrientation) {
            case "SUD" -> mWall = "NORD";
            case "NORD" -> mWall = "SUD";
            case "EST" -> mWall = "OUEST";
            case "OUEST" -> mWall = "EST";
        }
        return mWall;
    }
}
