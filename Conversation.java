import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

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
   * Constructor 
   */
  Conversation() {
     transcript = new ArrayList<>();
     random = new Random();
     mirrorWords = new HashMap<>();
     mirrorWords.put("I", "you");
     mirrorWords.put("me", "you");
     mirrorWords.put("am", "are");
     mirrorWords.put("you", "I");
     //mirrorWords.put("I'm", "you're");
     //mirrorWords.put("you are", "I am");
     //mirrorWords.put("you're", "I'm"); --> failed
     mirrorWords.put("my", "your");
     mirrorWords.put("your", "my");
     mirrorWords.put("mine", "yours");
     mirrorWords.put("we","you");

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
   * Prints transcript of conversation
   */
  public void printTranscript() {
    System.out.println("\nTRANSCRIPT:");
    for (String line : transcript) {
      System.out.println(line);
    }
  }

  /**
   * Gives appropriate response (mirrored or canned) to user input
   * @param inputString the users last line of input
   * @return mirrored or canned response to user input  
   */
  public String respond(String inputString) {
    String[] words = inputString.split(" ");
    boolean mirrored = false;

    for (int i = 0; i < words.length; i++) {
       if (mirrorWords.containsKey(words[i])) {
          words[i] = mirrorWords.get(words[i]);
          mirrored = true;
        }
    }
    /* tried to do the hudos but failed
      String word = words[i].toLowerCase().replaceAll("[^a-zA-Z]", ""); // Remove punctuation
      if (mirrorWords.containsKey(word)) {
          String replacement = mirrorWords.get(word);
  
          // Preserve capitalization
          if (Character.isUpperCase(words[i].charAt(0))) {
              replacement = Character.toUpperCase(replacement.charAt(0)) + replacement.substring(1);
          }
  
          // Replace the original word with its mirrored version (but keep punctuation)
          words[i] = words[i].replaceAll("[^a-zA-Z]", "") // Remove punctuation from the original word
                          .replace(word, replacement); // Replace the word itself
          mirrored = true;
      }
    */

    if (mirrored) {
        return String.join(" ", words) + "?";
    } else {
        return cannedResponses[random.nextInt(cannedResponses.length)];
    }
  }

  public static void main(String[] arguments) {

    Conversation myConversation = new Conversation();
    myConversation.chat();
    myConversation.printTranscript();

  }
}
