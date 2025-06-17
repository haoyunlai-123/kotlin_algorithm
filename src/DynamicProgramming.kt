import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sign

// 494
fun findTargetSumWays(nums: IntArray, target: Int): Int {
    var sum = nums.sum()
    if ((sum + target) % 2 != 0 || abs(target) > sum) return 0
    sum = (sum + target) / 2
    val dp = IntArray(sum + 1)
    dp[0] = 1
    for (num in nums)
        for(n in sum downTo num)
            dp[n] += dp[n - num]
    return dp[sum]
}

// 416
fun canPartition(nums: IntArray): Boolean {
    var sums = nums.sum()
    if (sums % 2 != 0) return false
    sums /= 2
    val dp = BooleanArray(sums + 1)
    dp[0] = true
    for (num in nums)
        for (n in sums downTo num)
            dp[n] = dp[n] || dp[n - num]
    return dp[sums]
}

// 2915
fun lengthOfLongestSubsequence(nums: List<Int>, target: Int): Int {
    val dp = IntArray(target + 1) { Int.MIN_VALUE }
    dp[0] = 0
    for (num in nums)
        for (t in target downTo num)
            dp[t] = max(dp[t], dp[t - num] + 1)
    return if (dp[target] > 0) dp[target] else -1
}

// 1774
fun closestCost(baseCosts: IntArray, toppingCosts: IntArray, target: Int): Int {
    baseCosts.sort()
    when {
        baseCosts[0] >= target -> return baseCosts[0]
    }
    val dp = BooleanArray(target * 2 + 1)
    for (num in baseCosts) {
        dp[num] = true
    }
    for (topping in toppingCosts) {
        for (count in 1..2) {
            for (i in target * 2 downTo topping) {
                if (dp[i - topping]) {
                    dp[i] = true
                }
            }
        }
    }
    // 找到距离target最近的，相同距离找最小的
    var i = target; var j = target
    while (true) {
        when {
            dp[i] && !dp[j] -> return i
            !dp[i] && dp[j] -> return j
            dp[i] && dp[j] -> return i
        }
        i--;j++
    }
}

// 322
fun coinChange(coins: IntArray, amount: Int): Int {
    val dp = IntArray(amount + 1) { Int.MAX_VALUE }
    for (num in coins) {
        for (i in num..amount) {
            dp[i] = dp[i].coerceAtMost(dp[i - num] + 1) // 返回二者指之中较小的
        }
    }
    return if (dp[amount] != Int.MAX_VALUE) dp[amount] else -1
}

//fun main() {
//    coinChange(intArrayOf(1, 2, 5),11)
//}

// 518
fun change(amount: Int, coins: IntArray): Int {
    val n = coins.size
    val dp = IntArray(amount + 1)
    dp[0] = 1
    for (i in 0..n - 1) {
        for (j in coins[i]..amount) {
            dp[j] = dp[j] + dp[j - coins[i]]
        }
    }
    return dp.last()
}

// 279
fun numSquares(n: Int): Int {
    val dp = IntArray(n + 1) { n + 1 }
    dp[0] = 0
    for (i in 1..n) {
        for (j in i*i..n) {
            dp[j] = minOf(dp[j], dp[j - i*i] + 1)
        }
    }
    return if(dp.last() == n - 1) 0 else dp.last()
}

// 1449
fun largestNumber(cost: IntArray, target: Int): String {
    val dp = IntArray(target + 1) { Int.MIN_VALUE }
    dp[0] = 0
    for ((idx,num) in cost.withIndex()) {
        for (i in num..target) {
            if (dp[i - num] != Int.MIN_VALUE)
                dp[i] = maxOf(dp[i], dp[i - num] * 10 + idx + 1)
        }
    }
    return dp.last().toString()
}

fun largestNumber1(cost: IntArray, target: Int): String {
    val dp = IntArray(target + 1) { Int.MIN_VALUE }
    dp[0] = 0
    // dp[i] 表示成本为 i 时能拼出的最多数字个数
    for (num in cost) {
        for (i in num..target) {
            if (dp[i - num] != Int.MIN_VALUE)
                dp[i] = maxOf(dp[i], dp[i - num] + 1)
        }
    }
    if (dp[target] < 0) return "0"
    val sb = StringBuilder()
    var t = target
    for (d in 8 downTo 0) {
        val c = cost[d]
        while (t >= c && dp[t] == dp[t - c] + 1) {
            sb.append((d + 1).toString())
            t -= c
        }
    }
    return sb.toString()
}

//fun main() {
//    println(largestNumber(intArrayOf(7, 6, 5, 5, 5, 6, 8, 7, 8), 12))
//}

// 3489
fun minZeroArray(nums: IntArray, queries: Array<IntArray>): Int {
    queries.sortedWith ( compareByDescending { it[2] } )
    var ans = 0
    for (query in queries) {
        val l = query[0]
        val r = query[1]
        val `val` = query[2]
        var k = 0
        for ((i, num) in nums.withIndex()) {
            if (i >= l && i <= r) {
                if (num >= `val`) {
                    nums[i] -= (num / `val`) * `val`
                    if (k < num / `val`)
                        k = num / `val`
                }
            }
        }
        ans += k
    }
    for (num in nums) {
        if (num != 0)
            return -1
    }
    return ans
}

// 416
fun canPartition1(nums: IntArray): Boolean {
    var sum = nums.sum()
    if (sum % 2 != 0) return false
    sum /= 2
    val dp = BooleanArray(sum + 1)
    for (num in nums)
        for (i in sum downTo num) {
            dp[i] = dp[i] || dp[i - num]
        }
    return dp[sum]
}

// 494
fun findTargetSumWays1(nums: IntArray, target: Int): Int {
    var sum = nums.sum()
    if (abs(target) > sum || sum % 2 != 0) return 0
    sum = (sum + target) / 2
    val dp = IntArray(sum + 1)
    dp[0] = 1
    for (num in nums)
        for (i in sum downTo num) {
            dp[i] = dp[i] + dp[i - num]
        }
    return dp[sum]
}

fun lengthOfLongestSubsequence1(nums: List<Int>, target: Int): Int {
    val dp = IntArray(target + 1) { Int.MIN_VALUE }
    dp[0] = 0
    for (num in nums)
        for (i in target downTo num) {
            dp[i] = maxOf(dp[i], dp[i - num] + 1)
        }
    return dp[target]
}

// 2787
fun numberOfWays(n: Int, x: Int): Int {
    val mod = 1_000_000_007
    val dp = IntArray(n + 1)
    dp[0] = 1
    for (num in 1..n) {
        for (i in n downTo num.toDouble().pow(x.toDouble()).toInt()) {
            dp[i] = (dp[i] + dp[i - num.toDouble().pow(x.toDouble()).toInt()]) % mod
        }
    }
    return dp[n] % mod
}

// 3180
fun maxTotalReward(rewardValues: IntArray): Int {
    val sum = rewardValues.sum()
    val dp = IntArray(sum + 1)
    dp[0] = rewardValues[0]
    for (num in rewardValues) {
        for (i in sum downTo num) {
            if (dp[i - sum] < num)
                dp[i] = dp[i] + dp[i - sum]
        }
    }
    for (i in dp.lastIndex downTo 1)
        if (dp[i] != 0)
            return dp[sum]
    return 0
}

fun closestCost1(baseCosts: IntArray, toppingCosts: IntArray, target: Int): Int {
    baseCosts.sort()
    if (baseCosts[0] >= target) return baseCosts[0]
    val dp = BooleanArray(target * 2 + 1)
    for (num in baseCosts) {
        if (num <= target) {
            dp[num] = true
        }
    }

    for (num in baseCosts) {
        for (i in 1..2) {
            for (j in target * 2 downTo num) {
                if (dp[j - num])
                    dp[j] = true
            }
        }
    }

    var i = target; var j = target
    while (true) {
        when {
            dp[i] && !dp[j] -> return i
            !dp[i] && dp[j] -> return j
            dp[i] && dp[j] -> return i
        }
        i--;j++
    }
}

// 474
fun findMaxForm(strs: Array<String>, m: Int, n: Int): Int {
    val dp = Array(m + 1) { IntArray(n + 1) }
    dp[m][n] = 0
    for (str in strs) {
        val cnt0 = str.count { it == '0' }
        val cnt1 = str.count { it == '1' }
        for (i in cnt0..m)
            for (j in cnt1..n) {
                dp[i][j] = maxOf(dp[i][j] + dp[i - cnt0][j - cnt1] + 1)
            }
    }
    return dp[m][n]
}

fun minZeroArray1(nums: IntArray, queries: Array<IntArray>): Int {
//    queries.sortWith(compareByDescending { it[2] })
    var ans = -1
    for ((j,num) in nums.withIndex()) {
        val dp = BooleanArray(num + 1)
        dp[0] = true
        for ((i, query) in queries.withIndex()) {
            if (query[0] > j || query[1] < j) continue
            for (i in num downTo query[2]) {
                dp[i] = dp[i] || dp[i - query[2]]
            }
            if (dp[num]) {
                ans = maxOf(ans, i + 1)
                break
            }
        }
        if (!dp[num])
            return -1
    }
    return ans
}

//fun main() {
//    println(minZeroArray1(intArrayOf(4, 3, 2, 1), arrayOf(intArrayOf(1, 3, 2), intArrayOf(0, 2, 1))))
//}

// 1049
fun lastStoneWeightII(stones: IntArray): Int {
    val dp = BooleanArray(stones.sum() / 2 + 1)
    dp[0] = true
    for (stone in stones) {
        for (i in dp.lastIndex downTo stone) {
            dp[i] = dp[i] || dp[i - stone]
        }
    }
    var left = 0
    for (i in dp.lastIndex downTo 0) {
        if (dp[i]) {
            left = i
            break
        }
    }
    return abs(stones.sum() - 2 * left)
}
//fun main() {
//    lastStoneWeightII(intArrayOf(2,7,4,1,8,1))
//}

fun closestCost2(baseCosts: IntArray, toppingCosts: IntArray, target: Int): Int {
    baseCosts.sort()
    if (baseCosts[0] >= target) return baseCosts[0]
    val dp = BooleanArray(target * 2 + 1)
    for (baseCost in baseCosts) {
        dp[baseCost] = true
    }

    for (num in toppingCosts) {
        repeat(2) {
            for (i in target * 2 downTo num) {
                if (dp[i - num])
                    dp[i] = dp[i - num]
            }
        }
    }

    var left = target; var right = target
    while (true) {
        when {
            dp[left] && dp[right] -> return left
            dp[left] && !dp[right] -> return left
            !dp[left] && dp[right] -> return right
        }
    }
}

//fun main() {
//    closestCost2(intArrayOf(3), intArrayOf(2,5),5)
//}


fun change1(amount: Int, coins: IntArray): Int {
    val dp = IntArray(amount + 1)
    dp[0] = 1
    for (coin in coins) {
        for (i in coin..amount) {
            dp[i] = dp[i] + dp[i - coin]
        }
    }
    return dp.last()
}

fun coinChange2(coins: IntArray, amount: Int): Int {
    val dp = IntArray(amount + 1) { Int.MAX_VALUE }
    dp[0] = 0
    for (coin in coins) {
        for (i in coin..amount) {
            dp[i] = minOf(dp[i], dp[i - coin] + 1)
        }
    }
    return dp.last()
}

// 2585
fun waysToReachTarget(target: Int, types: Array<IntArray>): Int {
    val dp = IntArray(target + 1)
    dp[0] = 1
    for (type in types) {
        val cnt = type[0]
        val num = type[1]
        for (i in 0..target - num) {
           for (j in 1..minOf(cnt, target / num)) {
               dp[i + j * num] = dp[i + j * num] + dp[i + j * num - num]
           }
        }
    }
    return dp.last()
}

// 72
fun minDistance(word1: String, word2: String): Int {
    val n = word1.length
    val m = word2.length
    val dp = Array(n + 1) { IntArray(m + 1) }
    for (i in 1..n) dp[i][0] = i
    for (j in 1..m) dp[0][j] = j
    for (i in 1..n) {
        for (j in 1..m) {
            dp[i][j] = if (word1[i - 1] == word2[j - 1]) dp[i - 1][j - 1]
            else minOf(dp[i - 1][j - 1] + 1, dp[i - 1][j] + 1, dp[i][j - 1] + 1)
        }
    }
    return dp.last().last()
}
fun minDistance1(word1: String, word2: String): Int {
    val n = word1.length
    val m = word2.length
    val dp = IntArray(m + 1)
    for (i in 1..m) dp[i] = i
    for (i in 1..n) {
        var pre = 0
        dp[0] = i
        for (j in 1..m) {
            val temp = dp[j]
            dp[j] = if (word1[i - 1] == word2[j - 1]) dp[j - 1]
            else minOf(pre + 1, dp[j - 1] + 1, dp[j] + 1)
            pre = temp
        }
    }
    return dp[m]
}

// 1035
fun maxUncrossedLines(nums1: IntArray, nums2: IntArray): Int {
    val n = nums1.size
    val m = nums2.size
    val dp = Array(n + 1) { IntArray(m + 1) }
    for (i in 1..n) dp[i][0] = i
    for (j in 1..m) dp[0][j] = j
    for (i in 1..n) {
        for (j in 1..m) {
            dp[i][j] = if (nums1[i - 1] == nums2[j - 1]) dp[i - 1][j - 1]
            else minOf(dp[i][j - 1] + 1, dp[i - 1][j] + 1)
        }
    }
    return ((n + m) - dp[n][m]) ushr 1
}
fun maxUncrossedLines1(nums1: IntArray, nums2: IntArray): Int {
    val n = nums1.size
    val m = nums2.size
    val dp = IntArray(m + 1)
    for (i in 1..m) dp[i] = i
    for (i in 1..n) {
        var pre = dp[0]
        dp[0] = i
        for (j in 1..m) {
            val temp = dp[j]
            dp[j] = if (nums1[i - 1] == nums2[j - 1]) pre
            else minOf(dp[j] + 1, dp[j - 1] + 1)
            pre = temp
        }
    }
    return ((n + m) - dp[m]) ushr 1
}

// 1458
fun maxDotProduct(nums1: IntArray, nums2: IntArray): Int {
    val n = nums1.size
    val m = nums2.size
    val dp = Array(n + 1) { IntArray(m + 1) { Int.MAX_VALUE } }
    for (i in 1..n) {
        for (j in 1..m) {
            val sum = nums1[i - 1] * nums2[j - 1]
            dp[i][j] = maxOf(
                dp[i - 1][j - 1] + sum,
                dp[i][j - 1],
                dp[i - 1][j],
                sum
                )
        }
    }
    return dp[n][m]
}

fun maxDotProduct1(nums1: IntArray, nums2: IntArray): Int {
    val n = nums1.size
    val m = nums2.size
    val dp = IntArray(m + 1) { Int.MIN_VALUE }
    for (i in 1..n) {
        var pre = dp[0]
        for (j in 1..m) {
            val temp = dp[j]
            val sum = nums1[i - 1] * nums2[j - 1]
            val ext: Int = if (dp[j - 1] == Int.MIN_VALUE) {
                sum
            }else
                dp[j - 1] + sum
            dp[j] = maxOf(
                sum,
                ext,
                dp[j],
                dp[j - 1]
            )
            pre = temp
        }
    }
    return dp.last()
}

fun maxDotProduct2(nums1: IntArray, nums2: IntArray): Int {
    val n = nums1.size
    val m = nums2.size
    val dp = Array(n + 1) { IntArray(m + 1) { Int.MIN_VALUE } }
    for (i in 1..n) {
        for (j in 1..m) {
            val sum = nums1[i - 1] * nums2[j - 1]
            val temp = if (dp[i - 1][j - 1] == Int.MIN_VALUE) sum
            else sum + dp[i - 1][j - 1]
            dp[i][j] = maxOf(
                sum,
                temp,
                dp[i][j - 1],
                dp[i - 1][j]
            )
        }
    }
    return dp[n][m]
}

// 718
fun findLength(nums1: IntArray, nums2: IntArray): Int {
    val n = nums1.size
    val m = nums2.size
    var maxLen = 0
    val dp = Array(n + 1) { IntArray(m + 1) }
    for (i in 1..n) {
        for (j in 1..m) {
            if (nums1[i - 1] == nums2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1] + 1
                maxLen = max(maxLen, dp[i][j])
            }
        }
    }
    return dp.last().last()
}

fun findLength1(nums1: IntArray, nums2: IntArray): Int {
    val n = nums1.size
    val m = nums2.size
    val dp = IntArray(m + 1)
    var maxLen = 0
    for (i in 1..n) {
        var pre = dp[0]
        for (j in 1..m) {
            val temp = dp[j]
            if (nums1[i - 1] == nums2[j - 1]) {
                dp[j] = pre + 1
                maxLen = max(maxLen, dp[j])
            }
            pre = temp
        }
    }
    return maxLen
}

fun maxUncrossedLines2(nums1: IntArray, nums2: IntArray): Int {
    val n = nums1.size
    val m = nums2.size
    val dp = Array(n + 1) { IntArray(m + 1) }
    for (i in 1..m) dp[0][i] = i
    for (i in 1..m) dp[i][0] = i
    for (i in 1..n) {
        for (j in 1..m) {
            dp[i][j] = if (nums1[i - 1] == nums2[j - 1]) dp[i - 1][j - 1]
            else minOf(dp[i][j - 1] + 1, dp[i - 1][j] + 1)
        }
    }
    return ((n + m) - dp.last().last()) ushr 1
}

fun minDistance2(word1: String, word2: String): Int {
    val n = word1.length
    val m = word2.length
    val dp = Array(n + 1) { IntArray(m + 1) }
    for (i in 1..n) dp[0][i] = i
    for (i in 1..m) dp[i][0] = i
    for (i in 1..n) {
        for (j in 1..m) {
            dp[i][j] = if (word1[i - 1] == word2[j - 1]) dp[i - 1][j - 1]
            else minOf(
                dp[i - 1][j] + 1, // 删除
                dp[i - 1][j - 1] + 1, // 替换
                dp[i][j - 1] + 1 // 插入
            )
        }
    }
    return dp.last().last()
}

fun minDistance3(word1: String, word2: String): Int {
    val n = word1.length
    val m = word2.length
    val dp = IntArray(m + 1)
    for (i in 1..m) dp[i] = i
    for (i in 1..n) {
        var pre = dp[0]
        dp[0] = i
        for (j in 1..m) {
            val temp = dp[j]
            dp[j] = if (word1[i - 1] == word2[j - 1]) pre
            else minOf(
                dp[j] + 1,
                pre + 1,
                dp[j - 1] + 1
            )
            pre = temp
        }
    }
    return dp.last()
}

// 583
fun minDistance4(word1: String, word2: String): Int {
    val n = word1.length
    val m = word2.length
    val dp = IntArray(m + 1)
    for (i in 1..m) dp[i] = i
    for (i in 1..n) {
        var pre = dp[0]
        dp[0] = i
        for (j in 1..m) {
            val temp = dp[j]
            dp[j] = if (word1[i - 1] == word2[j - 1]) pre
            else minOf(dp[j] + 1, dp[j - 1] + 1)
            pre = temp
        }
    }
    return dp.last()
}

// 712
fun minimumDeleteSum(s1: String, s2: String): Int {
    val n = s1.length
    val m = s2.length
    val dp = IntArray(m + 1)
    for (i in 1..m) dp[i] = dp[i - 1] + s2[i - 1].code
    for (i in 1..n) {
        var pre = dp[0]
        dp[0] += s1[i - 1].code
        for (j in 1..m) {
            val temp = dp[j]
            dp[j] = if (s1[i - 1] == s1[j - 1]) pre
            else minOf(dp[j] + s1[i - 1].code, dp[j - 1] + s2[j - 1].code)
            pre = temp
        }
    }
    return dp.last()
}

fun waysToReachTarget1(target: Int, types: Array<IntArray>): Int {
    val mod = 1_000_000_007
    val dp = IntArray(target + 1).also { it[0] = 1 }
    for ((cnt, num) in types) {
        for (i in target downTo num)
            for (j in 1..minOf(cnt, i / num)) {
                dp[i] = (dp[i] + dp[i - j * num]) % mod
            }
    }
    return dp.last() % mod
}