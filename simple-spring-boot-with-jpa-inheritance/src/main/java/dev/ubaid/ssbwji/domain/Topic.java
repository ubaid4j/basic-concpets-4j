package dev.ubaid.ssbwji.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table
public class Topic {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column
    @NotNull
    private String uuid;
    
    @Column
    @NotNull
    private String title;
    
    @Column(name = "DTYPE", insertable = false, updatable = false)
    private String dType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getUuid() {
        return uuid;
    }

    public void setUuid(@NotNull String uuid) {
        this.uuid = uuid;
    }

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Topic)) {
            return false;
        }
        return getId() != null && getId().equals(((Topic) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                "} " + super.toString();
    }
}
