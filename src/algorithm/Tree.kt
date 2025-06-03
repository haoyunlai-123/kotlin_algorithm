package algorithm

import com.sun.source.tree.Tree
import kotlin.math.max
import kotlin.math.min

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

val list = mutableListOf<Int>()
// 144
fun preorderTraversal(root: TreeNode?): List<Int> {
    if(root == null) return list
    list.add(root.`val`)
    preorderTraversal(root.left)
    preorderTraversal(root.right)
    return list
}

// 94
fun inorderTraversal(root: TreeNode?): List<Int> {
    if(root == null) return list
    preorderTraversal(root.left)
    list.add(root.`val`)
    preorderTraversal(root.right)
    return list
}

// 145
fun postorderTraversal(root: TreeNode?): List<Int> {
    if(root == null) return list
    postorderTraversal(root.left)
    postorderTraversal(root.right)
    list.add(root.`val`)
    return list
}

// 872
fun leafSimilar(root1: TreeNode?, root2: TreeNode?): Boolean =
    mutableListOf<Int>().also { judge872(root1,it) } == mutableListOf<Int>().also { judge872(root2,it) }

fun judge872(root: TreeNode?, list: MutableList<Int>): Unit {
    when {
        root == null -> return
        root?.left == null && root?.right == null -> list.add(root!!.`val`)
        else -> { judge872(root.left,list); judge872(root.right,list) }
    }
}

// 栈模拟中序遍历二叉树
fun judge1872(root: TreeNode?, list: MutableList<Int>): Unit{
    var root1 = root
    val stack = ArrayDeque<TreeNode?>()
    while(root1 != null || stack.isNotEmpty()){
        while(root1 != null){
            stack.addFirst(root1)
            root1 = root1.left
        }
        val p = stack.removeFirst()
        if(p?.left == null && p?.right == null) list.add(p!!.`val`)
        root1 = p.right
    }
}

// 栈模拟后序遍历二叉树
fun postorder(root: TreeNode?): MutableList<Int> {
    val res = mutableListOf<Int>()
    var root1 = root
    val stack = ArrayDeque<TreeNode?>()
    var flag: TreeNode? = null
    while(root1 != null && stack.isNotEmpty()) {
        while(root1 != null){
            stack.addFirst(root1)
            root1 = root1.left
        }
        val node = stack.first()
        when {
            node?.right != null && node.right != flag -> { root1 = root1?.right }
            else -> { res.add(node!!.`val`); flag = stack.removeFirst() }
        }
    }
    return res
}


class Solution {

    val set = mutableSetOf<Int>()

    // LCP 44
    fun numColor(root: TreeNode?): Int {
        preOrder(root)
        return set.size
    }

    fun preOrder(root: TreeNode?): Unit {
        root?.also {
            set.add(root.`val`)
            preOrder(it.left)
            preOrder(it.right)
        }
    }
}

// 404
fun sumOfLeftLeaves(root: TreeNode?): Int {
    when {
        root == null -> return 0
        else -> {
            var sum = 0
            root.left?.also {
                 when {
                     root.left?.left == null && root.left?.right == null -> sum += root.left!!.`val`
                     else -> sum = sumOfLeftLeaves(root.left)
                 }
            }
            sum += sumOfLeftLeaves(root.right)
            return sum
        }
    }
}


class Solution1 {

    var ans = -1
    // 671
    fun findSecondMinimumValue(root: TreeNode?): Int {
        preOrder671(root,root!!.`val`)
        return ans
    }

    fun preOrder671(root: TreeNode?, cur: Int): Unit {
        var cur1 = cur
        if (root == null) return
        if (cur1 != root.`val`) {
            if (ans == -1) ans = root.`val`
            else ans = min(ans, root.`val`)
            return
        }
        preOrder671(root?.left, cur1)
        preOrder671(root?.right, cur1)
    }
}

// 104
fun maxDepth(root: TreeNode?): Int {
    if(root == null) return 0
    val left = maxDepth(root?.left)
    val right = maxDepth(root?.right)
    return max(left,right) + 1
}

// 111
fun minDepth(root: TreeNode?): Int {
    when {
        root == null -> return 0
        else -> {
            when {
                root.left == null && root.right != null -> return minDepth(root.right) + 1
                root.right == null && root.left != null -> return minDepth(root.left) + 1
                root.left != null && root.right != null -> return min(minDepth(root.left),minDepth(root.right)) + 1
                else -> return 1
            }
        }
    }
}

// 112
fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean = dfs112(root,0,targetSum)

fun dfs112(root: TreeNode?, sum: Int,target: Int): Boolean {
    if(root == null) return false
    var sum1 = sum; sum1 += root!!.`val`
    when {
        root?.left == null && root?.right == null -> { return if(sum1 == target) true else false }
        else -> return dfs112(root.left,sum1,target) || dfs112(root.right,sum1,target)
    }
}


class Solution129 {
    var ans = 0
    // 129
    fun sumNumbers(root: TreeNode?): Int {
        dfs129(root,0)
        return ans
    }

    fun dfs129(root: TreeNode?, sum: Int): Unit {
        if(root == null) return
        val sum1 = sum * 10 + root.`val`
        when {
            root.left == null && root.right == null -> { ans += sum1; return }
            else -> { dfs129(root.left,sum1); dfs129(root.right,sum1) }
        }
    }
}