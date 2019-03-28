package topic2.task4;

import java.util.Iterator;

public class MyLinkedList<T> {

    private static final String TEXT_EMPTY_LIST = "List is empty.",
                                TEXT_NULL_VALUE = "This value is null!",
                                TEXT_NULL_NODE = "This node is null!";

    private MyListNode<T> head, tail;

    public class MyListNode<E>{
        private E value;
        private MyListNode<T> next;

        MyListNode(E value, MyListNode<T> next){
            if (value == null)
                throw new NullPointerException(TEXT_NULL_VALUE);

            this.value = value;
            this.next = next;
        }

        MyListNode(E value){
            this(value, null);
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public MyListNode getNext() {
            return next;
        }

        public void setNext(MyListNode next) {
            this.next = next;
        }
    }

    public MyLinkedList(){
        head = tail = null;
    }

    public boolean add(T value){
        if (value == null)
            throw new NullPointerException(TEXT_NULL_VALUE);
        MyListNode<T> node = new MyListNode<T>(value);

        if (tail == null){
            head = tail = node;
        }
        else {
            tail.next = node;
            tail = node;
        }
        return true;
    }

    public MyListNode<T> get(int index){
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        int count = 0;
        Iterator<MyListNode<T>> it = iterator();
        MyListNode<T> node;

        while (it.hasNext()){
            node = it.next();
            if (index == count++)
                return node;
        }
        return null;
    }

    public int size(){
        Iterator it = iterator();
        int count = 0;

        while (it.hasNext()) {
            it.next();
            count++;
        }
        return count;
    }

    public boolean remove(MyListNode<T> node){
        if (node == null)
            throw new NullPointerException(TEXT_NULL_NODE);

        Iterator<MyListNode<T>> it = iterator();
        MyListNode<T> prev = null, curr;
        while (it.hasNext()){
            curr = it.next();
            if (curr == node){
                if (prev != null){
                    prev.next = curr.next;
                }
                else
                    head = tail = null;
                return true;
            }
            prev = curr;
        }
        return false;
    }

    public void clear(){
        Iterator it = iterator();

        while (it.hasNext()){
            it.next();
            it.remove();
        }
    }

    public Iterator<MyListNode<T>> iterator(){
        return new MyIterator();
    }


    private class MyIterator implements Iterator<MyListNode<T>> {

        private MyListNode<T> curr, next;

        MyIterator(){
            curr = null;
            next = head;
        }

        public boolean hasNext(){
            return next != null;
        }

        public MyListNode<T> next(){
            curr = next;
            next = next.next;

            return curr;
        }

        public void remove(){
           MyLinkedList.this.remove(curr);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringRepresentationOfList = new StringBuilder();
        Iterator<MyListNode<T>> it = iterator();

        while (it.hasNext()){
            stringRepresentationOfList.append(it.next().value);
            if (it.hasNext())
                stringRepresentationOfList.append(',');
            stringRepresentationOfList.append(' ');
        }
        return stringRepresentationOfList.length() > 0 ? stringRepresentationOfList.toString() : TEXT_EMPTY_LIST;
    }
}