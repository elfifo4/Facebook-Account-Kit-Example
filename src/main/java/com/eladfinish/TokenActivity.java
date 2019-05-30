/**
 * Copyright (c) 2014-present, Facebook, Inc. All rights reserved.
 * <p>
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 * <p>
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.eladfinish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eladfinish.R;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;

public class TokenActivity extends Activity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_token);

        final Button signOut = findViewById(R.id.log_out_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountKit.logOut();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                final TextView userId = findViewById(R.id.user_id);
                userId.setText(account.getId());

                final TextView phoneNumber = findViewById(R.id.user_phone);
                final PhoneNumber number = account.getPhoneNumber();
                phoneNumber.setText(number == null ? "not set" : number.toString());

                final TextView emailTextView = findViewById(R.id.user_email);
                String email = account.getEmail();
                emailTextView.setText(email == null || email.isEmpty() ? "not set" : email);
            }

            @Override
            public void onError(final AccountKitError error) {
            }
        });


        final TextView token = findViewById(R.id.user_token);

        Intent intent = getIntent();
        String accountId = intent.getStringExtra("AccountId");
        long tokenRefreshIntervalInSeconds = intent.getLongExtra("TokenRefreshIntervalInSeconds", 0);

        token.setText(accountId);
        token.append("\n---\n");
        token.append(String.valueOf(tokenRefreshIntervalInSeconds));


    }
}
