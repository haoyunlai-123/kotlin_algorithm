import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
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