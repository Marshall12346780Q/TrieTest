import java.util.*;

public class Trie {
    TrieNode root;
    /***
     * Naive Trie implementation, inserted Strings are called words, terminated with null character.
     * TreeMap(k character, v Node) for storing child Nodes
     * Print all words uses a modified DFS.
     * @author Marshall Lang
     */
    public Trie(){
        this.root = new TrieNode();
    }

    public void printWords(){
        StringBuilder currentPrefix = new StringBuilder();
        Stack<traversalNode<Character, TrieNode>> traversalStack = new Stack<>();
        TrieNode currentNode = root;
        for (Map.Entry<Character, TrieNode> entry : currentNode.children.entrySet()) {
            Character k = entry.getKey();
            TrieNode v = entry.getValue();
            traversalStack.push(new traversalNode<>(k, v));
        }
        Stack<String> prefixStack = new Stack<String>();
        while (!traversalStack.isEmpty()){
            var currentStack = traversalStack.pop();
            currentPrefix.append(currentStack.c());
            currentNode = currentStack.n();//pop into currentNode
            if(currentStack.c() == '\u0000'){ //found null terminator, end of word
                System.out.println(currentPrefix.toString()); //can omit last character to avoid showing null terminator
                currentPrefix.delete(0, currentPrefix.length()); //wipe
                if(!prefixStack.isEmpty()){currentPrefix.append(prefixStack.pop());}//append common prefix that was saved at branch
                continue;
            }
            if(currentNode.children.size() > 1) {prefixStack.push(currentPrefix.toString());} //if branching save prefix
            for (Map.Entry<Character, TrieNode> entry : currentNode.children.entrySet()) {
                Character k = entry.getKey();
                TrieNode v = entry.getValue();
                traversalStack.push(new traversalNode<>(k, v));
            }
        }
    }

    public indexNode<Integer,TrieNode> findLastNode(String word){//returning null indicates found
        TrieNode current = root;
        int wordIndex = 0;
        while(wordIndex < word.length() && current.children.containsKey(word.charAt(wordIndex)))
        {
            current = current.children.get(word.charAt(wordIndex));
            if(wordIndex == word.length()-1 && current.children.containsKey('\u0000')){return null;}
            wordIndex++;
        }
        return new indexNode<Integer, TrieNode>(wordIndex, current);
    }
    //add is meant to be used following search, brittle?
    public void addNode(indexNode<Integer, TrieNode> entryPair, String word){
        TrieNode entry = entryPair.node();
        int wordIndex = entryPair.index(), length = word.length(); //advance to next character, nm no +1?
        while(wordIndex < length){ //was -1
            TrieNode temp = new TrieNode();
            //System.out.println("put: " + word.charAt(wordIndex));
            entry.children.put(word.charAt(wordIndex), temp); //adds and advances
            entry = temp; //can't be one-lined due to order of operations
            wordIndex++;
        }
        entry.children.put('\u0000',null);//standard null terminate
    }
}
record traversalNode<Character, TrieNode>(Character c, TrieNode n){}
record indexNode<Integer, TrieNode>(Integer index, TrieNode node){}

class TrieNode implements Comparable<TrieNode> {
    public TreeMap<Character, TrieNode> children = new TreeMap<Character,TrieNode>(); //sorted by keys
    public char character;
    public TrieNode() {}
    public int compareTo(TrieNode t2){
        return Character.compare(this.character, t2.character);
    }
}
