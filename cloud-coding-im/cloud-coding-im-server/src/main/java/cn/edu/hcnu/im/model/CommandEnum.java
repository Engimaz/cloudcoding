package cn.edu.hcnu.im.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandEnum {

    ONLINE("online"),

    CHAT("chat"),
    COMMMENT("comment"),

    ERROR("error");

    private String code;

    public static CommandEnum match(String code) {
        for (CommandEnum value : CommandEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return ERROR;
    }

}
