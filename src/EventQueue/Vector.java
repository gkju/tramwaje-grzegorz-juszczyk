package EventQueue;

import java.lang.reflect.Array;

public class Vector<T> {
    private T[] buffer;
    private int size = 0;
    private int capacity;
    private Class<T[]> type;

    public Vector(Class<T[]> type) {
        this.capacity = 10;
        this.buffer = type.cast(Array.newInstance(type.getComponentType(), capacity));
        this.type = type;
    }

    public Vector(Class<T[]> type, int capacity) {
        if(capacity < 0) throw new IllegalArgumentException("Capacity must be non-negative");
        this.capacity = capacity;
        this.buffer = type.cast(Array.newInstance(type.getComponentType(), capacity));
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public T get(int index) {
        if(index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        return buffer[index];
    }

    public void set(int index, T value) {
        if(index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        buffer[index] = value;
    }

    public void pushBack(T value) {
        if(size == capacity) {
            capacity *= 2;
            T[] newBuffer = type.cast(Array.newInstance(type.getComponentType(), capacity));
            for(int i = 0; i < size; i++) {
                newBuffer[i] = buffer[i];
            }
            buffer = newBuffer;
        }
        buffer[size++] = value;
    }

    public T popBack() {
        if(size == 0) throw new IndexOutOfBoundsException("Vector is empty");
        return buffer[--size];
    }

    public T[] toArray() {
        T[] toReturn = type.cast(Array.newInstance(type.getComponentType(), size));
        for(int i = 0; i < toReturn.length; ++i) {
            toReturn[i] = buffer[i];
        }

        return toReturn;
    }
}
