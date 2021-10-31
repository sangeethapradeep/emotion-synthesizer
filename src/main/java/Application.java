public class Application {
  public static void main(String[] args) {
    String[] texts = new String[] {"I am trying to imagine you with a personality", "This is shit"};
    Synthesizer synthesizer = new Synthesizer();
    synthesizer.run(texts);
  }
}
