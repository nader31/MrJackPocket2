import java.util.Scanner;

public class Investigator
{
    // Attributes

    protected static BoardCharacter mAlibiCard;
    protected static String mName;
    protected static String mRole;


    // Constructor

    public Investigator()
    {
        mRole = "Enquêteur";
        mName = "player";
    }


    // Methods

    // Défini qui est Enquêteur.
    public void choosePlayerInvestigator() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Qui veut être Enquêteur ?");
        mName = sc1.nextLine();
    }

}
