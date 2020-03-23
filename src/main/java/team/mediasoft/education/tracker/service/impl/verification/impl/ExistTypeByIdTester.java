package team.mediasoft.education.tracker.service.impl.verification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.repository.TypeRepository;
import team.mediasoft.education.tracker.service.impl.verification.Tester;

/**
 * Test is success, if exists type with specify id
 */
@Component
public class ExistTypeByIdTester implements Tester<NotExistsDataException> {

    private final TypeRepository typeRepository;

    @Autowired
    public ExistTypeByIdTester(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public NotExistsDataException apply(Object typeId) {
        if (!typeRepository.existsById((Long) typeId)) {
            return new NotExistsDataException("not found type by id = " + typeId);
        }
        return null;
    }
}
