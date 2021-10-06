package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //if a user is logged in
        Boolean enterToSystem = true;

        //if user chose to create a new card
        Boolean createNewCard = false;

        //if a user account was created
        Boolean accountWasCreated = false;

        //if user chose to insert existing card
        Boolean insertCard = false;

        //if inserting an existing card was OK
        Boolean insertCardOk = false;

        //if user chose to display the main menu
        Boolean displayMainMenu = true;

        //if user chose to display the balance
        Boolean checkBalance = false;

        //if user chose to put money into a bank account
        Boolean putMoneyInMenu = false;

        //if user chose to withdraw money from his bank account
        Boolean withdrawMenu = false;

        //if user chose to transfer money to another bank account
        Boolean transferToOther = false;

        String userFirstName = "";
        String userLastName = "";

        String cardNumber = "";
        String cardCode = "";

        //CHECK BALANCE
        //the current balance of the user who is logged in
        Double cardBalance = 0.0;


        //PUT MONEY IN
        //the amount of money that the user wants to deposit into his bank account
        Double amountPutMoneyInCard = 0.0;

        //success or fail of put money in
        Integer putMoneyInSuccess = -1;

        //WITHDRAW MONEY
        //the amount of money that the user wants to withdraw from his bank account
        Double amountWithdrawCard = 0.0;

        //the current amount of money in the bank account
        Double currentMoney = 0.0;

        //if there is not enough money to withdraw
        Boolean notEnoughMoney = false;

        //success or fail of withdraw
        Integer withdrawSuccess = 0;


        //TRANSFERRING MONEY TO ANOTHER BANK ACCOUNT
        //card number to which the user wants to transfer money
        String otherCardNumber = "";

        //id of that card
        Integer otherCardId = -1;

        //if a transferring money was success or fail
        Integer transferSuccess = -1;

        //amount of money that the user wants to transfer to another user
        Double amountToOtherCard = 0.0;

        //if there is not enough money to transfer to another user
        Boolean notEnoughMoneyToTransfer = false;

        //card id of the user who is currently logged in
        Integer cardId = -1;


        //SYSTEM OPTIONS
        //the option that the user has selected in the main menu (creating a new card / inserting an existing card)
        Integer userOption = -1;

        //option to perform operations with an existing bank card (check the balance / put money in / withdraw money / transfer money to another user)
        Integer cardInsertOption = -1;

        //type zero to return one level up
        Boolean userEnterZero = false;


        Scanner scanner = new Scanner(System.in);

        while (enterToSystem) {
            if (displayMainMenu) {
                System.out.println("\n=== MAIN MENU ===");

                System.out.println("--- Choose an option ---");

                System.out.println("1. Create a new card");
                System.out.println("2. Insert an existing card");

                System.out.println("0. Exit");
            }

            if (userOption < 0) {
                System.out.print("\nEnter the main menu option -> ");

                userOption = UserInput.enterNumberToSelectOption();
            }

            switch (userOption) {
                case 1: //if option "Create a new card" was selected
                    displayMainMenu = false;
                    createNewCard = true;
                    break;
                case 2: //if option "Insert an existing card" was selected
                    displayMainMenu = false;
                    insertCard = true;
                    break;
                case 0: //if exit was selected
                    displayMainMenu = false;
                    System.out.println("exit");
                    enterToSystem = false;
                    break;
                default: //if other value was selected
                    displayMainMenu = false;
                    System.out.print("Enter correct option");
                    userOption = -1;
                    break;
            }

            //if option "Create a new card" was selected
            if (createNewCard) {
                System.out.println("\n=== CREATING A NEW CARD ===");
                System.out.println("--- Enter your name ---");

                userFirstName = UserInput.enterFirstName();

                System.out.println("--- Enter your surname ---");

                userLastName = UserInput.enterLastName();

                User user = new User(userFirstName, userLastName);

                accountWasCreated = user.createNewAccount();

                if (!accountWasCreated) {
                    System.out.println("Problem with creating an account");
                }

                System.out.println("\nType zero to return one level up");

                //ask the user to enter zero to return one level up
                userEnterZero = UserInput.zeroToReturnOneLevelUp();

                if (userEnterZero) {
                    displayMainMenu = true;
                    createNewCard = false;

                    userOption = -1;
                }
            }

            //if option "Insert an existing card" was selected
            if (insertCard) {
                if (cardNumber == "") {
                    System.out.println("\n=== INSERT EXISTING CARD ===");

                    System.out.println("--- Enter card number ---");

                    cardNumber = UserInput.enterCardNumber();;
                }

                if (cardCode == "") {
                    System.out.println("Enter card code");

                    cardCode = UserInput.enterCode();
                }

                User user = new User(userFirstName, userLastName);

                insertCardOk = user.insertCard(cardNumber, cardCode);

                if (insertCardOk == false) {
                    System.out.println("Card number or card code was wrong. Please type again");

                    cardNumber = "";

                    cardCode = "";
                }

                //if card number and card code were right
                if (insertCardOk) {
                    System.out.println("\n=== MENU OF THE CURRENT CARD ===");

                    System.out.println("1. Checking the balance of a card");
                    System.out.println("2. Put money in");
                    System.out.println("3. Withdraw money");
                    System.out.println("4. Transfer money to another client");
                    System.out.println("0. Return one level up");

                    //user input
                    System.out.print("\nEnter the card option -> ");

                    cardInsertOption = UserInput.enterNumberToSelectOption();

                    switch (cardInsertOption) {
                        case 1: //if "Checking the balance" was selected
                            checkBalance = true;
                            break;
                        case 2: //if "Put money in" was selected
                            putMoneyInMenu = true;
                            break;
                        case 3: //if "Withdraw money" was selected
                            withdrawMenu = true;
                            break;
                        case 4: //if "Transfer money to another client" was selected
                            transferToOther = true;
                            break;
                        case 0: //if "Return one level up" was selected
                            insertCardOk = false;
                            insertCard = false;
                            userOption = -1;
                            displayMainMenu = true;
                            System.out.println("Exit");
                            break;

                        default:
                            System.out.println("\nPlease enter the right option\n");
                            break;
                    }

                    //if "Checking the balance" was selected
                    if (checkBalance) {
                        System.out.println("\n=== CHECKING THE BALANCE OF A CARD ===");

                        cardBalance = user.checkBalance();

                        System.out.println("Card balance " + cardBalance);

                        System.out.println("\nPress 0 to return one level up");

                        //ask user to type zero to return one level up
                        userEnterZero = UserInput.zeroToReturnOneLevelUp();

                        if (userEnterZero) {
                            checkBalance = false;
                        }
                    }

                    //if "Put money in" was selected
                    if (putMoneyInMenu) {
                        System.out.println("\n=== PUT MONEY IN ===");

                        System.out.println("--- Enter the amount to put money in ---");

                        amountPutMoneyInCard = UserInput.enterAmountPutMoneyIn();

                        putMoneyInSuccess = user.putMoneyIn(amountPutMoneyInCard);

                        if (putMoneyInSuccess > 0) {
                            System.out.println("Putting money in was success");
                        } else {
                            System.out.println("Something was wrong");
                        }

                        System.out.println("\nPress 0 to return one level up");

                        //ask user to type zero to return one level up
                        userEnterZero = UserInput.zeroToReturnOneLevelUp();

                        if (userEnterZero) {
                            putMoneyInMenu = false;
                        }
                    }

                    //if "Withdraw money" was selected
                    if (withdrawMenu) {
                        System.out.println("\n=== WITHDRAW MONEY ===");

                        System.out.println("--- Enter the amount to withdraw money ---");

                        amountWithdrawCard = UserInput.enterAmountWithdraw();

                        try {
                            Connection con = Database.getConnection();
                            Statement stmt = con.createStatement();

                            //check the current amount of money
                            currentMoney = user.checkBalance();

                            if (currentMoney < amountWithdrawCard) {
                                do {
                                    System.out.println("Not enough money. Please type a different amount or 0 to return one level up");

                                    amountWithdrawCard = scanner.nextDouble();

                                    if (amountWithdrawCard == 0) {
                                        withdrawMenu = false;
                                        notEnoughMoney = true;

                                        continue;
                                    }
                                } while (currentMoney < amountWithdrawCard);
                            }

                            if (notEnoughMoney) {
                                continue;
                            }

                            //check the success of the operation
                            withdrawSuccess = user.withdrawFromTheCard(amountWithdrawCard);
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        if (withdrawSuccess > 0) {
                            System.out.println("Withdrawal completed successfully");
                        } else {
                            System.out.println("Something was wrong");
                        }

                        System.out.println("\nPress 0 to return one level up");

                        //ask user to type zero to return one level up
                        userEnterZero = UserInput.zeroToReturnOneLevelUp();

                        if (userEnterZero) {
                            withdrawMenu = false;
                        }
                    }

                    //if "Transfer money to another client" was selected
                    if (transferToOther) {
                        System.out.println("\n=== TRANSFER MONEY TO ANOTHER USER ===");
                        System.out.println("--- Enter the number of another card ---");

                        otherCardNumber = UserInput.enterCardNumber();

                        System.out.println("--- Enter the amount to transfer ---");

                        amountToOtherCard = UserInput.enterAmount();

                        otherCardId = user.getCartIdByNumber(otherCardNumber);

                        //get the card id of the user who is logged in to the system
                        cardId = user.getCardId();

                        //get the balance of this user
                        currentMoney = user.getBalanceById(cardId);

                        if (currentMoney < amountToOtherCard) {
                            do {
                                System.out.println("Not enough money. Please type a different amount or 0 to return one level up");

                                //get the amount that we want to send to another card
                                amountToOtherCard = scanner.nextDouble();

                                if (amountToOtherCard == 0) {
                                    transferToOther = false;

                                    notEnoughMoneyToTransfer = true;

                                    continue;
                                }
                            } while (currentMoney < amountToOtherCard);
                        }

                        if (notEnoughMoneyToTransfer) {
                            continue;
                        }

                        //check the success of the money transfer to another card
                        transferSuccess = user.transferToOther(otherCardId, amountToOtherCard);

                        if (transferSuccess == 1) {
                            System.out.println("Money transfer completed successfully");
                        } else {
                            System.out.println("Something was wrong");
                        }

                        System.out.println("\nPress 0 to return one level up");

                        //ask user to type zero to return one level up
                        userEnterZero = UserInput.zeroToReturnOneLevelUp();

                        if (userEnterZero) {
                            transferToOther = false;
                        }
                    }
                }
            }
        }
    }
}