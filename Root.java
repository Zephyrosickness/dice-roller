import java.util.*;
//by alexander (zephyros)
//dont steal my codeee pretty pleaaase

/*

---[OVERVIEW]--- (lists all methods in order, mostly so i dont get lost on my own spaghetticode lol)
--[methods are placed in the logical order they would be called in a program]--

-[Root Class]- (the main class)
* intChecksum: used when inputting int values, makes sure input value is an int and meets minimum/maximum requirements before proceeding and returning the int
* YNStringCheck: used for a [Y/N] prompt, makes sure user inputs [Y/N] or [yes/no] (case-insensitive) before returning false if user input N, and true for Y
* playerName: renames any players from the default Player 1/2 naming-scheme, passes an int (player index) and a string into one of the Player-class constructors which does the renaming

-[Player Class]- (used to hold and manage attributes of all players)
* constructor 1 (String playerName, int roll, int index) - sets player attributes and adds them to the map so you can locate player objects using their index [only called on initialization]
* constructor 2 (no params) - calls the roll() and scoreDisplay() methods, and prints out headers such as [[----ROUND-X-START!----]] [this constructor is called every round, and is the backbone of this program]
* constructor 3 (int playerIndex, String newName) - renames a player by finding them via the index parameter and changes their playerName attr. to the string parameter [called in the playerName class in root]
* initPlayers: creates X amount of player objects based on the playercount, assigns all of them a default name, a score of 0, and an index in order of creation
* roll: assigns a random number to each player for their roll, adds the roll to their score, and displays the roll.
* scoreDisplay: prints out the score of every player. pretty self explanitory lolll
* findWinner: compares scores of all players, declares highest score the winner and will mention if players are tied

*/

public class Root {
    /*TODO: i feel like there's a better way to do this than static ints :/
    but since the player class also needs these vars its like ://///  bruhhh
    the only other option i can think of is passing these as params to every player method (cringe)
    or passing these as params into a constructor and then the constructor changes the local vars in the player class using this.[var] but ngl this code already has enough constructors that only do like one thing
    this would be so easy if i could just use this.[var] in a static context lmaooo*/
    /*ngl as it stands with my knowledge turning these non-static would be more trouble than its worth
    and i dont even know why static vars are bad ppl on stackoverflow just keep saying it is and this program is small anyway its not that deep*/
    //why am i yapping in the comments of my own code bruh

    protected static int players;
    protected static int maxRoll;

    public static void main(String[] args) {
        //init var
        /*PLAYER_CAP and ROLL_CAP are used as caps when doing data validation; you can have less than these values but you cant go over
        although these values could just be hardcoded (and player/roll mins are both the same lmao) its better to have these freely adjustable just in case*/
        final int PLAYER_MIN = 2;
        final int PLAYER_CAP = 20; //dawg aint no way u playin this game with more than 20 people like u got an entire mf house party plain dice game from github no shot
        final int ROLL_MIN = 2;
        final int ROLL_CAP = 100;
        String message = "How many players? ";

        //input/output Player count
        players = intChecksum(PLAYER_MIN, PLAYER_CAP, message);
        Player.initPlayers(); //initalizes player list (creates objects for each player, sets their score to 0)

        //allows user to add names for players instead of it all being player 1/2/3
        message = "Would you like to add a name to any players? [Y/N]\nIf unchanged, default names are Player 1/2/3..[etc.]"; //yes, you can just pass this as one block of text into the checksum but that makes it long. this is more readable even if theres an extraneous variable/line or two floatin around
        if(YNStringCheck(message)){playerName();}

        //input/output roll cap
        message = "What do you want the maximum roll number to be? ";
        maxRoll = intChecksum(ROLL_MIN, ROLL_CAP, message);

        System.out.println("[[[-----GAME-START!-----]]]");

        message = "Do you want to roll again? [Y/N] [Your scores will save if you do another round.]";

        while(true){
            new Player(); //runs constructor method in player class (rolls, adds to score, and displays scores
            //if player chooses to play again; does nothing and allows the loops to run again | if player doesnt want to play again; terminates program
            if(!YNStringCheck(message)){
                Player.findWinner();
                System.exit(0);  //if input = n, end the game
            }
        }
    }

    //used when inputting int values, makes sure input value is an int and meets minimum/maximum requirements before proceeding and returning the int
    private static int intChecksum(int MIN, int MAX300, String message) { //"why is the maximum variable called max300" BRO IT'S A DDR REF ITS FUN LET ME HAVE THIS
        //init var
        Scanner scan = new Scanner(System.in);
        int input = 0; //i still hate having to initialze variables bruh "input is not initialized" NO BRO IT IS INITIALIZED IN THE SCANNER CALM DOWN!!!
        boolean exitStatus = false; //determines whether the dowhile loop should exit
        final String MINMAX_DISPLAY = "[MIN: " + MIN + " MAX: " + MAX300 +"]"; //tells the user the minimum and maximum inputs allowed (i got tired of writing it like 2 times
        System.out.println(message + MINMAX_DISPLAY); //prints specified message

        //"why is it formatted so strange" BRO ALL THESE IF/ELSES ARE ONE LINE'S WORTH I DON'T NEED 3 LINES OF STUFF JUST FOR ONE LINE JUST PUT IT ALL IN ONE
        //genuinely i remember hearing somewhere that its good practice to put it all in one line if it's an oneline if-statement but i cant remember if thats real or not or where i heard it from so maybe im cray cray
        do{
            if(scan.hasNextInt()){ input = scan.nextInt();
                if(input <= MAX300 && input >= MIN) {exitStatus = true;  //exits do while if the input is not bigger than the maximum value and isnt a negative number
                }else{System.out.println("The input you've entered does not meet the range. " + MINMAX_DISPLAY);} //if input is an int but is too large or small
            }else{System.out.println("The input you've entered is not the correct type. You must enter a valid integer. " + MINMAX_DISPLAY);} //if user enters string or double
            if(!exitStatus){scan.nextLine();}
        }while(!exitStatus);
        return input;
    }

    //used for a [Y/N] prompt, makes sure user inputs [Y/N] or [yes/no] (case-insensitive) before returning false if user input N, and true for Y
    private static boolean YNStringCheck(String message){
        //init var
        Scanner scan = new Scanner(System.in);
        boolean check = false;
        final String[] ACCEPTED_VALUES = {"y", "n", "yes", "no"}; //ngl i could probably make this an arraylist to make the if-statement more readable since the only time its called its casted to an arraylist but regular array[] variables have a predefined length so it feels better
        boolean status = true;

        System.out.println(message);
        do {
            String input = scan.nextLine().toLowerCase();  //input is casted to lowercase so inputs work regardless of case

            if(Arrays.asList(ACCEPTED_VALUES).contains(input)){
                check = true;
                if (input.equals("n") || input.equals("no")) {status = false;}
            }else{System.out.println("Invalid input. Try again.\n"+message);}

        }while(!check);
        return status;
    }

    //renames any players from the default Player 1/2 naming-scheme, passes an int (player index) and a string into one of the Player-class constructors which does the renaming
    private static void playerName(){
        Scanner scan = new Scanner(System.in);
        boolean continueLoop = true;
        final String MESSAGE = "Would you like to add a name to another player? [Y/N]";
        ArrayList<Integer> renamedPlayers = new ArrayList<>(); //list of all renamed players to prevent dupes, will auto-terminate naming method if all players are named


        do {
            //auto-terminates if all players have been named
            if(renamedPlayers.size()>=players){
                System.out.println("All players have been named!");
                continueLoop = false;
            }else{

                //i/o player index
                int playerIndexInput = intChecksum(1, players, "Which player do you want to set a name for? ");

                //adds index to the list of renamed players if player was renamed for the first time
                if (!(renamedPlayers.contains(playerIndexInput))) {renamedPlayers.add(playerIndexInput);}

                //input/output new name
                System.out.println("What would you like to rename this player?");
                String newName = scan.nextLine();

                //calls constructor in playerclass to rename the var
                new Player(playerIndexInput, newName);

                if(renamedPlayers.size()<players) {continueLoop = YNStringCheck(MESSAGE);}
            }
        }while(continueLoop); //loops so player can rename multiple players
    }
}

//used to hold and manage attributes of all players
class Player extends Root{
    //utility vars
    private int roundCount = 1; //intellij says this can be converted into a local var in a constructor, this is a false positive. if this was a local var, it would be re-initialized as 1 every time the constructor runs, thereby defeating the purpose of the var
    private final static Map<Integer,Player> playerMap = new HashMap<>(); //list of all player indexes (allows you to locate player objects based on index)
    private final Random rand = new Random();

    //player attributes
    private String playerName;
    private int score;

    //sets player attributes and adds them to the map so you can locate player objects using their index
    //[only called on initialization]
    private Player(String playerName, int roll, int index){
        this.playerName = playerName;
        this.score += roll;
        //numerical index used to identify players regardless of their string name
        playerMap.put(index, this);
    }

    //calls the roll() and scoreDisplay() methods, and prints out headers such as [[----ROUND-X-START!----]]
    //[this constructor is called every round, and is the backbone of this program]
    protected Player(){
        System.out.println("[[----ROUND-"+ roundCount +"-START!----]]");

        System.out.println("[---ROLLING---]");
        roll();

        System.out.println("[---SCORES---]");
        displayScores();

        System.out.println("[[----ROUND-END!----]]");

        roundCount++;
    }

    //renames a player by finding them via the index parameter and changes their playerName attr. to the string parameter
    //[called in the playerName class in root]
    protected Player(int playerIndex, String newName){
        Player currentPlayer = playerMap.get(playerIndex);
        currentPlayer.playerName = newName;
    }

    // creates X amount of player objects based on the playercount, assigns all of them a default name, a score of 0, and an index in order of creation
    //this used to be so clunky it was like the roll() method but i called new objects and it was like i had to do 10 million stupid things to make it work properly
    protected static void initPlayers(){
        for(int i = 1; i<=players; i++){
            String defaultName = "Player "+i;
            new Player(defaultName,0, i);
        }
        players = playerMap.size(); //i don't think this would ever do anything since these should always be the same, but just in case yk??

    }

    //assigns a random number to each player for their roll, adds the roll to their score, and displays the roll.
    private void roll(){
        for(int i = 1; i<=players; i++) {
            int roll = (rand.nextInt(maxRoll))+1; //adds +1 because rolls start at 0 (so if you want maxroll to be 10, it would only roll 0-9 if there was no +1, so this allows it to roll from 1-10, for instance)

            Player currentPlayer = playerMap.get(i); //finds current player and their attributes (current player is the index of the for loop)
            currentPlayer.score += roll;
            System.out.println(currentPlayer.playerName+" rolled "+roll+".");
        }
    }

    //prints out the score of every player. pretty self explanitory lolll
    //ngl u could just put this inside the roll() method or make the roll method call this instead of having them seperate and called seperately but this feels cleaner
    private void displayScores(){
        for(int i = 1; i<=players; i++){
            Player currentPlayer = playerMap.get(i); //finds current player and their attributes (current player is the index of the for loop)
            int currentPlayerScore = currentPlayer.score;

            System.out.println(currentPlayer.playerName+"'s score is "+currentPlayerScore);
        }
    }

    //compares scores of all players, declares highest score the winner and will mention if players are tied
    protected static void findWinner(){
        int winningScore = 0;
        boolean tie = false;
        String winner ="!!![PLACEHOLDER; CONTACT IF SEEN DURING REGULAR GAMEPLAY]!!!";
        ArrayList<String> tieList = new ArrayList<>();

        System.out.println("[[[-----GAME-END!-----]]]");
        for(int i = 1; i<=players; i++){
            Player currentPlayer = playerMap.get(i);  //finds current player and their attributes (current player is the index of the for loop)
            if(currentPlayer.score>winningScore) {
                winner = currentPlayer.playerName; //sets player with highest score as winner
                winningScore = currentPlayer.score;
                tieList.clear(); //clears tielist; since this runs sequentially, its possible 2 players can have the same score of 14, then another player has 18, and all players are still displayed as tied
            }else if(currentPlayer.score==winningScore){
                tie = true;
                if(!tieList.contains(winner)){tieList.add(winner);}
                tieList.add(currentPlayer.playerName);
            }
        }
        //output final results
        if(tie){
            if(tieList.size()==players){System.out.println("All players tied!");} //if all players somehow tie
            else{System.out.println("Players "+tieList+" tied!");} //if only some players tie
        }else{System.out.println("The winner is "+winner+"!");} //if no tie
    }
}