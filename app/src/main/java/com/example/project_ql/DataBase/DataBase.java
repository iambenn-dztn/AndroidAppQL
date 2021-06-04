package com.example.project_ql.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
//Truy van khong tra ve
    public void QueryData(String sql) {
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

//    INSERT SP
    public void INSERT_SP(String ten, int soluong, int gianhap,int giaban, String nhaphanphoi, String ghicgu, byte[] anh) {
        SQLiteDatabase db=getWritableDatabase();
        String sql = "INSERT INTO tblSanPham VALUES(null,?,?,?,?,?,?,?)";
        SQLiteStatement statement=db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, ten);
        statement.bindDouble(2, soluong);
        statement.bindDouble(3, gianhap);
        statement.bindDouble(4, giaban);
        statement.bindString(5, nhaphanphoi);
        statement.bindString(6, ghicgu);
        if(anh==null) statement.bindBlob(7, null);
        else statement.bindBlob(7,anh);

        statement.executeInsert();
    }



//Truy van co tra ket qua
    public Cursor GetData(String sql) {
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
