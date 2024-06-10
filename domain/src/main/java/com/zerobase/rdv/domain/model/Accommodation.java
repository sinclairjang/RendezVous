package com.zerobase.rdv.domain.model;

import com.zerobase.rdv.domain.model.validator.RangeCheck;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RDV_ACCOMMODATION")
@RangeCheck(message = "예약 가능 범위 체크")
public class Accommodation {
    @Id
    @GeneratedValue(generator = "addressKeyGenerator")
    @org.hibernate.annotations.GenericGenerator(
            name = "addressKeyGenerator",
            strategy = "foreign",
            parameters =
                    @org.hibernate.annotations.Parameter(
                            name = "property", value = "business"
                    )
    )
    private Long id;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private Business business;

    @NotNull
    @Min(0)
    private Long totalAvailability;

    @NotNull
    @Min(0)
    private Long currentAvailability;

    public Accommodation(Business business, Long totalAvailability, Long currentAvailability) {
        this.business = business;
        this.totalAvailability = totalAvailability;
        this.currentAvailability = currentAvailability;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Long getTotalAvailability() {
        return totalAvailability;
    }

    public void setTotalAvailability(Long totalAvailability) {
        this.totalAvailability = totalAvailability;
    }

    public Long getCurrentAvailability() {
        return currentAvailability;
    }

    public void setCurrentAvailability(Long currentAvailability) {
        this.currentAvailability = currentAvailability;
    }
}
