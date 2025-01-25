// Generated from C:/Users/pazma/IdeaProjects/GiliC/src/main/antlr/GiliLang.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GiliLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GiliLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GiliLangParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GiliLangParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link GiliLangParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(GiliLangParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GiliLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(GiliLangParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GiliLangParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(GiliLangParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link GiliLangParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(GiliLangParser.FactorContext ctx);
}