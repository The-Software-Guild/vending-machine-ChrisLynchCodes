package org.chris.dao;

import org.chris.dto.Item;

import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDaoFileImplTest {

    ItemDao testDao;

    public ItemDaoFileImplTest()
    {
    }

    @BeforeAll
    public static void setUpClass()
    {
    }

    @AfterAll
    public static void tearDownClass()
    {
    }

    //before every test runs, we will have created a new blank vendingMachineTextFile.txt file using the FileWriter.
    @BeforeEach
    public void setUp() throws Exception
    {
        String testFile = "vendingMachineTestFile.txt";
        // Use the FileWriter to quickly blank the file
        try (FileWriter fileWriter = new FileWriter(testFile)) {
            testDao = new ItemDaoFileImpl(testFile);
        }

    }

    @AfterEach
    void tearDown()
    {

    }

    @Test
    void addGetItem() throws Exception
    {
        // Create our method test inputs
        String itemId = "1";
        Item item = new Item(itemId);
        item.setTitle("Apple");

        // Add item to DAO
        testDao.addItem(itemId, item);
        Item retrievedItem = testDao.getItem(itemId);

        // Verify that they are the same item
        assertEquals(item.getItemId(), retrievedItem.getItemId());

    }

    //
    @Test
    void addGetAllItems() throws Exception
    {

        //create first item
        String itemId = "1";
        Item item = new Item(itemId);
        item.setTitle("Apple");
        item.setPrice(new BigDecimal("0.75"));
        item.setQuantity(5);
                //create second item
        String itemId2 = "2";
        Item item2 = new Item(itemId2);
        item2.setTitle("Banana");
        item2.setPrice(new BigDecimal("0.75"));
        item2.setQuantity(5);
        //create third item
        String itemId3 = "3";
        Item item3 = new Item(itemId3);
        item3.setTitle("Cherry");
        item3.setPrice(new BigDecimal("0.00"));
        item3.setQuantity(5);

     //add items to dao
        testDao.addItem(itemId, item);
        testDao.addItem(itemId2, item2);
        testDao.addItem(itemId3, item3);
        //get all items from dao
        List<Item> allItems = testDao.getITEMS();
        //verify not null
        assertNotNull(allItems);
        //verify size is 3
        assertEquals(3, allItems.size());
        //verify item1 is equal to item1
        assertEquals(item.getItemId(), allItems.get(0).getItemId());
        assertEquals(item.getTitle(), allItems.get(0).getTitle());
        //verify item2 is equal to item2
        assertEquals(item2.getItemId(), allItems.get(1).getItemId());
        assertEquals(item2.getTitle(), allItems.get(1).getTitle());
        //verify item3 is equal to item3
        assertEquals(item3.getItemId(), allItems.get(2).getItemId());
        assertEquals(item3.getTitle(), allItems.get(2).getTitle());

    }

    @Test
    void addRemoveItem() throws Exception
    {
        //create first item
        String itemId = "1";
        Item item = new Item(itemId);
        item.setTitle("Apple");
        //create second item
        String itemId2 = "2";
        Item item2 = new Item(itemId2);
        item2.setTitle("Banana");


        //Add to dao
        testDao.addItem(itemId, item);
        testDao.addItem(itemId2, item2);


        //Remove item from dao
        Item removedItem = testDao.removeItem(itemId);

        //Verify removed item

        assertEquals(item.getItemId(), removedItem.getItemId(), "The removed DVD must have an ID of 1");

        //Verify item is no longer in dao
        List<Item> allItems = testDao.getITEMS();

        assertNotNull(allItems, "The list of items must not be null");
        assertEquals(1, allItems.size(), "The list of items must contain 1 item");
        assertEquals(item2.getItemId(), allItems.get(0).getItemId(), "The first item in the list must have an ID of 2");
        assertFalse(allItems.contains(item), "The list should not contain the removed item");

    }

    @Test
    void addUpdateItem() throws Exception
    {
        //create first item
        String itemId = "1";
        Item item = new Item(itemId);
        item.setTitle("Apple");
        //create second item
        String itemId2 = "2";
        Item item2 = new Item(itemId2);
        item2.setTitle("Banana");
        //add items to dao
        testDao.addItem(itemId, item);
        testDao.addItem(itemId2, item2);

        //update & get item from dao
        item.setTitle("Cherry");
        Item retrievedItem = testDao.updateItem(itemId, item);


        //verify item is equal to item
        assertEquals(item.getItemId(), retrievedItem.getItemId());
        assertEquals(item.getTitle(), retrievedItem.getTitle());


    }
//    @Test
//    void searchDvdByTitle()
//    {
//
//    }

}