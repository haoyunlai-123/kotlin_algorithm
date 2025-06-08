import com.sun.source.tree.Tree
import kotlin.math.max
import kotlin.math.min

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

// 199
fun rightSideView(root: TreeNode?): List<Int> = mutableListOf<Int>().also { list -> dfs199(root,1,list) }

fun dfs199(root: TreeNode?, depth: Int, list: MutableList<Int>): Unit {
    when {
        root == null -> return
        depth != list.size -> list.add(root.`val`)
    }
    dfs199(root.right, depth + 1, list); dfs199(root.left, depth + 1, list)
}

class Solution1448 {
    var ans = 0
    // 1448
    fun goodNodes(root: TreeNode?): Int {
        dfs1448(root,Int.MIN_VALUE)
        return ans
    }

    // 不能剪枝
    fun dfs1448(root: TreeNode?,max: Int): Unit {
        if(root == null) return
        var max1 = max
        if(root.`val` >= max1){
            ans++
            max1 = root.`val`
        }
        dfs1448(root.left,max1)
        dfs1448(root.right,max1)
    }
}

class Solution1457 {
    var ans = 0
    // 1457
    fun pseudoPalindromicPaths(root: TreeNode?): Int {
        dfs1457(root,mutableSetOf<Int>())
        return ans
    }

    fun dfs1457(root: TreeNode?, set: MutableSet<Int>): Unit {
        if (root == null) return
        when {
            set.contains(root.`val`) -> set.remove(root.`val`)
            else -> set.add(root.`val`)
        }
        if (root?.left == null && root?.right == null) {
            if (set.size == 1)
                ans++
        }
        dfs1457(root.left,set)
        dfs1457(root.right,set)
        if(set.contains(root.`val`))
            set.remove(root.`val`)
    }
}

fun pseudoPalindromicPaths(root: TreeNode?): Int {
    fun dfs(root: TreeNode?, set: MutableSet<Int>): Int {
        root ?: return 0
        val newSet = set.toMutableSet().also { newSet ->
            if(!newSet.remove(root.`val`))
                newSet.add(root.`val`)
        }
        return when {
            root.left == null && root.right == null -> if(set.size <= 1) 1 else 0
            else -> dfs(root.left,newSet) + dfs(root.right,newSet)
        }
    }
    return dfs(root,mutableSetOf<Int>())
}

class Solution1315 {
    val list = mutableListOf<Int>()
    var ans = 0
    // 1315
    fun sumEvenGrandparent(root: TreeNode?): Int {
        fun dfs(root: TreeNode?): Unit {
            root ?: return
            list.add(root.`val`)
            val idx = list.lastIndex - 2
            if(idx >= list.indexOf(list.first()) && list[idx] %2 == 0)
                ans += root.`val`
            dfs(root.left)
            dfs(root.right)
            list.remove(root.`val`)
        }
        dfs(root)
        return ans
    }
}

class Solution988 {

    var ans = "~"

    fun smallestFromLeaf(root: TreeNode?): String {
        dfs988(root, StringBuilder())
        return ans
    }

    fun dfs988(root: TreeNode?, s: StringBuilder): Unit {
        if (root == null) return
        s.append('a' + root.`val`)
        when {
            root.left == null && root.right == null -> {
                val s1 = s.toString().reversed()
                if (s1 < ans) ans = s1
            }
            else -> { dfs988(root.left, s); dfs988(root.right, s); s.deleteAt(s.length-1) }
        }
    }
}

class Solution1026 {

    var ans = 0

    fun maxAncestorDiff(root: TreeNode?): Int {
        dfs1026(root,root!!.`val`,root.`val`)
        return ans
    }

    fun dfs1026(root: TreeNode?, mn: Int, mx: Int): Unit {
        if (root == null) return
        val mn1 = min(mn,root.`val`)
        val mx1 = max(mx,root.`val`)
        ans = max(ans,max(root.`val` - mn1,mx1 - root.`val`))
        dfs1026(root.left,mn1,mx1)
        dfs1026(root.right,mn1,mx1)
    }
}