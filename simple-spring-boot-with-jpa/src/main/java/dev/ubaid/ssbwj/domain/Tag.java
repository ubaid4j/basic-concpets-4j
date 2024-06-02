package dev.ubaid.ssbwj.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Tag extends AbstractAuditingEntity<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String uuid;
    
    @NotNull
    @Column(nullable = false)
    private String name;
    
    @NotNull
    @Column(nullable = false)
    private Integer version;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();
    
    @Override
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

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull Integer getVersion() {
        return version;
    }

    public void setVersion(@NotNull Integer version) {
        this.version = version;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return getId() != null && getId().equals(((Tag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tag{" +
                "id=" + getId() +
                ", uuid='" + getUuid() + "'" +
                ", name='" + getName() + "'" +
                ", version='" + getVersion() + "'" +
                "}";
    }

}
