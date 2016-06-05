package rm.com.gasy.robo_modules;

import android.app.Application;

import com.google.inject.AbstractModule;

import rm.com.gasy.persistence.dao.implementation.TankingDAO;

import rm.com.gasy.persistence.dao.interfaces.ITankingDAO;


/**
 * Created by oscargallon on 6/5/16.
 */

public class GasyModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(ITankingDAO.class).to(TankingDAO.class);
    }
}
