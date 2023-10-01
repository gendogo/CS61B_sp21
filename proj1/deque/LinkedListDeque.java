package deque;

import java.util.Iterator;

/**
 * @Description: LinkedList based Deque
 * @Author: whj
 * @Date: 2023-10-01 13:44
 */
public class LinkedListDeque<T> implements Iterable<T> {
    private int size;

    private Node<T> sentinel;

    public class Node<T> {
        private T value;
        private Node<T> pre;
        private Node<T> next;

        public Node(T value, Node<T> pre, Node<T> next) {
            this.value = value;
            this.pre = pre;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<T> node = (Node<T>) o;

            if (this.getValue() != null ? !this.getValue().equals(node.getValue()) : node.getValue() != null)
                return false;
            if (pre != null ? !pre.equals(node.pre) : node.pre != null) return false;
            return next != null ? next.equals(node.next) : node.next == null;
        }

        @Override
        public int hashCode() {
            int result = value != null ? value.hashCode() : 0;
            result = 31 * result + (pre != null ? pre.hashCode() : 0);
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }
    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node<T>(null, null, null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
    }


    //Adds an item of type T to the front of the deque.
    // You can assume that item is never null.
    public void addFirst(T item) {
        if (item == null) {
            return;
        }
        Node<T> frontNode = new Node<>(item, sentinel, sentinel.next);
        sentinel.next.pre = frontNode;
        sentinel.next = frontNode;
        size++;
    }

    public void addLast(T item) {
        if (item == null) {
            return;
        }
        Node<T> lastNode = sentinel.pre;
        Node<T> newLastNode = new Node<>(item, lastNode, sentinel);
        lastNode.next = newLastNode;
        sentinel.pre = newLastNode;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    //    Prints the items in the deque from first to last, separated by a space.
    //    Once all the items have been printed, print out a new line.
    public void printDeque() {
        if (sentinel.next == sentinel) {
            System.out.println();
            return;
        }
        printDequeHelper(sentinel.next);


    }

    private void printDequeHelper(Node<T> cur) {
        if (cur.next == sentinel) {
            System.out.println(cur.getValue().toString());
            System.out.println();
            return;
        }
        System.out.println(cur.getValue().toString());
        printDequeHelper(cur.next);
    }

    // Removes and returns the item at the front of the deque.
    // If no such item exists, returns null.
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        Node<T> firstNode = sentinel.next;
        T result = firstNode.getValue();
        Node<T> secondNode = firstNode.next;
        firstNode = null;
        size--;
        sentinel.next = secondNode;
        secondNode.pre = sentinel;
        return result;
    }

    //Removes and returns the item at the back of the deque.
    // If no such item exists, returns null.
    public T removeLast() {
        if (sentinel.pre == sentinel) {
            return null;
        }
        Node<T> lastNode = sentinel.pre;
        Node<T> lastSecondNode = lastNode.pre;
        T result = lastNode.getValue();
        lastNode = null;
        size--;
        lastSecondNode.next = sentinel;
        sentinel.pre = lastSecondNode;
        return result;

    }

    // Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    // If no such item exists, returns null. Must not alter the deque!
    public T get(int index) {
        Node<T> cur = sentinel;
        for (int i = 0; i <= index; i++) {
            cur = cur.next;
        }
        if (cur == sentinel) {
            return null;
        }
        return cur.getValue();

    }

    // Same as get, but uses recursion.
    public T getRecursive(int index) {
        Node<T> cur = sentinel;
        if (cur.next == sentinel) {
            return null;
        }
        return getRecursiveHelper(cur.next, index);
    }

    public T getRecursiveHelper(Node<T> cur, int index) {
        if (index == 0) {
            if (cur == sentinel) {
                return null;
            }
            return cur.getValue();
        }
        return getRecursiveHelper(cur.next, --index);


    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorHelper(sentinel.next);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedListDeque<T> that = (LinkedListDeque<T>) o;

        if (size != that.size) return false;
        return sentinel != null ? sentinel.equals(that.sentinel) : that.sentinel == null;
    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + (sentinel != null ? sentinel.hashCode() : 0);
        return result;
    }

    public class IteratorHelper implements Iterator<T> {
        private Node<T> _start;

        public IteratorHelper(Node<T> start) {
            _start = start;
        }

        @Override
        public boolean hasNext() {
            if (_start == sentinel) {
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            T result = _start.getValue();
            _start = _start.next;
            return result;
        }
    }


}
