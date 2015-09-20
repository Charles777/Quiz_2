package mainPackage;
import java.util.Scanner;
import org.apache.poi.ss.formula.functions.FinanceLib;

/**
 * @author Owner
 *
 */
public class Tuition {
	/*
	 The current tuition rate for 2015-2016 is $12,520 (not including room and board).

	 Write a program that will calculate the total cost of tuition to complete a degree and determine the monthly payment for your student loan.

	 Assumptions:
	 1) You're a freshman, this is the first year you'll be paying tuition
	 2) You plan to attend a standard four year degree. You don't drop out, take time off, etc.
	 3) Each year there is an percentage increase (variable, based on university costs)
	 4) Assume a fixed APR for your student loan.  The rate should be a variable in the program.
	 5) The repayment schedule (how many years) should be a variable in the program.
	 6) Assume the student loan uses a fixed amount for payback (in other words, you'll be paying the same amount each month when you repay the loan).


	 Hint:
	 Initial tuition cost, percentage increases for tuition, repayment APR and term should be variables read by Scanner.

	 Skills used:
	 Basic math, power function, loop (for loop), display formatted answer.

	 Advanced skills used:
	 Amortization.  Hint: https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html

	 Deliverables:
	 Java Project using JDK 1.8
	 Encapsulated class with private attributes, public methods
	 JUnit 4 test cases implemented (Don't Junit the scanner stuff, just the methods doing the calculating)
	 Methods should be well-documented, and javadocs created for project
	 Make sure to submit a .zip file (not jar, not .war, not .rar)

	 */
		
		private double initialTuitionRate;
		private double tuitionIncreaseRate;
		private double APR; 
		private int repaymentTerm; //in discrete years
		private double totalLoan; // the accumulated debt after 4 years in school
		private double monthlyPayment; //the amount to pay off each month
		
		/**
		 * The no arg constructor. Assumes you want to assign values through the console. After the object is created, the final accumulated debt is calculated.
		 */
		public Tuition(){
			
			Scanner input = new Scanner(System.in);//Inputs taken via scanner
			
			System.out.print("Enter the tuition rate your freshman year: $");
			initialTuitionRate = input.nextDouble();
			
			System.out.print("Enter the percent rise in tuition rate per year(0.012 = 1.2%): ");
			tuitionIncreaseRate = input.nextDouble();
			
			System.out.print("Enter the fixed APR you have for your loans(0.012 = 1.2%):");
			APR = input.nextDouble();
			
			System.out.print("Enter the number of months you'll repay your loan over.");
			repaymentTerm = input.nextInt();
			
			input.close();//close scanner
			
			this.calculateLoan();//calculate monthly repayments
			
			
		}
		
		
		/**
		 * The primary constructor, takes arguments. After the object is created, the final accumulated debt is calculated.
		 * @param initialTuitionRate
		 * @param tuitionIncreaseRate
		 * @param APR 
		 * @param repaymentTerm
		 */
		public Tuition(double initialTuitionRate,double tuitionIncreaseRate, double APR, int repaymentTerm)
		{
			
			if (tuitionIncreaseRate > 1.0)
				this.tuitionIncreaseRate = tuitionIncreaseRate/100.0;
			else
				this.tuitionIncreaseRate = tuitionIncreaseRate;
			
			this.initialTuitionRate = initialTuitionRate;
			
			if (APR > 1.0)//assume they mean X% when they enter an integer, not X00%
				this.APR = APR/100.0;
			else
				this.APR = APR;
			
			this.repaymentTerm = repaymentTerm;
			
			this.calculateLoan();//calculate monthly repayments
			
		}

		/**
		 * Calculates the amount you will owe at the end of your senior year, including tuition hikes and interest :'( Assumes half the year's tuition is paid at the beginning of August, and half at the beginning of January. Interest is calculated at the end of each month.
		 */
		public void calculateLoan() 
		{
			this.totalLoan = 0;
			
			for(int year = 1; year <= 4; year++){
				for(int month = 1; month <= 12; month++){
					if (month == 1 || month == 6){
						double currentTuition = this.initialTuitionRate*(1 + this.tuitionIncreaseRate*year);
						this.totalLoan = this.totalLoan + currentTuition/2;
					}
					this.totalLoan = this.totalLoan*(1 + this.APR/12);					

				}
			}
			
			//p: present value (loan)
			//f: future value (0)
			//n: number of months
			//t: (T/F)payments made at beginning of the month
			//r: monthly interest rate
			
			double p = this.totalLoan;
			double f = 0;
			double n = (int)(this.repaymentTerm*12);//number of months
			double r = this.APR/12; //monthly interest rate
			boolean t = true;//could be false too. I don't know how real loans work
			
			//calling FinanceLib to calculate payments...
			this.monthlyPayment = FinanceLib.pmt(r,n,p,f,t);


		}
		
		/**
		 * Simple output method. Displays the various variables with formatting and context.
		 */
		public void display()
		{
			System.out.printf("At the beginning of Freshman year, tuition was at $%.2f \n", this.initialTuitionRate);
			System.out.printf("Each year, tuition increases %.1f", (this.tuitionIncreaseRate*100));
			System.out.println("%\nAnd since your APR is " + (APR*100) + "%");
			System.out.printf("You will graduate with a debt of $%.2f \n", this.totalLoan);
			System.out.printf("To be paid off in %d monthly payments of $%.2f \n", (this.repaymentTerm*12), -this.monthlyPayment);
		}


		/**
		 * @return the totalLoan
		 */
		public double getTotalLoan() {
			this.calculateLoan();
			return this.totalLoan;
		}


		/**
		 * @return the monthlyPayment
		 */
		public double getMonthlyPayment() {
			this.calculateLoan();
			return this.monthlyPayment;
		}


		/**
		 * @return the initialTuitionRate
		 */
		public double getInitialTuitionRate() {
			return initialTuitionRate;
		}

		/**
		 * @param initialTuitionRate the initialTuitionRate to set
		 */
		public void setInitialTuitionRate(double initialTuitionRate) {
			this.initialTuitionRate = initialTuitionRate;
		}

		/**
		 * @return the tuitionIncreaseRate
		 */
		public double getTuitionIncreaseRate() {
			return tuitionIncreaseRate;
		}

		/**
		 * @param tuitionIncreaseRate the tuitionIncreaseRate to set
		 */
		public void setTuitionIncreaseRate(double tuitionIncreaseRate) {
			this.tuitionIncreaseRate = tuitionIncreaseRate;
		}

		/**
		 * @return the aPR
		 */
		public double getAPR() {
			return APR;
		}

		/**
		 * @param aPR the aPR to set
		 */
		public void setAPR(double aPR) {
			APR = aPR;
		}

		/**
		 * @return the repaymentTerm
		 */
		public int getRepaymentTerm() {
			return repaymentTerm;
		}

		/**
		 * @param repaymentTerm the repaymentTerm to set
		 */
		public void setRepaymentTerm(int repaymentTerm) {
			this.repaymentTerm = repaymentTerm;
		}
		
		
}
