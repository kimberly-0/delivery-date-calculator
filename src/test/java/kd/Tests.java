package kd;

import org.junit.Test;
import static org.junit.Assert.*;

public class Tests {

    /**
     * Test invalid data
     * 
     * @author Next
     */
    @Test
    public void testInvalidData() {
        DeliveryDates solution = new DeliveryDates();

        assertEquals("Invalid Data", solution.calculateDeliveryDate("Order Date", "Lead Time", "Dispatch Cut Off",
                "Working Day Delivery Only"));
    }

    /**
     * Test order placed after dispatch cut off
     * 
     * @author Next
     */
    @Test
    public void testOrderAfterDispatchCutOff() {
        DeliveryDates solution = new DeliveryDates();

        assertEquals("25/09/2022", solution.calculateDeliveryDate("07/09/2022 13:00:00", "17", "12:00:00", "False"));
    }

    /**
     * Test negative lead time
     * 
     * @author Kimberly Dijkmans
     */
    @Test
    public void testNegativeLeadTime() {
        DeliveryDates solution = new DeliveryDates();

        assertEquals("Invalid Data", solution.calculateDeliveryDate("12/09/2022 10:00:00", "-2", "12:00:00", "False"));
    }

    /**
     * Test order placed before dispatch cut off
     * 
     * @author Kimberly Dijkmans
     */
    @Test
    public void testOrderBeforeDispatchCutOff() {
        DeliveryDates solution = new DeliveryDates();

        assertEquals("15/09/2022", solution.calculateDeliveryDate("10/09/2022 10:00:00", "5", "12:00:00", "False"));
    }

    /**
     * Test working day delivery only when initial expected delivery falls on
     * weekend day
     * 
     * @author Kimberly Dijkmans
     */
    @Test
    public void testWorkingDayDeliveryOnlyThatFallsOnWeekend() {
        DeliveryDates solution = new DeliveryDates();

        assertEquals("26/09/2022", solution.calculateDeliveryDate("07/09/2022 13:00:00", "17", "12:00:00", "True"));
    }

    /**
     * Test working day delivery only when initial expected delivery date falls on
     * bank holiday and when changed also on a weekend day
     * 
     * @author Kimberly Dijkmans
     */
    @Test
    public void testWorkingDayDeliveryOnlyThatFallsOnBankHolidayAndWeekend() {
        DeliveryDates solution = new DeliveryDates();

        assertEquals("04/01/2021", solution.calculateDeliveryDate("28/12/2020 11:00:00", "4", "12:00:00", "True"));
    }

    /**
     * Test delivery when initial expected delivery date falls on bank holiday that
     * has been moved because it fell on a weekend day
     * 
     * @author Kimberly Dijkmans
     */
    @Test
    public void testDeliveryWithBankHolidayThatWasMoved() {
        DeliveryDates solution = new DeliveryDates();

        assertEquals("25/12/2021", solution.calculateDeliveryDate("23/12/2021 11:00:00", "2", "12:00:00", "False"));
    }

    /**
     * Test working day delivery only when initial expected delivery date falls on
     * bank holiday that has been moved because it fell on a weekend day
     * 
     * @author Kimberly Dijkmans
     */
    @Test
    public void testWorkingDayDeliveryOnlyWithBankHolidayThatWasMoved() {
        DeliveryDates solution = new DeliveryDates();

        assertEquals("29/12/2021", solution.calculateDeliveryDate("23/12/2021 11:00:00", "2", "12:00:00", "True"));
    }

    /**
     * Test same-day delivery (lead time being 0 days)
     * 
     * @author Kimberly Dijkmans
     */
    @Test
    public void testLeadTimeSameDay() {
        DeliveryDates solution = new DeliveryDates();

        assertEquals("12/09/2022", solution.calculateDeliveryDate("12/09/2022 10:00:00", "0", "12:00:00", "False"));
    }

    /**
     * Test lead time of 0 days but ordered after dispatch cut off time
     * 
     * @author Kimberly Dijkmans
     */
    @Test
    public void testLeadTimeZeroDaysOrderedAfterDispatchCutOff() {
        DeliveryDates solution = new DeliveryDates();

        assertEquals("13/09/2022", solution.calculateDeliveryDate("12/09/2022 14:00:00", "0", "12:00:00", "False"));
    }

}