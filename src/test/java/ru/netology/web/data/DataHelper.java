package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DataHelper {

    private String urlSql = "jdbc:mysql://192.168.99.100:3306/app";
    private String user = "app";
    private String pass = "pass";

    public void cleanAllTable() throws SQLException {
        val foreignCheckOff = "SET FOREIGN_KEY_CHECKS=0;";
        val foreignCheckOn = "SET FOREIGN_KEY_CHECKS=1;";
        val run = new QueryRunner();
        val credit_request_entity = "truncate table credit_request_entity;";
        val order_entity = "truncate table order_entity;";
        val payment_entity = "truncate table payment_entity;";

        try (
                val connect = DriverManager.getConnection(urlSql, user, pass);
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
                val connect = DriverManager.getConnection(urlSql, user, pass);
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


}
