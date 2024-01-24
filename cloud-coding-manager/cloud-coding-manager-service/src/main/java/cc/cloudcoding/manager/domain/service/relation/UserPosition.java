package cc.cloudcoding.manager.domain.service.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/8 23:13
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope("prototype")
public class UserPosition {
    private Long id;
    private Long userId;
    private String position;
    private Long positionId;
}
