package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Description:
 * @Author: whj
 * @Date: 2023-10-01 21:46
 */
public class ArrayDequeTest {

    @Test
    public void addIsEmptySizeTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertTrue(ad.isEmpty());

        ad.addFirst(1);
        assertFalse(ad.isEmpty());
        assertEquals(1, ad.size());
        ad.addLast(2);
        assertEquals(2, ad.size());
        ad.addFirst(3);
        assertEquals(3, ad.size());
        ad.removeLast();
        assertEquals(2, ad.size());

        ad.removeFirst();
        ad.removeFirst();
        assertTrue(ad.isEmpty());
    }

    @Test
    public void removeEmptyTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(3);
        ad.removeFirst();
        ad.removeFirst();
        ad.removeLast();
        ad.removeLast();
        assertTrue(ad.isEmpty());
        assertEquals(0, ad.size());

    }

    @Test
    public void multipleParamTest() {
        ArrayDeque<Double> ad1 = new ArrayDeque<>();
        ArrayDeque<String> ad2 = new ArrayDeque<>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<>();

        ad1.addFirst(1.1);
        ad1.removeFirst();
        assertEquals(true, ad1.isEmpty());
        ad2.addFirst("xxx");
        ad2.removeFirst();
        assertEquals(true, ad2.isEmpty());
        ad3.addFirst(true);
        ad3.removeLast();
        assertEquals(true, ad3.isEmpty());
    }

    @Test
    public void emptyNullReturnTest() {
        ArrayDeque<Double> ad1 = new ArrayDeque<>();


        assertEquals(null, ad1.removeLast());
        assertEquals(null, ad1.removeFirst());
    }

    @Test
    public void printTest(){
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(1);
        ad1.addFirst(2);
        ad1.addFirst(3);
        ad1.addFirst(4);
        ad1.addLast(5);
        ad1.addLast(6);
        ad1.addFirst(7);
        ad1.addFirst(8);
        ad1.printDeque();

        assertEquals(true,ad1.isFull());
    }

    @Test
    public void iteratorTest(){
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(1);
        ad1.addFirst(2);
        ad1.addFirst(3);
        ad1.addFirst(4);
        ad1.addLast(5);
        ad1.addLast(6);
        ad1.addFirst(7);
        ad1.addFirst(8);
        for (Integer integer : ad1) {
            System.out.println(integer);
        }

    }

    @Test
    public void resizeTest(){
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            ad1.addFirst(i);
        }
        System.out.println(ad1.size());
        System.out.println(ad1.getLength());
        for (int i = 0; i < 80 ; i++) {
            ad1.removeLast();
        }
        System.out.println();
        System.out.println(ad1.size());
        System.out.println(ad1.getLength());
    }

    @Test
    public void getTest(){
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(1);
        ad1.addLast(2);
        System.out.println(ad1.get(0));
        System.out.println(ad1.get(1));

    }
}
