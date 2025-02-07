package org.example;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

// Strings and Integers and such will sometimes return true on == as if .equals was called. This won't,
// so we won't get false positives in the tests.
class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ')';
    }
}

public class HashSetTest {

    @Test
    public void keeps_just_a_single_instance_of_an_added_item() {
        HashSet<Point> points = new HashSet<Point>(10);
        points.add(new Point(1, 1));
        points.add(new Point(1, 1));
        assertEquals(1, points.size());
    }

    @Test
    public void can_iterate_over_the_unique_values() {
        HashSet<Point> points = new HashSet<>(5);
        Point[] arr = uniquePoints(50);
        populateWith(points, arr);
        boolean[] found = new boolean[50];
        Arrays.fill(found, false);
        Iterator<Point> i = points.iterator();

        while (i.hasNext()) {
            Point p = i.next();
            // See which point we just found
            for (int n = 0; n < arr.length; n++)
                if (arr[n].equals(p))
                    found[n] = true;
        }

        // Did we find ALL of the points?
        for (int n = 0; n < arr.length; n++)
            assertTrue(found[n]);
    }


    @Test
    public void union_combines_unique_elements_of_two_sets() {
        HashSet<Integer> a = new HashSet<>(5);
        HashSet<Integer> b = new HashSet<>(5);

        a.add(1);
        a.add(3);

        b.add(3);
        b.add(7);

        HashSet<Integer> c = a.union(b);
        assertTrue(c.contains(1));
        assertTrue(c.contains(3));
        assertTrue(c.contains(7));
    }

    @Test
    public void union_leaves_original_sets_unchanged() {
        HashSet<Integer> a = new HashSet<>(5);
        HashSet<Integer> b = new HashSet<>(5);

        a.add(1);
        a.add(3);

        b.add(3);
        b.add(7);

        a.union(b);

        assertEquals(2, a.size());
        assertTrue(a.contains(1));
        assertTrue(a.contains(3));
        assertFalse(a.contains(7));

        assertEquals(2, b.size());
        assertFalse(b.contains(1));
        assertTrue(b.contains(3));
        assertTrue(b.contains(7));
    }

    /********************************************************************************
     * HELPER METHODS BELOW THIS LINE
     ********************************************************************************/
    Point[] uniquePoints(int n) {
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new Point(i, i);
        }
        return pts;
    }

    void populateWith(HashSet<Point> dest, Point[] src) {
        for (Point p : src)
            dest.add(p);
    }

}
