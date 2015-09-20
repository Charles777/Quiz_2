package mainPackage;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TuitionTests {

	Tuition j;

	@Before
	public void setUp() throws Exception {
		j = new Tuition(12520, .07, .05, 10);
		
	}

	@After
	public void tearDown() throws Exception {
		j = null;
	}

	@Test
	public void test1() throws Exception {
		j.display();
		
	}
	
	@Test
	public void test2() throws Exception {
		j.setAPR(.01);
		j.setRepaymentTerm(5);
		assertEquals("the repayment term didn't change correctly", (long)5, (long)j.getRepaymentTerm());
		j.calculateLoan();
		j.display();
		
	}
	
	@Test
	public void test3() throws Exception {
		j.setAPR(.02);
		j.setRepaymentTerm(25);
		j.setInitialTuitionRate(15000);
		j.setTuitionIncreaseRate(.01);
		assertEquals("the tuition increase rate didn't change correctly", (long)(.01), (long)j.getTuitionIncreaseRate());
		j.calculateLoan();		
		j.display();
		
	}

}
