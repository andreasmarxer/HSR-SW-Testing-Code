package ch.hsr.testing.unittest.weekenddiscount;

import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.stream.Stream;

class WeekendDiscountTimeSlotValidatorTest {
	
	private WeekendDiscountTimeSlotValidator validatorUnderTest = new WeekendDiscountTimeSlotValidator();

	@Test
	void testExceptionWhenWeekendNumberNotInitialized() throws IllegalWeekendNumberException{
		
		// Arrange
		LocalDateTime time = LocalDateTime.of(2023, 10, 14, 0, 0); // Sat 14.10.2023 00:00
		
		// Act and Assert
		IllegalWeekendNumberException thrown = Assertions.assertThrows(IllegalWeekendNumberException.class, () -> {
			validatorUnderTest.isAuthorizedForDiscount(time);
		});
		Assertions.assertEquals("WeekendDiscountTimeSlotValidator has not been initialized correctly!", thrown.getMessage());
	}
	
	@Test
	void testExceptionWhenWeekendNumberInvalidInitializedWithValue0() throws IllegalWeekendNumberException{
		
		// Arrange
		validatorUnderTest.initializeWithWeekendNumber(0);
		LocalDateTime time = LocalDateTime.of(2023, 10, 14, 0, 0); // Sat 14.10.2023 00:00
		
		// Act and Assert
		Assertions.assertThrows(DateTimeException.class, () -> {
			validatorUnderTest.isAuthorizedForDiscount(time);
		});
	}
	
	@Test
	void testExceptionWhenWeekendNumberInvalidInitializedWithValue6() throws IllegalWeekendNumberException{
		
		// Arrange
		validatorUnderTest.initializeWithWeekendNumber(6);
		LocalDateTime time = LocalDateTime.of(2023, 10, 14, 0, 0); // Sat 14.10.2023 00:00
		
		// Act and Assert
		Assertions.assertThrows(DateTimeException.class, () -> {
			validatorUnderTest.isAuthorizedForDiscount(time);
		});
	}
	
	@ParameterizedTest()
	@MethodSource("weekendDaysInOctober2023")
	void testAuthorizationOnWeekendsInOctober2023(int weekendNumber, LocalDateTime time, boolean expectedDiscount) throws IllegalWeekendNumberException{
		
		// Arrange
		validatorUnderTest.initializeWithWeekendNumber(weekendNumber);
		
		// Act
		boolean resultedDiscount = validatorUnderTest.isAuthorizedForDiscount(time);
		
		// Assert
		MatcherAssert.assertThat(resultedDiscount, Matchers.is(expectedDiscount));
	}
	
    private static Stream<Arguments> weekendDaysInOctober2023() {
        return Stream.of(
    		// Overlapping from September
        	Arguments.of(5, LocalDateTime.of(2023, 9, 30, 00, 00),  true),  // Sat 31.09.2023 00:00 - 5th of September
        	
        	// 1st weekend
    		Arguments.of(1, LocalDateTime.of(2023, 9, 30, 00, 00),  false), // Sat 31.09.2023 00:00 - 5th of September
            Arguments.of(1, LocalDateTime.of(2023, 10, 1, 23, 59),  true),  // Sun 01.10.2023 23:59 - 1st of October
            Arguments.of(1, LocalDateTime.of(2023, 10, 7, 00, 00),  false), // Sat 07.10.2023 00:00 - 2nd of October
            Arguments.of(1, LocalDateTime.of(2023, 10, 8, 23, 59),  false), // Sun 08.10.2023 23:59 - 2nd of October
            Arguments.of(1, LocalDateTime.of(2023, 10, 14, 00, 00), false), // Sat 14.10.2023 00:00 - 3rd of October
            Arguments.of(1, LocalDateTime.of(2023, 10, 15, 23, 59), false), // Sun 15.10.2023 23:59 - 3rd of October
            Arguments.of(1, LocalDateTime.of(2023, 10, 21, 00, 00), false), // Sat 21.10.2023 00:00 - 4th of October
            Arguments.of(1, LocalDateTime.of(2023, 10, 22, 23, 59), false), // Sun 22.10.2023 23:59 - 4th of October
            Arguments.of(1, LocalDateTime.of(2023, 10, 28, 00, 00), false), // Sat 28.10.2023 00:00 - 5th of October
            Arguments.of(1, LocalDateTime.of(2023, 10, 29, 23, 59), false), // Sun 29.10.2023 23:59 - 5th of October
            
    		// 2nd weekend
            Arguments.of(2, LocalDateTime.of(2023, 10, 1, 23, 59), false),  // Sun 01.10.2023 23:59 - 1st of October
            Arguments.of(2, LocalDateTime.of(2023, 10, 7, 00, 00), true),   // Sat 07.10.2023 00:00 - 2nd of October
            Arguments.of(2, LocalDateTime.of(2023, 10, 8, 23, 59), true),   // Sun 08.10.2023 23:59 - 2nd of October
            Arguments.of(2, LocalDateTime.of(2023, 10, 14, 00, 00), false), // Sat 14.10.2023 00:00 - 3rd of October
            Arguments.of(2, LocalDateTime.of(2023, 10, 15, 23, 59), false), // Sun 15.10.2023 23:59 - 3rd of October
            Arguments.of(2, LocalDateTime.of(2023, 10, 21, 00, 00), false), // Sat 21.10.2023 00:00 - 4th of October
            Arguments.of(2, LocalDateTime.of(2023, 10, 22, 23, 59), false), // Sun 22.10.2023 23:59 - 4th of October
            Arguments.of(2, LocalDateTime.of(2023, 10, 28, 00, 00), false), // Sat 28.10.2023 00:00 - 5th of October
            Arguments.of(2, LocalDateTime.of(2023, 10, 29, 23, 59), false), // Sun 29.10.2023 23:59 - 5th of October
            
    		// 3rd weekend
            Arguments.of(3, LocalDateTime.of(2023, 10, 1, 23, 59),  false), // Sun 01.10.2023 23:59 - 1st of October
            Arguments.of(3, LocalDateTime.of(2023, 10, 7, 00, 00),  false), // Sat 07.10.2023 00:00 - 2nd of October
            Arguments.of(3, LocalDateTime.of(2023, 10, 8, 23, 59),  false), // Sun 08.10.2023 23:59 - 2nd of October
            Arguments.of(3, LocalDateTime.of(2023, 10, 14, 00, 00), true),  // Sat 14.10.2023 00:00 - 3rd of October
            Arguments.of(3, LocalDateTime.of(2023, 10, 15, 23, 59), true),  // Sun 15.10.2023 23:59 - 3rd of October
            Arguments.of(3, LocalDateTime.of(2023, 10, 21, 00, 00), false), // Sat 21.10.2023 00:00 - 4th of October
            Arguments.of(3, LocalDateTime.of(2023, 10, 22, 23, 59), false), // Sun 22.10.2023 23:59 - 4th of October
            Arguments.of(3, LocalDateTime.of(2023, 10, 28, 00, 00), false), // Sat 28.10.2023 00:00 - 5th of October
            Arguments.of(3, LocalDateTime.of(2023, 10, 29, 23, 59), false), // Sun 29.10.2023 23:59 - 5th of October
            
            // 4th weekend
            Arguments.of(4, LocalDateTime.of(2023, 10, 1, 23, 59),  false), // Sun 01.10.2023 23:59 - 1st of October
            Arguments.of(4, LocalDateTime.of(2023, 10, 7, 00, 00),  false), // Sat 07.10.2023 00:00 - 2nd of October
            Arguments.of(4, LocalDateTime.of(2023, 10, 8, 23, 59),  false), // Sun 08.10.2023 23:59 - 2nd of October
            Arguments.of(4, LocalDateTime.of(2023, 10, 14, 00, 00), false), // Sat 14.10.2023 00:00 - 3rd of October
            Arguments.of(4, LocalDateTime.of(2023, 10, 15, 23, 59), false), // Sun 15.10.2023 23:59 - 3rd of October
            Arguments.of(4, LocalDateTime.of(2023, 10, 21, 00, 00), true),  // Sat 21.10.2023 00:00 - 4th of October
            Arguments.of(4, LocalDateTime.of(2023, 10, 22, 23, 59), true),  // Sun 22.10.2023 23:59 - 4th of October
            Arguments.of(4, LocalDateTime.of(2023, 10, 28, 00, 00), false), // Sat 28.10.2023 00:00 - 5th of October
            Arguments.of(4, LocalDateTime.of(2023, 10, 29, 23, 59), false), // Sun 29.10.2023 23:59 - 5th of October
            
            // 5th weekend
            Arguments.of(5, LocalDateTime.of(2023, 10, 1, 23, 59),  false), // Sun 01.10.2023 23:59 - 1st of October
            Arguments.of(5, LocalDateTime.of(2023, 10, 7, 00, 00),  false), // Sat 07.10.2023 00:00 - 2nd of October
            Arguments.of(5, LocalDateTime.of(2023, 10, 8, 23, 59),  false), // Sun 08.10.2023 23:59 - 2nd of October
            Arguments.of(5, LocalDateTime.of(2023, 10, 14, 00, 00), false), // Sat 14.10.2023 00:00 - 3rd of October
            Arguments.of(5, LocalDateTime.of(2023, 10, 15, 23, 59), false), // Sun 15.10.2023 23:59 - 3rd of October
            Arguments.of(5, LocalDateTime.of(2023, 10, 21, 00, 00), false), // Sat 21.10.2023 00:00 - 4th of October
            Arguments.of(5, LocalDateTime.of(2023, 10, 22, 23, 59), false), // Sun 22.10.2023 23:59 - 4th of October
            Arguments.of(5, LocalDateTime.of(2023, 10, 28, 00, 00), true),  // Sat 28.10.2023 00:00 - 5th of October
            Arguments.of(5, LocalDateTime.of(2023, 10, 29, 23, 59), true)   // Sun 29.10.2023 23:59 - 5th of October
        );
    }

}
