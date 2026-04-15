import java.util.*;
import java.util.NoSuchElementException; // we will use this to illustrate Java Error Handling mechanism

// Every vertex in this BST is a Java Class
class BSTVertex {
  BSTVertex(int p, int v) {pos = p; vol = v; minPos= p; maxPos = p; maxVol = v; parent = left = right = null; height = 0; }
  // all these attributes remain public to slightly simplify the code
  public BSTVertex parent, left, right;
  public int pos;
  public int vol; //augmented with volume
  public int height; // will be used in AVL lecture
  public int maxVol; //augmented with maxvol
  public int minPos; //augmented with min and max pos
  public int maxPos;
}

// This is just a sample implementation
// There are other ways to implement BST concepts...
class BST {
  protected BSTVertex root;

  protected BSTVertex search(BSTVertex T, int v) {
         if (T == null)  return T;                                  // not found
    else if (T.pos == v) return T;                                      // found
    else if (T.pos < v)  return search(T.right, v);       // search to the right
    else                 return search(T.left, v);         // search to the left
  }

  protected BSTVertex insert(BSTVertex T, int p, int v) {
    if (T == null) return new BSTVertex(p, v);          // insertion point is found

    if (T.pos < p) {                                      // search to the right
      T.right = insert(T.right, p, v);
      T.right.parent = T;
    }
    else {                                                 // search to the left
      T.left = insert(T.left, p, v);
      T.left.parent = T;
    }

    return T;                                          // return the updated BST
  }

  protected int[] findMin(BSTVertex T) {
         if (T == null)      throw new NoSuchElementException("BST is empty, no minimum");
    else if (T.left == null) return new int[]{T.pos, T.vol};                    // this is the min
    else                     return findMin(T.left);           // go to the left
  }

  protected int findMax(BSTVertex T) {
         if (T == null)       throw new NoSuchElementException("BST is empty, no maximum");
    else if (T.right == null) return T.pos;                   // this is the max
    else                      return findMax(T.right);        // go to the right
  }

  protected int[] successor(BSTVertex T) {
    if (T.right != null)                       // this subtree has right subtree
      return findMin(T.right);  // the successor is the minimum of right subtree
    else {
      BSTVertex par = T.parent;
      BSTVertex cur = T;
      // if par(ent) is not root and cur(rent) is its right children
      while ((par != null) && (cur == par.right)) {
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par == null ? new int[]{-1,-1} : new int[]{par.pos,par.vol};           // this is the successor of T
    }
  }

  protected int predecessor(BSTVertex T) {
    if (T.left != null)                         // this subtree has left subtree
      return findMax(T.left);  // the predecessor is the maximum of left subtree
    else {
      BSTVertex par = T.parent;
      BSTVertex cur = T;
      // if par(ent) is not root and cur(rent) is its left children
      while ((par != null) && (cur == par.left)) { 
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par == null ? -1 : par.pos;           // this is the successor of T
    }
  }

  protected BSTVertex delete(BSTVertex T, int p) {
    if (T == null)  return T;              // cannot find the item to be deleted

    if (T.pos == p) {                          // this is the node to be deleted
      if (T.left == null && T.right == null)                   // this is a leaf
        T = null;                                      // simply erase this node
      else if (T.left == null && T.right != null) {   // only one child at right
        T.right.parent = T.parent;             // ma, take care of my only child
        T = T.right;                                                 // bypass T
      }
      else if (T.left != null && T.right == null) {    // only one child at left
        T.left.parent = T.parent;              // ma, take care of my only child
        T = T.left;                                                  // bypass T
      }
      else {                // has two children, find successor to avoid quarrel
        int[] successorV = successor(T);             // predecessor is also OK btw
        T.pos = successorV[0];         // replace this pos with the successor's pos
        T.vol = successorV[1];
        T.right = delete(T.right, successorV[0]);      // delete the old successorV
      }
    }
    else if (T.pos < p)                                   // search to the right
      T.right = delete(T.right, p);
    else                                                   // search to the left
      T.left = delete(T.left, p);
    return T;                                          // return the updated BST
  }

  public BST() { root = null; }

  public int search(int p) {
    BSTVertex res = search(root, p);
    return res == null ? -1 : res.pos;
  }

  public void insert(int p, int v) { root = insert(root, p, v); }

  // public int findMin() { return findMin(root); }

  public int findMax() { return findMax(root); }

  // public int successor(int p) { 
  //   BSTVertex vPos = search(root, p);
  //   return vPos == null ? -1 : successor(vPos);
  // }

  public int predecessor(int p) { 
    BSTVertex vPos = search(root, p);
    return vPos == null ? -1 : predecessor(vPos);
  }

  public void delete(int p) { root = delete(root, p); }

  // will be used in AVL lecture
  protected int getHeight(BSTVertex T) {
    if (T == null) return -1;
    else return Math.max(getHeight(T.left), getHeight(T.right)) + 1;
  }

  public int getHeight() { return getHeight(root); }
}

class AVL extends BST { // an example of Java inheritance
  public AVL() { root = null; }

  private int h(BSTVertex T) { return T == null ? -1 : T.height; }

  protected BSTVertex rotateLeft(BSTVertex T) {
    // T must have a right child

    BSTVertex w = T.right;
    w.parent = T.parent;
    T.parent = w;
    T.right = w.left;
    if (w.left != null) w.left.parent = T;
    w.left = T;

    T.height = Math.max(h(T.left), h(T.right)) + 1;
    w.height = Math.max(h(w.left), h(w.right)) + 1;

    update(T);
    update(w);

    return w;
  }

  protected BSTVertex rotateRight(BSTVertex T) {
    // T must have a left child

    BSTVertex w = T.left;
    w.parent = T.parent;
    T.parent = w;
    T.left = w.right;
    if (w.right != null) w.right.parent = T;
    w.right = T;

    T.height = Math.max(h(T.left), h(T.right)) + 1;
    w.height = Math.max(h(w.left), h(w.right)) + 1;

    update(T);
    update(w);

    return w;
  }

  protected BSTVertex rebalance(BSTVertex T) {
    int balance = h(T.left) - h(T.right);
    if (balance == 2) { // left heavy
      int balance2 = h(T.left.left) - h(T.left.right);
      if (balance2 >= 0) {
        T = rotateRight(T);
      }
      else { // -1
        T.left = rotateLeft(T.left);
        T = rotateRight(T);
      }
    }
    else if (balance == -2) { // right heavy
      int balance2 = h(T.right.left) - h(T.right.right);
      if (balance2 <= 0)
        T = rotateLeft(T);
      else { // 1
        T.right = rotateRight(T.right);
        T = rotateLeft(T);
      }
    }

    T.height = Math.max(h(T.left), h(T.right)) + 1;
    update(T);
    return T;
  }

  protected BSTVertex insert(BSTVertex T, int p, int v) {
    if (T == null) return new BSTVertex(p, v);          // insertion point is found

    if (T.pos < p) {                                      // search to the right
      T.right = insert(T.right, p, v);
      T.right.parent = T;
    }
    else {                                                 // search to the left
      T.left = insert(T.left,p, v);
      T.left.parent = T;
    }

    T = rebalance(T);
    return T;                                          // return the updated AVL
  }

  void update(BSTVertex T){
    if(T == null) return;

    T.maxVol = T.vol;
    T.minPos = T.pos;
    T.maxPos = T.pos;

    if(T.left != null){
      T.maxVol = Math.max(T.left.maxVol, T.maxVol);
      T.minPos = Math.min(T.left.minPos, T.minPos);
    }

    if(T.right != null){
      T.maxVol = Math.max(T.right.maxVol, T.maxVol);
      T.maxPos = Math.max(T.right.maxPos, T.maxPos);
    }
  }

  protected BSTVertex delete(BSTVertex T, int p) {
    if (T == null)  return T;              // cannot find the item to be deleted

    if (T.pos == p) {                          // this is the node to be deleted
      if (T.left == null && T.right == null)                   // this is a leaf
        T = null;                                      // simply erase this node
      else if (T.left == null && T.right != null) {   // only one child at right
        BSTVertex temp = T;
        T.right.parent = T.parent;
        T = T.right;                                                 // bypass T
        temp = null;
      }
      else if (T.left != null && T.right == null) {    // only one child at left
        BSTVertex temp = T;
        T.left.parent = T.parent;
        T = T.left;                                                  // bypass T
        temp = null;
      }
      else {                                 // has two children, find successor
        int[] successorV = successor(T);
        T.pos = successorV[0];         // replace this pos with the successor's pos
        T.vol = successorV[1];
        T.right = delete(T.right, successorV[0]);      // delete the old successorV
      }
    }
    else if (T.pos < p)                                   // search to the right
      T.right = delete(T.right, p);
    else                                                   // search to the left
      T.left = delete(T.left, p);

    if (T != null) {
      T = rebalance(T);
    }

    return T;                                          // return the updated BST
  }
}
