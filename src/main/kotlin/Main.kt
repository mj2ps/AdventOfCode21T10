import java.io.File

val commandList: MutableList<Command> = mutableListOf(
    Command('(', ')', 3, 1u),
    Command('[', ']', 57, 2u),
    Command('{', '}', 1197, 3u),
    Command('<', '>', 25137, 4u)
)

fun main(args: Array<String>) {
    var inputFileName = "src/main/resources/input_real.txt"
    //var inputFileName = "src/main/resources/input.txt"

    if (!File(inputFileName).exists()) {
        println("No file @ current path")
        return
    }
    var completionCost: MutableList<ULong> = mutableListOf()

    var cost = 0
    File(inputFileName).forEachLine {
        var s = it
        var oldLength = 0
        while (oldLength != s.length) {
            oldLength = s.length
            s = s.replace(commandList[0].getCmd(), "")
                .replace(commandList[1].getCmd(), "")
                .replace(commandList[2].getCmd(), "")
                .replace(commandList[3].getCmd(), "")
        }
        val corruptCost = findCorruptLine(s, commandList)
        cost += corruptCost
        if (corruptCost == 0 && s.isNotEmpty()) {
            completionCost.add(findEndings(s))
        }
    }
    completionCost.sort()
    completionCost[completionCost.size/2]

    println("Cost $cost, autoCompletion center cost ${completionCost[completionCost.size/2]}")
}

fun findCorruptLine(s: String, commandList: MutableList<Command>): Int {

    for (c in 1 until s.length) {
        commandList.forEach {
            if (it.closer == s[c]) {
                //println(s + "Corrupt line: Found ${it.closer}, expecting ${getCommand(s[c - 1]).closer}")
                return it.cost
            }
        }
    }
    return 0
}

fun getCommand(s: Char): Command {
    if (s == commandList[0].opener) {
        return commandList[0]
    } else if (s == commandList[1].opener) {
        return commandList[1]
    } else if (s == commandList[2].opener) {
        return commandList[2]
    } else if (s == commandList[3].opener) {
        return commandList[3]
    } else {
        return Command(' ', ' ', 0, 0u)
    }
}

fun findEndings(string: String):ULong {
    var autoCompleteCost : ULong = 0u
    var compString: String = ""
    for (i in string.length -1 downTo 0) {
        var command = getCommand(string[i])
        compString = compString.plus(command.closer.toString())
        autoCompleteCost = autoCompleteCost * Command.AUTOCOMPLETE + command.autoCompleteCost
    }
    //println("Autocompleted: Remaning $string Completed $compString, cost $autoCompleteCost")
    return  autoCompleteCost
}

