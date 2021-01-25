import java.util.Scanner;

public class MrJack
{
    // Attributes

    protected static String mRole;
    protected static String mName;
    protected static int mNbHourglass;
    private int mNbAlibiCard;


    // Constructor

    public MrJack()
    {
        mName = "player";
        mNbHourglass = 0;
        mRole = "Mr Jack";
    }


    // Methods

    // Défini qui est Mr Jack.
    public void choosePlayerMrJack()  {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Qui veut être Mr Jack ?");
        mName = sc1.nextLine();
    }

}
