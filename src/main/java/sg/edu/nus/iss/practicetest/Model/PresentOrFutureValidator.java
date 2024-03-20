package sg.edu.nus.iss.practicetest.Model;

import java.util.Calendar;
import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PresentOrFutureValidator implements ConstraintValidator<PresentOrFuture, Date> {

    public final void initialize(final PresentOrFuture annotation) {}

    public final boolean isValid(final Date value, final ConstraintValidatorContext context) {
            // Return null if the input date is null
            // without this, if null is provided in html, then will crash
            if (value == null) {
                return false;
            }
            
        // Only use the date for comparison
        Calendar calendar = Calendar.getInstance(); 
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
            
        Date today = calendar.getTime();
        System.out.println("today:" + today.getTime());
        System.out.println("Value" + value.getTime());

        // Your date must be after today or today (== not before today)
        return !value.before(today) || value.equals(today);

    }
}
