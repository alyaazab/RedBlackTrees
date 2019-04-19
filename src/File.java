import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class File {

    public static RedBlackTree readFromFile() {
        RedBlackTree redBlackTree = new RedBlackTree();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("dictionary.txt"));
            String line = bufferedReader.readLine();
            while(line != null)
            {
                redBlackTree.insertNode(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return redBlackTree;
    }
}
