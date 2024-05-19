package com.fit2081.fit2081_a2_tamekalougoon_32507356.provider;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// database has one table, create a version
@Database(entities = {Invoice.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class InvoiceDatabase extends RoomDatabase {

    // add database name
    public static final String DATABASE_NAME="invoice_database";

    // add access to the dao - declared as abstract to postpone implementation
    public abstract InvoiceDao invoiceDao();

    // the instance of the database - volatile ensures that all threads will use updated values
    private static volatile InvoiceDatabase instance;

    // declare the number of threads to be used
    private static final int NUMBER_OF_THREADS = 4;

    // set up number of threads
    static final ExecutorService databaseWriteExecutors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //
    static InvoiceDatabase getInstance(final Context context) {

        // if invoked, return the instance
        // if instance is null, create one
        if(instance == null) {

            // lock so only one instance can be created
            synchronized (InvoiceDatabase.class) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context, InvoiceDatabase.class, DATABASE_NAME).build();
                }
            }
        }

        return instance;
    }
}
