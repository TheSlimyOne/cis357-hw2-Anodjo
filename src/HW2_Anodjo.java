/*
 * Homework 2: Sales Register Program part 2
 * Course: CIS357
 * Due date: 7/12/2022
 * Name: Jordan Anodjo
 * GitHub: https://github.com/TheSlimyOne/cis357-hw2-Anodjo.git
 * Instructor: Il-Hyung Cho
 * ===============================================================================
 * Program description: 
 * 		In this program, a user will interact with an emulated cash register.
 * 		The user will be able to purchase items from a predetermined list.
 * 		The cash register will respond appropriately from the user. 
 * 		Always producing the correct results/response from the user.
 *      Then the user is allowed to add, delete, and modify items.
 * ===============================================================================
 * Program features:
 * Change the item code type to String: Y | The code of an Item is a class called Code that uses a string.
 * Provide the input in CSV format. Ask the user to enter the input file name: Y | input.txt
 * 
 * Implement exception handling for:
 *      File input: Y
 *      Checking wrong input data type: Y
 *      Checking invalid data value: Y
 *      Tendered amount less than the total amount: Y
 * Use ArrayList for the items data: Y
 * Adding item data:    Y | prompt users if they are sure if they want to add
 * Deleting item data:  Y | prompt users if they are sure if they want to delete
 * Modifying item data: Y | prompt users if they are sure if they want to modfiy
 */

/**
 * Driver Class.
 * 
 * @author Jordan Anodjo
 */
public class HW2_Anodjo {
    // Start the shopping session.
    public static void main(String[] args) throws java.io.FileNotFoundException {
        CashRegister.promptStartShopping();
    }
}
