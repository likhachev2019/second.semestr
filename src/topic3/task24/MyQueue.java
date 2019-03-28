package topic3.task24;

import topic2.task4.MyLinkedList;

import java.util.AbstractQueue;
import java.util.Iterator;

public class MyQueue<E> extends AbstractQueue<E> {

    private MyLinkedList<E> list = new MyLinkedList<>();

    private class MyQueueIterator<T> implements Iterator<T>{

//      Ссылка на следующий элемнент, который надо вернуть
        private MyLinkedList.MyListNode nextToExit;

        MyQueueIterator(){
            nextToExit = list.get(0);
        }

        @Override
        public boolean hasNext() {
            return nextToExit == null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            MyLinkedList.MyListNode buff = nextToExit;
            nextToExit = nextToExit.getNext();
            return (T) buff.getValue();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new MyQueueIterator<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    /**
     * Записываем новый элемент в конец очереди.
     */
    @Override
    public boolean offer(E e) {
        return list.add(e);
    }

    /**
     * Изымаем первый элемент из очереди
     */
    @Override
    public E poll() {
        E val;
        try{
            int i = size() - 1;
            val = list.get(i).getValue();
            list.remove(list.get(size() - 1));
            return val;
        }
        catch (RuntimeException e){
            return null;
        }
    }

    @Override
    public E peek() {
        try{
            return list.get(size() - 1).getValue();
        }
        catch (RuntimeException e){
            return null;
        }
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
