import javax.swing.*;
import java.util.*;

public class Game {

    // Attributes

    public static ArrayList<BoardCharacter> mList;
    private static DetectiveCharacter[] mTableauDetective;
    private District[][] mBoard;
    private final String[] mListPosition = {"b1", "c1", "d1", "e2", "e3", "e4", "d5", "c5", "b5", "a4", "a3", "a2"};
    private ArrayList<String> mSousListAction1;
    private ArrayList<String> mSousListAction2;
    private District mDistrictPivote;


    // Methods

    // Fonction qui décrit le déroulement du jeu.
    public void play() {

        int turn = 1;
        int sousTurn = 1;
        String move;
        System.out.println("Bienvenue sur Mr Jack Pocket, bonne chance !");
        creationPlayers();
        creationAlibiCard();
        creationDetective();
        boardDistrictInitialization(mList);
        positionDetectiveInitialization(mTableauDetective);
        Graphics.InitialisationPanel();
        System.out.println(" ");

        while (turn < 9) {
            System.out.println("----------- TOUR " + turn + " -----------");
            if (turn % 2 != 0)
                throwToken();
            mDistrictPivote = null;
            while (sousTurn <= 4) {
                System.out.println("----------- Sous-tour " + sousTurn + " -----------");
                printWhoTurn(turn, sousTurn);
                chooseActionPlayer(chooseListAction(turn), whoTurn(turn, sousTurn));
                turnOverDistrict();
                updateBoardDistrict();
                updatePositionDetective(mTableauDetective);
                sousTurn++;
            }
            if (!whoISee()) {
                MrJack.mNbHourglass += 1;
                System.out.println("Mr Jack a gagné un sablier.");
            }
            turnOverDistrict();
            updateBoardDistrict();
            updatePositionDetective(mTableauDetective);
            int cpt = 9;
            if (MrJack.mNbHourglass == 6 || turn == 8)
                isGameOver("Enquêteur");
            turn++;
            sousTurn = 1;
            for (int i = 0; i < mBoard.length; i++)
                for (int j = 0; j < mBoard[i].length; j++)
                    if (!mBoard[i][j].getmCharacter().getmRecto())
                        cpt -= 1;
            if (cpt < 2)
                isGameOver("Mr Jack");
        }
    }

    // Création des deux joueurs et affectation de Mr Jack et Enquêteur.
    public static void creationPlayers() {
        MrJack mrJack = new MrJack();
        Investigator investigator = new Investigator();
        mrJack.choosePlayerMrJack();
        investigator.choosePlayerInvestigator();
    }





    // Création des personnages.
    public void creationAlibiCard() {

        //Création des images
        ImageIcon noir = new ImageIcon("noir.png");
        ImageIcon orange = new ImageIcon("orange.png");
        ImageIcon jaune = new ImageIcon("jaune.png");
        ImageIcon marron = new ImageIcon("gris.png");
        ImageIcon bleu = new ImageIcon("bleu.png");
        ImageIcon violet = new ImageIcon("violet.png");
        ImageIcon vert = new ImageIcon("vert.png");
        ImageIcon rose = new ImageIcon("rose.png");
        ImageIcon blanc = new ImageIcon("blanc.png");

        BoardCharacter pinkCharacter = new BoardCharacter("ROSE", 2, rose);
        BoardCharacter blackCharacter = new BoardCharacter("NOIR", 0, noir);
        BoardCharacter orangeCharacter = new BoardCharacter("ORANGE", 1, orange);
        BoardCharacter purpleCharacter = new BoardCharacter("VIOLET", 1, violet);
        BoardCharacter greenCharacter = new BoardCharacter("VERT", 1, vert);
        BoardCharacter yellowCharacter = new BoardCharacter("JAUNE", 1, jaune);
        BoardCharacter blueCharacter = new BoardCharacter("BLEU", 0, bleu);
        BoardCharacter whiteCharacter = new BoardCharacter("BLANC", 1, blanc);
        BoardCharacter brownCharacter = new BoardCharacter("MARRON", 1, marron);
        ArrayList<BoardCharacter> listCharacter = new ArrayList<BoardCharacter>();
        listCharacter.add(pinkCharacter);
        listCharacter.add(blackCharacter);
        listCharacter.add(orangeCharacter);
        listCharacter.add(purpleCharacter);
        listCharacter.add(greenCharacter);
        listCharacter.add(yellowCharacter);
        listCharacter.add(blueCharacter);
        listCharacter.add(whiteCharacter);
        listCharacter.add(brownCharacter);

        Random rand = new Random();
        int randomIndex = rand.nextInt(listCharacter.size() - 1);
        BoardCharacter randomElement = listCharacter.get(randomIndex);
        randomElement.setmStatus("Mr Jack");
        System.out.println("Mr Jack est le personnage " + randomElement.getmColor() + ".");
        mList = listCharacter;
    }

    // Création des détectives.
    public static void creationDetective() {

        DetectiveCharacter watson = new DetectiveCharacter("Watson");
        DetectiveCharacter toby = new DetectiveCharacter("Toby");
        DetectiveCharacter holmes = new DetectiveCharacter("Holmes");
        DetectiveCharacter[] listDetective = new DetectiveCharacter[3];
        listDetective[0] = watson;
        listDetective[1] = toby;
        listDetective[2] = holmes;
        mTableauDetective = listDetective;
    }

    // Création des districts avec affectation à un personnage + change l'orientation des district en face des détectives.
    public void boardDistrictInitialization(ArrayList<BoardCharacter> listCharacter) {
        Collections.shuffle(listCharacter);
        mBoard = new District[3][3];
        int cpt = 0;
        int cpt1 = 1;
        for (int i = 0; i < mBoard.length; i++) {
            for (int j = 0; j < mBoard[i].length; j++) {
                mBoard[i][j] = new District(listCharacter.get(cpt), cpt1);
                cpt++;
                cpt1++;
            }
        }
        int cpt2 = 1;
        System.out.println(" ");
        System.out.println("PLATEAU :");
        System.out.println(" ");
        for (int i = 0; i < mBoard.length; i++) {
            for (int j = 0; j < mBoard[i].length; j++) {
                if (cpt2 == 1) {
                    mBoard[i][j].setmOrientation("EST");
                    mBoard[i][j].setmWall("OUEST");
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est associé au personnage " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].getmWall() + ".");
                } else if (cpt2 == 3) {
                    mBoard[i][j].setmOrientation("OUEST");
                    mBoard[i][j].setmWall("EST");
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est associé au personnage " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].getmWall() + ".");
                } else if (cpt2 == 8) {
                    mBoard[i][j].setmOrientation("NORD");
                    mBoard[i][j].setmWall("SUD");
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est associé au personnage " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].getmWall() + ".");
                } else
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est associé au personnage " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].getmWall() + ".");
                cpt2++;
            }
        }
    }

    // Initialise la position des détectives.
    public void positionDetectiveInitialization(DetectiveCharacter[] tableauDetective) {

        tableauDetective[0].setmPosition(DetectiveCharacter.toString('e', 2));
        tableauDetective[1].setmPosition(DetectiveCharacter.toString('c', 5));
        tableauDetective[2].setmPosition(DetectiveCharacter.toString('a', 2));
        System.out.println(" ");
        System.out.println(tableauDetective[0].getmName() + " est sur la case " + tableauDetective[0].getmPosition());
        System.out.println(tableauDetective[1].getmName() + " est sur la case " + tableauDetective[1].getmPosition());
        System.out.println(tableauDetective[2].getmName() + " est sur la case " + tableauDetective[2].getmPosition());
        System.out.println(" ");

    }

    // Lance les jetons actions.
    public void throwToken() {

        Random rand = new Random();
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("Alibi");
        list1.add("Pivoter");
        list1.add("Pivoter");
        list1.add("Joker");
        list1.add("Echanger");
        list1.add("Holmes");
        list1.add("Watson");
        list1.add("Toby");

        ArrayList<String> list2 = new ArrayList<String>();
        int numberOfElements = 4;

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(list1.size() - 1);
            String randomElement = list1.get(randomIndex);
            list1.remove(randomIndex);
            list2.add(randomElement);
        }

        mSousListAction1 = list1;
        mSousListAction2 = list2;
    }

    // Print à qui est le tour.
    public void printWhoTurn(int turn, int sousTurn) {

        if (turn % 2 != 0) {
            if (sousTurn == 1 || sousTurn == 4)
                System.out.println("C'est à " + Investigator.mName + " (" + Investigator.mRole + ") de jouer !");
            else
                System.out.println("C'est à " + MrJack.mName + " (" + MrJack.mRole + ") de jouer !");
        } else {
            if (sousTurn == 1 || sousTurn == 4)
                System.out.println("C'est à " + MrJack.mName + " (" + MrJack.mRole + ") de jouer !");
            else
                System.out.println("C'est à " + Investigator.mName + " (" + Investigator.mRole + ") de jouer !");
        }
    }

    // Détermine qui doit jouer en fonction du tour et des sous-tours.
    public String whoTurn(int turn, int sousTurn) {

        if (turn % 2 != 0) {
            if (sousTurn == 1 || sousTurn == 4)
                return Investigator.mName;
            else
                return MrJack.mName;
        } else {
            if (sousTurn == 1 || sousTurn == 4)
                return MrJack.mName;
            else
                return Investigator.mName;
        }
    }

    // Choisi une action parmi une liste d'action.
    public String chooseListAction(int turn) {

        if (turn % 2 != 0) { // Si tour impair
            Scanner sc3 = new Scanner(System.in);
            System.out.println("Choisissez une action dans la liste suivante: " + mSousListAction1);
            String action = sc3.nextLine();
            while (!mSousListAction1.contains(action)) {
                System.out.println("ERREUR !");
                System.out.println("Choisissez une action dans la liste suivante: " + mSousListAction1);
                action = sc3.nextLine();
            }
            mSousListAction1.remove(action);
            return action;
        }
        else {// Si tour pair
            Scanner sc3 = new Scanner(System.in);
            System.out.println("Choisissez une action dans la liste suivante: " + mSousListAction2);
            String action = sc3.nextLine();
            while (!mSousListAction2.contains(action)) {
                System.out.println("ERREUR !");
                System.out.println("Choisissez une action dans la liste suivante: " + mSousListAction2);
                action = sc3.nextLine();
            }
            mSousListAction2.remove(action);
            return action;
        }
    }

    // Choix du jeton action.
    public void chooseActionPlayer(String action, String player) {

        switch (action) {
            case "Pivoter" -> {
                Scanner sc1 = new Scanner(System.in);
                System.out.println("Entre le numéro de ligne du district.");
                int districtColonne = sc1.nextInt();
                while (districtColonne > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de ligne du district.");
                    districtColonne = sc1.nextInt();
                }
                Scanner sc2 = new Scanner(System.in);
                System.out.println("Entre le numéro de colonne du district.");
                int districtLigne = sc2.nextInt();
                while (districtLigne > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de colonne du district.");
                    districtLigne = sc2.nextInt();
                }
                District a = mBoard[districtColonne][districtLigne];
                while (a == mDistrictPivote) {
                    Scanner sc17 = new Scanner(System.in);
                    System.out.println("ERREUR ! Ce district a déjà été pivoté dans ce tour.");
                    System.out.println("Entre le numéro de ligne du district.");
                    int districtColonne1 = sc17.nextInt();
                    while (districtColonne1 > 2) {
                        System.out.println("ERREUR !");
                        System.out.println("Entre le numéro de ligne du district.");
                        districtColonne1 = sc17.nextInt();
                    }
                    Scanner sc18 = new Scanner(System.in);
                    System.out.println("Entre le numéro de colonne du district.");
                    int districtLigne1 = sc18.nextInt();
                    while (districtLigne1 > 2) {
                        System.out.println("ERREUR !");
                        System.out.println("Entre le numéro de colonne du district.");
                        districtLigne1 = sc18.nextInt();
                    }
                    a = mBoard[districtColonne1][districtLigne1];
                }
                Scanner sc4 = new Scanner(System.in);
                System.out.println("Faire pivoter le district vers le SUD, le NORD, l'EST ou l'OUEST ?");
                String orientationDistrict = sc4.nextLine();
                while (mBoard[districtColonne][districtLigne].getmOrientation().equals(orientationDistrict)) {
                    System.out.println("ERREUR ! Le district est déjà orienté vers " + orientationDistrict);
                    System.out.println("Faire pivoter le district vers le SUD, le NORD, l'EST ou l'OUEST ?");
                    orientationDistrict = sc4.nextLine();
                }
                while (!(orientationDistrict.equals("NORD")) && !(orientationDistrict.equals("SUD")) && !(orientationDistrict.equals("EST")) && !(orientationDistrict.equals("OUEST"))) {
                    System.out.println("ERREUR !");
                    System.out.println("Faire pivoter le district vers le SUD, le NORD, l'EST ou l'OUEST ?");
                    orientationDistrict = sc4.nextLine();
                }
                mBoard[districtColonne][districtLigne].setmOrientation(orientationDistrict);
                mDistrictPivote = mBoard[districtColonne][districtLigne];
                System.out.println("Le district " + mBoard[districtColonne][districtLigne].getmNumber() + " est maintenant orienté vers " + mBoard[districtColonne][districtLigne].getmOrientation() + ".");
            }
            case "Joker" -> {
                if (player.equals(Investigator.mName)) {
                    Scanner sc5 = new Scanner(System.in);
                    System.out.println("Déplacer Toby, Watson ou Holmes d'une case ?");
                    String deplacementDetective = sc5.nextLine();
                    for (int i = 0; i < mListPosition.length; i++) {
                        if (deplacementDetective.equals(mTableauDetective[1].getmName())) {
                            int a = 1;
                            if ((i == 11) && (mListPosition[i].equals(mTableauDetective[1].getmPosition()))) {
                                mTableauDetective[1].setmPosition(mListPosition[0]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                            if (mListPosition[i].equals(mTableauDetective[1].getmPosition())) {
                                mTableauDetective[1].setmPosition(mListPosition[i + 1]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                        }
                        if (deplacementDetective.equals(mTableauDetective[0].getmName())) {
                            int a = 0;
                            if (i == 11 && (mListPosition[i].equals(mTableauDetective[0].getmPosition()))) {
                                mTableauDetective[0].setmPosition(mListPosition[0]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                            if (mListPosition[i].equals(mTableauDetective[0].getmPosition())) {
                                mTableauDetective[0].setmPosition(mListPosition[i + 1]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                        }
                        if (deplacementDetective.equals(mTableauDetective[2].getmName())) {
                            int a = 2;
                            if ((i == 11) && (mListPosition[i].equals(mTableauDetective[2].getmPosition()))) {
                                mTableauDetective[2].setmPosition(mListPosition[0]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                            if (mListPosition[i].equals(mTableauDetective[2].getmPosition())) {
                                mTableauDetective[2].setmPosition(mListPosition[i + 1]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                        }
                    }
                }
                if (player.equals(MrJack.mName)) {
                    Scanner sc16 = new Scanner(System.in);
                    System.out.println("Déplacer un détective d'une case (1) OU ne rien faire (2) ?");
                    int choixJoker = sc16.nextInt();
                    while ((choixJoker != 1) && (choixJoker != 2)) {
                        System.out.println("ERREUR !");
                        System.out.println("Déplacement de 1 ou 2 cases ?");
                        choixJoker = sc16.nextInt();
                    }
                    if (choixJoker == 1) {
                        Scanner sc17 = new Scanner(System.in);
                        System.out.println("Déplacer Toby, Watson ou Holmes d'une case ?");
                        String deplacementDetective = sc17.nextLine();
                        for (int i = 0; i < mListPosition.length; i++) {
                            if (deplacementDetective.equals(mTableauDetective[1].getmName())) {
                                int a = 1;
                                if ((i == 11) && (mListPosition[i].equals(mTableauDetective[1].getmPosition()))) {
                                    mTableauDetective[1].setmPosition(mListPosition[0]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                                if (mListPosition[i].equals(mTableauDetective[1].getmPosition())) {
                                    mTableauDetective[1].setmPosition(mListPosition[i + 1]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                            }
                            if (deplacementDetective.equals(mTableauDetective[0].getmName())) {
                                int a = 0;
                                if (i == 11 && (mListPosition[i].equals(mTableauDetective[0].getmPosition()))) {
                                    mTableauDetective[0].setmPosition(mListPosition[0]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                                if (mListPosition[i].equals(mTableauDetective[0].getmPosition())) {
                                    mTableauDetective[0].setmPosition(mListPosition[i + 1]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                            }
                            if (deplacementDetective.equals(mTableauDetective[2].getmName())) {
                                int a = 2;
                                if ((i == 11) && (mListPosition[i].equals(mTableauDetective[2].getmPosition()))) {
                                    mTableauDetective[2].setmPosition(mListPosition[0]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                                if (mListPosition[i].equals(mTableauDetective[2].getmPosition())) {
                                    mTableauDetective[2].setmPosition(mListPosition[i + 1]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                            }
                        }
                    } else if (choixJoker == 2) {
                        System.out.println("Mr Jack n'a rien fait !");
                    }

                }
            }
            case "Toby" -> {
                Scanner sc14 = new Scanner(System.in);
                System.out.println("Déplacement de 1 ou 2 cases ?");
                int deplacement = sc14.nextInt();
                while ((deplacement != 1) && (deplacement != 2)) {
                    System.out.println("ERREUR !");
                    System.out.println("Déplacement de 1 ou 2 cases ?");
                    deplacement = sc14.nextInt();
                }
                for (int i = 0; i < mListPosition.length; i++) {
                    if (deplacement == 1) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[1].getmPosition()))) {
                            mTableauDetective[1].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[1].getmPosition())) {
                            mTableauDetective[1].setmPosition(mListPosition[i + deplacement]);
                            break;
                        }
                    }
                    if (deplacement == 2) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[1].getmPosition()))) {
                            mTableauDetective[1].setmPosition(mListPosition[1]);
                            break;
                        }
                        if ((i == 10) && (mListPosition[i].equals(mTableauDetective[1].getmPosition()))) {
                            mTableauDetective[1].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[1].getmPosition())) {
                            mTableauDetective[1].setmPosition(mListPosition[i + deplacement]);
                            break;
                        }
                    }
                }
                System.out.println("Toby est maintenant en " + mTableauDetective[1].getmPosition());
            }
            case "Watson" -> {
                Scanner sc10 = new Scanner(System.in);
                System.out.println("Déplacement de 1 ou 2 cases ?");
                int deplacement1 = sc10.nextInt();
                while ((deplacement1 != 1) && (deplacement1 != 2)) {
                    System.out.println("ERREUR !");
                    System.out.println("Déplacement de 1 ou 2 cases ?");
                    deplacement1 = sc10.nextInt();
                }
                for (int i = 0; i < mListPosition.length; i++) {
                    if (deplacement1 == 1) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[0].getmPosition()))) {
                            mTableauDetective[0].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[0].getmPosition())) {
                            mTableauDetective[0].setmPosition(mListPosition[i + deplacement1]);
                            break;
                        }
                    }
                    if (deplacement1 == 2) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[0].getmPosition()))) {
                            mTableauDetective[0].setmPosition(mListPosition[1]);
                            break;
                        }
                        if ((i == 10) && (mListPosition[i].equals(mTableauDetective[0].getmPosition()))) {
                            mTableauDetective[0].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[0].getmPosition())) {
                            mTableauDetective[0].setmPosition(mListPosition[i + deplacement1]);
                            break;
                        }
                    }
                }
                System.out.println("Watson est maintenant en " + mTableauDetective[0].getmPosition());
            }
            case "Holmes" -> {
                Scanner sc11 = new Scanner(System.in);
                System.out.println("Déplacement de 1 ou 2 cases ?");
                int deplacement2 = sc11.nextInt();
                while ((deplacement2 != 1) && (deplacement2 != 2)) {
                    System.out.println("ERREUR !");
                    System.out.println("Déplacement de 1 ou 2 cases ?");
                    deplacement2 = sc11.nextInt();
                }
                for (int i = 0; i < mListPosition.length; i++) {
                    if (deplacement2 == 1) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[2].getmPosition()))) {
                            mTableauDetective[2].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[2].getmPosition())) {
                            mTableauDetective[2].setmPosition(mListPosition[i + deplacement2]);
                            break;
                        }
                    }
                    if (deplacement2 == 2) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[2].getmPosition()))) {
                            mTableauDetective[2].setmPosition(mListPosition[1]);
                            break;
                        }
                        if ((i == 10) && (mListPosition[i].equals(mTableauDetective[2].getmPosition()))) {
                            mTableauDetective[2].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[2].getmPosition())) {
                            mTableauDetective[2].setmPosition(mListPosition[i + deplacement2]);
                            break;
                        }
                    }
                }
                System.out.println("Holmes est maintenant en " + mTableauDetective[2].getmPosition());
            }
            case "Alibi" -> {
                Random rand = new Random();
                mList.removeIf(el -> el.getmStatus().equals("Mr Jack"));
                int randomIndex = rand.nextInt(mList.size() - 1);
                BoardCharacter randomElement = mList.get(randomIndex);
                mList.remove(randomIndex);
                for (int i = 0; i < mList.size(); i++)
                    System.out.println(mList.get(i).getmColor());
                if (player.equals(MrJack.mName)) {
                    MrJack.mNbHourglass += randomElement.getmNbHourglass();
                    System.out.println("Alibi Card : " + randomElement.getmColor() + ". Tu as " + MrJack.mNbHourglass + " sablier(s).");
                } else if (player.equals(Investigator.mName)) {
                    Investigator.mAlibiCard = randomElement;
                    Investigator.mAlibiCard.setmStatus("Pas Mr Jack");
                    System.out.println("Alibi Card : " + Investigator.mAlibiCard.getmColor() + ".");
                    randomElement.setmRecto(false);
                }
            }
            case "Echanger" -> {
                Scanner sc6 = new Scanner(System.in);
                System.out.println("Entre le numéro de ligne du 1er district à échanger.");
                int ligneExchange1 = sc6.nextInt();
                while (ligneExchange1 > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de ligne du 1er district à échanger.");
                    ligneExchange1 = sc6.nextInt();
                }
                Scanner sc7 = new Scanner(System.in);
                System.out.println("Entre le numéro de colonne du 1er district à échanger.");
                int columnExchange1 = sc7.nextInt();
                while (columnExchange1 > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de colonne du 1er district à échanger.");
                    columnExchange1 = sc7.nextInt();
                }
                Scanner sc8 = new Scanner(System.in);
                System.out.println("Entre le numéro de ligne du 2eme district à échanger.");
                int ligneExchange2 = sc8.nextInt();
                while (ligneExchange2 > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de ligne du 2eme district à échanger.");
                    ligneExchange2 = sc8.nextInt();
                }
                Scanner sc9 = new Scanner(System.in);
                System.out.println("Entre le numéro de colonne du 2eme district à échanger.");
                int columnExchange2 = sc9.nextInt();
                while (columnExchange2 > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de colonne du 2eme district à échanger.");
                    columnExchange2 = sc9.nextInt();
                }
                while (mBoard[ligneExchange1][columnExchange1] == mBoard[ligneExchange2][columnExchange2]){
                    System.out.println("ERREUR ! District déjà sélectionné.");
                    Scanner sc20 = new Scanner(System.in);
                    System.out.println("Entre le numéro de ligne du 2eme district à échanger.");
                    int ligneExchange3 = sc20.nextInt();
                    while (ligneExchange3 > 2) {
                        System.out.println("ERREUR !");
                        System.out.println("Entre le numéro de ligne du 2eme district à échanger.");
                        ligneExchange3 = sc20.nextInt();
                    }
                    Scanner sc21 = new Scanner(System.in);
                    System.out.println("Entre le numéro de colonne du 2eme district à échanger.");
                    int columnExchange3 = sc21.nextInt();
                    while (columnExchange3 > 2) {
                        System.out.println("ERREUR !");
                        System.out.println("Entre le numéro de colonne du 2eme district à échanger.");
                        columnExchange3 = sc21.nextInt();
                    }
                    ligneExchange1 = ligneExchange3;
                    columnExchange1 = columnExchange3;
                }
                District a = mBoard[ligneExchange1][columnExchange1];
                mBoard[ligneExchange1][columnExchange1] = mBoard[ligneExchange2][columnExchange2];
                mBoard[ligneExchange2][columnExchange2] = a;
                int nb = mBoard[ligneExchange1][ligneExchange1].getmNumber();
                mBoard[ligneExchange1][columnExchange1].setmNumber(mBoard[ligneExchange2][columnExchange2].getmNumber());
                mBoard[ligneExchange2][columnExchange2].setmNumber(nb);
            }
        }
    }

    // Appel à témoin.
    public boolean whoISee()
    {
        int a = 0;
        ArrayList<BoardCharacter> characterISee = new ArrayList<BoardCharacter>();
        ArrayList<Integer> b = new ArrayList<Integer>();
        ArrayList<BoardCharacter> characterIDontSee = new ArrayList<BoardCharacter>();
        for (int k = 0; k < mTableauDetective.length; k++) {
            switch (mTableauDetective[k].getmPosition()) {
                case "b1":
                    if (!(mBoard[0][0].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[0][0].getmCharacter());
                    }
                    if (!(mBoard[0][0].getmWall().equals("NORD")) && !(mBoard[0][0].getmWall().equals("SUD")) && !(mBoard[1][0].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[1][0].getmCharacter());
                    }
                    if (!(mBoard[0][0].getmWall().equals("NORD")) && !(mBoard[0][0].getmWall().equals("SUD")) && !(mBoard[1][0].getmWall().equals("NORD")) && !(mBoard[1][0].getmWall().equals("SUD")) && !(mBoard[2][0].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[2][0].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "c1":
                    if (!(mBoard[0][1].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[0][1].getmCharacter());
                    }
                    if (!(mBoard[0][1].getmWall().equals("NORD")) && !(mBoard[0][1].getmWall().equals("SUD")) && !(mBoard[1][1].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[1][1].getmCharacter());
                    }
                    if (!(mBoard[0][1].getmWall().equals("NORD")) && !(mBoard[0][1].getmWall().equals("SUD")) && !(mBoard[1][1].getmWall().equals("NORD")) && !(mBoard[1][1].getmWall().equals("SUD")) && !(mBoard[2][1].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[2][1].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "d1":
                    if (!(mBoard[0][2].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[0][2].getmCharacter());
                    }
                    if (!(mBoard[0][2].getmWall().equals("NORD")) && !(mBoard[0][2].getmWall().equals("SUD")) && !(mBoard[1][2].getmWall().equals("NORD"))){
                        characterISee.add(mBoard[1][2].getmCharacter());
                    }
                    if (!(mBoard[0][2].getmWall().equals("NORD")) && !(mBoard[0][2].getmWall().equals("SUD")) && !(mBoard[1][2].getmWall().equals("NORD")) && !(mBoard[1][2].getmWall().equals("SUD")) && !(mBoard[2][2].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[2][2].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "e2":
                    if (!(mBoard[0][2].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[0][2].getmCharacter());
                    }
                    if (!(mBoard[0][2].getmWall().equals("EST")) && !(mBoard[0][2].getmWall().equals("OUEST")) && !(mBoard[0][1].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[0][1].getmCharacter());
                    }
                    if (!(mBoard[0][2].getmWall().equals("EST")) && !(mBoard[0][2].getmWall().equals("OUEST")) && !(mBoard[0][1].getmWall().equals("EST")) && !(mBoard[0][1].getmWall().equals("OUEST")) && !(mBoard[0][0].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[0][0].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "e3":
                    if (!(mBoard[1][2].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[1][2].getmCharacter());
                    }
                    if (!(mBoard[1][2].getmWall().equals("EST")) && !(mBoard[1][2].getmWall().equals("OUEST")) && !(mBoard[1][1].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[1][1].getmCharacter());
                    }
                    if (!(mBoard[1][2].getmWall().equals("EST")) && !(mBoard[1][2].getmWall().equals("OUEST")) && !(mBoard[1][1].getmWall().equals("EST")) && !(mBoard[1][1].getmWall().equals("OUEST")) && !(mBoard[1][0].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[1][0].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "e4":
                    if (!(mBoard[2][2].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[2][2].getmCharacter());
                    }
                    if (!(mBoard[2][2].getmWall().equals("EST")) && !(mBoard[2][2].getmWall().equals("OUEST")) && !(mBoard[2][1].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[2][1].getmCharacter());
                    }
                    if (!(mBoard[2][2].getmWall().equals("EST")) && !(mBoard[2][2].getmWall().equals("OUEST")) && !(mBoard[2][1].getmWall().equals("EST")) && !(mBoard[2][1].getmWall().equals("OUEST")) && !(mBoard[2][0].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[2][0].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "d5":
                    if (!(mBoard[2][2].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[2][2].getmCharacter());
                    }
                    if (!(mBoard[2][2].getmWall().equals("SUD")) && !(mBoard[2][2].getmWall().equals("NORD")) && !(mBoard[1][2].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[1][2].getmCharacter());
                    }
                    if (!(mBoard[2][2].getmWall().equals("SUD")) && !(mBoard[2][2].getmWall().equals("NORD")) && !(mBoard[1][2].getmWall().equals("SUD")) && !(mBoard[1][2].getmWall().equals("NORD")) && !(mBoard[0][2].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[0][2].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "c5":
                    if (!(mBoard[2][1].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[2][1].getmCharacter());
                    }
                    if (!(mBoard[2][1].getmWall().equals("SUD")) && !(mBoard[2][1].getmWall().equals("NORD")) && !(mBoard[1][1].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[1][1].getmCharacter());
                    }
                    if (!(mBoard[2][1].getmWall().equals("SUD")) && !(mBoard[2][1].getmWall().equals("NORD")) && !(mBoard[1][1].getmWall().equals("SUD")) && !(mBoard[1][1].getmWall().equals("NORD")) && !(mBoard[0][1].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[0][1].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "b5":
                    if (!(mBoard[2][0].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[2][0].getmCharacter());
                    }
                    if (!(mBoard[2][0].getmWall().equals("SUD")) && !(mBoard[2][0].getmWall().equals("NORD")) && !(mBoard[1][0].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[1][0].getmCharacter());
                    }
                    if (!(mBoard[2][0].getmWall().equals("SUD")) && !(mBoard[2][0].getmWall().equals("NORD")) && !(mBoard[1][0].getmWall().equals("SUD")) && !(mBoard[1][0].getmWall().equals("NORD")) && !(mBoard[0][0].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[0][0].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "a4":
                    if (!(mBoard[2][0].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[2][0].getmCharacter());
                    }
                    if (!(mBoard[2][0].getmWall().equals("OUEST")) && !(mBoard[2][0].getmWall().equals("EST")) && !(mBoard[2][1].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[2][1].getmCharacter());
                    }
                    if (!(mBoard[2][0].getmWall().equals("OUEST")) && !(mBoard[2][0].getmWall().equals("EST")) && !(mBoard[2][1].getmWall().equals("OUEST")) && !(mBoard[2][1].getmWall().equals("EST")) && !(mBoard[2][2].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[2][2].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "a3":
                    if (!(mBoard[1][0].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[1][0].getmCharacter());
                    }
                    if (!(mBoard[1][0].getmWall().equals("OUEST")) && !(mBoard[1][0].getmWall().equals("EST")) && !(mBoard[1][1].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[1][1].getmCharacter());
                    }
                    if (!(mBoard[1][0].getmWall().equals("OUEST")) && !(mBoard[1][0].getmWall().equals("EST")) && !(mBoard[1][1].getmWall().equals("OUEST")) && !(mBoard[1][1].getmWall().equals("EST")) && !(mBoard[1][2].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[1][2].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "a2":
                    if (!(mBoard[0][0].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[0][0].getmCharacter());
                    }
                    if (!(mBoard[0][0].getmWall().equals("OUEST")) && !(mBoard[0][0].getmWall().equals("EST")) && !(mBoard[0][1].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[0][1].getmCharacter());
                    }
                    if (!(mBoard[0][0].getmWall().equals("OUEST")) && !(mBoard[0][0].getmWall().equals("EST")) && !(mBoard[0][1].getmWall().equals("OUEST")) && !(mBoard[0][1].getmWall().equals("EST")) && !(mBoard[0][2].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[0][2].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;

            }
        }
        // Enlève les doublons de la liste characterIDontSee
        Set set = new HashSet() ;
        set.addAll(characterIDontSee) ;
        ArrayList<BoardCharacter> characterIDontSee2 = new ArrayList<BoardCharacter>(set) ;

        // Enlève Mr Jack de la liste characterIDontSee2 s'il est présent
        for(int i = 0 ; i < characterIDontSee2.size(); i++) {
            if (characterIDontSee2.get(i).getmStatus().equals("Mr Jack")) {
                characterIDontSee2.remove(i);
            }
        }
        // Enlève Mr Jack de la liste characterISee s'il est présent
        for(int i = 0 ; i < characterISee.size(); i++) {
            if (characterISee.get(i).getmStatus().equals("Mr Jack")) {
                characterISee.remove(i);
                b.add(1);
            }
            else {
                b.add(0);
            }
        }
        // Enlève les district de characterIDontSee2, vue par un autre détective
        for (int i = 0 ; i < characterISee.size() ; i ++) {
            for (int j = 0 ; j < characterIDontSee2.size() ; j++) {
                if (characterIDontSee2.get(j) == characterISee.get(i)) {
                    characterIDontSee2.remove(j);
                }
            }
        }

        if (b.contains(1)) {
            System.out.println("Les détectives voient Mr Jack :)");
            for(int i = 0 ; i < characterIDontSee2.size(); i++){
                characterIDontSee2.get(i).setmRecto(false);
            }
            return true;
        } else {
            System.out.println("Le détective ne voient pas Mr Jack.");
            for(int i = 0 ; i < characterISee.size(); i++){
                characterISee.get(i).setmRecto(false);
            }
            return false;
        }
    }

    // Retourne le district.
    public void turnOverDistrict() {

        for (int i = 0; i < mBoard.length; i++)
            for (int j = 0; j < mBoard[i].length; j++)
                if (!mBoard[i][j].getmCharacter().getmRecto())
                    mBoard[i][j].getmCharacter().setmColor("vide");
    }

    // Met à jour le plateau.
    public void updateBoardDistrict() {

        System.out.println(" ");
        System.out.println("PLATEAU :");
        System.out.println(" ");
        for (int i = 0; i < mBoard.length; i++)
            for (int j = 0; j < mBoard[i].length; j++)
                if (!mBoard[i][j].getmCharacter().getmColor().equals("vide"))
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est associé au personnage " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].orientationWall() + ".");
                else
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].orientationWall() + ".");

    }

    // Met à jour la position des détéctives.
    public void updatePositionDetective(DetectiveCharacter[] tableauDetective) {

        System.out.println(" ");
        System.out.println(tableauDetective[0].getmName() + " est sur la case " + tableauDetective[0].getmPosition());
        System.out.println(tableauDetective[1].getmName() + " est sur la case " + tableauDetective[1].getmPosition());
        System.out.println(tableauDetective[2].getmName() + " est sur la case " + tableauDetective[2].getmPosition());
        System.out.println(" ");

    }

    // Arrête le programme si le jeu est fini.
    public void isGameOver(String role)
    {
        if (role.equals("Mr Jack")){
            System.out.println("GAME OVER ! ");
            System.out.println("BRAVO ! " + Investigator.mName + " (" + Investigator.mRole + ") A GAGNÉ :)" );
            System.out.println("Bien tenté " + MrJack.mName + " (" + MrJack.mRole + "), mais tu as perdu..." );
            System.exit(1);
        }
        else if (role.equals("Enquêteur")){
            System.out.println("GAME OVER ! ");
            System.out.println("BRAVO ! " + MrJack.mName + " (" + MrJack.mRole + ") A GAGNÉ :)" );
            System.out.println("Bien tenté " + Investigator.mName + " (" + Investigator.mRole + "), mais tu as perdu... :(" );
            System.exit(1);
        }
    }
}

