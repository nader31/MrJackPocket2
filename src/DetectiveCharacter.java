public class DetectiveCharacter
{
    // Attributes

    private String mName;
    private String mPosition;


    // Constructor

    public DetectiveCharacter(String name)
    {
        this.mPosition = "position";
        this.mName = name;
    }


    // Getter et Setter

    public String getmName()
    {
        return this.mName;
    }

    public String getmPosition()
    {
        return this.mPosition;
    }
    public void setmPosition(String position)
    {
        this.mPosition = position;
    }


    // Methods

    public static String toString(char column, int row)
    {
        return Character.toString(column) + row;
    }

 }
