package com.company;

import java.util.Scanner;

public class UserInput {

    /*
    If the user needs to enter zero to complete the current operation
     */
    public static boolean zeroToReturnOneLevelUp() {
        String optionAfterCreateNewCardString = "";

        Boolean result = false;

        Scanner scanner = new Scanner(System.in);

        do {
            optionAfterCreateNewCardString = scanner.next();

            if (optionAfterCreateNewCardString.equals("0")) {
                result = true;
            } else {
                System.out.println("Enter only 0");
            }
        } while (!optionAfterCreateNewCardString.equals("0"));

        return result;
    }

    /*
    Entering an option number in some menu
     */
    public static Integer enterNumberToSelectOption() {
        Scanner scanner = new Scanner(System.in);

        //opton that user entered
        String cardInsertOptionString = "";

        //option that will convert for the system
        Integer cardInsertOption = -1;

        do {
            cardInsertOptionString = scanner.next();

            //if user entered option from 1 to 10
            if (cardInsertOptionString.matches("[0-9]+") && cardInsertOptionString.length() <= 2) {
                cardInsertOption = Integer.parseInt(cardInsertOptionString);
            } else {
                System.out.println("Enter only digits < 10");
            }
        } while (!cardInsertOptionString.matches("[0-9]+") || cardInsertOptionString.length() > 2);

        return cardInsertOption;
    }

    /*
    Entering the amount of money to put in on a bank account
     */
    public static Double enterAmountPutMoneyIn() {
        Scanner scanner = new Scanner(System.in);

        String amountPutMoneyInCardString = "";

        Double amountPutMoneyInCard = 0.0;

        do {
            amountPutMoneyInCardString = scanner.next();

            if (amountPutMoneyInCardString.matches("[0-9]+")) {

                amountPutMoneyInCard = Double.parseDouble(amountPutMoneyInCardString);
            } else {
                System.out.println("Enter only digits");
            }
        } while (!amountPutMoneyInCardString.matches("[0-9]+"));

        return amountPutMoneyInCard;
    }

    /*
    Entering the amount of money to withdraw from the bank account
     */
    public static Double enterAmountWithdraw() {
        Scanner scanner = new Scanner(System.in);

        //amount that user entered
        String amountWithdrawCardString = "";

        //amount that will convert for the system
        Double amountWithdrawCard = 0.0;

        do {
            amountWithdrawCardString = scanner.next();

            if (amountWithdrawCardString.matches("[0-9]+")) {

                amountWithdrawCard = Double.parseDouble(amountWithdrawCardString);
            } else {
                System.out.println("Enter only digits");
            }
        } while (!amountWithdrawCardString.matches("[0-9]+"));

        return amountWithdrawCard;
    }

    /*
    Entering card number
     */
    public static String enterCardNumber() {
        Scanner scanner = new Scanner(System.in);

        String otherCardNumber = "";

        do {
            otherCardNumber = scanner.next();

            if (!otherCardNumber.matches("[0-9]+")) {
                System.out.println("Enter only digits");
            }
        } while (!otherCardNumber.matches("[0-9]+"));

        return otherCardNumber;
    }

    /*
    Entering card code
     */
    public static String enterCode() {
        Scanner scanner = new Scanner(System.in);

        String otherCardNumber = "";

        do {
            otherCardNumber = scanner.next();

            if (!otherCardNumber.matches("[0-9]+")) {
                System.out.println("Enter only digits");
            }
        } while (!otherCardNumber.matches("[0-9]+"));

        return otherCardNumber;
    }

    /*
    Entering the amount of money
     */
    public static Double enterAmount() {
        Scanner scanner = new Scanner(System.in);

        //amount that user entered
        String amountToOtherCardString = "";

        //amount that will convert for the system
        Double amountToOtherCard = 0.0;

        do {
            amountToOtherCardString = scanner.next();

            if (amountToOtherCardString.matches("[0-9]+")) {

                amountToOtherCard = Double.parseDouble(amountToOtherCardString);
            } else {
                System.out.println("Enter only digits");
            }
        } while (!amountToOtherCardString.matches("[0-9]+"));

        return amountToOtherCard;
    }

    /*
    Entering first name
     */
    public static String enterFirstName() {
        Scanner scanner = new Scanner(System.in);

        String userFirstName = scanner.next();

        //check only alphabet
        if (!userFirstName.matches("[a-zA-z]+")) {
            do {
                System.out.println("Enter only letters");

                userFirstName = scanner.next();
            } while (!userFirstName.matches("[a-zA-z]+"));
        }

        return userFirstName;
    }

    /*
    Entering last name
     */
    public static String enterLastName() {
        Scanner scanner = new Scanner(System.in);

        String userLastName = scanner.next();

        //check only alphabet
        if (!userLastName.matches("[a-zA-Z]+")) {
            do {
                System.out.println("Enter only letters");

                userLastName = scanner.next();
            } while (!userLastName.matches("[a-zA-Z]+"));
        }

        return userLastName;
    }
}