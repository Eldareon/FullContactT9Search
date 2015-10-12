package fjodors.com.fullcontactt9search;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Fjodors on 2015.10.11..
 */
public class Trie {

    private Node root = new Node();

    Map<Character, Character> t9map = new HashMap<Character, Character>();

    public Trie() {
        t9map.put('a', '2');
        t9map.put('b', '2');
        t9map.put('c', '2');
        t9map.put('d', '3');
        t9map.put('e', '3');
        t9map.put('f', '3');
        t9map.put('g', '4');
        t9map.put('h', '4');
        t9map.put('i', '4');
        t9map.put('j', '5');
        t9map.put('k', '5');
        t9map.put('l', '5');
        t9map.put('m', '6');
        t9map.put('n', '6');
        t9map.put('o', '6');
        t9map.put('p', '7');
        t9map.put('q', '7');
        t9map.put('r', '7');
        t9map.put('s', '7');
        t9map.put('t', '8');
        t9map.put('u', '8');
        t9map.put('v', '8');
        t9map.put('w', '9');
        t9map.put('x', '9');
        t9map.put('y', '9');
        t9map.put('z', '9');
    }


    private class Node {

        private List<String> words;
        private SortedMap<Character, Node> next;

        public Node() {
            next = new TreeMap<Character, Node>();
            words = new ArrayList<String>();
        }

    }


    public void loadDictionary(List<String> Words) {

        for (String word : Words) {
            Node current = root;
            for (int i = 0; i < word.length(); i++) {
                if (!current.next.containsKey(t9map.get(word.charAt(i)))) {
                    Node n = new Node();
                    current.next.put(t9map.get(word.charAt(i)), n);
                }
                current = current.next.get(t9map.get(word.charAt(i)));
            }
            current.words.add(word);
        }
    }


    public List<String> lookup(String numbers) {

        Node current = root;
        for (int i = 0; i < numbers.length(); i++) {
            if (current.next.get(numbers.charAt(i)) == null) {
                current.words = Collections.emptyList();
                break;
            }
            current = current.next.get(numbers.charAt(i));
        }

        return current.words;
    }
}