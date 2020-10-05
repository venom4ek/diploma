package ru.netology.web.data.genData;

import com.github.javafaker.Faker;
import lombok.val;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GenerateData {

    public String getCurrentMonth() {
        val month = YearMonth.now().format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }

    public String getMonthPlus1() {
        val month = YearMonth.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }

    public String getMonthPlus5() {
        val month = YearMonth.now().plusMonths(5).format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }

    public String getZeroMonth() {
        return "00";
    }

    public String getThirteenthMonth() {
        return "13";
    }

    public String getMonthIsEmpty() {
        return "";
    }

    public String getCurrentYear() {
        val year = YearMonth.now().format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getYearWithMonthPlus1() {
        val year = YearMonth.now().plusMonths(1).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getYearWithMonthPlus5() {
        val year = YearMonth.now().plusMonths(5).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getLastYear() {
        val year = YearMonth.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getNextYear() {
        val year = YearMonth.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getYearPlus3() {
        val year = YearMonth.now().plusYears(3).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getYearPlus5() {
        val year = YearMonth.now().plusYears(5).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getYearIsEmpty() {
        return "";
    }

    public String getOwner() {
        Faker faker = new Faker(new Locale("en"));
        val owner = faker.name().firstName() + " " + faker.name().lastName();
        return owner;
    }

    public String getWrongOwnerWithRusKey() {
        return "Иван Русский";
    }

    public String getWrongOwnerWithSpecialChar() {
        return "Ivan %^&*(";
    }

    public String getOwnerWithDubleName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.instance().name().firstName() + " " + faker.name().lastName();
    }

    public String getOwnerWithDash() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + "-" + faker.name().firstName() + " " + faker.name().lastName();
    }

    public String getOwnerWithNumeral() {
        return "546 235645787";
    }

    public String getOwnerWithTwoChar() {
        return "A G";
    }

    public String getOwnerWithPoint() {
        return "Ms.Anna Ivanova";
    }

    public String getOwnerIsEmpty() {
        return "";
    }

    public String getCvcCode() {
        return "159";
    }

    public String getCvcCodeWhenTwoChar() {
        return "17";
    }

    public String getCvcCodeWhenMoreTreeChar() {
        return "1486";
    }

    public String getCvcIsEmpty() {
        return "";
    }

}
