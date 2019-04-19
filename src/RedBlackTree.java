public class RedBlackTree {

    private Node nil = new Node("xyz");
    private Node root;
    private int treeSize = 0;



    private int treeHeight = 0;

    public RedBlackTree() {
        nil.setColor(Node.Color.BLACK);
        this.root = nil;
    }
    public RedBlackTree(String key) {
        nil.setColor(Node.Color.BLACK);
        root = new Node(key);
        root.setParent(nil);
        root.setRight(nil);
        root.setLeft(nil);
        root.setColor(Node.Color.BLACK);
        setTreeSize(1);
    }

    private void rotateLeft(Node x) {
        //set y as x's right child
        Node y = nil;
        if (x.getRight() != nil)
            y = x.getRight();
        //set y's left child as x's right child
        x.setRight(y.getLeft());
        //set x as y's left child's parent
        if (y.getLeft() != nil)
            y.getLeft().setParent(x);


        y.setParent(x.getParent());
        if (x.getParent() == nil)
            this.root = y;
        else if (x == x.getParent().getLeft())
            x.getParent().setLeft(y);
        else
            x.getParent().setRight(y);

        y.setLeft(x);
        x.setParent(y);

    }


    private void rotateRight(Node x) {
        Node y = nil;
        if (x.getLeft() != nil)
            y = x.getLeft();
        x.setLeft(y.getRight());

        if (y.getRight() != nil)
            y.getRight().setParent(x);

        y.setParent(x.getParent());
        if (x.getParent() == nil)
            this.root = y;
        else if (x == x.getParent().getLeft())
            x.getParent().setLeft(y);
        else
            x.getParent().setRight(y);

        y.setRight(x);
        x.setParent(y);
    }

    public boolean insertNode(String key) {
        Node z = new Node(key);
        z.setLeft(nil);
        z.setRight(nil);
        z.setParent(nil);
        Node y = nil;
        Node x = this.root;

        //traverse down the tree to find the leaf node y that should be z's parent
        while (x != nil) {
            y = x;

            if (z.getKey().compareTo(x.getKey()) < 0)
                x = x.getLeft();
            else if(z.getKey().compareTo(x.getKey()) > 0)
                x = x.getRight();
            else
                return false;
        }

        z.setParent(y);
        if (y == nil)
            this.root = z;
        else if (z.getKey().compareTo(y.getKey()) < 0)
            y.setLeft(z);
        else
            y.setRight(z);

        z.setColor(Node.Color.RED);

        insertFixUp(z);

        setTreeSize(getTreeSize() + 1);

        return true;
    }

    private void insertFixUp(Node z) {
        Node y;

        while (z.getParent() != nil && z.getParent().getColor() == Node.Color.RED) {
            System.out.println("insert fixuuup");
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                y = z.getParent().getParent().getRight();
                //case 1
                if (y.getColor() == Node.Color.RED) {
                    z.getParent().setColor(Node.Color.BLACK);
                    y.setColor(Node.Color.BLACK);
                    z.getParent().getParent().setColor(Node.Color.RED);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getRight()) {
                        //case 2
                        z = z.getParent();
                        rotateLeft(z);
                    }
                    //case 3 (uncle is black, z is left child)
                    z.getParent().setColor(Node.Color.BLACK);
                    z.getParent().getParent().setColor(Node.Color.RED);
                    rotateRight(z.getParent().getParent());
                }
            } else {
                y = z.getParent().getParent().getLeft();
                if (y.getColor() == Node.Color.RED) {
                    z.getParent().setColor(Node.Color.BLACK);
                    y.setColor(Node.Color.BLACK);
                    z.getParent().getParent().setColor(Node.Color.RED);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getLeft()) {
                        z = z.getParent();
                        rotateRight(z);
                    }

                    z.getParent().setColor(Node.Color.BLACK);
                    z.getParent().getParent().setColor(Node.Color.RED);
                    rotateLeft(z.getParent().getParent());
                }
            }
        }
        this.root.setColor(Node.Color.BLACK);
    }

    public void printTree(Node node) {
        if (node != nil) {
            printTree(node.getLeft());
            System.out.println(node.getKey() + "" + node.getColor());
            printTree(node.getRight());
        }

    }

    private void transplant(Node u, Node v) {
        if (u.getParent() == nil)
            this.root = v;
        else if (u == u.getParent().getLeft())
            u.getParent().setLeft(v);
        else u.getParent().setRight(v);

        v.setParent(u.getParent());
    }

    public boolean deleteNode(String key) {

        Node z = search(key);
        if(z == nil)
            return false;

        Node y = z;
        Node x;
        Node.Color yOriginalColor = y.getColor();

        if(z.getLeft() == nil) {
            x = z.getRight();
            transplant(z, z.getRight());
        } else if(z.getRight() == nil) {
            x = z.getLeft();
            transplant(z, z.getLeft());
        } else {
            y = findMinimum(z.getRight());
            yOriginalColor = y.getColor();
            x = y.getRight();
            if(y.getParent() == z)
                x.setParent(y);
            else {
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setColor(z.getColor());
        }

        if(yOriginalColor == Node.Color.BLACK)
            deleteFixUp(x);

        setTreeSize(getTreeSize() - 1);

        return true;
    }

    private void deleteFixUp(Node x) {
        Node sibling;

        while(x != this.getRoot() && x.getColor() == Node.Color.BLACK)
        {
            if(x == x.getParent().getLeft()) {
                sibling = x.getParent().getRight();
                //case 1: sibling is red
                if(sibling.getColor() == Node.Color.RED) {
                    sibling.setColor(Node.Color.BLACK);
                    x.getParent().setColor(Node.Color.RED);
                    rotateLeft(x.getParent());
                    sibling = x.getParent().getRight();
                }
                //case 2: sibling is black, both sibling's children black
                if(sibling.getLeft().getColor() == Node.Color.BLACK && sibling.getRight().getColor() == Node.Color.BLACK) {
                    sibling.setColor(Node.Color.RED);
                    x = x.getParent();
                } else {
                    //case 3: sibling is black, sibling's left child is red, right child is black
                    if(sibling.getRight().getColor() == Node.Color.BLACK) {
                        sibling.setColor(Node.Color.RED);
                        sibling.getLeft().setColor(Node.Color.BLACK);
                        rotateRight(sibling);
                    }
                    //case 4: sibling is black, sibling's left child is black, right child is red
                    sibling.setColor(x.getParent().getColor());
                    x.getParent().setColor(Node.Color.BLACK);
                    sibling.getRight().setColor(Node.Color.BLACK);
                    rotateLeft(x.getParent());
                    x = this.getRoot();
                }
            } else {
                sibling = x.getParent().getLeft();
                //case 1
                if(sibling.getColor() == Node.Color.RED) {
                    sibling.setColor(Node.Color.BLACK);
                    x.getParent().setColor(Node.Color.RED);
                    rotateRight(x.getParent());
                    sibling = x.getParent().getLeft();
                }
                //case 2
                if(sibling.getLeft().getColor() == Node.Color.BLACK && sibling.getRight().getColor() == Node.Color.BLACK) {
                    sibling.setColor(Node.Color.RED);
                    x = x.getParent();
                } else {
                    //case 3
                    if(sibling.getLeft().getColor() == Node.Color.BLACK) {
                        sibling.setColor(Node.Color.RED);
                        sibling.getRight().setColor(Node.Color.BLACK);
                        rotateLeft(sibling);
                    }
                    //case 4
                    sibling.setColor(x.getParent().getColor());
                    x.getParent().setColor(Node.Color.BLACK);
                    sibling.getLeft().setColor(Node.Color.BLACK);
                    rotateRight(x.getParent());
                    x = this.getRoot();
                }
            }
        }
        x.setColor(Node.Color.BLACK);
    }

    private Node findMinimum(Node node) {

        while(node.getLeft() != nil)
            node = node.getLeft();

        return node;
    }

    public Node search(String key) {
        Node node = this.getRoot();
        while(node != nil)
        {
            if(key.compareTo(node.getKey()) < 0)
                node = node.getLeft();
            else if(key.compareTo(node.getKey()) > 0)
                node = node.getRight();
            else
                return node;
        }
        return nil;
    }

    public Node getRoot() {
        return root;
    }


    int height(Node root)
    {
        if (root == nil)
            return 0;
        else
        {
            /* compute  height of each subtree */
            int leftHeight = height(root.getLeft());
            int rightHeight = height(root.getRight());

            /* use the larger one */
            if (leftHeight > rightHeight)
                return(leftHeight + 1);
            else return(rightHeight + 1);
        }
    }

    void printGivenLevel (Node root ,int level)
    {
        if (root == nil)
            return;
        if (level == 1)
            System.out.print(root.getKey() + " ");
        else if (level > 1)
        {
            printGivenLevel(root.getLeft(), level-1);
            printGivenLevel(root.getRight(), level-1);
        }
    }

    public int getTreeHeight() {
        return height(this.root);
    }


    public Node getNil() {
        return nil;
    }
    public int getTreeSize() {
        return treeSize;
    }

    public void setTreeSize(int treeSize) {
        this.treeSize = treeSize;
    }

}




