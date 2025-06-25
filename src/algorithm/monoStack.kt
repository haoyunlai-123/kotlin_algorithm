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

// 2866
fun maximumSumOfHeights(maxHeights: List<Int>): Long {
    val n = maxHeights.size
    val left = LongArray(n)
    val right = LongArray(n)
    val stack = ArrayDeque<Int>()
    for (i in n - 1 downTo 0) {
        while (stack.isNotEmpty() && maxHeights[stack.first()] > maxHeights[i]) {
            stack.removeFirst()
        }
        val j = if (stack.isEmpty()) n else stack.first()
        val width = j - i
        right[i] = (if (j == n) 0 else right[j]) + width.toLong() * maxHeights[i]
        stack.addFirst(i)
    }
    stack.clear()
    for (i in 0 until n - 1) {
        while (stack.isNotEmpty() && maxHeights[stack.first()] > maxHeights[i]) {
            stack.removeFirst()
        }
        val j = if (stack.isEmpty()) -1 else stack.first()
        val width = i - j
        left[i] = (if (j == -1) 0 else left[j]) + width.toLong() * maxHeights[i]
        stack.addFirst(i)
    }

    var ans = 0L
    for (i in 0 until n) {
        ans = maxOf(ans, left[i] + right[i] - maxHeights[i])
    }
    return ans
}

// 1944
fun canSeePersonsCount(heights: IntArray): IntArray {
    val n = heights.size
    val stack = ArrayDeque<Int>()
    val ans = IntArray(n)
    for (i in n - 1 downTo 0) {
        var cnt = 0
        while (stack.isNotEmpty() && stack.first() < heights[i]) {
            cnt++
            stack.removeFirst()
        }
        if (stack.isNotEmpty()) cnt++
        ans[i] = cnt
        stack.addFirst(heights[i])
    }
    return ans
}

// 962
fun maxWidthRamp(nums: IntArray): Int {
    val stack = ArrayDeque<Int>()
    for ((i, num) in nums.withIndex()) {
        while (stack.isEmpty() || nums[stack.first()] > num) {
            stack.addFirst(i)
        }
    }
    var ans = 0
    for (i in nums.size - 1 downTo 0) {
        while (stack.isNotEmpty() && nums[stack.first()] <= nums[i]) {
            ans++
            stack.removeFirst()
        }
    }
    return ans
}

// 2454
fun secondGreaterElement(nums: IntArray): IntArray {
    val n = nums.size
    val ans = IntArray(n) { -1 }
    val sta1 = ArrayDeque<Int>()
    val sta2 = ArrayDeque<Int>()
    // 7 10 4 0 6
    for (i in nums.indices) {
        while (sta2.isNotEmpty() && nums[i] > nums[sta2.first()]) {
            ans[sta2.removeFirst()] = nums[i]
        }
        val temp = mutableListOf<Int>()
        while (sta1.isNotEmpty() && nums[i] > nums[sta1.first()]) {
            temp.add(sta1.removeFirst())
        }
        for (j in temp.size - 1 downTo 0) {
            sta2.addFirst(j)
        }
        sta1.addFirst(i)
    }
    return ans
}

// 1130
fun mctFromLeafValues(arr: IntArray): Int {
    val sta = ArrayDeque<Int>()
    sta.addFirst(Int.MAX_VALUE)
    var ans = 0
    for (num in arr) {
        while (sta.first() <= num) {
            val tmp = sta.removeFirst()
            ans += minOf(num, sta.first()) * tmp
        }
        sta.addFirst(num)
    }
    while (sta.size > 2) {
        val tmp = sta.removeFirst()
        ans += tmp * sta.first()
    }
    return ans
}
