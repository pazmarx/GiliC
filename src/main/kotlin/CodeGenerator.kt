import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class CodeGenerator {
    // Track the next available local variable slot.
    // For the main method, slot 0 is `args`, so we start from 1.
    private var nextLocalSlot = 1
    private val variableSlots = mutableMapOf<String, Int>()

    fun generateClass(className: String, ast: ProgramNode): ByteArray {
        // Create a ClassWriter that automatically calculates stack map frames and maximum stack/local variables.
        val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS)
        // Define the class
        cw.visit(
            V1_8,
            ACC_PUBLIC,
            className,
            null,
            "java/lang/Object",
            null
        )
        
        generateDefaultConstructor(cw)

        generateMainMethod(cw, ast)

        // End of class
        cw.visitEnd()

        return cw.toByteArray()
    }

    private fun generateMainMethod(cw: ClassWriter, ast: ProgramNode) {
        // public static void main(String[] args)
        val mv = cw.visitMethod(ACC_PUBLIC or ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null)
        // Start method
        mv.visitCode()

        for (stmt in ast.statements) {
            generateStatement(mv, stmt)
        }

        mv.visitInsn(RETURN)
        // Automatically computes the necessary stack size and local variables for this method.
        mv.visitMaxs(0, 0)
        // End of method
        mv.visitEnd()
    }

    private fun generateDefaultConstructor(cw: ClassWriter) {
        val mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null)
        // Start constructor
        mv.visitCode()
        // Loads `this` (i.e., the reference to the current object instance) onto the stack.
        mv.visitVarInsn(ALOAD, 0)
        // Invoke super() => Object's constructor
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)

        mv.visitInsn(RETURN)

        // Automatically computes the necessary stack size and local variables for this method.
        mv.visitMaxs(0, 0)
        // End of method
        mv.visitEnd()
    }

    private fun generateStatement(mv: MethodVisitor, stmt: StatementNode) {
        when (stmt) {
            is PrintStatementNode -> generatePrintStatement(mv, stmt)
            is LetStatementNode -> generateLetStatement(mv, stmt)
            is AssignStatementNode -> generateAssignStatement(mv, stmt)
        }
    }

    private fun generatePrintStatement(mv: MethodVisitor, stmt: PrintStatementNode) {
        // Pushes the static field `System.out` onto the stack.
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        
        generateExpression(mv, stmt.expression)
        
        // Calls the `println(double)` method of the `PrintStream` class, 
        // which prints the double value (top of the stack) to the standard output.
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(D)V", false)
    }

    private fun generateLetStatement(mv: MethodVisitor, stmt: LetStatementNode) {
        generateExpression(mv, stmt.expression)

        // Allocate a new local variable slot for the declared variable.
        val slot = nextLocalSlot
        // Increment the local slot counter by 2 since doubles consume two slots.
        nextLocalSlot += 2
        // Map the variable identifier to its local variable slot.
        variableSlots[stmt.identifier] = slot
        // Store the double value from the top of the stack into the allocated local variable slot.
        mv.visitVarInsn(DSTORE, slot)
    }

    private fun generateAssignStatement(mv: MethodVisitor, stmt: AssignStatementNode) {
        generateExpression(mv, stmt.expression)

        val slot = variableSlots[stmt.identifier]
            ?: error("Variable ${stmt.identifier} not declared.")
        // Stores the value at the top of the stack into the local variable slot corresponding to the given identifier.
        mv.visitVarInsn(DSTORE, slot)
    }

    private fun generateExpression(mv: MethodVisitor, expr: ExpressionNode) {
        when (expr) {
            is NumberLiteralNode -> {
                when (val value = expr.value) {
                    0.0 -> mv.visitInsn(DCONST_0)
                    1.0 -> mv.visitInsn(DCONST_1)
                    else -> mv.visitLdcInsn(value)
                }
            }

            is IdentifierNode -> {
                val slot = variableSlots[expr.identifier]
                    ?: error("Variable ${expr.identifier} not declared.")
                mv.visitVarInsn(DLOAD, slot)
            }

            is BinaryExpressionNode -> {
                generateExpression(mv, expr.left)
                generateExpression(mv, expr.right)
                when (expr.operator) {
                    BinaryOperator.PLUS -> mv.visitInsn(DADD)
                    BinaryOperator.MINUS -> mv.visitInsn(DSUB)
                    BinaryOperator.MULTIPLY -> mv.visitInsn(DMUL)
                    BinaryOperator.DIVIDE -> mv.visitInsn(DDIV)
                }
            }

            is ParenExpressionNode -> {
                generateExpression(mv, expr.inner)
            }
        }
    }
}