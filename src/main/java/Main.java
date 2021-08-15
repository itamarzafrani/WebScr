import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int totalScore = 0;

        Scanner scanner = new Scanner(System.in);
        YnetBot ynetBot = new YnetBot();
        System.out.println("Welcome to guess game!");
        System.out.println("We will play on Ynet website");
        System.out.println("Which words is the most showen in the website?");
        System.out.println("You have 5 options to get to the hights score");
        System.out.println("The clue is that the longest artical is :" + ynetBot.getLongestArticleTitle());

        Map<String, Integer> allWords = ynetBot.getWordsStatistics();
        for (int i = 0; i < 5; i++) {
            int score;
            System.out.println("Whats your word: ");
            String guess = scanner.nextLine();
            if (!allWords.containsKey(guess)) {
                System.out.println("You earned 0 points");
            } else {
                score = allWords.get(guess);
                System.out.println("You earned " + score + " points");
                totalScore = totalScore + score;

            }
        }
        System.out.println("Your score until now is :" + totalScore);
        System.out.println("\n\n");
        System.out.println(" Plz enter word,char or sentences that you think that common in this website and a number that you think that this sentences will show and if you will close to the real solution you will get 250 points");
        String sentences = scanner.nextLine();
        System.out.println("Plz enter a number ");
        int numGuess = scanner.nextInt();
        scanner.nextLine();
        int result = ynetBot.countInArticlesTitles(sentences);
        if (Math.abs(  result - numGuess) <= 2) {
            totalScore = totalScore + 250;
        } else System.out.println("You arent correct ");
        System.out.println("Your final score is " + totalScore + " !!!!!!!!!");
    }
}
