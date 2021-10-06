package com.company;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
    static final String NUMBERS = "0123456789";
    static SecureRandom RAND_STRING = new SecureRandom();

    String userFirstName = "";
    String userLastName = "";

    String cardNumber = "";
    String cardCode = "";

    public User(String userFirstName, String userLastName) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    /*
    Creating a new card
     */
    public Boolean createNewAccount() {
        this.cardNumber = User.generateCardNumber();
        this.cardCode = User.generateCode();

        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();

            //insert the string about a new card with the card number and the card code
            int rs = stmt.executeUpdate("INSERT INTO cards (card_number, card_code) VALUES ('" + cardNumber + "', '" + cardCode  + "')", Statement.RETURN_GENERATED_KEYS);

            if (rs == 0) {
                return false;
            }

            int lastInsertId = -1;

            //get an id of the card that was created
            ResultSet rs_last_id = stmt.getGeneratedKeys();

            if (rs_last_id.next()) {
                lastInsertId = rs_last_id.getInt(1);
            }

            //insert information about the balance of the new card
            int rs2 = stmt.executeUpdate("INSERT INTO balance (card_id, balance) VALUES (" + lastInsertId + ", 0)");

            if (rs2 == 0) {
                return false;
            }

            //insert the first name and last name of the holder of the new card
            int rs3 = stmt.executeUpdate("INSERT INTO account_detail (card_id, first_name, last_name) VALUES (" + lastInsertId + ", '" + this.userFirstName + "', '" + this.userLastName + "')");

            if (rs3 == 0) {
                return false;
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("\n");
        System.out.println(this.userFirstName + " " + this.userLastName);

        System.out.println("Card number: " + cardNumber);
        System.out.println("Code: " + cardCode);

        return true;
    }

    /*
    Get the id of the card the owner of which is logged in
     */
    public Integer getCardId() {
        Integer cardId = -1;

        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM cards WHERE card_number = " + this.cardNumber + " AND card_code = " + this.cardCode);

            while (rs.next()) {
                cardId = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Something was wrong");
        }

        return cardId;
    }

    /*
    Get card id by card number
     */
    public Integer getCartIdByNumber(String otherCardNumber) {
        Integer otherCardId = -1;

        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();

            ResultSet rs8 = stmt.executeQuery("SELECT * FROM cards WHERE card_number = " + otherCardNumber);

            if (rs8.next()) {
                do {
                    otherCardId = rs8.getInt("id");

                } while (rs8.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return otherCardId;
    }

    /*
    Insert existing card
     */
    public Boolean insertCard(String cardNumber, String cardCode) {

        this.cardNumber = cardNumber;
        this.cardCode = cardCode;

        Boolean insertCardOk = false;

        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM cards WHERE card_number = '" + this.cardNumber + "' AND card_code = '" + this.cardCode + "'");

            while (rs.next()) {
                insertCardOk = true;
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        };

        return insertCardOk;
    }

    /*
    Check the balance of the card that was inserted
     */
    public Double checkBalance() {
        Double cardBalance = 0.0;

        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();

            //get card id that was inserted
            ResultSet rs = stmt.executeQuery("SELECT * FROM cards WHERE card_number = " + this.cardNumber + " AND card_code = " + this.cardCode);

            Integer cardId = -1;

            while (rs.next()) {
                cardId = rs.getInt("id");
            }

            //get the balance of the card that was inserted
            ResultSet rs3 = stmt.executeQuery("SELECT * from balance WHERE card_id = " + cardId);

            while (rs3.next()) {
                cardBalance = rs3.getDouble("balance");
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        };

        return cardBalance;
    }

    /*
    Get the balance of the card by card id
     */
    public Double getBalanceById(Integer cardId) {
        Double currentMoney = 0.0;

        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();

            ResultSet rs12 = stmt.executeQuery("SELECT * FROM balance WHERE card_id = " + cardId);

            while (rs12.next()) {
                currentMoney = rs12.getDouble("balance");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return currentMoney;
    }

    /*
    Put in operation
     */
    public Integer putMoneyIn(Double amountToPutMoneyIn) {
        Integer success = 0;

        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();

            //get information about a card that was inserted
            ResultSet rs6 = stmt.executeQuery("SELECT * FROM cards WHERE card_number = " + this.cardNumber + " AND card_code = " + this.cardCode);

            Integer cardId = -1;

            while (rs6.next()) {
                cardId = rs6.getInt("id");
            }

            //update balance of the card
            PreparedStatement ps = con.prepareStatement("UPDATE balance SET balance = balance + ? WHERE card_id = ?");
            ps.setDouble(1, amountToPutMoneyIn);
            ps.setInt(2, cardId);

            success = ps.executeUpdate();

            ps.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return success;
    }

    /*
    Withdraw operation
     */
    public Integer withdrawFromTheCard(Double amountToWithdraw) {
        Integer success = 0;

        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();

            Integer cardId = this.getCardId();

            PreparedStatement ps2 = con.prepareStatement("UPDATE balance SET balance = balance - ? WHERE card_id = ?");

            ps2.setDouble(1, amountToWithdraw);
            ps2.setInt(2, cardId);

            success = ps2.executeUpdate();
        } catch (Exception e) {
            System.out.println("Something was wrong");
        }

        return success;
    }

    /*
    Transfer money from the current card to another card
     */
    public Integer transferToOther(Integer otherCardId, Double amount_to_transfer) {
        Integer withdrawFromCurrentCardOk = -1;
        Integer putMoneyInToOtherCardOk = -1;
        Integer cardId = -1;

        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();

            //pay out from current card
            PreparedStatement ps3 = con.prepareStatement("UPDATE balance SET balance = balance - ? WHERE card_id = ?");

            cardId = this.getCardId();

            ps3.setDouble(1, amount_to_transfer);
            ps3.setInt(2, cardId);

            withdrawFromCurrentCardOk = ps3.executeUpdate();

            ps3.close();

            //pay in to other card
            PreparedStatement ps4 = con.prepareStatement("UPDATE balance SET balance = balance + ? WHERE card_id = ?");

            ps4.setDouble(1, amount_to_transfer);
            ps4.setInt(2, otherCardId);

            putMoneyInToOtherCardOk = ps4.executeUpdate();

            ps4.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (withdrawFromCurrentCardOk > 0 && putMoneyInToOtherCardOk > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    /*
    Generate card number
     */
    public static String generateCardNumber() {
        Integer numberOfNewCard = 0;

        Integer len = 10;

        StringBuilder cardNumber = new StringBuilder(len);

        for(int i = 0; i < len; i++) {
            numberOfNewCard = RAND_STRING.nextInt(NUMBERS.length());

            //remove zero as a first char of string
            if (i == 0 && numberOfNewCard == 0) {
                do {
                    numberOfNewCard = RAND_STRING.nextInt(NUMBERS.length());
                } while (numberOfNewCard == 0);

                cardNumber.append(NUMBERS.charAt(numberOfNewCard));
            } else {
                cardNumber.append(NUMBERS.charAt(numberOfNewCard));
            }
        }

        return cardNumber.toString();
    }

    /*
    Generate card code
     */
    public static String generateCode() {
        Integer codeOfNewCard = 0;

        Integer len = 3;

        StringBuilder cardCode = new StringBuilder(len);

        for(int i = 0; i < len; i++) {
            codeOfNewCard = RAND_STRING.nextInt(NUMBERS.length());

            //remove zero as first character of string
            if (i == 0 && codeOfNewCard == 0) {
                do {
                    codeOfNewCard = RAND_STRING.nextInt(NUMBERS.length());
                } while (codeOfNewCard == 0);

                cardCode.append(NUMBERS.charAt(codeOfNewCard));
            } else {
                cardCode.append(NUMBERS.charAt(codeOfNewCard));
            }
        }

        return cardCode.toString();
    }
}