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
fun rob(nums: IntArray): Int {
    val dp = IntArray(nums.size + 2) { 0 }
    for ((i, num) in nums.withIndex()) {
        dp[i + 2] = max(dp[i + 1], dp[i] + num)
    }
    return dp[dp.lastIndex]
}
