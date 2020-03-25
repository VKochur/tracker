package team.mediasoft.education.tracker.service.impl;


import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.StoryPoint;

import javax.transaction.Synchronization;
import javax.transaction.Transactional;

@Component
public class SynchronizatorImpl implements Synchronizator {

    //@Transactional
    @Override
    public void doSynchronizationWithPack(StoryPoint forCreation) {
        Pack pack = forCreation.getPack();
        if (pack.getState() != forCreation.getState()) {
            pack.setState(forCreation.getState());
        }
    }
}
