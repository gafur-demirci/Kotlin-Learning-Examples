
fun main() {
    println("Hello")
    val newPerson = Person()
    newPerson.name = "newPerson"
    newPerson.age = 33
    println(newPerson.name)
    println(newPerson.age)
}

class Person {
    var age: Int = 0
    var name: String = ""
}