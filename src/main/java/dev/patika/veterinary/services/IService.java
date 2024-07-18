package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
public interface IService<T, D, R> {

    D save(T entity);

    D findById(long id);

    List<D> findAll();

    D update(long id, R entity);

    void deleteById(long id);
}