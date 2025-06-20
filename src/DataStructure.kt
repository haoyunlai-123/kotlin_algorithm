import kotlin.math.abs

// 1
fun twoSum(nums: IntArray, target: Int): IntArray {
    val hash = mutableMapOf<Int, Int>()
    val ans = mutableListOf<Int>()
    for ((idx, num) in nums.withIndex()) {
        if (hash.containsKey(target - num)) {
            ans.add(hash[target - num]!!)
            ans.add(idx)
            ans.remove(target - num)
        }else {
            hash.put(num, idx)
        }
    }
    return ans.toIntArray()
}

fun twoSum1(nums: IntArray, target: Int): IntArray {
    mutableMapOf<Int, Int>().also { hash ->
        nums.forEachIndexed { idx,num ->
            if (target - num in hash) {
                return intArrayOf(hash[target - num]!!, idx)
            }
            hash.put(num, idx)
        }
    }
    return intArrayOf()
}

// 2441
fun findMaxK(nums: IntArray): Int {
    var ans = Int.MIN_VALUE
    mutableSetOf<Int>().also { set ->
        nums.forEach { num ->
            if (-num in set) {
                ans = ans.coerceAtLeast(abs(num))
            } else {
                set.add(num)
            }
        }
    }
    return if (ans == Int.MIN_VALUE) -1 else ans
}

// 1512
fun numIdenticalPairs(nums: IntArray): Int {
    var ans = 0
    mutableMapOf<Int,Int>().also { hash ->
        nums.forEach { num ->
            if (num in hash) {
                ans += hash[num]!!
            }
            hash.merge(num,1,Int::plus)
        }
    }
    return ans
}