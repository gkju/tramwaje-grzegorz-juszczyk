package EventQueue;

import Transit.PassengerWaiting;

// Ponieważ nie możemy używać ArrayList muszę mieć konkretne typy do tworzenia tablic, z arraylistami ta klasa jest niepotrzebna
public class PassengerWaitingHeapNode {
    protected PassengerWaiting value;
    protected PassengerWaitingHeapNode left;
    protected PassengerWaitingHeapNode right;
    protected PassengerWaitingHeapNode parent = null;

    public PassengerWaitingHeapNode(PassengerWaiting value) {
        this.value = value;
    }

    public PassengerWaiting getValue() {
        return value;
    }

    public PassengerWaitingHeapNode getLeft() {
        return left;
    }

    public PassengerWaitingHeapNode getRight() {
        return right;
    }

    public PassengerWaitingHeapNode getParent() {
        return parent;
    }

    public void setParent(PassengerWaitingHeapNode parent) {
        this.parent = parent;
    }

    public void setValue(PassengerWaiting value) {
        this.value = value;
    }

    public void setLeft(PassengerWaitingHeapNode left) {
        this.left = left;
    }

    public void setRight(PassengerWaitingHeapNode right) {
        this.right = right;
    }

    public int compareTo(PassengerWaitingHeapNode o) {
        return value.compareTo(o.getValue());
    }
}
