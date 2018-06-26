package com.enoxs.example.kotlin

fun getMsg() : String?{
    return null
}
fun String.printStr() {
    println("printStr: " + this)
}

fun runEmptyVar(){
    var info = ApInfo(1,"Enoxs",true)
    println(info.toString())
    var str = "OwO it is Cool !!!"
    str.printStr()
    /*var str2: String? = null
    println("str2.length: " + str2.length)  //compile error
    println("str2?.length: " + str2?.length)  //print null
    println("str2!!.length: " + str2!!.length)  //run exception
    if (str2 != null) {
        println("str2!!.length: " + str2!!.length)  //don't run
    }
    str2 = "testNull"  //assign
    println("str2.length: " + str2.length)  //print 8
    println("str2?.length: " + str2?.length)  //print 8
    println("str2!!.length: " + str2!!.length)  //print 8
    if (str2 != null) {
        println("str2!!.length: " + str2.length)  //print 8
    }*/
}