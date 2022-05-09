import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	French fr;
	Hindi hi;
	Spanish sp;

	@BeforeEach
	void init() {
		fr = new French();
		hi = new Hindi();
		sp = new Spanish();
	}

	@Test
	void frenchOne() {
		assertEquals("Salut ! Ça me fait plaisir de te voir.", fr.greeting());
	}

	@Test
	void frenchTwo() {
		assertEquals("Qu'est-ce que vous allez faire aujourd'hui ?", fr.question());
	}

	@Test
	void frenchThree() {
		assertEquals("Je suis en train de finir mes devoirs pour cette matiére.", fr.statement());
	}

	@Test
	void frenchFour() {
		assertEquals("Au revoir ! À la prochaine !", fr.farewell());
	}

	@Test
	void spanishOne() {
		assertEquals("¿Qué pasa? Espero que estés bien!", sp.greeting());
	}

	@Test
	void spanishTwo() {
		assertEquals("¿Has tenido algún viaje en otros países?", sp.question());
	}

	@Test
	void spanishThree() {
		assertEquals("Camarón que se duerme, se lo lleva la corriente.", sp.statement());
	}

	@Test
	void spanishFour() {
		assertEquals("Hasta luego! Nos vemos á la próxima vez!", sp.farewell());
	}

	@Test
	void hindiOne() {
		assertEquals("Namaste, aap kaise hain?", hi.greeting());
	}

	@Test
	void hindiTwo() {
		assertEquals("Dukaan se doodh la sakate hain?", hi.question());
	}

	@Test
	void hindiThree() {
		assertEquals("Main hindi to samajh sakata hoon, lekin bol nahin sakata.", hi.statement());
	}

	@Test
	void hindiFour() {
		assertEquals("Phir milenge!", hi.farewell());
	}

}
