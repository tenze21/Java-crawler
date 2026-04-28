package org.jcrawler.utils;

public class UrlArrayList{/*used for managing url containing user specified tokens.*/
    private static class URL{
        String url;
        int count;
        public URL(String url){
            this.url=url;
            this.count=1;
        }
    }

    private URL[] urls= new URL[10];
    public int size=0;
    private void grow(){
        int initialCapacity=urls.length;
        int additionalCapacity= initialCapacity / 2;
        URL[] newArr= new URL[initialCapacity + additionalCapacity];
        System.arraycopy(urls, 0, newArr, 0, urls.length);
        urls=newArr;
    }

    public void push(String url){
        if(size == urls.length) grow();
        for(int i=0; i<size; i++){
            if(urls[i].url.equals(url)){
                urls[i].count++;
                return;
            }
        }
        URL urlObject= new URL(url);
        urls[size]= urlObject;
        size++;
    }

    /*An implementation of the quick sort algorithm.*/
    private void swap(URL[] arr, int i, int j){
        URL tmp= arr[i];
        arr[i]= arr[j];
        arr[j]= tmp;
    }

    private int partition(URL[] arr, int low, int high){
        URL pivot= arr[high];
        int i=low - 1;
        for(int j=low; j<high; j++){
            if(arr[j].count>pivot.count){
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i+1, high);
        return i + 1;
    }

    private void quickSort(URL[] arr, int low, int high){
        if(low<high){
            int pivot= partition(arr, low, high);
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    public void sort(){
        quickSort(urls, 0, size - 1);
    }

    public void print(){/*Print the contents of the array list*/
        for(int i=0; i<size; i++){
            System.out.println(urls[i].url + "\t count: " + urls[i].count);
        }
    }

    public URL get(int idx){
        return urls[idx];
    }
}