package org.jcrawler.queue;

/*
The below implementation of the {@code Queue} class servers for producer consumer design, 
whereby the producer and consumer programs are running simultaneously on different threads.
The crawler and the parser will be using instances of the class as their frontier and parser queues
respectively and both acts either consumer or producer for eachothers queues, the crawler is the 
producer for the parser queue and consumer for the frontier queue while the parser is the producer
for the frontier queue and consumer for the parser queue.

The producer adds items to the queue as long as the queue hasn't reached it's maximum capacity, if so 
it is blocked until a consumer removes an item from the queue and wakes it. The consumer keeps removing 
items from the queue until the queue becomes empty by then it gets blocked and waits for the producer to 
add an item to the queue and wake it.

Alhough for our use case we could have just build the queue to store strings but we with a queue that could handle any 
java object(Integer, String, Double...) passed to it referring Java's own implementation of {@code LinkedList} in java.util
*/

public class Queue<E>{/*by default {@code E extends Object} */
    /*{ @code Node } is a static class for efficiency reasons (reffered java.util.LinkedList)*/
    private static class Node<E>{
        E item;
        Node<E> next;
    }
    Node<E> head;
    int size=0;/*number of items in queue*/
    final int MAX_QUEUE_SIZE=100;/*defined the maximum number of items that can be stored in queue*/

    public synchronized void put(E item){/*add items at the end of the queue*/
        while(size == MAX_QUEUE_SIZE){/*Wait if queue is full*/
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Node<E> node= new Node();
        node.item=item;
        node.next=null;
        if(head==null){
            head= node;
        }else{
            Node<E> currentNode=head;
            while(currentNode.next!=null){
                currentNode=currentNode.next;
            }
            currentNode.next=node;
        }
        size++;
        notify();/*wake the consumer incase it's waiting*/
    }
    public synchronized E take(){/*remove the first item from queue*/
        while(head==null || size==0){/*wait if queue is empty*/
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Node<E> currentHead=head;
        head=head.next;
        size--;
        notify();/*notify producer incase it's sleeping.*/
        return currentHead.item;
    }
    public int getSize(){/*return the size/number of items in queue*/
        return size;
    }
}