package com.project.kcookserver.product.dto;

import com.project.kcookserver.product.vo.Popularity;
import java.util.List;
import lombok.Getter;

@Getter
public class UpdatePopularityReq {

	List<Popularity> popularities;
}
