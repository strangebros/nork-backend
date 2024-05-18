package site.strangebros.nork.domain.currentWorker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.strangebros.nork.domain.currentWorker.entity.CurrentWorker;

@Repository
public interface CurrentWorkerRepository extends CrudRepository<CurrentWorker, String> {
}
