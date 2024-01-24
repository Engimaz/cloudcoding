package cc.cloudcoding.manager.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPositionCommand {
    private String id;

    private String positionId;

    private String userId;
}
