package com.android.project3.recipesapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.ui.MainActivity;
import com.android.project3.recipesapp.utils.RecipesAppUtils;

/**
 * Implementation of App Widget functionality.
 */
public class RecipesAppWidget extends AppWidgetProvider {
    private static final String TAG = RecipesAppWidget.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_app_widget);
        Log.i(TAG, "updateAppWidget: widget ID #"+appWidgetId);

        //load recipe data
        RecipesAppUtils utils = new RecipesAppUtils(context);
        Recipe recipe = utils.loadRecipeByID();

        //set up intent for ingredients list
        Intent intent = new Intent(context, RecipesAppWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        if (recipe != null){
            Log.i(TAG, "updateAppWidget: recipe name is "+recipe.getName()+" and ID is "+recipe.getId());
            views.setTextViewText(R.id.tv_appwidget_title, recipe.getName());
            views.setRemoteAdapter(R.id.lv_ingredients_list_widget, intent);
            views.setEmptyView(R.id.lv_ingredients_list_widget, R.id.tv_widget_empty_list);
        } else {
            views.setTextViewText(R.id.tv_appwidget_title, context.getString(R.string.start_app_to_explore));

            Intent startMainIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, startMainIntent, 0);

            views.setOnClickPendingIntent(R.id.tv_appwidget_title, pendingIntent);
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.tv_appwidget_title);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(TAG, "onUpdate: size of IDs vectors: "+appWidgetIds.length);
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

