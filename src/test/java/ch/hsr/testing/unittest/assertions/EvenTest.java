package ch.hsr.testing.unittest.assertions;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.jupiter.api.Test;

import static ch.hsr.testing.unittest.assertions.EvenMatcher.isEven;

public class EvenTest {

	@Test
	void evenNumber() {
		MatcherAssert.assertThat(16, isEven());
	}
	
	@Test
	void oddNumber() {
		MatcherAssert.assertThat(15, Matchers.not(isEven()));
	}

}

class EvenMatcher extends TypeSafeDiagnosingMatcher<Integer>{

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean matchesSafely(Integer item, Description mismatchDescription) {
		// TODO description
		return item % 2 == 0;
	}
	
	@Factory
	public static EvenMatcher isEven() {
		return new EvenMatcher();
	}
	
}
