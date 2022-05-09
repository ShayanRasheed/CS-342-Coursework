import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	CoffeeBuilder order;

	@BeforeEach
	void init() {
		order = new CoffeeBuilder();
	}

	@Test
	void test() {
		assertEquals(3.99, order.makeCoffee());
	}

	@Test
	void testTwo() {
		order.addSugar();
		assertEquals(4.49, order.makeCoffee());
	}

	@Test
	void testThree() {
		order.addCream();
		assertEquals(4.49, order.makeCoffee());
	}

	@Test
	void testFour() {
		order.addExtraShot();
		assertEquals(5.19, order.makeCoffee());
	}

	@Test
	void testFive() {
		order.addVanilla();
		assertEquals(4.34, order.makeCoffee());
	}

	@Test
	void testSix() {
		order.addWhippedCream();
		assertEquals(4.24, order.makeCoffee());
	}

	@Test
	void testSeven() {
		order.addCream();
		order.addExtraShot();

		assertEquals(5.69, order.makeCoffee());
	}

	@Test
	void testEight() {
		order.addSugar();
		order.addWhippedCream();
		order.addVanilla();

		assertEquals(5.09, order.makeCoffee());
	}

	@Test
	void testNine() {
		order.addVanilla();
		order.addExtraShot();
		order.addCream();
		order.addWhippedCream();

		assertEquals(6.29, order.makeCoffee());
	}

	@Test
	void testTen() {
		order.addCream();
		order.addSugar();
		order.addVanilla();
		order.addWhippedCream();
		order.addExtraShot();

		assertEquals(6.79, order.makeCoffee());
	}


}
