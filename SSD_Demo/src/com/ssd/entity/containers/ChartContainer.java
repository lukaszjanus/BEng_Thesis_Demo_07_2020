package com.ssd.entity.containers;

/**
 * Class-container to prepare data for Charts. This class is used to show
 * quantity of one or two Objects.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 11 kwi 2020
 *
 */
public class ChartContainer {

	/**
	 * Column for x-axis.
	 */
	public String column1;

	/**
	 * Value column with quantity of presented report.
	 */
	public Long column2;

	/**
	 * Value column - used in two companies/users for compare quantity of tickts.
	 */
	public Long column3;

	/* standard constructors */

	public ChartContainer() {
	}

	/* controller to generate value for x-line */
	public ChartContainer(String column1) {
		this.column1 = column1;
	}

	/* standard methods - getters, setters, toString */

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public Long getColumn2() {
		return column2;
	}

	public void setColumn2(Long column2) {
		this.column2 = column2;
	}

	public Long getColumn3() {
		return column3;
	}

	public void setColumn3(Long column3) {
		this.column3 = column3;
	}

	@Override
	public String toString() {
		return "ChartContainer [column1=" + column1 + ", column2=" + column2 + ", column3=" + column3 + "]";
	}
}