package site.strangebros.nork.domain.currentWorker.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.strangebros.nork.domain.currentWorker.entity.CurrentWorker;
import site.strangebros.nork.domain.currentWorker.entity.CurrentWorkerHolder;
import site.strangebros.nork.domain.currentWorker.repository.CurrentWorkerHolderRepository;
import site.strangebros.nork.domain.currentWorker.repository.CurrentWorkerRepository;
import site.strangebros.nork.domain.currentWorker.service.dto.CreateWorkerRequest;
import site.strangebros.nork.domain.currentWorker.service.dto.RefreshWorkerRequest;

@RequiredArgsConstructor
@Service
public class CurrentWorkerService {

    private final CurrentWorkerHolderRepository currentWorkerHolderRepository;
    private final CurrentWorkerRepository currentWorkerRepository;

    public List<CurrentWorker> getWorkersOfWorkspace(int workspaceId) {
        Optional<CurrentWorkerHolder> currentWorkerHolder = currentWorkerHolderRepository.findById(
                String.valueOf(workspaceId));
        if (currentWorkerHolder.isEmpty()) {
            return List.of();
        }

        return findCurrentWorkers(currentWorkerHolder.get());
    }

    private List<CurrentWorker> findCurrentWorkers(CurrentWorkerHolder currentWorkerHolder) {
        List<Integer> workerIds = currentWorkerHolder.getWorkerIds();
        return workerIds.stream()
                .map(workerId -> currentWorkerRepository.findById(String.valueOf(workerId))
                                .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    public void createWorker(int memberId, CreateWorkerRequest request) {
        CurrentWorkerHolder currentWorkerHolder = currentWorkerHolderRepository.findById(String.valueOf(request.getWorkspaceId()))
                .orElse(CurrentWorkerHolder.builder().workspaceId(request.getWorkspaceId()).build());
        CurrentWorker currentWorker = request.toEntity(memberId);

        currentWorkerHolder.addWorker(currentWorker.getId());

        currentWorkerRepository.save(currentWorker);
        currentWorkerHolderRepository.save(currentWorkerHolder);
    }

    public void refreshWorker(int memberId, RefreshWorkerRequest request) {
        CurrentWorker currentWorker = currentWorkerRepository.findById(String.valueOf(request.getReservationId()))
                .orElseThrow(() -> new IllegalArgumentException("요청 장소와 회원이 유효하지 않습니다."));

        currentWorker.refresh();
        currentWorkerRepository.save(currentWorker);
    }

}
