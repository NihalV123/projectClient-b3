package a123.vaidya.nihal.foodcrunchclient.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import a123.vaidya.nihal.foodcrunchclient.Model.Order;

/**
 * Created by nnnn on 27/12/2017.
 */

public class Database extends SQLiteAssetHelper{
    private static final String DB_NAME="FoodCrunchDB.db";
    private static final int DB_VER=2;
    public Database(Context context) {
        super(context, DB_NAME,null,DB_VER);
    }
        public List <Order> getCarts()
        {
            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder gb = new SQLiteQueryBuilder();

            String[] sqlSelect={"ID","ProductName","ProductId", "Quantity", "Price",
//                    "Email" ,
                    "Discount","Image"};
//check above l
            String sqlTable = "OrderDetail";


            gb.setTables(sqlTable);
            Cursor c = gb.query(db,sqlSelect,null,null,null,
                    null,null);
//latest change
            final List<Order> result = new ArrayList<>();
            if(c.moveToFirst())
            {
                do{
                    result.add ( new Order
                            //debugging cart change later
                            (c.getInt(c.getColumnIndex("ID")),
                            //(
                                    c.getString(c.getColumnIndex("ProductId")),
                                            c.getString(c.getColumnIndex("ProductName")),
                                             c.getString(c.getColumnIndex("Quantity")),
                                            c.getString(c.getColumnIndex("Price")),
                                           // c.getString(c.getColumnIndex("Email")),
                                             c.getString(c.getColumnIndex("Discount")),
                                    c.getString(c.getColumnIndex("Image"))));

                }while(c.moveToNext());
                }
        return result;
        }

        public void addToCart(Order order)
        {
            SQLiteDatabase db = getReadableDatabase();
            //Damn bro a single comma took me 42 hours to debug sql is a bitch
            String query = String.format("INSERT INTO OrderDetail (ProductId,ProductName,Quantity,Price,Discount,Image) " +
                            "VALUES ('%s','%s','%s','%s','%s','%s');",
                    order.getProductId(),
                    order.getProductName(),
                    order.getQuantity(),
                    order.getPrice(),
                    //order.getEmail(),
                    order.getDiscount(),
                    order.getImage());
            db.execSQL(query);


        }


    public void clearCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM OrderDetail";
        db.execSQL(query);
    }

    //favorites query
    public void addToFavorites(String foodId)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO Favorites(FoodId)VALUES('%s');",foodId);
        db.execSQL(query);
    }
    public void removeFromFavorites(String foodId)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Favorites WHERE FoodId='%s';",foodId);
        db.execSQL(query);
    }

    public boolean isFavorites(String foodId)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM Favorites WHERE FoodId='%s';",foodId);
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public int getCountCart() {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT COUNT (*) FROM OrderDetail";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                count = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return count;
    }

    public void updateCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE OrderDetail SET QUANTITY = %s WHERE ID = %d",order.getQuantity(),order.getID());
        db.execSQL(query);
    }
}



