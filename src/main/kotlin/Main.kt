import model.LogicConfiguration
import util.*

const val filename = "assets/statement.in"

fun main() {
    // convert text format to token stream
    val line = readString(filename)
    val tokens = tokenize(line)

    // throw exception if UNKNOWN token is found
    tokens.forEach {
        if (TokenType.isUnknown(it.first)) {
            throw Exception("Unknown token found: '${it.second}'")
        }
    }

    // extract IDENTIFIERs from tokens in a list, remove duplicates and sort them
    val identifiers = tokens.filter { TokenType.isIdentifier(it.first) }
        .map { it.second }
        .toSet()
        .sorted()

    // assign each IDENTIFIER a unique number
    val operandsMap = identifiers.associateWith { identifiers.indexOf(it) }

    // generate boolean cartesian product of length identifiers.size
    val backGen = BooleanGenerator(identifiers.size)
    val listOfGeneration = backGen.generate(0, mutableListOf())

    // declare a context of expression evaluation for each generated boolean combination
    val listOfConfiguration = listOfGeneration.map {
        LogicConfiguration(it, operandsMap)
    }

    // evaluate each generated boolean combination
    evaluateExpression(tokens, listOfConfiguration)

    // extract the result of each configuration into a list
    listOfConfiguration.forEach {
        if (it.operandsStack.size != 1)
            throw Exception("Evaluation failed for ${it.booleanConfiguration}")
        it.result = it.operandsStack.pop()
    }
    val results = listOfConfiguration.map { it.result }

    // find the nature of the expression
    println("The statement $line is ${getStatementType(results)}\n")
    listOfConfiguration.forEach { configuration ->
        configuration.operandsMap.forEach {
            print("${it.key}: ${configuration.booleanConfiguration[it.value]}".padEnd(11))
        }
        print("=> ${configuration.result}\n")
    }
}
