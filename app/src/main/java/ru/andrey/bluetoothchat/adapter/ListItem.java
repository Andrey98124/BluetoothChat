package ru.andrey.bluetoothchat.adapter;

import android.bluetooth.BluetoothDevice;

public class ListItem {
    private BluetoothDevice btDevice;
    private String itemType = BtAdapter.DEF_ITEM_TYPE;

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public BluetoothDevice getBtDevice() {
        return btDevice;
    }

    public void setBtDevice(BluetoothDevice btDevice) {
        this.btDevice = btDevice;
    }
}
