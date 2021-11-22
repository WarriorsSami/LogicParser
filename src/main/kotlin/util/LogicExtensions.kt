package util

// extend Boolean with a method that returns the logical negation
fun Boolean.logicNot(): Boolean = !this

// extend Boolean with a method that returns the logical and
fun Boolean.logicAnd(other: Boolean): Boolean = this && other

// extend Boolean with a method that returns the logical or
fun Boolean.logicOr(other: Boolean): Boolean = this || other

// extend Boolean with a method that returns the logical xor
fun Boolean.logicXor(other: Boolean): Boolean = this != other

// extend Boolean with a method that returns the logical implication
fun Boolean.logicImpl(other: Boolean): Boolean = !this || other

// extend Boolean with a method that returns the logical equivalence
fun Boolean.logicEquiv(other: Boolean): Boolean = this == other