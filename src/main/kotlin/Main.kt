import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.File
import java.io.FileOutputStream

const val COMPILER_OUTPUT_DIRECTORY = ".\\compilerOutput"

private fun compile(sourceCode: String, className: String): ByteArray {
    val charStream = CharStreams.fromString(sourceCode)

    val lexer = GiliLangLexer(charStream)
    val tokens = CommonTokenStream(lexer)
    val parser = GiliLangParser(tokens)
    val tree = parser.program()
    val programAst = ASTBuilder().visitProgram(tree) as ProgramNode
    val analyzer = SemanticAnalyzer()
    val errors = analyzer.checkProgram(programAst)

    if (parser.numberOfSyntaxErrors > 0 || errors.isNotEmpty())
        error("Compilation failed due to errors.")

    val generatedCode = CodeGenerator().generateClass(className, programAst)
    return generatedCode
}

private fun tryCompile(giliFile: File): ByteArray? {
    try {
        return compile(giliFile.readText(), giliFile.nameWithoutExtension)
    }
    catch (e: Exception) {
        println(e.message)
        return null
    }
}

private fun processFile(giliFile: File) {
    println("Compiling: ${giliFile.absolutePath}")
    val bytecode = tryCompile(giliFile) ?: return

    val outputDir = File(COMPILER_OUTPUT_DIRECTORY).apply { mkdirs() }
    val outputFile = File(outputDir, "${giliFile.nameWithoutExtension}.class")

    FileOutputStream(outputFile).use { it.write(bytecode) }
}

private fun processArgument(path: String) {
    val file = File(path)
    if (!file.exists()) {
        println("Warning: '$path' does not exist on the file system.")
        return
    }

    if (file.isDirectory) {
        file.listFiles()
            ?.filter { it.isFile && it.extension == "gili" }
            ?.forEach { processFile(it) }
    } else if (file.isFile && file.extension == "gili") {
        processFile(file)
    } else {
        println("Warning: '$path' is neither a .gili file nor a directory.")
    }
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("No arguments provided. Please pass paths to .gili files or directories.")
        return
    }

    args.forEach(::processArgument)
}
