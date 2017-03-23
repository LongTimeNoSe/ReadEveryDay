package com.readeveryday.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.readeveryday.greendao.MeiZhiCollection;

import com.readeveryday.greendao.MeiZhiCollectionDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig meiZhiCollectionDaoConfig;

    private final MeiZhiCollectionDao meiZhiCollectionDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        meiZhiCollectionDaoConfig = daoConfigMap.get(MeiZhiCollectionDao.class).clone();
        meiZhiCollectionDaoConfig.initIdentityScope(type);

        meiZhiCollectionDao = new MeiZhiCollectionDao(meiZhiCollectionDaoConfig, this);

        registerDao(MeiZhiCollection.class, meiZhiCollectionDao);
    }
    
    public void clear() {
        meiZhiCollectionDaoConfig.clearIdentityScope();
    }

    public MeiZhiCollectionDao getMeiZhiCollectionDao() {
        return meiZhiCollectionDao;
    }

}
