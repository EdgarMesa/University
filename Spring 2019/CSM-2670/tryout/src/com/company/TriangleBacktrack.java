package com.company;
import java.util.*;
import java.util.ArrayList;

public class TriangleBacktrack
{
  private static Integer[] triangle = new Integer[9];

  private static void printTriangle(List list)
  {
    if (list.size() != 9)
    {
      return;
    }

    System.out.println("   " + list.get(0));
    System.out.println("  " + list.get(8) + " " + list.get(1));
    System.out.println(" " + list.get(7) + "   " + list.get(2));
    System.out.println(list.get(6) + " " + list.get(5) + " " + list.get(4) + " " + list.get(3));
  }

  private static boolean findTriangle(int position, List<Integer> free)
  {

    if (position == 4 && triangle[0] + triangle[1] + triangle[2] + triangle[3] != 20)
    {
      return false;
    }

    if (position == 7 && 
        (triangle[3] + triangle[4] + triangle[5] + triangle[6] != 20 ||
         triangle[0] * triangle[3] * triangle[6] % 2 == 0))
    {
      return false;
    }

    if (position == 9)
    {
      return triangle[6] + triangle[7] + triangle[8] + triangle[0] == 20;
    }

    // Try every free value in our position
    for (Integer temp : free)
    {
      List<Integer> left = new LinkedList<Integer>(free);
      left.remove(temp);
      triangle[position] = temp;
      if (findTriangle(position + 1, left))
      {
        return true;
      }
    }

    return false;
  }

  public static void main(String[] args)
  {
    List<Integer> free = new ArrayList<Integer>(9);
    for (int i = 1; i <= 9; i++)
    {
      free.add(i);
    }

    if (findTriangle(0, free))
    {
      printTriangle(Arrays.asList(triangle));
    }
    else
    {
      System.out.println("No Solution!");
    }
  }
}
