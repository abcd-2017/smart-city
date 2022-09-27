package com.example.dbhelper.bus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pojo.busparam.BusListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class BusListDBHelper extends SQLiteOpenHelper {
    public BusListDBHelper(@Nullable Context context) {
        super(context, Constant.DB_NAME, null, Constant.VERSION);
    }

    public long queryCount() {
        String sqlStr = "select count(*) from " + Constant.TableName.BUS_LIST;
        Cursor cursor = getReadableDatabase().rawQuery(sqlStr, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStr = "create table " + Constant.TableName.BUS_LIST
                + " (id integer primary key,name varchar(50),first varchar(50), bus_end varchar(50),startTime varchar(50),endTime varchar(50),price integer,mileage varchar(10))";
        System.out.println(sqlStr);
        db.execSQL(sqlStr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Constant.TableName.BUS_LIST);
        onCreate(db);
    }

    public List<BusListParam.RowsDTO> query() {
        List<BusListParam.RowsDTO> list = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = readableDatabase.query(Constant.TableName.BUS_LIST, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BusListParam.RowsDTO rowsDTO = new BusListParam.RowsDTO();
            rowsDTO.setId(cursor.getInt(0));
            rowsDTO.setName(cursor.getString(1));
            rowsDTO.setFirst(cursor.getString(2));
            rowsDTO.setEnd(cursor.getString(3));
            rowsDTO.setStartTime(cursor.getString(4));
            rowsDTO.setEndTime(cursor.getString(5));
            rowsDTO.setPrice(cursor.getInt(6));
            rowsDTO.setMileage(cursor.getString(7));
            list.add(rowsDTO);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public void insert(List<BusListParam.RowsDTO> listParamRows) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (BusListParam.RowsDTO listParamRow : listParamRows) {
            contentValues.clear();
            contentValues.put("id", listParamRow.getId());
            contentValues.put("name", listParamRow.getName());
            contentValues.put("first", listParamRow.getFirst());
            contentValues.put("bus_end", listParamRow.getEnd());
            contentValues.put("startTime", listParamRow.getStartTime());
            contentValues.put("endTime", listParamRow.getEndTime());
            contentValues.put("price", listParamRow.getPrice());
            contentValues.put("mileage", listParamRow.getMileage());
            long insert = writableDatabase.insert(Constant.TableName.BUS_LIST, null, contentValues);
            System.out.println(insert);
        }
    }

    public void queryLineById(Integer id) {
        String where = "id = " + id;
        SQLiteDatabase readableDatabase = getReadableDatabase();
//        readableDatabase.query()
    }
}
