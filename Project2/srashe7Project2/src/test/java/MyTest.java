import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

class MyTest {
	HashSet<Integer> winningNums = new HashSet<>();
	HashSet<Integer> chosenNums = new HashSet<>();
	HashSet<Integer> correctlyChosen = new HashSet<>();
	Text[] gridNums = new Text[24];
	GridPane results = new GridPane();

	@BeforeEach
	void init() {
		for(int i = 1; i <= 80; i++) {
			chosenNums.add(i);
		}

		KenoGame.makeGrid(gridNums, winningNums, chosenNums, correctlyChosen, results);
	}

	@Test
	void makeGridOne() {
		for(Integer e : winningNums) {
			System.out.println(e);
		}
		assertEquals(20, winningNums.size());
	}

	@Test
	void makeGridTwo() {
		for(int i = 0; i < 20; i++) {
			assertNotNull(gridNums[i], "Wrong: " + i);
		}
	}

	@Test
	void makeGridThree() {
		for(Integer e : correctlyChosen) {
			assertTrue(chosenNums.contains(e));
		}
	}

	@Test
	void makeGridFour() {
		assertNotNull(results);
	}

	@Test
	void makeGridFive() {
		assertEquals(20, correctlyChosen.size());
	}

	@Test
	void makeGridSix() {
		for(Integer e : correctlyChosen) {
			assertTrue(winningNums.contains(e));
		}
	}

	@Test
	void makeGridSeven() {
		for(Integer e : correctlyChosen) {
			assertTrue(e <= 80);
		}
	}

	@Test
	void makeGridEight() {
		for(Integer e : winningNums) {
			assertTrue(e <= 80);
		}
	}

	@Test
	void makeGridNine() {
		for(Integer e : winningNums) {
			assertTrue(chosenNums.contains(e), "Wrong: e");
		}
	}

	@Test
	void calcResultOne() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		assertEquals(2, KenoGame.calcResult(correctlyChosen, 1));
	}

	@Test
	void calcResultTwo() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		assertEquals(1, KenoGame.calcResult(correctlyChosen, 4));
	}

	@Test
	void calcResultThree() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		assertEquals(5, KenoGame.calcResult(correctlyChosen, 4));
	}

	@Test
	void calcResultFour() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		assertEquals(75, KenoGame.calcResult(correctlyChosen, 4));
	}

	@Test
	void calcResultFive() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		assertEquals(2, KenoGame.calcResult(correctlyChosen, 8));
	}

	@Test
	void calcResultSix() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		correctlyChosen.add(5);
		assertEquals(12, KenoGame.calcResult(correctlyChosen, 8));
	}

	@Test
	void calcResultSeven() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		correctlyChosen.add(5);
		correctlyChosen.add(6);
		assertEquals(50, KenoGame.calcResult(correctlyChosen, 8));
	}

	@Test
	void calcResultEight() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		correctlyChosen.add(5);
		correctlyChosen.add(6);
		correctlyChosen.add(7);
		assertEquals(750, KenoGame.calcResult(correctlyChosen, 8));
	}

	@Test
	void calcResultNine() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		correctlyChosen.add(5);
		correctlyChosen.add(6);
		correctlyChosen.add(7);
		correctlyChosen.add(8);
		assertEquals(10000, KenoGame.calcResult(correctlyChosen, 8));
	}

	@Test
	void calcResultTen() {
		correctlyChosen.clear();
		assertEquals(5, KenoGame.calcResult(correctlyChosen, 10));
	}

	@Test
	void calcResultEleven() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		correctlyChosen.add(5);
		assertEquals(2, KenoGame.calcResult(correctlyChosen, 10));
	}

	@Test
	void calcResultTwelve() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		correctlyChosen.add(5);
		correctlyChosen.add(6);
		assertEquals(15, KenoGame.calcResult(correctlyChosen, 10));
	}

	@Test
	void calcResultThirteen() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		correctlyChosen.add(5);
		correctlyChosen.add(6);
		correctlyChosen.add(7);
		assertEquals(40, KenoGame.calcResult(correctlyChosen, 10));
	}

	@Test
	void calcResultFourteen() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		correctlyChosen.add(5);
		correctlyChosen.add(6);
		correctlyChosen.add(7);
		correctlyChosen.add(8);
		assertEquals(450, KenoGame.calcResult(correctlyChosen, 10));
	}

	@Test
	void calcResultFifteen() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		correctlyChosen.add(5);
		correctlyChosen.add(6);
		correctlyChosen.add(7);
		correctlyChosen.add(8);
		correctlyChosen.add(9);
		assertEquals(4250, KenoGame.calcResult(correctlyChosen, 10));
	}

	@Test
	void calcResultSixteen() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		correctlyChosen.add(4);
		correctlyChosen.add(5);
		correctlyChosen.add(6);
		correctlyChosen.add(7);
		correctlyChosen.add(8);
		correctlyChosen.add(9);
		correctlyChosen.add(10);
		assertEquals(100000, KenoGame.calcResult(correctlyChosen, 10));
	}

	@Test
	void calcResultSeventeen() {
		correctlyChosen.clear();
		correctlyChosen.add(1);
		correctlyChosen.add(2);
		correctlyChosen.add(3);
		assertEquals(0, KenoGame.calcResult(correctlyChosen, 10));
	}
}
