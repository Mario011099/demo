package com.example.demo.dao;

import com.example.demo.model.Vehiculo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;


@Stateless
public class VehiculoDao {
    @PersistenceContext(name = "demo")
    protected EntityManager entityManager;
    public void guardar(Vehiculo vehiculo){
        entityManager.persist(vehiculo);
    }

    public List<Vehiculo> obtenerVehiculosMantenimiento(Date fechaConsulta){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT v ");
        sql.append(" FROM Vehiculo v ");
        sql.append(" WHERE :fechaConsulta BETWEEN v.fechaCompra and DATE_ADD(v.fechaComrpa, INTERVAL :dias DAY) ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("fechaConsulta", fechaConsulta);
        query.setParameter("dias", 60);
        return query.getResultList();
    }
}
