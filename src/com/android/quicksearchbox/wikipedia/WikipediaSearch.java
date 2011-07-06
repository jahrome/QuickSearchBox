/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.quicksearchbox.wikipedia;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import android.provider.Browser;

/**
 * The main activity for wikipedia search.
 * Displays search results triggered by the search dialog and handles
 * actions from search suggestions.
 */
public class WikipediaSearch extends Activity {

    private static final String TAG = "WikipediaSearch";
    private String wikipediaSearchUrlBase = "http://en.wikipedia.org/wiki/Special:Search?search=";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.v(TAG, "Got query : " + query);
            String applicationId = intent.getStringExtra(Browser.EXTRA_APPLICATION_ID);
            if (applicationId == null) {
                applicationId = getPackageName();
            }
            try {
                String searchUri = wikipediaSearchUrlBase + URLEncoder.encode(query, "UTF-8");
                Intent launchUriIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchUri));
                launchUriIntent.putExtra(Browser.EXTRA_APPLICATION_ID, applicationId);
                launchUriIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchUriIntent);
            } catch (UnsupportedEncodingException e) {
                Log.w(TAG, "Error", e);
            }
        }
    }
}
