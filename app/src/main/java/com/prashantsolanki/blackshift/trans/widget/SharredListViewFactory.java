/***
 * Copyright (c) 2008-2012 CommonsWare, LLC
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 * by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * <p>
 * From _The Busy Coder's Guide to Advanced Android Development_
 * http://commonsware.com/AdvAndroid
 */


package com.prashantsolanki.blackshift.trans.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.prashantsolanki.blackshift.trans.App;
import com.prashantsolanki.blackshift.trans.R;

public class SharredListViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context ctxt = null;

    public SharredListViewFactory(Context ctxt) {
        this.ctxt = ctxt;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return (App.starred.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(ctxt.getPackageName(),
                R.layout.row);

        row.setTextViewText(android.R.id.text1, App.starred.get(position).getOutput());

        Intent i = new Intent();
        Bundle extras = new Bundle();

        extras.putString(WidgetProvider.EXTRA_QUOTE, App.starred.get(position).getOutput());
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);

        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return (null);
    }

    @Override
    public int getViewTypeCount() {
        return (1);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public boolean hasStableIds() {
        return (true);
    }

    @Override
    public void onDataSetChanged() {
    }
}