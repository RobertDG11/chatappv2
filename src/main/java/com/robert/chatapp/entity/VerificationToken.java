package com.robert.chatapp.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "activation")
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(nullable = false, name = "user_id")
    private User userId;

    public VerificationToken() {
        super();
    }

    public VerificationToken(final String token) {
        super();

        this.confirmationToken = token;
        this.expiryDate = calculateExpiryDate();
    }

    public VerificationToken(final String token, final User user) {
        super();

        this.confirmationToken = token;
        this.userId = user;
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, VerificationToken.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {

        setConfirmationToken(token);
        setExpiryDate(calculateExpiryDate());
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
        result = prime * result + ((confirmationToken == null) ? 0 : confirmationToken.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {

            return true;
        }

        if (obj == null) {

            return false;
        }

        if (getClass() != obj.getClass()) {

            return false;
        }

        final VerificationToken other = (VerificationToken) obj;

        if (expiryDate == null) {

            if (other.expiryDate != null) {
                return false;
            }
        }
        else if (!expiryDate.equals(other.expiryDate)) {

            return false;
        }

        if (confirmationToken == null) {

            if (other.confirmationToken != null) {
                return false;
            }
        }
        else if (!confirmationToken.equals(other.confirmationToken)) {
            return false;
        }
        if (userId == null) {
            return other.userId == null;
        } else return userId.equals(other.userId);
    }
}
