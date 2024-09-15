package kz.tempest.tpapp.modules.person.dtos;

import kz.tempest.tpapp.commons.contexts.PersonContext;
import kz.tempest.tpapp.commons.utils.TokenUtil;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String refreshToken;
    private String accessToken;
    private String mobileToken = "";

    public TokenResponse() {
        this(PersonContext.getCurrentPerson());
    }

    public TokenResponse(Person person) {
        this(person, false);
    }

    public TokenResponse(Person person, boolean withMobileToken) {
        this.refreshToken = TokenUtil.getRefreshToken(person.getUsername());
        this.accessToken = TokenUtil.getAccessToken(this.refreshToken);
        if (withMobileToken) {
            this.mobileToken = TokenUtil.getMobileToken(person.getUsername());
        } else {
            this.mobileToken = "";
        }
    }
}
