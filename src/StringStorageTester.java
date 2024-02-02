import java.util.Scanner;
public class StringStorageTester {
    /***
     * Tester to compare structures for String storage: Tries
     * @author Marshall Lang
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter Strings line by line, ~ to Stop:"); //READ IN STRINGS, FROM ONlINE?, STORE IN TRIE STORE IN BALANCED BST
        String word = "";
        Trie trieTest = new Trie();

        while(!word.equals("~")){
            word = reader.nextLine();
            indexNode<Integer, TrieNode> check = trieTest.findLastNode((word));
            if(check == null){
                System.out.println(word + " already in trie");
            }
            else{
                trieTest.addNode(check, word);
                System.out.println(word + " added");
            }
        }

        System.out.println("ENTRIES COMPLETED. TRIE CONTENTS: ");
        trieTest.printWords(); //Null terminator included to show ending whitespace, which creates a distinct String as intended.
    }
}