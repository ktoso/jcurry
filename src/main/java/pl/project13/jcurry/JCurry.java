package pl.project13.jcurry;

abstract class CurryMode<T> {
  abstract boolean curried();

  public T value() {
    if (curried()) {
      throw new RuntimeException("This param is curried!");
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    if (curried()) {
      return "_";
    } else {
      return String.format("put(%s)", value());
    }
  }
}

class Currying {
  public static CurryMode _ = new CurryMode() {
    @Override
    boolean curried() {
      return true;
    }
  };

  public static <T> CurryMode<T> put(T t) {
    return new CurryMode<T>() {
      @Override
      public boolean curried() {
        return false;
      }

      @Override
      public T value() {
        return t;
      }
    };
  }
}

@FunctionalInterface
interface Function2<T1, T2, R> {
  R apply(T1 t1, T2 t2);
}

@FunctionalInterface
interface Function3<T1, T2, T3, R> {
  R apply(T1 t1, T2 t2, T3 t3);
}

public class JCurry {

  public static <T1, T2, T3, R> Function2 curry(Function3<T1, T2, T3, R> func, CurryMode<T1> m1, CurryMode<T2> m2, CurryMode<T3> m3) {
    if (!m1.curried() && m2.curried() && m3.curried()) {
      return (tt1, tt2) -> func.apply(m1.value(), (T2) tt1, (T3) tt2);

    } else if (m1.curried() && !m2.curried() && m3.curried()) {
      return (tt1, tt2) -> func.apply((T1) tt1, m2.value(), (T3) tt2);

    } else if (m1.curried() && m2.curried() && !m3.curried()) {
      return (tt1, tt2) -> func.apply((T1) tt1, (T2) tt2, m3.value());

    } else {
      throw new RuntimeException(String.format("You curried != 1 argument! [%s] [%s] [%s] ", m1, m2, m3));
    }
  }

}
