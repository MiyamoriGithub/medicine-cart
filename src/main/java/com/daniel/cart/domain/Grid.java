package com.daniel.cart.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Data
@Component
@Getter
@Setter
public class Grid {
    /**
     * 1， 每个Grid中最多有capacity个block
     * 2， cartId，layer，row，column相同的grid应该只有一个
     * 3， 每个block的药品名称应该和grid的相同
     */
    private Long id;

    private Boolean isFull;
    private Integer capacity;               // 当前格的容量
    private Long drugInfId;                // 当前格存储药品的id，用于格中药品的校验，一个格只能存储一种药品不同日期的对象

    // 通过以下四个字段唯一地确定一个grid
    private Long cartId;                    // 所在急救车id，对应cart对象
    private Integer layer;                  // 所在急救车层数
    private Integer row;                    // 所在层的行数
    private Integer column;                 // 所在层的列数

//    {
//        this.blocks = new ArrayList<>();
//    }

    public Grid() {}

    public Grid(Long cartId, Integer layer, Integer row, Integer column) {
        this.cartId = cartId;
        this.layer = layer;
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return cartId.equals(grid.cartId) && layer.equals(grid.layer) && row.equals(grid.row) && column.equals(grid.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, layer, row, column);
    }

    @Override
    public String toString() {
        return "Grid{" +
                "id=" + id +
                ", drugInfId=" + drugInfId +
                ", cartId=" + cartId +
                ", layer=" + layer +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
