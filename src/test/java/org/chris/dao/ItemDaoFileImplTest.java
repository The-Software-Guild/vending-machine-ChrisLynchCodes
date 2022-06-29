package org.chris.dao;

import org.chris.dto.Item;

import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.math.BigDecimal;
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

    //before every test runs, we will have created a new blank vendingMachine.txt file using the FileWriter.
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
        item.setPrice(new BigDecimal("0.75"));
        item.setQuantity(5);
        //create second item
        // Add item to DAO
        testDao.addItem(item);

        Item retrievedItem = testDao.getItem(itemId);

        // Verify that they are the same item
        assertEquals(item.getITEM_ID(), retrievedItem.getITEM_ID());

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
        testDao.addItem(item);
        testDao.addItem(item2);
        testDao.addItem(item3);
        //get all items from dao
        List<Item> allItems = testDao.getItems();
        //verify not null
        assertNotNull(allItems);
        //verify size is 3
        assertEquals(3, allItems.size());
        //verify item1 is equal to item1
        assertEquals(item.getITEM_ID(), allItems.get(0).getITEM_ID());
        assertEquals(item.getTitle(), allItems.get(0).getTitle());
        //verify item2 is equal to item2
        assertEquals(item2.getITEM_ID(), allItems.get(1).getITEM_ID());
        assertEquals(item2.getTitle(), allItems.get(1).getTitle());
        //verify item3 is equal to item3
        assertEquals(item3.getITEM_ID(), allItems.get(2).getITEM_ID());
        assertEquals(item3.getTitle(), allItems.get(2).getTitle());

    }


    @Test
    public void addUpdateItemQuantity() throws ItemPersistenceException
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

        //add items to dao
        testDao.addItem(item);
        testDao.addItem(item2);
        item.setQuantity(4);



        //update item quantity
        testDao.updateItem(item);
        //Get all items
        List<Item> allItems = testDao.getItems();
        //verify not null
        assertNotNull(allItems);
        //verify size is 2
        assertEquals(2, allItems.size());
        //verify item1 is equal to item1
        assertEquals(item.getITEM_ID(), allItems.get(0).getITEM_ID());
        //verify 1 has been removed from quantity
        assertEquals(4, allItems.get(0).getQuantity());
        //verify item2 is equal to item2
        assertEquals(item2.getITEM_ID(), allItems.get(1).getITEM_ID());
        assertEquals(5, allItems.get(1).getQuantity());


    }


}