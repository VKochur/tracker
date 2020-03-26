package team.mediasoft.education.tracker.service.impl;


import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.StoryPoint;

/**
 * changes pack in conformity with state
 */
@Component
public class Synchronizator {

    public void doSynchronizationWithPack(StoryPoint forCreation) {
        Pack pack = forCreation.getPack();
        if (pack.getState() != forCreation.getState()) {
            pack.setState(forCreation.getState());
        }
    }
}
