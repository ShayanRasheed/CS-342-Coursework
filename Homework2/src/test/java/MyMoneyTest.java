import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyMoneyTest {
	    private MyMoney m1;
		private MyMoney m2;
		private MyMoney m3;
		private static int iter;

		@BeforeAll
		static void setup() {
			iter = 0;
		}

		@BeforeEach
		void init() {
			m1 = new MyMoney("values.txt", 8, 1);
			m2 = new MyMoney("values2.txt", 8, 2);
			m3 = new MyMoney("values.txt", "values2.txt", 8, 8);
		}

		@Test
		void constructOne() {
			double[] expected = {4000, 5500, 15000, 18000, 24000, 9000, 11000, 12000};
			assertNull(m1.getInterestValues(), "ConstructOne: Array is not Null");
			assertArrayEquals(expected, m1.getCashValues(), "ConstructOne: Incorrect Cash Array Values");
		}

		@Test
		void constructTwo() {
			double[] expected = {4000, 5500, 15000, 18000, 24000, 9000, 11000, 12000};
			double[] expected2 = {.055, .075, .045, .09, .10, .065, .035, .025};

			assertNotNull(m3.getCashValues(), "ConstructTwo: Null Array 1");
			assertNotNull(m3.getInterestValues(), "ConstructTwo: Null Array 2");

			assertArrayEquals(expected, m3.getCashValues(), "ConstructTwo: Cash Array Error");
			assertArrayEquals(expected2, m3.getInterestValues(), "ConstructTwo: Interest Array Error");
		}

		@Test
		void ConstuctThree() {
			double[] expected = {.055, .075, .045, .09, .10, .065, .035, .025};
			assertArrayEquals(expected, m2.getInterestValues(), "ConstructThree: Incorrect Interest Array Values");
			assertNull(m2.getCashValues(), "ConstructThree: Array is not Null");
		}

		@Test
		void FormulaOne() {
			assertEquals(12763, Math.round(SavingsFormulas.futureValueLumpSum(10000, .05, 5)), "FormulaOne: Wrong Value 1");
			assertEquals(39343, Math.round(SavingsFormulas.futureValueLumpSum(20000, .07, 10)), "FormulaOne: Wrong Value 2");

			assertEquals(12763, Math.round(m1.lumpSum_ConstantRate(10000, .05, 5)), "FormulaOne: Wrong Value 3");
			assertEquals(39343, Math.round(m3.lumpSum_ConstantRate(20000, .07, 10)), "FormulaOne: Wrong Value 4");
		}


		@Test
		void FormulaTwo() {
			assertEquals(32110, Math.round(SavingsFormulas.futureValueLS_VariableInterest(20000, m3.getInterestValues())), "FormulaTwo: Wrong Value 1");
			assertEquals(16055, Math.round(SavingsFormulas.futureValueLS_VariableInterest(10000, m2.getInterestValues())), "FormulaTwo: Wrong Value 2");

			assertEquals(32110, Math.round(m3.lumpSum_VariableRate(20000)), "FormulaTwo: Wrong Value 3");
			assertEquals(16055, Math.round(m2.lumpSum_VariableRate(10000)), "FormulaTwo: Wrong Value 4");
		}

		@Test
		void FormulaThree() {
			assertEquals(5526, Math.round(SavingsFormulas.compoundSavingsConstant(1000, .05, 5)), "FormulaThree: Wrong Value 1");
			assertEquals(27633, Math.round(SavingsFormulas.compoundSavingsConstant(2000, .07, 10)), "FormulaThree: Wrong Value 2");

			assertEquals(5526, Math.round(m1.compoundSavings_sameContribution(1000, .05, 5)), "FormulaThree: Wrong Value 3");
			assertEquals(27633, Math.round(m1.compoundSavings_sameContribution(2000, .07, 10)), "FormulaThree: Wrong Value 4");
		}

		@Test
		void FormulaFour() {
			assertEquals(122785, Math.round(SavingsFormulas.compoundSavingsVariable(m3.getCashValues(), 0.07)), "FormulaFour: Wrong Value 1");
			assertEquals(115278, Math.round(SavingsFormulas.compoundSavingsVariable(m1.getCashValues(), 0.05)), "FormulaFour: Wrong Value 2");

			assertEquals(122785, Math.round(m3.compoundSavings_variableContribution(0.07)), "FormulaFour: Wrong Value 3");
			assertEquals(115278, Math.round(m1.compoundSavings_variableContribution(0.05)), "FormulaFour: Wrong Value 4");
		}

		@ParameterizedTest
		@ValueSource(ints = {4000, 5500, 15000, 18000, 24000, 9000, 11000, 12000})
		void ParamTest(int val) {
			assertEquals(val, m1.getCashValues()[iter]);
			iter++;
		}
}
