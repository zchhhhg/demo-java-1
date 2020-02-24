/*
* 测试集合框架
* author：张璐思
* 2020-02-24
* */
package com.bosssoft.hr.train.collectionframwork;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TestCollection {
    public static void main(String[] args) {
        TestCollection tc = new TestCollection();
        tc.testTreeSet();
    }
    public void testArraylist(){
        ArrayList<String> al = new ArrayList<>();
        al.add("zhangsan");
        al.add(1, "lisi");
        al.remove(1);
        al.set(0, "lisi");
        System.out.println(al.get(0));
        System.out.println(al.isEmpty());
        System.out.println(al.size());
        for (String str:al){
            System.out.println(str);
        }
    }

    //以下集合框架暴露的相同方法不做具体演示,参考jdk1.8 API
    public void testLinkedlist(){
        LinkedList<String> ld = new LinkedList<>();
        ld.addFirst("zhangsan");
        ld.addLast("lisi");
        ld.offer("wangwu");
        System.out.println(ld.indexOf("lisi"));
        System.out.println(ld.pop());
        for (String str:ld){
            System.out.println(str);
        }

    }

    public void testTreeSet(){
        TreeSet<Integer> ts = new TreeSet<>();
        ts.add(1);
        ts.add(2);
        ts.add(3);
        System.out.println(ts.ceiling(2));
        SortedSet<Integer> ss = ts.headSet(2);
        Iterator iter =ts.iterator();
    }

    public void testQueue(){
        //Queue为接口类型，无法新建对象
        Queue<String> queue = new LinkedList<>();
        queue.add("zhangsan");
        queue.add("lisi");
        queue.element();
        queue.offer("wangwu");
        queue.peek();
        queue.poll();
    }
    public void testHashMap(){
        HashMap<Integer,String> hm = new HashMap<>();
        hm.put(0, "zhangsan");
        hm.put(1, "lisi");
        hm.put(2, "wangwu");
        hm.replace(1, "lisi", "lisisi");
        Collection<String> co = hm.values();
    }
    public void testTreeMap(){
        TreeMap<Integer,String> tm = new TreeMap<>();
        tm.put(0, "zhangsan");
        tm.put(1, "lisi");
        tm.put(2, "wangwu");
        boolean flag = tm.containsKey(1);
        Integer i =tm.firstKey();
        SortedMap sm = tm.subMap(0, 1);
    }
    public void ConCurrentHashMap(){
        ConcurrentHashMap<Integer, String> cchm = new ConcurrentHashMap<>(100);
        cchm.put(0, "zhangsan");
        cchm.put(1, "lisi");
        String str = cchm.toString();
        System.out.println(cchm.hashCode());

    }
}
