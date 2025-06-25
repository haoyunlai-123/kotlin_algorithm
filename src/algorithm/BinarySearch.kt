

// 34
fun searchRange(
    nums: IntArray,
    target: Int,
): IntArray {
    fun binarySearch(
        nums: IntArray,
        target: Int,
    ): Int {
        var left = -1
        var right = nums.size
        var mid = 0
        while (right > left + 1) {
            mid = left + ((right - left) ushr 1)
            if (nums[mid] >= target) {
                right = mid
            } else {
                left = mid
            }
        }
        return right
    }
    val a = binarySearch(nums, target)
    if (a == nums.size || nums[a] != target) {
        return intArrayOf(-1, -1)
    }
    val b = binarySearch(nums, target + 1)
    return intArrayOf(a, b - 1)
}
