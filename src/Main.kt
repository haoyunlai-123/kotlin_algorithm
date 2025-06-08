import java.util.PriorityQueue

//// 703
//class KthLargest(k: Int, nums: IntArray) {
//
//    var cap = k
//    val heap = PriorityQueue<Int>(k,compareBy { it })
//    init{
//        nums.forEach { heap.add(it) }
//    }
//
//    fun add(`val`: Int): Int {
//        if(heap.size < cap)
//            heap.add(`val`)
//        else if(heap.peek() < `val`){
//            heap.remove()
//            heap.add(`val`)
//        }
//        return heap.peek()
//    }
//}

fun main() {
    var a = 0
    fun add(): Unit {
        println("i am inner")
        a++
    }
    add()
    println(a)
}