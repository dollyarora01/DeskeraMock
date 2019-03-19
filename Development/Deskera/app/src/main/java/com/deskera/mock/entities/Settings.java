package com.deskera.mock.entities;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.Months;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.joda.time.Period;
import org.joda.time.PeriodType;

@Entity
public class Settings {

    @Id
    private Long userId;
    @Property
    boolean notification;
    @Convert(converter = TemperatureConverter.class, columnType = Integer.class)
    TemperatureType temperatureType;
    @Property
    double temperature;
    @Property
    boolean sound;
    @Property
    Long doj;
    @Property
    Long probationDate;
    @ToOne(joinProperty = "userId")
    private User user;
    @Transient
    Long permanentDate;
    @Transient
    String probationLength;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 345530924)
    private transient SettingsDao myDao;


    @Generated(hash = 247581433)
    public Settings(Long userId, boolean notification, TemperatureType temperatureType,
                    double temperature, boolean sound, Long doj, Long probationDate) {
        this.userId = userId;
        this.notification = notification;
        this.temperatureType = temperatureType;
        this.temperature = temperature;
        this.sound = sound;
        this.doj = doj;
        this.probationDate = probationDate;
    }


    @Generated(hash = 456090543)
    public Settings() {
    }


    public Long getUserId() {
        return this.userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public boolean getNotification() {
        return this.notification;
    }


    public void setNotification(boolean notification) {
        this.notification = notification;
    }


    public TemperatureType getTemperatureType() {
        return this.temperatureType;
    }


    public void setTemperatureType(TemperatureType temperatureType) {
        this.temperatureType = temperatureType;
    }


    public double getTemperature() {
        return this.temperature;
    }


    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }


    public boolean getSound() {
        return this.sound;
    }


    public void setSound(boolean sound) {
        this.sound = sound;
    }


    public Long getDoj() {
        return this.doj ;
    }


    public void setDoj(Long doj) {
        this.doj = doj;
    }


    public Long getProbationDate() {
        return this.probationDate;
    }


    public void setProbationDate(Long probationDate) {
        this.probationDate = probationDate;
    }


    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;


    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 859885876)
    public User getUser() {
        Long __key = this.userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }


    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 496399742)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            userId = user == null ? null : user.getUserId();
            user__resolvedKey = userId;
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


    public static class TemperatureConverter implements PropertyConverter<TemperatureType, Integer> {
        @Override
        public TemperatureType convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (TemperatureType role : TemperatureType.values()) {
                if (role.id == databaseValue) {
                    return role;
                }
            }
            return TemperatureType.DEFAULT;
        }

        @Override
        public Integer convertToDatabaseValue(TemperatureType entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }

    @Keep
    public Long getPermanentDate() {
        if (getProbationDate() != null) {
            return new DateTime(getProbationDate()).plusDays(1).getMillis();
        }
        return null;
    }

    @Keep
    public String getProbationLength() {
        if (getDoj() != null && getProbationDate() != null) {
            DateTime doj = new DateTime(getDoj());
            DateTime probationDate = new DateTime(getProbationDate());
            int difference = Months.monthsBetween(doj, probationDate).getMonths();
            return difference > 6 ? "More than 6 months" :
                    "Less than 6 months";
        }
        return "User is on probation";
    }

    @Keep
    public String getProbationDuration() {
        if (getDoj() != null && getProbationDate() != null) {
            PeriodType fields = PeriodType.forFields(new DurationFieldType[]{
                    DurationFieldType.months(), DurationFieldType.days()
            });
            DateTime doj = new DateTime(getDoj());
            DateTime probationDate = new DateTime(getProbationDate());
            Period period = new Period(doj, probationDate)
                    .normalizedStandard(fields);
            return String.format("%s months %s days", period.getMonths(), period.getDays());
        }
        return "";
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1870773816)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSettingsDao() : null;
    }

}


