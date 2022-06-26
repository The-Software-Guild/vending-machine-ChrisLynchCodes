package org.chris.dao;

import org.chris.dto.Item;
import org.chris.service.ItemNoItemInventoryException;

import java.io.*;
import java.util.*;

public class ItemDaoFileImpl implements ItemDao {
    private final Map<String, Item> ITEMS = new HashMap<>();
    private final String VENDING_MACHINE_FILE;
    private static final String DELIMITER = "::";

    public ItemDaoFileImpl()
    {
        VENDING_MACHINE_FILE = "vendingMachine.txt";

    }

    public ItemDaoFileImpl(String dvdLibraryTextFile)
    {
        VENDING_MACHINE_FILE = "vendingMachineTestFile.txt";

    }


    //================== DATA MARSHALLING ==================

    //Writes the data from memory to a file
    private String marshallItem(Item aItem)
    {
        return String.format("%s%s%s",
                aItem.getItemId(),
                DELIMITER,
                aItem.getTitle()
        );
    }


    //Writes the data from memory to a file
    private void writeLibrary() throws ItemPersistenceException
    {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VENDING_MACHINE_FILE));
        } catch (IOException e) {
            throw new ItemPersistenceException(
                    "Could not save item data.", e);
        }


        String itemAsText;
        List<Item> itemList = this.getITEMS();
        for (Item currentItem : itemList) {
            // turn an item into a String
            itemAsText = marshallItem(currentItem);
            // write the item object to the file
            out.println(itemAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }


    //================== DATA UNMARSHALLING ==================
    //Loads the data from a file into memory
    private void loadLibrary() throws ItemPersistenceException
    {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_MACHINE_FILE)));
        } catch (FileNotFoundException e) {
            //catch the FileNotFoundException and translate it into a ItemDaoException
            throw new ItemPersistenceException(
                    "-_- Could not load item data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentItem holds the most recent item unmarshalled
        Item currentItem;
        // Go through VENDING_MACHINE_FILE line by line, decoding each line into an
        // Item object by calling the unmarshallItem method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Dvd
            currentItem = unmarshallItem(currentLine);

            // We are going to use the item id as the map key for our item object.
            // Put currentItem into the map using item id as the key
            ITEMS.put(currentItem.getItemId(), currentItem);
        }
        // close scanner
        scanner.close();
    }

    //Loads the data from a file into memory
    private Item unmarshallItem(String itemAsText)
    {

        String[] itemTokens = itemAsText.split(DELIMITER);

        // Index 0 - ID
        String dvdId = itemTokens[0];
        Item itemFromFile = new Item(dvdId);

        // Index 1 - title
        itemFromFile.setTitle(itemTokens[1]);

        return itemFromFile;
    }


    @Override
    public Item addItem(String itemId, Item item) throws ItemPersistenceException
    {
        loadLibrary();
        Item newItem = ITEMS.put(itemId, item);
        writeLibrary();
        return newItem;

    }


    @Override
    public List<Item> getITEMS() throws ItemPersistenceException
    {
        loadLibrary();
        return new ArrayList<>(ITEMS.values());

    }


    @Override
    public Item getItem(String itemId) throws ItemPersistenceException
    {
        loadLibrary();
        return ITEMS.get(itemId);
    }



    @Override
    public Item removeItem(String itemId) throws ItemPersistenceException
    {
        loadLibrary();
        Item removedItem = ITEMS.remove(itemId);
        writeLibrary();
        return removedItem;
    }

    @Override
    public Item updateItem(String itemId, Item editedItem) throws ItemPersistenceException
    {

        loadLibrary();
        ITEMS.get(itemId).setTitle(editedItem.getTitle());
        writeLibrary();
        //return the updated item
        return ITEMS.get(itemId);
    }
}

