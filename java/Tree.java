package code.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import javafx.util.Pair;

/**
 * Created by Piyush Patel.
 */
public class Tree {

  static int countN = 0;

  public static void main(String[] args) {
    Node tree = new Node(1);
    tree.right = new Node(0);
    tree.right.left = new Node(0);
    tree.right.right = new Node(1);
    //printAllPathSum(tree, 7, "");
        /*Node tree = new Node(1);
        tree.left = new Node(2);
        tree.right = new Node(7);
        tree.left.left = new Node(4);
        tree.left.right = new Node(3);
        tree.left.right.right = new Node(5);
        tree.left.right.right.left = new Node(6);
        tree.right.right = new Node(9);
        tree.right.right.right = new Node(12);
        tree.right.right.right.left = new Node(13);
        tree.right.left = new Node(8);
        tree.right.left.right = new Node(10);
        tree.right.left.right.left = new Node(11);
        tree.right.left.right.left.right = new Node(14);
        System.out.println(diameterOfBinaryTree(tree));

        Node1 tree1 = new Node1(1);
        tree1.left = new Node1(2);
        tree1.right = new Node1(7);
        tree1.left.left = new Node1(4);
        tree1.left.right = new Node1(3);
        tree1.left.right.right = new Node1(5);
        tree1.left.right.right.left = new Node1(6);
        tree1.right.right = new Node1(9);
        tree1.right.right.right = new Node1(12);
        tree1.right.right.right.left = new Node1(13);
        tree1.right.left = new Node1(8);
        tree1.right.left.right = new Node1(10);
        tree1.right.left.right.left = new Node1(11);
        tree1.right.left.right.left.right = new Node1(14);
        System.out.println(iterativeDiameter(tree1));

        //System.out.print(printDiameterOfBinaryTree(tree));
        N_Tree.NTree nt0 = new N_Tree.NTree('0');
        N_Tree.NTree nt1 = new N_Tree.NTree('1');
        N_Tree.NTree nt6 = new N_Tree.NTree('6');
        N_Tree.NTree nt7 = new N_Tree.NTree('7');
        N_Tree.NTree nt8 = new N_Tree.NTree('8');
        N_Tree.NTree nt2 = new N_Tree.NTree('2');
        N_Tree.NTree nt3 = new N_Tree.NTree('3');
        N_Tree.NTree nt4 = new N_Tree.NTree('4');
        N_Tree.NTree nt5 = new N_Tree.NTree('5');
        N_Tree.NTree nt9 = new N_Tree.NTree('9');
        nt0.addChild(nt1);
        nt1.addChild(nt2);
        nt1.addChild(nt6);
        nt6.addChild(nt7);
        nt6.addChild(nt8);
        nt2.addChild(nt3);
        nt2.addChild(nt9);
        nt2.addChild(nt4);
        nt4.addChild(nt5);
        System.out.print(longestPathNaryTree(nt0));*/
    pruneTree(tree);
  }

  public static class Node {

    public int data;
    public Node left;
    public Node right;
    public Node parent;
    public boolean isVisited;

    Node(int data) {
      this.data = data;
    }

    Node() {
    }
  }

  public Node root;

  // insert node in BST
  public void insert(Node root, int value) {
    if (root == null) {
      Node newNode = new Node();
      newNode.data = value;
      root = newNode;
    }
    if (value < root.data) {
      insert(root.left, value);
    } else if (value > root.data) {
      insert(root.right, value);
    }
  }

  // Insert node into tree Iterative
  public void insertItr(Node root, int data) {
    Node newNode = new Node();
    newNode.data = data;
    if (root == null) {
      root.data = data;
    } else {
      Node current = root;
      while (true) {
        if (data < current.data) {
          current = current.left;
          if (current == null) {
            current.left = newNode;
            return;
          }
        } else {
          current = current.right;
          if (current == null) {
            current.right = newNode;
            return;
          }
        }
      }
    }
  }

  // recursive search in BST
  public Node search(Node root, int data) {
    if (root == null) {
      return null;
    }
    if (root.data == data) {
      return root;
    }
    if (data < root.data) {
      search(root.left, data);
    } else if (data > root.data) {
      search(root.right, data);
    }
    return null;
  }

  // DFS : Implement a depth first search algorithm recursively.
  private Boolean DFS(Node root, int target) {
    if (root == null) {
      return false;
    }
    if (root.data == target) {
      return true;
    }
    return DFS(root.left, target) || DFS(root.right, target);
  }

  //DFS: Iterative
  private Boolean DFSIterative(Node root, int target) {
    if (root == null) {
      return false;
    }
    Stack<Node> _stack = new Stack<Node>();
    _stack.push(root);
    while (_stack.size() > 0) {
      Node temp = _stack.pop();
      if (temp.data == target) {
        return true;
      }
      if (temp.right != null) {
        _stack.push(temp.right);
      }
      if (temp.left != null) {
        _stack.push(temp.left);
      }
    }
    return false;
  }

  // BFS : Implement a breadth first search algorithm. Level order traversal
  private Boolean BFS(Node root, int target) {
    if (root == null) {
      return false;
    }
    Queue<Node> _queue = new LinkedList<>();
    Node tmp = null;
    _queue.add(root);
    while (_queue.size() > 0) {
      tmp = _queue.remove();
      if (tmp.data == target) {
        return true;
      }
      if (tmp.left != null) {
        _queue.add(tmp.left);
      }
      if (tmp.right != null) {
        _queue.add(tmp.right);
      }
    }
    return false;
  }

  //Print binary tree level by level in new line
  public static void printLevels(Node root) {
    if (root == null) {
      return;
    }
    // Create an empty queue for level order traversal
    Queue<Node> q = new LinkedList<>();
    // Enqueue Root and initialize height
    q.add(root);
    while (q.size() > 0) {
      // nodeCount (queue size) indicates number of nodes at current level.
      int nodeCount = q.size();
      // Dequeue all nodes of current level and Enqueue all nodes of next level
      while (nodeCount > 0) {
        Node node = q.poll();
        System.out.print(node.data);
        if (node.left != null) {
          q.add(node.left);
        }
        if (node.right != null) {
          q.add(node.right);
        }
        nodeCount--;
      }
      System.out.println();
    }
  }

  //Given a binary tree, print vertically from left to right
  //Binary Tree Vertical Order Traversal.
  // Time = O(n) and space = O(n)
  static void printVerticalOrder(Node root) {
    if (root == null) {
      return;
    }
    // Create a map and store vertical oder
    Map<Integer, ArrayList<Integer>> map = new HashMap<>();
    ArrayList<Integer> list;
    int hd = 0;
    // Create queue to do level order traversal. Every item of queue contains node and horizontal distance.
    Queue<Pair<Node, Integer>> que = new LinkedList<>();
    que.add(new Pair(root, hd));
    int max = 0, min = 0;
    while (!que.isEmpty()) {
      // pop from queue front
      Pair<Node, Integer> temp = que.poll();
      hd = temp.getValue();
      Node node = temp.getKey();

      if (!map.containsKey(hd)) {
        list = new ArrayList<>();
      } else {
        list = map.get(hd);
      }
      list.add(node.data);
      map.put(hd, list);
      if (node.left != null) {
        que.add(new Pair<>(node.left, hd - 1));
        min = Math.min(min, hd - 1);
      }
      if (node.right != null) {
        que.add(new Pair<>(node.right, hd + 1));
        max = Math.max(max, hd + 1);
      }
    }
    // Traverse the map and print nodes at every horizontal distance (hd)
    for (int i = min; i <= max; i++) {
      System.out.println(map.get(i));
    }
  }

  /* Given a binary tree, print its nodes in reverse level order */
  void reverseLevelOrder(Node root) {
    Stack<Node> S = new Stack<>();
    Queue<Node> Q = null;
    Q.add(root);
    // Do something like normal level order traversal order. Following are the
    // differences with normal level order traversal
    // 1) Instead of printing a node, we push the node to stack
    // 2) Right subtree is visited before left subtree
    while (Q.size() > 0) {
      root = Q.peek();
      Q.poll();
      S.push(root);
      if (root.right != null) {
        Q.add(root.right); // NOTE: RIGHT CHILD IS ENQUEUED BEFORE LEFT
      }
      if (root.left != null) {
        Q.add(root.left);
      }
    }
    // Now pop all items from stack one by one and print them
    while (S.size() > 0) {
      root = S.peek();
      System.out.print(root.data);
      S.pop();
    }
  }

  //Given a binary tree , print it’s nodes in spiral fashion. Example for Given Tree,Output should be F,B,G,I,D,A,C,E,H
  private void spiralLevelOrderTraversal(Node root) {
    if (root == null) {
      return;
    }
    Stack<Node> _stack1 = new Stack<>();
    Stack<Node> _stack2 = new Stack<>();
    _stack1.push(root);
    while (!_stack1.empty() || !_stack2.empty()) {
      while (!_stack1.empty()) {
        Node node = _stack1.pop();
        System.out.print(node.data);
        if (node.right != null) {
          _stack2.add(node.right);
        }
        if (node.left != null) {
          _stack2.add(node.left);
        }
      }
      while (!_stack2.isEmpty()) {
        Node node = _stack2.pop();
        System.out.print(node.data);
        if (node.left != null) {
          _stack1.add(node.left);
        }
        if (node.right != null) {
          _stack1.add(node.right);
        }
      }
    }
  }

  // Inorder traversal of BST
  public void inOrder(Node root) {
    if (root != null) {
      inOrder(root.left);
      System.out.print(root.data);
      inOrder(root.right);
    }
  }

  // Preorder traversal of BST
  public void preOrder(Node root) {
    if (root != null) {
      System.out.print(root.data);
      preOrder(root.left);
      preOrder(root.right);
    }
  }

  // In Order traversal using Morris traversal
  void MorrisTraversal(Node root) {
    Node current, pre;
    if (root == null) {
      return;
    }
    current = root;
    while (current != null) {
      if (current.left == null) {
        System.out.print(current.data + " ");
        current = current.right;
      } else {
        /* Find the inorder predecessor of current */
        pre = current.left;
        while (pre.right != null && pre.right != current) {
          pre = pre.right;
        }

        /* Make current as right child of its inorder predecessor */
        if (pre.right == null) {
          pre.right = current;
          current = current.left;
        }
        /* Revert the changes made in if part to restore the original tree i.e.,fix the right child of predecssor*/
        else {
          pre.right = null;
          System.out.print(current.data + " ");
          current = current.right;
        }   /* End of if condition pre->right == NULL */

      } /* End of if condition current->left == NULL*/
    }
  }

  // an iterative inOrder traversal
  public void inorder(Node root) {
    Node node = root;
    Stack<Node> stack = new Stack<Node>();
    while (!stack.isEmpty() || node != null) {
      if (node != null) {
        stack.push(node);
        node = node.left;
      } else {
        node = stack.pop();
        System.out.print(node.data + " ");
        node = node.right;
      }
    }
  }

  // An iterative process to print preorder traversal of Binary tree
  void iterativePreorder(Node root) {
    if (root == null) {
      return;
    }
    Stack<Node> nodeStack = new Stack<Node>();
    nodeStack.push(root);
            /* Pop all items one by one. Do following for every popped item
               a) print it
               b) push its right child
               c) push its left child
            Note that right child is pushed first so that left is processed first */
    while (nodeStack.size() > 0) {
      // Pop the top item from stack and print it
      Node node = nodeStack.peek();
      System.out.print(node.data);
      nodeStack.pop();
      if (node.right != null) {
        nodeStack.push(node.right);
      }
      if (node.left != null) {
        nodeStack.push(node.left);
      }
    }
  }

  // Postorder traversal of BST
  public void postOrder(Node root) {
    if (root != null) {
      postOrder(root.left);
      postOrder(root.right);
      System.out.print(root.data);
    }
  }

  // An iterative function to do postorder traversal of a given binary tree
  // Post order with two stack
    /*  1. Push root to first stack.
        2. Loop while first stack is not empty
            2.1 Pop a node from first stack and push it to second stack
            2.2 Push left and right children of the popped node to first stack
        3. Print contents of second stack*/
  // below is solution with one stack
  public void postOrderIterative(Node node) {
    Stack<Node> S = new Stack<Node>();
    // Check for empty tree
    if (node == null) {
      return;
    }
    S.push(node);
    Node prev = null;
    while (!S.isEmpty()) {
      Node current = S.peek();
      /* go down the tree in search of a leaf an if so process it and pop sack otherwise move down */
      if (prev == null || prev.left == current || prev.right == current) {
        if (current.left != null) {
          S.push(current.left);
        } else if (current.right != null) {
          S.push(current.right);
        } else {
          S.pop();
          System.out.print(current.data);
        }
                /* go up the tree from left node, if the child is right
                push it onto stack otherwise process parent and pop stack */
      } else if (current.left == prev) {
        if (current.right != null) {
          S.push(current.right);
        } else {
          S.pop();
          System.out.print(current.data);
        }
        /* go up the tree from right node and after coming back from right node process parent and pop stack */
      } else if (current.right == prev) {
        S.pop();
        System.out.print(current.data);
      }
      prev = current;
    }
  }

  //Construct Tree from given Inorder and Preorder traversals
  //We can also Use HashSet for Search
  static int preIndex = 0;

  Node buildTree(char in[], char pre[], int inStrt, int inEnd) {
    if (inStrt > inEnd) {
      return null;
    }
    /* Pick current node from Preorder traversal using preIndex and increment preIndex */
    Node tNode = new Node(pre[preIndex++]);
    /* If this node has no children then return */
    if (inStrt == inEnd) {
      return tNode;
    }
    /* Else find the index of this node in Inorder traversal */
    int inIndex = search(in, inStrt, inEnd, tNode.data);
    /* Using index in Inorder traversal, construct left and right sub-tress */
    tNode.left = buildTree(in, pre, inStrt, inIndex - 1);
    tNode.right = buildTree(in, pre, inIndex + 1, inEnd);
    return tNode;
  }

  /* Function to find index of value in arr[start...end] The function assumes that value is present in in[] */
  int search(char arr[], int start, int end, int value) {
    int i;
    for (i = start; i <= end; i++) {
      if (arr[i] == value) {
        return i;
      }
    }
    return i;
  }

  // Delete node from binary tree
  public void delete(int key) {
    Node parent = null;
    Node nodetoDelete = null;
    if (root.data == key) {
      nodetoDelete = root;
    } else {
      parent = getParent(root, key, nodetoDelete);
    }
    if (nodetoDelete.left == null && nodetoDelete.right == null) {
      if (parent != null) {
        if (parent.left == nodetoDelete) {
          parent.left = null;
        } else {
          parent.right = null;
        }
      } else {
        root = null;
      }
    } else if (nodetoDelete.left == null) {
      if (parent != null) {
        if (parent.left == nodetoDelete) {
          parent.left = nodetoDelete.right;
        } else {
          parent.right = nodetoDelete.right;
        }
      } else {
        root = nodetoDelete.right;
      }
    } else if (nodetoDelete.right == null) {
      if (parent != null) {
        if (parent.left == nodetoDelete) {
          parent.left = nodetoDelete.left;
        } else {
          parent.right = nodetoDelete.left;
        }
      } else {
        root = nodetoDelete.left;
      }
    } else {
      Node successor = FinMinValue(nodetoDelete.right);
      if (parent != null) {
        if (parent.right == nodetoDelete) {
          parent.right = successor;
        } else {
          parent.left = successor;
        }
      } else {
        root = successor;
      }
    }
  }

  private Node getParent(Node root, int target, Node NodetoDelete) {
    if (root != null) {
      if (root.left != null) {
        if ((root.left.data == target)) {
          NodetoDelete = root.left;
          return root;
        }
      }
      if (root.right != null) {
        if ((root.right.data == target)) {
          NodetoDelete = root.right;
          return root;
        }
      }
      getParent(root.left, target, NodetoDelete);
      getParent(root.right, target, NodetoDelete);
    }
    return root;
  }

  private Node FinMinValue(Node startNode) {
    Node parent = null;
    while (startNode.left != null) {
      parent = startNode;
      startNode = startNode.left;
    }
    if (parent != null) {
      parent.left = null;
    }
    return startNode;
  }

  /* Given a binary search tree and a key, this function deletes the key and returns the new root */
  Node deleteRec(Node root, int key) {
    /* Base Case: If the tree is empty */
    if (root == null) {
      return root;
    }
    /* Otherwise, recur down the tree */
    if (key < root.data) {
      root.left = deleteRec(root.left, key);
    } else if (key > root.data) {
      root.right = deleteRec(root.right, key);
    }
    // if key is same as root's key, then This is the node to be deleted
    else {
      // node with only one child or no child
      if (root.left == null) {
        Node temp = root.right;
        return temp;
      } else if (root.right == null) {
        Node temp = root.left;
        return temp;
      }
      // node with two children: Get the inorder successor (smallest in the right subtree)
      root.data = minValuedata(root.right);
      // Delete the inorder successor
      root.right = deleteRec(root.right, root.data);
    }
    return root;
  }

  int minValuedata(Node root) {
    int minv = root.data;
    while (root.left != null) {
      minv = root.left.data;
      root = root.left;
    }
    return minv;
  }

  //In a binary search tree, find the lowest common ancestor.
  private Node FindLCA(Node root, Node one, Node two) {
    while (root != null) {
      if (root.data < one.data && root.data < two.data) {
        return root.right;
      } else if (root.data > one.data && root.data > two.data) {
        return root.left;
      } else {
        return root;
      }
    }
    return null;
  }

  // Find LCA of Binary Tree.
  //The run time complexity is O(h), where h is the tree’s height. The space complexity is also O(h)
  public Node FindLCA_BTree(Node root, Node one, Node two) {
    HashSet<Node> hash = new HashSet<Node>();
    while (one != null || two != null) {
      if (one != null) {
        if (hash.contains(one)) {
          return one;
        } else {
          hash.add(one);
        }
        one = one.parent;
      }
      if (two != null) {
        if (hash.contains(two)) {
          return two;
        } else {
          hash.add(two);
        }
        two = two.parent;
      }
    }
    return null;
  }

  // Find LCA best way no Space Required
  public Node FindLCA_Best(Node root, Node one, Node two) {
    int h1 = getHeight(one);
    int h2 = getHeight(two);
    //swap elements
    if (h1 > h2) {
      //swapping h1 and h2 using XOR gate
      h1 ^= h2;
      h2 ^= h1;
      h1 ^= h2;
      swap(one.data, two.data);
    }
    int dh = h2 - h1;
    for (int i = 0; i < dh; i++) {
      two = two.parent;
    }
    while (one != null && two != null) {
      if (one.data == two.data) {
        return one;
      }
      one = one.parent;
      two = two.parent;
    }
    return null;
  }

  public int getHeight(Node node) {
    int height = 0;
    while (node != null) {
      height++;
      node = node.parent;
    }
    return height;
  }

  //Find LCA without parent pointer
  static Node FindLowestCommonAncestor(Node n, int n1, int n2) {
    if (n == null) {
      return null;
    }
    if (n.data == n1 || n.data == n2) {
      return n;
    }
    Node left = FindLowestCommonAncestor(n.left, n1, n2);
    Node right = FindLowestCommonAncestor(n.right, n1, n2);
    if (left == null && right == null) {
      return null;
    }
    if (left != null && right != null) {
      return n;
    }
    if (left != null) {
      return left;
    }
    if (right != null) {
      return right;
    }
    return null;
  }

  //LCA of Binary tree
  public static Node findFirstCommonAncestor(Node root, Node n1, Node n2) {
    if (root == null) {
      return null;
    }
    if (!contains(root, n1) || !contains(root, n2)) {
      return null;
    }
    if (root == n1 || root == n2) {
      return root;
    }
    boolean n1OnLeft = contains(root.left, n1);
    boolean n2OnLeft = contains(root.left, n2);
    if (n1OnLeft != n2OnLeft) {
      return root;
    } else if (n1OnLeft && n2OnLeft) {
      return findFirstCommonAncestor(root.left, n1, n2);
    } else if (!n1OnLeft && !n2OnLeft) {
      return findFirstCommonAncestor(root.right, n1, n2);
    }
    return null;
  }

  private static boolean contains(Node root, Node n) {
    if (root == null) {
      return false;
    }
    if (root == n) {
      return true;
    }
    return contains(root.left, n) || contains(root.right, n);
  }

  // Find the Diameter of Binary Tree. The diameter of a binary tree is the length of the longest path between
  //any two nodes in a tree. This path may or may not pass through the root.
  // Iterative [http://techieme.in/tree-diameter/]
  static class Node1 {

    int data;
    Node1 left;
    Node1 right;
    int maxDistance;
    int rHeight;
    int lHeight;

    Node1(int data) {
      this.data = data;
    }
  }

  public static int iterativeDiameter(Node1 root) {
    if (root == null) {
      return 0;
    }
    Stack<Node1> S = new Stack<>();
    Stack<Node1> O = new Stack<>();
    Node1 node;
    int maxDistance = 0;
    S.push(root);
    while (!S.empty()) {
      node = S.pop();
      O.push(node);
      if (node.left != null) {
        S.push(node.left);
      }
      if (node.right != null) {
        S.push(node.right);
      }
    }
    while (!O.empty()) {
      node = O.pop();
      if (node.left == null) {
        node.lHeight = 1;
        node.maxDistance = 0;
      } else {
        node.lHeight = Math.max(node.left.lHeight, node.left.rHeight) + 1;
      }
      if (node.right == null) {
        node.rHeight = 1;
        node.maxDistance = 0;
      } else {
        node.rHeight = Math.max(node.right.rHeight, node.right.lHeight) + 1;
      }

      if (node.left != null && node.right != null) {
        int temp = node.lHeight + node.rHeight - 1;
        node.maxDistance = temp;
        if (maxDistance < temp) {
          maxDistance = temp;
        }
      }
    }
    return maxDistance;
  }

  // recursive
  //http://www.makeinjava.com/find-diameter-binary-tree-java-dfs-recursive-example/
  static int diameter = 0;

  public static int diameterOfBinaryTree(Node root) {
    maxDepth(root);
    return diameter;
  }

  private static int maxDepth(Node root) {
    if (root == null) {
      return 0;
    }
    int left = maxDepth(root.left);
    int right = maxDepth(root.right);
    diameter = Math.max(diameter, left + right + 1);
    return Math.max(left, right) + 1;
  }

  // print longest path of binary tree (directed)
  public static String printDiameterOfBinaryTree(Node root) {
    longestPath(root);
    String s = "";
    while (!path.isEmpty()) {
      s += path.pop().data + " ";
    }
    return s;
  }

  static Stack<Node> path;

  static Stack<Node> longestPath(Node root) {
    if (root == null) {
      Stack s = new Stack();
      return s;
    }
    Stack<Node> l = longestPath(root.left);
    Stack<Node> r = longestPath(root.right);
    if (l.size() + r.size() + 1 > diameter) {
      diameter = l.size() + r.size() + 1;
      Stack<Node> tmp = new Stack();
      tmp.addAll(l);
      tmp.push(root);
      tmp.addAll(r);
      path = tmp;
    }
    l.push(root);
    r.push(root);
    return l.size() > r.size() ? l : r;
  }

  // Write a program to output the length of the longest path (from one node to another) in undirected tree
  static int longestPathNaryTree(N_Tree.NTree root) {
    longestPathNaryTreeUtil(root, new HashSet<>());
    System.out.println("start: - " + (char) _path[0] + " End: - " + (char) _path[1]);
    return maxSoFar;
  }

  static int maxSoFar = 0;
  static int[] _path = new int[2];

  static int longestPathNaryTreeUtil(N_Tree.NTree root, Set<N_Tree.NTree> visited) {
    int large = 0;
    int small = 0;
    visited.add(root);
    for (N_Tree.NTree next : root.children) {
      if (!visited.contains(next)) {
        int val = longestPathNaryTreeUtil(next, visited);
        if (val > large) {
          small = large;
          large = val;
          _path[1] = _path[0];
          _path[0] = next.data;
        } else if (val > small && val != large) {
          small = val;
          _path[1] = next.data;
        }
      }
    }
    maxSoFar = Math.max(maxSoFar, (small + large));
    return large + 1;
  }

  // Find out if given tree is Binary Search Tree or not
  //Using Morris traversal and maintaining the pre node, solution in O(n) time complexity and O(1) space complexity.
  public static boolean isValidBST(Node root) {
    Node pre = null, cur = root, tmp;
    while (cur != null) {
      if (cur.left == null) {
        if (pre != null && pre.data >= cur.data) {
          return false;
        }
        pre = cur;
        cur = cur.right;
      } else {
        tmp = cur.left;
        while (tmp.right != null && tmp.right != cur) {
          tmp = tmp.right;
        }
        if (tmp.right == null) { // left child has not been visited
          tmp.right = cur;
          cur = cur.left;
        } else { // left child has been visited already
          tmp.right = null;
          if (pre != null && pre.data >= cur.data) {
            return false;
          }
          pre = cur;
          cur = cur.right;
        }
      }
    }
    return true;
  }

  //Integer.MIN_VALUE, Integer.MAX_VALUE
  public static boolean validateBST(Node root, int min, int max) {
    if (root == null) {
      return true;
    }
    // not in range
    if (root.data <= min || root.data >= max) {
      return false;
    }
    // left subtree must be < root.val && right subtree must be > root.val
    return validateBST(root.left, min, root.data) && validateBST(root.right, root.data, max);
  }

  //iterative solution
  public static boolean isValidBST1(Node root) {
    if (root == null) {
      return true;
    }
    Queue<BNode> queue = new LinkedList<BNode>();
    queue.add(new BNode(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
    while (!queue.isEmpty()) {
      BNode b = queue.poll();
      if (b.n.data <= b.left || b.n.data >= b.right) {
        return false;
      }
      if (b.n.left != null) {
        queue.offer(new BNode(b.n.left, b.left, b.n.data));
      }
      if (b.n.right != null) {
        queue.offer(new BNode(b.n.right, b.n.data, b.right));
      }
    }
    return true;
  }

  //define a BNode class with TreeNode and it's boundaries
  static class BNode {

    Node n;
    double left;
    double right;

    public BNode(Node n, double left, double right) {
      this.n = n;
      this.left = left;
      this.right = right;
    }
  }

  //Second largest element in BST. Time complexity of the above solution is O(h) where h is height of BST.
  public static int findSecondLargestValueInBST(Node root) {
    int secondMax;
    Node pre = root;
    Node cur = root;
    while (cur.right != null) {
      pre = cur;
      cur = cur.right;
    }
    if (cur.left != null) {
      cur = cur.left;
      while (cur.right != null) {
        cur = cur.right;
      }
      secondMax = cur.data;
    } else {
      if (cur == root && pre == root)
      //Only one node in BST
      {
        secondMax = Integer.MIN_VALUE;
      } else {
        secondMax = pre.data;
      }
    }
    return secondMax;
  }

  //recursive
  void secondLargestUtil(Node root, int c) {
    // Base cases, the second condition is important to avoid unnecessary recursive calls
    if (root == null || c >= 2) {
      return;
    }
    // Follow reverse inorder traversal so that the largest element is visited first
    secondLargestUtil(root.right, c);
    c++;
    // If c becomes k now, then this is the 2nd largest
    if (c == 2) {
      System.out.print("2nd largest element is " + root.data);
      return;
    }
    secondLargestUtil(root.left, c);
  }

  //K’th smallest element in BST using O(1) Extra Space
  int KSmallestUsingMorris(Node root, int k) {
    // Count to iterate over elements till we get the kth smallest number
    int count = 0;
    int ksmall = Integer.MIN_VALUE; // store the Kth smallest
    Node curr = root; // to store the current node
    while (curr != null) {
      // Like Morris traversal if current does not have left child rather than printing as we did in inorder, we will just
      // increment the count as the number will be in an increasing order
      if (curr.left == null) {
        count++;
        // if count is equal to K then we found the kth smallest, so store it in ksmall
        if (count == k) {
          ksmall = curr.data;
        }
        // go to current's right child
        curr = curr.right;
      } else {
        // we create links to Inorder Successor and count using these links
        Node pre = curr.left;
        while (pre.right != null && pre.right != curr) {
          pre = pre.right;
        }
        // building links
        if (pre.right == null) {
          //link made to Inorder Successor
          pre.right = curr;
          curr = curr.left;
        }
        // While breaking the links in so made temporary threaded tree we will check for the K smallest
        // condition
        else {
          // Revert the changes made in if part (break link from the Inorder Successor)
          pre.right = null;
          count++;
          // If count is equal to K then we found the kth smallest and so store it in ksmall
          if (count == k) {
            ksmall = curr.data;
          }
          curr = curr.right;
        }
      }
    }
    return ksmall; //return the found value
  }

  //given a binary search tree and you are asked to find the Kth smallest element in that tree.
  private Node findKthNode_SMALLEST(Node root, int k) {
    if (root == null) {
      return null;
    }
    int leftSize = findLeftTreeSize(root.left);
    if (leftSize == k - 1) {
      return root;
    } else if (leftSize < k - 1) {
      findKthNode_SMALLEST(root.left, k);
    } else {
      findKthNode_SMALLEST(root.right, k - leftSize - 1);
    }
    return null;
  }

  private int findLeftTreeSize(Node root) {
    if (root == null) {
      return 0;
    } else {
      return 1 + findLeftTreeSize(root.left) + findLeftTreeSize(root.right);
    }
  }

  //Follow up: what if the BST is modified (insert/delete operations) often and you need to find the k-th smallest frequently?
  //Idea is to while building up the tree we can maintain number of elements of left subtree in every node.
  //Time complexity: O(h) where h is height of tree.
  class Node_t {

    int data;
    int lCount;
    Node_t left;
    Node_t right;
  }

  //build a tree with counting left subtree nodes of every node
  Node_t insert_node(Node_t root, Node_t node) {
    Node_t pTraverse = root;
    Node_t currentParent = root;
    // Traverse till appropriate node
    while (pTraverse != null) {
      currentParent = pTraverse;
      if (node.data < pTraverse.data) {
        /* We are branching to left subtree increment node count */
        pTraverse.lCount++;
        /* left subtree */
        pTraverse = pTraverse.left;
      } else {
        /* right subtree */
        pTraverse = pTraverse.right;
      }
    }
    /* If the tree is empty, make it as root node */
    if (root == null) {
      root = node;
    } else if (node.data < currentParent.data) {
      /* Insert on left side */
      currentParent.left = node;
    } else {
      /* Insert on right side */
      currentParent.right = node;
    }
    return root;
  }

  int k_smallest_element(Node_t root, int k) {
    int ret = -1;
    if (root != null) {
      Node_t pTraverse = root;
      /* Go to k-th smallest */
      while (pTraverse != null) {
        if ((pTraverse.lCount + 1) == k) {
          ret = pTraverse.data;
          break;
        } else if (k
            > pTraverse.lCount) { /*  There are less nodes on left subtree Go to right subtree */
          k = k - (pTraverse.lCount + 1);
          pTraverse = pTraverse.right;
        } else { /* The node is on left subtree */
          pTraverse = pTraverse.left;
        }
      }
    }
    return ret;
  }

  // Convert sorted array into balanced tree
  // construct BST from inorder traversal
  private static Node sortedArraytoBST(int[] arr, int start, int end) {
    if (end < start) {
      return null;
    }
    int mid = start + (end - start) / 2;
    Node tree = new Node();
    tree.data = arr[mid];
    tree.left = sortedArraytoBST(arr, start, mid - 1);
    tree.right = sortedArraytoBST(arr, mid + 1, end);
    return tree;
  }

  /* A O(n) iterative program for construction of BST from preorder traversal
   * Deserialize the BST IN = 11,6,4,8,19,17,43 */
  Node constructBST(int pre[]) {
    // The first element of pre[] is always root
    Node root = new Node(pre[0]);
    Stack<Node> s = new Stack<>();
    s.push(root);
    // Iterate through rest of the size-1 items of given preorder array
    for (int i = 1; i < pre.length; ++i) {
      Node temp = null;
      /* Keep on popping while the next value is greater than stack's top value. */
      while (!s.isEmpty() && pre[i] > s.peek().data) {
        temp = s.pop();
      }
      // Make this greater value as the right child and push it to the stack
      if (temp != null) {
        temp.right = new Node(pre[i]);
        s.push(temp.right);
      }
      // If the next value is less than the stack's top value, make this value
      // as the left child of the stack's top node. Push the new node to stack
      else {
        temp = s.peek();
        temp.left = new Node(pre[i]);
        s.push(temp.left);
      }
    }
    return root;
  }

  //Given a preorder traversal of a BST, print out the inorder traversal of the BST
  static void inOrderFromPreOrderBST(int[] pre) {
    if (pre == null) {
      return;
    }
    if (pre.length < 2) {
      System.out.println(pre[0]);
    }
    Stack<Integer> s = new Stack<>();
    s.push(pre[0]);
    for (int i = 1; i < pre.length; i++) {
      while (!s.empty() && pre[i] > s.peek()) {
        System.out.println(s.pop());
      }
      s.push(pre[i]);
    }
    System.out.println(s.pop());
  }

  //Two nodes of a BST are swapped, correct the BST
  public void CorrectBST(Node root) {
    Node first = null, middle = null, last = null, prev = null;
    CorrectBSTUtil(root, first, middle, last, prev);
    if (first != null && last != null) {
      swap(first.data, last.data);
    } else if (first != null && middle != null) {
      swap(first.data, middle.data);
    }
  }

  public void CorrectBSTUtil(Node root, Node first, Node middle, Node last, Node prev) {
    if (root != null) {
      CorrectBSTUtil(root.left, first, middle, last, prev);
      if (prev != null && root.data < prev.data) {
        if (first == null) {
          first = prev;
          middle = root;
        } else {
          last = root;
        }
      }
      prev = root;
      CorrectBSTUtil(root.right, first, middle, last, prev);
    }
  }

  public void swap(int a, int b) {
    int t = a;
    a = b;
    b = t;
  }

  /*  You are given a binary tree in which each node contains an integer value (which
      might be positive or negative). Design an algorithm to count the number of paths that sum to a
      given value. The path does not need to start or end at the root or a leaf, but it must go downwards
      (traveling only from parent nodes to ch ild nodes). Time = O(N) space O(logN) or O(N) for unbalanced tree*/
  public static int countPathsWithSum(Node node, int targetSum, int runningSum,
      HashMap<Integer, Integer> pathCount) {
    if (node == null) {
      return 0; // Base case
    }
    runningSum += node.data;
    /* Count paths with sum ending at the current node. */
    int sum = runningSum - targetSum;
    int totalPaths = pathCount.getOrDefault(sum, 0);

    /* If runningSum equals targetSum, then one additional path starts at root. Add in this path.*/
    if (runningSum == targetSum) {
      totalPaths++;
    }
    /* Add runningSum to pathCounts. */
    incrementHashTable(pathCount, runningSum, 1);
    /* Count paths with sum on the left and right. */
    totalPaths += countPathsWithSum(node.left, targetSum, runningSum, pathCount);
    totalPaths += countPathsWithSum(node.right, targetSum, runningSum, pathCount);

    incrementHashTable(pathCount, runningSum, -1); // Remove runningSum
    return totalPaths;
  }

  public static void incrementHashTable(HashMap<Integer, Integer> hashTable, int key, int delta) {
    int newCount = hashTable.getOrDefault(key, 0) + delta;
    if (newCount == 0) { // Remove when zero to reduce space usage
      hashTable.remove(key);
    } else {
      hashTable.put(key, newCount);
    }
  }

  //You are given a binary tree in which each node contains a value. Design an algorithm to print all paths which
  //sum to a given value. The path does not need to start or end at the root or a leaf.
  //Time Complexity = O(NlogN) and space (logN)
  void findSumUtil(Node node, int sum, int[] path, int level) {
    if (node == null) {
      return;
    }
    /* Insert current node into path. */
    path[level] = node.data;
    /* Look for paths with a sum that ends at this node. */
    int t = 0;
    for (int i = level; i >= 0; i--) {
      t += path[i];
      if (t == sum) {
        print(path, i, level);
      }
    }
    /* Search nodes beneath this one. */
    findSumUtil(node.left, sum, path, level + 1);
    findSumUtil(node.right, sum, path, level + 1);
    /* Remove current node from path. Not strictly necessary, since
     * we would ignore this value, but it's good practice. */
    path[level] = Integer.MIN_VALUE;
  }

  public void findSum(Node node, int sum) {
    int depth = depth(node);
    int[] path = new int[depth];
    findSumUtil(node, sum, path, 0);
  }

  public static void print(int[] path, int start, int end) {
    for (int i = start; i <= end; i++) {
      System.out.print(path[i] + " ");
    }
    System.out.println();
  }

  public int depth(Node node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + Math.max(depth(node.left), depth(node.right));
    }
  }

  //Binary Tree Maximum Path Sum
  public int maxPathSum(Node root) {
    if (root == null) {
      return 0;
    }
    int maxLeft = maxPathSum(root.left); //max length in the whole left subtree
    int maxRight = maxPathSum(root.right); //max length in the whole right subtree
    int leftLen = 0; //max length in the left subtree starting with left child
    int rightLen = 0; //max length in the left subtree starting with left child
    if (root.left != null) {
      leftLen = Math.max(root.left.data, 0);
    }
    if (root.right != null) {
      rightLen = Math.max(root.right.data, 0);
    }
    int maxLength = root.data;
    if (leftLen > 0) {
      maxLength += leftLen;
    }
    if (rightLen > 0) {
      maxLength += rightLen;
    }
    if (root.left != null) {
      maxLength = Math.max(maxLeft, maxLength);
    }
    if (root.right != null) {
      maxLength = Math.max(maxRight, maxLength);
    }
    //root.val is replaced with the maximum length starting from root downwards
    root.data = Math.max(leftLen, rightLen) + root.data;
    return maxLength;
  }

  //Given a binary tree, every node has a int value, return the root node of subtree with the largest sum up value.
  private Node maxSumSubtree(Node root) {
    if (root == null) {
      return null;
    }

    int maxsum = 0;
    Node res = null;
    helper(root, res, maxsum);
    return res;
  }

  int helper(Node p, Node res, int maxsum) {
    if (p == null) {
      return 0;
    }
    int lsum = helper(p.left, res, maxsum);
    int rsum = helper(p.right, res, maxsum);
    int total = lsum + rsum + p.data;
    if (total > maxsum) {
      maxsum = total;
      res = p;
    }
    return total;
  }

  //Find the maximum sum of the subtree (triangle) from the given tree.
  private int FindMaxSumSubtree(Node root) {
    int max_sum = 0;
    max_sum = MaxSumSubtree(root, max_sum);
    return max_sum;
  }

  private int MaxSumSubtree(Node root, int max_sum) {
    int sum = 0;
    int lsum = 0;
    int rsum = 0;
    if (root == null) {
      return 0;
    }
    if (root.left != null) {
      lsum = MaxSumSubtree(root.left, max_sum);
    }
    if (root.right != null) {
      rsum = MaxSumSubtree(root.right, max_sum);
    }
    sum = root.data + lsum + rsum;
    if (max_sum < sum) {
      max_sum = sum;
    }
    return max_sum;
  }

  //Print Right View of a Binary Tree
  public void rightView() {
    int max_level = 0;
    rightViewUtil(root, 1, max_level);
  }

  public void rightViewUtil(Node root, int level, int max_level) {
    if (root == null) {
      return;
    }
    if (max_level < level) {
      System.out.print(root.data);
      max_level = level;
    }
    rightViewUtil(root.right, level + 1, max_level);
    rightViewUtil(root.left, level + 1, max_level);
  }

  // Given a Binary Tree mirror it with left and right subtree
  private Node mirrorTreeIterative(Node root) {
    Node newNode = new Node();
    if (root == null) {
      return null;
    }
    Queue<Node> _q = new LinkedList<>();
    _q.add(root);
    while (!_q.isEmpty()) {
      newNode = _q.poll();
      //SWAP(newNode.left, newNode.right);
      if (newNode.left != null) {
        _q.add(newNode.left);
      }
      if (newNode.right != null) {
        _q.add(newNode.right);
      }
    }
    return newNode;
  }

  //with new tree
  private Node mirrorTree(Node root) {
    Node newNode = new Node();
    if (root == null) {
      return null;
    } else {
      newNode.data = root.data;
      newNode.left = mirrorTree(root.right);
      newNode.right = mirrorTree(root.left);
    }
    return newNode;
  }

  //Given a binary tree, check whether it is a mirror of itself.
  boolean isMirror(Node node1, Node node2) {
    // if both trees are empty, then they are mirror image
    if (node1 == null && node2 == null) {
      return true;
    }
    // For two trees to be mirror images, the following three conditions must be true
    // 1 - Their root node's key must be same
    // 2 - left subtree of left tree and right subtree of right tree have to be mirror images
    // 3 - right subtree of left tree and left subtree of right tree have to be mirror images
    if (node1 != null && node2 != null && node1.data == node2.data) {
      return (isMirror(node1.left, node2.right)
          && isMirror(node1.right, node2.left));
    }
    // if neither of the above conditions is true then root1 and root2 are mirror images
    return false;
  }

  //Given the root of a binary search tree and 2 numbers min and max,
  //trim the tree such that all the numbers in the new tree are between min and max (inclusive).
  private Node trimBST(Node root, int minValue, int maxValue) {
    if (root == null) {
      return null;
    }
    root.left = trimBST(root.left, minValue, maxValue);
    root.right = trimBST(root.right, minValue, maxValue);
    if (minValue <= root.data && root.data <= maxValue) {
      return root;
    }
    if (root.data < minValue) {
      return root.right;
    }
    if (root.data > maxValue) {
      return root.left;
    }
    return null;
  }

  // Given a Binary Search Tree and a value, find the closest element to the given value in BST.
  int min_diff(int a, int b, int key) {
    if (Math.abs(a - key) <= Math.abs(b - key)) {
      return a;
    } else {
      return b;
    }
  }

  int searchClosest(Node root, int key) {
    int close = Integer.MAX_VALUE;
    if (root == null) {
      return 0;
    }
    if (key == root.data) {
      return key;
    }
    close = min_diff(close, root.data, key);
    if (key > root.data && root.right != null) {
      close = min_diff(close, searchClosest(root.right, key), key);
    }
    if (key < root.data && root.left != null) {
      close = min_diff(close, searchClosest(root.left, key), key);
    }
    return close;
  }

  //Given a binary search tree(BST), sum all the nodes which are at on the same vertical line.
  int HD_OFFSET = 16;

  private void verticalSUM(Node root, int[] sum, int hd, int min, int max) {
    int index = hd + HD_OFFSET / 2;
    if (index < min) {
      min = index;
    }
    if (index > max) {
      max = index;
    }
    sum[index] += root.data;
    verticalSUM(root.left, sum, hd - 1, min, max);
    verticalSUM(root.right, sum, hd + 1, min, max);
  }

  static class DLL {

    int data;
    DLL next;
    DLL prev;

    public DLL(int data) {
      this.data = data;
    }
  }

  //Given Binary tree print vertical sum
  static void verticalSumDLL(Node root) {
    // Create a doubly linked list node to store sum of lines going through root. Vertical sum is initialized as 0.
    DLL _dllNode = new DLL(0);
    // Compute vertical sum of different lines
    verticalSumDLLUtil(root, _dllNode);
    // llnode refers to sum of vertical line going through root. Move llnode to the leftmost line.
    while (_dllNode.prev != null) {
      _dllNode = _dllNode.prev;
    }
    // Prints vertical sum of all lines starting from leftmost vertical line
    while (_dllNode != null) {
      System.out.print(_dllNode.data + " ");
      _dllNode = _dllNode.next;
    }
  }

  // Constructs linked list
  static void verticalSumDLLUtil(Node tnode, DLL _dllNode) {
    // Add current node's data to its vertical line
    _dllNode.data = _dllNode.data + tnode.data;
    // Recursively process left subtree
    if (tnode.left != null) {
      if (_dllNode.prev == null) {
        _dllNode.prev = new DLL(0);
        _dllNode.prev.next = _dllNode;
      }
      verticalSumDLLUtil(tnode.left, _dllNode.prev);
    }
    // Process right subtree
    if (tnode.right != null) {
      if (_dllNode.next == null) {
        _dllNode.next = new DLL(0);
        _dllNode.next.prev = _dllNode;
      }
      verticalSumDLLUtil(tnode.right, _dllNode.next);
    }
  }

  //Given a binary tree where all the right nodes are leaf nodes,
  // flip it upside down and turn it into a tree with left leaf nodes.
  Node FlipTree(Node root) {
    if (root == null) {
      return null;
    }
    // Working base condition
    if (root.left == null && root.right == null) {
      return root.left;
    }
    Node newRoot = FlipTree(root.left);
    root.left.left = root.right;
    root.left.right = root;
    root.left = null;
    root.right = null;
    return newRoot;
  }

  // Build max-heap
  public static void BuildMaxHeap(int[] arr) {
    for (int i = arr.length - 1; i >= 0; i--) {
      MaxHeapify(arr, i);
    }
  }

  public static void MaxHeapify(int[] arr, int i) {
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    int largest = i;
    if (left < arr.length && arr[left] > arr[largest]) {
      largest = left;
    }
    if (right < arr.length && arr[right] > arr[largest]) {
      largest = right;
    }
    if (largest != i) {
      int temp = arr[i];
      arr[i] = arr[largest];
      arr[largest] = temp;
      MaxHeapify(arr, largest);
    }
  }

  //Check if tree is a balanced tree.
  //This code runs in 0(N) time and 0(H) space, where H is the height of the tree.
  public static boolean isBalanced3(Node n) {
    return getHeightBalanced(n) != -1;
  }

  private static int getHeightBalanced(Node n) {
    if (n == null) {
      return 0;
    }
    int leftHeight = getHeightBalanced(n.left);
    if (leftHeight == -1) {
      return -1;
    }
    int rightHeight = getHeightBalanced(n.right);
    if (rightHeight == -1) {
      return -1;
    }
    if (Math.abs(leftHeight - rightHeight) > 1) {
      return -1;
    }
    return 1 + Math.max(leftHeight, rightHeight);
  }

  //Write a function to see if a binary tree is "superbalanced" (a new tree property we just made up).
  //A tree is "superbalanced" if the difference between the depths of any two leaf nodes is no greater than one.
  private static boolean isSuperBalanced(Node root) {
    if (root == null) {
      return true;
    }
    Stack<javafx.util.Pair<Node, Integer>> stack = new Stack<>();
    stack.push(new Pair<>(root, 0));
    ArrayList<Integer> set = new ArrayList<>();
    while (stack.size() > 0) {
      Pair<Node, Integer> node = stack.pop();
      int depth = node.getValue();
      if (node.getKey().left == null && node.getKey().right == null) {
        if (!set.contains(depth)) {
          set.add(depth);
        }
        if (set.size() > 2 || (set.size() == 2 && Math.abs(set.get(0) - set.get(1)) > 1)) {
          return false;
        }
      } else {
        if (node.getKey().left != null) {
          stack.push(new Pair<>(node.getKey().left, depth + 1));
        }
        if (node.getKey().right != null) {
          stack.push(new Pair<>(node.getKey().right, depth + 1));
        }
      }
    }
    return true;
  }

  // Inorder Successor in Binary Search Tree
  public Node inorderSuccessor(Node root, Node p) {
    if (root == null || p == null) {
      return null;
    }
    Node successor = null;
    while (root != null) {
      if (p.data < root.data) {
        successor = root;
        root = root.left;
      } else {
        root = root.right;
      }
    }

    return successor;
  }

  //Inorder Successor in Binary Search Tree
  public static Node inorderSuccessor(Node n) {
    if (n == null) {
      return null;
    }
    // case 1: n has right subtree ->
    //         return leftmost node of right subtree
    if (n.right != null) {
      return leftmostChild(n.right);
    }
    // case 2:   n has no right subtree
    // case 2.1: n is left child of its parent ->
    //           just return its parent
    // case 2.2: n is right child of its parent ->
    //           n goes up until n is left child of its parent,
    //           then return this parent
    // case 3:   n is the last node in traversal ->
    //           return root's parent, ie., null
    while (n.parent != null && n.parent.right == n) {
      n = n.parent;
    }
    return n.parent;
  }

  private static Node leftmostChild(Node n) {
    if (n.left == null) {
      return n;
    }
    return leftmostChild(n.left);
  }

  //PreOrder Successor in Binary Search Tree
  public static Node preorderSuccessor(Node n) {
    if (n == null) {
      return null;
    }
    // case 1: n has a child ->
    //         just return that child (left if exists, otherwise right)
    if (n.left != null) {
      return n.left;
    } else if (n.right != null) {
      return n.right;
    }
    // case 2: n has no child ->
    //         n climbs up until reaching a parent that has a right child
    //         (which is not n), then return this right child
    while (n.parent != null && (n.parent.right == null || n.parent.right == n)) {
      n = n.parent;
    }
    // case 3: n is the last node in traversal ->
    //         return root's parent, ie., null
    if (n.parent == null) {
      return null;
    }
    return n.parent.right;
  }

  //Postorder Successor in Binary Search Tree
  public static Node postorderSuccessor(Node n) {
    // case 1: n is the last node in traversal ->
    //         return root's parent, ie., null
    if (n == null || n.parent == null) {
      return null;
    }
    // case 2:   n is left child of its parent ->
    //           just return parent
    // case 3:   n is right child of its parent
    // case 3.1: parent has no right child ->
    //           just return parent
    if (n.parent.right == n || n.parent.right == null) {
      return n.parent;
    }
    // case 3.2: parent has right child ->
    //           return leftmost bottom node of parent's right subtree
    return leftmostBottomChild(n.parent.right);
  }

  private static Node leftmostBottomChild(Node n) {
    if (n.left == null && n.right == null) {
      return n;
    }
    if (n.left != null) {
      return leftmostBottomChild(n.left);
    } else {
      return leftmostBottomChild(n.right);
    }
  }

  //Get Level of a node in a Binary Tree, level = 1
  private int getLevel(Node node, int data, int level) {
    if (node == null) {
      return 0;
    }
    if (node.data == data) {
      return level;
    }
    int downlevel = getLevel(node.left, data, level + 1);
    if (downlevel != 0) {
      return downlevel;
    }
    downlevel = getLevel(node.right, data, level + 1);
    return downlevel;
  }

  // Recursive function to check if two Nodes are siblings
  Boolean isSibling(Node root, Node a, Node b) {
    if (root == null) {
      return false;
    }
    return ((root.left == a && root.right == b) ||
        (root.left == b && root.right == a) ||
        isSibling(root.left, a, b) ||
        isSibling(root.right, a, b));
  }

  //Check if two nodes are cousins in a Binary Tree
  private Boolean isCousin(Node root, Node a, Node b) {
    if ((getLevel(root, a.data, 1) == getLevel(root, b.data, 1)) && !(isSibling(root, a, b))) {
      return true;
    }
    return false;
  }
  //Find distance between two given keys of a Binary Tree
  //Dist(n1, n2) = Dist(root, n1) + Dist(root, n2) - 2*Dist(root, lca)

  //Morris in-order traversal, Find the median of the BST
  public void morrisTraverse(Node root) {
    while (root != null) {
      if (root.left == null) {
        System.out.println(root.data);
        root = root.right;
      } else {
        Node ptr = root.left;
        while (ptr.right != null && ptr.right != root) {
          ptr = ptr.right;
        }

        if (ptr.right == null) {
          ptr.right = root;
          root = root.left;
        } else {
          ptr.right = null;
          System.out.println(root.data);
          root = root.right;
        }
      }
    }
  }

  //Find two node in BST that add up to given number x in O(logn) space and O(n) time
  List<Integer> find_sum(int search, Node root) {
    Stack<Node> s1 = new Stack<Node>();
    Stack<Node> s2 = new Stack<Node>();
    Node curr1 = root;
    Node curr2 = root;
    boolean done1, done2;
    done1 = done2 = false;
    int val1 = 0, val2 = 0;
    List<Integer> result = new ArrayList<Integer>();
    while (true) {
      while (!done1) {
        if (curr1 != null) {
          s1.push(curr1);
          curr1 = curr1.left;
        } else if (s1.empty()) {
          done1 = true;
        } else {
          curr1 = s1.pop();
          val1 = curr1.data;
          curr1 = curr1.right;
          done1 = true;
        }
      }
      while (!done2) {
        if (curr2 != null) {
          s2.push(curr2);
          curr2 = curr2.right;
        } else {
          if (s2.empty()) {
            done2 = true;
          } else {
            curr2 = s2.pop();
            val2 = curr2.data;
            curr2 = curr2.left;
            done2 = true;
          }
        }
      }
      if (val1 + val2 == search) {
        result.add(val1);
        result.add(val2);
        return result;
      } else if (val1 + val2 > search) {
        done2 = false;
      } else {
        done1 = false;
      }
    }
  }

  //Perfect Binary Tree Specific Level Order Traversal
  //1 2 3 4 7 5 6 8 15 9 14 10 13 11 12 16 31 17 30 18 29 19 28 20 27 21 26  22 25 23 24
  //the enqueue order will be: 1st node’s left child, 2nd node’s right child, 1st node’s right child and 2nd node’s left child.
  void printSpecificLevelOrder(Node root) {
    if (root == null) {
      return;
    }
    System.out.print(root.data);
    // / Since it is perfect Binary Tree, right is not checked
    if (root.left != null) {
      System.out.print(root.left.data + " " + root.right.data);
    }
    if (root.left.left == null) {
      return;
    }
    // Create a queue and enqueue left and right children of root
    Queue<Node> q = null;
    q.add(root.left);
    q.add(root.right);
    // We process two nodes at a time, so we need two variables to store two front items of queue
    Node first = null, second = null;
    while (!q.isEmpty()) {
      // Pop two items from queue
      first = q.peek();
      q.poll();
      second = q.peek();
      q.poll();
      // Print children of first and second in reverse order
      System.out.print(first.left.data + " " + second.right.data);
      System.out.print(first.right.data + " " + second.left.data);
      // If first and second have grandchildren, enqueue them in reverse order
      if (first.left.left != null) {
        q.add(first.left);
        q.add(second.right);
        q.add(first.right);
        q.add(second.left);
      }
    }
  }

  //Find sum of all left leaves in a given Binary Tree. O(n)
  int leftLeavesSum(Node root) {
    int res = 0;
    // Update result if root is not NULL
    if (root != null) {
      // If left of root is NULL, then add key of left child
      if (isLeaf(root.left)) {
        res += root.left.data;
      } else // Else recur for left child of root
      {
        res += leftLeavesSum(root.left);
      }
      // Recur for right child of root and update res
      res += leftLeavesSum(root.right);
    }
    return res;
  }

  Boolean isLeaf(Node node) {
    if (node == null) {
      return false;
    }
    if (node.left == null && node.right == null) {
      return true;
    }
    return false;
  }

  //Convert Binary tree to Doubly Link list (DLL)
  //Pass head and prev as NULL
  void BTtoDLLmorris(Node root, Node head, Node prev) {
    if (root == null) {
      return;
    }
    Node curr = root;
    while (curr != null) {
      if (curr.left == null) {
        if (head == null) {
          head = curr;
          prev = curr;
        } else {
          prev.right = curr;
          curr.left = prev;
          prev = curr;
        }
        curr = curr.right;
      } else {
        Node pre = curr.left;
        while (pre.right != null && pre.right != curr) {
          pre = pre.right;
        }
        if (pre.right == null) {
          pre.right = curr;
          curr = curr.left;
        } else {
          if (head == null) {
            head = curr;
            prev = curr;
          } else {
            prev.right = curr;
            curr.left = prev;
            prev = curr;
          }
          pre.right = null;
          curr = curr.right;
        }
      }
    }
  }

  //convert a binary tree to a circular doubly-linked list
  // use above tree to DLL using morris traversal method
  Node prev = null;
  Node head = null;

  void treeToDoublyList(Node root, Node prev, Node head) {
    if (root == null) {
      return;
    }
    treeToDoublyList(root.left, prev, head);
    // current node's left points to previous node
    root.left = prev;
    if (prev != null) {
      prev.right = root; // previous node's right points to current node
    } else {
      head = root;        // if previous is NULL that current node is head
    }
    Node right = root.right; //Saving right node
    //Now we need to make list created till now as circular
    head.left = root;
    root.right = head;
    //For right-subtree/parent, current node is in-order predecessor
    prev = root;
    treeToDoublyList(right, prev, head);
  }

  //Serialize/Deserialize the binary tree
  // ‘is used to indicate an internal node set bit, and ‘/’ is used as NULL marker for node which has one child
  static String Serialize(Node root) {
    if (root == null) {
      return "";
    }
    Stack<Node> nodeStack = new Stack<>();
    nodeStack.push(root);
    StringBuffer sb = new StringBuffer();
    while (nodeStack.size() > 0) {
      // Pop the top item from stack and print it
      Node node = nodeStack.peek();
      nodeStack.pop();
      if (node != null) {
        if (node.left != null && node.right != null) {
          sb.append(node.data + "'");
          nodeStack.push(node.right);
          nodeStack.push(node.left);
        } else if (node.left == null && node.right == null) {
          sb.append(node.data);
        } else {
          sb.append(node.data + "'");
          nodeStack.push(node.right);
          nodeStack.push(node.left);
        }
      } else {
        sb.append("/");
      }
    }
    return sb.toString();
  }

  static int currentIndex = 0;

  static Node Deserialize(String str) {
    Node root = null;
    if (currentIndex > str.length()) {
      return null;
    } else if (str.charAt(currentIndex) == '/') {
      return null;
    } else if (str.charAt(currentIndex + 1) == '\'') {
      root = new Node(str.charAt(currentIndex));
      currentIndex += 2;
      root.left = Deserialize(str);
      root.right = Deserialize(str);
    } else {
      root = new Node(str.charAt(currentIndex));
      root.left = null;
      root.right = null;
      currentIndex++;
      return root;
    }
    return root;
  }

  // Serialize and Deserialize BST
  private String splitter = ",";

  // Encodes a tree to a single string.
  public String serialize(Node root) {
    StringBuilder sb = new StringBuilder();
    buildString(root, sb);
    return sb.toString();
  }

  private void buildString(Node root, StringBuilder sb) {
    if (root == null) {
      return;
    }
    sb.append(root.data).append(splitter);
    buildString(root.left, sb);
    buildString(root.right, sb);
  }

  // Decodes your encoded data to tree.
  public Node deserialize(String data) {
    if (data.length() == 0) {
      return null;
    }
    int[] pos = new int[1];
    pos[0] = 0;
    return buildTree(data.split(splitter), pos, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private Node buildTree(String[] nodes, int[] pos, int min, int max) {
    if (pos[0] == nodes.length) {
      return null;
    }

    int val = Integer.valueOf(nodes[pos[0]]);
    if (val < min || val > max) {
      return null; // Go back if we are over the boundary
    }
    Node cur = new Node(val);

    pos[0]++; // update current position
    cur.left = buildTree(nodes, pos, min, val);
    cur.right = buildTree(nodes, pos, val, max);
    return cur;
  }


  /**
   * Given a sorted (increasing order) array with unique integer elements, write an algorithm to
   * create a binary search tree with minimal height.
   */
  private Node createBST(int[] a, int start, int end) {
    if (start > end) {
      return null;
    }
    int mid = start + (end - start) / 2;
    Node n = new Node(a[mid]);
    n.left = createBST(a, start, mid - 1);
    n.right = createBST(a, mid + 1, end);
    return n;
  }

  //Maximum Height (Depth) of a Binary Tree
  public int maxDepthIter(Node root) {
    if (root == null) {
      return 0;
    }
    Deque<Node> queue = new LinkedList<>();
    queue.offer(root);
    int count = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size-- > 0) {
        Node node = queue.poll();
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }
      count++;
    }
    return count;
  }

  //Deepest left leaf node in a binary tree
  // BFS traverse tree right to left
  public Node deepestLeftLeaf(Node root) {
    Queue<Node> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      root = queue.poll();
      if (root.right != null)
        queue.add(root.right);
      if (root.left != null)
        queue.add(root.left);
    }
    return root;
  }

  //Deepest node in binary tree
  private Node deepestNode(Node root) {
    if (root == null) {
      return null;
    }
    Queue<Node> _queue = null;
    Node tmp = null;
    _queue.add(root);
    while (_queue.size() > 0) {
      tmp = _queue.remove();
      if (tmp.left != null) {
        _queue.add(tmp.left);
      }
      if (tmp.right != null) {
        _queue.add(tmp.right);
      }
    }
    return tmp;
  }

  int deepestlevel = 0;
  int value;

  public void find(Node root, int level) {
    if (root != null) {
      find(root.left, level + 1);
      if (level > deepestlevel) {
        value = root.data;
        deepestlevel = level;
      }
      find(root.right, level + 1);
    }
  }

  //Given a binary tree, design an algorithm which creates a linked list of all the nodes at each depth
  //(e.g., if you have a tree with depth D, you'll have D linked lists). Time = O(N)
  public static ArrayList<java.util.LinkedList<Node>> createLevelLinkedList(Node root) {
    ArrayList<java.util.LinkedList<Node>> result = new ArrayList<java.util.LinkedList<Node>>();
    java.util.LinkedList<Node> current = new java.util.LinkedList<Node>();
    if (root != null) {
      current.add(root);
    }
    while (!current.isEmpty()) {
      result.add(current);
      java.util.LinkedList<Node> parents = current;
      current = new java.util.LinkedList<Node>();
      for (Node parent : parents) {
        if (parent.left != null) {
          current.add(parent.left);
        }
        if (parent.right != null) {
          current.add(parent.right);
        }
      }
    }
    return result;
  }

  //You have two very large binary trees: Tl, with millions of nodes, and T2, with hundreds of nodes. Create an
  //algorithm to decide ifT2 is a subtree ofTl.A tree T2 is a subtree of Tl if there exists a node n in Tl such that
  //the subtree of n is identical to T2. That is, if you cut off the tree at node n, the two trees would be identical.
  //Approach 1 : Time O(n + km) where k is the number of occurrences of T2's root in Tl memory = O(logn + logm)
  boolean containsTree(Node tl, Node t2) {
    if (t2 == null) { // The empty tree is always a subtree
      return true;
    }
    return subTree(tl, t2);
  }

  boolean subTree(Node rl, Node r2) {
    if (rl == null) {
      return false; // big tree empty & subtree still not found.
    }
    if (rl.data == r2.data) {
      if (matchTree(rl, r2)) {
        return true;
      }
    }
    return (subTree(rl.left, r2) || subTree(rl.right, r2));
  }

  boolean matchTree(Node rl, Node r2) {
    if (r2 == null && rl == null) // if both are empty
    {
      return true; // nothing left in the subtree
    }
    // if one, but not both, are empty
    if (rl == null || r2 == null) {
      return false;
    }
    if (rl.data != r2.data) {
      return false; // data doesn't match
    }
    return (matchTree(rl.left, r2.left) && matchTree(rl.right, r2.right));
  }
  //Approach 2: Compare whether T2's leaf-delimited traversal string (pre-order,in-order, etc) is a substring of T1's.
  //Fast but waste memory, not good for large trees.time and memory = O(n+m)

  //print all path from root to leaf in Binary tree in new line
  // time = O(n) and space = O(n)
  public static void RootToLeafPathPrint(Node root) {
    Stack<Object> stack = new Stack<>();
    if (root == null) {
      return;
    }
    stack.push(root.data + "");
    stack.push(root);
    while (!stack.isEmpty()) {
      Node temp = (Node) stack.pop();
      String path = (String) stack.pop();

      if (temp.right != null) {
        stack.push(path + temp.right.data);
        stack.push(temp.right);
      }
      if (temp.left != null) {
        stack.push(path + temp.left.data);
        stack.push(temp.left);
      }
      if (temp.left == null && temp.right == null) {
        System.out.println(path);
      }
    }
  }

  // print root to leaf.
  void printPaths(Node root) {
    if (root == null) {
      return;
    }
    Stack<Node> s = new Stack<>();
    s.push(root);
    Node temp = root.left;

    while (s.size() != 0) {
      while (temp != null) {
        s.push(temp);
        temp = temp.left;
      }
      Node top = s.peek();
      if (!top.isVisited) {
        top.isVisited = true;
        temp = top.right;
        if (temp == null) {
          printThePath(s);
          s.pop();
        }
      } else {
        s.pop();
      }
    }

  }

  void printThePath(Stack<Node> s) {
    // get an iterator and print the stack
  }

  // print root to leaf paths using StringBuilder
  public List<String> binaryTreePaths(Node root) {
    List<String> res = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    helper(res, root, sb);
    return res;
  }

  private void helper(List<String> res, Node root, StringBuilder sb) {
    if (root == null) {
      return;
    }
    int len = sb.length();
    sb.append(root.data);
    if (root.left == null && root.right == null) {
      res.add(sb.toString());
    } else {
      sb.append("->");
      helper(res, root.left, sb);
      helper(res, root.right, sb);
    }
    sb.setLength(len);
  }


  public static void printAllPossiblePath(Node node, List<Node> nodelist) {
    if (node != null) {
      nodelist.add(node);
      if (node.left != null) {
        printAllPossiblePath(node.left, nodelist);
      }
      if (node.right != null) {
        printAllPossiblePath(node.right, nodelist);
      } else if (node.left == null && node.right == null) {
        for (int i = 0; i < nodelist.size(); i++) {
          System.out.print(nodelist.get(i).data);
        }
        System.out.println();
      }
      nodelist.remove(node);
    }
  }

  // Print longest path from root to leaf in Binary tree
  // Do BFS and maintain map to add parent node for all traverse node.
  private void printLongestPath(Node root) {
    Queue<Node> _queue = null;
    HashMap<Node, Node> map = new HashMap<>();
    map.put(root, null);
    Node tmp = null;
    _queue.add(root);
    while (_queue.size() > 0) {
      tmp = _queue.remove();
      if (tmp.left != null) {
        map.put(tmp.left, tmp);
        _queue.add(tmp.left);
      }
      if (tmp.right != null) {
        _queue.add(tmp.right);
        map.put(tmp.right, tmp);
      }
    }
    //printTopToBottomPath(tmp, map);
  }

  //Imagine you are reading in a stream of integers. Periodically, you wish to be able to look up the rank of a number x
// (the number of values less than or equal to x). Implement the data structures and algorithms to support these
// operations. That is, implement the method track(int x), which is called when each number is generated, and the
// method getRankOf'Number (int x), which returns the number of values less than or equal to x (not including x itself).
  class RankNode {

    public int left_size = 0;
    public RankNode left;
    public RankNode right;
    public int data = 0;

    public RankNode(int d) {
      data = d;
    }

    public void insert(int d) {
      if (d <= data) {
        if (left != null) {
          left.insert(d);
        } else {
          left = new RankNode(d);
        }
        left_size++;
      } else {
        if (right != null) {
          right.insert(d);
        } else {
          right = new RankNode(d);
        }
      }
    }

    public int getRank(int d) {
      if (d == data) {
        return left_size;
      } else if (d < data) {
        if (left == null) {
          return -1;
        } else {
          return left.getRank(d);
        }
      } else {
        int right_rank = right == null ? -1 : right.getRank(d);
        if (right_rank == -1) {
          return -1;
        } else {
          return left_size + 1 + right_rank;
        }
      }
    }
  }

  // check if binary tree is unival
  public static boolean isTreeUnivalRoot(Node root) {
    if (root == null) {
      return true;
    }
    return isTreeUnival(root.left, root.data) && isTreeUnival(root.right, root.data);
  }

  public static boolean isTreeUnival(Node n, int val) {
    if (n == null) {
      return true;
    }
    if (n.data != val) {
      return false;
    }
    return isTreeUnival(n.left, val) && isTreeUnival(n.right, val);
  }

  //count unival subtrees
  int count = 0;

  boolean countSingleRec(Node node) {
    // Return false to indicate NULL
    if (node == null) {
      return true;
    }
    // Recursively count in left and right subtrees also
    boolean left = countSingleRec(node.left);
    boolean right = countSingleRec(node.right);

    // If any of the subtrees is not singly, then this cannot be singly.
    if (left == false || right == false) {
      return false;
    }

    // If left subtree is singly and non-empty, but data doesn't match
    if (node.left != null && node.data != node.left.data) {
      return false;
    }

    // Same for right subtree
    if (node.right != null && node.data != node.right.data) {
      return false;
    }

    // If none of the above conditions is true, then tree rooted under root is single valued, increment
    // count and return true.
    count++;
    return true;
  }

  //given a binary tree, write a function that returns the number of nodes beneath a specified level
  public static int findNodesCountBelowLevel(Node root, int curr, int level) {
    if (root == null) {
      return 0;
    }
    if (curr > level) {
      countN++;
    }
    findNodesCountBelowLevel(root.left, curr + 1, level);
    findNodesCountBelowLevel(root.right, curr + 1, level);
    return countN;
  }

  // given a tree find the level that has maximum nodes
  private static int findLevelWithMaxNodes(N_Tree.NTree root) {
    if (root == null) {
      return 0;
    }
    Queue<N_Tree.NTree> q = new LinkedList<>();
    q.add(root);
    int max_Nodes = 1;
    int level = 0;
    int max_level = 0;
    while (true) {
      int nodeCount = q.size();
      if (nodeCount > max_Nodes) {
        max_Nodes = nodeCount;
        max_level = level;
      }
      if (nodeCount == 0) {
        break;
      }
      ;
      while (nodeCount > 0) {
        N_Tree.NTree _node = q.poll();
        for (N_Tree.NTree t : _node.children) {
          q.add(t);
        }
        nodeCount--;
      }
      level++;
    }
    return max_level;
  }
  //given a binary tree print boundry nodes in anti-clock wise

  //convert ternary expression to binary tree i.e. a?b:c , a?b?c:d:e
  public static Node convertTtoBT(char[] values) {
    Node n = new Node(values[0]);
    for (int i = 1; i < values.length; i += 2) {
      if (values[i] == '?') {
        n.left = new Node(values[i + 1]);
        n.left.parent = n;
        n = n.left;
      } else if (values[i] == ':') {
        n = n.parent;
        while (n.right != null && n.parent != null) {
          n = n.parent;
        }
        n.right = new Node(values[i + 1]);
        n.right.parent = n;
        n = n.right;
      }
    }
    return n;
  }

  //With stack
  public static Node convert(char[] expr) {
    if (expr.length == 0) {
      return null;
    }
    Node root = new Node(expr[0]);
    Stack<Node> stack = new Stack<>();
    stack.push(root);
    for (int i = 1; i < expr.length; i += 2) {
      Node node = new Node(expr[i + 1]);
      if (expr[i] == '?') {
        stack.peek().left = node;
      } else if (expr[i] == ':') {
        stack.pop();
        while (stack.peek().right != null) {
          stack.pop();
        }
        stack.peek().right = node;
      }
      stack.push(node);
    }
    stack.clear();
    return root;
  }

  // Convert binary tree to ternary expression
  static String convertBack(Node root) {
    Stack<Node> stack = new Stack<>();
    StringBuilder sb = new StringBuilder();
    stack.push(root);
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      if (node.left != null && node.right != null) {
        stack.push(node.right);
        stack.push(node.left);
        sb.append(node.data + "?");
      } else if (node.left == null && node.right == null) {
        sb.append(node.data + ":");
      }
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  //Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
  public boolean hasPathSum(Node root, int sum) {
    if (root == null) {
      return false;
    }
    if (root.data == sum && (root.left == null && root.right == null)) {
      return true;
    }
    return hasPathSum(root.left, sum - root.data) || hasPathSum(root.right, sum - root.data);
  }

  //print all paths that add to sum
  public static void printAllPathSum(Node root, int sum, String path) {
    if (root != null) {
      if (root.data > sum) { // if root is greater than Sum required, return
        return;
      } else {
        path += " " + root.data; //add root to path
        if (root.data == sum && (root.left == null && root.right == null)) {
          System.out.println(path);
        }
        printAllPathSum(root.left, sum - root.data, path);
        printAllPathSum(root.right, sum - root.data, path);
      }
    }
  }

  /*Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
    Calling next() will return the next smallest number in the BST. Note: next() and hasNext() should run in
    average O(1) time and uses O(h) memory, where h is the height of the tree.
    For next() to be O(1) space and time O(n) use morris traversal */
  class BSTIterator {

    private Stack<Node> stack = new Stack<>();

    public BSTIterator(Node root) {
      pushAll(root);
    }

    /**
     * @return whether we have a next smallest number
     */
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    /**
     * @return the next smallest number
     */
    public int next() {
      Node tmpNode = stack.pop();
      pushAll(tmpNode.right);
      return tmpNode.data;
    }

    private void pushAll(Node node) {
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
    }
  }

  // using morris traversal in O(1) next() and hasNext()
  class BSTIteratorUsingMorris {

    private Node root, cur;

    public BSTIteratorUsingMorris(Node root) {
      root = root;
      cur = root;
    }

    /**
     * @return whether we have a next smallest number
     */
    public boolean hasNext() {
      return cur != null;
    }

    /**
     * @return the next smallest number
     */
    public int next() {
      int r = 0;
      while (cur != null) {
        if (cur.left == null) {
          r = cur.data;
          cur = cur.right;
          // Got the node, break loop
          break;
        } else {
          Node node = cur.left;
          while (node.right != null && node.right != cur) {
            node = node.right;
          }
          if (node.right == null) {
            node.right = cur;
            cur = cur.left;
          } else {
            node.right = null;
            r = cur.data;
            cur = cur.right;
            // Got the node, break loop
            break;
          }
        }
      }
      return r;
    }
  }

  /* upside down tree: Given a binary tree where all the right nodes are either leaf nodes with a sibling
  (a left node that shares the same parent node) or empty, flip it upside down and turn it into a
  tree where the original right nodes turned into left leaf nodes. Return the new root.
      For example:
      Given a binary tree {1,2,3,4,5},
      1
      / \
      2 3
      / \
      4 5

      return the root of the binary tree [4,5,2,#,#,3,1].
      4
      / \
      5 2
        / \
       3 1
   */
  public Node upsideDownBinaryTree(Node root) {
    Node curr = root;
    Node next = null;
    Node temp = null;
    Node prev = null;
    while (curr != null) {
      next = curr.left;
      // swapping nodes now, need temp to keep the previous right child
      curr.left = temp;
      temp = curr.right;
      curr.right = prev;

      prev = curr;
      curr = next;
    }
    return prev;
  }

  //largest perfect tree size in given tree
  static int maxSize = 1;

  public static int getLargestSizeOfPerfactTree(Node root) {
    if (root.left == null && root.right == null) {
      return 1;
    }
    if (root.left == null || root.right == null) {
      return 0;
    }
    int left = getLargestSizeOfPerfactTree(root.left);
    int right = getLargestSizeOfPerfactTree(root.right);

    if (left < 1 || right < 1) {
      return 1;
    }
    if (left == right) {
      maxSize = left + right + 1;
    } else {
      maxSize = Math.max(left, right);
    }
    return maxSize;
  }

  //Given a binary tree, find the number of subtrees having odd count of even numbers.
  int countSubtrees(Node root) {
    if (root == null) {
      return 0;
    }
    // count even numbers in left subtree
    int c = countSubtrees(root.left);
    // Add count of even numbers in right subtree
    c += countSubtrees(root.right);
    // check if root data is an even number
    if (root.data % 2 == 0) {
      c += 1;
    }
    // If total count of even numbers for the subtree is odd
    if (c % 2 != 0) {
      count++;
    }
    // return total count of even numbers of the subtree
    return c;
  }

  //Find if Two binary trees are considered leaf-similar if their leaf value sequence is the same.
  static boolean leafSimilar(Node root1, Node root2) {
    Stack<Node> s1 = new Stack<>(), s2 = new Stack<>();
    s1.push(root1);
    s2.push(root2);
    while (!s1.empty() && !s2.empty()) {
      if (dfs(s1) != dfs(s2)) {
        return false;
      }
    }
    return s1.empty() && s2.empty();
  }

  static int dfs(Stack<Node> s) {
    while (true) {
      Node node = s.pop();
      if (node.right != null) {
        s.push(node.right);
      }
      if (node.left != null) {
        s.push(node.left);
      }
      if (node.left == null && node.right == null) {
        return node.data;
      }
    }
  }

  //We are given the head node root of a binary tree, where additionally every node's value is either a 0 or a 1.
  //Return the same tree where every subtree (of the given tree) not containing a 1 has been removed.
  public static Node pruneTree(Node root) {
    if (root == null) {
      return null;
    }
    root.left = pruneTree(root.left);
    root.right = pruneTree(root.right);
    if (root.left == null && root.right == null && root.data == 0) {
      return null;
    }
    return root;
  }

  /* Given a list of child->parent relationships, build a binary tree out of it. All the element Ids inside the tree are unique.
    Given the following relationships:

    Child Parent IsLeft
    15 20 true
    19 80 true
    17 20 false
    16 80 false
    80 50 false
    50 null false
    20 50 true

    You should return the following tree:

        50
       /  \
      20   80
     / \   / \
    15 17 19 16
  */
  class Relation {

    Integer parent;
    Integer child;
    boolean isLeft;
  }

  static Node buildTree(List<Relation> data) {
    HashMap<Integer, Node> map = new HashMap<>();
    Node root = null;

    for (Relation r : data) {
      Node child = map.get(r.child);
      if (child == null) {
        child = new Node();
        child.data = r.child;
        map.put(r.child, child);
      }
      if (r.parent == null) {
        root = child;
        continue;
      }
      Node parent = map.get(r.parent);
      if (parent == null) {
        parent = new Node();
        parent.data = r.parent;
        map.put(r.parent, parent);
      }

      if (r.isLeft) {
        parent.left = child;
      } else {
        parent.right = child;
      }
    }
    return root;
  }

  /*Graph Valid Tree
   Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
   write a function to check whether these edges make up a valid tree.
   Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
   Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
   */
  public boolean validTree(int n, int[][] edges) {
    // initialize n isolated islands
    int[] nums = new int[n];
    Arrays.fill(nums, -1);
    // perform union find
    for (int i = 0; i < edges.length; i++) {
      int x = find(nums, edges[i][0]);
      int y = find(nums, edges[i][1]);
      // if two vertices happen to be in the same set then there's a cycle
      if (x == y) {
        return false;
      }
      // union
      nums[x] = y;
    }
    return edges.length == n - 1;
  }

  int find(int nums[], int i) {
    if (nums[i] == -1) {
      return i;
    }
    return find(nums, nums[i]);
  }

}
