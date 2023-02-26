package ru.andrey.bluetoothchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import ru.andrey.bluetoothchat.adapter.BtConsts;
import ru.andrey.bluetoothchat.bluetooth.BtConnection;

public class MainActivity extends AppCompatActivity {
    private MenuItem menuItem;
    private BluetoothAdapter btAdapter;
    private final int ENABLE_REQUEST = 15;
    private SharedPreferences pref;
    private BtConnection btConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuItem = menu.findItem(R.id.bt_toggle);
        setBtIcon();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.bt_toggle){
            if(!btAdapter.isEnabled()){
                enableBt();
            } else {
                btAdapter.disable();
                menuItem.setIcon(R.drawable.ic_bluetooth_enable);
                Log.d("myLog", "Иконка должна смениться");
            }
        } else if(item.getItemId() == R.id.bt_list){
            if(btAdapter.isEnabled()){
                Intent intent = new Intent(MainActivity.this, BtListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Включите блютуз для перехода на этот экран!", Toast.LENGTH_SHORT).show();
            }
        } else if(item.getItemId() == R.id.bt_connect){
            btConnection.connect();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ENABLE_REQUEST){
            if(resultCode == RESULT_OK){
                setBtIcon();
            }
        }
    }

    private void setBtIcon(){
        if(btAdapter.isEnabled()){
            menuItem.setIcon(R.drawable.ic_bluetooth_disabled);
        } else {
            menuItem.setIcon(R.drawable.ic_bluetooth_enable);
        }
    }

    private void init(){
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pref = getSharedPreferences(BtConsts.MY_PREF, Context.MODE_PRIVATE);
        btConnection = new BtConnection(this);
    }

    private void enableBt(){
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent,ENABLE_REQUEST);
    }
}