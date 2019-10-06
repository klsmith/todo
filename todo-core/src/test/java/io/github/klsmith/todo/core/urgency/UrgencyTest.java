package io.github.klsmith.todo.core.urgency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class UrgencyTest {

	private static final LocalDateTime TEST_DATE = LocalDateTime.of(2019, 9, 6, 10, 30);

	@Test
	void testValidConstructorCalls() {
		// none of these are for use outside of factory
		new Urgency(UrgencyLevel.ASAP, null);
		new Urgency(UrgencyLevel.WHENEVER, null);
		new Urgency(UrgencyLevel.DATE, TEST_DATE);
		// this test passes as long as exceptions are not thrown
	}

	@Test
	void testInvalidAsapLevel() {
		assertThrows(InvalidUrgencyException.class, () -> {
			new Urgency(UrgencyLevel.ASAP, TEST_DATE);
		});
	}

	@Test
	void testInvalidWheneverLevel() {
		assertThrows(InvalidUrgencyException.class, () -> {
			new Urgency(UrgencyLevel.WHENEVER, TEST_DATE);
		});
	}

	@Test
	void testInvalidDateLevel() {
		assertThrows(InvalidUrgencyException.class, () -> {
			new Urgency(UrgencyLevel.DATE, null);
		});
	}

	@Test
	void testInvalidNullLevel() {
		assertThrows(InvalidUrgencyException.class, () -> {
			new Urgency(null, null);
		});
	}

	@Test
	void testIdentityEquality() {
		final Urgency urgency = new Urgency(UrgencyLevel.ASAP, null);
		assertTrue(urgency.equals(urgency), "Identity equality failure.");
		assertEquals(0, urgency.compareTo(urgency));
	}

	@Test
	void testSimpleEquality() {
		final Urgency urgencyA = new Urgency(UrgencyLevel.ASAP, null);
		final Urgency urgencyB = new Urgency(UrgencyLevel.ASAP, null);
		assertEquals(urgencyA, urgencyB);
		assertEquals(urgencyB, urgencyA);
		assertEquals(urgencyA.hashCode(), urgencyB.hashCode());
		assertEquals(0, urgencyA.compareTo(urgencyB));
		assertEquals(0, urgencyB.compareTo(urgencyA));
	}

	@Test
	void testSimpleInequality() {
		final Urgency urgencyA = new Urgency(UrgencyLevel.ASAP, null);
		final Urgency urgencyB = new Urgency(UrgencyLevel.WHENEVER, null);
		assertNotEquals(urgencyA, urgencyB);
		assertNotEquals(urgencyB, urgencyA);
		assertNotEquals(urgencyA.hashCode(), urgencyB.hashCode());
		assertNotEquals(0, urgencyA.compareTo(urgencyB));
		assertNotEquals(0, urgencyB.compareTo(urgencyA));
	}

	@Test
	void testSimpleDateEquality() {
		final Urgency urgencyA = new Urgency(UrgencyLevel.DATE, TEST_DATE);
		final Urgency urgencyB = new Urgency(UrgencyLevel.DATE, TEST_DATE);
		assertEquals(urgencyA, urgencyB);
		assertEquals(urgencyB, urgencyA);
		assertEquals(urgencyA.hashCode(), urgencyB.hashCode());
		assertEquals(0, urgencyA.compareTo(urgencyB));
		assertEquals(0, urgencyB.compareTo(urgencyA));
	}

	@Test
	void testSimpleDateInequality() {
		final Urgency urgencyA = new Urgency(UrgencyLevel.DATE, TEST_DATE);
		final Urgency urgencyB = new Urgency(UrgencyLevel.DATE, TEST_DATE.plusDays(1));
		assertNotEquals(urgencyA, urgencyB);
		assertNotEquals(urgencyB, urgencyA);
		assertNotEquals(urgencyA.hashCode(), urgencyB.hashCode());
		assertNotEquals(0, urgencyA.compareTo(urgencyB));
		assertNotEquals(0, urgencyB.compareTo(urgencyA));
	}

	@Test
	void testNullInequality() {
		final Urgency urgency = new Urgency(UrgencyLevel.DATE, TEST_DATE);
		assertNotEquals(urgency, null);
	}

	@Test
	void testLevelSorting() {
		final Urgency asap = new Urgency(UrgencyLevel.ASAP, null);
		final Urgency date = new Urgency(UrgencyLevel.DATE, TEST_DATE);
		final Urgency whenever = new Urgency(UrgencyLevel.WHENEVER, null);
		final List<Urgency> expected = Arrays.asList(asap, date, whenever);
		final List<Urgency> actual = new ArrayList<>(expected);
		Collections.reverse(actual);
		Collections.sort(actual);
		assertEquals(expected, actual);
	}

	@Test
	void testDateLevelSortsByDate() {
		final Urgency earlier = new Urgency(UrgencyLevel.DATE, TEST_DATE);
		final Urgency later = new Urgency(UrgencyLevel.DATE, TEST_DATE.plusDays(1));
		final List<Urgency> expected = Arrays.asList(earlier, later);
		final List<Urgency> actual = new ArrayList<>(expected);
		Collections.reverse(actual);
		Collections.sort(actual);
		assertEquals(expected, actual);
	}

}
