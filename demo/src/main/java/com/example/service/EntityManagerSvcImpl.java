package com.example.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.testo.core.service.EntityManagerSvc;


@Component
public class EntityManagerSvcImpl implements EntityManagerSvc {

	
	@PersistenceContext(unitName = "PU1")
	EntityManager emA;
	
	@PersistenceContext(unitName = "PU2")
	EntityManager emB;
	
	@PersistenceContext(unitName = "PU3")
	EntityManager emC;

	@Override
	public EntityManager getInstance() {
		return emA;
	}

	@Override
	public EntityManager getMain() {
		return emB;
	}

	@Override
	public EntityManager getMulti() {
		return emC;
	}
}
