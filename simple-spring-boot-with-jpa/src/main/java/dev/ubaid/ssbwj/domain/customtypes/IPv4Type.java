package dev.ubaid.ssbwj.domain.customtypes;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class IPv4Type extends ImmutableType<Inet> {
    
    public IPv4Type() {
        super(Inet.class);
    }

    @Override
    protected Inet get(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String ip = rs.getString(position);
        return Objects.nonNull(ip) ? new Inet(ip) : null;
    }

    @Override
    protected void set(PreparedStatement st, Inet value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            PGobject holder = new PGobject();
            holder.setType("inet");
            holder.setValue(value.getAddress());
            st.setObject(index, holder);    
        }
    }
}
