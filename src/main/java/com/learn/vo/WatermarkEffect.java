package com.learn.vo;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author cuideng
 * @className: WatermarkEffect
 * @description 水印元素信息
 * @date 2022/5/27 17:17
 */
@Data
public class WatermarkEffect implements Serializable {
    private String isOpen;
    private String watermarkName;
}

