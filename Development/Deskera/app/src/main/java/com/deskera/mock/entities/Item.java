package com.deskera.mock.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Item {
    @Id
    Long itemId;
    private Long userId;
    @Property
    private String description;
    @Property
    private Boolean isFavourite;
    @Property
    private String itemName;
    private long categoryId;
    @ToOne(joinProperty = "categoryId")
    private Category category;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 182764869)
    private transient ItemDao myDao;
    @Generated(hash = 1372501278)
    private transient Long category__resolvedKey;

    @Generated(hash = 1180908888)
    public Item(Long itemId, Long userId, String description, Boolean isFavourite, String itemName,
            long categoryId) {
        this.itemId = itemId;
        this.userId = userId;
        this.description = description;
        this.isFavourite = isFavourite;
        this.itemName = itemName;
        this.categoryId = categoryId;
    }

    @Generated(hash = 1470900980)
    public Item() {
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsFavourite() {
        return this.isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 234631651)
    public Category getCategory() {
        long __key = this.categoryId;
        if (category__resolvedKey == null || !category__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDao targetDao = daoSession.getCategoryDao();
            Category categoryNew = targetDao.load(__key);
            synchronized (this) {
                category = categoryNew;
                category__resolvedKey = __key;
            }
        }
        return category;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1173781595)
    public void setCategory(@NotNull Category category) {
        if (category == null) {
            throw new DaoException(
                    "To-one property 'categoryId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.category = category;
            categoryId = category.getCategoryId();
            category__resolvedKey = categoryId;
        }
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 881068859)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getItemDao() : null;
    }
}
