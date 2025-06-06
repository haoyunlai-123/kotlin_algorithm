package algorithm

import kotlin.math.max

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
