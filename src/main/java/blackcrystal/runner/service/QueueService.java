package blackcrystal.runner.service;


import blackcrystal.runner.domain.Queue;

import java.util.List;

public interface  QueueService {

    List<Queue> getAll();

    Queue getNext();

    boolean hasNext();

    Queue remove();

    void add(Queue queue);

}
