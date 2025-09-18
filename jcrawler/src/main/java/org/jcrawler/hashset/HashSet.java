package org.jcrawler.hashset;

public class HashSet{
    private final int HASH_SIZE= 100;
    private static class Node{
        String url;
        Node next;
    }
    private Node hashtab[]=new Node[HASH_SIZE];

    private int hash(String input){
        return (input.hashCode() & 0x7fffffff) % HASH_SIZE;
    }

    public void add(String url){
        int hashValue= hash(url);
        for(Node current= hashtab[hashValue]; current!=null; current=current.next){
            if(current.url.equals(url)) return;
        }
        Node node= new Node();
        node.url=url;
        node.next= hashtab[hashValue];
        hashtab[hashValue]= node;
    }

    public boolean contains(String url){
        int hashValue= hash(url);
        for(Node current= hashtab[hashValue]; current!=null; current=current.next){
            if(current.url.equals(url)){
                return true;
            }
        }
        return false;
    }

    public void clear(){
        hashtab= new Node[HASH_SIZE];
    }
}