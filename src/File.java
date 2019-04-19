import java.io.*;

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

    public static void writeToFile(RedBlackTree redBlackTree){
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("dictionary.txt"));
            writeTreeToFile(writer, redBlackTree, redBlackTree.getRoot());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeTreeToFile(BufferedWriter writer, RedBlackTree redBlackTree, Node node) {
        if (node != redBlackTree.getNil()) {
            writeTreeToFile(writer, redBlackTree, node.getLeft());
            try {
                writer.write(node.getKey() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            writeTreeToFile(writer, redBlackTree, node.getRight());
        }
    }

}
