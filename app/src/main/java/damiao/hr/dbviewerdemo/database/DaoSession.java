package damiao.hr.dbviewerdemo.database;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import damiao.hr.dbviewerdemo.Cat;
import damiao.hr.dbviewerdemo.BadCat;

import damiao.hr.dbviewerdemo.database.CatDao;
import damiao.hr.dbviewerdemo.database.BadCatDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig catDaoConfig;
    private final DaoConfig badCatDaoConfig;

    private final CatDao catDao;
    private final BadCatDao badCatDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        catDaoConfig = daoConfigMap.get(CatDao.class).clone();
        catDaoConfig.initIdentityScope(type);

        badCatDaoConfig = daoConfigMap.get(BadCatDao.class).clone();
        badCatDaoConfig.initIdentityScope(type);

        catDao = new CatDao(catDaoConfig, this);
        badCatDao = new BadCatDao(badCatDaoConfig, this);

        registerDao(Cat.class, catDao);
        registerDao(BadCat.class, badCatDao);
    }
    
    public void clear() {
        catDaoConfig.clearIdentityScope();
        badCatDaoConfig.clearIdentityScope();
    }

    public CatDao getCatDao() {
        return catDao;
    }

    public BadCatDao getBadCatDao() {
        return badCatDao;
    }

}
