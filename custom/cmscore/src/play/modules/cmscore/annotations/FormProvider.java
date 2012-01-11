package play.modules.cmscore.annotations;

import play.modules.cmscore.Leaf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FormProvider {

    Class<? extends Leaf> type();

}
