class CSuper {
  object A
}
class C extends CSuper {
  def f = (A: AnyRef @unchecked) match { case _: A.type  => "joepie" }
}

object Test extends C with App {
  println(f)
}
