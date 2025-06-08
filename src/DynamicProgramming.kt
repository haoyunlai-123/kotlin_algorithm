import kotlin.math.abs
import kotlin.math.max

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