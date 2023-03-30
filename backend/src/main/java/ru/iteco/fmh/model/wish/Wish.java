package ru.iteco.fmh.model.wish;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * просьба
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "wish")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    String title;
    String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    User creator;

    Instant createDate;
    Instant planExecuteDate;
    Instant factExecuteDate;

    @Enumerated(EnumType.STRING)
    Status status;

    boolean deleted;
    boolean helpRequest;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "wish_visibility", joinColumns = @JoinColumn(name = "wish_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> wishRoles;

    @OneToMany(mappedBy = "wish", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<WishExecutor> executors;

    @ManyToOne
    @JoinColumn(name = "execution_initiator_id")
    User executionInitiator;

    public void changeStatus(Status newStatus, User executor) {
        status.changeStatus(this, newStatus, executor);
    }

    public WishPriority getWishPriority() {
        if (this.planExecuteDate == null) {
            return null;
        } else {
            ZonedDateTime now = Instant.now().atZone(ZoneId.of("Europe/Moscow"));
            ZonedDateTime sixHourCurrentDay = now.toLocalDate().atStartOfDay(ZoneId.of("Europe/Moscow")).plusHours(6);
            ZonedDateTime calculatedDate = now.plusHours(2);

            if (this.planExecuteDate.isBefore(calculatedDate.toInstant())) {
                return WishPriority.RED;
            } else if (now.isAfter(sixHourCurrentDay)) {
                return WishPriority.GREEN;
            } else {
                return WishPriority.YELLOW;
            }
        }
    }
}
