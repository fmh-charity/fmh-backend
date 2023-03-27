package ru.iteco.fmh.model.wish;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wish")
    List<WishExecutor> executors;

    public void changeStatus(Status newStatus, User executor) {
        status.changeStatus(this, newStatus, executor);
    }

    public static WishPriority getWishPriority(Wish wish) {
        if (wish.planExecuteDate == null) {
            return null;
        } else {
            Calendar sixHourCurrentDay = Calendar.getInstance();
            sixHourCurrentDay.set(Calendar.HOUR, 6);
            sixHourCurrentDay.set(Calendar.MINUTE, 0);
            sixHourCurrentDay.set(Calendar.SECOND, 0);
            Date calculatedDate = DateUtils.addHours(new Date(), 2);
            if (wish.planExecuteDate.isBefore(calculatedDate.toInstant())) {
                return WishPriority.RED;
            } else if (Calendar.getInstance().after(sixHourCurrentDay)) {
                return WishPriority.GREEN;
            } else {
                return WishPriority.YELLOW;
            }
        }
    }
}
