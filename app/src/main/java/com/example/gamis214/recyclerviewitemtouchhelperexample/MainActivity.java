package com.example.gamis214.recyclerviewitemtouchhelperexample;

import android.app.Service;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.gamis214.recyclerviewitemtouchhelperexample.Model.ServicesResponse;
import com.example.gamis214.recyclerviewitemtouchhelperexample.services.ApiClient;
import com.example.gamis214.recyclerviewitemtouchhelperexample.services.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<List<ServicesResponse>>,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinator;
    private List<ServicesResponse> list;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        coordinator = (CoordinatorLayout) findViewById(R.id.coordinator);
        getSupportActionBar().setTitle("Example");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getTheService();

    }

    public void getTheService(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ServicesResponse>> call = apiService.getListItems();
        call.enqueue(this);
    }

    private void setData(List<ServicesResponse> body) {

        list = body;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new CustomAdapter(this,body);
        recyclerView.swapAdapter(adapter,true);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onResponse(Call<List<ServicesResponse>> call, Response<List<ServicesResponse>> response) {
        Log.i(TAG,response.message());
        if(response.body() != null && response.isSuccessful()){
            setData(response.body());
        }else{
            Log.e(TAG,response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<List<ServicesResponse>> call, Throwable t) {
        Log.e(TAG, t.toString());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CustomAdapter.itemHolder){
            String name = list.get(viewHolder.getAdapterPosition()).getName();

            final ServicesResponse deletedItem = list.get(viewHolder.getAdapterPosition());
            final int deletedItemPosition = viewHolder.getAdapterPosition();

            adapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar
                    .make(coordinator, name + " Movido de listado!", Snackbar.LENGTH_LONG);
            snackbar.setAction("Restaurar", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedItemPosition);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds cartList to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }
}
