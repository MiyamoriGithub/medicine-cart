package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.domain.vo.CartVo;
import com.daniel.cart.exception.CartOperateException;
import com.daniel.cart.mapper.CartMapper;
import com.daniel.cart.mapper.DepartmentMapper;
import com.daniel.cart.service.CartService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daniel.cart.util.AttributeCheck.isIdOk;
import static com.daniel.cart.util.AttributeCheck.isStringOk;

@Service("cartService")
@Transactional
public class CartServiceImpl implements CartService {
    private final CartMapper mapper;
    private final DepartmentMapper departmentMapper;


    @Autowired
    public CartServiceImpl(CartMapper mapper, DepartmentMapper departmentMapper) {
        this.mapper = mapper;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<Cart> findAll() {
        return findAll(null, null);
    }

    @Override
    public List<Cart> findAll(Integer start, Integer pageSize) {
        return findAllByLimit(null, null, null, start, pageSize);
    }

    @Override
    public List<Cart> findAllByDepartment(String departmentName) {
        return findAllByDepartment(departmentName, null, null);
    }

    @Override
    public List<Cart> findAllByDepartment(String departmentName, Integer start, Integer pageSize) {
        departmentNameCheck(departmentName);
        return findAllByLimit(null, departmentName, null, start, pageSize);
    }

    @Override
    public List<Cart> findAllByDepartment(Long departmentId) {
        return findAllByDepartment(departmentId, null, null);
    }

    @Override
    public List<Cart> findAllByDepartment(Long departmentId, Integer start, Integer pageSize) {
        departmentIdCheck(departmentId);
        return findAllByLimit(departmentId, null, null, start, pageSize);
    }

    @Override
    public List<Cart> findAllByState(String state) {
        return findAllByState(state, null, null);
    }

    @Override
    public List<Cart> findAllByState(String state, Integer start, Integer pageSize) {
        stateCheck(state);
        return findAllByLimit(null, null, state, start, pageSize);
    }

    @Override
    public List<Cart> findByLimit(Long departmentId, String state) {
        return findByLimit(departmentId, state, null, null);
    }

    @Override
    public List<Cart> findByLimit(Long departmentId, String state, Integer start, Integer pageSize) {
        departmentIdCheck(departmentId);
        stateCheck(state);
        return findAllByLimit(departmentId, null, state, start, pageSize);
    }

    @Override
    public List<Cart> findByLimit(String departmentName, String state) {
        return findByLimit(departmentName, state);
    }

    @Override
    public List<Cart> findByLimit(String departmentName, String state, Integer start, Integer pageSize) {
        stateCheck(state);
        departmentNameCheck(departmentName);
        return findAllByLimit(null, departmentName, state, start, pageSize);
    }

    @Override
    public Cart findById(Long id) {
        isIdOk(id);
        return mapper.getById(id);
    }

    @Override
    public Long getCountByState(String state) {
        stateCheck(state);
        return getCountByLimit(null, null, state);
    }

    @Override
    public Long getCountByDepartment(Long departmentId) {
        departmentIdCheck(departmentId);
        return getCountByLimit(departmentId, null, null);
    }

    @Override
    public Long getCountByDepartment(String departmentName) {
        departmentNameCheck(departmentName);
        return getCountByLimit(null, departmentName, null);
    }

    @Override
    public Long getCountByLimit(Long departmentId, String state) {
        return null;
    }

    @Override
    public Long getCountByLimit(String departmentName, String state) {
        stateCheck(state);
        departmentNameCheck(departmentName);
        return getCountByLimit(null, departmentName, state);
    }

    @Override
    public Boolean addCart(Cart cart) {
        if(cart == null) {
            return false;
        }
        long res = mapper.addCart(cart);
        if(res > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean modifyCart(Cart cart) {
        if(cart == null || cart.getId() == null) {
            return false;
        }
        Long res = mapper.modifyCart(cart);
        if(res > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeCart(Long id) {
        idCheck(id);
        if(null == findById(id)) {
            throw new CartOperateException(ResultCodeEnum.CART_OPERATE_ERROR.getCode(), "待删除的急救车信息不存在");
        }
        return mapper.removeById(id) > 0;
    }

    @Override
    public Boolean setCartFree(Long id) {
        return setCartState(id, CartStateEnum.free);
    }

    @Override
    public Boolean setCartInventory(Long id) {
        return setCartState(id, CartStateEnum.inventory);
    }

    @Override
    public Boolean setCartEmergency(Long id) {
        return setCartState(id, CartStateEnum.emergency);
    }

    @Override
    public Map<String, String> getAllStates() {
        Map<String, String> map = new HashMap<>();
        for (CartStateEnum value : CartStateEnum.values()) {
            map.put(value.name(), value.getName());
        }
        return map;
    }

    private Boolean setCartState(Long id, CartStateEnum state) {
        idCheck(id);
        Cart cart = findById(id);
        if(null == cart) {
            throw new CartOperateException(ResultCodeEnum.CART_OPERATE_ERROR.getCode(), "待修改的急救车信息不存在");
        }
        cart.setState(state);
        return mapper.modifyCart(cart) > 0;
    }

    private Long getCountByLimit(Long departmentId, String nameCondition, String state) {
        CartVo limit = new CartVo();
        limit.setDepartmentId(departmentId);
        limit.setDepartmentName(nameCondition);
        limit.setState(CartStateEnum.valueOf(state));
        return mapper.getCountByLimit(limit);
    }

    private List<Cart> findAllByLimit(Long departmentId, String nameCondition, String state, Integer start, Integer pageSize) {
        CartVo limit = new CartVo();
        limit.setDepartmentId(departmentId);
        limit.setDepartmentName(nameCondition);
        limit.setState(CartStateEnum.valueOf(state));
        limit.setStart(start);
        limit.setPageSize(pageSize);
        return mapper.findAllByLimit(limit);
    }

    private void idCheck(Long id) {
        if(!isIdOk(id)) {
            throw new CartOperateException(ResultCodeEnum.CART_OPERATE_ERROR.getCode(), "id 信息缺失");
        }
    }

    private void stateCheck(String state) {
        if(!isStringOk(state) || !EnumUtils.isValidEnum(CartStateEnum.class, state)) {
            throw new CartOperateException(ResultCodeEnum.CART_QUERY_ERROR.getCode(), "状态信息缺失");
        }
    }

    private void departmentNameCheck(String departmentName) {
        if(!isStringOk(departmentName)) {
            throw new CartOperateException(ResultCodeEnum.CART_QUERY_ERROR.getCode(), "部门名称信息缺失");
        }
    }

    private void departmentIdCheck(Long departmentId) {
        if(!isIdOk(departmentId)) {
            throw new CartOperateException(ResultCodeEnum.CART_QUERY_ERROR.getCode(), "部门 id 信息缺失");
        }
        if(departmentMapper.findById(departmentId) == null) {
            throw new CartOperateException(ResultCodeEnum.CART_QUERY_ERROR.getCode(), "对应部门信息不存在");
        }
    }


}
