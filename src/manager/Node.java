package manager;

import model.Task;

public class Node {

    Node next; // link to the next node
    Node previous;//link to the previous node;
    Task data;// data as a task

    public Node(Task data,Node next, Node previous ) {
        this.next = next;
        this.previous = previous;
        this.data = data;
    }

    public Node(Task data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }
}
