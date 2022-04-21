package com.company;
import java.util.Set;
import java.util.TreeSet;

public class CombinationsWithSet
{

    public static void main(String[] args)
    {
        String s = "GOOGLE";
        combinations(s, 3);
    }

    public static void combinations(String s, int length)
    {
        Set<Character> all = new TreeSet<>();
        for (char c : s.toCharArray())
        {
            all.add(c);
        }

        combinations(all, new StringBuilder(), length);
    }

    private static void combinations(Set<Character> s, StringBuilder chosen, int length)
    {
        if (length == 0)
        {
            System.out.println(chosen);         // base case: no choices left
            return;
        }
        
        Set<Character> copy = new TreeSet<>();
        copy.addAll(s);

       for(Character c : s)
        {
            if (chosen.indexOf("" + c) == -1)
            {
                copy.remove(c);
                chosen.append(c);
                combinations(copy, chosen, length - 1);
                chosen.deleteCharAt(chosen.length() - 1);
                copy.add(c);
            }
        }
    }

}
