package com.example.coffeeorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if(quantity < 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    public void decrement(View view) {
        if(quantity > 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    public void submitOrder(View view) {
        EditText name = findViewById(R.id.customer_name_edit_text);
        String customerName = name.getText().toString();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(quantity, hasWhippedCream, hasChocolate);
        String message = createOrderSummary(customerName, price, hasWhippedCream, hasChocolate);
        displayMessage(message);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"techdevg@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order from Coffee Ordering App!");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(intent, "Select an Email Client"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no Email Client", Toast.LENGTH_SHORT).show();
        }
    }

    public String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity + "\nTotal: ₹" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    private int calculatePrice(int numberOfCoffees, boolean hasWhippedCream, boolean hasChocolate) {
        if (hasChocolate && hasWhippedCream) {
            return (numberOfCoffees * 10) + numberOfCoffees * 4;
        } else if (hasChocolate || hasWhippedCream) {
            return (numberOfCoffees * 10) + numberOfCoffees * 2;
        }
        return (numberOfCoffees * 10);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}
