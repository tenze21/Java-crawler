package org.jcrawler.arraylist;
/*Array list for stroring seed urls.*/
public class ArrayList{
    private static class URL{
        String url;
        int count;
        public URL(String url){
            this.url=url;
            this.count=0;
        }
    }
    private URL[] urls= new URL[10];
    int size=0;
    private void grow(){
        int initialCapacity=urls.length;
        int additionalCapacity= initialCapacity / 2;
        URL[] newArr= new URL[initialCapacity + additionalCapacity];
        System.arraycopy(urls, 0, newArr, 0, urls.length);
        urls=newArr;
    }
    public void push(String url){
        if(size == urls.length) grow();
        for(URL urlObject : urls){
            if(urlObject.url.equals(url)){
                urlObject.count++;
                return;
            }
        }
        URL urlObject= new URL(url);
        urls[size]= urlObject;
        size++;
    }

    public void sort(){
        
    }
}