# 剑指offer面试题36--二叉搜索树与双向链表

> ```
> 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。
> ```

看到这道题我第一反映就是，二叉树的线索化，不过还是有些区别的，下面会讨论。按照二叉搜索树的特点，最左边的结点是值最小的，而题目要求得到排序的双向链表，所以基本确定下来**中序遍历**的方法。

二叉树的线索化：是针对每个叶则结点，为了将空指针利用起来，可以将叶子结点的左子结点指向其遍历顺序的前驱，右子结点指向遍历序列的后继。根据遍历顺序的不同，线索化也分为前序、中序、后序。二叉树的结点定义中需要加入布尔变量，用来标示每个结点的左右指针是否被线索化了（这些标志只可能在叶子结点为true）

```java
public static class Node<T> {
    private T data;
    private Node<T> lchild;
    private Node<T> rchild;
    private boolean isleftThread;
    private boolean isRightThread;

  	public Node(T data) {
    	this.data = data;
    	isleftThread = false;
    	isRightThread = false;
  	}
}

// 当前访问结点的前一个结点
private Node<Item> preNode;

public void inOrderThread(Node<Item> node) {
  	if (node == null) {
    	return;
  	}
  	inOrderThread(node.lchild);

  	if (node.lchild == null) {
    	node.lchild = preNode;
    	node.isleftThread = true;
  	}

  	if (preNode != null && preNode.rchild == null) {
    	preNode.rchild = node;
    	preNode.isRightThread = true;
  	}
  	// preNode始终表示上一个访问的结点
  	preNode = node;
  	inOrderThread(node.rchild);
}

public void inOrderThread() {
  inOrderThread(root);
}
```

而本题要实现双向链表，不光是叶子结点，所有结点的左右指针都要重新设置。因此将上面的限制条件（左右子结点为空才线索化）去掉，就得到本题的答案！

```java
package Chap4;

public class BSTTransToDLinkedList {
    // 当前结点的前驱
    private TreeNode preNode;

    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }

        TreeNode root = pRootOfTree;
        // 得到双向链表
        inOrder(root);
        // 向左找到双向链表的头结点
        while (root.left != null) {
            root = root.left;
        }
        return root;

    }

    // 中序遍历并改变指针
    private void inOrder(TreeNode node) {
        if (node == null) return;

        inOrder(node.left);

        node.left = preNode;
        if (preNode != null) {
            preNode.right = node;
        }
        preNode = node;

        inOrder(node.right);
    }
}

```

最后因为需要返回双向链表的头结点，只需从二叉树的根结点开始，向左遍历，即可找到双向链表的头结点。

---

by @sunhaiyu

2018.1.11
