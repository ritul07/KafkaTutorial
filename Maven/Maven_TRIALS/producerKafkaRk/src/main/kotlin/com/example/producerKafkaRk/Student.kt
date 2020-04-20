package com.example.producerKafkaRk

class Student {
    var name: String = ""
    var dept: String = ""

    constructor(name: String, dept: String){
        this.name = name
        this.dept = dept
    }

    constructor(){}

    override fun toString(): String {
        return "Student(name='$name', dept='$dept')"
    }


}