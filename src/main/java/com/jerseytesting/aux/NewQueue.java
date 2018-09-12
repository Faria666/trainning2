package com.jerseytesting.aux;

public class NewQueue extends Request {

    private int numElements;

    private Node first;
    private Node last;

    /**
     * Helper linked list class
     */
    private static class Node {
        private Request item;
        private Node next;
    }

    /**
     * Creates a new Queue object.
     */
    public NewQueue() {
        numElements = 0;
        first = last = null;
    }

    /**
     * Puts an object at the end of the queue.
     *
     * @param object
     */
    public void putObject(Request object) {

        synchronized (this) {

            Node newNode = new Node();
            newNode.item = object;
            newNode.next = null;

            if ( numElements == 0 ) {
                first = last = newNode;
            } else {
                last.next = newNode;
                last = newNode;
            }

            numElements += 1;
        }
    }

    /**
     * Gets an object from the beginning of the queue. The object is removed from the queue. If there are no objects in the queue, returns null.
     */
    public Request getObject() {

        synchronized (this) {

            Request item = null;

            if ( numElements > 0 ) {

                item = first.item;
                first = first.next;

                numElements -= 1;

                if (numElements == 0) {
                    last = null;
                }
            }

            return item;
        }
    }
}