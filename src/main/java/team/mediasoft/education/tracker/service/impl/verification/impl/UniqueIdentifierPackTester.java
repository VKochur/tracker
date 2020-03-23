package team.mediasoft.education.tracker.service.impl.verification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.exception.tree.request.NotUniqueDataException;
import team.mediasoft.education.tracker.repository.PackRepository;
import team.mediasoft.education.tracker.service.impl.verification.Tester;

import java.util.Optional;

/**
 * Test is success, if doesn't exists any packs, that have specify identifier
 */
@Component
public class UniqueIdentifierPackTester implements Tester<NotUniqueDataException> {

    private PackRepository packRepository;

    @Autowired
    public UniqueIdentifierPackTester(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    @Override
    public NotUniqueDataException apply(Object packIdentifier) {
        Optional<Pack> byIdentifier = packRepository.findByIdentifier((String) packIdentifier);
        if (byIdentifier.isPresent()) {
            return new NotUniqueDataException("pack with identifier = \"" + packIdentifier + "\" existed yet");
        }
        return null;
    }
}
