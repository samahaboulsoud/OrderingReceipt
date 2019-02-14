/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    // integer variable to save the quantity displayed on the screen
    int quantity = 1;
    // boolean variable to save the current state of the checkbox whipped_cream_checkbox
    Boolean hasWhippedCream = false;
    // boolean variable to save the current state of the checkbox chocolate_checkbox
    Boolean hasChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String priceMessage="";
        checkToppings();
        int price = calculatePrice();
        String userName ="";
        EditText nameView = (EditText) findViewById((R.id.name_view));
        userName = nameView.getText().toString();
        priceMessage = createOrderSummary(price,userName); //"Total: $" + price +  "\n" + "Thank you!";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject , userName) );
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     * @return total price
     */
    //private void calculatePrice(int quantity,int price) {
    private int calculatePrice() {
        int price = 5;
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate){
            price +=2;
        }
        price = quantity * price;
        //int TotalPrice= quantity * price ;
        //int price = quantity * 10 ;
        return price;
    }

    /** Creating Order summary to be displayed on the screen
     *@param orderPrice  the total price of the order
     *@param nameOfUser the name of the user entered in the input box name
     *@return the order summary
     */
    private String createOrderSummary(int orderPrice,String nameOfUser){
        String message = getString(R.string.user_name , nameOfUser);
        message += "\n" + getString(R.string.order_summary_whipped_cream , hasWhippedCream);
        message += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        message += "\n" + getString(R.string.order_summary_quantity, quantity) ;
        message += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(orderPrice)) ;
        message +=  "\n" + getString(R.string.thank_you) ;
        return message;
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this, "Sorry,You Can't Order more than 100  Cup of Coffee" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity  = quantity + 1;
        displayQuantity(quantity);

    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if (quantity==1){
            Toast.makeText(this, "Sorry,You Can't Order less than One Cup of Coffee" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity  = quantity - 1;
        displayQuantity(quantity);

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
//        TextView orderSummayTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummayTextView.setText(message);
    }
    /**
     * This method returns the value of the checkboxs for the Toppings.
     */
    private void checkToppings() {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheckbox.isChecked();
        //Log.v("MainActivity","Add Whipped Cream:" + hasWhippedCream);
    }
}
//Note when i turn the phone landscape it resets all the text boxs and the same when i turned it portrait