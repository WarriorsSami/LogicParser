package util

import model.LogicConfiguration
import java.util.*

enum class StatementType(s: String) {
    VALID("valid"),
    SATISFIABLE("satisfiable"),
    CONTINGENT("contingent"),
    INVALID("invalid"),
}

// evaluate operation
fun evaluate(operator: TokenType, operand1: Boolean, operand2: Boolean): Boolean {
    return when(operator) {
        TokenType.NOT -> operand2.logicNot()
        TokenType.AND -> operand1.logicAnd(operand2)
        TokenType.OR -> operand1.logicOr(operand2)
        TokenType.XOR -> operand1.logicXor(operand2)
        TokenType.IMPL -> operand1.logicImpl(operand2)
        TokenType.IFF -> operand1.logicEquiv(operand2)
        else -> throw IllegalArgumentException("Unknown operator: $operator")
    }
}

// evaluate the entire list of boolean configurations at the same time
fun evaluateConfig(configurations: List<LogicConfiguration>, operatorsStack: Stack<TokenType>) {
    val op = operatorsStack.pop()

    configurations.forEach { config ->
        val operand2 = config.operandsStack.pop()
        val operand1 = if (op == TokenType.NOT) true
                       else config.operandsStack.pop()

        config.operandsStack.push(
            evaluate(op, operand1, operand2)
        )
    }
}

// evaluate the stream of tokens
fun evaluateExpression(tokens: List<Pair<TokenType, String>>, listOfConfiguration: List<LogicConfiguration>) {
    // declare stack of operators
    val operatorsStack = Stack<TokenType>()

    // evaluate each expression in the context
    tokens.forEach {
        when(it.first) {
            TokenType.IDENTIFIER -> {
                listOfConfiguration.forEach { config ->
                    config.operandsStack.push(
                        config.booleanConfiguration[config.operandsMap[it.second]!!]
                    )
                }
            }
            TokenType.L_PAREN -> {
                operatorsStack.push(it.first)
            }
            TokenType.R_PAREN -> {
                while (!operatorsStack.empty() &&
                    !TokenType.isLeftParen(operatorsStack.peek())) {
                    evaluateConfig(listOfConfiguration, operatorsStack)
                }

                if (!operatorsStack.empty()) {
                    operatorsStack.pop()
                }
            }
            else -> {
                operatorsStack.push(it.first)
            }
        }
    }

    // apply remaining operators
    while (!operatorsStack.empty()) {
        evaluateConfig(listOfConfiguration, operatorsStack)
    }
}

fun getStatementType(configurations: List<Boolean>): List<StatementType> {
    val valid = configurations.all { it }
    val satisfiable = configurations.any { it }
    val contingent = configurations.any { it } && configurations.any { !it }
    val invalid = configurations.all { !it }

    return when {
        valid -> listOf(StatementType.VALID, StatementType.SATISFIABLE)
        contingent -> listOf(StatementType.CONTINGENT, StatementType.SATISFIABLE)
        satisfiable -> listOf(StatementType.SATISFIABLE)
        invalid -> listOf(StatementType.INVALID)
        else -> throw IllegalArgumentException("Unknown statement type")
    }
}