package site.strangebros.nork.domain.currentWorker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.strangebros.nork.domain.currentWorker.entity.CurrentWorkerHolder;

@Repository
public interface CurrentWorkerHolderRepository extends CrudRepository<CurrentWorkerHolder, String> {
}
