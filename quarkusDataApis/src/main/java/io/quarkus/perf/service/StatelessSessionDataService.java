package io.quarkus.perf.service;

import io.quarkus.perf.entity.DataEntity;
import org.hibernate.StatelessSession;
import org.hibernate.query.NativeQuery;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class StatelessSessionDataService {

    private static final Logger LOG = Logger.getLogger(StatelessSessionDataService.class);

    @Inject
    StatelessSession session;

    public List<DataEntity> getAllData(){

        LOG.debug("Executing getAllData()");

        List<DataEntity> results = null;

        session.beginTransaction();

        try {
            NativeQuery query = session.createNativeQuery("SELECT * FROM DATA_OBJECTS", DataEntity.class);
            results = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return  results;
    }

    public DataEntity getDataItem(String dataname){

        LOG.debug("Executing getDataItem()");

        DataEntity result = null;

        session.beginTransaction();

        try {
            NativeQuery query = session.createNativeQuery("SELECT * FROM DATA_OBJECTS WHERE DATA_NAME = ?", DataEntity.class);
            query.setParameter(1, dataname);
            result = (DataEntity) query.getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return result;
    }

}
