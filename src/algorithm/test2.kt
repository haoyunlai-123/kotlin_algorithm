package algorithm

import java.nio.file.Paths
import java.util.*
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

class test2 {

    // 933
    class RecentCounter() {

        val que = ArrayDeque<Int>()

        // 队尾添加，队头删除
        fun ping(t: Int): Int {
            while (que.isNotEmpty() && que.first() < t - 3000)
                que.removeFirst()
            que.addLast(t)
            return que.size
        }

    }

    // 950
    fun deckRevealedIncreasing(deck: IntArray): IntArray = ArrayDeque<Int>().apply {
        deck.sortDescending()
        this.addFirst(deck[0])
        for(i in 1..deck.size - 2){
            this.addFirst(deck[i])
            this.addFirst(this.removeLast())
        }
        if(deck.size > 1)
            this.addFirst(deck.last())
    }.toIntArray()

    // 649
    fun predictPartyVictory(senate: String): String {
        val queD = ArrayDeque<Int>()
        val queR = ArrayDeque<Int>()
        senate.indices.forEach { i ->
            if(senate[i] == 'R') queR.addLast(i)
            else queD.addLast(i)
        }
        while(queR.isNotEmpty() && queD.isNotEmpty()){
            val a = queR.removeFirst(); val b = queD.removeFirst()
            if(a < b) queR.addLast(a + senate.length)
            else queD.addLast(b + senate.length)
        }
        return if(queR.isNotEmpty()) "Radiant" else "Dire"
    }

    // 1670
    class FrontMiddleBackQueue() {

        val que1 = ArrayDeque<Int>()
        val que2 = ArrayDeque<Int>() // 右队列总是比左队列大一或相等

        fun pushFront(`val`: Int) {
            que1.addFirst(`val`)
            if(que1.isEmpty)
                return
            if(que1.size > que2.size)
                que2.addFirst(que1.removeLast())
        }

        fun pushMiddle(`val`: Int) {
            que2.addFirst(`val`)
            if(que2.size > que1.size + 1)
                que1.addLast(que2.removeFirst())
        }

        fun pushBack(`val`: Int) {
            que2.addLast(`val`)
            if(que2.size > que1.size + 1)
                que1.addLast(que2.removeFirst())
        }

        fun popFront(): Int {
            if(que1.isEmpty)
                return -1
            val a =  que1.removeFirst()
            if(que2.size > que1.size + 1)
                que1.addLast(que2.removeFirst())
            return a
        }

        fun popMiddle(): Int {
            if(que1.size == 1 && que2.isEmpty)
                return que1.removeFirst()
            if(que1.isEmpty && que2.isEmpty)
                return -1
            if(que2.size > que1.size)
                return que2.removeFirst()
            if(que2.size == que1.size)
                return que1.removeLast()
            return -1
        }

        fun popBack(): Int {
            if(que2.isEmpty)
                return -1
            val a = que2.removeLast()
            if(que1.size > que2.size)
                que2.addFirst(que1.removeLast())
            return a
        }

    }
}

//fun main() {
//    lastStoneWeight(intArrayOf(2,2))
//}
// 1046
// 最大堆
fun lastStoneWeight(stones: IntArray): Int {
    val heap = PriorityQueue<Int>(compareByDescending { it })
    stones.forEach { heap.add(it) }
    repeat(stones.size){
        if(heap.size <= 1) return@repeat
        val a = heap.remove()
        val b = heap.remove()
        if(a != b) heap.add(a - b)
    }
    return if(heap.isEmpty) 0 else heap.remove()
}

//fun main() {
//    getFinalState(intArrayOf(2,1,3,5,6),5,2)
//}
// 3264
fun getFinalState(nums: IntArray, k: Int, multiplier: Int): IntArray {
    val heap = PriorityQueue<Int>(compareBy { nums[it] })
    nums.indices.forEach { i ->
        heap.add(i)
    }
    repeat(k){
        val a = heap.remove()
        nums[a] *= multiplier
        heap.add(a)
    }
    return nums
}

// 2336
class SmallestInfiniteSet() {

    val heap = PriorityQueue<Int>(compareBy { it })
    val hash = mutableMapOf<Int, Int>()
    var idx = 1

    fun popSmallest(): Int {
        if(hash.isEmpty()) return idx++
        else {
            val a = heap.remove()
            hash.remove(a)
            return a
        }
    }

    fun addBack(num: Int) {
        if(hash.contains(num) || num >= idx) return
        heap.add(num)
        hash.put(num,1)
    }

}


// 2530
fun maxKelements(nums: IntArray, k: Int): Long {
    val heap = PriorityQueue<Int>(compareByDescending { it }) // 最大堆
    var ans = 0L
    nums.forEach { heap.add(it) }
        repeat(k){
        val a = heap.remove()
        ans += a
        heap.add(ceil(a.toDouble() / k).toInt())
    }
    return ans
}

// 3066
fun minOperations(nums: IntArray, k: Int): Int {
    val heap = PriorityQueue<Long>(compareBy { it })
    nums.forEach{ heap.add(it.toLong()) }
    var cnt = 0
    while(true){
        if(heap.peek() >= k) break
        val a = heap.remove()
        val b = heap.remove()
        heap.add(min(a,b) * 2 + max(a,b))
        cnt++
    }
    return cnt
}

// 1962
fun minStoneSum(piles: IntArray, k: Int): Int {
    val heap = PriorityQueue<Int>(compareByDescending { it })
    piles.forEach { heap.add(it) }
    repeat(k){
        val a = heap.remove()
        heap.add(ceil(a.toDouble() / 2).toInt())
    }
    return generateSequence(0) { if(heap.isNotEmpty()) it + heap.remove() else null }
        .last()
}

// 2233
fun maximumProduct(nums: IntArray, k: Int): Int {
    val heap = PriorityQueue<Int>(compareBy { it })
    val mod: Long = 10e9.toLong() + 7
    nums.forEach { heap.add(it) }
    repeat(k){
        val a = heap.remove()
        heap.add(a + 1)
    }
    return ((generateSequence(1L) { if(heap.isNotEmpty()) (it * heap.remove()) % mod else null }.last()) % mod) .toInt()
}

// 3296
fun minNumberOfSeconds(mountainHeight: Int, workerTimes: IntArray): Long {
    val heap = PriorityQueue<LongArray>(compareBy { it[0] })
    workerTimes.forEach { heap.add(longArrayOf(it.toLong(),it.toLong(),it.toLong())) }
    var ans = 0L
    for (i in 0 until mountainHeight) {
        val arr = heap.remove()
        val next = arr[0]; val cur = arr[1]; val base = arr[2]
        ans = next.toLong()
        arr[0] = next + cur + base
        arr[1] = cur + base
        arr[2] = base
        heap.add(arr)
    }
    return ans
}

// 1942
fun smallestChair(times: Array<IntArray>, targetFriend: Int): Int {
    val list = mutableListOf<IntArray>()
    for((idx,arr) in times.withIndex()){
        val come = arr[0]
        val move = arr[1]
        list.add(intArrayOf(come,1,idx))
        list.add(intArrayOf(move,0,idx))
    }
    list.sortWith (compareBy<IntArray> { it[0] }.thenBy{ it[1]})

    val heap = PriorityQueue<Int>(compareBy { it })
    val hash = IntArray(times.size){ -1 }
    for(i in 0 until times.size) heap.add(i)

    for(arr in list){
        if(arr[1] == 0) {
            heap.add(arr[2])
            hash[arr[2]] = -1
        }
        else{
            val a = heap.remove()
            hash[arr[2]] = a
            if(targetFriend == arr[2])
                return a
        }
    }
    return -1
}

// 2406
fun minGroups(intervals: Array<IntArray>): Int {
    intervals.sortWith(compareBy { it[0] })
    val heap = PriorityQueue<Int>(compareBy { it })
    intervals.forEach { arr ->
        if(heap.isNotEmpty() && arr[0] > heap.peek()) heap.remove()
        heap.add(arr[1])
    }
    return heap.size
}

fun minGroups1(intervals: Array<IntArray>): Int = PriorityQueue<Int>(compareBy { it }).apply {
    intervals.sortedWith(compareBy { it[0] }).forEach { arr ->
        if(isNotEmpty() && arr[0] > peek()) remove()
        add(arr[1])
    }
}.size

// 3478
fun findMaxSum(nums1: IntArray, nums2: IntArray, k: Int): LongArray {
    val n = nums1.size
    val list = mutableListOf<IntArray>()
    for(i in 0 until n)
        list.add(intArrayOf(nums1[i],nums2[i],i))
    list.sortWith ( compareBy { it[0] } )
    val ans = LongArray(n)
    PriorityQueue<Int>(compareBy { it }).apply {
        repeat(n) repeat@{
            if(it != 0 && list[it][0] == list[it - 1][0])
                return@repeat
            ans[list[it][2]] += sum()
            add(list[it][1])
            if(size == k + 1)
                remove()
        }
    }
    return ans
}

// 2462
fun totalCost(costs: IntArray, k: Int, candidates: Int): Long {
    val heap1 = PriorityQueue<Int>(compareBy { costs[it] })
    val heap2 = PriorityQueue<Int>(compareBy { costs[it] })
    repeat(candidates){
        heap1.add(it)
        heap2.add(costs.size - it - 1)
    }
    var idx1 = 0
    var idx2 = 0
    var ans = 0L
    heap1.let {  }
    repeat(k){
        val a = heap1.peek()
        val b = heap2.peek()
        if(costs[a] < costs[b]){
            ans += heap1.remove()
            if(idx1 + candidates < costs.size)
                heap1.add(idx1++ + candidates)
        }
        if(costs[b] < costs[a]){
            ans += heap2.remove()
            if(costs.size - candidates - idx2 < costs.size)
                heap1.add(costs.size - candidates - idx2--)
        }
        if(costs[a] == costs[b]){
            if(a < costs.size - candidates){
                ans += heap1.remove()
                if(idx1 + candidates < costs.size)
                    heap1.add(idx1++ + candidates)
            }
        }
    }
    return ans
}

// 1834
fun getOrder(tasks: Array<IntArray>): IntArray {
    val arr = Array<IntArray>(tasks.size) { intArrayOf(it, tasks[it][0], tasks[it][1]) }
    arr.sortWith ( compareBy { it[2] } )
    var time = 1
    arr.apply {  }
    var ans = IntArray(tasks.size)
    val heap = PriorityQueue<Int>()
    tasks.forEachIndexed{ i,arr ->
        when {
            heap.isNotEmpty() -> ans[i] = heap.remove()
            arr[0] <= time -> {
                ans[i] = i
                time += arr[1]
            }
            else -> heap.add(i)
        }
    }
    return ans
}

//fun main() {
//    strWithout3a3b(1,3)
//}
// 984
fun strWithout3a3b(a: Int, b: Int): String = buildString {
    var a1= a; var b1 = b
    var cnta = 0; var cntb = 0
    while(a1 > 0 && b1 > 0){
        if(a1 >= b1){
            if(cnta < 2){
                this.append("a")
                cnta++
                a1--
                }else{
                    this.append("b")
                    cntb++
                    b1--
                    cnta = 0
                }
            }
            else {
            if(cntb < 2){
                this.append("b")
                cntb++
                b1--
            }else{
                this.append("a")
                cnta++
                a1--
                cntb = 0
            }
        }
    }
    while(a1 > 0) {
        this.append("a")
        a1--
    }
    while(b1 > 0) {
        this.append("b")
        b1--
    }
}

// 767
fun reorganizeString(s: String): String = buildString outer@{
    var (a,b) = s.count{ it == 'a'} to s.count{ it == 'b'}
    var (cnta,cntb) = 0 to 0
    while(a > 0 && b > 0){
        when{
            cnta == 1 -> { append("b"); cnta = 0; cntb = 1; b--}
            cntb == 1 -> { append("a"); cntb = 0; cnta = 1; a--}
            a >= b -> { append("a"); cnta++; cntb = 0}
            else -> { append("b"); cntb++; cnta = 0}
        }
    }
    if(b > 0 && b == 1) {append("b"); return@outer}
    if(a > 0 && a == 1) {append("a"); return@outer}
    this.clear()
}

fun reorganizeString1(s: String): String = buildString {
    val fre = s.groupingBy { it }.eachCount().toMutableMap()
    val heap = PriorityQueue<Char>(compareByDescending { fre[it] }).apply {
         addAll(fre.keys)
     }
    if(fre[heap.peek()]!! > (s.length + 1) ushr 1) return ""
    var pre = '#'
    while(heap.isNotEmpty()){
        val a = heap.remove()
        append(a)
        fre[a] = fre[a]!! - 1
        if(pre != '#' && fre[pre]!! > 0) heap.add(pre)
        pre = if(fre[a]!! > 0) a else '#'
    }
}

// 1054
fun rearrangeBarcodes(barcodes: IntArray): IntArray  = mutableListOf<Int>().apply{
    val fre = barcodes.toList().groupingBy { it }.eachCount().toMutableMap()
    val heap = PriorityQueue<Int>(compareByDescending { fre[it] }).apply { addAll(fre.keys) }
    var pre = 0
    while (heap.isNotEmpty()) {
        val a = heap.remove()
        add(a)
        fre[a] = fre[a]!! - 1
        if (pre != 0 && fre[pre]!! > 0) heap.add(pre)
        pre = if (fre[a]!! > 0) a else 0
    }
}.toIntArray()

//fun main() {
//    nthUglyNumber(10)
//}
// 264
fun nthUglyNumber(n: Int): Int {
    val heap = PriorityQueue<Long>()
    val seen = HashSet<Long>()
    val factors = longArrayOf(2, 3, 5)

    heap.offer(1L)
    seen.add(1L)

    repeat(n - 1) {
        val current = heap.poll()
        factors.forEach { factor ->
            val next = current * factor
            if (!seen.contains(next)) {
                seen.add(next)
                heap.offer(next)
            }
        }
    }
    return heap.poll().toInt()
}


// Lcp 30
fun magicTower(nums: IntArray): Int {
    var ans = 0
    var heap = PriorityQueue<Int>(compareBy { it })
    var res = 0
    nums.forEach { num ->
        res += num
        if(num < 0) heap.add(num)
    }
   if(res < 0)
       return -1
    res = 0
    nums.forEach { num ->
        res += num
        if(res < 1){
            res -= heap.remove()
            ans++
        }
    }
    return ans
}

// 630
fun scheduleCourse(courses: Array<IntArray>): Int {
    courses.sortWith (compareBy { it[1] })
    val heap = PriorityQueue<Int> { a, b -> b - a }
    var day = 0
    courses.forEachIndexed{i,arr ->
        val cur = arr[0]; val last = arr[1]
        if(day + cur <= last){
            heap.add(cur)
            day += cur
        }else if(heap.isNotEmpty() && heap.peek() >= cur){
            day -= heap.remove() - cur
            heap.add(cur)
        }
    }
    return heap.size
}

// 2349
class NumberContainers() {

    val map1 = mutableMapOf<Int, Int>()
    val map2 = mutableMapOf<Int, PriorityQueue<Int>>()

    fun change(index: Int, number: Int) {
        map1.put(index,number)
        map2.computeIfAbsent(number){ number -> PriorityQueue<Int>() }.add(index)
    }

    fun find(number: Int): Int {
        return map2[number]?.let { heap ->
            if (heap.isEmpty()) return@let -1
            while (heap.isNotEmpty() && map1[heap.peek()] != number) {
                heap.poll()
            }
            heap.peek() ?: -1
        } ?: -1
    }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}
// 1290
fun getDecimalValue(head: ListNode?): Int {
    var t = head
    var ans = 0
    while(t != null){
        ans += ans shl 1 + t.`val`
        t = t.next
    }
    return ans
}

// 2058
fun nodesBetweenCriticalPoints(head: ListNode?): IntArray {
    var a = head; var b = head!!.next; var c = head!!.next!!.next
    if(a == null || a != null&&b == null || a != null&&b != null&&c == null)
        return intArrayOf(-1,-1)
    var pre = 0; var cur = 0; var last = 0
    var first = 0
    var min = Int.MAX_VALUE
    var i = 1
    while(c != null){
        if(pre == 0)
            first = i
        if(a!!.`val` > b!!.`val` && b!!.`val` < c!!.`val` || a!!.`val` < b!!.`val` && b!!.`val` > c!!.`val`)
            cur = i
        min = min(min,cur - pre)
        a = b; b = c; c = c!!.next
        pre = cur
        i++
    }
    if(cur == first)
        return intArrayOf(-1,-1)
    return intArrayOf(min,cur - first)
}

// 2181
fun mergeNodes(head: ListNode?): ListNode? {
    var tail = head
    var cur = tail?.next
    while(cur?.next != null){
        if(cur.`val` != 0)
            tail?.`val` += cur.`val`
        else{
            tail = tail?.next
            tail?.`val` = 0
        }
        cur = cur.next
    }
    tail?.next = null
    return head
}

// 725
fun splitListToParts(head: ListNode?, k: Int): Array<ListNode?> {
    var curr = head
    var length = 0
    while (curr != null) {
        curr = curr.next
        length++
    }
    val each = length / k
    val remainder = length % k
    val result = Array<ListNode?>(k) { null }
    curr = head
    for (i in 0 until k) {
        result[i] = curr
        val partSize = each + if (i < remainder) 1 else 0
        for (j in 1 until partSize) {
            curr = curr?.next
        }
        val next = curr?.next
        curr?.next = null
        curr = next
    }
    return result
}

// 817
fun numComponents(head: ListNode?, nums: IntArray): Int {
    var t = head
    var ans = 0
    val set = mutableSetOf<Int>()
    set.addAll(nums.toList())
    while(t != null){
        if(set.contains(t.`val`)){
            while(t != null && set.contains(t.`val`)) t = t.next
            ans++
        }else
            t = t.next
    }
    return ans
}

// 2487
// 单链表递归,从后往前遍历
fun removeNodes(head: ListNode?): ListNode? {
    if(head?.next == null)
        return head
    var node = removeNodes(head?.next)
    if(node!!.`val` > head!!.`val`)
        return node
    head?.next = node
    return head
}

// 2807
fun insertGreatestCommonDivisors(head: ListNode?): ListNode? {
    if(head?.next == null)
        return head
    var cur = head
    while(cur?.next != null){
        val a = gcd(cur.`val`,cur.next!!.`val`).toInt()
        val node = ListNode(a).apply { next = cur.next }
        cur.next = node
        cur = node?.next
    }
    return head
}

//fun main() {
//    var n1: ListNode? = ListNode(4)
//    n1?.next = ListNode(2)
//    n1?.next?.next = ListNode(1)
//    n1?.next?.next?.next = ListNode(3)
//    insertionSortList(n1)
//}
// 147
fun insertionSortList(head: ListNode?): ListNode? {
    val dump = ListNode(0)
    var cur = head
    while(cur != null){
        val a = cur.`val`
        var judge: ListNode? = dump
        while(judge?.next != null && judge?.next!!.`val` <= a){
            judge = judge.next
        }
        val t = cur
        cur = cur?.next
        t?.next = judge?.next
        judge?.next = t
    }
    return dump.next
}

class Node(var `val`: Int) {
    var next: Node? = null
}
// LCR 029
fun insert(head: Node?, insertVal: Int): Node? {
    if(head == null) {
        val a: Node? =  Node(insertVal)
        a!!.next = a
    }
    if(head?.next == head){
        head?.next = Node(insertVal).apply { next = head }
        return head
    }
    var cur = head
    while(true) {
        if (insertVal > cur!!.`val` && insertVal < cur.next!!.`val`) {
            val node = Node(insertVal).apply { next = cur.next }
            cur.next = node
            return head
        } else {
            cur = cur.next
        }
    }
}

// 206
fun reverseList(head: ListNode?): ListNode? {
    var cur = head
    var pre: ListNode? = null
    while(cur != null){
        val nxt = cur.next
        cur.next = pre
        pre = cur
        cur = nxt
    }
    return pre
}

// 92
fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
    val dump: ListNode? = ListNode(0).apply { next = head }
    var cur = dump
    var pre: ListNode? = null
    repeat(left - 1){ cur = cur?.next }
    val p0: ListNode? = cur
    cur = cur?.next
    repeat(right - left + 1){
        val nxt: ListNode? = cur?.next
        cur?.next = pre
        pre = cur
        cur = cur?.next
    }
    p0?.next?.next = cur
    p0?.next = pre
    return head
}

// 24
fun swapPairs(head: ListNode?): ListNode? {
    val dump = ListNode(0).apply { next = head }
    var cur: ListNode? = dump
    var pre: ListNode?
    var p0 = cur
    while(p0?.next != null && p0?.next?.next != null){
        pre = null
        cur = p0.next
        repeat(2){
            val nxt = cur?.next
            cur?.next = pre
            pre = cur
            cur = nxt
        }
        p0?.next?.next = cur
        p0?.next = pre
        p0 = p0?.next?.next
    }
    return dump?.next
}

// 25
fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
    var cur = head
    var n = 0
    while(cur != null){
        n++
        cur = cur.next
    }
    val time = n / k
    val dump = ListNode(0).apply { next = head }
    var p0: ListNode? = dump
    repeat(time){
        cur = p0?.next
        var pre: ListNode? = null
        repeat(k){
            val nxt = cur?.next
            cur?.next = pre
            pre = cur
            cur = nxt
        }
        p0?.next?.next = cur
        p0?.next = pre
        p0 = p0?.next?.next
    }
    return dump.next
}

// 2074
fun reverseEvenLengthGroups(head: ListNode?): ListNode? {
    val list = mutableListOf<ListNode?>()
    var size = 1
    val dump = ListNode(0).apply { next = head }
    var p0: ListNode? = dump
    var cur: ListNode? = p0?.next
    while(cur != null){
        list.add(cur)
        if(size == list.size || cur?.next == null){
            if(size % 2 == 0) {
                var cur1 = p0?.next
                val groupPre = cur1
                var pre: ListNode? = null
                repeat(size) {
                    val nxt = cur1?.next
                    cur1?.next = pre
                    pre = cur1
                    cur1 = nxt
                }
                p0?.next?.next = cur1
                p0?.next = pre
                p0 = groupPre
            }
            list.clear()
            size++
        }
        cur = cur?.next
    }
    return dump.next
}

// 160
fun getIntersectionNode(headA:ListNode?, headB:ListNode?):ListNode? {
    var diff = 0; var a = 0; var b = 0
    var cur = headA
    while(cur != null){
        cur = cur?.next
        a++
    }
    cur = headB
    while(cur != null){
        cur = cur?.next
        b++
    }
    var curA = headA; var curB = headB
    when {
        a >= b -> { cur = headA; diff = a - b; repeat(diff){ curA = curA?.next } }
        else -> { cur = headB; diff = b - a; repeat(diff){ curB = curB?.next }}
    }
    while(curA != curB){
        curA = curA?.next
        curB = curB?.next
    }
    return if(curA == null) null else curA
}


fun getIntersectionNode1(headA:ListNode?, headB:ListNode?):ListNode? {
    var a = headA; var b = headB
    while(a != b){
        a = if(a != null) a?.next else headB
        b = if(b != null) b?.next else headA
    }
    return a
}

// 2
fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    var carry = 0
    var a = l1; var b = l2
    val dump = ListNode(0); var cur: ListNode? = dump
    while(a != null && b != null){
        var sum = (a.`val` + b.`val` + carry) % 10
        carry = (a.`val` + b.`val` + carry) / 10
        cur?.next =  ListNode(sum)
        cur = cur?.next
        a = a.next
        b = b.next
    }
    while(a != null){
        var sum = (a.`val`+ carry) % 10
        carry = (a.`val` + carry) / 10
        cur?.next =  ListNode(sum)
        cur = cur?.next
        a = a.next
    }
    while(b != null){
        var sum = (b.`val`+ carry) % 10
        carry = (b.`val` + carry) / 10
        cur?.next =  ListNode(sum)
        cur = cur?.next
        b = b.next
    }
    if(carry != 0)
        cur?.next = ListNode(carry)
    return dump.next
}

// 445
fun addTwoNumbers2(l1: ListNode?, l2: ListNode?): ListNode? {
    var a = reverse(l1)
    var b = reverse(l2)
    var c = addTwo(a, b, 0)
    return reverse(c)
}

fun addTwo(l1: ListNode?, l2: ListNode?, carry: Int): ListNode? {
    if(l1 == null && l2 == null && carry == 0) return null
    var l11 = l1; var l22 = l2
    var s = carry
    if(l11 != null){
        s += l11.`val`
        l11 = l11.next
    }
    if(l22 != null){
        s += l22.`val`
        l22 = l22.next
    }
    return ListNode(s % 10).apply {  next = addTwo(l11,l22,s / 10) }
}

fun reverse(head: ListNode?): ListNode? {
    if (head == null || head?.next == null) return head
    val newHead = reverse(head.next)
    head.next?.next = head
    head.next = null
    return newHead
}

// 2816
fun doubleIt(head: ListNode?): ListNode? {
    var a = reverse(head)
    a = mutiTwo(a,0)
    return reverse(a)
}

fun mutiTwo(l1: ListNode?, carry: Int): ListNode? {
    if(l1 == null && carry == 0) return null
    var l11 = l1;
    var a = 0;
    if(l11 != null) {
        a = l11.`val` * 2
        l11 = l11?.next
    }
    return ListNode((a % 10) + carry).apply { next = mutiTwo(l11,a / 10) }
}


fun doubleIt1(head: ListNode?): ListNode? {
    var newHead = head
    if(head!!.`val` > 4) newHead = ListNode(0).apply { next = head }
    var cur = newHead
    while(cur != null){
        cur?.`val` = (cur!!.`val` * 2) % 10
        if(cur?.next != null && cur.next!!.`val` > 4){
            cur.`val` += 1
        }
    }
    return newHead
}

// 21
fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
    val dump = ListNode(0); var cur: ListNode? = dump
    var cur1 = list1; var cur2 = list2
    while(cur1 != null && cur2 != null){
        when {
            cur1.`val` <= cur2.`val` -> {
                val nxt = cur1.next
                cur?.next = cur1
                cur = cur?.next
                cur1 = nxt
            }
            else -> {
                val nxt = cur2.next
                cur?.next = cur2
                cur = cur?.next
                cur2 = nxt
            }
        }
    }
    when {
        cur1 != null -> cur?.next = cur1
        else -> cur?.next = cur2
    }
    return dump.next
}

// 23
fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    var heap = PriorityQueue<ListNode>(compareBy { it.`val` })
    lists.forEach { node ->
        when {
            node != null -> { heap.add(node) }
        }
    }
    val dump = ListNode(0)
    var cur: ListNode? = dump
    while(heap.isNotEmpty()) {
        var removed = heap.remove()
        cur?.next = removed
        cur = cur?.next
        removed = removed.next
        when {
            removed != null -> heap.add(removed)
        }
    }
    return dump.next
}


fun mergeKLists1(lists: Array<ListNode?>): ListNode? {
    return merge(lists,0,lists.size)
}

fun merge(lists: Array<ListNode?>, i: Int, j: Int): ListNode? {
    val m = j - i
    if(m == 0)
        return null
    if(m == 1)
        return lists[i] // 此时只有一个节点，无法分治，直接返回
    var left = merge(lists,i,i + m / 2)
    var right = merge(lists,i + m / 2 ,j)
    return mergeTwoLists(left,right)
}

// 148
// 快慢指针找中间节点并断开
// 合并两个链表
fun sortList(head: ListNode?): ListNode? {
    var head1 = head
    if(head1 == null || head1.next == null) return head
    var head2 = findMid(head1)
    head1 = sortList(head1)
    head2 = sortList(head2)
    return mergeTwoLists(head1,head2)
}

fun findMid(head: ListNode?): ListNode?{
    var pre = head; var slow = head; var fast = head
    while(fast != null && fast?.next != null){
        pre = slow
        fast = fast.next!!.next
        slow = slow?.next
    }
    pre?.next = null
    return slow
}

// 739
// 单调栈
fun dailyTemperatures1(temperatures: IntArray): IntArray = IntArray(temperatures.size).apply outer@{
    ArrayDeque<Int>().apply {
        (temperatures.size - 1 downTo 0).forEach { i ->
            while(isNotEmpty() && temperatures[first] <= temperatures[i])
                removeFirst()
            this@outer[i] = firstOrNull()?.minus(i) ?: 0
            add(i)
        }
    }
}


class Problem1019 {
    val stack = ArrayDeque<Int>()
    var arr: IntArray? = null
    // 1019
    fun nextLargerNodes(head: ListNode?): IntArray {
        function1019(head,0)
        return arr!!
    }

    fun function1019(head: ListNode?, num: Int): Unit{
        if(head == null){
            arr = IntArray(num)
            return
        }
        function1019(head.next,num + 1)
        while(stack.isNotEmpty() && stack.first() <= head.`val`)
            stack.removeFirst()
        if(stack.isNotEmpty()) arr!![num] = stack.first()
        stack.addFirst(head.`val`)
    }
}
