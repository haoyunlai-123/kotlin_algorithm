package algorithm

// 1475
fun finalPrices1(prices: IntArray): IntArray =
    IntArray(prices.size).also { arr ->
        ArrayDeque<Int>().also { stack ->
            (prices.size - 1 downTo 0).forEach { idx ->
                while (stack.isNotEmpty() && stack.first() >= prices[idx]) {
                    stack.removeFirst()
                }
                arr[idx] = if (stack.isEmpty()) prices[idx] else prices[idx] - stack.first()
                stack.addFirst(prices[idx])
            }
        }
    }

// 单调栈的核心：栈中只保留有用的元素
fun dailyTemperatures2(temperatures: IntArray): IntArray {
    val n = temperatures.size
    val stack = ArrayDeque<Int>()
    val ans = IntArray(n)
    for (i in n - 1 downTo 0) {
        while (stack.isNotEmpty() && temperatures[stack.first()] <= temperatures[i]) {
            stack.removeFirst()
        }
        ans[i] = if (stack.isNotEmpty()) stack.first() - i else 0
        stack.addFirst(i)
    }
    return ans
}

fun finalPrices2(prices: IntArray): IntArray {
    val n = prices.size
    val ans = IntArray(n)
    val stack = ArrayDeque<Int>(n)
    for (i in n - 1 downTo 0) {
        while (stack.isNotEmpty() && stack.first() >= prices[i]) {
            stack.removeFirst()
        }
        ans[i] = if (stack.isEmpty) prices[i] else prices[i] - stack.first()
        stack.addFirst(prices[i])
    }
    return ans
}

fun nextGreaterElement2(
    nums1: IntArray,
    nums2: IntArray,
): IntArray {
    val stack = ArrayDeque<Int>()
    val hash = mutableMapOf<Int, Int>()
    val ans = IntArray(nums1.size)
    for (i in nums2.size - 1 downTo 0) {
        while (stack.isNotEmpty() && stack.first() <= nums2[i]) {
            stack.removeFirst()
        }
        if (stack.isNotEmpty()) {
            hash.put(nums2[i], stack.first())
        } else {
            hash.put(nums2[i], -1)
        }
        stack.addFirst(nums2[i])
    }
    for ((i, num) in nums1.withIndex()) {
        ans[i] = hash[num]!!
    }
    return ans
}

fun nextGreaterElements2(nums: IntArray): IntArray {
    val stack = ArrayDeque<Int>()
    val ans = IntArray(nums.size)
    for (i in nums.size * 2 - 1 downTo 0) {
        val idx = i % nums.size
        while (stack.isNotEmpty() && stack.first() <= nums[idx]) {
            stack.removeFirst()
        }
        ans[idx] = if (stack.isNotEmpty()) stack.first() else -1
        stack.addFirst(nums[idx])
    }
    return ans
}
