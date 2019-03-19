package com.deskera.mock.entities;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Table implements Parcelable {
    @Id(autoincrement = true)
    Long tableId;
    private Long userId;
    @Property
    private String name;
    @Generated(hash = 2105662825)
    public Table(Long tableId, Long userId, String name) {
        this.tableId = tableId;
        this.userId = userId;
        this.name = name;
    }
    @Generated(hash = 752389689)
    public Table() {
    }

    protected Table(Parcel in) {
        if (in.readByte() == 0) {
            tableId = null;
        } else {
            tableId = in.readLong();
        }
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readLong();
        }
        name = in.readString();
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    public Long getTableId() {
        return this.tableId;
    }
    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.tableId);
        dest.writeValue(this.userId);
        dest.writeString(this.name);
    }
}
