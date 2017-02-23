

 package com.example.adymihai.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;

import static android.R.attr.x;
import static android.R.id.message;
import static com.example.adymihai.justjava.R.string.coffee;
import static com.example.adymihai.justjava.R.string.hasChocolate;
import static com.example.adymihai.justjava.R.string.hasWhippedCream;
import static com.example.adymihai.justjava.R.string.thank_you;
import static com.example.adymihai.justjava.R.string.total;

 /**
  * This app displays an order form to order coffee.
  */
 public class MainActivity extends AppCompatActivity {
     int quantity = 2;
     int price;
     /**
      * ATTENTION: This was auto-generated to implement the App Indexing API.
      * See https://g.co/AppIndexing/AndroidStudio for more information.
      */
     private GoogleApiClient client;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         // ATTENTION: This was auto-generated to implement the App Indexing API.
         // See https://g.co/AppIndexing/AndroidStudio for more information.
         client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
     }

     /**
      * This method is called when the order button is clicked.
      */
     public void submitOrder(View view) {
         // Check if the user whants whippedcream topping
         CheckBox whippedCream = (CheckBox) findViewById(R.id.checkbox);
         boolean hasWhippedCream = whippedCream.isChecked();
         // Check if the user wants chocolate topping
         CheckBox chocolate = (CheckBox) findViewById(R.id.checkbox2);
         boolean hasChocolate = chocolate.isChecked();
         // User types name
         EditText enterName = (EditText) findViewById(R.id.enter_name);
         String name = enterName.getText().toString();
         //calculate price and display on the screen
         price = calculatePrice(hasWhippedCream, hasChocolate);
         displayMessage(createOrderSummary(price, hasWhippedCream, hasChocolate, name));


         Intent intent = new Intent(Intent.ACTION_SENDTO);
         intent.setData(Uri.parse("mailto: "));
         intent.putExtra(Intent.EXTRA_SUBJECT, "Order for: " + name);
         intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, hasWhippedCream, hasChocolate, name));
         if (intent.resolveActivity(getPackageManager()) != null) {
             startActivity(intent);
         }

     }

     /**
      * this method calculates the price of the coffees
      *
      * @return total price
      */
     private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
         price = quantity * 5;
         if (hasWhippedCream && !hasChocolate) {
             price = quantity * 6;
         } else if (hasChocolate && !hasWhippedCream) {
             price = quantity * 7;
         } else if (hasWhippedCream && hasChocolate) {
             price = quantity * 8;
         }
         return price;
     }

     /**
      * this method summarizes the order details
      *
      * @param price           of the order
      * @param addWhippedCream
      * @param addChocolate
      * @param name
      * @return order message
      */
     private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {


         String message = quantity + getString(R.string.coffee) + name
                 + " \n" + getString(R.string.hasWhippedCream) + addWhippedCream + "\n"
                 +    getString(R.string.hasChocolate) + addChocolate + "\n"
                 +  getString(R.string.total)+ + price  + getString(R.string.thank_you);
         return message;

     }

     /**
      * This method displays the given text on the screen.
      */
     private void displayMessage(String message) {
         TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
         orderSummaryTextView.setText(message);
     }

     /**
      * This method increments quantity
      */
     public void increment(View view) {
         if (quantity == 5) {
             Toast.makeText(getApplicationContext(), "Hey, ai grija, prea multa cafea!", Toast.LENGTH_SHORT).show();
             return;
         }
         quantity = quantity + 1;
         displayQuantity(quantity);

     }

     /**
      * This method decrements quantity
      */
     public void decrement(View view) {

         if (quantity == 1) {
             Toast.makeText(getApplicationContext(), "Hey, nu mai vrei cafea?", Toast.LENGTH_SHORT).show();
             return;
         }
         quantity = quantity - 1;
         displayQuantity(quantity);

     }

     /**
      * This method displays the given quantity value on the screen.
      */
     private void displayQuantity(int number) {
         TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
         quantityTextView.setText("" + number);
     }

     /**
      * This method displays the given price on the screen.
      */
     private void displayPrice(int number) {
         TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
         priceTextView.setText(NumberFormat.getCurrencyInstance().format(price));
     }

     /**
      * ATTENTION: This was auto-generated to implement the App Indexing API.
      * See https://g.co/AppIndexing/AndroidStudio for more information.
      */
     public Action getIndexApiAction() {
         Thing object = new Thing.Builder()
                 .setName("Main Page") // TODO: Define a title for the content shown.
                 // TODO: Make sure this auto-generated URL is correct.
                 .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                 .build();
         return new Action.Builder(Action.TYPE_VIEW)
                 .setObject(object)
                 .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                 .build();
     }

     @Override
     public void onStart() {
         super.onStart();

         // ATTENTION: This was auto-generated to implement the App Indexing API.
         // See https://g.co/AppIndexing/AndroidStudio for more information.
         client.connect();
         AppIndex.AppIndexApi.start(client, getIndexApiAction());
     }

     @Override
     public void onStop() {
         super.onStop();

         // ATTENTION: This was auto-generated to implement the App Indexing API.
         // See https://g.co/AppIndexing/AndroidStudio for more information.
         AppIndex.AppIndexApi.end(client, getIndexApiAction());
         client.disconnect();
     }
 }

