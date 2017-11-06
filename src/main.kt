
import ru.hello.Elevator
import ru.hello.events.FloorEvent
import ru.hello.events.FromToEvent
import java.util.regex.Pattern

fun main(args: Array<String>) {
    println("Hello, world!")
    val e = Elevator(1, 25)
    val t = Thread(e)
    t.start()

    loop@ while (true) {
        val s = readLine()

        when {
            s == null || s == "q" -> break@loop
            Pattern.matches("^\\d+$", s) -> e.addEvent(FloorEvent(Integer.parseInt(s)))
            Pattern.matches("^\\d+-\\d+$", s) -> {
                val ss = s.split("-")
                e.addEvent(FromToEvent(Integer.parseInt(ss[0]), Integer.parseInt(ss[1])))
            }
        }
    }

    t.interrupt()
    t.join()
}
