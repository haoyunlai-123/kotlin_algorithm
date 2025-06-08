import java.nio.file.Files
import java.util.Collections
import java.util.PriorityQueue
import kotlin.contracts.Returns
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

// 454
fun fourSumCount(nums1: IntArray, nums2: IntArray, nums3: IntArray, nums4: IntArray): Int {
    hashMapOf<Int,Int>().apply {
        var ans: Int = 0
        for (i1 in nums1)
            for (i2 in nums2)
                merge(i1 + i2,1,Int::plus)

        for (i3 in nums3)
            for (i4 in nums4)
                ans += getOrDefault(-i3-i4,0)
        return ans
    }
}

// 16.24
fun pairSums(nums: IntArray, target: Int): List<List<Int>> {
    hashMapOf<Int,Int>().apply {
        mutableListOf<List<Int>>().apply {
            for (num in nums) {
                val c = getOrDefault(target - num,0)
                if(c > 0){
                    add(listOf(target - num,num))
                    merge(target - num,-1,Int::plus)
                    continue
                }
                merge(num,1,Int::plus)
            }
            return toList()
        }
    }
}

// 447
fun numberOfBoomerangs(points: Array<IntArray>): Int {
    val map = mutableMapOf<Int, Int>()
    var ans = 0
    for (point in points) {
        map.clear()
        for (point2 in points) {
            val dx = point2[0] - point[0]
            val dy = point2[1] - point[1]
            val distance = dx * dx + dy * dy
            val count = map.getOrDefault(distance, 0)
            ans += count * 2       // 累加组合数：C(n,2)*2 = n*(n-1)
            map[distance] = count + 1 // 等价 map.merge(distance, 1, Int::plus)
        }
    }
    return ans
}

// 2588
fun beautifulSubarrays(nums: IntArray): Long {
    val map = HashMap<Int, Int>()
    var pre = 0
    var ans = 0L
    map.put(0,1)
    for(num in nums.indices){
        pre = pre xor num
        val c = map.getOrDefault(pre,0)
        ans += c
        map.put(pre,c + 1)
    }
    return ans
}

// 525
fun findMaxLength(nums: IntArray): Int {
    val map = HashMap<Int, Int>()
    var ans = 0
    map.put(-1,-1)
    var pre = -1
    for ((i,num) in nums.withIndex()) {
        pre += if(num == 1) 1 else -1
        if(map.containsKey(pre)){
            ans = max(ans,i - map.get(pre)!!)
        }else
            map.put(pre,i)
    }
    return ans
}

//fun main() {
//    findMaxLength(intArrayOf(0,1,1,1,1,1,0,0,0))
//}

// 2848
fun numberOfPoints(nums: List<List<Int>>): Int {
    var size = 0
    for (num in nums) {
        size = max(size,num.get(1))
    }
    val diff = IntArray(size + 2)
    for(i in 0..nums.size - 1){
        diff[nums[i].get(0)]++
        diff[nums[i].get(1) + 1]--
    }
    var ans = 0
    for(i in 1..diff.size - 1)
        ans += diff[i]
    return ans
}

// 1893
fun isCovered(ranges: Array<IntArray>, left: Int, right: Int): Boolean {
    var size = 0
    for (range in ranges) {
        size = max(size,range[1])
    }
    val diff = IntArray(size + 2)
    for (range in ranges) {
        diff[range[0]]++
        diff[range[1] + 1]--
    }
    var sum = 0
    for(i in 1..diff.size - 1){
        sum += diff[i]
        if(sum == 0 && (i >= left) && (i >= right)){
            return false
        }
    }
    return true
}

// 1381
class CustomStack(maxSize: Int) {
    var maxsize: Int = 0
    val sta = mutableListOf<Int>()
    val incre = IntArray(maxsize + 1)
    init{
        maxsize = maxSize
    }
    fun push(x: Int) {
        if(sta.size < maxsize)
            sta.add(x)
    }

    fun pop(): Int {
        if(sta.isNotEmpty())
            return -1
        incre[sta.size] += incre[sta.size + 1]
        return sta.last() + incre[sta.size + 1]
    }

    fun increment(k: Int, `val`: Int) {
        incre[min(k,maxsize)] += `val`
    }
}

// 636
fun exclusiveTime(n: Int, logs: List<String>): IntArray {
    var sta = mutableListOf<Task>()
    var ans = IntArray(n)
    for (log in logs) {
        var task = Task(log.split(":"))
        if(task.isStart)
            sta.add(task)
        else{
            var time = task.time - sta.last().time + 1
            ans[task.id] += time
            sta.removeLast()
            if(sta.isNotEmpty())
                ans[sta.last().id] -= time
        }
    }
    return ans
}

class Task(strs: List<String>){
    var id = 0
    var isStart = true
    var time = 0
    init{
        id = strs[0].toInt()
        isStart = if(strs[1] == "start") true else false
        time = strs[2].toInt()
    }
}

// 2434
fun robotWithString(s: String): String {
    val sta = mutableListOf<Char>()
    val chars = s.toCharArray()
    val cnt = IntArray(26)
    val ans = StringBuilder()
    for (ch in chars) {
        cnt[ch - 'a']++
    }
    var min = 0
    for(ch in chars){
        for(i in 0..cnt.size - 1){
            if(cnt[i] != 0) {
                min = i;
                break
            }
        }
        sta.add(ch)
        while(sta.isNotEmpty() && sta.last() - 'a' <= min){
            cnt[min]--
            ans.append(sta.last())
            sta.removeLast()
        }
    }
    return ans.toString()
}

// 907
fun sumSubarrayMins(arr: IntArray): Int {
    val mod: Long = 10e9.toLong()+7
    val left = IntArray(arr.size).apply out@{
        ArrayDeque<Int>().apply {
            this.addFirst(-1)
            arr.indices.forEach {i ->
                while(this.size > 1  && arr[this.first()] >= arr[i])
                    this.removeFirst()
                this@out[i] = this.first()
                this.addFirst(i)
            }
        }
    }
    val right = IntArray(arr.size).apply out@{
        ArrayDeque<Int>().apply {
            this.addFirst(arr.size)
            arr.indices.reversed().forEach {i ->
                while(this.size > 1  && arr[this.first()] >= arr[i])
                    this.removeFirst()
                this@out[i] = this.first()
                this.addFirst(i)
            }
        }
    }
    var ans = 0L
    for(i in 0..arr.size - 1){
        ans += arr[i].toLong() * (right[i] - i) * (i - left[i])
    }
    return (ans % mod).toInt()
}

// 402
fun removeKdigits(num: String, k: Int): String {
    val str = buildString {
        var k1 = k
        num.indices.forEach { i ->
            while(this.isNotEmpty() && k1 > 0 && this.last().code - '0'.code >= num[i].code - '0'.code) {
                this.deleteAt(this.lastIndex)
                k1--
            }
            if(num[i] == '0' && this.isEmpty())
                return@forEach
            this.append(num[i])
        }
        while(k1 > 0 && this.isNotEmpty()){
            this.deleteAt(this.lastIndex)
            k1--
        }
    }
    return if(str.isNotEmpty()) str else "0"
}

//fun main() {
//    mostCompetitive(intArrayOf(3,5,2,6),2)
//}
// 1673
fun mostCompetitive(nums: IntArray, k: Int): IntArray {
   val sta =  ArrayDeque<Int>().apply {
        var k1 = k
        for(i in 0..nums.size - 1){
            while(this.isNotEmpty() && this.last() > nums[i] && this.size + nums.size - i > k1)
                this.removeLast()
            this.addLast(nums[i])
        }
       while(this.size > k1)
           this.removeLast()
    }
    return sta.toIntArray()
}

// 394
fun decodeString(s: String): String {
    val staCount = ArrayDeque<Int>()
    val staString = ArrayDeque<StringBuilder>()
    var str = StringBuilder()
    var count = 0
    s.forEach { ch ->
        if(ch.isDigit())
            count = count * 10 + (ch - '0')
        else if(ch == '['){
                staCount.addFirst(count)
                staString.addFirst(str)
                count = 0
                str = StringBuilder()
            }else if(ch == ']'){
                val k = staCount.removeFirst()
                val str1 = staString.removeFirst()
                str = str1.append(str.toString().repeat(k))
            }else
                str.append(ch)

    }
    return str.toString()
}

//fun main() {
//    pickGifts(intArrayOf(25,64,9,4,100),4)
//}
// 2558
// 最小堆模拟
val heap = IntArray(10010)
var sz = 0
fun pickGifts(gifts: IntArray, k: Int): Long {
    // 最小堆转为最大堆
    for (num in gifts)
        add(-num)
    var k1 = k
    var ans = 0L
    while(k1 > 0){
        val a = -sqrt(-peek().toDouble()).toInt()
        remove()
        add(a)
        k1--
    }
    for(i in 1..sz)
        ans += -heap[i]
    return ans
}

fun swap(i: Int,j: Int){
    val a = heap[i]
    heap[i] = heap[j]
    heap[j] = a
}

fun up(i: Int){
    val fa = i / 2
    if(fa > 0 && heap[fa] > heap[i]){
        swap(i,fa)
        up(fa)
    }
}

fun down(i: Int){
    var cur = i
    val left = i * 2; val right = i * 2 + 1
    if(left <= sz && heap[left] < heap[cur]) cur = left
    if(right <= sz && heap[right] < heap[cur]) cur = right
    if(cur != i){
        swap(cur,i)
        down(cur)
    }
}

fun add(num: Int){
    if(sz < heap.size){
        heap[++sz] = num
        up(sz)
    }
}

fun peek(): Int = heap[1]

fun remove(){
    heap[1] = heap[sz--]
    down(1)
}

// 2558
fun pickGifts1(gifts: IntArray, k: Int): Long {
    val maxheap = PriorityQueue<Int>(Collections.reverseOrder())
    var ans = 0L
    for (num in gifts)
        maxheap.offer(num)
    for(i in 1..k){
        val a = maxheap.poll()
        maxheap.offer(sqrt(a.toDouble()).toInt())
    }
    while(maxheap.isNotEmpty())
        ans += maxheap.poll()
    return ans
}

// 2558
// 最小堆模拟
fun pickGifts2(gifts: IntArray, k: Int): Long {
    // Kotlin中创建最大堆的正确方式
    val maxHeap = PriorityQueue<Int>(compareByDescending { it })
    var ans = 0L
    gifts.forEach { maxHeap.add(it) }  // 使用add替代offer

    repeat(k) {
        if (maxHeap.isEmpty()) return@repeat
        val a = maxHeap.remove()      // 使用remove替代poll
        maxHeap.add(sqrt(a.toDouble()).toInt())
    }

    while (maxHeap.isNotEmpty()) {
        ans += maxHeap.remove()
    }
    return ans
}

// 703
class KthLargest(k: Int, nums: IntArray) {

    var cap = k
    val heap = PriorityQueue<Int>(k,compareBy { it })
    init{
        nums.forEach { heap.add(it) }
    }

    fun add(`val`: Int): Int {
        if(heap.size < cap)
            heap.add(`val`)
        else if(heap.peek() < `val`){
            heap.remove()
            heap.add(`val`)
        }
        return heap.peek()
    }
}

// 3275
// 最大堆
fun resultsArray(queries: Array<IntArray>, k: Int): IntArray {
    val heap = PriorityQueue<Int>(compareByDescending { it })
    val add: (Int) -> Int = add@{ num ->
        heap.add(num)
        if(heap.size > k)
            heap.remove()
        return@add if(heap.size < k) -1 else heap.peek()
    }
    val ans = IntArray(queries.size)
    for((idx,query) in queries.withIndex()){
        ans[idx] = add(abs(query[0] + query[1]))
    }
    return ans
}

//1845
class SeatManager(n: Int) {

    val limt = n
    var idx = 1
    val heap = PriorityQueue<Int>(compareBy { it })

    fun reserve(): Int {
        if(heap.isEmpty() && idx < limt + 1) return idx++
        return heap.remove()
    }

    fun unreserve(seatNumber: Int) = heap.add(seatNumber)

}

// 2208
fun halveArray(nums: IntArray): Int {
    val heap = PriorityQueue<Int>(compareByDescending { it }) // 最大堆
    var sum1 = 0
    nums.forEach {
        heap.add(it)
        sum1 += it
    }
    var sum = 0
    return generateSequence(1){ it + 1 }
        .takeWhile { sum < sum1 / 2}
        .onEach {
            val a = heap.remove()
            heap.add(a / 2)
            sum += a / 2
        }
        .last()
}

// 1642
fun furthestBuilding(heights: IntArray, bricks: Int, ladders: Int): Int {
    var sum = 0
    val heap = PriorityQueue<Int>(compareBy { it })
    var i = 1
    while(i < heights.size){
        var a = heights[i] - heights[i - 1]
        if(a > 0){
            heap.add(a)
            if(heap.size > ladders)
                sum += heap.remove()
            if(sum > bricks)
                return i - 1
        }
    }
    return heights.size - 1
}

// 630
// 反悔堆
fun scheduleCourse(courses: Array<IntArray>): Int {
    courses.sortWith ( compareBy { it[1] } )
    val heap = PriorityQueue<Int>(compareByDescending { it })
    var day = 0
    courses.forEachIndexed { i,arr ->
        val dur = arr[0]; var last = arr[1]
        if(day + dur <= last) {
            heap.add(dur)
            day += dur
        }
        else{
            if(heap.isNotEmpty() && dur < heap.peek()){
            }
        }
    }
    return 0
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

// 203
fun removeElements(head: ListNode?, `val`: Int): ListNode? {
    val dummy = ListNode(0).apply { next = head }
    var cur: ListNode? = dummy
    while(cur?.next != null) {
        if(cur.next?.`val` == `val`) {
            cur.next = cur.next?.next
        } else {
            cur = cur.next
        }
    }
    return dummy.next
}

// 3217
fun modifiedList(nums: IntArray, head: ListNode?): ListNode? {
    val set = mutableSetOf<Int>().apply { addAll(nums.toList()) }
    var dump = ListNode(0).apply { next = head }
    var cur: ListNode? = dump
    while(cur?.next != null) {
        if(set.contains(cur.next?.`val`)){
            cur.next = cur.next?.next
        }else{
            cur = cur.next
        }
    }
    return dump.next
}

// 83
fun deleteDuplicates(head: ListNode?): ListNode? {
    var cur = head
    while(cur?.next != null) {
        if(cur.next?.`val` == cur.`val`)
            cur.next = cur.next?.next
        else{
            cur = cur.next
        }
    }
    return head
}

// 82
fun deleteDuplicates1(head: ListNode?): ListNode? {
    var dump = ListNode(0).apply { next = head }
    var cur: ListNode? = dump
    while(cur?.next != null && cur?.next?.next != null) {
        val num = cur.next?.`val`
        if(cur.next?.next?.`val` == num){
            while(cur.next != null && cur.next?.`val` == num)
                cur.next = cur.next?.next
        }
        else
            cur = cur.next
    }
    return dump.next
}

// 237
fun deleteNode(node: ListNode?) {
    node?.`val` = node?.next?.`val`!!
    node?.next = node?.next?.next
}

// 1669
fun mergeInBetween(list1: ListNode?, a: Int, b: Int, list2: ListNode?): ListNode? {
    val dump = ListNode(0).apply { next = list1 }
    var num = -1
    var cur: ListNode? = list2
    while(cur?.next != null){
        cur = cur.next
    }
    var last2 = cur; cur = dump
    while(cur?.next != null){
        num++
        when{
            num == a && num == b -> {last2?.next = cur.next?.next; cur.next = list2}
            num == a -> {var flag = cur; cur = cur.next; flag.next = list2}
            num == b -> {last2?.next = cur.next?.next; return list1}
            else -> cur = cur.next
        }
    }
    return list1
}

// 19
fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    val dump = ListNode(0).apply { next = head }
    var left: ListNode? = dump
    var right: ListNode? = dump
    repeat(n){
        right = right!!.next
    }
    while(right!!.next != null){
        right = right!!.next
        left = left!!.next
    }
    left!!.next = left!!.next!!.next
    return dump.next
}

// 61
fun rotateRight(head: ListNode?, k: Int): ListNode? {
    if(k == 0)
        return head
    val dump = ListNode(0).apply { next = head }
    var left: ListNode? = dump
    var right: ListNode? = dump
    var ans: ListNode? = head
    var n = 0
    while(ans != null){
        n++
        ans = ans!!.next
    }
    var k1 = k % n
    repeat(k1){
        right = right!!.next
    }
    while(right!!.next != null){
        left = left!!.next
        right = right!!.next
    }
    right.next = head
    ans = left!!.next
    left!!.next = null
    return ans
}

// 1721
fun swapNodes(head: ListNode?, k: Int): ListNode? {
    var left: ListNode? = head
    var right: ListNode? = head
    var flag: ListNode? = null
    repeat(k - 1){
        right = right!!.next
    }
    flag = right
    while(right!!.next != null){
        right = right.next
        left = left!!.next
    }
    var temp = flag!!.`val`
    flag.`val` = left!!.`val`
    left!!.`val` = temp
    return head
}

// 876
// 快慢指针
fun middleNode(head: ListNode?): ListNode? {
    var fast: ListNode? = head
    var slow: ListNode? = head
    while(fast != null && fast.next != null){
        fast = fast.next!!.next
        slow = slow!!.next
    }
    return slow
}

// 2095
fun deleteMiddle(head: ListNode?): ListNode? {
    val dump = ListNode(0).apply { next = head }
    var fast: ListNode? = dump
    var slow: ListNode? = dump
    while(fast?.next != null){
        fast = fast.next!!.next!!.next
        slow = slow!!.next
    }
    slow?.next = slow.next?.next
    return head
}

// 234
fun isPalindrome(head: ListNode?): Boolean {
    var mid = findMid(head)
    var first2 = reverseListNode(mid)
    var first1 = head
    while(first2 != null){
        if(first1?.`val` != first2?.`val`)
            return false
        first1 = first1?.next
        first2 = first2?.next
    }
    return true
}

fun reverseListNode(head: ListNode?): ListNode?{
    var pre: ListNode? = null
    var cur = head
    while(cur != null){
        val nxt = cur?.next
        cur.next = pre
        pre = cur
        cur = nxt
    }
    return pre
}

fun findMid(head: ListNode?): ListNode?{
    var fast = head; var slow = head
    while(fast != null && fast?.next != null){
        fast = fast.next!!.next
        slow = slow!!.next
    }
    return slow
}

// 2130
fun pairSum(head: ListNode?): Int {
    val mid = findMid(head)
    var first2 = reverseListNode(mid)
    var max = 0
    var first1 = head
    while(first2 != null){
        max = max(max,first1!!.`val` + first2!!.`val`)
        first1 = first1!!.next
        first2 = first2!!.next
    }
    return max
}

// 143
fun reorderList(head: ListNode?): Unit {
    if(head?.next == null || head?.next?.next == null) return
    var mid = findMid(head)
    var first2 = reverseListNode(mid?.next)
    var first1 = head
    mid?.next = null
    while(first2 != null){
        val nxt = first2.next
        first2.next = first1?.next
        first1?.next = first2
        first1 = first2?.next
        first2 = nxt
    }
}

// 141
fun hasCycle(head: ListNode?): Boolean {
    if(head?.next == head) return true
    if(head?.next == null) return false
    var fast = head.next?.next
    var slow = head.next
    while(fast != null && fast != slow){
        fast = fast?.next?.next
        slow = slow?.next
    }
    if(fast == slow)
        return true
    return false
}

// 142
fun detectCycle(head: ListNode?): ListNode? {
    var fast = head
    var slow = head
    var flag = head
    while(fast != null && fast.next != null){
        fast = fast.next?.next
        slow = slow?.next
        if(fast == slow){
            while(flag != slow){
                flag = flag?.next
                slow = slow?.next
            }
            return slow
        }
    }
    return null
}

// 475
//fun circularArrayLoop(nums: IntArray): Boolean {
//    var hash = IntArray(nums.size)
//    for(i in 0 until nums.size){
//        if(hash[i] == 0){
//            while(hash[i] == 0 && )
//        }
//    }
//    return false
//}

// 328
fun oddEvenList(head: ListNode?): ListNode? {
    if(head == null) return null
    var oddFirst = head
    var evenFirst = head.next; var evenHead = evenFirst
    while(evenFirst != null && evenFirst.next != null){
        oddFirst?.next = evenFirst.next
        oddFirst = oddFirst?.next
        evenFirst.next = oddFirst?.next
        evenFirst = evenFirst?.next
    }
    oddFirst?.next = evenHead
    return head
}

// 86
fun partition(head: ListNode?, x: Int): ListNode? {
    val dumpSmall = ListNode(0).apply { next = head }
    val dumpBig = ListNode(0).apply { next = head }
    var s: ListNode? = dumpSmall
    var b: ListNode? = dumpBig
    var cur = head
    while(cur != null){
        if(cur.`val` < x){
            s?.next = cur
            s = cur
        }else {
            b?.next = cur
            b = cur
        }
        cur = cur?.next
    }
    s?.next = dumpBig.next
    b?.next = null
    return dumpSmall.next
}



