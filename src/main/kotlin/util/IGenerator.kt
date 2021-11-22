package util

interface IGenerator<T> {
    fun generate(depth: Int, current: List<T>): List<List<T>>
}