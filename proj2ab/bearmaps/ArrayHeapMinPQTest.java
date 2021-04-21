package bearmaps;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    //contains, add, getSmallest, removeSmallest, changePriority
    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 4);
        test.add(5, 3);
        test.add(6, 5);
        test.add(7, 7);
        assertTrue(test.contains(1));
        assertTrue(test.contains(2));
        assertTrue(test.contains(3));
        assertFalse(test.contains(8));
        assertFalse(test.contains(9));
        assertFalse(test.contains(10));

    }


    @Test
    public void testAdd() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 4);
        test.add(5, 3);
        test.add(6, 5);
        test.add(7, 7);
        assertEquals(7, test.size());
        test.removeSmallest();
        assertEquals(6, test.size());
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 0);
        test.add(5, 3);
        test.add(6, 5);
        test.add(7, 7);
        assertEquals(4, (int) test.getSmallest());
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 0);
        test.add(5, 3);
        test.add(6, 5);
        test.add(7, 7);
        assertEquals(4, (int) test.removeSmallest());
        assertEquals(1, (int) test.removeSmallest());
        assertEquals(2, (int) test.removeSmallest());
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 0);
        test.add(5, 3);
        test.add(6, 5);
        test.add(7, 7);
        test.changePriority(4, 7);
        test.changePriority(7, 0);
        assertEquals(7, (int) test.getSmallest());
        assertEquals(7, (int) test.removeSmallest());
        assertEquals(1, (int) test.removeSmallest());
        assertEquals(2, (int) test.getSmallest());
    }

}
