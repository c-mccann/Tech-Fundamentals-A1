/**
 * Created by C12508463 on 27/10/2016.
 */
public class MyLinkedList {
    private MyNode head;
    private String key;

    MyLinkedList(String key, String value){
        head = new MyNode(value);
        this.key = key;
    }

    // getters & setters
    public MyNode getHead() {
        return head;
    }

    public String getKey() {
        return key;
    }
}
