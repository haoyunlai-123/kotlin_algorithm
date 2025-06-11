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