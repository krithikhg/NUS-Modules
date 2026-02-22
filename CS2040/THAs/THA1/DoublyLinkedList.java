import java.util.*;

class DoublyLinkedList implements ListInterface {
  // attributes
  public DListNode head;
  public DListNode tail;
  public int num_nodes;

  // interface methods

  // Return true if list is empty; otherwise return false.
  public boolean isEmpty() { return (num_nodes == 0); }

  // Return number of items in list
  public int size() { return num_nodes; }

  // return index of item if item is found in the list, otherwise return -1
  public int indexOf(String item) {
    int index = 0;

    for (DListNode cur = head; cur != null; cur = cur.getNext()) {
      if (cur.getElement().equals(item)) 
        return index;
      else 
        index++;
    }
    return -1;
  }

  // return true if item is in the list false otherwise
  public boolean contains(String item) {
    if (indexOf(item) != -1)
      return true;
    return false;
  }

  // get item at index
  public String getItemAtIndex(int index) {
    int counter = 0;
    String item = "";

    if (index < 0 || index > size()-1) {
      System.out.println("invalid index");
      System.exit(1);
    }
    if (index == size()-1)
      item = tail.getElement();
    else {
      for (DListNode cur = head; cur != null; cur = cur.getNext(), counter++) {
        if (counter == index) {
          item = cur.getElement();
          break;
        }
      }
    }
    return item;
  }

  // Return first item
  public String getFirst() { return getItemAtIndex(0); }

  // Return last item
  public String getLast() { return getItemAtIndex(size()-1); }

  // add item at position index, shifting all current items from index onwards to the right by 1 
  // pre: 0 <= index <= size()
  public void  addAtIndex(int index, String item) {
    DListNode cur;
    DListNode newNode = new DListNode(item);

    if (index >= 0 && index <= size()) {
      if (index == 0) // insert in front
        insert(null,newNode);
      else if (index == size()) // insert at the back, don't have to move all the way to the back
        insert(tail,newNode);
      else {
        cur = getNodeAtIndex(index-1); // access node at index-1
        insert(cur,newNode);
      }
    }
    else { // index out of bounds
      System.out.println("invalid index");
      System.exit(1);
    }
  } 

  // Add item to front of list
  public void addFront(String item) { addAtIndex(0,item); }

  // Add item to back of list
  public void addBack(String item) { addAtIndex(size(),item); }

  // remove item at index and return it
  // pre: 0 <= index < size()
  public String removeAtIndex(int index) {
    DListNode cur;
    String item = "";

    // index within bounds and list is not empty
    if (index >= 0 && index < size()) {
      if (index == 0) // remove 1st item
        item = remove(null);
      else {
        cur = getNodeAtIndex(index-1); // access node at index-1
        item = remove(cur);
      }
    }
    else { // index out of bounds
      System.out.println("invalid index or list is empty");
      System.exit(1);
    }
    return item;
  }

  // Remove first node of list
  public String removeFront() { return removeAtIndex(0); }

  // Remove last node of list
  public String removeBack() { return removeAtIndex(size()-1); }

  // Print values of nodes in list.
  public void print() {
    if (head == null)
      System.out.println("Nothing to print...");
    else {
      DListNode cur = head;
      System.out.print("List is: " + cur.getElement());
      for (int i=1; i < num_nodes; i++) {
       cur = cur.getNext();
       System.out.print(", " + cur.getElement());
      }
      System.out.println(".");
    }
  }

  // non-interface helper methods
  public DListNode getHead() { return head; }
  public DListNode getTail() { return tail; }

  /* return the ListNode at index */
  public DListNode getNodeAtIndex(int index) {
    int counter = 0;
    DListNode node = null;

    if (index < 0 || index > size()-1) {
      System.out.println("invalid index");
      System.exit(1);
    }
    if (index == size()-1) // return tail if index == size()-1
      return tail;
    for (DListNode cur = head; cur != null; cur = cur.getNext()) {
      if (counter == index) {
        node = cur;
        break;
      }
      counter++;
    }
    return node;
  }

  // insert newNode after the node referenced by cur 
  public void insert(DListNode cur, DListNode n) {
    // insert in front
    if (cur == null) {
      n.setNext(head);
      head = n; // update head
      if (tail == null) // update tail if list originally empty
        tail = head;
    }
    else { // insert anywhere else
      n.setNext(cur.getNext());
      if(cur!=tail) cur.next.setPrev(n);
      cur.setNext(n);
      n.setPrev(cur);
      if (cur == tail) // update tail if inserted new last item
        tail = tail.getNext();
    }
    num_nodes++;
  }

  // remove the node referenced by cur.next, and return the item in the node 
  // if cur == null, remove the first node 
  public String remove(DListNode cur) {
    String value;

    if (cur == null) { // remove 1st node
      value = head.getElement();
      head = head.getNext(); // update head
      if (num_nodes == 1) // update tail to null if only item in list is removed
        tail = null;
    }
    else { // remove any other node
      value = cur.getNext().getElement();
      cur.setNext(cur.getNext().getNext());
      if (cur.getNext() == null) // update tail if last item is removed
        tail = cur;
    }
    num_nodes--;

    return value;
  }
}
