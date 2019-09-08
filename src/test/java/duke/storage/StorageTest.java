package duke.storage;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class StorageTest extends Storage {
    public StorageTest() {
        super("nopath");
    }

    @Test
    public ArrayList<String> readFile() {
        return null;
    }

    @Test
    public void writeFile() {
        System.out.println("StorageTest: written file");
    }
}