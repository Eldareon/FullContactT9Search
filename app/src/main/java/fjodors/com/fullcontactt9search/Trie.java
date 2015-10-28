package fjodors.com.fullcontactt9search;


import android.util.Log;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Fjodors on 2015.10.11..
 */
public class Trie {

    private Node root = new Node();

    public static final ImmutableMap<Character, Character> T9MAP = ImmutableMap.<Character, Character>builder()
            .put('a', '2')
            .put('b', '2')
            .put('c', '2')
            .put('d', '3')
            .put('e', '3')
            .put('f', '3')
            .put('g', '4')
            .put('h', '4')
            .put('i', '4')
            .put('j', '5')
            .put('k', '5')
            .put('l', '5')
            .put('m', '6')
            .put('n', '6')
            .put('o', '6')
            .put('p', '7')
            .put('q', '7')
            .put('r', '7')
            .put('s', '7')
            .put('t', '8')
            .put('u', '8')
            .put('v', '8')
            .put('w', '9')
            .put('x', '9')
            .put('y', '9')
            .put('z', '9')
            .build();

    private class Node {

        private List<String> words;
        private SortedMap<Character, Node> next;

        public Node() {
            next = new TreeMap<>();
            words = new ArrayList<>();
        }

    }


    public void loadDictionary(List<String> Words) {

        for (String word : Words) {
            Node current = root;
            for (int i = 0; i < word.length(); i++) {
                if (!current.next.containsKey(T9MAP.get(word.charAt(i)))) {
                    Node n = new Node();
                    current.next.put(T9MAP.get(word.charAt(i)), n);
                }
                current = current.next.get(T9MAP.get(word.charAt(i)));
            }
            current.words.add(word);
        }
    }


    public List<String> lookup(String numbers) {

        if(numbers.length() <= 0)
            return Collections.emptyList();

        Node current = root;
        for (int i = 0; i < numbers.length(); i++) {
            if (current.next.get(numbers.charAt(i)) == null) {
                return Collections.emptyList();
            }
            current = current.next.get(numbers.charAt(i));
        }

        return bfs_search(current);
    }

    public List<String> bfs_search(Node currentRoot) {

        Queue<Node> q = new LinkedList<Node>();
        List<String> words = new ArrayList<String>();
        q.add(currentRoot);

        while (!q.isEmpty()) {
            Node tempNode = q.remove();
            if (tempNode.words != null && tempNode.words.size() > 0) {
                for (String word : tempNode.words) {
                    words.add(word);
                }
            }

            if (tempNode.next != null) {
                Iterator it = tempNode.next.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    q.add((Node) pair.getValue());
                }
            }
        }
        return words;
    }
}
