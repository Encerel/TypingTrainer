package by.yankavets.typingtrainer.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class DTOErrorPrinter {

    public static String printErrors(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error : errors) {
            errorMessage
                    .append(error.getDefaultMessage())
                    .append("; ");
        }

        errorMessage = new StringBuilder(errorMessage.substring(0, errorMessage.length() - 2));
        return errorMessage.toString();
    }

}
