import ru.hello.Elevator
import ru.hello.events.FloorEvent
import ru.hello.events.FromToEvent

fun main(args: Array<String>) {
    println("Hello, world!")
    val e = Elevator(1, 25)
    val t = Thread(e)
    t.start()

    loop@ while (true) {
        val s = readLine()

        if (s == "q") {
            break
        }

        val n = parseInt(s)
        if (n != null) {
            e.addEvent(FloorEvent(n))
            continue
        }

        val p = parsePairInt(s)
        if (p != null) {
            e.addEvent(FromToEvent(p.first, p.second))
            continue
        }
    }

    t.interrupt()
    t.join()
}

fun parseInt(s: String?) : Int? {
    if (!s.isNullOrEmpty()) {
        try {
            return Integer.valueOf(s)
        }
        catch (_: NumberFormatException){
            return null
        }
    }
    return null
}

fun parsePairInt(s: String?) : Pair<Int, Int>? {
    if (!s.isNullOrEmpty()) {
        try {
            val l = s!!.split("-")
            if (l.size == 2) {
                val from = Integer.valueOf(l[0])
                val to = Integer.valueOf(l[1])
                return Pair(from, to)
            }
            return null
        }
        catch (_: NumberFormatException){
            return null
        }
    }
    return null
}
