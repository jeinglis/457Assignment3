/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc457;

import java.util.Iterator;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cpsc457.LinkedList;
import static org.junit.Assert.*;

/**
 * Used to test the LinkedList class
 * @author Brad
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
     * Tests both iterator and the toString method in LinkedList
    */ 
    @Test
    public void testToString()  {
        for (int i = 0; i < 5; i++) {
            list.insert(i + 1, i);
        }
        assertTrue((list.toString()).contains("{ 1, 2, 3, 4, 5 }"));
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
    public void testPar_sort_LinkedList() throws InterruptedException {
    	Random rand = new Random(22);
    	
    	int numberOfThreads = 4;
    	double [] Tests = {1e5, 2e5,4e5,8e5,16e5};
    	long startPar,endPar;
    	long startSeq,endSeq;
    


    	for(int i = 0; i< Tests.length; i++){
        	for(double j = 0 ; j < Tests[i]; j++)
           	 list.append(rand.nextInt(999));
         startPar = System.currentTimeMillis();
         LinkedList.par_sort(list,numberOfThreads);
         endPar = System.currentTimeMillis();
         System.out.printf("The %f item parallel sort took %d seconds \n", Tests[i], (endPar-startPar)/1000);
         
         startSeq = System.currentTimeMillis();
         LinkedList.sort(list);
         endSeq = System.currentTimeMillis();
         System.out.printf("The %f item sequential sort took %d seconds \n", Tests[i], (endSeq-startSeq)/1000);
         System.out.println("");
         list.clear();
    	}
        
    }

    /**
     * Test of sort method, of class LinkedList.
     */
    @Test//(timeout=15000)
    public void testSort_LinkedList() {
        list.append(14);
        list.append(13);
        list.append(5);
        list.append(12);
        list.append(11);
        list.append(10);
        list.append(11);
        list.append(4);
        list.append(3);
        
        LinkedList.sort(list);
        System.out.println(list.toString());
        assertTrue(list.toString().contains("{ 3, 4, 5, 10, 11, 11, 12, 13, 14 }"));
    }
}
