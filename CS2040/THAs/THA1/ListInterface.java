// list interface for a list of integers
// Note: 1st item is at index 0, last item at index N-1 (where N is number of items in the list)
public interface ListInterface {
	public boolean isEmpty(); // Return true if list is empty; otherwise return false.
	public int     size(); // Return number of items in the list

  //  access items in the list
  public int     indexOf(String item); // return index of item if item is found in the list, otherwise return -1
	public boolean contains(String item); // return true if item is in the list false otherwise
	public String     getItemAtIndex(int index); // get item at index
	public String     getFirst(); // get first item
	public String     getLast(); //get last item
    
  // add items to the list
  public void    addAtIndex(int index, String item); // add item at position index, 
                                                  // shifting all current items from index onwards to the right by 1 
                                                  // add item to the back if index == size() 
	public void    addFront(String item); // add item to front of list
	public void    addBack(String item); // add item to back of list

	// remove items from the list
	public String     removeAtIndex(int index); // remove item at index and return it                              
	public String  removeFront(); // remove 1st item and return it
	public String     removeBack(); // remove last item and return it

	// print out the list from index 0 to index N-1
	public void    print();
}
