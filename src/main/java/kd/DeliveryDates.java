package kd;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h2>E-commerce delivery date calculator</h2>
 * This class contains methods to calculate the delivery date for e-commerce
 * orders depending on the lead time, dispatch cut off time, whether or not
 * delivery is on working days only, and taking into consideration bank
 * holidays.
 * 
 * This code was written for a VarsityCode challenge created by Next.
 * VarsityCode is a student coding community created by ShowCode.
 */
public class DeliveryDates {

    /**
     * This method calculates the delivery date for an order
     * 
     * @param orderDate              The date and time the order was placed
     * @param leadTime               The number of days it takes to process and
     *                               deliver the order
     * @param dispatchCutOff         If order was placed after the dispatch cut off
     *                               time, it will add an extra day to the lead time
     * @param workingDayDeliveryOnly If working day delivery only, if the delivery
     *                               date falls on a weekend day, the delivery date
     *                               will move to the next available weekday
     * @return
     */
    public static String calculateDeliveryDate(String orderDate, String leadTime, String dispatchCutOff,
            String workingDayDeliveryOnly) {

        LocalDate orderDateObject;
        LocalTime orderTimeObject;
        LocalTime dispatchCutOffTime;
        int leadTimeNumber;
        boolean workingDayDeliveryOnlyBoolean;

        /*
         * Convert order date and time, and dispatch cut off time to date and time
         * objects + lead time to integer + working day delivery only to boolean
         */
        try {
            orderDateObject = LocalDate.parse(orderDate,
                    DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm:ss"));
            orderTimeObject = LocalTime.parse(orderDate,
                    DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm:ss"));
            dispatchCutOffTime = LocalTime.parse(dispatchCutOff,
                    DateTimeFormatter.ofPattern("HH:mm:ss"));

            leadTimeNumber = Integer.parseInt(leadTime);
            if (Integer.signum(leadTimeNumber) < 0) {
                return "Invalid Data";
            }
            workingDayDeliveryOnlyBoolean = Boolean.parseBoolean(workingDayDeliveryOnly);
        } catch (Exception e) {
            return "Invalid Data";
        }

        /*
         * Calculate whether order time is before dispatch cut off time and add a day to
         * lead time if necessary
         */
        Boolean orderTimeIsBeforeDispatchCutOffTime = ((orderTimeObject
                .compareTo(dispatchCutOffTime) >= 0) ? false : true);

        if (!orderTimeIsBeforeDispatchCutOffTime) {
            leadTimeNumber++;
        }

        /*
         * Calculate initial expected delivery date
         */
        LocalDate expectedDeliveryDate = orderDateObject.plusDays(leadTimeNumber);

        /*
         * Adjust delivery date if it falls on a bank holiday, and when delivery is
         * working day only also change it if it falls on a weekend day
         */
        if (workingDayDeliveryOnlyBoolean) {
            /*
             * Ensure delivery date doesn't fall on a weekend day or bank holiday
             */
            while (isDateInWeekend(expectedDeliveryDate) || isDateABankHoliday(expectedDeliveryDate)) {
                if (isDateInWeekend(expectedDeliveryDate)) {
                    expectedDeliveryDate = expectedDeliveryDate.plusDays(1);
                } else if (isDateABankHoliday(expectedDeliveryDate)) {
                    expectedDeliveryDate = expectedDeliveryDate.plusDays(1);
                }
            }
        } else {
            /*
             * Ensure delivery date doesn't fall on a bank holiday
             */
            while (isDateABankHoliday(expectedDeliveryDate)) {
                expectedDeliveryDate = expectedDeliveryDate.plusDays(1);
            }
        }

        /*
         * Convert expected delivery date to correct String format
         */
        DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String expectedDeliveryDateString = expectedDeliveryDate.format(newPattern);

        return expectedDeliveryDateString;
    }

    /*
     * This method checks whether a given date falls on a weekend day
     * 
     * @return true if date is a weekend day
     * 
     * @return false if date is not a weekend day
     */
    private static boolean isDateInWeekend(LocalDate date) {
        return date.getDayOfWeek().toString().equals("SATURDAY")
                || date.getDayOfWeek().toString().equals("SUNDAY");
    }

    /*
     * This method checks whether a given date falls on a bank holiday
     * 
     * @return true if date is a bank holiday
     * 
     * @return false if date is not a bank holiday
     */
    private static boolean isDateABankHoliday(LocalDate date) {

        List<LocalDate> bankHolidays = new ArrayList<LocalDate>();

        /*
         * Identify the bank holidays relevant to the expected delivery date
         */
        String[] bankHolidaysDates = { "25/12", "26/12", "1/1" };

        int dateMonth = date.getMonthValue();
        int dateYear = date.getYear();

        for (String bankHolidayDate : bankHolidaysDates) {
            int bankHolidayDayOfMonth = Integer.parseInt(bankHolidayDate.split("/")[0]);
            int bankHolidayMonth = Integer.parseInt(bankHolidayDate.split("/")[1]);
            int bankHolidayYear;

            if (dateMonth <= bankHolidayMonth) {
                bankHolidayYear = dateYear;
            } else {
                bankHolidayYear = dateYear + 1;
            }

            LocalDate expectedBankHolidayDate = LocalDate.of(bankHolidayYear, bankHolidayMonth, bankHolidayDayOfMonth);
            bankHolidays.add(expectedBankHolidayDate);
        }

        Collections.sort(bankHolidays);

        /*
         * Check if bank holiday falls on a weekend day, and if so, move to the
         * next available work day that is also not a bank holiday
         */
        for (int i = 0; i < bankHolidays.size(); i++) {
            LocalDate newDate = bankHolidays.get(i);

            while (isDateInWeekend(newDate)) {
                newDate = newDate.plusDays(1);
                while (bankHolidays.contains(newDate)) {
                    newDate = newDate.plusDays(1);
                }
            }
            bankHolidays.set(i, newDate);
        }

        /*
         * Check if the expected delivery date falls on a bank holiday
         */
        // System.out.println("Bank holidays: " + bankHolidays);
        if (bankHolidays.contains(date)) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Method for testing and debugging
     */
    private void debug() {

        /*
         * Test working day delivery only when initial expected delivery falls on
         * weekend day
         */
        String delivery1 = calculateDeliveryDate("07/09/2022 13:00:00", "17",
                "12:00:00", "True"); // "26/09/2022"
        System.out.printf(
                "-------%nOrder 1%n   Order Date: %s%n   Lead time: %s%n   Dispatch cut off: %s%n   Working day delivery only: %s%n   Delivery date: %s%n",
                "07/09/2022 13:00:00", "17", "12:00:00", "True", delivery1);

        /*
         * Test working day delivery only when initial expected delivery date falls on
         * bank holiday and when changed also on a weekend day
         */
        String delivery2 = calculateDeliveryDate("28/12/2020 11:00:00", "4", "12:00:00", "True"); // "04/01/2021"
        System.out.printf(
                "-------%nOrder 2%n   Order Date: %s%n   Lead time: %s%n   Dispatch cut off: %s%n   Working day delivery only: %s%n   Delivery date: %s%n",
                "28/12/2020 11:00:00", "4", "12:00:00", "True", delivery2);

        /*
         * Test delivery when initial expected delivery date falls on bank holiday that
         * has been moved because it fell on a weekend day
         */
        String delivery3 = calculateDeliveryDate("23/12/2021 11:00:00", "2", "12:00:00", "False"); // "25/12/2021"
        System.out.printf(
                "-------%nOrder 3%n   Order Date: %s%n   Lead time: %s%n   Dispatch cut off: %s%n   Working day delivery only: %s%n   Delivery date: %s%n",
                "23/12/2021 11:00:00", "2", "12:00:00", "False", delivery3);

        /*
         * Test working day delivery only when initial expected delivery date falls on
         * bank holiday that has been moved because it fell on a weekend day
         */
        String delivery4 = calculateDeliveryDate("23/12/2021 11:00:00", "2", "12:00:00", "True"); // "29/12/2021"
        System.out.printf(
                "-------%nOrder 4%n   Order Date: %s%n   Lead time: %s%n   Dispatch cut off: %s%n   Working day delivery only: %s%n   Delivery date: %s%n",
                "23/12/2021 11:00:00", "2", "12:00:00", "True", delivery4);

    }

    /**
     * This method is the main method to run to start the program
     */
    public static void main(String[] args) {
        new DeliveryDates().debug();
    }

}
