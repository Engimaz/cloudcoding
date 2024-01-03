package cn.edu.hcnu.manager.domain.service.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 14:22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope("prototype")
public class FeatureUrl {
    private Long id;
    private Long featureId;
    private Long urlId;
}
