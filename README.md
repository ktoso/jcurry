jcurry
======

The Java8 function currying library!

Q: Can we do currying and partial applcation in Java?
-----------------------------------------------------
Was a question asked on: http://stackoverflow.com/questions/6134278/does-java-support-currying/18937862#18937862

<hr/>

Well, **Scala**, Clojure or Haskell (or any other functional programming language...) are definitely *THE languages* to use for currying and other functional tricks.

Having that said is certainly possible to curry with Java without the super amounts of boilerplate one might expect (well, having to be explicit about the types hurts a lot though - just take a look at the `curried` example ;-)).

The tests bellow showcase both, *currying* a `Function3` into `Function1 => Function1 => Function1`:

```java
    @Test
    public void shouldCurryFunction() throws Exception {
      // given
      Function3<Integer, Integer, Integer, Integer> func = (a, b, c) -> a + b + c;

      // when
      Function<Integer, Function<Integer, Function<Integer, Integer>>> cur = curried(func);

      // then
      Function<Integer, Function<Integer, Integer>> step1 = cur.apply(1);
      Function<Integer, Integer> step2 = step1.apply(2);
      Integer result = step2.apply(3);

      assertThat(result).isEqualTo(6);
    }
```

as well as *partial application*, although it's not really typesafe in this example:

```java
    @Test
    public void shouldCurryOneArgument() throws Exception {
      // given
      Function3<Integer, Integer, Integer, Integer> adding = (a, b, c) -> a + b + c;

      // when
      Function2<Integer, Integer, Integer> curried = applyPartial(adding, _, _, put(1));

      // then
      Integer got = curried.apply(0, 0);
      assertThat(got).isEqualTo(1);
    }
```

This is taken from a Proof Of Concept I've just implemented for fun before JavaOne tomorrow in an hour "because I was bored" ;-) The code is available here: https://github.com/ktoso/jcurry

The general idea could be expanded to FunctionN => FunctionM, relatively easily, though "real typesafety" remains a problem for the partia application example and the currying example would need a hell lot of boilerplaty code in **jcurry**, but it's doable.

All in all, it's doable, yet in Scala it's out of the box ;-)

Disclaimer
==========
<a href="http://www.scala-lang.org/">Scala</a> is great.

License
=======
Apache 2.0
