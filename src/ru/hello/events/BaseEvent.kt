package ru.hello.events

sealed class BaseEvent

data class FloorEvent(val Num:Int) : BaseEvent()

data class FromToEvent(val From:Int,val To:Int):BaseEvent()
