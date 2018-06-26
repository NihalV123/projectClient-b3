package a123.vaidya.nihal.foodcrunchclient.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import a123.vaidya.nihal.foodcrunchclient.Model.Favorites;
import a123.vaidya.nihal.foodcrunchclient.Model.Order;

/**
 * Created by nnnn on 27/12/2017.
 */

public class Database extends SQLiteAssetHelper{
    private static final String DB_NAME="FoodCrunchDB.db";
    public boolean checkFoodExist(String foodId,String userPhone)
    {
        boolean flag = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String SQLQuery =String.format( "SELECT * FROM OrderDetail WHERE UserPhone = '%s' AND ProductId='%s'",
                userPhone,foodId);
        cursor = db.rawQuery(SQLQuery,null);
        if(cursor.getCount()>0)
        {
            flag = true;
        }else
            flag = false;
        cursor.close();
        return flag;

    }
    private static final int DB_VER=2;
    public Database(Context context) {
        super(context, DB_NAME,null,DB_VER);
    }

        public List <Order> getCarts(String userPhone)
        {
            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder gb = new SQLiteQueryBuilder();

            String[] sqlSelect={"UserPhone","ProductName","ProductId", "Quantity", "Price",
                    "Email", "Discount","Image" };
//check above l
            String sqlTable = "OrderDetail";


            gb.setTables(sqlTable);
            Cursor c = gb.query(db,sqlSelect,"UserPhone=?",new String[]{userPhone},null,
                    null,null);
//latest change
            final List<Order> result = new ArrayList<>();
            if(c.moveToFirst())
            {
                do{
                    result.add ( new Order
                                             (c.getString(c.getColumnIndex("UserPhone")),
                                           c.getString(c.getColumnIndex("ProductId")),
                                            c.getString(c.getColumnIndex("ProductName")),
                                             c.getString(c.getColumnIndex("Quantity")),
                                            c.getString(c.getColumnIndex("Price")),
                                                     c.getString(c.getColumnIndex("Email")),
                                             c.getString(c.getColumnIndex("Discount")),
                                            c.getString(c.getColumnIndex("Image"))
                                           ));

                }while(c.moveToNext());
                }
        return result;
        }

        public void addToCart(Order order)
        {
            SQLiteDatabase db = getReadableDatabase();
            //Damn bro a single comma took me 42 hours to debug sql is a bitch
            String query = String.format("INSERT OR REPLACE INTO OrderDetail (UserPhone,ProductId,ProductName,Quantity,Price,Email,Discount,Image)" +
                            " VALUES ('%s','%s','%s','%s','%s','%s','%s','%s');",
                    order.getUserPhone(),
                    order.getProductId(),
                    order.getProductName(),
                    order.getQuantity(),
                    order.getPrice(),
                    order.getEmail(),
                    order.getDiscount(),
                    order.getImage()

                    );
            db.execSQL(query);


        }

    public void clearCart(String userPhone)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail WHERE UserPhone ='%s'",userPhone);
        db.execSQL(query);
    }

    //favorites query
    public void addToFavorites(Favorites food)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO Favorites(FoodId,FoodName,FoodPrice,FoodMenuId,FoodImage,FoodDiscount,FoodDescription,UserPhone)" +
                "VALUES('%s','%s','%s','%s','%s','%s','%s','%s');",
                food.getFoodId(),
                food.getFoodName(),
                food.getFoodPrice(),
                food.getFoodMenuId(),
                food.getFoodImage(),
                food.getFoodDiscount(),
                food.getFoodDescription(),
                food.getUserPhone()

                );
        db.execSQL(query);
    }

    public List <Favorites> getAllFavorites(String userPhone)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder gb = new SQLiteQueryBuilder();

        String[] sqlSelect={"UserPhone","FoodId","FoodName","FoodPrice", "FoodMenuId", "FoodImage","FoodDiscount","FoodDescription"};

        String sqlTable = "Favorites";


        gb.setTables(sqlTable);
        Cursor c = gb.query(db,sqlSelect,"UserPhone=?",new String[]{userPhone},null,
                null,null);
//latest change
        final List<Favorites> result = new ArrayList<>();
        if(c.moveToFirst())
        {
            do{
                result.add( new Favorites
                                (c.getString(c.getColumnIndex("FoodId")),
                                c.getString(c.getColumnIndex("FoodName")),
                                c.getString(c.getColumnIndex("FoodPrice")),
                                c.getString(c.getColumnIndex("FoodMenuId")),
                                c.getString(c.getColumnIndex("FoodImage")),
                                c.getString(c.getColumnIndex("FoodDiscount")),
                                c.getString(c.getColumnIndex("FoodDescription")),
                                c.getString(c.getColumnIndex("UserPhone"))
                                ));

            }while(c.moveToNext());
        }
        return result;
    }


    public void removeFromFavorites(String foodId,String userPhone)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Favorites WHERE FoodId='%s' and UserPhone='%s';",foodId,userPhone);
        db.execSQL(query);
    }

    public boolean isFavorites(String foodId,String userPhone)
    {
        SQLiteDatabase db = getReadableDatabase();

        String query = String.format("SELECT * FROM Favorites WHERE FoodId='%s' and UserPhone='%s';",foodId,userPhone);
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public int getCountCart(String userPhone) {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT (*) FROM OrderDetail WHERE UserPhone='%s'",userPhone);

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
        String query = String.format("UPDATE OrderDetail SET QUANTITY = '%s' WHERE UserPhone = '%s'" +
                "AND ProductId='%s'",order.getQuantity(),order.getUserPhone(),order.getProductId());
        db.execSQL(query);
    }

    public void increaseCart(String userPhone,String foodId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE OrderDetail SET QUANTITY = QUANTITY+1 WHERE UserPhone = '%s'" +
                "AND ProductId='%s'",userPhone,foodId);
        db.execSQL(query);
    }

    public void removeFromCart(String productId, String phone) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail WHERE UserPhone ='%s' and ProductId='%s'",phone,productId);
        db.execSQL(query);
    }
}



