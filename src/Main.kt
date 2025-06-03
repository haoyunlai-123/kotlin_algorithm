
fun parent() {
    val parentVar = "outer"
    fun child() {
        val childVar = "inner"
        println("$parentVar -> $childVar")
    }
    child()
}

fun main(){
    val s = "1234"
    println(s.toInt())

    val length = "Hello".let { it ->
        println(it.length) // 5
        it.length // 返回值
    }

    "hello".run {
        println(length)
        length
    }

    run {
        repeat(5) {
            println("hello")
        }
    }

    with("hello") {
        this.length
    }

    /**
     * 上面三个一组
     * 有返回值
     * let it参数
     * run this参数
     * with 非接收者参数
     * run 的直接使用
     */

    "hello".apply {
        substring(0,2)
    }

    "hello".also { it ->
        println(it)
    }

    /**
     * 上面两个一组
     * 无返回值
     * apply this参数
     * also it参数
     */


    repeat(5) {
        println("hello")
    }
    /**
     * 重复数次
     */

    var str1 = "hello".takeIf {it -> it.length > 7}
    var str2 = "hello".takeUnless {it -> it.length > 7}
    /**
     * 满足条件时返回本身，否则返回null(可空类型)
     * 二者都是it参数
     */

    "hello".also { it ->
        TODO()
        TODO("no initizaliation")
    }
    /**
     * 占位，编译时不会报错，运行时会报错
     */
}