package Chap4;

/**
 * 请实现两个函数，分别用来序列化和反序列化二叉树
 */
public class SerializeBT {

    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    /*
     * 刚开始想法太死板了，只记得中序和前序两个序列才能决定一棵唯一的二叉树，于是分别进行了前序、中序遍历，中序和前序的序列用"|"分隔，之后根据分隔符分成前序和中序序列，最后采用面试题7————重建二叉树的思路进行反序列化
     * 其实遇到空指针可以也用一个字符表示，比如#，这样前序遍历序列就可以表示唯一的一棵二叉树了。
     * 而二叉树的建立，必须先要建立根结点再建立左右子树（root为空怎么调用root.left是吧），所以必须前序建立二叉树，那么序列化时也应该用前序遍历，保证了根结点在序列前面
     *
     * 不能使用中序遍历，因为中序扩展序列是一个无效的序列，比如
     *   A      B
     *  / \      \
     * B   C  和  A  中序扩展序列都是 #B#A#C#
     *            \
     *             C
     */

    // 结点值用String[] seq保存，index是seq的索引
    private int index = -1;

    public String Serialize(TreeNode root) {
        if (root == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        return sb.toString();

    }

    public TreeNode Deserialize(String str) {
        if (str == null) {
            return null;
        }

        String[] seq = str.split("\\s");

        return reconstructBST(seq);

    }

    private TreeNode reconstructBST(String[] seq) {
        ++index;
        if (seq[index].equals("#")) return null;

        TreeNode root = new TreeNode(Integer.parseInt(seq[index]));
        root.left = reconstructBST(seq);
        root.right = reconstructBST(seq);
        return root;
    }

    private void preOrder(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("# ");
            return;
        }
        sb.append(node.val).append(" ");
        preOrder(node.left, sb);
        preOrder(node.right, sb);
    }

}
