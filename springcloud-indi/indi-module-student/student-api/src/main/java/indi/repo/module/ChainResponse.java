package indi.repo.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Andon
 * 2022/2/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChainResponse<T> implements Serializable {

    private int code;
    private T data;
    private String message;
}
