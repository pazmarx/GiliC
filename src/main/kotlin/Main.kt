
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream


fun compile(sourceCode: String): String {
    val charStream = CharStreams.fromString(sourceCode)

    // Lex
    val lexer = GiliLangLexer(charStream) // Ensure GiliLangLexer is generated and imported

    // Tokenize
    val tokens = CommonTokenStream(lexer)

    // Parse
    val parser = GiliLangParser(tokens)
    val tree = parser.program()

    // Build AST
    val programAst = ASTBuilder().visitProgram(tree) as ProgramNode

    // Semantic Analysis
    val analyzer = SemanticAnalyzer()
    val errors = analyzer.checkProgram(programAst)

    if (parser.numberOfSyntaxErrors > 0 || errors.isNotEmpty())
        error("Compilation failed due to errors.")

//    val generatedCode = generateCode(ast)            // Code Generation
//    return generatedCode
    return sourceCode // ToDo: Delete this line
}

fun main() {
    val input = """
        let num = 3-(3+4);
        print(num);
        let x = num + 7;
        x = 3;
        """.trimIndent()

    println("Input:\n$input\n-----")
//    val charStream = CharStreams.fromString(input)
//    val lexer = GiliLangLexer(charStream)
//    val tokens = CommonTokenStream(lexer)
//    val parser = GiliLangParser(tokens)
//    val tree = parser.program()
//    println(tree.toStringTree(parser))
//
//    val programAst = ASTBuilder().visitProgram(tree) as ProgramNode
//
//    println(programAst)
    try{
        val output = compile(input)
        println("Output:\n$output")
    }
    catch (e: Exception){
        println(e.message)
    }
}
