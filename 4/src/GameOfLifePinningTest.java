import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class GameOfLifePinningTest {
	/*
	 * READ ME: You may need to write pinning tests for methods from multiple
	 * classes, if you decide to refactor methods from multiple classes.
	 * 
	 * In general, a pinning test doesn't necessarily have to be a unit test; it can
	 * be an end-to-end test that spans multiple classes that you slap on quickly
	 * for the purposes of refactoring. The end-to-end pinning test is gradually
	 * refined into more high quality unit tests. Sometimes this is necessary
	 * because writing unit tests itself requires refactoring to make the code more
	 * testable (e.g. dependency injection), and you need a temporary end-to-end
	 * pinning test to protect the code base meanwhile.
	 * 
	 * For this deliverable, there is no reason you cannot write unit tests for
	 * pinning tests as the dependency injection(s) has already been done for you.
	 * You are required to localize each pinning unit test within the tested class
	 * as we did for Deliverable 2 (meaning it should not exercise any code from
	 * external classes). You will have to use Mockito mock objects to achieve this.
	 * 
	 * Also, you may have to use behavior verification instead of state verification
	 * to test some methods because the state change happens within a mocked
	 * external object. Remember that you can use behavior verification only on
	 * mocked objects (technically, you can use Mockito.verify on real objects too
	 * using something called a Spy, but you wouldn't need to go to that length for
	 * this deliverable).
	 */

	/* TODO: Declare all variables required for the test fixture. */
	MainFrame mf;
	MainPanel mainP;
	Cell[][] _cells;
	int size;

	@Before
	public void setUp() throws SecurityException, IllegalArgumentException, IllegalAccessException {
		/*
		 * TODO: initialize the text fixture. For the initial pattern, use the "blinker"
		 * pattern shown in:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns
		 * The actual pattern GIF is at:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#/media/File:
		 * Game_of_life_blinker.gif Start from the vertical bar on a 5X5 matrix as shown
		 * in the GIF.
		 */
		size = 5;
		//mf = new MainFrame(size);
		mainP = new MainPanel(size);
		_cells = new Cell[size][size];
		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				_cells[j][k] = Mockito.mock(Cell.class);
				if (k == 2 && (j == 1 || j == 2 || j == 2))
					Mockito.when(_cells[j][k].getAlive()).thenReturn(true);
				else
					Mockito.when(_cells[j][k].getAlive()).thenReturn(false);
			}
		}		
	}

	/* TODO: Write the three pinning unit tests for the three optimized methods */
	@Test	
	public void testIterateCell()
	{		
		mainP.setCells(_cells);		
		when(_cells[2][1].getAlive()).thenReturn(true);		
		assertTrue(mainP.iterateCell(2, 1));		
	}

	@Test
	public void testCalculateNextIteration()
	{
		
		mainP.setCells(_cells);		
		mainP.calculateNextIteration();		
		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				verify(mainP.getCells()[j][k], atLeast(1)).getAlive();
			}
		}
	}

	@Test
	public void testCellToString()
	{
		mainP.setCells(_cells);
		Cell cell = spy(new Cell());
		cell.setText("X");
		assertEquals(cell.toString(), "X");
	}
}
