package pl.project13.jcurry;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.jcurry.Currying._;
import static pl.project13.jcurry.Currying.put;
import static pl.project13.jcurry.JCurry.curry;

@SuppressWarnings("unchecked")
public class JCurryTest {

  @Test
  public void shouldCurryOneArgument() throws Exception {
    // given
    Function3<Integer, Integer, Integer, Integer> adding = (a, b, c) -> a + b + c;

    // when
    Function2<Integer, Integer, Integer> curried = curry(adding, _, _, put(1));

    // then
    Integer got = curried.apply(0, 0);
    assertThat(got).isEqualTo(1);
  }

  @Test
  public void shouldCurryOneArgumentWithManyTypes() throws Exception {
    // given
    Function3<Integer, Float, Integer, Double> adding = (a, b, c) -> {
      float value = a + b + c;
      return (double) value;
    };

    // when
    Function2<Integer, Integer, Double> curried = curry(adding, _, put(1.0f), _);

    // then
    Double got = curried.apply(4, 5);
    assertThat(got).isEqualTo(10.0d);
  }

}
