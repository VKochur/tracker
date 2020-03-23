package team.mediasoft.education.tracker.service.impl.verification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.exception.tree.request.NotUniqueDataException;
import team.mediasoft.education.tracker.repository.TypeRepository;
import team.mediasoft.education.tracker.service.impl.verification.Tester;

import java.util.Optional;

/**
 * Test is success, if doesn't exists any types, that have specify identifier (ignore case for match)
 */
@Component
public class UniqueNameTypeTester implements Tester<NotUniqueDataException> {

    private TypeRepository typeRepository;

    @Autowired
    public UniqueNameTypeTester(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public NotUniqueDataException apply(Object typeName) {
        Optional<Long> idByNameIgnoreCase = typeRepository.findIdByNameIgnoreCase((String) typeName);
        if (idByNameIgnoreCase.isPresent()) {
            return new NotUniqueDataException("type \"" + typeName + "\" exists yet (differences may be in case of letters only)");
        }
        return null;
    }
}
