
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream


fun compile(sourceCode: String): String {
    val charStream = CharStreams.fromString(sourceCode)
    val lexer = GiliLangLexer(charStream)
    val tokens = CommonTokenStream(lexer)
    val parser = GiliLangParser(tokens)
    val ast = ASTBuilder().visitProgram(parser.program())
//    checkSemantics(ast)                              // Semantic Analysis
//    val generatedCode = generateCode(ast)            // Code Generation
//    return generatedCode
    return sourceCode // ToDo: Delete this line
}

fun main() {
    val input = "let num = 3-(3+4);"
    val charStream = CharStreams.fromString(input)
    val lexer = GiliLangLexer(charStream)
    val tokens = CommonTokenStream(lexer)
    val parser = GiliLangParser(tokens)

    val tree = parser.program() // parse the 'prog' rule
    println(tree.toStringTree(parser))
}
