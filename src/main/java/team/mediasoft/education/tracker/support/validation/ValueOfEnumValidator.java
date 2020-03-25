package team.mediasoft.education.tracker.support.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {


   private List<String> enumNames;

   @Override
   public void initialize(ValueOfEnum constraint) {
      Enum<?>[] enumConstants = constraint.enumClazz().getEnumConstants();
      enumNames = new ArrayList<>();
      Function<Enum<?>, String> mapper = anEnum -> anEnum.name();
      for (Enum<?> enumConstant : enumConstants) {
         String name = mapper.apply(enumConstant);
         enumNames.add(name);
      }
   }

   @Override
   public boolean isValid(CharSequence str, ConstraintValidatorContext context) {
      if (str == null) {
         return true;
      } else {
         return enumNames.contains(str);
      }
   }
}
