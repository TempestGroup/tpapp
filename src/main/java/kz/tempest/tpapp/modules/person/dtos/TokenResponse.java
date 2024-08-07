package kz.tempest.tpapp.modules.person.dtos;

import kz.tempest.tpapp.commons.utils.TokenUtil;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String refreshToken;
    private String accessToken;
    private String mobileToken = "";

    public TokenResponse(Person person) {
        this(person, false);
    }

    public TokenResponse(Person person, boolean mobile) {
        this.refreshToken = TokenUtil.getRefreshToken(person.getUsername());
        this.accessToken = TokenUtil.getAccessToken(this.refreshToken);
        if (mobile) {
            this.mobileToken = TokenUtil.getMobileToken(person.getUsername());
        } else {
            this.mobileToken = "";
        }
    }
}
