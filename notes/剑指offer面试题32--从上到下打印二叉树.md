# 剑指offer面试题32--从上到下打印二叉树

> ```
> 不分行，从上往下打印出二叉树的每个节点，同层节点从左至右打印。即层序遍历
> ```

## 不分行，层序遍历

二叉树的层序遍历其实就是广度优先搜索的简单版。使用队列实现。

```java
package Chap4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeLevelOrder {
    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();

        if (root == null) {
            return list;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node.val);

            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        return list;
    }
}

```

先将根结点入列，出列并打印然后按照从左到右的顺序依次将该结点的子结点入列....不断重复这个过程直到队列为空。

## 分行打印

和上面类似，现在要求每打印完树的一层需要换行。核心代码其实和上面一样。只是为了确定在何时需要换行操作，**需要用两个变量记录当前层还没有被打印的结点数、下层总结点数。每打印完一行后需要换行，接下来要打印下一层了，所以用下层总结点数更新当前层未被打印的结点数，同时下层总结点数重置为0，准备进行下一层的计数。**

```java
package Chap4;

import java.util.LinkedList;
import java.util.Queue;

public class PrintTreeEveryLayer {
    public void printEveryLayer(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        // 下一层的结点数
        int nextLevel = 0;
        // 本层还有多少结点未打印,因为目前只有根结点没被打印所以初始化为1
        int toBePrinted = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            // 每打印一个，减1
            toBePrinted--;

            if (node.left != null) {
                queue.offer(node.left);
              	// 每一个元素加入队列，加1
                nextLevel++;
            }

            if (node.right != null) {
                queue.offer(node.right);
                nextLevel++;
            }
		   // 本层打印完毕
            if (toBePrinted == 0) {
                System.out.println();
                toBePrinted = nextLevel;
                nextLevel = 0;
            }
        }
    }

}

```

其实还可以更直观的分层打印, queue.size()就是每层的结点个数。

```java
// 也是分行打印，比上面简洁
public void printEveryLayer(TreeNode root) {
  	if (root == null) return;
  	Queue<TreeNode> queue = new LinkedList<>();
  	queue.offer(root);
  	while (!queue.isEmpty()) {
    	int layerSize = queue.size();
    	for (int i = 0; i < layerSize; i++) {
      		TreeNode node = queue.poll();
      		System.out.println(node.val+" ");
      		if (node.left != null) queue.offer(node.left);
      		if (node.right != null) queue.offer(node.right);
    	}
      	System.out.println();
  	}
}
```

## Z字形打印二叉树

举个例子，下面的二叉树，打印顺序是1 3 2 4 5 6 7

```
   	   1
  	 /   \
    2      3
   / \    / \
  4   5  6   7
```
先搞清楚要求：根结点先被打印，然后从右往左打印第二行，接着从左往右打印第三行...以此类推，**总之偶数层就从右往左打印，奇数行就从左到右打印**。依然需要某种数据结构来存放结点，栈可以满足我们的打印顺序：当前层为奇数层时，按照**从左到右**的顺序将下层结点（偶数层）压入栈中，出栈的时候就是从右往左打印偶数层了；当前层是偶数层时，按照**从右到左**的顺序将下层结点（奇数层）压入栈中，由于此时先出栈的是偶数层最右边的结点，所以可以保证下层最右边的结点被压到了栈底，而最左边的结点位于栈顶，出栈的时候就是从左往右打印奇数层了...如此反复交替。

为了达到上述的交替效果，需要用到**两个栈，一个栈stackOdd存奇数层的结点，另一个栈stackEven存偶数层的结点。**

- 奇数层，其下层的结点按左到右的顺序入栈
- 偶数层，其下层的结点按右到左的顺序入栈

奇偶层顺序是固定的，即根结点是奇数层，则奇偶顺序是“奇偶奇偶....“

stackOdd存放的是某一奇数层的全部结点，**stackOdd不为空说明当前层是奇数层，全部弹出后为空，该处理下一层了;因此当stackOdd为空时当前层必然是偶数层**，stackOdd就这样不断为空，不为空...交替，正好反映了当前层是奇数层还是偶数层，进而采取不同的结点存入顺序即可。

```java
package Chap4;

import java.util.ArrayList;
import java.util.LinkedList;

public class PrintTreeZ {
    public void printTreeZ(TreeNode root) {
        if (root == null) return;
        LinkedList<TreeNode> stackOdd = new LinkedList<>();
        LinkedList<TreeNode> stackEven = new LinkedList<>();
        stackOdd.push(root);
        // 只要还有一个栈不为空就继续
        while (!stackOdd.isEmpty() || !stackEven.isEmpty()) {
            if (!stackOdd.isEmpty()) {
                while (!stackOdd.isEmpty()) {
                    TreeNode node = stackOdd.pop();
                    System.out.println((node.val + " "));
                    if (node.left != null) stackEven.push(node.left);
                    if (node.right != null) stackEven.push(node.right);
                }
            } else {
                while (!stackEven.isEmpty()) {
                    TreeNode node = stackEven.pop();
                    System.out.println((node.val + " "));
                    if (node.right != null) stackOdd.push(node.right);
                    if (node.left != null) stackOdd.push(node.left);
                }
            }
            System.out.println();
        }
    }
}
```

---

by @sunhaiyu

2017.1.6
