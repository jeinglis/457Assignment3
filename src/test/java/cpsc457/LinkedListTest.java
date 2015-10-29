/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc457;

import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brad
 */
public class LinkedListTest {
    LinkedList<Integer> list;
    
    public LinkedListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Starting my personal tests for LinkedList and the "
                + "corresponding sort methods");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        list = new LinkedList<>();
    }
    
    @After
    public void tearDown() {
        list = null;
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInsert()    {
        list.insert(3, 0).append(25);
        list.insert(1, 1);
        list.insert(6, 3);
        assertTrue(list.get(1).equals(1));
        assertTrue(list.get(0).equals(3));
        assertTrue(list.get(3).equals(6));
        list.insert(4, 6);
    }
    
    
    /**
     * Test of append method, of class LinkedList.
     */
    @Test
    public void testAppend() {
        list.append(25);
        assertTrue(list.get(0).equals(25));
    }

    /**
     * Test of size method, of class LinkedList.
     */
    @Test
    public void testSize() {
        list.append(25);
        assertTrue(list.size() == 1);
        list.append(26);
        assertTrue(list.size() == 2);
    }

    /**
     * Test of isEmpty method, of class LinkedList.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.append(25);
        assertTrue(!list.isEmpty());
    }

    /**
     * Test of clear method, of class LinkedList.
     */
    @Test
    public void testClear() {
        list.append(25);
        list.clear();
        assertTrue(list.isEmpty());
    }

    /**
     * Test of get method, of class LinkedList.
     */
    @Test
    public void testGet() {
        list.append(25);
        list.append(32);
        list.append(53);
        assertTrue(list.get(0) == 25);
        assertTrue(list.get(1) == 32);
        assertTrue(list.get(2) == 53);
    }

    /**
     * Test of par_sort method, of class LinkedList.
     */
    @Test
    public void testPar_sort_LinkedList() {
        fail("You haven't implemented the test for parallel sort yet!");
    }

    /**
     * Test of sort method, of class LinkedList.
     */
    @Test
    public void testSort_LinkedList() {
        fail("You haven't implemented a test for sort yet!");
    }
}
