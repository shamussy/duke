package duke.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String path;

    public Storage(String path) {
        this.path = path;
    }

    public ArrayList<String> readFile() throws FileNotFoundException {
        ArrayList<String> out = new ArrayList<>();
        File f = new File(path);
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            out.add(sc.nextLine());
        }
        return out;
    }

    public void writeFile(ArrayList<String> lines) throws IOException {
        new File("data/").mkdirs(); //creates directory if it does not exist
        FileWriter writer = new FileWriter(path);
        for (String line: lines) {
            writer.write(line + "\n");
        }
        writer.close();
    }

}
