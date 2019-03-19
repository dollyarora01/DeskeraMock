package com.deskera.mock.entities;

import org.greenrobot.greendao.annotation.Entity;

@Entity
public class Item {
    public Long itemId;
    private String description;
    private Boolean isFavourite;
    private String itemName;
}
