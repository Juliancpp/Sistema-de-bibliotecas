package DataAccess.DAO;

import java.util.List;

public interface IDAO<T> {
    public T getBy(Integer id) throws Exception;

    public List<T> getAll() throws Exception;

    public boolean create(T entity) throws Exception;

    public boolean update(T entity) throws Exception;

    public boolean delete(Integer id) throws Exception;

}
