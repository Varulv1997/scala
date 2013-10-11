package scala
package reflect
package runtime

/** An implementation of [[scala.reflect.api.Universe]] for runtime reflection using JVM classloaders.
 *
 *  Should not be instantiated directly, use [[scala.reflect.runtime.universe]] instead.
 *
 *  @contentDiagram hideNodes "*Api" "*Extractor"
 */
class JavaUniverse extends internal.SymbolTable with ReflectSetup with runtime.SymbolTable { self =>

  override def inform(msg: String): Unit = log(msg)
  def picklerPhase = internal.SomePhase
  lazy val settings = new Settings
  private val isLogging = sys.props contains "scala.debug.reflect"

  def log(msg: => AnyRef): Unit = if (isLogging) Console.err.println("[reflect] " + msg)

  type TreeCopier = InternalTreeCopierOps
  def newStrictTreeCopier: TreeCopier = new StrictTreeCopier
  def newLazyTreeCopier: TreeCopier = new LazyTreeCopier

  val currentFreshNameCreator = new reflect.internal.util.FreshNameCreator

  // can't put this in runtime.Trees since that's mixed with Global in ReflectGlobal, which has the definition from internal.Trees
  object treeInfo extends {
    val global: JavaUniverse.this.type = JavaUniverse.this
  } with internal.TreeInfo

  init()
}
