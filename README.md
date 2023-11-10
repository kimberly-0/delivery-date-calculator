# E-commerce delivery date calculator

This program calculates the delivery date for e-commerce orders, depending on the lead time, dispatch cut off time, whether or not delivery is on working days only, and taking into consideration bank holidays.

This code was written for a **VarsityCode challenge** created by **Next** which can be accessed [here](https://bit.ly/3rujTmc). VarsityCode is a student coding community set up by [**ShowCode**](https://www.showcode.io/).

## Original challenge brief created by Next

Next now supplies software to many third-party clients such as GAP, Victoria Secret & Reiss.
Our various third-party clients have different delivery lead times and rules.

Our clients can have the following differences:

- Lead Times (the number of days it takes to process and deliver an order)
- Dispatch Cutoff (if ordered on or after a specific time, this will add a day to the lead time)
- Working Day Delivery Only (if working day delivery only, if the delivery date falls on a weekend day, the delivery date will move to the next available weekday)

Next nor any of its clients deliver on the Christmas Bank Holidays, any deliveries scheduled for these days will move to the next available day.
The Christmas Bank Holidays are:
25/12/YYYY
26/12/YYYY
01/01/YYYY

If a Bank Holiday falls on a weekend, it will move to the next available working day which is not also a Bank Holiday.

The following inputs will be provided as strings, but are limited to the format/values noted below:
Order Date (DD/MM/YYYY HH:MM:SS)
Lead Time (Must be an unsigned integer)
Dispatch Cut Off (HH:MM:SS)
Working Day Delivery Only (True or False)

The expected results are either a date (DD/MM/YYYY) as a string or "Invalid Data" if any of the data is invalid.

## Getting started

1. Install [Java](https://www.oracle.com/java/technologies/downloads/#java17) and [Maven](https://www.baeldung.com/install-maven-on-windows-linux-mac)

2. Download the code in a ZIP file or clone the repository

``` $ git clone https://github.com/kimberly-0/ecommerce-delivery-date-calculator.git ```

3. Open `DeliveryDates.java` (located in *src/main/java/kd*) in an IDE

4. Scroll all the way down and run the `main` method
