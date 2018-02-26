package com.santra.sanchita.portfolioapp.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by sanchita on 9/12/17.
 */

@Entity(nameInDb = "design_item")
public class DesignItem {

    @Expose
    @SerializedName("id")
    @Id
    private Long id;

    @Expose
    @SerializedName("parent_item_id")
    @Property(nameInDb = "parent_item_id")
    private Long parentItemId;

    @Expose
    @SerializedName("design_item_name")
    @Property(nameInDb = "design_item_name")
    private String designItemName;

    @Expose
    @SerializedName("image_path")
    @Property(nameInDb = "image_path")
    private String imagePath;

    @Expose
    @SerializedName("description")
    @Property(nameInDb = "description")
    private String description;

    @Expose
    @SerializedName("is_liked")
    @Property(nameInDb = "is_liked")
    private boolean isLiked;

    @ToMany(referencedJoinProperty = "parentItemId")
    private List<DesignItem> subItemList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2136024226)
    private transient DesignItemDao myDao;

    @Generated(hash = 121327550)
    public DesignItem(Long id, Long parentItemId, String designItemName,
            String imagePath, String description, boolean isLiked) {
        this.id = id;
        this.parentItemId = parentItemId;
        this.designItemName = designItemName;
        this.imagePath = imagePath;
        this.description = description;
        this.isLiked = isLiked;
    }

    @Generated(hash = 1517974120)
    public DesignItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignItemName() {
        return designItemName;
    }

    public void setDesignItemName(String designItemName) {
        this.designItemName = designItemName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public Long getParentItemId() {
        return this.parentItemId;
    }

    public void setParentItemId(Long parentItemId) {
        this.parentItemId = parentItemId;
    }

    public boolean getIsLiked() {
        return this.isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 885180500)
    public List<DesignItem> getSubItemList() {
        if (subItemList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DesignItemDao targetDao = daoSession.getDesignItemDao();
            List<DesignItem> subItemListNew = targetDao
                    ._queryDesignItem_SubItemList(id);
            synchronized (this) {
                if (subItemList == null) {
                    subItemList = subItemListNew;
                }
            }
        }
        return subItemList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1380327044)
    public synchronized void resetSubItemList() {
        subItemList = null;
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
    @Generated(hash = 1293461304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDesignItemDao() : null;
    }
}
