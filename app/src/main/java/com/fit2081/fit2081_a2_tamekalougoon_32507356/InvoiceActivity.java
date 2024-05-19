package com.fit2081.fit2081_a2_tamekalougoon_32507356;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.fit2081_a2_tamekalougoon_32507356.provider.Invoice;
import com.fit2081.fit2081_a2_tamekalougoon_32507356.provider.InvoiceViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class InvoiceActivity extends AppCompatActivity {


    // declare variables for usage
    EditText issuerNameET;
    EditText buyerNameET;
    EditText buyerAddressET;
    EditText itemNameET;
    EditText itemQuantityET;
    EditText itemCostET;
    Switch isPaidSwitch;


    String invoiceID;
    String buyerID;
    String itemID;


    // assignment 2 additions
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fabButton;
    RecyclerView recyclerView;
    ItemRecyclerAdapter itemAdapter;
    ArrayList<Item> items;

    TextView totalTV;
    int total;

    InvoiceViewModel viewModel;

    private LinearLayoutManager layoutManager;

    private SavedInvoiceFragment mInvoiceAdapter;

    // assignment 3 additions

    GestureDetectorCompat gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_invoice);

        // request sms permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        // listener for the messages
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();

        // register receiver
        registerReceiver(new SMSReceiver(), new IntentFilter("android.provider.Telephony.SMS_RECEIVED"), RECEIVER_EXPORTED);
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), Context.RECEIVER_EXPORTED);

        //prepare ET for updating
        issuerNameET = findViewById(R.id.issuerNameET);
        buyerNameET = findViewById(R.id.buyerNameET);
        buyerAddressET = findViewById(R.id.buyerAddressET);
        itemNameET = findViewById(R.id.itemNameET);
        itemQuantityET = findViewById(R.id.itemQuantityET);
        itemCostET = findViewById(R.id.itemCostET);
        isPaidSwitch = (Switch) findViewById(R.id.isPaidSwitch);

        // prepare TV for updating
        totalTV = findViewById(R.id.totalTV);
        totalTV.setText(Integer.toString(0));

        // set item quantity and cost to 0
        itemCostET.setText(Integer.toString(0));
        itemQuantityET.setText(Integer.toString(0));

        // set up drawer layout and toolbar
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // create sync
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavListener());

        fabButton = findViewById(R.id.fabBtn);
        fabButton.setOnClickListener(new FabListener());

        // set up recycler view
        recyclerView=findViewById(R.id.recycler_id);
        itemAdapter = new ItemRecyclerAdapter();

        // tell recycler how to position elements
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // set up data source
        items = new ArrayList<>();

        // link the recycler to the adapter
        itemAdapter.setItemData(items);
        recyclerView.setAdapter(itemAdapter);



        /*// places fragment in the fragment container
        mInvoiceAdapter = new SavedInvoiceFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_id, mInvoiceAdapter).commit();
*/
        // create instance of the gestures
        gestureDetector = new GestureDetectorCompat(this, new MyGestureListener());

        View v = findViewById(R.id.gesture_view);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }



    public InvoiceActivity() {

    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        // on double tap, ave the invoice
        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            addInvoice();
            return true;
        }

        // on long press, add item

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            addItem();
        }

        // horizontal scroll

        @Override
        public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {

            // work out if vertical or horizontal scroll

            if (Math.abs(distanceX) > Math.abs(distanceY)) {

                // horizontal

                // check if it was a positive or a negative scroll
                if (distanceX > 0) {

                    // if it is bottom to top
                    // get the distance travelled
                    int increaseCostAmount = (int) Math.abs(distanceX);

                    // calculate the new quantity
                    int newQuantity = Integer.parseInt(itemCostET.getText().toString()) + increaseCostAmount;

                    // update the quantity
                    itemCostET.setText(Integer.toString(newQuantity));
                } else if (distanceX < 0) {

                    // if it is top to bottom
                    // get the distance travelled
                    int decreaseCostAmount = (int) Math.abs(distanceX);

                    // calculate the new quantity
                    int newQuantity = Integer.parseInt(itemCostET.getText().toString()) - decreaseCostAmount;


                    // check if negative - if yes, set to 0
                    if (newQuantity <= 0) {
                        itemCostET.setText(Integer.toString(0));
                    }

                    // otherwise set value
                    else {
                        itemCostET.setText(Integer.toString(newQuantity));
                    }
                }

            } else if (Math.abs(distanceX) < Math.abs(distanceY)) {

                // vertical

                // check if it was a positive or a negative scroll
                if (distanceY > 0) {

                    // if it is bottom to top
                    // get the distance travelled
                    int increaseQuantityAmount = (int) Math.abs(distanceY);

                    // calculate the new quantity
                    int newQuantity = Integer.parseInt(itemQuantityET.getText().toString()) + increaseQuantityAmount;

                    // update the quantity
                    itemQuantityET.setText(Integer.toString(newQuantity));
                } else if (distanceY < 0) {

                    // if it is top to bottom
                    // get the distance travelled
                    int decreaseQuantityAmount = (int) Math.abs(distanceY);

                    // calculate the new quantity
                    int newQuantity = Integer.parseInt(itemQuantityET.getText().toString()) - decreaseQuantityAmount;


                    // check if negative - if yes, set to 0
                    if (newQuantity <= 0) {
                        itemQuantityET.setText(Integer.toString(0));
                    }

                    // otherwise set value
                    else {
                        itemQuantityET.setText(Integer.toString(newQuantity));
                    }
                }
            }
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // tells android that this is a menu
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    class NavListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();

            if (id == R.id.nav_menu_add_item) {
                addItem();
            }

            else if (id == R.id.nav_menu_add_invoice) {
                addInvoice();

            }
            else if (id == R.id.nav_menu_clear_fields) {
                clearFields();
            }
            else if (id == R.id.nav_menu_items_map) {
                displayMap();
            }
            else if (id == R.id.nav_menu_list_invoices) {
                listInvoices();
            }
            else if (id == R.id.nav_menu_exit_app) {
                exitApp();
            }
            // close the drawer
            drawerLayout.closeDrawers();
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //the options in the options list
        int id = item.getItemId();

        // processes the item selected
        if (id == R.id.options_addItem) {
            addItem();
        }

        else if (id == R.id.options_clearFields) {
            clearFields();
        }



        return true;
    }

    class FabListener implements View.OnClickListener {

        //on click, display snackbar
        @Override
        public void onClick(View v) {


            // notify that it has been updated and to refresh the list
            addInvoice();
           // Snackbar.make(v, "Added Invoice", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void calculateTotal() {
            // get total cost of the item addition
            int itemGroupCost = Integer.parseInt(itemQuantityET.getText().toString()) * Integer.parseInt(itemCostET.getText().toString());
            int totalNew = 0;

            // add it to the current total to get new total
            totalNew =  Integer.parseInt(totalTV.getText().toString()) + itemGroupCost;

            //update the total
            totalTV.setText(Integer.toString(totalNew));
    }

    public void addItem() {
        Item itemAdd = new Item(itemNameET.getText().toString(), Integer.parseInt(itemQuantityET.getText().toString()), Integer.parseInt(itemCostET.getText().toString()));
        items.add(itemAdd);

        // notifies that there has been an item added
        itemAdapter.notifyDataSetChanged();

        //re calculate the invoice total
        calculateTotal();
    }

    public void onWikiBtnClick(View view) {

        //get item name
        String itemName = itemNameET.getText().toString();


        // switch to wiki page

        Intent wikiIntent = new Intent(this, WikiViewerActivity.class);
        wikiIntent.putExtra("itemName", itemName);
        startActivity(wikiIntent);
    }

    public void addInvoice() {
// create view model
        viewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

        // create a new row

        Invoice invoice1 = new Invoice(issuerNameET.getText().toString(), buyerNameET.getText().toString(), buyerAddressET.getText().toString(), isPaidSwitch.isChecked(), items, Integer.parseInt(totalTV.getText().toString()));

        // insert new row into database
        viewModel.insertNewInvoice(invoice1);

        // reset the items, delay so not null in database
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clearAndReset();
            }
        },100);



    }

    public void exitApp() {
        Intent exitIntent = new Intent(Intent.ACTION_MAIN);
        exitIntent.addCategory(Intent.CATEGORY_HOME);
        exitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(exitIntent);
    }

    public void clearFields() {
        issuerNameET.setText("");
        buyerNameET.setText("");
        buyerAddressET.setText("");
        isPaidSwitch.setChecked(false);
        itemNameET.setText("");
        itemCostET.setText("");
        itemQuantityET.setText("");
    }

    public void displayMap() {
        // switch to invoice page
        Intent displayMapIntent = new Intent(this, ItemsMapActivity.class);
        startActivity(displayMapIntent);
    }

    public void listInvoices() {
        // switch to invoice page
        Intent allInvoiceIntent = new Intent(this, ShowInvoiceActivity.class);
        startActivity(allInvoiceIntent);
    }

    public void clearAndReset() {

        // clear the items array
        items.clear();

        // reset the invoice total
        totalTV.setText(Integer.toString(0));

        // notifies that there has been an item added
        itemAdapter.notifyDataSetChanged();
    }



    // -------------------------- A1 FUNCTIONS ----------------------------------------------
    public void saveData () {

        // setting numbers for random generator and storing them in an array
        Random rand = new Random();
        int[] randNums =new int[12];

        for (int i = 0; i < randNums.length; i++) {
            randNums[i] = rand.nextInt(10);
        }

        // setting characters for random generator and storing them
        char randomChar1 = (char)(rand.nextInt(26) + 'a');
        randomChar1 = Character.toUpperCase(randomChar1);
        char randomChar2 = (char)(rand.nextInt(26) + 'a');
        randomChar2 = Character.toUpperCase(randomChar2);

        // get inputs for usage
        String issuerName = issuerNameET.getText().toString();
        String buyerName = buyerNameET.getText().toString();
        String buyerAddress = buyerAddressET.getText().toString();
        Boolean isPaid = isPaidSwitch.isChecked();
        String itemName = itemNameET.getText().toString();
        int itemQuantity = Integer.parseInt(itemQuantityET.getText().toString());
        int itemCost = Integer.parseInt(itemCostET.getText().toString());

        //generate invoiceID
        // append this to a single string including the other values
        String invoiceID = "I" + randomChar1 + randomChar2 + "-" + randNums[1] + randNums[2] + randNums[3] + randNums[4];


        //generate buyerID
        // get the first two letters of the buyer name and save as a string
        String twoLettersBuyer = buyerName.substring(0, 2);
        twoLettersBuyer = twoLettersBuyer.toUpperCase();

        // append this to a single string including the other values
        String buyerID = "B" + twoLettersBuyer + "-" + randNums[5] + randNums[6] + randNums[7];


        //generate itemID
        // get the first two letters of the item name and save as a string
        String twoLettersItem = itemName.substring(0, 2);
        twoLettersItem = twoLettersItem.toUpperCase();

        // append this to a single string including the other values
        String itemID = "T" + twoLettersItem + "-" + randNums[8] + randNums[9] + randNums[10] + randNums[11];

        // output the new id's
        Toast.makeText(this, "Saved Successfully -" + " Invoice ID: " + invoiceID + " Buyer ID: " + buyerID + " Item ID: " + itemID, Toast.LENGTH_LONG).show();



        SharedPreferences invoiceSP = getApplicationContext().getSharedPreferences("loginPref", 0);
        SharedPreferences.Editor editor = invoiceSP.edit();

        //store in shared pref
        editor.putString("Invoice ID", invoiceID);
        editor.putString("Issuer Name", issuerName);
        editor.putString("Buyer ID", buyerID);
        editor.putString("Buyer Name", buyerName);
        editor.putString("Buyer Address", buyerAddress);
        editor.putBoolean("Is Paid", isPaid);
        editor.putString("Item ID", itemID);
        editor.putString("Item Name", itemName);
        editor.putInt("Item Quantity", itemQuantity);
        editor.putInt("Item Cost", itemCost);

        editor.commit();

    }

    public void loadData() {

        SharedPreferences invoiceSP = getApplicationContext().getSharedPreferences("loginPref", 0);

        // retrieve values from shared pref
        String invoiceID = invoiceSP.getString("Invoice ID", null);
        String issuerName = invoiceSP.getString("Issuer Name", null);
        String buyerID = invoiceSP.getString("Buyer ID", null);
        String buyerName = invoiceSP.getString("Buyer Name", null);
        String buyerAddress = invoiceSP.getString("Buyer Address", null);
        Boolean isPaid = invoiceSP.getBoolean("Is Paid", false);
        String itemID = invoiceSP.getString("Item ID", null);
        String itemName = invoiceSP.getString("Item Name", null);
        int itemQuantity = invoiceSP.getInt("Item Quantity", 0);
        int itemCost = invoiceSP.getInt("Item Cost", 0);

        issuerNameET.setText(issuerName);
        buyerNameET.setText(buyerName);
        buyerAddressET.setText(buyerAddress);
        if (isPaid.equals(true)) {
            isPaidSwitch.setChecked(true);
        } else if (isPaid.equals(false)) {
            isPaidSwitch.setChecked(false);
        }
        itemNameET.setText(itemName);
        itemCostET.setText(Integer.toString(itemCost));
        itemQuantityET.setText(Integer.toString(itemQuantity));

        Toast.makeText(this, "Loaded Successfully -" + " Invoice ID: " + invoiceID + " Buyer ID: " + buyerID + " Item ID: " + itemID, Toast.LENGTH_LONG).show();
    }

    public void onSaveBtnClick (View view) {
        saveData();
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            // retrieve the sms
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            // parse the sms
            StringTokenizer sT = new StringTokenizer(msg, ";");
            int parameterNumber = sT.countTokens();

            // determine whether it is invoice or item information
            // convert to uppercase for error prevention
            String determiner = sT.nextToken();
            determiner = determiner.toUpperCase();

            //check correct number of parameters for an invoice, if valid continue
            if (parameterNumber == 5 && determiner.equals("INVOICE")) {

                // processes the sms
                String issuerName = sT.nextToken();
                String buyerName = sT.nextToken();
                String buyerAddress = sT.nextToken();
                String isPaid = sT.nextToken();
                isPaid = isPaid.toUpperCase();

                // update the et and switch from sms
                issuerNameET.setText(issuerName);
                buyerNameET.setText(buyerName);
                buyerAddressET.setText(buyerAddress);
                if (isPaid.equals("TRUE")) {
                    isPaidSwitch.setChecked(true);
                } else if (isPaid.equals("FALSE")) {
                    isPaidSwitch.setChecked(false);
                }

            }

            // if the determiner was item, check it has the correct amount of parameters
            // if parameter number is correct, continue
            else if (determiner.equals("ITEM") && parameterNumber == 4) {
                String itemName = sT.nextToken();
                String itemQuantity = sT.nextToken();
                String itemCost = sT.nextToken();

                itemNameET.setText(itemName);
                itemQuantityET.setText(itemQuantity);
                itemCostET.setText(itemCost);
            }
            else if (determiner.equals("SAVE")) {
                saveData();
            }
            else if (determiner.equals("LOAD")) {
                loadData();
            }
            else if (parameterNumber != 5 && determiner.equals("INVOICE")) {
                Toast.makeText(getApplicationContext(), "Must have 5 parameters for invoice", Toast.LENGTH_LONG).show();
            }
            else if (parameterNumber != 4 && determiner.equals("ITEM")) {
                Toast.makeText(getApplicationContext(), "Must have 4 parameters for item", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Must choose Invoice, Item, Save or Load", Toast.LENGTH_LONG).show();
            }

        }
    }



}







// note on additional functionality for the FAB button that could be added later
/*class FabListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Clicked button", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put the action of the FAB button text click here (the additional click)
            }
        }).show();
    }
}*/

