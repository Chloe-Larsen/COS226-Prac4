/*
    Anchen Kruger, u25073703
    Caleb Jennings, u25173805
    Chloe Larsen, u25004141
*/

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoarseList {

    private final Node head;
    private final Node tail;

    private final Lock lock = new ReentrantLock();

    public CoarseList() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);

        head.next = tail;
    }

    public boolean add(int value) {
        lock.lock();

        try {
            // get node less than "value" whose next is greater than "value"
            Node node = head;
            while (true) {
                if (node.value == value)
                    return false; // duplicate value
                if (node.next.value > value)
                    break; // found correct position
                node = node.next;
            }

            // node.value < value < node.next.value

            Node newNode = new Node(value);
            newNode.next = node.next;
            node.next = newNode;

            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(int value) {
        lock.lock();

        try {
            // find node before node to remove
            Node node = head;
            while (node.next != tail && node.next.value != value)
                node = node.next;

            if (node.next == tail)
                return false; // no node to remove

            node.next = node.next.next; // remove next node
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean contains(int value) {
        lock.lock();

        try {
            Node node = head.next;

            while (node != tail) {
                if (node.value == value)
                    return true;
                node = node.next;
            }

            return false;
        } finally {
            lock.unlock();
        }
    }
}