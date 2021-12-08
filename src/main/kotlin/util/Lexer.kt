package util

import java.io.FileInputStream

enum class TokenType {
    IDENTIFIER,
    NOT,
    AND,
    OR,
    XOR,
    IMPL,
    IFF,
    L_PAREN,
    R_PAREN,
    UNKNOWN;

    companion object {
        // test if the given token is a logic operator
        fun isLogicOperator(token: TokenType): Boolean {
            return token != IDENTIFIER && token != UNKNOWN &&
                   token != L_PAREN && token != R_PAREN
        }

        // test if the given token is an identifier
        fun isIdentifier(token: TokenType): Boolean {
            return token == IDENTIFIER
        }

        // test if the given token is a left parenthesis
        fun isLeftParen(token: TokenType): Boolean {
            return token == L_PAREN
        }

        // test if the given token is a right parenthesis
        fun isRightParen(token: TokenType): Boolean {
            return token == R_PAREN
        }

        // test if the given token is an unknown symbol
        fun isUnknown(token: TokenType): Boolean {
            return token == UNKNOWN
        }

        // get the precedence of the given operator token
        fun getPrecedence(token: TokenType): Int {
            return when (token) {
                NOT -> 2
                AND -> 1
                OR -> 1
                XOR -> 1
                IMPL -> 0
                IFF -> 0
                else -> -1
            }
        }
    }
}

fun readString(filename: String): String {
    val stream = FileInputStream(filename)
    return stream.readBytes().toString(Charsets.UTF_8)
}

fun tokenize(input: String): List<Pair<TokenType, String>> {
    val tokens = mutableListOf<Pair<TokenType, String>>()
    var i = 0
    while (i < input.length) {
        when (val c = input[i]) {
            ' ' -> i++
            '(' -> {
                tokens.add(TokenType.L_PAREN to "(")
                i++
            }
            ')' -> {
                tokens.add(TokenType.R_PAREN to ")")
                i++
            }
            '!' -> {
                tokens.add(TokenType.NOT to "!")
                i++
            }
            '&' -> {
                tokens.add(TokenType.AND to "&")
                i++
            }
            '|' -> {
                tokens.add(TokenType.OR to "|")
                i++
            }
            '^' -> {
                tokens.add(TokenType.XOR to "^")
                i++
            }
            '=' -> {
                if (i + 1 < input.length && input[i + 1] == '>') {
                    tokens.add(TokenType.IMPL to "=>")
                    i += 2
                } else {
                    tokens.add(TokenType.UNKNOWN to c.toString())
                    break
                }
            }
            '<' -> {
                if (i + 1 < input.length && input[i + 1] == '=') {
                    i += if (i + 2 < input.length && input[i + 2] == '>') {
                        tokens.add(TokenType.IFF to "<=>")
                        3
                    } else {
                        tokens.add(TokenType.UNKNOWN to c.toString())
                        break
                    }
                } else {
                    tokens.add(TokenType.UNKNOWN to c.toString())
                    break
                }
            }
            in 'a'..'z' -> {
                tokens.add(TokenType.IDENTIFIER to c.toString())
                i++
            }
            else -> {
                tokens.add(TokenType.UNKNOWN to c.toString())
                break
            }
        }
    }

    return tokens
}
