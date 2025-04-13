import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * A chatbot that simulates a reflective conversation with the user using mirror words.
 * Implements the Chatbot interface and maintains a transcript of the conversation.
 */

class Conversation implements Chatbot {

  // Attributes 
  private int rounds;
  private List<String> transcript;
  private Random random;
  private Map<String, String> mirrorWords;
  private static final String[] cannedResponses = {
      "Mmm-hm.",
      "Interesting.",
      "Tell me more.",
      "I got it.",
      "Ohhh, really?",
      "Good to know.",
      "Sounds great!",
      "Cooool!",
      "That's fascinating."
  };

  /**
   * Constructs a new Conversation instance with default values and mirror words.
   */
  Conversation() {
    transcript = new ArrayList<>();
    random = new Random();
    mirrorWords = new HashMap<>();
    mirrorWords.put("i", "you");
    mirrorWords.put("me", "you");
    mirrorWords.put("am", "are");
    mirrorWords.put("you", "I");
    mirrorWords.put("my", "your");
    mirrorWords.put("your", "my");
    mirrorWords.put("mine", "yours");
    mirrorWords.put("we", "you");
  }

  /**
   * Starts and runs the conversation with the user
   */
  public void chat() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("How many rounds?");
    this.rounds = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Hi there! What's on your mind?");
    transcript.add("Hi there! What's on your mind?");

    for (int i = 0; i < rounds; i++){
      String userInput = scanner.nextLine();
      transcript.add(userInput);

      String response = respond(userInput);
      System.out.println(response);
      transcript.add(response);
    }

    System.out.println("See ya!");
    transcript.add("See ya!");
    scanner.close();
  }

  /**
   * Prints transcript of conversation when terminating
   */
  public void printTranscript() {
    System.out.println("\nTRANSCRIPT:");
    for (String line : transcript) {
      System.out.println(line);
    }
  }

  /**
   * Gives appropriate response (mirrored or canned) based on user's input
   * @param inputString the user's last line of input
   * @return mirrored or canned response to user input  
   */
  public String respond(String inputString) {
    boolean mirrored = false;

    // Normalize input and strip terminal punctuation
    inputString = inputString.trim();
    if (inputString.endsWith(".") || inputString.endsWith("!") || inputString.endsWith("?")) {
      inputString = inputString.substring(0, inputString.length() - 1);
    }

    String[] tokens = inputString.split(" ");
    List<String> responseTokens = new ArrayList<>();

    for (int i = 0; i < tokens.length; i++) {
      String wordOnly = tokens[i].replaceAll("[^a-zA-Z']", "");
      String punct = tokens[i].replaceAll("[a-zA-Z']", "");
      String lowerWord = wordOnly.toLowerCase();

      if (mirrorWords.containsKey(lowerWord)) {
        String replacement = mirrorWords.get(lowerWord);
        if (Character.isUpperCase(wordOnly.charAt(0))) {
          replacement = Character.toUpperCase(replacement.charAt(0)) + replacement.substring(1);
        }
        responseTokens.add(replacement + punct);
        mirrored = true;
      } else if (lowerWord.equals("i'm")) {
        responseTokens.add("you're" + punct);
        mirrored = true;
      } else if (lowerWord.equals("you're")) {
        responseTokens.add("I'm" + punct);
        mirrored = true;
      } else {
        responseTokens.add(wordOnly + punct);
      }
    }

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < responseTokens.size(); i++) {
      result.append(responseTokens.get(i));
      if (i != responseTokens.size() - 1) result.append(" ");
    }

    if (!result.isEmpty()) {
      result.setCharAt(0, Character.toUpperCase(result.charAt(0)));
    }

    return mirrored ? result.toString() + "?" : cannedResponses[random.nextInt(cannedResponses.length)];
  }

  /**
   * Runs the program.
   * @param arguments command-line arguments
   */
  public static void main(String[] arguments) {
    Conversation myConversation = new Conversation();
    myConversation.chat();
    myConversation.printTranscript();
  }

}
