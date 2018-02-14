# 剑指offer面试题55--二叉树的深度

>   ```
>   输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。
>   ```

## 递归版本

很容易想到使用递归，根结点处的深度为1，既然要求树的最长路径，必然从根结点的左右子树中选出深度更大的那棵子树，也就是整棵树的深度为

$$depth(root) = max[depth(root.left), depth(root.right)] + 1$$

加1是因为要加上树的根结点。那么对于每棵子树，也要按照这样的规则——挑选出深度更大的子树并加上1，也就得到了以当前结点为根结点的二叉树的深度。这是个递归结构。

```java
/**
 * 递归版本
 */
public int TreeDepth(TreeNode root) {
    if (root == null) return 0;
    int left = TreeDepth(root.left);
    int right = TreeDepth(root.right);
    return Math.max(left, right) + 1;
}
```

当递归自上而下深入到叶子结点的孩子结点（null）时，返回0，从而会得到叶子结点的深度为1，就这样自底向上返回，每次回到上一层都是下一层的最大深度加上1，直到根结点......

这个代码也可以看成是后序遍历，先访问了父结点的左右子结点，从中选出深度更大着，然后回到父结点时加1（也就是算上父结点后深度会增加1）。

## 非递归版本

求深度，其实就是求这棵二叉树有多少层。于是采用BFS的层序遍历。关键就是怎么知道什么时候处理完了二叉树的一层？我们来模拟一下：

就假设这是棵满二叉树吧，根结点先入队列，此时队列中结点个数为1，接着会弹出这唯一的根结点，同时入列两个结点，此时第一层处理完毕；

现在队列中结点个数为2，我们出列两次，4个结点又会入列，此时第二层处理完毕；

现在队列中结点个数为4，我们出列4次，8个结点又会入列，此时第三层处理完毕....

发现规律了吗？**每次要出列前，先得到队列中现有的结点个数，假设是m，那么就在循环内出列m次，随即跳出循环，这样就算处理完一行了。**跳出循环后只需要将深度自增，最后层序遍历完毕也就得到了二叉树的深度。

```java
/**
 * 非递归，层序遍历
 */
public int depth(TreeNode root) {
    if (root == null) return 0;
    Queue<TreeNode> queue = new LinkedList<>();
    int depth = 0;
    queue.offer(root);
    while (!queue.isEmpty()) {
      	int layerSize = queue.size();
      	for (int i = 0; i < layerSize; i++) {
      		TreeNode node = queue.poll();
        	if (node.left != null) queue.offer(node.left);
        	if ((node.right) != null) queue.offer(node.right);
    	}
    	depth++;
  	}
  	return depth;
}
```

## 平衡二叉树

>   ```
>   输入一棵二叉树，判断该二叉树是否是平衡二叉树。
>   ```

本题应该是假定输入的已经是二叉搜索树，因为平衡二叉树首先是一颗二叉搜索树。平衡二叉树的定义是：对于任意结点，其左右子树的深度相差不超过1。

### 利用上题的函数

有了上面求二叉树深度的基础，这道题就很容易写出如下的递归解法。

先从根结点开始求得其左右子树的深度，然后做差，如果深度差超过1，那这不是棵平衡二叉树；切不可以为根结点的左右子树深度差不超过1，下面的子树深度差就不会超过1了，画几幅反例就知道了。所以递归地对左右子树也做同样的判断是必须的，以确保每个以结点为根结点其左右子树的深度差不超过1。

```java
package Chap6;

public class BalancedTree {
    /**
     * 方法1：递归地求每个结点的左右子树深度差，有重复计算
     */
    public boolean isBalancedTree(TreeNode root) {
        if (root == null) return true;
        int left = depth(root.left);
        int right = depth(root.right);
        if (Math.abs(left - right) > 1) return false;
        return isBalancedTree(root.left) && isBalancedTree(root.right);
    }

    private int depth(TreeNode root) {
        if (root == null) return 0;
        int left = depth(root.left);
        int right = depth(root.right);
        return Math.max(left, right) + 1;
    }
}

```

这种方法有个缺点，多个结点会被重复访问。

### 修改求二叉树深度的方法

仍然是先求得左右子树的深度，如果做差不超过1，就正常返回深度；如果超过了1就说明这不是棵平衡二叉树了，于是不断返回-1，直到根结点。如果不是平衡二叉树，最后会得到该二叉树的深度为-1，所以只需判断一棵二叉树的深度是不是大于等于0即可。

```java
/**
 * 方法2：修改求二叉树深度的方法：
 * 只要有某个结点不平衡，将一直返回-1直到root；否则就正常返回树的深度
 */
public boolean isBalanced(TreeNode root) {
  return depth2(root) >= 0;
}

private int depth2(TreeNode root) {
    if (root == null) return 0;
    int left = depth2(root.left);
    int right = depth2(root.right);
    return left >= 0 && right >= 0 && Math.abs(left - right) <= 1 ? Math.max(left, right) + 1 : -1;
}
```

不得不说这是个很巧秒的方法，推荐。

### 书上的方法--后序遍历

之所以使用后续遍历，是因为后续遍历先访问父结点的左右子结点后，再访问父结点的，因此这种遍历顺序可以先得到左右子结点深度之差，然后再回到当前父结点求得父结点的深度...就这样自底向上判断每个结点的左右子树深度差，有某一个结点不平衡都将导致不断返回false。

需要一个指针记录当前结点的深度，而int型不是对象只能传值，为了传引用那就建立一个长度为1的对象数组吧，和前面说过的一样，当到达叶子结点的做右子结点（null）时深度为0，那么返回到叶子结点深度为1 ...

```java
package Chap6;

public class BalancedTree {
    /**
     * 方法3：后序遍历，为了传引用使用了对象数组
     */
    public boolean IsBalanced_Solution(TreeNode root) {
        return isBalance(root, new int[1]);
    }

    public boolean isBalance(TreeNode root, int[] depth) {
        if (root == null) {
            depth[0] = 0;
            return true;
        }
        boolean left = isBalance(root.left, depth);
        // 左子树的深度
        int leftDepth = depth[0];
        // 右子树的深度
        boolean right = isBalance(root.right, depth);
        int rightDepth = depth[0];
        // 当前结点的深度
        depth[0] = Math.max(leftDepth + 1, rightDepth + 1);
        if (left && right && Math.abs(leftDepth - rightDepth) <= 1) return true;
        return false;
    }
}

```

---

by @sunhaiyu

2018.1.30
