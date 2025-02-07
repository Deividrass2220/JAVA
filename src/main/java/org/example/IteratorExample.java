package org.example;

import java.util.Iterator;
import java.util.LinkedList;

public class IteratorExample{

    public static void main(String[] args) {
        LinkedList <String> frutas = new LinkedList<>();
        frutas.add( "Banana");
        frutas.add( "Fresa");
        frutas.add( "Lulo");
        frutas.add( "Sandía");

        Iterator<String> iterator = frutas.iterator();
        iterator.hasNext();
        System.out.println(iterator);
    }
}
