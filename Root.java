import java.util.*;

public class Root {
    //TODO: i feel like there's a better way to do this than static ints :/
    //also idk how to display scores on init
    protected static int players;
    protected static int maxRoll;

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        //init var
        final int PLAYER_CAP = 20; //dawg aint no way u playin this game with more than 20 people like u got an entire mf house party plain dice game from github no shot
        final int ROLL_CAP = 100;
        String message = "How many players? ";
        boolean check = false;

        //input/output Player count
        players = inputChecksum(PLAYER_CAP, message);

        //input/output roll maximum
        message = "What do you want the maximum roll number to be?";
        maxRoll = inputChecksum(ROLL_CAP, message);

        //initalize players
        Player.initPlayers();

        //start gameplay loop
        do {
            //ask if want to play again
            System.out.println("Do you want to play another round? [Y/N] \nYour scores will save if you do another round.");
            String input = scan.nextLine();
            final String[] ACCEPTED_VALUES = {"Y", "N", "yes", "no"};

            //TODO: make this work with invalid values
            do {
                if (Arrays.asList(ACCEPTED_VALUES).contains(input)) {
                    if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("yes")) {
                        check = true;
                    }
                } else {
                    System.out.println("Invalid input. Try again.");
                }

                if (check) {
                    new Player(); //calls the player constructor (that runs the rolling and score methods)
                }
            }
            }while(check);
    }

    public static int inputChecksum(int MAX300, String message) { //"why is the maximum variable called max300" BRO ITS A DDR REF ITS FUN LET ME HAVE THIS
        //init var
        final int MIN = 1; //this is hardcoded instead of a param because the only reason a min exists is to prevent negative nums and/or inputs of 0 and theres nothing in the code that would warrant a different min value
        Scanner scan = new Scanner(System.in);
        int input = 0;
        boolean exitStatus = false; //determines whether or not the dowhile loop should exit
        final String MINMAX_DISPLAY = "[Minimum: " + MIN + " Maximum: " + MAX300 +"]"; //tells the user the minimum and maximum inputs allowed
        System.out.println(message + MINMAX_DISPLAY); //prints specified message

        //"why is it formatted so strange" BRO ALL THESE IF/ELSES ARE ONE LINE'S WORTH I DONT NEED 3 LINES OF STUFF JUST FOR ONE LINE JUST PUT IT ALL IN ONE
        do{
            if(scan.hasNextInt()){ input = scan.nextInt();

                if(input <= MAX300 && input >= MIN){exitStatus = true;}  //exits do while if the input is not bigger than the maximum value and isnt a negative number

                else{System.out.println("The input you've entered does not meet the range. " + MINMAX_DISPLAY);} //if input is an int but is too large or small

            }else{System.out.println("The input you've entered is not the correct type. You must enter a valid integer. " + MINMAX_DISPLAY);} //if user enters string or double

        }while(!exitStatus);

        return input;
    }
}

//new Player class :D
//holds attributess for all players
class Player extends Root{

    private final static Map<String,Player> playerMap = new HashMap<>(); //list of all player names (so u can sort by players)
    static Random rand = new Random(); //its kinda dumb how this has to be static but the initplayer method has to be static since its the only method being called by the root class
    String playerName;
    int score;

    //(constructor only used when initializing players) adds initial roll to score
    private Player(String playerName, int roll){
        this.playerName = playerName;
        this.score += roll;
        playerMap.put(playerName, this);
    }

    //(called any time players need to roll excluding initialization)
    //calls roll and displayscores classes
    protected Player(){
        System.out.println("[---ROLLING---]");
        roll();

        System.out.println("[---SCORES---]");
        displayScores();
    }

    //creates objects for every player and sets an initial roll for them
    //the first round has to have its own method because the first round creates the objects but the other rounds dont need to create objects (since theyre already created)
    //this used to be so clunky it was like the roll() method but i called new objects and it was like i had to do 10 million stupid things to make it work properly
    protected static void initPlayers(){
        for(int i = 1; i<=players; i++){

            new Player(Integer.toString(i), 0);
        }
        new Player(); //calls constructor (which does the actual rolling/scores)
    }

    //rolls for all players, and adds their roll to their score
    private void roll(){
        for(int i = 1; i<=players; i++) {
            int roll = rand.nextInt(maxRoll);

            Player currentPlayer = playerMap.get(Integer.toString(i)); //finds current player (current player is the index of the for loop)
            currentPlayer.score += roll; //adds roll to the players score THIS GIVES NULLPOINTEXP
            System.out.println("Player " + i + " rolled " + roll + ".");
        }
    }

    //prints all player scores
    //ngl u could just put this inside the roll method or make the roll method call this instead of having them seperate and called seperately but this feels cleaner
    private void displayScores(){
        for(int i = 1; i<=players; i++){
            Player currentPlayer = playerMap.get(Integer.toString(i));  //finds current player (current player is the index of the for loop)
            int currentPlayerScore = currentPlayer.score;

            System.out.println("Player "+i+"'s score is "+currentPlayerScore);
        }
    }
}