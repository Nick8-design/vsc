/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ecd;

/**
 *
 * @author Nick Dieda
 */
import java.util.Arrays;

public class ECD {
    private int day;
    private int month;
    private int year;
    private final int[] daysPerMonth;
    private final String[] daysNames;
    private final String[] monthsNames;
    private final int[] publicHolidays;

    ECD() {
        this.daysPerMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        this.daysNames = new String[]{"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
        this.monthsNames = new String[]{"January", "February", "March", "April",
                "May", "June", "July", "August", "September", "October", "November", "December"};
        this.publicHolidays = new int[]{1, 121, 601, 1010, 2010, 1225, 1226};
    }

    public void setDay(int d) {
        day = d;
    }

    public int getDay() {
        return day;
    }

    public void setMonth(int m) {
        month = m;
    }

    public int getMonth() {
        return month;
    }

    public void setYear(int y) {
        year = y;
    }

    public int getYear() {
        return year;
    }

    public int setFirstJan() {
        return (5 * ((year - 1) % 4) + 4 * ((year - 1) % 100) + 6 *
                ((year - 1) % 400)) % 7;
    }

    public void isLeap() {
        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
            daysPerMonth[1] = 29;
        } else {
            daysPerMonth[1] = 28;
        }
    }

    public int serviceDay() {
        int sum = setFirstJan();
        isLeap();
        for (int i = (month - 2); i >= 0; i--) {
            sum += daysPerMonth[i];
        }
        sum = sum + day;
        return sum % 7;
    }

    public int calfingDay() {
        int sum = serviceDay() + 282;
        return sum % 7;
    }

    public void calfing() {
        if (serviceDay() == 0) {
            System.out.print("\u001B[42mServed on " + daysNames[serviceDay() + 6] + " " + day + "-" + monthsNames[month - 1] + "-" + year + "\u001B[0m\n");
        } else {
            System.out.print("\u001B[42mServed on " + daysNames[serviceDay() - 1] + " " + day + "-" + monthsNames[month - 1] + "-" + year + "\u001B[0m\n");
        }

        int gestationWeeks = 282 / 7;
        int daysLeft = 282 % 7;
        isLeap();
        boolean counter = true;
        int count = 1;
        while (count <= gestationWeeks) {
            int dateCounter = day + 7;
            int checker = dateCounter - daysPerMonth[month - 1];
            if (checker > 0) {
                month = (month + 1);
                day = checker;
            } else {
                day = dateCounter;
            }
            if (month == 12 && counter) {
                year = year + 1;
                isLeap();
                month = 1;
                day = checker;
                counter = false;
            }
            count++;
        }

        month = month - 1;
        if (month == 0) {
            month = 12;
        }

        int calfingDay = day + daysLeft;
        day = calfingDay;

        if (calfingDay() == 0) {
            System.out.print("\u001B[42mCalfing on " + daysNames[calfingDay() + 6] + " " + day + "-" + monthsNames[month - 1] + "-" + year + "\u001B[0m\n");
        } else if (calfingDay() < 3) {
            System.out.print("\u001B[42mCalfing on " + daysNames[calfingDay() + 4] + " " + day + "-" + monthsNames[month - 1] + "-" + year + "\u001B[0m\n");
        } else if (calfingDay() == 3) {
            System.out.print("\u001B[42mCalfing on " + daysNames[0] + " " + day + "- " + monthsNames[month - 1] + "-" + year + "\u001B[0m\n");
        } else {
            System.out.print("\u001B[42mCalfing on " + daysNames[calfingDay() - 3] + " " + day + "-" + monthsNames[month - 1] + "-" + year + "\u001B[0m\n");
        }
    }

    public void calendar() {
        final String CYAN = "\u001B[36m";
        final String BLUE = "\u001B[34m";
        final String RED = "\u001B[31m";
        final String RESET = "\u001B[0m";

        int sum = setFirstJan();
        if (month == 1) {
            month = 12;
        }
        for (int i = month - 2; i >= 0; i--) {
            sum += daysPerMonth[i];
        }
        int dayIndex = sum % 7;

        System.out.print(CYAN + "\n\nCalendar for " + monthsNames[month - 1] + " " + year + "\n" + RESET);

        for (int i = 0; i < daysNames.length; i++) {
            if (i == 5) {
                System.out.print(BLUE + daysNames[i] + RESET + "\t");
            } else if (i == 6) {
                System.out.print(RED + daysNames[i] + RESET);
                System.out.print("\n");
            } else {
                System.out.print(CYAN + daysNames[i] + RESET + "\t");
            }
        }

        if (Arrays.stream(publicHolidays).anyMatch(x -> x == 101)) {
            System.out.print(RED + " 1" + RESET);
        } else {
            System.out.print(CYAN + " 1" + RESET);
        }

        for (int j = 2; j <= daysPerMonth[month - 2]; j++) {
            if (dayIndex == 5) {
                System.out.print(BLUE + " " + j + RESET + "\n");
            } else if (dayIndex == 6) {
                System.out.print(RED + " " + j + RESET + "\n");
            } else {
                int finalJ = j;
                if (Arrays.stream(publicHolidays).anyMatch(x -> x == finalJ)) {
                    System.out.print(RED + " " + j + RESET + "\t");
                } else {
                    System.out.print(CYAN + " " + j + RESET + "\t");
                }
            }
            dayIndex = (dayIndex + 1) % 7;
        }

        dayIndex = (dayIndex + daysPerMonth[month - 2]) % 7;
        int count = 1;

        for (int k = 1; k <= daysPerMonth[month - 1]; k++) {
            if (dayIndex == 5) {
                System.out.print(BLUE + k + "\n" + RESET);
            } else if (dayIndex == 6) {
                System.out.print(RED + k + "\n" + RESET);
            } else {
                int finalK = k;
                if (Arrays.stream(publicHolidays).anyMatch(x -> x == finalK)) {
                    System.out.print(RED + k + RESET + "\t");
                } else {
                    if (day == k) {
                        System.out.print("\u001B[42m" + k + "\u001B[0m" + "\t");
                    } else {
                        System.out.print(CYAN + k + RESET + "\t");
                    }
                }
            }
            dayIndex = (dayIndex + 1) % 7;
        }

        dayIndex = (dayIndex + daysPerMonth[month - 1]) % 7;
        int weeksPrinted = 0;

        for (int k = 1; k <= daysPerMonth[month - 1]; k++) {
            if (dayIndex == 5) {
                System.out.print(BLUE + k + RESET + "\n");
            } else if (dayIndex == 6) {
                System.out.print(RED + k + RESET + "\n");
            } else {
                int finalK = k;
                if (Arrays.stream(publicHolidays).anyMatch(x -> x == finalK)) {
                    System.out.print(RED + k + RESET + "\t");
                } else {
                    if (day == k) {
                        System.out.print("\u001B[42m" + k + "\u001B[0m" + "\t");
                    } else {
                        System.out.print(CYAN + k + RESET + "\t");
                    }
                }
            }
            dayIndex = (dayIndex + 1) % 7;
            weeksPrinted++;

            if (weeksPrinted >= 2) {
                break;
            }
        }
    }

    public static class ECDTest {
        public static void main(String[] args) {
            ECD ecd = new ECD();
            ecd.setYear(2019);
            ecd.setMonth(5);
            ecd.setDay(1);
            ecd.calfing();
            ecd.calendar();
            boolean checker = true;

            for (int m = 1; m < 3; m++) {
                int updateMonth = ecd.getMonth();
                if (updateMonth < 12 && updateMonth > 1) {
                    ecd.setYear(ecd.getYear());
                    ecd.setMonth(ecd.getMonth() - 1);
                    ecd.calendar();
                } else if (updateMonth == 1 && checker == true) {
                    ecd.setYear(ecd.getYear() - 1);
                    ecd.setMonth(12);
                    ecd.calendar();
                    checker = false;
                } else if (updateMonth == 12) {
                    ecd.setYear(ecd.getYear() - 1);
                    ecd.setMonth(updateMonth);
                    ecd.calendar();
                    ecd.setMonth(ecd.getMonth() - 1);
                }
            }
        }
    }
}


