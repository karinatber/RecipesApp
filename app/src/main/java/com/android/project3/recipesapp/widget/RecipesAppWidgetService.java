package com.android.project3.recipesapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Ingredient;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.utils.RecipesAppUtils;

import java.util.List;

/**
 * Created by katanbern on 28/03/2018.
 */

public class RecipesAppWidgetService extends RemoteViewsService {
    private static final String TAG = RecipesAppWidgetService.class.getSimpleName();

    public RecipesAppWidgetService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public RecipeAppWidgetFactory onGetViewFactory(Intent intent) {
        Log.i(TAG, "onGetViewFactory was called");
        return new RecipeAppWidgetFactory(this.getApplicationContext(), intent);
    }
}

class RecipeAppWidgetFactory  implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = RecipeAppWidgetFactory.class.getSimpleName();
    private Context mContext;
    private List<Recipe> mRecipeList;
    private Recipe mRecipe;
    private RecipesAppUtils mUtils;
    private int mAppWidgetId;

    public RecipeAppWidgetFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        mUtils = new RecipesAppUtils(context);
        mRecipeList = mUtils.loadAllRecipes();
        mRecipe = mUtils.loadRecipeByID(mAppWidgetId);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate was called");
        mRecipeList = mUtils.loadAllRecipes();
        mRecipe = mUtils.loadRecipeByID(mAppWidgetId);
    }

    @Override
    public void onDataSetChanged() {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipes_app_widget);

        AppWidgetManager.getInstance(mContext).updateAppWidget(mAppWidgetId, rv);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mRecipe == null) return 0;
        return mRecipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.i(TAG,"getViewAt was called at "+i);
        if(mRecipe == null) {
            Log.i(TAG, "getViewAt: mRecipe is null");
            return null;
        }
        Ingredient ingredient = mRecipe.getIngredients().get(i);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_widget_list_item);

        String name = ingredient.getIngredient();
        String quantity = String.valueOf(ingredient.getQuantity());
        String unit = ingredient.getMeasure();

        remoteViews.setTextViewText(R.id.tv_name_ingr_widget, name);
        remoteViews.setTextViewText(R.id.tv_quantity_ingr_widget, quantity);
        remoteViews.setTextViewText(R.id.tv_unit_ingr_widget, unit);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}