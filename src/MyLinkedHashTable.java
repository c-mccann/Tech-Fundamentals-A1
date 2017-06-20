import java.util.Arrays;

/**
 * Created by carlmccann2 on 16/10/2016.
 */
public class MyLinkedHashTable {
    private final int initialSize = 1024; // set as 2 caused error: java.lang.OutOfMemoryError: Java heap space
    private MyLinkedList[] nodes = new MyLinkedList[initialSize];
    // this list of primes could be shrunk or expanded, if less rehashing options are needed
    private int[] primes = new int[]{17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71};

    public void insert(String key, String value){

        // create the node to be inserted

        int currentSize = nodes.length;

        for (int i = 0; true; i++) {
            // generate a hash value. the primes array within the loop avoids collisions, and provides a way of finding
            // keys that would have collided without the array. absolute value and modulo arithmetic ensures value
            // is always a valid array index

            // if we find a duplicate value, we can use this to stop its insertion.
            boolean duplicate = false;

            // this would probably work with + primes[?], rather than * primes[?] and be less expensive, but not hugely
            int hash = Math.abs((key.hashCode() * primes[i % primes.length]) % nodes.length);

            // if empty put it in
            if(nodes[hash] == null){
                nodes[hash] = new MyLinkedList(key, value);
                break;
            }
            // if not empty, but needs to be chained
            else if(key.equals(nodes[hash].getKey())){
                MyNode tempMyNode = nodes[hash].getHead();
                while(tempMyNode.getNext() != null){
                    // duplicate values. this could help stop java.lang.OutOfMemoryError: Java heap space error as it
                    // cuts down memory usage. Seems to be caused by using bigger prefix size e.g. 4 on the big.txt
                    // file. Optionally you could add int count to Node and increment, to keep track of them if you
                    // wanted to reflect the frequency of a follower in how they are chosen.
                    if(value.equals(tempMyNode.getValue())) duplicate = true;
                    tempMyNode = tempMyNode.getNext();
                }

                if(!duplicate) tempMyNode.setNext(new MyNode(value));

                break;
            }
            // loops again with an updated hash if node is not inserted

            //update 04/11/2016, this if statement
            // if no more space left
            if( i > currentSize){
                MyLinkedList[] temp = nodes;                    // store values safely
                nodes = new MyLinkedList[currentSize*2];     // assign a new expanded array
                for (int j = 0; j < temp.length; j++) {      // repopulate
                    nodes[j] = temp[j];
                }
                currentSize *= 2;              // update the checking value
            }
        }
    }

    public boolean contains(String key){
        for (int i = 0; true ; i++) {
            int hash = Math.abs((key.hashCode() * primes[i % primes.length]) % nodes.length);

            // if hash value finds a null value in the hash table, it does not contain the key
            if(nodes[hash] == null){
                return false;
            }
            // if hash value finds a node, and it matches the key
            else if(key.equals(nodes[hash].getKey())){
                return true;
            }
            // if hash value finds node, but it isnt the right key, loop again with the next prime
        }
    }


    public String[] retrieve(String key){
        for (int i = 0; true ; i++) {
            int hash = Math.abs((key.hashCode() * primes[i % primes.length]) % nodes.length);

            // if hash value finds a null value in the hash table, it does not contain the key
            if(nodes[hash] == null){
                return new String[0];
            }
            // if hash value finds a node, and it matches the key
            else if(key.equals(nodes[hash].getKey())){

                // nodes[hash] has to exist at this point therefore count can be started at 1
                int count = 1;
                MyNode tempMyNode = nodes[hash].getHead();
                // finds how many followers
                while(tempMyNode.getNext() != null){
                    count++;
                    tempMyNode = tempMyNode.getNext();
                }

                // construct the return array, reset tempNode to the first node in chain
                String[] followers = new String[count];
                tempMyNode = nodes[hash].getHead();
                for (int j = 0; j < count ; j++) {
                    followers[j] = tempMyNode.getValue();
                    tempMyNode = tempMyNode.getNext();
                }

                return followers;
            }
            // if hash value finds node, but it isn't the right key, loop again with the next prime
        }
    }

    // this is wrong, need to implement changes based on key. Never implemented as in this case, a remove function is
    // not needed.
    public void remove(String value){
//        int arrayIndex = Math.abs(value.hashCode() % nodes.length);
//        if(nodes[arrayIndex] != null){
//            Node tempNode = nodes[arrayIndex];
//            while(!tempNode.getValue().equals(value)){
//                Node previousNode = tempNode;
//                tempNode = tempNode.getNext();
//                if(tempNode.getValue().equals(value)){
//                    previousNode.setNext(tempNode.getNext());
//                }
//                if(tempNode.getNext() == null){
//                    break;
//                }
//            }
//        }
    }

    // for testing
    public void printDetails(){
        System.out.println("\nKey\t-\tValues\n");
        for (int i = 0; i < nodes.length; i++) {
            if(nodes[i] != null){
                MyNode tempMyNode = nodes[i].getHead();
                System.out.print(nodes[i].getKey() + "\t-\t");
                System.out.print(tempMyNode.getValue());
                while(tempMyNode.getNext() != null){
                    tempMyNode = tempMyNode.getNext();
                    System.out.print(", " + tempMyNode.getValue());
                }
                System.out.println();
            }
        }
    }

    // Getters and Setters

    public MyLinkedList[] getNodes() {
        return nodes;
    }

    public static void main(String[] args){
        MyLinkedHashTable m = new MyLinkedHashTable();




        m.insert("Carl","1");
        m.insert("Carl","2");
        m.insert("Carl", "3");

        System.out.println(m.contains("Carl"));
        System.out.println(m.contains("Charles"));
        System.out.println(Arrays.toString(m.retrieve("Carl")));



    }

}
