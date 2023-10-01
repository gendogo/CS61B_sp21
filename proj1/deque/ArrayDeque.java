package deque;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @Description: Array based Deque
 * @Author: whj
 * @Date: 2023-10-01 17:13
 */
public class ArrayDeque<T> implements Iterable<T> {
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    private static final int FACTOR = 2;
    private static final int CAPACITY = 8;
    private static final double PROPOTION = 0.25;


    //The starting size of your array should be 8.

    public Integer getLength() {
        return items.length;
    }

    public ArrayDeque() {
        items = (T[]) new Object[CAPACITY];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public void addFirst(T item) {
        if (isFull()) {
            resize(FACTOR * this.getLength());
        }
        items[nextFirst] = item;
        if ((nextFirst - 1) < 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst--;
        }
        size++;
    }

    private void resize(int newCapacity) {
        T[] arr = (T[]) new Object[newCapacity];
        int i = 0;
        //size should not be changed.
        int tempSize = size;
        while (tempSize > 0) {
            if (nextFirst + 1 >= items.length) {
                nextFirst = 0;
            } else {
                nextFirst = nextFirst + 1;
            }
            arr[i] = items[nextFirst];
            items[nextFirst] = null;
            tempSize--;
            i++;
        }
        items = arr;
        arr = null;
        nextFirst = items.length - 1;
        nextLast = i;

    }

    public void addLast(T item) {
        if (isFull()) {
            resize(FACTOR * this.getLength());
        }
        items[nextLast] = item;
        if ((nextLast + 1) >= items.length) {
            nextLast = 0;
        } else {
            nextLast++;
        }
        size++;

    }

    public boolean isEmpty() {
        return size == 0;

    }

    public boolean isFull() {
        return size == items.length;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (isEmpty()) {
            System.out.println();
            return;
        }
        int tempSize = size;
        int temp = nextFirst;
        while (tempSize != 0) {
            if (nextFirst + 1 >= items.length) {
                nextFirst = 0;
            } else {
                nextFirst = nextFirst + 1;
            }
            System.out.println(items[nextFirst]);
            tempSize--;
        }
        System.out.println();
        nextFirst = temp;
    }

    //need to check if it is nessasary to resize the array
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (items.length >= 16 && size <= (int) Math.round(items.length * PROPOTION)) {
            resize(size);
        }
        if (nextFirst + 1 >= items.length) {
            nextFirst = 0;
        } else {
            nextFirst = nextFirst + 1;
        }
        T result = items[nextFirst];
        items[nextFirst] = null;
        size--;
        return result;
    }

    //need to check if it is nessasary to resize the array
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (items.length >= 16 && size <= (int) Math.round(items.length * PROPOTION)) {
            resize(size);
        }
        if (nextLast - 1 < 0) {
            nextLast = items.length - 1;
        } else {
            nextLast--;
        }
        T result = items[nextLast];
        items[nextLast] = null;
        size--;
        return result;
    }


    public T get(int index) {
        if (index < 0 || index >= items.length) {
            return null;
        }
        return items[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayDeque<T> that = (ArrayDeque<T>) o;

        //if (nextFirst != that.nextFirst) return false;
        //if (nextLast != that.nextLast) return false;
        if (size != that.size) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(items);
        //result = 31 * result + nextFirst;
        //result = 31 * result + nextLast;
        result = 31 * result + size;
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<T> {

        private int _size;
        private int _nextFirst;

        public DequeIterator() {
            _size = size;
            _nextFirst = nextFirst;
        }

        @Override
        public boolean hasNext() {
            if (_size == 0) {
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            if (_nextFirst + 1 >= items.length) {
                _nextFirst = 0;
            } else {
                _nextFirst = _nextFirst + 1;
            }
            T result = items[_nextFirst];
            _size--;
            return result;
        }
    }
}
