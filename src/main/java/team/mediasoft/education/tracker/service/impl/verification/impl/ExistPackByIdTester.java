package team.mediasoft.education.tracker.service.impl.verification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.repository.PackRepository;
import team.mediasoft.education.tracker.service.impl.verification.Tester;

/**
 * Test is success, if exists pack with specify id
 */
@Component
public class ExistPackByIdTester implements Tester<NotExistsDataException> {

    private final PackRepository packRepository;

    @Autowired
    public ExistPackByIdTester(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    @Override
    public NotExistsDataException apply(Object packId) {
        if (!packRepository.existsById((Long) packId)) {
            return new NotExistsDataException("not found pack by id = " + packId);
        }
        return null;
    }
}
