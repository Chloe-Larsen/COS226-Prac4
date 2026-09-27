/*
    Anchen Kruger, u25073703
    Caleb Jennings, u25173805
    Chloe Larsen, u25004141
*/

public class FineList {

    private final Node head;
    private final Node tail;

    public FineList() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);

        head.next = tail;
    }

    public boolean add(int value) {
        head.lock.lock();
        Node predecessor = head;

        try {
            Node current = predecessor.next;
            current.lock.lock();
            try {
                while (current.value < value) {
                    predecessor.lock.unlock();
                    predecessor = current;
                    current = current.next;
                    current.lock.lock();
                }
                if (current.value == value)
                    return false;
                Node node = new Node(value);
                node.next = current;
                predecessor.next = node;
                return true;
            } finally {
                current.lock.unlock();
            }
        } finally {
            predecessor.lock.unlock();
        }
    }

    public boolean remove(int value) {
        head.lock.lock();
        Node predecessor = head;

        try {
            Node current = predecessor.next;
            current.lock.lock();
            try {
                while (current.value < value) {
                    predecessor.lock.unlock();
                    predecessor = current;
                    current = current.next;
                    current.lock.lock();
                }
                if (current.value != value)
                    return false;                             
                predecessor.next = current.next;
                return true;
            } finally {
                current.lock.unlock();
            }
        } finally {
            predecessor.lock.unlock();
        }
    }

    public boolean contains(int value) {
        head.lock.lock();
        Node predecessor = head;

        try {
            Node current = predecessor.next;
            current.lock.lock();
            try {
                while (current.value < value) {
                    predecessor.lock.unlock();
                    predecessor = current;
                    current = current.next;
                    current.lock.lock();
                }
                return current.value == value;
            } finally {
                current.lock.unlock();
            }
        } finally {
            predecessor.lock.unlock();
        }
    }
}