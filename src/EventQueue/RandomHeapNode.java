package EventQueue;

public class RandomHeapNode<T extends Comparable<T>> implements Comparable<RandomHeapNode<T>> {
    private T value;
    private RandomHeapNode<T> left;
    private RandomHeapNode<T> right;
    private RandomHeapNode<T> parent;

    public RandomHeapNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public RandomHeapNode<T> getLeft() {
        return left;
    }

    public RandomHeapNode<T> getRight() {
        return right;
    }

    public RandomHeapNode<T> getParent() {
        return parent;
    }

    public void setParent(RandomHeapNode<T> parent) {
        this.parent = parent;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setLeft(RandomHeapNode<T> left) {
        this.left = left;
    }

    public void setRight(RandomHeapNode<T> right) {
        this.right = right;
    }

    @Override
    public int compareTo(RandomHeapNode<T> o) {
        return value.compareTo(o.getValue());
    }
}
