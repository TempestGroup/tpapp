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

    public TokenResponse(Person person) {
        this.refreshToken = TokenUtil.getRefreshToken(person.getUsername());
        this.accessToken = TokenUtil.getAccessToken(this.refreshToken);
    }
}
