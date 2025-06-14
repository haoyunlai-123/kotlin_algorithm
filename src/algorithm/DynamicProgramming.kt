package algorithm

import kotlin.math.max
import kotlin.math.pow

// 198
// 0st 直接递归
fun rob0(nums: IntArray): Int {
    fun dp(idx: Int): Int {
        if (idx < 0) {
            return 0
        }
        val a = max(dp(idx - 1), dp(idx - 2) + nums[idx])
        return a
    }
    return dp(nums.size - 1)
}

// 1st 保存中间计算状态
// 时间复杂度为o(n)
fun rob1(nums: IntArray): Int {
    val cache = IntArray(nums.size) { -1 }

    fun dp(idx: Int): Int {
        if (idx < 0) {
            return 0
        }
        if (cache[idx] != -1) {
            return cache[idx]
        }
        val a = max(dp(idx - 1), dp(idx - 2) + nums[idx])
        cache[idx] = a
        return a
    }
    return dp(nums.size - 1)
}

// 2st 递推：求情况数组
// 时间复杂度为o(n)
fun rob2(nums: IntArray): Int {
    val dp = IntArray(nums.size + 2) { 0 }
    for ((i, num) in nums.withIndex()) {
        dp[i + 2] = max(dp[i + 1], dp[i] + num)
    }
    return dp[dp.lastIndex]
}

// 213
fun rob213(nums: IntArray): Int {
    val cache = IntArray(nums.size) { -1 }

    fun dp(idx: Int): Int {
        if (idx < 0) {
            return 0
        }
        if (cache[idx] != -1) {
            return cache[idx]
        }
        val a = max(dp(idx - 1), dp(idx - 2) + nums[idx])
        cache[idx] = a
        return a
    }

    return dp(nums.size - 1)
}

fun rob(nums: IntArray): Int = max(rob213(nums.sliceArray(1..nums.lastIndex)), nums[0] + rob213(nums.sliceArray(2..nums.lastIndex - 1)))

// 2320
fun countHousePlacements(n: Int): Int {
    val mod = 1_000_000_007L
    val cache = LongArray(n + 1) { -1L }

    fun dp(n: Int): Long {
        if (n == 0) {
            return 1
        }
        if (n == 1) {
            return 2
        }
        if (cache[n] != -1L) {
            return cache[n]
        }
        val a = dp(n - 1) + dp(n - 2)
        cache[n] = a
        return a
    }

    val res: Long = dp(n)
    return ((res * res) % mod).toInt()
}

// 2787
fun numberOfWays(
    n: Int,
    x: Int,
): Int {
    val mod = 1_000_000_007
    val dp = IntArray(n + 1)
    dp[0] = 1
    for (i in 1..n) {
        for (m in n downTo i) {
            if (i.toDouble().pow(x.toDouble()) <= m) {
                dp[m] = (dp[m] + dp[m - i.toDouble().pow(x.toDouble()).toInt()]) % mod
            }
        }
    }
    return dp[n]
}

// fun main() {
//    println(numberOfWays(10, 2))
// }

// 3180
fun maxTotalReward(rewardValues: IntArray): Int {
    rewardValues.sort()
    val n = rewardValues.size
    val m = rewardValues[n - 1]
    val dp = IntArray(m)
    dp[0] = 0
    for (x in rewardValues) {
        for (y in m - 1 downTo x) {
            var a = 0
            if (y - x >= x) {
                a = x - 1
            } else {
                a = y - x
            }
            dp[y] = max(dp[y], x + dp[a])
        }
    }
    return dp[m - 1] + m
}

// 474
fun findMaxForm(
    strs: Array<String>,
    m: Int,
    n: Int,
): Int {
    fun String.one(): Int = count { it == '1' }

    fun String.zero(): Int = count { it == '0' }
    val dp = Array(m + 1) { IntArray(n + 1) { 0 } }
    for (str in strs) {
        for (i in m downTo 1) {
            for (j in n downTo 1) {
                dp[i][j] = dp[i][j].coerceAtLeast(dp[i - str.one()][j - str.zero()] + 1)
            }
        }
    }
    return dp[m][n]
}

fun findMaxForm1(
    strs: Array<String>,
    m: Int,
    n: Int,
): Int {
    val dp = Array(m + 1) { IntArray(n + 1) }

    strs.forEach { s ->
        val zeros = s.count { it == '0' }
        val ones = s.length - zeros

        for (i in m downTo zeros) {
            for (j in n downTo ones) {
                dp[i][j] = maxOf(dp[i][j], dp[i - zeros][j - ones] + 1)
            }
        }
    }

    return dp[m][n]
}

// 2585
fun waysToReachTarget(
    target: Int,
    types: Array<IntArray>,
): Int {
    val dp = IntArray(target + 1)
    dp[0] = 1
    for (type in types) {
        val cnt = type[0]
        val num = type[1]
        for (n in (target downTo 1)) {
            for (i in 1..minOf(cnt, n / num)) {
                dp[n] = dp[n] + dp[n - i * num]
            }
        }
    }
    return dp[target]
}

// 3180
fun maxTotalReward1(rewardValues: IntArray): Int {
    rewardValues.sort()
    val max = rewardValues.last()
    val dp = IntArray(max)
    for (num in rewardValues) {
        for (i in max - 1 downTo num) {
            if (i - num >= num) {
                dp[i] = maxOf(dp[i], dp[num - 1] + num)
            } else {
                dp[i] = maxOf(dp[i], dp[i - num] + num)
            }
        }
    }
    return dp.last() + max
}

// 1449
fun largestNumber(
    cost: IntArray,
    target: Int,
): String {
    val dp = IntArray(target + 1) { Int.MIN_VALUE }
    dp[0] = 0
    for (num in cost) {
        for (i in num..target) {
            dp[i] = maxOf(dp[i], dp[i - num] + 1)
        }
    }

    if (dp[target] < 0) return "0"
    val sb = StringBuilder()
    var t = target
    for (j in 8 downTo 0) {
        val n = cost[j]
        // 追踪dp路径
        while (t >= n && dp[t] == dp[t - n] + 1) {
            sb.append((j + 1).toString())
            t -= n
        }
    }
    return sb.toString()
}

// fun main() {
//    println(largestNumber(intArrayOf(3, 2, 2), 9))
// }

// 1143
fun longestCommonSubsequence(
    text1: String,
    text2: String,
): Int {
    val dp = Array(text1.length + 1) { IntArray(text2.length + 1) }
    for (i in 1 until dp.size) {
        for (j in 1 until dp[0].size) {
            dp[i][j] =
                if (text1[i - 1] == text2[j - 1]) {
                    dp[i - 1][j - 1]
                } else {
                    maxOf(dp[i][j - 1], dp[j][i - 1])
                }
        }
    }
    return dp[text1.length][text2.length]
}
