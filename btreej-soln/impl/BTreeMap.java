package impl;

import java.util.Iterator;
import java.util.Stack;

import adt.Map;

/**
 * BTreeMap
 * 
 * This implementation is based on the description in
 * CLRS, chapter 18.
 * 
 * @author Thomas VanDrunen
 * CSCI 445, Wheaton College
 * Oct 16, 2014
 * @param <K> The key-type of the map
 * @param <V> The value-type of the map
 */

public class BTreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    /**
     * All internal nodes (except the root in special
     * underflow cases) must have at least this many
     * children--and no more than twice this many children.
     * This is equivalent to t in CLRS.
     */
    private final int minDegree;

    
    
    /**
     * Simple class to encapsulate the result of a search
     * for a key: Was it found, and, if so, where is it?
     */
    private class Found {
        BNode location;  // which node is it in?
        int index;       // where is it in that node?
        boolean found;   // wait, was it found at all?
        public Found(BNode location, int index, boolean found) {
            this.location = location;
            this.index = index;
            this.found = found;
        }
    }

 
    
    // --------- Classes for nodes in the tree --------------
    
    /**
     * Things common between leaves and internals
     */
    abstract class BNode {
        /**
         * Array of keys, partially filled
         */
        K[] keys;

        /**
         * Array of vals, partially filled
         */
        V[] vals;

        /**
         * The number of pairs currently stored; 
         * this will also be one less than the number of
         * children in internal nodes.
         */
        int n;

        @SuppressWarnings("unchecked")
        BNode() {
            keys = (K[]) new Comparable[2 * minDegree - 1];
            vals = (V[]) new Object[2 * minDegree - 1];
            n = 0;
        }

        boolean isFull() { return n == keys.length;  }
        
        /**
         * In the given node, find the location (index) of the key
         * or where it would go (the index of smallest greater than
         * or equal to key)
         * @param key
         * @return The index in keys where the key is, if anywhere,
         * or otherwise the index in children (for leaves, the hypothetical
         * index that would be in children) indicating the subtree
         * where the key would be found. Note the range is [0, n]; note
         * especially that this could return n which is not a valid index
         * into keys.  
         */
        int binarySearchKeys(K key) {
            // special case for empty tree
            if (n == 0)
                return 0;
            
            // Invariants:
            // The given key comes after all the keys in [0, start)
            // and before all the keys in [stop, n].
            // mid = floor((stop + start) / 2).
            // comparison = the result of comparing the key at mid to 
            // the given key
            int start = 0,
                stop = n,
                mid = n / 2; 
            int comparison = keys[mid].compareTo(key);
        
            // when the range contains only one position,
            // start will equal mid
            while (comparison != 0 && start != mid) {
                if (comparison < 0) 
                    start = mid;
                else {
                    assert(comparison > 0);
                    stop = mid;
                }
                mid = (start + stop) / 2;
                assert keys[mid] != null;
                comparison = keys[mid].compareTo(key);
            }
            // if mid is less than the given key,
            // return one greater than mid, to comply with
            // "index of smallest greater than or equal to"
            if (comparison < 0)
                return mid + 1;
            else
                return mid;
        }

        /**
         * Splits this node into two nodes. Valid only if
         * this node is full.
         * @return The new sibling to the right.
         * PRECONDITION: This node is full
         */
        public BNode split() {
            assert isFull();
            // Make the actual node (depends on what
            // kind of node this is).
            BNode sibling = makeSibling();

            // begin solution, replace with: // ...add code here...
            // copy about half the associations
            // to the sibling. 
            // Invariant: the j largest-indexed key-val 
            // pairs have been copied to the first j
            // positions in the new sibling
            for (int j = 0; j < sibling.n; j++) {
                sibling.keys[j] = keys[j+minDegree];
                sibling.vals[j] = vals[j+minDegree];
            }
            // reduce this node's number of associations
            n = minDegree - 1;
            // end solution
            
            return sibling;
        }

        // --- method signatures--leaves and internals handle differently ---
        
        /**
         * Find the location (node and index) where a key is,
         * or where it would go.
         * @param key
         * @return
         */
        abstract Found search(K key);

        /**
         * Contains the part of split() that is specific to Leaves
         * and Internals
         * @return The new sibling to the right
         */
        abstract BNode makeSibling();

        /**
         * Insert the key and value into the subtree rooted here.
         * PRECONDITION: This node is not full.
         * @param key
         * @param val
         */
        abstract void insertNonFull(K key, V val);
        
        // the next two are for use in the iterator

        /**
         * Is the given index a valid index into the children array?
         */
        abstract boolean canDescend(int i);

        /**
         * Retrieve the child node at the given index, if it exists;
         * throw an exception otherwise. Don't call this unless
         * canDescend() returns true.
         */
        abstract BNode descend(int i);
        
    }
    

    /**
     * Leaves have no extra attributes, but have different implementations
     * for the operations.
     */
    class Leaf extends BNode {
        Leaf() { 
            super();
        }
        
        /**
         * Find the location (node and index) where a key is,
         * or where it would go. For leaves we binary search
         * for the key, and it's either there or it isn't.
         * @param key
         * @return
         */        
        Found search(K key) {
            // Special case that happens only in the root.
            if (n == 0) {
                assert  this == root;
                return new Found(this, 0, false);
            }
               
            int pos = binarySearchKeys(key);
        
            return new Found(this, pos, 
                        pos < n && keys[pos].compareTo(key) == 0);
       }


        /**
         * Do the part of split() that is specific to Leaves;
         * specifically, don't copy any children, since there are none.
         * @return The new sibling
         */
        BNode makeSibling() {
            Leaf sibling = new Leaf();
            sibling.n = minDegree - 1;
            return sibling;
        }


        /**
         * Insert the key and value into the subtree rooted here.
         * PRECONDITION: This node is not full.
         * Move everything over to make room  
         * @param key
         * @param val
         */
        void insertNonFull(K key, V val) {
            assert ! isFull();

            // begin solution, replace with: // ...add code here...
            int i = n-1;
            // Invariant: Position i + 1 is available
            // to be written to; keys in positions
            // [i+2, n] are greater than the key
            // we want to insert
            while (i >= 0 && key.compareTo(keys[i])<0) {
                keys[i+1] = keys[i];
                vals[i+1] = vals[i];
                i--;
           }
            keys[i+1] = key;
            vals[i+1] = val;
            n++;
            // end solution
        }

        /**
         * Is the given index a valid index into the children array?
         * Since this is a leaf (no children), the answer is always
         * no.
         */
        boolean canDescend(int i) {
            return false;
        }

        /**
         * Retrieve the child node at the given index, if it exists;
         * throw an exception otherwise. Don't call this unless
         * canDescend() returns true. Since this is a leaf,
         * this will always throw an exception.
         */
        BTreeMap<K, V>.BNode descend(int i) {
            throw new UnsupportedOperationException();
        }


    }

    /**
     * Internals differ from leaves in that they also have children.
     */
    private class Internal extends BNode {

        /**
         * Array for children, partially filled (one more
         * than keys and vals).
         */
        BNode[] children;

        // suppresswarnings needed because of arrays and generics
        // not playing nicely with each other
        @SuppressWarnings("unchecked") 
        Internal() {
            children = new BTreeMap.BNode[2 * minDegree];
        }

        
        /**
         * Find the location (node and index) where a key is,
         * or where it would go. For internals, if the key isn't
         * here we call search recursively on the child where
         * it might be.
         * @param key
         * @return
         */        
        Found search(K key) {
            int pos = binarySearchKeys(key);

            if (pos < n && keys[pos].compareTo(key) == 0)
                return new Found(this, pos, true);
            else
                return children[pos].search(key);
        }

        /**
         * Do the part of split() that is specific to internals;
         * specifically, copy the children.
         * @return The new sibling
         */        
        BTreeMap<K, V>.BNode makeSibling() {
            Internal sibling = new Internal();
            sibling.n = minDegree - 1;

            // Invariant: Positions [0, j) in the sibling contain
            // children [minDegree, minDigree+j) in this
            for (int j = 0; j < sibling.n + 1; j++) 
                sibling.children[j] = children[j+minDegree];
            return sibling;
        }

        /**
         * Insert the key and value into the subtree rooted here.
         * PRECONDITION: This node is not full.
         * Find the child where it would go. If that one is
         * full, split it. Either way, insert into that subtree
         * (if a split, the correct subtree might be either of
         * the nodes from the split---need to check the key
         * between them).
         * @param key
         * @param val
         */
        void insertNonFull(K key, V val) {
        	assert !isFull();

        	// begin solution, replace with: // ...add code here...
            int i = binarySearchKeys(key);
            if (children[i].isFull()) {
                splitChild(i);
                if (key.compareTo(keys[i])>0)
                    i++;
            }
            children[i].insertNonFull(key, val);
            // end solution
       
        }

        /**
         * Turn a child node into two new children.
         * Valid only in a non-full node but with a 
         * full child.
         * PRECONDITION: This node is not full but the
         * indicated child is full
         * POSTCONDITION: The node has one more child than
         * before.
         * @param pos The position of the child to split.
         */
        void splitChild(int pos) {
            assert ! isFull();
            assert children[pos].isFull();

            // begin solution, replace with: // ...add code here...
            BNode child = children[pos],    // old child
                    sibling = child.split();   // new child
            // Move the children over to make room
            for (int j = n; j >= pos+1; j--)
                children[j+1] = children[j];
            // insert new child
            children[pos+1] = sibling;
            // Move the corresponding keys and values
            for (int j = n-1; j >= pos; j--) {
                keys[j+1] = keys[j];
                vals[j+1] = vals[j];
            }
            // bring up the last pair in the original
            // child to differentiate it from the new sibling
            keys[pos] = child.keys[minDegree - 1];
            vals[pos] = child.vals[minDegree - 1];
            n++;
            // end solution
        }

        /**
         * Is the given index a valid index into the children array?
         * Check to see if it is in range.
         */
         boolean canDescend(int i) {
            return i >= 0 && i <= n;
        }

         /**
          * Retrieve the child node at the given index, if it exists;
          * throw an exception otherwise. Don't call this unless
          * canDescend() returns true. If the index is bad, 
          * an IndexOutOfBoundsException will be thrown.
          */
         BTreeMap<K, V>.BNode descend(int i) {
            if (! canDescend(i))
                throw new IndexOutOfBoundsException();
            else
                return children[i];
        }


    }

    
    // ---- The B-Tree class itself begins here ----
    

    /**
     * The root of the tree, which may violate the
     * B-tree properties if it is a leaf and not full.
     */
    BNode root;
    
    /**
     * To initialize a BTree, specify a minimum degree.
     * The root is initially an empty leaf.
     * @param minDegree
     */
    public BTreeMap(int minDegree) {
        this.minDegree = minDegree;
        root = new Leaf();
    }

    
    /**
     * Add an association to the map.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public void put(K key, V val) {
        // search for the key
        Found result = root.search(key);
        // if it's already there, update the value
        if (result.found)
            result.location.vals[result.index] = val;
        // otherwise, insert
        else {
            // Taking the speculatively-insert approach,
            // we split a full node before we enter it.
            // Handle a full root here.
            
            // This appears in CLRS pg 495 as "B-Tree_Insert"
            if (root.isFull()) {
                Internal newRoot = new Internal();
                newRoot.children[0] = root;
                root = newRoot;
                newRoot.splitChild(0);
            }
            root.insertNonFull(key, val);
        }
    }

    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
    public V get(K key) {
        Found result = root.search(key);
        if (result.found)
            return result.location.vals[result.index];
        else 
            return null;
    }

    
    /**
     * Test if this map contains an association for this key.
     * @param key The key to test.
     * @return true if there is an association for this key, false otherwise
     */
    public boolean containsKey(K key) {
        return root.search(key).found;
    }

    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    public void remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Simple class to act as a breadcrumb for our
     * descent as we traverse the B-tree.
     */
    private class IteratorRecord {
        BNode node;
        // we have visited the children up through (including)
        // pos, so pos is the next key to return
        int pos;
        IteratorRecord(BNode node) {
            this.node = node;
            pos = 0;
        }
    }

    /**
     * Iterate over the keys in this map.
     * @return An iterator for the keys in this map.
     */
    public Iterator<K> iterator() {
        // invariant:
        // The stack contains a trace of parent nodes that are not finished.
        // 
        // With this scheme, if a node is finished, we pop it before pushing its
        // last child.
        // An empty stack means we're done.
        final Stack<IteratorRecord> st = new Stack<IteratorRecord>();

        // begin solution, replace with: // ...add code here to initialize the stack...
        IteratorRecord top;
        st.push(top = new IteratorRecord(root));
        while (top.node.canDescend(0))
            st.push(top = new IteratorRecord(top.node.descend(0)));

        // special case for empty tree
        if (root.n == 0) {
            // sanity check
            assert root instanceof BTreeMap.Leaf && st.peek().node == root;
            st.pop();
            assert st.isEmpty();
        }
        // end solution
        
        return new Iterator<K>() {
            
            public boolean hasNext() {
                return !st.isEmpty();
            }

            public K next() {
                // begin solution, replace with: // ...add code here...
                IteratorRecord current = st.peek();
                K toReturn = current.node.keys[current.pos++];
                if (current.pos == current.node.n)
                    st.pop();
                if (current.node.canDescend(current.pos))
                    st.push(new IteratorRecord(current.node.descend(current.pos)));
                return toReturn;
                // end solution
                // begin solution, replace with: throw new UnsupportedOperationException();
                // end solution
            }

            
        };
    }

    protected static String[] data = { 
            "Minnesota", "Minneapolis",
            "Texas", "Dallas",
            "Oregon", "Seattle",
            "New Jersey", "Newark",
            "Pennsylvania", "Philadelphia",
            "Massachusetts", "Springfield",
            "Arizona", "Tuscon",
            "Michigan", "Ann Arbor",
            "Ohio", "Cincinatti",
            "New York", "Buffalo",
            "Florida", "Orlando",
            "Colorado", "Boulder",
            "Alabama", "Jackson",
            "Kentucky", "Louisville",
            "Kansas", "Wichita",
            "Alaska", "Vasilia" };

    
    public static void main(String[] args) {
        BTreeMap<String, String> map = new BTreeMap<String,String>(10);
        for (int i = 0; i < data.length; i++) {
            System.out.println("---");
            map.put(data[i], data[i]);
            for (int j = 0; j <= i; j++)
                System.out.println(data[j] + " " + map.containsKey(data[j]) + " "
                        + map.get(data[j]));
            
        }
            
    }
}
