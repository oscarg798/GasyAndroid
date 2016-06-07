package rm.com.gasy.robo_modules;


import com.google.inject.AbstractModule;

import rm.com.gasy.persistence.dao.implementation.TankingDAO;

import rm.com.gasy.persistence.dao.interfaces.ITankingDAO;


/**
 * Created by oscargallon on 6/5/16.
 * This class is a {@link AbstractModule} instance to
 * bind all the interfaces with their correct implementation
 */
public class GasyModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(ITankingDAO.class).to(TankingDAO.class);
    }
}
