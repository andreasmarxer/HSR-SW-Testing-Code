package ch.hsr.testing.unittest.assertions;

import org.hamcrest.*;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import static ch.hsr.testing.unittest.assertions.DivisibleByMatcher.isDivisibleBy;

public class DivisibleByTest {
	@Test
	public void numberIsDivisible() {
		MatcherAssert.assertThat(16, isDivisibleBy(4));
	}
	
	@Test
	public void numberIsNotDivisible() {
		MatcherAssert.assertThat(16, Matchers.not(isDivisibleBy(5)));
	}
}

class DivisibleByMatcher extends TypeSafeDiagnosingMatcher<Integer> {

	private int by;
	
	public DivisibleByMatcher(int by) {
		this.by = by;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("A number is divisible by ").appendValue(by);
	}

	@Override
	protected boolean matchesSafely(Integer item, Description mismatchDescription) {
		// TODO mismatch Description
		return item % by == 0;
	}
	
	@Factory
	public static DivisibleByMatcher isDivisibleBy(int by) {
		return new DivisibleByMatcher(by);
	}
	
}