package gfg.ThreadedBST;


import java.util.Scanner;

public class MorrisTraversal {
    public static void main(String[] args) {
        System.out.println("Enter number of elements to create BST");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println("Enter elements in one line separated by spaces and hit enter to insert");
        TBSTNode root = new TBSTNode(null, null, sc.nextInt());
        while (--n > 0) {
            insert(root, sc.nextInt());
        }
        System.out.println(" BST created and root is having root " + root.data);
        inOrderTraverse(root);
        System.out.println("Now doing morris traversal");
        morrisTraverse(root);
        System.out.println("Now doing inorder traversal");
        inOrderTraverse(root);
    }

    private static void morrisTraverse(TBSTNode root) {
        TBSTNode current = root;
        if (current.left == null) {
            System.out.println(" " + current.data);
            current = current.right;
        } else {
            while (current != null) {
                //System.out.println("Outer loop "+current.data);
                while (current!=null && current.left != null) {
                    //System.out.println("Middle loop : came to left "+(current.left).data);
                    boolean decisionMade = false;
                    TBSTNode leftSubstree = current.left;
                    while (leftSubstree.right != null) {
                        if (leftSubstree.right == current) {
                            /*
                             * Thread has already been created, so it means the traversal would have completed
                             * so now shift current to its right subtree for further traversal
                             * also we can break the loop now, as no more required
                             * */
                            leftSubstree.right = null;
                            System.out.println(" "+current.data);
                            current = current.right;
                            //System.out.println("Broke Thread from "+leftSubstree.data + " to "+current.data);
                            decisionMade = true;
                            break;
                        }
                        leftSubstree = leftSubstree.right;
                    }
                    if (!decisionMade) {
                        leftSubstree.right = current;
                        //System.out.println("Made Thread from "+leftSubstree.data +" to "+current.data);
                        current = current.left;
                    }
                }
                if(current!=null) {
                    System.out.println(" " + current.data);
                    current = current.right;
                }else
                    break;
            }
        }
    }

    private static TBSTNode insert(TBSTNode root, int data) {
        if (root == null) {
            return new TBSTNode(null, null, data);
        } else if (data > root.data) {
            root.right = insert(root.right, data);
        } else {
            root.left = insert(root.left, data);
        }
        return root;
    }

    private static void inOrderTraverse(TBSTNode root) {
        if (root == null)
            return;
        else {
            inOrderTraverse(root.left);
            System.out.println(" " + root.data);
            inOrderTraverse(root.right);
        }
    }
}

class TBSTNode {
    TBSTNode left;
    TBSTNode right;
    int data;

    public TBSTNode(TBSTNode left, TBSTNode right, int data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }
}
