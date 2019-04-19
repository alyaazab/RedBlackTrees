import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choice;
        String word;
        boolean condition = true;

        RedBlackTree redBlackTree = File.readFromFile();

        while(condition)
        {
            System.out.println("\n\n\n");
            System.out.println("What would you like to do?");
            System.out.println("1) Search for a word");
            System.out.println("2) Insert a new word");
            System.out.println("3) Remove a word");
            System.out.println("4) Print dictionary size and tree height");
            System.out.println("5) Exit");

            choice = scanner.nextInt();
            switch(choice)
            {
                case 1:
                    System.out.println("Please enter a word");
                    word = scanner.next();
                    if(redBlackTree.search(word) == redBlackTree.getNil())
                        System.out.println("The word '" + word + "' was not found.");
                    else
                        System.out.println("The word '" + word + "' was found.");
                    break;

                case 2:
                    System.out.println("Please enter a word");
                    word = scanner.next();
                    if(redBlackTree.insertNode(word))
                    {
                        File.writeToFile(redBlackTree);
                        System.out.println("The word '" + word + "' was successfully inserted.");
                    }
                    else
                        System.out.println("The word '" + word + "' already exists.");

                    break;

                case 3:
                    System.out.println("Please enter a word");
                    word = scanner.next();
                    if(redBlackTree.deleteNode(word))
                    {
                        File.writeToFile(redBlackTree);
                        System.out.println("The word '" + word + "' was successfully deleted.");
                    }
                    else
                        System.out.println("The word '" + word + "' does not exist.");
                    break;

                case 4:
                    System.out.println("Dictionary Size = " + redBlackTree.getTreeSize());
                    System.out.println("Tree Height = " + redBlackTree.getTreeHeight());
                    break;

                case 5:
                    condition = false;
                    break;
            }
        }

    }
}
