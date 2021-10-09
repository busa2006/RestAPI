package com.kurly.demo.repository;

import com.kurly.demo.domain.Goods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoodsRepository {
    private final EntityManager em;
    public Goods save(Goods goods){
            em.persist(goods);
            return goods;
    }

    public Goods findOne(Long id){
        return em.find(Goods.class,id);
    }

    public List<Goods> findAll() {
        return em.createQuery("select g from Goods g", Goods.class)
                .getResultList();
    }

    public List<Goods> findPaging(int n){
        return em.createQuery("select g from Goods g",Goods.class)
                .setFirstResult(0)
                .setMaxResults(n)
                .getResultList();
    }

    @Modifying
    public void deleteById(@Param("id") Long id) {
        em.createQuery("delete from Goods g where g.id = :id");
    }
}
