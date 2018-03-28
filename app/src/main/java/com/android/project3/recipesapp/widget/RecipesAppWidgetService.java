package com.android.project3.recipesapp.widget;

import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by katanbern on 28/03/2018.
 */

public class RecipesAppWidgetService extends RemoteViewsService {
    public RecipesAppWidgetService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public RecipeAppWidgetFactory onGetViewFactory(Intent intent) {
        return null;
    }

    class RecipeAppWidgetFactory implements RemoteViewsFactory{

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
