/**
 * Created by carlmccann2 on 18/10/2016.
 */
public class MyNode {
    private String value;
    private MyNode next;
    MyNode(String value){
        this.value = value;
    }
//    getters and setters
    public String getValue() {
        return value;
    }

    public MyNode getNext() {
        return next;
    }

    public void setNext(MyNode next) {
        this.next = next;
    }
}
