package code.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

/**
 * Created by Piyush Patel.
 */
public class Numbers {

  public static void main(String[] args) {
    int[] in = {0, 2, 8, 5, 2, 1, 4, 13, 23};
    //List<Integer> out = getFibonnaciNumbers(in);
    //System.out.println(nextGreaterNumber(12543 ));
    System.out.println(lookandsayUtil("1", 4));
  }

  //Write a function that takes a number n and returns an array containing a Fibonacci sequence of length n
  // Time  = O(n), Space  = O(n)
  public static int[] fib(int n) {
    /* Declare an array to store Fibonacci numbers. */
    int f[];
    if (n == 0) {
      return null;
    } else if (n == 1) {
      f = new int[1];
      f[0] = 0;
    } else {
      f = new int[n];
      /* 0th and 1st number of the series are 0 and 1*/
      f[0] = 0;
      f[1] = 1;
      for (int i = 2; i < n; i++) {
        /* Add the previous 2 numbers in the series and store it */
        f[i] = f[i - 1] + f[i - 2];
      }
    }
    return f;
  }

  // Print nth Fibonacci number. Time  = O(n), space = (1)
  private static int fib1(int n) {
    int a = 0, b = 1, c, i;
    if (n == 0) {
      return a;
    }
    for (i = 2; i <= n; i++) {
      c = a + b;
      a = b;
      b = c;
    }
    return b;
  }

  //Given an array with positive number the task to find the largest subsequence from array that contain elements which are Fibonacci numbers.
  // input = {1 4 3 9 10 13 7} output = {1 3 13} in = {0 2 8 5 2 1 4 13 23} out = {0 2 8 5 2 1 13}
  private static List<Integer> getFibonnaciNumbers(int[] given) {
    ArrayList<Integer> output = new ArrayList<>();
    for (int x : given) {
      int nearestFib = getNthFibonacciNumber(x);
      if (x == nearestFib) {
        output.add(x);
      }
    }
    return output;
  }

  private static int getNthFibonacciNumber(int given) {
    int fN = 1;
    int fNPrev = 1;
    while (fN < given) {
      int temp = fN;
      fN = fN + fNPrev;
      fNPrev = temp;
    }
    System.out.println("Neartest to " + given + " is " + fN);
    return fN;
  }

  //Write a function that takes a number n and returns an array containing a Factorial of length n
  // Time  = O(n), Space  = O(n)
  public static int[] factorial(int n) {
    int result[];
    if (n == 0) {
      result = new int[1];
      result[0] = 1;
    } else {
      result = new int[n];
      result[0] = 1;
      for (int i = 1; i < n; i++) {
        result[i] = i * result[i - 1];
      }
    }
    return result;
  }

  //Print nth Factorial number. Time  = O(n), space = (1)
  public static int factorial1(int n) {
    int b = 1, c = 1;
    if (n == 0 || n == 1) {
      return b;
    } else {
      for (int i = 2; i <= n; i++) {
        b = i * c;
        c = b;
      }
    }
    return b;
  }

  //Function to calculate x raised to the power y in O(logn)
  int power(int x, int y) {
    int temp;
    if (y == 0) {
      return 1;
    }
    temp = power(x, y / 2);
    if (y % 2 == 0) {
      return temp * temp;
    } else {
      return x * temp * temp;
    }
  }

  /* Extended version of power function that can work for float x and negative y*/
  float power(float x, int y) {
    float temp;
    if (y == 0) {
      return 1;
    }
    temp = power(x, y / 2);
    if (y % 2 == 0) {
      return temp * temp;
    } else {
      if (y > 0) {
        return x * temp * temp;
      } else {
        return (temp * temp) / x;
      }
    }
  }

  //Implement decimal to roman and vice versa
  public int romanToInt(String s) {
    Map<Character, Integer> romans = new HashMap<Character, Integer>();
    romans.put('I', 1);
    romans.put('V', 5);
    romans.put('X', 10);
    romans.put('L', 50);
    romans.put('C', 100);
    romans.put('D', 500);
    romans.put('M', 1000);
    int intNum = 0;
    int prev = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
      int temp = romans.get(s.charAt(i));
      if (temp < prev) {
        intNum -= temp;
      } else {
        intNum += temp;
      }
      prev = temp;
    }
    return intNum;
  }

  public static String IntegerToRomanNumeral(int input) {
    if (input < 1 || input > 3999) {
      return "Invalid Roman Number Value";
    }
    String s = "";
    while (input >= 1000) {
      s += "M";
      input -= 1000;
    }
    while (input >= 900) {
      s += "CM";
      input -= 900;
    }
    while (input >= 500) {
      s += "D";
      input -= 500;
    }
    while (input >= 400) {
      s += "CD";
      input -= 400;
    }
    while (input >= 100) {
      s += "C";
      input -= 100;
    }
    while (input >= 90) {
      s += "XC";
      input -= 90;
    }
    while (input >= 50) {
      s += "L";
      input -= 50;
    }
    while (input >= 40) {
      s += "XL";
      input -= 40;
    }
    while (input >= 10) {
      s += "X";
      input -= 10;
    }
    while (input >= 9) {
      s += "IX";
      input -= 9;
    }
    while (input >= 5) {
      s += "V";
      input -= 5;
    }
    while (input >= 4) {
      s += "IV";
      input -= 4;
    }
    while (input >= 1) {
      s += "I";
      input -= 1;
    }
    return s;
  }

  // Find K Nearest points on a plane O(nlogk)
  //E.g. Stored: (0, 1) (0, 2) (0, 3) (0, 4) (0, 5) findNearest(new Point(0, 0), 3) -> (0, 1), (0, 2), (0, 3)
  class Point implements Comparable<Point> {

    int x, y;
    Double distance;

    public Point(int x, int y, Point original) {
      this.x = x;
      this.y = y;

      // sqrt(x^2 + y^2)
      distance = Math.hypot(x - original.x, y - original.y);
    }

    @Override
    public int compareTo(Point that) {
      return this.distance.compareTo(that.distance);
    }
  }

  public List<Point> findKNearestPoints(List<Point> points, Point original, int k) {
    List<Point> result = new ArrayList<>();
    if (points == null || points.size() == 0 || original == null || k <= 0) {
      return result;
    }
    PriorityQueue<Point> pq = new PriorityQueue<>(k);
    for (Point point : points) {
      if (pq.size() < k) {
        pq.offer(point);
      } else {
        if (pq.peek().compareTo(point) > 0) {
          pq.poll();
          pq.offer(point);
        }
      }
    }
    result.addAll(pq);
    return result;
  }

  // K nearest points using selection algorithm. Time = O(n) but worst can be O(n^2)
  // better to use Median of medians selection algorithm
  public static List<Point> findKNearestPointsSelection(final Point points[], final int k) {
    final int n = points.length;
    final double[] dist = new double[n];
    for (int i = 0; i < n; i++) {
      dist[i] = Math.sqrt(points[i].x * points[i].x + points[i].y * points[i].y);
    }
    final double kthMin = kthSmallest(dist, 0, n - 1, k - 1);
    List<Point> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      final double d = Math.sqrt(points[i].x * points[i].x + points[i].y * points[i].y);
      if (d <= kthMin) {
        result.add(points[i]);
      }
    }
    return result;
  }

  //Median Of Medians worst case time O(n)
  public static double MedianOfMediansSelect(double[] A, int low, int high, int k) {
    if (high - low + 1 <= 5) {
      Arrays.sort(A, low, high);
      return A[low + k - 1];
    }
    int noOfGroups = (high - low + 1) / 5;
    double[] medianArray = new double[noOfGroups];
    for (int i = 0; i < noOfGroups; i++) {
      medianArray[i] = MedianOfMediansSelect(A, low + i * 5, low + (i * 5) + 4, 3);
    }
    double medianOfMedians = MedianOfMediansSelect(medianArray, 0, medianArray.length - 1,
        noOfGroups / 2 + 1);
    //swap(A, medianOfMedians, high);
    int medianOfMediansPosition = partition1(A, low, high, medianOfMedians);
    if (medianOfMediansPosition - low + 1 == k) {
      return A[low + k - 1];
    } else if (k < medianOfMediansPosition - low + 1) {
      return MedianOfMediansSelect(A, low, medianOfMediansPosition - 1, k);
    } else {
      return MedianOfMediansSelect(A, medianOfMediansPosition + 1, high,
          k - (medianOfMediansPosition - low + 1));
    }
  }

  public static int partition1(double[] G, int first, int last, double pivot) {
    int i;
    for (i = first; i < last; i++) {
      if (G[i] == pivot) {
        break;
      }
    }
    swap(G, i, last - 1);
    i = first;
    int pIndex = first;
    for (i = first; i < last; i++) {
      if (G[i] < pivot) {
        swap(G, i, pIndex);
        pIndex++;
      }
    }
    swap(G, pIndex, last - 1);
    return pIndex;
  }

  // kth smallest element in unsorted array
  public static double kthSmallest(double[] G, int first, int last, int k) {
    if (first <= last) {
      //int pivot = partition(G, first, last);
      int pivot = randomPartition(G, first, last);
      if (pivot == k) {
        return G[k];
      }
      if (pivot > k) {
        return kthSmallest(G, first, pivot - 1, k);
      } else {
        return kthSmallest(G, pivot + 1, last, k);
      }
    }
    return 0;
  }

  // Picks a random pivot element between l and r and partitions
  public static int randomPartition(double arr[], int l, int r) {
    int pivot = (int) Math.round(l + Math.random() * (r - l));
    swap(arr, pivot, r);
    return partition(arr, l, r);
  }

  public static int partition(double[] G, int first, int last) {
    double pivot = G[last];
    int pIndex = first;
    for (int i = first; i < last; i++) {
      if (G[i] < pivot) {
        swap(G, i, pIndex);
        pIndex++;
      }
    }
    swap(G, pIndex, last);
    return pIndex;
  }

  private static void swap(double[] G, int x, int y) {
    double temp = G[y];
    G[y] = G[x];
    G[x] = temp;
  }

  //Calculate the angle between hour hand and minute hand
  int calcAngle(int h, int m) {
    if (h < 0 || m < 0 || h > 12 || m > 60) {
      System.out.print("Wrong input");
    }
    if (h == 12) {
      h = 0;
    }
    if (m == 60) {
      m = 0;
    }
    int hour_angle = (h * 60 + m) / 2;
    int minute_angle = 6 * m;
    // Find the difference between two angles
    int angle = Math.abs(hour_angle - minute_angle);
    // Return the smaller angle of two possible angles
    angle = Math.min(360 - angle, angle);
    return angle;
  }

  //Returns true if the input string is a number and false otherwise
  public static boolean isNumber(String toTest) {
    boolean flag = false;
    // implementation here
    String pattern = "-?\\d+(\\.\\d+)?";
    flag = toTest.matches(pattern);
    return flag;
  }

  /*Given a nested list of integers, returns the sum of all integers in the list weighted by their depth
   * For example, given the list {{1,1},2,{1,1}} the function should return 10 (four 1's at depth 2, one 2 at depth 1)
   * Given the list {1,{4,{6}}} the function should return 27 (one 1 at depth 1, one 4 at depth 2, one 6 at depth2)*/
  class NestedInteger {

    boolean isInteger() {
      return true;
    }

    int getInteger() {
      return 1;
    }

    List<NestedInteger> getList() {
      return new ArrayList<NestedInteger>();
    }
  }

  private int getListSum(List<NestedInteger> lni, int depth) {
    int sum = 0;
    for (NestedInteger ni : lni) {
      if (ni.isInteger()) {
        sum += ni.getInteger() * depth;
      } else {
        sum += getListSum(ni.getList(), depth + 1);
      }
    }
    return sum;
  }

  public int getSum(NestedInteger ni) {
    if (ni.isInteger()) {
      return ni.getInteger();
    } else {
      return getListSum(ni.getList(), 1);
    }
  }

  //Squareroot of a Number - O(logN)
  //2^0.5logN
  private int sqrt(int num) {
    if (num < 0) {
      return 0;
    }
    if (num == 1) {
      return 1;
    }
    int low = 0;
    int high = 1 + (num / 2);
    int mid;
    int square;
    while (low + 1 < high) {
      mid = low + (high - low) / 2;
      square = mid * mid;
      if (square == num) {
        return mid;
      } else if (square < num) {
        low = mid;
      } else {
        high = mid;
      }
    }
    return low;
  }

  //In "the 100 game," two players take turns adding, to a running total, any integer from 1..10.
  // The player who first causes the running total to reach or exceed 100 wins.
  boolean canIWin(int maxChoosableInteger, int desiredTotal) {
    int[] numbers = new int[maxChoosableInteger];
    int sum = 0;
    for (int i = 0; i < maxChoosableInteger; ++i) {
      numbers[i] = i + 1;
    }
    return canWin(numbers, desiredTotal, sum);
  }

  boolean canWin(int numbers[], int desiredTotal, int sum) {
    for (int i = 0; i < numbers.length; i++) {
      int temp = sum + numbers[i];
      if ((desiredTotal - temp) % 11 == 0) {
        sum = temp;
      }
    }
    if (sum >= 100) {
      return true;
    }
    return false;
  }

  // Given a stream of unsorted integers, find the median element in sorted order at any given time.
  int numOfElements = 0;
  PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
  PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new Comparator<Integer>() {
    @Override
    public int compare(Integer o1, Integer o2) {
      return o2 - o1;
    }
  });

  public void addNumberToStream(Integer num) {
    maxHeap.add(num);
    if (numOfElements % 2 == 0) {
      if (minHeap.isEmpty()) {
        numOfElements++;
        return;
      } else if (maxHeap.peek() > minHeap.peek()) {
        Integer maxHeapRoot = maxHeap.poll();
        Integer minHeapRoot = minHeap.poll();
        maxHeap.add(minHeapRoot);
        minHeap.add(maxHeapRoot);
      }
    } else {
      minHeap.add(maxHeap.poll());
    }
    numOfElements++;
  }

  public Double getMedian() {
    if (numOfElements % 2 != 0) {
      return new Double(maxHeap.peek());
    } else {
      return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
  }

  // Returns the maximum value that can be put in a knapsack of capacity W
  int knapSack(int W, int wt[], int val[], int n) {
    int i, j;
    int K[][] = new int[n + 1][W + 1];
    // Build table K[][] in bottom up manner
    for (i = 0; i <= n; i++) {
      for (j = 0; j <= W; j++) {
        if (i == 0 || j == 0) {
          K[i][j] = 0;
        } else if (wt[i - 1] <= j) {
          K[i][j] = Math.max(val[i - 1] + K[i - 1][j - wt[i - 1]], K[i - 1][j]);
        } else {
          K[i][j] = K[i - 1][j];
        }
      }
    }
    return K[n][W];
  }

  //Divide without Division
  //https://discuss.leetcode.com/topic/15568/detailed-explained-8ms-c-solution
  public static int divide(int dividend, int divisor) {
    if (divisor == 0 || dividend == Integer.MIN_VALUE && divisor == -1) {
      return Integer.MAX_VALUE;
    }
    int res = 0;
    int sign = (dividend < 0) ^ (divisor < 0) ? -1 : 1;
    long dvd = Math.abs((long) dividend);
    long dvs = Math.abs((long) divisor);
    while (dvs <= dvd) {
      long temp = dvs, mul = 1;
      while (dvd >= temp << 1) {
        temp <<= 1;
        mul <<= 1;
      }
      dvd -= temp;
      res += mul;
    }
    return sign == 1 ? res : -res;
  }

  public void divide1(int N, int D) {
    int result = 0;
    if (D == 0) {
      System.out.println("Cannot divide by 0");
    } else if (N == 0) {
      System.out.println(0);
    } else if (N == D) {
      System.out.println(1);
    } else if (N > 0 && D > 0 && N < D) {
      System.out.println(0);
    } else {
      // both negative
      if (N < 0 && D < 0) {
        while (N <= D) {
          N += -1 * D;
          result++;
        }
        System.out.println(result);
      }
      // either N or D negative
      else if (N < 0 || D < 0) {
        if (N < 0) {
          N = -1 * N;
        } else {
          D = -1 * D;
        }
        while (N >= D) {
          N -= D;
          result--;
        }
        System.out.println(result);
      }
      // both positive
      else {
        while (N >= D) {
          N -= D;
          result++;
        }
        System.out.println(result);
      }
    }
  }


  //Given a decimal number, write a function that returns its negabinary (i.e. negative 2-base) representation as a string.
  // 2 = 1 1 0 , 15  = 110001
  private static String negaBinary(int x) {
    StringBuilder sb = new StringBuilder();
    while (x != 0) {
      int rem = x % -2;
      x = x / -2;
      if (rem < 0) {
        rem += 2;
        x += 1;
      }
      sb.append(rem);
    }
    return sb.reverse().toString();
  }

  //getting a random items from a collection in constant time
  public static <T> Set<T> randomSample(List<T> items, int m) {
    Random rnd = new Random();
    HashSet<T> res = new HashSet<>(m);
    int n = items.size();
    for (int i = n - m; i < n; i++) {
      int pos = rnd.nextInt(i + 1);
      T item = items.get(pos);
      if (res.contains(item)) {
        res.add(items.get(i));
      } else {
        res.add(item);
      }
    }
    return res;
  }

  //for stream of collections items
  public static <T> List<T> reservoirSample(Iterable<T> items, int m) {
    ArrayList<T> res = new ArrayList<>(m);
    Random rnd = new Random();
    int count = 0;
    for (T item : items) {
      count++;
      if (count <= m) {
        res.add(item);
      } else {
        int r = rnd.nextInt(count);
        if (r < m) {
          res.set(r, item);
        }
      }
    }
    return res;
  }

  //Write a function that returns values randomly, according to their weight.
  public static String RandomByWeight(List<String> input, HashMap<String, Integer> weightFunc) {
    int totalWeight = 0; // this stores sum of weights of all elements before current
    String selected = input.get(0); // currently selected element
    for (String data : input) {
      int weight = weightFunc.get(data); // weight of current element
      int r = (int) (Math.random() * (totalWeight + weight)); // random value
      if (r >= totalWeight) // probability of this is weight/(totalWeight+weight)
      {
        selected = data; // it is the probability of discarding last selected element and selecting current one instead
      }
      totalWeight += weight; // increase weight sum
    }
    return selected; // when iterations end, selected is some element of sequence.
  }

  //return a random number from (0,...,n-1) with given weights
  private static int randomNumber(int[] weights) {
    if (weights == null || weights.length == 0)    return 0;
    int n = weights.length;
    for (int i = 1; i < n; i++)
      weights[i] += weights[i - 1];//[1,2,4,5,1,3] -> [1,3,7,12,13,16]
    Random rand = new Random();
    int num = rand.nextInt(weights[n - 1]);//num is from 0 to 15
    return binarySearch(weights, 0, n - 1, num);//returned value is from 0 to n-1
  }
  //find the leftmost mid that target < weights[mid]; eg.target=10,weight[mid]=12
  private static int binarySearch(int[] weights, int start, int end, int target) {
    while (start < end) {
      int mid = start + (end - start) / 2;
      if (target <= weights[mid]) 	end = mid;
      else 	start = mid + 1;
    }
    return start;
  }

  //checks whether an int is prime or not.
  boolean isPrime(int n) {
    //check if n is a multiple of 2
    if (n % 2 == 0) {
      return false;
    }
    //if not, then just check the odds
    for (int i = 3; i * i <= n; i += 2) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }

  //Arrange Given Numbers To Form The Biggest Number Possible
  private static int biggestNumber(int number) {
    List<Integer> digits = numbertoDigits(number);
    Collections.sort(digits);
    return digitToNumber(digits);
  }

  private static int digitToNumber(List<Integer> digits) {
    int number = 0;
    int base = 1;
    for (int i = digits.size() - 1; i >= 0; i--) {
      number += digits.get(i) * base;
      base *= 10;
    }
    return number;
  }

  private static List<Integer> numbertoDigits(int number) {
    List<Integer> digits = new ArrayList<>();
    while (number > 0) {
      digits.add(0, number % 10);
      number /= 10;
    }
    return digits;
  }

  //How can you get the next greater number with the same set of digits?
  //finding the greatest number (but less than the given number) by reordering the digits in the number
  private static int nextGreaterNumber(int number) {
    List<Integer> digits = numbertoDigits(number);
    for (int i = digits.size() - 2; i >= 0; i--) {
      if (digits.get(i) < digits.get(i + 1)) {
        for (int j = digits.size() - 1; j > i; j--) {
          if (digits.get(j) > digits.get(i)) {
            int temp = digits.get(j);
            digits.set(j, digits.get(i));
            digits.set(i, temp);
            Collections.sort(digits.subList(i + 1, digits.size()));
            return digitToNumber(digits);
          }
        }
      }
    }
    return -1;
  }

  /* Count and say: Given a  number start and a number of iteration n, calculate the nth number in a "Look and Say" sequence starting with start.
      Reusing the previous example with start = 11 and n = 2, LookAndSay(11, 2) = 1211 because LookAndSay(LookAndSay(11)) = 1211 */
  public static String lookandsayUtil(String num, int n) {
    for (int i = 0; i < n; i++) {
      num = lookandsay(num);
    }
    return num;
  }

  public static String lookandsay(String number) {
    StringBuilder result = new StringBuilder();
    char say = number.charAt(0);
    int times = 1;
    for (int i = 1;i<number.length();i++) {
      char actual = number.charAt(i);
      if (actual != say) {
        result.append(times + "" + say);
        times = 1;
        say = actual;
      } else {
        times ++;
      }
    }
    result.append(times).append(say);
    return result.toString();
  }

  //String to integer
  public static int atoi(String str) {
    int index = 0, sign = 1, total = 0;
    //1. Empty string
    if (str.length() == 0) {
      return 0;
    }

    //2. Remove Spaces
    while (str.charAt(index) == ' ' && index < str.length()) {
      index++;
    }

    //3. Handle signs
    if (str.charAt(index) == '+' || str.charAt(index) == '-') {
      sign = str.charAt(index) == '+' ? 1 : -1;
      index++;
    }

    //4. Convert number and avoid overflow
    while (index < str.length()) {
      int digit = str.charAt(index) - '0';
      if (digit < 0 || digit > 9) {
        break;
      }

      //check if total will be overflow after 10 times and add digit
      if (Integer.MAX_VALUE / 10 < total
          || Integer.MAX_VALUE / 10 == total && Integer.MAX_VALUE % 10 < digit) {
        return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
      }

      total = 10 * total + digit;
      index++;
    }
    return total * sign;
  }

  // find factors of number
  static void findFactors(int num){
    for(long i = 1; i <= Math.sqrt(num); i++) {
      if(num % i == 0) {
        System.out.println(i);
        if(i != num/i) {
          System.out.println(num/i);
        }
      }
    }
  }
}

