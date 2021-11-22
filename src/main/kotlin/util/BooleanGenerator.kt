package util

class BooleanGenerator(
    private val maxDepth: Int
): IGenerator<Boolean> {

    // generate all possible sequences of true and false of length maxDepth
    override fun generate(depth: Int, current: List<Boolean>): List<List<Boolean>> {
        if (depth == maxDepth) {
            return listOf(current)
        }
        return generate(depth + 1, current + true) +
               generate(depth + 1, current + false)
    }
}