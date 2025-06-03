package algorithm

import kotlin.math.max

// 1456
fun maxVowels(s: String, k: Int): Int {
    val arr = s.toCharArray()
    var ans = 0
    var num = 0
    arr.indices.forEach { i ->
        if(arr[i] == 'a' || arr[i] == 'e' || arr[i] == 'i' || arr[i] == 'o' || arr[i] == 'u')
            num++
        if(i < k - 1)
            return@forEach
        ans = max(ans,num)
        val a = arr[i - k + 1]
        if(a == 'a' || a == 'e' || a == 'i' || a == 'o' || a == 'u')
            num--
    }
    return ans
}

// 643
fun findMaxAverage(nums: IntArray, k: Int): Double {
    var ans = Int.MIN_VALUE
    var sum = 0
    nums.indices.forEach { idx ->
        sum += nums[idx]
        when {
            idx < k - 1 -> return@forEach
        }
        ans = max(ans,sum)
        sum -= nums[idx - k + 1]
    }
    return ans.toDouble() / k
}

// 1343
fun numOfSubarrays(arr: IntArray, k: Int, threshold: Int): Int {
    var ans = 0
    var sum = 0
    arr.indices.forEach { idx ->
        sum += arr[idx]
        when {
            idx < k - 1 -> return@forEach
        }
        when {
            sum / k > threshold * k -> ans++
        }
        sum -= arr[idx - k + 1]
    }
    return ans
}

// 2090
fun getAverages(nums: IntArray, k: Int): IntArray = IntArray(nums.size){ -1 }.also { arr ->
    if(k*2 + 1 > nums.size) return@also
    var sum = 0
    for(i in (0..nums.size - 1)) {
        sum += nums[i]
        if(i < nums.size - k) continue
        arr[i] = sum / (2 * k + 1)
        sum -= nums[i - k*2]
    }
}

