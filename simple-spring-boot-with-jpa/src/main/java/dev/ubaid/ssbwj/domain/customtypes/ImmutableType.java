package dev.ubaid.ssbwj.domain.customtypes;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class ImmutableType<T> implements UserType<T> {
    
    private final Class<T> clazz;
    
    protected ImmutableType(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    @Override
    public int getSqlType() {
        return 0;
    }

    @Override
    public Class<T> returnedClass() {
        return clazz;
    }

    @Override
    public boolean equals(Object x, Object y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) {
        return x.hashCode();
    }

    @Override
    public T nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        return get(rs, position, session, owner);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        set(st, clazz.cast(value), index, session);
    }

    @Override
    public T deepCopy(Object value) {
        return clazz.cast(value);
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) {
        return (Serializable) value;
    }

    @Override
    public T assemble(Serializable cached, Object owner) {
        return clazz.cast(cached);
    }
    
    protected abstract T get(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException;
    
    protected abstract void set(PreparedStatement st, T value, int index, SharedSessionContractImplementor session) throws SQLException;
}
