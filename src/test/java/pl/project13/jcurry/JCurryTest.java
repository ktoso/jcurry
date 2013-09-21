package pl.project13.jcurry;

import org.junit.Test;

import java.util.function.Function;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.jcurry.Currying._;
import static pl.project13.jcurry.Currying.put;
import static pl.project13.jcurry.JCurry.applyPartial;
import static pl.project13.jcurry.JCurry.curry;

@SuppressWarnings("unchecked")
public class JCurryTest {

  @Test
  public void shouldFixOneArgument() throws Exception {
    // given
    Function3<Integer, Integer, Integer, Integer> adding = (a, b, c) -> a + b + c;

    // when
    Function2<Integer, Integer, Integer> curried = applyPartial(adding, _, _, put(1));

    // then
    Integer got = curried.apply(0, 0);
    assertThat(got).isEqualTo(1);
  }

  @Test
  public void shouldFixOneArgumentWithManyTypes() throws Exception {
    // given
    Function3<Integer, Float, Integer, Double> adding = (a, b, c) -> {
      float value = a + b + c;
      return (double) value;
    };

    // when
    Function2<Integer, Integer, Double> curried = applyPartial(adding, _, put(1.0f), _);

    // then
    Double got = curried.apply(4, 5);
    assertThat(got).isEqualTo(10.0d);
  }

  @Test
  public void shouldCurryFunction() throws Exception {
    // given
    Function3<Integer, Integer, Integer, Integer> func = (a, b, c) -> a + b + c;

    // when
    Function<Integer, Function<Integer, Function<Integer, Integer>>> cur = curry(func);

    // then
    Function<Integer, Function<Integer, Integer>> step1 = cur.apply(1);
    Function<Integer, Integer> step2 = step1.apply(2);
    Integer result = step2.apply(3);

    assertThat(result).isEqualTo(6);
  }

}
