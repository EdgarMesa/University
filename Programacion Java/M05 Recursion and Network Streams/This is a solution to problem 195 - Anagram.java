// This is a solution to problem 195 - Anagram, on uva.onlinejudge.org.
// I think this solution is too complex. I got stuck on the idea of using a
// multi-set to hold the letters of the anagram as it was being built. Java
// does not have a multi-set so built one out of a TreeMap. The keys are the
// letters of the anagram and the values are how many times that the letter
// appears. In general, I am using backtracking and storing the current state
// in this TreeMap. I invite the programing team to simplify this solution or
// to demonstrate another technique.

import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

class Main
{

    // Normal use of I/O classes
    private static final Scanner CIN = new Scanner(System.in);
    private static final PrintStream COUT = new PrintStream(new BufferedOutputStream(System.out));

    public static void main(String[] args)
    {
        // First token is the number of cases
        int numberOfCases = CIN.nextInt();

        // For each case... solve it
        for(int i = 0; i < numberOfCases; i++)
        {
            // Build the initial state from the given word and generate all of
            // it anagrams
            makeAnagrams(new State(CIN.next()));
        }

        // Make sure that all of the output has been written
        COUT.flush();
    }

    private static void makeAnagrams(State state)
    {
        // Base case is that there are no more letters to use. In this
        // case print the current state and back up.
        if(state.isEmpty())
        {
            COUT.println(state);
            return;
        }

        // The pop will return the character in the current state that
        // is larger than its input (null if there is none). Thus,
        // this loop will try to build an anagram with each distinct
        // letter in the current state. The null character is used at
        // the start as every letter is larger than it.
        Character last = state.pop('\0');
        while(last != null)
        {
            // Build an anagram with the current state
            makeAnagrams(state);

            // Restore the old state
            state.pushBack();

            // Try the next letter
            last = state.pop(last);
        }
    }

    private static class State
    {
        // The map will hold the unused letters and their frequencies
        private final TreeMap<Character, Integer> map;

        // This linked list will be used as a stack to restore the map when
        // letters are removed from it
        private final LinkedList<Character> stack = new LinkedList<>();

        // A buffer to hold each anagram as it is bing built.
        private final StringBuilder outputBuffer;

        // Initialize the starting state with a given word
        public State(String s)
        {
            // Make a buffer to hold each anagram
            outputBuffer = new StringBuilder(s.length());

            // The map will start with the frequencies of the letters in s.
            // As the default ordering of characters does not satisfy the
            // requirements of the problem, a custom comparator is used.
            map = new TreeMap<>(AlphaComparator.getComparator());
            for(int i = 0; i < s.length(); i++)
            {
                char c = s.charAt(i);
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        }

        // Are there any letters left in the map?
        public boolean isEmpty() { return map.isEmpty(); }

        // Returns and removes the smallest character in the map that is larger
        // than c, null if there is none
        public Character pop(Character c)
        {
            // Get the entry for the the smallest item in the map that is
            // larger than c
            Entry<Character, Integer> e = map.higherEntry(c);

            // If there is no such item then return null
            if(e == null) return null;

            // Save the item on the stack so it can be restored later
            stack.addLast(e.getKey());

            // Remove the item from the map. If it's current count is > 1 then
            // just update the count. Otherwise remove its entry completely.
            int value = e.getValue() - 1;
            if(value > 0) map.replace(e.getKey(), value);
            else map.remove(e.getKey());

            // Update the output buffer and return the item
            outputBuffer.append(e.getKey());
            return e.getKey();
        }

        // Restore the state from the last pop.
        public void pushBack()
        {
            // Remove the item added to the output buffer
            outputBuffer.deleteCharAt(outputBuffer.length() - 1);

            // Add the item back to the map
            Character c = stack.pollLast();
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        @Override
        public String toString()
        {
            return outputBuffer.toString();
        }

    }

    // The problem states that: The words generated from the same input word
    // should be output in alphabetically ascending order. An upper case letter
    // goes before the corresponding lower case letter. This comparator gives
    // such an ordering.
    public static class AlphaComparator implements Comparator<Character>
    {
        private static final AlphaComparator THERE_CAN_BE_ONLY_ONE = new AlphaComparator();

        public static AlphaComparator getComparator(){return THERE_CAN_BE_ONLY_ONE;}

        @Override
        public int compare(Character a, Character b)
        {
            boolean aIsUpper = Character.isUpperCase(a);
            boolean bIsUpper = Character.isUpperCase(b);
            a = Character.toLowerCase(a);
            b = Character.toLowerCase(b);
            if(a < b) return -1;
            if(a > b) return  1;
            if(aIsUpper & !bIsUpper) return -1;
            if(!aIsUpper & bIsUpper) return  1;
            return 0;
        }
    }

}
