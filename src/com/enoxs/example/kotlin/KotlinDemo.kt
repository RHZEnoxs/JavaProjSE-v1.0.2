package com.enoxs.example.kotlin

import com.enoxs.utillity.Calculator

fun main(args: Array<String>) {
//    runStrTemplate()
//    runConditions()
//    runLoop()
//    runArrays()
//    runAppKotlin()
    runCalculator()
}
fun runHello(){
    println("Hello World");
    val textVal = "Hello Kotlin"
    var textVar = "Hello KitKat"
    textVar = textVal
    println(textVal)
    println(textVar)
}

fun runTaskCallFunction(){
    var sum = calSum(15,12)
    println(sum)
}

/**
 * 函式
 * （function）
 */

fun calSum(num1:Int,num2:Int):Int{
    return num1 + num2
}

/**
 * 字串模板
 * string template
 */
fun runStrTemplate(){
    val x = 1
    val y = 2
    val z = 3
    val msg1 = "X: $x, Y: $y, Z: $z"
    val msg2 = "SUM: ${x + y + z}"
    println(msg1)
    println(msg2)
}

/**
 * 條件
 * conditions
 */
fun runConditions(){
    val x = 3
    val y = 5
    val z = if(x>y) x else y
    println(z)
    when(z){
        1 -> print("1")
        3 -> print("x")
        5 -> print("y")
    }
}

/**
 * 迴圈
 * loop
 */

fun runLoop(){
    var count = 0
    println(" --- while ---")
    //while
    while(count < 5){
        count++
        println("count $count")
    }
    println(" --- do while --- ")
    //do while
    count = 0
    do{
        count ++
        println("count $count")
    }while(count < 5)
    println("do while finish.")
    println(" --- for --- ")
    //for
    for(i:Int in 1..5){
        print(i)
    }
    println()
    for(i:Int in 1..7 step 3){
        print(i)
    }
    println()
    for(i:Int in 5 downTo 1){
        print(i)
    }
}
/**
 * 陣列
 * array
 */
fun runArrays(){
    val arr : IntArray = intArrayOf(20,10,23,15,73,59)
    for(n in arr){
        print(n)
    }
    println()
    arr.filter{ it > 12 }
            .sortedBy { it }
            .map{ it + 1 }
            .forEach{print("$it ")}

}

/**
 * 類別
 *（class）
 */
fun runAppKotlin(){
    var app1 = AppKotlin("Enoxs")
    app1.appVersion = "1.0.1"
    app1.show()

    var app2 = AppKotlin("RHZ","1.0.2")
    app2.show()
    println("${app2.name}'s Version is ${app2.appVersion}")

    var app3 = App("Desktop","1.0.1",true)
    app3.show2()
}

open class AppKotlin(var name:String){

    var appName : String = ""
        get() = "$name"
    var appVersion : String = ""
        get() = "${if ("RHZ".equals("$appName")) "1.0.3" else "1.0.5"}"
//        get() = "$name(${if (gender=='M') "Male" else "Female"})"
    constructor(name:String ,version :String) : this(name){
        this.appName = name
        this.appVersion = version
    }
    fun show(){
        println("Name : $appName")
        println("Version : $appVersion")
    }
    open fun show2() {
        println("Name : $appName")
        println("Version : $appVersion")
    }
}
class App(name:String):AppKotlin(name){
    var isFlag = false

    constructor(name: String,version: String ,flag: Boolean):this(name){
        super.appName = name
        super.appVersion = version
        this.isFlag = flag
    }

    override fun show2() {
        super.show2()
        println("Flag : $isFlag")
    }
}

fun runCalculator(){
    val cal = Calculator()
    var msg = cal.BytesToHexString(0x31)
    println(msg)
}
