package com.example.practica3.Repositorio;

import com.example.practica3.Entidades.SensorData;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class SensorDataRepository implements JpaRepository<SensorData, Long> {


    @Override
    public void flush() {

    }

    @Override
    public <S extends SensorData> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends SensorData> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<SensorData> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public SensorData getOne(Long aLong) {
        return null;
    }

    @Override
    public SensorData getById(Long aLong) {
        return null;
    }

    @Override
    public SensorData getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends SensorData> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends SensorData> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends SensorData> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends SensorData> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SensorData> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends SensorData> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends SensorData, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends SensorData> S save(S entity) {
        return null;
    }

    @Override
    public <S extends SensorData> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<SensorData> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<SensorData> findAll() {
        return null;
    }

    @Override
    public List<SensorData> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(SensorData entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends SensorData> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<SensorData> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<SensorData> findAll(Pageable pageable) {
        return null;
    }
}
