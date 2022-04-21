package com.company;
public class Change
{
  private static int count = 0;

  private static void findChange(int p, int n, int d, int q, boolean startedDimes, boolean startedQuarters)
  {

    if (p < 0)
    {
      return;
    }

    count++;

    System.out.println("P: " + p
                       + ", N: " + n
                       + ", D: " + d
                       + ", Q: " + q);

    if (!startedDimes)
    {
      findChange(p - 5, n + 1, d, q, false, false);
    }

    if (!startedQuarters)
    {
      findChange(p - 10, n, d + 1, q, true, false);
    }

    findChange(p - 25, n, d, q + 1, true, true);
  }

  public static void main(String[] args)
  {
    findChange(300, 45, 0, 0, false, false);
    System.out.println("Count: " + count);
  }
}
