package com.nihalvaidya123.foodcrunch.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.nihalvaidya123.foodcrunch.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nnnn on 27/12/2017.
 */

public class Database extends SQLiteAssetHelper{
    private static final String DB_NAME="FoodCrunchDB.db";
    private static final int DB_VERSION=1;
    public Database(Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }
        public List <Order> getCarts()
        {
            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder gb = new SQLiteQueryBuilder();

            String[] sqlSelect={"ProductName","ProductId", "Quantity", "Price", "Discount"};
//check above l
            String sqlTable = "OrderDetail";


            gb.setTables(sqlTable);
            Cursor c = gb.query(db,sqlSelect,null,null,null,
                    null,null);

            final List<Order> result = new ArrayList<>();
            if(c.moveToFirst())
            {
                do{
                    result.add ( new Order(c.getString(c.getColumnIndex("ProductId")),
                                            c.getString(c.getColumnIndex("ProductName")),
                                             c.getString(c.getColumnIndex("Quantity")),
                                            c.getString(c.getColumnIndex("Price")),
                                            c.getString(c.getColumnIndex("Discount"))));
                }while(c.moveToNext());
                }
        return result;
        }

        public void addToCart(Order order)
        {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName," +
                    "Quantity,Price,Discount) VALUES ('%s','%s','%s','%s','%s');"+
                    order.getProductId(),
                    order.getProductName(),
                    order.getQuantity(),
                    order.getPrice(),
                    order.getDiscount());
            db.execSQL(query);
        }

}



