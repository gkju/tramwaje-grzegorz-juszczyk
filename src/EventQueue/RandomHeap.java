package EventQueue;

import Main.Losowanie;
import Transit.PassengerWaiting;

import java.util.EmptyStackException;

public class RandomHeap<T extends Comparable<T>> {
    private RandomHeapNode<T> root = null;
    private int size = 0;

    public RandomHeap() {
    }

    private RandomHeapNode<T> meld(RandomHeapNode<T> a, RandomHeapNode<T> b) {
        if(a == null) return b;
        if(b == null) return a;

        if (a.getValue().compareTo(b.getValue()) > 0) {
            var swap = a;
            a = b;
            b = swap;
        }

        if (Losowanie.losuj(1, 2) <= 1) {
            a.setLeft(meld(a.getLeft(), b));
        } else {
            a.setRight(meld(a.getRight(), b));
        }
        return a;
    }

    public void insert(RandomHeapNode<T> node) {
        root = meld(root, node);
        root.setParent(null);
        size++;
    }

    public RandomHeapNode<T> findMin() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public RandomHeapNode<T> popMin() {
        if (root == null) throw new EmptyStackException();
        var min = root;
        root = meld(root.getLeft(), root.getRight());
        if (root != null) root.setParent(null);
        size--;
        return min;
    }

    public void delete(RandomHeapNode<T> node) {
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
}
