package helpers;

public class UnknownParameterTypeException extends Throwable {

    public final Class parameterType;

    public UnknownParameterTypeException(Class parameterType) {
        this.parameterType = parameterType;
    }

    public UnknownParameterTypeException(String message, Class parameterType) {
        super(message);
        this.parameterType = parameterType;
    }

    public Class getParameterType() {
        return parameterType;
    }
}
