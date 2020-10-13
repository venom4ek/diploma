package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DataHelper {

    private String urlMsSql = "jdbc:mysql://192.168.99.100:3306/app";
    private String urlPostgre = "jdbc:postgresql://192.168.99.100:5432/app";
    private String user = "app";
    private String pass = "pass";

    public void cleanAllTableMySql() throws SQLException {
        val foreignCheckOff = "SET FOREIGN_KEY_CHECKS=0;";
        val foreignCheckOn = "SET FOREIGN_KEY_CHECKS=1;";
        val run = new QueryRunner();
        val credit_request_entity = "truncate table credit_request_entity;";
        val order_entity = "truncate table order_entity;";
        val payment_entity = "truncate table payment_entity;";

        try (
                val connect = DriverManager.getConnection(urlMsSql, user, pass);
        ) {
            run.update(connect, foreignCheckOff);
            run.update(connect, credit_request_entity);
            run.update(connect, order_entity);
            run.update(connect, payment_entity);
            run.update(connect, foreignCheckOn);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void cleanAllTablePostgre() throws SQLException {
        val foreignCheckOff = "alter table credit_request_entity disable trigger all; " +
                "alter table order_entity disable trigger all; " +
                "alter table payment_entity disable trigger all;";
        val foreignCheckOn = "alter table credit_request_entity enable trigger all; " +
                "alter table order_entity enable trigger all; " +
                "alter table payment_entity enable trigger all;";
        val run = new QueryRunner();
        val credit_request_entity = "truncate table credit_request_entity;";
        val order_entity = "truncate table order_entity;";
        val payment_entity = "truncate table payment_entity;";

        try (
                val connect = DriverManager.getConnection(urlPostgre, user, pass);
        ) {
            run.update(connect, foreignCheckOff);
            run.update(connect, credit_request_entity);
            run.update(connect, order_entity);
            run.update(connect, payment_entity);
            run.update(connect, foreignCheckOn);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getStatusPayment() {
        val request = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        try (
                val connect = DriverManager.getConnection(urlMsSql, user, pass);
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(request)) {
                if (resultSet.next()) {
                    val status = resultSet.getString(1);
                    return status;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public String getCreditId() {
        val request = "SELECT credit_id FROM order_entity ORDER BY created DESC LIMIT 1;";
        try (
                val connect = DriverManager.getConnection(urlMsSql, user, pass);
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(request)) {
                if (resultSet.next()) {
                    val creditId = resultSet.getString(1);
                    return creditId;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public String getStatusPaymentPostgre() {
        val request = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        try (
                val connect = DriverManager.getConnection(urlPostgre, user, pass);
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(request)) {
                if (resultSet.next()) {
                    val status = resultSet.getString(1);
                    return status;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public String getStatusCredit() {
        val request = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        try (
                val connect = DriverManager.getConnection(urlMsSql, user, pass);
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(request)) {
                if (resultSet.next()) {
                    val status = resultSet.getString(1);
                    return status;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public String getCreditIdPostgre() {
        val request = "SELECT credit_id FROM order_entity ORDER BY created DESC LIMIT 1;";
        try (
                val connect = DriverManager.getConnection(urlPostgre, user, pass);
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(request)) {
                if (resultSet.next()) {
                    val creditId = resultSet.getString(1);
                    return creditId;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public String getStatusCreditPostgre() {
        val request = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        try (
                val connect = DriverManager.getConnection(urlPostgre, user, pass);
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(request)) {
                if (resultSet.next()) {
                    val status = resultSet.getString(1);
                    return status;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Value
    public static class Card {
        private String card;

    }

    public static Card getApprovedCard() {
        return new Card("4444 4444 4444 4441");
    }

    public static Card getDeclinedCard() {
        return new Card("4444 4444 4444 4442");
    }


    //данная тестовая карта необходима для проверки отказа банка при неизвестной карте
    public static Card getTestCard() {
        Faker faker = new Faker(new Locale("ru"));
        return new Card(faker.business().creditCardNumber());
    }

    public static Card getEmptyCard() {
        return new Card("");
    }

    public static Card getLessSixteenCharCard() {
        return new Card("15861497126732");
    }

    public static Card getMoreSixteenCharCard() {
        return new Card("1567456275183401157462");
    }

    public static Card getOneSpaceCard() {
        return new Card(" ");
    }

    public static Card getSpecialCharCard() {
        return new Card("!@#$%^&*()-=_+:\"\'\\|/.,;`~><№%?");
    }

    public static Card getCardFromLetter() {
        return new Card("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

}
