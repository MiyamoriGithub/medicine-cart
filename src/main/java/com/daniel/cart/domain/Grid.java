package com.daniel.cart.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Todo Grid和Block的关系还需要再考虑，以及数据库的设计

@ToString
@Getter
@Setter
@Component
public class Grid {
    /**
     * 1， 每个Grid中最多有capacity个block
     * 2， cartId，layer，row，column相同的grid应该只有一个
     * 3， 每个block的药品名称应该和grid的相同
     */
    private Long id;

    @Getter(AccessLevel.NONE)
    private List<Block> blocks;             // 每个格中有多个块，每个块存储一个药品
    private Integer capacity;               // 当前格的容量
    private String drugName;                // 当前格存储药品的名称，用于格中药品的校验，一个格只能存储一种药品不同日期的对象

    // 通过以下四个字段唯一地确定一个grid
    private Long cartId;                    // 所在急救车id，对应cart对象
    private Integer layer;                  // 所在急救车层数
    private Integer row;                    // 所在层的行数
    private Integer column;                 // 所在层的列数

    {
        this.blocks = new ArrayList<>();
    }

    public Grid() {}

    public Grid(Long cartId, Integer layer, Integer row, Integer column) {
        this.cartId = cartId;
        this.layer = layer;
        this.row = row;
        this.column = column;
    }

    // Todo 完成存入药品逻辑，以及编写对应的异常类
    public void addBlock(Block block) {
        if(this.drugName != block.getDrugName()) {
            throw new RuntimeException("药品不一致");
        }
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
}
