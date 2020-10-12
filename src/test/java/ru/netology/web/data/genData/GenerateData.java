package ru.netology.web.data.genData;

import com.github.javafaker.Faker;
import lombok.val;
import ru.netology.web.data.DataHelper;

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

    public String getPreviousMonth() {
        val month = YearMonth.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }

    public String getFirstMonth() {
        return "01";
    }

    public String getLastMonth() {
        return "12";
    }

    public String getZeroMonth() {
        return "00";
    }

    public String getThirteenthMonth() {
        return "13";
    }

    public String getMonthIsOneNumeral() {
        return "2";
    }

    public String getCurrentYear() {
        val year = YearMonth.now().format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getPreviousYear() {
        val year = YearMonth.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getNextYear() {
        val year = YearMonth.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getYearPlus5() {
        val year = YearMonth.now().plusYears(5).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getYearPlus6() {
        val year = YearMonth.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String getOwner() {
        Faker faker = new Faker(new Locale("en"));
        val owner = faker.name().firstName() + " " + faker.name().lastName();
        return owner;
    }

    public String getOwnerWithRusKey() {
        return "Иван Русский";
    }

    public String getOwnerWithSpecialChar() {
        return "Ivan !@#$%^&*()-=_+:\"\'\\|/.,;`~><№%?";
    }

    public String getOwnerWithDoubleName() {
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

    public String getOwnerWithTwoLetter() {
        return "A G";
    }

    public String getOwnerWithPoint() {
        return "Ms.Anna Ivanova";
    }

    public String getCvcCode() {
        return "159";
    }

    public String getCvcCode1() {
        return "946";
    }

    public String getCvcCode2() {
        return "007";
    }

    public String getCvcCodeWhenTwoChar() {
        return "17";
    }

    public String getCvcCodeWhenMoreTreeChar() {
        return "14865";
    }

    public String getEmpty() {
        return "";
    }

    public String getSpace() {
        return " ";
    }

    public String getSpecialChar() {
        return "!@#$%^&*()=_+:\"\'\\|/.,;`~><№%? ";
    }

    public String getLetter() {
        return "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }

}
