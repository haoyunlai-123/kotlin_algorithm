package algorithm

// 1475
fun finalPrices1(prices: IntArray): IntArray  = IntArray(prices.size).also{ arr ->
    ArrayDeque<Int>().also { stack ->
        (prices.size - 1 downTo  0).forEach { idx ->
            while(stack.isNotEmpty() && stack.first() >= prices[idx])
                stack.removeFirst()
            arr[idx] = if(stack.isEmpty()) prices[idx] else prices[idx] - stack.first()
            stack.addFirst(prices[idx])
        }
    }
}