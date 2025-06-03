package algorithm

import jdk.dynalink.Operation
import java.util.ArrayDeque
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

// 1.
fun twoSum(nums: IntArray, target: Int): IntArray {
    hashMapOf<Int,Int>().apply {
        nums.forEachIndexed(){ i,num ->
            if(target - num in this) {
                var index = get(target - num) ?: 0
                return intArrayOf(index, i)
            }
            put(num, i)
        }
    }
    return intArrayOf()
}

//// 1512
//fun numIdenticalPairs(nums: IntArray): Int {
//    hashMapOf<Int,Int>().apply {
//        var ans = 0
//        nums.forEachIndexed(){ i,num ->
//            var c = getOrDefault(num,0)
//            ans += c
//            put(num,++c)
//        }
//        return ans
//    }
//}

// 1512
fun numIdenticalPairs(nums: IntArray): Int {
    var count = nums.groupBy { it }.mapValues { it.value.size }
    return count.values.sumOf { it * (it - 1) / 2}
}

// 2001
fun interchangeableRectangles(rectangles: Array<IntArray>): Long {
    hashMapOf<Double,Int>().apply {
        var ans: Long = 0
        rectangles.forEach { arr ->
            val c: Double = arr[0].toDouble() / arr[1]
            var a = getOrDefault(c, 0)
            ans += a
            put(c,++a)
        }
        return ans
    }
}

//// 3267
//fun countPairs(nums: IntArray): Int {
//    nums.sort()
//    hashMapOf<Int,Int>().apply {
//        var ans = 0
//        nums.forEach { num ->
//            hashSetOf<Int>().apply {
//                add(num)
//                var arrchar = num.toString().toCharArray()
//
//
//            }
//        }
//    }
//}

// 42
// 前后缀分解
fun trap(height: IntArray): Int {
    var pre = IntArray(height.size).apply {
        this[0] = height[0]
        for(i in 1 until height.size){
            this[i] = maxOf(this[i - 1],height[i])
        }
    }
    var suf = IntArray(height.size).apply {
        this[height.size - 1] = height[height.size - 1]
        for(i in height.size - 2 downTo 0){
            this[i] = minOf(this[i + 1],height[i])
        }
    }
    return height.indices.sumOf { i ->
        minOf(suf[i],height[i]) - height[i] }
}

// 1534
fun countGoodTriplets(arr: IntArray, a: Int, b: Int, c: Int): Int {
    var n = arr.size
    var ans = 0
    for(i in 0 until a){
        var list1 = mutableListOf<Int>()
        var list2 = mutableListOf<Int>()
        for(j in 0 until i)
            if(abs(arr[j] - arr[i]) <= a)
                list1.add(arr[j])
        for(k in i + 1 until n)
            if(abs(arr[k] - arr[i]) <= b)
                list2.add(arr[k])
        for (i1 in list1)
            for (i2 in list2)
                if(abs(i1 - i2) <= c)
                    ans++;
    }
    return ans
}

fun countGoodTriplets1(arr: IntArray, a: Int, b: Int, c: Int): Int {
    var count = 0
    for (i in arr.indices) {
        val list1 = (0 until i).filter { j -> abs(arr[j] - arr[i]) <= a }.map { arr[it] }
        val list2 = (i + 1 until arr.size).filter { k -> abs(arr[k] - arr[i]) <= b }.map { arr[it] }
        count += list1.flatMap { l1 -> list2.map { l2 -> l1 to l2 } }.count { (l1, l2) -> abs(l1 - l2) <= c }
    }
    return count
}

// 53
fun maxSubArray(nums: IntArray): Int {
    IntArray(nums.size + 1).apply {
        var maxi = 1
        var mini = 1
        for(i in 1..<this.size){
            this[i] = this[i - 1] + nums[i - 1]
            maxi = if(this[maxi] < this[i]) i else maxi
            mini = if(this[mini] > this[i]) i else mini
        }
        if(maxi > mini) return this[maxi] - this[mini]
        else return this[maxi]
    }
}

// 1523
fun countOdds(low: Int, high: Int): Int {
    if(low % 2 != high % 2) return (high - low + 1) shr 1
    else {
        if (low % 2 == 1) return ((high - low + 1) shr 1) + 1
        else return (high - low + 1) shr 1
    }
}

// 930
fun numSubarraysWithSum(nums: IntArray, goal: Int): Int {
    val sum: IntArray = IntArray(nums.size + 1)
    var map = HashMap<Int, Int>()
    for(i in 1..(sum.size - 1)) {
        sum[i] = sum[i - 1] + nums[i - 1]
        map.merge(sum[i], 1, Int::plus)
    }
    map.merge(0,1, Int::plus)
    var ans = 0
    for (i in nums.indices) {
        ans += map.getOrDefault(sum[i + 1] - goal, 0)
    }
    return ans
}

// 560
fun subarraySum(nums: IntArray, k: Int): Int {
    val sum: IntArray = IntArray(nums.size + 1)
    var map = HashMap<Int, Int>()
    for(i in 1..(nums.size - 1))
        sum[i] = sum[i - 1] + nums[i - 1]
    map.put(0,1)
    var ans = 0
    for(i in 0..<nums.size){
        val c = map.getOrDefault(sum[i + 1] - k,0)
        ans += c
        map.merge(sum[i + 1], 1, Int::plus)
    }
    return ans
}

// 1524
fun numOfSubarrays(arr: IntArray): Int {
    val MOD = 1e9 + 7
    var ji = 0
    var ou = 1
    var ans = 0L
    val sum = IntArray(arr.size + 1)
    for(i in 1..<sum.size){
        sum[i] = sum[i - 1] + arr[i - 1]
        if(sum[i] % 2 == 1){
            ans += ou
            ji++
        } else{
            ans += ji
            ou++
        }
    }
    return (ans.toDouble() % MOD).toInt()
}

// 974
fun subarraysDivByK(nums: IntArray, k: Int): Int {
    val sum: IntArray = IntArray(nums.size + 1)
    var map = HashMap<Int, Int>()
    for(i in 1..(sum.size - 1)){
        sum[i] = sum[i - 1] + nums[i - 1]
        sum[i - 1] %= k // 将前缀和中的元素都转换至一个范围内
        if(sum[i - 1] < 0 )
            sum[i - 1] += k
    }
    sum[sum.size - 1] %= k
    if(sum[sum.size - 1] < 0)
        sum[sum.size - 1] += k
    map.put(0,1)
    var ans = 0
    for(i in nums.indices){
        val c = map.getOrDefault(sum[i + 1],0)
        ans += c
        map.merge(sum[i + 1],1,Int::plus)
    }
    return ans
}

// 523
fun checkSubarraySum(nums: IntArray, k: Int): Boolean {
    val sum = IntArray(nums.size + 1)
    var map = HashMap<Int, Int>()
    for(i in 1..(sum.size - 1)){
        sum[i] = sum[i - 1] + nums[i - 1]
        sum[i - 1] %= k
    }
    sum[sum.size - 1] %= k
    map.put(0,0)
    for (i in nums.indices){
        if(map.contains(sum[i + 1]) && i - map[i]!! >= 2)
            return true
        map.merge(sum[i + 1],i,Int::plus)
    }
    return false
}

// 17.05
fun findLongestSubarray(array: Array<String>): Array<String> {
    var maxi = 0
    var mini = 0
    var len = 0
    val map = HashMap<Int, Int>()
    map.put(0,-1)
    var pre = 0;
    for ((i,num) in array.withIndex()) {
        pre += if((num.get(0) in 'a'..'z') || (num.get(0) in 'A'..'Z')) 1 else -1
        if(map.containsKey(pre)){
            if(i - map.get(pre)!! > len){
                len = i - map.get(pre)!!
                maxi = i
                mini = map.get(pre)!!
            }
        } else
            map.put(pre, i)
    }
    return array.sliceArray(mini + 1..maxi)
}

// 3026
fun maximumSubarraySum(nums: IntArray, k: Int): Long {
    val sum = LongArray(nums.size + 1)
    var map = HashMap<Int, Int>()
    for(i in 1..(sum.size - 1)){
        sum[i] = sum[i - 1] + nums[i - 1]
    }
    var ans = Long.MIN_VALUE
    for((i,num) in nums.withIndex()){
        if(map.containsKey(num + k)){
           ans = max(ans, sum[i + 1] - sum[map.get(num + k)!!])
        }
        if(map.containsKey(num - k)){
            ans = max(ans, sum[i + 1] - sum[map.get(num - k)!!])
        }
        map.merge(num,i,{old,new -> if(sum[new + 1] < sum[old + 1]) new else old})
    }
    if(ans == Long.MIN_VALUE)
        return 0
    return ans
}
//fun main() {
//    maximumSubarraySum(intArrayOf(-1,3,2,4,5), 3)
//}

// 1477
fun minSumOfLengths(arr: IntArray, target: Int): Int {
    val map = HashMap<Int, Int>()
    map.put(0,-1)
    var ans = -1
    var pre = 0
    val list = mutableListOf<Int>()
    for(i in arr.indices){
        pre += arr[i]
        if(map.containsKey(pre - target)){
            if(list.size <= 1) {
                list.add(i - map.get(pre - target)!!)
                if(list.size == 2)
                    ans = list.get(0) + list.get(1)
            }
            else{
                if(list.get(0) > list.get(1)){
                    list.set(0,i - map.get(pre - target)!!)
                    ans = list.get(0) + list.get(1)
                }else if(list.get(0) < list.get(1)){
                    list.set(1,i - map.get(pre - target)!!)
                    ans = list.get(0) + list.get(1)
                }
            }
        }
        map.merge(pre,i,Int::plus)
    }
    return ans
}

// 1546
fun maxNonOverlapping(nums: IntArray, target: Int): Int {
    var leftindex = -1
    var map = mutableMapOf<Int, Int>()
    map.put(0,-1)
    var pre = 0
    var ans = 0
    for(i in nums.indices){
        pre += nums[i]
        if(map.containsKey(pre - target)){
            if(map.get(pre - target)!! + 1 > leftindex){
                ans++
                leftindex = i
            }
        }
        map.put(pre,i)
    }
    return ans
}

// 1124
fun longestWPI(hours: IntArray): Int {
    val map = mutableMapOf<Int, Int>()
    var pre = 0
    var ans = 0
    for (i in hours.indices){
        pre += if(hours[i] > 8) 1 else -1
        if(hours[i] > 0){
            ans = i + 1
        } else{
            if(map.containsKey(pre - 1))
                ans = max(ans,i - map.get(pre - 1)!!)
        }
        map.putIfAbsent(pre,i)
    }
    return ans
}

// 3381
// n^2复杂度
fun maxSubarraySum(nums: IntArray, k: Int): Long {
    val sum = LongArray(nums.size + 1)
    for(i in 1..sum.size - 1){
        sum[i] = sum[i - 1] + nums[i - 1]
    }
    var ans = Long.MIN_VALUE
    for(i in sum.indices){
        var j = 1
        while(i - j * k >= 0){
            ans = max(ans,sum[i] - sum[i - j * k])
            j++
        }
    }
    return ans
}

// 3381
// n复杂度
fun maxSubarraySum1(nums: IntArray, k: Int): Long {
    val sum = LongArray(nums.size + 1)
    for(i in 1..sum.size - 1){
        sum[i] = sum[i - 1] + nums[i - 1]
    }
    // 哈希表键为下标，值为最小前缀和
    val map = mutableMapOf<Int, Long>()
    map.put(0,0)
    var ans = Long.MIN_VALUE
    for(i in sum.indices){
        //每次找下标比自己小2的，并把前缀最小值更新
        if(map.containsKey(i - k)) {
            map.put(i, min(map.get(i - k)!!, sum[i]))
            ans = max(ans,sum[i] - map.get(i - k)!!)
        }
        else
            map.put(i,sum[i])
    }
    return ans
}

// 1893
fun isCovered(ranges: Array<IntArray>, left: Int, right: Int): Boolean {
    var size = 0
    var size1 = Int.MAX_VALUE
    for (range in ranges) {
        size = max(size,range[1])
        size1 = min(size1,range[0])
    }
    val diff = IntArray(size + 2)
    for (range in ranges) {
        diff[range[0]]++
        diff[range[1] + 1]--
    }
    var sum = 0
    if(right > size || left < size1)
        return false
    for(i in 1..diff.size - 1){
        sum += diff[i]
        if(sum == 0 && (i >= left && i <= right)){
            return false
        }
    }
    return true
}

// 1854
fun maximumPopulation(logs: Array<IntArray>): Int {
    var max = 0
    var min = Int.MAX_VALUE
    for (log in logs) {
        max = max(max,log[1])
        min = min(min,log[0])
    }
    max -= 1950
    min -= 1950
    val diff = IntArray(max - min + 1 + 1)
    var ans = 0
    for(log in logs) {
        diff[log[0] - 1950]++
        diff[log[1] - 1950 + 1]--
    }
    var sum = 0
    var index = 0
    for(i in 0..diff.size - 1){
        sum += diff[i]
        if(sum > ans){
            index = i
            ans = sum
        }
    }
    return index
}

// 2960
// 差分数组、差分思想
fun countTestedDevices(batteryPercentages: IntArray): Int {
    val diff = IntArray(batteryPercentages.size + 1)
    diff[0] = batteryPercentages[0]
    for(i in 1..batteryPercentages.size-1){
        diff[i] = batteryPercentages[i] - batteryPercentages[i - 1]
    }
    var ans = 0
    var sum = 0
    for(i in 0..< diff.size - 1){
        sum += diff[i]
        if(sum > 0){
            diff[i + 1]--
            ans++
        }
    }
    return ans
}

// 1094
fun carPooling(trips: Array<IntArray>, capacity: Int): Boolean {
    var max = 0
    var min = Int.MAX_VALUE
    for (trip in trips) {
        max = max(max,trip[2])
        min = min(min,trip[1])
    }
    val t = min
    max -= t
    min -= t
    val diff = IntArray(max - min + 1 + 1)
    var pre = 0
    for (trip in trips) {
        diff[trip[1] - t] += trip[0]
        diff[trip[2] - t + 1] -= trip[0]
    }
    for(i in 0..<diff.size - 1){
        pre += diff[i]
        if(pre > capacity)
            return false
    }
    return true
}

// 1109
fun corpFlightBookings(bookings: Array<IntArray>, n: Int): IntArray {
    val diff = IntArray(n + 1)
    for (booking in bookings) {
        diff[booking[0]] += booking[2]
        diff[booking[1] + 1] -= booking[2]
    }
    var pre = 0
    val ans = IntArray(diff.size - 1)
    for(i in 0..diff.size - 2){
        pre += diff[i]
        ans[i] = pre
    }
    return ans
}

// 3355
fun isZeroArray(nums: IntArray, queries: Array<IntArray>): Boolean {
    val diff = IntArray(nums.size + 1)
    diff[0] = nums[0]
    for(i in 1..diff.size - 2){
        diff[i] = nums[i] - nums[i - 1]
    }
    for (quer in queries) {
        diff[quer[0]]--
        diff[quer[1] + 1]++
    }
    var pre = 0
    for(i in 0..diff.size - 2){
        pre += diff[i]
        if(pre > 0)
            return false
    }
    return true
}

//// 56
//fun merge(intervals: Array<IntArray>): Array<IntArray> {
//    var max = 0
//    var min = Int.MAX_VALUE
//    for (inter in intervals) {
//        max = max(max,inter[1])
//        min = min(min,inter[0])
//    }
//    val t = min
//    min -= t
//    max -= t
//    val diff = IntArray(max - min + 1 + 1)
//    var pre = 0
//    for (inter in intervals) {
//        diff[inter[0] - t]++
//        diff[inter[1] - t + 1]++
//    }
//    var left = -1
//    val ans = arrayListOf<IntArray>()
//    for(i in 0..diff.size - 2){
//        pre += diff[i]
//        if(pre > 0 && left == -1)
//            left = i
//        if(pre == 0 && left != -1){
//            ans.add(intArrayOf(left + 1, i))
//            left = -1
//        }
//    }
//    return ans.toTypedArray()
//}

// 56
fun merge(intervals: Array<IntArray>): Array<IntArray> {
    if(intervals.isEmpty()) return intervals
    intervals.sortBy { it[0] }
    val ans = mutableListOf<IntArray>()
    val current = intervals[0]
    ans.add(current)
    for(i in 1..intervals.size-1){
        val next = intervals[i]
        if(next[0] <= current[1]) {
            current[1] = max(current[1],next[1])
        } else{
            ans.add(next)
        }
    }
    return ans.toTypedArray()
}

// 57
fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
    if(intervals.isEmpty()) return arrayOf(newInterval)
    var current = intervals[0]
    val ans = mutableListOf<IntArray>()
    for(i in 0..intervals.size-1){
        if(newInterval[0] <= intervals[i][1]) {
            current[1] = max(newInterval[0],current[1])
        }else {
            ans.add(current)
            if(i == intervals.size - 1){
                ans.add(intervals[i])
                break
            }
            current = intervals[i + 1]
        }
    }
    return ans.toTypedArray()
}

// 1441
fun buildArray(target: IntArray, n: Int): List<String> {
    var ans = mutableListOf<String>()
    var i = 1; var j = 0
    while(i <= n && j < target.size){
        ans.add("Push")
        if(i != target[j]) ans.add("Pop")
        else j++
        i++
    }
    return ans
}

// 844
fun backspaceCompare(s: String, t: String): Boolean {
    var st1 = mutableListOf<Char>()
    var st2 = mutableListOf<Char>()
    for (c in s) {
        st1.add(c)
        if(c == '#')
            st1.removeLast()
    }
    for (c in t) {
        st2.add(c)
        if(c == '#')
            st2.removeLast()
    }
    return st1 == st2
}

// 682
fun calPoints(operations: Array<String>): Int {
    var sta = mutableListOf<Int>()
    var ans = 0
    for (num in operations) {
        if(num.get(0) != 'C' || num.get(0) != 'D' || num.get(0) != '+') {
            sta.add(num.toInt())
            ans += sta.last()
        }
        if(num.get(0) == 'C'){
            ans -= sta.last()
            sta.removeLast()
        }
        if(num.get(0) == 'D') {
            sta.add(sta.last() * 2)
            ans += sta.last()
        }
        if(num.get(0) == '+'){
            sta.add(sta.last() + sta.get(sta.lastIndex - 1))
            ans += sta.last()
        }
    }
    return ans
}

// 2390
fun removeStars(s: String): String {
    var sta = mutableListOf<Char>()
    for (ch in s) {
        if(ch == '*')
            if(!sta.isEmpty())
                sta.removeLast()
        else
            sta.add(ch)
    }
    return sta.joinToString()
}

class BrowserHistory(homepage: String) {
    val sta = mutableListOf<String>()
    var top = -1
    init{
        sta.add(homepage)
        top = sta.lastIndex
    }

    fun visit(url: String) {
        var top1 = sta.lastIndex
        while(top1 > top) {
            sta.removeLast()
            top1--
        }
        sta.add(url)
        top = sta.lastIndex
    }

    fun back(steps: Int): String {
        if(steps > top)
            top = 0
        else
            top -= steps
        return sta.get(top)
    }

    fun forward(steps: Int): String {
        if(steps > sta.size - (top + 1))
            top = sta.lastIndex
        else
            top += steps
        return sta.get(top)
    }
}

// 946
fun validateStackSequences(pushed: IntArray, popped: IntArray): Boolean {
    var sta = mutableListOf<Int>()
    var i = 0;var j = 0
    while(i < pushed.size && j < popped.size) {
        if(!sta.isEmpty() && popped[j] == sta.last()) {
            sta.removeLast()
            j++
            continue
        }
        if(j < popped.size && (popped[j] != pushed[i] || popped[j] == pushed[i])){
            sta.add(pushed[i++])
            continue
        }
        if(popped[j] != sta.last())
            return false
    }
    return true
}

// 3142
fun calculateScore(s: String): Long {
    // 创建一个包含26个ArrayDeque的数组，用于存储每个字母的索引
    val list = Array<ArrayDeque<Int>>(26) { ArrayDeque<Int>() }
    // 初始化结果变量
    var ans = 0L
    // 将字符串转换为字符数组
    var chars = s.toCharArray()
    // 遍历字符数组
    for ((i,ch) in chars.withIndex()) {
        // 如果当前字符对应的ArrayDeque不为空
        if(list[25 - ch.code].size != 0)
            // 计算当前索引与对应ArrayDeque中最后一个索引的差值，并累加到结果中
            ans += i - list[25 - ch.code].pop()
        else
            // 如果当前字符对应的ArrayDeque为空，则将当前索引添加到对应ArrayDeque中
            list[ch - 'a'].push(i)
    }
    // 返回计算结果
    return ans
}

// 71
fun simplifyPath(path: String): String {
    val split = path.split('/')
    val sta = mutableListOf<String>()
    for (str in split) {
        if(str == "..") {
            if (!sta.isEmpty())
                sta.removeLast()
        }else{
            if(str.isBlank() && str != ".")
                sta.add(str)
        }
    }
    return  sta.joinToString("/","/")
}

// 3170
fun clearStars(s: String): String {
    val chars = s.toCharArray()
    val stas = Array<MutableList<Int>>(26) { mutableListOf() }
    for ((i,ch) in chars.withIndex()) {
        if(ch == '*'){
            for(sta in stas){
                if(!sta.isEmpty())
                    sta.removeLast()
            }
        }else{
            stas[ch - 'a'].add(i)
        }
    }

    var indexs = mutableListOf<Int>()
    for (sta in stas) {
        if(!sta.isEmpty())
            indexs.addAll(sta.toList())
    }
    indexs.sort()

    var ans = StringBuilder()
    for(idx in indexs)
        ans.append(chars[idx])
    return ans.toString()
}

class MinStack() {

    // 使用一个可变列表sta来存储元素及其对应的最小值
    val sta = mutableListOf<Pair<Int,Int>>()

    // 向栈中添加元素
    fun push(`val`: Int) {
        // 如果栈不为空，则将新元素及其与当前最小值的较小值作为一对添加到栈中
        if(sta.isNotEmpty())
            sta.add(`val` to min(sta.last().second,`val`))
        // 如果栈为空，则将新元素及其自身作为一对添加到栈中
        else
            sta.add(`val` to `val`)
    }

    // 从栈中移除元素
    fun pop() {
        // 如果栈不为空，则移除栈顶元素
        if(sta.isNotEmpty())
            sta.removeLast()
    }

    // 获取栈顶元素
    fun top(): Int {
        // 如果栈不为空，则返回栈顶元素的值
        if(sta.isNotEmpty())
            return sta.last().first
        // 如果栈为空，则返回0
        else
            return 0
    }

    // 获取栈中的最小值
    fun getMin(): Int {
        // 如果栈不为空，则返回栈顶元素的最小值
        if(sta.isNotEmpty())
            return sta.last().second
        // 如果栈为空，则返回0
        else
            return 0
    }
}


// 895
class FreqStack() {

    val map = mutableMapOf<Int,Int>()
    val sta = mutableListOf<MutableList<Int>>()

    fun push(`val`: Int) {
        var c = map.getOrDefault(`val`, 0)
        if(c == sta.size)
            sta.add(mutableListOf())
        sta[c].add(`val`)
        map.merge(`val`,1,{a,b -> a + b})
    }

    fun pop(): Int {
        var len = sta.size - 1
        var ans = sta[len].removeLast()
        if(sta[len].isEmpty())
            sta.removeLast()
        map.merge(ans,-1, {a,b -> a + b})
        return ans
    }
}

class DinnerPlates(capacity: Int) {

    val cap = capacity
    val sta = mutableListOf<MutableList<Int>>()
    fun push(`val`: Int) {
        for (st in sta) {
            if(st.size < cap) {
                st.add(`val`)
                break
            }
        }
        sta.add(mutableListOf(`val`))
    }

    fun pop(): Int {
        if(sta.isNotEmpty())
            if(sta.last().isNotEmpty()) {
                val ans = sta.last().removeLast()
                if(sta.last().isEmpty())
                    sta.removeLast()
                return ans
            }
        return -1
    }

    fun popAtStack(index: Int): Int {
        if(sta.isNotEmpty()) {
            if (sta[index].isNotEmpty()) {
                val ans = sta[index].removeLast()
                if (sta[index].isEmpty())
                    sta.removeAt(index)
                return ans
            }
        }
        return -1
    }
}

// 2589
fun findMinimumTime(tasks: Array<IntArray>): Int {
    tasks.sortBy { it[1] }
    val flag: Array<Boolean> = Array(tasks[tasks.size - 1][1] + 1) { false }
    var ans = 0
    for (task in tasks) {
        val start = task[1]
        val end = task[0]
        var time = task[2]
        for(i in start downTo end){
            if(flag[i])
                time--
        }
        if(time == 0)
            continue
        for(i in start downTo end){
            if(flag[i].not()){
                flag[i] = true
                time--
                ans++
            }
            if(time == 0) {
                break
            }
        }
    }
    return ans
}

// 2696
fun minLength(s: String): Int {
    val sta = mutableListOf<Char>()
    for (ch in s) {
        if(sta.isNotEmpty() && (sta.last() == 'B' && ch == 'A' || sta.last() == 'D' && ch == 'C'))
            sta.removeLast()
        else
            sta.add(ch)
    }
    return sta.size
}

// 1047
fun removeDuplicates(s: String): String = buildString{
    s.forEach {
        if(this.isNotEmpty() && this.get(this.lastIndex) == it)
            this.deleteAt(this.lastIndex)
        else
            this.append(it)
    }
}

// 1544
fun makeGood(s: String): String = buildString{
    s.forEach {
        if(this.isNotEmpty() && (it.code + 32 == this.last().code || it.code - 32 == this.last().code))
            this.deleteAt(this.lastIndex)
        else
            this.append(it)
    }
}

// 1003
fun isValid(s: String): Boolean {
    return buildString {
        s.forEach {
            if(this.length >= 2 && it.equals('c') && this.last().equals('b') && this.get(lastIndex - 1).equals('a')){
                this.deleteAt(lastIndex)
                this.deleteAt(lastIndex)
            } else
                this.append(it)
        }
    }.isEmpty()
}

// 2216
fun minDeletion(nums: IntArray): Int {
    val sta = mutableListOf<Int>()
    var count = 0
    for (num in nums) {
        count++
        if(sta.isNotEmpty() && count % 2 == 0 && num == sta.last())
            count--
        else
            sta.add(num)
    }
    if(sta.isNotEmpty() && sta.size % 2 != 0)
        sta.removeLast()
    return nums.size - sta.size
}

fun minDeletion1(nums: IntArray): Int = nums.size - mutableListOf<Int>().apply {
    var count = 0
    for (num in nums) {
        count++
        if(isNotEmpty() && count % 2 == 0 && num == last())
            count--
        else
            add(num)
    }
    if(isNotEmpty() && size % 2 != 0)
        removeLast()
}.size

//fun main() {
//    println(minDeletion(intArrayOf(1, 1, 2, 3, 5)))
//}

// 1209
fun removeDuplicates(s: String, k: Int): String = buildString {
    val sta = mutableListOf<MutableMap<Char, Int>>()
    for (ch in s) {
        if(sta.isNotEmpty() && ch == sta.last().keys.single()) {
                sta[sta.lastIndex].put(ch,(sta[sta.lastIndex].get(ch)!!) + 1)
        }
        else
            sta.add(mutableMapOf(ch to 1))
        if(sta.last().values.single() == k)
            sta.removeLast()
    }
    for (st in sta) {
        var n = st.values.single()
        while(n > 0)
            this.append(st.keys.single())
    }
}

//fun main() {
//    println(buildString {
//        append("A")
//        with(StringBuilder()) { // 新的接收者：内层 StringBuilder
//            append("B")
//            listOf(1).forEach {
//                // this → 当前 with 的接收者（内层 StringBuilder）
//                this@buildString.append("C") // 外层 buildString 的接收者
//                append(it) // 当前 with 的接收者
//            }
//        }
//    }
//// 结果："ACB1"
//    )
//}

//data class Student(name: String,age: Int){ // 此处会出错，数据类的主构造器必须声明成员
//    val name = name
//    val age = age
//}

// 2211
fun countCollisions(directions: String): Int {
    val sta = mutableListOf<Char>()
    var ans = 0
    for (ch in directions) {
        if(ch == 'R')
            sta.add(ch)
        else if(ch == 'L'){
            if(sta.isNotEmpty()){
            while(sta.isNotEmpty() && sta.last() == 'R'){
                ans++
                sta.removeLast()
            }
            // 当前元素一定会停止
            sta.add('S')
            ans++
        }}else {
            while(sta.isNotEmpty() && sta.last() == 'R'){
                ans++
                sta.removeLast()
            }
            sta.add('S')
        }
    }
    return ans
}

//fun main() {
//    asteroidCollision(intArrayOf(-2,-1,1,2))
//}

// 735
fun asteroidCollision(asteroids: IntArray): IntArray {
    val sta = mutableListOf<Int>().apply {
        a@for (num in asteroids) {
            if(num > 0)
                this.add(num)
            else {
                    while(this.isNotEmpty() && this.last() > 0 && this.last() <= abs(num)){
                        if(this.last() == abs(num)){
                            this.removeLast()
                            continue@a
                        }
                        this.removeLast()
                    }
                    if((this.isNotEmpty() && this.last() > 0).not()) {
                        this.add(num)
                    }
            }
        }
    }
    return sta.toIntArray()
}

// 1717
fun maximumGain(s: String, x: Int, y: Int): Int = if(x >= y) delete1(s,x,y) else delete2(s,x,y)

fun delete2(s: String, x: Int, y: Int): Int {
    var ans = 0
    val sta1 = mutableListOf<Char>()
    for (ch in s) {
        if(sta1.isNotEmpty() && sta1.last() == 'b' && ch == 'a'){
            ans += y
            sta1.removeLast()
        }else {
            sta1.add(ch)
        }
    }
    val sta2 = mutableListOf<Char>()
    for (ch in sta1) {
        if(sta2.isNotEmpty() && sta2.last() == 'a' && ch == 'b'){
            ans += x
            sta2.removeLast()
        }else
            sta2.add(ch)
    }
    return ans
}

fun delete1(s: String, x: Int, y: Int): Int {
    var ans = 0
    val sta1 = mutableListOf<Char>()
    for (ch in s) {
        if(sta1.isNotEmpty() && sta1.last() == 'a' && ch == 'b'){
            ans += x
            sta1.removeLast()
        }else {
            sta1.add(ch)
        }
    }
    val sta2 = mutableListOf<Char>()
    for (ch in sta1) {
        if(sta2.isNotEmpty() && sta2.last() == 'b' && ch == 'a'){
            ans += y
            sta2.removeLast()
        }else
            sta2.add(ch)
    }
    return ans
}

//fun main() {
//    println(replaceNonCoprimes(intArrayOf(287,41,49,287,899,23,23,20677,5,825)))
//}
// 2179
fun replaceNonCoprimes(nums: IntArray): List<Int> = mutableListOf<Int>().apply {
    nums.forEach{
        if(this.isNotEmpty() && gcd(this.last(),it) > 1){
            val a = this.last()
            this.removeLast()
            this.add(lcm(a,it).toInt())
        }else{
            this.add(it)
            return@forEach
        }
        while(this.size >= 2 && gcd(this.last(),this.get(this.lastIndex - 1)) > 1){
            val a = removeLast()
            val b = removeLast()
            this.add(lcm(a,b).toInt())
        }
    }
}

// 最大公约数
tailrec fun gcd(a: Int,b: Int): Long = if(b == 0) abs(a).toLong() else gcd(b,a % b)
// 最小公倍数
fun lcm(a: Int,b: Int): Long = abs(a.toLong() * b) / gcd(a,b)

//fun main() {
//    survivedRobotsHealths(intArrayOf(3,5,2,6),intArrayOf(10,10,15,12),"RLRL")
//}

// 2751
fun survivedRobotsHealths(positions: IntArray, healths: IntArray, directions: String): List<Int> {
    val arr = Array<Robot>(positions.size){
        Robot(positions[it],healths[it],directions[it],it)
    }
    arr.sortWith(compareBy { it.position }) // 明确指定排序字段
    val sta = mutableListOf<Robot>()
    a@for (robot in arr) {
        if(robot.dire == 'R')
            sta.add(robot)
        else{
            if(sta.isNotEmpty()){
                while(sta.isNotEmpty() && sta.last().dire == 'R'){
                    if(sta.last().health == robot.health) {
                        sta.removeLast()
                        continue@a
                    }
                    else if(sta.last().health > robot.health) {
                         sta.last().health -= 1
                        continue@a
                    } else{
                        sta.removeLast()
                        robot.health -= 1
                    }
                }
            }
            sta.add(robot)
        }
    }
    sta.sortBy { it.hIndex }
    return sta.map { it.health }
}

data class Robot(var position: Int,var health: Int,var dire: Char,var hIndex: Int)

//fun main() {
//    isValid1("([])")
//}
// 20
fun isValid1(s: String): Boolean {
    s.toCharArray().apply outer@ {
        mutableListOf<Char>().apply {
            for (ch in this@outer) {
                if (ch == '(' || ch == '{' || ch == '[')
                    this.add(ch)
                else {
                    when(ch){
                        ')' ->{ if(this.isEmpty()) return false;if(this.last() == '(') this.removeLast(); else return false }
                        ']' ->{ if(this.isEmpty()) return false;if(this.last() == '[') this.removeLast(); else return false }
                        '}' ->{ if(this.isEmpty()) return false;if(this.last() == '{') this.removeLast(); else return false }
                    }
                }
            }
            return this.isEmpty()
        }
    }
}

// 921
fun minAddToMakeValid(s: String): Int {
    var ans = 0; var score = 0
    for(ch in s){
        score += if(ch == '(') 1 else -1
        if(score < 0){
            ans++
            score = 0
        }
    }
    return score + ans
}

fun minAddToMakeValid1(s: String): Int = s.fold(0 to 0){(score,ans),ch ->
    val newScore = score + if(ch == '(') 1 else -1
    if(newScore < 0) 0 to ans + 1 else newScore to ans
}.let{(score,ans) -> score + ans}

// 1021
fun removeOuterParentheses(s: String): String = buildString {
    mutableListOf<Char>().apply {
        s.forEach{
            if(it == '(') this.add(it)
            else {
                this.removeLast()
                if(this.isNotEmpty()) {
                    this@buildString.append('(')
                    this@buildString.append(')')
                }
            }
        }
    }
}

fun removeOuterParentheses1(s: String): String = buildString {
    var count = 0
    s.forEach {
        if(it == ')') count--
        if(count >= 1) append(it)
        if(it == '(') count++
    }
}

// 1614
fun maxDepth(s: String): Int {
    var max = 0
    mutableListOf<Char>().apply {
        s.forEach {
            if(it == '(') add('(')
            if(it == ')') {
                max = max(max,size)
                removeLast()
            }
        }
    }
    return max
}

//// 1190
//fun reverseParentheses(s: String): String {
//    val chars = mutableListOf<Pair<Char, Int>>()
//    val sta = mutableListOf<Pair<Char, Int>>()
//    for((i,ch) in s.withIndex()) {
//        if(ch == '(')
//            sta.add(ch to i)
//        if(ch == ')'){
//            val start = sta.last().second
//            val end = i
//            sta.removeLast()
//            chars.reverse(start + 1,end - 1)
//        }else
//            chars.add(ch to i)
//    }
//
//}

fun MutableList<Char>.reverse(start: Int, end: Int): Unit {
    require(start in indices && end in indices && start <= end){"下标错误"}
    var i = start
    var j = end
    while (i < j) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
        i++
        j--
    }
}

fun reverseParentheses(s: String): String {
    val sta = mutableListOf<String>()
    var s1: String = ""
    s.forEach {
        if(it == '('){
            sta.add(s1)
            s1 = ""
        }else {
            if (it == ')') {
                s1 = sta.last() + s1.reversed()
                sta.removeLast()
            } else
                s1 += it
        }
    }
    return s1
}

// 856
fun scoreOfParentheses(s: String): Int {
    val sta = mutableListOf<Any>()
    s.forEach{it ->
        if(it =='(') sta.add(it)
        if(it == ')'){
            if(sta.last() == '('){
                sta.removeLast()
                sta.add(1)
            }else{
                var a = sta.removeLast()
                var temp = 0
                while(sta.isNotEmpty() && a != '('){
                    temp += a as Int
                    a = sta.removeLast()
                }
                sta.add(temp * 2)
            }
        }
    }
    return sta.fold(0){sum,num->
        sum + num as Int
    }
}

//// 1249
//fun minRemoveToMakeValid(s: String): String {
//    var cnt = 0
//    val list = mutableListOf<Char>().apply {
//        s.forEach { ch ->
//            if(ch == '(') {
//                this.add(ch)
//                cnt++
//            }else if(ch == ')'){
//                cnt--
//                this.add(ch)
//                if(cnt < 0) {
//                    this.removeLast()
//                    cnt = 0
//                }
//            }else
//                this.add(ch)
//        }
//    }
//    return buildString{
//        for(ch in list){
//            if(ch == '(' && cnt > 0) {
//                cnt--
//            }else
//                this.append(ch)
//        }
//    }
//}

fun minRemoveToMakeValid(s: String): String = buildString {
    val sta = mutableListOf<Int>()
    val flag = BooleanArray(s.length)
    for((i,ch) in s.withIndex()){
        if(ch == '('){
            sta.add(i)
            flag[i] = true
        }
        if(ch == ')'){
            if(sta.isEmpty())
                flag[i] = true
            else {
                flag[sta.last()] = false
                sta.removeLast()
            }
        }
    }
    for((i,ch) in s.withIndex()){
        if(flag[i] == false)
            append(ch)
    }
}

// 1963
fun minSwaps(s: String): Int = s.let { s ->
    var cnt = 0
    s.forEach { ch ->
        if(ch == '[' && cnt == 0)
            cnt++
        else
            cnt--
    }
    cnt ushr 1
}

// 678
fun checkValidString(s: String): Boolean = s.let {s ->
    fun judge(s: String,a: Int): Boolean{
        var cnt = 0
        s.forEach { ch ->
            when(ch){
                '(' -> cnt += a
                ')' -> cnt -= a
                '*' -> cnt += 1
            }
            if(cnt < 0)
                return false
        }
        return true
    }

    return judge(s,1) && judge(s.reversed(),-1)
}

//fun main() {
//    dailyTemperatures(intArrayOf(30,60,90))
//}
// 739
// 单调栈
// 单调栈适用于只找比自己大的场景
//fun dailyTemperatures(temperatures: IntArray): IntArray {
//    val ans = IntArray(temperatures.size)
//    val sta = mutableListOf<Int>()
//    for (i in 0 downTo  temperatures.size - 1){
//        while (sta.isNotEmpty() && temperatures[sta.last()] <= temperatures[i])
//            sta.removeLast()
//        if(sta.isNotEmpty()){
//            ans[i] = sta.last() - i
//        }
//        sta.add(i)
//    }
//    return ans
//}

fun dailyTemperatures(temperatures: IntArray): IntArray = IntArray(temperatures.size).apply arr@{
    ArrayDeque<Int>().apply {
        temperatures.indices.reversed().forEach { i ->
            while (isNotEmpty() && temperatures[first()] <= temperatures[i])
                removeFirst()
            this@arr[i] = firstOrNull()?.minus(i) ?: 0
            addFirst(i)
        }
    }
}

// 1475
fun finalPrices(prices: IntArray): IntArray = prices.clone().apply arr@{
    ArrayDeque<Int>().apply {
        prices.indices.reversed().forEach { i ->
            while(this.isNotEmpty() && this.first() >= prices[i])
                this.removeFirst()
            if(this.isNotEmpty())
                this@arr[i] = this@arr[i] - this.first()
            this.addFirst(prices[i])
        }
    }
}

//fun main() {
//    nextGreaterElement(intArrayOf(4,1,2),intArrayOf(1,3,4,2))
//}
// 496
fun nextGreaterElement(nums1: IntArray, nums2: IntArray): IntArray = IntArray(nums1.size){ -1 }.apply outer@ {
    val hash = mutableMapOf<Int, Int>().apply {
        nums1.indices.forEach { i ->
            this.put(nums1[i],i)
        }
    }
    ArrayDeque<Int>().apply {
        nums2.indices.reversed().forEach { i ->
            while (this.isNotEmpty() && this.first() < nums2[i])
                this.removeFirst()
            if(this.isNotEmpty() && hash.containsKey(nums2[i]))
                this@outer[hash[nums2[i]]!!] = this.first()
            this.addFirst(nums2[i])
        }
    }
}

// 503
fun nextGreaterElements(nums: IntArray): IntArray = IntArray(nums.size){-1}.apply outer@{
    ArrayDeque<Int>().apply {
        for(i in nums.size - 1 downTo 0){
            while(this.isNotEmpty() && this.first() <= nums[i])
                this.removeFirst()
            if(this.isNotEmpty())
                this@outer[i] = this.first()
            this.addFirst(nums[i])
        }
        for(i in nums.size - 1 downTo 0){
            while(this.isNotEmpty() && this.first() <= nums[i])
                this.removeFirst()
            if(this.isNotEmpty())
                this@outer[i] = this.first()
            this.addFirst(nums[i])
        }
    }
}

// 901
class StockSpanner() {

    val sta = ArrayDeque<IntArray>()
    var now = 0

    init {
        sta.addFirst(intArrayOf(Int.MAX_VALUE,0))
    }

    fun next(price: Int): Int {
        var ans = 0
        while(sta.first()[0] <= price)
            sta.removeFirst()
        ans = ++now - sta.first()[1]
        sta.addFirst(intArrayOf(price,now))
        return ans
    }
}

// 853
fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {
    Array(position.size){i ->
        intArrayOf(position[i],speed[i])
    }.sortedBy{it[0]}.apply outer@ {
        fun judgeSpeed(pos1: Int,speed1: Int,pos2: Int,speed2: Int,tar: Int): Boolean = (
                (tar - pos1) * speed2 >= (tar - pos2) * speed1
                )
        var ans = 0
        ArrayDeque<IntArray>().apply inner@{
            this@outer.indices.reversed().forEach { i ->
                var stalast: IntArray = this@outer.last()
                while (this.isNotEmpty() && this.first()[1] >= this@outer[i][1]) {
                    stalast = this.removeFirst()
                }
                if(this.isEmpty && judgeSpeed(stalast[0],stalast[1],this@outer[i][0],this@outer[i][1],target))
                    ans++
                this.addFirst(intArrayOf(this@outer[i][0],this@outer[i][1]))
            }
        }
        return ans
    }
}

// 962
fun maxWidthRamp(nums: IntArray): Int {
    ArrayDeque<Int>().apply {
        nums.indices.forEach { i ->
            if(this.isEmpty || nums[i] < this.first)
                this.addFirst(i)
        }
        var ans = 0
        for(i in nums.size - 1 downTo 0){
            while(this.isNotEmpty() && nums[this.first] < nums[i]){
                ans = max(ans,i - this.first)
                this.removeFirst()
            }
        }
        return ans
    }
}

// 1124
fun longestWPI1(hours: IntArray): Int {
    val nums = IntArray(hours.size + 1)
    for(i in 1..nums.size - 1){
        nums[i] = nums[i - 1] + hours[i - 1]
    }
    ArrayDeque<Int>().apply {
        nums.indices.forEach { i ->
            if(this.isEmpty || nums[i] < nums[this.first()])
                this.addFirst(i)
        }
        var ans = 0
        for(i in nums.size - 1 downTo 0){
            while(this.isNotEmpty() && nums[this.first()] <= nums[i]){
                ans = max(ans,i - this.first())
                this.removeFirst()
            }
        }
        return ans
    }
}

// 84
fun largestRectangleArea(heights: IntArray): Int {
    val left = IntArray(heights.size).apply out@{
        ArrayDeque<Int>().apply {
            heights.indices.forEach { i ->
                while(this.isNotEmpty() && this.first() >= heights[i])
                    this.removeFirst()
                this@out[i] = this.firstOrNull() ?: -1
                this.addFirst(heights[i])
            }
        }
    }
    val right = IntArray(heights.size).apply out@{
        ArrayDeque<Int>().apply {
            heights.indices.reversed().forEach { i ->
                while(this.isNotEmpty() && heights[this.first()] >= heights[i])
                    this.removeFirst()
                this@out[i] = this.firstOrNull() ?: heights.size
                this.addFirst(i)
            }
        }
    }

    var ans = 0
    heights.indices.forEach { i ->
        ans = max(ans,(right[i] - left[i] - 1) * heights[i])
    }
    return ans
}

// 1793
fun maximumScore(nums: IntArray, k: Int): Int {
    val left = IntArray(nums.size).apply out@{
        ArrayDeque<Int>().apply {
            nums.indices.forEach { i ->
                while(this.isNotEmpty() && nums[this.first()] >= nums[i])
                    this.removeFirst()
                this@out[i] = this.firstOrNull() ?: -1
                this.addFirst(i)
            }
        }
    }
    val right = IntArray(nums.size).apply out@{
        ArrayDeque<Int>().apply {
            nums.indices.reversed().forEach { i ->
                while(this.isNotEmpty() && nums[this.first()] >= nums[i])
                    this.removeFirst()
                this@out[i] = this.firstOrNull() ?: nums.size
                this.addFirst(i)
            }
        }
    }
    var ans = 0
    nums.indices.forEach { i ->
        if(left[i] <= k && right[i] >= k)
            ans = max(ans,(right[i] - left[i] - 1) * nums[i])
    }
    return ans
}

// 85
fun maximalRectangle(matrix: Array<CharArray>): Int {
    val n = matrix.size
    val m = matrix[0].size
    val arr = Array<IntArray>(n + 10){ IntArray(m + 10) }
    for(i in 1..n)
        for(j in 1..m)
            arr[i][j] = if(matrix[i - 1][j - 1] == '0') 0 else arr[i - 1][j] + 1
    val left = IntArray(m)
    val right = IntArray(m)
    var ans = 0
    for(i in 1..n){
        ArrayDeque<Int>().apply {
            arr[i].indices.forEach { j ->
                while(this.isNotEmpty() && arr[i][this.first()] >= arr[i][j])
                    this.removeFirst()
                left[i] = this.firstOrNull() ?: -1
                this.addFirst(i)
            }
        }
        ArrayDeque<Int>().apply {
            arr[i].indices.reversed().forEach { j ->
                while(this.isNotEmpty() && arr[i][this.first()] >= arr[i][j])
                    this.removeFirst()
                right[i] = this.firstOrNull() ?: -1
                this.addFirst(i)
            }
        }
        for(j in 1..m)
            ans = max(ans,(right[j - 1] - left[j - 1] - 1) * arr[i][j])
    }
    return ans
}

// 2104
fun subArrayRanges(nums: IntArray): Long {
    var ans = 0L
    val left1 = IntArray(nums.size).apply out@{
        ArrayDeque<Int>().apply {
            this.addFirst(-1)
            nums.indices.forEach { i ->
                while(this.size > 1 && nums[this.first()] > nums[i])
                    this.removeFirst()
                this@out[i] = this.first()
                this.addFirst(i)
            }
        }
    }
    val right1 = IntArray(nums.size).apply out@{
        ArrayDeque<Int>().apply {
            this.addFirst(nums.size)
            nums.indices.reversed().forEach { i ->
                while(this.size > 1 && nums[this.first()] >= nums[i])
                    this.removeFirst()
                this@out[i] = this.first()
                this.addFirst(i)
            }
        }
    }
    val left2 = IntArray(nums.size).apply out@{
        ArrayDeque<Int>().apply {
            this.addFirst(-1)
            nums.indices.forEach { i ->
                while(this.size > 1 && nums[this.first()] < nums[i])
                    this.removeFirst()
                this@out[i] = this.first()
                this.addFirst(i)
            }
        }
    }
    val right2 = IntArray(nums.size).apply out@{
        ArrayDeque<Int>().apply {
            this.addFirst(nums.size)
            nums.indices.reversed().forEach { i ->
                while(this.size > 1 && nums[this.first()] <= nums[i])
                    this.removeFirst()
                this@out[i] = this.first()
                this.addFirst(i)
            }
        }
    }
    for(i in 0..nums.size - 1){
        ans += nums[i].toLong() * (i - left2[i]) * (right2[i] - i)
        ans -= nums[i].toLong() * (i - left1[i]) * (right1[i] - i)
    }
    return ans
}

// 1006
fun clumsy(n: Int): Int = ArrayDeque<Int>().apply {
    this.addFirst(n)
    var item = 0
    for(i in n-1 downTo 1){
        if(item == 0)
            this.addFirst(this.removeFirst() * i)
        if(item == 1)
            this.addFirst(this.removeFirst() / i)
        if(item == 2)
            this.addFirst(i)
        if(item == 3)
            this.addFirst(-i)
        item = (item + 1) % 4
    }
}.sum()

//// 150
//fun evalRPN(tokens: Array<String>): Int{
//    ArrayDeque<Int>().apply {
//        for(token in tokens){
//            val ch = token[0]
//            when(ch) {
//                    '*' -> this.addFirst(this.removeFirst() * this.removeFirst())
//                    '/' -> {
//                        val a = this.removeFirst()
//                        val b = this.removeFirst()
//                        this.addFirst(b / a)
//                    }
//                    '+' -> this.addFirst(this.removeFirst() + this.removeFirst())
//                    '-' -> {
//                        if(token.length == 1) {
//                            val a = this.removeFirst()
//                            val b = this.removeFirst()
//                            this.addFirst(b - a)
//                        }else
//                            this.addFirst(-(token[1].code - '0'.code))
//                    }
//                    else -> {
//                        this.addFirst(ch.code - '0'.code)
//                    }
//            }
//        }
//        return this.first()
//    }
//}


//fun main() {
//    println(calculate("1-(     -2)"))
//}
//// 224
//fun calculate(s: String): Int {
//    if(s[0] == '0')
//        return 0
//    val operateSta = ArrayDeque<Char>()
//    val numSta = ArrayDeque<Int>()
//    var num = 0
//    s.forEach { ch ->
//        if(ch.isDigit())
//            num = num * 10 + (ch.code - '0'.code)
//        else if(ch == '(' || ch == '+' || ch == '-'){
//            if(num != 0){
//                numSta.addFirst(num)
//                num = 0
//            }
//            operateSta.addFirst(ch)
//        }else if(ch == ')'){
//            if(num != 0){
//                numSta.addFirst(num)
//                num = 0
//            }
//            while(operateSta.isNotEmpty() && operateSta.first() != '('){
//                val a = numSta.removeFirst()
//                val b = numSta.removeFirst()
//                if(operateSta.first() == '+')
//                    numSta.addFirst(b + a)
//                if(operateSta.first() == '-')
//                    numSta.addFirst(b - a)
//                operateSta.removeFirst()
//            }
//            if(operateSta.isNotEmpty() && operateSta.first() == '(')
//                operateSta.removeFirst()
//        }
//    }
//    if(num != 0)
//        numSta.addFirst(num)
//    while(operateSta.isNotEmpty()){
//        val a = numSta.removeLast()
//        val b = numSta.removeLast()
//        if(operateSta.last() == '+')
//            numSta.addLast(a + b)
//        if(operateSta.last() == '-')
//            numSta.addLast( a - b)
//        operateSta.removeLast()
//    }
//    return numSta.first()
//}

//fun main() {
//    println(calculate("-2+ 1"))
//}
// 224
fun calculate(s: String): Int{
    val s1 = s.replace(" ","")
    val staNum = ArrayDeque<Int>()
    val staOpe = ArrayDeque<Char>()
    var i = 0
    staNum.addFirst(0)
    while(i < s1.length){
        if(s1[i] =='(')
            staOpe.addFirst(s1[i])
        else {
            if(s1[i] == ')'){
                while(staOpe.first() != '(')
                    operate(staOpe,staNum)
                if(staOpe.first() == '(')
                    staOpe.removeFirst()
            }else{
                if(s1[i].isDigit()){
                    var num = 0
                    while((i < s1.length) && s1[i].isDigit())
                        num = num * 10 + (s1[i++].code - '0'.code)
                    i--
                    staNum.addFirst(num)
                }else{
                    if(i > 0 && s1[i - 1] == '(')
                        staNum.addFirst(0)
                    while(staOpe.isNotEmpty() && staOpe.first() != '(')
                       operate(staOpe,staNum)
                    staOpe.addFirst(s1[i])
                }
            }
        }
        i++
    }
    while(staOpe.isNotEmpty())
        operate(staOpe,staNum)
    return staNum.first()
}

fun operate(staOpe: ArrayDeque<Char>, staNum: ArrayDeque<Int>){
    if(staNum.size < 2 || staOpe.isEmpty)
        return
    val b = staNum.removeFirst()
    val a = staNum.removeFirst()
    val op = staOpe.removeFirst()
    staNum.addFirst(if(op == '+') a + b else a - b)
}

//fun main() {
//    println(calculate1("3+2*2"))
//}
// 227
fun calculate1(s: String): Int {
    val s1 = s.replace(" ","")
    val staNum = ArrayDeque<Int>()
    val staOpe = ArrayDeque<Char>()
    staNum.addFirst(0)
    var num = 0
    for (ch in s1) {
        when(ch){
            in '0'..'9' -> {
                num = num * 10 + (ch.code - '0'.code)
            }
            '+','-' -> {
                staNum.addFirst(num)
                while(staOpe.isNotEmpty())
                    operate1(staOpe,staNum)
                staOpe.addFirst(ch)
                num = 0
            }
            '*','/' -> {
                staNum.addFirst(num)
                while(staOpe.isNotEmpty() && staOpe.first() != '+' && staOpe.first() != '-')
                    operate1(staOpe,staNum)
                staOpe.addFirst(ch)
                num = 0
            }
        }
    }
    if(num != 0)
        staNum.addFirst(num)
    while(staOpe.isNotEmpty())
        operate1(staOpe,staNum)
    return staNum.first()
}

fun operate1(staOpe: ArrayDeque<Char>, staNum: ArrayDeque<Int>) {
    if(staOpe.isEmpty()) return
    val b = staNum.removeFirst()
    val a = staNum.removeFirst()
    val ch = staOpe.removeFirst()
    when(ch){
        '+' -> staNum.addFirst(a + b)
        '-' -> staNum.addFirst(a - b)
        '*' -> staNum.addFirst(a * b)
        '/' -> staNum.addFirst(a / b)
    }
}

// 2296
class TextEditor() {

    val left = StringBuilder()
    val right = StringBuilder()

    fun addText(text: String) {
        left.append(text)
    }

    fun deleteText(k: Int): Int {
        val a = left.length
        left.setLength(max(0,a - k))
        return if(k > a) a else k
    }

    fun cursorLeft(k: Int): String {
        var k1 = k
        while(k1 > 0 && left.isNotEmpty()){
            right.append(left.last())
            left.setLength(left.length - 1)
            k1--
        }
        return text()
    }

    fun cursorRight(k: Int): String {
        var k1 = k
        while(k1 > 0 && right.isNotEmpty()){
            left.append(right.last())
            right.setLength(right.length - 1)
            k1--
        }
        return text()
    }

    // 显示左边的元素
    fun text(): String = left.substring(max(0,left.length - 10))

}
