import java.util.*;

public class Root {

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        //init var
        final int PLAYER_CAP = 20; //dawg aint no way u playin this game with more than 20 people like u got an entire mf house party plain dice game from github no shot
        final int ROLL_CAP = 100;
        String message = "How many players? ";

        //input/output Player count
        int players = inputChecksum(PLAYER_CAP, message);

        //input/output roll maximum
        message = "What do you want the maximum roll number to be?";
        int maxRoll = inputChecksum(ROLL_CAP, message);

    }

    public static int inputChecksum(int MAX300, String message) { //"why is the maximum variable called max300" BRO ITS A DDR REF ITS FUN LET ME HAVE THIS
        //init var
        final int MIN = 1; //this is hardcoded instead of a param because the only reason a min exists is to prevent negative nums and/or inputs of 0 and theres nothing in the code that would warrant a different min value
        Scanner scan = new Scanner(System.in);
        int input = 0;
        boolean exitStatus = false; //determines whether or not the dowhile loop should exit
        String MINMAX_DISPLAY = "[Minimum: " + MIN + "Maximum: " + MAX300 + "]"; //tells the user the minimum and maximum inputs allowed
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
//holds atts for all players
class Player {
    Random rand = new Random();
    int playerName;
    int score;
    int roll;

    private Player(){
        this.score += roll;
    }

    private void initPlayers(int playerCount, int maxRoll){
        
    }
}