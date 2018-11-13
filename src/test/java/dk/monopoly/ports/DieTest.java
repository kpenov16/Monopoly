package dk.monopoly.ports;

import dk.monopoly.HandImpl;
import dk.monopoly.SixSidedDieImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {
    private static String[] DICE_SIDES = new String[]{"1","2","3","4","5","6"};
    private static String[] HAND_OUTCOMES = new String[]{"2","3","4","5","6","7",
            "8","9","10","11","12"};
    private static final int NUMBER_OF_ROLLS = 60_000;
    private static final double FRAC_DIVIATION = 0.2;

    private static int NUMBER_DICE_SIDES = DICE_SIDES.length;
    private static final int BASE_EXPECTED_OCCURRANCES = NUMBER_OF_ROLLS / NUMBER_DICE_SIDES;
    private static final int DEVIATION_DICES = (int)(BASE_EXPECTED_OCCURRANCES * FRAC_DIVIATION);
    private static Die die;

    private static final int NUMBER_OF_ROLL_HANDS = 60_000;
    private static final double FRAC_DIVIATION_HANDS = 0.9;
    private static int NUMBER_HAND_OUTCOMES = HAND_OUTCOMES.length;
    private static final int BASE_EXPECTED_OCCURRANCES_HANDS = NUMBER_OF_ROLL_HANDS / NUMBER_HAND_OUTCOMES;
    private static final int DEVIATION_HANDS = (int)(BASE_EXPECTED_OCCURRANCES_HANDS * FRAC_DIVIATION_HANDS);

    private static HandImpl hand;

    @BeforeEach
    void setUp() {
        //dice
        die = new SixSidedDieImpl();


        //hand
        hand = new HandImpl();
        Die[] dice = new Die[2];
        dice[0] = new SixSidedDieImpl();
        dice[1] = new SixSidedDieImpl();
        hand.setDice(dice);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    public void givenRollingHand_returnNuberBetween2And12(){
        assertTrue(eachRollIsValidNumber(NUMBER_HAND_OUTCOMES, hand, HAND_OUTCOMES));
    }

    @Test
    public void givenBaseLineAndDeviationOnHand_returnEachOccurrenceWithinLimits() {
        String allHands = "";
        for (int i = 0; i < NUMBER_OF_ROLL_HANDS; i++)
            allHands += hand.roll();

        //region Playing with general solution using standard deviation
        int occurrenceOf2 = getOccurrence("2", allHands);
        int occurrenceOf3 = getOccurrence("3", allHands);
        int occurrenceOf4 = getOccurrence("4", allHands);
        int occurrenceOf5 = getOccurrence("5", allHands);
        int occurrenceOf6 = getOccurrence("6", allHands);
        int occurrenceOf7 = getOccurrence("7", allHands);
        int occurrenceOf8 = getOccurrence("8", allHands);
        int occurrenceOf9 = getOccurrence("9", allHands);
        int occurrenceOf10 = getOccurrence("10", allHands);
        int occurrenceOf11 = getOccurrence("11", allHands);
        int occurrenceOf12 = getOccurrence("12", allHands);

        System.out.println(
                "2 : " + occurrenceOf2 + " \n" +
                        "3 : " + occurrenceOf3 + " \n" +
                        "4 : " + occurrenceOf4 + " \n" +
                        "5 : " + occurrenceOf5 + " \n" +
                        "6 : " + occurrenceOf6 + " \n" +
                        "7 : " + occurrenceOf7 + " \n" +
                        "8 : " + occurrenceOf8 + " \n" +
                        "9 : " + occurrenceOf9 + " \n" +
                        "10 : " + occurrenceOf10 + " \n" +
                        "11 : " + occurrenceOf11 + " \n" +
                        "12 : " + occurrenceOf12 + " \n"
        );

        for(String handOutcome : HAND_OUTCOMES)
            assertOccurrenceMatchExpectedRange(handOutcome, allHands, BASE_EXPECTED_OCCURRANCES_HANDS, DEVIATION_HANDS);
    }
/*
    @Test
    public void givenCertainNumberOfRollsWithDieFrom1To7Sides_returnOnlyValidNumbersOccurrence(){
        String[] DICE_SIDES_1to7 = new String[]{"1","2","3","4","5","6","7"};
        Die dieWithSidesFrom1to7 = new SevenSidedDieImpl();

        assertTrue( eachRollIsValidNumber(NUMBER_OF_ROLLS, dieWithSidesFrom1to7, DICE_SIDES_1to7) );
    }

    @Test
    public void givenCertainNumberOfRollsWithDieFrom1To7Sides_returnAllValidSidesWereRolled(){
        String[] validSides = new String[]{"1","2","3","4","5","6","7"};
        Die sevenSidedDie = new SevenSidedDieImpl();

        assertTrue( allValidSidesWereRolled(NUMBER_OF_ROLLS, sevenSidedDie, validSides) );
    }
*/
    private boolean allValidSidesWereRolled(int numberOfRolls, Die sevenSidedDie, String[] validSides) {
        String allRolls = "";
        for (int i = 0; i < numberOfRolls; i++)
            allRolls += sevenSidedDie.roll();

        for (String valid : validSides){
            Pattern pattern = Pattern.compile(valid);
            Matcher matcherOne = pattern.matcher(allRolls);
            if(!matcherOne.find())
                return false;
        }
        return true;
    }


    @Test
    public void givenCertainNumberOfRolls_returnOnlyValidNumbersOccurrence(){
        assertTrue( eachRollIsValidNumber(NUMBER_OF_ROLLS, die, DICE_SIDES) );
    }

    private boolean eachRollIsValidNumber(int numberOfRolls, HandImpl roller, final String[] diceSides) {
        String[] validNumbers = diceSides;
        boolean allRollsValid = true;
        for(int i = 0; i < numberOfRolls; i++){
            String currentRoll = String.valueOf( roller.roll() );
            if(!isCurrentRollFound(validNumbers, currentRoll)){
                allRollsValid = false;
                break;
            }
        }
        return allRollsValid;
    }

    private boolean eachRollIsValidNumber(int numberOfRolls, Die roller, final String[] diceSides) {
        String[] validNumbers = diceSides;
        boolean allRollsValid = true;
        for(int i = 0; i < numberOfRolls; i++){
            String currentRoll = String.valueOf( roller.roll() );
            if(!isCurrentRollFound(validNumbers, currentRoll)){
                allRollsValid = false;
                break;
            }
        }
        return allRollsValid;
    }

    private boolean isCurrentRollFound(String[] validNumbers, String currentRoll) {//(List<String> validNumbers, String currentRoll) {
        boolean currentRollFound = false;
        for(String num : validNumbers){
            if(num.equals(currentRoll)){
                currentRollFound = true;
                break;
            }
        }
        return currentRollFound;
    }

    @Test
    public void givenBaseLineAndDeviation_returnEachOccurrenceWithinLimits() {
        String allRolls = "";
        for (int i = 0; i < NUMBER_OF_ROLLS; i++)
            allRolls += die.roll();

        for(String diceSide : DICE_SIDES)
            assertOccurrenceMatchExpectedRange(diceSide, allRolls, BASE_EXPECTED_OCCURRANCES, DEVIATION_DICES);

        //region Playing with general solution using standard deviation
        int occurrenceOf1 = getOccurrence("1", allRolls);
        int occurrenceOf2 = getOccurrence("2", allRolls);
        int occurrenceOf3 = getOccurrence("3", allRolls);
        int occurrenceOf4 = getOccurrence("4", allRolls);
        int occurrenceOf5 = getOccurrence("5", allRolls);
        int occurrenceOf6 = getOccurrence("6", allRolls);

        double mean = (occurrenceOf1 + occurrenceOf2 + occurrenceOf3 + occurrenceOf4 + occurrenceOf5 + occurrenceOf6) / 6;
        int meanAsInt = (int) mean;
        //System.out.println("mean : " + mean);
        double averageMeanOf1 = Math.pow((occurrenceOf1 - mean), 2);
        double averageMeanOf2 = Math.pow((occurrenceOf2 - mean), 2);
        //System.out.println("averageMeanOf2 : " + averageMeanOf2 + "pow of: " + (occurrenceOf2 - mean));
        double averageMeanOf3 = Math.pow((occurrenceOf3 - mean), 2);
        double averageMeanOf4 = Math.pow((occurrenceOf4 - mean), 2);
        double averageMeanOf5 = Math.pow((occurrenceOf5 - mean), 2);
        double averageMeanOf6 = Math.pow((occurrenceOf6 - mean), 2);
        double sqredAvrMean = (averageMeanOf1 + averageMeanOf2 + averageMeanOf3 + averageMeanOf4 + averageMeanOf5 + averageMeanOf6) / 6;
        //System.out.println("sqredAvrMean : " + sqredAvrMean);
        double stdDev = Math.sqrt(sqredAvrMean);
        System.out.println("standard diviation : " + stdDev);
        System.out.println("1 : " + occurrenceOf1 + " \n" +
                "2 : " + occurrenceOf2 + " \n" +
                "3 : " + occurrenceOf3 + " \n" +
                "4 : " + occurrenceOf4 + " \n" +
                "5 : " + occurrenceOf5 + " \n" +
                "6 : " + occurrenceOf6 + " \n");

        assertOccurrenceMatchExpectedRange("6", allRolls, BASE_EXPECTED_OCCURRANCES, DEVIATION_DICES);
        assertOccurrenceMatchExpectedRange("5", allRolls, BASE_EXPECTED_OCCURRANCES, DEVIATION_DICES);
        assertOccurrenceMatchExpectedRange("4", allRolls, BASE_EXPECTED_OCCURRANCES, DEVIATION_DICES);
        assertOccurrenceMatchExpectedRange("3", allRolls, BASE_EXPECTED_OCCURRANCES, DEVIATION_DICES);
        assertOccurrenceMatchExpectedRange("2", allRolls, BASE_EXPECTED_OCCURRANCES, DEVIATION_DICES);
        assertOccurrenceMatchExpectedRange("1", allRolls, BASE_EXPECTED_OCCURRANCES, DEVIATION_DICES);
        //endregion
    }

    private int getOccurrence(String occurrenceOf, String rolls){
        Pattern pattern = Pattern.compile(occurrenceOf);
        Matcher matcherOne = pattern.matcher(rolls);
        int countOccurrences = 0;
        while (matcherOne.find())
            countOccurrences++;
        return countOccurrences;
    }

    private void assertOccurrenceMatchExpectedRange(String occurrenceOf, String rolls, final int base, final int deviation) {
        Pattern pattern = Pattern.compile(occurrenceOf);
        Matcher matcherOne = pattern.matcher(rolls);
        int countOccurrences = 0;
        while (matcherOne.find())
            countOccurrences++;

        //System.out.println(rolls);
        //System.out.println(countOccurrences);

        assertTrue(countOccurrences >= (base - deviation)
                && countOccurrences <= (base + deviation)
        );
    }

}