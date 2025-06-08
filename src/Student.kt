open class Student(){
    open fun hello() = println("hello")
}
class Boystudent() : Student(){
    override fun hello() = println("lanmaohello")
}