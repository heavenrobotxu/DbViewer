package damiao.hr.dbviewerdemo.database;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import damiao.hr.dbviewerdemo.Cat;

import damiao.hr.dbviewerdemo.database.CatDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig catDaoConfig;

    private final CatDao catDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        catDaoConfig = daoConfigMap.get(CatDao.class).clone();
        catDaoConfig.initIdentityScope(type);

        catDao = new CatDao(catDaoConfig, this);

        registerDao(Cat.class, catDao);
    }
    
    public void clear() {
        catDaoConfig.clearIdentityScope();
    }

    public CatDao getCatDao() {
        return catDao;
    }

}
