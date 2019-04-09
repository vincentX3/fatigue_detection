package com.example.dell.fatiguedetection;

import android.app.ActivityOptions;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.View;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import static android.util.Log.i;

public class BluetoothConnection extends AppCompatActivity {

    private Toolbar toolbar_bluetooth_connection;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager mBluetoothManager;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattService mBluetoothGattServices;
    private BluetoothGattCharacteristic characteristic;
    private boolean mScanning;
    private Handler mHandler;
    private List<BluetoothDevice> bluetoothDeviceArrayList =new ArrayList<>();
    List<String> serviceslist = new ArrayList<String>();
    private ListView list;

    private static final long SCAN_PERIOD = 10000;
    final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connection);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());

        initView();

        mBluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mHandler = new Handler();

        toolbar_bluetooth_connection=(Toolbar)findViewById(R.id.toolbar_bluetooth_connection);


        toolbar_bluetooth_connection.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BluetoothConnection.this,Home.class),
                        ActivityOptions.makeSceneTransitionAnimation(BluetoothConnection.this).toBundle());
            }
        });
    }

    protected void onResume(){
        super.onResume();
        scanLeDevice(true);

    }

    //蓝牙搜索的回调
    final BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] bytes) {
            //rssi为信号强度
            i("TAG", "onLeScan: " + bluetoothDevice.getName() + "/t" + bluetoothDevice.getAddress() + "/t" + bluetoothDevice.getBondState());
            if(!bluetoothDeviceArrayList.contains(bluetoothDevice)) {
                bluetoothDeviceArrayList.add(bluetoothDevice);
            }
            list.setAdapter(new BleListAdapter(BluetoothConnection.this,bluetoothDeviceArrayList));
        }
    };



    private void scanLeDevice(final boolean enable) {
        if (enable) {//true
            //10秒后停止搜索
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(callback);
                }
            }, SCAN_PERIOD);
            mScanning = true;
            Toast.makeText(BluetoothConnection.this, "蓝牙扫描中", Toast.LENGTH_LONG);
            mBluetoothAdapter.startLeScan(callback);//开始搜索

        } else {//false
            mScanning = false;
            mBluetoothAdapter.stopLeScan(callback);//停止搜索
        }
    }

    public void initView(){
        list = (ListView)findViewById(R.id.bluetooth_list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mBluetoothDevice = bluetoothDeviceArrayList.get(i);
                mBluetoothGatt = mBluetoothDevice.connectGatt(BluetoothConnection.this, false, gattcallback);
                Toast.makeText(BluetoothConnection.this, "设备连接中...",Toast.LENGTH_LONG);
            }
        });

    }

    private BluetoothGattCallback gattcallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, final int newState) {
            super.onConnectionStateChange(gatt, status, newState);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String status;
                    switch (newState) {
                        //已经连接
                        case BluetoothGatt.STATE_CONNECTED:
                            Toast.makeText(BluetoothConnection.this, "设备已连接", Toast.LENGTH_LONG);

                            //该方法用于获取设备的服务，寻找服务
                            mBluetoothGatt.discoverServices();
                            break;
                        //正在连接
                        case BluetoothGatt.STATE_CONNECTING:
                            Toast.makeText(BluetoothConnection.this, "正在连接...", Toast.LENGTH_LONG);
                            break;
                        //连接断开
                        case BluetoothGatt.STATE_DISCONNECTED:
                            Toast.makeText(BluetoothConnection.this, "设备已断开", Toast.LENGTH_LONG);
                            break;
                        //正在断开
                        case BluetoothGatt.STATE_DISCONNECTING:
                            Toast.makeText(BluetoothConnection.this, "断开中...", Toast.LENGTH_LONG);
                            break;
                    }
                    //pd.dismiss();
                }
            });
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);

            if(status == mBluetoothGatt.GATT_SUCCESS){
                final List<BluetoothGattService> services = mBluetoothGatt.getServices();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(final BluetoothGattService bluetoothGattService : services){
                            mBluetoothGattServices = bluetoothGattService;
                            Log.i(TAG,"onServicesDiscovered: "+bluetoothGattService.getUuid());
                            List<BluetoothGattCharacteristic>charc = bluetoothGattService.getCharacteristics();
                            for (BluetoothGattCharacteristic charac : charc){
                                Log.i(TAG,"run: "+charac.getUuid());
                                if(charac.getUuid().toString().equals("")){
                                    characteristic = charac;
                                    System.out.println("还没实现哈哈哈");
                                }
                            }

                            serviceslist.add(mBluetoothGattServices.getUuid().toString());
                        }
                    }
                });
            }

        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);

            if(status == mBluetoothGatt.GATT_SUCCESS){
                final int sum = characteristic.getValue()[0];
                Log.e(TAG,"onCharacteristicRead "+ characteristic.getValue()[0]);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    } ;


}
