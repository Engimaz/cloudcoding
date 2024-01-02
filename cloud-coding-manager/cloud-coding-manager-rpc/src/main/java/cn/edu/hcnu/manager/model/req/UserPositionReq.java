package cn.edu.hcnu.manager.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPositionReq {
    private Long id;

    // 职位value
    private String position;

    private Long userId;
}
