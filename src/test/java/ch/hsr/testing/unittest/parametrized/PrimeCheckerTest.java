package ch.hsr.testing.unittest.parametrized;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PrimeCheckerTest {

	@Test
	@MethodSource("createPrimeTestInput")
	void testPrimeCheckerParametrized(int input, boolean isPrime) {
		boolean result = PrimeChecker.isPrime(input);
		MatcherAssert.assertThat(result, Matchers.is(isPrime));
	}
	
	private static Stream<Arguments> createPrimeTestInput() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(2, true),
                Arguments.of(3, true),
                Arguments.of(4, false),
                Arguments.of(9, false),
                Arguments.of(22, false),
                Arguments.of(23, true),
                Arguments.of(144, false),
                Arguments.of(7187, true)
        );
    }

}
