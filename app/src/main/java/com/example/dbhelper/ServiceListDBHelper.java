package com.example.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pojo.ServiceListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ServiceListDBHelper extends SQLiteOpenHelper {
    public ServiceListDBHelper(@Nullable Context context) {
        super(context, Constant.DB_NAME, null, Constant.VERSION);
    }

    public long queryCount() {
        String sqlStr = "select count(*) from " + Constant.TableName.SERVICE_LIST;
        Cursor cursor = getReadableDatabase().rawQuery(sqlStr, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Constant.TableName.SERVICE_LIST
                + "(id integer primary key,service_name varchar(60),img_url varchar(120),sort integer)");
        db.execSQL("create table " + Constant.TableName.BUS_LIST
                + " (id integer primary key,name varchar(50),first varchar(50), bus_end varchar(50),startTime varchar(50),endTime varchar(50),price integer,mileage varchar(10))");
        db.execSQL("create table " + Constant.TableName.PARKING_LOT +
                "(id integer primary key,parkName varchar(60),rates varchar(10),vacancy varchar(10),open varchar(10),imgUrl varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Constant.TableName.SERVICE_LIST);
        onCreate(db);
    }

    public void insert(List<ServiceListParam.RowsDTO> data) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase writableDatabase = getWritableDatabase();

        for (ServiceListParam.RowsDTO datum : data) {
            contentValues.clear();
            contentValues.put("id", datum.getId());
            contentValues.put("service_name", datum.getServiceName());
            contentValues.put("img_url", datum.getImgUrl());
            contentValues.put("sort", datum.getSort());
            writableDatabase.insert(Constant.TableName.SERVICE_LIST, null, contentValues);
        }
    }

    public List<ServiceListParam.RowsDTO> query() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.query(Constant.TableName.SERVICE_LIST, null, null, null, null, null, "id asc");
        cursor.moveToFirst();
        List<ServiceListParam.RowsDTO> data = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            ServiceListParam.RowsDTO rowsDTO = new ServiceListParam.RowsDTO();
            rowsDTO.setId(cursor.getInt(0));
            rowsDTO.setServiceName(cursor.getString(1));
            rowsDTO.setImgUrl(cursor.getString(2));
            rowsDTO.setSort(cursor.getInt(3));
            data.add(rowsDTO);
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }
}
