
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

fun main() {
    val input = "let num = 3-(3+4);"
    val charStream = CharStreams.fromString(input)
    val lexer = GiliLangLexer(charStream)
    val tokens = CommonTokenStream(lexer)
    val parser = GiliLangParser(tokens)

    val tree = parser.program() // parse the 'prog' rule
    println(tree.toStringTree(parser))
}
