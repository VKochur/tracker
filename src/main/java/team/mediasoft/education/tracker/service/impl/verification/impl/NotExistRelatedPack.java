package team.mediasoft.education.tracker.service.impl.verification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.FailForeignConstraintException;
import team.mediasoft.education.tracker.repository.PackRepository;
import team.mediasoft.education.tracker.repository.TypeRepository;
import team.mediasoft.education.tracker.service.impl.verification.Tester;

import java.util.Optional;

/**
 * Test is success, if doesn't exists any packs, that have type with specify id
 */
@Component
public class NotExistRelatedPack implements Tester<SurfaceException> {

    private TypeRepository typeRepository;

    private PackRepository packRepository;

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Autowired
    public void setPackRepository(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    @Override
    public SurfaceException apply(Object typeId) {
        Optional<Type> byId = typeRepository.findById((Long) typeId);
        if (byId.isPresent()) {
            if (packRepository.existsPackByType(byId.get())) {
                return new FailForeignConstraintException("related packs existed for type with id = " + typeId);
            } else {
                return null;
            }
        } else {
            //type doesn't exist => related pack's don't exist
            return null;
        }
    }
}
