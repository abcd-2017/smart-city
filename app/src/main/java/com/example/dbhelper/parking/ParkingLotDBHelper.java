package com.example.dbhelper.parking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pojo.parkingparam.ParkingLotListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ParkingLotDBHelper extends SQLiteOpenHelper {

    public ParkingLotDBHelper(@Nullable Context context) {
        super(context, Constant.DB_NAME, null, Constant.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStr = "create table " +
                Constant.TableName.PARKING_LOT +
                "(id integer primary key,parkName varchar(60),rates varchar(10),vacancy varchar(10),open varchar(10),imgUrl varchar(100))";
        db.execSQL(sqlStr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Constant.TableName.PARKING_LOT);
        onCreate(db);
    }

    public synchronized void insert(List<ParkingLotListParam.RowsDTO> data) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (ParkingLotListParam.RowsDTO datum : data) {
            contentValues.clear();
            contentValues.put("id", datum.getId());
            contentValues.put("rates", datum.getRates());
            contentValues.put("vacancy", datum.getVacancy());
            contentValues.put("imgUrl", datum.getImgUrl());
            contentValues.put("parkName", datum.getParkName());
            writableDatabase.insert(Constant.TableName.PARKING_LOT, null, contentValues);
        }
    }

    public void update(ParkingLotListParam.RowsDTO rowsDTO) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("rates", rowsDTO.getRates());
        contentValues.put("parkName", rowsDTO.getParkName());
        contentValues.put("vacancy", rowsDTO.getVacancy());
        contentValues.put("imgUrl", rowsDTO.getImgUrl());
        String where = "id = " + rowsDTO.getId();
        int update = writableDatabase.update(Constant.TableName.PARKING_LOT, contentValues, where, null);
    }

    public List<ParkingLotListParam.RowsDTO> query() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = readableDatabase.query(Constant.TableName.PARKING_LOT, null, null, null, null, null, null);
        cursor.moveToFirst();
        List<ParkingLotListParam.RowsDTO> rowsDTOS = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            ParkingLotListParam.RowsDTO rowsDTO = new ParkingLotListParam.RowsDTO();
            rowsDTO.setId(cursor.getInt(0));
            rowsDTO.setParkName(cursor.getString(1));
            rowsDTO.setVacancy(cursor.getString(2));
            rowsDTO.setImgUrl(cursor.getString(3));
            rowsDTOS.add(rowsDTO);
            cursor.moveToNext();
        }
        cursor.close();
        return rowsDTOS;
    }

    public long queryCount() {
        String sqlStr = "select count(*) from " + Constant.TableName.PARKING_LOT;
        Cursor cursor = getReadableDatabase().rawQuery(sqlStr, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }
}