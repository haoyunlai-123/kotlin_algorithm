import kotlin.math.max

// 34
fun searchRange(nums: IntArray, target: Int): IntArray {
    fun binarySearch(nums: IntArray, target: Int): Int {
        var left = -1
        var right = nums.size
        var mid = 0
        while (left + 1 < right) {
            mid = left + (right - left) ushr 1
            if (nums[mid] >= target)
                right = mid
            else
                left = mid
        }
        return right
    }

    val a = binarySearch(nums, target)
    if (a == nums.size || nums[a] != target)
        return intArrayOf(-1, -1)
    val b = binarySearch(nums, target + 1) - 1
    return intArrayOf(a, b)
}

// 35
fun searchInsert(nums: IntArray, target: Int): Int {
    var left = -1
    var right = nums.size
    var mid = 0
    while (left + 1 < right) {
        mid = left + ((right - left) shr 1)
        if (nums[mid] >= target)
            right = mid
        else
            left = mid
    }
    return right
}

// 704
fun search(nums: IntArray, target: Int): Int {
    var left = -1
    var right = nums.size
    var mid = 0
    while (left + 1 < right) {
        mid = left + ((right - left) shr 1)
        if (nums[mid] >= target)
            right = mid
        else
            left = mid
    }
    if (right == nums.size || nums[right] != target)
        return -1
    return right
}

// 744
fun nextGreatestLetter(letters: CharArray, target: Char): Char {
    var left = -1
    var right = letters.size
    var mid = 0
    while (left + 1 < right) {
        mid = left + ((right - left) shr 1)
        if (letters[mid] > target)
            right = mid
        else
            left = mid
    }
    return if (right == letters.size) letters[0] else letters[right]
}

// 2529
fun maximumCount(nums: IntArray): Int {
    fun binarySearch1(nums: IntArray): Int {
        var left = -1
        var right = nums.size
        var mid = 0
        while (left + 1 < right) {
            mid = left + ((right - left) shr 1)
            if (nums[mid] < 0)
                left = mid
            else
                right = mid
        }
        return left
    }
    fun binarySearch2(nums: IntArray): Int {
        var left = -1
        var right = nums.size
        var mid: Int
        while (left + 1 < right) {
            mid = left + ((right - left) shr 1)
            if (nums[mid] <= 0)
                left = mid
            else
                right = mid
        }
        return right
    }
    val a = binarySearch1(nums)
    val b = binarySearch2(nums)
    return max(a + 1,nums.size - b)
}