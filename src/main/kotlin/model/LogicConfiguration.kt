package model

import java.util.*

class LogicConfiguration(
    val booleanConfiguration: List<Boolean>,
    val operandsMap: Map<String, Int>
) {

    var result: Boolean = false
    var operandsStack = Stack<Boolean>()
}
