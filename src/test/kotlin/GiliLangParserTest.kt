import org.antlr.v4.runtime.misc.ParseCancellationException
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
// etc.


class GiliLangParserTest {

    private fun createGiliLangParser(code: String, useThrowingErrorListener: Boolean = true): GiliLangParser {
        val inputStream = CharStreams.fromString(code)
        val lexer = GiliLangLexer(inputStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = GiliLangParser(tokenStream)

        if (useThrowingErrorListener) {
            // Remove default console error listeners
            lexer.removeErrorListeners()
            parser.removeErrorListeners()

            // Add our throwing listener so tests fail fast on syntax errors
            lexer.addErrorListener(ThrowingErrorListener())
            parser.addErrorListener(ThrowingErrorListener())
        }

        return parser
    }

    @Test
    fun `test print statement`() {
        val code = """    
        print(3);
        """.trimIndent()

        val parser = createGiliLangParser(code)
        val tree = parser.program()

        assertNotNull(tree)
    }

    @Test
    fun `test let statement and assignment`() {
        val code = """
            let x = 5;
            x = x + 3;
        """.trimIndent()

        val parser = createGiliLangParser(code)
        val tree = parser.program()

        assertNotNull(tree)
    }

    @Test
    fun `test multiple statements`() {
        val code = """
            print(42);
            let y = 10;
            y = y * 2;
        """.trimIndent()

        val parser = createGiliLangParser(code)
        val tree = parser.program()

        assertNotNull(tree)
    }

    @Test
    fun `test expression with parentheses`() {
        val code = """
            print((1 + 2) * (3 - 4) / 5);
        """.trimIndent()

        val parser = createGiliLangParser(code)
        val tree = parser.program()

        assertNotNull(tree)
    }

    @Test
    fun `test invalid code throws exception`() {
        val code = """
            prunt(1);
        """.trimIndent()

        // We expect a ParseCancellationException due to the invalid token "prunt"
        assertThrows(ParseCancellationException::class.java) {
            val parser = createGiliLangParser(code)
            parser.program() // This should throw
        }
    }
}
