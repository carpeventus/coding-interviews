# 剑指offer面试题37--序列化二叉树

>   请实现两个函数，分别用来序列化和反序列化二叉树。

刚开始想法太死板了，只记得中序和前序或者中序和后续两个序列才能决定一棵唯一的二叉树，于是分别进行了前序、中序遍历，前序和中序的序列用"|"分隔，之后再根据这个分隔符分成前序和中序序列，最后采用面试题7——重建二叉树的思路进行反序列化。思路是正确的但是太麻烦。

其实遇到空指针可以也用一个特殊的字符表示，比如“#”，**这样前序遍历序列就可以表示唯一的一棵二叉树了。**对于空指针也用一个字符表示，可称这样的序列为**扩展序列**。而二叉树的建立，必须先要建立根结点再建立左右子树（root为空怎么调用root.left是吧），所以必须前序建立二叉树，那么序列化时也应该用前序遍历，保证了根结点在序列前面。

不能使用中序遍历，因为**中序扩展序列**是一个无效的序列，比如

```
   A      B
  / \      \
 B   C  和  A  中序扩展序列都是 #B#A#C#
             \
              C
```

先来看序列化的代码，其实就是在前序遍历的基础上，如果遇到空指针就用“#”表示。

```java
package Chap4;

public class SerializeBT {
    // 结点值用String[] seq保存，index是seq的索引
    private int index = -1;

    public String serialize(TreeNode root) {
        if (root == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        return sb.toString();

    }
	// 前序遍历
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

```

再来看反序列化，通过前序遍历得到的字符串，重建二叉树。

```java
package Chap4;

public class SerializeBT {
    // 结点值用String[] seq保存，index是seq的索引
    private int index = -1;

    public TreeNode deserialize(String str) {
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

}

```

由于前序遍历时每存入一个结点值或者存入“#”后面都紧跟着一个空格。所以最后得到的序列时这样的格式`A B # # C # #`，可以根据空格将其分割成`[A, B, #, #, C, #, #]`这样就还原了各个结点的值，根据这些值重建二叉树。由于得到的是二叉树的前序序列，因此也要以前序重建二叉树，当遇到结点值是“#”时说明这是一个空指针，那么返回null给上层的父结点。如果不为“#”就递归地重建该结点的左右子树。注意这里使用了一个int型的index，用于表示当前结点在String[] seq中的索引，无需担心index在seq中会造成数组下标越界，因为最后一个结点的左右子树肯定是null，必然会终止递归。

---

by @sunhaiyu

2018.1.15