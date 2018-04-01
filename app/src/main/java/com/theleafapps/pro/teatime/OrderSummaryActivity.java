/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.theleafapps.pro.teatime;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class OrderSummaryActivity extends AppCompatActivity {

    int price, quantity;
    String teaName, size, milkType, sugarType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.order_summary_toolbar);
        setSupportActionBar(menuToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.order_summary_title));
        actionBar.setIcon(R.mipmap.ic_launcher);

        Intent intent = getIntent();
        teaName = intent.getStringExtra(OrderActivity.EXTRA_TEA_NAME);
        price = intent.getIntExtra(OrderActivity.EXTRA_TOTAL_PRICE, 0);
        size = intent.getStringExtra(OrderActivity.EXTRA_SIZE);
        milkType = intent.getStringExtra(OrderActivity.EXTRA_MILK_TYPE);
        sugarType = intent.getStringExtra(OrderActivity.EXTRA_SUGAR_TYPE);
        quantity = intent.getIntExtra(OrderActivity.EXTRA_QUANTITY, 0);

        displayOrderSummary(teaName, price, size, milkType, sugarType, quantity);
    }

    /**
     * Create summary of the order.
     *
     * @param teaName   type of tea
     * @param quantity  quantity ordered
     * @param price     price of the order
     * @param milkType  type of milk to add
     * @param sugarType amount of sugar to add
     */
    private void displayOrderSummary(String teaName, int price, String size, String milkType,
                                     String sugarType, int quantity) {

        // Set tea name in order summary
        TextView teaNameTextView = (TextView) findViewById(
                R.id.summary_tea_name);
        teaNameTextView.setText(teaName);

        // Set quantity in order summary
        TextView quantityTextView = (TextView) findViewById(
                R.id.summary_quantity);
        quantityTextView.setText(String.valueOf(quantity));

        // Set tea size in order summary
        TextView sizeTextView = (TextView) findViewById(
                R.id.summary_tea_size);
        sizeTextView.setText(size);

        // Set milk type in order summary
        TextView milkTextView = (TextView) findViewById(
                R.id.summary_milk_type);
        milkTextView.setText(milkType);

        // Set sugar amount in order summary
        TextView sugarTextView = (TextView) findViewById(
                R.id.summary_sugar_amount);
        sugarTextView.setText(sugarType);

        // Set total price in order summary
        TextView priceTextView = (TextView) findViewById(
                R.id.summary_total_price);

        String convertPrice = NumberFormat.getCurrencyInstance().format(price);
        priceTextView.setText(convertPrice);

    }

    /**
     * This method is called when the Send Email button is clicked and sends a copy of the order
     * summary to the inputted email address.
     */

    public void sendEmail(View view) {

        StringBuilder emailMessage = new StringBuilder();
        emailMessage.append(getString(R.string.email_message));
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.order_summary));
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.summary_tea_label_with_colon));
        emailMessage.append(teaName);
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.summary_size_label_with_colon));
        emailMessage.append(size);
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.summary_milk_label_with_colon));
        emailMessage.append(milkType);
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.summary_sugar_label_with_colon));
        emailMessage.append(sugarType);
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.summary_quantity_label_with_colon));
        emailMessage.append(quantity);
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.summary_price_label_with_colon));
        emailMessage.append(price);
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.newline));
        emailMessage.append(getString(R.string.thank_you));

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, emailMessage.toString());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            finish();
        }
    }
}