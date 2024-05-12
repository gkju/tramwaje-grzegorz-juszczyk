package EventQueue;

import Main.Losowanie;
import Transit.PassengerWaiting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class RandomHeap<T extends Comparable<T>> {
    private RandomHeapNode<T> root = null;
    private int size = 0;

    public RandomHeap() {

    }

    public void clear() {
        size = 0;
        root = null;
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

    // TODO: W javie nie da się zaimplementować własnego wektora, bo nie da się stworzyć array'a generic'a, więc trzeba w generic clasie użyć w jednym miejscu arraylist.
    private void getNodes(RandomHeapNode<T> node, ArrayList<RandomHeapNode<T>> nodes) {
        if (node == null) return;
        nodes.add(node);
        getNodes(node.getLeft(), nodes);
        getNodes(node.getRight(), nodes);
    }

    public ArrayList<RandomHeapNode<T>> getNodes() {
        ArrayList<RandomHeapNode<T>> nodes = new ArrayList<>(size);
        getNodes(root, nodes);
        return nodes;
    }
}
