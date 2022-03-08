package code.ds;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Piyush Patel.
 */
public class MISC {

  public static void main(String[] args) throws InterruptedException {
    Interval i1 = new Interval(10, 15);
    Interval i2 = new Interval(25, 35);
    Interval i3 = new Interval(45, 65);
    Interval i4 = new Interval(85, 95);
    ArrayList<Interval> list = new ArrayList<>();
    list.add(i3);
    list.add(i2);
    list.add(i4);
    list.add(i1);
    Interval target = new Interval(17, 100);
    //System.out.print(find_min_intervals(list, target));
    //drawCircle(2);
    for(Interval i : findInterval(list, target)){
      System.out.println(i.start + " " + i.end);
    }


  }

  //Given a set of time intervals in any order, merge all overlapping intervals into one and output the result
  //{{1,3}, {2,4}, {5,7}, {6,8} }. output {1, 4} and {5, 8} Time Complexity: O(n Log n)
  // merge overlapping intervals
  static class Interval {

    int start;
    int end;

    Interval(int s, int e) {
      start = s;
      end = e;
    }
  }

  private static class IntervalComparator implements Comparator<Interval> {

    @Override
    public int compare(Interval a, Interval b) {
      return a.start < b.start ? -1 : a.start == b.start ? 0 : 1;
    }
  }

  //in place merge interval with space O(1) and time O(nlogn) with modifying input
  public static List<Interval> mergeIntervalsInPlace(List<Interval> intervals) {
    if (intervals == null) {
      throw new IllegalArgumentException();
    }
    Collections.sort(intervals, new IntervalComparator());
    Iterator<Interval> it = intervals.iterator();
    Interval prev = it.hasNext() ? it.next() : null;
    while (it.hasNext()) {
      Interval next = it.next();
      if (prev.end >= next.start) {
        prev.end = Math.max(prev.end, next.end);
        it.remove();
      } else {
        prev = next;
      }
    }
    return intervals;
  }

  //Space is O(n)
  public static List<Interval> mergeIntervals(List<Interval> intervals) {
    Collections.sort(intervals, new IntervalComparator());
    LinkedList<Interval> merged = new LinkedList<>();
    for (Interval interval : intervals) {
      // if the list of merged intervals is empty or if the current
      // interval does not overlap with the previous, simply append it.
      if (merged.isEmpty() || merged.getLast().end < interval.start) {
        merged.add(interval);
      }
      // otherwise, there is overlap, so we merge the current and previous intervals.
      else {
        merged.getLast().end = Math.max(merged.getLast().end, interval.end);
      }
    }
    return merged;
  }

  //Find least number of intervals from A that can fully cover B
  //A =[[0,3],[3,4],[4,6],[2,7]] B =[0,6] return 2
  //A =[[0,3],[4,7]] B =[0,6] return 0
  static int find_min_intervals(List<Interval> intervals, Interval target) {
    // Sort Intervals in decreasing order of start time
    intervals.sort(new Comparator<Interval>() {
      @Override
      public int compare(Interval i1, Interval i2) {
        if (i1.start == i2.start) {
          return i2.end - i1.end;
        }
        return i1.start - i2.start;
      }
    });
    int i = 0;
    int start = target.start;
    int max_end = -1;
    int num = 0;
    while (i < intervals.size() && max_end < target.end) {
      if (intervals.get(i).end <= start) {
        i++;
      } else {
        if (intervals.get(i).start > start) {
          break;
        }
        while (i < intervals.size() && max_end < target.end
            && intervals.get(i).start <= start) {
          max_end = Math.max(max_end, intervals.get(i).end);
          i++;
        }
        if (start != max_end) {
          start = max_end;
          num++;
        }
      }
    }
    if (max_end < target.end) {
      return 0;
    }
    return num;
  }

  /* You have a number of envelopes with widths and heights given as a pair of integers (w, h). One envelope can fit
  into another if and only if both the width and height of one envelope is greater than the width and height of the other envelope.
  What is the maximum number of envelopes can you Russian doll? (put one inside other)*/
  static int maxEnvelope(ArrayList<Interval> intervals) {
    if (intervals.size() == 0 || intervals.size() == 1) {
      return 0;
    }
    // Sort Intervals in Ascend on width and descend on height if width are same.
    intervals.sort(new Comparator<Interval>() {
      @Override
      public int compare(Interval i1, Interval i2) {
        if (i1.start == i2.start) {
          return i2.end - i1.end;
        }
        return i1.start - i2.start;
      }
    });
    Interval first = intervals.get(0);
    int width = first.start;
    int height = first.end;
    int count = 0;
    for (int i = 1; i < intervals.size(); i++) {
      Interval curr = intervals.get(i);
      if (width < curr.start && height < curr.end) {
        count++;
      }
      width = curr.start;
      height = curr.end;
    }
    return count + 1;
  }

  /**
   * Adds an interval [from, to] into internal structure. https://github.com/yubinbai/leetcode/blob/master/common_questions/add_interval_total_length/AddInterval.java
   */
  private static List<Interval> intervals = new ArrayList<>();
  static int coverage = 0;

  static void addInterval(int from, int to) {
    Interval interval = new Interval(from, to);
    intervals.add(interval);
  }

  // in place add interval time = O(n) space O(1)
  static void addInterval1(int from, int to) {
    Interval newInterval = new Interval(from, to);
    if (intervals.size() == 0) {
      intervals.add(newInterval);
      coverage = newInterval.end - newInterval.start;
      return;
    }
    Iterator<Interval> it = intervals.iterator();
    while (it.hasNext()) {
      Interval prev = it.next();
      if (prev.end >= newInterval.start) {
        newInterval.end = Math.max(prev.end, newInterval.end);
        newInterval.start = Math.min(prev.start, newInterval.start);
        coverage -= prev.end - prev.start;
        it.remove();
      }
      intervals.add(newInterval);
      coverage += newInterval.end - newInterval.start;
    }
    /*List<Interval> result = new LinkedList<>();
    int i = 0;
    // add all the intervals ending before newInterval starts
    while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
      i++;
    }
    // merge all overlapping intervals to one considering newInterval
    while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
      newInterval.start =
          Math.min(newInterval.start, intervals.get(i).start);
      newInterval.end =
          Math.max(newInterval.end, intervals.get(i).end);
      i++;
    }
    result.add(newInterval); // add the union of intervals we got
    // add all the rest
    while (i < intervals.size()) {
      result.add(intervals.get(i++));
    }*/
  }

  private int getCoverageOfIntervals() {
    return coverage;
  }

  /*Given a list of tuples representing intervals, return the range these UNIQUE intervals
  covered. e.g: [(1,3),(2,5),(8,9)] should return 5
  a) 1 2 3 = 2 unique intervals (1 to 2, 2 to 3)
  b) 2 3 4 5 = 2 unique intervals ( 3 to 4, 4 to 5) We did not count 2 - 3 since it was already counted.
  c) 8 9 = 1 unique interval
  result = 2 + 2 + 1 = 5 */
  private int getCoverageOfIntervals(ArrayList<Interval> intervals) {
    if (intervals.isEmpty()) {
      return 0;
    }

    Collections.sort(intervals, new IntervalComparator());
    int len = 0;
    Interval prev = intervals.get(0);

    for (int i = 1; i < intervals.size(); i++) {
      Interval curr = intervals.get(i);

      if (prev.end > curr.start) {
        prev.end = Math.max(prev.end, curr.end);
      } else {
        len += prev.end - prev.start;
        prev = curr;
      }
    }

    len += prev.end - prev.start; // Be very careful to check this case.

    return len;
  }
  /* you are given a set of non-overlapping intervals and a requestInterval. Find all non-overlapping intervals.
     Eg: [[10,15] , [25,35]], requestInterval: [17,27]
     Output: [[17,25]]
     [[10,15] , [25,35], [45,65], [85,95]], requestInterval: [17,100]
     Output: [[17,25],[35,45],[65,85],[95,100]]
   */
  public static List<Interval> findInterval(List<Interval> intervals, Interval requestInterval) {
    List<Interval> result = new ArrayList<>();
    if (intervals == null || intervals.size() == 0 || requestInterval.end < requestInterval.start) {
      return result;
    }
    PriorityQueue<Interval> priorityQueue = new PriorityQueue<>((o1, o2) -> o1.start - o2.start);
    for (Interval interval : intervals) {
      priorityQueue.add(interval);
    }
    while (requestInterval.start >= priorityQueue.peek().end) {
      priorityQueue.remove();
    }
    int start = requestInterval.start;
    if (start < priorityQueue.peek().start) {
      if (requestInterval.end <= priorityQueue.peek().start) {
        result.add(requestInterval);
        return result;
      } else {
        result.add(new Interval(start, priorityQueue.peek().start));
      }
    }
    start = Math.max(start, priorityQueue.remove().end);
    while (!priorityQueue.isEmpty() && priorityQueue.peek().start < requestInterval.end) {
      Interval current = priorityQueue.remove();
      result.add(new Interval(start, current.start));
      start = current.end;
    }
    if (requestInterval.end > start) {
      result.add(new Interval(start, requestInterval.end));
    }
    return result;
  }

  //Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...]
  // (si < ei), determine if a person could attend all meetings.
  static boolean canAttend(Interval[] intervals) {
    Arrays.sort(intervals, new Comparator<Interval>() {
      @Override
      public int compare(Interval o1, Interval o2) {
        return o1.start - o2.start;
      }
    });
    if (intervals == null || intervals.length == 0) {
      return true;
    }
    for (int i = 0; i < intervals.length - 1; i++) {
      if (intervals[i].end > intervals[i + 1].start) {
        return false;
      }
    }
    return true;
  }

  // minimum meeting rooms available
  static int minMeetingRooms(Interval[] intervals) {
    if (intervals == null || intervals.length == 0) {
      return 0;
    }

    Arrays.sort(intervals, new Comparator<Interval>() {
      public int compare(Interval i1, Interval i2) {
        return i1.start - i2.start;
      }
    });

    PriorityQueue<Integer> queue = new PriorityQueue<>();
    int count = 1;
    queue.offer(intervals[0].end);

    for (int i = 1; i < intervals.length; i++) {
      if (intervals[i].start < queue.peek()) {
        count++;
      } else {
        queue.poll();
      }
      queue.offer(intervals[i].end);
    }
    return count;
  }

  //Solution 1: greedy
  public int minMeetingRooms1(Interval[] intervals) {
    int[] start = new int[intervals.length];
    int[] end = new int[intervals.length];
    for (int i = 0; i < intervals.length; i++) {
      start[i] = intervals[i].start;
      end[i] = intervals[i].end;
    }
    Arrays.sort(start);
    Arrays.sort(end);
    int endIdx = 0, res = 0;
    for (int i = 0; i < start.length; i++) {
      if (start[i] < end[endIdx]) {
        res++;
      } else {
        endIdx++;
      }
    }
    return res;
  }

  // given a list of interval, start and end time of drivers. find maximum active drivers for given intervals
  // input = [6, 10], [1, 5], [3, 7], [2, 7] output = 3
  public static int findMaxDriver(Interval[] input){
    if(input == null || input.length < 1)
      return 0;
    int[] start = new int[input.length];
    int[] end = new int[input.length];

    for(int i = 0; i< input.length;i++){
      start[i] = input[i].start;
      end[i] = input[i].end;
    }
    Arrays.sort(start);
    Arrays.sort(end);

    int driverIn = 1, maxDriver = 1;
    int i = 1, j =0;
    while(i < input.length && j <input.length){
      if(start[i] < end[j]){
        driverIn++;
        maxDriver = Math.max(driverIn, maxDriver);
        i++;
      }else{
        driverIn--;
        j++;
      }
    }
    return maxDriver;
  }


  /* Employee Free Time: We are given a list schedule of employees, which represents the working time for each employee.
    Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.
    Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.
    Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
    Output: [[3,4]]
   */



  // Method to convert infix to postfix:
  private static boolean isOperator(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/' || c == '^'
        || c == '(' || c == ')';
  }

  private boolean isSpace(char c) {  // Tell whether c is a space.
    return (c == ' ');
  }

  private boolean lowerPrecedence(char op1, char op2) {
    // Tell whether op1 has lower precedence than op2, where op1 is an
    // operator on the left and op2 is an operator on the right.
    // op1 and op2 are assumed to be operator characters (+,-,*,/,^).
    switch (op1) {
      case '+':
      case '-':
        return !(op2 == '+' || op2 == '-');
      case '*':
      case '/':
        return op2 == '^' || op2 == '(';
      case '^':
        return op2 == '(';
      case '(':
        return true;
      default:  // (shouldn't happen)
        return false;
    }
  }

  // Method to convert infix to postfix:
  public String infixToPostfix(String infix) {
    // Return a postfix representation of the expression in infix.
    Stack<String> operatorStack = new Stack<String>();  // the stack of operators
    char c;       // the first character of a token
    StringTokenizer parser = new StringTokenizer(infix, "+-*/^() ", true);
    // StringTokenizer for the input string
    StringBuffer postfix = new StringBuffer(infix.length());  // result
    // Process the tokens.
    while (parser.hasMoreTokens()) {
      String token = parser.nextToken();          // get the next token and let c be
      c = token.charAt(0);         // the first character of this token
      if ((token.length() == 1) && isOperator(c)) {    // if token is an operator
        while (!operatorStack.empty() &&
            !lowerPrecedence((operatorStack.peek()).charAt(0), c))
        // (Operator on the stack does not have lower precedence, so
        //  it goes before this one.)
        {
          postfix.append(" ").append(operatorStack.pop());
        }
        if (c == ')') {
          // Output the remaining operators in the parenthesized part.
          String operator = operatorStack.pop();
          while (operator.charAt(0) != '(') {
            postfix.append(" ").append(operator);
            operator = operatorStack.pop();
          }
        } else {
          operatorStack.push(token);// Push this operator onto the stack.
        }
      } else if ((token.length() == 1) && isSpace(c)) {    // else if
        // token was a space
        ;                                                  // ignore it
      } else {  // (it is an operand)
        postfix.append(" ").append(token);  // output the operand
      }
    }
    // Output the remaining operators on the stack.
    while (!operatorStack.empty()) {
      postfix.append(" ").append(operatorStack.pop());
    }
    // Return the result.
    return postfix.toString();
  }

  public void infixToPrefix(String infix) {
    //Step 1. Reverse the infix expression.
    //Step 2. Make Every '(' as ')' and every ')' as '('
    //Step 3. Convert expression to postfix form
    //Step 4. Reverse the expression.
  }

  //Evaluates the specified postfix expression.
  // infix evaluation: http://www.geeksforgeeks.org/expression-evaluation/
  public static int evaluate(String expr) {
    Stack<Integer> stack = new Stack<Integer>();
    int op1, op2, result = 0;
    String token;
    StringTokenizer tokenizer = new StringTokenizer(expr);
    while (tokenizer.hasMoreTokens()) {
      token = tokenizer.nextToken();
      if (isOperator(token.charAt(0))) {
        op2 = (stack.pop()).intValue();
        op1 = (stack.pop()).intValue();
        result = evalSingleOp(token.charAt(0), op1, op2);
        stack.push(new Integer(result));
      } else {
        stack.push(new Integer(Integer.parseInt(token)));
      }
    }
    return result;
  }

  private static int evalSingleOp(char operation, int op1, int op2) {
    int result = 0;
    switch (operation) {
      case '+':
        result = op1 + op2;
        break;
      case '-':
        result = op1 - op2;
        break;
      case '*':
        result = op1 * op2;
        break;
      case '/':
        result = op1 / op2;
    }
    return result;
  }

  // evaluate reverse polish notation
  static double rpn(List<String> ops) throws IllegalArgumentException, ArithmeticException {
    double num1;
    double num2;
    double num;
    if (ops == null || ops.size() == 0) {
      return 0;
    }
    Stack<Double> stack = new Stack<>();
    for (String item : ops) {
      try {
        num1 = stack.pop();
        num2 = stack.pop();
      } catch (NoSuchElementException ex) {
        throw new IllegalArgumentException("ps don't represent a well-formed RPN expression");
      }
      if (item.equals("+")) {
        stack.add(num1 + num2);
      } else if (item.equals("/")) {
        if (num1 == 0) {
          throw new ArithmeticException("can not divide by Zero");
        }
        stack.add(num2 / num1);
      } else if (item.equals("*")) {
        stack.add(num1 * num2);
      } else if (item.equals("-")) {
        stack.add(num2 - num1);
      } else {
        try {
          num = Double.parseDouble(item);
        } catch (NumberFormatException ex) {
          throw new IllegalArgumentException("ps don't represent a well-formed RPN expression");
        }
        stack.add(num);
      }
    }
    if (stack.size() > 1) {
      throw new IllegalArgumentException("ps don't represent a well-formed RPN expression");
    }
    return stack.pop();
  }

  /*Given a matrix of following between N LinkedIn users (with ids from 0 to N-1):
  followingMatrix[i][j] == true if user i is following user j thus followingMatrix[i][j] doesn't imply followingMatrix[j][i].
  Let's also agree that followingMatrix[i][i] == false */
  //Logic: a person "i" is not an influencer if "i" is following any "j" or any "j" is not following "i"
  static int getInfluencer(boolean[][] followingMatrix) {
    if (followingMatrix.length == 0 || followingMatrix[0].length == 0) {
      return -1;
    }
    // Phase 1. elimination
    int c = 0; // candidate
    for (int i = 1; i < followingMatrix.length; i++) {
      // Check if candidate
      if (followingMatrix[c][i] == true) {
        c = i; // switch candidate
      }
    }
    // Phase 2. validation
    for (int i = 0; i < followingMatrix.length; i++) {
      if (i != c && followingMatrix[c][i] == true) {
        return -1;
      }
    }
    return c;
  }

  //findCelebrity: Find Famous person in the list of persons.A person is a famous person if he doesn't know anyone in the list and
  //everyone else in the list should know this person.The function isKnow(i,j) => true/ false is given to us.

  public static int findCelebrity(int n) {
    int celeb = 0;
    // We find a 'i' which is known by everyone, but doesn't know anyone.
    for (int i = 1; i < n; i++) {
      if (isKnow(celeb, i)) {
        celeb = i;
      }
    }
    /*
     * To make sure the value we find out is actually the celebrity, we
     * check if celeb knows none and everyone knows him.
     */
    for (int i = 0; i < n; i++) {
      if (i != celeb && (isKnow(celeb, i) || !isKnow(i, celeb))) {
        return -1;
      }
    }

    return celeb;
  }

  static boolean isKnow(int i, int j) {
    return true;
  }

  public class Singleton {

    private Singleton uniqInstance;

    private Singleton() {
    }

    public synchronized Singleton getInstance() {
      if (uniqInstance == null) {
        uniqInstance = new Singleton();
      }
      return uniqInstance;
    }
  }

  //implement Java Iterable interface to read a file.
  class Line<T> {

    int LineNumber;
    byte[] LineData;
  }

  class FileReaderIterable<E> implements Iterable<E> {

    byte[] Data;

    FileReaderIterable(byte[] data) {
      this.Data = data;
    }

    public Iterator<E> iterator() {
      return new FileReaderIterator<E>(Data);
    }
  }

  class FileReaderIterator<T> implements Iterator {

    private byte[] data;
    private Queue<Byte> buffer = new java.util.LinkedList<Byte>();

    public FileReaderIterator(byte[] dat) {
      this.data = dat;
    }

    public synchronized boolean hasNext() {
      tryGetNext();
      return !this.buffer.isEmpty();
    }

    public synchronized Byte next() {
      if (!this.hasNext()) {
        throw new NoSuchElementException("Nothing left");
      }
      return this.buffer.poll();
    }

    private void tryGetNext() {
      if (this.buffer.isEmpty()) {
        for (byte item : this.data) {
          this.buffer.add(item);
        }
      }
    }

    public void remove() {
      throw new UnsupportedOperationException("It is read-only");
    }
  }

  // Airbnb: 2D Iterator with remove()
  // Given a 2-D int array, write an iterator that traverses it   from left to right and top to bottom.
  class twoDArrayIterator<T> {

    private List<List<Integer>> array;
    private int rowId;
    private int colId;
    private int numRows;

    public twoDArrayIterator(List<List<Integer>> array) {
      this.array = array;
      rowId = 0;
      colId = 0;
      numRows = array.size();
    }

    public boolean hasNext() {
      if (array == null || array.isEmpty()) {
        return false;
      }
      while (rowId < numRows && (array.get(rowId) == null ||
          array.get(rowId).isEmpty())) {
        rowId++;
      }
      return rowId < numRows;
    }

    public int next() {
      int ret = array.get(rowId).get(colId);
      colId++;
      if (colId == array.get(rowId).size()) {
        rowId++;
        colId = 0;
      }
      return ret;
    }

    public void remove() {
      List<Integer> listToRemove;
      int rowToRemove;
      int colToRemove;
      // Case 1: if the element to remove is the last element of the row
      if (colId == 0) {
        rowToRemove = rowId - 1;
        listToRemove = array.get(rowToRemove);
        colToRemove = listToRemove.size() - 1;
        listToRemove.remove(colToRemove);
      } else { // Case 2: the element to remove is not the last element
        rowToRemove = rowId;
        listToRemove = array.get(rowToRemove);
        colToRemove = colId - 1;
        listToRemove.remove(colToRemove);
      }
      // If the list to remove has only one element
      if (listToRemove.isEmpty()) {
        array.remove(listToRemove);
        rowId--;
      }
      // Update the colId
      if (colId != 0) {
        colId--;
      }
    }
  }

  //Check if a given sequence of moves for a robot is circular or not
  // G - Go one unit     L - Turn left     R - Turn right
  //Input = "GLGLGLG" output = yes
  // This function returns true if the given path is circular, else false
  boolean isCircular(char path[]) {
    int N = 0, E = 1, S = 2, W = 3;
    // Initialize starting point for robot as (0, 0) and starting direction as N North
    int x = 0, y = 0;
    int dir = N;
    // Travers the path given for robot
    for (int i = 0; i < path.length; i++) {
      // Find current move
      char move = path[i];
      // If move is left or right, then change direction
      if (move == 'R') {
        dir = (dir + 1) % 4;
      } else if (move == 'L') {
        dir = (4 + dir - 1) % 4;
      }
      // If move is Go, then change  x or y according to current direction
      else {// if (move == 'G')
        if (dir == N) {
          y++;
        } else if (dir == E) {
          x++;
        } else if (dir == S) {
          y--;
        } else // dir == W
        {
          x--;
        }
      }
    }
    // If robot comes back to (0, 0), then path is cyclic
    return (x == 0 && y == 0);
  }

  //Egg dropping problem. eggs should be dropped so that total number of trials are minimized
  //Time Complexity: O(nk^2)  Auxiliary Space: O(nk) where n is eggs and K is floors
  public int calculate(int eggs, int floors) {
    int T[][] = new int[eggs + 1][floors + 1];
    int c = 0;
    for (int i = 0; i <= floors; i++) {
      T[1][i] = i;
    }
    for (int e = 2; e <= eggs; e++) {
      for (int f = 1; f <= floors; f++) {
        T[e][f] = Integer.MAX_VALUE;
        for (int k = 1; k <= f; k++) {
          c = 1 + Math.max(T[e - 1][k - 1], T[e][f - k]);
          if (c < T[e][f]) {
            T[e][f] = c;
          }
        }
      }
    }
    return T[eggs][floors];
  }

  //Given many points on a coordinate plane, find the pair of points that is the closest among all pairs of points.
  class Point {

    int x;
    int y;

    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  private int closestPairOfPoints(Point[] px, Point[] py, int start, int end) {
    if (end - start < 3) {
      //brute force
      //return computeMinDistance(px, start, end);
    }
    int mid = (start + end) / 2;
    Point[] pyLeft = new Point[mid - start + 1];
    Point[] pyRight = new Point[end - mid];
    int i = 0, j = 0;
    for (Point p : px) {
      if (p.x <= px[mid].x) {
        pyLeft[i++] = p;
      } else {
        pyRight[j++] = p;
      }
    }
    int d1 = closestPairOfPoints(px, pyLeft, start, mid);
    int d2 = closestPairOfPoints(px, pyRight, mid + 1, end);
    int d = Math.min(d1, d2);

    List<Point> deltaPoints = new ArrayList<Point>();
    for (Point p : px) {
      if (Math.sqrt(distance(p, px[mid])) < Math.sqrt(d)) {
        deltaPoints.add(p);
      }
    }
    int d3 = closest(deltaPoints);
    return Math.min(d3, d);
  }

  private int closest(List<Point> deltaPoints) {
    int minDistance = Integer.MAX_VALUE;
    for (int i = 0; i < deltaPoints.size(); i++) {
      for (int j = i + 1; j <= i + 7 && j < deltaPoints.size(); j++) {
        int distance = distance(deltaPoints.get(i), deltaPoints.get(j));
        if (minDistance < distance) {
          minDistance = distance;
        }
      }
    }
    return minDistance;
  }

  private int distance(Point p1, Point p2) {
    return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
  }

  //draw a circle
  static void drawCircle(int r) {
    double x = 0;
    double y = r;
    draw(x, y);
    draw(-x, y);
    draw(x, -y);
    draw(-x, -y);
    draw(y, x);
    draw(-y, x);
    draw(y, -x);
    draw(-y, -x);
    while (x < y) {
      y = Math.sqrt(y * y - x * x);
      x++;
      draw(x, y);
      draw(-x, y);
      draw(x, -y);
      draw(-x, -y);
      draw(y, x);
      draw(-y, x);
      draw(y, -x);
      draw(-y, -x);
    }
  }

  static void drawCircle1(int n) {
    for (int i = -n; i <= n; i++) {
      for (int j = -n; j <= n; j++) {
        if (i * i + j * j <= n * n + 1) {
          System.out.print("* ");
        } else {
          System.out.print("  ");
        }
      }
      System.out.println();
    }
  }

  static void draw(double x, double y) {
  }

  /*Museum Problem : Given a 2D grid of rooms which can be closed, open with no guard, or open with a guard.
  Return a grid with each square labeled with the distance to the nearest guard.
  Idea is to start from every guard position and do BFS search in all 4 directions and maintain the distance of every
  space from guard. If another exit in future iterator is closer than already calculated exit then update
  the distance.
  Space complexity is O(n*m) Time complexity is O(number of guard * m * n);*/
  enum Room {
    Open,
    Closed,
    GUARD
  }

  public int[][] findShortest(Room input[][]) {
    int distance[][] = new int[input.length][input[0].length];
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[0].length; j++) {
        distance[i][j] = Integer.MAX_VALUE;
      }
    }
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        //for every guard location do a BFS starting with this guard as the origin
        if (input[i][j] == Room.GUARD) {
          distance[i][j] = 0;
          setDistance(input, i, j, distance);
        }
      }
    }
    return distance;
  }

  private void setDistance(Room input[][], int x, int y, int distance[][]) {
    boolean visited[][] = new boolean[input.length][input[0].length];
    Queue<Point> q = new java.util.LinkedList<Point>();
    q.offer(new Point(x, y));
    //Do a BFS at keep updating distance.
    while (!q.isEmpty()) {
      Point p = q.poll();
      setDistanceUtil(q, input, p, getNeighbor(input, p.x + 1, p.y), distance, visited);
      setDistanceUtil(q, input, p, getNeighbor(input, p.x, p.y + 1), distance, visited);
      setDistanceUtil(q, input, p, getNeighbor(input, p.x - 1, p.y), distance, visited);
      setDistanceUtil(q, input, p, getNeighbor(input, p.x, p.y - 1), distance, visited);
    }
  }

  private void setDistanceUtil(Queue<Point> q, Room input[][], Point p, Point newPoint,
      int distance[][], boolean visited[][]) {
    if (newPoint != null && !visited[newPoint.x][newPoint.y]) {
      if (input[newPoint.x][newPoint.y] != Room.GUARD
          && input[newPoint.x][newPoint.y] != Room.Closed) {
        distance[newPoint.x][newPoint.y] = Math
            .min(distance[newPoint.x][newPoint.y], 1 + distance[p.x][p.y]);
        visited[newPoint.x][newPoint.y] = true;
        q.offer(newPoint);
      }
    }
  }

  private Point getNeighbor(Room input[][], int x, int y) {
    if (x < 0 || x >= input.length || y < 0 || y >= input[0].length) {
      return null;
    }
    return new Point(x, y);
  }

  //Airbnb: Achieve a mini parser, enter the string in the following format : "324" or "[123,456, [788,799,833], [[]], 10, []]"
  //Required output : 324 or [123,456, [788,799,833], [[]], 10, []].That is, to convert a string into the corresponding data formats.
  private static class NestedIntList {

    private int value;
    private boolean isNumber;
    private List<NestedIntList> intList;

    // Constructor to construct a number
    public NestedIntList(int value) {
      this.value = value;
      isNumber = true;
    }

    // Constructor to construct a list
    public NestedIntList() {
      intList = new ArrayList<NestedIntList>();
      isNumber = false;
    }

    public void add(NestedIntList num) {
      this.intList.add(num);
    }

    public NestedIntList miniParser(String s) {
      if (s == null || s.length() == 0) {
        return null;
      }
      // Corner case "123"
      if (s.charAt(0) != '[') {
        int num = Integer.parseInt(s);
        return new NestedIntList(num);
      }
      int i = 0;
      int counter = 1;
      Stack<NestedIntList> stack = new Stack<NestedIntList>();
      NestedIntList result = null;
      while (i < s.length()) {
        char c = s.charAt(i);
        if (c == '[') {
          NestedIntList num = new NestedIntList(Integer.parseInt(s.substring(counter, i)));
          if (!stack.isEmpty()) {
            stack.peek().add(num);
          }
          stack.push(num);
          counter = i + 1;
        } else if (c == ',' || c == ']') {
          if (counter != i) {
            int value = Integer.parseInt(s.substring(counter, i));
            NestedIntList num = new NestedIntList(value);
            stack.peek().add(num);
          }
          counter = i + 1;
          if (c == ']') {
            result = stack.pop();
          }
        }
        i++;
      }
      return result;
    }

    public String toString() {
      if (this.isNumber) {
        return this.value + "";
      } else {
        return this.intList.toString();
      }
    }
  }

  //Given a nested list of integers, returns the sum of all integers in the list weighted by their depth. For example,
  //given the list {{1,1},2,{1,1}} the function should return 10 (four 1's at depth 2, one 2 at depth 1)
  private static int depthNestedIntSum(List<NestedIntList> input, int level) {
    if (input == null || input.size() == 0) {
      return 0;
    }
    int sum = 0;
    for (int i = 0; i < input.size(); i++) {
      if (input.get(i).isNumber) {
        sum += input.get(i).value * level;
      } else {
        sum += depthNestedIntSum(input.get(i).intList, level + 1);
      }
    }
    return sum;
  }
  //Follow up: What if the list is in reverse order?
  //In that case get the depth first and call same above function with each recursive call do level - 1;

  //Given two rectangles, find if the given two rectangles overlap or not.
  boolean doOverlap(Point l1, Point r1, Point l2, Point r2) {
    // If one rectangle is on left side of other
    if (l1.x > r2.x || l2.x > r1.x) {
      return false;
    }
    // If one rectangle is above other
    if (l1.y < r2.y || l2.y < r1.y) {
      return false;
    }
    return true;
  }

  //Given n appointments, find all conflicting appointments
  //Interval Tree implementation. http://www.davismol.net/2016/02/07/data-structures-augmented-interval-tree-to-search-for-interval-overlapping/
  class Interval1 implements Comparable<Interval1> {

    public int start;
    public int end;
    public int max;
    public Interval1 left;
    public Interval1 right;

    public Interval1(int start, int end) {
      this.start = start;
      this.end = end;
      this.max = end;
    }

    @Override
    public int compareTo(Interval1 i) {
      if (this.start < i.start) {
        return -1;
      } else if (this.start == i.start) {
        return this.end <= i.end ? -1 : 1;
      } else {
        return 1;
      }
    }
  }

  class IntervalTree {

    private Interval1 root;

    public Interval1 insert(Interval1 root, final Interval1 newNode) {
      if (root == null) {
        root = newNode;
        return root;
      }
      // Get low value of interval at root
      int l = root.start;
      // If root's low value is smaller, then new interval goes to left subtree
      if (newNode.start < l) {
        root.left = insert(root.left, newNode);
      }
      // Else, new node goes to right subtree.
      else {
        root.right = insert(root.right, newNode);
      }
      // Update the max value of this ancestor if needed
      if (root.max < newNode.end) {
        root.max = newNode.end;
      }

      return root;
    }

    //Find all overlapping intervals for given interval
    public void intersectInterval(Interval1 root, Interval1 i, List<Interval1> output) {
      if (root == null) {
        return;
      }
      if (!((root.start > i.end)) || (root.end < i.start)) {
        if (output == null) {
          output = new ArrayList<Interval1>();
        }
        output.add(root);
      }
      if ((root.left != null) && (root.left.max >= i.start)) {
        this.intersectInterval(root.left, i, output);
      }
      this.intersectInterval(root.right, i, output);
    }

    //Find all non-overlapping intervals for given interval
    public void nonOverlappingInterval(Interval1 root, Interval1 i, List<Interval1> output) {
      if (root == null) {
        return;
      }
      if (!((root.start < i.end)) || (root.end > i.start)) {
        if (output == null) {
          output = new ArrayList<Interval1>();
        }
        output.add(root);
      }
      if ((root.left != null) && (root.left.max <= i.start)) {
        this.intersectInterval(root.left, i, output);
      }
      this.intersectInterval(root.right, i, output);
    }

    // A utility function to check if given two intervals overlap
    boolean doOVerlap(Interval1 i1, Interval1 i2) {
      if (i1.start <= i2.end && i2.start <= i1.end) {
        return true;
      }
      return false;
    }

    Interval1 overlapSearch(Interval1 root, Interval1 i) {
      // Base Case, tree is empty
      if (root == null) {
        return null;
      }
      // If given interval overlaps with root
      if (doOVerlap(root, i)) {
        return root;
      }
      // If left child of root is present and max of left child is greater than or equal to given interval, then i may
      // overlap with an interval is left subtree
      if (root.left != null && root.left.max >= i.start) {
        return overlapSearch(root.left, i);
      }
      // Else interval can only overlap with right subtree
      return overlapSearch(root.right, i);
    }

    // This function prints all conflicting appointments in a given array of appointments.
    void printConflicting(List<Interval1> appt, int n) {
      // Create an empty Interval Search Tree, add first appointment
      Interval1 root = null;
      root = insert(root, appt.get(0));
      // Process rest of the intervals
      for (int i = 1; i < n; i++) {
        // If current appointment conflicts with any of the existing intervals, print it
        Interval1 res = overlapSearch(root, appt.get(i));
        if (res != null) {
          System.out.println(
              "[" + appt.get(i).start + "," + appt.get(i).end + "] Conflicts with [" + res.start
                  + ","
                  + res.end + "]");
        }
        // Insert this appointment
        root = insert(root, appt.get(i));
      }
    }
  }

  /*Implement LRU Cache.
  1. If cache has free entry, add the page entry to queue and make it head.
  2. If cache hit, remove the item from present location and add it to front of queue and make it head.
  3. If cache is full and its cache miss, remove the item at end and insert the new item at front of queue.
  4. To check hit or miss, use hash table.
   So at front items would be most recently used while in the end of queue least recently used items
  O(1) all operations
  use pseudo head and tail to avoid null checks. https://leetcode.com/problems/lru-cache/discuss/45911/Java-Hashtable-+-Double-linked-list-(with-a-touch-of-pseudo-nodes)
  */
  class DoublyNode {

    int data;
    int key;
    DoublyNode next;
    DoublyNode prev;
  }

  class LRU {

    HashMap<Integer, DoublyNode> map;
    int capacity;
    DoublyNode head;
    DoublyNode tail;

    LRU(int capacity) {
      this.capacity = capacity;
      map = new HashMap<>();
      //pseudo head and tail to avoid null checks
      head = new DoublyNode();
      head.prev = null;
      tail = new DoublyNode();
      tail.next = null;
      head.next = tail;
      tail.prev = head;
    }

    private void add(DoublyNode item) {
      item.prev = head;
      item.next = head.next;

      head.next.prev = item;
      head.next = item;
    }

    private void remove(DoublyNode item) {
      DoublyNode pre = item.prev;
      DoublyNode post = item.next;

      pre.next = post;
      post.prev = pre;
    }

    private void moveFirst(DoublyNode item) {
      remove(item);
      add(item);
    }

    private DoublyNode removeLast() {
      DoublyNode res = tail.prev;
      this.remove(res);
      return res;
    }

    public int get(int key) {
      if (map.containsKey(key)) {
        DoublyNode node = map.get(key);
        moveFirst(node);
        return node.data;
      }
      return -1;
    }

    public void set(int key, int value) {
      //cache hit
      if (map.containsKey(key)) {
        DoublyNode node = map.get(key);
        moveFirst(node);
        node.data = value;
        map.put(key, node);
        return;
      }
      //cache is full and cache miss
      if (map.size() >= capacity) {
        DoublyNode end = removeLast();
        map.remove(end.key);
      }
      DoublyNode node = new DoublyNode();
      node.key = key;
      node.data = value;
      add(node);
      map.put(key, node);
    }

    public void removeCache(int key) {
      if (map.containsKey(key)) {
        DoublyNode node = map.get(key);
        map.remove(key);
        remove(node);
      }
    }
  }

  // Thread Safe LRU cache with use pseudo head and tail to avoid null checks.
  //https://www.ebayinc.com/stories/blogs/tech/high-throughput-thread-safe-lru-caching/
  //https://github.com/ben-manes/concurrentlinkedhashmap/blob/master/src/main/java/com/googlecode/concurrentlinkedhashmap/ConcurrentLinkedHashMap.java
  class LRUThreadSafe<K, V> {

    private ConcurrentLinkedQueue<K> queue = new ConcurrentLinkedQueue<>();

    private ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Lock readLock = readWriteLock.readLock();

    private Lock writeLock = readWriteLock.writeLock();

    AtomicLong capacity;

    AtomicLong total;

    LRUThreadSafe(long capacity) {
      this.capacity.lazySet(capacity);
    }

    public V get(K key) {
      readLock.lock();
      try {
        V v = null;
        if (map.contains(key)) {
          v = map.get(key);
          queue.remove(key);
          queue.add(key);
        }
        return v;
      } finally {
        readLock.unlock();
      }
    }

    public V set(K key, V value) {
      writeLock.lock();
      try {
        if (map.contains(key)) {
          queue.remove(key);
        }
        while (total.get() >= capacity.get()) {
          K queueKey = queue.poll();
          map.remove(queueKey);
        }
        //New elements are inserted at the tail of the queue
        queue.add(key);
        total.incrementAndGet();
        map.put(key, value);

        return value;
      } finally {
        writeLock.unlock();
      }
    }

    public V remove(K key) {
      writeLock.lock();
      try {
        V v = null;
        if (map.contains(key)) {
          v = map.remove(key);
          queue.remove(key);
        }
        return v;
      } finally {
        writeLock.unlock();
      }
    }
  }

  //Implement a peek using a existing iterator next and hasnext function.
  // better implementation https://commons.apache.org/proper/commons-collections/jacoco/org.apache.commons.collections4.iterators/PeekingIterator.java.html
  class PeekingIterator<E> implements Iterator<E> {

    private final Iterator<? extends E> iterator;
    /**
     * Indicates that the decorated iterator is exhausted.
     */
    private boolean exhausted = false;
    /**
     * Indicates if the lookahead slot is filled.
     */
    private boolean slotFilled = false;
    /**
     * The current slot for lookahead.
     */
    private E slot;

    public PeekingIterator(Iterator<E> iter) {
      iterator = iter;
    }

    private void fill() {
      if (exhausted || slotFilled) {
        return;
      }
      if (iterator.hasNext()) {
        slot = iterator.next();
        slotFilled = true;
      } else {
        exhausted = true;
        slot = null;
        slotFilled = false;
      }
    }

    // Returns the next element in the iteration without advancing the iterator.
    public E peek() {
      fill();
      return exhausted ? null : slot;
    }

    // hasNext() and next() should behave the same as in the Iterator interface. Override them if needed.
    @Override
    public E next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      final E x = slotFilled ? slot : iterator.next();
      // reset the lookahead slot
      slot = null;
      slotFilled = false;
      return x;
    }

    @Override
    public boolean hasNext() {
      if (exhausted) {
        return false;
      }
      return slotFilled ? true : iterator.hasNext();
    }

    @Override
    public void remove() {
      if (slotFilled) {
        throw new IllegalStateException("peek() or element() called before remove()");
      }
      iterator.remove();
    }
  }

  //Reverse a stack using recursion. You are not allowed to use loops or data structure.
  public void reverse(Stack<Integer> stack) {
    if (stack.isEmpty() || stack.size() == 1) {
      return;
    }
    int top = stack.pop();
    this.reverse(stack);
    this.insertAtBottom(stack, top);
  }

  private void insertAtBottom(Stack<Integer> stack, int val) {
    if (stack.isEmpty()) {
      stack.push(val);
      return;
    }
    int temp = stack.pop();
    this.insertAtBottom(stack, val);
    stack.push(temp);
  }

  //palantir magic box
  //https://github.com/siddharthgoel88/problem-solving/blob/master/Hackerrank/Palantir-magic-box/src/Solution.java
  //https://github.com/vrdmr/interview-prep/blob/master/DataStructuresAndAlgorithms/src/interviewquestions/palantir/Palantir-Question.png
  private static Map<String, Integer> columnFlippingStat;
  private static int maxWishes = -1;

  private static void findFlippingSet(char[] row) {
    StringBuilder allP = new StringBuilder();
    StringBuilder allT = new StringBuilder();
    for (int i = 0; i < row.length; i++) {
      if (row[i] == 'P') {
        allP.append('0');
        allT.append('1');
      } else {
        allP.append('1');
        allT.append('0');
      }
    }
    int allPFreq = updateSet(allP.toString());
    int allTFreq = updateSet(allT.toString());
    maxWishes = Math.max(maxWishes, Math.max(allPFreq, allTFreq));
  }

  private static int updateSet(String flippedCols) {
    int freq = 0;
    if (columnFlippingStat.containsKey(flippedCols)) {
      freq = columnFlippingStat.get(flippedCols);
    }
    columnFlippingStat.put(flippedCols, freq + 1);
    return freq + 1;
  }

  // Connect4: Implement the function that takes a board string
  // and decodes it into the representative 2D array.
  //    |_|_|_|_|_|_|_|
  //    |_|_|r|_|_|_|_|
  //    |b|r|b|r|b|r|_|
  //    |b|b|b|r|r|b|_|
  //    |b|r|r|b|b|r|_|
  //    |r|b|b|r|r|r|b|
  //    CFN: 9_r4_brbrbr_3b2rb_b2r2br_r2b3rb
  public static char[][] decodeBoard(String str) {
    char[] input = str.toCharArray();
    char[][] output = new char[6][7];
    char[] temp = new char[42];
    int index = 0;
    for (int i = 0; i < input.length; i++) {
      if (isInteger(Character.toString(input[i]))) {
        int number = Character.getNumericValue(input[i]);
        for (int l = 0; l < number; l++) {
          temp[index + l] = input[i + 1];
        }
        index += number;
        i++;
      } else {
        temp[index] = input[i];
        index++;
      }
    }
    for (int k = 0; k < 6; k++) {
      for (int j = 0; j < 7; j++) {
        output[k][j] = temp[7 * k + j];
      }
    }
    return output;
  }

  public static boolean isInteger(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    } catch (NullPointerException e) {
      return false;
    }
    return true;
  }

  //Design data structure “Map” storing pairs of integers (key, value) and define following member functions in O(1)
  //runtime: void insert(key, value), void delete(key), int get(key), int getRandomKey().
  class CustomMap {

    HashMap<String, List<Integer>> _map = new HashMap<>();
    ArrayList<String> arr = new ArrayList<>();
    int index;
    int size;

    public void Insert(String key, int value) {
      if (!_map.containsKey(key)) {
        index = size;
        List<Integer> temp = new ArrayList<>();
        temp.add(value);
        temp.add(index);
        _map.put(key, temp);
        arr.add(index, key);
        size++;
      } else {
        List<Integer> _list = _map.get(key);
        _list.set(0, value);
        _map.put(key, _list);
      }
    }

    public int Get(String key) {
      return _map.get(key).get(0);
    }

    public void Delete(String key) {
      index = _map.get(key).get(1);
      //copy last element at array index and remove last element, so delete can done in O(1)
      arr.set(index, arr.get(size - 1));
      arr.remove(size - 1);
      size--;
      //delete from the map
      _map.remove(key);
      //update the index of the swapped key
      _map.get(arr.get(index)).set(1, index);
    }

    public String GetRandomKey() {
      int r = (int) (Math.random() * size + 1);
      return arr.get(r);
    }

    public void clear() {
      size = 0;
    }
  }

  /* Java program to design a data structure that support following operations
  //   in Theta(n) time
  //   a) Insert
  //   b) Delete
  //   c) Search
  //   d) getRandom
     doesnt work with duplicates value [1, 1, 2, 2, 3,3] and then remove(2) and remove(2) */
  class MyDS {

    ArrayList<Integer> arr;   // A resizable array
    // A hash where keys are array elements and values are
    // indexes in arr[]
    HashMap<Integer, Integer> hash;

    // Constructor (creates arr[] and hash)
    public MyDS() {
      arr = new ArrayList<>();
      hash = new HashMap<>();
    }

    // A Theta(1) function to add an element
    void add(int x) {
      // If element is already present, then noting to do
      if (hash.get(x) != null) {
        return;
      }
      // Else put element at the end of arr[]
      int s = arr.size();
      arr.add(x);
      // And put in hash also
      hash.put(x, s);
    }

    // A Theta(1) function to remove an element
    void remove(int x) {
      // Check if element is present
      Integer index = hash.get(x);
      if (index == null) {
        return;
      }
      // If present, then remove element from hash
      hash.remove(x);
      // Swap element with last element so that remove from
      // arr[] can be done in O(1) time
      int size = arr.size();
      Integer last = arr.get(size - 1);
      Collections.swap(arr, index, size - 1);

      // Remove last element (This is O(1))
      arr.remove(size - 1);

      // Update hash table for new index of last element
      hash.put(last, index);
    }

    // Returns a random element from MyDS
    int getRandom() {
      // Find a random index from 0 to size - 1
      Random rand = new Random();  // Choose a different seed
      int index = rand.nextInt(arr.size());
      // Return element at randomly picked index
      return arr.get(index);
    }

    // Returns index of element if element is present, otherwise null
    Integer search(int x) {
      return hash.get(x);
    }
  }

  // Insert Delete GetRandom O(1) - Duplicates allowed
  class RandomizedCollection {

    private List<Integer> list;
    private Map<Integer, LinkedHashSet<Integer>> map;

    /**
     * Initialize your data structure here.
     */
    public RandomizedCollection() {
      list = new ArrayList<>();
      map = new HashMap<>();
    }

    /**
     * Inserts a value to the collection. Returns true if the collection did not already contain the
     * specified element.
     */
    public boolean insert(int val) {
      boolean ans = true;

      LinkedHashSet<Integer> indices;
      int loc = list.size();

      list.add(val);

      if (map.containsKey(val)) {
        ans = false;
        indices = map.get(val);
      } else {
        indices = new LinkedHashSet<>();
      }
      indices.add(loc);
      map.put(val, indices);

      return ans;
    }

    /**
     * Removes a value from the collection. Returns true if the collection contained the specified
     * element.
     */
    public boolean remove(int val) {
      if (!map.containsKey(val) || map.get(val).isEmpty()) {
        return false;
      }
      // Get loc of the val to be removed
      int locToRemove = map.get(val).iterator().next();

      // Remove the val
      map.get(val).remove(locToRemove);

      if (locToRemove < list.size() - 1) {

        // Get the number to be swapped
        int numToSwap = list.get(list.size() - 1);

        // Put the tail number to the location to be removed
        list.set(locToRemove, numToSwap);

        // Update the loc
        if (map.get(numToSwap).contains(list.size() - 1)) {
          map.get(numToSwap).remove(list.size() - 1);
        }
        map.get(numToSwap).add(locToRemove);
      }

      // Remove the val
      list.remove(list.size() - 1);

      return true;
    }

    /**
     * Get a random element from the collection.
     */
    public int getRandom() {
      if (list.isEmpty()) {
        return 0;
      }
      Random rand = new Random();
      int loc = rand.nextInt(list.size());
      return list.get(loc);
    }
  }

  // data structure to add(name, value), get(name), delete(name), count(value)


  //Add a third dimension of time to a hashmap,so ur hashmap will look something like this - HashMap<K, t, V> where
  //t is a float value. Implement the get and put methods to this map. The get method should be something like - map.get(K,t)
  //which should give us the value. If t does not exists then map should return the closest t' such that t' is smaller than t.
  //For example, if map contains (K,1,V1) and (K,2,V2) and the user does a get(k,1.5) then the output should be v1 as 1 is the next smallest number to 1.5
  class TimeHashMap<Key, Time, Value> {

    private HashMap<Key, TreeMap<Time, Value>> map = new HashMap<>();

    public Value get(Key key, Time time) {
      TreeMap<Time, Value> tree = map.get(key);
      if (tree == null) {
        return null;
      }
      final Time floorKey = tree.floorKey(time);
      return floorKey == null ? null : tree.get(floorKey);
    }

    public void put(Key key, Time time, Value value) {
      if (!map.containsKey(key)) {
        map.put(key, new TreeMap<>());
      }
      map.get(key).put(time, value);
    }
  }

  //how do you count the number of visitors for the past 1 minute?
  class HitCounter {

    java.util.concurrent.atomic.AtomicIntegerArray time;
    java.util.concurrent.atomic.AtomicIntegerArray hit;

    /**
     * Initialize your data structure here.
     */
    public HitCounter() {
      time = new java.util.concurrent.atomic.AtomicIntegerArray(60);
      hit = new java.util.concurrent.atomic.AtomicIntegerArray(60);
    }

    /**
     * Record a hit.
     *
     * @param timestamp - The current timestamp (in seconds granularity).
     */
    public void hit(int timestamp) {
      int index = timestamp % 60;
      if (time.get(index) != timestamp) {
        time.set(index, timestamp);
        hit.set(index, 1);
      } else {
        hit.incrementAndGet(index);//add one
      }
    }

    /**
     * Return the number of hits in the past 5 minutes.
     *
     * @param timestamp - The current timestamp (in seconds granularity).
     */
    public int getHits(int timestamp) {
      int total = 0;
      for (int i = 0; i < 60; i++) {
        if (timestamp - time.get(i) < 60) {
          total += hit.get(i);
        }
      }
      return total;
    }
  }

  /*A bot is an id that visit the site m times in the last n seconds, given a list of logs with id and time sorted by time, return all the bots's id */
  static class Log {

    String id;
    int time;
  }

  static class LogCount {

    int time;
    int count;

    public LogCount(int time, int count) {
      this.time = time;
      this.count = count;
    }
  }

  public static HashSet<String> getBots1(Log[] logs, int m, int n) {
    HashMap<String, LogCount> map = new HashMap<>();
    HashSet<String> out = new HashSet<>();
    for (Log log : logs) {
      if (map.containsKey(log.id) && (log.time - map.get(log.id).time <= n)) {
        LogCount _lc = map.get(log.id);
        _lc.time = log.time;
        _lc.count++;
        map.put(log.id, _lc);
        if (map.get(log.id).count == m) {
          System.out.println(log.id);
          out.add(log.id);
        }
      } else {
        map.put(log.id, new LogCount(log.time, 1));
      }
    }
    return out;
  }

  public static HashSet<String> getBots(Log[] logs, int m, int n) {
    int[] time = new int[n];
    AtomicReferenceArray data = new AtomicReferenceArray(n);
    //HashMap<String, LogCount> bot = new HashMap<>(n);
    HashSet<String> output = new HashSet<>();
    int count = 0;
    for (Log log : logs) {
      HashMap<String, Integer> temp;
      int index = log.time % n;
      if (time[index] != log.time) {
        temp = (HashMap<String, Integer>) data.get(index);
        if (temp != null && temp.containsKey(log.id)) {
          temp.put(log.id, temp.get(log.id) + 1);
        } else {
          time[index] = log.time;
          temp = new HashMap<>();
          temp.put(log.id, 1);
          data.set(index, temp);
        }
      } else {
        temp = (HashMap<String, Integer>) data.get(index);
        if (!temp.containsKey(log.id)) {
          temp.put(log.id, 1);
        } else {
          temp.put(log.id, temp.get(log.id) + 1);
        }
      }
      if (count > n) {
        for (int i = 0; i < n; i++) {
          if (log.time - time[i] < n) {
            HashMap<String, Integer> _map = (HashMap<String, Integer>) data.get(i);
            for (String key : _map.keySet()) {
              if (_map.get(key) >= m) {
                System.out.println(key);
                output.add(key);
              }
            }
          }
        }
      }
      count++;
    }
    return output;
  }

  //Stream deduplication - Give a real-time source of data that emits strings.
  // print last 1 minute unique data.
  class Streamdedu {

    AtomicIntegerArray time;
    java.util.concurrent.atomic.AtomicReferenceArray data;

    public Streamdedu() {
      time = new AtomicIntegerArray(60);
      data = new java.util.concurrent.atomic.AtomicReferenceArray(60);
    }

    void OnDataReceived(String input, int timestamp) {
      Set<String> temp;
      int index = timestamp % 60;
      if (time.get(index) != timestamp) {
        time.set(index, timestamp);
        temp = new HashSet<>();
        temp.add(input);
        data.set(index, temp);
      } else {
        temp = (HashSet<String>) data.get(index);
        if (!temp.contains(input)) {
          temp.add(input);
        }
      }
    }

    public Set<String> PrintData(int timestamp) {
      Set<String> output = new HashSet<>();
      for (int i = 0; i < 60; i++) {
        if (timestamp - time.get(i) < 60) {
          output.addAll((HashSet<String>) data.get(i));
        }
      }
      return output;
    }
  }

  /* Design a logger system that receive stream of messages along with its timestamps, each message should be printed
  if and only if it is not printed in the last 10 seconds. Given a message and a timestamp (in seconds granularity),
  return true if the message should be printed in the given timestamp, otherwise returns false.
  It is possible that several messages arrive roughly at the same time.*/
  class Logger {

    // Fast but space complexity is very high as map will keep on adding data
    private Map<String, Integer> map = new HashMap<>();

    public boolean shouldPrintMessage(int timestamp, String message) {
      if (map.containsKey(message) && (timestamp - map.get(message) < 10)) {
        return false;
      }
      map.put(message, timestamp);
      return true;
    }
  }

  /* Given input which is vector of log entries of some online system each entry is something like (user_name, login_time, logout_time),
  come up with an algorithm with outputs number of users logged in the system at each time slot in the input, output should contain only the time slot which are in the input.
  input: [
          ("Jane", 1.2, 4.5),
          ("Jin", 3.1, 6.7),
          ("June", 8.9, 10.3)
         ]
  Output: [(1.2, 1), (3.1, 2), (4.5, 1), (6.7, 0), (8.9, 1), (10.3,0)] */
  class Input {

    String name;
    double login;
    double logout;
  }

  class Type implements Comparable<Type> {

    boolean loggedin;
    double time;

    public Type(double time, boolean loogedIn) {
      this.time = time;
      this.loggedin = loogedIn;
    }

    @Override
    public int compareTo(Type that) {
      return (int) (this.time - that.time);
    }
  }

  class Output {

    double time;
    int numLoggedIn;

    public Output(double t, int num) {
      time = t;
      numLoggedIn = num;
    }
  }

  public List<Output> findLoggedIn(List<Input> list) {
    List<Type> loggedIn = new ArrayList<>();
    List<Output> retValue = new ArrayList<>();
    int loggedInNow = 0;
    for (Input iv : list) {
      loggedIn.add(new Type(iv.login, true));
      loggedIn.add(new Type(iv.logout, false));
    }
    Collections.sort(loggedIn);
    for (Type t : loggedIn) {
      if (t.loggedin == true) {
        loggedInNow++;
      } else {
        loggedInNow--;
      }
      retValue.add(new Output(t.time, loggedInNow));
    }
    return retValue;
  }

  //Knight tour on keypad
  void KnightTour() {
    int keypad[][] = new int[3][4];
    int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, -1, 0, -1};
    int count = 0;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 3; j++) {
        keypad[i][j] = values[count];
      }
    }
    count++;
    int[][] mtable = new int[11][10];

  }

  int KnightUtil(int[][] table, int[][] mem, int digits, int start) {
    if (digits == 1) {
      return 1;
    }
    if (mem[digits][start] == 0) {
      for (int next : nextKnightMove(start, table)) {
        mem[digits][start] += KnightUtil(table, mem, digits - 1, next);
      }
    }
    //#else:
    //#print("found ...",digits,start)
    return mem[digits][start];
  }

  int[] nextKnightMove(int start, int[][] table) {
    return new int[2];
  }

  //Given users with locations in a list and a logged in user with locations. find given User's travel buddies (people who shared more than half of your locations).
  public static void preProcess(List<javafx.util.Pair<String, List<Integer>>> input,
      javafx.util.Pair<String, List<Integer>> user) {
    HashMap<Integer, List<String>> map = new HashMap<>(); // map key is location, values is list of users
    for (javafx.util.Pair<String, List<Integer>> p : input) {
      List<String> _users;
      for (Integer location : p.getValue()) {
        if (map.get(location) == null) {
          _users = new ArrayList<>();
        } else {
          _users = map.get(location);
        }
        map.put(location, _users);
      }
    }
    findTravelBuddy(user, map);
  }

  public static List<String> findTravelBuddy(javafx.util.Pair<String, List<Integer>> user,
      HashMap<Integer, List<String>> map) {
    List<String> _output = new ArrayList<>();
    List<String> temp = new ArrayList<>();
    int N = user.getValue().size();
    HashMap<String, Integer> _commonUsers = new HashMap<>();
    for (Integer _loc : user.getValue()) {
      if (map.containsKey(_loc)) {
        temp.addAll(map.get(_loc));
      }
    }
    // find majority element from the temp array
    for (String _user : temp) {
      if (_commonUsers.containsKey(_user)) {
        _commonUsers.put(_user, _commonUsers.get(_user) + 1);
      } else {
        _commonUsers.put(_user, 1);
      }
    }
    for (String u : _commonUsers.keySet()) {
      if (_commonUsers.get(u) > N / 2) {
        _output.add(u);
      }
    }
    return _output;
  }

  //Implement Browser prev, forward, and History functionality
  class BrowserNode {

    BrowserNode next;
    BrowserNode prev;
    String url;
    Date time;
    BrowserNode curr;

    public BrowserNode(String url) {
      this.url = url;
      //this.time = time;
    }
  }

  class Browser {

    BrowserNode head;
    BrowserNode end;
    BrowserNode curr;
    int size = 0;

    public Browser(int size) {
      head = null;
      end = null;
      curr = null;
      this.size = size;
    }

    public void add(String url) {
      BrowserNode node = new BrowserNode(url);
      if (head == null) {
        head = node;
        end = head;
      } else {
        node.prev = end;
        end.next = node;
        end = node;
        end = node;
      }
      curr = node;
      size++;
    }

    public String forward() {
      if (curr != null && curr.next != null) {
        curr.next = curr.next.next;
        curr.prev = curr;
        curr = curr.next;
        return curr.next.url;
      }
      return null;
    }

    public String backward() {
      if (curr != null && curr.prev != null) {
        curr.next = curr;
        curr.prev = curr.prev.prev;
        curr = curr.prev;
        return curr.prev.url;
      }
      return null;
    }
  }

  List<File> files;
  // read all files from directory in java
  public void listFilesForFolder(final File folder) {
    for (final File fileEntry : folder.listFiles()) {
      if (fileEntry.isDirectory()) {
        listFilesForFolder(fileEntry);
      } else {
        System.out.println(fileEntry.getName());
        files.add(fileEntry);
      }
    }
  }

  //Design a class to calculate moving average of last N numbers in a stream of real numbers
  //Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.
  //Complexity: time O(1), space O(window size)
  class MovingAverage {

    private int[] window;
    private int n, insert;
    private long sum;

    public MovingAverage(int size) {
      window = new int[size];
      insert = 0;
      sum = 0;
    }

    public double next(int val) {
      if (n < window.length) {
        n++;
      }
      sum -= window[insert]; // subtract num when window is full
      sum += val;
      window[insert] = val; // insert num
      insert = (insert + 1) % window.length;

      try {
        return (double) sum / n; // handle divide by zero exception here
      } catch (ArithmeticException ex) {
        throw new ArithmeticException("Cannot divide by Zero");
      }
    }
  }

  //Find Duplicate File in System
  /*- /foo
    - /images
      - /foo.png  <------.
      - /temp              | same file contents
      - /baz             |
      - /that.foo  <---|--.
      - /bar.png  <--------'  |
      - /file.tmp  <----------| same file contents
    - /other.temp  <--------'
      - /blah.txt

  Input: "/foo"
      [['/foo/bar.png', '/foo/images/foo.png'],
      ['/foo/file.tmp', '/foo/other.temp', '/foo/temp/baz/that.foo']
      ] */
  public List<List<String>> findDuplicateFiles(List<String> paths) {
    List<List<String>> output = new ArrayList<>();
    HashMap<Long, List<String>> mapSize = new HashMap<>();
    HashMap<String, List<String>> map = new HashMap<>();
    for (String path : paths) {
      File f = new File(path);

      if (!mapSize.containsKey(f.length())) {
        mapSize.put(f.length(), new ArrayList<>());
      }
      mapSize.get(f.length()).add(path);
    }
    for (Map.Entry<Long, List<String>> files : mapSize.entrySet()) {
      if (files.getValue().size() > 1) {
        for (String file : files.getValue()) {
          String hashCode = "";// Utils.getMD5(file);
          //String value = f.getContent(0, f.length());
          if (!map.containsKey(hashCode)) {
            map.put(hashCode, new ArrayList<>());
          }
          map.get(hashCode).add(file);
        }
      }
    }
    for (Map.Entry<String, List<String>> file : map.entrySet()) {
      if (file.getValue().size() > 1) {
        output.add(file.getValue());
      }
    }
    return output;
  }

  //next() : given a list containing k sorted lists of integers , each list is of varying size, max of
  // which is n. Implement an iterator in java that with each call to its next() function retrieves the
  // next integer in the overall order of all the integers in all the lists combined. Answer should be
  // efficient depending on the number of calls to next().
  // Example: lists = [[1,2,3],[1,4],[2,5,7,8]] n = 4 k = 3 iterator should return (if called 9 times): 1,1,2,2,3,4,5,7,8
  // Time = O(logk) space = O(k)
  PriorityQueue<Element> minHeap = new PriorityQueue<>(new ElementComparator());
  List<List<Integer>> data;

  public Integer next() {
    Integer result = null;
    if (!minHeap.isEmpty()) {
      Element output = minHeap.poll();
      result = output.value;
      if (output.position + 1 < data.get(output.kIndex).size()) {
        output.value = data.get(output.kIndex).get(output.position + 1);
        output.position += 1;
        minHeap.add(output);
      }
    }
    return result;
  }

  public void process(List<List<Integer>> data) {
    for (int i = 0; i < data.size(); i++) {
      Element e = new Element();
      e.position = 0;
      e.value = data.get(i).get(0);
      e.kIndex = i;
      minHeap.add(e);
    }
  }

  class Element {

    int value;
    int position;
    int kIndex;
  }

  class ElementComparator implements Comparator<Element> {

    @Override
    public int compare(Element e1, Element e2) {
      return e1.value - e2.value;
    }
  }
}
