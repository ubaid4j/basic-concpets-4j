import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public final class Period implements Serializable {

    @Serial
    private static final long serialVersionUID = 4647424730390249716L;

    private final Date start;
    private final Date end;

    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        validateDate();
    }

    private void validateDate() {
        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
    }

    public Date getStart() {
        return new Date(start.getTime());
    }

    public Date getEnd() {
        return new Date(end.getTime());
    }

    @Override
    public String toString() {
        return "Period_{" +
            "start=" + start +
            ", end=" + end +
            '}';
    }
    
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
/*
        ois.defaultReadObject();
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        validateDate();
*/
        
        throw new InvalidObjectException("Proxy required");
    }
    
    @Serial
    private Object writeReplace() {
        return new SerializationProxy(this);
    }
    
    private static class SerializationProxy implements Serializable {
        
        @Serial
        private static final long serialVersionUID = 23409823485285L;
        
        private final Date start;
        private final Date end;
        
        SerializationProxy(Period p) {
            this.start = p.start;
            this.end = p.end;
        }
        
        @Serial
        private Object readResolve() {
            return new Period(start, end);
        }
    }

}
