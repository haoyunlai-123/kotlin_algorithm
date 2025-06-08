import java.util.TreeSet
import kotlin.collections.set
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

// 2841
fun maxSum(nums: List<Int>, m: Int, k: Int): Long {
    val set = mutableSetOf<Int>()
    var ans = 0L
    var sum = 0L
    for ((idx,num) in nums.withIndex()) {
        set.add(num)
        sum += num
        if(idx < k - 1) continue
        if(set.size >= m) ans = max(ans,sum)
        set.remove(nums[idx - k + 1])
        sum -= num
    }
    return ans
}

//  1984
fun minimumDifference(nums: IntArray, k: Int): Int {
    nums.sort()
    var ans = nums[k - 1] - nums[0]
    for(i in k until nums.size) {
        ans = min(ans,nums[i] - nums[i - k + 1])
    }
    return ans
}

// 1461
fun hasAllCodes(s: String, k: Int): Boolean {
    val n = 2.0.pow(k.toDouble())
    val hash = mutableMapOf<String,Int>()
    s.indices.forEach{ i ->
        if(i < k - 1) return@forEach
        val a = s.substring(i - k + 1,i)
        hash[a] = (hash[a] ?:0) + 1
    }
    return hash.size == n.toInt()
}
fun hasAllCodes1(s: String, k: Int): Boolean = mutableMapOf<String,Int>().run {
    s.indices.forEach{ i ->
        if(i < k - 1) return@forEach
        val a = s.substring(i - k + 1,i)
        this[a] = (this[a] ?:0) + 1
    }
    size == 2.0.pow(k.toDouble()).toInt()
}

// 220
fun containsNearbyAlmostDuplicate(nums: IntArray, indexDiff: Int, valueDiff: Int): Boolean {
    val treeSet = TreeSet<Long>()
    for((i,num) in nums.withIndex()) {
        val n = num.toLong()
        val a = treeSet.ceiling(n - valueDiff)
        if(a != null && a <= n + valueDiff)
            return true
        treeSet.add(n)
        if(i >= indexDiff)
        treeSet.remove(nums[i - indexDiff].toLong())
    }
    return false
}

// 563
fun findTilt(root: TreeNode?): Int {
    var ans = 0
    fun dfs(root: TreeNode?): Int {
        root ?: return 0
        val a = dfs(root.left)
        val b = dfs(root.right)
        val sum = root.`val` + a + b
        ans += abs(a - b)
        return sum
    }
    dfs(root)
    return ans
}

// 606
fun tree2str(root: TreeNode?): String {
    val s = StringBuilder()
    s.append(root?.`val`.toString())
    fun dfs(root: TreeNode?): Unit {
        root ?: return
        s.append("(")
        s.append(root.`val`.toString())
        dfs(root.left)
        dfs(root.right)
        s.append(")")
    }
    dfs(root?.left)
    dfs(root?.right)
    return s.toString()
}

// 3090
fun maximumLengthSubstring(s: String): Int {
    var ans = 0
    var left = 0
    val hash = mutableMapOf<Char, Int>()
    for ((right, ch) in s.withIndex()) {
        hash[ch] = (hash[ch] ?: 0) + 1
        while (hash[ch]!! > 2) {
            hash[s[left]] = hash[s[left]]!! - 1
            left++
        }
        ans = max(ans,right - left + 1)
    }
    return ans
}