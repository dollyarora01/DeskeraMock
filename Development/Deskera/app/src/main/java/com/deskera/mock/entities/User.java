package com.deskera.mock.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity
public class User {

    @Id(autoincrement = true)
    Long userId;
    @Property
    String username;
    @Property
    String email;
    @Property
    String hobby;
    @ToOne(joinProperty = "userId")
    private Settings settings;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;
    @Generated(hash = 247153925)
    private transient Long settings__resolvedKey;
    @Generated(hash = 356349294)
    public User(Long userId, String username, String email, String hobby) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.hobby = hobby;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getHobby() {
        return this.hobby;
    }
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 333130130)
    public Settings getSettings() {
        Long __key = this.userId;
        if (settings__resolvedKey == null || !settings__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SettingsDao targetDao = daoSession.getSettingsDao();
            Settings settingsNew = targetDao.load(__key);
            synchronized (this) {
                settings = settingsNew;
                settings__resolvedKey = __key;
            }
        }
        return settings;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1169111881)
    public void setSettings(Settings settings) {
        synchronized (this) {
            this.settings = settings;
            userId = settings == null ? null : settings.getUserId();
            settings__resolvedKey = userId;
        }
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
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }


}
