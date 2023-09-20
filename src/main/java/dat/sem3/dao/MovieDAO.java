package dat.sem3.dao;

import dat.sem3.config.HibernateConfig;
import dat.sem3.model.MovieEntity;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MovieDAO implements DAOInterface<MovieEntity> {
    EntityManagerFactory emf;

    private static MovieDAO instance;

    private MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static MovieDAO getInstance() {
        if (instance == null) {
            instance = new MovieDAO(HibernateConfig.getEntityManagerFactoryConfig("my_movie_db"));
        }

        return instance;
    }

    @Override
    public MovieEntity persist(MovieEntity entity) {
        MovieEntity e = getById(entity.getImdbId());
        if (e == null) {
            try (var em = emf.createEntityManager()) {
                em.getTransaction().begin();
                em.persist(entity);
                em.getTransaction().commit();
            }
            return entity;
        }

        return e;
    }

    @Override
    public MovieEntity update(MovieEntity entity) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public void delete(MovieEntity entity) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }
    }

    @Override
    public <K> MovieEntity getById(K id) {
        MovieEntity res = null;
        try (var em = emf.createEntityManager()) {
            res = em.find(MovieEntity.class, id);
        }

        return res;
    }

    @Override
    public List<MovieEntity> getAll() {
        try (var em = emf.createEntityManager()) {
            TypedQuery<MovieEntity> q = em.createQuery("SELECT m FROM MovieEntity m", MovieEntity.class);

            return q.getResultList();
        }
    }

    @Override
    public <K> List<MovieEntity> getByRating(K rating) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<MovieEntity> q = em.createQuery("SELECT m FROM MovieEntity m WHERE m.rating >= :rating", MovieEntity.class);
            q.setParameter("rating", rating);

            return q.getResultList();
        }
    }

    @Override
    public List<MovieEntity> getSortedByReleaseYear() {
        try (var em = emf.createEntityManager()) {
            TypedQuery<MovieEntity> q = em.createQuery("SELECT m FROM MovieEntity m ORDER BY releaseYear DESC", MovieEntity.class);

            return q.getResultList();
        }
    }
}
