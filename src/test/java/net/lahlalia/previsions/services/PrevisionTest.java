package net.lahlalia.previsions.services;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrevisionTest {
    @Test
    void getYearFromDateTest() {
        // Create a date for testing
        Date date = new Date(125, Calendar.MAY, 30); // Year is 2024, month is May (zero-based), day is 30

        // Call the method to be tested
        int year = PrevisionService.getYearFromDate(date);

        // Assert the result
        assertEquals(2025, year, "Year should be 2024 for the input date");
    }

    @Test
    void getMonthDateTest(){
        Date date = new Date(124,Calendar.MAY,30);
        int Month = PrevisionService.getMonthFromDate(date);
        assertEquals(5,Month);
    }
}
