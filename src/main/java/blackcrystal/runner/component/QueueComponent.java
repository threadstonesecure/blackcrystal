package blackcrystal.runner.component;

import blackcrystal.runner.domain.Queue;
import blackcrystal.runner.service.QueueService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("queueService")
public class QueueComponent implements QueueService {

    private List<Queue> list = new ArrayList<Queue>();

    @Override
    public List<Queue> getAll() {
        return list;
    }

    @Override
    public Queue getNext() {
        return list.iterator().next();
    }

    @Override
    public boolean hasNext() {
        return list.iterator().hasNext();
    }

    @Override
    public Queue remove() {
        return list.remove(0);
    }

    @Override
    public void add(Queue queue) {
       list.add(queue);
    }
}
