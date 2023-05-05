package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> lastNode;
    private Node<T> firstNode;

    @Override
    public void add(T value) {
        addLastNode(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            addNodeInside(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = get(index);
        getNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> temporaryNode = firstNode; temporaryNode != null;
                temporaryNode = temporaryNode.next) {
            if (temporaryNode.item == object
                    || (object != null && object.equals(temporaryNode.item))) {
                unlink(temporaryNode);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void addLastNode(T value) {
        Node<T> temporaryNode = lastNode;
        Node<T> newNode = new Node<T>(temporaryNode, value, null);
        lastNode = newNode;
        if (temporaryNode == null) {
            firstNode = lastNode;
        } else {
            temporaryNode.next = newNode;
        }
        size++;
    }

    private void addNodeInside(T value, int index) {
        Node<T> temporaryNode = getNode(index);
        Node<T> previous = temporaryNode.prev;
        Node<T> nodeInside = new Node<T>(previous, value, temporaryNode);
        temporaryNode.prev = nodeInside;
        if (previous == null) {
            firstNode = nodeInside;
        } else {
            previous.next = nodeInside;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("invalid index: "
                    + index + " is not exist");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = firstNode;
        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = lastNode;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
        if (prev == null) {
            firstNode = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            lastNode = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
        return element;
    }
}
