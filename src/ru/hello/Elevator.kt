package ru.hello

import ru.hello.events.BaseEvent
import ru.hello.events.FloorEvent
import ru.hello.events.FromToEvent
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

const val TICK: Long = 1000

class Elevator(val bottom: Int, val top: Int) : Runnable {

    enum class Direction {
        UP,
        DOWN,
        NONE
    }

    val events: BlockingQueue<BaseEvent> = LinkedBlockingQueue<BaseEvent>()

    var floor: Int = bottom
    var doors: Boolean = false
    var dir: Direction = Direction.NONE

    override fun run() = try {
        while (true) workCycle()
    } catch (_: InterruptedException) {
        println("Elevator is destroyed")
    }

    fun addEvent(event: BaseEvent) = events.put(event)

    private fun workCycle() {
        val event = events.take()
        return when(event){
            is FloorEvent -> processFloorEvent(event)
            is FromToEvent -> processFromToEvent(event)
        }
    }

    private fun processFloorEvent(event: FloorEvent) {
        if (event.Num < bottom || event.Num > top) {
            println("Wrong floor number!")
            return
        }

        // go to floor
        goToFloor(event.Num)

        // open & close door
        openDoor()
        closeDoor()
    }

    private fun processFromToEvent(event: FromToEvent) {
        if (event.From < bottom || event.From > top || event.To < bottom || event.To > top) {
            println("Wrong floor number!")
            return
        }

        // go to floor From
        goToFloor(event.From)

        // open & close door
        openDoor()
        closeDoor()

        // go to floor To
        goToFloor(event.To)

        // open & close door
        openDoor()
        closeDoor()
    }

    private fun goToFloor(n:Int) {
        while (floor != n) {
            if (floor < n) {
                goUp()
            } else {
                goDown()
            }
        }
    }

    private fun goUp() {
        floor++
        println("---=== " + floor + " ===---")
        Thread.sleep(TICK)
    }

    private fun goDown() {
        floor--
        println("---=== " + floor + " ===---")
        Thread.sleep(TICK)
    }

    private fun openDoor() {
        this.doors = true
        println("open")
        Thread.sleep(TICK)
    }

    private fun closeDoor() {
        this.doors = false
        println("close")
        Thread.sleep(TICK)
    }
}