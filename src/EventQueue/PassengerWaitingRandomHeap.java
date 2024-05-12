package EventQueue;

import Main.Losowanie;
import Transit.PassengerWaiting;

import java.util.EmptyStackException;

// Ponieważ w javie niemożliwe jest tworzenie generic array (do tego są zakazane arraylisty) muszę explicite'a tworzyć wyspecjalizowaną kopię
public class PassengerWaitingRandomHeap {
    protected PassengerWaitingHeapNode root = null;
    private int size = 0;

    public PassengerWaitingRandomHeap() {

    }

    public void clear() {
        size = 0;
        root = null;
    }

    private PassengerWaitingHeapNode meld(PassengerWaitingHeapNode a, PassengerWaitingHeapNode b) {
        if(a == null) return b;
        if(b == null) return a;

        if (a.getValue().compareTo(b.getValue()) > 0) {
            var swap = a;
            a = b;
            b = swap;
        }

        if (Losowanie.losuj(1, 2) <= 1) {
            var res = meld(a.getLeft(), b);
            res.setParent(a);
            a.setLeft(res);
        } else {
            var res = meld(a.getRight(), b);
            res.setParent(a);
            a.setRight(res);
        }
        return a;
    }

    public void insert(PassengerWaitingHeapNode node) {
        root = meld(root, node);
        root.setParent(null);
        size++;
    }

    public PassengerWaitingHeapNode findMin() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public PassengerWaitingHeapNode popMin() {
        if (root == null) throw new EmptyStackException();
        var min = root;
        root = meld(root.getLeft(), root.getRight());
        if (root != null) root.setParent(null);
        size--;
        return min;
    }

    public void delete(PassengerWaitingHeapNode node) {
        if (node == null) return;
        if (node == root) {
            popMin();
            return;
        }
        if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(null);
        } else {
            node.getParent().setRight(null);
        }

        root = meld(root, meld(node.getLeft(), node.getRight()));
        root.setParent(null);
        size--;
    }

    private void getNodes(PassengerWaitingHeapNode node, Vector<PassengerWaitingHeapNode> nodes) {
        if (node == null) return;
        nodes.pushBack(node);
        getNodes(node.getLeft(), nodes);
        getNodes(node.getRight(), nodes);
    }

    public PassengerWaitingHeapNode[] getNodes() {
        Vector<PassengerWaitingHeapNode> passengerWaitingVector = new Vector<>(PassengerWaitingHeapNode[].class);
        getNodes(root, passengerWaitingVector);
        return passengerWaitingVector.toArray();
    }
}
